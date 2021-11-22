package com.luke.camunda.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.Serializable;

/**
 * 自定义任务委派类
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/11 13:28
 */
public class MyJavaDelegate implements JavaDelegate, Serializable {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("This is java delegate!");
    }
}
