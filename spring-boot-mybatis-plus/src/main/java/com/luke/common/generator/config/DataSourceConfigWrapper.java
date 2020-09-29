package com.luke.common.generator.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Package com.luke.common.generator
 * ClassName: DataSourceConfigWrapper
 * Description: 数据源配置
 *
 * @author YangLong
 * @date 2019-05-21
 *
 * @version V1.0
 */
@ConfigurationProperties(prefix = "mybatis-plus.datasource")
public class DataSourceConfigWrapper extends DataSourceConfig {
}
