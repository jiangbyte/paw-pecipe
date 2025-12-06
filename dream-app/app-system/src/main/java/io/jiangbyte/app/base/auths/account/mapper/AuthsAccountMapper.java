package io.jiangbyte.app.base.auths.account.mapper;

import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 核心账户表 Mapper 接口
*/
@Mapper
//@CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
public interface AuthsAccountMapper extends BaseMapper<AuthsAccount> {

}
