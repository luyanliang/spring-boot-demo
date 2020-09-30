# 系统状态组件

此组件提供固定的请求，用于gateway检测应用状态，不同于actuator，本组件对每个request进行计数，
当收到下线通知后，发出下线事件，事件监听器接收到通知，会开始等待周期，周期结束后开始监控应用在
处理中的请求，当请求处理完后，执行下线回调。

## QuickStart

### 添加本组件依赖到pom中：

```xml
<dependency>
    <groupId>com.luke</groupId>
    <artifactId>common-sys</artifactId>
    <version>${project.version}</version>
</dependency>
```
### 此组件依赖线程池，进行如下配置

```text
    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setThreadNamePrefix("Async event task:");
        return executor;
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(){
        SimpleApplicationEventMulticaster multicaster=new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(taskExecutor());
        return multicaster;
    }
```

### 实现系统信息获取接口

> SystemInfoService

注意此接口只能实现一次。

此接口可以只实现customSystemInfo方法。

### 实现下线回调接口

> OfflineHookService

此接口中的方法会在系统下线时被触发。可实现多次，依次执行。

### 配置组件配置

application.yml
```yaml
sys:
  #访问密码
  switch-token: 123456
  #gateway检测间隔
  check-interval: 5000
  #gateway认为服务不可用检查次数阈值
  check-times: 1
  #监听器配置,uri支持ant表达式
  listener:
    #排除的uri,多个使用英文逗号分割，配置的uri即使还在处理，系统也会关闭。
    exclusion: "/hello"
    #包含的uri，处理完这些uri后系统才会下线，exclusion和include只能配置其中一个
    include: 
```
**exclusion和include只能配置其中一个**，建议只配置exclusion，排除不需要在下线时保证处理完的uri。

## 设计

流程：
1. 接收到下线通知。
2. 将系统运行状态转换到下线，检测连接总是返回500；发出下线通知。
3. 事件监听器监听到下线事件通知，开始休眠健康检测的周期。
4. 休眠结束后，检测请求是否处理完，处理完则触发下线回调接口。

## 请求

查看系统状态："/sys/status";
逻辑下线系统："/sys/off";
获取系统信息："/sys/info";

## 注意事项

当请求系统下线接口触发后，同步servlet请求可以保证执行完。异步的请求或开子线程处理的请求，以及使用spring自带的异步方法等无法保证执行完。
所以需要自行处理。可在异步线程中开始时向计数器加1，执行完成后在计数器减1，如果使用自定义线程池，可在OfflineHookService实现中进行线程池清理工作。
计数器获取如下：

```text
    @Autowired
    private SystemHealthConfig systemHealthConfig;

    .....
    //计数器+1
    systemHealthConfig.getRequestNums().addAndGet(1);
    //计数器-1
    systemHealthConfig.getRequestNums().decrementAndGet();
```