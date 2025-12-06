package io.jiangbyte.app.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FlashSaleRabbitConfig {

    public static final String DELAYED_EXCHANGE = "flash.sale.delayed.exchange";
    public static final String DELAYED_QUEUE = "flash.sale.delayed.queue";
    public static final String DELAYED_ROUTING_KEY = "flash.sale.release";

    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct"); // 底层使用 direct exchange
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding bindingDelayedQueue(CustomExchange delayedExchange, Queue delayedQueue) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}