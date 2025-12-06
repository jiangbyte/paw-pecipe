package io.jiangbyte.app.base.users.info.mapper;

import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户基本信息表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UsersInfoMapper extends BaseMapper<UsersInfo> {

}
