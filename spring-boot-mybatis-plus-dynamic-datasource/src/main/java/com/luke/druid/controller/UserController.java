package com.luke.druid.controller;

import com.luke.druid.entity.User;
import com.luke.druid.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LuYanLiang [lu_yanliang@leapmotor.com]
 * @since 2020/10/13 11:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("portal")
    public List<User> portalUsers() {
        return userService.selectPortalUsers();
    }

    @GetMapping("test")
    public List<User> testUsers() {
        return userService.selectTestUsers();
    }
}
