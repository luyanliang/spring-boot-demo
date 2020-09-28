package com.luke.activiti.start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/9/21 19:02
 */
public class ProcessEngineTest {

    /**
     * 使用代码创建工作流需要的23张表
     */
    @Test
    public void createTable() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        // 连接数据库配置
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://10.192.1.108:3306/test?characterEncoding=utf8");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("leapmotor&mydb.0314");

        /**
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE: 如果表不存在，则自动创建表
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE: 不能自动创建表，需要表存在
         * ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP: 先删除表再创建表
         */
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 创建工作流的核心对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        Assert.assertNotNull(processEngine);
    }

    @Test
    public void createProcessEngineByConfigFillTest() {
        // 默认加载classpath路径下的activity.cfg.xml文件
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        System.out.println(taskService);
    }
}
