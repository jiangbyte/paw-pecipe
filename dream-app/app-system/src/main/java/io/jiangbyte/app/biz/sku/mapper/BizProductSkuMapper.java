package io.jiangbyte.app.biz.sku.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuPageQuery;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuWithProductDto;
import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import io.jiangbyte.app.biz.sku.entity.BizProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 商品日常销售SKU表 Mapper 接口
 */
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizProductSkuMapper extends BaseMapper<BizProductSku> {
    Page<BizProductSkuWithProductDto> selectSkuWithProductPage(IPage<BizProductSkuWithProductDto> page, @Param("req") BizProductSkuPageQuery req);
}
