package io.jiangbyte.app.base.users.info.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import io.jiangbyte.app.base.users.info.dto.UsersInfoDto;
import io.jiangbyte.app.base.users.info.dto.UsersInfoPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户基本信息表 服务类
*/
public interface UsersInfoService extends IService<UsersInfo> {
    Page<UsersInfo> page(UsersInfoPageQuery req);

    void add(UsersInfoDto req);

    void edit(UsersInfoDto req);

    void delete(List<String> ids);

    UsersInfo detail(String id);

    List<UsersInfo> latest(int n);

    List<UsersInfo> topN(int n);
}