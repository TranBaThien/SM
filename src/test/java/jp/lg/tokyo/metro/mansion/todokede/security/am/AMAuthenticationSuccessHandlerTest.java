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
package jp.lg.tokyo.metro.mansion.todokede.security.am;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AMAuthenticationSuccessHandlerTest {

    @Mock
    private TBL110DAO tbl110DAO;

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
    private AMAuthenticationSuccessHandler amAuthenticationSuccessHandler;

    private final String USER_ID = "10000001";
    private final String LOGIN_ID = "M0000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String VALID = "1";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private final String UPDATE_USER_ID = "M0000001";
    private final String APARTMENT_NAME = " apartment name";
    private final String ST_M_ACCOUNT_LOCK_PERIOD = "60";
    private final int FIRST_LOGGING = 0;
    private final int SECOND_LOGGING = 1;
    private final int THIRD_LOGGING = 2;
    private final String SUCCESS_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.security.am.AMAuthenticationSuccessHandler";
    private final String BASE_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.security.BaseAuthenticationHandler";

    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD, ST_M_ACCOUNT_LOCK_PERIOD);
        systemSettings.put(CommonConstants.ST_SESSION_TIMEOUT_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(session.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
        Mockito.when(httpServletRequest.getSession()).thenReturn(session);
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-008／区分：E／チェック内容：LoginIdが見つかり、パスワードは正しいがアカウントがロックされている場合のテスト
     */
    @Test
    public void testWhenAccountLocked() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：M0000001 ※アカウントロック");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/MAA0110");
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-009／区分：E／チェック内容：アカウントが既にログインしている場合のテスト
     */
    @Test
    public void testWhenAccountAlreadyLoggedIn() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：M0000001 ※ログイン中");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/MAA0110");
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-010／区分：E／チェック内容：アカウントが利用できない場合のテスト
     */
    @Test
    public void testWhenAccountUnavailable() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.IMPOSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：M0000001 ※ユーザログイン利用不可");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/MAA0110");
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-011／区分：N／チェック内容：アカウントがパスワードを変更する必要がある場合のテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNeedToChangePassword() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        TBL110Entity tbl110Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl110Entity.setBiginingPasswordChangeFlag("0");
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL110、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：M0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/SBA0110");
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-020／区分：N／チェック内容：アカウントが期間ごとにパスワードを変更する必要がないときにテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNeedToChangePasswordByPeriodTime() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        TBL110Entity tbl110Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl110Entity.setBiginingPasswordChangeFlag("1");
        tbl110Entity.setPasswordPeriod(LocalDateTime.now().minus(5, ChronoUnit.DAYS));
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL110、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：M0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/SBA0110");
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-012／区分：N／チェック内容：アカウントがパスワードを変更する必要がない場合のテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountNoNeedToChangePassword() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        TBL110Entity tbl110Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl110Entity.setBiginingPasswordChangeFlag("1");
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL110、キー：10000001");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に成功しました。ユーザーID：M0000001");
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(THIRD_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/MBA0110");
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-017／区分：E／チェック内容：アカウントが無効な場合のテスト
     * @throws IOException
     */
    @Test
    public void testWhenAccountInvalid() throws IOException {
        LogAppender logAppender = LogAppender.initialize(SUCCESS_HANDLER_PATH, BASE_HANDLER_PATH);
        TBL110Entity tbl110Entity = generateEntity(AccountLockFlag.UNLOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl110Entity.setValidityFlag("2");
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        amAuthenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, response, authentication);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：M0000001 ※ユーザログイン無効化");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：管理組合ログイン");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/MAA0110");
    }

    private TBL110Entity generateEntity(String accountLockFlag, String accountAvailableFlag, String loginStatusFlag) {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
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

    private UserDetails prepareSecurityContextHolder(TBL110Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }
}
