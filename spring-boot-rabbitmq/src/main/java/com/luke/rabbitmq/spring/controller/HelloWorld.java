package com.luke.rabbitmq.spring.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.luke.rabbitmq.spring.config.RabbitMqConfig.EXCHANGE_NAME;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/20 8:19 PM
 */
@RestController
public class HelloWorld {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping({"", "/", "/index"})
    public String home() {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "boot.helloWorld", "发送消息。。。。");
        return "success";
    }
}
