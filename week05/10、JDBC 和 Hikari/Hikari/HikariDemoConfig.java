package com.yunmu.geek;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author mfXing
 */
@Configuration
public class HikariDemoConfig {
    @Bean
    public DataSource dataSource() throws SQLException {
        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
