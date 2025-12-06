package io.jiangbyte.app.biz.recipe.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import io.jiangbyte.app.biz.recipe.dto.BizRecipePageQuery;
import io.jiangbyte.app.biz.recipe.entity.BizRecipe;
import io.jiangbyte.app.biz.recipe.mapper.BizRecipeMapper;
import io.jiangbyte.app.biz.recipe.service.BizRecipeService;
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
* @description 菜谱表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizRecipeServiceImpl extends ServiceImpl<BizRecipeMapper, BizRecipe> implements BizRecipeService {

    @Override
    public Page<BizRecipe> page(BizRecipePageQuery req) {
        QueryWrapper<BizRecipe> queryWrapper = new QueryWrapper<BizRecipe>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(BizRecipe::getTitle, req.getKeyword());
        }
        if (ObjectUtil.isNotEmpty(req.getCategoryId()) && !"0".equals(req.getCategoryId())) {
            queryWrapper.lambda().eq(BizRecipe::getCategoryId, req.getCategoryId());
        }
        queryWrapper.lambda().eq(BizRecipe::getIsPublic, Boolean.TRUE);
        SortUtils.handleSort(BizRecipe.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizRecipeDto req) {
        BizRecipe bean = BeanUtil.toBean(req, BizRecipe.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRecipeDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizRecipe>().eq(BizRecipe::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizRecipe bean = BeanUtil.toBean(req, BizRecipe.class);
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
    public BizRecipe detail(String id) {
        BizRecipe bizRecipe = this.getById(id);
        if (ObjectUtil.isEmpty(bizRecipe)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizRecipe;
    }

    @Override
    public List<BizRecipe> latest(int n) {
        return this.list(new QueryWrapper<BizRecipe>()
            .lambda()
            .orderByDesc(BizRecipe::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizRecipe> topN(int n) {
        return this.list(new QueryWrapper<BizRecipe>()
            .lambda()
            .orderByDesc(BizRecipe::getId)
            .last("limit " + n));
    }

}