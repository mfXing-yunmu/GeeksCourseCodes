package com.yunmu.geek.sharding;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author mfXing
 */
@Component
public class ShardingDataSource {
    private final String DRIVER = ".driver-class-name";
    private final String URL = ".url";
    private final String USERNAME = ".username";
    private final String PASSWORD = ".password";
    private final String DATASOURCE = "sharding.jdbc.datasource.names";

    @Autowired
    private Environment env;

    DataSource createDataSource() throws SQLException {
        /** 获取数据库列表 */
        String[] dbs = Objects.requireNonNull(env.getProperty(DATASOURCE)).split(",");

        /** 设置主从 */
        MasterSlaveRuleConfiguration configuration = new MasterSlaveRuleConfiguration(dbs[0], dbs[0],
                Arrays.asList(Arrays.copyOfRange(dbs, 1, dbs.length)));

        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(dbs), configuration, new HashMap<>(0), new Properties());
    }

    private Map<String, DataSource> createDataSourceMap(String[] dbs) {
        Map<String, DataSource> result = new HashMap<>(dbs.length);
        for (String db: dbs) {
            result.put(db, createDataSource("sharding.jdbc.datasource.ds-" + db));
        }
        return result;
    }

    private DataSource createDataSource(String prefix) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty(prefix + DRIVER));
        dataSource.setJdbcUrl(env.getProperty(prefix + URL));
        dataSource.setUsername(env.getProperty(prefix + USERNAME));
        dataSource.setPassword(env.getProperty(prefix + PASSWORD));
        return dataSource;
    }
}
