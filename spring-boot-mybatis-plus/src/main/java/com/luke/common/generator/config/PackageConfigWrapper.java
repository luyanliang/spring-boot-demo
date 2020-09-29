package com.luke.common.generator.config;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Package com.luke.common.generator
 * ClassName: PackageConfigWrapper
 * Description: 包名配置
 *
 * @author YangLong
 * @date 2019-05-21
 *
 * @version V1.0
 */
@ConfigurationProperties(prefix = "mybatis-plus.package")
public class PackageConfigWrapper extends PackageConfig {
}
