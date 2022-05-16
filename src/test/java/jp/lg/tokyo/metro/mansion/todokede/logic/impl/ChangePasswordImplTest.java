/*
 * @(#) ManagementAssociationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: Dec 31, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ChangePasswordForm;

@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class ChangePasswordImplTest {

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    private HttpSession session;
    
    @Mock
    SequenceUtil sequenceUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    TBL110DAO tbl110DAO;

    @Mock
    TBL120DAO tbl120DAO;

    @Mock
    TBL100DAO tbl100DAO;

    @Mock
    TBL105DAO tbl105DAO;

    @InjectMocks
    private ChangePasswordLogicImpl changePasswordImpl;

    private final String ST_M_ACCOUNT_LOCK_PERIOD = "60";
    private final String ST_G_PASSWORD_VALID_PERIOD = "60";
    private final String APARTMENT_ID = "1000000002";
    private final String ADDRESS = "tokyo";
    private final String APARTMENT_LOST_FLAG = "1";
    private final String APARTMENT_NAME = "abc";
    private final String APARTMENT_NAME_PHONETIC = "abc phonetic";
    private final String BUILD_DAY = "10";
    private final String BUILD_MONTH = "02";
    private final String BUILD_YEAR = "2020";
    private final double BUILDING_AREA = 00000000001;
    private final String BUILDING_STRUCTURE = "0101010101";
    private final String CITY_CODE = "202020";
    private final String CITY_NAME = "kyoto";
    private final String DELETE_FLAG = "0";
    private final int FLOOR_NUMBER = 111;
    private final int HOUSE_NUMBER = 222;
    private final String MAIL_ADDRESS = "abc@gmail.com";
    private final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
    private final String NEWEST_NOTIFICATION_NAME = "osaka";
    private final String NEWEST_NOTIFICATION_NO = "newest kyoto";
    private final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.now();
    private final String NOTIFICATION_KBN = "1";
    private final String PRELIMINARY1 = "0000001";
    private final String PRELIMINARY2 = "0000002";
    private final String PRELIMINARY3 = "0000003";
    private final String PRELIMINARY4 = "0000004";
    private final String PRELIMINARY5 = "0000005";
    private final String PROPERTY_NUMBER = "00000008";
    private final String REAL_ESTATE_NO = "1111111111113";
    private final String REGISTRATION_ADDRESS = "kyoto";
    private final String REGISTRATION_HOUSE_NO = "osaka";
    private final String REGISTRATION_NO = "00000008";
    private final String REPASSWORD_MAIL_ADDRESS = "abcd@gmail.com";
    private final double SITE_AREA = 10101010;
    private final String SUPPORT = "1";
    private final double TOTAL_FLOOR_AREA = 11111;
    private final String ZIP_CODE = "12345678";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String PASSWORD_NOW = "123456789";
    private final String PWD_PASSWORD = "123456789";
    private final String PWD_PASSWORD_CONFIRM = "123456789";
    private final String TXT_MAIL = "text@text.com";
    private final String TXT_MAIL_CONFIRM = "text@text.com";
    private final String USER_ID = "1000000013";
    private final String LOGIN_ID = "G00000013";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String PASSWORD = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.MINUTES);
    private final String STOP_REASON = "stop reason";
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
    private final String UPDATE_USER_ID = "G00000011";
    private final String USER_NAME = "username 11";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String VALIDITY_FLAG = "1";
    private final LocalDateTime UPDATE_DATETIME120 = LocalDateTime.now();

    /**
     * 
     */
    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD, ST_M_ACCOUNT_LOCK_PERIOD);
        systemSettings.put(CommonConstants.ST_SESSION_TIMEOUT_PERIOD, ST_G_PASSWORD_VALID_PERIOD);
        systemSettings.put(CommonConstants.ST_M_PASSWORD_VALID_PERIOD, ST_M_ACCOUNT_LOCK_PERIOD);
        systemSettings.put(CommonConstants.ST_G_PASSWORD_VALID_PERIOD, ST_M_ACCOUNT_LOCK_PERIOD);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        Mockito.when(session.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
        Mockito.when(httpServletRequest.getSession()).thenReturn(session);
    }
    
    /**
     *  create table TBL120 
     */
    private TBL120Entity generateTBL120Entity() {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(ACCOUNT_AVAILABLE_FLAG);
        entity.setAvailability(LOGIN_STATUS_FLAG);
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
        entity.setUpdateDatetime(UPDATE_DATETIME120);
        return entity;
    }
    
    /**
     *  create table TBL110 
     */
    private TBL110Entity generateTBL110Entity(String apartmentId) {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(apartmentId);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(ACCOUNT_AVAILABLE_FLAG);
        entity.setAvailability(LOGIN_STATUS_FLAG);
        entity.setPassword(PASSWORD);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setStopTime(STOP_TIME);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setTbl100(generateTBL100Entity(apartmentId));
        entity.setValidityFlag(VALIDITY_FLAG);
        return entity;
    }
    
    /**
     *  create table TBL100 
     */
    private TBL100Entity generateTBL100Entity(String apartmentId) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(apartmentId);
        entity.setAddress(ADDRESS);
        entity.setApartmentLostFlag(APARTMENT_LOST_FLAG);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setBuildDay(BUILD_DAY);
        entity.setBuildMonth(BUILD_MONTH);
        entity.setBuildYear(BUILD_YEAR);
        entity.setBuildingArea(BUILDING_AREA);
        entity.setBuildingStructure(BUILDING_STRUCTURE);
        entity.setCityCode(CITY_CODE);
        entity.setCityName(CITY_NAME);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setFloorNumber(FLOOR_NUMBER);
        entity.setHouseNumber(HOUSE_NUMBER);
        entity.setMailAddress(MAIL_ADDRESS);
        entity.setNewestNotificationAddress(NEWEST_NOTIFICATION_ADDRESS);
        entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME);
        entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
        entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE);
        entity.setNotificationKbn(NOTIFICATION_KBN);
        entity.setPreliminary1(PRELIMINARY1);
        entity.setPreliminary2(PRELIMINARY2);
        entity.setPreliminary3(PRELIMINARY3);
        entity.setPreliminary4(PRELIMINARY4);
        entity.setPreliminary5(PRELIMINARY5);
        entity.setPropertyNumber(PROPERTY_NUMBER);
        entity.setRealEstateNo(REAL_ESTATE_NO);
        entity.setRegistrationAddress(REGISTRATION_ADDRESS);
        entity.setRegistrationHouseNo(REGISTRATION_HOUSE_NO);
        entity.setRegistrationNo(REGISTRATION_NO);
        entity.setRepasswordMailAddress(REPASSWORD_MAIL_ADDRESS);
        entity.setSiteArea(SITE_AREA);
        entity.setSupport(SUPPORT);
        entity.setTotalFloorArea(TOTAL_FLOOR_AREA);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setZipCode(ZIP_CODE);
        return entity;
    }
    
    /**
     * @return ChangePasswordVo
     */
    private ChangePasswordForm newChangePasswordForm() {
        ChangePasswordForm form = new ChangePasswordForm();
        form.setApartmentId(APARTMENT_ID);
        form.setPwdPassword(PWD_PASSWORD);
        form.setPwdPasswordNow(PASSWORD_NOW);
        form.setPwdPasswordConfirm(PWD_PASSWORD_CONFIRM);
        form.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        form.setTxtMail(TXT_MAIL);
        form.setTxtMailConfirm(TXT_MAIL_CONFIRM);
        form.setLoginId(LOGIN_ID);
        form.setUpdateUserId(USER_ID);
        return form;
    }

    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-001/区分:N/チェック内容:test Change Password Success With Municipalities
     * 
     **/
    @Test
    public void testChangePasswordSuccessWithMunicipalities() throws BusinessException {
        ChangePasswordForm form = newChangePasswordForm();
        TBL120Entity entity = generateTBL120Entity();
        Optional<TBL120Entity> returnEntity = Optional.of((TBL120Entity) entity);
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(returnEntity);
        changePasswordImpl.changerPasswordTBL120(form);
        assertThat(0).isEqualTo(entity.getLoginErrorCount());
        assertThat(form.getUpdateUserId()).isEqualTo(entity.getUserId());
        assertThat(entity).isNotNull();
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-002/区分:N/チェック内容:test Change Password Input Is Null WithMunicipalities
     * 
     **/
    @Test(expected = BusinessException.class)
    public void testChangePasswordInputIsNullWithMunicipalities() throws BusinessException {
        ChangePasswordForm form = null;
        TBL120Entity entity = generateTBL120Entity();
        Optional<TBL120Entity> returnEntity = Optional.of((TBL120Entity) entity);
        Mockito.when(tbl120DAO.findByLoginId(Mockito.anyString())).thenReturn(returnEntity);
        changePasswordImpl.changerPasswordTBL120(form);
        assertThat(entity).isNull();
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-003/区分:I/チェック内容:test Change Password Success With Manager Mansion
     * 
     **/
    @Test
    public void testChangePasswordSuccessWithManagerMansion() throws BusinessException {
        ChangePasswordForm form = newChangePasswordForm();
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        TBL100Entity entity2 = generateTBL100Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity2);
        changePasswordImpl.updateApartmentInfor(form);
        assertThat(entity).isNotNull();
        assertThat(entity.getApartmentId()).isEqualTo(entity2.getApartmentId());
        
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-005/区分:N/チェック内容:test Change Password Success With Manager Mansion
     * 
     **/
    @Test
    public void testChangePasswordSuccessWithManagerMansionByUserTypeCode() throws BusinessException {
        ChangePasswordForm form = newChangePasswordForm();
        form.setUserTypeCode(5);
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        TBL100Entity entity2 = generateTBL100Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity2);
        changePasswordImpl.updateApartmentInfor(form);
        assertThat(entity).isNotNull();
        assertThat(entity.getApartmentId()).isEqualTo(entity2.getApartmentId());
        
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-006/区分:N/チェック内容:test Change Password Success With Manager Mansion
     * 
     **/
    @Test
    public void testChangePasswordSuccessWithManagerMansionByBiginingPasswordChangeFlag() throws BusinessException {
        ChangePasswordForm form = newChangePasswordForm();
        form.setBiginingPasswordChangeFlag("1");
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        TBL100Entity entity2 = generateTBL100Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity2);
        changePasswordImpl.updateApartmentInfor(form);
        assertThat(entity).isNotNull();
        assertThat(entity.getApartmentId()).isEqualTo(entity2.getApartmentId());
        
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-007/区分:I/チェック内容:test Change Password Success With Manager Mansion
     * 
     **/
    @Test
    public void testChangePasswordSuccessWithManagerMansionByUserTypeCode1() throws BusinessException {
        ChangePasswordForm form = newChangePasswordForm();
        form.setUserTypeCode(0);
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        TBL100Entity entity2 = generateTBL100Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity2);
        changePasswordImpl.updateApartmentInfor(form);
        assertThat(entity).isNotNull();
        assertThat(entity.getApartmentId()).isEqualTo(entity2.getApartmentId());
        
    }
    
    /**
     * 案件ID:SBA0110/チェックリストID:UT- SBA0110-004/区分:N/チェック内容:test Change Password Input Is Null With Manager Mansion
     * 
     **/
    @Test(expected = BusinessException.class)
    public void testChangePasswordInputIsNullWithManagerMansion() throws BusinessException {
        ChangePasswordForm form = null;
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        TBL100Entity entity2 = generateTBL100Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity2);
        changePasswordImpl.updateApartmentInfor(form);
        assertThat(entity2).isNull();
        assertThat(entity).isNull();
    }
    
    /**
     * 
     */
    @Test
    public void testSaveRunMethod() {
        changePasswordImpl.save(new Object());
    }
    
    /**
     * 
     */
    @Test
    public void testDeleteRunMethod() {
        changePasswordImpl.delete(new Object());
        
    }

}
