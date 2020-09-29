package com.luke.common.generator.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Package com.luke.common.generator
 * ClassName: StrategyConfigWrapper
 * Description: 代码生成策略配置
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "mybatis-plus.strategy")
public class StrategyConfigWrapper extends StrategyConfig {
    private Map<String, FieldFill> tableFills;

    /**
     * 生成table fill
     *
     * @return StrategyConfigWrapper
     */
    public StrategyConfigWrapper genTableFill() {
        if (!CollectionUtils.isEmpty(tableFills)) {
            List<TableFill> tableFillList = tableFills.entrySet().stream().map(e -> new TableFill(e.getKey(), e.getValue())).collect(Collectors.toList());
            this.setTableFillList(tableFillList);
        }
        return this;
    }
}
