package io.jiangbyte.app.biz.specs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.specs.entity.BizProductSpecs;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsDto;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsPageQuery;
import io.jiangbyte.app.biz.specs.mapper.BizProductSpecsMapper;
import io.jiangbyte.app.biz.specs.service.BizProductSpecsService;
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
* @description 规格表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizProductSpecsServiceImpl extends ServiceImpl<BizProductSpecsMapper, BizProductSpecs> implements BizProductSpecsService {

    @Override
    public Page<BizProductSpecs> page(BizProductSpecsPageQuery req) {
        QueryWrapper<BizProductSpecs> queryWrapper = new QueryWrapper<BizProductSpecs>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(BizProductSpecs::getName, req.getKeyword());
        }
        SortUtils.handleSort(BizProductSpecs.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizProductSpecsDto req) {
        BizProductSpecs bean = BeanUtil.toBean(req, BizProductSpecs.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizProductSpecsDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizProductSpecs>().eq(BizProductSpecs::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizProductSpecs bean = BeanUtil.toBean(req, BizProductSpecs.class);
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
    public BizProductSpecs detail(String id) {
        BizProductSpecs bizProductSpecs = this.getById(id);
        if (ObjectUtil.isEmpty(bizProductSpecs)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizProductSpecs;
    }

    @Override
    public List<BizProductSpecs> latest(int n) {
        return this.list(new QueryWrapper<BizProductSpecs>()
            .lambda()
            .orderByDesc(BizProductSpecs::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizProductSpecs> topN(int n) {
        return this.list(new QueryWrapper<BizProductSpecs>()
            .lambda()
            .orderByDesc(BizProductSpecs::getId)
            .last("limit " + n));
    }

}