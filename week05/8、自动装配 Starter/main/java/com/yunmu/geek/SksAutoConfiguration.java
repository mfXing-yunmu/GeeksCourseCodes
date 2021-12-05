package com.yunmu.geek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mfXing
 */
@Configuration
@ConditionalOnClass(School.class)
@EnableConfigurationProperties(SksProperties.class)
@PropertySource("classpath:application.properties")
public class SksAutoConfiguration {
    private final SksProperties sksProperties;

    @Autowired
    public SksAutoConfiguration(SksProperties sksProperties) {
        this.sksProperties = sksProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "school", value = "enabled", havingValue = "true")
    public School school() {
        List<Integer> studentIds = sksProperties.getStudentIds();
        List<String> studentNames = sksProperties.getStudentNames();
        List<Integer> classIds = sksProperties.getKlassIds();
        List<String> classNames = sksProperties.getKlassNames();
        List<Map<String, Integer>> studentOfKlass = sksProperties.getStudentOfKlass();

        List<Student> students = new ArrayList<>(studentIds.size());
        for (int i=0; i<studentIds.size(); i++) {
            students.add(new Student(studentIds.get(i), studentNames.get(i)));
        }

        List<Klass> klasses = new ArrayList<>();
        for (int i=0; i<classIds.size(); i++) {
            klasses.add(new Klass(classIds.get(i), classNames.get(i)));
        }

        for (Map info: studentOfKlass) {
            klasses.get((Integer) info.get("klassId")).addStudent(students.get((Integer) info.get("studentId")));
        }

        System.out.println(studentIds.toString());
        System.out.println(studentNames.toString());
        System.out.println(classIds.toString());
        System.out.println(classNames.toString());
        System.out.println(studentOfKlass.toString());

        return new School(klasses);
    }
}
