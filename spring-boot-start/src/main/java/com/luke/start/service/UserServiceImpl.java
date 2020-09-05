package com.luke.start.service;

import org.springframework.stereotype.Service;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/8/15
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getUserName() {
        return "陶辉";
    }
}
