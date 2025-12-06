package io.jiangbyte.app.biz.flashsalerecord.mapper;

import io.jiangbyte.framework.cache.MybatisPlusRedisCache;
import io.jiangbyte.app.biz.flashsalerecord.entity.BizFlashSaleRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 秒杀资格记录表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface BizFlashSaleRecordMapper extends BaseMapper<BizFlashSaleRecord> {

}
