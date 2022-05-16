/*
 * @(#) GSAuthenticationFailedHandlerTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/11
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.AuthenticationException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GSAuthenticationFailedHandlerTest {

    @Mock
    private TBL120DAO tbl120DAO;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    private AuthenticationException authenticationException;

    @Mock
    private HttpSession session;

    @Captor
    ArgumentCaptor<String> nextURI;

    final RedirectStrategy strategy = Mockito.mock(RedirectStrategy.class);

    @InjectMocks
    private GSAuthenticationFailedHandler failedHandler;

    private final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.security.gs.GSAuthenticationFailedHandler";
    private final int FIRST_LOGGING = 0;
    private final int SECOND_LOGGING = 1;
    private final String LOGIN_ID = "txtLoginId";
    private final String USER_ID = "10000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String DELETE_FLAG = "0";
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLG = "0";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String ST_G_ACCOUNT_LOCK_PERIOD = "60";
    private final String ST_G_LOGIN_INVALIDITY_TIMES_MAX = "5";

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
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-005／区分：E／チェック内容：LoginIdが見つからないときにログインのテストに失敗しました
     * @throws IOException
     */
    @Test
    public void testWhenLoginIdNotFound() throws IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        failedHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);

        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(FIRST_LOGGING).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：txtLoginId ※アカウント不正");
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(SECOND_LOGGING).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：onAuthenticationFailure");
        LogAppender.cleanup(logAppender);
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-006／区分：E／チェック内容：LoginIdが見つかり、パスワードが間違っているとログインに失敗する
     * @throws IOException
     */
    @Test
    public void testWhenLoginIdFoundAndPasswordFailed() throws IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        TBL120Entity entity = generateEntity(0);
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(entity));
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        failedHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);

        assertThat(logAppender.getEvents().get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：txtLoginId ※ログイン認証不可");
        assertThat(logAppender.getEvents().get(1).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL120、キー：10000001");
        assertThat(logAppender.getEvents().get(2).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(2).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：onAuthenticationFailure");
        LogAppender.cleanup(logAppender);
        assertThat(entity.getLoginErrorCount()).isEqualTo(1);
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-007／区分：E／チェック内容：LoginIdが見つかり、パスワードが失敗し、エラーカウントが最大に達すると、ログインが失敗する
     * @throws IOException
     */
    @Test
    public void testWhenLoginIdFoundAndPasswordFailedAndErrorCountReachMax() throws IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        TBL120Entity entity = generateEntity(5);
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(entity));
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        failedHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);

        assertThat(logAppender.getEvents().get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo(
                "指定の回数ログイン認証に失敗したため、アカウントがロックされました。ユーザーＩＤ：txtLoginId　、アカウントロック日時：" + entity.getAccountLockTime().toString());
        assertThat(logAppender.getEvents().get(1).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：txtLoginId ※ログイン認証不可");
        assertThat(logAppender.getEvents().get(2).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(2).getMessage()).isEqualTo(
                "更新を実行します。テーブル：TBL120、キー：10000001");
        assertThat(logAppender.getEvents().get(3).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(3).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：onAuthenticationFailure");
        LogAppender.cleanup(logAppender);
        assertThat(entity.getLoginErrorCount()).isEqualTo(6);
        assertThat(entity.getAccountLockFlag()).isEqualTo("1");
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-019／区分：E／チェック内容：アカウントがロックされている場合、テストはエラーカウントを更新しません
     * @throws IOException
     */
    @Test
    public void testWillNotUpdateErrorCountWhenAccountLocked() throws IOException {
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        TBL120Entity entity = generateEntity(6);
        entity.setAccountLockFlag("1");
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(entity));
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());

        failedHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);

        assertThat(logAppender.getEvents().get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo(
                "ログイン認証に失敗しました。ユーザーID：txtLoginId ※アカウントロック");
        assertThat(logAppender.getEvents().get(1).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo(
                "プログラムを終了しました。モジュール名：onAuthenticationFailure");
        LogAppender.cleanup(logAppender);
        assertThat(entity.getLoginErrorCount()).isEqualTo(6);
        assertThat(entity.getAccountLockFlag()).isEqualTo("1");
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
    }

    private TBL120Entity generateEntity(int loginErrorCount) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(ACCOUNT_AVAILABLE_FLG);
        entity.setPassword(PASSWORD);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLoginErrorCount(loginErrorCount);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        return entity;
    }
}
