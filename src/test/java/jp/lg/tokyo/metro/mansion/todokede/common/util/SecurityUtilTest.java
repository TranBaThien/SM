/*
 * @(#) SecurityUtilTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: Dec 15, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class SecurityUtilTest {
    
    @Mock
    Authentication authentication;
    
    @Mock
    SecurityContext securityContext;
    
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
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    

    /**
     * 案件SecurityUtil／チェックリストID：UT- SecurityUtil-001 ／区分：N／チェック内容： Test Get Current LoginId When Success
     */
    @Test
    public void testGetUserPrincipleWhenSucess() {
        // prepare data
        UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(), UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        
        // execute
        SecurityUtil.getUserPrinciple();                
        SecurityUtil.getUserLoggedInInfo();
        SecurityUtil.getCurrentLoginId();
        SecurityUtil.getAuthorities();
        SecurityUtil.isAnonymousUser();
        
    }
    
    /**
     * 案件SecurityUtil／チェックリストID：UT- SecurityUtil-002 ／区分：E／チェック内容： Test Get Current LoginId When UserPrincipal Is Null
     */
    @Test
    public void testGetUserPrincipleWhenUserPrincipalIsNull() {
        // prepare data
        SecurityContextHolder.getContext().setAuthentication(authentication);        
        Mockito.when(authentication.getPrincipal()).thenReturn(null);
        
        // execute
        SecurityUtil.getUserPrinciple();                
        SecurityUtil.getUserLoggedInInfo();
        SecurityUtil.getCurrentLoginId();
        SecurityUtil.getAuthorities();
        
    }
    
    /**
     * 案件SecurityUtil／チェックリストID：UT- SecurityUtil-003 ／区分：E／チェック内容： Test Get Current LoginId When Exception
     */
    @Test
    public void testGetCurrentLoginIdWhenException() {        
        // execute
        SecurityUtil.getCurrentLoginId();
        
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

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

}
