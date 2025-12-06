package io.jiangbyte.app.base.config.item.mapper;

import io.jiangbyte.app.base.config.item.entity.ConfigItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统配置表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface ConfigItemMapper extends BaseMapper<ConfigItem> {

}
