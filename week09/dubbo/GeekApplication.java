package com.yunmu.geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring-dubbo.xml"})
public class GeekApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GeekApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        SpringApplication.run(GeekApplication.class, args);
    }

}
