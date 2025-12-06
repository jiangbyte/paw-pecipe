package io.jiangbyte.app.biz.recipe.mapper;

import io.jiangbyte.app.biz.recipe.entity.BizRecipe;
import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizRecipeMapper extends BaseMapper<BizRecipe> {

}
