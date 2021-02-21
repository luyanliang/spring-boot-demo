package com.luke.rabbitmq.spring.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/20 8:28 PM
 */
@Component
public class RabbitMQListener {

    /**
     * 定义方法进行信息的监听  RabbitListener中的参数用于表示监听的是哪一个队列
     */
    @RabbitListener(queues = "boot_queue")
    public void listenerQueue(Message message) {
        System.out.println("message: " + new String(message.getBody()));
    }
}
