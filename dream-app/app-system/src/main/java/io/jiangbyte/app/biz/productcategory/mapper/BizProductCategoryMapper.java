package io.jiangbyte.app.biz.productcategory.mapper;

import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import io.jiangbyte.app.biz.productcategory.entity.BizProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品分类表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizProductCategoryMapper extends BaseMapper<BizProductCategory> {

}
