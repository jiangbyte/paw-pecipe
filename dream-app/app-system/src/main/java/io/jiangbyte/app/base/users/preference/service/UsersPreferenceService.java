package io.jiangbyte.app.base.users.preference.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferenceDto;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferencePageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户偏好设置表 服务类
*/
public interface UsersPreferenceService extends IService<UsersPreference> {
    Page<UsersPreference> page(UsersPreferencePageQuery req);

    void add(UsersPreferenceDto req);

    void edit(UsersPreferenceDto req);

    void delete(List<String> ids);

    UsersPreference detail(String id);

    List<UsersPreference> latest(int n);

    List<UsersPreference> topN(int n);
}