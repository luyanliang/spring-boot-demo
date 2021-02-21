package com.luke.rocketmq.simple.consumer;

import com.luke.rocketmq.utils.RocketMQConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主动拉取消息，一般不常用这种方法
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 5:28 PM
 */
public class PullConsumer {

    private static final Map<MessageQueue, Long> offsetTable = new HashMap<>();

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("lukeTestConsumer");
        consumer.setNamesrvAddr(RocketMQConstant.NAME_SRV_ADDR);
        consumer.start();

        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(RocketMQConstant.TOPIC);
        for (MessageQueue mq : mqs) {
            System.err.println("Consume from the queue: " + mq);
            SINGLE_MQ:
            while (true) {
                try {
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.println(pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());

                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                            for (MessageExt m : messageExtList) {
                                System.out.println(new String(m.getBody()));
                            }
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        consumer.shutdown();
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetTable.put(mq, offset);
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetTable.get(mq);
        if (offset != null) {
            return offset;
        }
        return 0;
    }
}
