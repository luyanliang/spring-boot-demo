package com.luke.camunda.controller;

import com.luke.camunda.listener.MyJavaDelegate;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时器
 * https://docs.camunda.org/manual/latest/reference/bpmn20/events/timer-events/
 *
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/11/24 16:16
 */
@RestController
@RequestMapping("/timer/event")
public class TimerEventController {

    private static final String TIMER_EVENT_DEFINITION_DEMO = "bpmn/timerEventDefinitionDemo.bpmn";

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @GetMapping("start")
    public String startEvent() {
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
        variables.put("duration", "PT1M");
        ProcessInstance pi = runtimeService.startProcessInstanceById(definition.getId(), variables);
        return pi.getId();
    }

    @GetMapping("task/list")
    public List<Task> taskList(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }
}
