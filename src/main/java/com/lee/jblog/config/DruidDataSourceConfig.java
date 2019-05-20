package com.lee.jblog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


@Configuration
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
@ConditionalOnProperty(name = "spring.dataSource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource",
                        matchIfMissing = true)
public class DruidDataSourceConfig {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

    @Bean(name = "druidDataSource")
    @Primary
    public DataSource dataSource(@Autowired Environment environment){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSource.setInitialSize(Integer.parseInt(environment.getProperty("spring.datasource.druid.initial-size")));
        dataSource.setMinIdle(Integer.parseInt(environment.getProperty("spring.datasource.druid.min-idle")));
        dataSource.setMaxActive(Integer.parseInt(environment.getProperty("spring.datasource.druid.max-active")));
        dataSource.setMaxWait(Long.parseLong(environment.getProperty("spring.datasource.druid.max-wait")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(environment.getProperty("spring.datasource.druid.time-between-eviction-runs-millis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(environment.getProperty("spring.datasource.druid.min-evictable-idle-time-millis")));
        dataSource.setValidationQuery(environment.getProperty("spring.datasource.druid.validation-query"));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-while-idle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-on-borrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.test-on-return")));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(environment.getProperty("spring.datasource.druid.pool-prepared-statements")));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(environment.getProperty("spring.datasource.druid.max-pool-prepared-statement-per-connection-size")));
        return dataSource;
    }
}
