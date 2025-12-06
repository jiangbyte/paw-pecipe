package io.jiangbyte.app.base.system.menu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.system.menu.entity.SysMenu;
import io.jiangbyte.app.base.system.menu.dto.SysMenuDto;
import io.jiangbyte.app.base.system.menu.dto.SysMenuPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 菜单表 服务类
*/
public interface SysMenuService extends IService<SysMenu> {
    Page<SysMenu> page(SysMenuPageQuery req);

    void add(SysMenuDto req);

    void edit(SysMenuDto req);

    void delete(List<String> ids);

    SysMenu detail(String id);

    List<SysMenu> latest(int n);

    List<SysMenu> topN(int n);

    /**
     * 根据账户ID获取菜单列表（树形结构）
     */
    List<SysMenu> getSysMenuListTreeWithAccountID(String accountId);

    /**
     * 根据账户ID获取菜单列表（扁平结构）
     */
    List<SysMenu> getSysMenuListWithAccountID(String accountId);
}