package com.luke.common.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Package com.luke.common.sys
 * ClassName: SystemShutdownListener
 * Description: 捕捉系统下线事件
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
@DependsOn("systemHealthConfig")
@Component
public class SystemOfflineListener {
    private static final Logger log = LoggerFactory.getLogger(SystemOfflineListener.class);
    /**
     * 获取列表
     */
    @Autowired
    private List<OfflineHookService> offlineHookServices;
    @Autowired
    private SystemHealthConfig systemHealthConfig;
    @Autowired
    private ApplicationContext context;

    @EventListener(SystemOfflineEvent.class)
    public void onOffline(SystemOfflineEvent event) {
        try {
            log.debug("pre shutdown wait gateway remove this node.");
            //沉睡等待gateway将本服务摘除
            Thread.sleep(systemHealthConfig.getCheckInterval() * systemHealthConfig.getCheckTimes());
            //开始自旋
            log.debug("wait process exists request.");
            while (true) {
                if (systemHealthConfig.getRequestNums().get() < 1) {
                    //统计请求已处理完，执行下线逻辑
                    log.debug("invoke shutdown hook.");
                    if (!CollectionUtils.isEmpty(offlineHookServices)) {
                        offlineHookServices.forEach(l -> {
                            try {
                                l.onSystemOffline(event.getParams());
                            } catch (Exception e) {
                                log.error("execute offlineHookService error!", e);
                            }
                        });
                    }
                    log.debug("shutdown hook complete.");
                    break;
                }
            }
            log.debug("server shutdown!");
            SpringApplication.exit(context, () -> 0);
            System.exit(0);
        } catch (InterruptedException e) {
            log.error("can't invoke shutdownHookService!", e);
        }
    }
}
