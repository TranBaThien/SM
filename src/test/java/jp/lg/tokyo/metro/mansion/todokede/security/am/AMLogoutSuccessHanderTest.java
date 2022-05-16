/*
 * @(#) AMLogoutSuccessHanderTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: 2019/12/23
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.am;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

@RunWith(SpringRunner.class)
public class AMLogoutSuccessHanderTest {
    @Mock
    private TBL110DAO tbl110DAO;
    
    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpSession session;

    @Captor
    ArgumentCaptor<String> nextURI;

    final RedirectStrategy strategy = Mockito.mock(RedirectStrategy.class);
       
    private final String USER_ID = "1000";
    private final String LOGIN_ID = "M0000001";
    private final String ACCOUNT_LOCKED_FLAG = "1";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String VALID = "1";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String LOGIN_STATUS_FLAG = "0";

    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private final String UPDATE_USER_ID = "M0000001";

    private final String APARTMENT_NAME = " apartment name";

    
    @InjectMocks
    private AMLogoutSuccessHander amLogoutSuccessHander;
    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
    
    /**
     *案件ID：ZAA0110／チェックリストID：UT-ZAA0110-005／区分：N／チェック内容：Test AMLogoutSuccess When Success
     */
    @Test
    public void testAMLogoutSuccessWhenSuccess() throws IOException {
        TBL110Entity tbl110Entity = generateEntity();
        HttpSession sessionTest = newSession(session);
        Mockito.when(httpServletRequest.getSession()).thenReturn(sessionTest);        
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);                 
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(tbl110Entity);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());
        
        amLogoutSuccessHander.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        
        assertThat(nextURI.getValue()).isEqualTo("/MAA0110");
    }
    
    /**
     *案件ID：ZAA0110／チェックリストID：UT-ZAA0110-006／区分：E／チェック内容：Test AMLogoutSuccess When Failed
     */
    @Test(expected = NullPointerException.class)
    public void testAMLogoutSuccessWhenFailed() throws IOException {
        TBL110Entity tbl110Entity = generateEntity();
        HttpSession sessionTest = newSession(session);
        Mockito.when(httpServletRequest.getSession()).thenReturn(sessionTest);      
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);                 
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(null);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());
        
        amLogoutSuccessHander.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);       
    }
    
    private TBL110Entity generateEntity() {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCKED_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability("1");
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
        entity.setValidityFlag(VALID);
        entity.setTbl100(generateMansionInfo(USER_ID));
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
    
    private HttpSession newSession(HttpSession session)
    {
        session.setAttribute("loginId", USER_ID);
        return session;
    }
    
}
