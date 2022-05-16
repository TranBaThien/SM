/*
 * @(#) ProgressRecordBatchRegistrationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.SystemException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordForm;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class ProgressRecordBatchRegistrationLogicImplTest {

    @InjectMocks
    private ProgressRecordBatchRegistrationLogicImpl progressRecordBatchRegistrationLogicImpl;

    @Mock
    private SequenceUtil sequenceUtil;

    @Mock
    TBL100DAO tbl100DAO;

    @Mock
    TBL300DAO tbl300DAO;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    private final String CITY_ONETIME_PASSWORD_PERIOD = "30";
    private final String CITY_LOGIN_URL = "http://localhost:8080/GAA0110";

    private final String PR_APARTMENT_ID = "1000000001";
    private final String PR_CORRESPOND_DATE = "20191225";
    private final String PR_CORRESPOND_TIME = "1117";
    private final String PR_CORRESPOND_TYPE = "電話";
    private final String PR_NOTICE_TYPE = "管理状況届出書";
    private final String PR_PROGRESS_RECORD_OVERVIEW = "Hoang Test Nhe Phat";
    private final String PR_PROGRESS_RECORD_DETAIL = "Hoang Test Nhe Phat Nua";

    /* Create TBL120Entity */
    private final String USER_ID_TBL120 = "10000001";
    private final String LOGIN_ID_TBL120 = "G0000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME_TBL120 = LocalDateTime.now();
    private final String BIGINING_PASSWORD_CHANGE_FLAG_TBL120 = "0";
    private final String CITY_CODE_TBL120 = "111111";
    private final String DELETE_FLAG_TBL120 = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME_TBL120 = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT_TBL120 = 0;
    private final String MAIL_ADDRESS_TBL120 = "abc@gmail.com";
    private final String PASSWORD_TBL120 = "password_hash";
    private final LocalDateTime PASSWORD_PERIOD_TBL120 = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String STOP_REASON_TBL120 = "stop reason";
    private final LocalDateTime STOP_TIME_TBL120 = LocalDateTime.now();
    private final String TEL_NO_TBL120 = "09999999";
    private final LocalDateTime UPDATE_DATETIME_TBL120 = LocalDateTime.now();
    private final String UPDATE_USER_ID_TBL120 = "G0000001";
    private final String USER_NAME_TBL120 = "username";
    private final String USER_NAME_PHONETIC_TBL120 = "username phonetic";
    private final String USER_TYPE_TBL120 = "2";

    private TBL120Entity generateEntityTbl120(String accountLockFlag, String accountAvailableFlag,
            String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID_TBL120);
        entity.setLoginId(LOGIN_ID_TBL120);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.CITY.getCode()));
        entity.setPassword(PASSWORD_TBL120);
        entity.setUserType(USER_TYPE_TBL120);
        entity.setUserName(USER_NAME_TBL120);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC_TBL120);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME_TBL120);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT_TBL120);
        entity.setCityCode(CITY_CODE_TBL120);
        entity.setDeleteFlag(DELETE_FLAG_TBL120);
        entity.setPasswordPeriod(PASSWORD_PERIOD_TBL120);
        entity.setMailAddress(MAIL_ADDRESS_TBL120);
        entity.setTelNo(TEL_NO_TBL120);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG_TBL120);
        entity.setStopReason(STOP_REASON_TBL120);
        entity.setStopTime(STOP_TIME_TBL120);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME_TBL120);
        entity.setUpdateUserId(UPDATE_USER_ID_TBL120);
        entity.setUpdateDatetime(UPDATE_DATETIME_TBL120);
        return entity;
    }
    /* end create tbl120Entity */

    /* create TBL300Entity */
    private static final String PROGRESS_RECORD_NO = "1000000451";
    private static final String APARTMENT_ID = "1000000001";
    private static final String CORRESPOND_DATE = "201912251117";
    private static final String TYPE_CODE = "";
    private static final String CORRESPOND_TYPE_CODE = "01";
    private static final String NOTICE_TYPE_CODE = "1";
    private static final String RELATED_NUMBER = "";
    private static final String PROGRESS_RECORD_OVERVIEW = "Hoang Test Nhe Phat";
    private static final String PROGRESS_RECORD_DETAIL = "Hoang Test Nhe Phat Nua";
    private static final String SUPPORT_CODE = "";
    private static final String NOTIFICATION_METHOD_CODE = "";
    private static final String DELETE_FLAG = "0";
    private static final String UPDATE_USER_ID = "1000000030";
    private static final Timestamp UPDATE_DATETIME = null;
    /* End TBL300Entity */
    
    private List<String> lengthOfRowList() {
        List<String> lst = new ArrayList<String>();
        lst.add("7");
        return lst;
    }
    
    private List<String> lengthOfRowListFalse() {
        List<String> lst = new ArrayList<String>();
        lst.add("10");
        return lst;
    }

    /**
     * before
     */
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession();
        session.setAttribute(CommonConstants.SYSTEM_SETTING, generateSettingMap());
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    private Map<String, String> generateSettingMap() {
        return generateTBM004EntityList().stream()
                .collect(Collectors.toMap(TBM004Entity::getSetTargetNameEng, TBM004Entity::getSetPoint));
    }

    private List<TBM004Entity> generateTBM004EntityList() {
        List<TBM004Entity> settingList = new ArrayList<TBM004Entity>();
        settingList.add(
                generateTBM004Entity("1", CommonConstants.CITY_ONETIME_PASSWORD_PERIOD, CITY_ONETIME_PASSWORD_PERIOD));
        settingList.add(generateTBM004Entity("2", CommonConstants.CITY_LOGIN_URL, CITY_LOGIN_URL));
        return settingList;
    }

    private TBM004Entity generateTBM004Entity(String setNo, String setTargetNameEng, String setPoint) {
        TBM004Entity entity = new TBM004Entity();
        entity.setSetNo(setNo);
        entity.setSetTargetNameEng(setTargetNameEng);
        entity.setSetPoint(setPoint);
        return entity;
    }

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, true);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

    /**
     * create List Progress Record Form True
     * 
     * @return listProgressRecordFormTrue
     */
    private List<ProgressRecordForm> listProgressRecordFormTrue() {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        ProgressRecordForm progress = new ProgressRecordForm();

        progress.setApartmentId(PR_APARTMENT_ID);
        progress.setCorrespondDate(PR_CORRESPOND_DATE);
        progress.setCorrespondTime(PR_CORRESPOND_TIME);
        progress.setCorrespondType(PR_CORRESPOND_TYPE);
        progress.setNoticeType(PR_NOTICE_TYPE);
        progress.setProgressRecordOverview(PR_PROGRESS_RECORD_OVERVIEW);
        progress.setProgressRecordDetail(PR_PROGRESS_RECORD_DETAIL);

        listProgress.add(progress);
        return listProgress;
    }
    
    /**
     * create List Progress Record Form True But ApartmentId Not Exist In TBL100
     * 
     * @return listProgressRecordFormTrueButApartmentIdNotExistInTBL100
     * @throws ex SystemException
     */
    private List<ProgressRecordForm> listProgressRecordFormTrueButApartmentIdNotExistInTBL100() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setApartmentId("1212121212");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form Null
     * 
     * @return listProgressRecordFormNull
     */
    private List<ProgressRecordForm> listProgressRecordFormNull() {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        ProgressRecordForm progress = new ProgressRecordForm();

        progress.setApartmentId(null);
        progress.setCorrespondDate(null);
        progress.setCorrespondTime(null);
        progress.setCorrespondType(null);
        progress.setNoticeType(null);
        progress.setProgressRecordOverview(null);
        progress.setProgressRecordDetail(null);

        listProgress.add(progress);
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is Not Alpha Numeric
     * 
     * @return listProgressRecordFormIsNotAlphaNumeric
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsNotAlphaNumeric() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setApartmentId("マンション");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is Not Half Size
     * 
     * @return listProgressRecordFormIsNotHalfSize
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsNotHalfSize() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setCorrespondDate("マンション");
        listProgress.get(0).setCorrespondTime("マンション");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is Not True Day
     * 
     * @return listProgressRecordFormIsNotTrueDay
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsNotTrueDay() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setCorrespondDate("99999999");
        listProgress.get(0).setCorrespondTime("9999");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is Not Full Size
     * 
     * @return listProgressRecordFormIsNotFullSize
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsNotFullSize() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setCorrespondType("alphanumeric");
        listProgress.get(0).setNoticeType("alphanumeric");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is special character
     * 
     * @return listProgressRecordFormIsSpecialCharacter
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsSpecialCharacter() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setProgressRecordOverview("&");
        listProgress.get(0).setProgressRecordDetail("&");
        return listProgress;
    }
    
    /**
     * create List Progress Record Form is exceed max length
     * 
     * @return listProgressRecordFormIsExceedMaxLength
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormIsExceedMaxLength() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        ProgressRecordForm progress = new ProgressRecordForm();
        progress.setApartmentId("10000000181");
        progress.setCorrespondDate("199712191");
        progress.setCorrespondTime("19121");
        progress.setCorrespondType("都支援対象変更都支援対象変更都支援対象変更");
        progress.setNoticeType("ＡＢＣＤＥＡＢＣＤＥＡＢＣＤＥ");
        progress.setProgressRecordOverview("123123123123123123123123123123123123");
        progress.setProgressRecordDetail("1234567890123456789012345678901234567890123456789012345678901234567890"
                                        + "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123"
                                        + "456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456"
                                        + "789012345678901234567890123456789012345678901");
        listProgress.add(progress);
        return listProgress;
    }

    private TBL300Entity newTBL300Entity() {
        TBL300Entity entity = new TBL300Entity();

        entity.setApartmentId(APARTMENT_ID);
        entity.setCorrespondDate(CORRESPOND_DATE);
        entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setNoticeTypeCode(NOTICE_TYPE_CODE);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL);
        entity.setProgressRecordNo(PROGRESS_RECORD_NO);
        entity.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW);
        entity.setRelatedNumber(RELATED_NUMBER);
        entity.setSupportCode(SUPPORT_CODE);
        entity.setTypeCode(TYPE_CODE);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);

        return entity;
    }
    
    /**
     * create List Progress Record Form have correspondType is 05
     * 
     * @return listProgressRecordFormHaveCorrespondTypeIs05AndNoticeTypeIsNotNull
     * @throws ex SystemException 
     */
    private List<ProgressRecordForm> listProgressRecordFormHaveCorrespondTypeIs05AndNoticeTypeIsNotNull() throws SystemException {
        List<ProgressRecordForm> listProgress = new ArrayList<ProgressRecordForm>();
        listProgress = CommonUtils.copyList(listProgressRecordFormTrue());
        listProgress.get(0).setCorrespondType("通知書郵送");
        listProgress.get(0).setNoticeType("通知書郵送");
        return listProgress;
    }
    
    /**
     * create apartmentId list
     * 
     * @return List<String> list Apartment Id
     */
    private List<String> listApartmentId() {
        List<String> apartmentIdList = new ArrayList<String>();
        apartmentIdList.add(APARTMENT_ID);
        return apartmentIdList;
    }

    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-001/区分:N/チェック内容:test get data from csv
     * 
     * @throws IOException ex
     */
    @Test
    public void testGetDataFormCsv() throws IOException {
        File file = ResourceUtils.getFile("classpath:text2.csv");
        InputStream stream =  new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", stream);

        // prepare list expected
        List<ProgressRecordForm> expected = new ArrayList<ProgressRecordForm>();

        ProgressRecordForm data1 = new ProgressRecordForm();
        data1.setApartmentId("1234112");
        data1.setCorrespondDate("マンション");
        data1.setCorrespondTime("フリガナ");
        data1.setCorrespondType("1311024");
        data1.setNoticeType("中央区");
        data1.setProgressRecordOverview("東京都墨田区");
        data1.setProgressRecordDetail("任意");

        expected.add(data1);

        // get result
        List<ProgressRecordForm> result = progressRecordBatchRegistrationLogicImpl.getDataFormCsv(multipartFile.getBytes(), lengthOfRowList());

        // compare result with expected
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(expected.size() == result.size()).isTrue();

        int i = 0;
        for (ProgressRecordForm data : result) {
            assertThat(data.getApartmentId()).isEqualTo(expected.get(i).getApartmentId());
            assertThat(data.getCorrespondDate()).isEqualTo(expected.get(i).getCorrespondDate());
            assertThat(data.getCorrespondTime()).isEqualTo(expected.get(i).getCorrespondTime());
            assertThat(data.getCorrespondType()).isEqualTo(expected.get(i).getCorrespondType());
            assertThat(data.getNoticeType()).isEqualTo(expected.get(i).getNoticeType());
            assertThat(data.getProgressRecordOverview()).isEqualTo(expected.get(i).getProgressRecordOverview());
            assertThat(data.getProgressRecordDetail()).isEqualTo(expected.get(i).getProgressRecordDetail());
            i++;
        }
    }

    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-002/区分:E/チェック内容:test upload csv when file count max is false
     * 
     * @throws SystemException ex
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndLengthOfRowListIsFalse() {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        List<ZAA0150ErrorVo> lstVo = progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormTrue(), "0", lengthOfRowListFalse());
//        ZAA0150ErrorVo vo = new ZAA0150ErrorVo("1", "", "E0120", MessageUtil.getMessage(CommonConstants.MS_E0120, "0"));
//        assertThat(lstVo.get(0).getRowNum()).isEqualTo(vo.getRowNum());
//        assertThat(lstVo.get(0).getApartmentName()).isEqualTo(vo.getApartmentName());
//        assertThat(lstVo.get(0).getMessageId()).isEqualTo(vo.getMessageId());
//        assertThat(lstVo.get(0).getMessageContent()).isEqualTo(vo.getMessageContent());
    }

    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-003/区分:N/チェック内容:test upload csv when file count max and all field in csv is true
     * 
     * @throws SystemException ex
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsTrue() {
        
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        
        String cityCode = "111111";
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);
        
        TBL300Entity tbl300Entity = newTBL300Entity();
        Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(tbl300Entity);
        
        String progressRecordNo = "1000000145";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_LOGIN_ID,
                UserTypeCode.NONE)).thenReturn(progressRecordNo);
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);

        List<ZAA0150ErrorVo> vo = progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormTrue(), "1000", lengthOfRowList());
        assertThat(vo).isNotNull();
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-004/区分:E/チェック内容:test upload CSV when file count max and all field in csv true but apartmentId not exist in TBL100
     * 
     * @throws SystemException ex
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldIsTrueButApartmentIdNotExistInTbl100() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormTrueButApartmentIdNotExistInTBL100(),
                "1000", lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-005/区分:E/チェック内容:test upload csv when file count max, all field is true and User Type Code is City But CITY_CODE of User and Apartment NOT MATCH
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldIsTrueAndUserTypeCodeIsCityButCityCodeOfUserAndApartmentNotMatch()
            throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "222222";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.anyString())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormTrue(), "1000", lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-006/区分:E/チェック内容:test upload csv when file count max is true and all field is null (required test)
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsNull() {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormNull(), "1000", lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-007/区分:E/チェック内容:test upload csv when file count max is true and all field is not Alpha Numeric
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsNotAlphaNumeric() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsNotAlphaNumeric(), "1000",
                lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-008/区分:E/チェック内容:test upload csv when file count max is true and all field is not half size
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsNotHalfSize() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsNotHalfSize(), "1000",
                lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-009/区分:E/チェック内容:test upload csv when file count max is true and some field is not Date
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndSomeFieldInCsvNotTrueDate() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsNotTrueDay(), "1000",
                lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-010/区分:E/チェック内容:test upload csv when file count max is true and some field is not Date
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndSomeFieldInCsvNotFullSize() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsNotFullSize(), "1000",
                lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-011/区分:E/チェック内容:test upload csv when file count max is true and some field is special character
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndSomeFieldInCsvIsSpecialCharacter() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsSpecialCharacter(), "1000",
                lengthOfRowList());
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-012/区分:E/チェック内容:test upload csv when file count max is true and some field is special character
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsExceedMaxLength() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(listProgressRecordFormIsExceedMaxLength(), "1000",
                lengthOfRowList());
    }

    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-013/区分:N/チェック内容:test upload csv when file count max is true and correspondType is 05
     * 
     * @throws ex SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndCorrespondTypeIs05AndNoticeTypeIsNotNull() throws SystemException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        String cityCode = "111111";
        List<String> apartmentIdList = listApartmentId();
        Mockito.when(tbl100DAO.getListApartmentId()).thenReturn(apartmentIdList);
        Mockito.when(tbl100DAO.getCityCodeByApartmentId(Mockito.any())).thenReturn(cityCode);

        progressRecordBatchRegistrationLogicImpl.uploadCSV(
                listProgressRecordFormHaveCorrespondTypeIs05AndNoticeTypeIsNotNull(), "1000", lengthOfRowList());
    }
}
