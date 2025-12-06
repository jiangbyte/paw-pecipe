package io.jiangbyte.app.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.rabbitmq.client.Channel;
import io.jiangbyte.app.biz.flashsalerecord.FlashSaleStockUtil;
import io.jiangbyte.app.biz.flashsalerecord.dto.FlashSaleRecordStatus;
import io.jiangbyte.app.biz.flashsalerecord.entity.BizFlashSaleRecord;
import io.jiangbyte.app.biz.flashsalerecord.mapper.BizFlashSaleRecordMapper;
import io.jiangbyte.app.biz.flashsalerecord.service.BizFlashSaleRecordService;
import io.jiangbyte.app.biz.test.RecordMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlashSaleReleaseConsumer {
    private final FlashSaleStockUtil flashSaleStockUtil;
    private final BizFlashSaleRecordMapper bizFlashSaleRecordMapper;

    @RabbitListener(queues = FlashSaleRabbitConfig.DELAYED_QUEUE)
    public void handleReleaseMessage(RecordMessage mes, Message message, Channel channel) throws IOException {
        // 从 Spring Message 中获取 deliveryTag
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {

            if (mes == null) {
                log.warn("recordId 为空，丢弃消息");
                channel.basicAck(deliveryTag, false);
                return;
            }

            log.info("释放秒杀资格开始，秒杀资格ID：{} {} {}", mes.getRecordId(), mes.getUserId(), System.currentTimeMillis());

            // 2. 释放库存
            flashSaleStockUtil.increaseStock(mes.getFlashId(), mes.getQuantity());

            // 更新状态为已失效
            bizFlashSaleRecordMapper.update(new LambdaUpdateWrapper<BizFlashSaleRecord>()
                    .eq(BizFlashSaleRecord::getId, mes.getRecordId())
                    .set(BizFlashSaleRecord::getStatus, FlashSaleRecordStatus.EXPIRED)
                    .set(BizFlashSaleRecord::getExpiredAt, new Date())
            );

            // 手动 ACK
//            channel.basicAck(deliveryTag, false); // 手动 ACK
            // 安全 ACK：先判断 channel 是否还活着
            if (channel.isOpen()) {
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception e) {
            log.error("释放秒杀资格失败", e);
            // 拒绝消息，不重回队列（避免死循环）
//            channel.basicNack(deliveryTag, false, false); // 不重回队列

            // 安全 NACK
            if (channel.isOpen()) {
                channel.basicNack(deliveryTag, false, false);
            }
        }
    }
}