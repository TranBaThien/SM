/*
 * @(#) AdviceNotificationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Dec 12, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL220;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL225;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL220DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL225DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM003DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM003Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.AdviceNotificationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceTemplateVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML050Vo;

/**
 * @author nvlong1
 *
 */
@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class AdviceNotificationLogicImplTest {

	@Mock
	private IEMailLogic mailLogic;

	@Mock
	private SequenceUtil sequenceUtil;

	@Mock
	private Authentication authentication;

	@Mock
	private TBL100DAO tbl100DAO;

	@Mock
	private TBL200DAO tbl200DAO;

	@Mock
	private TBL220DAO tbl220DAO;

	@Mock
	private TBL225DAO tbl225DAO;

	@Mock
	private TBL300DAO tbl300DAO;
	
	@Mock
	private TBM001DAO tbm001DAO;

	@Mock
	private TBM003DAO tbm003DAO;

	@InjectMocks
	private AdviceNotificationLogicImpl adviceNotificationLogicImpl;

	protected MockHttpSession session;

	protected MockHttpServletRequest request;

	private final String CITY_ONETIME_PASSWORD_PERIOD = "30";
	private final String CITY_LOGIN_URL = "http:localhost:8080/GAA0110";
	private final String FAILED_HANDLER_PATH = "jp.lg.tokyo.metro.mansion.todokede.logic.impl.AdviceNotificationLogicImpl";
	/* Create TBL100Entity */
	private final String APARTMENT_ID_TBL100 = "11000000001";
	private final String MAIL_ADDRESS_TBL100 = "abc@gmail.com";
	private final String NEWEST_NOTIFICATION_NAME_TBL100 = "osaka";
	private final String NEWEST_NOTIFICATION_NO_TBL100 = "11000000001";

	private TBL100Entity newTBL100Entity(String apartmentId) {
		TBL100Entity entity = new TBL100Entity();

		entity.setApartmentId(apartmentId);
		entity.setMailAddress(MAIL_ADDRESS_TBL100);
		entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME_TBL100);
		entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);

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

	private TBL200Entity newTBL200Entity(String notificationNo) {

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

		return entity;
	}

	/* Create TBM001Entity */
	private final String CITY_CODE_TBM001 = "111111";
	private final String CHIEF_NAME_TBM001 = "abc";
	private final String CITY_NAME_TBM001 = "abc";
	private final String DOCUMENT_NO_TBM001 = "qrtufxfb";

	private TBM001Entity newTBM001Entity(String cityCode) {
		TBM001Entity entity = new TBM001Entity();

		entity.setCityCode(cityCode);
		entity.setChiefName(CHIEF_NAME_TBM001);
		entity.setCityName(CITY_NAME_TBM001);
		entity.setDocumentNo(DOCUMENT_NO_TBM001);

		return entity;
	}

	/* Create List<TBM003Entity> */
	private final String ADVICE_TEMPLATE_NO_TBM003 = "100";
	private final String ADVICE_TEMPLATE_DETAIL_TBM003 = "abc1";
	private final String ADVICE_TEMPLATE_OVERVIEW_TBM003 = "abcdebc";

	private List<TBM003Entity> listTBM003Entity() {
		List<TBM003Entity> listEntity = new ArrayList<TBM003Entity>();
		TBM003Entity entity = new TBM003Entity();

		entity.setAdviceTemplateNo(ADVICE_TEMPLATE_NO_TBM003);
		entity.setAdviceTemplateDetail(ADVICE_TEMPLATE_DETAIL_TBM003);
		entity.setAdviceTemplateOverview(ADVICE_TEMPLATE_OVERVIEW_TBM003);
		listEntity.add(entity);

		return listEntity;
	}

	/* Create TBL220Entity */
	private final String ADVICE_NO_TBL220 = "1000000173";

	private TBL220Entity newTBL220Entity(String adviceNumber) {
		TBL220Entity entity = new TBL220Entity();
		entity.setAdviceNo(adviceNumber);

		return entity;
	}

	/* Create TBL225Entity */
	private final String TEMP_NO_TBL225 = "100000";
	private final String NOTIFICATION_NO_TBL225 = "111000";
	private final String TEMP_KBN_TBL225 = "3";

	private TBL225Entity newTBL225Entity(String notificationNo, String temporaryStorage) {
		TBL225Entity entity = new TBL225Entity();
		entity.setTempNo(TEMP_NO_TBL225);

		return entity;
	}

	/* Create AdviceNotificationForm */
	private final String APARTMENT_ID_FORM = "11000000001";
	private final String APARTMENT_NAME_FORM = "abc";
	private final String APARTMENT_NAME_PHONETIC_FORM = "abc";
	private final String ADDRESS_FORM = "abc";
	private final String NOTIFICATION_NO_FORM = "abcccac";
	private final int NUM_ROW_ADVICE_FORM = 1;
	private final String KEY_ADVICE_NO_FORM = "1";
	private final String NEWEST_NOTIFICATION_NO_FORM = "11000000001";
	private final String LBL_RECIPIENT_NAME_APARTMENT_FORM = "aaaaaa";
	private final String LBL_RECIPIENT_NAME_USER_FORM = "bbbbbbb";
	private final String LBL_ADDRESS_FORM = "cccccc";
	private final String LBL_APARTMENT_NAME_FORM = "dddđ";
	private final String LBL_NOTIFICATION_DATE_FORM = "2019/12/07";
	private final String LBL_EVIDENCE_BAR_FORM = "1";
	private final String[] LST_EVIDENCE_NO_FORM = { "1", "2", "3" };
	private final String ADVICE_FORM = "1";
	private final String CHK_CONFIRM_FORM = "0";
	private final String TXT_APPENDIX_NO_FORM = "aaaaaaa";
	private final String TXT_DOCUMENT_NO_FORM = "eeeeeee";
	private final String CAL_NOTICE_DATE_FORM = "2019/12/07";
	private final String TXT_SENDER_FORM = "eeeeeeee";
	private final String EVIDENCE_NO_FORM = "1";
	private final String TXT_ADVICE_DETAILS_FORM = "qqqqqqqqq";
	private final String RDO_NOTIFICATION_METHOD_FORM = "2";
	private final String CONTACT_MAIL_ADDRESS_FORM = "nvlong@gmail.com";
	

	private AdviceNotificationForm adviceNotificationForm() {
		AdviceNotificationForm adviceNotificationForm = new AdviceNotificationForm();
		adviceNotificationForm.setApartmentId(APARTMENT_ID_FORM);
		adviceNotificationForm.setApartmentName(APARTMENT_NAME_FORM);
		adviceNotificationForm.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC_FORM);
		adviceNotificationForm.setAddress(ADDRESS_FORM);
		adviceNotificationForm.setNotificationNo(NOTIFICATION_NO_FORM);
		adviceNotificationForm.setNumRowAdvice(NUM_ROW_ADVICE_FORM);
		adviceNotificationForm.setKeyAdviceNo(KEY_ADVICE_NO_FORM);
		adviceNotificationForm.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_FORM);
		adviceNotificationForm.setLblAddress(LBL_ADDRESS_FORM);
		adviceNotificationForm.setLblApartmentName(LBL_APARTMENT_NAME_FORM);
		adviceNotificationForm.setLblEvidenceBar(LBL_EVIDENCE_BAR_FORM);
		adviceNotificationForm.setLblNotificationDate(LBL_NOTIFICATION_DATE_FORM);
		adviceNotificationForm.setLblRecipientNameApartment(LBL_RECIPIENT_NAME_APARTMENT_FORM);
		adviceNotificationForm.setLblRecipientNameUser(LBL_RECIPIENT_NAME_USER_FORM);
		adviceNotificationForm.setLstEvidenceNo(LST_EVIDENCE_NO_FORM);
		adviceNotificationForm.setAdvice(ADVICE_FORM);
		adviceNotificationForm.setChkConfirm(CHK_CONFIRM_FORM);
		adviceNotificationForm.setTxtAdviceDetails(TXT_ADVICE_DETAILS_FORM);
		adviceNotificationForm.setTxtAppendixNo(TXT_APPENDIX_NO_FORM);
		adviceNotificationForm.setTxtDocumentNo(TXT_DOCUMENT_NO_FORM);
		adviceNotificationForm.setTxtSender(TXT_SENDER_FORM);
		adviceNotificationForm.setCalNoticeDate(CAL_NOTICE_DATE_FORM);
		adviceNotificationForm.setEvidenceNo(EVIDENCE_NO_FORM);
		adviceNotificationForm.setRdoNotificationMethod(RDO_NOTIFICATION_METHOD_FORM);
		adviceNotificationForm.setContactMailAddress(CONTACT_MAIL_ADDRESS_FORM);

		List<AdviceTemplateVo> adviceTemplateVoes = new ArrayList<AdviceTemplateVo>();
		adviceTemplateVoes.add(new AdviceTemplateVo("a", "abc"));
		adviceTemplateVoes.add(new AdviceTemplateVo("b", "bcd"));
		adviceNotificationForm.setLstAdvice(adviceTemplateVoes);

		List<CodeVo> lstRdoNotificationMethod = new ArrayList<CodeVo>();
		CodeVo codeVo = new CodeVo();
		codeVo.setId("1");
		codeVo.setValue("1");
		codeVo.setLabel("abc");
		lstRdoNotificationMethod.add(codeVo);
		adviceNotificationForm.setLstRdoNotificationMethod(lstRdoNotificationMethod);

		return adviceNotificationForm;

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

	// Mail variable
	private final String APARTMENT_NAME_MAIL = "パスワード再発行通知メール（管理組合向け）";
	private final String CONTACT_NAME_MAIL = "Demo";
	private final String LOGIN_URL_MAIL = "http:localhost:8080/GFA0110/1000000001";
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

	private ML050Vo generateML050Vo() {
		ML050Vo vo = new ML050Vo();
		vo.setApartmentName(APARTMENT_NAME_MAIL);
		vo.setCityName(CITY_NAME_MAIL);
		vo.setContactMailAddress(CONTACT_MAIL_ADDRESS_MAIL);
		vo.setContactName(CONTACT_NAME_MAIL);
		vo.setLoginUrl(LOGIN_URL_MAIL);
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

	/* Start test get Advisory Notice By Id */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-001/区分:N/チェック内容:Test Get Advisory Notice By Id Success
	 */
	@Test
	public void testGetAdvisoryNoticeByIdSuccess() {

		TBL220Entity entity = newTBL220Entity(ADVICE_NO_TBL220);
		Mockito.when(tbl220DAO.getAdvisoryNoticeById(Mockito.anyString())).thenReturn(entity);
		assertThat(entity).isEqualToComparingFieldByField(adviceNotificationLogicImpl.getAdvisoryNoticeById(ADVICE_NO_TBL220));
	}
	/* End test getAdvisoryNoticeById */

	/* Start test check exclusive  */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-002/区分:N/チェック内容:Test Check Exclusive When NotificationNo Unequal
	 * @throws BusinessException 
	 */
	@Test
	public void testCheckExclusiveWhenNotificationNoUnequal() throws BusinessException {
		TBL100Entity entity = newTBL100Entity(APARTMENT_ID_TBL100);

		String apartmentIdPreviousScreen = APARTMENT_ID_TBL100;
		String newestNotificationNoPreviousScreen = "100000002";
		Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity);
		assertThat(CommonConstants.ZERO).isEqualTo(adviceNotificationLogicImpl.exclusiveCheck(apartmentIdPreviousScreen, newestNotificationNoPreviousScreen));
	}

	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-003/区分:N/チェック内容:Test Check Exclusive When NotificationNo Equal And Advice Done Flag Is Implemented
	 * @throws BusinessException 
	 */
	@Test
	public void testCheckExclusiveWhenNotificationNoEqualAndAdviceDoneFlagIsImplemented() throws BusinessException {
		TBL100Entity entity = newTBL100Entity(APARTMENT_ID_TBL100);

		String apartmentIdPreviousScreen = APARTMENT_ID_TBL100;
		String newestNotificationNoPreviousScreen = APARTMENT_ID_TBL100;
		Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity);

		TBL200Entity tbl200Entity = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
		
		adviceNotificationLogicImpl.exclusiveCheck(apartmentIdPreviousScreen, newestNotificationNoPreviousScreen);
		assertThat(CommonConstants.CD011_IMPLEMENTED).isEqualTo(adviceNotificationLogicImpl.exclusiveCheck(apartmentIdPreviousScreen, newestNotificationNoPreviousScreen));
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004/区分:N/チェック内容:Test Check Exclusive When Notification No Equal And Advice Done Flag Is Not Implemented
	 * @throws BusinessException 
	 */
	@Test
	public void testCheckExclusiveWhenNotificationNoEqualAndAdviceDoneFlagIsNotImplemented() throws BusinessException {
		TBL100Entity entity = newTBL100Entity(APARTMENT_ID_TBL100);

		String apartmentIdPreviousScreen = APARTMENT_ID_TBL100;
		String newestNotificationNoPreviousScreen = APARTMENT_ID_TBL100;
		Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(entity);

		TBL200Entity tbl200Entity = newTBL200Entity(NOTIFICATION_NO_TBL200);
		tbl200Entity.setAdviceDoneFlag(CommonConstants.ZERO);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(tbl200Entity);
		
		assertThat(CommonConstants.CD011_NOT_IMPLEMENTED).isEqualTo(adviceNotificationLogicImpl.exclusiveCheck(apartmentIdPreviousScreen, newestNotificationNoPreviousScreen));
	}
	/* End test check exclusive */

	/* Start test getAdviceNotification */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-005/区分:N/チェック内容:Test Get Advice Notification Success And Contact Property Else Is Category Owner Etc And User Type Code Is City And Notification Type Is New
	 * @throws BusinessException
	 */
	@Test
	public void testGetAdviceNotificationSuccessAndContactPropertyElseIsCategoryOwnerEtcAndUserTypeCodeIsCityAndNotificationTypeIsNew() throws BusinessException {
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		entityTbl200.setNotificationType(CodeUtil.getValue(CommonConstants.CD003, CommonConstants.CD003_NEW));
		entityTbl200.setContactPropertyElse(CodeUtil.getValue(CommonConstants.CD013, CommonConstants.CD013_CATEGORY_OWNER_ETC));
		prepareSecurityContextHolder(generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO_TBL200)).thenReturn(entityTbl200);

		TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
		Mockito.when(tbm001DAO.getMunicipalMasterInfo(CITY_CODE_TBM001)).thenReturn(entityTbm001);

		List<TBM003Entity> entitiesTbm003 = listTBM003Entity();
		Mockito.when(tbm003DAO.getAdviceTemplateContent()).thenReturn(entitiesTbm003);

		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, CommonConstants.CD027_CITY));
		prepareSecurityContextHolder(entity);
		
		AdviceNotificationVo adviceNotificationVo = adviceNotificationLogicImpl.getAdviceNotification(NOTIFICATION_NO_TBL200);
		List<Code> listCD042 = CodeUtil.getList(CommonConstants.CD042);
		List<AdviceTemplateVo> adviceTemplateVoes = new ArrayList<AdviceTemplateVo>();
		AdviceTemplateVo adviceTemplateVo = null;
		
		assertThat(adviceNotificationVo.getTxtAppendixNo()).isEqualTo("第" + CommonConstants.SPACE_FULL_SIZE + CommonConstants.SPACE_FULL_SIZE + "号様式（第" + CommonConstants.SPACE_FULL_SIZE + CommonConstants.SPACE_FULL_SIZE + "関係）");
		assertThat(adviceNotificationVo.getTxtDocumentNo()).isEqualTo(String.format("%s%s%s第%s号", 
                DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting(), 
                entityTbm001.getDocumentNo(), CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE));
		assertThat(adviceNotificationVo.getLblRecipientNameApartment()).isEqualTo(entityTbl200.getApartmentName());
		assertThat(adviceNotificationVo.getLblRecipientNameUser()).isEqualTo("区分所有者" + CommonConstants.SPACE_FULL_SIZE + entityTbl200.getNotificationPersonName());
		assertThat(adviceNotificationVo.getTxtSender()).isEqualTo(entityTbm001.getCityName() + "長" + CommonConstants.SPACE_FULL_SIZE +  entityTbm001.getChiefName());
		assertThat(adviceNotificationVo.getLblNotificationDate()).isEqualTo(DateTimeUtil.formatDate(entityTbl200.getNotificationDate(), CommonConstants.DATE_FORMATTER));
		assertThat(adviceNotificationVo.getLblEvidenceBar()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD041, entityTbl200.getNotificationType()));
		assertThat(adviceNotificationVo.getLstEvidenceNo()).isEqualTo(getEvidenceNoesByListCD(listCD042));
		assertThat(adviceNotificationVo.getLblAddress()).isEqualTo("東京都" + entityTbm001.getCityName() + entityTbl200.getAddress());
		assertThat(adviceNotificationVo.getLblApartmentName()).isEqualTo(entityTbl200.getApartmentName());

		adviceTemplateVoes.add(new AdviceTemplateVo(CommonConstants.BLANK,CommonConstants.BLANK));
		for (int i = 0; i < entitiesTbm003.size(); i++) {
			adviceTemplateVo = new AdviceTemplateVo();
			adviceTemplateVo.setAdviceTemplateDetail(entitiesTbm003.get(i).getAdviceTemplateDetail());
			adviceTemplateVo.setAdviceTemplateOverview(entitiesTbm003.get(i).getAdviceTemplateOverview());
			adviceTemplateVoes.add(adviceTemplateVo);
		}

		for (int i = 0; i <adviceTemplateVoes.size(); i++) {
			assertThat(adviceNotificationVo.getLstAdvice().get(i).getAdviceTemplateDetail()).isEqualTo(adviceTemplateVoes.get(i).getAdviceTemplateDetail());
			assertThat(adviceNotificationVo.getLstAdvice().get(i).getAdviceTemplateOverview()).isEqualTo(adviceTemplateVoes.get(i).getAdviceTemplateOverview());
		}
		assertThat(adviceNotificationVo.getTxtAdviceDetails()).isEqualTo(CommonConstants.BLANK);
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-006/区分:I/チェック内容:Test Get Advice Notification Success And Contact Property Else Is Other Case And User Type Code Is Other Case And Notification Type Is Update
	 * @throws BusinessException
	 */
	@Test
	public void testGetAdviceNotificationSuccessAndContactPropertyElseIsOtherCaseAndUserTypeCodeIsOtherCaseAndNotificationTypeIsUpdate() throws BusinessException {
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		entityTbl200.setNotificationType(CodeUtil.getValue(CommonConstants.CD003, CommonConstants.CD003_UPDATE));
		entityTbl200.setContactPropertyElse(CodeUtil.getValue(CommonConstants.CD013, CommonConstants.CD013_OTHER));
		prepareSecurityContextHolder(generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(tbl200DAO.getNotificationInfo(NOTIFICATION_NO_TBL200)).thenReturn(entityTbl200);

		TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
		Mockito.when(tbm001DAO.getMunicipalMasterInfo(CITY_CODE_TBM001)).thenReturn(entityTbm001);

		List<TBM003Entity> entitiesTbm003 = listTBM003Entity();
		Mockito.when(tbm003DAO.getAdviceTemplateContent()).thenReturn(entitiesTbm003);

		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
		prepareSecurityContextHolder(entity);
		
		AdviceNotificationVo adviceNotificationVo = adviceNotificationLogicImpl.getAdviceNotification(NOTIFICATION_NO_TBL200);
		List<Code> listCD043 = CodeUtil.getList(CommonConstants.CD043);
		List<AdviceTemplateVo> adviceTemplateVoes = new ArrayList<AdviceTemplateVo>();
		AdviceTemplateVo adviceTemplateVo = null;
		
		assertThat(adviceNotificationVo.getTxtAppendixNo()).isEqualTo("第" + CommonConstants.SPACE_FULL_SIZE + CommonConstants.SPACE_FULL_SIZE + "号様式（第" + CommonConstants.SPACE_FULL_SIZE + CommonConstants.SPACE_FULL_SIZE + "関係）");
		assertThat(adviceNotificationVo.getTxtDocumentNo()).isEqualTo(String.format("%s%s%s第%s号", 
                                                                   DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting(), 
                                                                   entityTbm001.getDocumentNo(), CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE));
		assertThat(adviceNotificationVo.getLblRecipientNameApartment()).isEqualTo(entityTbl200.getApartmentName());
		assertThat(adviceNotificationVo.getLblRecipientNameUser()).isEqualTo("管理組合理事長");
		assertThat(adviceNotificationVo.getTxtSender()).isEqualTo("東京都知事" + CommonConstants.SPACE_FULL_SIZE + entityTbm001.getChiefName());
		assertThat(adviceNotificationVo.getLblNotificationDate()).isEqualTo(DateTimeUtil.formatDate(entityTbl200.getNotificationDate(), CommonConstants.DATE_FORMATTER));
		assertThat(adviceNotificationVo.getLblEvidenceBar()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD041, entityTbl200.getNotificationType()));
		assertThat(adviceNotificationVo.getLstEvidenceNo()).isEqualTo(getEvidenceNoesByListCD(listCD043));
		assertThat(adviceNotificationVo.getLblAddress()).isEqualTo("東京都" + entityTbm001.getCityName() + entityTbl200.getAddress());
		assertThat(adviceNotificationVo.getLblApartmentName()).isEqualTo(entityTbl200.getApartmentName());

		adviceTemplateVoes.add(new AdviceTemplateVo(CommonConstants.BLANK,CommonConstants.BLANK));
		for (int i = 0; i < entitiesTbm003.size(); i++) {
			adviceTemplateVo = new AdviceTemplateVo();
			adviceTemplateVo.setAdviceTemplateDetail(entitiesTbm003.get(i).getAdviceTemplateDetail());
			adviceTemplateVo.setAdviceTemplateOverview(entitiesTbm003.get(i).getAdviceTemplateOverview());
			adviceTemplateVoes.add(adviceTemplateVo);
		}

		for (int i = 0; i <adviceTemplateVoes.size(); i++) {
			assertThat(adviceNotificationVo.getLstAdvice().get(i).getAdviceTemplateDetail()).isEqualTo(adviceTemplateVoes.get(i).getAdviceTemplateDetail());
			assertThat(adviceNotificationVo.getLstAdvice().get(i).getAdviceTemplateOverview()).isEqualTo(adviceTemplateVoes.get(i).getAdviceTemplateOverview());
		}
		assertThat(adviceNotificationVo.getTxtAdviceDetails()).isEqualTo(CommonConstants.BLANK);
	}
	
	private String[] getEvidenceNoesByListCD(List<Code> listCD) {
		
		String[] evidenceNo = new String[listCD.size() + 1];
		evidenceNo[0] = CommonConstants.BLANK;
		int index = 1;
		for (Code code : listCD) {
			evidenceNo[index++] = code.getLabel();
		}
		return evidenceNo;
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-007/区分:E/チェック内容:Test Get Advice Notification When Data Entity Tbl200 Is Null Throw Exception
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testGetAdviceNotificationWhenDataEntityTbl200IsNullThrowException() throws BusinessException {
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(null);

		TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
		Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(entityTbm001);

		List<TBM003Entity> entitiesTbm003 = listTBM003Entity();
		Mockito.when(tbm003DAO.getAdviceTemplateContent()).thenReturn(entitiesTbm003);

		adviceNotificationLogicImpl.getAdviceNotification(NOTIFICATION_NO_TBL200);
	}

	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-008/区分:E/チェック内容:Test Get Advice Notification When Data Entity Tbm001 Is Null Throw Exception
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testGetAdviceNotificationWhenDataEntityTbm001IsNullThrowException() throws BusinessException {
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);

		Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(null);

		List<TBM003Entity> entitiesTbm003 = listTBM003Entity();
		Mockito.when(tbm003DAO.getAdviceTemplateContent()).thenReturn(entitiesTbm003);

		adviceNotificationLogicImpl.getAdviceNotification(NOTIFICATION_NO_TBL200);
	}

	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-009/区分:E/チェック内容:Test Get Advice Notification When Data Entities Tbm003 Is Null Throw Exception
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testGetAdviceNotificationWhenDataEntitiesTbm003IsNullThrowException() throws BusinessException {
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);

		TBM001Entity entityTbm001 = newTBM001Entity(CITY_CODE_TBM001);
		Mockito.when(tbm001DAO.getMunicipalMasterInfo(Mockito.anyString())).thenReturn(entityTbm001);

		Mockito.when(tbm003DAO.getAdviceTemplateContent()).thenReturn(null);

		adviceNotificationLogicImpl.getAdviceNotification(NOTIFICATION_NO_TBL200);
	}
	/* End test getAdviceNotification */
	
	/* Start test getAdviceNotificationTemporaryStorage */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-010/区分:I/チェック内容:Test Get Advice Notification Temporary Storage From Tbl225 By Notification No Success When User Type Code Is Tokyo Staff
	 */
	@Test
	public void testGetAdviceNotificationTemporaryStorageFromTbl225ByNotificationNoSuccessWhenUserTypeCodeIsTokyoStaff() {
		TBL225Entity eTbl225Entity = newTBL225Entity(NOTIFICATION_NO_TBL225, TEMP_KBN_TBL225);
		Mockito.when(tbl225DAO.getAdviceNotificationTemporaryStorage(Mockito.anyString(), Mockito.anyString())).thenReturn(eTbl225Entity);
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		assertThat(eTbl225Entity).isEqualToComparingFieldByField(adviceNotificationLogicImpl.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO_TBL225));
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-011/区分:N/チェック内容:Test Get Advice Notification Temporary Storage From Tbl225 By Notification No Success When User Type Code Is City
	 */
	@Test
	public void testGetAdviceNotificationTemporaryStorageFromTbl225ByNotificationNoSuccessWhenUserTypeCodeIsCity() {
		TBL225Entity eTbl225Entity = newTBL225Entity(NOTIFICATION_NO_TBL225, TEMP_KBN_TBL225);
		Mockito.when(tbl225DAO.getAdviceNotificationTemporaryStorage(Mockito.anyString(), Mockito.anyString())).thenReturn(eTbl225Entity);
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		assertThat(eTbl225Entity).isEqualToComparingFieldByField(adviceNotificationLogicImpl.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO_TBL225));
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-012/区分:N/チェック内容:Test Get Advice Notification Temporary Storage From Tbl225 By Notification No Success When User Type Code Is System Admin
	 */
	@Test
	public void testGetAdviceNotificationTemporaryStorageFromTbl225ByNotificationNoSuccessWhenUserTypeCodeIsSystemAdmin() {
		TBL225Entity eTbl225Entity = newTBL225Entity(NOTIFICATION_NO_TBL225, TEMP_KBN_TBL225);
		Mockito.when(tbl225DAO.getAdviceNotificationTemporaryStorage(Mockito.anyString(), Mockito.anyString())).thenReturn(eTbl225Entity);
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		assertThat(eTbl225Entity).isEqualToComparingFieldByField(adviceNotificationLogicImpl.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO_TBL225));
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-013/区分:N/チェック内容:Test Get Advice Notification Temporary Storage From Tbl225 By Notification No Success When User Type Code Is Maintenancer
	 */
	@Test
	public void testGetAdviceNotificationTemporaryStorageFromTbl225ByNotificationNoSuccessWhenUserTypeCodeIsMaintenancer() {
		TBL225Entity eTbl225Entity = newTBL225Entity(NOTIFICATION_NO_TBL225, TEMP_KBN_TBL225);
		Mockito.when(tbl225DAO.getAdviceNotificationTemporaryStorage(Mockito.anyString(), Mockito.anyString())).thenReturn(eTbl225Entity);
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		assertThat(eTbl225Entity).isEqualToComparingFieldByField(adviceNotificationLogicImpl.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO_TBL225));
	}
	/* End test getAdviceNotificationTemporaryStorage */
	
	/* Start test delete temporarily saved data */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-014/区分:N/チェック内容:Test Delete Temporarily Saved Data Case Register Success
	 * @throws BusinessException
	 */
	@Test
	public void testDeleteTemporarilySavedDataCaseRegisterSuccess() throws BusinessException {
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL220), CommonConstants.COL_ADVICE_NO)).thenReturn("1000000001");
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn("1000000001");
        
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		prepareSecurityContextHolder(tbl120Entity);
		
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
		
		List<String> list = new ArrayList<String>();
		list.add(adviceNotificationForm.getNewestNotificationNo());
		Mockito.when( tbl225DAO.getTempNoByNotificationNo(Mockito.anyString())).thenReturn(list);
		adviceNotificationLogicImpl.saveAdvice(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL225、キー：11000000001");
	}

	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-015/区分:N/チェック内容:Test Delete Temporarily Saved Data Case Save Temporarily Saved Data Success When User Type Code Is Tokyo Staff
	 * @throws BusinessException 
	 */
	@Test
	public void testDeleteTemporarilySavedDataCaseSaveTemporarilySavedDataSuccessWhenUserTypeCodeIsTokyoStaff() throws BusinessException {
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL225、キー：3");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-016/区分:N/チェック内容:Test Delete Temporarily Saved Data Case Save Temporarily Saved Data Success When User Type Code Is City
	 * @throws BusinessException 
	 */
	@Test
	public void testDeleteTemporarilySavedDataCaseSaveTemporarilySavedDataSuccessWhenUserTypeCodeIsCity() throws BusinessException {
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL225、キー：2");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-017/区分:N/チェック内容:Test Delete Temporarily Saved Data Case Save Temporarily Saved Data Success When User Type Code Is System Admin
	 * @throws BusinessException 
	 */
	@Test
	public void testDeleteTemporarilySavedDataCaseSaveTemporarilySavedDataSuccessWhenUserTypeCodeIsSystemAdmin() throws BusinessException {
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL225、キー：4");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-018/区分:N/チェック内容:Test Delete Temporarily Saved Data Case Save Temporarily Saved Data Success When User Type Code Is Maintenancer
	 * @throws BusinessException 
	 */
	@Test
	public void testDeleteTemporarilySavedDataCaseSaveTemporarilySavedDataSuccessWhenUserTypeCodeIsMaintenancer() throws BusinessException {
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		TBL120Entity tbl120Entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
		prepareSecurityContextHolder(tbl120Entity);
		
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("削除（物理）を実行します。テーブル：TBL225、キー：5");
	}
	/* End test delete temporarily saved data */
	
	/* Start test save advice */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-019/区分:N/チェック内容:Test Save Advice Success When Save Data Success And Sent Mail
	 * @throws BusinessException
	 */
	@Test
	public void testSaveAdviceSuccessWhenSaveDataSuccessAndSentMail() throws BusinessException {
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL220), CommonConstants.COL_ADVICE_NO)).thenReturn("1000000001");
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn("1000000001");
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		prepareSecurityContextHolder(entity);
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		//set notification method is sent mail
		adviceNotificationForm.setRdoNotificationMethod(CommonConstants.ONE);
		
		ML050Vo ml050Vo = generateML050Vo();
		Mockito.when(mailLogic.getCommonTemplateMail(ML050Vo.class)).thenReturn(ml050Vo);

		TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
		Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(tbl100Entity);

		adviceNotificationLogicImpl.saveAdvice(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("登録を実行します。テーブル：TBL220、キー：1000000001");
		assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL300、キー：1000000001");
		assertThat(logAppender.getEvents().get(2).getMessage()).isEqualTo("更新を実行します。テーブル：TBL200、キー：notificationNo=11000000001, apartmentId=11000000001");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-020/区分:N/チェック内容:Test Save Advice Success When Save Data Success And Dont Sent Mail
	 * @throws BusinessException
	 */
	@Test
	public void testSaveAdviceSuccessWhenSaveDataSuccessAndDontSentMail() throws BusinessException {
	     Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL220), CommonConstants.COL_ADVICE_NO)).thenReturn("1000000001");
	     Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO)).thenReturn("1000000001");
		TBL200Entity entityTbl200 = newTBL200Entity(NOTIFICATION_NO_TBL200);
		Mockito.when(tbl200DAO.getNotificationInfo(Mockito.anyString())).thenReturn(entityTbl200);
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		prepareSecurityContextHolder(entity);
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		//set notification method is sent mail
		adviceNotificationForm.setRdoNotificationMethod(CommonConstants.ZERO);
		
		ML050Vo ml050Vo = generateML050Vo();
		Mockito.when(mailLogic.getCommonTemplateMail(ML050Vo.class)).thenReturn(ml050Vo);

		TBL100Entity tbl100Entity = newTBL100Entity(APARTMENT_ID_TBL100);
		Mockito.when(tbl100DAO.getMansionInformationById(Mockito.anyString())).thenReturn(tbl100Entity);

		adviceNotificationLogicImpl.saveAdvice(adviceNotificationForm);
        assertThat(logAppender.getEvents().get(0).getMessage()).isEqualTo("登録を実行します。テーブル：TBL220、キー：1000000001");
        assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL300、キー：1000000001");
        assertThat(logAppender.getEvents().get(2).getMessage()).isEqualTo("更新を実行します。テーブル：TBL200、キー：notificationNo=11000000001, apartmentId=11000000001");
	}
	
	/* End test save advice */
	
	/* Start test save temporarily saved data */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-021/区分:N/チェック内容:test Save Temporarily Saved Data Success When Type Code User Is City
	 * @throws BusinessException 
	 */
	@Test
	public void testSaveTemporarilySavedDataSuccessWhenTypeCodeUserIsCity() throws BusinessException {
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()));
		prepareSecurityContextHolder(entity);
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL225、キー：1000000001");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-022/区分:N/チェック内容:test Save Temporarily Saved Data Success When Type Code User Is Tokyo Staff
	 * @throws BusinessException 
	 */
	@Test
	public void testSaveTemporarilySavedDataSuccessWhenTypeCodeUserIsTokyoStaff() throws BusinessException {
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()));
		prepareSecurityContextHolder(entity);
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL225、キー：1000000001");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-023/区分:N/チェック内容:test Save Temporarily Saved Data Success When Type Code User Is System Administrator
	 * @throws BusinessException 
	 */
	@Test
	public void testSaveTemporarilySavedDataSuccessWhenTypeCodeUserIsSystemAdministrator() throws BusinessException {
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()));
		prepareSecurityContextHolder(entity);
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL225、キー：1000000001");
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-024/区分:N/チェック内容:test Save Temporarily Saved Data Success When Type Code User Is Maintenance Company
	 * @throws BusinessException 
	 */
	@Test
	public void testSaveTemporarilySavedDataSuccessWhenTypeCodeUserIsMaintenanceCompany() throws BusinessException {
	    Mockito.when(sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO)).thenReturn("1000000001");
		AdviceNotificationForm adviceNotificationForm = adviceNotificationForm();
		
		TBL120Entity entity = generateEntityTbl120(AccountLockFlag.LOCK.getFlag(),UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		entity.setUserType(CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()));
		prepareSecurityContextHolder(entity);
		
		LogAppender logAppender = LogAppender.initialize(FAILED_HANDLER_PATH);
		adviceNotificationLogicImpl.tmpSave(adviceNotificationForm);
		assertThat(logAppender.getEvents().get(1).getMessage()).isEqualTo("登録を実行します。テーブル：TBL225、キー：1000000001");
	}
	/* End test save temporarily saved data */
	
	/* Start test Get Temporary Advice Notification */
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-025/区分:N/チェック内容:Test Get Temporary Advice Notification Return False
	 */
	@Test
	public void testGetTemporaryAdviceNotificationReturnFalse() {
		assertFalse(adviceNotificationLogicImpl.getTemporaryAdviceNotification(NOTIFICATION_NO_TBL225));
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-026/区分:N/チェック内容:Test Get Temporary Advice Notification Return True
	 */
	@Test
	public void testGetTemporaryAdviceNotificationReturnTrue() {
		List<TBL225Entity> entityList = new ArrayList<>();
 		TBL225Entity eTbl225Entity = newTBL225Entity(NOTIFICATION_NO_TBL225, TEMP_KBN_TBL225);
 		entityList.add(eTbl225Entity);
		Mockito.when(tbl225DAO.findByNotificationNoAndDeleteFlagFalse(Mockito.anyString())).thenReturn(entityList);
		assertTrue(adviceNotificationLogicImpl.getTemporaryAdviceNotification(NOTIFICATION_NO_TBL225));
	}
	/* End test Get Temporary Advice Notification */
}
