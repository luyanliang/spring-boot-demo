package com.luke.common.sys;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Package com.luke.common.sys
 * ClassName: SystemInfoServiceImpl
 * Description:
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-17
 */
@Component
public class SystemInfoServiceImpl implements SystemInfoService {

    @Override
    public Map<String, Object> customSystemInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", "测试加载自定义系统信息");
        return map;
    }
}
