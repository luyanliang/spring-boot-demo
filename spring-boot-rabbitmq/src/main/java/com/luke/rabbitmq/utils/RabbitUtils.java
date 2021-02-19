package com.luke.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/14 11:43 PM
 */
public class RabbitUtils {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static {
        // 设置主机信息，默认端口号：5672
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("helloworld");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
