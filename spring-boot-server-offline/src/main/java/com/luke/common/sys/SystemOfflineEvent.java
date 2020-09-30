package com.luke.common.sys;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Package com.luke.common.sys
 * ClassName: SystemOfflineEvent
 * Description: 系统下线事件
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
public class SystemOfflineEvent extends ApplicationEvent {
    private static final long serialVersionUID = -2L;

    private Map<String, String> params;

    public SystemOfflineEvent(Object source, Map<String, String> params) {
        super(source);
        this.params = params;
    }

    public SystemOfflineEvent(Object source) {
        super(source);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
