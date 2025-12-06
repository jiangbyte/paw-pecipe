package io.jiangbyte.app.base.auths.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.role.entity.AuthsRoleMenu;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuPageQuery;
import io.jiangbyte.app.base.auths.role.mapper.AuthsRoleMenuMapper;
import io.jiangbyte.app.base.auths.role.service.AuthsRoleMenuService;
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
* @description 角色菜单关联表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsRoleMenuServiceImpl extends ServiceImpl<AuthsRoleMenuMapper, AuthsRoleMenu> implements AuthsRoleMenuService {

    @Override
    public Page<AuthsRoleMenu> page(AuthsRoleMenuPageQuery req) {
        QueryWrapper<AuthsRoleMenu> queryWrapper = new QueryWrapper<AuthsRoleMenu>().checkSqlInjection();
        SortUtils.handleSort(AuthsRoleMenu.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsRoleMenuDto req) {
        AuthsRoleMenu bean = BeanUtil.toBean(req, AuthsRoleMenu.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsRoleMenuDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsRoleMenu>().eq(AuthsRoleMenu::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsRoleMenu bean = BeanUtil.toBean(req, AuthsRoleMenu.class);
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
    public AuthsRoleMenu detail(String id) {
        AuthsRoleMenu authsRoleMenu = this.getById(id);
        if (ObjectUtil.isEmpty(authsRoleMenu)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsRoleMenu;
    }

    @Override
    public List<AuthsRoleMenu> latest(int n) {
        return this.list(new QueryWrapper<AuthsRoleMenu>()
            .lambda()
            .orderByDesc(AuthsRoleMenu::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsRoleMenu> topN(int n) {
        return this.list(new QueryWrapper<AuthsRoleMenu>()
            .lambda()
            .orderByDesc(AuthsRoleMenu::getId)
            .last("limit " + n));
    }

}