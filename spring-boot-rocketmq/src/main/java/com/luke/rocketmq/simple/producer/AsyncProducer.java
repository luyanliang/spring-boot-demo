package com.luke.rocketmq.simple.producer;

import com.luke.rocketmq.utils.RocketMQConstant;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 3:26 PM
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("lukeTestProducer");
        // Specify name server addresses.
        producer.setNamesrvAddr(RocketMQConstant.NAME_SRV_ADDR);

        producer.start();

        //设置发送失败重试机制
        producer.setRetryTimesWhenSendAsyncFailed(5);

        int messageCount = 1;
        // 由于是异步发送，这里引入一个countDownLatch，保证所有Producer发送消息的回调方法都执行完了再停止Producer服务。
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            Message msg = new Message(RocketMQConstant.TOPIC,
                    "TagSendOne",
                    "OrderID188",
                    "I m sending msg content is yangguo".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //消息发送成功后，执行回调函数
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
