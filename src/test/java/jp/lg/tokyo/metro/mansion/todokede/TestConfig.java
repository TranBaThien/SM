/*
 * @(#) TestConfig.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/23
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede;

import com.zaxxer.hikari.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;

import javax.sql.*;

@TestConfiguration
@EnableJpaAuditing
public class TestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://192.168.66.56:3306/mansion_todokede_test2");
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setUsername("mansion");
        hikariDataSource.setPassword("Abc@12345");
        return hikariDataSource;
    }

}
