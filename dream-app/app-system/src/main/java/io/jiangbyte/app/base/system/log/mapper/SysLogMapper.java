package io.jiangbyte.app.base.system.log.mapper;

import io.jiangbyte.app.base.system.log.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统活动日志记录表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface SysLogMapper extends BaseMapper<SysLog> {

}
