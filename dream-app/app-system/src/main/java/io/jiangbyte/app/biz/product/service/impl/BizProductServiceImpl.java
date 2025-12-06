package io.jiangbyte.app.biz.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.product.entity.BizProduct;
import io.jiangbyte.app.biz.product.dto.BizProductDto;
import io.jiangbyte.app.biz.product.dto.BizProductPageQuery;
import io.jiangbyte.app.biz.product.mapper.BizProductMapper;
import io.jiangbyte.app.biz.product.service.BizProductService;
import io.jiangbyte.app.biz.recipe.entity.BizRecipe;
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
* @description 商品主表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizProductServiceImpl extends ServiceImpl<BizProductMapper, BizProduct> implements BizProductService {

    @Override
    public Page<BizProduct> page(BizProductPageQuery req) {
        QueryWrapper<BizProduct> queryWrapper = new QueryWrapper<BizProduct>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(BizProduct::getTitle, req.getKeyword());
        }
        SortUtils.handleSort(BizProduct.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProductDto req) {
        BizProduct bean = BeanUtil.toBean(req, BizProduct.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProductDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizProduct>().eq(BizProduct::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizProduct bean = BeanUtil.toBean(req, BizProduct.class);
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
    public BizProduct detail(String id) {
        BizProduct bizProduct = this.getById(id);
        if (ObjectUtil.isEmpty(bizProduct)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizProduct;
    }

    @Override
    public List<BizProduct> latest(int n) {
        return this.list(new QueryWrapper<BizProduct>()
            .lambda()
            .orderByDesc(BizProduct::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizProduct> topN(int n) {
        return this.list(new QueryWrapper<BizProduct>()
            .lambda()
            .orderByDesc(BizProduct::getId)
            .last("limit " + n));
    }

}