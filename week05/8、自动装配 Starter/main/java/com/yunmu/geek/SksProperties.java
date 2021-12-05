package com.yunmu.geek;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * 其中 @ConfigurationProperties 注解是读取 application.yml 中以 sks 开头的属性
 *
 * @author mfXing
 */
@EnableConfigurationProperties(SksProperties.class)
@ConfigurationProperties(prefix = "sks")
public class SksProperties {
    private List<Integer> studentIds;
    private List<String> studentNames;
    private List<Integer> klassIds;
    private List<String> klassNames;
    private List<Map<String, Integer>> studentOfKlass;

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    public List<Integer> getKlassIds() {
        return klassIds;
    }

    public void setKlassIds(List<Integer> klassIds) {
        this.klassIds = klassIds;
    }

    public List<String> getKlassNames() {
        return klassNames;
    }

    public void setKlassNames(List<String> klassNames) {
        this.klassNames = klassNames;
    }

    public List<Map<String, Integer>> getStudentOfKlass() {
        return studentOfKlass;
    }

    public void setStudentOfKlass(List<Map<String, Integer>> studentOfKlass) {
        this.studentOfKlass = studentOfKlass;
    }
}
