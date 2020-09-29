package com.luke.common.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.luke.common.generator.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package com.luke.common.generator
 * ClassName: CodeGeneratorConfig
 * Description: 配置代码生成器
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-05-21
 */
@Configuration
@EnableConfigurationProperties({GlobalConfigWrapper.class, DataSourceConfigWrapper.class, PackageConfigWrapper.class, StrategyConfigWrapper.class, TemplateConfigWrapper.class})
public class CodeGeneratorConfig {
    @Autowired
    private DataSourceConfigWrapper dataSourceConfig;
    @Autowired
    private GlobalConfigWrapper globalConfig;
    @Autowired
    private StrategyConfigWrapper strategyConfig;
    @Autowired
    private PackageConfigWrapper packageConfig;
    @Autowired
    private TemplateConfigWrapper templateConfig;

    @Bean
    public AutoGenerator getGenerator() {
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setDataSource(dataSourceConfig)
                .setGlobalConfig(globalConfig)
                .setStrategy(strategyConfig.genTableFill())
                .setPackageInfo(packageConfig)
                .setTemplate(templateConfig);
        return autoGenerator;
    }
}
