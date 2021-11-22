package com.luke.common.controller;

import com.luke.common.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/9/30 11:06
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AsyncService asyncService;

    /**
     * 同步请求
     */
    @GetMapping("/sync1")
    public String sync1() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("request url is sync1. request time is " + System.currentTimeMillis());
        return "sync1";
    }

    /**
     * 同步请求
     */
    @GetMapping("/sync2")
    public String sync2() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("request url is sync2. request time is " + System.currentTimeMillis());
        return "sync2";
    }

    /**
     * 忽略计数请求
     */
    @GetMapping("/hello")
    public String hello() {
        logger.info("request url is hello. request time is " + System.currentTimeMillis());
        return "hello";
    }

    /**
     * 异步请求
     */
    @GetMapping("/async1")
    public String async1() {
        asyncService.asyncTest("async1");
        logger.info("request url is async1. request time is " + System.currentTimeMillis());
        return "sync2";
    }
}
