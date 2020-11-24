package com.luke.camunda.task;

import com.luke.camunda.listener.MyJavaDelegate;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
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
 * 不同种类的任务测试类
 *
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/11/11 13:31
 */
public class TaskServiceTypeTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RepositoryService repositoryService = processEngine.getRepositoryService();
    private RuntimeService runtimeService = processEngine.getRuntimeService();
    private TaskService taskService = processEngine.getTaskService();
    private HistoryService historyService = processEngine.getHistoryService();

    private static String SERVICE_TASK_PROCESS_FILE = "processes/serviceTask.bpmn";

    /**
     * 服务任务组件：用于调用Web Service或者自动执行程序。
     * <p>
     * 服务任务不会添加到ACT_RU_TASK表中，而是添加到ACT_RU_JOB表中。
     */
    @Test
    public void serviceTaskTest() {
        // 部署流程定义文件
        ProcessDefinition pd = this.deployProcessFile(SERVICE_TASK_PROCESS_FILE);

        // 启动流程
        Map<String, Object> vars = new HashMap<>();
        vars.put("serviceTaskDelegate", new MyJavaDelegate());
        ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), vars);

        // 完成所有任务
        this.completeAllTask(pi.getId(), null);

        // 查询历史
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(pi.getId())
                .finished()
                .list();
        if (!CollectionUtils.isEmpty(historicTaskInstances)) {
            for (HistoricTaskInstance instance : historicTaskInstances) {
                System.out.println("历史任务:" + instance.getName());
            }
        }
    }

    @Test
    public void deleteDeployment() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            // 默认不进行级联删除。如果已经存在流程实例数据，则会删除失败
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    public void completeAllTask(String processInstanceId, List<Task> taskList) {
        if (CollectionUtils.isEmpty(taskList)) {
            taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        }
        for (Task task : taskList) {
            System.out.println("任务名称：" + task.getName());
            taskService.complete(task.getId());
        }
        taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (!CollectionUtils.isEmpty(taskList)) {
            this.completeAllTask(processInstanceId, taskList);
        }
    }

    public ProcessDefinition deployProcessFile(String processFile) {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(processFile)
                .enableDuplicateFiltering(true)
                .deploy();
        return repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
    }

    public ProcessInstance startProcess(String definitionId) {
        return runtimeService.startProcessInstanceById(definitionId);
    }

}
