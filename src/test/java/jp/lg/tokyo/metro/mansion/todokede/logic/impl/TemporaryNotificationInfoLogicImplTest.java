/*
 * @(#) TemporaryNotificationInfoLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/12/26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import ch.qos.logback.classic.Level;
import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL205DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL215DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.SystemException;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class TemporaryNotificationInfoLogicImplTest {
    @InjectMocks
    private ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic = new TemporaryNotificationInfoLogicImpl();
    
    @Mock
    TBL100DAO tbl100Dao;
    
    @Mock
    private TBL205DAO tbl205DAO = Mockito.mock(TBL205DAO.class);
    @Mock
    private TBL215DAO tbl215DAO;
    @Mock
    private SequenceUtil sequenceUtil;
    @Captor
    private ArgumentCaptor<String> notificationNoCapture;

    public static final String TEMPORARY_NOTIFICATION_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.TemporaryNotificationInfoLogicImpl";
    private final String APARTMENT_ID = "1000000001";
    private final String ADDRESS = "tokyo";
    private final String APARTMENT_LOST_FLAG = "1";
    private final String APARTMENT_NAME = "abc";
    private final String APARTMENT_NAME_PHONETIC = "abc phonetic";
    private final String BUILD_DAY = "10";
    private final String BUILD_MONTH = "02";
    private final String BUILD_YEAR = "2020";
    private final double BUILDING_AREA = 0.1;
    private final String BUILDING_STRUCTURE = "0101010101";
    private final String CITY_NAME = "kyoto";
    private final int FLOOR_NUMBER = 111;
    private final int HOUSE_NUMBER = 222;
    private final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
    private final String NEWEST_NOTIFICATION_NAME = "osaka";
    private final String NEWEST_NOTIFICATION_NO = "123456";
    private final String NOTIFICATION_KBN = "1";
    private final String PRELIMINARY1 = "00001";
    private final String PRELIMINARY2 = "00002";
    private final String PRELIMINARY3 = "0000003";
    private final String PRELIMINARY4 = "00004";
    private final String PRELIMINARY5 = "00005";
    private final String PROPERTY_NUMBER = "0008";
    private final String REAL_ESTATE_NO = "1111111111113";
    private final String REGISTRATION_ADDRESS = "kyoto";
    private final String REGISTRATION_HOUSE_NO = "osaka";
    private final String REGISTRATION_NO = "000008";
    private final String REPASSWORD_MAIL_ADDRESS = "abcd@gmail.com";
    private final double SITE_AREA = 0.1;
    private final String SUPPORT = "1";
    private final double TOTAL_FLOOR_AREA = 0.1;
    private final String ZIP_CODE = "12345678";
    private final Timestamp UPDATE_DATETIME = null;
    private final String CITY_CODE = "111111";
    private final String MAIL_ADDRESS = "vutran26999@gmail.com";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "G00000011";

    /* Create TBL300Entity */
    private final String PROGRESS_RECORD_NO_1 = "000001";
    private final String APARTMENT_ID_1 = "2000000000";
    private final String CORRESPOND_DATE_1 = "201912040529";
    private final String CORRESPOND_TYPE_CODE_1 = "0";
    private final String DELETE_FLAG_1 = "0";
    private final String NOTICE_TYPE_CODE_1 = "1";
    private final String NOTIFICATION_METHOD_CODE_1 = "0";
    private final String PROGRESS_RECORD_DETAIL_1 = "abcdef";
    private final String PROGRESS_RECORD_OVERVIEW_1 = "0000004";
    private final String RELATED_NUMBER_1 = "2000000000";
    private final String SUPPORT_CODE_1 = "0";
    private final String TYPE_CODE_1 = "3";
    private final Timestamp UPDATE_DATETIME_1 = new Timestamp(new Date().getTime());
    private final String UPDATE_USER_ID_1 = "1111";
    
    private TBL100Entity generateTBL100Entity(String APARTMENT_ID) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
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
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setZipCode(ZIP_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        return entity;
    }

    
    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getTemporaryData-001／区分：N／観点ID：01／チェック内容：Test Get Temporary Data When ApartmentId And Status And TempKbn Are Invalid
     *
     */
    @Test
    public void getTemporaryData_whenApartmentIdAndStatusAndTempKbnAreInvalid_thenReturnNull() throws SystemException {
        String apartmentId = "123";
        String status = "1";
        String tempKbn = "1";
        Mockito.when(tbl205DAO.getTemporaryData(apartmentId, status, tempKbn, null)).thenReturn(null);
        Assert.assertNull(temporaryNotificationInfoLogic.getTemporaryData(apartmentId, status, tempKbn, true));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getTemporaryData-002／区分: I／観点ID：02／チェック内容：Test Get Temporary Data When ApartmentId And Status And TempKbn Are Valid
     *
     */
    @Test
    public void getTemporaryData_whenApartmentIdAndStatusAndTempKbnAreValid_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "10000001";
        String status = "1";
        String tempKbn = "3";
        String tempNo = "10000001";
        TBL205Entity entity = new TBL205Entity();
        entity.setId(new TBL205EntityPK(tempNo, apartmentId));
        Mockito.when(tbl205DAO.getTemporaryData(apartmentId, status, tempKbn, STR_1)).thenReturn(entity);
        Assert.assertEquals(apartmentId, temporaryNotificationInfoLogic.getTemporaryData(apartmentId, status, tempKbn, true).getApartmentId());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteAndSaveTemporaryData-001／区分：N／観点ID：03／チェック内容：Test Delete And Save Temporary Data When ScreenId Is MDA0110 And Is Created And TempKbn Is Null And Nothing Saved Temporary
     *
     */
    @Test
    public void deleteAndSaveTemporaryData_whenScreenIdIsMDA0110AndIsCreatedAndTempKbnIsNullAndNothingSavedTemporary_thenReturnSuccess() throws SystemException, InvocationTargetException, IllegalAccessException {
        String apartmentId = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setApartmentId(apartmentId);
        CurrentUserInformation user = new CurrentUserInformation();

        mockitoPrepareDataForDeleteAndSaveTemporaryData(apartmentId, null);
        Mockito.when(tbl205DAO.countByApartmentIdAndStatus(apartmentId, CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED))).thenReturn(NUM_0);

        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, null, SCREEN_ID_MDA0110);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteAndSaveTemporaryData-002／区分：N／観点ID：04／チェック内容：Test Delete And Save Temporary Data When ScreenId Is MDA0110 And Is Created And TempKbn Is Null And Have Saved Temporary Data
     *
     */
    @Test
    public void deleteAndSaveTemporaryData_whenScreenIdIsMDA0110AndIsCreatedAndTempKbnIsNullAndHaveSavedTemporaryData_thenReturnSuccess() throws SystemException, InvocationTargetException, IllegalAccessException {
        String apartmentId = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setApartmentId(apartmentId);
        CurrentUserInformation user = new CurrentUserInformation();

        mockitoPrepareDataForDeleteAndSaveTemporaryData(apartmentId, null);

        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, null, SCREEN_ID_MDA0110);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteAndSaveTemporaryData-003／区分：N／観点ID：05／チェック内容：Test Delete And Save Temporary Data When ScreenId Is MDA0110 And Is Updated And TempKbn Is Valid And Nothing Saved Temporary
     *
     */
    @Test
    public void deleteAndSaveTemporaryData_whenScreenIdIsMDA0110AndIsUpdatedAndTempKbnIsValidAndNothingSavedTemporary_thenReturnSuccess() throws SystemException, InvocationTargetException, IllegalAccessException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setNotificationNo(notificationNo);
        vo.setApartmentId(apartmentId);
        CurrentUserInformation user = new CurrentUserInformation();
        String tempKbn = "3";

        mockitoPrepareDataForDeleteAndSaveTemporaryData(apartmentId, tempKbn);
        Mockito.when(tbl205DAO.countByApartmentIdAndStatusAndTempKbn(apartmentId, CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED), tempKbn)).thenReturn(NUM_0);

        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, tempKbn, SCREEN_ID_MDA0110);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteAndSaveTemporaryData-004／区分：N／観点ID：06／チェック内容：Test Delete And Save Temporary Data When ScreenId Is MDA0110 And Is Updated And TempKbn Is Valid And Have Saved Temporary Data
     *
     */
    @Test
    public void deleteAndSaveTemporaryData_whenScreenIdIsMDA0110AndIsUpdatedAndTempKbnIsValidAndHaveSavedTemporaryData_thenReturnSuccess() throws SystemException, InvocationTargetException, IllegalAccessException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setNotificationNo(notificationNo);
        vo.setApartmentId(apartmentId);
        CurrentUserInformation user = new CurrentUserInformation();
        String tempKbn = "3";

        mockitoPrepareDataForDeleteAndSaveTemporaryData(apartmentId, tempKbn);

        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, tempKbn, SCREEN_ID_MDA0110);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteAndSaveTemporaryData-005／区分：N／観点ID：07／チェック内容：Test Delete And Save Temporary Data When ScreenId Is GDA0110 And Is Created And TempKbn Is Valid And Have Saved Temporary Data
     *
     */
    @Test
    public void deleteAndSaveTemporaryData_whenScreenIdIsGDA0110AndIsCreatedAndTempKbnIsValidAndHaveSavedTemporaryData_thenReturnSuccess() throws SystemException, InvocationTargetException, IllegalAccessException {
        String apartmentId = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setApartmentId(apartmentId);
        CurrentUserInformation user = new CurrentUserInformation();
        String tempKbn = "3";

        mockitoPrepareDataForDeleteAndSaveTemporaryData(apartmentId, tempKbn);

        temporaryNotificationInfoLogic.deleteAndSaveTemporaryData(vo, user, tempKbn, SCREEN_ID_GDA0110);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-004／区分：N／チェック内容：テストチェックは、一時的なデータを受け入れている
     */
    @Test
    public void checkWhenAcceptanceDataExistThenReturnTrue() {
        String newestNotificationNo = "newestNotificationNo";
        String tempKbn = "tempKbn";
        Mockito.when(tbl215DAO.countByNotificationNoAndTempKbn(newestNotificationNo, tempKbn)).thenReturn(1);
        boolean result = temporaryNotificationInfoLogic.isActiveAcceptanceRestoreButton(newestNotificationNo, tempKbn);
        Assert.assertTrue(result);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-005／区分：N／チェック内容：テストチェックは、受け入れに一時的なデータがないこと
     */
    @Test
    public void checkWhenAcceptanceHasNotDataExistThenReturnFalse() {
        String newestNotificationNo = "newestNotificationNo";
        String tempKbn = "tempKbn";
        Mockito.when(tbl215DAO.countByNotificationNoAndTempKbn(newestNotificationNo, tempKbn)).thenReturn(0);
        boolean result = temporaryNotificationInfoLogic.isActiveAcceptanceRestoreButton(newestNotificationNo, tempKbn);
        Assert.assertFalse(result);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-006／区分：N／チェック内容：物理的な受け入れ一時データの削除のテスト
     */
    @Test
    public void deletePhysicallyAcceptanceTemporaryData() {
        //Prepare data
        String notificationNo = "notificationNo";
        LogAppender logAppender = LogAppender.initialize(TEMPORARY_NOTIFICATION_PATH);

        //Execute
        temporaryNotificationInfoLogic.deleteAcceptanceTemporaryData(notificationNo);

        //Assert
        Mockito.verify(tbl215DAO, Mockito.only()).deleteByNotificationNo(notificationNoCapture.capture());
        assertThat(notificationNoCapture.getValue()).isEqualTo(notificationNo);
        assertThat(logAppender.getEvents().get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL215、キー：notificationNo");
    }

    private void mockitoPrepareDataForDeleteAndSaveTemporaryData(String apartmentId, String tempKbn) {
        String status = CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED);
        int count = 1;

        String tempNo = "10000000002";
        TBL205Entity entity = new TBL205Entity();
        entity.setId(new TBL205EntityPK(tempNo, apartmentId));

        Mockito.when(tbl205DAO.countByApartmentIdAndStatus(apartmentId, status)).thenReturn(count);
        Mockito.doNothing().when(tbl205DAO).deleteByApartmentIdAndStatus(apartmentId, status);
        Mockito.when(tbl205DAO.countByApartmentIdAndStatusAndTempKbn(apartmentId, status, tempKbn)).thenReturn(count);
        Mockito.doNothing().when(tbl205DAO).deleteByApartmentIdAndStatusAndTempKbn(apartmentId, status, tempKbn);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL205), COL_TEMP_NO)).thenReturn(tempNo);
        Mockito.when(tbl205DAO.save(Mockito.any())).thenReturn(entity);
    }
    
    @Test
    public void testGetAcceptanceTemporaryData () {
        //prepare data
        String notificationNo = "notificationNo";
        String tempKbn ="tempKbn";
        
        //execute
        temporaryNotificationInfoLogic.getAcceptanceTemporaryData(notificationNo, tempKbn);
    }
    
    @Test
    public void testSaveTemporaryAcceptanceWhenSuccess () throws BusinessException {

        TBL100Entity tbl100Entity = generateTBL100Entity(APARTMENT_ID);
        TBL215Entity tbl215Entity = new TBL215Entity();
        tbl215Entity.setTempNo("1");
        
        String tempKbn = "3";
        NotificationAcceptanceForm form = new NotificationAcceptanceForm();
        Mockito.when(sequenceUtil.generateKey(Mockito.anyString(), Mockito.anyString())).thenReturn("1");
        CurrentUserInformation user = new CurrentUserInformation();
        Mockito.when(tbl100Dao.getMansionInformationById(Mockito.anyString())).thenReturn(tbl100Entity);
        Mockito.when(tbl215DAO.save(Mockito.any())).thenReturn(tbl215Entity);
        temporaryNotificationInfoLogic.saveTemporaryAcceptance(APARTMENT_ID, form, tempKbn, user);
    }
    
    @Test(expected = BusinessException.class)
    public void testSaveTemporaryAcceptanceWhenTBL100EntityIsNull () throws BusinessException {
        String tempKbn = "3";
        NotificationAcceptanceForm form = new NotificationAcceptanceForm();
        CurrentUserInformation user = new CurrentUserInformation();
        Mockito.when(tbl100Dao.getMansionInformationById(Mockito.anyString())).thenReturn(null);

        temporaryNotificationInfoLogic.saveTemporaryAcceptance(APARTMENT_ID, form, tempKbn, user);
    }
}