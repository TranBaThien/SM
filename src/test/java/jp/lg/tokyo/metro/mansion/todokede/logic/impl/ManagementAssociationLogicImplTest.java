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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL400DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL400Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ApproveNewUserRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApproveNewUserRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserRegistrationVo;

/**
 * @author nhvu
 *
 */
@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class ManagementAssociationLogicImplTest { 
    
    @InjectMocks
    private ManagementAssociationLogicImpl associationLogicImpl;
    
    @Mock
    TBL400DAO tbl400dao;
    
    @Mock
    TBL400BDAO tbl400bdao;
    
    @Mock
    TBL100DAO tbl100dao;
    
    @Mock
    TBL110DAO tbl110dao;
    
    @Mock
    TBM001DAO tbm001dao;
    
    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest httpServletRequest;
    
    @Mock
    private IEMailLogic mailLogic;
    
    private final String APPLICATION_NO = "1000000013";
    private final String CONTACT_PROPERTY_CODE = "1";
    private final String CONTACT_PROPERTY_CODE9 = "9";
    private final String CONTACT_PROPERTY_ELSE = "1";
    private final String CONTACT_ZIP_CODE = "192-0032";
    private final String CONTACT_ADDRESS = "東京都八王子市石川町";
    private final String CONTACT_TEL_NO  = "123-1111-1111";
    private final String CONTACT_NAME = "フリガナ";
    private final String CONTACT_NAME_PHONETIC = "タキャナー・オーキナーワ";
    private final String CONTACT_MAIL_ADDRESS = "takana_1@gmail.com";
    private final String RESULT_APARTMENT_NAME = "";
    private final String APARTMENT_ID = "1000000013";
    private final String CITY_NAME = "文京区";
    private final String PROPERTY_NUMBER = "14";
    private final String APARTMENT_NAME  = "da nang";
    private final String APARTMENT_NAME_PHONETIC = "Hai Chau";
    private final String ZIP_CODE = "192-0032";
    private final String CITY_CODE = "131091";
    private final String ADDRESS = "Da Nang";
    private final String REPASSWORD_MAIL_ADDRESS = "test@test.com";
    private final String MAIL_ADDRESS = "vuvu@cmc.com";
    private final String NEWEST_NOTIFICATION_NO = "1000000001";
    private final String NEWEST_NOTIFICATION_NAME = "Demo";
    private final String NEWEST_NOTIFICATION_ADDRESS = "1";
    private final String NOTIFICATION_KBN = "1";
    private final String SUPPORT = "1";
    private final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.now();
    private final String BUILD_YEAR = "2000";
    private final String BUILD_MONTH = "11";
    private final String BUILD_DAY = "11";
    private final int FLOOR_NUMBER = 10;
    private final int HOUSE_NUMBER = 1;
    private final int SITE_AREA  = 11;
    private final int TOTAL_FLOOR_AREA = 10;
    private final double BUILDING_AREA = 10;
    private final String APARTMENT_LOST_FLAG = "1";
    private final String UPDATE_USER_ID = "1000000013";
    private final Timestamp UPDATE_DATETIME = new Timestamp(System.currentTimeMillis());
    private final String DELETE_FLAG = "0";
    private final String LOGIN_ID = "M0000013";
    private final String PASSWORD=  "$2a$10$PlK8Po6LMP6V2Czr8P14AuC7OIryeN/rICL9OmWst65GlabLs8vQG";
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String VALIDITY_FLAG = "1";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "1";
    private final String LOGIN_STATUS_FLAG = "1";
    private final String ST_M_ACCOUNT_LOCK_PERIOD = "60";
    private final String ST_G_PASSWORD_VALID_PERIOD = "60";
    private final String PRELIMINARY1 = "0000001";
    private final String PRELIMINARY2 = "0000002";
    private final String PRELIMINARY3 = "0000003";
    private final String PRELIMINARY4 = "0000004";
    private final String PRELIMINARY5 = "0000005";
    private final String REAL_ESTATE_NO = "1111111111113";
    private final String REGISTRATION_ADDRESS = "kyoto";
    private final String REGISTRATION_HOUSE_NO = "osaka";
    private final String REGISTRATION_NO = "00000008";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.MINUTES);
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String BUILDING_STRUCTURE = "0101010101";
    // Mail variable
    private final String MAIL_TEMPLATE = "パスワード再発行通知メール（管理組合向け）";
    private final String MAIL_SEND_FROM = "mansiondev@cmcglobal.com.vn";
    private final String MAIL_REPLY_TO = "to@gmail.com";
    private final String WINDOW_CITY_NAME = "BBBBB";
    private final String WINDOW_BE_LONG = "問合せ窓口_部署（区市町村向け）";
    private final String WINDOW_TEL_NO = "TEL_123456780";
    private final String WINDOW_FAX_NO = "FAX_987654320";
    private final String WINDOW_MAIL_ADDRESS = "window2@gmail.com";
    
    private final String APPLICATIONNUMBER = "1";
    private final LocalDateTime APPLICATIONTIME = LocalDateTime.of(2019, 12, 04, 10, 10, 10);
    private final String APARTMENTNAME = "アパート 1";    
    private final String CITYCODE = "131059";
    private final String CITYNAME = "文京区";
    private final String JUDGERESULT = "1";
    private final String JUDGERESULTOUT = "未審査";

    private List<ManagementAssociationCustomVo> generateManagementAssociationCustomVoList() {
        
        int i;
        
        List<ManagementAssociationCustomVo> list = new ArrayList<ManagementAssociationCustomVo>();
                 
        
        for(i = 0; i < 50; i++) {
            ManagementAssociationCustomVo vo = new ManagementAssociationCustomVo();
            
            vo.setApplicationNo(APPLICATIONNUMBER);
            vo.setApplicationTime(APPLICATIONTIME);
            vo.setApartmentName(APARTMENTNAME);
            vo.setAddress(ADDRESS);
            vo.setCityCode(CITYCODE);
            vo.setCityName(CITYNAME);
            vo.setJudgeResult(JUDGERESULT);
            
            list.add(vo);
        } 
        
        return list;
        
    }
    
    
    private List<GCA0110Vo> generateGCA0110VoList() {
         
        int i;
        
        List<GCA0110Vo> list = new ArrayList<GCA0110Vo>(); 
        
        for(i = 0; i < 50; i++) {
            GCA0110Vo vo = new GCA0110Vo();
            
            vo.setApplicationNo(APPLICATIONNUMBER);
            vo.setApplicationTime("20191204101010");
            vo.setApartmentName(APARTMENTNAME);
            vo.setAddress(ADDRESS);
            vo.setCityCode(CITYCODE);
            vo.setCityName(CITYNAME);
            vo.setJudgeResult(JUDGERESULT);
            
            list.add(vo);
        }
        
        return list;
    }
    
    private SearchForm generateSearchForm() {
        
        SearchForm form = new SearchForm();
        
        form.setCityCode("131059");
        form.setApartmentName("アパート");
        form.setStartTimeApply("2019/12/05");
        form.setEndTimeApply("2019/12/23");
        form.setUnexamined("on");
        form.setApproval("off");
        form.setReject("off"); 
        
        return form;
        
    }
    
    /**
     * 
     * @return
     */
    private ManagementAssociationCustomVo generateManagementAssociationCustomVo() {
        String judgeResult= CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_UNREVIEWED);
        ManagementAssociationCustomVo vo = new ManagementAssociationCustomVo(
                APPLICATION_NO,
                ADDRESS, 
                CITY_CODE, 
                judgeResult, 
                "",
                APARTMENT_NAME_PHONETIC, 
                ZIP_CODE, APARTMENT_NAME, 
                CONTACT_PROPERTY_CODE, 
                CONTACT_PROPERTY_ELSE, 
                CONTACT_ZIP_CODE, 
                CONTACT_ADDRESS, 
                CONTACT_TEL_NO, 
                CONTACT_NAME, 
                CONTACT_NAME_PHONETIC, 
                CONTACT_MAIL_ADDRESS, 
                RESULT_APARTMENT_NAME,
                "", "", new Date(), 
                APARTMENT_ID, 
                CITY_NAME);
        return vo;
    }
    
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

    /* Create TBM001Entity */
    private final String CITY_CODE_TBM001 = "131091";
    private final String CHIEF_NAME_TBM001 = "濱野　健";
    private final String CITY_NAME_TBM001 = "品川区";
    private final String DOCUMENT_NO_TBM001 = "中央区文書番号初期値１２３４５６７８９０";

    private TBM001Entity newTBM001Entity(String cityCode) {
        TBM001Entity entity = new TBM001Entity();

        entity.setCityCode(cityCode);
        entity.setChiefName(CHIEF_NAME_TBM001);
        entity.setCityName(CITY_NAME_TBM001);
        entity.setDocumentNo(DOCUMENT_NO_TBM001);

        return entity;
    }
    
    /**
     * 
     * @return
     */
    private List<TBL100Entity> generateListTBL100Entity(){
        List<TBL100Entity> getList = new ArrayList<TBL100Entity>();
        TBL100Entity entity = generateTBL100Entity(APARTMENT_ID);
        getList.add(entity);
        return getList;
    }
    
    private ApproveNewUserRegistrationForm generateApproveNewUserRegistrationForm() {
        ApproveNewUserRegistrationForm form = new ApproveNewUserRegistrationForm();
        form.setApplicationNo(APPLICATION_NO);
        form.setTxaNote("txaNote");
        form.setUpdateUserId(APARTMENT_ID);
        form.setLoginId(APARTMENT_ID);
        return form;
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
     *  create table TBL400 
     */
    private TBL400Entity generateTBL400Entity() {
        TBL400Entity entity = new TBL400Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setApplicationNo(APPLICATION_NO);
        entity.setPassword(PASSWORD);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setContactMailAddress("test@test.com");
        entity.setApartmentName("test");
        entity.setContactName("test");
        return entity;
    }
    
    /**
     *  create UserRegistrationVo
     */
    private UserRegistrationVo generateUserRegistrationVo() {
        UserRegistrationVo entity = new UserRegistrationVo();
        entity.setApartmentId(APARTMENT_ID);
        entity.setLblRowNumber("1");
        entity.setCityCode("131091");
        entity.setLblApartmentAddress2("東五反田");
        entity.setLblApartmentName2("マンション名17");
        entity.setLblApartmentZipCode2("141-0022");
        return entity;
    }

    /**
     *  create table ApproveNewUserRegistrationVo
     */
    private ApproveNewUserRegistrationVo generateApproveNewUserRegistrationVo() {
        ApproveNewUserRegistrationVo vo = new ApproveNewUserRegistrationVo();
        vo.setContactMailAddress(CONTACT_ADDRESS);
        vo.setApartmentName(APARTMENT_NAME);
        vo.setContactName(CONTACT_NAME);
        vo.setLoginUrl("localhost:8080/MAAA");
        vo.setLoginId(LOGIN_ID);
        return vo;
    }

    /**
     *  create table generateML010Vo
     */
    private ML010Vo generateML010Vo() {
        ML010Vo vo = new ML010Vo();
        vo.setApartmentName(APARTMENT_NAME);
        vo.setContactName(CONTACT_NAME);
        vo.setMailTemplate(MAIL_TEMPLATE);
        vo.setMailSendFrom(MAIL_SEND_FROM);
        vo.setMailReplyTo(MAIL_REPLY_TO);
        vo.setContactMailAddress(CONTACT_MAIL_ADDRESS);
        vo.setCityName(WINDOW_CITY_NAME);
        vo.setWindowBelong(WINDOW_BE_LONG);
        vo.setWindowTelNo(WINDOW_TEL_NO);
        vo.setWindowFaxNo(WINDOW_FAX_NO);
        vo.setWindowMailAddress(WINDOW_MAIL_ADDRESS);
        return vo;
    }

    /**
     *  create table generateML020Vo
     */
    private ML020Vo generateML020Vo() {
        ML020Vo vo = new ML020Vo();
        vo.setContactName(CONTACT_NAME);
        vo.setMailTemplate(MAIL_TEMPLATE);
        vo.setMailSendFrom(MAIL_SEND_FROM);
        vo.setMailReplyTo(MAIL_REPLY_TO);
        vo.setContactMailAddress(CONTACT_MAIL_ADDRESS);
        vo.setCityName(WINDOW_CITY_NAME);
        vo.setWindowBelong(WINDOW_BE_LONG);
        vo.setWindowTelNo(WINDOW_TEL_NO);
        vo.setWindowFaxNo(WINDOW_FAX_NO);
        vo.setWindowMailAddress(WINDOW_MAIL_ADDRESS);
        return vo;
    }

    
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
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-001/区分:N/チェック内容: test get a Page of Management Association Success
     * 
     */
    @Test 
    public void GetListManagementAssociation_WhenAllConditionsAreMet_ThenReturnPageImpl() {
      
          List<ManagementAssociationCustomVo> list = generateManagementAssociationCustomVoList();
          
          GCA0110Vo vo = new GCA0110Vo();
            
          vo.setApplicationNo(APPLICATIONNUMBER);
          vo.setApplicationTime("2019/12/04 10:10");
          vo.setApartmentName(APARTMENTNAME);
          vo.setAddress(ADDRESS);
          vo.setCityCode(CITYCODE);
          vo.setCityName(CITYNAME);
          vo.setJudgeResult(JUDGERESULTOUT); 
           
          int a = 150;
          Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
                              
          Pageable pageable = PageRequest.of(0, 50); 
          
          Page<ManagementAssociationCustomVo> pageModel = new PageImpl<ManagementAssociationCustomVo>(list, pageable, 150);                      
      
          Mockito.when(tbl400dao.getListManagementAssociation(Mockito.any())).thenReturn(pageModel);
                 
          Page<GCA0110Vo> pageVo = associationLogicImpl.getListManagementAssociation(0, 50);                      
          
          for(GCA0110Vo gvo : pageVo.getContent()) {
                 assertThat(gvo.getApplicationNo()).isEqualTo(vo.getApplicationNo());
                 assertThat(gvo.getApplicationTime()).isEqualTo(vo.getApplicationTime());
                 assertThat(gvo.getApartmentName()).isEqualTo(vo.getApartmentName());
                 assertThat(gvo.getAddress()).isEqualTo(vo.getAddress());
                 assertThat(gvo.getCityCode()).isEqualTo(vo.getCityCode());
                 assertThat(gvo.getCityName()).isEqualTo(vo.getCityName());
                 assertThat(gvo.getJudgeResult()).isEqualTo(vo.getJudgeResult());
          }
      
    }
     
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-002/区分:E/チェック内容: test get a Page of Management Association when Page Number is Negative
     * 
     */
    @Test 
    public void GetListManagementAssociation_WhenPageNumberIsNegative_ThenReturnEmptyPage() {
      
        List<ManagementAssociationCustomVo> list = generateManagementAssociationCustomVoList();                  
         
        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
                              
        Pageable pageable = PageRequest.of(0, 50);  
          
        Page<ManagementAssociationCustomVo> pageModel = new PageImpl<ManagementAssociationCustomVo>(list, pageable, 150);                      
      
        Mockito.when(tbl400dao.getListManagementAssociation(Mockito.any())).thenReturn(pageModel);
                 
        Page<GCA0110Vo> pageVo = associationLogicImpl.getListManagementAssociation(-1, 50);    
                                  
        boolean empty = pageVo.isEmpty();
            
        assertThat(empty).isEqualTo(true);
             
    }
     
     
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-003/区分:E/チェック内容: test get a Page of Management Association when Size Number is Negative
     * 
     */
    @Test 
    public void GetListManagementAssociation_WhenSizeNumberIsNegative_ThenReturnEmptyPage() {
          
        List<ManagementAssociationCustomVo> list = generateManagementAssociationCustomVoList();                  
             
        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
                                  
        Pageable pageable = PageRequest.of(0, 50);  
              
        Page<ManagementAssociationCustomVo> pageModel = new PageImpl<ManagementAssociationCustomVo>(list, pageable, 150);                      
          
        Mockito.when(tbl400dao.getListManagementAssociation(Mockito.any())).thenReturn(pageModel);
                     
        Page<GCA0110Vo> pageVo = associationLogicImpl.getListManagementAssociation(0, -50);    
                                      
        boolean empty = pageVo.isEmpty();
                
        assertThat(empty).isEqualTo(true);     
          
    }
     
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-004/区分:E/チェック内容: test get a Page of Management Association when Page Number And Size Number is Negative
     * 
     */
    @Test 
    public void GetListManagementAssociation_WhenPageNumberAndSizeNumberIsNegative_ThenReturnEmptyPage() {
      
        List<ManagementAssociationCustomVo> list = generateManagementAssociationCustomVoList();                  
         
        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
                                  
        Pageable pageable = PageRequest.of(0, 50);  
              
        Page<ManagementAssociationCustomVo> pageModel = new PageImpl<ManagementAssociationCustomVo>(list, pageable, 150);                      
          
        Mockito.when(tbl400dao.getListManagementAssociation(Mockito.any())).thenReturn(pageModel);
                     
        Page<GCA0110Vo> pageVo = associationLogicImpl.getListManagementAssociation(-1, -50);    
                                      
        boolean empty = pageVo.isEmpty(); 
                
        assertThat(empty).isEqualTo(true);    
      
    }
         
        
     
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-005/区分:E/チェック内容: test get a Page of Management Association when search result is zero
     * 
     */
    @Test 
    public void GetListManagementAssociation_WhenListManagementAssociationCustomVoIsEmpty_ThenReturnEmptyPage() { 

        List<ManagementAssociationCustomVo> list = new ArrayList<ManagementAssociationCustomVo>(); 
        
        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
                                  
        Pageable pageable = PageRequest.of(0, 50);  
              
        Page<ManagementAssociationCustomVo> pageModel = new PageImpl<ManagementAssociationCustomVo>(list, pageable, 0);                      
          
        Mockito.when(tbl400dao.getListManagementAssociation(Mockito.any())).thenReturn(pageModel);
        
        Page<GCA0110Vo> pageVo = associationLogicImpl.getListManagementAssociation(1, 50);
        
        boolean empty = pageVo.isEmpty(); 
        
        assertThat(empty).isEqualTo(true);    
      
    }
                       
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-006/区分:N/チェック内容: test Count the total number of search results Success
     * 
     */
    @Test 
    public void totalResultWhenDisplay__WhenAllConditionsAreMet_ThenReturnTotalSearchResultsNumber() {
      
        int a = 18;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
      
        int result = associationLogicImpl.totalResultWhenDisplay(0, 50);
        
        assertThat(result).isEqualTo(18);    
      
    }
      
    
     
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-007/区分:E/チェック内容: test Count the total number of search results when Page Number is Negative
     * 
     */
    @Test 
    public void totalResultWhenDisplay__WhenPageNumberIsNegative_ThenReturnZero() { 

        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
      
        int result = associationLogicImpl.totalResultWhenDisplay(-1, 50);
        
        assertThat(result).isEqualTo(0);
      
    }
     
     
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-008/区分:E/チェック内容: test Count the total number of search results when Size Number is Negative
     * 
     */
    @Test 
    public void totalResultWhenDisplay__WhenSizeNumberIsNegative_ThenReturnZero() 
    { 
        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
      
        int result = associationLogicImpl.totalResultWhenDisplay(1, -50);
        
        assertThat(result).isEqualTo(0);
    }
     
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-009/区分:E/チェック内容: test Count the total number of search results when Page Number and Size Number is Negative
     * 
     */
    @Test 
    public void totalResultWhenDisplay__WhenPageNumberAndSizeNumberIsNegative_ThenReturnZero() { 

        int a = 150;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
      
        int result = associationLogicImpl.totalResultWhenDisplay(-1, -50);
        
        assertThat(result).isEqualTo(0);
      
    }
         
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-010/区分:N/チェック内容: test Count the total number of search results When Total Search Results is Zero
     * 
     */
    @Test 
    public void totalResultWhenDisplay__WhenTotalSearchResultsNumberIsZero_ThenReturnZero() { 
        
        int a = 0;
        Mockito.when(tbl400dao.countManagementAssociationNumber()).thenReturn(a);
      
        int result = associationLogicImpl.totalResultWhenDisplay(0, 50);
        
        assertThat(result).isEqualTo(0);
        
    }
           
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-011/区分:N/チェック内容: test Search for management association information Success
     * 
     */
    @Test
    public void searchManagementAssociation_WhenAllConditionsAreMet_ThenReturnPageImpl() {
          
        List<GCA0110Vo> listvo = generateGCA0110VoList();
        
        SearchForm form = generateSearchForm();
        
        GCA0110Vo vo = new GCA0110Vo();
        
        vo.setApplicationNo(APPLICATIONNUMBER);
        vo.setApplicationTime("2019/12/04 10:10");
        vo.setApartmentName(APARTMENTNAME);
        vo.setAddress(ADDRESS);
        vo.setCityCode(CITYCODE);
        vo.setCityName(CITYNAME);
        vo.setJudgeResult(JUDGERESULTOUT);
        
        Mockito.when(tbl400bdao.searchManagementAssociation(Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(listvo);
        
        int a = 15; 
        Mockito.when(tbl400bdao.TotalSearchResults(Mockito.any())).thenReturn(a);
        
        Page<GCA0110Vo> page = associationLogicImpl.searchManagementAssociation(0, 50, form);
        
        for(GCA0110Vo gvo : page.getContent()) {
             assertThat(gvo.getApplicationNo()).isEqualTo(vo.getApplicationNo());
             assertThat(gvo.getApplicationTime()).isEqualTo(vo.getApplicationTime());
             assertThat(gvo.getApartmentName()).isEqualTo(vo.getApartmentName());
             assertThat(gvo.getAddress()).isEqualTo(vo.getAddress());
             assertThat(gvo.getCityCode()).isEqualTo(vo.getCityCode());
             assertThat(gvo.getCityName()).isEqualTo(vo.getCityName());
             assertThat(gvo.getJudgeResult()).isEqualTo(vo.getJudgeResult());
        }
        
    } 
    
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-012/区分:N/チェック内容: test get the total number of search results Success
     * 
     */
    @Test
    public void TotalSearchResults_WhenAllConditionsAreMet_ThenReturnTotalSearchResultsNumber() {
        
        SearchForm form = generateSearchForm(); 
        
        int a = 15; 
        Mockito.when(tbl400bdao.TotalSearchResults(Mockito.any())).thenReturn(a);
        
        int result = associationLogicImpl.TotalSearchResults(form);
        
        assertThat(result).isEqualTo(15);
        
    } 
    
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-013/区分:N/チェック内容: test get List Management Association Success
     * 
     */
    @Test
    public void getListManagementAssociationAgain_WhenAllConditionsAreMet_ThenReturnPageImpl() {
        
        List<ManagementAssociationCustomVo> list = generateManagementAssociationCustomVoList();                
        
        List<String> listapplyno = new ArrayList<String>();
        
        listapplyno.add("1");
        listapplyno.add("2");
        listapplyno.add("3");
        
        SearchForm form = generateSearchForm();
        
        GCA0110Vo vo = new GCA0110Vo();
        
        vo.setApplicationNo(APPLICATIONNUMBER);
        vo.setApplicationTime("2019/12/04 10:10");
        vo.setApartmentName(APARTMENTNAME);
        vo.setAddress(ADDRESS);
        vo.setCityCode(CITYCODE);
        vo.setCityName(CITYNAME);
        vo.setJudgeResult(JUDGERESULTOUT);
         
        Mockito.when(tbl400dao.getListManagementAssociationAgain(Mockito.any())).thenReturn(list);
        
        Page<GCA0110Vo> page = associationLogicImpl.getListManagementAssociationAgain(0, 50, form, listapplyno);
        
        for(GCA0110Vo gvo : page.getContent()) {
             assertThat(gvo.getApplicationNo()).isEqualTo(vo.getApplicationNo());
             assertThat(gvo.getApplicationTime()).isEqualTo(vo.getApplicationTime());
             assertThat(gvo.getApartmentName()).isEqualTo(vo.getApartmentName());
             assertThat(gvo.getAddress()).isEqualTo(vo.getAddress());
             assertThat(gvo.getCityCode()).isEqualTo(vo.getCityCode());
             assertThat(gvo.getCityName()).isEqualTo(vo.getCityName());
             assertThat(gvo.getJudgeResult()).isEqualTo(vo.getJudgeResult());
        }
        
    }
    
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-001/区分:N/チェック内容: test New User Registration Apartment Information With Unreviewed ContactProperty Code Not Is Other
     * @throws BusinessException BusinessException
     */
    @Test
    public void testNewUserRegistration_ApartmentInformation_WithUnreviewed_ContactPropertyCodeNotIsOther() throws BusinessException {
        List<TBL100Entity> getList = generateListTBL100Entity();
        ManagementAssociationCustomVo customVo = generateManagementAssociationCustomVo();
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        Mockito.when(tbl100dao.getRegistrationApartmentInformation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getList);
        TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
        Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBM001)).thenReturn(entityTbm001);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-002/区分:N/チェック内容: test New User Registration Apartment Information With Unreviewed ContactProperty Code Is Other
     * @throws BusinessException BusinessException
     */
    @Test
    public void testNewUserRegistration_ApartmentInformation_WithUnreviewed_ContactPropertyCodeIsOther() throws BusinessException {
        List<TBL100Entity> getList = generateListTBL100Entity();
        ManagementAssociationCustomVo customVo = generateManagementAssociationCustomVo();
        customVo.setContactPropertyCode(CONTACT_PROPERTY_CODE9);
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        Mockito.when(tbl100dao.getRegistrationApartmentInformation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getList);
        TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
        Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBM001)).thenReturn(entityTbm001);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNotNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-003/区分:E/チェック内容: test New User Registration Apartment Information With Unreviewed Error
     * @throws BusinessException BusinessException
     */
    @Test(expected = BusinessException.class)
    public void testNewUserRegistrationApartmentInformationWithUnreviewedError() throws BusinessException {
        List<TBL100Entity> getList = generateListTBL100Entity();
        ManagementAssociationCustomVo customVo = null;
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        Mockito.when(tbl100dao.getRegistrationApartmentInformation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getList);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-004/区分:E/チェック内容: test New User Registration Apartment Information With Approve Or Rejection Error
     * @throws BusinessException BusinessException
     */
    @Test(expected = BusinessException.class)
    public void testNewUserRegistrationApartmentInformationWithApproveOrRejectionError() throws BusinessException {
        List<TBL100Entity> getList = null;
        ManagementAssociationCustomVo customVo = null;
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        Mockito.when(tbl100dao.getRegistrationApartmentInformation(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getList);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-005/区分:I/チェック内容: test New User Registration Apartment Information With Approve/Rejection Contact Property Code Not Is Other
     * @throws BusinessException 
     */
    @Test
    public void testNewUserRegistrationApartmentInformationWithApproveRejectionContactPropertyCodeNotIsOther() throws BusinessException {
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_APPROVAL);
        ManagementAssociationCustomVo customVo = generateManagementAssociationCustomVo();
        customVo.setJudgeResult(judgeResult);
        // set PropertyCode Not Is Other
        customVo.setContactPropertyCode(CONTACT_PROPERTY_CODE);
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNotNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-006/区分:N/チェック内容: test New User Registration Apartment Information With Approve/Rejection Contact Property Code Is Other
     * @throws BusinessException BusinessException
     */
    @Test
    public void testNewUserRegistrationApartmentInformationWithApproveRejectionContactPropertyCodeIsOther() throws BusinessException {
        String judgeResult = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_APPROVAL);
        ManagementAssociationCustomVo customVo = generateManagementAssociationCustomVo();
        customVo.setJudgeResult(judgeResult);
        // PropertyCode Is Other
        customVo.setContactPropertyCode(CONTACT_PROPERTY_CODE9);
        Mockito.when(tbl400dao.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(customVo);
        associationLogicImpl.getRegistrationApartmentInformation(customVo);
        assertThat(customVo).isNotNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-008/区分:I/チェック内容: test Save To Register Aparment Rejection
     * @throws BusinessException 
     * 
     */
    @Test
    public void testSaveToRegisterAparmentRejection() throws BusinessException {
        ApproveNewUserRegistrationForm form = generateApproveNewUserRegistrationForm();
        String judgeResultCode = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_REJECTION);
        form.setRdoInspectionResult(judgeResultCode);
        UserRegistrationVo userVo = generateUserRegistrationVo();
        TBL400Entity tbl400 = generateTBL400Entity();
        TBL110Entity tbl110 = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl400dao.findByApplicationNo(APPLICATION_NO)).thenReturn(tbl400);
        Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110);
        associationLogicImpl.saveToRegisterAparment(form, userVo);
        assertThat(form).isNotNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-007/区分:N/チェック内容: test Save To Registe Aparment Approve
     * @throws BusinessException 
     * 
     */
    @Test
    public void testSaveToRegisterAparmentApprove() throws BusinessException {
        ApproveNewUserRegistrationForm form = generateApproveNewUserRegistrationForm();
        String judgeResultCode = CodeUtil.getValue(CommonConstants.CD016, CommonConstants.CD016_APPROVAL);
        form.setRdoInspectionResult(judgeResultCode);
        UserRegistrationVo userVo = generateUserRegistrationVo();
        TBL400Entity tbl400 = generateTBL400Entity();
        TBL110Entity tbl110 = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl400dao.findByApplicationNo(APPLICATION_NO)).thenReturn(tbl400);
        Mockito.when(tbl110dao.getUserByApartmentId(Mockito.anyString())).thenReturn(tbl110);
        associationLogicImpl.saveToRegisterAparment(form, userVo);
        assertThat(form).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-009/区分:E/チェック内容: test Save To Register Aparment Approve Rejection Is Exception
     * @throws BusinessException 
     * 
     */
    @Test(expected = BusinessException.class)
    public void testSaveToRegisterAparmentApproveRejectionIsException() throws BusinessException {
        ApproveNewUserRegistrationForm form = null;
        UserRegistrationVo userVo = new UserRegistrationVo();
        associationLogicImpl.saveToRegisterAparment(form, userVo);
        assertThat(form).isNull();
    }

    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-010/区分:E/チェック内容: test get ML010 Template Success
     * @throws BusinessException 
     * 
     */
    @Test
    public void testGetML010TemplateSuccess() throws BusinessException {
        ML010Vo ml010Vo = generateML010Vo();
        ApproveNewUserRegistrationVo vo = generateApproveNewUserRegistrationVo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML010Vo.class)).thenReturn(ml010Vo);
        associationLogicImpl.getML010Template(vo);
        assertThat(vo).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-011/区分:E/チェック内容: test get ML010 Template Success
     * @throws BusinessException 
     * 
     */
    @Test
    public void testGetML020TemplateSuccess() throws BusinessException {
        ML020Vo ml020Vo = generateML020Vo();
        ApproveNewUserRegistrationVo vo = generateApproveNewUserRegistrationVo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML020Vo.class)).thenReturn(ml020Vo);
        associationLogicImpl.getML020Template(vo);
        assertThat(vo).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-012/区分:E/チェック内容: test Send ML010 Success
     * @throws BusinessException 
     * 
     */
    @Test
    public void testSendML010Success() throws BusinessException {
        ML010Vo ml010Vo = generateML010Vo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML010Vo.class)).thenReturn(ml010Vo);
        associationLogicImpl.sendML010(ml010Vo);
        assertThat(ml010Vo).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-012/区分:E/チェック内容: test Send ML010 Success
     * @throws BusinessException 
     * 
     */
    @Test
    public void testSendML020Success() throws BusinessException {
        ML020Vo ml020Vo = generateML020Vo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML020Vo.class)).thenReturn(ml020Vo);
        associationLogicImpl.sendML020(ml020Vo);
        assertThat(ml020Vo).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-013/区分:N/チェック内容: test Mansion Information By Id
     * @throws BusinessException 
     * 
     */
    @Test
    public void getMansionInformationById() throws BusinessException {
        TBL100Entity entity = generateTBL100Entity(APARTMENT_ID);
        associationLogicImpl.getMansionInformationById(APARTMENT_ID);
        Mockito.when(associationLogicImpl.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        assertThat(entity).isNotNull();
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-015/区分:N/チェック内容: test Mansion Information By Id All
     * @throws BusinessException 
     * 
     */
    @Test
    public void getMansionInformationfindAll() throws BusinessException {
        TBL400Entity entity = generateTBL400Entity();
        List<TBL400Entity> list = new ArrayList<TBL400Entity>();
        list.add(entity);
        associationLogicImpl.getMansionInformationById(APARTMENT_ID);
        Mockito.when(associationLogicImpl.findAll()).thenReturn(list);
        assertThat(list).isEqualTo(associationLogicImpl.findAll());
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-014/区分:N/チェック内容: test get Registration Apartment Information
     * @throws BusinessException 
     * 
     */
    @Test
    public void getRegistrationApartmentInformation() throws BusinessException {
        TBL100Entity entity = generateTBL100Entity(APARTMENT_ID);
        ManagementAssociationCustomVo vo = generateManagementAssociationCustomVo();
        Mockito.when(associationLogicImpl.getMansionInformationById(APARTMENT_ID)).thenReturn(entity);
        Mockito.when(associationLogicImpl.getNewRegistrationInformation(APPLICATION_NO)).thenReturn(vo);
        TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
        Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBM001)).thenReturn(entityTbm001);
        associationLogicImpl.getRegistrationApartmentInformation(vo, entity);
        assertThat(entity).isNotNull();
        assertThat(vo).isNotNull();
    }

}
