官网地址：http://www.camunda.com/

## 工作流介绍

工作流（Work Flow）引擎将原本散乱甚至混乱的业务梳理后制定成业务规范流程，进而约束业务的规范化处理和运转。
工作流总是以任务的形式驱动人处理业务或者驱动业务系统自动完成作业。

> BPM(Business Process Management)：业务流程管理，是一套达成企业各种业务环节整合的全面管理模式。
> BPMN(Business Process Modeling Notation): 业务流程建模标注，定义了业务流程图规范。

### 解决了什么问题

未使用工作流前：

* 流转步骤在代码中写死；
    * 流程不够直观；
    * 后期维护成本困难；
* 记录流转日志代码冗余；

使用工作流后：

* 流程自动化；
* 专注于流程任务开发，不需要再关心流程的流转和日志记录；

### 生命周期

1. 绘制流程定义文件；
2. 部署流程定义文件；
3. 启动流程定义；
4. 完成流程任务；
5. 查询历史记录。

## BPMN规范

### 开始与结束事件

`开始事件`都是`捕获型`的，等待第三方触发后才可以启动。主要分三种类型：空启动事件、定时启动事件和异常启动事件。
`结束事件`都是`抛出型`的，当流程执行结束事件时，会抛出一个执行结果。主要分三种类型：空结束事件、异常结束事件和取消结束事件。

### 监听器

监听器是在BPMN 2.0规范基础上扩展的功能。
监听器分为两类：`执行监听器`和`任务监听器`。

监听器执行类型：

* class: 需要实现接口：ExecutionListener或TaskListener。需要在流程文件中写死类路径，不推荐;
* expression: 定义一个表示式，需要指定调用的方法。把监听器类封装成参数，表达式中指定类中的方法；
* delegateExpression: 也是把监听器类封装成参数传递，但是该类需要实现`JavaDelegate`接口并重写execute方法，表达式中不需要指定方法。

#### 执行监听器

执行流监听器允许在执行流程中执行Java代码或表达式，使用`executionListener`定义，通过event属性指定监听事件的类型（start, end, take）。
可以监听的事件有：

* 流程实例启动、结束；
* 输出流捕获；
* 活动启动、结束；
* 路由开始、结束；
* 中间事件开始、结束；
* 触发开始事件、触发结束事件。

#### 任务监听器

只能用于用户任务。用来监听3种事件：

* create: 在任务被创建且所有的任务属性设置完成之后才触发；
* assignment: 在任务被分配给某个办理人之后触发；
* complete: 任务完成后被触发。

## 用户与组管理

候选人/组：不确定将任务分配给哪个人或组办理；

## Process Engine

![Process Engine API](https://docs.camunda.org/manual/7.14/user-guide/process-engine/img/api.services.png)


```sql
-- 流程部署

SELECT * FROM ACT_GE_BYTEARRAY;     -- 流程文件
SELECT * FROM ACT_RE_DEPLOYMENT;    -- 流程部署
SELECT * FROM ACT_RE_PROCDEF;		-- 流程定义

-- 用户或用户组

SELECT * FROM ACT_ID_GROUP;
SELECT * FROM ACT_ID_USER;
SELECT * FROM ACT_ID_INFO;
SELECT * FROM ACT_ID_MEMBERSHIP;
```




## NOTE

* mysql-connector必须使用5.1.x版本，不能使用8.x版本，否则无法正常创建表；

