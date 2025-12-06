package io.jiangbyte.app.biz.productcategory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.productcategory.entity.BizProductCategory;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryDto;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryPageQuery;
import io.jiangbyte.app.biz.productcategory.mapper.BizProductCategoryMapper;
import io.jiangbyte.app.biz.productcategory.service.BizProductCategoryService;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.framework.option.LabelOption;
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
* @description 商品分类表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizProductCategoryServiceImpl extends ServiceImpl<BizProductCategoryMapper, BizProductCategory> implements BizProductCategoryService {

    @Override
    public Page<BizProductCategory> page(BizProductCategoryPageQuery req) {
        QueryWrapper<BizProductCategory> queryWrapper = new QueryWrapper<BizProductCategory>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(BizProductCategory::getName, req.getKeyword());
        }
        SortUtils.handleSort(BizProductCategory.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProductCategoryDto req) {
        BizProductCategory bean = BeanUtil.toBean(req, BizProductCategory.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProductCategoryDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizProductCategory>().eq(BizProductCategory::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizProductCategory bean = BeanUtil.toBean(req, BizProductCategory.class);
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
    public BizProductCategory detail(String id) {
        BizProductCategory bizProductCategory = this.getById(id);
        if (ObjectUtil.isEmpty(bizProductCategory)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizProductCategory;
    }

    @Override
    public List<BizProductCategory> latest(int n) {
        return this.list(new QueryWrapper<BizProductCategory>()
            .lambda()
            .orderByDesc(BizProductCategory::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizProductCategory> topN(int n) {
        return this.list(new QueryWrapper<BizProductCategory>()
            .lambda()
            .orderByDesc(BizProductCategory::getId)
            .last("limit " + n));
    }

    @Override
    public List<LabelOption<String>> lists() {
        List<BizProductCategory> list = this.list(new QueryWrapper<BizProductCategory>()
                .lambda()
                .orderByDesc(BizProductCategory::getId));
        // 进行map
        return list.stream().map(item -> new LabelOption<>(item.getId(), item.getName())).toList();
    }

}