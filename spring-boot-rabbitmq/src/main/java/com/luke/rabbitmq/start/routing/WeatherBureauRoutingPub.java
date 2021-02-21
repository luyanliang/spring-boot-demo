package com.luke.rabbitmq.start.routing;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 天气预报发布者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/19 9:13 PM
 */
public class WeatherBureauRoutingPub {

    public static void main(String[] args) throws IOException, TimeoutException {
        Map<String, String> area = new HashMap<>();
        area.put("henan.zhengzhou", "河南郑州天气晴");
        area.put("henan.anyang", "河南安阳天气晴");
        area.put("henan.xinyang", "河南信阳天气阴");
        area.put("zhejiang.hangzhou", "浙江杭州天气小雨");
        area.put("zhejiang.jinhua", "浙江金华天气小雨");

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        /**
         * 第一个参数交换机名字
         */
        for (Map.Entry<String, String> entry : area.entrySet()) {
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_ROUTING, entry.getKey(), null, entry.getValue().getBytes());
        }

        channel.close();
        connection.close();
    }
}
