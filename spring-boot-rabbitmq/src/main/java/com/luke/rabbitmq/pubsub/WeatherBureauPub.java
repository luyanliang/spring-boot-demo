package com.luke.rabbitmq.pubsub;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 天气预报发布者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/19 9:13 PM
 */
public class WeatherBureauPub {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        String input = new Scanner(System.in).next();
        Channel channel = connection.createChannel();

        /**
         * 第一个参数交换机名字
         */
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER, "", null, input.getBytes());

        channel.close();
        connection.close();
    }
}
