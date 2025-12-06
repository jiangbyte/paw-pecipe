package io.jiangbyte.app.biz.order.mapper;

import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import io.jiangbyte.app.biz.order.entity.BizOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单主表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizOrderMapper extends BaseMapper<BizOrder> {

}
