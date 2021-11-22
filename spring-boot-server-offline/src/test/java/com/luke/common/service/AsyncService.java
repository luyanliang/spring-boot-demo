package com.luke.common.service;

import com.luke.common.sys.SystemHealthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/9/30 11:01
 */
@Service
public class AsyncService {
    private Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private SystemHealthConfig systemHealthConfig;

    @Async
    public void asyncTest(String name) {
        systemHealthConfig.getRequestNums().addAndGet(1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("async service running....." + name);
        systemHealthConfig.getRequestNums().decrementAndGet();
    }
}
