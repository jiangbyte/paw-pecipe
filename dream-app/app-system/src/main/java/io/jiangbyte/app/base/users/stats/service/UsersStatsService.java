package io.jiangbyte.app.base.users.stats.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsDto;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户统计信息表 服务类
*/
public interface UsersStatsService extends IService<UsersStats> {
    Page<UsersStats> page(UsersStatsPageQuery req);

    void add(UsersStatsDto req);

    void edit(UsersStatsDto req);

    void delete(List<String> ids);

    UsersStats detail(String id);

    List<UsersStats> latest(int n);

    List<UsersStats> topN(int n);
}