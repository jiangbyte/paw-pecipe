package io.jiangbyte.app.base.auths.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.role.entity.AuthsRoleMenu;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色菜单关联表 服务类
*/
public interface AuthsRoleMenuService extends IService<AuthsRoleMenu> {
    Page<AuthsRoleMenu> page(AuthsRoleMenuPageQuery req);

    void add(AuthsRoleMenuDto req);

    void edit(AuthsRoleMenuDto req);

    void delete(List<String> ids);

    AuthsRoleMenu detail(String id);

    List<AuthsRoleMenu> latest(int n);

    List<AuthsRoleMenu> topN(int n);
}