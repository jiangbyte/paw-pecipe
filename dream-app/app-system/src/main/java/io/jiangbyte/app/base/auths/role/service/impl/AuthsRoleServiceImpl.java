package io.jiangbyte.app.base.auths.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.role.entity.AuthsRole;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRolePageQuery;
import io.jiangbyte.app.base.auths.role.mapper.AuthsRoleMapper;
import io.jiangbyte.app.base.auths.role.service.AuthsRoleService;
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
* @description 角色表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsRoleServiceImpl extends ServiceImpl<AuthsRoleMapper, AuthsRole> implements AuthsRoleService {

    @Override
    public Page<AuthsRole> page(AuthsRolePageQuery req) {
        QueryWrapper<AuthsRole> queryWrapper = new QueryWrapper<AuthsRole>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(AuthsRole::getName, req.getKeyword());
        }
        SortUtils.handleSort(AuthsRole.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsRoleDto req) {
        AuthsRole bean = BeanUtil.toBean(req, AuthsRole.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsRoleDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsRole>().eq(AuthsRole::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsRole bean = BeanUtil.toBean(req, AuthsRole.class);
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
    public AuthsRole detail(String id) {
        AuthsRole authsRole = this.getById(id);
        if (ObjectUtil.isEmpty(authsRole)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsRole;
    }

    @Override
    public List<AuthsRole> latest(int n) {
        return this.list(new QueryWrapper<AuthsRole>()
            .lambda()
            .orderByDesc(AuthsRole::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsRole> topN(int n) {
        return this.list(new QueryWrapper<AuthsRole>()
            .lambda()
            .orderByDesc(AuthsRole::getId)
            .last("limit " + n));
    }

}