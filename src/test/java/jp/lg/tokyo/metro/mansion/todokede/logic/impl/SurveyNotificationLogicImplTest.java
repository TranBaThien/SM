package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_INVESTIGATION_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_PROGRESS_RECORD_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_TEMP_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL230;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL235;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL230DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL235DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM002DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL235Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SurveyForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML060Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SurveyVo;

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class SurveyNotificationLogicImplTest {

    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    @Mock
    private IEMailLogic mailLogic;

    @Mock
    private SequenceUtil sequenceUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private TBL200DAO tbl200DAO;

    @Mock
    private TBL300DAO tbl300DAO;

    @Mock
    private TBL230DAO tbl230DAO;

    @Mock
    private TBM001DAO tbm001DAO;

    @Mock
    private TBM002DAO tbm002DAO;

    @Mock
    private TBL235DAO tbl235DAO;

    @InjectMocks
    private SurveyNotificationLogicImpl surveyNotificationLogicImpl;

    private final String CITY_ONETIME_PASSWORD_PERIOD = "30";
    private final String CITY_LOGIN_URL = "http://localhost:8080/GAA0110";
    private final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.SurveyNotificationLogicImpl";

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
    private final String LOGIN_ID = "G0110101";
    private final String PASSWORD = "123456789";
    private final String USER_ID = "10000001";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String MAIL_ADDRESS = "abc@gmail.com";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String STOP_REASON = "stop reason";
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    private final String CITY_CODE_1 = "111111";

    private TBL120Entity generateEntityTbl120(String accountLockFlag, String accountAvailableFlag,
            String loginStatusFlag) {
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

    private final String CITY_CODE = "111111";
    private final String CITY_NAME = "小笠原村";
    private final String USER_AUTHORITY = "1";
    private final String DOCUMENT_NO = "八丈町文書番号初";
    private final String CHIEF_NAME = "森下　一男";
    private final String CITY_HEAD_WORD = "osw";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "";
    private final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();

    private TBM001Entity newTBM001Entity() {
        TBM001Entity entity = new TBM001Entity();

        entity.setCityCode(CITY_CODE);
        entity.setCityName(CITY_NAME);
        entity.setUserAuthority(USER_AUTHORITY);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setChiefName(CHIEF_NAME);
        entity.setCityHeadWord(CITY_HEAD_WORD);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);

        return entity;
    }

    private final String TEMP_NO = "1000000001";
    private final String APARTMENT_ID = "1000000001";
    private final String DOCUMENT_NO_Tbl235 = "八丈町文書番号初";
    private final String TEMP_KBN = "3";
    private final String APPENDIX_NO = "第　　号様式（第　　条関係）";
    private final LocalDate NOTICE_DATE = LocalDate.now();
    private final String NOTICE_DESTINATION_CODE = "1";
    private final String RECIPIENT_NAME_APARTMENT = "Tokyo";
    private final String RECIPIENT_NAME_USER = "管理組合理事長";
    private final String SENDER = "東京都知事山本　泰人";
    private final String TEXTADDRESS = "貴管理組合が管理する";
    private final String ADDRESS = "東京都中央区 東京都武蔵野市吉祥寺東町";
    private final String APARTMENT_NAME = "Tokyo";
    private final String INVEST_IMPL_PLAN_TIME = "201912241123";
    private final String INVEST_IMPL_NUMBER_PEOPLE = "1";
    private final String NECESSARY_DOCUMENT = "1000000001";
    private final String CONTACT = "adaád";
    private final String NOTIFICATION_METHOD_CODE = "1";
    private final Timestamp UPDATE_DATETIME_225 = new Timestamp(new Date().getTime());

    private TBL235Entity newTBL235Entity() {
        TBL235Entity entity = new TBL235Entity();

        entity.setTempNo(TEMP_NO);
        entity.setApartmentId(APARTMENT_ID);
        entity.setTempKbn(TEMP_KBN);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setNoticeDestinationCode(NOTICE_DESTINATION_CODE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setTextaddress(TEXTADDRESS);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setInvestImplPlanTime(INVEST_IMPL_PLAN_TIME);
        entity.setInvestImplNumberPeople(INVEST_IMPL_NUMBER_PEOPLE);
        entity.setNecessaryDocument(NECESSARY_DOCUMENT);
        entity.setContact(CONTACT);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_225);

        return entity;
    }

    // Mail variable
    private final String APARTMENT_NAME_MAIL = "パスワード再発行通知メール（管理組合向け）";
    private final String CONTACT_NAME_MAIL = "Demo";
    private final String MAIL_TEMPLATE_MAIL = "パスワード再発行通知メール（管理組合向け）";
    private final String MAIL_SUBJECT_MAIL = "【東京都マンション管理状況届出】パスワード再発行完了のお知らせ";
    private final String MAIL_SEND_FROM_MAIL = "mansiondev@cmcglobal.com.vn";
    private final String MAIL_REPLY_TO_MAIL = "to@gmail.com";
    private final String CONTACT_MAIL_ADDRESS_MAIL = "nvlong1511@gmail.com";
    private final String CITY_NAME_MAIL = "BBBBB";
    private final String WINDOW_BE_LONG_MAIL = "問合せ窓口_部署（区市町村向け）";
    private final String WINDOW_TEL_NO_MAIL = "TEL_123456780";
    private final String WINDOW_FAX_NO_MAIL = "FAX_987654320";
    private final String WINDOW_MAIL_ADDRESS = "window2@gmail.com";

    private ML060Vo generateML060Vo() {
        ML060Vo vo = new ML060Vo();
        vo.setApartmentName(APARTMENT_NAME_MAIL);
        vo.setCityName(CITY_NAME_MAIL);
        vo.setContactMailAddress(CONTACT_MAIL_ADDRESS_MAIL);
        vo.setContactName(CONTACT_NAME_MAIL);
        vo.setMailReplyTo(MAIL_REPLY_TO_MAIL);
        vo.setMailSendFrom(MAIL_SEND_FROM_MAIL);
        vo.setMailSubject(MAIL_SUBJECT_MAIL);
        vo.setMailTemplate(MAIL_TEMPLATE_MAIL);
        vo.setWindowBelong(WINDOW_BE_LONG_MAIL);
        vo.setWindowFaxNo(WINDOW_FAX_NO_MAIL);
        vo.setWindowMailAddress(WINDOW_MAIL_ADDRESS);
        vo.setWindowTelNo(WINDOW_TEL_NO_MAIL);
        return vo;
    }

    private final String APARTMENT_ID_VO = "1000000001";
    private final String NEWEST_NOTIFICATION_NO_VO = "1000000001";
    private final String NOTIFICATION_PERSON_NAME_VO = "1000000001";

    private SurveyVo getSurvayVo() {
        SurveyVo surveyVo = new SurveyVo();
        surveyVo.setApartmentId(APARTMENT_ID_VO);
        surveyVo.setNotificationPersonName(NOTIFICATION_PERSON_NAME_VO);
        surveyVo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_VO);
        return surveyVo;
    }

    /* Create AdviceNotificationForm */
    private final String APARTMENT_ID_FORM = "1000000001";
    private final String APARTMENT_NAME_FORM = "abc";
    private final String APARTMENT_NAME_PHONETIC_FORM = "abc";
    private final String ADDRESS_FORM = "abc";
    private final String NEWEST_NOTIFICATION_NO_FORM = "1000000001";
    private final String LBL_ADDRESS_FORM = "cccccc";
    private final String TXT_APPENDIX_NO_FORM = "aaaaaaa";
    private final String TXT_DOCUMENT_NO_FORM = "eeeeeee";
    private final String CAL_NOTICE_DATE_FORM = "2019/12/07";
    private final String TXT_SENDER_FORM = "eeeeeeee";
    private final String RDO_NOTIFICATION_METHOD_FORM = "1";

    private SurveyForm getSurvayForm() {
        SurveyForm surveyForm = new SurveyForm();
        surveyForm.setApartmentId(APARTMENT_ID_FORM);
        surveyForm.setApartmentName(APARTMENT_NAME_FORM);
        surveyForm.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC_FORM);
        surveyForm.setAddress(ADDRESS_FORM);
        surveyForm.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_FORM);
        surveyForm.setLblAddress(LBL_ADDRESS_FORM);
        surveyForm.setTxtAppendixNo(TXT_APPENDIX_NO_FORM);
        surveyForm.setTxtDocumentNo(TXT_DOCUMENT_NO_FORM);
        surveyForm.setTxtSender(TXT_SENDER_FORM);
        surveyForm.setCalNoticeDate(CAL_NOTICE_DATE_FORM);
        surveyForm.setRdoNotificationMethod(RDO_NOTIFICATION_METHOD_FORM);
        surveyForm.setCalInvestImplPlanTime("20191010");
        surveyForm.setTxtInvestImplNumberPeople("1");
        return surveyForm;

    }

    private final String CONTACT_MAIL_ADDRESS_Tbm002 = "nvlong1511@gmail.com";
    private final String WINDOW_BE_LONG_Tbm002 = "問合せ窓口_部署（区市町村向け）";
    private final String WINDOW_TEL_NO_Tbm002 = "TEL_123456780";
    private final String WINDOW_FAX_NO_Tbm002 = "FAX_987654320";
    private final String CITY_CODE_Tbm002 = "111111";
    private final String USER_CODE_Tbm002 = "100000000";

    private TBM002Entity newTBM002Entity() {

        TBM002Entity entity = new TBM002Entity();
        TBM002EntityPK pk = new TBM002EntityPK();
        pk.setCityCode(CITY_CODE_Tbm002);
        pk.setUsedCode(USER_CODE_Tbm002);
        entity.setId(pk);
        entity.setWindowTelNo(WINDOW_TEL_NO_Tbm002);
        entity.setWindowMailAddress(CONTACT_MAIL_ADDRESS_Tbm002);
        entity.setWindowFaxNo(WINDOW_FAX_NO_Tbm002);
        entity.setWindowBelong(WINDOW_BE_LONG_Tbm002);

        return entity;
    }

    /* Create TBL200Entity */
    private final String APARTMENT_ID_TBL200 = "11000000001";
    private final String NOTIFICATION_NO_TBL200 = "11000000001";
    private final String APARTMENT_NAME_TBL200 = "tokyo";
    private final String CONTACT_PROPERTY_ELSE_TBL200 = "1";
    private final String NOTIFICATION_PERSON_NAME_TBL200 = "abc";
    private final LocalDate NOTIFICATION_DATE_TBL200 = LocalDate.now();
    private final String NOTIFICATION_TYPE_TBL200 = "abc";
    private final String ADDRESS_TBL200 = "abc";
    private final String CONTACT_MAIL_ADDRESS_TBL200 = "abc";
    private final String DELETE_FLAG_TBL200 = "0";
    private final String ADVICE_DONE_FLAG_TBL200 = "1";
    private final String CITY_CODE_TBL200 = "1";

    private TBL200Entity newTBL200Entity() {

        TBL200Entity entity = new TBL200Entity();

        TBL200EntityPK entityPK = new TBL200EntityPK();
        entityPK.setApartmentId(APARTMENT_ID_TBL200);
        entityPK.setNotificationNo(NOTIFICATION_NO_TBL200);
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
        entity.setCityCode(CITY_CODE_TBL200);

        return entity;
    }

    private TBL120Entity generateEntity(String accountLockFlag, String accountAvailableFlag, String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.MANSION.getCode()));
        entity.setPassword(PASSWORD);
        entity.setUserType(USER_TYPE);
        entity.setUserName(USER_NAME);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setCityCode(CITY_CODE_1);
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

    private void assertSurvayForm(SurveyForm surveyForm) {

        assertThat(surveyForm.getTxtAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(surveyForm.getTxtDocumentNo()).isEqualTo(DOCUMENT_NO_Tbl235);
        assertThat(surveyForm.getCalNoticeDate()).isEqualTo(
                DateTimeUtil.formatDate(NOTICE_DATE, DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD)));
        assertThat(surveyForm.getRdoNoticeDestination()).isEqualTo(NOTICE_DESTINATION_CODE);
        assertThat(surveyForm.getTxtRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(surveyForm.getTxtRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(surveyForm.getTxtSender()).isEqualTo(SENDER);
        assertThat(surveyForm.getTxtTextAdress()).isEqualTo(TEXTADDRESS);
        assertThat(surveyForm.getLblAddress()).isEqualTo(ADDRESS);
        assertThat(surveyForm.getCalInvestImplPlanTime())
                .isEqualTo(DateTimeUtil.convertReFormatDate(INVEST_IMPL_PLAN_TIME));
        assertThat(surveyForm.getTxtInvestImplNumberPeople()).isEqualTo(INVEST_IMPL_NUMBER_PEOPLE);
        assertThat(surveyForm.getTxtNecessaryDocument()).isEqualTo(NECESSARY_DOCUMENT);
        assertThat(surveyForm.getTxaContact()).isEqualTo(CONTACT);
        assertThat(surveyForm.getRdoNotificationMethod()).isEqualTo(NOTIFICATION_METHOD_CODE);
        assertThat(surveyForm.getHasDataStore()).isEqualTo("true");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-001/区分:N/チェック内容:Test get Survey Notification Info When Newest Notification is null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenNewestNotificationIsNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        surveyVo.setNewestNotificationNo(null);
        surveyVo.setNotificationPersonName(null);
        Mockito.when(tbm002DAO.getWindowInformation(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getNewestNotificationNo()).isEqualTo(null);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-002/区分:N/チェック内容:Test Get Survey Notification Info When Get Newest Notification Info Success
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenGetNewestNotificationInfoSuccess() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        TBL200Entity entity =  newTBL200Entity();
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entity);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getNewestNotificationNo()).isEqualTo(NEWEST_NOTIFICATION_NO_VO);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(entity.getNotificationPersonName());
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-003/区分:N/チェック内容:Test Get Survey Notification Info When Tbl200 Is Null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbl200IsNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        surveyVo.setNotificationPersonName(null);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(null);
        Mockito.when(tbm002DAO.getWindowInformation(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(null);
        assertThat(surveyVo.getCityCode()).isEqualTo(null);
        assertThat(surveyVo.getLblAddress()).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-005/区分:N/チェック内容:Test Get Survey Notification Info When Tbl200 Is Not Null And CityCode Is Null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbl200IsNotNullAndCityCodeIsNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        TBL200Entity tbl200Entity = newTBL200Entity();
        tbl200Entity.setCityCode(null);
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
        Mockito.when(tbm002DAO.getWindowInformation(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME_TBL200);
        assertThat(surveyVo.getTxtDocumentNo()).isEqualTo(null);
        assertThat(surveyVo.getTxtSender()).isEqualTo(null);
        assertThat(surveyVo.getLblAddress()).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-006/区分:N/チェック内容:Test Get Survey Notification Info When Tbl200 Is Not Null And Tbm001 Is Null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbl200IsNotNullAndTbm001IsNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        TBL200Entity tbl200Entity = newTBL200Entity();
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME_TBL200);
        assertThat(surveyVo.getTxtDocumentNo()).isEqualTo(null);
        assertThat(surveyVo.getTxtSender()).isEqualTo(null);
        assertThat(surveyVo.getLblAddress()).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-007/区分:N/チェック内容:Test Get Survey Notification Info
     *  When Tbl200 Is Not Null And Tbm001 Is Not Null And User Type Code Is City
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbl200IsNotNullAndTbm001IsNotNullAndUserTypeCodeIsCity() throws BusinessException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "区市町村"));
        prepareSecurityContextHolder(entity);
        SurveyVo surveyVo = getSurvayVo();
        TBL200Entity tbl200Entity = newTBL200Entity();
        TBM001Entity tbm001Entity = newTBM001Entity();
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(tbm001Entity);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getTxtDocumentNo()).isEqualTo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + DOCUMENT_NO
                        + CommonConstants.SPACE_FULL_SIZE + "第" + CommonConstants.SPACE_FULL_SIZE + "号");
        assertThat(surveyVo.getTxtSender()).isEqualTo(CITY_NAME + "長" + CommonConstants.SPACE_FULL_SIZE + CHIEF_NAME);
        assertThat(surveyVo.getLblAddress()).isEqualTo("東京都" + CITY_NAME + ADDRESS_TBL200);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME_TBL200);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-008/区分:N/チェック内容:Test Get Survey Notification Info
     *  When Tbl200 Is Null And Tbm001 Is Not Null And User Type Code Is Not City
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbl200IsNotNullAndTbm001IsNotNullAndUserTypeCodeIsNotCity() throws BusinessException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, "都職員"));
        prepareSecurityContextHolder(entity);
        SurveyVo surveyVo = getSurvayVo();
        TBL200Entity tbl200Entity = newTBL200Entity();
        TBM001Entity tbm001Entity = newTBM001Entity();
        Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(tbm001Entity);
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, null);
        assertThat(surveyVo.getTxtDocumentNo())
                .isEqualTo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + DOCUMENT_NO
                        + CommonConstants.SPACE_FULL_SIZE + "第" + CommonConstants.SPACE_FULL_SIZE + "号");
        assertThat(surveyVo.getTxtSender()).isEqualTo("東京都知事" + CommonConstants.SPACE_FULL_SIZE + CHIEF_NAME);
        assertThat(surveyVo.getLblAddress()).isEqualTo("東京都" + CITY_NAME + ADDRESS_TBL200);
        assertThat(surveyVo.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME_TBL200);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-009/区分:N/チェック内容:Test Get Survey Notification Info When Tbm 002 Is Not Null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbm002IsNotNull() throws BusinessException {

        TBM002Entity tbm002Entity = newTBM002Entity();
        Mockito.when(tbm002DAO.getWindowInformation(Mockito.anyString(), Mockito.anyString())).thenReturn(tbm002Entity);

        SurveyVo surveyVo = getSurvayVo();
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, tbm002Entity.getId().getCityCode());
        assertThat(surveyVo.getTxaContact()).isEqualTo(WINDOW_BE_LONG_Tbm002 + "\n" + CONTACT_MAIL_ADDRESS_Tbm002 + "\n"
                + WINDOW_TEL_NO_Tbm002 + "\n" + WINDOW_FAX_NO_Tbm002);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-010/区分:N/チェック内容:Test Get Survey Notification When Tbm 002 Is Null
     */
    @Test
    public void testGetSurveyNotificationInfo_WhenTbm002IsNull() throws BusinessException {

        Mockito.when(tbm002DAO.getWindowInformation(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        SurveyVo surveyVo = getSurvayVo();
        surveyNotificationLogicImpl.getSurveyNotificationInfo(surveyVo, Mockito.anyString());
        assertThat(surveyVo.getTxaContact()).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-011/区分:N/チェック内容:Test Check Temporary Data When Tbl235 Is Null
     */
    @Test
    public void testCheckTemporaryDataWhenTBL235IsNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        surveyVo.setHasDataStore(null);
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.CITY.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.checkTemporaryData(surveyVo);
        assertThat(surveyVo.getHasDataStore()).isEqualTo("false");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-012/区分:N/チェック内容:Test Check Temporary Data When Tbl235 Is Not Null
     */
    @Test
    public void testCheckTemporaryDataWhenTBL235IsNotNull() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        surveyVo.setHasDataStore(null);
        TBL235Entity entity = newTBL235Entity();
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.MAINTENANCER.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(Mockito.anyString(), Mockito.anyString())).thenReturn(entity);
        surveyNotificationLogicImpl.checkTemporaryData(surveyVo);
        assertThat(surveyVo.getHasDataStore()).isEqualTo("true");
    }

       /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-013/区分:E/チェック内容:Test Check Temporary Data When UserTypeCode Is InCorrect
     */
    @Test(expected = BusinessException.class)
    public void testCheckTemporaryDataWhenUserTypeCodeIsiNCorrect() throws BusinessException {
        SurveyVo surveyVo = getSurvayVo();
        surveyVo.setHasDataStore(null);
        TBL235Entity entity = newTBL235Entity();
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(Mockito.anyString(), Mockito.anyString())).thenReturn(entity);
        surveyNotificationLogicImpl.checkTemporaryData(surveyVo);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-014/区分:N/チェック内容:Test Restore Survey Notification Info
     * When User Type Code Is Tokyo Staff And Tbl235 Is Not Null
     */
    @Test
    public void testRestoreSurveyNotificationInfoWhenUserTypeCodeIsTokyoStaffAndTbl235IsNotNull()
            throws BusinessException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
        prepareSecurityContextHolder(entity);

        SurveyForm surveyForm = getSurvayForm();

        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity
                .setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF));
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(surveyForm.getApartmentId(),
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF)))
                .thenReturn(tbl235Entity);
        surveyNotificationLogicImpl.restoreSurveyNotificationInfo(surveyForm);
        assertSurvayForm(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-015/区分:N/チェック内容:Test Restore Survey Notification Info When User Type Code Is City And Tbl235 Is Not Null
     */
    @Test
    public void testRestoreSurveyNotificationInfo_WhenUserTypeCodeIsCityAndTbl235IsNotNull() throws BusinessException {

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_CITY));
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(surveyForm.getApartmentId(),
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_CITY)))
                .thenReturn(tbl235Entity);
        surveyNotificationLogicImpl.restoreSurveyNotificationInfo(surveyForm);
        assertSurvayForm(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-016/区分:N/チェック内容:Test Restore Survey Notification Info
     *  When User Type Code Is System Administrator And Tbl235 Is Not Null
     */
    @Test
    public void testRestoreSurveyNotificationInfoWhenUserTypeCodeIsSystemAdministratorTbl235IsNotNull()
            throws BusinessException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(surveyForm.getApartmentId(),
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_SYS_ADMIN)))
                .thenReturn(tbl235Entity);
        surveyNotificationLogicImpl.restoreSurveyNotificationInfo(surveyForm);
        assertSurvayForm(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-017/区分:N/チェック内容:Test Restore Survey Notification Info 
     * When User Type Code Is Maintenance Company And Tbl235 Is Not Null
     */
    @Test
    public void testRestoreSurveyNotificationInfoWhenUserTypeCodeIsMaintenanceCompanyAndTbl235IsNotNull()
            throws BusinessException {
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR));
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(surveyForm.getApartmentId(),
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR)))
                .thenReturn(tbl235Entity);
        surveyNotificationLogicImpl.restoreSurveyNotificationInfo(surveyForm);
        assertSurvayForm(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-018/区分:N/チェック内容:Test Restore Survey Notification Info When Tbl235 Is Null
     */
    @Test
    public void testRestoreSurveyNotificationInfoWhenTbl235IsNull() throws BusinessException {
        SurveyForm surveyForm = getSurvayForm();
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(
                CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR));
        Mockito.when(tbl235DAO.getSurveyByApartmentIdAndTempKbn(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        surveyNotificationLogicImpl.restoreSurveyNotificationInfo(surveyForm);
        assertThat(surveyForm.getHasDataStore()).isEqualTo("false");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-019/区分:N/チェック内容:Test SaveSurvey Case Register Success
     */
    @Test
    public void testDeleteTemporarilyData_CaseRegisterSuccess() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        List<String> list = new ArrayList<>();
        list.add(surveyForm.getApartmentId());
        Mockito.when(tbl235DAO.getTempNoByApartmentId(surveyForm.getApartmentId())).thenReturn(list);
        surveyForm.setRdoNotificationMethod("2");
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("削除（物理）を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-020/区分:N/チェック内容:Test Delete Temporarily Data
     *  Case Save Temporarily Data Success When User Type Code Is Tokyo Staff
     */
    @Test
    public void testDeleteTemporarilyDataCaseSaveTemporarilyDataSuccessWhenUserTypeCodeIsTokyoStaff() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        List<String> list = new ArrayList<>();
        list.add(surveyForm.getApartmentId());
        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF));
        Mockito.when(tbl235DAO.getTempNoByApartmentIdAndTempKBN(surveyForm.getApartmentId(), tbl235Entity.getTempKbn())).thenReturn(list);
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("削除（物理）を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-021/区分:N/チェック内容:Test Delete Temporarily Data Case Save Temporarily Data Success When User Type Code Is City
     */
    @Test
    public void testDeleteTemporarilyDataCaseSaveTemporarilyDataSuccessWhenUserTypeCodeIsCity()
            throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");
        
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        List<String> list = new ArrayList<>();
        list.add(surveyForm.getApartmentId());

        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity.setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_CITY));

        Mockito.when(tbl235DAO.getTempNoByApartmentIdAndTempKBN(surveyForm.getApartmentId(), tbl235Entity.getTempKbn()))
                .thenReturn(list);

        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("削除（物理）を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-022/区分:N/チェック内容:Test Delete Temporarily
     *  Data Case Save Temporarily Data Success When User Type Code Is System Admin
     */
    @Test
    public void testDeleteTemporarilyDataCaseSaveTemporarilyDataSuccessWhenUserTypeCodeIsSystemAdmin()
            throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");
        
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        List<String> list = new ArrayList<>();
        list.add(surveyForm.getApartmentId());

        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity
                .setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_SYS_ADMIN));

        Mockito.when(tbl235DAO.getTempNoByApartmentIdAndTempKBN(surveyForm.getApartmentId(), tbl235Entity.getTempKbn()))
                .thenReturn(list);

        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("削除（物理）を実行します。テーブル：TBL235、キー：1000000001");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-023/区分:N/チェック内容:Test Delete Temporarily Data 
     * Case Save Temporarily Data Success When User Type Code Is Maintenancer
     */
    @Test
    public void testDeleteTemporarilyDataCaseSaveTemporarilyDataSuccessWhenUserTypeCodeIsMaintenancer() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);

        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        List<String> list = new ArrayList<>();
        list.add(surveyForm.getApartmentId());

        TBL235Entity tbl235Entity = newTBL235Entity();
        tbl235Entity
                .setTempKbn(CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR));

        Mockito.when(tbl235DAO.getTempNoByApartmentIdAndTempKBN(surveyForm.getApartmentId(), tbl235Entity.getTempKbn())).thenReturn(list);

        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("削除（物理）を実行します。テーブル：TBL235、キー：1000000001");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-024/区分:N/チェック内容:Test Save Survey When Save Data To Tbl230 Is Success
     */
    @Test
    public void testSaveSurveyWhenSaveDataToTbl230IsSuccess() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod("2");
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL230、キー：123456789");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-025/区分:E/チェック内容:Test Save Survey When Save Data To Tbl230 Is Unsuccess Throw Exception
     */
    @Test(expected = BusinessException.class)
    public void testSaveSurveyWhenSaveDataToTbl230IsUnsuccessThrowException() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod("2");
        Mockito.when(tbl230DAO.save(Mockito.any())).thenReturn(false);
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-026/区分:N/チェック内容:Test Save Survey When Save Data To Tbl300 Is Success
     */
    @Test
    public void testSaveSurveyWhenSaveDataToTbl300IsSuccess() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod("2");
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
        assertThat(logAppender.getEvents().get(1).getMessage()).contains("登録を実行します。テーブル：TBL300、キー：123456789");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-027/区分:E/チェック内容:Test Save Survey When Save Data To Tbl300 Is Unsuccess Throw Exception
     */
    @Test(expected = BusinessException.class)
    public void testSaveSurveyWhenSaveDataToTbl300IsUnsuccessThrowException() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod("2");
        Mockito.when(tbl300DAO.save(Mockito.any())).thenReturn(false);
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-028/区分:N/チェック内容:Test Save Survey Success When Save Data
     *  To Tbl230 Is Success And Save Data To Tbl300 Is Success And Dont Sent Mail
     */
    @Test
    public void testSaveSurveySuccessWhenSaveDataToTbl230IsSuccessAndSaveDataToTbl300IsSuccessAndDontSentMail() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod("2");
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL230、キー：");
        assertThat(logAppender.getEvents().get(1).getMessage()).contains("登録を実行します。テーブル：TBL300、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-029/区分:N/チェック内容:Test Save Survey Success When Save Data To Tbl230
     *  Is Success And Save Data To Tbl300 Is Success And Sent Mail
     */
    @Test
    public void testSaveSurveySuccessWhenSaveDataToTbl230IsSuccessAndSaveDataToTbl300IsSuccessAndSentMail() throws BusinessException {
        TBL120Entity one = generateEntity("0", "0", "0");
        one.setUserType(String.valueOf(UserTypeCode.NONE.getCode()));
        prepareSecurityContextHolder(one);
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO)).thenReturn("123456789");
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyForm.setRdoNotificationMethod(CodeUtil.getValue(CommonConstants.CD017, CommonConstants.CD017_NOTIFI_BY_EMAIL));
        ML060Vo ml060Vo = generateML060Vo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML060Vo.class)).thenReturn(ml060Vo);
        surveyNotificationLogicImpl.saveSurvey(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL230、キー：");
        assertThat(logAppender.getEvents().get(1).getMessage()).contains("登録を実行します。テーブル：TBL300、キー：");
        assertThat(logAppender.getEvents().get(2).getMessage()).contains("登録を実行します。テーブル：TBL300、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-030/区分:N/チェック内容:Test Save Temporary When Save Data To Tbl235 Is Success And User Type Code Is Tokyo Staff
     */
    @Test
    public void testSaveTemporaryWhenSaveDataToTbl235IsSuccessAndUserTypeCodeIsTokyoStaff() throws BusinessException {
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-031/区分:N/チェック内容:Test Save Temporary When Save Data To Tbl235 Is Success And User Type Code Is City
     */
    @Test
    public void testSaveTemporaryWhenSaveDataToTbl235IsSuccessAndUserTypeCodeIsCity() throws BusinessException {
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-032/区分:N/チェック内容:Test Save Temporary 
     * When Save Data To Tbl235 Is Success And User Type Code Is System Administrator
     */
    @Test
    public void testSaveTemporaryWhenSaveDataToTbl235IsSuccessAndUserTypeCodeIsSystemAdministrator() throws BusinessException {
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");

        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-033/区分:N/チェック内容:Test Save Temporary
     *  When Save Data To Tbl235 Is Success And User Type Code Is Maintenance Company
     */
    @Test
    public void testSaveTemporaryWhenSaveDataToTbl235IsSuccessAndUserTypeCodeIsMaintenanceCompany() throws BusinessException {
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
        prepareSecurityContextHolder(entity);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
        SurveyForm surveyForm = getSurvayForm();
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).contains("登録を実行します。テーブル：TBL235、キー：");
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-034/区分:E/チェック内容:Test Save Temporary When Save Data To Tbl235 Is Unsuccess Throw Exception
     */
    @Test(expected = BusinessException.class)
    public void testSaveTemporaryWhenSaveDataToTbl235IsUnsuccessThrowException() throws BusinessException {
        Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO)).thenReturn("123456789");
        TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        prepareSecurityContextHolder(entity);
        SurveyForm surveyForm = getSurvayForm();
        Mockito.when(tbl235DAO.save(Mockito.any())).thenReturn(false);
        surveyNotificationLogicImpl.saveTemporary(surveyForm);
    }
}
