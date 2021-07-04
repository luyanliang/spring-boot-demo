package com.luke.auth.service;

import com.luke.auth.bean.RoleBean;
import com.luke.auth.bean.UserBean;
import com.luke.auth.dao.TestDataDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自行注入一个UserDetailsService
 * 如果没有的话，在UserDetailsServiceAutoConfiguration中会默认注入一个包含user用户的InMemoryUserDetailsManager
 * 另外也可以采用修改configure(AuthenticationManagerBuilder auth)方法并注入authenticationManagerBean的方式。
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private TestDataDao testDataDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean user = testDataDao.queryUser(username);
        List<RoleBean> userRoles = user.getUserRoles();

        List<String> roleNames = userRoles.stream().map(RoleBean::getRoleName).collect(Collectors.toList());
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String roleName : roleNames) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            authorityList.add(authority);
        }

        return new User(username, passwordEncoder.encode(user.getUserPass()), authorityList);
    }
}
