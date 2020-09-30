package com.luke.common.sys;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * Package com.luke.common.sys
 * ClassName: SystemSwitchController
 * Description: 逻辑切换当前应用是否下线，如果下线，将系统运行状态切换到下线，并发出系统下线事件。
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-14
 */
@DependsOn("systemHealthConfig")
@Api(tags = "系统信息接口")
@RestController
public class SystemSwitchController {
    static final String SYS_STATUS = "/sys/status";
    static final String SYS_OFF = "/sys/off";
    static final String SYS_INFO = "/sys/info";
    @Value("${sys.switch-token}")
    private String switchToken;

    @Autowired
    private SystemHealthConfig systemHealthConfig;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private SystemInfoService systemInfoService;

    @ApiOperation("检测服务是否下线")
    @GetMapping(SYS_STATUS)
    public String status(HttpServletResponse response) {
        String result = "offLine";

        boolean switcher = systemHealthConfig.getSwitcher().get();
        response.setStatus(switcher ? 200 : 500);
        if (switcher) {
            result = "onLine";
        }
        return result;
    }

    @ApiOperation("将服务下线")
    @ApiImplicitParam(value = "密钥", name = "userToken", required = true, dataType = "String", paramType = "query")
    @PostMapping(SYS_OFF)
    public String turnOff(String userToken, @RequestParam Map<String, String> params) {
        String msg = "status not change!";
        if (Objects.equals(switchToken, userToken)) {
            systemHealthConfig.getSwitcher().getAndSet(false);
            //发布下线事件
            eventPublisher.publishEvent(new SystemOfflineEvent("turnOff", params));
            msg = "system off line!";
        }
        return msg;
    }

    @ApiOperation("获取系统信息")
    @ApiImplicitParam(value = "密钥", name = "userToken", required = true, dataType = "String", paramType = "query")
    @PostMapping(SYS_INFO)
    public Map<String, Object> sysInfo(String userToken) {
        if (Objects.equals(switchToken, userToken)) {
            Map<String, Object> info = systemInfoService.loadSystemInfo();
            return info;
        }
        return null;
    }
}
