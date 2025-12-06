package io.jiangbyte.app.base.auths.role.mapper;

import io.jiangbyte.app.base.auths.role.entity.AuthsRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色菜单关联表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface AuthsRoleMenuMapper extends BaseMapper<AuthsRoleMenu> {

}
