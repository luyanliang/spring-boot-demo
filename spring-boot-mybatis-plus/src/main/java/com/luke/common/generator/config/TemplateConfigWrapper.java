package com.luke.common.generator.config;

import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Package com.luke.common.generator
 * ClassName: TemplateConfigWrapper
 * Description: 模板配置
 *
 * @author YangLong
 * @date 2019-05-21
 *
 * @version V1.0
 */
@ConfigurationProperties(prefix = "mybatis-plus.template")
public class TemplateConfigWrapper extends TemplateConfig {
}
