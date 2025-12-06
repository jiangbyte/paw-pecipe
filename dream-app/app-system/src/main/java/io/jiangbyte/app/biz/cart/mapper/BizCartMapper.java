package io.jiangbyte.app.biz.cart.mapper;

import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import io.jiangbyte.app.biz.cart.entity.BizCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 购物车表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizCartMapper extends BaseMapper<BizCart> {

}
