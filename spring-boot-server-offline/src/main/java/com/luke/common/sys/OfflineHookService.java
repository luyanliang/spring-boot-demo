package com.luke.common.sys;

import java.util.Map;

/**
 * Package com.luke.common.sys
 * ClassName: OfflineHookService
 * Description: 当系统关闭时要做的处理流程
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
public interface OfflineHookService {

    /**
     * 系统关闭时执行的方法
     *
     * @param params 从事件中获取到的参数
     */
    void onSystemOffline(Map<String, String> params);
}
