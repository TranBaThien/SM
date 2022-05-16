/*
 * @(#) GSLogoutSuccessHanderTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: 2019/12/23
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

@RunWith(SpringRunner.class)
public class GSLogoutSuccessHanderTest {
    
    @Mock
    private TBL120DAO tbl120dao;
    
    @Mock
    private HttpServletRequest httpServletRequest;
    
    @Mock
     private  HttpServletResponse httpServletResponse;
    
    @Mock
    private Authentication authentication;
    
    @Mock HttpSession session;
    
    @Captor
    ArgumentCaptor<String> nextURI;
    
    final RedirectStrategy strategy = Mockito.mock(RedirectStrategy.class);
    
    @InjectMocks
    private GSLogoutSuccessHander gsLogoutSuccessHander;
   
    private final String USER_ID = "10000001";
    private final String LOGIN_ID = "G0000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String LOGIN_STATUS_FLAG = "1";
    private final String MAIL_ADDRESS = "abc@gmail.com";
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String STOP_REASON = "stop reason";
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
    private final String UPDATE_USER_ID = "G0000001";
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    
    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
    
    /**
     *案件ID：ZAA0110／チェックリストID：UT-ZAA0110-007／区分：N／チェック内容：Test GSLogoutSuccess When Success
     */
    @Test
    public void testLogoutSuccessWhenSucess() throws IOException {

        UserDetails userDetails = prepareSecurityContextHolder(generateEntity());
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        TBL120Entity tbl120Entity = generateEntity();
        Mockito.when(tbl120dao.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(tbl120Entity));
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());
        
        gsLogoutSuccessHander.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        
        assertThat(nextURI.getValue()).isEqualTo("/GAA0110");
        
    }
    
    /**
     *案件ID：ZAA0110／チェックリストID：UT-ZAA0110-008／区分：E／チェック内容：Test GSLogoutSuccess When Failed
     */
    @Test
    public void testLogoutSuccessWhenFailed() throws IOException {

        UserDetails userDetails = prepareSecurityContextHolder(generateEntity());
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(tbl120dao.findByLoginId(Mockito.anyString())).thenReturn(null);
        Mockito.doNothing().when(strategy).sendRedirect(Mockito.any(), Mockito.any(), nextURI.capture());
        
        gsLogoutSuccessHander.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
        
    }
    
    private TBL120Entity generateEntity() {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(ACCOUNT_AVAILABLE_FLAG);
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

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }
    
}
