package com.luke.rabbitmq.confirm;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

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
public class WeatherBureauConfirmPub {

    public static void main(String[] args) throws IOException {
        Map<String, String> area = new HashMap<>();
        area.put("henan.zhengzhou", "河南郑州天气晴");
        area.put("henan.anyang.linzhou", "河南安阳市林州市天气晴");
        area.put("henan.xinyang", "河南信阳天气阴");
        area.put("zhejiang.hangzhou", "浙江杭州天气小雨");
        area.put("zhejiang.jinhua", "浙江金华天气小雨");
        area.put("hebei.shijiazhuang", "河北省石家庄市天气小雨");

        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        // 开启confirm监听模式
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                // 第二个参数代表接收的数据是否为批量接收，一般用不到
                System.out.println("消息已被Broker接收，Tag：" + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息被Broker拒收，Tag：" + deliveryTag);
            }
        });
        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return returnMessage) {
                System.err.println("============================");
                System.err.println("Return编码：" + returnMessage.getReplyCode() + "-Return描述:" + returnMessage.getReplyText());
                System.err.println("交换机：" + returnMessage.getExchange() + "-路由key:" + returnMessage.getRoutingKey());
                System.err.println("Return主题：" + new String(returnMessage.getBody()));
                System.err.println("============================");
            }
        });

        for (Map.Entry<String, String> entry : area.entrySet()) {
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC, entry.getKey(), null, entry.getValue().getBytes());
        }

        // 关闭后无法完成监听
//        channel.close();
//        connection.close();
    }
}
