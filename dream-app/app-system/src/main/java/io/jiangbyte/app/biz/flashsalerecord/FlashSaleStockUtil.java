package io.jiangbyte.app.biz.flashsalerecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 秒杀库存 Redis 工具类（线程安全、高并发友好）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FlashSaleStockUtil {

    private final StringRedisTemplate redisTemplate;

    /**
     * 库存 Key 前缀
     */
    private static final String STOCK_KEY_PREFIX = "flash:stock:";

    /**
     * 构建 Redis Key
     */
    private String buildKey(String flashId) {
        return STOCK_KEY_PREFIX + flashId;
    }

    /**
     * 初始化库存（幂等：如果已存在则不覆盖）
     *
     * @param flashId 秒杀活动ID
     * @param stock   初始库存（必须 >= 0）
     */
    public void initStock(String flashId, int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("库存不能为负数");
        }
        String key = buildKey(flashId);
        // setIfAbsent 保证只初始化一次
        redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(stock));
    }

    /**
     * 获取当前可用库存
     *
     * @param flashId 秒杀活动ID
     * @return 库存数量，若未初始化或为空则返回 0
     */
    public int getStock(String flashId) {
        String key = buildKey(flashId);
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("Redis 库存值格式异常, key: {}, value: {}", key, value);
            return 0;
        }
    }

    /**
     * 原子扣减库存（核心方法！防止超卖）
     *
     * @param flashId  秒杀活动ID
     * @param quantity 扣减数量（必须 > 0）
     * @return true 表示扣减成功，false 表示库存不足
     */
    public boolean tryDecreaseStock(String flashId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("扣减数量必须大于0");
        }
        String key = buildKey(flashId);

        // Lua 脚本：先判断库存是否足够，再 DECRBY
        String script = """
                    local current = redis.call('GET', KEYS[1])
                    if not current or tonumber(current) < tonumber(ARGV[1]) then
                        return 0
                    end
                    redis.call('DECRBY', KEYS[1], ARGV[1])
                    return 1
                """;

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), String.valueOf(quantity));

        return result != null && result == 1L;
    }

    /**
     * 增加库存（用于订单取消、超时释放等场景）
     *
     * @param flashId  秒杀活动ID
     * @param quantity 增加数量（必须 > 0）
     */
    public void increaseStock(String flashId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("增加数量必须大于0");
        }
        String key = buildKey(flashId);
        redisTemplate.opsForValue().increment(key, quantity);
    }
}