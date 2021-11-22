package com.luke.druid.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.luke.druid.entity.User;
import com.luke.druid.mapper.UserMapper;
import com.luke.druid.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/10/13 11:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectPortalUsers() {
        return userMapper.selectUsers();
    }

    @DS("mysql_test")
    @Override
    public List<User> selectTestUsers() {
        return userMapper.selectUsers();
    }
}
