package io.jiangbyte.app.base.auths.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.account.dto.UserAccount;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 核心账户表 服务类
*/
public interface AuthsAccountService extends IService<AuthsAccount> {
    Page<AuthsAccount> page(AuthsAccountPageQuery req);

    void add(AuthsAccountDto req);

    void edit(AuthsAccountDto req);

    void delete(List<String> ids);

    AuthsAccount detail(String id);

    List<AuthsAccount> latest(int n);

    List<AuthsAccount> topN(int n);

    UserAccount userAccount(String id);
}