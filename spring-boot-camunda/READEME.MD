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

