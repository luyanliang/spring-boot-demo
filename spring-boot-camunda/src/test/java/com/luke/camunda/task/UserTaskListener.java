package com.luke.camunda.task;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * 使用任务监听器进行权限分配
 * <p>
 * 一般在应用中，用户组和用户均有可能发生变化，将用户和用户组写死在流程文件中显然不合适，
 * 因此可以使用任务监听器进行动态权限分配。
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/11 15:13
 */
public class UserTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("使用任务监听器设置任务权限");
        delegateTask.setAssignee("1");
        delegateTask.addCandidateGroup("1");
    }
}
