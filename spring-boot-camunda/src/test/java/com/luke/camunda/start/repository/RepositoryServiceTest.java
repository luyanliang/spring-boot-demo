package com.luke.camunda.start.repository;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 流程存储服务组件测试类
 * <p>
 * 1. 流程文件管理（增删查）
 * 2. 流程定义管理（增删查）
 * 3. 中止/启用流程定义
 *
 * @author LuYanLiang
 * @since 2020/9/27 10:51
 */
public class RepositoryServiceTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RepositoryService repositoryService = processEngine.getRepositoryService();

    private static String SERVICE_TASK_PROCESS_FILE = "processes/serviceTask.bpmn";
    private static String LEAVE_BILL_PROCESS_FILE = "processes/leaveBill.bpmn";

    /**
     * 部署流程定义文件
     * <p>
     * 目的：把流程描述文件写入到数据库中。
     * <p>
     * 受影响的表：
     * * ACT_RE_DEPLOYMENT：存储部署时间等信息
     * * ACT_GE_BYTEARRAY：存储流程文件
     * * ACT_RE_PROCDEF：存储流程定义文件中的流程描述信息
     */
    @Test
    public void deploymentProcessDefinition_classPath() {
        // 创建一个部署对象
        DeploymentBuilder builder = repositoryService.createDeployment()
                .addClasspathResource(LEAVE_BILL_PROCESS_FILE)
                .name("请假流程部署名称")
                .enableDuplicateFiltering(true);    // 流程文件已经部署过一次，下次重复部署时，返回已部署的文件

        // 部署流程文件
        Deployment deployment = builder.deploy();

        System.out.printf("[DeploymentId：%s, \tDeploymentName：%s, \tDeploymentTime: %s]",
                deployment.getId(), deployment.getName(), deployment.getDeploymentTime());
    }

    /**
     * 查询部署流程
     */
    @Test
    public void queryDeploymentList() {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> deployments = deploymentQuery.list();
        for (Deployment deployment : deployments) {
            System.out.printf("[DeploymentId：%s, \tDeploymentName：%s]\n" + deployment.getId(), deployment.getName());
        }
    }

    /**
     * 删除流程部署
     * <p>
     * 不级联删除：只删除流程部署文件。包括身份数据、流程定义数据、流程资源与部署数据。
     * 级联删除：删除流程部署文件和流程实例数据。流程实例数据包含：流程任务、流程历史数据。
     */
    @Test
    public void deleteDeployment() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            // 默认不进行级联删除。如果已经存在流程实例数据，则会删除失败
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinitionTest() {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.processDefinitionNameLike("请假流程");
        List<ProcessDefinition> processDefinitions = query.list();
        for (ProcessDefinition definition : processDefinitions) {
            System.out.printf("ID：%s, \tName：%s, \t是否中止: %s\n",
                    definition.getId(), definition.getName(), definition.isSuspended());
        }
    }

    /**
     * 激活流程定义
     */
    @Test
    public void activateProcessDefinitionTest() {
        repositoryService.activateProcessDefinitionByKey("LeaveBill");

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("LeaveBill").suspended().list();
        Assert.assertTrue(processDefinitions.isEmpty());
    }

    /**
     * 中止流程定义
     * 中止流程定以后，流程不允许启动。
     */
    @Test
    public void suspendProcessDefinitionTest() {
        repositoryService.suspendProcessDefinitionByKey("LeaveBill");

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("LeaveBill").active().list();
        Assert.assertTrue(processDefinitions.isEmpty());
    }

    /**
     * 删除流程定义
     * 不级联删除：只删除流程定义；
     * 级联删除：删除流程定义和流程历史数据。
     */
    @Test
    public void deleteProcessDefinitionTest() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("LeaveBill").active().list();
        for (ProcessDefinition definition : processDefinitions) {
            repositoryService.deleteProcessDefinition(definition.getId(), true);
        }
    }

    /**
     * 设置流程定义的权限
     */
    @Test
    public void setUserAuthTest() {
        // 得到身份服务组件
        IdentityService identityService = processEngine.getIdentityService();
        createUser(identityService, "user1", "long", "yang", "yanglong@qq.com", "123");
        createUser(identityService, "user2", "yuebo", "sun", "sunyuebo@qq.com", "test");
        createUser(identityService, "user3", "lei", "xu", "xulei@qq.com", "12345");

        // 查询流程定义实体
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("LeaveBill").list();

        for (ProcessDefinition definition : processDefinitions) {
            repositoryService.addCandidateStarterUser(definition.getId(), "user1");
        }

        List<ProcessDefinition> authDefinitionList = repositoryService.createProcessDefinitionQuery().startableByUser("user1").list();
        Assert.assertFalse(authDefinitionList.isEmpty());
    }

    private static void createUser(IdentityService identityService, String id, String first, String last,
                                   String email, String pwd) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        if (user != null) {
            return;
        }
        // 使用newUser方法创建User实例
        user = identityService.newUser(id);
        // 设置用户的各个属性
        user.setEmail(email);
        user.setFirstName(first);
        user.setLastName(last);
        user.setPassword(pwd);

        // 使用saveUser方法保存用户
        identityService.saveUser(user);
    }
}
