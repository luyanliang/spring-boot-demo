package com.luke.common.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.luke.common.sys.SystemSwitchController.*;

/**
 * Package com.luke.common.sys
 * ClassName: SystemServletListener
 * Description: 在请求到达时将系统处理请求数+1，请求出去后将系统处理请求数-1。支持ant表达式匹配排除/包括请求链接。
 * 只能配置一个，如果同时配置2个，只会使用排除。多个表达式使用","分割。
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
@DependsOn("systemHealthConfig")
@Component
public class SystemServletListener implements ServletRequestListener {
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    private final Logger log = LoggerFactory.getLogger(SystemServletListener.class);
    @Autowired
    private SystemHealthConfig systemHealthConfig;
    @Autowired
    private ServletContext servletContext;
    /**
     * 排除链接的ant表达式
     */
    @Value("${sys.listener.exclusion}")
    private String exclusion;
    /**
     * 要统计链接的ant表达式
     */
    @Value("${sys.listener.include:''}")
    private String include;

    /**
     * 表达式
     */
    private CopyOnWriteArrayList<String> patterns;
    /**
     * 使用包含为true，不包含为false
     */
    private boolean includeState;

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        String uri = ((HttpServletRequest) sre.getServletRequest()).getRequestURI();
        boolean match = this.match(uri);
        if (includeState && match) {
            int nums = systemHealthConfig.getRequestNums().decrementAndGet();
            log.info("current hold request nums:{}", nums);
        }
        if (!includeState && !match) {
            int nums = systemHealthConfig.getRequestNums().decrementAndGet();
            log.info("current hold request nums:{}", nums);
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        String uri = ((HttpServletRequest) sre.getServletRequest()).getRequestURI();
        boolean match = this.match(uri);
        if (includeState && match) {
            int nums = systemHealthConfig.getRequestNums().addAndGet(1);
            log.info("current hold request nums:{}", nums);
        }
        if (!includeState && !match) {
            int nums = systemHealthConfig.getRequestNums().addAndGet(1);
            log.info("current hold request nums:{}", nums);
        }
    }

    @PostConstruct
    public void init() {
        String contextPath = servletContext.getContextPath();
        if ("/".equals(contextPath)) {
            contextPath = "";
        }
        if (StringUtils.isEmpty(exclusion)) {
            //使用包含
            includeState = true;
            patterns = new CopyOnWriteArrayList<>(include.split(","));
        } else {
            //使用不包含
            includeState = false;
            patterns = new CopyOnWriteArrayList<>(exclusion.split(","));
            patterns.add(SYS_STATUS);
            patterns.add(SYS_INFO);
            patterns.add(SYS_OFF);
        }
        if (!CollectionUtils.isEmpty(patterns) && !StringUtils.isEmpty(contextPath)) {
            List<String> origin = patterns;
            final String context = contextPath;
            patterns = new CopyOnWriteArrayList<>();
            origin.parallelStream().forEach(path -> {
                patterns.add(context + path);
            });
        }
    }

    /**
     * 检查是否匹配
     *
     * @param uri 请求uri
     * @return 匹配true，不匹配false
     */
    private boolean match(String uri) {
        boolean match = false;
        for (String pattern : patterns) {
            if (ANT_PATH_MATCHER.match(pattern, uri)) {
                //一旦匹配就终止
                match = true;
                break;
            }
        }
        return match;
    }
}
