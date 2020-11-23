package com.luke.camunda.start;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 演示工作流基本操作流程
 *
 * @author LuYanLiang
 * @since 2020/9/28 9:27
 */
public class HelloWorld {

    /**
     * 单向流动的工作流
     * <p>
     * start --> userTask --> end
     */
    @Test
    public void singleFlowTest() {
        // 1. 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 2. 流程存储服务器组件：管理流程定义文件
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/leaveBill.bpmn").deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        // 3. 运行时服务组件：启动流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(definition.getId());

        // 4. 流程任务组件：完成任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : taskList) {
            System.out.println("任务完成前，当前任务名称：" + task.getName());
            taskService.complete(task.getId());
        }

        // 5. 历史服务组件：查询历史任务
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().processDefinitionId(definition.getId()).list();
        for (HistoricTaskInstance instance : taskInstances) {
            System.out.println("历史任务名称：" + instance.getName());
        }
    }
}
