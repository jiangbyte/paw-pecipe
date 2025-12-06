package io.jiangbyte.app.base.users.preference.mapper;

import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户偏好设置表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface UsersPreferenceMapper extends BaseMapper<UsersPreference> {

}
