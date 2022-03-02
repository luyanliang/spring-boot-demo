package com.luke.start.web;

import com.luke.start.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/8/15
 */
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping({"", "/", "/index"})
    public String home() {
        return userService.getUserName();
    }

    @RequestMapping("/shutdown")
    public String shutdown() throws InterruptedException {
        Thread.sleep(20 * 1000);
        return "OK";
    }
}
