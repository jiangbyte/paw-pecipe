package io.jiangbyte.app.base.auths.group.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.group.entity.AuthsGroup;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupDto;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupPageQuery;
import io.jiangbyte.app.base.auths.group.mapper.AuthsGroupMapper;
import io.jiangbyte.app.base.auths.group.service.AuthsGroupService;
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
* @description 用户组表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsGroupServiceImpl extends ServiceImpl<AuthsGroupMapper, AuthsGroup> implements AuthsGroupService {

    @Override
    public Page<AuthsGroup> page(AuthsGroupPageQuery req) {
        QueryWrapper<AuthsGroup> queryWrapper = new QueryWrapper<AuthsGroup>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(AuthsGroup::getName, req.getKeyword());
        }
        SortUtils.handleSort(AuthsGroup.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsGroupDto req) {
        AuthsGroup bean = BeanUtil.toBean(req, AuthsGroup.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsGroupDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsGroup>().eq(AuthsGroup::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsGroup bean = BeanUtil.toBean(req, AuthsGroup.class);
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
    public AuthsGroup detail(String id) {
        AuthsGroup authsGroup = this.getById(id);
        if (ObjectUtil.isEmpty(authsGroup)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsGroup;
    }

    @Override
    public List<AuthsGroup> latest(int n) {
        return this.list(new QueryWrapper<AuthsGroup>()
            .lambda()
            .orderByDesc(AuthsGroup::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsGroup> topN(int n) {
        return this.list(new QueryWrapper<AuthsGroup>()
            .lambda()
            .orderByDesc(AuthsGroup::getId)
            .last("limit " + n));
    }

}