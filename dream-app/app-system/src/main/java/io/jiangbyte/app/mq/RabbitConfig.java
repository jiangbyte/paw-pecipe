package io.jiangbyte.app.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author charlie-zhang-code
 * @version v1.0
 * @date 2025/4/13
 * @description Rabbit 配置
 */
@Slf4j
@Configuration
public class RabbitConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setUsePublisherConnection(true);

        // 添加确认回调，确保消息可靠投递
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.warn("Message failed to reach broker: {}", cause);
            }
        });

        return rabbitTemplate;
    }


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(5); // 初始消费者数量
        factory.setMaxConcurrentConsumers(20); // 最大消费者数量
        factory.setPrefetchCount(10); // 控制预取数量
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置手动确认
        // 设置重试机制
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3) // 最大重试次数
                .backOffOptions(1000, 2.0, 5000) // 初始间隔1秒，倍数2.0，最大间隔10秒
                .recoverer(new RejectAndDontRequeueRecoverer()) // 超过重试次数后不重新入队
                .build());

        return factory;
    }

}
