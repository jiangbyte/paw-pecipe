package io.jiangbyte.app.biz.sku.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.product.dto.BizProductDto;
import io.jiangbyte.app.biz.product.entity.BizProduct;
import io.jiangbyte.app.biz.product.mapper.BizProductMapper;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuWithProductDto;
import io.jiangbyte.app.biz.sku.entity.BizProductSku;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuDto;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuPageQuery;
import io.jiangbyte.app.biz.sku.mapper.BizProductSkuMapper;
import io.jiangbyte.app.biz.sku.service.BizProductSkuService;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsDto;
import io.jiangbyte.app.biz.specs.entity.BizProductSpecs;
import io.jiangbyte.app.biz.specs.mapper.BizProductSpecsMapper;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.enums.ISortOrderEnum;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.dromara.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-06-23
 * @description 商品日常销售SKU表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BizProductSkuServiceImpl extends ServiceImpl<BizProductSkuMapper, BizProductSku> implements BizProductSkuService {
    private final BizProductMapper bizProductMapper;
    private final BizProductSpecsMapper bizProductSpecsMapper;
    private final TransService transService;

    @Override
    public Page<BizProductSku> page(BizProductSkuPageQuery req) {
        QueryWrapper<BizProductSku> queryWrapper = new QueryWrapper<BizProductSku>().checkSqlInjection();
        SortUtils.handleSort(BizProductSku.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Override
    public Page<BizProductSkuWithProductDto> pageWithProduct(BizProductSkuPageQuery req) {
        Page<BizProductSkuWithProductDto> page = BasePageRequest.Page(
                Optional.ofNullable(req.getCurrent()).orElse(1),
                Optional.ofNullable(req.getPageSize()).orElse(10));
        if ("0".equals(req.getCategoryId())) {
            req.setCategoryId(null);
        }
        page.addOrder(OrderItem.desc("sku_id"));
        return this.baseMapper.selectSkuWithProductPage(page, req);
    }

    @Override
    public BizProductSkuWithProductDto detailWithProduct(String id) {
        BizProductSku byId = this.getById(id);

        if (ObjectUtil.isEmpty(byId)) {
            return null;
        }

        BizProductSkuWithProductDto dto = new BizProductSkuWithProductDto();
        BeanUtil.copyProperties(byId, dto);

        if (ObjectUtil.isNotEmpty(byId.getProductId())) {
            BizProduct bizProduct = bizProductMapper.selectById(byId.getProductId());
            dto.setProduct(bizProduct);
            List<BizProductSpecs> bizProductSpecs = bizProductSpecsMapper.selectList(new LambdaQueryWrapper<BizProductSpecs>().eq(BizProductSpecs::getProductId, byId.getProductId()));
            dto.setSpecs(bizProductSpecs);
        }

        transService.transOne(dto);
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProductSkuDto req) {
        BizProductSku bean = BeanUtil.toBean(req, BizProductSku.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProductSkuDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizProductSku>().eq(BizProductSku::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizProductSku bean = BeanUtil.toBean(req, BizProductSku.class);
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
    public BizProductSku detail(String id) {
        BizProductSku bizProductSku = this.getById(id);
        if (ObjectUtil.isEmpty(bizProductSku)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizProductSku;
    }

    @Override
    public List<BizProductSku> latest(int n) {
        return this.list(new QueryWrapper<BizProductSku>()
                .lambda()
                .orderByDesc(BizProductSku::getId)
                .last("limit " + n));
    }

    @Override
    public List<BizProductSku> topN(int n) {
        return this.list(new QueryWrapper<BizProductSku>()
                .lambda()
                .orderByDesc(BizProductSku::getId)
                .last("limit " + n));
    }

}