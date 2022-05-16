/*
 * @(#) GSUserdetailServiceTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/03
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.security.am;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AMUserDetailServiceTest {

    @Mock
    private HttpSession session;

    @Mock
    private TBL110DAO tbl110DAO;

    @InjectMocks
    private AMUserDetailService amUserDetailService;

    private final String USER_ID = "10000001";
    private final String LOGIN_ID = "M0000001";
    private final String ACCOUNT_LOCKED_FLAG = "1";
    private final String ACCOUNT_NON_LOCK_FLAG = "0";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String AVAILABILITY = "1";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String LOGIN_STATUS_FLAG = "0";
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private final String UPDATE_USER_ID = "M0000001";
    private final String ST_M_ACCOUNT_LOCK_PERIOD = "60";
    private final String APARTMENT_NAME = "apartment name";
    private final String VALID = "1";
    private final int FIRST_LOGGING = 0;


    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD, ST_M_ACCOUNT_LOCK_PERIOD);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(session.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-001／区分：N／チェック内容：ユーザーを取得し、ログが正確にログに記録される
     */
    @Test
    public void testIsLoggingExactly() {
        LogAppender logAppender = LogAppender.initialize("jp.lg.tokyo.metro.mansion.todokede.security.am.AMUserDetailService");
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(generateEntity(ACCOUNT_NON_LOCK_FLAG));

        amUserDetailService.loadUserByUsername(LOGIN_ID);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo("ログイン認証を開始しました。ユーザーID：M0000001");
        LogAppender.cleanup(logAppender);
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-002／区分：E／チェック内容：LoginIdが間違っているときにユーザーを取得する
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserWhenLoginIdWrong() {
        amUserDetailService.loadUserByUsername(LOGIN_ID);
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-003／区分：N／チェック内容：LoginIdが正確でアカウントがロックされていない場合にユーザーを取得する
     */
    @Test
    public void testWhenLoginIdExactlyAndAccountNotLock() {
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(generateEntity(ACCOUNT_NON_LOCK_FLAG));

        UserPrincipal result = (UserPrincipal) amUserDetailService.loadUserByUsername(LOGIN_ID);
        assertEntity((TBL110Entity) result.getEntity(), ACCOUNT_NON_LOCK_FLAG);
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-004／区分：E／チェック内容：LoginIdが正確でアカウントがロックされている場合にユーザーを取得する
     */
    @Test
    public void testWhenLoginIdExactlyAndAccountLocked() {
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(generateEntity(ACCOUNT_LOCKED_FLAG));

        UserPrincipal result = (UserPrincipal) amUserDetailService.loadUserByUsername(LOGIN_ID);
        assertEntity((TBL110Entity) result.getEntity(), ACCOUNT_LOCKED_FLAG);
    }



    private TBL110Entity generateEntity(String accountLockFlag) {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(AVAILABILITY);
        entity.setPassword(PASSWORD);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setStopTime(STOP_TIME);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setTbl100(generateMansionInfo(USER_ID));
        entity.setValidityFlag(VALID);
        return entity;
    }

    private TBL100Entity generateMansionInfo(String apartmentId) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(apartmentId);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITY_CODE);
        return entity;
    }

    private void assertEntity(TBL110Entity entity, String accountLockFlag) {
        assertThat(entity.getApartmentId()).isEqualTo(USER_ID);
        assertThat(entity.getTbl100().getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getAccountLockTime()).isEqualTo(ACCOUNT_LOCK_TIME);
        assertThat(entity.getAccountLockFlag()).isEqualTo(accountLockFlag);
        assertThat(entity.getPasswordPeriod()).isEqualTo(PASSWORD_PERIOD);
        assertThat(entity.getAvailability()).isEqualTo(AVAILABILITY);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
        assertThat(entity.getTbl100().getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getStopTime()).isEqualTo(STOP_TIME);
    }
}
