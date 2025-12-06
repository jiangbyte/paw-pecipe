package io.jiangbyte.app.base.auths.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccountGroup;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户用户组关联表 服务类
*/
public interface AuthsAccountGroupService extends IService<AuthsAccountGroup> {
    Page<AuthsAccountGroup> page(AuthsAccountGroupPageQuery req);

    void add(AuthsAccountGroupDto req);

    void edit(AuthsAccountGroupDto req);

    void delete(List<String> ids);

    AuthsAccountGroup detail(String id);

    List<AuthsAccountGroup> latest(int n);

    List<AuthsAccountGroup> topN(int n);
}