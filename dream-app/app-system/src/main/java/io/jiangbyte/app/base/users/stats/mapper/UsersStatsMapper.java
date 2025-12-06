package io.jiangbyte.app.base.users.stats.mapper;

import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户统计信息表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UsersStatsMapper extends BaseMapper<UsersStats> {

}
