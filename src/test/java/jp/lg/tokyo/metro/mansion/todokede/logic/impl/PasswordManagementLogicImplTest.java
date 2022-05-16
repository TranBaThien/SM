/*
 * @(#) PasswordManagementLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/11
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.HashUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM004DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML035Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationApplicationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissuePasswordAfterSaveVo;

@RunWith(SpringRunner.class)
@Import(value = {CodeUtilConfig.class})
public class PasswordManagementLogicImplTest {

    @Mock
    private TBL120DAO tbl120DAO;

    @Mock
    private TBL110DAO tbl110DAO;

    @Mock
    private TBM004DAO tbm004DAO;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private IEMailLogic mailLogic;

    @InjectMocks
    private PasswordManagementLogicImpl passwordManagementLogic;

    protected MockHttpSession session;

    protected MockHttpServletRequest request;

    private static final String USER_ID = "1000000011";
    private static final String LOGIN_ID = "G00000011";
    private static final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private static final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private static final String CITY_CODE = "111111";
    private static final String DELETE_FLAG = "0";
    private static final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private static final int LOGIN_ERROR_COUNT = 0;
    private static final String MAIL_ADDRESS = "vutran26999@gmail.com";
    private static final String PASSWORD = "password_hash";
    private static final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.MINUTES);
    private static final String STOP_REASON = "stop reason";
    private static final LocalDateTime STOP_TIME = LocalDateTime.now();
    private static final String TEL_NO = "09999999";
    private static final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
    private static final String UPDATE_USER_ID = "G00000011";
    private static final String USER_AUTHORITY = "1";
    private static final String USER_NAME = "username 11";
    private static final String USER_NAME_PHONETIC = "username phonetic";
    private static final String USER_TYPE = "1";
    private static final String ST_G_ACCOUNT_LOCK_PERIOD = "60";
    private static final String ACCOUNT_LOCK_FLAG = "0";
    private static final String ACCOUNT_AVAILABLE_FLAG = "1";
    private static final String LOGIN_STATUS_FLAG = "0";
    private static final String VALIDITY_FLAG = "1";

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
    private static final String CITY_NAME = "kyoto";
    private static final int FLOOR_NUMBER = 111;
    private static final int HOUSE_NUMBER = 222;
    private static final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
    private static final String NEWEST_NOTIFICATION_NAME = "osaka";
    private static final String NEWEST_NOTIFICATION_NO = "newest kyoto";
    private static final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.now();
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
    private static final String ZIP_CODE = "12345678";
    private static final Timestamp UPDATE_DATETIME_2 = new Timestamp(new Date().getTime());

    // Setting variable
    private static final String G_ACCOUT_LOCK_PERIOD = "60";
    private static final String AAA0110_FILE_SIZE_MAX = "500000";
    private static final String ADVICE_DETAILS_INDENTION_MAX = "5";
    private static final String G_PASSWORD_VALID_PERIOD = "180";
    private static final String M_ACCOUT_LOCK_PERIOD = "60";
    private static final String M_PASSWORD_VALID_PERIOD = "180";
    private static final String M_LOGIN_INVALIDITY_TIMES_MAX = "5";
    private static final String TERMS_HTML_PATH = "/rule/rule.htm";
    private static final String APARTMENT_NOTICE_HTML_PATH = "/contets/apartment_notice.htm";
    private static final String CITY_NOTICE_HTML_PATH = "/contets/city_notice.htm";
    private static final String GCA0110_SEARCH_MAX = "2000";
    private static final String G_LOGIN_INVALIDITY_TIMES_MAX = "5";
    private static final String GCA0110_LIST_DISPLAY_MAX = "50";
    private static final String CITY_ONETIME_PASSWORD_PERIOD = "30";
    private static final String APARTMENT_ONETIME_PASSWORD_PERIOD = "30";
    private static final String CITY_LOGIN_URL = "http://localhost:8080/GAA0110";
    private static final String APARTMENT_LOGIN_URL = "http://localhost:8080/MAA0110";
    private static final String ML030_CONTACT_NAME = " 管理組合理事長殿";
    private static final String GEB0110_FILE_SIZE_MAX = "10M";
    private static final String GEB0110_FILE_COUNT_MAX = "3";

    // Mail variable
    private static final String MAIL_TEMPLATE = "パスワード再発行通知メール（管理組合向け）";
    private static final String MAIL_SUBJECT = "【東京都マンション管理状況届出】パスワード再発行完了のお知らせ";
    private static final String MAIL_SEND_FROM = "mansiondev@cmcglobal.com.vn";
    private static final String MAIL_REPLY_TO = "to@gmail.com";
    private static final String CONTACT_MAIL_ADDRESS = "vutran26999@gmail.com";
    private static final String WINDOW_CITY_NAME = "BBBBB";
    private static final String WINDOW_BE_LONG = "問合せ窓口_部署（区市町村向け）";
    private static final String WINDOW_TEL_NO = "TEL_123456780";
    private static final String WINDOW_FAX_NO = "FAX_987654320";
    private static final String WINDOW_MAIL_ADDRESS = "window2@gmail.com";
    private static final String CONTACT_NAME = "Demo";
    private static final Integer PASSWORD_PERIOD_INT = 30;

    /**
     * Before test
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

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001／区分：N／チェック内容：Test Get Government Staff User Login Info success
     * @throws BusinessException when has error
     */
    @Test
    public void getGovernmentStaffUserLoginInfoSuccess() throws BusinessException, SystemException {
        TBL120Entity entity = generateTBL120Entity();
        Mockito.when(tbl120DAO.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG)).thenReturn(entity);
        GovernmentStaffInfoVo result = passwordManagementLogic.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG);

        assertResult1(result, entity);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002／区分：E／チェック内容：Test Get Government Staff User Login Info return null
     * @throws BusinessException when has error
     */
    @Test
    public void getGovernmentStaffUserLoginInfoReturnNull() throws BusinessException {
        Mockito.when(tbl120DAO.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG)).thenReturn(null);
        GovernmentStaffInfoVo result = passwordManagementLogic.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG);

        assertThat(result).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-003／区分：E／チェック内容：Test Get Government Staff User Login Info throws BusinessException
     * @throws BusinessException when has error
     */
    @Test
    public void getGovernmentStaffUserLoginInfoThrowException() throws BusinessException {
        Mockito.when(tbl120DAO.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG)).thenReturn(null);
        GovernmentStaffInfoVo result = passwordManagementLogic.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG);

        assertThat(result).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-004／区分: I／チェック内容：Test Get Management AssociationInfo success
     * @throws BusinessException when has error
     * @throws SystemException when has error when copy properties
     */
    @Test
    public void getManagementAssociationInfoSuccess() throws BusinessException, SystemException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG)).thenReturn(entity);
        ManagementAssociationApplicationVo result =
                passwordManagementLogic.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG);

        assertResult2(result, entity);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-005／区分：E／チェック内容：Test Get Management AssociationInfo return null
     * @throws BusinessException when has error
     * @throws SystemException when has error when copy properties
     */
    @Test
    public void getManagementAssociationInfoReturnNull() throws BusinessException, SystemException {
        Mockito.when(tbl110DAO.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG)).thenReturn(null);
        ManagementAssociationApplicationVo result =
                passwordManagementLogic.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG);

        assertThat(result).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-006／区分：E／チェック内容：Test Get Management AssociationInfo throws Exception
     * @throws BusinessException when has error
     * @throws SystemException when has error when copy properties
     */
    @Test
    public void getManagementAssociationInfoThrowException() throws BusinessException, SystemException {
        Mockito.when(tbl110DAO.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG)).thenReturn(null);
        ManagementAssociationApplicationVo result =
                passwordManagementLogic.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG);

        assertThat(result).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-007／区分：N／チェック内容：Test Save Data To Tbl120 success
     * @throws BusinessException when has error
     */
    @Test
    public void saveDataToTbl120Success() throws BusinessException {
        TBL120Entity entity = generateTBL120Entity();
        Mockito.when(tbl120DAO.findByLoginId(LOGIN_ID)).thenReturn(Optional.of(entity));
        Mockito.when(tbl120DAO.save(entity)).thenReturn(updateDataToTbl120(entity));

        passwordManagementLogic.saveDataToTbl120(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-008／区分：E／チェック内容：Test Save Data To Tbl120 when login id not found then throw exception
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void saveDataToTbl120WhenLoginIdNotFoundThenThrowException() throws BusinessException {
        TBL120Entity entity = generateTBL120Entity();
        Mockito.when(tbl120DAO.findByLoginId(LOGIN_ID)).thenReturn(Optional.empty());
        Mockito.when(tbl120DAO.save(entity)).thenReturn(updateDataToTbl120(entity));

        passwordManagementLogic.saveDataToTbl120(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-009／区分：E／チェック内容：Test Save Data To Tbl120 fail
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void saveDataToTbl120WhenReturnNullAfterSaveThenThrowException() throws BusinessException {
        TBL120Entity entity = generateTBL120Entity();
        Mockito.when(tbl120DAO.findByLoginId(LOGIN_ID)).thenReturn(Optional.of(entity));
        Mockito.when(tbl120DAO.save(entity)).thenReturn(null);

        passwordManagementLogic.saveDataToTbl120(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0010／区分：N／チェック内容：Test Save Data To Tbl110 success
     * @throws BusinessException when has error
     */
    @Test
    public void saveDataToTbl110Success() throws BusinessException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(LOGIN_ID)).thenReturn(entity);
        Mockito.when(tbl110DAO.save(entity)).thenReturn(updateDataToTbl110(entity));

        passwordManagementLogic.saveDataToTbl110(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0011／区分：E／チェック内容：Test Save Data To Tbl110 when login id not found
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void saveDataToTbl1100WhenLoginIdNotFoundThenThrowException() throws BusinessException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(LOGIN_ID)).thenReturn(null);
        Mockito.when(tbl110DAO.save(entity)).thenReturn(updateDataToTbl110(entity));

        passwordManagementLogic.saveDataToTbl110(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0012／区分：E／チェック内容：Test Save Data To Tbl110 fail
     * @throws BusinessException when has error
     */
    @Test(expected = NullPointerException.class)
    public void saveDataToTbl110WhenReturnNullAfterSaveThenThrowException() throws BusinessException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        Mockito.when(tbl110DAO.findByLoginId(LOGIN_ID)).thenReturn(entity);
        Mockito.when(tbl110DAO.save(entity)).thenReturn(null);

        passwordManagementLogic.saveDataToTbl110(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0013／区分：E／チェック内容：Test Save Data To Tbl110 not reference with Tbl100
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void saveDataToTbl110WhenNotExistReferenceBetweenTbl110AndTbl100ThenThrowException() throws BusinessException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        entity.setTbl100(null);
        Mockito.when(tbl110DAO.findByLoginId(LOGIN_ID)).thenReturn(entity);
        Mockito.when(tbl110DAO.save(entity)).thenReturn(entity);

        passwordManagementLogic.saveDataToTbl110(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0014／区分：N／チェック内容：Test Save Data To Tbl110 when tbl100.getNewestNotificationName() is null
     * @throws BusinessException when has error
     */
    @Test
    public void saveDataToTbl110WhenTbl100GetNewsestNotificationIsNullThenGetFromSession() throws BusinessException {
        TBL110Entity entity = generateTBL110Entity(APARTMENT_ID);
        entity.getTbl100().setNewestNotificationName(null);
        Mockito.when(tbl110DAO.findByLoginId(LOGIN_ID)).thenReturn(entity);
        Mockito.when(tbl110DAO.save(entity)).thenReturn(entity);

        passwordManagementLogic.saveDataToTbl110(LOGIN_ID);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0015／区分：N／チェック内容：Test Get ML030 Template success
     * @throws BusinessException when has error
     */
    @Test
    public void getML030TemplateSuccess() throws BusinessException {
        ML030Vo ml030Vo = generateML030Vo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML030Vo.class)).thenReturn(ml030Vo);
        ReissuePasswordAfterSaveVo param = new ReissuePasswordAfterSaveVo();
        param.setApartmentName(APARTMENT_NAME);
        param.setCityCode(CITY_CODE);
        param.setContactMailAddress(CONTACT_MAIL_ADDRESS);
        param.setLoginUrl(CITY_LOGIN_URL);
        param.setPassword(PASSWORD);
        param.setPasswordPeriod(PASSWORD_PERIOD_INT);
        ML030Vo result = passwordManagementLogic.getML030Template(param);

        assertThat(result).isEqualToComparingFieldByField(ml030Vo);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0016／区分：E／チェック内容：Test Get ML030 Template throws BusinessException
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void getML030TemplateThrowException() throws BusinessException {
        Mockito.when(mailLogic.getCommonTemplateMail(ML030Vo.class)).thenThrow(BusinessException.class);
        passwordManagementLogic.getML030Template(Mockito.any());
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0017／区分: I／チェック内容：Test Get ML035 Template success
     * @throws BusinessException when has error
     */
    @Test
    public void getML035TemplateSuccess() throws BusinessException {
        ML035Vo ml035Vo = generateML035Vo();
        Mockito.when(mailLogic.getCommonTemplateMail(ML035Vo.class)).thenReturn(ml035Vo);
        ReissuePasswordAfterSaveVo param = new ReissuePasswordAfterSaveVo();
        param.setCityCode(CITY_CODE);
        param.setContactMailAddress(CONTACT_MAIL_ADDRESS);
        param.setPassword(PASSWORD);
        param.setPasswordPeriod(PASSWORD_PERIOD_INT);
        ML035Vo result = passwordManagementLogic.getML035Template(param);

        assertThat(result).isEqualToComparingFieldByField(ml035Vo);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0018／区分：E／チェック内容：Test Get ML035 Template throws BusinessException
     * @throws BusinessException when has error
     */
    @Test(expected = BusinessException.class)
    public void getML035TemplateThrowException() throws BusinessException {
        Mockito.when(mailLogic.getCommonTemplateMail(ML035Vo.class)).thenThrow(BusinessException.class);
        passwordManagementLogic.getML035Template(Mockito.any());
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0019／区分：N／チェック内容：Test sendML030 success
     * @throws BusinessException when has error
     */
    @Test
    public void sendML030Success() throws BusinessException {
        ML030Vo ml030Vo = generateML030Vo();
        passwordManagementLogic.sendML030(ml030Vo);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-0020／区分：N／チェック内容：Test sendML035 success
     * @throws BusinessException when has error
     */
    @Test
    public void sendML035Success() throws BusinessException {
        ML035Vo ml035Vo = generateML035Vo();
        passwordManagementLogic.sendML035(ml035Vo);
    }

    private TBL120Entity updateDataToTbl120(TBL120Entity entity) {
        entity.setPassword(HashUtil.getUnixCript(CommonConstants.BLANK));
        entity.setPasswordPeriod(
                UPDATE_DATETIME.plusMinutes(Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.CITY_ONETIME_PASSWORD_PERIOD))));
        entity.setLoginErrorCount(CommonConstants.NUM_0);
        entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CommonConstants.CD025, CommonConstants.CD025_UNCHANGED));
        entity.setLoginStatusFlag(CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_NOT_LOGGED_IN));
        entity.setUpdateUserId(LOGIN_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        return entity;
    }

    private TBL110Entity updateDataToTbl110(TBL110Entity entity) {
        entity.setPassword(HashUtil.getUnixCript(CommonConstants.BLANK));
        entity.setPasswordPeriod(
                UPDATE_DATETIME.plusMinutes(Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.CITY_ONETIME_PASSWORD_PERIOD))));
        entity.setLoginErrorCount(CommonConstants.NUM_0);
        entity.setAccountLockTime(null);
        entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CommonConstants.CD025, CommonConstants.CD025_UNCHANGED));
        entity.setLoginStatusFlag(CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_NOT_LOGGED_IN));
        entity.setUpdateUserId(LOGIN_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_2);
        return entity;
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
        entity.setUpdateDatetime(UPDATE_DATETIME_2);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setZipCode(ZIP_CODE);

        return entity;
    }

    private TBL110Entity generateTBL110Entity(String apartmentId) {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(apartmentId);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(ACCOUNT_AVAILABLE_FLAG);
        entity.setAvailability(LOGIN_STATUS_FLAG);
        entity.setPassword(PASSWORD);
        entity.setAccountLockTime(ACCOUNT_LOCK_TIME);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setPasswordPeriod(PASSWORD_PERIOD);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setStopTime(STOP_TIME);
        entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_2);
        entity.setTbl100(generateTBL100Entity(apartmentId));
        entity.setValidityFlag(VALIDITY_FLAG);
        return entity;
    }

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
        entity.setUpdateDatetime(UPDATE_DATETIME);
        return entity;
    }

    private TBM004Entity generateTBM004Entity(String setNo, String setTargetNameEng, String setPoint) {
        TBM004Entity entity = new TBM004Entity();
        entity.setSetNo(setNo);
        entity.setSetTargetNameEng(setTargetNameEng);
        entity.setSetPoint(setPoint);
        return entity;
    }

    private List<TBM004Entity> generateTBM004EntityList() {
        List<TBM004Entity> settingList = new ArrayList<TBM004Entity>();
        settingList.add(generateTBM004Entity("1", CommonConstants.CITY_ONETIME_PASSWORD_PERIOD, CITY_ONETIME_PASSWORD_PERIOD));
        settingList.add(generateTBM004Entity("2", CommonConstants.CITY_LOGIN_URL, CITY_LOGIN_URL));
        return settingList;
    }

    private Map<String, String> generateSettingMap() {
        return generateTBM004EntityList().stream().collect(Collectors.toMap(TBM004Entity::getSetTargetNameEng, TBM004Entity::getSetPoint));
    }

    private ML030Vo generateML030Vo() {
        ML030Vo vo = new ML030Vo();
        vo.setApartmentName(APARTMENT_NAME);
        vo.setContactName(CONTACT_NAME);
        vo.setPasswordPeriod(PASSWORD_PERIOD_INT);
        vo.setLoginUrl(CITY_LOGIN_URL);
        vo.setPassword(PASSWORD);
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

    private ML035Vo generateML035Vo() {
        ML035Vo vo = new ML035Vo();
        vo.setContactName(CONTACT_NAME);
        vo.setPasswordPeriod(PASSWORD_PERIOD_INT);
        vo.setPassword(PASSWORD);
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

    private void assertResult1(GovernmentStaffInfoVo result, TBL120Entity entity) throws SystemException {
        GovernmentStaffInfoVo vo = new GovernmentStaffInfoVo();
        CommonUtils.copyProperties(vo, entity);
        assertThat(result).isEqualToComparingFieldByField(vo);
    }

    private void assertResult2(ManagementAssociationApplicationVo result, TBL110Entity entity) throws SystemException {
        ManagementAssociationApplicationVo vo = new ManagementAssociationApplicationVo();
        CommonUtils.copyProperties(vo, entity);
        assertThat(result).isEqualToComparingFieldByField(vo);
    }

}
