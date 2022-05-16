/*
 * @(#)TBL120BDAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/18
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

@Repository
public class TBL120BDAO extends JdbcDaoSupport implements RowMapper<UserInforVo> {

    public TBL120BDAO() {
    }

    @Autowired
    public TBL120BDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * @Screen ABB0110
     * Get minimum Usable LoginId
     * @return String
     * @author PTLuan
     */
    public String getNewLoginId() {
        String sql = "SELECT IF( " 
                    + " EXISTS(SELECT SUBSTRING(tb.LOGIN_ID, 2, 6) as Id "
                    + " FROM tbl120 tb "
                    + " WHERE SUBSTRING(tb.LOGIN_ID, 2, 6) = '000001') "
                    + " ,(SELECT CAST(MIN(t1.Id + 1) AS UNSIGNED) "
                    + " FROM (SELECT SUBSTRING(tb.LOGIN_ID, 2, 6) as Id FROM tbl120 tb) as t1 "
                    + " LEFT JOIN (SELECT SUBSTRING(tb.LOGIN_ID, 2, 6) as Id  FROM tbl120 tb) as t2 "
                    + " ON t1.Id + 1 = t2.Id "
                    + " WHERE t2.Id IS NULL ) "
                    + " ,'1')";
        String loginId = jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);
        return loginId;
    }

    /**
     * @Screen ABA0110
     * Get list user info.
     * @return UserInforVo
     * @author PTLuan
     */
    public List<UserInforVo> getListUserB() {

        String sql = "SELECT   "
                + " tb.CITY_CODE, "
                + " tb.USER_ID,"
                + " tb.LOGIN_ID,"
                + " tb.USER_NAME,"
                + " tb2.CITY_NAME,"
                + " tb.USER_TYPE,"
                + " tb.LOGIN_ERROR_COUNT,"
                + " tb.AVAILABILITY,"
                + " tb.LOGIN_STATUS_FLAG"
                + " FROM tbl120 tb LEFT JOIN tbm001 tb2"
                + " ON tb.CITY_CODE = tb2.CITY_CODE"
                + " WHERE tb.DELETE_FLAG = '0'"
                + " AND tb2.DELETE_FLAG = '0'"
                + " ORDER BY tb.CITY_CODE ASC, tb.USER_NAME ASC";
        List<UserInforVo> list = jdbcTemplate.query(sql, this);
        return list;
    }

    /**
     * Convert form ResultSet to UserInforVo
     * @return UserInforVo
     * @author PTLuan
     */
    @Override
    public UserInforVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInforVo one = new UserInforVo();
        one.setCityCode(rs.getString("CITY_CODE"));
        one.setUserId(rs.getString("USER_ID"));
        one.setLoginId(rs.getString("LOGIN_ID"));
        one.setUserName(rs.getString("USER_NAME"));
        one.setCityName(rs.getString("CITY_NAME"));
        one.setUserType(rs.getString("USER_TYPE"));
        one.setAvailability(rs.getString("AVAILABILITY"));
        one.setLoginStatusFlag(rs.getString("LOGIN_STATUS_FLAG"));
        one.setLoginErrorCount(rs.getString("LOGIN_ERROR_COUNT"));
        return one;
    }
}
