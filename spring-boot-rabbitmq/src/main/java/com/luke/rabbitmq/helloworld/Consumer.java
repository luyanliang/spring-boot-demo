package com.luke.rabbitmq.helloworld;

import com.luke.rabbitmq.utils.RabbitConstant;
import com.luke.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消息消费者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/14 11:22 PM
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        // 获取TCP长连接
        Connection conn = RabbitUtils.getConnection();
        // 创建通信"通道"，相当于TCP中的虚拟连接
        Channel channel = conn.createChannel();
        /**
         * 创建队列，声明并创建一个队列，如果队列已存在，则使用这个队列
         * 第一个参数：队列名称
         * 第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
         * 第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能访问
         * 第四个：是否自动删除，false代表连接停掉后不自动删除这个队列
         * 其他额外参数，null
         */
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);

        // 从MQ服务器中获取数据

        // 如果不写basicQos(1)，则自动MQ会将所有请求平均分发给所有消费者
        // basicQos，MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的
        channel.basicQos(1);

        /**
         * 创建一个消息消费者
         * 第一个参数：队列名
         * 第二个参数：是否自动确认收到消息，false代表手动编程来确认消息，这是MQ的推荐做法
         * 第三个参数：要传入DefaultConsumer的实现类
         */
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("消费者接收到的消息：" + message);
                System.out.println("消息的TagID: " + envelope.getDeliveryTag());

                // false只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
