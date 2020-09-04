package com.fhmou.service;

import com.fhmou.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/9/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    @Cacheable(value="accountCache", key = "'5'")
    public User getById() {
        System.out.println("-------没有走缓存------");
        User user = new User();
        user.setId(5);
        user.setUserName("陈发");
        return user;
    }
}
