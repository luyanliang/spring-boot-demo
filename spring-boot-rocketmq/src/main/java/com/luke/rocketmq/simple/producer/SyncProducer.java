package com.luke.rocketmq.simple.producer;

import com.luke.rocketmq.utils.RocketMQConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 同步发送消息
 *
 * http://rocketmq.apache.org/docs/simple-example/
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 3:22 PM
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("lukeTestProducer");
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketMQConstant.NAME_SRV_ADDR);

        producer.start();

        Message msg = new Message(RocketMQConstant.TOPIC,
                "syncTag",
                "syncKey",
                ("send sync message...").getBytes(RemotingHelper.DEFAULT_CHARSET)
        );

        //Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
