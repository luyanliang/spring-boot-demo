package com.luke.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Unit test for simple App.
 */
@RestController
@SpringBootApplication(scanBasePackages = "com.luke.common")
public class AppTest {

    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }
}
