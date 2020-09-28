###  Deployment(部署对象)

1. 一次可部署多个文件的信息。对于不需要的流程可以删除和修改。
2. 对应的表：
	1. act_re_deployment: 部署对象表
	2. act_re_procdef: 流程定义表
	3. act_ge_bytearray: 资源文件表
	4. act_ge_property: 主键生成策略表

### ProcessDefinition(流程定义)

解析.bpmn后得到的流程定义规则的信息，工作流程系统就是按照定义的规则执行的。
