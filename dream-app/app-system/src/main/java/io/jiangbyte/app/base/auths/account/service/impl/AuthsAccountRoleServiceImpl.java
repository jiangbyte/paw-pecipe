package io.jiangbyte.app.base.auths.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccountRole;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRoleDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRolePageQuery;
import io.jiangbyte.app.base.auths.account.mapper.AuthsAccountRoleMapper;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountRoleService;
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
* @description 账户角色关联表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsAccountRoleServiceImpl extends ServiceImpl<AuthsAccountRoleMapper, AuthsAccountRole> implements AuthsAccountRoleService {

    @Override
    public Page<AuthsAccountRole> page(AuthsAccountRolePageQuery req) {
        QueryWrapper<AuthsAccountRole> queryWrapper = new QueryWrapper<AuthsAccountRole>().checkSqlInjection();
        SortUtils.handleSort(AuthsAccountRole.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsAccountRoleDto req) {
        AuthsAccountRole bean = BeanUtil.toBean(req, AuthsAccountRole.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsAccountRoleDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsAccountRole>().eq(AuthsAccountRole::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsAccountRole bean = BeanUtil.toBean(req, AuthsAccountRole.class);
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
    public AuthsAccountRole detail(String id) {
        AuthsAccountRole authsAccountRole = this.getById(id);
        if (ObjectUtil.isEmpty(authsAccountRole)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsAccountRole;
    }

    @Override
    public List<AuthsAccountRole> latest(int n) {
        return this.list(new QueryWrapper<AuthsAccountRole>()
            .lambda()
            .orderByDesc(AuthsAccountRole::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsAccountRole> topN(int n) {
        return this.list(new QueryWrapper<AuthsAccountRole>()
            .lambda()
            .orderByDesc(AuthsAccountRole::getId)
            .last("limit " + n));
    }

}