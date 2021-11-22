package com.luke.camunda.authorization;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.authorization.Authorization;
import org.junit.Test;

/**
 * 授权管理
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/10 19:57
 */
public class AuthorizationTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private AuthorizationService authorizationService = processEngine.getAuthorizationService();

    @Test
    public void addAuthorizationTest() {
        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        authorizationService.saveAuthorization(authorization);
    }
}
