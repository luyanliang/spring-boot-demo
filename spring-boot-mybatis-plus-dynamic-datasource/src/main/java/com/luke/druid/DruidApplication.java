package com.luke.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/10/13 11:19
 */
@Slf4j
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)// ←←←←← look at here
@MapperScan("com.luke.druid.mapper")
public class DruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruidApplication.class, args);
        log.info("open http://localhost:8080/doc.html \n" +
                "http://localhost:8080/druid/index.html");
    }
}
