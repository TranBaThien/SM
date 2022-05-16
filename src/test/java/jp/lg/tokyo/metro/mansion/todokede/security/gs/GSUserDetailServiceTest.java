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
package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GSUserDetailServiceTest {

    @Mock
    private HttpSession session;

    @Mock
    private TBL120DAO tbl120DAO;

    @InjectMocks
    private GSUserDetailService gsUserDetailService;

    private final String USER_ID = "10000001";
    private final String LOGIN_ID = "G0000001";
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
    private final String MAIL_ADDRESS = "abc@gmail.com";
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String STOP_REASON = "stop reason";
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
    private final String UPDATE_USER_ID = "G0000001";
    private final String USER_AUTHORITY = "1";
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    private final String ST_G_ACCOUNT_LOCK_PERIOD = "60";
    private final String ST_G_LOGIN_INVALIDITY_TIMES_MAX = "5";
    private final int FIRST_LOGGING = 0;


    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX, ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        systemSettings.put(CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD, ST_G_ACCOUNT_LOCK_PERIOD);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
    }

    /**
     *案件ID：GAA0110／チェックリストID：UT-GAA0110-001／区分：N／チェック内容：ユーザーを取得し、ログが正確にログに記録される
     */
    @Test
    public void testIsLoggingExactly() {
        LogAppender logAppender = LogAppender.initialize("jp.lg.tokyo.metro.mansion.todokede.security.gs.GSUserDetailService");
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(generateEntity(ACCOUNT_NON_LOCK_FLAG)));

        gsUserDetailService.loadUserByUsername(LOGIN_ID);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo("ログイン認証を開始しました。ユーザーID：G0000001");
        LogAppender.cleanup(logAppender);
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-002／区分：E／チェック内容：LoginIdが間違っているときにユーザーを取得する
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserWhenLoginIdWrong() {
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
        gsUserDetailService.loadUserByUsername(LOGIN_ID);
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-003／区分：N／チェック内容：LoginIdが正確でアカウントがロックされていない場合にユーザーを取得する
     */
    @Test
    public void testWhenLoginIdExactlyAndAccountNotLock() {
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(generateEntity(ACCOUNT_NON_LOCK_FLAG)));

        UserPrincipal result = (UserPrincipal) gsUserDetailService.loadUserByUsername(LOGIN_ID);
        assertEntity((TBL120Entity) result.getEntity(), ACCOUNT_NON_LOCK_FLAG);
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-004／区分：E／チェック内容：LoginIdが正確でアカウントがロックされている場合にユーザーを取得する
     */
    @Test
    public void testWhenLoginIdExactlyAndAccountLocked() {
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(generateEntity(ACCOUNT_LOCKED_FLAG)));

        UserPrincipal result = (UserPrincipal) gsUserDetailService.loadUserByUsername(LOGIN_ID);
        assertEntity((TBL120Entity) result.getEntity(), ACCOUNT_LOCKED_FLAG);
    }



    private TBL120Entity generateEntity(String accountLockFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(AVAILABILITY);
        entity.setPassword(PASSWORD);
        entity.setUserType(USER_TYPE);
        entity.setUserName(USER_NAME);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setAvailability(AVAILABILITY);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setCityCode(CITY_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setMailAddress(MAIL_ADDRESS);
        entity.setTelNo(TEL_NO);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setStopReason(STOP_REASON);
        entity.setStopTime(STOP_TIME);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        return entity;
    }

    private void assertResult(UserPrincipal result, String accountLockFlag) {
        assertThat(result.isCredentialsNonExpired()).isEqualTo(true);
        assertThat(result.isAccountNonExpired()).isEqualTo(true);

        if (ACCOUNT_LOCKED_FLAG.equals(accountLockFlag)) {
            assertThat(result.isAccountNonLocked()).isEqualTo(false);
        } else {
            assertThat(result.isAccountNonLocked()).isEqualTo(true);
        }

        assertThat(result.isEnabled()).isEqualTo(true);
        assertThat(result.getUsername()).isEqualTo(LOGIN_ID);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
        assertEntity((TBL120Entity) result.getEntity(), accountLockFlag);
    }

    private void assertEntity(TBL120Entity entity, String accountLockFlag) {
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
        assertThat(entity.getUserName()).isEqualTo(USER_NAME);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getAccountLockTime()).isEqualTo(ACCOUNT_LOCK_TIME);
        assertThat(entity.getAccountLockFlag()).isEqualTo(accountLockFlag);
        assertThat(entity.getPasswordPeriod()).isEqualTo(PASSWORD_PERIOD);
        assertThat(entity.getAvailability()).isEqualTo(AVAILABILITY);
        assertThat(entity.getUserType()).isEqualTo(USER_TYPE);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUserNamePhonetic()).isEqualTo(USER_NAME_PHONETIC);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getTelNo()).isEqualTo(TEL_NO);
        assertThat(entity.getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getStopReason()).isEqualTo(STOP_REASON);
        assertThat(entity.getStopTime()).isEqualTo(STOP_TIME);
    }
}
