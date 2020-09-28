package com.luke.activiti.start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/9/27 13:23
 */
public class HelloWorld {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()  // 与流程定义和部署对象相关的Service
                .createDeployment()     // 创建一个部署对象
                .name("流程定义")   // 添加部署的名称
                .addClasspathResource("processes/categorize-text.bpmn")   // 从classPath的资源中加载，一次只能加载一个文件
                .deploy();

        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        // 流程定义的Key
        String processDefinitionKey = "categorizeProcess";

        ProcessInstance pi = processEngine.getRuntimeService()      // 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);   // 使用流程定义的Key启动流程实例，key对应categorize-text.bpmn文件中ID的属性值

        System.out.println("流程实例ID: " + pi.getId()); // 流程实例ID
        System.out.println("流程定义ID: " + pi.getProcessDefinitionId());    // 流程定义ID
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "";
        List<Task> list = processEngine.getTaskService()  // 与正在执行的任务管理相关的Service
                .createTaskQuery()  // 创建任务查询对象
                .taskAssignee(assignee) // 指定个人任务查询，指定办理人
                .list();

        if (!CollectionUtils.isEmpty(list)) {
            for (Task task : list) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务的创建时间：" + task.getCreateTime());
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务实例ID：" + task.getProcessInstanceId());
                System.out.println("任务对象ID：" + task.getExecutionId());
                System.out.println("任务定义ID：" + task.getProcessDefinitionId());
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        // 任务ID
        String taskId = "";
        processEngine.getTaskService()  // 与正在执行的任务管理相关的Service
                .complete(taskId);

        System.out.println("完成任务：任务ID：" + taskId);
    }
}
