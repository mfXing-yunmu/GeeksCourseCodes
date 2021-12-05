package com.yunmu.geek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mfXing
 */
@Component
public class HikariDemoRunner implements CommandLineRunner {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from test";
        List<TestDto> testDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(TestDto.class));
        testDtos.stream().forEach(System.out::println);
    }
}
