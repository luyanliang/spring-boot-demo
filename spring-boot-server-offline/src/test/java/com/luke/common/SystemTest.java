package com.luke.common;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

/**
 * Package com.luke.common
 * ClassName: SystemTest
 * Description: 单元测试
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-06-17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTest {
    private static final String SYS_STATUS = "/sys/status";
    private static final String SYS_OFF = "/sys/off";
    private static final String SYS_INFO = "/sys/info";

    @Value("${sys.switch-token}")
    private String token;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void aStatus() {
        ResponseEntity<String> status = testRestTemplate.getForEntity(SYS_STATUS, String.class);
        Assert.assertEquals(status.getStatusCode().value(), 200);
    }


    @Test
    public void bSysInfo() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userToken", token);
        HashMap<Object, Object> result = testRestTemplate.postForObject(SYS_INFO, multiValueMap, HashMap.class);
        Assert.assertNotNull(result);
    }

    @Ignore
    public void cTurnOffline() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        //==================================应用系统设置变量==================================//
        //userToken对应系统环境变量设置的switch-token
        multiValueMap.add("userToken", token);
        multiValueMap.add("appId", "本应用系统id");
        //==================================必选参数==================================//
        //jenkins或其他需要关闭时执行钩子的系统发送古来的变量
        multiValueMap.add("job", "jenkins jobName");
        multiValueMap.add("uri", "jenkins address http://10.12.132.121");
        multiValueMap.add("token", "jenkins token");
        //从参数很重要，如果设置为0或空，则不执行回调请求。其他数字执行回调请求。
        multiValueMap.add("callback", "0");
        multiValueMap.add("cause", "jenkins user/gitlab user/other user");
        //==================================可选参数==================================//
        //这个参数可有可无，是jenkins可以自定义自己的参数
        multiValueMap.add("rollback", "0");

        String msg = testRestTemplate.postForObject(SYS_OFF, multiValueMap, String.class);
        Assert.assertEquals("system off line!", msg);
    }

}
