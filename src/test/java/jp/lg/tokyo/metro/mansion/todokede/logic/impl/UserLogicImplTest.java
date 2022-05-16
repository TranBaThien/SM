/*
 * @(#) UserLogicImplTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: 2020/01/06
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.SystemException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class UserLogicImplTest {
    
    @Mock
    private TBL120DAO tbl120Dao;
     
    @Rule
    public ExpectedException expectedThrow = ExpectedException.none();
    
    @InjectMocks
    UserLogicImpl userLogicImpl;
    
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
    private final LocalDateTime UPDATE_DATETIME1 = LocalDateTime.now();
    private final String UPDATE_USER_ID = "G0000001";
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    
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
    
    
    /**
     *  案件ID：GDA0110／チェックリストID：UT- GDA0110-020／区分：I／チェック内容：Test Get Government Staff User Login Info When Success
     * @throws BusinessException
     * 
     */
    
    @Test
    public void testGetGovernmentStaffUserLoginInfoWhenSuccess() throws BusinessException, SystemException {
        //prepare data
        TBL120Entity entity = generateEntity();
        Mockito.when(tbl120Dao.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(entity);
        
        //execute
        GovernmentStaffInfoVo governmentStaffInfoVo = userLogicImpl.getGovernmentStaffUserLoginInfo(USER_ID);
        
        //assert Result
        assertThat(governmentStaffInfoVo.getUserId()).isEqualTo(USER_ID);
    }
    
    /**
    *  案件ID：GDA0110／チェックリストID：UT- GDA0110-020／区分：N／チェック内容：Test Get Government Staff User Login Info When Entity Is Null
    * @throws BusinessException, SystemException 
    */
    
    @Test
    public void testGetGovernmentStaffUserLoginInfoWhenEntityIsNull() throws BusinessException, SystemException {
        //prepare data
        Mockito.when(tbl120Dao.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(null);
        
        //execute
        GovernmentStaffInfoVo governmentStaffInfoVo = userLogicImpl.getGovernmentStaffUserLoginInfo(USER_ID);
        
        //assert Result
        assertThat(governmentStaffInfoVo.getUserId()).isEqualTo(null);
    }
    
    /**
         *  案件ID：GDA0110／チェックリストID：UT- GDA0110-020／区分：N／チェック内容：Test Get Government Staff User Login Info When Success
    * 
    * @throws BusinessException
    */
    
    @Test(expected = BusinessException.class)
    public void testgetGovernmentStaffUserLoginInfoWhenUserIdIsNull() throws BusinessException, SystemException {
    
    //execute
    GovernmentStaffInfoVo governmentStaffInfoVo = userLogicImpl.getGovernmentStaffUserLoginInfo(CommonConstants.BLANK);
    
    }
    
    @Test(expected = BusinessException.class)
    public void testGetGovernmentStaffUserLoginInfoWhenException() throws BusinessException, SystemException {
        
        //prepare data
        TBL120Entity entity = generateEntity();
        GovernmentStaffInfoVo governmentStaffInfoVo = new GovernmentStaffInfoVo();

        //execute
        governmentStaffInfoVo = userLogicImpl.getGovernmentStaffUserLoginInfo(null);
        
    }
}
