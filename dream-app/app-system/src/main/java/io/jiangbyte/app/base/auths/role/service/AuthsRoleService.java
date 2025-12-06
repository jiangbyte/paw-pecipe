package io.jiangbyte.app.base.auths.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.role.entity.AuthsRole;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRolePageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色表 服务类
*/
public interface AuthsRoleService extends IService<AuthsRole> {
    Page<AuthsRole> page(AuthsRolePageQuery req);

    void add(AuthsRoleDto req);

    void edit(AuthsRoleDto req);

    void delete(List<String> ids);

    AuthsRole detail(String id);

    List<AuthsRole> latest(int n);

    List<AuthsRole> topN(int n);
}