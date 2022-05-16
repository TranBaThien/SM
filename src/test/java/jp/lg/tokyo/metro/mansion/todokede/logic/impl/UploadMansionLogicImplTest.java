/*
 * @(#) UploadMansionLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoUploadForm;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */
@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class UploadMansionLogicImplTest {

    @InjectMocks
    private UploadMansionLogicImpl uploadMansionLogicImpl;

    @Mock
    TBL100DAO tbl100DAO;

    @Mock
    TBM001DAO tbm001DAO;

    @Mock
    TBL105DAO tbl105DAO;
    
    @Mock
    TBL110DAO tbl110DAO;

    @Mock
    private SequenceUtil sequenceUtil;

    @Mock
    private Authentication authentication;
    
    protected MockHttpSession session;
    protected MockHttpServletRequest request;
    
    private final String CITY_ONETIME_PASSWORD_PERIOD = "30";
    private final String CITY_LOGIN_URL = "http://localhost:8080/GAA0110";
    
    private final String MI_PROPERTY_NUMBER = "55555";
    private final String MI_APARTMENT_NAME = "マンション８２";
    private final String MI_APARTMENT_NAME_PHONETIC = "マンションハチ二";
    private final String MI_ZIPCODE = "1410026";
    private final String MI_CITY_NAME = "中央区";
    private final String MI_ADDRESS = "東十条";
    private final String MI_NOTIFICATION_KBN = "任意";
    private final String MI_SUPPORT = "指定しない";
    private final String MI_BUILD_YEAR = "1967";
    private final String MI_BUILD_MONTH = "12";
    private final String MI_BUILD_DAY = "19";
    private final String MI_FLOOR_NUMBER = "77";
    private final String MI_HOUSE_NUMBER = "88";
    private final String MI_SITE_AREA = "12345678.11";
    private final String MI_TOTAL_FLOOR_AREA = "12.11";
    private final String MI_BUILDING_AREA = "100.00";
    private final String MI_BUILDING_STRUCTURE = "bStructure";
    private final String MI_REGISTRATION_NO = "regNo";
    private final String MI_REGISTRATION_ADDRESS = "ｒｅｇＡｄｄｒｅｓｓ";
    private final String MI_REGISTRATION_HOUSE_NO = "ｒｅｇ";
    private final String MI_REAL_ESTATE_NO = "realE";
    private final String MI_PRELIMINARY_1 = "p1";
    private final String MI_PRELIMINARY_2 = "p2";
    private final String MI_PRELIMINARY_3 = "p3";
    private final String MI_PRELIMINARY_4 = "p4";
    private final String MI_PRELIMINARY_5 = "p5";

    /**
     * list Mansion info form
     * 
     * @return
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadForm() {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        MansionInfoUploadForm mansion = new MansionInfoUploadForm();

        mansion.setPropertyNumber(MI_PROPERTY_NUMBER);
        mansion.setApartmentName(MI_APARTMENT_NAME);
        mansion.setApartmentNamePhonetic(MI_APARTMENT_NAME_PHONETIC);
        mansion.setZipCode(MI_ZIPCODE);
        mansion.setCityName(MI_CITY_NAME);
        mansion.setAddress(MI_ADDRESS);
        mansion.setNotificationKbn(MI_NOTIFICATION_KBN);
        mansion.setSupport(MI_SUPPORT);
        mansion.setBuildYear(MI_BUILD_YEAR);
        mansion.setBuildMonth(MI_BUILD_MONTH);
        mansion.setBuildDay(MI_BUILD_DAY);
        mansion.setFloorNumber(MI_FLOOR_NUMBER);
        mansion.setHouseNumber(MI_HOUSE_NUMBER);
        mansion.setSiteArea(MI_SITE_AREA);
        mansion.setTotalFloorArea(MI_TOTAL_FLOOR_AREA);
        mansion.setBuildingArea(MI_BUILDING_AREA);
        mansion.setBuildingStructure(MI_BUILDING_STRUCTURE);
        mansion.setRegistrationNo(MI_REGISTRATION_NO);
        mansion.setRegistrationAddress(MI_REGISTRATION_ADDRESS);
        mansion.setRegistrationHouseNo(MI_REGISTRATION_HOUSE_NO);
        mansion.setRealEstateNo(MI_REAL_ESTATE_NO);
        mansion.setPreliminary1(MI_PRELIMINARY_1);
        mansion.setPreliminary2(MI_PRELIMINARY_2);
        mansion.setPreliminary3(MI_PRELIMINARY_3);
        mansion.setPreliminary4(MI_PRELIMINARY_4);
        mansion.setPreliminary5(MI_PRELIMINARY_5);

        lstMansion.add(mansion);
        return lstMansion;
    }
    
    /**
     * list mansion info form true
     * 
     * @return
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormTrue() {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        MansionInfoUploadForm mansion = new MansionInfoUploadForm();

        mansion.setPropertyNumber("1000001");
        mansion.setApartmentName("千代田区マンション１");
        mansion.setApartmentNamePhonetic("マンションハチニ");
        mansion.setZipCode("1020072");
        mansion.setCityName("中央区");
        mansion.setAddress("東十条");
        mansion.setNotificationKbn("任意");
        mansion.setSupport("指定しない");
        mansion.setBuildYear("1999");
        mansion.setBuildMonth("12");
        mansion.setBuildDay("19");
        mansion.setFloorNumber("10");
        mansion.setHouseNumber("10");
        mansion.setSiteArea("100.11");
        mansion.setTotalFloorArea("100.11");
        mansion.setBuildingArea("100.11");
        mansion.setBuildingStructure("bStructure");
        mansion.setRegistrationNo("regNo");
        mansion.setRegistrationAddress("ｒｅｇＡｄｄｒｅｓｓ");
        mansion.setRegistrationHouseNo("ｒｅｇ");
        mansion.setRealEstateNo("realE");
        mansion.setPreliminary1("p1");
        mansion.setPreliminary2("p2");
        mansion.setPreliminary3("p3");
        mansion.setPreliminary4("p4");
        mansion.setPreliminary5("p5");

        lstMansion.add(mansion);
        return lstMansion;
    }
    
    /**
     * List Mansion Info Upload Form is Exceed Length
     * 
     * @return
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormExceedLength() {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        MansionInfoUploadForm mansion = new MansionInfoUploadForm();

        mansion.setPropertyNumber("12345678");
        mansion.setApartmentName("123456789012345678901234567890123456789012345678901234567890");
        mansion.setApartmentNamePhonetic("オハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマス");
        mansion.setZipCode("123456789012345678901234567890");
        mansion.setCityName("オハヨウゴザイマス");
        mansion.setAddress("オハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマス");
        mansion.setNotificationKbn("オハヨウゴザイマス");
        mansion.setSupport("オハヨウゴザイマス");
        mansion.setBuildYear("1234567890123456789012345678901");
        mansion.setBuildMonth("1234567890123456789012345678901");
        mansion.setBuildDay("1234567890123456789012345678901");
        mansion.setFloorNumber("1234567890123456789012345678901");
        mansion.setHouseNumber("1234567890123456789012345678901");
        mansion.setSiteArea("123123123123.11");
        mansion.setTotalFloorArea("123123123123.11");
        mansion.setBuildingArea("123123123123.11");
        mansion.setBuildingStructure("12345678901234567890123456789011234567890123456789012345678901");
        mansion.setRegistrationNo("12345678901234567890123456789011234567890123456789012345678901");
        mansion.setRegistrationAddress("オハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマスオハヨウゴザイマス");
        mansion.setRegistrationHouseNo("オハヨウゴザイマスオハヨウゴザイマス");
        mansion.setRealEstateNo("12345678901234567890");
        mansion.setPreliminary1("1234567890123456789012345678901");
        mansion.setPreliminary2("1234567890123456789012345678901");
        mansion.setPreliminary3("1234567890123456789012345678901");
        mansion.setPreliminary4("1234567890123456789012345678901");
        mansion.setPreliminary5("1234567890123456789012345678901");

        lstMansion.add(mansion);
        return lstMansion;
    }
    
    /**
     * List Mansion Info Upload Form Is Empty
     * 
     * @return
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormNull() {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        MansionInfoUploadForm mansion = new MansionInfoUploadForm();

        mansion.setPropertyNumber(null);
        mansion.setApartmentName(null);
        mansion.setApartmentNamePhonetic(null);
        mansion.setZipCode(null);
        mansion.setCityName(null);
        mansion.setAddress(null);
        mansion.setNotificationKbn(null);
        mansion.setSupport(null);
        mansion.setBuildYear(null);
        mansion.setBuildMonth(null);
        mansion.setBuildDay(null);
        mansion.setFloorNumber(null);
        mansion.setHouseNumber(null);
        mansion.setSiteArea(null);
        mansion.setTotalFloorArea(null);
        mansion.setBuildingArea(null);
        mansion.setBuildingStructure(null);
        mansion.setRegistrationNo(null);
        mansion.setRegistrationAddress(null);
        mansion.setRegistrationHouseNo(null);
        mansion.setRealEstateNo(null);
        mansion.setPreliminary1(null);
        mansion.setPreliminary2(null);
        mansion.setPreliminary3(null);
        mansion.setPreliminary4(null);
        mansion.setPreliminary5(null);
        lstMansion.add(mansion);
        return lstMansion;
    }
    
    /**
     * List mansion info upload form is not halfsize
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotHalfSize() throws SystemException {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setZipCode("１０２００７２");
        lstMansion.get(0).setBuildYear("１９９９");
        lstMansion.get(0).setBuildMonth("１２");
        lstMansion.get(0).setBuildDay("１９");
        lstMansion.get(0).setFloorNumber("１０");
        lstMansion.get(0).setHouseNumber("１０");

        return lstMansion;
    }
    
    /**
     * List mansion info upload form is not decimal
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotDecimal() throws SystemException {
        
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setSiteArea("a");
        lstMansion.get(0).setTotalFloorArea("b");
        lstMansion.get(0).setBuildingArea("c");

        
        return lstMansion;
    }
    
    /**
     * List mansion info upload form is not katakana
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotKatakana() throws SystemException {
        
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setApartmentNamePhonetic("abc");;
        
        return lstMansion;
    }
    /**
     * List mansion info upload form is not decimal limit point
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotDecimalLimitPoint() throws SystemException {
        
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setSiteArea("1.1");
        lstMansion.get(0).setTotalFloorArea("1.1");
        lstMansion.get(0).setBuildingArea("1.1");
        
        
        return lstMansion;
    }
    
    /**
     * List mansion info upload form is not fullsize
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotFullSize() throws SystemException {
        
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setCityName("Tokyo");
        lstMansion.get(0).setAddress("Tokyo");
        lstMansion.get(0).setNotificationKbn("a");
        lstMansion.get(0).setSupport("b");
        lstMansion.get(0).setRegistrationAddress("EegistrationAddress");
        lstMansion.get(0).setRegistrationHouseNo("RegistrationHouseNo");
        
        return lstMansion;
    }
    
    /**
     * List mansion info upload form is not alpha numeric
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsNotAlphaNumeric() throws SystemException {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setPropertyNumber("１０００００１");
        lstMansion.get(0).setRealEstateNo("ｒｅａｌＥ");

        return lstMansion;
    }
    
    /**
     * list mansion info upload form is special character
     * 
     * @return
     * @throws SystemException 
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsSpecialCharacter() throws SystemException {

        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setApartmentName("&");
        lstMansion.get(0).setBuildingStructure("&");
        lstMansion.get(0).setRegistrationNo("&");
        lstMansion.get(0).setPreliminary1("&");
        lstMansion.get(0).setPreliminary2("&");
        lstMansion.get(0).setPreliminary3("&");
        lstMansion.get(0).setPreliminary4("&");
        lstMansion.get(0).setPreliminary5("&");

        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and notificationKbn is not equals
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndNotificationKbnNotEquals() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setNotificationKbn("任任");
        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and notificationKbn is equals 必須
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndNotificationKbnEquals2() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setNotificationKbn("必須");
        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and support is not equals
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndSupportNotEquals() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setSupport("象象");
        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and support is equals 対象外
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndSupportEquals2() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setSupport("対象外");
        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and support is equals 指定しない
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndSupportEquals3() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setSupport("指定しない");
        return lstMansion;
    }
    
    /**
     * list mansion info upload form is true and zipCode min length false
     * 
     * @return
     * @throws SystemException
     */
    private List<MansionInfoUploadForm> listMansionInfoUploadFormIsTrueAndZipcodeMinLengthFalse() throws SystemException {
        List<MansionInfoUploadForm> lstMansion = new ArrayList<MansionInfoUploadForm>();
        lstMansion = CommonUtils.copyList(listMansionInfoUploadFormTrue());
        lstMansion.get(0).setZipCode("000");
        return lstMansion;
    }

    /* Create TBL100Entity */
    private final String APARTMENT_ID_TBL100 = "11000000001";
    private final String ADDRESS_TBL100 = "tokyo";
    private final String APARTMENT_LOST_FLAG_TBL100 = "1";
    private final String APARTMENT_NAME_TBL100 = "abc";
    private final String APARTMENT_NAME_PHONETIC_TBL100 = "abc phonetic";
    private final String BUILD_DAY_TBL100 = "10";
    private final String BUILD_MONTH_TBL100 = "02";
    private final String BUILD_YEAR_TBL100 = "2020";
    private final double BUILDING_AREA_TBL100 = 00000000001;
    private final String BUILDING_STRUCTURE_TBL100 = "0101010101";
    private final String CITY_CODE_TBL100 = "202020";
    private final String CITY_NAME_TBL100 = "kyoto";
    private final String DELETE_FLAG_TBL100 = "0";
    private final int FLOOR_NUMBER_TBL100 = 111;
    private final int HOUSE_NUMBER_TBL100 = 222;
    private final String MAIL_ADDRESS_TBL100 = "abc@gmail.com";
    private final String NEWEST_NOTIFICATION_ADDRESS_TBL100 = "tokyo";
    private final String NEWEST_NOTIFICATION_NAME_TBL100 = "osaka";
    private final String NEWEST_NOTIFICATION_NO_TBL100 = "11000000001";
    private final LocalDate NEXT_NOTIFICATION_DATE_TBL100 = LocalDate.now();
    private final String NOTIFICATION_KBN_TBL100 = "1";
    private final String PRELIMINARY1_TBL100 = "0000001";
    private final String PRELIMINARY2_TBL100 = "0000002";
    private final String PRELIMINARY3_TBL100 = "0000003";
    private final String PRELIMINARY4_TBL100 = "0000004";
    private final String PRELIMINARY5_TBL100 = "0000005";
    private final String PROPERTY_NUMBER_TBL100 = "00000008";
    private final String REAL_ESTATE_NO_TBL100 = "1111111111113";
    private final String REGISTRATION_ADDRESS_TBL100 = "kyoto";
    private final String REGISTRATION_HOUSE_NO_TBL100 = "osaka";
    private final String REGISTRATION_NO_TBL100 = "00000008";
    private final String REPASSWORD_MAIL_ADDRESS_TBL100 = "abcd@gmail.com";
    private final double SITE_AREA_TBL100 = 10101010;
    private final String SUPPORT_TBL100 = "1";
    private final double TOTAL_FLOOR_AREA_TBL100 = 11111;
    private final Timestamp UPDATE_DATETIME_TBL100 = new Timestamp(new Date().getTime());
    private final String UPDATE_USER_ID_TBL100 = "00001";
    private final String ZIP_CODE_TBL100 = "12345678";

    /**
     * create new TBL100Entity
     * 
     * @param apartmentId
     * @return
     */
    private TBL100Entity newTBL100Entity(String apartmentId) {
        TBL100Entity entity = new TBL100Entity();

        entity.setApartmentId(apartmentId);
        entity.setAddress(ADDRESS_TBL100);
        entity.setApartmentLostFlag(APARTMENT_LOST_FLAG_TBL100);
        entity.setApartmentName(APARTMENT_NAME_TBL100);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC_TBL100);
        entity.setBuildDay(BUILD_DAY_TBL100);
        entity.setBuildMonth(BUILD_MONTH_TBL100);
        entity.setBuildYear(BUILD_YEAR_TBL100);
        entity.setBuildingArea(BUILDING_AREA_TBL100);
        entity.setBuildingStructure(BUILDING_STRUCTURE_TBL100);
        entity.setCityCode(CITY_CODE_TBL100);
        entity.setCityName(CITY_NAME_TBL100);
        entity.setDeleteFlag(DELETE_FLAG_TBL100);
        entity.setFloorNumber(FLOOR_NUMBER_TBL100);
        entity.setHouseNumber(HOUSE_NUMBER_TBL100);
        entity.setMailAddress(MAIL_ADDRESS_TBL100);
        entity.setNewestNotificationAddress(NEWEST_NOTIFICATION_ADDRESS_TBL100);
        entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME_TBL100);
        entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);
        entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE_TBL100);
        entity.setNotificationKbn(NOTIFICATION_KBN_TBL100);
        entity.setPreliminary1(PRELIMINARY1_TBL100);
        entity.setPreliminary2(PRELIMINARY2_TBL100);
        entity.setPreliminary3(PRELIMINARY3_TBL100);
        entity.setPreliminary4(PRELIMINARY4_TBL100);
        entity.setPreliminary5(PRELIMINARY5_TBL100);
        entity.setPropertyNumber(PROPERTY_NUMBER_TBL100);
        entity.setRealEstateNo(REAL_ESTATE_NO_TBL100);
        entity.setRegistrationAddress(REGISTRATION_ADDRESS_TBL100);
        entity.setRegistrationHouseNo(REGISTRATION_HOUSE_NO_TBL100);
        entity.setRegistrationNo(REGISTRATION_NO_TBL100);
        entity.setRepasswordMailAddress(REPASSWORD_MAIL_ADDRESS_TBL100);
        entity.setSiteArea(SITE_AREA_TBL100);
        entity.setSupport(SUPPORT_TBL100);
        entity.setTotalFloorArea(TOTAL_FLOOR_AREA_TBL100);
        entity.setUpdateDatetime(UPDATE_DATETIME_TBL100);
        entity.setUpdateUserId(UPDATE_USER_ID_TBL100);
        entity.setZipCode(ZIP_CODE_TBL100);

        return entity;
    }

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
        return generateTBM004EntityList().stream().collect(Collectors.toMap(TBM004Entity::getSetTargetNameEng, TBM004Entity::getSetPoint));
    }

    private List<TBM004Entity> generateTBM004EntityList() {
        List<TBM004Entity> settingList = new ArrayList<TBM004Entity>();
        settingList.add(generateTBM004Entity("1", CommonConstants.CITY_ONETIME_PASSWORD_PERIOD, CITY_ONETIME_PASSWORD_PERIOD));
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
    private final String USER_TYPE_TBL120 = "1";

    private TBL120Entity generateEntityTbl120(String accountLockFlag, String accountAvailableFlag, String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID_TBL120);
        entity.setLoginId(LOGIN_ID_TBL120);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.TOKYO_STAFF.getCode()));
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
    
    private List<String> listCityName() {
        List<String> lst = new ArrayList<String>();
        lst.add("中央区");
        return lst;
    }
    
    private List<String> lengthOfRowList() {
        List<String> lst = new ArrayList<String>();
        lst.add("26");
        return lst;
    }
    
    private List<String> lengthOfRowListFalse() {
        List<String> lst = new ArrayList<String>();
        lst.add("10");
        return lst;
    }
    
 // required error message
    private String requiredMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0002, name);
    }

    // half size error message
    private String halfSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0003, name);
    }

    // half size and decimal error message
    private String halfSizeDecimal(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0004, name);
    }

    // half size alphanumeric error message
    private String halfSizeAlphaMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0005, name);
    }

    // full size error message
    private String fullSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0006, name);
    }

    // katakana error message
    private String katakanaMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0007, name);
    }

    // max error message
    private String maxMessage(String name, String max) {
        return MessageUtil.getMessage(CommonConstants.MS_E0012, name, max);
    }
    
    // min error message
    private String minMessage(String name, String min) {
        return MessageUtil.getMessage(CommonConstants.MS_E0013, name, min);
    }

    // special character error message
    private String specialChar(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0015, name);
    }

    // half size and decimal error message
    private String halfSizeDecimalLimitMsg(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0016, name);
    }
    
    // check file row message
//    private String fileRow(String name) {
//        return MessageUtil.getMessage(CommonConstants.MS_E0120, name);
//    }

    // check equals message
    private String equalsMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0100, name);
    }

    // check equals message
//    private String existMessage(String name) {
//        return MessageUtil.getMessage(CommonConstants.MS_E0121, name);
//    }
    
    // check file row message
    private String formatMessage() {
        return MessageUtil.getMessage(CommonConstants.MS_E0136);
    }

    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-001/区分:E/チェック内容:test upload csv when file count max is false
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndLengthOfRowListIsFalse() throws SystemException {
        String cityCode = "111111";
        String cityHeadWord = "abc";
        List<String> lstCityName = listCityName();
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormTrue(), "0", lengthOfRowListFalse());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.BLANK,
                CommonConstants.E0136, formatMessage());
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }

    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-002/区分:N/チェック内容:test upload csv when file count max and all field in csv is true
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsTrue() throws SystemException {
        TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
        TBL105Entity tbl105Entity = new TBL105Entity();
        TBL110Entity tbl110Entity = new TBL110Entity();

        String cityCode = "111111";
        String cityHeadWord = "abc";
        String loginId = "G0000002";
        List<String> lstCityName = listCityName();

        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL110), CommonConstants.COL_LOGIN_ID, UserTypeCode.NONE)).thenReturn(loginId);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
        Mockito.when(tbl110DAO.save(tbl110Entity)).thenReturn(tbl110Entity);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);

        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormTrue(), CommonConstants.STR_50, lengthOfRowList());
        
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-003/区分:N/チェック内容:test upload csv when file count max and all field in csv is true but number column is null
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadCsvWhenFileCountMaxAndAllFieldInCsvIsTrueButNumberColumnIsNull() throws SystemException {
        TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
        TBL105Entity tbl105Entity = new TBL105Entity();
        TBL110Entity tbl110Entity = new TBL110Entity();

        String cityCode = "111111";
        String cityHeadWord = "abc";
        String loginId = "G0000002";
        List<String> lstCityName = listCityName();
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL110), CommonConstants.COL_LOGIN_ID, UserTypeCode.NONE)).thenReturn(loginId);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
        Mockito.when(tbl110DAO.save(tbl110Entity)).thenReturn(tbl110Entity);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);

        List<MansionInfoUploadForm> lstMansion = listMansionInfoUploadFormTrue();
        lstMansion.get(0).setFloorNumber(CommonConstants.BLANK);
        lstMansion.get(0).setHouseNumber(CommonConstants.BLANK);
        lstMansion.get(0).setSiteArea(CommonConstants.BLANK);
        lstMansion.get(0).setTotalFloorArea(CommonConstants.BLANK);
        lstMansion.get(0).setBuildingArea(CommonConstants.BLANK);
        
        uploadMansionLogicImpl.uploadCSV(lstMansion, CommonConstants.STR_50, lengthOfRowList());
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-004/区分:E/チェック内容:test upload mansion when file count max and all field is true but exist in database
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxAndAllFieldIsTrueButExistInDB() throws SystemException {
        List<String> lstCityName = listCityName();
        Mockito.when(tbl100DAO.getCount(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);
        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormTrue(), CommonConstants.STR_50, lengthOfRowList());
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-005/区分:E/チェック内容:test upload mansion when file count max is true and all field is exceed max length
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsExceedMaxLength() throws SystemException {
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(listCityName());
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormExceedLength(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_PROPERTY_NUMBER, CommonConstants.E0012, maxMessage(CommonConstants.MI_PROPERTY_NUMBER, CommonConstants.STR_7));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-006/区分:E/チェック内容:test upload mansion when file count max is true and zipCode min length false
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndZipcodeMinLengthFalse() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndZipcodeMinLengthFalse(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_ZIPCODE, CommonConstants.E0013, minMessage(CommonConstants.MI_ZIPCODE, CommonConstants.STR_7));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-007/区分:E/チェック内容:test upload mansion when file count max is true and all field is empty
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNull() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormNull(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_APARTMENT_NAME, CommonConstants.E0002,
                requiredMessage(CommonConstants.MI_APARTMENT_NAME));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-008/区分:E/チェック内容:test upload mansion when file count max is true and all field is not alpha numeric
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotAlphaNumeric() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotAlphaNumeric(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_PROPERTY_NUMBER,
                CommonConstants.E0005, halfSizeAlphaMessage(CommonConstants.MI_PROPERTY_NUMBER));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-009/区分:E/チェック内容:test upload mansion when file count max is true and all field is special character
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsSpecialCharacter() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsSpecialCharacter(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_APARTMENT_NAME, CommonConstants.E0015,
                specialChar(CommonConstants.MI_APARTMENT_NAME));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-010/区分:E/チェック内容:test upload mansino when file count max is true and notificatinoKbn is not equals
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndNotificationKbnNotEquals() throws SystemException {
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(listCityName());
        Mockito.when(tbl100DAO.getCount(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(0);
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndNotificationKbnNotEquals(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_NOTIFICATION_KBN,
                CommonConstants.E0100, equalsMessage(CommonConstants.MI_NOTIFICATION_KBN));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-011/区分:N/チェック内容:test upload mansion when file count max is true and notificationKbn equals 必須
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndNotificationKbnEquals2() throws SystemException {
        TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
        TBL105Entity tbl105Entity = new TBL105Entity();
        TBL110Entity tbl110Entity = new TBL110Entity();

        String cityCode = "111111";
        String cityHeadWord = "abc";
        String loginId = "G0000002";
        List<String> lstCityName = listCityName();
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL110), CommonConstants.COL_LOGIN_ID, UserTypeCode.NONE)).thenReturn(loginId);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
        Mockito.when(tbl110DAO.save(tbl110Entity)).thenReturn(tbl110Entity);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndNotificationKbnEquals2(), CommonConstants.STR_50, lengthOfRowList());
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-012/区分:E/チェック内容:test upload mansino when file count max is true and support is not equals
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndSupportNotEquals() throws SystemException {
        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndSupportNotEquals(), CommonConstants.STR_50, lengthOfRowList());
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-013/区分:N/チェック内容:test upload mansion when file count max is true and support equals 対象外
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndSupportEquals2() throws SystemException {
        TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
        TBL105Entity tbl105Entity = new TBL105Entity();
        TBL110Entity tbl110Entity = new TBL110Entity();

        String cityCode = "111111";
        String cityHeadWord = "abc";
        String loginId = "G0000002";
        List<String> lstCityName = listCityName();
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL110), CommonConstants.COL_LOGIN_ID, UserTypeCode.NONE)).thenReturn(loginId);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
        Mockito.when(tbl110DAO.save(tbl110Entity)).thenReturn(tbl110Entity);

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndSupportEquals2(), CommonConstants.STR_50, lengthOfRowList());
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-014/区分:N/チェック内容:test upload mansion when file count max is true and support equals 指定しない
     * 
     * @throws SystemException
     */
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndSupportEquals3() throws SystemException {
        TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
        TBL105Entity tbl105Entity = new TBL105Entity();
        TBL110Entity tbl110Entity = new TBL110Entity();

        String cityCode = "111111";
        String cityHeadWord = "abc";
        String loginId = "G0000002";
        List<String> lstCityName = listCityName();
        Mockito.when(tbm001DAO.getCityCodeByCityName(Mockito.anyString())).thenReturn(cityCode);
        Mockito.when(tbm001DAO.getCityHeadWordByCityCode(Mockito.anyString())).thenReturn(cityHeadWord);
        Mockito.when(tbm001DAO.getListCityName()).thenReturn(lstCityName);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL110), CommonConstants.COL_LOGIN_ID, UserTypeCode.NONE)).thenReturn(loginId);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
        Mockito.when(tbl110DAO.save(tbl110Entity)).thenReturn(tbl110Entity);

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
        UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        
        uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsTrueAndSupportEquals3(), CommonConstants.STR_50, lengthOfRowList());
    }
    
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotHalfSize() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotHalfSize(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_ZIPCODE, CommonConstants.E0003,
                halfSizeMessage(CommonConstants.MI_ZIPCODE));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotFullSize() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotFullSize(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_CITY_NAME, CommonConstants.E0006,
                fullSizeMessage(CommonConstants.MI_CITY_NAME));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotDecimal() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotDecimal(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_SITE_AREA, CommonConstants.E0004,
                halfSizeDecimal(CommonConstants.MI_SITE_AREA));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotDecimalLimitPoint() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotDecimalLimitPoint(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_SITE_AREA, CommonConstants.E0016,
                halfSizeDecimalLimitMsg(CommonConstants.STR_2));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    @Test
    public void testUploadMansionWhenFileCountMaxIsTrueAndAllFieldIsNotKatakana() throws SystemException {
        List<ZAA0150ErrorVo> lst = uploadMansionLogicImpl.uploadCSV(listMansionInfoUploadFormIsNotKatakana(), CommonConstants.STR_50, lengthOfRowList());
        ZAA0150ErrorVo vo = new ZAA0150ErrorVo(String.valueOf(1), CommonConstants.MI_APARTMENT_NAME_PHONETIC,
                CommonConstants.E0007, katakanaMessage(CommonConstants.MI_APARTMENT_NAME_PHONETIC));
        assertThat(lst.get(0)).isEqualToComparingFieldByField(vo);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-015/区分:N/チェック内容:test get data from csv
     * 
     * @throws IOException
     * @throws BusinessException 
     */
    @Test
    public void testGetDataFormCsv() throws IOException, BusinessException {
        File file = ResourceUtils.getFile("classpath:csv_FL040_template.csv");
        InputStream stream =  new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", stream);
        
        // prepare list expected
        List<MansionInfoUploadForm> expected = new ArrayList<MansionInfoUploadForm>();
        MansionInfoUploadForm data1 = new MansionInfoUploadForm();
        data1.setPropertyNumber("55555");
        data1.setApartmentName("マンション８２");
        data1.setApartmentNamePhonetic("マンションハチ二");
        data1.setZipCode("1410026");
        data1.setCityName("中央区");
        data1.setAddress("東十条");
        data1.setNotificationKbn("任意");
        data1.setSupport("指定しない");
        data1.setBuildYear("1967");
        data1.setBuildMonth("12");
        data1.setBuildDay("19");
        data1.setFloorNumber("77");
        data1.setHouseNumber("88");
        data1.setSiteArea("12345678.11");
        data1.setTotalFloorArea("12.11");
        data1.setBuildingArea("12.11");
        data1.setBuildingStructure("bStructure");
        data1.setRegistrationNo("regNo");
        data1.setRegistrationAddress("ｒｅｇＡｄｄｒｅｓｓ");
        data1.setRegistrationHouseNo("ｒｅｇ");
        data1.setRealEstateNo("realE");
        data1.setPreliminary1("p1");
        data1.setPreliminary2("p2");
        data1.setPreliminary3("p3");
        data1.setPreliminary4("p4");
        data1.setPreliminary5("p5");
        expected.add(data1);

        // get result
        List<MansionInfoUploadForm> result = uploadMansionLogicImpl.getDataFormCsv(multipartFile.getBytes(), lengthOfRowList());

        // compare result with expected
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(expected.size() == result.size()).isTrue();

        int i = 0;
        for (MansionInfoUploadForm data : result) {
            assertThat(data.getPropertyNumber()).isEqualTo(expected.get(i).getPropertyNumber());
            assertThat(data.getApartmentName()).isEqualTo(expected.get(i).getApartmentName());
            assertThat(data.getApartmentNamePhonetic()).isEqualTo(expected.get(i).getApartmentNamePhonetic());
            assertThat(data.getZipCode()).isEqualTo(expected.get(i).getZipCode());
            assertThat(data.getCityName()).isEqualTo(expected.get(i).getCityName());
            assertThat(data.getAddress()).isEqualTo(expected.get(i).getAddress());
            assertThat(data.getNotificationKbn()).isEqualTo(expected.get(i).getNotificationKbn());
            assertThat(data.getSupport()).isEqualTo(expected.get(i).getSupport());
            assertThat(data.getBuildYear()).isEqualTo(expected.get(i).getBuildYear());
            assertThat(data.getBuildMonth()).isEqualTo(expected.get(i).getBuildMonth());
            assertThat(data.getBuildDay()).isEqualTo(expected.get(i).getBuildDay());
            assertThat(data.getFloorNumber()).isEqualTo(expected.get(i).getFloorNumber());
            assertThat(data.getHouseNumber()).isEqualTo(expected.get(i).getHouseNumber());
            assertThat(data.getSiteArea()).isEqualTo(expected.get(i).getSiteArea());
            assertThat(data.getTotalFloorArea()).isEqualTo(expected.get(i).getTotalFloorArea());
            assertThat(data.getBuildingArea()).isEqualTo(expected.get(i).getBuildingArea());
            assertThat(data.getBuildingStructure()).isEqualTo(expected.get(i).getBuildingStructure());
            assertThat(data.getRegistrationNo()).isEqualTo(expected.get(i).getRegistrationNo());
            assertThat(data.getRegistrationAddress()).isEqualTo(expected.get(i).getRegistrationAddress());
            assertThat(data.getRegistrationHouseNo()).isEqualTo(expected.get(i).getRegistrationHouseNo());
            assertThat(data.getRealEstateNo()).isEqualTo(expected.get(i).getRealEstateNo());
            assertThat(data.getPreliminary1()).isEqualTo(expected.get(i).getPreliminary1());
            assertThat(data.getPreliminary2()).isEqualTo(expected.get(i).getPreliminary2());
            assertThat(data.getPreliminary3()).isEqualTo(expected.get(i).getPreliminary3());
            assertThat(data.getPreliminary4()).isEqualTo(expected.get(i).getPreliminary4());
            assertThat(data.getPreliminary5()).isEqualTo(expected.get(i).getPreliminary5());
            i++;
        }
    }
    
}
