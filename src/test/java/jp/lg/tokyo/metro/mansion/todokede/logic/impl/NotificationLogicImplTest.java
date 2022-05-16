/*
 * @(#) NotificationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 17, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.Notice;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.NotificationStatus;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.TypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.NoticeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationVo;

/**
 * @author hntvy
 *
 */
@RunWith(SpringRunner.class)
public class NotificationLogicImplTest {

    @Mock
    private TBL100DAO tbl100DAO;
    
    @Mock
    private TBL200DAO tbl200DAO;
    
    @Mock
    private TBL300DAO tbl300DAO;
    
    @InjectMocks
    private NotificationLogicImpl notificationLogic;
    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }
    
    /* Create TBL100Entity */
    private static final String APARTMENT_ID = "11000000001";
    private static final String ADDRESS = "tokyo";
    private static final String APARTMENT_LOST_FLAG = "1";
    private static final String APARTMENT_NAME = "abc";
    private static final String APARTMENT_NAME_PHONETIC = "abc phonetic";
    private static final String BUILD_DAY = "10";
    private static final String BUILD_MONTH = "02";
    private static final String BUILD_YEAR = "2020";
    private static final double BUILDING_AREA = 00000000001;
    private static final String BUILDING_STRUCTURE = "0101010101";
    private static final String CITY_CODE = "202020";
    private static final String CITY_NAME = "kyoto";
    private static final String DELETE_FLAG = "0";
    private static final int FLOOR_NUMBER = 111;
    private static final int HOUSE_NUMBER = 222;
    private static final String MAIL_ADDRESS = "abc@gmail.com";
    private static final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
    private static final String NEWEST_NOTIFICATION_NAME = "osaka";
    private static final String NEWEST_NOTIFICATION_NO = "10";
    private static final String NEXT_NOTIFICATION_DATE = "20191010";
    private static final String NOTIFICATION_KBN = "1";
    private static final String PRELIMINARY1 = "0000001";
    private static final String PRELIMINARY2 = "0000002";
    private static final String PRELIMINARY3 = "0000003";
    private static final String PRELIMINARY4 = "0000004";
    private static final String PRELIMINARY5 = "0000005";
    private static final String PROPERTY_NUMBER = "00000008";
    private static final String REAL_ESTATE_NO = "1111111111113";
    private static final String REGISTRATION_ADDRESS = "kyoto";
    private static final String REGISTRATION_HOUSE_NO = "osaka";
    private static final String REGISTRATION_NO = "00000008";
    private static final String REPASSWORD_MAIL_ADDRESS = "abcd@gmail.com";
    private static final double SITE_AREA = 10101010;
    private static final String SUPPORT = "1";
    private static final double TOTAL_FLOOR_AREA = 11111;
    private static final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
    private static final String UPDATE_USER_ID = "00001";
    private static final String ZIP_CODE = "12345678";
    
    private static final String NEXT_NOTIFICATION_DATE1 = "20300101";
    
    private TBL100Entity newTBL100Entity(String apartmentId, String nextNotificationDate) {
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
        entity.setNextNotificationDate(DateTimeUtil.getLocalDateFromString(nextNotificationDate));
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
    
    
    
    /* Create TBL300Entity */
    private static final String PROGRESS_RECORD_NO_1 = "000001";
    private static final String APARTMENT_ID_1 = "11000000001";
    private static final String CORRESPOND_DATE_1 = "201912040529";
    private static final String CORRESPOND_TYPE_CODE_1 = "0";
    private static final String DELETE_FLAG_1 = "1";
    private static final String NOTICE_TYPE_CODE_1 = "1";
    private static final String NOTIFICATION_METHOD_CODE_1 = "0";
    private static final String PROGRESS_RECORD_DETAIL_1 = "abcdef";
    private static final String PROGRESS_RECORD_OVERVIEW_1 = "0000004";
    private static final String RELATED_NUMBER_1 = "11111";
    private static final String SUPPORT_CODE_1 = "0";
    private static final String TYPE_CODE_1 = "3";
    private static final Timestamp UPDATE_DATETIME_1 = new Timestamp(new Date().getTime());
    private static final String UPDATE_USER_ID_1 = "1111";
    
    
    private static final String TYPE_CODE_2 = "4";

    private TBL300Entity newTBL300Entity(String progressRecordNo) {
        TBL300Entity entity = new TBL300Entity();
    
        entity.setProgressRecordNo(progressRecordNo);
        entity.setApartmentId(APARTMENT_ID_1);
        entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
        entity.setDeleteFlag(DELETE_FLAG_1);
        entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
        entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
        entity.setRelatedNumber(RELATED_NUMBER_1);
        entity.setSupportCode(SUPPORT_CODE_1);
        entity.setTypeCode(TYPE_CODE_1);
        entity.setUpdateDatetime(UPDATE_DATETIME_1);
        entity.setUpdateUserId(UPDATE_USER_ID_1);
        entity.setCorrespondDate(CORRESPOND_DATE_1);
    
    
    return entity;
    }
    
    
    private List<TBL300Entity> newTBL300Entitys(String apartmentId, String correspondDate, String typeCode) {
        List<TBL300Entity> list  = new ArrayList<TBL300Entity>();
        TBL300Entity entity = new TBL300Entity();
        
        entity.setProgressRecordNo(PROGRESS_RECORD_NO_1);
        entity.setApartmentId(apartmentId);
        entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
        entity.setDeleteFlag(DELETE_FLAG_1);
        entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
        entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
        entity.setRelatedNumber(RELATED_NUMBER_1);
        entity.setSupportCode(SUPPORT_CODE_1);
        entity.setTypeCode(typeCode);
        entity.setUpdateDatetime(UPDATE_DATETIME_1);
        entity.setUpdateUserId(UPDATE_USER_ID_1);
        entity.setCorrespondDate(correspondDate);
        
        list.add(entity);
        return list;
    }
    
    /* Create TBL200Entity */
    private static final String APARTMENT_ID_TBL200 = "11000000001";
    private static final String NOTIFICATION_NO_TBL200 = "11000000001";
    private static final String APARTMENT_NAME_TBL200 = "tokyo";
    private static final String CONTACT_PROPERTY_ELSE_TBL200 = "1";
    private static final String NOTIFICATION_PERSON_NAME_TBL200 = "abc";
    private static final LocalDate NOTIFICATION_DATE_TBL200 = LocalDate.now();
    private static final String NOTIFICATION_TYPE_TBL200 = "abc";
    private static final String ADDRESS_TBL200 = "abc";
    private static final String CONTACT_MAIL_ADDRESS_TBL200 = "abc";
    private static final String DELETE_FLAG_TBL200 = "0";
    private static final String ADVICE_DONE_FLAG_TBL200 = "1";
    private static final String ACCEPT_STATUS = "1";
    private static final String ACCEPT_STATUS1 = "2";
    private static final String ACCEPT_STATUS2 = "3";
    private static final int CHANGE_COUNT = 0;
    private static final int CHANGE_COUNT1 = 1;
    
    private TBL200Entity newTBL200Entity(String notificationNo, String acceptStatus, int changeCount) {

        TBL200Entity entity = new TBL200Entity();

        TBL200EntityPK entityPK = new TBL200EntityPK();
        entityPK.setApartmentId(APARTMENT_ID_TBL200);
        entityPK.setNotificationNo(notificationNo);
        entity.setId(entityPK);
        entity.setApartmentName(APARTMENT_NAME_TBL200);
        entity.setContactPropertyElse(CONTACT_PROPERTY_ELSE_TBL200);
        entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME_TBL200);
        entity.setNotificationDate(NOTIFICATION_DATE_TBL200);
        entity.setNotificationType(NOTIFICATION_TYPE_TBL200);
        entity.setAddress(ADDRESS_TBL200);
        entity.setContactMailAddress(CONTACT_MAIL_ADDRESS_TBL200);
        entity.setDeleteFlag(DELETE_FLAG_TBL200);
        entity.setAdviceDoneFlag(ADVICE_DONE_FLAG_TBL200);
        entity.setAcceptStatus(acceptStatus);
        entity.setChangeCount(changeCount);
        return entity;

    }

    /* Create NotificationVo */
    private static final String CORRESPOND_DATE_VO1 = "2019/12/04";

    private NotificationVo newNotificationVoCase1(String notificationStatus,String corresponDate, String noticeMsg) {
        NotificationVo notification = new NotificationVo();
        NoticeVo notice = new NoticeVo();
        List<NoticeVo> noticeVos = new ArrayList<NoticeVo>();
        notice.setCorresponDate(corresponDate);
        notice.setNotice(noticeMsg);
        noticeVos.add(notice);
        notification.setNotificationStatus(notificationStatus);
        notification.setNoticeVos(noticeVos);
        
        return notification;
    }
    
    /* Start test method getMansionInformationByIdTbl100 */
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-001/区分:N/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Greater Than Local Date And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */

    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateGreaterThanLocalDateAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.UNREPORTED.getNotificationStatus(),
                CORRESPOND_DATE_VO1,Notice.ACCEPTANCE_NOTICE.getNotice() );

        assertResult(vo, result);

    }

    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-007/区分:N/チェック内容:Test Get Apartment Notification When Type code Is CHANGE_NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateGreaterThanLocalDateAndTypeCodeIsCHANGENOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.CHANGE_NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.UNREPORTED.getNotificationStatus(),
                CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());

        assertResult(vo, result);
        
    }
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-008/区分:N/チェック内容:Test Get Apartment Notification When Type code Is ADVISORY_NOTICE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateGreaterThanLocalDateAndTypeCodeIsADVISORYNOTICE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.ADVISORY_NOTICE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.UNREPORTED.getNotificationStatus(),CORRESPOND_DATE_VO1, Notice.ADVISORY_NOTICE.getNotice());

        assertResult(vo, result);
        
    }
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-009/区分:I/チェック内容:Test Get Apartment Notification When Type code Is FIELD_SURVEY_NOTIFICATION
     * @throws BusinessException
     */

    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateGreaterThanLocalDateAndTypeCodeIsFIELDSURVEYNOTICE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.FIELD_SURVEY_NOTIFICATION.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.UNREPORTED.getNotificationStatus(),
                CORRESPOND_DATE_VO1, Notice.FIELD_SURVEY_NOTICE.getNotice());

        assertResult(vo, result);
        
    }
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-010/区分:E/チェック内容:Test Get Apartment Notification When Type code Is Other Code
     * @throws BusinessException
     */

    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateGreaterThanLocalDateAndTypeCodeIsOtherCode() throws BusinessException {
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = newNotificationVoCase1(NotificationStatus.UNREPORTED.getNotificationStatus(),null, null);
        assertNull(vo, result);
        
    }

    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-002/区分:N/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Less Than Local Date And Accept Status Is UNACCEPTED And Change Count Is 0 And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateLessThanLocalDateAndStatusIsUNACCEPTEDandCHANGECOUNTIsZEROAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE1);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.REPORTED_UNDER_REVIEW.getNotificationStatus(),
                CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());

        assertResult(vo, result);
        
    }
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-003/区分:N/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Less Than Local Date And Accept Status Is UNACCEPTED And Change Count Greater Than 0 And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateLessThanLocalDateAndStatusIsUNACCEPTEDandCHANGECOUNTIsNotZEROAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE1);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS, CHANGE_COUNT1);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.CHANGE_NOTIFICATION_COMPLETED_UNDER_REVIEW.getNotificationStatus(),
                CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());

        assertResult(vo, result);
        
    }
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-005/区分:N/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Less Than Local Date And Accept Status Is ACCEPTED And Change Count Greater Than 0 And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateLessThanLocalDateAndStatusIsACCEPTEDandCHANGECOUNTIsNotZEROAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE1);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS1, CHANGE_COUNT1);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = 
                newNotificationVoCase1(NotificationStatus.CHANGE_NOTIFICATION_ACCEPTED.getNotificationStatus(),
                CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());

        assertResult(vo, result);
        
    }  
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-004/区分:I/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Less Than Local Date And Accept Status Is ACCEPTED And Change Count Is 0 And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateLessThanLocalDateAndStatusIsACCEPTEDandCHANGECOUNTIsZEROAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException {
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE1);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS1, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = newNotificationVoCase1(NotificationStatus.REPORTED_ACCEPTED.getNotificationStatus(),
            CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());

        assertResult(vo, result);
        
    }    
    
    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-006/区分:E/チェック内容:Test Get Apartment Notification When NEXT_NOTIFICATION_DATE Less Than Local Date And Accept Status Is NOT_SPECIFIED And Type code Is NOTIFICATION_ACCEPTANCE
     * @throws BusinessException
     */
    @Test
    public void testGetMansionInformationByIdTblWhenNextNotificationDateLessThanLocalDateAndStatusIsNOTSPECIFIEDDandCHANGECOUNTIsZEROAndTypeCodeIsNOTIFICATIONACCEPTANCE() throws BusinessException{
        TBL100Entity entity100 = newTBL100Entity(APARTMENT_ID, NEXT_NOTIFICATION_DATE1);
        Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity100);
        
        TBL200Entity entity200 = newTBL200Entity(NEWEST_NOTIFICATION_NO, ACCEPT_STATUS2, CHANGE_COUNT);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity200);
        
        List<String> correspondDates = new ArrayList<String>();
        correspondDates.add(CORRESPOND_DATE_1);
        Mockito.when(tbl100DAO.getLatestProgressInformationDate(Mockito.anyString())).thenReturn(correspondDates);
        
        List<TBL300Entity> entity300s = 
                newTBL300Entitys(APARTMENT_ID, CORRESPOND_DATE_1, Integer.toString(TypeCode.NOTIFICATION_ACCEPTANCE.getCode()));
        Mockito.when(tbl300DAO.getProgressRecordFromDate(Mockito.anyString(), Mockito.anyString())).thenReturn(entity300s);
        NotificationVo vo = notificationLogic.getApartmentNotification(APARTMENT_ID);
        NotificationVo result = newNotificationVoCase1(null,CORRESPOND_DATE_VO1, Notice.ACCEPTANCE_NOTICE.getNotice());
        
        assertNull1(vo, result);
    
    }
    
    private void assertResult(NotificationVo vo, NotificationVo result) {
        assertThat(vo.getNotificationStatus()).isEqualTo(result.getNotificationStatus());
        for (int i = 0; i < result.getNoticeVos().size(); i++) {
            assertThat(vo.getNoticeVos().get(i).getCorresponDate()).isEqualTo(result.getNoticeVos().get(i).getCorresponDate());
            assertThat(vo.getNoticeVos().get(i).getNotice()).isEqualTo(result.getNoticeVos().get(i).getNotice());
        }
    }
    
    private void assertNull(NotificationVo vo, NotificationVo result) {
        assertThat(vo.getNotificationStatus()).isEqualTo(result.getNotificationStatus());
        assertThat(vo.getNoticeVos().size()).isEqualTo(0);
        
    }
    
    private void assertNull1(NotificationVo vo, NotificationVo result) {
        assertThat(vo.getNotificationStatus()).isEqualTo(result.getNotificationStatus());
        for (int i = 0; i < vo.getNoticeVos().size(); i++) {
            assertThat(vo.getNoticeVos().get(i).getCorresponDate()).isEqualTo(result.getNoticeVos().get(i).getCorresponDate());
            assertThat(vo.getNoticeVos().get(i).getNotice()).isEqualTo(result.getNoticeVos().get(i).getNotice());
        }
        
        
    }
    

}
