package com.luke.druid.service;

import com.luke.druid.entity.User;

import java.util.List;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/10/13 11:23
 */
public interface UserService {

    List<User> selectPortalUsers();

    List<User> selectTestUsers();
}
