package com.luke.auth.service;

import com.luke.auth.bean.UserBean;
import com.luke.auth.dao.TestDataDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {

    private final String demoUserName = "admin";
    private final String demoUserPass = "admin";

    @Resource
    private TestDataDao testDataDao;

    public UserBean userLogin(UserBean loginUser) {
        return testDataDao.queryUser(loginUser);
    }
}
