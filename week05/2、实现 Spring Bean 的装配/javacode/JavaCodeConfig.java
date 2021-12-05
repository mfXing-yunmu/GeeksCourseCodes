package com.yunmu.geek.javacode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Java 代码方式
 *
 * @author mfXing
 */
@Configuration
public class JavaCodeConfig {
    @Bean
    public JavaCodeDemo javaCodeExample() {
        return new JavaCodeDemo();
    }
}
