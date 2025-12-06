package io.jiangbyte.app.base.auths.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccountRole;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRoleDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRolePageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户角色关联表 服务类
*/
public interface AuthsAccountRoleService extends IService<AuthsAccountRole> {
    Page<AuthsAccountRole> page(AuthsAccountRolePageQuery req);

    void add(AuthsAccountRoleDto req);

    void edit(AuthsAccountRoleDto req);

    void delete(List<String> ids);

    AuthsAccountRole detail(String id);

    List<AuthsAccountRole> latest(int n);

    List<AuthsAccountRole> topN(int n);
}