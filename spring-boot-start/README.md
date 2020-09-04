
# Introduction

本项目是`Spring Boot`的一个脚手架，旨在帮助开发者快速搭建项目结构。

## 项目启动

#### @SpringBootApplication

> @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan

* `@Configuration`标明该类使用Spring基于java配置
* `@ComponentScan`启动组建扫描
* `@EnableAutoConfiguration`开启SpringBoot自动配置能力

官方说明：

```
The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan with their default attributes.
```

## 测试框架

[官方测试文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)

#### jar说明

`Spring Boot`提供的测试框架包括两个模块：

* spring-boot-test: 包含核心功能
* spring-boot-test-autoconfigure: 提供自动配置化测试

开发者直接在maven中添加`spring-boot-starter-test`依赖，项目就会自动包含了`JUnit`, `AssertJ`, `Hamcrest`等libraries.


