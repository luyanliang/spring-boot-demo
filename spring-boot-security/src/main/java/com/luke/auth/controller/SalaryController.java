package com.luke.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @GetMapping("/query/auth")
    @PreAuthorize("hasAuthority('salary')")
    public String queryByAuth() {
        return "auth --> 查薪水";
    }

    @GetMapping("/query/role")
    @PreAuthorize("hasAuthority('salary')")
    public String queryByRole() {
        return "role --> 查薪水";
    }
}
