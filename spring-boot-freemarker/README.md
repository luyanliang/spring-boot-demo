### 快速搭建

#### 导入jar包

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-freemarker</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### 系统自动配置

当在项目依赖中加入freemarker配置后，SpringBoot会自动初始化项目的配置。

默认的ftl文件存放位置是 `classpath: /templates/`, 文件后缀是 `.ftl`



