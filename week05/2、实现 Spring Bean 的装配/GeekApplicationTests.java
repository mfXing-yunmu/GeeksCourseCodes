package com.yunmu.geek;

import com.yunmu.geek.autoannotation.AutoAnnotationDemo;
import com.yunmu.geek.javacode.JavaCodeDemo;
import com.yunmu.geek.xml.XmlDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootTest
class GeekApplicationTests {
    @Autowired
    private AutoAnnotationDemo autoAnnotationDemo;

    @Autowired
    private JavaCodeDemo javaCodeDemo;

    /** 自动注解方式测试 */
    @Test
    public void autoAnnotationTest() {
        autoAnnotationDemo.getInfo();
    }

    /** Java 代码方式测试 */
    @Test
    public void javaCodeTest() {
        javaCodeDemo.getInfo();
    }

    /** XML 配置方式测试 */
    @Test
    public void xmlTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xmlDemoConfig.xml");
        XmlDemo xmlDemo = (XmlDemo) context.getBean("xmlDemo");
        xmlDemo.getInfo();
    }
}
