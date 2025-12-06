package io.jiangbyte.app.biz.cart.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.cart.dto.CardReq;
import io.jiangbyte.app.biz.cart.entity.BizCart;
import io.jiangbyte.app.biz.cart.dto.BizCartDto;
import io.jiangbyte.app.biz.cart.dto.BizCartPageQuery;
import io.jiangbyte.app.biz.cart.mapper.BizCartMapper;
import io.jiangbyte.app.biz.cart.service.BizCartService;
import io.jiangbyte.app.biz.product.entity.BizProduct;
import io.jiangbyte.app.biz.product.mapper.BizProductMapper;
import io.jiangbyte.app.biz.sale.entity.BizFlashSale;
import io.jiangbyte.app.biz.sale.mapper.BizFlashSaleMapper;
import io.jiangbyte.app.biz.sku.entity.BizProductSku;
import io.jiangbyte.app.biz.sku.mapper.BizProductSkuMapper;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.enums.ISortOrderEnum;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-06-23
 * @description 购物车表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BizCartServiceImpl extends ServiceImpl<BizCartMapper, BizCart> implements BizCartService {
    private final BizProductMapper bizProductMapper;
    private final BizProductSkuMapper bizProductSkuMapper;
    private final BizFlashSaleMapper bizFlashSaleMapper;

    @Override
    public Page<BizCart> page(BizCartPageQuery req) {
        QueryWrapper<BizCart> queryWrapper = new QueryWrapper<BizCart>().checkSqlInjection();
        SortUtils.handleSort(BizCart.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCartDto req) {
        BizCart bean = BeanUtil.toBean(req, BizCart.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCartDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizCart>().eq(BizCart::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizCart bean = BeanUtil.toBean(req, BizCart.class);
        BeanUtil.copyProperties(req, bean);
        this.updateById(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        this.removeByIds(ids);
    }

    @Override
    public BizCart detail(String id) {
        BizCart bizCart = this.getById(id);
        if (ObjectUtil.isEmpty(bizCart)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizCart;
    }

    @Override
    public List<BizCart> latest(int n) {
        return this.list(new QueryWrapper<BizCart>()
                .lambda()
                .orderByDesc(BizCart::getId)
                .last("limit " + n));
    }

    @Override
    public List<BizCart> topN(int n) {
        return this.list(new QueryWrapper<BizCart>()
                .lambda()
                .orderByDesc(BizCart::getId)
                .last("limit " + n));
    }

    // ==================== 核心业务方法 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToCart(CardReq req) {
        String userId = "1"; // TODO 模拟用户ID, 实际项目需要从上下文中获取
        // 参数校验
        String productId = req.getProductId();
        Integer quantity = req.getQuantity();
        String skuId = req.getSkuId();
        String flashId = req.getFlashId();
        String specId = req.getSpecId();

        if (StrUtil.isBlank(userId) || StrUtil.isBlank(productId) || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误");
        }
        if (skuId != null && flashId != null) {
            throw new BusinessException("不能同时指定 SKU 和抢购活动");
        }

        // 校验商品
        BizProduct product = bizProductMapper.selectById(productId);
        if (product == null || !product.getStatus()) {
            throw new BusinessException("商品不存在或已下架");
        }

        // 分支校验
        if (flashId != null) {
            validateFlashSale(flashId, quantity);
        } else {
            if (skuId == null) throw new BusinessException("普通商品必须指定 SKU");
            validateSku(skuId, quantity);
        }

        // 构建唯一键查询条件
        LambdaQueryWrapper<BizCart> query = new LambdaQueryWrapper<BizCart>()
                .eq(BizCart::getUserId, userId)
                .eq(BizCart::getProductId, productId)
                .eq(skuId != null, BizCart::getSkuId, skuId)
                .eq(flashId != null, BizCart::getFlashId, flashId);
//                .eq(specId != null, BizCart::getSpecId, specId);

        BizCart existing = this.getOne(query);
        if (existing != null) {
            // 合并数量
            int totalQty = existing.getQuantity() + quantity;
            // 再次校验合并后是否超限
            if (flashId != null) {
                validateFlashSale(flashId, totalQty);
            } else {
                validateSku(skuId, totalQty);
            }
            existing.setQuantity(totalQty);
            this.updateById(existing);
        } else {
            // 新增
            BizCart cart = new BizCart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setSkuId(skuId);
            cart.setFlashId(flashId);
            cart.setSpecId(specId);
            cart.setQuantity(quantity);
            cart.setSelected(true); // 默认选中
            this.save(cart);
        }
    }

    // ==================== 私有校验方法 ====================

    private void validateSku(String skuId, Integer quantity) {
        BizProductSku sku = bizProductSkuMapper.selectById(skuId);
        if (sku == null || !sku.getStatus()) {
            throw new BusinessException("SKU 不存在或已下架");
        }
        if (quantity > sku.getTotalStock()) {
            throw new BusinessException("库存不足，当前可购 " + sku.getTotalStock() + " 件");
        }
        // 限制检查，0 表示不限制
        if (sku.getLimitPerUser() > 0 && quantity > sku.getLimitPerUser()) {
            throw new BusinessException("超过单次限购数量：" + sku.getLimitPerUser());
        }
    }

    private void validateFlashSale(String flashId, Integer quantity) {
        BizFlashSale flash = bizFlashSaleMapper.selectById(flashId);
        if (flash == null || !flash.getStatus()) {
            throw new BusinessException("抢购活动不存在或已下架");
        }
        Date now = new Date();
        if (now.before(flash.getStartTime()) || now.after(flash.getEndTime())) {
            throw new BusinessException("抢购未开始或已结束");
        }
        // 限制检查，0 表示不限制
        if (flash.getLimitPerUser() > 0 && quantity > flash.getLimitPerUser()) {
            throw new BusinessException("超过每人限购数量：" + flash.getLimitPerUser());
        }
        if (quantity > flash.getTotalStock()) {
            throw new BusinessException("抢购库存不足，当前仅剩 " + flash.getTotalStock() + " 件");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCartItemQuantity(String cartId, Integer newQuantity) {
        if (newQuantity == null || newQuantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }
        BizCart cart = this.getById(cartId);
        if (cart == null) {
            throw new BusinessException("购物车项不存在");
        }
        // 重新校验
        if (cart.getFlashId() != null) {
            validateFlashSale(cart.getFlashId(), newQuantity);
        } else {
            validateSku(cart.getSkuId(), newQuantity);
        }
        cart.setQuantity(newQuantity);
        this.updateById(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCartItem(String cartId) {
        this.removeById(cartId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selectCartItem(String cartId, boolean selected) {
        BizCart cart = this.getById(cartId);
        if (cart != null) {
            cart.setSelected(selected);
            this.updateById(cart);
        }
    }

    @Override
    public List<BizCart> getSelectedCartItemsForCheckout() {
        String userId = "1";
        return this.list(new LambdaQueryWrapper<BizCart>()
                .eq(BizCart::getUserId, userId)
                .eq(BizCart::getSelected, true)
                .eq(BizCart::getIsDeleted, false));
    }

}