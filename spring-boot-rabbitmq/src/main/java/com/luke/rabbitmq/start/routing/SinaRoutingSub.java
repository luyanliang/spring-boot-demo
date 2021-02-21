package com.luke.rabbitmq.start.routing;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 新浪订阅者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/19 9:18 PM
 */
public class SinaRoutingSub {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        // 获取虚拟连接
        final Channel channel = connection.createChannel();

        // 声明队列信息
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);

        // queueBind用于将队列与交换机绑定
        // 参数1：队列名 参数2：交换机名  参数3：路由key
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "henan.zhengzhou");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "henan.anyang");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "zhejiang.hangzhou");

        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SINA, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("新浪天气收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
