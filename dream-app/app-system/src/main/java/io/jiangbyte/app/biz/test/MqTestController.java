package io.jiangbyte.app.biz.test;

import com.alibaba.fastjson.JSON;
import io.jiangbyte.app.mq.FlashSaleRabbitConfig;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author CharlieZhang
 * @version v1.0
 * @date 05/12/2025
 * @description TODO
 */
@Tag(name = "测试控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class MqTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/mq/test")
    public void test() {
        // 2. 【关键】发送 5 分钟后过期的消息
        String message = JSON.toJSONString(Map.of("recordId", 2));

        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");
        props.setContentEncoding("UTF-8");

        // ✅ 正确方式：通过 headers 设置 x-delay（单位：毫秒）
        props.setHeader("x-delay", 1 * 10 * 1000); // 注意：是 header，不是方法！

        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8), props);

//        rabbitTemplate.send(
//                FlashSaleRabbitConfig.DELAYED_EXCHANGE,
//                FlashSaleRabbitConfig.DELAYED_ROUTING_KEY,
//                msg
//        );

        RecordMessage message1 = new RecordMessage();
        message1.setRecordId("1");
        message1.setUserId("1");
        rabbitTemplate.convertAndSend(
                FlashSaleRabbitConfig.DELAYED_EXCHANGE,
                FlashSaleRabbitConfig.DELAYED_ROUTING_KEY,
                message1, // 自动转为 JSON
                mes -> {
                    // 在 MessagePostProcessor 中设置 header
                    mes.getMessageProperties().setHeader("x-delay", 1 * 10 * 1000);
                    return mes;
                }
        );
        log.info("消息发送成功 {}", System.currentTimeMillis());
    }
}
