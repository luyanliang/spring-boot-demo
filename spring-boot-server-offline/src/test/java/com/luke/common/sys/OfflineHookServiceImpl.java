package com.luke.common.sys;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Package com.luke.common.sys
 * ClassName: OfflineHookServiceImpl
 * Description:
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-17
 */
@Component
public class OfflineHookServiceImpl implements OfflineHookService {
    @Override
    public void onSystemOffline(Map<String, String> params) {
        if (!CollectionUtils.isEmpty(params)) {
            params.forEach((k, v) -> System.out.println(k + ":" + v));
        }
        System.out.println("系统下线！");
    }
}
