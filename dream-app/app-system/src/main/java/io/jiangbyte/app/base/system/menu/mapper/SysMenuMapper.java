package io.jiangbyte.app.base.system.menu.mapper;

import io.jiangbyte.app.base.system.menu.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 菜单表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> selectMenusByAccountId(String accountId);
}
