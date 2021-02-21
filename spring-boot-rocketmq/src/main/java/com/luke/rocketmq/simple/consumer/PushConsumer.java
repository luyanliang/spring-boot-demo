package com.luke.rocketmq.simple.consumer;

import com.luke.rocketmq.utils.RocketMQConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * 主动推送消息给消费者
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 5:14 PM
 */
public class PushConsumer {

    public static void main(String[] args) throws MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lukeTestConsumer");

        // Specify name server addresses.
        consumer.setNamesrvAddr(RocketMQConstant.NAME_SRV_ADDR);

        // Subscribe one more more topics to consume.
        consumer.subscribe(RocketMQConstant.TOPIC, "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("PushConsumer Started.%n");
    }
}
