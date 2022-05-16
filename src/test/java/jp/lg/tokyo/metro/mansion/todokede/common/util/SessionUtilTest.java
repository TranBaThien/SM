/*
 * @(#) SessionUtilTest.java 
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
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.security.am.AMAuthenticationSuccessHandler;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class SessionUtilTest {
    
    @Mock
    HttpSession session;
    
    @Mock
    Enumeration<String> sessionList;
    
    /**
     * 案件SessionUtil／チェックリストID：UT- SessionUtil-001 ／区分：N／チェック内容： Test Get System Setting When Success
     */
    @Test
    public void testGetSystemSettingWhenSuccess () throws IOException {
                
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
       
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX, CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        systemSettings.put(CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD, CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(session.getAttributeNames()).thenReturn(sessionList);
        
        SessionUtil.getSystemSettings();
        SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX, CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);        
        SessionUtil.setScreenId("MAA0110");
        SessionUtil.getPreviousScreenId();        
        SessionUtil.getScreenId();
        SessionUtil.deleteUnNeedData();
        SessionUtil.invalidate();
        
    }
    
    /**
     * 案件SessionUtil／チェックリストID：UT- SessionUtil-002 ／区分：E／チェック内容： Test Get System Setting When Session Null
     */
    @Test
    public void testGetSystemSettingWhenSessionNull () throws IOException {
                
        MockHttpServletRequest request = new MockHttpServletRequest();       
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX, CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        systemSettings.put(CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD, CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        
        SessionUtil.getSystemSettings();
        SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX, CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX);
        SessionUtil.setScreenId("MAA0110");
        SessionUtil.getPreviousScreenId();        
        SessionUtil.getScreenId();
        SessionUtil.deleteUnNeedData();
        SessionUtil.invalidate();
        
    }
    
}
