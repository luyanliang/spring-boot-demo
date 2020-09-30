package com.luke.common.sys;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Package com.luke.common.sys
 * ClassName: SystemInfoService
 * Description: 获取系统信息
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
public interface SystemInfoService {
    /**
     * 加载定制化系统，不包含所在服务器环境变量，cpu数量，jvm当前内存信息
     *
     * @return map
     */
    Map<String, Object> customSystemInfo();

    /**
     * 加载定制化系统信息后，将物理主机环境变量、可用cpu数，jvm内存信息添加到信息表中
     *
     * @return 信息表
     */
    default Map<String, Object> loadSystemInfo() {
        Map<String, Object> map = this.customSystemInfo();
        map = CollectionUtils.isEmpty(map) ? new HashMap<>() : map;
        map.put("system_env", System.getenv());
        map.put("CPUs", Runtime.getRuntime().availableProcessors());
        map.put("jvm_total_memory", Runtime.getRuntime().totalMemory());
        map.put("jvm_free_memory", Runtime.getRuntime().freeMemory());
        map.put("jvm_max_memory", Runtime.getRuntime().maxMemory());
        return map;
    }
}
