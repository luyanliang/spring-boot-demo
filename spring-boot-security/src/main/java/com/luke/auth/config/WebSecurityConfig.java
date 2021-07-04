package com.luke.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置安全拦截策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf跨域检查
        http.csrf().disable()
                .authorizeRequests()
                // 配置资源权限
                .antMatchers("/main.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 默认跳转到 Referer 来源页面，如果 Referer 为空，没有来源页，则跳转到默认设置的页面
                // defaultSuccessUrl 另外一个重载方法，第二个参数如果输入为 true，则效果和 successForwardUrl 一致。
                .defaultSuccessUrl("/main.html")
                // successForwardUrl 表示不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。
                // 请求的路径必须是Post类型
                .successForwardUrl("/main")
                // 设定登录失败页面
                .failureUrl("/loginFailed");
    }
}
