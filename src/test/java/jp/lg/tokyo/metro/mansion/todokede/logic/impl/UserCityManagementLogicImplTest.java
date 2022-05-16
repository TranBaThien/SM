package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.UserCityForm;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserCityVo;

@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class UserCityManagementLogicImplTest {
    private final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.UserCityManagementLogicImpl";
    private final String CITY_CODE_1 = "111111";
    private final String CITY_CODE_2 = "130001";
    private final String CITY_CODE_3 = "131016";
//    private final String CITY_CODE_4 = "131024";
    
    private final String CITY_NAME_1 = "小笠原村";
    private final String CITY_REMOVE_2 = "東京都";
    private final String CITY_NAME_3 = "千代田区";
//    private final String CITY_NAME_4 = "中央区";
    private final String USERID = "1000000030";
    private final String TELNO = "0324-969-966";
    private final String TELNO_INCORRECT_FORMAT = "0324969966";
    private final String TELNO_1 = "0324";
    private final String TELNO_2 = "969";
    private final String TELNO_3 = "966";
    
//    private final String TELNOINCORRECT = "0324969966";
    private final String LOGIN_ID = "G0110101";
    private final String LOGIN_ID_6_REGIX = "000001";
    private final String PASSWORD = "123456789";
    private final String USER_ID = "10000001";
    private final String USER_ID_30 = "1000000030";
    private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String DELETE_FLAG = "0";
    private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
    private final int LOGIN_ERROR_COUNT = 0;
    private final String MAIL_ADDRESS = "abc@gmail.com";
    private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private final String STOP_REASON = "stop reason";
    private final LocalDateTime STOP_TIME = LocalDateTime.now();
    private final String TEL_NO = "09999999";
    private final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
    private final String UPDATE_USER_ID = "G0000001";
    private final String USER_NAME = "username";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    private final String USERID_ONE = "000001";
    private final String USERTYPE_INCORECT_FORMAT = "H";

    private final String ACCOUNT_AVAILABLE_FLAG_1 = "1";
//    private final String ACCOUNT_AVAILABLE_FLAG_2 = "2";
//    private final String CD024_1 = "利用可能";
//    private final String CD024_2 = "利用不可";

//    private final String CITYNAME_1 = "小笠原村";
//    private final String CITYNAME_2 = "区市町村";
//    private final String CITYNAME_CODE_1 = "1";
//    private final String CITYNAME_CODE_2 = "2";

    
    private final String LOGIN_STATUS_FLAG_1 = "0";
//    private final String LOGIN_STATUS_FLAG_2 = "1";
//    private final String CD026_1 = "ログインしていない";
//    private final String CD026_2 = "ログイン中";

//    private final String USER_ID_1 = "1000000011";
//    private final String USER_ID_2 = "1000000012";


    private final String USER_TYPE_1 = "1";
    private final String USER_TYPE_2 = "2";
    private final String USER_TYPE_3 = "3";
//    private final String USER_TYPE_4 = "4";
//    private final String CD027_1 = "都職員";
//    private final String CD027_2 = "区市町村";
//    private final String CD027_3 = "システム管理者";
//    private final String CD027_4 = "保守業者";
    //out 
    
    
    
    @Mock
    private TBL120DAO tbl120DAO;
    @Mock
    private TBL120BDAO tbl120BDAO;
    @Mock
    private TBM001DAO tbm001DAO;
    @Mock
    SequenceUtil sequenceUtil;
    @Mock
    private HttpSession session;

    @InjectMocks
    private UserCityManagementLogicImpl userCityManagementLogicImpl;

    private List<TBM001Entity> getListCityName(){
        List<TBM001Entity> list  = new ArrayList<TBM001Entity>();
        TBM001Entity entity = new TBM001Entity();
        TBM001Entity entity2 = new TBM001Entity();
        TBM001Entity entity3 = new TBM001Entity();
        entity.setCityCode(CITY_CODE_1);
        entity.setCityName(CITY_NAME_1);
        entity2.setCityCode(CITY_CODE_2);
        entity2.setCityName(CITY_REMOVE_2);
        entity3.setCityCode(CITY_CODE_3);
        entity3.setCityName(CITY_NAME_3);
        list.add(entity);
        list.add(entity2);
        list.add(entity3);
        return list;
    }

    private TBL120Entity generateEntity(String accountLockFlag, String accountAvailableFlag, String loginStatusFlag) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(String.valueOf(UserTypeCode.TOKYO_STAFF.getCode()));
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

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

    UserCityForm getUserCityForm(String UserId, String userType) {
        UserCityForm form = new UserCityForm();
        form.setUserId(UserId);
        form.setTxtLoginid(LOGIN_ID_6_REGIX);
        form.setPwdPassword(PASSWORD);
        form.setRdoUserType(userType);
        form.setTxtUserName(USER_NAME);
        form.setTxtUserNamePhonetic(USER_NAME_PHONETIC);
        form.setLstCity(CITY_CODE_1);
        form.setTxtMail(MAIL_ADDRESS);
        form.setTxtPhonenumber1(TELNO_1);
        form.setTxtPhonenumber2(TELNO_2);
        form.setTxtPhonenumber3(TELNO_3);
        form.setChkAvailability(ACCOUNT_AVAILABLE_FLAG_1);
        form.setTxtContact(STOP_REASON);
        return form;
    }

    private void assertEntity( UserCityVo vo){
        assertThat(vo.getTxtLoginid()).isEqualTo(USERID_ONE);
        assertThat(vo.getLstCitylist().get(0).getValue()).isEqualTo(CITY_CODE_1);
        assertThat(vo.getLstCitylist().get(1).getValue()).isEqualTo(CITY_CODE_2);
        assertThat(vo.getLstCitylist().get(2).getValue()).isEqualTo(CITY_CODE_3);
    }
    private void  assertEntityEdit(UserCityVo vo){
        assertThat(vo.getRdoUserType()).isEqualTo(USER_TYPE_2);
        assertThat(vo.getLstCity()).isEqualTo(CITY_CODE_1);
        assertThat(vo.getLstCitylist().get(0).getValue()).isEqualTo(CITY_CODE_1);
        assertThat(vo.getLstCitylist().get(1).getValue()).isEqualTo(CITY_CODE_2);
        assertThat(vo.getLstCitylist().get(2).getValue()).isEqualTo(CITY_CODE_3);
        assertThat(vo.getTxtUserName()).isEqualTo(USER_NAME);
        assertThat(vo.getTxtUserNamePhonetic()).isEqualTo(USER_NAME_PHONETIC);
        assertThat(vo.getTxtMail()).isEqualTo(MAIL_ADDRESS);
        assertThat(vo.getTxtMailConfirm()).isEqualTo(MAIL_ADDRESS);
        assertThat(vo.getTxtPhonenumber1() + "-" + vo.getTxtPhonenumber2() + "-" + vo.getTxtPhonenumber3()).isEqualTo(TELNO);
        assertThat(vo.getChkAvailability()).isEqualTo("1");
        assertThat(vo.getTxtContact()).isEqualTo(STOP_REASON);
        assertThat(vo.getTxtLoginid()).isEqualTo(LOGIN_ID.substring(1, 7));
    }

    private void  assertEntityEdit_2(UserCityVo vo){
        assertThat(vo.getRdoUserType()).isEqualTo(USER_TYPE_1);
        assertThat(vo.getLstCity()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getLstCitylist()).isEqualTo(null);
        assertThat(vo.getTxtUserName()).isEqualTo(USER_NAME);
        assertThat(vo.getTxtUserNamePhonetic()).isEqualTo(USER_NAME_PHONETIC);
        assertThat(vo.getTxtMail()).isEqualTo(MAIL_ADDRESS);
        assertThat(vo.getTxtMailConfirm()).isEqualTo(MAIL_ADDRESS);
        assertThat(vo.getTxtPhonenumber1() + "-" + vo.getTxtPhonenumber2() + "-" + vo.getTxtPhonenumber3()).isEqualTo(TELNO);
        assertThat(vo.getChkAvailability()).isEqualTo("1");
        assertThat(vo.getTxtContact()).isEqualTo(STOP_REASON);
        assertThat(vo.getTxtLoginid()).isEqualTo(LOGIN_ID.substring(1, 7));
    }
    
    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-007／区分：N／チェック内容：Test get GetDataShow When List City Name Is CITY
     */
    @Test
    public void testGetDataShow_WhenListCityNameIsCITY () throws BusinessException {
        List<TBM001Entity> list  = getListCityName();
        Mockito.when(tbl120BDAO.getNewLoginId()).thenReturn("1");
        Mockito.when(tbm001DAO.getMunicipalMasterNotDelete()).thenReturn(list);
        UserCityVo vo = userCityManagementLogicImpl.getDataShow();
        assertEntity(vo);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-008／区分：E／チェック内容：Test get UserInfo When TelNo incorrect Throw Exception
     */
    @Test(expected = Exception.class)
    public void testGetUserInfo_TelNoIncorrectFormat () throws BusinessException {
        List<TBM001Entity> list  = getListCityName();
        TBL120Entity entity = generateEntity(null,"1" ,"0");
        entity.setTelNo(TELNO_INCORRECT_FORMAT);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbm001DAO.getMunicipalMasterNotDelete()).thenReturn(list);
        userCityManagementLogicImpl.getUserInfor(USERID);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-009／区分：E／チェック内容：Test get UserInfo When UserInfo is null and ThrowException
     */
    @Test(expected = Exception.class)
    public void testGetUserInfoWhenUserInfoIsNullThrowException () throws BusinessException {
        TBL120Entity entity = null;
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(entity);
        userCityManagementLogicImpl.getUserInfor(USERID);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-0010／区分：N／チェック内容：Test get UserInfo When TypeUser is city
     */
    @Test
    public void testGetUserInfoWhenUserTypeCodeIsCity () throws BusinessException {
        List<TBM001Entity> list  = getListCityName();
        TBL120Entity entity = generateEntity(null,"1" ,"0");
        entity.setTelNo(TELNO);
        entity.setUserType(USER_TYPE_2);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbm001DAO.getMunicipalMasterNotDelete()).thenReturn(list);
        UserCityVo vo = userCityManagementLogicImpl.getUserInfor(USERID);
        assertEntityEdit(vo);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-011／区分: I／チェック内容：Test get UserInfo When TypeUser is not City
     */
    @Test
    public void testGetUserInfoWhenUserTypeCodeIsNotCity () throws BusinessException {
        List<TBM001Entity> list  = getListCityName();
        TBL120Entity entity = generateEntity(null,"1" ,"0");
        entity.setTelNo(TELNO);
        entity.setUserType(USER_TYPE_1);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(entity);
        Mockito.when(tbm001DAO.getMunicipalMasterNotDelete()).thenReturn(list);
        UserCityVo vo = userCityManagementLogicImpl.getUserInfor(USERID);
        assertEntityEdit_2(vo);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-013／区分：N／チェック内容：Test check the existence of loginId when LoginId exist
     */
    @Test
    public void testCheckExistLoginId_WhenEntitysIsNotEmpty () throws BusinessException {
        List<TBL120Entity> entitys = new ArrayList<TBL120Entity>();
        TBL120Entity one = generateEntity(null,"1" ,"0");
        entitys.add(one);
        Mockito.when(tbl120DAO.getGovernmentStaffInfoByLikeLoginId(Mockito.anyString())).thenReturn(entitys);
        Boolean check = userCityManagementLogicImpl.checkExistLoginId(LOGIN_ID);
        assertThat(check).isEqualTo(true);
        LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-014／区分：N／チェック内容：Test check the existence of loginId when LoginId not exist
     */
    @Test
    public void testCheckExistLoginId_WhenEntitysIsEmpty () throws BusinessException {
        List<TBL120Entity> entitys = new ArrayList<TBL120Entity>();
        Mockito.when(tbl120DAO.getGovernmentStaffInfoByLikeLoginId(Mockito.anyString())).thenReturn(entitys);
        Boolean check = userCityManagementLogicImpl.checkExistLoginId(LOGIN_ID);
        assertThat(check).isEqualTo(false);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-015／区分：N／チェック内容：Test Get Government Info When User Id not exist
     */
    @Test 
    public void TestGetGovernmentInfo_WhenEntityIsNull () throws BusinessException { 
    Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(null);
    TBL120Entity actual = userCityManagementLogicImpl.getGovernmentInfo(USERID);
    assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-016／区分：N／チェック内容：Test Get Government Info flag When User Id exist
     */
    @Test
    public void TestGetGovernmentInfoWhenEntityIsNotNull () throws BusinessException {
        TBL120Entity one =  generateEntity(null, LOGIN_STATUS_FLAG_1 ,"0");
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(one);
        TBL120Entity actual = userCityManagementLogicImpl.getGovernmentInfo(USERID);
        assertThat(actual).isEqualTo(one);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-017／区分：N／チェック内容：Test save User Info when user user type is TOKYO_STAFF
     */
    @Test
    public void TestcaveUserInfor_WhenUserTypeCodeIs_TokyoStaff() throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "1", "0");
        prepareSecurityContextHolder(one);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-018／区分：N／チェック内容：Test save User Info when user user type is City
     */
    @Test
    public void TestSaveUserType_WhenUserTypeCodeIsCity () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_2);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "1", "0");
        prepareSecurityContextHolder(one);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-019／区分：N／チェック内容：Test save User Info when user user type is SYSTEM_ADMIN
     */
    @Test
    public void TestSaveUserType_WhenUserTypeCodeIsSystemAdmin () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_3);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "1", "0");
        prepareSecurityContextHolder(one);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-020／区分：E／チェック内容：Test save User Info when user user type is SYSTEM_ADMIN
     */
    @Test(expected = BusinessException.class)
    public void TestSaveUser_UserTypeIsIncorrectFormat () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_3);
        form.setRdoUserType(USERTYPE_INCORECT_FORMAT);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "1", "0");
        prepareSecurityContextHolder(one);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-021／区分：E／チェック内容：Test update User Info  When the Data corresponding with the UserId not exist
     */
    @Test(expected = BusinessException.class)
    public void testUpdateUserInfo_WhenEntityIsNull () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_3);
        form.setUserId(USERID);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(Mockito.anyString())).thenReturn(null);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-022／区分：N／チェック内容：Test update User Info  When the UserType is City
     */
    @Test
    public void TestSaveUserType_WhenUserTypeIsCity () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_2);
        form.setUserId(USER_ID_30);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "1", "0");
        prepareSecurityContextHolder(one);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(form.getUserId())).thenReturn(one);
        Mockito.when(tbl120DAO.existsById(Mockito.anyString())).thenReturn(true);
        userCityManagementLogicImpl.saveUserInfor(form);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-023／区分：N／チェック内容：Test update User Info  When the UserType is not City
     */
    @Test
    public void TestSaveUserTypeWhenUserTypeCodeISystemAdminAndUserHasExisted () throws BusinessException {
        UserCityForm form = getUserCityForm(CommonConstants.BLANK, USER_TYPE_1);
        form.setChkAvailability("2");
        form.setUserId(USER_ID_30);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Map<String, String> systemSettings = new HashMap<>();
        systemSettings.put(CommonConstants.G_PASSWORD_VALID_PERIOD, "60");
        Mockito.when(session.getAttribute(CommonConstants.SYSTEM_SETTING)).thenReturn(systemSettings);
        TBL120Entity one = generateEntity("0", "2", "0");
        prepareSecurityContextHolder(one);
        Mockito.when(tbl120DAO.getGovernmentStaffInfo(form.getUserId())).thenReturn(one);
        Mockito.when(tbl120DAO.existsById(Mockito.anyString())).thenReturn(true);
        userCityManagementLogicImpl.saveUserInfor(form);
    }
}
