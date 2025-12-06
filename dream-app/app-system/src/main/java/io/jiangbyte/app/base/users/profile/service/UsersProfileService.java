package io.jiangbyte.app.base.users.profile.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import io.jiangbyte.app.base.users.profile.dto.UsersProfileDto;
import io.jiangbyte.app.base.users.profile.dto.UsersProfilePageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户档案详情表 服务类
*/
public interface UsersProfileService extends IService<UsersProfile> {
    Page<UsersProfile> page(UsersProfilePageQuery req);

    void add(UsersProfileDto req);

    void edit(UsersProfileDto req);

    void delete(List<String> ids);

    UsersProfile detail(String id);

    List<UsersProfile> latest(int n);

    List<UsersProfile> topN(int n);
}