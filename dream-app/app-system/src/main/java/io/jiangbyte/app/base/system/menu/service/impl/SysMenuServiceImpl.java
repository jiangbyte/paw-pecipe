package io.jiangbyte.app.base.system.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.system.menu.entity.SysMenu;
import io.jiangbyte.app.base.system.menu.dto.SysMenuDto;
import io.jiangbyte.app.base.system.menu.dto.SysMenuPageQuery;
import io.jiangbyte.app.base.system.menu.mapper.SysMenuMapper;
import io.jiangbyte.app.base.system.menu.service.SysMenuService;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import io.jiangbyte.framework.utils.TreeBuilder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-06-23
* @description 菜单表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public Page<SysMenu> page(SysMenuPageQuery req) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(SysMenu::getName, req.getKeyword());
        }
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(SysMenu::getTitle, req.getKeyword());
        }
        SortUtils.handleSort(SysMenu.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysMenuDto req) {
        SysMenu bean = BeanUtil.toBean(req, SysMenu.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysMenuDto req) {
        if (!this.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        SysMenu bean = BeanUtil.toBean(req, SysMenu.class);
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
    public SysMenu detail(String id) {
        SysMenu sysMenu = this.getById(id);
        if (ObjectUtil.isEmpty(sysMenu)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return sysMenu;
    }

    @Override
    public List<SysMenu> latest(int n) {
        return this.list(new QueryWrapper<SysMenu>()
            .lambda()
            .orderByDesc(SysMenu::getId)
            .last("limit " + n));
    }

    @Override
    public List<SysMenu> topN(int n) {
        return this.list(new QueryWrapper<SysMenu>()
            .lambda()
            .orderByDesc(SysMenu::getId)
            .last("limit " + n));
    }


    @Override
    public List<SysMenu> getSysMenuListTreeWithAccountID(String accountId) {
        // 获取扁平菜单列表
        List<SysMenu> menus = getSysMenuListWithAccountID(accountId);

        // 使用TreeBuilder构建树形结构
        TreeBuilder<SysMenu> treeBuilder = new TreeBuilder<>(
                SysMenu::getId,
                menu -> menu.getPid() == null ? "" : menu.getPid(),
                SysMenu::setChildren
        );

        return treeBuilder.buildTree(menus);
    }

    @Override
    public List<SysMenu> getSysMenuListWithAccountID(String accountId) {
        return this.baseMapper.selectMenusByAccountId(accountId);
    }

}