package com.luke.activiti.start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/9/27 10:51
 */
public class ProcessDefinitionTest {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义（从classPath）
     * <p>
     * 带有Key的流程定义第一次部署时，version为1。之后每次部署都会在当前最高版本号上加1.
     * 重复部署一次，deploymentId的值就会重新生成一次。生成规则有act_ge_property表生成。
     */
    @Test
    public void deploymentProcessDefinition_classPath() {
        Deployment deployment = processEngine.getRepositoryService()  // 与流程定义和部署对象相关的Service
                .createDeployment()     // 创建一个部署对象
                .name("流程定义")   // 添加部署的名称
                .addClasspathResource("processes/categorize-text.bpmn")   // 从classPath的资源中加载，一次只能加载一个文件
                .deploy();  // 完成部署

        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     * 部署流程定义（从zip）
     */
    @Test
    public void deploymentProcessDefinition_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/categorize-text.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);

        Deployment deployment = processEngine.getRepositoryService()  // 与流程定义和部署对象相关的Service
                .createDeployment()     // 创建一个部署对象
                .name("流程定义")   // 添加部署的名称
                .addZipInputStream(zipInputStream)   // 从classPath的资源中加载，一次只能加载一个文件
                .deploy();  // 完成部署

        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition() {
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
//        query.deploymentId(deploymentId);   // 使用部署对象ID查询
//        query.processDefinitionId(processDefinitionId);     // 使用流程定义ID查询
//        query.processDefinitionKey(processDefinitionKey);   // 使用流程定义的Key查询

        // 排序
        query.orderByProcessDefinitionName().desc();

        // 查询返回结果集
        List<ProcessDefinition> list = query.list();    // 返回一个集合列表，封装流程定义
        if (!CollectionUtils.isEmpty(list)) {
            for (ProcessDefinition definition : list) {
                System.out.println("流程定义ID：" + definition.getId());     // 流程定义的Key+版本+随机生成数
                System.out.println("流程定义的名称：" + definition.getName());
                System.out.println("流程定义的Key：" + definition.getKey());
                System.out.println("流程定义的版本：" + definition.getVersion());
                System.out.println("资源名称BPMN文件：" + definition.getResourceName());
                System.out.println("资源名称PNG文件：" + definition.getDiagramResourceName());
                System.out.println("部署对象ID：" + definition.getDeploymentId());
            }
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition() {
        String deploymentId = "";

        RepositoryService repositoryService = processEngine.getRepositoryService();

        /**
         * 不带级联的删除
         * 只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
        repositoryService.deleteDeployment(deploymentId);

        /**
         * 级联删除
         * 不管流程是否启动，都能删除
         */
//        repositoryService.deleteDeployment(deploymentId, true);

        System.out.println("删除成功！");
    }
}
