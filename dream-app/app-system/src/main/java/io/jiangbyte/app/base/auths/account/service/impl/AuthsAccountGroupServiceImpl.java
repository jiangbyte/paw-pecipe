package io.jiangbyte.app.base.auths.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccountGroup;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupPageQuery;
import io.jiangbyte.app.base.auths.account.mapper.AuthsAccountGroupMapper;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountGroupService;
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
* @description 账户用户组关联表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsAccountGroupServiceImpl extends ServiceImpl<AuthsAccountGroupMapper, AuthsAccountGroup> implements AuthsAccountGroupService {

    @Override
    public Page<AuthsAccountGroup> page(AuthsAccountGroupPageQuery req) {
        QueryWrapper<AuthsAccountGroup> queryWrapper = new QueryWrapper<AuthsAccountGroup>().checkSqlInjection();
        SortUtils.handleSort(AuthsAccountGroup.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsAccountGroupDto req) {
        AuthsAccountGroup bean = BeanUtil.toBean(req, AuthsAccountGroup.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsAccountGroupDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsAccountGroup>().eq(AuthsAccountGroup::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsAccountGroup bean = BeanUtil.toBean(req, AuthsAccountGroup.class);
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
    public AuthsAccountGroup detail(String id) {
        AuthsAccountGroup authsAccountGroup = this.getById(id);
        if (ObjectUtil.isEmpty(authsAccountGroup)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsAccountGroup;
    }

    @Override
    public List<AuthsAccountGroup> latest(int n) {
        return this.list(new QueryWrapper<AuthsAccountGroup>()
            .lambda()
            .orderByDesc(AuthsAccountGroup::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsAccountGroup> topN(int n) {
        return this.list(new QueryWrapper<AuthsAccountGroup>()
            .lambda()
            .orderByDesc(AuthsAccountGroup::getId)
            .last("limit " + n));
    }

}