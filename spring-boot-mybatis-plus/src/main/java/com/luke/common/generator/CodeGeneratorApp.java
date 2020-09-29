package com.luke.common.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Package com.luke.common.generator
 * ClassName: CodeGeneratorApp
 * Description: 生成代码模板app
 *
 * @author YangLong
 * @version V1.0
 * @date 2019-05-21
 */
@SpringBootApplication
public class CodeGeneratorApp implements CommandLineRunner {

    @Autowired
    private AutoGenerator autoGenerator;

    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApp.class, args);
    }

    @Override
    public void run(String... args) {
        autoGenerator.execute();
    }
}
