package com.luke.rocketmq.utils;

/**
 * 消息队列常量
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2021/2/21 5:16 PM
 */
public class RocketMQConstant {

    /**
     * Name Server 地址，集群部署时用"分号"分隔
     */
    public static final String NAME_SRV_ADDR = "127.0.0.1:9876";

    /**
     * 主题名称 主题一般是服务器设置好 而不能在代码里去新建topic
     */
    public static final String TOPIC = "TOPIC-TEST";
}
