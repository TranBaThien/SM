/*
 * @(#) SupervisedNoticeLogicTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2020/01/06
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM002DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SupervisedNoticeLogicForm;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.SupervisedNoticeLogicVo;

/**
 * @author lthai
 * @Screen GIA0110
 *
 */
@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class SupervisedNoticeLogicTest {
    @Mock
    private TBL100DAO tbl100DAO;

    @Mock
    private TBL200DAO tbl200DAO;

    @Mock
    private TBL240DAO tbl240DAO;

    @Mock
    private TBL300DAO tbl300DAO;

    @Mock
    private TBM001DAO tbm001DAO;

    @Mock
    private TBM002DAO tbm002DAO;

    @Mock
    private SequenceUtil sequenceUtil;

    @InjectMocks
    private SupervisedNoticeLogicImpl supervisedNoticeLogicImpl;
    private static final String TBM002_FIRST_TIME = "督促通知書(1回目用)";

    private static final String SEPARATOR = "\n";

    private static final String CITYCODE = "111111";
    /* Create TBL100Entity */
    private static final String APARTMENT_ID = "111";
    private static final String APARTMENT_NAME = "Test apartment name";
    private static final String ADDRESS = "address test";

    private final static String NOTIFICATION_NO = "111";
    /* Create TBM001Entity */
    private static final String CITYNAME = "Test City Name";
    private static final String CHIEFNAME = "Test chief Name";

    /* Create TBL120Entity */
    private static final String USER_ID_TBL120 = "10000001";
    private static final String LOGIN_ID_TBL120 = "G0000001";
    private static final LocalDateTime ACCOUNT_LOCK_TIME_TBL120 = LocalDateTime.now();
    private static final String BIGINING_PASSWORD_CHANGE_FLAG_TBL120 = "0";
    private static final String CITY_CODE_TBL120 = "111111";
    private static final String DELETE_FLAG_TBL120 = "0";
    private static final LocalDateTime LAST_TIME_LOGIN_TIME_TBL120 = LocalDateTime.now();
    private static final int LOGIN_ERROR_COUNT_TBL120 = 0;
    private static final String MAIL_ADDRESS_TBL120 = "abc@gmail.com";
    private static final String PASSWORD_TBL120 = "password_hash";
    private static final LocalDateTime PASSWORD_PERIOD_TBL120 = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private static final String STOP_REASON_TBL120 = "stop reason";
    private static final LocalDateTime STOP_TIME_TBL120 = LocalDateTime.now();
    private static final String TEL_NO_TBL120 = "09999999";
    private static final LocalDateTime UPDATE_DATETIME_TBL120 = LocalDateTime.now();
    private static final String UPDATE_USER_ID_TBL120 = "G0000001";
    private static final String USER_NAME_TBL120 = "username";
    private static final String USER_NAME_PHONETIC_TBL120 = "username phonetic";
    private static final String USER_TYPE_TBL120 = "2";
    private static final String SUPERVISEDNOTICE_NO = "10000001";
    
    /* Create SupervisedNoticeLogicVo */
    private static final String TXTDOCUMENTNO = "123456789";
    private static final String APPENDIXNO = "appendixNo";

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, true);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

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


    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-001／区分：E／チェック内容：Test Get Supervised Notice Data When Get Apartment Information Is Null
     * 
     * @throws BusinessException
     */
    @Test(expected = BusinessException.class)
    public void testGetSupervisedNoticeData_WhenGetApartmentInformationIsNull() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(null);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);
        supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
    }

    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-002／区分：N／チェック内容：Test Get Supervised Notice Data When Municipal Master Info
     * 
     * @throws BusinessException
     */
    @Test(expected = BusinessException.class)
    public void testGetSupervisedNoticeData_WhenMunicipalMasterInfo() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(null);
        supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
    }

    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-003／区分：N／チェック内容：Test Get Supervised Notice Data
     *  When TBL240DAO Is Null And TBM002Entity Is Not Null And TBL200Entity Is Null And NewestNotificationNo Is Null And UserType Is City
     * tbl240DAO is null
     * Session.UserType is City
     * NewestNotificationNo is Null
     * tbm002Entity is not null
     * tbl200Entity = null
     */
    @Test
    public void testGetSupervisedNoticeData_WhenTBL240DAOIsNull_AndTBM002EntityIsNotNull_AndTBL200EntityIsNull_AndNewestNotificationNoIsNull_AndUserTypeIsCity() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITYCODE);
        entity.setAddress(ADDRESS);
        entity.setNewestNotificationNo(null);
        
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        tbm001Entity.setCityName(CITYNAME);
        tbm001Entity.setChiefName(CHIEFNAME);

        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "区市町村"));
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);

        TBM002Entity tbm002Entity = new TBM002Entity();
        tbm002Entity.setWindowTelNo("TEL_123456789");
        tbm002Entity.setWindowMailAddress("window1@gmail.com");
        tbm002Entity.setWindowBelong("問合せ窓口_部署（区市町村向け）");
        TBM002EntityPK pk = new TBM002EntityPK();
        pk.setCityCode("111111");
        pk.setUsedCode("06");
        tbm002Entity.setId(pk);
        tbm002Entity.setWindowFaxNo("FAX_987654321");
        tbm002Entity.setDeleteFlag("0");
        TBL200Entity tbl200Entity = null;

        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(APARTMENT_ID)).thenReturn(null);
        Mockito.when(tbm002DAO.getWindowInformation(CITYCODE, CodeUtil.getValue(CommonConstants.CD046, TBM002_FIRST_TIME))).thenReturn(tbm002Entity);
        Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO)).thenReturn(tbl200Entity);
        SupervisedNoticeLogicVo vo = supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
        /* 様式名 */
        assertThat(vo.getTxtAppendixNo()).isEqualTo("第　　号様式(第　　関係）");
        /* 文書番号 */
        assertThat(vo.getTxtDocumentNo()).isEqualTo( DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + tbm001Entity.getDocumentNo() + "　第　号");
        /* 督促通知書種別 */
        //1回目用（CD022）
        assertThat(vo.getLblSupervisedNoticeTypeCode()).isEqualTo("1回目用");
        /* 通知書宛先 */
        assertThat(vo.getRdoMailingCode()).isEqualTo(CommonConstants.BLANK);
        /* 宛先(マンション名) */
        assertThat(vo.getTxtRecipientNameApartment()).isEqualTo(entity.getApartmentName());
        /* 宛先(氏名等) */
        assertThat(vo.getTxtRecipientNameUser()).isEqualTo(CommonConstants.BLANK);
        /* 発信者名 */
        assertThat(vo.getTxtSender()).isEqualTo(tbm001Entity.getCityName() + "長　" + tbm001Entity.getChiefName());
        /* 本文中宛先1 */
        assertThat(vo.getTxtTextAdress1()).isEqualTo(CommonConstants.BLANK);
        /* 根拠条文1 */
        assertThat(vo.getLblEvidenceBar()).isEqualTo("15");
        /* 根拠条文2 */
        assertThat(vo.getLstEvidenceNo()).isEqualTo(CommonConstants.BLANK);
        /* 期限に関する規定 */
        assertThat(vo.getLblPeriodEvidence()).isEqualTo(CommonConstants.BLANK);
        /* 前回通知年月日 */
        assertThat(vo.getLblLastNoticeDate()).isEqualTo(null);
        /* 前回文書番号 */
        assertThat(vo.getLblLastDocumentNo()).isEqualTo(vo.getTxtDocumentNo());
        /* 本文中宛先2 */
        assertThat(vo.getTxtTextAdress2()).isEqualTo(CommonConstants.BLANK);
        /* 所在地 */
        assertThat(vo.getLblAddress()).isEqualTo("東京都" + tbm001Entity.getCityName() + entity.getAddress());
        /* マンション名 */
        assertThat(vo.getLblApartmentName()).isEqualTo(entity.getApartmentName());
        /* 届出様式名 */
        assertThat(vo.getLblNotificationFormatName()).isEqualTo(CommonConstants.BLANK);
        /* 提出期限 */
        assertThat(vo.getCalNotificationTimelimit()).isEqualTo("2020/09/30");
        /* 担当・連絡先 */
        StringBuffer contactString = new StringBuffer();
        contactString.append(tbm002Entity.getWindowBelong());
        contactString.append(SEPARATOR);
        contactString.append(tbm002Entity.getWindowMailAddress());
        contactString.append(SEPARATOR);
        contactString.append(tbm002Entity.getWindowTelNo());
        contactString.append(SEPARATOR);
        contactString.append(tbm002Entity.getWindowFaxNo());
        assertThat(vo.getTxaContact()).isEqualTo(contactString+"");
    }

    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-004／区分：N／チェック内容：Test Get Supervised Notice Data When Get Supervised Notice Not Null And Notification Information Is Null
     * tbl240DAO is null
     * Session.UserType is not City
     * NewestNotificationNo is not Null
     * tbm002Entity is null
     */
    @Test
    public void testGetSupervisedNoticeData_WhenTBL240IsNull_AndTBM002IsNull_AndNewestNotificationNoIsNotNull_AndUserTypeIsNotCity() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITYCODE);
        entity.setAddress(ADDRESS);
        entity.setNewestNotificationNo(NOTIFICATION_NO);
        
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        tbm001Entity.setCityName(CITYNAME);
        tbm001Entity.setChiefName(CHIEFNAME);
        
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "都職員"));
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);

        TBM002Entity tbm002Entity = null;
        TBL200Entity tbl200Entity = null;

        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(APARTMENT_ID)).thenReturn(null);
        Mockito.when(tbm002DAO.getWindowInformation(CITYCODE, CodeUtil.getValue(CommonConstants.CD046, TBM002_FIRST_TIME))).thenReturn(tbm002Entity);
        Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO)).thenReturn(tbl200Entity);
        SupervisedNoticeLogicVo vo = supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
        /* 様式名 */
        assertThat(vo.getTxtAppendixNo()).isEqualTo("第　　号様式(第　　関係）");
        /* 文書番号 */
        assertThat(vo.getTxtDocumentNo()).isEqualTo( DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + tbm001Entity.getDocumentNo() + "　第　号");
        /* 督促通知書種別 */
        //1回目用（CD022）
        assertThat(vo.getLblSupervisedNoticeTypeCode()).isEqualTo("1回目用");
        /* 通知書宛先 */
        assertThat(vo.getRdoMailingCode()).isEqualTo(CommonConstants.BLANK);
        /* 宛先(マンション名) */
        assertThat(vo.getTxtRecipientNameApartment()).isEqualTo(entity.getApartmentName());
        /* 宛先(氏名等) */
        assertThat(vo.getTxtRecipientNameUser()).isEqualTo(CommonConstants.BLANK);
        /* 発信者名 */
        assertThat(vo.getTxtSender()).isEqualTo("東京都知事　" + tbm001Entity.getChiefName());
        /* 本文中宛先1 */
        assertThat(vo.getTxtTextAdress1()).isEqualTo(CommonConstants.BLANK);
        /* 根拠条文1 */
        assertThat(vo.getLblEvidenceBar()).isEqualTo("16");
        /* 根拠条文2 */
        assertThat(vo.getLstEvidenceNo()).isEqualTo(CommonConstants.BLANK);
        /* 期限に関する規定 */
        assertThat(vo.getLblPeriodEvidence()).isEqualTo(CommonConstants.BLANK);
        /* 前回通知年月日 */
        assertThat(vo.getLblLastNoticeDate()).isEqualTo(null);
        /* 前回文書番号 */
        assertThat(vo.getLblLastDocumentNo()).isEqualTo(vo.getTxtDocumentNo());
        /* 本文中宛先2 */
        assertThat(vo.getTxtTextAdress2()).isEqualTo(CommonConstants.BLANK);
        /* 所在地 */
        assertThat(vo.getLblAddress()).isEqualTo("東京都" + tbm001Entity.getCityName() + entity.getAddress());
        /* マンション名 */
        assertThat(vo.getLblApartmentName()).isEqualTo(entity.getApartmentName());
        /* 届出様式名 */
        assertThat(vo.getLblNotificationFormatName()).isEqualTo(CommonConstants.BLANK);
        /* 提出期限 */
        assertThat(vo.getCalNotificationTimelimit()).isEqualTo("2020/09/30");
        /* 担当・連絡先 */
        assertThat(vo.getTxaContact()).isEqualTo(CommonConstants.BLANK);
    }

    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-005／区分：N／チェック内容：Test Get Supervised Notice Data When 
     * TBL240Entity Is Not Null And TBM002 Is Null And Newest NotificationNo Is Not Null And UserType Is Not City
     * tbl240DAO is not null
     * Session.UserType is not City
     * NewestNotificationNo is not Null
     * tbm002Entity is null
     * EvidenceBar is 1
     */
    @Test
    public void testGetSupervisedNoticeData_WhenTBL240IsNotNull_AndTBM002IsNull_AndNewestNotificationNoIsNotNull_AndUserTypeIsNotCity() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITYCODE);
        entity.setAddress(ADDRESS);
        entity.setNewestNotificationNo(NOTIFICATION_NO);
        
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        tbm001Entity.setCityName(CITYNAME);
        tbm001Entity.setChiefName(CHIEFNAME);
        
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "都職員"));
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);

        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setAppendixNo(APPENDIXNO);
        tbl240Entity.setMailingCode("4");
        tbl240Entity.setEvidenceBar("1");
        tbl240Entity.setRecipientNameApartment("Tokyo");
        tbl240Entity.setRecipientNameUser("管理組合理事長");
        tbl240Entity.setTextaddress1("東京都");
        tbl240Entity.setTextaddress2("文京区");
        tbl240Entity.setNotificationFormatName("2");
        tbl240Entity.setPeriodEvidence("5");
        tbl240Entity.setNoticeDate("20200930");
        TBM002Entity tbm002Entity = null;
        TBL200Entity tbl200Entity = null;

        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(APARTMENT_ID)).thenReturn(tbl240Entity);
        Mockito.when(tbm002DAO.getWindowInformation(CITYCODE, CodeUtil.getValue(CommonConstants.CD046, TBM002_FIRST_TIME))).thenReturn(tbm002Entity);
        Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO)).thenReturn(tbl200Entity);
        SupervisedNoticeLogicVo vo = supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
        /* 様式名 */
        assertThat(vo.getTxtAppendixNo()).isEqualTo(tbl240Entity.getAppendixNo());
        /* 文書番号 */
        assertThat(vo.getTxtDocumentNo()).isEqualTo( DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + tbm001Entity.getDocumentNo() + "　第　号");
        /* 督促通知書種別 */
        //1回目用（CD022）
        assertThat(vo.getLblSupervisedNoticeTypeCode()).isEqualTo("2回目以降用");
        /* 通知書宛先 */
        assertThat(vo.getRdoMailingCode()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD018, tbl240Entity.getMailingCode()));
        /* 宛先(マンション名) */
        assertThat(vo.getTxtRecipientNameApartment()).isEqualTo(tbl240Entity.getRecipientNameApartment());
        /* 宛先(氏名等) */
        assertThat(vo.getTxtRecipientNameUser()).isEqualTo(tbl240Entity.getRecipientNameUser());
        /* 発信者名 */
        assertThat(vo.getTxtSender()).isEqualTo("東京都知事　" + tbm001Entity.getChiefName());
        /* 本文中宛先1 */
        assertThat(vo.getTxtTextAdress1()).isEqualTo(tbl240Entity.getTextaddress1());
        /* 根拠条文1 */
        assertThat(vo.getLblEvidenceBar()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD041 ,tbl240Entity.getEvidenceBar()));
        /* 根拠条文2 */
        assertThat(vo.getLstEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD044 ,tbl240Entity.getEvidenceNo()));
        /* 期限に関する規定 */
        assertThat(vo.getLblPeriodEvidence()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD045 ,tbl240Entity.getPeriodEvidence()));
        /* 前回通知年月日 */
        assertThat(vo.getLblLastNoticeDate()).isEqualTo("2020/09/30");
        /* 前回文書番号 */
        assertThat(vo.getLblLastDocumentNo()).isEqualTo(vo.getTxtDocumentNo());
        /* 本文中宛先2 */
        assertThat(vo.getTxtTextAdress2()).isEqualTo(tbl240Entity.getTextaddress2());
        /* 所在地 */
        assertThat(vo.getLblAddress()).isEqualTo("東京都" + tbm001Entity.getCityName() + entity.getAddress());
        /* マンション名 */
        assertThat(vo.getLblApartmentName()).isEqualTo(entity.getApartmentName());
        /* 届出様式名 */
        assertThat(vo.getLblNotificationFormatName()).isEqualTo("2");
        /* 提出期限 */
        assertThat(vo.getCalNotificationTimelimit()).isEqualTo("2020/09/30");
        /* 担当・連絡先 */
        assertThat(vo.getTxaContact()).isEqualTo(null);
    }

    /**
     * 案件GIA0110／チェックリストID：UT- GIA0110-006／区分：N／チェック内容：Test Get Supervised Notice Data When 
     * TBL240Entity Is Not Null And TBM002 Is Not Null And Newest NotificationNo Is Null And UserType Is City
     * tbl240DAO is not null
     * Session.UserType is City
     * NewestNotificationNo is Null
     * tbm002Entity is not null
     * tbl200Entity is not null
     * EvidenceBar is 2
     */
    @Test
    public void testGetSupervisedNoticeData_WhenTBL240IsNotNull_AndTBM002IsNull_AndNewestNotificationNoIsNull_AndUserTypeIsCity() throws BusinessException {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setCityCode(CITYCODE);
        entity.setAddress(ADDRESS);
        entity.setNewestNotificationNo(NOTIFICATION_NO);
        
        TBM001Entity tbm001Entity = new TBM001Entity();
        tbm001Entity.setDocumentNo(TXTDOCUMENTNO);
        tbm001Entity.setCityName(CITYNAME);
        tbm001Entity.setChiefName(CHIEFNAME);
        
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        TBL120Entity tbl120entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        tbl120entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "区市町村"));
        prepareSecurityContextHolder(tbl120entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.any())).thenReturn(tbm001Entity);

        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setAppendixNo(APPENDIXNO);
        tbl240Entity.setMailingCode("4");
        tbl240Entity.setEvidenceBar("2");
        tbl240Entity.setRecipientNameApartment("Tokyo");
        tbl240Entity.setRecipientNameUser("管理組合理事長");
        tbl240Entity.setTextaddress1("東京都");
        tbl240Entity.setTextaddress2("文京区");
        tbl240Entity.setNotificationFormatName("2");
        tbl240Entity.setPeriodEvidence("5");
        tbl240Entity.setNoticeDate("20200930");
        tbl240Entity.setContact("されます。");
        TBM002Entity tbm002Entity = new TBM002Entity();
        tbm002Entity.setWindowTelNo("TEL_123456789");
        tbm002Entity.setWindowMailAddress("window1@gmail.com");
        tbm002Entity.setWindowBelong("問合せ窓口_部署（区市町村向け）");
        TBM002EntityPK pk = new TBM002EntityPK();
        pk.setCityCode("111111");
        pk.setUsedCode("06");
        tbm002Entity.setId(pk);
        tbm002Entity.setWindowFaxNo("FAX_987654321");
        tbm002Entity.setDeleteFlag("0");
        TBL200Entity tbl200Entity = new TBL200Entity();
        tbl200Entity.setNotificationPersonName("Name");

        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(APARTMENT_ID)).thenReturn(tbl240Entity);
        Mockito.when(tbm002DAO.getWindowInformation(CITYCODE, CodeUtil.getValue(CommonConstants.CD046, TBM002_FIRST_TIME))).thenReturn(tbm002Entity);
        Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO)).thenReturn(tbl200Entity);
        SupervisedNoticeLogicVo vo = supervisedNoticeLogicImpl.getSupervisedNoticeData(APARTMENT_ID);
        /* 様式名 */
        assertThat(vo.getTxtAppendixNo()).isEqualTo(tbl240Entity.getAppendixNo());
        /* 文書番号 */
        assertThat(vo.getTxtDocumentNo()).isEqualTo( DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + tbm001Entity.getDocumentNo() + "　第　号");
        /* 督促通知書種別 */
        //1回目用（CD022）
        assertThat(vo.getLblSupervisedNoticeTypeCode()).isEqualTo("2回目以降用");
        /* 通知書宛先 */
        assertThat(vo.getRdoMailingCode()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD018, tbl240Entity.getMailingCode()));
        /* 宛先(マンション名) */
        assertThat(vo.getTxtRecipientNameApartment()).isEqualTo(tbl240Entity.getRecipientNameApartment());
        /* 宛先(氏名等) */
        assertThat(vo.getTxtRecipientNameUser()).isEqualTo(tbl240Entity.getRecipientNameUser());
        /* 発信者名 */
        assertThat(vo.getTxtSender()).isEqualTo(tbm001Entity.getCityName() + "長　" + tbm001Entity.getChiefName());
        /* 本文中宛先1 */
        assertThat(vo.getTxtTextAdress1()).isEqualTo(tbl240Entity.getTextaddress1());
        /* 根拠条文1 */
        assertThat(vo.getLblEvidenceBar()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD041 ,tbl240Entity.getEvidenceBar()));
        /* 根拠条文2 */
        assertThat(vo.getLstEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD043 ,tbl240Entity.getEvidenceNo()));
        /* 期限に関する規定 */
        assertThat(vo.getLblPeriodEvidence()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD045 ,tbl240Entity.getPeriodEvidence()));
        /* 前回通知年月日 */
        assertThat(vo.getLblLastNoticeDate()).isEqualTo("2020/09/30");
        /* 前回文書番号 */
        assertThat(vo.getLblLastDocumentNo()).isEqualTo(vo.getTxtDocumentNo());
        /* 本文中宛先2 */
        assertThat(vo.getTxtTextAdress2()).isEqualTo(tbl240Entity.getTextaddress2());
        /* 所在地 */
        assertThat(vo.getLblAddress()).isEqualTo("東京都" + tbm001Entity.getCityName() + entity.getAddress());
        /* マンション名 */
        assertThat(vo.getLblApartmentName()).isEqualTo(entity.getApartmentName());
        /* 届出様式名 */
        assertThat(vo.getLblNotificationFormatName()).isEqualTo("2");
        /* 提出期限 */
        assertThat(vo.getCalNotificationTimelimit()).isEqualTo("2020/09/30");
        /* 担当・連絡先 */
        assertThat(vo.getTxaContact()).isEqualTo(tbl240Entity.getContact());
    }

    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-007/区分:N/チェック内容:test Exclusive Check Report Status Then Return True When Newest Notification No Of Supervised Notice Logic Form And Newest Notification No Of Tbl100 Entity Are The Same
     * @throws BusinessException
     */
    @Test
    public void testExclusiveCheckReportStatusThenReturnTrueWhenNewestNotificationNoOfSupervisedNoticeLogicFormAndNewestNotificationNoOfTBL100EntityAreTheSame() throws BusinessException {

        TBL100Entity tbl100Entity = new TBL100Entity();
        tbl100Entity.setApartmentId(APARTMENT_ID);
        tbl100Entity.setNewestNotificationNo("10");
        
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setNewestNotificationNo("10");
        
        Mockito.when(tbl100DAO.getMansionInformationById(form.getApartmentId())).thenReturn(tbl100Entity);
        assertTrue(supervisedNoticeLogicImpl.exclusiveCheckReportStatus(form));
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-008/区分:N/チェック内容:test Exclusive Check Report Status Then Return False When Newest Notification No Of Supervised Notice Logic Form And Newest Notification No Of Tbl100 Entity Are Not The Same
     * @throws BusinessException
     */
    @Test
    public void testExclusiveCheckReportStatusThenReturnFalseWhenNewestNotificationNoOfSupervisedNoticeLogicFormAndNewestNotificationNoOfTBL100EntityAreNotTheSame() throws BusinessException {

        TBL100Entity tbl100Entity = new TBL100Entity();
        tbl100Entity.setApartmentId(APARTMENT_ID);
        tbl100Entity.setNewestNotificationNo("10");
        
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setNewestNotificationNo("11");
        
        Mockito.when(tbl100DAO.getMansionInformationById(form.getApartmentId())).thenReturn(tbl100Entity);
        assertFalse(supervisedNoticeLogicImpl.exclusiveCheckReportStatus(form));
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-009/区分:N/チェック内容:test Exclusive Check Dunning Notice Count Then Return True When Tbl240 Is Null
     * @throws BusinessException
     */
    @Test
    public void testExclusiveCheckDunningNoticeCountThenReturnTrueWhenTbl240IsNull() throws BusinessException {

        TBL240Entity tbl240Entity = null;
        
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(tbl240Entity);
        assertTrue(supervisedNoticeLogicImpl.exclusiveCheckDunningNoticeCount(form));
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-010/区分:N/チェック内容:test Exclusive Check Dunning Notice Count Then Return True When Tbl240 Is Not Null And Supervised Notice Count Of Tbl240 Entity And Supervised Notice Count Of Supervised Notice Logic Form Are The Same
     * @throws BusinessException
     */
    @Test
    public void testExclusiveCheckDunningNoticeCountThenReturnTrueWhenTbl240IsNotNullAndSupervisedNoticeCountOfTBL240EntityAndSupervisedNoticeCountOfSupervisedNoticeLogicFormAreTheSame() throws BusinessException {

        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setApartmentId(APARTMENT_ID);
        tbl240Entity.setSupervisedNoticeCount(1);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setSupervisedNoticeCount(1);
        form.setApartmentId(APARTMENT_ID);
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(tbl240Entity);
        assertTrue(supervisedNoticeLogicImpl.exclusiveCheckDunningNoticeCount(form));
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-011/区分:N/チェック内容:test Exclusive Check Dunning Notice Count Then Return False When Tbl240 Is Not Null And Supervised Notice Count Of Tbl240 Entity And Supervised Notice Count Of Supervised Notice Logic Form Are Not The Same
     * @throws BusinessException
     */
    @Test
    public void testExclusiveCheckDunningNoticeCountThenReturnFalseWhenTbl240IsNotNullAndSupervisedNoticeCountOfTBL240EntityAndSupervisedNoticeCountOfSupervisedNoticeLogicFormAreNotTheSame() throws BusinessException {

        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setApartmentId(APARTMENT_ID);
        tbl240Entity.setSupervisedNoticeCount(1);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setSupervisedNoticeCount(2);
        form.setApartmentId(APARTMENT_ID);
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(tbl240Entity);
        assertFalse(supervisedNoticeLogicImpl.exclusiveCheckDunningNoticeCount(form));
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-012/区分:N/チェック内容:test Supervise Notice Register Success When Evidence Bar Is First Time Bar And Tbl240 Is Null
     * @throws BusinessException
     */
    @Test
    public void testSuperviseNoticeRegisterSuccessWhenEvidenceBarIsFirstTimeBarAndTbl240IsNull() throws BusinessException {

        TBL240Entity entity240 = null;
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("15");
        form.setCalNotificationTimelimit("");
        
        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setSupervisedNoticeCount(1);
        Mockito.when(tbl240DAO.save(Mockito.any())).thenReturn(tbl240Entity);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-013/区分:N/チェック内容:test Supervise Notice Register Success When Evidence Bar Is Second Time Bar And Tbl240 Is Null
     * @throws BusinessException
     */
    @Test
    public void testSuperviseNoticeRegisterSuccessWhenEvidenceBarIsSecondTimeBarAndTbl240IsNull() throws BusinessException {

        TBL240Entity entity240 = null;
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("16");
        form.setCalNotificationTimelimit("");
        
        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setSupervisedNoticeCount(1);
        Mockito.when(tbl240DAO.save(Mockito.any())).thenReturn(tbl240Entity);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-014/区分:N/チェック内容:test Supervise Notice Register Success When Evidence Bar Is Second Time Bar And Tbl240 Is Not Null And Notice Date Is Null
     * @throws BusinessException
     */
    @Test
    public void testSuperviseNoticeRegisterSuccessWhenEvidenceBarIsSecondTimeBarAndTbl240IsNotNullAndNoticeDateIsNull() throws BusinessException {

        TBL240Entity entity240 = new TBL240Entity();
        entity240.setSupervisedNoticeCount(1);
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("16");
        form.setCalNotificationTimelimit("");
        
        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setSupervisedNoticeCount(entity240.getSupervisedNoticeCount() + 1);
        Mockito.when(tbl240DAO.save(Mockito.any())).thenReturn(tbl240Entity);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-015/区分:N/チェック内容:test Supervise Notice Register Success When Evidence Bar Is Second Time Bar And Tbl240 Is Not Null And Notice Date Is Not Null
     * @throws BusinessException
     */
    @Test
    public void testSuperviseNoticeRegisterSuccessWhenEvidenceBarIsSecondTimeBarAndTbl240IsNotNullAndNoticeDateIsNotNull() throws BusinessException {

        TBL240Entity entity240 = new TBL240Entity();
        entity240.setNoticeDate("20022020");
        entity240.setSupervisedNoticeCount(1);
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setApartmentId(APARTMENT_ID);
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("16");
        form.setCalNotificationTimelimit("");
        
        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setSupervisedNoticeCount(entity240.getSupervisedNoticeCount() + 1);
        Mockito.when(tbl240DAO.save(Mockito.any())).thenReturn(tbl240Entity);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-016/区分:E/チェック内容:test Supervise Notice Register Unsuccess When Save Data To Table Tbl240 Fail
     * @throws BusinessException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSuperviseNoticeRegisterUnsuccessWhenSaveDataToTableTbl240Fail() throws BusinessException {

        TBL240Entity entity240 = new TBL240Entity();
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("16");
        form.setCalNotificationTimelimit("");
        
        Mockito.when(tbl240DAO.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-017/区分:E/チェック内容:test Supervise Notice Register Unsuccess When Save Data To Table Tbl300 Fail
     * @throws BusinessException
     */
    @Test(expected = ClassCastException.class)
    public void testSuperviseNoticeRegisterUnsuccessWhenSaveDataToTableTbl300Fail() throws BusinessException {

        TBL240Entity entity240 = new TBL240Entity();
        
        Mockito.when(tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(Mockito.anyString())).thenReturn(entity240);
        SupervisedNoticeLogicForm form = new SupervisedNoticeLogicForm();
        form.setCalNoticeDate("");
        form.setLblEvidenceBar("16");
        form.setCalNotificationTimelimit("");
        
        TBL240Entity tbl240Entity = new TBL240Entity();
        tbl240Entity.setSupervisedNoticeCount(entity240.getSupervisedNoticeCount() + 1);
        Mockito.when(tbl240DAO.save(Mockito.any())).thenReturn(tbl240Entity);
        
        Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(IllegalArgumentException.class);
        supervisedNoticeLogicImpl.superviseNoticeRegister(form);
    }
    
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-018/区分:I/チェック内容:Test Get Supervised Notice By Supervised Notice No Success
     * @throws BusinessException
     */
    @Test
    public void testGetSupervisedNoticeBySupervisedNoticeNoSuccess() throws BusinessException {
        TBL240Entity actual = new TBL240Entity();
        actual.setApartmentId(APARTMENT_ID);
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISEDNOTICE_NO)).thenReturn(actual);
        
        TBL240Entity expected = supervisedNoticeLogicImpl.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISEDNOTICE_NO);
        assertThat(expected.getApartmentId()).isEqualTo(actual.getApartmentId());
    }
 
    /**
     * 案件ID:GIA0110/チェックリストID:UT- GIA0110-019/区分:N/チェック内容:Test Get Supervised Notice By Supervised Notice No Don’t Found And Return Null
     * @throws BusinessException
     */
    @Test
    public void testGetSupervisedNoticeBySupervisedNoticeNoDontFoundAndReturnNull() throws BusinessException {
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISEDNOTICE_NO)).thenReturn(null);
        
        TBL240Entity expected = supervisedNoticeLogicImpl.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISEDNOTICE_NO);
        assertThat(expected).isEqualTo(null);
    }
}
