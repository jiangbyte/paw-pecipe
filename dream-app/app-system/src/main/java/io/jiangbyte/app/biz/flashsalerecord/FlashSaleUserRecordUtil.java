package io.jiangbyte.app.biz.flashsalerecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 秒杀用户参与记录 Redis 工具类（防重复抢购）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlashSaleUserRecordUtil {

    private final StringRedisTemplate redisTemplate;

    /**
     * 用户参与 Key 前缀: flash:user:record:{flashId}:{userId}
     */
    private static final String USER_RECORD_KEY_PREFIX = "flash:user:record:";

    /**
     * 构建用户参与记录的 Redis Key
     */
    private String buildKey(String flashId, String userId) {
        return USER_RECORD_KEY_PREFIX + flashId + ":" + userId;
    }

    /**
     * 标记用户已参与秒杀（幂等）
     *
     * @param flashId 秒杀活动ID
     * @param userId  用户ID
     * @param expire  过期时间（如活动结束时间），可为 null（永不过期）
     * @return true 表示首次标记成功，false 表示已存在
     */
    public boolean markAsParticipated(String flashId, String userId, Duration expire) {
        String key = buildKey(flashId, userId);
        Boolean result;
        if (expire != null) {
            result = redisTemplate.opsForValue().setIfAbsent(key, "1", expire);
        } else {
            result = redisTemplate.opsForValue().setIfAbsent(key, "1");
        }
        return Boolean.TRUE.equals(result);
    }

    /**
     * 判断用户是否已参与该秒杀活动
     *
     * @param flashId 秒杀活动ID
     * @param userId  用户ID
     * @return true 表示已参与
     */
    public boolean hasParticipated(String flashId, String userId) {
        String key = buildKey(flashId, userId);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * （可选）手动清除用户参与记录（如用于测试或特殊业务）
     */
    public void removeRecord(String flashId, String userId) {
        String key = buildKey(flashId, userId);
        redisTemplate.delete(key);
    }
}