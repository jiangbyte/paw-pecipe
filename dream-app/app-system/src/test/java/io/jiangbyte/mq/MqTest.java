package io.jiangbyte.mq;

import com.alibaba.fastjson.JSON;
import io.jiangbyte.app.App;
import io.jiangbyte.app.biz.flashsalerecord.service.BizFlashSaleRecordService;
import io.jiangbyte.app.mq.FlashSaleRabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author CharlieZhang
 * @version v1.0
 * @date 05/12/2025
 * @description TODO
 */
@SpringBootTest(classes = App.class)
public class MqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test() {
        // 2. 【关键】发送 5 分钟后过期的消息
        String message = JSON.toJSONString(Map.of("recordId", 2));

        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");
        props.setContentEncoding("UTF-8");

        // ✅ 正确方式：通过 headers 设置 x-delay（单位：毫秒）
        props.setHeader("x-delay", 5 * 60 * 1000); // 注意：是 header，不是方法！

        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), props);

        rabbitTemplate.send(
                FlashSaleRabbitConfig.DELAYED_EXCHANGE,
                FlashSaleRabbitConfig.DELAYED_ROUTING_KEY,
                msg
        );
    }
}
