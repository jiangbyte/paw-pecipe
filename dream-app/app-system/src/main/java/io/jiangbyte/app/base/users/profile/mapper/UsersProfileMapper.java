package io.jiangbyte.app.base.users.profile.mapper;

import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户档案详情表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UsersProfileMapper extends BaseMapper<UsersProfile> {

}
