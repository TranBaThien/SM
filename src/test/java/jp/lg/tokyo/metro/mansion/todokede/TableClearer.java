/*
 * @(#) TableClearer.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/23
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede;

import jp.lg.tokyo.metro.mansion.todokede.common.*;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableClearer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    private Connection connection;

    public void clearTables() {
        try {
            connection = dataSource.getConnection();
            tryToClearTables();
        } catch (SQLException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            }
        }
    }

    private void tryToClearTables() throws SQLException {
        List<String> tableNames = getTableNames();
        clear(tableNames);
    }

    private List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(
                connection.getCatalog(), null, null, new String[]{"TABLE"});

        while (rs.next()) {
            String tbName = rs.getString("TABLE_NAME");
            if (!tbName.toLowerCase().contains("tbm")) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        }

        return tableNames;
    }

    private void clear(List<String> tableNames) throws SQLException {
        Statement statement = buildSqlStatement(tableNames);

        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();

        statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 0"));
        addDeleteStatements(tableNames, statement);
        statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 1"));

        return statement;
    }

    private void addDeleteStatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch(sql("DELETE FROM " + tableName));
            } catch (SQLException e) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
                throw new RuntimeException(e);
            }
        });
    }

    private String sql(String sql) {
        return sql;
    }

}
