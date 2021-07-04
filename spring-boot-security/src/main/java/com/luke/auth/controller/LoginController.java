package com.luke.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main() {
        logger.info("user login succeed");
        return "main";
    }

    @GetMapping("loginFailed")
    public String loginError() {
        logger.info("user login failed");
        return "failed";
    }
}
