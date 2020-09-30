package com.luke.common.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Package com.luke.common.sys
 * ClassName: SystemInfo
 * Description: 系统信息
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
@Component("systemHealthConfig")
public class SystemHealthConfig {
    /**
     * 系统是否在线检查间隔
     */
    @Value("${sys.check-interval:10000}")
    private long checkInterval;
    /**
     * 系统是否在线检查次数，如果连续检查checkTimes次返回非200，300的相应值，判定系统已经下线
     */
    @Value("${sys.check-times:2}")
    private int checkTimes;
    /**
     * 当前系统中的请求数
     */
    private AtomicInteger requestNums = new AtomicInteger();

    /**
     * 系统是否还能提供业务服务
     */
    private AtomicBoolean switcher = new AtomicBoolean(true);

    public AtomicInteger getRequestNums() {
        return requestNums;
    }

    public void setRequestNums(AtomicInteger requestNums) {
        this.requestNums = requestNums;
    }

    public AtomicBoolean getSwitcher() {
        return switcher;
    }

    public void setSwitcher(AtomicBoolean switcher) {
        this.switcher = switcher;
    }

    public long getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

    public int getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(int checkTimes) {
        this.checkTimes = checkTimes;
    }
}
