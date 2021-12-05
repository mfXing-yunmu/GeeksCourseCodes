package com.yunmu.geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mfXing
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yunmu.geek")
public class HikariDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HikariDemoApplication.class, args);
    }
}
