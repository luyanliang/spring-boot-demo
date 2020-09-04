package com.fhmou.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * functional describe:
 *
 * @author luyanliang [765673481@qq.com]
 * @version 1.0 2017/9/26
 */
@Lazy(false)
@Component
public class SpringUtil implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtil.class);

    /** ApplicationContext */
    private static ApplicationContext applicationContext;

    /**
     * 不可实例化
     */
    private SpringUtil() {
    }

    /**
     * 设置ApplicationContext
     *
     * @param applicationContext
     *            ApplicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        LOGGER.info("ApplicationContext registed-->{}", applicationContext);
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /***
     * 根据一个bean的id获取配置文件中相应的bean
     */
    public static Object getBean(String beanId) throws BeansException {
        if (applicationContext.containsBean(beanId)) {
            return applicationContext.getBean(beanId);
        }
        return null;
    }

    /**
     * 获取实例
     *
     * @param type
     *            Bean类型
     * @return 实例
     */
    public static <T> T getBean(Class<T> type) {
        Assert.notNull(type, "类类型不能为空");
        return applicationContext.getBean(type);
    }

    /**
     * 获取实例
     *
     * @param name
     *            Bean名称
     * @param type
     *            Bean类型
     * @return 实例
     */
    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name, "类名称不能为空");
        Assert.notNull(type, "类类型不能为空");
        return applicationContext.getBean(name, type);
    }

    /***
     * 根据一个bean的类型获取配置文件中相应的bean
     */
    public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

}
