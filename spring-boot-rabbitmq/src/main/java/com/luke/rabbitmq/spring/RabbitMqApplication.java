package com.luke.rabbitmq.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/20 8:00 PM
 */
@SpringBootApplication
public class RabbitMqApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RabbitMqApplication.class, args);
    }
}
