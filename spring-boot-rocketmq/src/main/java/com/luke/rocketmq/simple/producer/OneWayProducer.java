package com.luke.rocketmq.simple.producer;

import com.luke.rocketmq.utils.RocketMQConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向发送消息
 * 比较简单，发出去后，什么都不管直接返回。
 * <p>
 * 官网DEMO：http://rocketmq.apache.org/docs/simple-example/
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 3:11 PM
 */
public class OneWayProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("lukeTestProducer");
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketMQConstant.NAME_SRV_ADDR);

        producer.setSendMsgTimeout(10000);

        producer.start();
        for (int i = 0; i < 1; i++) {
            Message msg = new Message(RocketMQConstant.TOPIC,
                    "oneWayTag",
                    "onwWayKey",
                    ("send one way message. i " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.sendOneway(msg);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
