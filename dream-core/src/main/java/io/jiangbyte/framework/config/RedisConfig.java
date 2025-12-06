package io.jiangbyte.framework.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author charlie-zhang-code
 * @version v1.0
 * @date 2025/4/13
 * @description Redis配置
 */
@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 使用自定义配置的 Jackson 序列化器
        template.setValueSerializer(jackson2JsonRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 允许序列化 transient 字段
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, false);

        // 忽略未知属性（反序列化时）
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 忽略空bean转json错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 处理循环引用
        objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);

        // 日期格式处理
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());

        // 包含 null 值，确保所有字段都被序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
