package io.jiangbyte.app.base.auths.account.mapper;

import io.jiangbyte.app.base.auths.account.entity.AuthsAccountRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户角色关联表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface AuthsAccountRoleMapper extends BaseMapper<AuthsAccountRole> {

}
