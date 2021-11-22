package com.luke.camunda.event;

import com.luke.camunda.listener.MyJavaDelegate;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/24 14:06
 */
public class EventTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RepositoryService repositoryService = processEngine.getRepositoryService();
    private RuntimeService runtimeService = processEngine.getRuntimeService();
    private TaskService taskService = processEngine.getTaskService();

    private static final String TIMER_EVENT_DEFINITION_DEMO = "processes/timerEventDefinitionDemo.bpmn";

    /**
     * 定时任务事件
     */
    @Test
    public void timerStartEventTest() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(TIMER_EVENT_DEFINITION_DEMO)
                .name("定时任务DEMO")
                .enableDuplicateFiltering(true)
                .deploy();

        // 查询流程定义
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        // 启动流程定义
        Map<String, Object> variables = new HashMap<>();
        variables.put("timeoutDelegate", new MyJavaDelegate());
        ProcessInstance pi = runtimeService.startProcessInstanceById(definition.getId(), variables);

        // 查询任务列表
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task : taskList) {
                System.out.println("---------------------------当前任务名称：" + task.getName());
            }
        }

        taskList = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task : taskList) {
                System.out.println("---------------------------当前任务名称：" + task.getName());
            }
        }
    }
}
