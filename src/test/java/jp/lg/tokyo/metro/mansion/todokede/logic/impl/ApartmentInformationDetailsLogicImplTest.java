/*
 * @(#) ApartmentInformationDetailsLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nndo
 * Create Date: Dec 20, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.LogAppender;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.*;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.*;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.*;

import org.junit.Before;
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

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nndo
 */
@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class ApartmentInformationDetailsLogicImplTest {

	private static final String RE_FORMAT_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
	private static final String DECIMAL_FORMAT = "####,####,####";
	private static final String DECIMAL_FORMAT_2 = "##";
	private final String APARTMENT_ID = "10000000001";
	private final String NOTIFICATION_NO = "10";
	private final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/* Create TBL100Entity */
	private final String CITY_CODE_TBL100 = "202020";
	private final String ZIP_CODE_TBL100 = "192-0032";
	private final String NEWEST_NOTIFICATION_NO_TBL100 = "10";
	private final LocalDate NEXT_NOTIFICATION_DATE_TBL100 = LocalDate.now();
	private final LocalDate NEXT_NOTIFICATION_DATE2_TBL100 = DateTimeUtil.getLocalDateFromString("20201129");
	private final String SUPPORT_TBL100 = "1";
	private final Timestamp UPDATE_DATETIME_TBL100 = new Timestamp(new Date().getTime());
	/* Create TBM001Entity */
	private final String CITY_NAME_TBM001 = "abc";
	private final String USER_AUTHORITY_TBM001 = "1";
	/* Create TBL110Entity */
	private final String LOGIN_ID_TBL110 = "G00000011";
	private final String DELETE_FLAG_TBL110 = "0";
	private final LocalDateTime LAST_TIME_LOGIN_TIME_TBL110 = LocalDateTime.now();
	private final int LOGIN_ERROR_COUNT_TBL110 = 0;
	private final LocalDateTime PASSWORD_PERIOD_TBL110 = LocalDateTime.now();
	private final LocalDateTime LOGIN_VALIDITY_TIME_TBL110 = LocalDateTime.now();
	private final LocalDateTime STOP_TIME_TBL110 = LocalDateTime.now();
	private final Timestamp UPDATE_DATETIME_2_TBL110 = new Timestamp(new Date().getTime());
	private final String UPDATE_USER_ID_TBL110 = "G00000011";
	private final String LOGIN_STATUS_FLAG_TBL110 = "1";
	private final String AVAILABILITY_TBL110 = "2";
	private final String VALIDITY_FLAG_TBL110 = "1";
	private final String USER_ID = "10000001";
	private final String LOGIN_ID = "G0000001";
	private final LocalDateTime ACCOUNT_LOCK_TIME = LocalDateTime.now();
	private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
	private final String CITY_CODE = "111111";
	private final String DELETE_FLAG = "0";
	private final LocalDateTime LAST_TIME_LOGIN_TIME = LocalDateTime.now();
	private final int LOGIN_ERROR_COUNT = 0;
	private final String MAIL_ADDRESS = "abc@gmail.com";
	private final String PASSWORD = "password_hash";
	private final LocalDateTime PASSWORD_PERIOD = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
	private final String STOP_REASON = "stop reason";
	private final LocalDateTime STOP_TIME = LocalDateTime.now();
	private final String TEL_NO = "09999999";
	private final LocalDateTime UPDATE_DATETIME = LocalDateTime.now();
	private final String UPDATE_USER_ID = "G0000001";
	private final String USER_NAME = "username";
	private final String USER_NAME_PHONETIC = "username phonetic";
	private final String USER_TYPE = "1";
	/* Create TBL200Entity */
	private final String ACCEPT_STATUS = "2";
	private final String ADDRESS = "東京都武蔵野市吉祥寺東町";
	private final String ADVICE_DONE_FLAG = "1";
	private final String ADVICE_DONE_FLAG_2 = "2";
	private final String APARTMENT_NAME = "tokyo";
	private final String APARTMENT_NAME_PHONETIC = "70000";
	private final Integer APARTMENT_NUMBER = null;
	private final String ARREARAGE_RULE_CODE = "1";
	private final LocalDate BUILT_DATE = LocalDate.now();
	private final String CHANGE_REASON_CODE = "1";
	private final String CHANGE_REASON_CODE_2 = "2";
	private final String COMMUNITY_CODE = "1";
	private final String CONTACT_ADDRESS = "Tokyo ";
	private final String CONTACT_MAIL_ADDRESS = "0";
	private final String CONTACT_NAME = "0";
	private final String CONTACT_NAME_PHONETIC = "1";
	private final String CONTACT_PROPERTY_CODE = "0";
	private final String CONTACT_PROPERTY_ELSE = "CONTACT_PROPERTY_ELSE";
	private final String CONTACT_TEL_NO = "098-478-8011";
	private final String CONTACT_ZIP_CODE = "132-041";
	private final String DESIGN_DOCUMENT_CODE = "1";
	private final String DISASTER_PREVENTION_MANUAL_CODE = "1";
	private final String DISASTER_PREVENTION_REGULAR_CODE = "0";
	private final String DISASTER_PREVENTION_STOCKPILE_CODE = "1";
	private final String EARTHQUAKE_RESISTANCE_CODE = "1";
	private final String ELEVATOR_CODE = "1";
	private final Integer EMPTY_NUMBER = null;
	private final String EMPTY_PERCENT_CODE = "1";
	private final String EQUIPMENT_CHARGE_CODE = "1";
	private final int FLOOR_NUMBER = 123;
	private final int FLOOR_NUMBER_0 = 0;
	private final String GROUP_FORM = "1";
	private final String GROUP_FORM_ELSE = "GROUP_FORM_ELSE";
	private final String GROUP_YESNO_CODE = "1";
	private final String HEAT_SHIELDING_CODE = "1";
	private final int HOUSE_NUMBER = 123;
	private final int HOUSE_NUMBER_0 = 0;
	private final String LAND_RIGHTS_CODE = "1";
	private final String LAND_RIGHTS_ELSE = "LAND_RIGHTS_ELSE";
	private final String LED_CODE = "1";
	private final String LONG_REPAIR_PLAN_CODE = "1";
	private final int LONG_REPAIR_PLAN_PERIOD = 123;
	private final String LONG_REPAIR_PLAN_YEAR = "1";
	private final String LONG_REPAIR_PLAN_YEAR_FROM = "1";
	private final String LONG_REPAIR_PLAN_YEAR_TO = "1";
	private final String LOST_ELSE_REASON_CODE = "1";
	private final String LOST_ELSE_REASON_CODE_9 = "9";
	private final String LOST_ELSE_REASON_ELSE = "LOST_ELSE_REASON_ELSE";
	private final String MANAGEMENT_COST_CODE = "1";
	private final String MANAGEMENT_FORM_CODE = "1";
	private final String MANAGEMENT_FORM_ELSE = "MANAGEMENT_FORM_ELSE";
	private final String MANAGER_ADDRESS = "1";
	private final String MANAGER_CODE = "1";
	private final String MANAGER_NAME = "test manager name";
	private final String MANAGER_NAME_PHONETIC = "test manager name phonetic";
	private final String MANAGER_TEL_NO = "111-222-3333";
	private final String MANAGER_ZIP_CODE = "999-5555";
	private final String MINUTES = "1";
	private final String NEED_SUPPORT_LIST_CODE = "1";
	private final LocalDate NOTIFICATION_DATE = LocalDate.now();
	private final String NOTIFICATION_GROUP_NAME = "0";
	private final String NOTIFICATION_PERSON_NAME = "1";
	private final String NOTIFICATION_TYPE = "2";
	private final String OPEN_ONEYEAR_OVER = "1";
	private final String OPTIONAL = "1";
	private final String RAILING_CODE = "1";
	private final String RECEPTION_NO = "1000000002";
	private final Integer RENTAL_NUMBER = null;
	private final String RENTAL_PERCENT_CODE = "1";
	private final String REPAIR_COST_CODE = "1";
	private final String REPAIR_HISTORY_CODE = "1";
	private final Integer REPAIR_MONTHLY_COST = null;
	private final String REPAIR_NEAREST_YEAR = "2020";
	private final String REPAIR_PLAN_CODE = "1";
	private final String RULE_CHANGE_YEAR = "1";
	private final String RULE_CODE = "1";
	private final String SEGMENT_CODE = "1";
	private final String SEISMIC_DIAGNOSIS_CODE = "1";
	private final String SEISMIC_RETROFIT_CODE = "1";
	private final String SLOPE_CODE = "1";
	private final String USEFOR_CODE = "1";
	private final String USEFOR_ELSE = "USEFOR_ELSE";
	private final String VOLUNTARY_ORGANIZATION_CODE = "1";
	private final String ZIP_CODE = "131-041";
	/* Create TBL210Entity */
	private final String MODIFY_DETAILS = "detail";
	/* Create TBL100Entity */
	private final String LBL_NOTIFICATION_STATUS_1 = Status.UNREPORTED.getStatus();
	private final String LBL_NOTIFICATION_STATUS_2 = Status.REPORTED.getStatus();
	private final String LBL_ACCEPTED_STATUS_1 = "未受理";
	private final String LBL_ACCEPTED_STATUS_2 = "受理済※職権訂正有";
	private final String LBL_ACCEPTED_STATUS_3 = "受理済";
	private final String LBL_SUPPORT_STATUS_1 = "対象";
	private final String LBL_URGE_STATUS_1 = "未通知";
	private final String LBL_URGE_STATUS_2 = "通知済（1回目用）";
	private final String LBL_URGE_STATUS_3 = "通知済（2回目以降用）";
	private final String LBL_ADVICE_STATUS_1 = "";
	private final String LBL_ADVICE_STATUS_2 = "未通知";
	private final String LBL_ADVICE_STATUS_3 = "通知済";
	private final String LBL_SURVEY_STAUTS_1 = "未通知";
	private final String LBL_SURVEY_STAUTS_2 = "通知済";
	private final String STR_9 = "9";
	private final String STR_8 = "8";
	@Mock
	private TBM001DAO tbm001dao;
	@Mock
	private TBL100DAO tbl100dao;
	@Mock
	private TBL110DAO tbl110dao;
	@Mock
	private TBL200DAO tbl200dao;
	@Mock
	private TBL210DAO tbl210dao;
	@Mock
	private TBL230DAO tbl230dao;
	@Mock
	private TBL240DAO tbl240dao;
	@Mock
	private Authentication authentication;
	@InjectMocks
	private ApartmentInformationDetailsLogicImpl apartmentInformationDetailsLogicImpl;

	@Before
	public void init() {
		LogAppender.pauseTillLogbackReady();
	}

	private TBL100Entity generateTBL100Entity(String apartmentId) {
		TBL100Entity entity = new TBL100Entity();
		entity.setApartmentId(apartmentId);
		entity.setApartmentName(APARTMENT_NAME);
		entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		entity.setCityCode(CITY_CODE_TBL100);
		entity.setZipCode(ZIP_CODE_TBL100);
		entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);
		entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE_TBL100);
		entity.setAddress(ADDRESS);
		entity.setSupport(SUPPORT_TBL100);
		entity.setUpdateDatetime(UPDATE_DATETIME_TBL100);
		entity.setFloorNumber(FLOOR_NUMBER);
		entity.setHouseNumber(HOUSE_NUMBER);
		entity.setBuildYear(String.valueOf(LocalDateTime.now().getYear()));
		entity.setBuildMonth(String.format("%02d", LocalDateTime.now().getMonthValue()));
		entity.setBuildDay(String.valueOf(LocalDateTime.now().getDayOfMonth()));
		
		return entity;
	}

	private TBM001Entity generateTBM001Entity(String cityCode) {
		TBM001Entity entity = new TBM001Entity();

		entity.setCityCode(cityCode);
		entity.setCityName(CITY_NAME_TBM001);
		entity.setUserAuthority(USER_AUTHORITY_TBM001);

		return entity;
	}

	private TBL110Entity generateTBL110Entity(String apartmentId) {
		TBL110Entity entity = new TBL110Entity();
		entity.setApartmentId(apartmentId);
		entity.setLoginId(LOGIN_ID_TBL110);
		entity.setLoginStatusFlag(LOGIN_STATUS_FLAG_TBL110);
		entity.setAvailability(AVAILABILITY_TBL110);
		entity.setLoginErrorCount(LOGIN_ERROR_COUNT_TBL110);
		entity.setDeleteFlag(DELETE_FLAG_TBL110);
		entity.setPasswordPeriod(PASSWORD_PERIOD_TBL110);
		entity.setStopTime(STOP_TIME_TBL110);
		entity.setLastTimeLoginTime(LAST_TIME_LOGIN_TIME_TBL110);
		entity.setUpdateUserId(UPDATE_USER_ID_TBL110);
		entity.setUpdateDatetime(UPDATE_DATETIME_2_TBL110);
		entity.setValidityFlag(VALIDITY_FLAG_TBL110);
		entity.setLoginValidityTime(LOGIN_VALIDITY_TIME_TBL110);
		return entity;
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
		entity.setTbm001(generateTBM001Entity(CITY_CODE));
		return entity;
	}

	private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
		UserPrincipal userDetails = UserPrincipal.create(entity,
				entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
		userDetails.getCurrentUserInformation().setUserAuthority("1");
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return userDetails;
	}

	private UserDetails prepareSecurityContextHolder2(TBL120Entity entity) {
		UserPrincipal userDetails = UserPrincipal.create(entity,
				entity.getAccountLockFlag().equals(AccountLockFlag.UNLOCK.getFlag()));
		userDetails.getCurrentUserInformation().setUserAuthority("2");
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return userDetails;
	}

	private TBL200Entity generateTBL200Entity(String apartment, String notificationNo) {

		TBL200Entity entity = new TBL200Entity();

		entity.setAcceptStatus(ACCEPT_STATUS);
		entity.setAddress(ADDRESS);
		entity.setAdviceDoneFlag(ADVICE_DONE_FLAG);
		entity.setApartmentName(APARTMENT_NAME);
		entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		entity.setZipCode(ZIP_CODE);
		entity.setReceptionNo(RECEPTION_NO);
		entity.setNotificationDate(NOTIFICATION_DATE);
		entity.setNotificationGroupName(NOTIFICATION_GROUP_NAME);
		entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME);
		entity.setNotificationType(NOTIFICATION_TYPE);
		entity.setChangeReasonCode(CHANGE_REASON_CODE);
		entity.setLostElseReasonCode(LOST_ELSE_REASON_CODE);
		entity.setLostElseReasonElse(LOST_ELSE_REASON_ELSE);
		entity.setGroupYesnoCode(GROUP_YESNO_CODE);
		entity.setApartmentNumber(APARTMENT_NUMBER);
		entity.setGroupForm(GROUP_FORM);
		entity.setGroupFormElse(GROUP_FORM_ELSE);
		entity.setHouseNumber(HOUSE_NUMBER);
		entity.setFloorNumber(FLOOR_NUMBER);
		entity.setBuiltDate(BUILT_DATE);
		entity.setLandRightsCode(LAND_RIGHTS_CODE);
		entity.setLandRightsElse(LAND_RIGHTS_ELSE);
		entity.setUseforCode(USEFOR_CODE);
		entity.setUseforElse(USEFOR_ELSE);
		entity.setManagementFormCode(MANAGEMENT_FORM_CODE);
		entity.setManagementFormElse(MANAGEMENT_FORM_ELSE);
		entity.setManagerName(MANAGER_NAME);
		entity.setManagerNamePhonetic(MANAGER_NAME_PHONETIC);
		entity.setManagerZipCode(MANAGER_ZIP_CODE);
		entity.setManagerAddress(MANAGER_ADDRESS);
		entity.setManagerTelNo(MANAGER_TEL_NO);
		entity.setGroupYesnoCode(GROUP_YESNO_CODE);
		entity.setManagerCode(MANAGER_CODE);
		entity.setRuleCode(RULE_CODE);
		entity.setRuleChangeYear(RULE_CHANGE_YEAR);
		entity.setOpenOneyearOver(OPEN_ONEYEAR_OVER);
		entity.setMinutes(MINUTES);
		entity.setManagementCostCode(MANAGEMENT_COST_CODE);
		entity.setRepairCostCode(REPAIR_COST_CODE);
		entity.setRepairMonthlyCost(REPAIR_MONTHLY_COST);
		entity.setRepairPlanCode(REPAIR_PLAN_CODE);
		entity.setRepairNearestYear(REPAIR_NEAREST_YEAR);
		entity.setLongRepairPlanCode(LONG_REPAIR_PLAN_CODE);
		entity.setLongRepairPlanYear(LONG_REPAIR_PLAN_YEAR);
		entity.setLongRepairPlanPeriod(LONG_REPAIR_PLAN_PERIOD);
		entity.setLongRepairPlanYearFrom(LONG_REPAIR_PLAN_YEAR_FROM);
		entity.setLongRepairPlanYearTo(LONG_REPAIR_PLAN_YEAR_TO);
		entity.setArrearageRuleCode(ARREARAGE_RULE_CODE);
		entity.setSegmentCode(SEGMENT_CODE);
		entity.setEmptyPercentCode(EMPTY_PERCENT_CODE);
		entity.setEmptyNumber(EMPTY_NUMBER);
		entity.setRentalPercentCode(RENTAL_PERCENT_CODE);
		entity.setRentalNumber(RENTAL_NUMBER);
		entity.setSeismicDiagnosisCode(SEISMIC_DIAGNOSIS_CODE);
		entity.setEarthquakeResistanceCode(EARTHQUAKE_RESISTANCE_CODE);
		entity.setSeismicRetrofitCode(SEISMIC_RETROFIT_CODE);
		entity.setDesignDocumentCode(DESIGN_DOCUMENT_CODE);
		entity.setRepairHistoryCode(REPAIR_HISTORY_CODE);
		entity.setVoluntaryOrganizationCode(VOLUNTARY_ORGANIZATION_CODE);
		entity.setDisasterPreventionManualCode(DISASTER_PREVENTION_MANUAL_CODE);
		entity.setDisasterPreventionStockpileCode(DISASTER_PREVENTION_STOCKPILE_CODE);
		entity.setNeedSupportListCode(NEED_SUPPORT_LIST_CODE);
		entity.setDisasterPreventionRegularCode(DISASTER_PREVENTION_REGULAR_CODE);
		entity.setSlopeCode(SLOPE_CODE);
		entity.setRailingCode(RAILING_CODE);
		entity.setElevatorCode(ELEVATOR_CODE);
		entity.setLedCode(LED_CODE);
		entity.setHeatShieldingCode(HEAT_SHIELDING_CODE);
		entity.setEquipmentChargeCode(EQUIPMENT_CHARGE_CODE);
		entity.setCommunityCode(COMMUNITY_CODE);
		entity.setContactPropertyCode(CONTACT_PROPERTY_CODE);
		entity.setContactPropertyElse(CONTACT_PROPERTY_ELSE);
		entity.setContactZipCode(CONTACT_ZIP_CODE);
		entity.setContactAddress(CONTACT_ADDRESS);
		entity.setContactTelNo(CONTACT_TEL_NO);
		entity.setContactName(CONTACT_NAME);
		entity.setContactNamePhonetic(CONTACT_NAME_PHONETIC);
		entity.setContactMailAddress(CONTACT_MAIL_ADDRESS);
		entity.setOptional(OPTIONAL);
		return entity;

	}

	private TBL200Entity generateTBL200Entity2(String apartment, String notificationNo) {

		TBL200Entity entity = new TBL200Entity();

		entity.setAcceptStatus(ACCEPT_STATUS);
		entity.setAdviceDoneFlag(ADVICE_DONE_FLAG_2);
		entity.setReceptionNo(RECEPTION_NO);
		entity.setNotificationDate(NOTIFICATION_DATE);
		entity.setNotificationGroupName(NOTIFICATION_GROUP_NAME);
		entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME);
		entity.setNotificationType(NOTIFICATION_TYPE);
		entity.setChangeReasonCode(CHANGE_REASON_CODE_2);
		entity.setLostElseReasonCode(LOST_ELSE_REASON_CODE);
		entity.setLostElseReasonElse(LOST_ELSE_REASON_ELSE);
		entity.setApartmentNumber(APARTMENT_NUMBER);
		entity.setHouseNumber(HOUSE_NUMBER_0);
		entity.setFloorNumber(FLOOR_NUMBER_0);
		entity.setManagerName(MANAGER_NAME);
		entity.setManagerNamePhonetic(MANAGER_NAME_PHONETIC);
		entity.setManagerZipCode(MANAGER_ZIP_CODE);
		entity.setManagerAddress(MANAGER_ADDRESS);
		entity.setManagerTelNo(MANAGER_TEL_NO);
		entity.setManagerCode(MANAGER_CODE);
		entity.setRuleCode(RULE_CODE);
		entity.setRuleChangeYear(RULE_CHANGE_YEAR);
		entity.setOpenOneyearOver(OPEN_ONEYEAR_OVER);
		entity.setMinutes(MINUTES);
		entity.setManagementCostCode(MANAGEMENT_COST_CODE);
		entity.setRepairCostCode(REPAIR_COST_CODE);
		entity.setRepairMonthlyCost(REPAIR_MONTHLY_COST);
		entity.setRepairPlanCode(REPAIR_PLAN_CODE);
		entity.setRepairNearestYear(REPAIR_NEAREST_YEAR);
		entity.setLongRepairPlanYear(LONG_REPAIR_PLAN_YEAR);
		entity.setLongRepairPlanPeriod(LONG_REPAIR_PLAN_PERIOD);
		entity.setLongRepairPlanYearFrom(LONG_REPAIR_PLAN_YEAR_FROM);
		entity.setLongRepairPlanYearTo(LONG_REPAIR_PLAN_YEAR_TO);
		entity.setEmptyNumber(EMPTY_NUMBER);
		entity.setRentalNumber(RENTAL_NUMBER);
		entity.setContactAddress(CONTACT_ADDRESS);
		entity.setContactName(CONTACT_NAME);
		entity.setContactNamePhonetic(CONTACT_NAME_PHONETIC);
		entity.setContactMailAddress(CONTACT_MAIL_ADDRESS);
		entity.setOptional(OPTIONAL);
		return entity;

	}

	private TBL200Entity generateTBL200Entity3(String apartment, String notificationNo) {

		TBL200Entity entity = new TBL200Entity();

		entity.setAcceptStatus(ACCEPT_STATUS);
		entity.setAddress(ADDRESS);
		entity.setAdviceDoneFlag(ADVICE_DONE_FLAG);
		entity.setApartmentName(APARTMENT_NAME);
		entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		entity.setZipCode(ZIP_CODE);
		entity.setReceptionNo(RECEPTION_NO);
		entity.setNotificationDate(NOTIFICATION_DATE);
		entity.setNotificationGroupName(NOTIFICATION_GROUP_NAME);
		entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME);
		entity.setNotificationType(NOTIFICATION_TYPE);
		entity.setChangeReasonCode(CHANGE_REASON_CODE_2);
		entity.setLostElseReasonCode(LOST_ELSE_REASON_CODE_9);
		entity.setLostElseReasonElse(LOST_ELSE_REASON_ELSE);
		entity.setGroupYesnoCode(GROUP_YESNO_CODE);
		entity.setApartmentNumber(APARTMENT_NUMBER);
		entity.setGroupForm(STR_8);
		entity.setGroupFormElse(GROUP_FORM_ELSE);
		entity.setHouseNumber(HOUSE_NUMBER);
		entity.setFloorNumber(FLOOR_NUMBER);
		entity.setBuiltDate(BUILT_DATE);
		entity.setLandRightsCode(STR_8);
		entity.setLandRightsElse(LAND_RIGHTS_ELSE);
		entity.setUseforCode(STR_8);
		entity.setUseforElse(USEFOR_ELSE);
		entity.setManagementFormCode(STR_8);
		entity.setManagementFormElse(MANAGEMENT_FORM_ELSE);
		entity.setManagerName(MANAGER_NAME);
		entity.setManagerNamePhonetic(MANAGER_NAME_PHONETIC);
		entity.setManagerAddress(MANAGER_ADDRESS);
		entity.setGroupYesnoCode(GROUP_YESNO_CODE);
		entity.setManagerCode(MANAGER_CODE);
		entity.setRuleCode(RULE_CODE);
		entity.setRuleChangeYear(RULE_CHANGE_YEAR);
		entity.setOpenOneyearOver(OPEN_ONEYEAR_OVER);
		entity.setMinutes(MINUTES);
		entity.setManagementCostCode(MANAGEMENT_COST_CODE);
		entity.setRepairCostCode(REPAIR_COST_CODE);
		entity.setRepairMonthlyCost(REPAIR_MONTHLY_COST);
		entity.setRepairPlanCode(REPAIR_PLAN_CODE);
		entity.setRepairNearestYear(REPAIR_NEAREST_YEAR);
		entity.setLongRepairPlanCode(LONG_REPAIR_PLAN_CODE);
		entity.setLongRepairPlanYear(LONG_REPAIR_PLAN_YEAR);
		entity.setLongRepairPlanPeriod(LONG_REPAIR_PLAN_PERIOD);
		entity.setLongRepairPlanYearFrom(LONG_REPAIR_PLAN_YEAR_FROM);
		entity.setLongRepairPlanYearTo(LONG_REPAIR_PLAN_YEAR_TO);
		entity.setArrearageRuleCode(ARREARAGE_RULE_CODE);
		entity.setSegmentCode(SEGMENT_CODE);
		entity.setEmptyPercentCode(EMPTY_PERCENT_CODE);
		entity.setEmptyNumber(EMPTY_NUMBER);
		entity.setRentalPercentCode(RENTAL_PERCENT_CODE);
		entity.setRentalNumber(RENTAL_NUMBER);
		entity.setSeismicDiagnosisCode(SEISMIC_DIAGNOSIS_CODE);
		entity.setEarthquakeResistanceCode(EARTHQUAKE_RESISTANCE_CODE);
		entity.setSeismicRetrofitCode(SEISMIC_RETROFIT_CODE);
		entity.setDesignDocumentCode(DESIGN_DOCUMENT_CODE);
		entity.setRepairHistoryCode(REPAIR_HISTORY_CODE);
		entity.setVoluntaryOrganizationCode(VOLUNTARY_ORGANIZATION_CODE);
		entity.setDisasterPreventionManualCode(DISASTER_PREVENTION_MANUAL_CODE);
		entity.setDisasterPreventionStockpileCode(DISASTER_PREVENTION_STOCKPILE_CODE);
		entity.setNeedSupportListCode(NEED_SUPPORT_LIST_CODE);
		entity.setDisasterPreventionRegularCode(DISASTER_PREVENTION_REGULAR_CODE);
		entity.setSlopeCode(SLOPE_CODE);
		entity.setRailingCode(RAILING_CODE);
		entity.setElevatorCode(ELEVATOR_CODE);
		entity.setLedCode(LED_CODE);
		entity.setHeatShieldingCode(HEAT_SHIELDING_CODE);
		entity.setEquipmentChargeCode(EQUIPMENT_CHARGE_CODE);
		entity.setCommunityCode(COMMUNITY_CODE);
		entity.setContactPropertyCode(STR_8);
		entity.setContactPropertyElse(CONTACT_PROPERTY_ELSE);
		entity.setContactZipCode(CONTACT_ZIP_CODE);
		entity.setContactAddress(CONTACT_ADDRESS);
		entity.setContactTelNo(CONTACT_TEL_NO);
		entity.setContactName(CONTACT_NAME);
		entity.setContactNamePhonetic(CONTACT_NAME_PHONETIC);
		entity.setContactMailAddress(CONTACT_MAIL_ADDRESS);
		entity.setOptional(OPTIONAL);
		return entity;

	}

	private TBL210Entity generateTBL210Entity(String notificationNo) {
		TBL210Entity entity = new TBL210Entity();
		entity.setNotificationNo(notificationNo);
		entity.setModifyDetails(MODIFY_DETAILS);
		return entity;
	}

	private ApartmentStatusInfoVo generateApartmentStatusInfoVo1() {
		ApartmentStatusInfoVo vo = new ApartmentStatusInfoVo();
		vo.setLblNotificationStatus(LBL_NOTIFICATION_STATUS_1);
		vo.setLblAcceptedStatus(LBL_ACCEPTED_STATUS_1);
		vo.setLblSupportStatus(LBL_SUPPORT_STATUS_1);
		vo.setLblUrgeStatus(LBL_URGE_STATUS_2);
		vo.setLblAdviceStatus(LBL_ADVICE_STATUS_2);
		vo.setLblSurveyStatus(LBL_SURVEY_STAUTS_2);
		return vo;

	}

	private ApartmentStatusInfoVo generateApartmentStatusInfoVo2() {
		ApartmentStatusInfoVo vo = new ApartmentStatusInfoVo();
		vo.setLblNotificationStatus(LBL_NOTIFICATION_STATUS_2);
		vo.setLblAcceptedStatus(LBL_ACCEPTED_STATUS_2);
		vo.setLblSupportStatus(LBL_SUPPORT_STATUS_1);
		vo.setLblUrgeStatus(LBL_URGE_STATUS_1);
		vo.setLblAdviceStatus(LBL_ADVICE_STATUS_2);
		vo.setLblSurveyStatus(LBL_SURVEY_STAUTS_1);
		return vo;
	}

	private ApartmentStatusInfoVo generateApartmentStatusInfoVo3() {
		ApartmentStatusInfoVo vo = new ApartmentStatusInfoVo();
		vo.setLblNotificationStatus(LBL_NOTIFICATION_STATUS_2);
		vo.setLblAcceptedStatus(LBL_ACCEPTED_STATUS_3);
		vo.setLblSupportStatus(LBL_SUPPORT_STATUS_1);
		vo.setLblUrgeStatus(LBL_URGE_STATUS_3);
		vo.setLblAdviceStatus(LBL_ADVICE_STATUS_3);
		vo.setLblSurveyStatus(LBL_SURVEY_STAUTS_1);
		return vo;
	}

	private ApartmentStatusInfoVo generateApartmentStatusInfoVo4() {
		ApartmentStatusInfoVo vo = new ApartmentStatusInfoVo();
		vo.setLblNotificationStatus(LBL_NOTIFICATION_STATUS_2);
		vo.setLblAcceptedStatus(LBL_ACCEPTED_STATUS_3);
		vo.setLblSupportStatus(LBL_SUPPORT_STATUS_1);
		vo.setLblUrgeStatus(LBL_URGE_STATUS_3);
		vo.setLblAdviceStatus(LBL_ADVICE_STATUS_2);
		vo.setLblSurveyStatus(LBL_SURVEY_STAUTS_2);
		return vo;
	}

	/* Create ApartmentUserInfoVo */
	private ApartmentUserInfoVo generateApartmentUserInfoVo() {
		ApartmentUserInfoVo vo = new ApartmentUserInfoVo();
		vo.setUpdateDatetime(new SimpleDateFormat(TIME_FORMAT).format(UPDATE_DATETIME_2_TBL110));
		vo.setLblLoginid(LOGIN_ID_TBL110);
		vo.setLblUserStatus("有効");
		vo.setLblValidity("利用不可");
		vo.setLblLoginStatus("ログイン中");
		vo.setLblFinalLoginDate(DateTimeUtil.formatDateTime(LAST_TIME_LOGIN_TIME_TBL110,
				DateTimeFormatter.ofPattern(RE_FORMAT_YYYYMMDDHHMM)));
		vo.setLblLoginErrorCount(LOGIN_ERROR_COUNT_TBL110 + "回");
		vo.setLblFinalPwdDate(DateTimeUtil.formatDateTime(PASSWORD_PERIOD_TBL110,
				DateTimeFormatter.ofPattern(RE_FORMAT_YYYYMMDDHHMM)));

		return vo;

	}

	/* Create ApartmentBtnStatusVo */
	private ApartmentBtnStatusVo generateApartmentBtnStatusVo1() {
		ApartmentBtnStatusVo vo = new ApartmentBtnStatusVo();
		vo.setApartmentId(APARTMENT_ID);
		vo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);
		vo.setBtnNotificationActive(false);
		vo.setBtnNotificationChangeActive(true);
		vo.setBtnAdviceActive(true);
		vo.setBtnAdviceShow(true);
		vo.setBtnInvestigationShow(true);
		vo.setBtnDemandActive(false);
		vo.setBtnLoginIdActivationActive(true);
		vo.setBtnResumeActive(false);
		vo.setBtnSuspensionActive(true);
		vo.setBtnLoginIdReissueShow(true);
		return vo;

	}

	private ApartmentBtnStatusVo generateApartmentBtnStatusVo2() {
		ApartmentBtnStatusVo vo = new ApartmentBtnStatusVo();
		vo.setApartmentId(APARTMENT_ID);
		vo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);
		vo.setBtnNotificationActive(true);
		vo.setBtnNotificationChangeActive(false);
		vo.setBtnAdviceActive(false);
		vo.setBtnInvestigationShow(false);
		vo.setBtnDemandActive(true);
		vo.setBtnLoginIdActivationActive(false);
		vo.setBtnResumeActive(true);
		vo.setBtnSuspensionActive(false);
		vo.setBtnLoginIdReissueShow(false);
		return vo;

	}

	private ApartmentBtnStatusVo generateApartmentBtnStatusVo3() {
		ApartmentBtnStatusVo vo = new ApartmentBtnStatusVo();
		vo.setApartmentId(APARTMENT_ID);
		vo.setNewestNotificationNo(NEWEST_NOTIFICATION_NO_TBL100);
		vo.setBtnNotificationActive(true);
		vo.setBtnNotificationChangeActive(false);
		vo.setBtnAdviceActive(true);
		vo.setBtnInvestigationShow(false);
		vo.setBtnDemandActive(true);
		vo.setBtnLoginIdActivationActive(false);
		vo.setBtnResumeActive(true);
		vo.setBtnSuspensionActive(false);
		vo.setBtnLoginIdReissueShow(false);
		return vo;

	}

	private List<TBL240Entity> generateListTBL240Entity1() {
		List<TBL240Entity> tbl240Entities = new ArrayList<TBL240Entity>();
		tbl240Entities.add(new TBL240Entity());
		return tbl240Entities;
	}

	private List<TBL240Entity> generateListTBL240Entity2() {
		List<TBL240Entity> tbl240Entities = new ArrayList<TBL240Entity>();
		tbl240Entities.add(new TBL240Entity());
		tbl240Entities.add(new TBL240Entity());
		return tbl240Entities;
	}

	private List<TBL230Entity> generateListListTBL230Entity() {
		List<TBL230Entity> tbl230Entities = new ArrayList<TBL230Entity>();
		tbl230Entities.add(new TBL230Entity());
		return tbl230Entities;
	}

	private BasicReportInfoVo generateBasicReportInfoVo1() {
		BasicReportInfoVo vo = new BasicReportInfoVo();
		vo.setTxtApartmentName(APARTMENT_NAME);
		vo.setTxtApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		vo.setTxtApartmentZipCode1("131");
		vo.setTxtApartmentZipCode2("041");
		vo.setLblApartmentAddress1(CITY_NAME_TBM001);
		vo.setTxtApartmentAddress2(ADDRESS);
		return vo;
	}

	private BasicReportInfoVo generateBasicReportInfoVo2() {
		BasicReportInfoVo vo = new BasicReportInfoVo();
		vo.setTxtApartmentName(APARTMENT_NAME);
		vo.setTxtApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
		vo.setTxtApartmentZipCode1("192");
		vo.setTxtApartmentZipCode2("0032");
		vo.setLblApartmentAddress1(CITY_NAME_TBM001);
		vo.setTxtApartmentAddress2(ADDRESS);
		return vo;
	}

	private void assertgenerateNotificationInfoAreaCommonVo(NotificationInfoAreaCommonVo vo) {
		assertThat(vo.isInActiveItem()).isEqualTo(true);
		assertThat(vo.getLblReceiptNumber()).isEqualTo(RECEPTION_NO);
		assertThat(vo.getCalNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(NOTIFICATION_DATE));
		assertThat(vo.getTxtNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
		assertThat(vo.getTxtNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
		assertThat(vo.getRdoNotificationType()).isEqualTo(NOTIFICATION_TYPE);
		assertThat(vo.getRdoChangeReason()).isEqualTo(CHANGE_REASON_CODE);
		assertThat(vo.getRdoGroupYesno()).isEqualTo(GROUP_YESNO_CODE);
		assertThat(vo.getTxtApartmentNumber()).isEqualTo(CommonUtils.formatNumber(APARTMENT_NUMBER));
		assertThat(vo.getRdoGroupForm()).isEqualTo(GROUP_FORM);
		assertThat(vo.getTxtHouseNumber()).isEqualTo(CommonUtils.formatNumber(HOUSE_NUMBER));
		assertThat(vo.getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(BUILT_DATE));
		assertThat(vo.getTxtFloorNumber()).isEqualTo(CommonUtils.formatNumber(FLOOR_NUMBER));
		assertThat(vo.getRdoLandRights()).isEqualTo(LAND_RIGHTS_CODE);
		assertThat(vo.getRdoUsefor()).isEqualTo(USEFOR_CODE);
		assertThat(vo.getRdoManagementForm()).isEqualTo(MANAGEMENT_FORM_CODE);
		assertThat(vo.getTxtManagerName()).isEqualTo(MANAGER_NAME);
		assertThat(vo.getTxtManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
		assertThat(vo.getTxtManagerZipCode1()).isEqualTo("999");
		assertThat(vo.getTxtManagerZipCode2()).isEqualTo("5555");
		assertThat(vo.getTxtManagerAddress()).isEqualTo(MANAGER_ADDRESS);
		assertThat(vo.getTxtManagerTelno1()).isEqualTo("111");
		assertThat(vo.getTxtManagerTelno2()).isEqualTo("222");
		assertThat(vo.getTxtManagerTelno3()).isEqualTo("3333");
		assertThat(vo.getRdoGroupYesno()).isEqualTo(GROUP_YESNO_CODE);
		assertThat(vo.getRdoManager()).isEqualTo(MANAGER_CODE);
		assertThat(vo.getRdoRule()).isEqualTo(RULE_CODE);
		assertThat(vo.getTxtRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
		assertThat(vo.getRdoOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
		assertThat(vo.getRdoMinutes()).isEqualTo(MINUTES);
		assertThat(vo.getRdoManagementCost()).isEqualTo(MANAGEMENT_COST_CODE);
		assertThat(vo.getRdoRepairCost()).isEqualTo(REPAIR_COST_CODE);
		assertThat(vo.getTxtRepairMonthlycost()).isEqualTo(CommonUtils.formatNumber(REPAIR_MONTHLY_COST));
		assertThat(vo.getRdoRepairPlan()).isEqualTo(REPAIR_PLAN_CODE);
		assertThat(vo.getTxtRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
		assertThat(vo.getRdoLongRepairPlan()).isEqualTo(LONG_REPAIR_PLAN_CODE);
		assertThat(vo.getTxtLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
		assertThat(vo.getTxtLongRepairPlanPeriod()).isEqualTo(String.valueOf(LONG_REPAIR_PLAN_PERIOD));
		assertThat(vo.getTxtLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
		assertThat(vo.getTxtLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
		assertThat(vo.getRdoArrearageRule()).isEqualTo(ARREARAGE_RULE_CODE);
		assertThat(vo.getRdoSegment()).isEqualTo(SEGMENT_CODE);
		assertThat(vo.getRdoEmptyPercent()).isEqualTo(EMPTY_PERCENT_CODE);
		assertThat(vo.getTxtEmptyNumber()).isEqualTo(CommonUtils.formatNumber(EMPTY_NUMBER));
		assertThat(vo.getRdoRentalPercent()).isEqualTo(RENTAL_PERCENT_CODE);
		assertThat(vo.getTxtRentalNumber()).isEqualTo(CommonUtils.formatNumber(RENTAL_NUMBER));
		assertThat(vo.getRdoSeismicDiagnosis()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
		assertThat(vo.getRdoEarthquakeResistance()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
		assertThat(vo.getRdoSeismicRetrofit()).isEqualTo(SEISMIC_RETROFIT_CODE);
		assertThat(vo.getRdoDesignDocument()).isEqualTo(DESIGN_DOCUMENT_CODE);
		assertThat(vo.getRdoRepairHistory()).isEqualTo(REPAIR_HISTORY_CODE);
		assertThat(vo.getRdoVoluntaryOrganization()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
		assertThat(vo.getRdoDisasterPreventionManual()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
		assertThat(vo.getRdoDisasterPreventionStockpile()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
		assertThat(vo.getRdoNeedSupportList()).isEqualTo(NEED_SUPPORT_LIST_CODE);
		assertThat(vo.getRdoDisasterPreventionRegular()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
		assertThat(vo.getRdoSlope()).isEqualTo(SLOPE_CODE);
		assertThat(vo.getRdoRailing()).isEqualTo(RAILING_CODE);
		assertThat(vo.getRdoElevator()).isEqualTo(ELEVATOR_CODE);
		assertThat(vo.getRdoLed()).isEqualTo(LED_CODE);
		assertThat(vo.getRdoHeatShielding()).isEqualTo(HEAT_SHIELDING_CODE);
		assertThat(vo.getRdoEquipmentCharge()).isEqualTo(EQUIPMENT_CHARGE_CODE);
		assertThat(vo.getRdoCommunity()).isEqualTo(COMMUNITY_CODE);
		assertThat(vo.getRdoContactProperty()).isEqualTo(CONTACT_PROPERTY_CODE);
		assertThat(vo.getTxtContactZipCode1()).isEqualTo("132");
		assertThat(vo.getTxtContactZipCode2()).isEqualTo("041");
		assertThat(vo.getTxtContactAddress()).isEqualTo(CONTACT_ADDRESS);
		assertThat(vo.getTxtContactTelno1()).isEqualTo("098");
		assertThat(vo.getTxtContactTelno2()).isEqualTo("478");
		assertThat(vo.getTxtContactTelno3()).isEqualTo("8011");
		assertThat(vo.getTxtContactName()).isEqualTo(CONTACT_NAME);
		assertThat(vo.getTxtContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
		assertThat(vo.getTxtContactMail()).isEqualTo(CONTACT_MAIL_ADDRESS);
		assertThat(vo.getTxtContactMailConfirm()).isEqualTo("");
		assertThat(vo.getTxaOptional()).isEqualTo(OPTIONAL);
	}

	private void assertgenerateNotificationInfoAreaCommonVo1(NotificationInfoAreaCommonVo vo) {
		assertThat(vo.isInActiveItem()).isEqualTo(true);
		assertThat(vo.getLblReceiptNumber()).isEqualTo(RECEPTION_NO);
		assertThat(vo.getCalNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(NOTIFICATION_DATE));
		assertThat(vo.getTxtNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
		assertThat(vo.getTxtNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
		assertThat(vo.getRdoNotificationType()).isEqualTo(NOTIFICATION_TYPE);
		assertThat(vo.getRdoChangeReason()).isEqualTo(CHANGE_REASON_CODE_2);
		assertThat(vo.getRdoGroupYesno()).isEqualTo(STR_9);
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
		assertThat(vo.getTxtApartmentNumber()).isEqualTo(df.format(APARTMENT_NUMBER));
		assertThat(vo.getRdoGroupForm()).isEqualTo(STR_9);
		assertThat(vo.getTxtHouseNumber()).isEqualTo(df.format(HOUSE_NUMBER));
		assertThat(vo.getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(BUILT_DATE));
		assertThat(vo.getTxtFloorNumber()).isEqualTo(df.format(FLOOR_NUMBER));
		assertThat(vo.getRdoLandRights()).isEqualTo(STR_9);
		assertThat(vo.getRdoUsefor()).isEqualTo(STR_9);
		assertThat(vo.getRdoManagementForm()).isEqualTo(STR_9);
		assertThat(vo.getTxtManagerName()).isEqualTo(MANAGER_NAME);
		assertThat(vo.getTxtManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
		assertThat(vo.getTxtManagerZipCode1()).isEqualTo("999");
		assertThat(vo.getTxtManagerZipCode2()).isEqualTo("5555");
		assertThat(vo.getTxtManagerAddress()).isEqualTo(MANAGER_ADDRESS);
		assertThat(vo.getTxtManagerTelno1()).isEqualTo("111");
		assertThat(vo.getTxtManagerTelno2()).isEqualTo("222");
		assertThat(vo.getTxtManagerTelno3()).isEqualTo("3333");
		assertThat(vo.getRdoGroupYesno()).isEqualTo(STR_9);
		assertThat(vo.getRdoManager()).isEqualTo(MANAGER_CODE);
		assertThat(vo.getRdoRule()).isEqualTo(RULE_CODE);
		assertThat(vo.getTxtRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
		assertThat(vo.getRdoOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
		assertThat(vo.getRdoMinutes()).isEqualTo(MINUTES);
		assertThat(vo.getRdoManagementCost()).isEqualTo(MANAGEMENT_COST_CODE);
		assertThat(vo.getRdoRepairCost()).isEqualTo(REPAIR_COST_CODE);
		assertThat(vo.getTxtRepairMonthlycost()).isEqualTo(df.format(REPAIR_MONTHLY_COST));
		assertThat(vo.getRdoRepairPlan()).isEqualTo(REPAIR_PLAN_CODE);
		assertThat(vo.getTxtRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
		assertThat(vo.getRdoLongRepairPlan()).isEqualTo(STR_9);
		assertThat(vo.getTxtLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
		assertThat(vo.getTxtLongRepairPlanPeriod()).isEqualTo(String.valueOf(LONG_REPAIR_PLAN_PERIOD));
		assertThat(vo.getTxtLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
		assertThat(vo.getTxtLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
		assertThat(vo.getRdoArrearageRule()).isEqualTo(STR_9);
		assertThat(vo.getRdoSegment()).isEqualTo(STR_9);
		assertThat(vo.getRdoEmptyPercent()).isEqualTo(STR_9);
		assertThat(vo.getTxtEmptyNumber()).isEqualTo(CommonUtils.formatNumber(EMPTY_NUMBER));
		assertThat(vo.getRdoRentalPercent()).isEqualTo(STR_9);
		assertThat(vo.getTxtRentalNumber()).isEqualTo(CommonUtils.formatNumber(RENTAL_NUMBER));
		assertThat(vo.getRdoSeismicDiagnosis()).isEqualTo(STR_9);
		assertThat(vo.getRdoEarthquakeResistance()).isEqualTo(STR_9);
		assertThat(vo.getRdoSeismicRetrofit()).isEqualTo(STR_9);
		assertThat(vo.getRdoDesignDocument()).isEqualTo(STR_9);
		assertThat(vo.getRdoRepairHistory()).isEqualTo(STR_9);
		assertThat(vo.getRdoVoluntaryOrganization()).isEqualTo(STR_9);
		assertThat(vo.getRdoDisasterPreventionManual()).isEqualTo(STR_9);
		assertThat(vo.getRdoDisasterPreventionStockpile()).isEqualTo(STR_9);
		assertThat(vo.getRdoNeedSupportList()).isEqualTo(STR_9);
		assertThat(vo.getRdoDisasterPreventionRegular()).isEqualTo(STR_9);
		assertThat(vo.getRdoSlope()).isEqualTo(STR_9);
		assertThat(vo.getRdoRailing()).isEqualTo(STR_9);
		assertThat(vo.getRdoElevator()).isEqualTo(STR_9);
		assertThat(vo.getRdoLed()).isEqualTo(STR_9);
		assertThat(vo.getRdoHeatShielding()).isEqualTo(STR_9);
		assertThat(vo.getRdoEquipmentCharge()).isEqualTo(STR_9);
		assertThat(vo.getRdoCommunity()).isEqualTo(STR_9);
		assertThat(vo.getTxtContactAddress()).isEqualTo(CONTACT_ADDRESS);
		assertThat(vo.getTxtContactName()).isEqualTo(CONTACT_NAME);
		assertThat(vo.getTxtContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
		assertThat(vo.getTxtContactMail()).isEqualTo(CONTACT_MAIL_ADDRESS);
		assertThat(vo.getTxtContactMailConfirm()).isEqualTo("");
		assertThat(vo.getTxaOptional()).isEqualTo(OPTIONAL);
	}

	private void assertgenerateNotificationInfoAreaCommonVo2(NotificationInfoAreaCommonVo vo) {
		assertThat(vo.isInActiveItem()).isEqualTo(true);
		assertThat(vo.getLblReceiptNumber()).isEqualTo(RECEPTION_NO);
		assertThat(vo.getCalNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(NOTIFICATION_DATE));
		assertThat(vo.getTxtNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
		assertThat(vo.getTxtNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
		assertThat(vo.getRdoNotificationType()).isEqualTo(NOTIFICATION_TYPE);
		assertThat(vo.getRdoChangeReason()).isEqualTo(CHANGE_REASON_CODE_2);
		assertThat(vo.getRdoGroupYesno()).isEqualTo(GROUP_YESNO_CODE);
		assertThat(vo.getTxtApartmentNumber()).isEqualTo(CommonUtils.formatNumber(APARTMENT_NUMBER));
		assertThat(vo.getRdoGroupForm()).isEqualTo(STR_8);
		assertThat(vo.getTxtGroupFormElse()).isEqualTo(GROUP_FORM_ELSE);
		assertThat(vo.getTxtHouseNumber()).isEqualTo(CommonUtils.formatNumber(HOUSE_NUMBER));
		assertThat(vo.getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(BUILT_DATE));
		assertThat(vo.getTxtFloorNumber()).isEqualTo(CommonUtils.formatNumber(FLOOR_NUMBER));
		assertThat(vo.getRdoLandRights()).isEqualTo(STR_8);
		assertThat(vo.getTxtLandRightsElse()).isEqualTo(LAND_RIGHTS_ELSE);
		assertThat(vo.getRdoUsefor()).isEqualTo(STR_8);
		assertThat(vo.getTxtUseforElse()).isEqualTo(USEFOR_ELSE);
		assertThat(vo.getRdoManagementForm()).isEqualTo(STR_8);
		assertThat(vo.getTxtManagementFormElse()).isEqualTo(MANAGEMENT_FORM_ELSE);
		assertThat(vo.getTxtManagerName()).isEqualTo(MANAGER_NAME);
		assertThat(vo.getTxtManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
		assertThat(vo.getTxtManagerAddress()).isEqualTo(MANAGER_ADDRESS);
		assertThat(vo.getRdoGroupYesno()).isEqualTo(GROUP_YESNO_CODE);
		assertThat(vo.getRdoManager()).isEqualTo(MANAGER_CODE);
		assertThat(vo.getRdoRule()).isEqualTo(RULE_CODE);
		assertThat(vo.getTxtRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
		assertThat(vo.getRdoOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
		assertThat(vo.getRdoMinutes()).isEqualTo(MINUTES);
		assertThat(vo.getRdoManagementCost()).isEqualTo(MANAGEMENT_COST_CODE);
		assertThat(vo.getRdoRepairCost()).isEqualTo(REPAIR_COST_CODE);
		assertThat(vo.getTxtRepairMonthlycost()).isEqualTo(CommonUtils.formatNumber(REPAIR_MONTHLY_COST));
		assertThat(vo.getRdoRepairPlan()).isEqualTo(REPAIR_PLAN_CODE);
		assertThat(vo.getTxtRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
		assertThat(vo.getRdoLongRepairPlan()).isEqualTo(LONG_REPAIR_PLAN_CODE);
		assertThat(vo.getTxtLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
		assertThat(vo.getTxtLongRepairPlanPeriod()).isEqualTo(String.valueOf(LONG_REPAIR_PLAN_PERIOD));
		assertThat(vo.getTxtLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
		assertThat(vo.getTxtLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
		assertThat(vo.getRdoArrearageRule()).isEqualTo(ARREARAGE_RULE_CODE);
		assertThat(vo.getRdoSegment()).isEqualTo(SEGMENT_CODE);
		assertThat(vo.getRdoEmptyPercent()).isEqualTo(EMPTY_PERCENT_CODE);
		assertThat(vo.getTxtEmptyNumber()).isEqualTo(CommonUtils.formatNumber(EMPTY_NUMBER));
		assertThat(vo.getRdoRentalPercent()).isEqualTo(RENTAL_PERCENT_CODE);
		assertThat(vo.getTxtRentalNumber()).isEqualTo(CommonUtils.formatNumber(RENTAL_NUMBER));
		assertThat(vo.getRdoSeismicDiagnosis()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
		assertThat(vo.getRdoEarthquakeResistance()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
		assertThat(vo.getRdoSeismicRetrofit()).isEqualTo(SEISMIC_RETROFIT_CODE);
		assertThat(vo.getRdoDesignDocument()).isEqualTo(DESIGN_DOCUMENT_CODE);
		assertThat(vo.getRdoRepairHistory()).isEqualTo(REPAIR_HISTORY_CODE);
		assertThat(vo.getRdoVoluntaryOrganization()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
		assertThat(vo.getRdoDisasterPreventionManual()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
		assertThat(vo.getRdoDisasterPreventionStockpile()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
		assertThat(vo.getRdoNeedSupportList()).isEqualTo(NEED_SUPPORT_LIST_CODE);
		assertThat(vo.getRdoDisasterPreventionRegular()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
		assertThat(vo.getRdoSlope()).isEqualTo(SLOPE_CODE);
		assertThat(vo.getRdoRailing()).isEqualTo(RAILING_CODE);
		assertThat(vo.getRdoElevator()).isEqualTo(ELEVATOR_CODE);
		assertThat(vo.getRdoLed()).isEqualTo(LED_CODE);
		assertThat(vo.getRdoHeatShielding()).isEqualTo(HEAT_SHIELDING_CODE);
		assertThat(vo.getRdoEquipmentCharge()).isEqualTo(EQUIPMENT_CHARGE_CODE);
		assertThat(vo.getRdoCommunity()).isEqualTo(COMMUNITY_CODE);
		assertThat(vo.getRdoContactProperty()).isEqualTo(STR_8);
		assertThat(vo.getTxtContactPropertyElse()).isEqualTo(CONTACT_PROPERTY_ELSE);
		assertThat(vo.getTxtContactZipCode1()).isEqualTo("132");
		assertThat(vo.getTxtContactZipCode2()).isEqualTo("041");
		assertThat(vo.getTxtContactAddress()).isEqualTo(CONTACT_ADDRESS);
		assertThat(vo.getTxtContactTelno1()).isEqualTo("098");
		assertThat(vo.getTxtContactTelno2()).isEqualTo("478");
		assertThat(vo.getTxtContactTelno3()).isEqualTo("8011");
		assertThat(vo.getTxtContactName()).isEqualTo(CONTACT_NAME);
		assertThat(vo.getTxtContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
		assertThat(vo.getTxtContactMail()).isEqualTo(CONTACT_MAIL_ADDRESS);
		assertThat(vo.getTxtContactMailConfirm()).isEqualTo("");
		assertThat(vo.getTxaOptional()).isEqualTo(OPTIONAL);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-011/区分:E/チェック内容:Test Get Mansion Information By Id When TBL100Entity Null
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test(expected = BusinessException.class)
	public void testGetMansionInformationByIdWhenTBL100EntityNull()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(null);
		apartmentInformationDetailsLogicImpl.getMansionInformation(APARTMENT_ID, NOTIFICATION_NO);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-012/区分:E/チェック内容:Test Get Mansion Information By Id When TBL110Entity Null
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test(expected = BusinessException.class)
	public void testGetApartmentStatusInfoWhenTBL110EntityNull()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(generateTBL100Entity(APARTMENT_ID));
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(null);
		Mockito.when(
				tbl200dao.getNotificationByNotificationNoAndApartmentID(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(null);
		apartmentInformationDetailsLogicImpl.getMansionInformation(APARTMENT_ID,NOTIFICATION_NO);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-013/区分:N/チェック内容:Test Get Mansion Information By Id success When Notification Status is unreported
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetApartmentStatusInfoWhenSucessCase1()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(generateTBL100Entity(APARTMENT_ID));
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(generateTBL110Entity(APARTMENT_ID));
		Mockito.when(
				tbl200dao.getNotificationByNotificationNoAndApartmentID(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(generateTBL200Entity(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100));
		Mockito.when(tbl210dao.getAcceptByNotificationNo(NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(generateTBL210Entity(NEWEST_NOTIFICATION_NO_TBL100));
		Mockito.when(tbl240dao.getSupervisedNoticeByApartmentId(APARTMENT_ID)).thenReturn(generateListTBL240Entity1());
		Mockito.when(tbl230dao.getSurveysByApartmentId(APARTMENT_ID)).thenReturn(generateListListTBL230Entity());
		Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBL100))
				.thenReturn(generateTBM001Entity(CITY_CODE_TBL100));
		UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		ApartmentInformationDetailsVo result = apartmentInformationDetailsLogicImpl.getMansionInformation(APARTMENT_ID,NOTIFICATION_NO);
		assertThat(result.getApartmentStatusInfoVo()).isEqualToComparingFieldByField(generateApartmentStatusInfoVo1());
		assertThat(result.getApartmentBtnStatusVo()).isEqualToComparingFieldByField(generateApartmentBtnStatusVo1());
		assertThat(result.getNotificationRegistrationVo().getBasicReportInfo())
				.isEqualToComparingFieldByField(generateBasicReportInfoVo1());
		assertgenerateNotificationInfoAreaCommonVo(result.getNotificationRegistrationVo().getInfoAreaCommon());
		assertThat(result.getCorrectionDetails()).isEqualTo(MODIFY_DETAILS);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-014/区分:N/チェック内容:Test Get Mansion Information By Id success When Notification Status is reported
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetApartmentStatusInfoWhenSucessCase2()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		TBL100Entity tbl100Entity = generateTBL100Entity(APARTMENT_ID);
		tbl100Entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE2_TBL100);
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(tbl100Entity);
		TBL110Entity tbl110Entity = generateTBL110Entity(APARTMENT_ID);
		tbl110Entity.setAvailability("1");
		tbl110Entity.setValidityFlag("2");
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110Entity);
		Mockito.when(
				tbl200dao.getNotificationByNotificationNoAndApartmentID(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(generateTBL200Entity2(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100));
		Mockito.when(tbl210dao.getAcceptByNotificationNo(NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(generateTBL210Entity(NEWEST_NOTIFICATION_NO_TBL100));
		Mockito.when(tbl240dao.getSupervisedNoticeByApartmentId(APARTMENT_ID))
				.thenReturn(new ArrayList<TBL240Entity>());
		Mockito.when(tbl230dao.getSurveysByApartmentId(APARTMENT_ID)).thenReturn(new ArrayList<TBL230Entity>());
		Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBL100))
				.thenReturn(generateTBM001Entity(CITY_CODE_TBL100));

		TBL120Entity tbl120Entity = generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(String.valueOf(UserTypeCode.CITY.getCode()));
		UserDetails userDetails = prepareSecurityContextHolder2(tbl120Entity);
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		ApartmentInformationDetailsVo result = apartmentInformationDetailsLogicImpl.getMansionInformation(APARTMENT_ID,NOTIFICATION_NO);
		assertThat(result.getApartmentStatusInfoVo()).isEqualToComparingFieldByField(generateApartmentStatusInfoVo2());
		assertThat(result.getApartmentBtnStatusVo()).isEqualToComparingFieldByField(generateApartmentBtnStatusVo2());
		assertThat(result.getNotificationRegistrationVo().getBasicReportInfo())
				.isEqualToComparingFieldByField(generateBasicReportInfoVo2());
		assertgenerateNotificationInfoAreaCommonVo1(result.getNotificationRegistrationVo().getInfoAreaCommon());
		assertThat(result.getCorrectionDetails()).isEqualTo(MODIFY_DETAILS);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-015/区分:I/チェック内容:Test Get Mansion Information By Id success When Notification Status is reported AND TBL200Entity's field has reason else
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetApartmentStatusInfoWhenSucessCase3()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		TBL100Entity tbl100Entity = generateTBL100Entity(APARTMENT_ID);
		tbl100Entity.setNextNotificationDate(NEXT_NOTIFICATION_DATE2_TBL100);
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(tbl100Entity);
		TBL110Entity tbl110Entity = generateTBL110Entity(APARTMENT_ID);
		tbl110Entity.setAvailability("1");
		tbl110Entity.setValidityFlag("2");
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110Entity);
		Mockito.when(
				tbl200dao.getNotificationByNotificationNoAndApartmentID(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100))
				.thenReturn(generateTBL200Entity3(APARTMENT_ID, NEWEST_NOTIFICATION_NO_TBL100));
		Mockito.when(tbl210dao.getAcceptByNotificationNo(NEWEST_NOTIFICATION_NO_TBL100)).thenReturn(null);
		Mockito.when(tbl240dao.getSupervisedNoticeByApartmentId(APARTMENT_ID)).thenReturn(generateListTBL240Entity2());
		Mockito.when(tbl230dao.getSurveysByApartmentId(APARTMENT_ID)).thenReturn(new ArrayList<TBL230Entity>());
		Mockito.when(tbm001dao.getMunicipalMasterInfo(CITY_CODE_TBL100))
				.thenReturn(generateTBM001Entity(CITY_CODE_TBL100));

		TBL120Entity tbl120Entity = generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag());
		tbl120Entity.setUserType(String.valueOf(UserTypeCode.CITY.getCode()));
		UserDetails userDetails = prepareSecurityContextHolder2(tbl120Entity);
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		ApartmentInformationDetailsVo result = apartmentInformationDetailsLogicImpl.getMansionInformation(APARTMENT_ID,NOTIFICATION_NO);
		assertThat(result.getApartmentStatusInfoVo()).isEqualToComparingFieldByField(generateApartmentStatusInfoVo3());
		assertThat(result.getApartmentBtnStatusVo()).isEqualToComparingFieldByField(generateApartmentBtnStatusVo3());
		assertThat(result.getNotificationRegistrationVo().getBasicReportInfo())
				.isEqualToComparingFieldByField(generateBasicReportInfoVo1());
		assertgenerateNotificationInfoAreaCommonVo2(result.getNotificationRegistrationVo().getInfoAreaCommon());
		assertThat(result.getCorrectionDetails()).isEqualTo(null);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-016/区分:N/チェック内容:Test Get Modify Details
	 * success When TBL210Entity Is Null
	 */
	@Test
	public void testGetModifyDetailsWhenTBL100EntityIsNull() {
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(null);
		String result = apartmentInformationDetailsLogicImpl.getMansionInfoUpdateTime(APARTMENT_ID);
		assertThat(result).isEqualTo(null);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-017/区分:I/チェック内容:Test Get Modify Details When Success
	 * 
	 */
	@Test
	public void testGetModifyDetailsWhenSuccess() {
		Mockito.when(tbl100dao.getMansionInformationById(APARTMENT_ID)).thenReturn(generateTBL100Entity(APARTMENT_ID));
		String result = apartmentInformationDetailsLogicImpl.getMansionInfoUpdateTime(APARTMENT_ID);
		assertThat(result).isEqualTo(new SimpleDateFormat(TIME_FORMAT).format(UPDATE_DATETIME_TBL100));
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-018/区分:I/チェック内容:Test Get User Information
	 * When TBL110Entity Null
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test(expected = BusinessException.class)
	public void testGetUserInformationWhenTBL110EntityIsNull()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(null);
		apartmentInformationDetailsLogicImpl.getUserInformation(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-019/区分:E/チェック内容:Test Get User Information
	 * Success When LastTimeLoginTime Null
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetUserInformationSuccessWhenLastTimeLoginTimeIsNull()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		TBL110Entity tbl110Entity = generateTBL110Entity(APARTMENT_ID);
		tbl110Entity.setLastTimeLoginTime(null);
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(tbl110Entity);
		ApartmentUserInfoVo result = apartmentInformationDetailsLogicImpl.getUserInformation(APARTMENT_ID);
		ApartmentUserInfoVo expect = generateApartmentUserInfoVo();
		expect.setLblFinalLoginDate(null);
		assertThat(result).isEqualToComparingFieldByField(expect);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-020/区分:N/チェック内容:Test Get User Information
	 * When Success
	 *
	 * @throws BusinessException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetUserInformationWhenSuccess()
			throws BusinessException, InvocationTargetException, IllegalAccessException {
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(generateTBL110Entity(APARTMENT_ID));
		ApartmentUserInfoVo result = apartmentInformationDetailsLogicImpl.getUserInformation(APARTMENT_ID);
		assertThat(result).isEqualToComparingFieldByField(generateApartmentUserInfoVo());
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-021/区分:N/チェック内容:Test Get Enable LoginID When
	 * TBL110Entity Null
	 *
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testEnableLoginIDWhenTBL110EntityIsNull() throws BusinessException {
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(null);
		apartmentInformationDetailsLogicImpl.enableLoginID(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-022/区分:N/チェック内容:Test Get Enable LoginID When
	 * Success
	 *
	 * @throws BusinessException
	 */
	@Test
	public void testEnableLoginIDWhenSuccess() throws BusinessException {
		UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(generateTBL110Entity(APARTMENT_ID));
		apartmentInformationDetailsLogicImpl.enableLoginID(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-023/区分:E/チェック内容:Test Get Resuming Use When
	 * TBL110Entity Null
	 *
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testResumingUseWhenTBL110EntityIsNull() throws BusinessException {
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(null);
		apartmentInformationDetailsLogicImpl.resumingUse(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-024/区分:N/チェック内容:Test Get Resuming Use When Success
	 *
	 * @throws BusinessException
	 */
	@Test
	public void testResumingUseWhenSuccess() throws BusinessException {
		UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(generateTBL110Entity(APARTMENT_ID));
		apartmentInformationDetailsLogicImpl.resumingUse(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-025/区分:N/チェック内容:Test Get Use Stop When TBL110Entity Null
	 *
	 * @throws BusinessException
	 */
	@Test(expected = BusinessException.class)
	public void testUseStopWhenTBL110EntityIsNull() throws BusinessException {
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(null);
		apartmentInformationDetailsLogicImpl.stopUse(APARTMENT_ID);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-026/区分:N/チェック内容:Test Get Use Stop When Success
	 *
	 * @throws BusinessException
	 */
	@Test
	public void testUseStopWhenSuccess() throws BusinessException {
		UserDetails userDetails = prepareSecurityContextHolder(generateEntity(AccountLockFlag.LOCK.getFlag(),
				UserAvailabilityFlag.POSSIBLE.getFlag(), LoginStatusFlag.NOT_LOGGED_IN.getFlag()));
		Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
		Mockito.when(tbl110dao.getUserByApartmentId(APARTMENT_ID)).thenReturn(generateTBL110Entity(APARTMENT_ID));
		apartmentInformationDetailsLogicImpl.stopUse(APARTMENT_ID);
	}
}
