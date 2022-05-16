/*
 * @(#) GSAuthenticationSuccessHandlerTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM004DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GSAuthenticationSuccessHandlerTest {

    @Mock
    private TBL120DAO tbl120DAO;

    @Mock
    private TBM004DAO tbm004DAO;

    @Mock
    private TBM001DAO tbm001DAO;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpSession session;

    @Captor
    ArgumentCaptor<String> nextURI;

    final RedirectStrategy strategy = Mockito.mock(RedirectStrategy.class);

    @InjectMocks
    private GSAuthenticationSuccessHandler gsAuthenticationSuccessHandler;

    private final String USER_ID = "10000001";
    private final String LOGIN_ID = "G0000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
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
    private final int SECOND_LOGGING = 1;
    private final int THIRD_LOGGING = 2;
    private final String SUCCESS_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.security.gs.GSAuthenticationSuccessHandler";
    private final String BASE_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.security.BaseAuthenticationHandler";

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
        systemSettings.put(CommonConstants.ST_SESSION_TIMEOUT_PERIOD, "60");

        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(httpServletRequest.getSession()).thenReturn(session);
        Mockito.when(httpServletRequest.getParameter(Mockito.anyString())).thenReturn(LOGIN_ID);
        Mockito.when(session.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
    }

    /**
     *案件ID：GAA0110／チェックリストID：UT-GAA0110-008／区分：E／チェック内容：LoginIdが見つかり、パスワードは正しいがアカウントがロックされている場合のテスト
     */
    @Test
    public void testWhenAccountLocked() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：G0000001 ※アカウントロック");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-009／区分：E／チェック内容：アカウントが既にログインしている場合のテスト
     */
    @Test
    public void testWhenAccountAlreadyLoggedIn() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：G0000001 ※ログイン中");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-010／区分：E／チェック内容：アカウントが利用できない場合のテスト
     */
    @Test
    public void testWhenAccountUnavailable() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.IMPOSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：G0000001 ※ユーザログイン利用不可");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     *案件ID：GAA0110／チェックリストID：UT-GAA0110-011／区分：N／チェック内容：アカウントがパスワードを変更する必要がある場合のテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNeedToChangePasswordByFlag() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        TBL120Entity tbl120Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120Entity.setBiginingPasswordChangeFlag("0");

        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(generateTBM001());

        UserDetails userDetails = prepareSecurityContextHolder(tbl120Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL120、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：G0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/SBA0110");
    }

    /**
     *案件ID：GAA0110／チェックリストID：UT-GAA0110-020／区分：N／チェック内容：アカウントが期間ごとにパスワードを変更する必要がないときにテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNeedToChangePasswordByPeriodTime() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        TBL120Entity tbl120Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120Entity.setBiginingPasswordChangeFlag("1");
        tbl120Entity.setPasswordPeriod(LocalDateTime.now().minus(5, ChronoUnit.DAYS));

        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(generateTBM001());

        UserDetails userDetails = prepareSecurityContextHolder(tbl120Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL120、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：G0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/SBA0110");
    }

    /**
     *案件ID：GAA0110／チェックリストID：UT-GAA0110-012／区分：N／チェック内容：アカウントがパスワードを変更する必要がない場合のテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNoNeedToChangePassword() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);

        TBL120Entity tbl120Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120Entity.setBiginingPasswordChangeFlag("1");

        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(generateTBM001());

        UserDetails userDetails = prepareSecurityContextHolder(tbl120Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        gsAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL120、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：G0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：都区市町村ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/GBA0110");
    }

    private TBL120Entity generateEntity(String accountLockFlag, String accountAvailableFlag, String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.TOKYO_STAFF.getCode()));
        entity.setPassword(PASSWORD);
        entity.setUserType(USER_TYPE);
        entity.setUserName(USER_NAME);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
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

    private TBM001Entity generateTBM001() {
        TBM001Entity entity = new TBM001Entity();
        entity.setUserAuthority("1");
        return entity;
    }

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }
}
