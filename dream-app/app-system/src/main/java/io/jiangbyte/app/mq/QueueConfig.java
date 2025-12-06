package io.jiangbyte.app.mq;

import lombok.Data;

@Data
public class QueueConfig {
    /**
     * 交换机名称
     */
    private String exchange;

    /**
     * 队列名称
     */
    private String queue;

    /**
     * 路由键
     */
    private String routingKey;
}