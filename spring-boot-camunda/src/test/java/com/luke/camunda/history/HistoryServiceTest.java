package com.luke.camunda.history;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/10 15:12
 */
public class HistoryServiceTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private HistoryService historyService = processEngine.getHistoryService();

    @Test
    public void queryInstanceFinishListTest() {
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().finished().list();
        if (!CollectionUtils.isEmpty(historicProcessInstances)) {
            for (HistoricProcessInstance instance : historicProcessInstances) {
                System.out.println(instance.getProcessDefinitionName());
            }
        }
    }

    @Test
    public void queryTaskFinishListTest() {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().finished().list();
        if (!CollectionUtils.isEmpty(historicTaskInstances)) {
            for (HistoricTaskInstance instance : historicTaskInstances) {
                System.out.println(instance.getName());
            }
        }
    }
}
