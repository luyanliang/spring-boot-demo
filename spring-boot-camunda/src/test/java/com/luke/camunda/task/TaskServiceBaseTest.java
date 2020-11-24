package com.luke.camunda.task;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基础流程任务管理测试类
 * <p>
 * 1. 任务查询；
 * 2. 设置任务参数；
 * 3. 任务指派；
 * 4. 任务操作：任务评论、任务附件等；
 * 5. 完成任务.
 *
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/11/9 16:14
 */
public class TaskServiceBaseTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private TaskService taskService = processEngine.getTaskService();

    @Test
    public void saveTest() {
        Task task = taskService.newTask("testCreate");
        task.setName("taskName");
        task.setOwner("11687");
        taskService.saveTask(task);
    }

    /**
     * 任务指派。
     * setAssignee和claim作用一样
     */
    @Test
    public void setAssigneeTest() {
        Task task = taskService.createTaskQuery().taskName("taskName").singleResult();
        taskService.setAssignee(task.getId(), "xulei");

        // 任务申明claim和setAssignee作用一样。区别是：claim申明时会校验assignee是否为空或是否和新值一样，否则报错
        taskService.claim(task.getId(), "yuebo");
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTest() {
        List<Task> taskList = taskService.createTaskQuery().list();
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task : taskList) {
                taskService.complete(task.getId());
            }
        }
    }

    @Test
    public void selectTest() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.taskAssignee("xulei").list();
        for (Task task : taskList) {
            System.out.printf("Id: %s, \tName: %s \n", task.getId(), task.getName());
        }
    }

    /**
     * 设置任务参数
     */
    @Test
    public void setVariableTest() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.processDefinitionKey("LeaveBill").list();
        for (Task task : taskList) {
            taskService.setVariable(task.getId(), "param1", "test");
        }
    }

    /**
     * 查询任务参数列表
     */
    @Test
    public void getVariableTest() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.processDefinitionKey("LeaveBill").list();
        for (Task task : taskList) {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            Set<String> keys = variables.keySet();
            for (String key : keys) {
                Object value = variables.get(key);
                System.out.println("value: " + value);
            }
        }
    }

    /**
     * 添加任务的附件
     * <p>
     * insert into ACT_HI_ATTACHMENT (ID_, NAME_, DESCRIPTION_, TYPE_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, URL_, CONTENT_ID_, TENANT_ID_, CREATE_TIME_, REMOVAL_TIME_, REV_)
     * insert into ACT_GE_BYTEARRAY(ID_, NAME_, BYTES_, DEPLOYMENT_ID_, TENANT_ID_, TYPE_, CREATE_TIME_, ROOT_PROC_INST_ID_, REMOVAL_TIME_, REV_)
     */
    @Test
    public void createAttachmentTest() throws IOException {
        Task task = taskService.createTaskQuery().processDefinitionKey("LeaveBill").list().get(0);
        Attachment attachment = taskService.createAttachment("type", task.getId(), null, "附件名称", "附件描述", "https://img-blog.csdnimg.cn/20190121210001128.png");
        taskService.saveAttachment(attachment);

        Resource resource = new ClassPathResource("camunda-flow.jpg");
        InputStream is = resource.getInputStream();
        Attachment attachmentInput = taskService.createAttachment("inputAttachment", task.getId(), null, "附件流", "存放附件的文件流", is);
        taskService.saveAttachment(attachmentInput);
    }

    @Test
    public void setUserAuthTest() {
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.processDefinitionKey("LeaveBill").list();
        for (Task task : taskList) {
            System.out.printf("Id: %s, \tName: %s \n", task.getId(), task.getName());
        }
    }
}
