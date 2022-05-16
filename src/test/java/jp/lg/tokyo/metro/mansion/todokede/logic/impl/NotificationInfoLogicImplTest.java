/*
 * @(#) NotificationInfoLogicImplTest.java
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
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.DeleteFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.*;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.*;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.INotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.EMailInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class NotificationInfoLogicImplTest {
    @InjectMocks
    private INotificationInfoLogic notificationInfoLogic = new NotificationInfoLogicImpl();
    @Mock
    private SequenceUtil sequenceUtil;
    @Mock
    private TBL200DAO tbl200DAO;
    @Mock
    private TBL300DAO tbl300DAO;
    @Mock
    private TBL100DAO tbl100DAO;
    @Mock
    private TBL105DAO tbl105DAO;
    @Mock
    private TBL210DAO tbl210DAO;
    @Mock
    private TBL110DAO tbl110DAO;
    @Mock
    private IEMailLogic mailLogic;
    @Mock
    private ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Captor
    private ArgumentCaptor<TBL210Entity> tbl210EntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringCapture;
    @Captor
    private ArgumentCaptor<EMailInfoVo> eMailInfoVoCaptor;
    @Captor
    private ArgumentCaptor<Map<String, Object>> mapArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<File>> listArgumentCaptor;

    public static final String NOTIFICATION_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.NotificationInfoLogicImpl";

    @Before
    public void init() {
        LogAppender.pauseTillLogbackReady();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.NEXT_NOTIFICATION_DATE_MAX_KEY, "20251212");
        systemSettings.put(CommonConstants.NOTIFICATION_RENEWAL_PERIOD_KEY, "5");
        systemSettings.put(CommonConstants.APARTMENT_LOGIN_URL, APARTMENT_LOGIN_URL);
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);

    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-001／区分：N／観点ID：01／チェック内容：Test Get Notification Information When NotificationNo Is Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsBlank_thenReturnNull() throws SystemException {
        String notificationNo = "";
        Assert.assertNull(notificationInfoLogic.getNotificationInfo(notificationNo));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-002／区分：N／観点ID：02／チェック内容：Test Get Notification Information When NotificationNo Is Invalid
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsInvalid_thenReturnNull() throws SystemException {
        String notificationNo = "123";
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(null);
        Assert.assertNull(notificationInfoLogic.getNotificationInfo(notificationNo));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-003／区分: I／観点ID：03／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ZipCode is Blank
     *
     */
    @Test()
    public void getNotificationInfo_whenNotificationNoIsValidAndZipCodeIsBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-004／区分：N／観点ID：04／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ZipCode Is Not Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsValidAndZipCodeIsNotBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        entity.setZipCode("131-041");
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-005／区分：N／観点ID：05／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ManagerZipCode Is Not Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsValidAndManagerZipCodeIsNotBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        entity.setZipCode("131-041");
        entity.setManagerZipCode("999-5555");
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-006／区分：N／観点ID：06／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ContactZipCode Is Not Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsValidAndContactZipCodeIsNotBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        entity.setZipCode("131-041");
        entity.setManagerZipCode("999-5555");
        entity.setContactZipCode("132-041");
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-007／区分: I／観点ID：07／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ManagerTelNo Is Not Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsValidAndManagerTelNoIsNotBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        entity.setZipCode("131-041");
        entity.setManagerZipCode("999-5555");
        entity.setContactZipCode("132-041");
        entity.setManagerTelNo("111-222-3333");
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getNotificationInfo-008／区分：N／観点ID：08／チェック内容：Test Get Notification Information When NotificationNo Is Valid And ContactTelNo Is Not Blank
     *
     */
    @Test
    public void getNotificationInfo_whenNotificationNoIsValidAndContactTelNoIsNotBlank_thenReturnNotificationInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        String notificationNo = "1000000001";
        TBL200Entity entity = new TBL200Entity();
        entity.setId(new TBL200EntityPK(notificationNo, apartmentId));
        entity.setZipCode("131-041");
        entity.setManagerZipCode("999-5555");
        entity.setContactZipCode("132-041");
        entity.setManagerTelNo("111-222-3333");
        entity.setContactTelNo("111-222-3333");
        Mockito.when(tbl200DAO.getNotificationInfo(notificationNo)).thenReturn(entity);
        Assert.assertEquals(notificationNo, notificationInfoLogic.getNotificationInfo(notificationNo).getNotificationNo());
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteTemporaryAndSaveNotification-001／区分：N／観点ID：09／チェック内容：Test Delete Temporary And Save Notification When ScreenId Is MDA0110 And IsCreated And Input Are Valid
     *
     */
    @Test
    public void deleteTemporaryAndSaveNotification_WhenScreenIdIsMDA0110AndIsCreatedAndInputsAreValid_thenReturnSuccess() throws SystemException, BusinessException {
        String apartmentId = "10000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setApartmentId(apartmentId);

        CurrentUserInformation user = new CurrentUserInformation();
        String nextNotificationDateMax = "20251202";
        String notificationRenewalPeriod = "5";

        mockitoPrepareDataForDeleteTemporaryAndSaveNotification(vo);
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_MDA0110, user, nextNotificationDateMax, notificationRenewalPeriod);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteTemporaryAndSaveNotification-002／区分：N／観点ID：10／チェック内容：Test Delete Temporary And Save Notification When ScreenId Is MDA0110 And IsUpdated And Input Are Valid
     *
     */
    @Test
    public void deleteTemporaryAndSaveNotification_WhenScreenIdIsMDA0110AndIsUpdatedAndInputsAreValid_thenReturnSuccess() throws SystemException, BusinessException {
        String apartmentId = "10000001";
        String notificationNo = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setNotificationNo(notificationNo);
        vo.setApartmentId(apartmentId);
        vo.setNotificationDate(LocalDate.now());
        vo.setBuiltDate(LocalDate.now());
        vo.setContactMailAddress("lyly@test.com");
        vo.setChangeReasonCode("2");

        CurrentUserInformation user = new CurrentUserInformation();
        String nextNotificationDateMax = "20251202";
        String notificationRenewalPeriod = "5";

        mockitoPrepareDataForDeleteTemporaryAndSaveNotification(vo);
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_MDA0110, user, nextNotificationDateMax, notificationRenewalPeriod);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteTemporaryAndSaveNotification-003／区分：N／観点ID：11／チェック内容：Test Delete Temporary And Save Notification When ScreenId Is GDA0110 And IsCreated And Input Are Valid
     *
     */
    @Test
    public void deleteTemporaryAndSaveNotification_WhenScreenIdIsGDA0110AndIsCreatedAndInputsAreValid_thenReturnSuccess() throws SystemException, BusinessException {
        String apartmentId = "10000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setApartmentId(apartmentId);
        vo.setNotificationDate(LocalDate.now());
        vo.setBuiltDate(LocalDate.now());

        CurrentUserInformation user = new CurrentUserInformation();
        String nextNotificationDateMax = "20251202";
        String notificationRenewalPeriod = "5";

        mockitoPrepareDataForDeleteTemporaryAndSaveNotification(vo);
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_GDA0110, user, nextNotificationDateMax, notificationRenewalPeriod);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteTemporaryAndSaveNotification-004／区分：N／観点ID：12／チェック内容：Test Delete Temporary And Save Notification When ScreenId Is GDA0110 And IsUpdated And Input Are Valid
     *
     */
    @Test
    public void deleteTemporaryAndSaveNotification_WhenScreenIdIsGDA0110AndIsUpdatedAndInputsAreValid_thenReturnSuccess() throws SystemException, BusinessException {
        String apartmentId = "10000001";
        String notificationNo = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setNotificationNo(notificationNo);
        vo.setApartmentId(apartmentId);
        vo.setNotificationDate(LocalDate.now());
        vo.setBuiltDate(LocalDate.now());
        vo.setContactMailAddress("lyly@test.com");
        vo.setChangeReasonCode("2");

        CurrentUserInformation user = new CurrentUserInformation();
        String nextNotificationDateMax = "20251202";
        String notificationRenewalPeriod = "5";

        mockitoPrepareDataForDeleteTemporaryAndSaveNotification(vo);
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_GDA0110, user, nextNotificationDateMax, notificationRenewalPeriod);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：deleteTemporaryAndSaveNotification-005／区分：N／観点ID：13／チェック内容：Test Delete Temporary And Save Notification When ScreenId Is GJA0120 And Input Are Valid
     *
     */
    @Test
    public void deleteTemporaryAndSaveNotification_WhenScreenIdIsGJA0120AndInputsAreValid_thenReturnSuccess() throws SystemException, BusinessException {
        String apartmentId = "10000001";
        String notificationNo = "1000000001";
        NotificationInfoVo vo = new NotificationInfoVo();
        vo.setNotificationNo(notificationNo);
        vo.setApartmentId(apartmentId);
        vo.setNotificationDate(LocalDate.now());
        vo.setBuiltDate(LocalDate.now());
        vo.setContactMailAddress("lyly@test.com");
        vo.setChangeReasonCode("2");

        CurrentUserInformation user = new CurrentUserInformation();
        String nextNotificationDateMax = "20251202";
        String notificationRenewalPeriod = "5";

        mockitoPrepareDataForDeleteTemporaryAndSaveNotification(vo);
        notificationInfoLogic.deleteTemporaryAndSaveNotification(vo, SCREEN_ID_GJA0120, user, nextNotificationDateMax, notificationRenewalPeriod);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getDataFormCsv-001／区分：N／観点ID：14／チェック内容：Test Get Data Form Csv When File Is Valid
     *
     */
    @Test
    public void getDataFormCsv_WhenFileIsValid_thenReturnSuccess() throws IOException {
        String fileName = "D:\\Projects\\MANSION\\1.WIP\\02.Documents\\VN\\02.Q&A\\TemplateImportMDA0110_FL070.csv";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("user-file", fileName,
                "text/plain", "test data".getBytes());
        byte[] file = mockMultipartFile.getBytes();
        Assert.assertNotNull(notificationInfoLogic.getDataFormCsv(file));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getDataFormCsv-002／区分：E／観点ID：15／チェック内容：Test Get Data Form Csv When File Is Invalid
     *
     */
    @Test(expected = Exception.class)
    public void getDataFormCsv_WhenFileIsInvalid_thenReturnError() {
        LogAppender logAppender = LogAppender.initialize(NOTIFICATION_PATH);
        Assert.assertNull(notificationInfoLogic.getDataFormCsv(null));
        assertThat(logAppender.getEvents().get(0).getLevel()).isEqualTo(Level.ERROR);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：exportCsv-001／区分：N／観点ID：16／チェック内容：Test Export Csv When Output Stream Is Valid
     *
     */
    @Test()
    public void exportCsv_WhenOutputStreamIsInvalid_thenReturnTrue() throws IOException {
        NotificationExportForm form = this.generateNotificationExportForm();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setHeader("Content-Type", "text/csv");
        Assert.assertTrue(notificationInfoLogic.exportCsv(form, response.getOutputStream()));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：exportCsv-002／区分：N／観点ID：17／チェック内容：Test Export Csv When Output Stream Is Null
     *
     */
    @Test()
    public void exportCsv_WhenOutputStreamIsNull_thenReturnFalse() throws IOException {
        NotificationExportForm form = this.generateNotificationExportForm();
        Assert.assertFalse(notificationInfoLogic.exportCsv(form, null));
    }

    /**
     *案件ID：GDA0110／チェックリストID：UT-GDA0110-008／区分：E／チェック内容：apartmentIdが見つからない場合の受諾通知をテストする
     */
    @Test(expected = BusinessException.class)
    public void acceptNotificationWhenApartmentNotFoundById_thenThrowBusinessException() throws SystemException, BusinessException {
        String apartmentId = "1111111";
        NotificationRegistrationForm registrationForm = new NotificationRegistrationForm();
        NotificationAcceptanceForm acceptanceForm = new NotificationAcceptanceForm();
        CurrentUserInformation userLoggedInInfo = new CurrentUserInformation();
        notificationInfoLogic.saveAcceptanceNotification(apartmentId, registrationForm, acceptanceForm, userLoggedInInfo);
    }

    /**
     *案件ID：GDA0110／チェックリストID：UT-GDA0110-009／区分：N／チェック内容：建物を失ったときの通知受諾テスト
     */
    @Test
    public void acceptNotification_whenLostBuilding() throws SystemException, BusinessException {
        //Prepare data
        String tbl105Key = "1000000003";
        String tbl200NotificationNo = "1000000007";
        String tbl300Key = "1000000004";
        String FAX_NOTIFICATION = "2";
        String LOST_BUILDING = "2";

        TBL100Entity tbl100Entity = prepareTBL100GDA0110_009();
        TBL300Entity tbl300Entity = prepareTBL300GDA0110_009();
        TBL200Entity tbl200Entity = prepareTBL200GDA0110_009();
        TBL110Entity tbl110Entity = prepareTBL110GDA0110_009();
        NotificationRegistrationForm registrationForm = generateNotificationRegistrationForm();
        NotificationAcceptanceForm acceptanceForm = generateNotificationAcceptanceForm();
        CurrentUserInformation userLoggedInInfo = generateCurrentUserInformation();
        TBL210Entity tbl210Entity = prepareTBL210GDA0110_009(NOTIFICATION_NO, acceptanceForm, USER_ID, USER_DISPLAY_NAME);

        registrationForm.getInfoAreaCommon().setRdoChangeReason(LOST_BUILDING);

        //Mock data
        Mockito.when(tbl210DAO.save(Mockito.any())).thenReturn(tbl210Entity);
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(tbl100Entity);
        Mockito.when(tbl200DAO.save(Mockito.any())).thenReturn(tbl200Entity);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(tbl300Entity);
        Mockito.when(tbl110DAO.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110Entity);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL105"), CommonConstants.COL_HISTORY_NUMBER)).thenReturn(tbl105Key);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL200"), COL_NOTIFICATION_NO)).thenReturn(tbl200NotificationNo);
        Mockito.when(sequenceUtil.generateKey(TBL210, COL_ACCEPT_NO)).thenReturn(TBL210_KEY);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL300"), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn(tbl300Key);
        Mockito.when(tbl300DAO.findByByApartmentIdAndRelatedNumber(Mockito.anyString(), Mockito.anyString())).thenReturn(generateResultTBL300GDA0110_009());

        //Execute
        notificationInfoLogic.saveAcceptanceNotification(APARTMENT_ID, registrationForm, acceptanceForm, userLoggedInInfo);

        //Assert result
        Mockito.verify(tbl210DAO, Mockito.times(1)).save(tbl210EntityArgumentCaptor.capture());
        TBL210Entity result = tbl210EntityArgumentCaptor.getValue();

        assertResultGDA0110_009(result, FAX_NOTIFICATION);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-009／区分: I／チェック内容：通知方法が電子メールの場合、通知の受け入れをテストする
     */
    @Test
    public void acceptNotification_whenNotificationMethodIsEmail() throws SystemException, BusinessException {
        //Prepare data
        String tbl105Key = "1000000003";
        String tbl200NotificationNo = "1000000007";
        String tbl300Key = "1000000004";
        String EMAIL_NOTIFICATION = "1";
        String LOST_BUILDING = "2";

        TBL100Entity tbl100Entity = prepareTBL100GDA0110_009();
        TBL300Entity tbl300Entity = prepareTBL300GDA0110_009();
        TBL200Entity tbl200Entity = prepareTBL200GDA0110_009();
        TBL110Entity tbl110Entity = prepareTBL110GDA0110_009();
        NotificationRegistrationForm registrationForm = generateNotificationRegistrationForm();
        NotificationAcceptanceForm acceptanceForm = generateNotificationAcceptanceForm();
        CurrentUserInformation userLoggedInInfo = generateCurrentUserInformation();
        TBL210Entity tbl210Entity = prepareTBL210GDA0110_009(NOTIFICATION_NO, acceptanceForm, USER_ID, USER_DISPLAY_NAME);

        ML040Vo mailVo = prepareMailVo();

        registrationForm.getInfoAreaCommon().setRdoChangeReason(LOST_BUILDING);
        acceptanceForm.setRdoNotificationMethod(EMAIL_NOTIFICATION);

        //Mock data
        Mockito.when(tbl210DAO.save(Mockito.any())).thenReturn(tbl210Entity);
        Mockito.when(tbl100DAO.getMansionInformationById(APARTMENT_ID)).thenReturn(tbl100Entity);
        Mockito.when(tbl200DAO.save(Mockito.any())).thenReturn(tbl200Entity);
        Mockito.when(tbl100DAO.save(Mockito.any())).thenReturn(tbl100Entity);
        Mockito.when(tbl110DAO.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110Entity);
        Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(tbl300Entity);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL105"), CommonConstants.COL_HISTORY_NUMBER)).thenReturn(tbl105Key);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL200"), COL_NOTIFICATION_NO)).thenReturn(tbl200NotificationNo);
        Mockito.when(sequenceUtil.generateKey(TBL210, COL_ACCEPT_NO)).thenReturn(TBL210_KEY);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty("TBL300"), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn(tbl300Key);
        Mockito.when(tbl300DAO.findByByApartmentIdAndRelatedNumber(Mockito.anyString(), Mockito.anyString())).thenReturn(generateResultTBL300GDA0110_009());
        Mockito.when(mailLogic.getCommonTemplateMail(ML040Vo.class)).thenReturn(mailVo);

        //Execute
        notificationInfoLogic.saveAcceptanceNotification(APARTMENT_ID, registrationForm, acceptanceForm, userLoggedInInfo);

        //Get result
        Mockito.verify(tbl210DAO, Mockito.times(1)).save(tbl210EntityArgumentCaptor.capture());
        Mockito.verify(mailLogic, Mockito.times(1)).sendMailWithContent(
                stringCapture.capture(), eMailInfoVoCaptor.capture(), mapArgumentCaptor.capture(), listArgumentCaptor.capture());

        TBL210Entity result = tbl210EntityArgumentCaptor.getValue();

        //Assert result
        assertResultGDA0110_009(result, EMAIL_NOTIFICATION);
        assertThat(eMailInfoVoCaptor.getValue().getSubject()).isEqualTo(MAIL_SUBJECT);
        assertThat(mapArgumentCaptor.getValue().get(CommonConstants.ML_LOGIN_URL)).isEqualTo(APARTMENT_LOGIN_URL);
        assertThat(listArgumentCaptor.getValue()).isNull();
    }

    private ML040Vo prepareMailVo() {
        ML040Vo mailVo = new ML040Vo();
        mailVo.setContactMailAddress(MAIL_CONTACT_ADDRESS);
        mailVo.setMailSubject(MAIL_SUBJECT);
        return mailVo;
    }

    private TBL110Entity prepareTBL110GDA0110_009() {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(APARTMENT_ID);
        return entity;
    }

    private void assertResultGDA0110_009(TBL210Entity entity, String notificationMethod) {
        assertThat(entity.getAcceptNo()).isEqualTo(TBL210_KEY);
        assertThat(entity.getNotificationNo()).isEqualTo(NOTIFICATION_NO);
        assertThat(entity.getAcceptUserId()).isEqualTo(USER_ID);
        assertThat(entity.getAppendixNo()).isEqualTo(TXT_APPENDIX_NO);
        assertThat(entity.getDocumentNo()).isEqualTo(TXT_DOCUMENT_NO);
        assertThat(entity.getNoticeDate()).isEqualTo(LocalDate.parse(CAL_NOTICE_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        assertThat(entity.getRecipientNameApartment()).isEqualTo(LBL_RECIPIENT_NAME_APARTMENT);
        assertThat(entity.getRecipientNameUser()).isEqualTo(LBL_RECIPIENT_NAME_USER);
        assertThat(entity.getSender()).isEqualTo(TXT_SENDER);
        assertThat(entity.getNotificationDate()).isEqualTo(LocalDate.parse(CAL_NOTIFICATION_DAY, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        assertThat(entity.getEvidenceBar()).isEqualTo("1");
        assertThat(entity.getEvidenceNo()).isEqualTo(LBL_EVIDENCE_NO);
        assertThat(entity.getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(entity.getNotificationMethodCode()).isEqualTo(notificationMethod);
        assertThat(entity.getDeleteFlag()).isEqualTo("0");
    }

    private List<TBL300Entity> generateResultTBL300GDA0110_009() {
        List<TBL300Entity> result = new ArrayList<>();
        TBL300Entity entity = new TBL300Entity();
        entity.setProgressRecordNo(PROGRESS_RECORD_NO);
        result.add(entity);
        return result;
    }

    private TBL200Entity prepareTBL200GDA0110_009() {
        TBL200Entity entity = new TBL200Entity();
        TBL200EntityPK pk = new TBL200EntityPK();
        pk.setApartmentId(APARTMENT_ID);
        pk.setNotificationNo(NOTIFICATION_NO);
        entity.setId(pk);
        return entity;
    }

    private CurrentUserInformation generateCurrentUserInformation() {
        CurrentUserInformation information = new CurrentUserInformation();
        information.setUserId(USER_ID);
        information.setUserTypeCode(UserTypeCode.TOKYO_STAFF);
        information.setDisplayName(USER_DISPLAY_NAME);
        information.setLoginId(LOGIN_ID);
        return information;
    }

    private NotificationAcceptanceForm generateNotificationAcceptanceForm() {
        NotificationAcceptanceForm form = new NotificationAcceptanceForm();
        form.setRdoNotificationMethod(RDO_NOTIFICATION_METHOD);
        form.setTxtAppendixNo(TXT_APPENDIX_NO);
        form.setTxtDocumentNo(TXT_DOCUMENT_NO);
        form.setCalNoticeDate(CAL_NOTICE_DATE);
        form.setLblRecipientNameApartment(LBL_RECIPIENT_NAME_APARTMENT);
        form.setLblRecipientNameUser(LBL_RECIPIENT_NAME_USER);
        form.setTxtSender(TXT_SENDER);
        form.setLblNotificationDate(CAL_NOTIFICATION_DAY);
        form.setLblEvidenceBar(LBL_EVIDENCE_BAR);
        form.setLstEvidenceNo(LBL_EVIDENCE_NO);
        form.setTxaRemarks(TXA_REMARKS);
        form.setTxaOrrectionDetails(TXA_CORRECTION_DETAIL);
        form.setRdoNotificationMethod(RDO_NOTIFICATION_METHOD);
        return form;
    }

    private TBL300Entity prepareTBL300GDA0110_009() {
        TBL300Entity tbl300Entity = new TBL300Entity();
        tbl300Entity.setProgressRecordNo("");
        tbl300Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        return tbl300Entity;
    }

    private TBL210Entity prepareTBL210GDA0110_009(String notificationNo,
                                                  NotificationAcceptanceForm form,
                                                  String acceptedUserId,
                                                  String acceptedUsername) {
        final String NOT_CORRECTION = "0";
        final String CORRECTION = "1";
        TBL210Entity entity = new TBL210Entity();
        String entityId = "1000000007";
        entity.setAcceptNo(entityId);
        entity.setNotificationNo(notificationNo);
        entity.setAcceptTime(LocalDateTime.of(2019,12,12,12,12,12));
        entity.setAcceptUserId(acceptedUserId);
        entity.setAcceptUserName(acceptedUsername);
        entity.setAppendixNo(form.getTxtAppendixNo());
        entity.setDocumentNo(form.getTxtDocumentNo());
        entity.setNoticeDate(LocalDate.parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setRecipientNameApartment(form.getLblRecipientNameApartment());
        entity.setRecipientNameUser(form.getLblRecipientNameUser());
        entity.setSender(form.getTxtSender());
        entity.setNotificationDate(LocalDate.parse(form.getLblNotificationDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setEvidenceBar("15".equals(form.getLblEvidenceBar()) ? "1" : "2");
        entity.setEvidenceNo(form.getLstEvidenceNo());
        entity.setRemarks(form.getTxaRemarks());
        entity.setAuthorityModifyFlag(StringUtils.isEmpty(form.getTxaOrrectionDetails()) ? NOT_CORRECTION : CORRECTION);
        entity.setModifyDetails(form.getTxaOrrectionDetails());
        entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId(acceptedUserId);
        entity.setUpdateDatetime(Timestamp.valueOf(LocalDateTime.of(2019,12,12,12,12,12)));
        return entity;
    }

    private TBL100Entity prepareTBL100GDA0110_009() {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId("1000000001");
        entity.setPropertyNumber("013");
        entity.setApartmentName("ApartmentName");
        entity.setApartmentNamePhonetic("アパート");
        entity.setZipCode("192-0032");
        entity.setCityCode("131059");
        entity.setAddress("Address");
        entity.setRepasswordMailAddress("repassword@mail.com");
        entity.setMailAddress("mail@gmail.com");
        entity.setNewestNotificationNo("1000000001");
        entity.setNewestNotificationName("Demo");
        entity.setNewestNotificationAddress("Newest Notification Address");
        entity.setNotificationKbn("1");
        entity.setSupport("1");
        entity.setNextNotificationDate(LocalDate.of(2025,2,2));
        entity.setBuildDay("10");
        entity.setBuildMonth("10");
        entity.setBuildYear("2018");
        entity.setFloorNumber(10);
        entity.setHouseNumber(10);
        entity.setSiteArea(100.5);
        entity.setTotalFloorArea(1005);
        entity.setBuildingArea(2005);
        entity.setApartmentLostFlag("0");
        entity.setDeleteFlag("0");
        return entity;
    }

    private void mockitoPrepareDataForDeleteTemporaryAndSaveNotification(NotificationInfoVo vo) {
        String apartmentId = vo.getApartmentId();
        String notificationNo = "1000000001";
        String receptionNo = "100000011";

        TBL200Entity tbl200Entity = new TBL200Entity();
        tbl200Entity.setId(new TBL200EntityPK(notificationNo, apartmentId));

        String progressRecordNo = "1000000007";
        TBL300Entity tbl300Entity = new TBL300Entity();
        tbl300Entity.setProgressRecordNo(progressRecordNo);
        tbl300Entity.setApartmentId(apartmentId);

        TBL100Entity tbl100Entity = new TBL100Entity();
        tbl100Entity.setApartmentId(apartmentId);

        String historyNumber = "100000002";
        TBL105Entity tbl105Entity = new TBL105Entity();
        tbl105Entity.setHistoryNumber(historyNumber);
        tbl105Entity.setApartmentId(apartmentId);

        Mockito.doNothing().when(temporaryNotificationInfoLogic).deleteTemporarySavedData(apartmentId, CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED), null);

        Mockito.when(tbl200DAO.getNotificationInfo(vo.getNotificationNo())).thenReturn(tbl200Entity);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL200), COL_RECEPTION_NO)).thenReturn(receptionNo);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL200), COL_NOTIFICATION_NO)).thenReturn(notificationNo);
        Mockito.when(tbl200DAO.save(Mockito.any())).thenReturn(tbl200Entity);

        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn(progressRecordNo);
        Mockito.when(tbl300DAO.save(tbl300Entity)).thenReturn(tbl300Entity);

        Mockito.when(tbl100DAO.getMansionInformationById(vo.getApartmentId())).thenReturn(tbl100Entity);
        Mockito.when(tbl100DAO.save(tbl100Entity)).thenReturn(tbl100Entity);

        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL105), CommonConstants.COL_HISTORY_NUMBER)).thenReturn(historyNumber);
        Mockito.when(tbl105DAO.save(tbl105Entity)).thenReturn(tbl105Entity);
    }

    private NotificationRegistrationForm generateNotificationRegistrationForm() {
        BasicReportInfoForm basicReportInfoForm = new BasicReportInfoForm();
        basicReportInfoForm.setTxtApartmentZipCode1(TXT_APARTMENT_ZIP_CODE_1);
        basicReportInfoForm.setTxtApartmentZipCode2(TXT_APARTMENT_ZIP_CODE_2);
        basicReportInfoForm.setTxtApartmentAddress2(TXT_APARTMENT_ADDRESS_2);

        NotificationInfoAreaCommonForm infoAreaCommon = new NotificationInfoAreaCommonForm();
        infoAreaCommon.setRdoNotificationType(RDO_NOTIFICATION_TYPE);
        infoAreaCommon.setCalNotificationDate(CAL_NOTIFICATION_DATE);
        infoAreaCommon.setTxtNotificationGroupName(TXT_NOTIFICATION_GROUP_NAME);
        infoAreaCommon.setTxtNotificationPersonName(TXT_NOTIFICATION_PERSON_NAME);
        infoAreaCommon.setRdoLostElseReasonCode(RDO_LOST_ELSE_REASON_CODE);
        infoAreaCommon.setRdoChangeReason(RDO_CHANGE_REASON);
        infoAreaCommon.setTxtLostElseReasonElse(TXT_LOST_REASON);
        infoAreaCommon.setRdoGroupYesno(RDO_GROUP_YESNO);
        infoAreaCommon.setTxtApartmentNumber(TXT_APARTMENT_NUMBER);
        infoAreaCommon.setRdoGroupForm(RDO_GROUP_FORM);
        infoAreaCommon.setTxtGroupFormElse(TXT_GROUP_FORM_ELSE);
        infoAreaCommon.setTxtHouseNumber(TXT_HOUSE_NUMBER);
        infoAreaCommon.setTxtFloorNumber(TXT_FLOOR_NUMBER);
        infoAreaCommon.setCalBuiltDate(CAL_BUILT_DATE);
        infoAreaCommon.setRdoLandRights(RDO_LAND_RIGHT);
        infoAreaCommon.setTxtLandRightsElse(TXT_LAND_RIGHT_ELSE);
        infoAreaCommon.setRdoUsefor(RDO_USER_FOR);
        infoAreaCommon.setTxtUseforElse(TXT_USER_FOR_ELSE);
        infoAreaCommon.setRdoManagementForm(RDO_MANAGEMENT_FORM);
        infoAreaCommon.setTxtManagementFormElse(TXT_MANAGEMENT_FORM_ELSE);
        infoAreaCommon.setTxtManagerName(TXT_MANAGER_NAME);
        infoAreaCommon.setTxtManagerNamePhonetic(TXT_MANAGER_NAME_PHONETIC);
        infoAreaCommon.setTxtManagerZipCode1(TXT_MANAGER_ZIP_CODE_1);
        infoAreaCommon.setTxtManagerZipCode2(TXT_MANAGER_ZIP_CODE_2);
        infoAreaCommon.setTxtManagerAddress(TXT_MANAGER_ADDRESS);
        infoAreaCommon.setTxtManagerTelno1(TXT_MANAGER_TEL_NO_1);
        infoAreaCommon.setTxtManagerTelno2(TXT_MANAGER_TEL_NO_2);
        infoAreaCommon.setTxtManagerTelno3(TXT_MANAGER_TEL_NO_3);
        infoAreaCommon.setRdoGroup(RDO_GROUP);
        infoAreaCommon.setRdoManager(RDO_MANAGEMENT);
        infoAreaCommon.setRdoRule(RDO_RULE);
        infoAreaCommon.setTxtRuleChangeYear(TXT_RULE_CHANGE_YEAR);
        infoAreaCommon.setRdoOneyearOver(RDO_ONE_YEAR_OVER);
        infoAreaCommon.setRdoMinutes(RDO_MINUTES);
        infoAreaCommon.setRdoManagementCost(RDO_MANAGEMENT_COST);
        infoAreaCommon.setRdoRepairCost(RDO_REPAIR_COST);
        infoAreaCommon.setTxtRepairMonthlycost(TXT_REPAIR_MONTHLY_COST);
        infoAreaCommon.setRdoRepairPlan(RDO_REPAIR_PLAN);
        infoAreaCommon.setTxtRepairNearestYear(TXT_REPAIR_NEAREST_YEAR);
        infoAreaCommon.setRdoLongRepairPlan(RDO_LONG_REPAIR_PLAN);
        infoAreaCommon.setTxtLongRepairPlanYear(TXT_LONG_REPAIR_PLAN_YEAR);
        infoAreaCommon.setTxtLongRepairPlanPeriod(TXT_LONG_REPAIR_PLAN_PERIOD);
        infoAreaCommon.setTxtLongRepairPlanYearFrom(TXT_LONG_REPAIR_PLAN_YEAR_FROM);
        infoAreaCommon.setTxtLongRepairPlanYearTo(TXT_LONG_REPAIR_PLAN_YEAR_TO);
        infoAreaCommon.setRdoArrearageRule(RDO_ARREARAGE_RULE);
        infoAreaCommon.setRdoSegment(RDO_SEGMENT);
        infoAreaCommon.setRdoEmptyPercent(RDO_EMPTY_PERCENT);
        infoAreaCommon.setTxtEmptyNumber(TXT_EMPTY_NUMBER);
        infoAreaCommon.setRdoRentalPercent(RDO_RENTAL_PERCENT);
        infoAreaCommon.setTxtRentalNumber(TXT_RETAIL_NUMBER);
        infoAreaCommon.setRdoSeismicDiagnosis(RDO_SEISMIC_DIAGNOSIS);
        infoAreaCommon.setRdoEarthquakeResistance(RDO_EARTHQUAKE_RESISTANCE);
        infoAreaCommon.setRdoSeismicRetrofit(RDO_SEISMIC_RETROFIT);
        infoAreaCommon.setRdoDesignDocument(RDO_DESIGN_DOCUMENT);
        infoAreaCommon.setRdoRepairHistory(RDO_REPAIR_HISTORY);
        infoAreaCommon.setRdoVoluntaryOrganization(RDO_VOLUNTARY_ORGANIZATION);
        infoAreaCommon.setRdoDisasterPreventionManual(RDO_DISASTER_PREVENTION_MANUAL);
        infoAreaCommon.setRdoDisasterPreventionStockpile(RDO_DISASTER_PREVENTION_STOCKPILE);
        infoAreaCommon.setRdoNeedSupportList(RDO_NEED_SUPPORT_LIST);
        infoAreaCommon.setRdoDisasterPreventionRegular(RDO_DISASTER_PREVENTION_REGULAR);
        infoAreaCommon.setRdoSlope(RDO_SLOPE);
        infoAreaCommon.setRdoRailing(RDO_RAILING);
        infoAreaCommon.setRdoElevator(RDO_ELEVATOR);
        infoAreaCommon.setRdoLed(RDO_LED);
        infoAreaCommon.setRdoHeatShielding(RDO_HEAT_SHIELDING);
        infoAreaCommon.setRdoEquipmentCharge(RDO_EQUIPMENT_CHARGE);
        infoAreaCommon.setRdoCommunity(RDO_COMMUNITY);
        infoAreaCommon.setRdoContactProperty(RDO_CONTACT_PROPERTY);
        infoAreaCommon.setTxtContactPropertyElse(TXT_CONTACT_PROPERTY_ELSE);
        infoAreaCommon.setTxtContactZipCode1(TXT_CONTACT_ZIP_CODE_1);
        infoAreaCommon.setTxtContactZipCode2(TXT_CONTACT_ZIP_CODE_2);
        infoAreaCommon.setTxtContactAddress(TXT_CONTACT_ADDRESS);
        infoAreaCommon.setTxtContactTelno1(TXT_CONTACT_TEL_NO_1);
        infoAreaCommon.setTxtContactTelno2(TXT_CONTACT_TEL_NO_2);
        infoAreaCommon.setTxtContactTelno3(TXT_CONTACT_TEL_NO_3);
        infoAreaCommon.setTxtContactName(TXT_CONTACT_NAME);
        infoAreaCommon.setTxtContactNamePhonetic(TXT_CONTACT_NAME_PHONETIC);
        infoAreaCommon.setTxtContactMail(TXT_CONTACT_MAIL);
        infoAreaCommon.setTxaOptional(TXA_OPTIONAL);

        NotificationRegistrationForm form = new NotificationRegistrationForm();
        form.setApartmentId(APARTMENT_ID);
        form.setApartmentName(APARTMENT_NAME);
        form.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        form.setCityCode(CITY_CODE);
        form.setChangeCount(CHANGE_COUNT);
        form.setNotificationCount(NOTIFICATION_COUNT);
        form.setNextNotificationDate(NEXT_NOTIFICATION_DATE);
        form.setBasicReportInfo(basicReportInfoForm);
        form.setInfoAreaCommon(infoAreaCommon);
        return form;
    }

    private NotificationExportForm generateNotificationExportForm() {
        BasicNotificationInfoForm basicNotificationInfoForm = new BasicNotificationInfoForm();
        basicNotificationInfoForm.setTxtApartmentZipCode1(TXT_APARTMENT_ZIP_CODE_1);
        basicNotificationInfoForm.setTxtApartmentZipCode2(TXT_APARTMENT_ZIP_CODE_2);
        basicNotificationInfoForm.setTxtApartmentAddress2(TXT_APARTMENT_ADDRESS_2);

        NotificationInfoCommonExportForm infoAreaCommon = new NotificationInfoCommonExportForm();
        infoAreaCommon.setRdoNotificationType(RDO_NOTIFICATION_TYPE);
        infoAreaCommon.setCalNotificationDate(CAL_NOTIFICATION_DATE);
        infoAreaCommon.setTxtNotificationGroupName(TXT_NOTIFICATION_GROUP_NAME);
        infoAreaCommon.setTxtNotificationPersonName(TXT_NOTIFICATION_PERSON_NAME);
        infoAreaCommon.setRdoLostElseReasonCode(RDO_LOST_ELSE_REASON_CODE);
        infoAreaCommon.setRdoChangeReason(RDO_CHANGE_REASON);
        infoAreaCommon.setTxtLostElseReasonElse(TXT_LOST_REASON);
        infoAreaCommon.setRdoGroupYesno(RDO_GROUP_YESNO);
        infoAreaCommon.setTxtApartmentNumber(TXT_APARTMENT_NUMBER);
        infoAreaCommon.setRdoGroupForm(RDO_GROUP_FORM);
        infoAreaCommon.setTxtGroupFormElse(TXT_GROUP_FORM_ELSE);
        infoAreaCommon.setTxtHouseNumber(TXT_HOUSE_NUMBER);
        infoAreaCommon.setTxtFloorNumber(TXT_FLOOR_NUMBER);
        infoAreaCommon.setCalBuiltDate(CAL_BUILT_DATE);
        infoAreaCommon.setRdoLandRights(RDO_LAND_RIGHT);
        infoAreaCommon.setTxtLandRightsElse(TXT_LAND_RIGHT_ELSE);
        infoAreaCommon.setRdoUsefor(RDO_USER_FOR);
        infoAreaCommon.setTxtUseforElse(TXT_USER_FOR_ELSE);
        infoAreaCommon.setRdoManagementForm(RDO_MANAGEMENT_FORM);
        infoAreaCommon.setTxtManagementFormElse(TXT_MANAGEMENT_FORM_ELSE);
        infoAreaCommon.setTxtManagerName(TXT_MANAGER_NAME);
        infoAreaCommon.setTxtManagerNamePhonetic(TXT_MANAGER_NAME_PHONETIC);
        infoAreaCommon.setTxtManagerZipCode1(TXT_MANAGER_ZIP_CODE_1);
        infoAreaCommon.setTxtManagerZipCode2(TXT_MANAGER_ZIP_CODE_2);
        infoAreaCommon.setTxtManagerAddress(TXT_MANAGER_ADDRESS);
        infoAreaCommon.setTxtManagerTelno1(TXT_MANAGER_TEL_NO_1);
        infoAreaCommon.setTxtManagerTelno2(TXT_MANAGER_TEL_NO_2);
        infoAreaCommon.setTxtManagerTelno3(TXT_MANAGER_TEL_NO_3);
        infoAreaCommon.setRdoGroup(RDO_GROUP);
        infoAreaCommon.setRdoManager(RDO_MANAGEMENT);
        infoAreaCommon.setRdoRule(RDO_RULE);
        infoAreaCommon.setTxtRuleChangeYear(TXT_RULE_CHANGE_YEAR);
        infoAreaCommon.setRdoOneyearOver(RDO_ONE_YEAR_OVER);
        infoAreaCommon.setRdoMinutes(RDO_MINUTES);
        infoAreaCommon.setRdoManagementCost(RDO_MANAGEMENT_COST);
        infoAreaCommon.setRdoRepairCost(RDO_REPAIR_COST);
        infoAreaCommon.setTxtRepairMonthlycost(TXT_REPAIR_MONTHLY_COST);
        infoAreaCommon.setRdoRepairPlan(RDO_REPAIR_PLAN);
        infoAreaCommon.setTxtRepairNearestYear(TXT_REPAIR_NEAREST_YEAR);
        infoAreaCommon.setRdoLongRepairPlan(RDO_LONG_REPAIR_PLAN);
        infoAreaCommon.setTxtLongRepairPlanYear(TXT_LONG_REPAIR_PLAN_YEAR);
        infoAreaCommon.setTxtLongRepairPlanPeriod(TXT_LONG_REPAIR_PLAN_PERIOD);
        infoAreaCommon.setTxtLongRepairPlanYearFrom(TXT_LONG_REPAIR_PLAN_YEAR_FROM);
        infoAreaCommon.setTxtLongRepairPlanYearTo(TXT_LONG_REPAIR_PLAN_YEAR_TO);
        infoAreaCommon.setRdoArrearageRule(RDO_ARREARAGE_RULE);
        infoAreaCommon.setRdoSegment(RDO_SEGMENT);
        infoAreaCommon.setRdoEmptyPercent(RDO_EMPTY_PERCENT);
        infoAreaCommon.setTxtEmptyNumber(TXT_EMPTY_NUMBER);
        infoAreaCommon.setRdoRentalPercent(RDO_RENTAL_PERCENT);
        infoAreaCommon.setTxtRentalNumber(TXT_RETAIL_NUMBER);
        infoAreaCommon.setRdoSeismicDiagnosis(RDO_SEISMIC_DIAGNOSIS);
        infoAreaCommon.setRdoEarthquakeResistance(RDO_EARTHQUAKE_RESISTANCE);
        infoAreaCommon.setRdoSeismicRetrofit(RDO_SEISMIC_RETROFIT);
        infoAreaCommon.setRdoDesignDocument(RDO_DESIGN_DOCUMENT);
        infoAreaCommon.setRdoRepairHistory(RDO_REPAIR_HISTORY);
        infoAreaCommon.setRdoVoluntaryOrganization(RDO_VOLUNTARY_ORGANIZATION);
        infoAreaCommon.setRdoDisasterPreventionManual(RDO_DISASTER_PREVENTION_MANUAL);
        infoAreaCommon.setRdoDisasterPreventionStockpile(RDO_DISASTER_PREVENTION_STOCKPILE);
        infoAreaCommon.setRdoNeedSupportList(RDO_NEED_SUPPORT_LIST);
        infoAreaCommon.setRdoDisasterPreventionRegular(RDO_DISASTER_PREVENTION_REGULAR);
        infoAreaCommon.setRdoSlope(RDO_SLOPE);
        infoAreaCommon.setRdoRailing(RDO_RAILING);
        infoAreaCommon.setRdoElevator(RDO_ELEVATOR);
        infoAreaCommon.setRdoLed(RDO_LED);
        infoAreaCommon.setRdoHeatShielding(RDO_HEAT_SHIELDING);
        infoAreaCommon.setRdoEquipmentCharge(RDO_EQUIPMENT_CHARGE);
        infoAreaCommon.setRdoCommunity(RDO_COMMUNITY);
        infoAreaCommon.setRdoContactProperty(RDO_CONTACT_PROPERTY);
        infoAreaCommon.setTxtContactPropertyElse(TXT_CONTACT_PROPERTY_ELSE);
        infoAreaCommon.setTxtContactZipCode1(TXT_CONTACT_ZIP_CODE_1);
        infoAreaCommon.setTxtContactZipCode2(TXT_CONTACT_ZIP_CODE_2);
        infoAreaCommon.setTxtContactAddress(TXT_CONTACT_ADDRESS);
        infoAreaCommon.setTxtContactTelno1(TXT_CONTACT_TEL_NO_1);
        infoAreaCommon.setTxtContactTelno2(TXT_CONTACT_TEL_NO_2);
        infoAreaCommon.setTxtContactTelno3(TXT_CONTACT_TEL_NO_3);
        infoAreaCommon.setTxtContactName(TXT_CONTACT_NAME);
        infoAreaCommon.setTxtContactNamePhonetic(TXT_CONTACT_NAME_PHONETIC);
        infoAreaCommon.setTxtContactMail(TXT_CONTACT_MAIL);
        infoAreaCommon.setTxaOptional(TXA_OPTIONAL);

        NotificationExportForm form = new NotificationExportForm();
        form.setApartmentId(APARTMENT_ID);
        form.setApartmentName(APARTMENT_NAME);
        form.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        form.setCityCode(CITY_CODE);
        form.setChangeCount(CHANGE_COUNT);
        form.setNotificationCount(NOTIFICATION_COUNT);
        form.setNextNotificationDate(NEXT_NOTIFICATION_DATE);
        form.setBasicReportInfo(basicNotificationInfoForm);
        form.setInfoAreaCommon(infoAreaCommon);
        return form;
    }

    public static final String MAIL_SUBJECT = "MAIL_SUBJECT";
    public static final String MAIL_CONTACT_ADDRESS = "MAIL_CONTACT_MAIL";
    public static final String MAIL_CONTACT_NAME = "MAIL_CONTACT_NAME";
    public static final String APARTMENT_LOGIN_URL = "APARTMENT_LOGIN_URL";
    public static final String TBL210_KEY = "1000000002";
    public static final String CAL_NOTIFICATION_DAY = "2025/12/12";
    public static final String TXT_SENDER = "TXT_SENDER";
    public static final String LBL_EVIDENCE_BAR = "15";
    public static final String LBL_EVIDENCE_NO = "1";
    public static final String TXA_REMARKS = "TXA_REMARKS";
    public static final String TXA_CORRECTION_DETAIL = "TXA_CORRECTION_DETAIL";
    public static final String CAL_NOTICE_DATE = "2025/12/12";
    public static final String RDO_NOTIFICATION_METHOD = "2";
    public static final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.of(2025,12,12);
    public static final int NOTIFICATION_COUNT = 1;
    public static final int CHANGE_COUNT = 1;
    public static final String CITY_CODE = "CITY_CODE";
    public static final String APARTMENT_NAME_PHONETIC = "APARTMENT_NAME_PHONETIC";
    public static final String APARTMENT_ID = "APARTMENT_ID";
    public static final String NOTIFICATION_NO = "NOTIFICATION_NO";
    public static final String TXA_OPTIONAL = "TXA_OPTIONAL";
    public static final String TXT_CONTACT_MAIL = "contact@gmail.com";
    public static final String TXT_CONTACT_NAME_PHONETIC = "TXT_CONTACT_NAME_PHONETIC";
    public static final String TXT_CONTACT_NAME = "TXT_CONTACT_NAME";
    public static final String TXT_CONTACT_TEL_NO_1 = "TXT_CONTACT_TEL_NO_1";
    public static final String TXT_CONTACT_TEL_NO_2 = "TXT_CONTACT_TEL_NO_2";
    public static final String TXT_CONTACT_TEL_NO_3 = "TXT_CONTACT_TEL_NO_3";
    public static final String TXT_CONTACT_ADDRESS = "TXT_CONTACT_ADDRESS";
    public static final String TXT_CONTACT_ZIP_CODE_2 = "TXT_CONTACT_ZIP_CODE_2";
    public static final String TXT_CONTACT_ZIP_CODE_1 = "TXT_CONTACT_ZIP_CODE_1";
    public static final String TXT_CONTACT_PROPERTY_ELSE = "TXT_CONTACT_PROPERTY_ELSE";
    public static final String RDO_CONTACT_PROPERTY = "RDO_CONTACT_PROPERTY";
    public static final String RDO_COMMUNITY = "RDO_COMMUNITY";
    public static final String RDO_EQUIPMENT_CHARGE = "RDO_EQUIPMENT_CHARGE";
    public static final String RDO_HEAT_SHIELDING = "RDO_HEAT_SHIELDING";
    public static final String RDO_LED = "RDO_LED";
    public static final String RDO_ELEVATOR = "RDO_ELEVATOR";
    public static final String RDO_RAILING = "RDO_RAILING";
    public static final String RDO_SLOPE = "RDO_SLOPE";
    public static final String RDO_DISASTER_PREVENTION_REGULAR = "RDO_DISASTER_PREVENTION_REGULAR";
    public static final String RDO_NEED_SUPPORT_LIST = "RDO_NEED_SUPPORT_LIST";
    public static final String RDO_DISASTER_PREVENTION_STOCKPILE = "RDO_DISASTER_PREVENTION_STOCKPILE";
    public static final String RDO_DISASTER_PREVENTION_MANUAL = "RDO_DISASTER_PREVENTION_MANUAL";
    public static final String RDO_VOLUNTARY_ORGANIZATION = "RDO_VOLUNTARY_ORGANIZATION";
    public static final String RDO_REPAIR_HISTORY = "RDO_REPAIR_HISTORY";
    public static final String RDO_DESIGN_DOCUMENT = "RDO_DESIGN_DOCUMENT";
    public static final String RDO_SEISMIC_RETROFIT = "RDO_SEISMIC_RETROFIT";
    public static final String RDO_EARTHQUAKE_RESISTANCE = "RDO_EARTHQUAKE_RESISTANCE";
    public static final String RDO_SEISMIC_DIAGNOSIS = "RDO_SEISMIC_DIAGNOSIS";
    public static final String TXT_RETAIL_NUMBER = "20";
    public static final String RDO_RENTAL_PERCENT = "50";
    public static final String TXT_EMPTY_NUMBER = "2";
    public static final String RDO_EMPTY_PERCENT = "50";
    public static final String RDO_SEGMENT = "RDO_SEGMENT";
    public static final String RDO_ARREARAGE_RULE = "RDO_ARREARAGE_RULE";
    public static final String TXT_LONG_REPAIR_PLAN_YEAR_TO = "2030";
    public static final String TXT_LONG_REPAIR_PLAN_YEAR_FROM = "2020";
    public static final String TXT_LONG_REPAIR_PLAN_PERIOD = "2";
    public static final String TXT_LONG_REPAIR_PLAN_YEAR = "10";
    public static final String RDO_LONG_REPAIR_PLAN = "RDO_LONG_REPAIR_PLAN";
    public static final String TXT_REPAIR_NEAREST_YEAR = "TXT_REPAIR_NEAREST_YEAR";
    public static final String RDO_REPAIR_PLAN = "RDO_REPAIR_PLAN";
    public static final String TXT_REPAIR_MONTHLY_COST = "5000";
    public static final String RDO_REPAIR_COST = "3000";
    public static final String RDO_MANAGEMENT_COST = "3000";
    public static final String RDO_MINUTES = "60";
    public static final String RDO_ONE_YEAR_OVER = "RDO_ONE_YEAR_OVER";
    public static final String TXT_RULE_CHANGE_YEAR = "2020";
    public static final String RDO_RULE = "1";
    public static final String RDO_MANAGEMENT = "RDO_MANAGEMENT";
    public static final String RDO_GROUP = "RDO_GROUP";
    public static final String TXT_MANAGER_TEL_NO_3 = "TXT_MANAGER_TEL_NO_3";
    public static final String TXT_MANAGER_TEL_NO_2 = "TXT_MANAGER_TEL_NO_2";
    public static final String TXT_MANAGER_TEL_NO_1 = "TXT_MANAGER_TEL_NO_1";
    public static final String TXT_MANAGER_ADDRESS = "TXT_MANAGER_ADDRESS";
    public static final String TXT_MANAGER_ZIP_CODE_2 = "TXT_MANAGER_ZIP_CODE_2";
    public static final String TXT_MANAGER_ZIP_CODE_1 = "TXT_MANAGER_ZIP_CODE_1";
    public static final String TXT_MANAGER_NAME_PHONETIC = "TXT_MANAGER_NAME_PHONETIC";
    public static final String TXT_MANAGER_NAME = "TXT_MANAGER_NAME";
    public static final String TXT_MANAGEMENT_FORM_ELSE = "TXT_MANAGEMENT_FORM_ELSE";
    public static final String RDO_MANAGEMENT_FORM = "RDO_MANAGEMENT_FORM";
    public static final String TXT_USER_FOR_ELSE = "TXT_USER_FOR_ELSE";
    public static final String RDO_USER_FOR = "RDO_USER_FOR";
    public static final String TXT_LAND_RIGHT_ELSE = "TXT_LAND_RIGHT_ELSE";
    public static final String RDO_LAND_RIGHT = "RDO_LAND_RIGHT";
    public static final String CAL_BUILT_DATE = "2019/12/12";
    public static final String TXT_FLOOR_NUMBER = "12";
    public static final String TXT_HOUSE_NUMBER = "12";
    public static final String TXT_GROUP_FORM_ELSE = "TXT_GROUP_FORM_ELSE";
    public static final String RDO_GROUP_FORM = "RDO_GROUP_FORM";
    public static final String TXT_APARTMENT_NUMBER = "12";
    public static final String RDO_GROUP_YESNO = "RDO_GROUP_YESNO";
    public static final String TXT_LOST_REASON = "TXT_LOST_REASON";
    public static final String RDO_CHANGE_REASON = "RDO_CHANGE_REASON";
    public static final String TXT_NOTIFICATION_PERSON_NAME = "TXT_NOTIFICATION_PERSON_NAME";
    public static final String TXT_NOTIFICATION_GROUP_NAME = "TXT_NOTIFICATION_GROUP_NAME";
    public static final String RDO_NOTIFICATION_TYPE = "1";
    public static final String TXT_APARTMENT_ADDRESS_2 = "TXT_APARTMENT_ADDRESS_2";
    public static final String TXT_APARTMENT_ZIP_CODE_2 = "TXT_APARTMENT_ZIP_CODE_2";
    public static final String TXT_APARTMENT_ZIP_CODE_1 = "TXT_APARTMENT_ZIP_CODE_1";
    public static final String RDO_LOST_ELSE_REASON_CODE = "1";
    public static final String CAL_NOTIFICATION_DATE = "2019/12/28";
    public static final String DOCUMENT_NO = "DOCUMENT_NO";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String CHIEF_NAME = "CHIEF_NAME";
    public static final String NEW_NOTIFICATION = "1";
    public static final String UPDATE_NOTIFICATION = "2";
    public static final String TOKYO = "1";
    public static final String SECTION_OWNER = "2";
    public static final String APARTMENT_NAME = "APARTMENT_NAME";
    public static final String NOTIFICATION_PERSONAL_NAME = "NOTIFICATION_PERSONAL_NAME";
    public static final String USER_ID = "USER_ID";
    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String USER_DISPLAY_NAME = "USER_DISPLAY_NAME";
    public static final String USER_TYPE_CODE = "3";
    public static final String PROGRESS_RECORD_NO = "1000000008";
    public static final String TXT_APPENDIX_NO = "1000000008";
    public static final String TXT_DOCUMENT_NO = "1000000008";
    public static final String LBL_RECIPIENT_NAME_APARTMENT = "LBL_RECIPIENT_NAME_APARTMENT";
    public static final String LBL_RECIPIENT_NAME_USER = "LBL_RECIPIENT_NAME_USER";
}