/*
 * @(#) CustomOncePerRequestFilterTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: Dec 15, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM004DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class CustomOncePerRequestFilterTest {
          
    @Mock
    TBM004DAO tbm004Dao;
    
    @Mock
    HttpSession session;
    @InjectMocks
     CustomOncePerRequestFilter customOncePerRequestFilter;
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-001 ／区分：E／チェック内容： Test Do Filter When CommonConstants SystemSetting Is Null
     */
    @Test
    public void testDoFilterWhenCommonConstantsSystemSettingIsNull() throws IOException, ServletException {        
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
                
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GJA0110");
        request.setRequestURI("/GBA0110");       
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        
        Mockito.when(tbm004Dao.findAllNotDeleted()).thenReturn(listTBM004Entity);
        request.setSession(session);
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-002 ／区分：N／チェック内容： Test Do Filter When PassWord Is Null
     */
    @Test
    public void testDoFilterWhenPassWordIsNull() throws IOException, ServletException {
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GAA0110");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/gs/login");
        request.setParameter("txtLoginId", "G0000001");
        request.setParameter("pwdPassword", "");
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-003 ／区分：N／チェック内容： Test Do Filter When Length Of LoginID Greater Than 8
     */
    @Test
    public void testDoFilterWhenLengthOfLoginIDGreaterThan8() throws IOException, ServletException {                        
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GAA0110");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/gs/login");
        request.setParameter("txtLoginId", "G00000011");
        request.setParameter("pwdPassword", "123456789");
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-004 ／区分：N／チェック内容： Test Do Filter When Length Of PassWord Smaller Than 8 And 16
     */
    @Test
    public void testDoFilterWhenLengthOfPassWordSmallerThan8And16() throws IOException, ServletException {                       
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GAA0110");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/gs/login");
        request.setParameter("txtLoginId", "G000000");
        request.setParameter("pwdPassword", "1234567");
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-005 ／区分：N／チェック内容： Test Do Filter When Length Of LoginId Is Not AlphaNumeric
     */
    @Test
    public void testDoFilterWhenLengthOfLoginIdIsNotAlphaNumeric() throws IOException, ServletException {
                
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GAA0110");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/gs/login");
        request.setParameter("txtLoginId", "#$%^&*^");
        request.setParameter("pwdPassword", "123456789");
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-006 ／区分：N／チェック内容： Test Do Filter When Length Of PassWord Is Not AlphaNumeric
     */
    @Test
    public void testDoFilterWhenLengthOfPassWordIsNotAlphaNumeric() throws IOException, ServletException {
                
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("GAA0110");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/gs/login");
        request.setParameter("txtLoginId", "G0000001");
        request.setParameter("pwdPassword", "12345678%%");
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    
    /**
     * 案件CustomOncePerRequestFilter／チェックリストID：UT- CustomOncePerRequestFilter-007 ／区分：N／チェック内容： Test Do Filter When Id Screen Else GsLogin
     */
    @Test
    public void testDoFilterWhenIdScreenElseGsLogin() throws IOException, ServletException {
        
        TBM004Entity entity = newTBM004Entity();
        List<TBM004Entity> listTBM004Entity = new ArrayList<TBM004Entity>();
        listTBM004Entity.add(entity);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        HttpSession session = request.getSession(true);
        request.setRequestURI("/am/login");
        
        
        request.setSession(generateSession(session));
        
        customOncePerRequestFilter.doFilter(request, response, filterChain);
    }
    private TBM004Entity newTBM004Entity() {
        TBM004Entity entity = new TBM004Entity();
        
        entity.setSetNo(SET_NO);
        entity.setSetTargetNameJp(SET_TARGET_NAME_JP);
        entity.setSetTargetNameEng(SET_TARGET_NAME_ENG);
        entity.setSetPoint(SET_POINT);
        entity.setComment(COMMENT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDateTime(UPDATE_DATETIME);
        
        return entity;
    }
    
    private HttpSession generateSession(HttpSession session) {
        session.setAttribute(CommonConstants.SYSTEM_SETTING, "12");
        return session;
    }
    
    private static final String SET_NO = "1";
    private static final String SET_TARGET_NAME_JP = "区市町村用アカウントロック期間";
    private static final String SET_TARGET_NAME_ENG = "G_ACCOUT_LOCK_PERIOD";
    private static final String SET_POINT = "60";
    private static final String COMMENT = "";
    private static final String DELETE_FLAG = "0";
    private static final String UPDATE_USER_ID = "";
    private static final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
}
