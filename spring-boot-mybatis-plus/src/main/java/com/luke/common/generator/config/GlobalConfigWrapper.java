package com.luke.common.generator.config;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Package com.luke.common.generator
 * ClassName: GlobalConfigWrapper
 * Description: 全局配置
 *
 * @author YangLong
 * @date 2019-05-21
 *
 * @version V1.0
 */
@ConfigurationProperties(prefix = "mybatis-plus.global")
public class GlobalConfigWrapper extends GlobalConfig {
}
