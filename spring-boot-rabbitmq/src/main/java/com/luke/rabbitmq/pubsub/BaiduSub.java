package com.luke.rabbitmq.pubsub;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 百度订阅者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/19 9:18 PM
 */
public class BaiduSub {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        // 获取虚拟连接
        final Channel channel = connection.createChannel();
        // 声明队列信息
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);

        // queueBind用于将队列与交换机绑定
        // 参数1：队列名 参数2：交换机名  参数3：路由key（暂时用不到）
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER, "");
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_BAIDU, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度天气收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
