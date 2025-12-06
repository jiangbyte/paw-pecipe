package io.jiangbyte.app.biz.recipecategory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryDto;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryPageQuery;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.app.biz.recipecategory.mapper.BizRecipeCategoryMapper;
import io.jiangbyte.app.biz.recipecategory.service.BizRecipeCategoryService;
import io.jiangbyte.framework.option.LabelOption;
import io.jiangbyte.framework.utils.SortUtils;
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
 * @description 菜谱分类表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BizRecipeCategoryServiceImpl extends ServiceImpl<BizRecipeCategoryMapper, BizRecipeCategory> implements BizRecipeCategoryService {

    @Override
    public Page<BizRecipeCategory> page(BizRecipeCategoryPageQuery req) {
        QueryWrapper<BizRecipeCategory> queryWrapper = new QueryWrapper<BizRecipeCategory>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(BizRecipeCategory::getName, req.getKeyword());
        }
        SortUtils.handleSort(BizRecipeCategory.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizRecipeCategoryDto req) {
        BizRecipeCategory bean = BeanUtil.toBean(req, BizRecipeCategory.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRecipeCategoryDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizRecipeCategory>().eq(BizRecipeCategory::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizRecipeCategory bean = BeanUtil.toBean(req, BizRecipeCategory.class);
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
    public BizRecipeCategory detail(String id) {
        BizRecipeCategory bizCategory = this.getById(id);
        if (ObjectUtil.isEmpty(bizCategory)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizCategory;
    }

    @Override
    public List<BizRecipeCategory> latest(int n) {
        return this.list(new QueryWrapper<BizRecipeCategory>()
                .lambda()
                .orderByDesc(BizRecipeCategory::getId)
                .last("limit " + n));
    }

    @Override
    public List<BizRecipeCategory> topN(int n) {
        return this.list(new QueryWrapper<BizRecipeCategory>()
                .lambda()
                .orderByDesc(BizRecipeCategory::getId)
                .last("limit " + n));
    }

    @Override
    public List<LabelOption<String>> lists() {
        List<BizRecipeCategory> list = this.list(new QueryWrapper<BizRecipeCategory>()
                .lambda()
                .orderByDesc(BizRecipeCategory::getId));
        // 进行map
        return list.stream().map(item -> new LabelOption<>(item.getId(), item.getName())).toList();
    }

}