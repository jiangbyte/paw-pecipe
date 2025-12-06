package io.jiangbyte.app.biz.recipecategory.mapper;

import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱分类表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizRecipeCategoryMapper extends BaseMapper<BizRecipeCategory> {

}
