package io.jiangbyte.app.base.auths.group.mapper;

import io.jiangbyte.app.base.auths.group.entity.AuthsGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户组表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface AuthsGroupMapper extends BaseMapper<AuthsGroup> {

}
