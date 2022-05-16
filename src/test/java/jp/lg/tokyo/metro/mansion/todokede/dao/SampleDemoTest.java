/*
 * @(#) SampleDemoTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO.private final String LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/23
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.jdbc.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class SampleDemoTest extends AbstractDaoTest {

    @Autowired
    private TBL120DAO tbl120DAO;

    private final String LOGIN_ID = "G0000001";
    private final String USER_ID = "1000000001";
    private final String PASSWORD = "$2a$10$ZE.sliJb44AqxbsXhZBHJekKyHvI341AcSvo1.nxAf3uRtutSuPGq";
    private final String USER_TYPE = "1";
    private final String USER_NAME = "間には全";
    private final String USER_NAME_PHONETIC = "ユーザー";
    private final String CITY_CODE = "111111";
    private final String MAIL_ADDRESS = "G0000001@gmail.com";
    private final String TEL_NO = "0969-696-969";
    private final String USER_AUTHORITY = "1";
    private final LocalDateTime PASSWORD_PERIOD = DateTimeUtil.getLocalDateTimeFromString("20201231101010");
    private final int LOGIN_ERROR_COUNT = 0;
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final LocalDateTime ACCOUNT_LOCK_TIME = null;
    private final LocalDateTime LAST_TIME_LOGIN_TIME = DateTimeUtil.getLocalDateTimeFromString("20191217161502");
    private final String AVAILABILITY = "1";
    private final LocalDateTime STOP_TIME = null;
    private final String STOP_REASON = "fsdf";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "G0000001";
    private final LocalDateTime UPDATE_DATETIME = DateTimeUtil.getLocalDateTimeFromString("2019-12-17 16:15:03", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Test
    @Sql(value = "classpath:/sql/UT-GAA0110-013.sql")
    public void testGetUserWhenLoginIdFoundAndAccountNotDelete() {
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId(LOGIN_ID);

        assertThat(optional.isPresent()).isTrue();
        assertEntity(optional.get());
    }

    private void assertEntity(TBL120Entity entity) {
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getUserType()).isEqualTo(USER_TYPE);
        assertThat(entity.getUserName()).isEqualTo(USER_NAME);
        assertThat(entity.getUserNamePhonetic()).isEqualTo(USER_NAME_PHONETIC);
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getTelNo()).isEqualTo(TEL_NO);
        assertThat(entity.getPasswordPeriod()).isEqualTo(PASSWORD_PERIOD);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getAccountLockFlag()).isEqualTo(ACCOUNT_LOCK_FLAG);
        assertThat(entity.getAccountLockTime()).isEqualTo(ACCOUNT_LOCK_TIME);
        assertThat(entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
        assertThat(entity.getAvailability()).isEqualTo(AVAILABILITY);
        assertThat(entity.getStopTime()).isEqualTo(STOP_TIME);
        assertThat(entity.getStopReason()).isEqualTo(STOP_REASON);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
    }
}
