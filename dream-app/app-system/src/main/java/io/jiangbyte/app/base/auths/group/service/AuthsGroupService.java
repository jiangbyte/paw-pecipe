package io.jiangbyte.app.base.auths.group.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.auths.group.entity.AuthsGroup;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupDto;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户组表 服务类
*/
public interface AuthsGroupService extends IService<AuthsGroup> {
    Page<AuthsGroup> page(AuthsGroupPageQuery req);

    void add(AuthsGroupDto req);

    void edit(AuthsGroupDto req);

    void delete(List<String> ids);

    AuthsGroup detail(String id);

    List<AuthsGroup> latest(int n);

    List<AuthsGroup> topN(int n);
}