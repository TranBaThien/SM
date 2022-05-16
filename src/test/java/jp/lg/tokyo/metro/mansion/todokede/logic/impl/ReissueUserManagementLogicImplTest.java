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

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissueUserManagementVo;

@RunWith(SpringRunner.class)
public class ReissueUserManagementLogicImplTest {
    
    @Mock
    private TBL110DAO tbl110DAO;
    
    @Mock
    private SequenceUtil sequenceUtil;
    
    @InjectMocks
    private ReissueUserManagementLogicImpl reissueUserManagementLogic;
    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
          
    /* Create TBL110Entity */
    private final String APARTMENT_ID = "100000001";
    private final String APARTMENT_ID1 = "1002";
    private final String LOGIN_ID = "G0000044";
    private final String LOGIN_ID1 = "G0000045";
    private final String PASSWORD = "1111111111111111";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String ACCOUNT_LOCK_FLAG ="0";
    private final LocalDateTime ACCOUNT_LOCK_TIME = null;
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final String VALIDITY_FLAG = "1";
    private final LocalDateTime LOGIN_VALIDITY_TIME = LocalDateTime.now();
    private final String AVAILABILITY = "1";
    private final LocalDateTime STOP_TIME =  null;
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "";
    private final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private final Timestamp UPDATE_DATETIME_NULL = null;
        
    private TBL110Entity newEntity(String apartmentId, LocalDateTime lastTimeLoginTime) {
        TBL110Entity entity = new TBL110Entity();
        
        entity.setApartmentId(apartmentId);
        entity.setLoginId(LOGIN_ID);
        entity.setPassword(PASSWORD);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLastTimeLoginTime(lastTimeLoginTime);
        entity.setValidityFlag(VALIDITY_FLAG);
        entity.setLoginValidityTime(LOGIN_VALIDITY_TIME);
        entity.setAvailability(AVAILABILITY);
        entity.setStopTime(STOP_TIME);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        
        return entity;
    }
    
    private TBL110Entity newEntity1(String loginId, Timestamp timeStamp) {
        TBL110Entity entity = new TBL110Entity();
        
        entity.setApartmentId(APARTMENT_ID);
        entity.setLoginId(loginId);
        entity.setPassword(PASSWORD);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        entity.setValidityFlag(VALIDITY_FLAG);
        entity.setLoginValidityTime(LOGIN_VALIDITY_TIME);
        entity.setAvailability(AVAILABILITY);
        entity.setStopTime(STOP_TIME);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(timeStamp);
        
        return entity;
    }
    
    private ReissueUserManagementVo newVo() {
        ReissueUserManagementVo vo = new ReissueUserManagementVo();
        vo.setPwdPassword(PASSWORD);
        vo.setPasswordPeriod(PASSWORD_PERIOD);
        vo.setLoginErrorCount(LOGIN_ERROR_COUNT);
        vo.setAcountLockFlag(ACCOUNT_LOCK_FLAG);
        vo.setAccountLockTime(ACCOUNT_LOCK_TIME);
        vo.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        vo.setBindingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        vo.setDeleteFlag(DELETE_FLAG);
        vo.setUpdateUserId(UPDATE_USER_ID);
        vo.setUpdateDateTime(UPDATE_DATETIME);
        return vo;
    }
    
    /* Start test class updateUserLogin */
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-001／区分：N／チェック内容：Test Update User Login Success

    @Test
    public void testUpdateUserLoginWhenEntityNotNull() {
        // Prepare data
        TBL110Entity entity = newEntity(APARTMENT_ID,LAST_TIME_LOGIN_TIME);
        Mockito.when(tbl110DAO.getUserByApartmentId(Mockito.anyString())).thenReturn(entity);
        // Execute
        boolean save = reissueUserManagementLogic.updateUserLogin(APARTMENT_ID, newVo());
        
        // Check result
        assertTrue(save);
    }
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-002／区分：E／チェック内容：Test Update User Login Failure
    @Test
    public void testUpdateUserLoginWhenEntityNull() {
        // Prepare data
        Mockito.when(tbl110DAO.getUserByApartmentId(Mockito.anyString())).thenReturn(null);
        
        // Execute
        boolean save = reissueUserManagementLogic.updateUserLogin(APARTMENT_ID, newVo());
        
        // Check result
        assertFalse(save);
    }
    /* End test class updateUserLogin */
    
    /* Start test class getUserLoginInformation */
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-003／区分: I／チェック内容：Test Get User Login Information Success
    @Test
    public void testGetUserLoginInformationWhenEntityNotNull() {
        TBL110Entity entity = newEntity(APARTMENT_ID,LAST_TIME_LOGIN_TIME);
        Mockito.when(tbl110DAO.getUserByApartmentId(Mockito.anyString())).thenReturn(entity);
        
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformation(APARTMENT_ID);
        assertResult(vo, entity);    
    }
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-004／区分：E／チェック内容：Test Get User Login Information When Entity Is Null
    @Test
    public void testGetUserLoginInformationWhenEntityNull() throws BusinessException {
        // Prepare data
        Mockito.when(tbl110DAO.getUserByApartmentId(Mockito.anyString())).thenReturn(null);
        
        // Execute
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformation(APARTMENT_ID1);
        
        // Check result
        assertNull(vo);
    }    
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-005／区分: I／チェック内容：Test Get User Login Information When Last Time Login Is Null
    @Test
    public void testGetUserLoginInformationWhenEntityNotNullAndUpdateDateTimeIsNull() {
        // Prepare data
        TBL110Entity entity = newEntity(APARTMENT_ID,null);
        Mockito.when(tbl110DAO.getUserByApartmentId(Mockito.anyString())).thenReturn(entity);
        
        // Execute
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformation(APARTMENT_ID);
        // Check result
        assertResultUpdateDateTimeIsNull(vo, entity);    
    }
    
    
    private void assertNull(ReissueUserManagementVo vo) {

        assertThat(vo.getLblLoginidNow()).isEqualTo(null);
        assertThat(vo.getApartmentId()).isEqualTo(null);

    }


    private void assertResult(ReissueUserManagementVo vo, TBL110Entity entity) {
        assertThat(vo.getLblLoginidNow()).isEqualTo(entity.getLoginId());
        assertThat(vo.getApartmentId()).isEqualTo(entity.getApartmentId());
        assertThat(vo.getUpdateDateTimeInitial()).isEqualTo(entity.getUpdateDatetime().toString());
        
    }
    
    private void assertResultUpdateDateTimeIsNull(ReissueUserManagementVo vo, TBL110Entity entity) {
        assertThat(vo.getLblLoginidNow()).isEqualTo(entity.getLoginId());
        assertThat(vo.getApartmentId()).isEqualTo(entity.getApartmentId());
        assertThat(vo.getUpdateDateTimeInitial()).isEqualTo(entity.getUpdateDatetime().toString());
    }
    /* End test class getUserLoginInformation */
    
    /* Start test class getUserLoginInformationByLoginId */
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-006／区分：N／チェック内容：Test Get User Login Information By Login Id Success
    @Test
    public void testGetUserLoginInformationByLoginIdWhenEntityNotNull() {
        // Prepare data
        TBL110Entity entity = newEntity1(LOGIN_ID,UPDATE_DATETIME);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        
        // Execute
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformationByLoginId(LOGIN_ID);
        // Check result
        assertResult1(vo, entity);    
    }
    
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-006／区分：E／チェック内容：Test Get User Login Information By Login Id  Failure
    @Test
    public void testGetUserLoginInformationByLoginIdWhenEntityNull() throws BusinessException {
        // Prepare data
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(null);
        
        // Execute
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformationByLoginId(LOGIN_ID1);
        
        // Check result
        assertNull(vo);
    }
    
    // 案件ID：GLA0110／チェックリストID：UT- GLA0110-008／区分: I／チェック内容：Test Get User Login Information By Login Id  When Update Date time Is Null
    @Test
    public void testGetUserLoginInformationByLoginIdWhenEntityNotNullAndUpdateDatetimeIsNull() throws BusinessException {
        // Prepare data
        TBL110Entity entity = newEntity1(LOGIN_ID1, UPDATE_DATETIME_NULL );
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        
        // Execute
        ReissueUserManagementVo vo = reissueUserManagementLogic.getUserLoginInformationByLoginId(LOGIN_ID1);
        
        // Check result
        assertResultUpdateDateTimeIsNull1(vo, entity);
    }
    
    private void assertResult1(ReissueUserManagementVo vo, TBL110Entity entity) {
        assertThat(vo.getLoginStatusFlag()).isEqualTo(entity.getLoginStatusFlag());
        assertThat(vo.getUpdateDateTime()).isEqualTo(entity.getUpdateDatetime());
        
    }
    
    private void assertResultUpdateDateTimeIsNull1(ReissueUserManagementVo vo, TBL110Entity entity) {
        assertThat(vo.getLoginStatusFlag()).isEqualTo(entity.getLoginStatusFlag());        
        assertThat(vo.getUpdateDateTime()).isEqualTo(null);
    }
    /* End test class getUserLoginInformationByLoginId */

}
