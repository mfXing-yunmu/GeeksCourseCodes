package com.yunmu.geek.autoannotation;

import org.springframework.stereotype.Component;

/**
 * 自动注解方式
 *
 * @author mfXing
 */
@Component
public class AutoAnnotationDemo {
    public AutoAnnotationDemo() {
        System.out.println("Construct Method");
    }

    public void getInfo() {
        System.out.println("Auto Annotation Demo!");
    }
}
