package com.luke.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @GetMapping("/query/auth")
    @PreAuthorize("hasAuthority('mobile')")
    public String query() {
        return "mobile";
    }

    @GetMapping("/query/role")
    @PreAuthorize("hasAnyRole('adminRole', 'managerRole')")
    public String queryByRole() {
        return "role --> mobile";
    }
}
