package com.luke.camunda.start;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngines;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author LuYanLiang
 * @since 2020/9/28 9:28
 */
public class ProcessEngineTest {

    /**
     * 使用代码创建工作流需要的23张表
     */
    @Test
    public void createTable() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        // 连接数据库配置
        configuration.setJdbcDriver("com.mysql.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://10.192.1.108:3306/camunda?characterEncoding=utf8");
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
        // 默认加载classpath路径下的camunda.cfg.xml文件
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Assert.assertNotNull(processEngine);
    }
}
