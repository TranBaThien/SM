/*
 * @(#) SessionDestroyedEventListenerTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: 2019/12/23
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.SessionDestroyedEventListener;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class SessionDestroyedEventListenerTest {
    @Mock
    private TBL110DAO tbl110dao;
     
    @Mock
    private TBL120DAO tbl120dao;
     
    @Mock
    private HttpSession session;
     
    @Mock
    private HttpSessionDestroyedEvent destroyedEvent;
     
    @Mock
    private SecurityContext securityContext;
     
    @Mock
    private Authentication authentication;
     
     private final String USER_ID_GS = "10000001";
    private final String LOGIN_ID_GS = "G0000001";

    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String MAIL_ADDRESS = "abc@gmail.com"; 
    private final String STOP_REASON = "stop reason";
    private final String TEL_NO = "09999999";
    private final LocalDateTime UPDATE_DATETIME_GS = LocalDateTime.now();
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE_NONE = "0";
    private final String USER_TYPE_MANSION = "1";
    private final String USER_TYPE_TOKYO = "4";
     
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
    private final String LOGIN_STATUS_FLAG = "1";

    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private final String UPDATE_USER_ID = "10000001";
    private final String APARTMENT_NAME = " apartment name";
    private final String LOGIN_ID_TITLE ="loginId";

    private final String LOGIN_STATUS_UPDATE ="0";

    @InjectMocks
    SessionDestroyedEventListener sessionDestroyedEventListener;
    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
    
    
    /*Start test SessionDestroyedEventListener class */
    /**
             *案件ID：ZAA0110／チェックリストID：UT- ZAA0110-001／区分：N／チェック内容：Test Update Table110 Success When Type Is Mansion
     */
    @Test
    public void testUpdateTable110SucessWhenTypeIsMansion() {        

        // prepare Data
        TBL110Entity tbl110Entity = generateEntity(USER_TYPE_MANSION);
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        List<SecurityContext> listSecurityContext = new ArrayList<SecurityContext>();
        listSecurityContext.add(securityContext);
        Mockito.when(destroyedEvent.getSecurityContexts()).thenReturn(listSecurityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
          
        Mockito.when(tbl110dao.findByLoginId(userDetails.getUsername())).thenReturn(tbl110Entity);
        session = newSession(session);
        Mockito.when(destroyedEvent.getSession()).thenReturn(session);
          
        //Execute
        sessionDestroyedEventListener.onApplicationEvent(destroyedEvent);
          
        // Verify
        assertThat(tbl110Entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_UPDATE);
        assertThat(tbl110Entity.getUpdateUserId()).isEqualTo(USER_ID);
        assertThat(tbl110Entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
          
        assertThat(session.getAttribute(LOGIN_ID_TITLE)).isEqualTo(null);
    }
    
    /**
          *案件ID：ZAA0110／チェックリストID：UT- ZAA0110-002／区分：N／チェック内容：Test Update Table110 Success When Type Is None
     */
    @Test
    public void testUpdateTable110SucessWhenTypeISsNone() {
         
        // prepare Data
        TBL110Entity tbl110Entity = generateEntity(USER_TYPE_NONE);
        UserDetails userDetails = prepareSecurityContextHolder(tbl110Entity);
        List<SecurityContext> listSecurityContext = new ArrayList<SecurityContext>();
        listSecurityContext.add(securityContext);
        Mockito.when(destroyedEvent.getSecurityContexts()).thenReturn(listSecurityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
          
        Mockito.when(tbl110dao.findByLoginId(userDetails.getUsername())).thenReturn(tbl110Entity);
        session = newSession(session);
        Mockito.when(destroyedEvent.getSession()).thenReturn(session);
          
        //Execute
        sessionDestroyedEventListener.onApplicationEvent(destroyedEvent);
          
        // Verify
        assertThat(tbl110Entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_UPDATE);
        assertThat(tbl110Entity.getUpdateUserId()).isEqualTo(USER_ID);
        assertThat(tbl110Entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
          
        assertThat(session.getAttribute(LOGIN_ID_TITLE)).isEqualTo(null);
    }
    
    /**
          *案件ID：ZAA0110／チェックリストID：UT- ZAA0110-003／区分：N／チェック内容：Test Update Table120 Success When Type Not Is Mansion Or None
     */
    @Test
    public void testUpdateTable120SucessWhenTypeNotIsMansionOrNone() {
         
        // prepare Data
        TBL120Entity tbl120Entity = generateEntity1(USER_TYPE_TOKYO);
        UserDetails userDetails = prepareSecurityContextHolder(tbl120Entity);
        List<SecurityContext> listSecurityContext = new ArrayList<SecurityContext>();
        listSecurityContext.add(securityContext);
        Mockito.when(destroyedEvent.getSecurityContexts()).thenReturn(listSecurityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
          
        Mockito.when(tbl120dao.findByLoginId(userDetails.getUsername())).thenReturn(Optional.of(tbl120Entity));
        session = newSession(session);
        Mockito.when(destroyedEvent.getSession()).thenReturn(session);
          
        //Execute
        sessionDestroyedEventListener.onApplicationEvent(destroyedEvent);
          
        // Verify
        assertThat(tbl120Entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_UPDATE);
        assertThat(tbl120Entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(tbl120Entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);
          
        assertThat(session.getAttribute(LOGIN_ID_TITLE)).isEqualTo(null);
    }
        
    @Test
    /**
          *案件ID：ZAA0110／チェックリストID：UT- ZAA0110-004／区分：E／チェック内容：Test Update DB Failed
     */
    public void testUpdateDBFailed() {
        // Prepare data
        TBL120Entity tbl120Entity = generateEntity1(USER_TYPE_TOKYO);
        List<SecurityContext> listSecurityContext = new ArrayList<SecurityContext>();
        listSecurityContext.add(securityContext);
        Mockito.when(destroyedEvent.getSecurityContexts()).thenReturn(listSecurityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
          
        // Execute
        sessionDestroyedEventListener.onApplicationEvent(destroyedEvent);  
        assertThat(tbl120Entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(tbl120Entity.getUpdateUserId()).isEqualTo(USER_ID_GS);
        assertThat(tbl120Entity.getLastTimeLoginTime()).isEqualTo(LAST_TIME_LOGIN_TIME);      
    }
    /*End test SessionDestroyedEventListener class  */
    
    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }
    
    private UserDetails prepareSecurityContextHolder(TBL110Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }
    
    private TBL110Entity generateEntity(String userType) {
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
    
    private TBL120Entity generateEntity1(String userType) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID_GS);
        entity.setLoginId(LOGIN_ID_GS);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(ACCOUNT_AVAILABLE_FLAG);
        entity.setUserType(String.valueOf(UserTypeCode.TOKYO_STAFF.getCode()));
        entity.setPassword(PASSWORD);
        entity.setUserType(userType);
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
        entity.setUpdateDatetime(UPDATE_DATETIME_GS);
        
        return entity;
    }
    
    private TBL100Entity generateMansionInfo(String apartmentId) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(apartmentId);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITY_CODE);
        return entity;
    }
    
    private HttpSession newSession(HttpSession session) {
        session.setAttribute(LOGIN_ID_TITLE, USER_ID);
        return session;
    }
}


