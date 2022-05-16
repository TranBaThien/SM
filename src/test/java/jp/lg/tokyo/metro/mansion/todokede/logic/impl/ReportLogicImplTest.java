/*
r * @(#) ReportLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
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

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL210DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL220DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL230DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL210Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010TmpVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;

/**
 * @author tqvu1
 *
 */
@RunWith(SpringRunner.class)
@Import(value = {CodeUtilConfig.class})
public class ReportLogicImplTest {

    @InjectMocks
    private ReportLogicImpl reportLogicImpl;

    @Mock
    private TBL200DAO tbl200DAO;

    @Mock
    private TBL210DAO tbl210DAO;

    @Mock
    private TBL220DAO tbl220DAO;

    @Mock
    private TBL230DAO tbl230DAO;

    @Mock
    private TBL240DAO tbl240DAO;

    /* RP010 */
    private static final String NOTIFICATION_NO = "1000000001";
    private static final String RECEPTION_NO = "1000000001";
    private static final String ACCEPT_STATUS = "1";
    private static final String APARTMENT_ID = "1000000001";
    private static final String APARTMENT_NAME = "Tokyo";
    private static final String APARTMENT_NAME_PHONETIC = "Vinh Trung";
    private static final String ZIP_CODE = "131-041";
    private static final String CITY_CODE = "130001";
    private static final String ADDRESS = "東京都武蔵野市吉祥寺東町";
    private static final String NOTIFICATION_TYPE = "1";
    private static final LocalDate NOTIFICATION_DATE = LocalDate.parse("20191129", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private static final String NOTIFICATION_GROUP_NAME = "0";
    private static final String NOTIFICATION_PERSON_NAME = "0";
    private static final String TEMP_KBN = "";
    private static final String ADVICE_DONE_FLAG = "2";
    private static final int CHANGE_COUNT = 0;
    private static final int NOTIFICATION_COUNT = 0;
    private static final String CHANGE_REASON_CODE = "1";
    private static final String LOST_ELSE_REASON_CODE = "9";
    private static final String LOST_ELSE_REASON_ELSE = "reason test";
    private static final String GROUP_YESNO_CODE = "1";
    private static final Integer APARTMENT_NUMBER = null;
    private static final String GROUP_FORM = "1";
    private static final String GROUP_FORM_ELSE = "";
    private static final Integer HOUSE_NUMBER = null;
    private static final Integer FLOOR_NUMBER = null;
    private static final LocalDate BUILT_DATE = LocalDate.parse("20191125", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private static final String LAND_RIGHTS_CODE = "";
    private static final String LAND_RIGHTS_ELSE = "0";
    private static final String USEFOR_CODE = "1";
    private static final String USEFOR_ELSE = "0";
    private static final String MANAGEMENT_FORM_CODE = "1";
    private static final String MANAGEMENT_FORM_ELSE = "0";
    private static final String MANAGER_NAME = "test manager name";
    private static final String MANAGER_NAME_PHONETIC = "test manager name phonetic";
    private static final String MANAGER_ZIP_CODE = "999-5555";
    private static final String MANAGER_ADDRESS = "0";
    private static final String MANAGER_TEL_NO = "111-222-3333";
    private static final String GROUP_CODE = "";
    private static final String MANAGER_CODE = "";
    private static final String RULE_CODE = "1";
    private static final String RULE_CHANGE_YEAR = "2019";
    private static final String OPEN_ONEYEAR_OVER = "1";
    private static final String MINUTES = "2";
    private static final String MANAGEMENT_COST_CODE = "1";
    private static final String REPAIR_COST_CODE = "2";
    private static final Integer REPAIR_MONTHLY_COST = null;
    private static final String REPAIR_PLAN_CODE = "1";
    private static final String REPAIR_NEAREST_YEAR = "2020";
    private static final String LONG_REPAIR_PLAN_CODE = "1";
    private static final String LONG_REPAIR_PLAN_YEAR = "0";
    private static final Integer LONG_REPAIR_PLAN_PERIOD = null;
    private static final String LONG_REPAIR_PLAN_YEAR_FROM = "0";
    private static final String LONG_REPAIR_PLAN_YEAR_TO = "0";
    private static final String ARREARAGE_RULE_CODE = "";
    private static final String SEGMENT_CODE = "1";
    private static final String EMPTY_PERCENT_CODE = "1";
    private static final Integer EMPTY_NUMBER = null;
    private static final String RENTAL_PERCENT_CODE = "";
    private static final Integer RENTAL_NUMBER = null;
    private static final String SEISMIC_DIAGNOSIS_CODE = "1";
    private static final String EARTHQUAKE_RESISTANCE_CODE = "1";
    private static final String SEISMIC_RETROFIT_CODE = "1";
    private static final String DESIGN_DOCUMENT_CODE = "1";
    private static final String REPAIR_HISTORY_CODE = "1";
    private static final String VOLUNTARY_ORGANIZATION_CODE = "1";
    private static final String DISASTER_PREVENTION_MANUAL_CODE = "1";
    private static final String DISASTER_PREVENTION_STOCKPILE_CODE = "1";
    private static final String NEED_SUPPORT_LIST_CODE = "1";
    private static final String DISASTER_PREVENTION_REGULAR_CODE = "";
    private static final String SLOPE_CODE = "1";
    private static final String RAILING_CODE = "1";
    private static final String ELEVATOR_CODE = "1";
    private static final String LED_CODE = "1";
    private static final String HEAT_SHIELDING_CODE = "1";
    private static final String EQUIPMENT_CHARGE_CODE = "1";
    private static final String COMMUNITY_CODE = "1";
    private static final String CONTACT_PROPERTY_CODE = "2";
    private static final String CONTACT_PROPERTY_ELSE = "2";
    private static final String CONTACT_ZIP_CODE = "132-041";
    private static final String CONTACT_ADDRESS = "Tokyo";
    private static final String CONTACT_TEL_NO = "098-098-8011";
    private static final String CONTACT_NAME = "0";
    private static final String CONTACT_NAME_PHONETIC = "0";
    private static final String CONTACT_MAIL_ADDRESS = "adf";
    private static final String OPTIONAL = "コンニチハ";
    private static final String DELETE_FLAG = "0";
    private static final String UPDATE_USER_ID = "1000000018";
    private static final LocalDateTime UPDATE_DATETIME =
            LocalDateTime.parse("2019-12-24 16:05:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private static final LocalDateTime ACCPET_TIME = LocalDateTime.parse("20191010101010", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    private static final String RECEIPT_PERSON_IN_CHARGE = "QQ";
    private static final String RECEIPT_NOTE = "KSKSS";
    private static final String CITY_NAME = "東京都";

    /* RP020 */
    private static final String ACCEPT_NO = "1000000001";
    private static final LocalDateTime ACCEPT_TIME =
            LocalDateTime.parse("20191010101010", DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    private static final String ACCEPT_USER_ID = "1000000020";
    private static final String ACCEPT_USER_NAME = "QQ";
    private static final String APPENDIX_NO = "1000000001";
    private static final String DOCUMENT_NO = "1000000001";
    private static final LocalDate NOTICE_DATE =
            LocalDate.parse("20191212", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private static final String RECIPIENT_NAME_APARTMENT = "";
    private static final String RECIPIENT_NAME_USER = "";
    private static final String SENDER = "Abcd";
    private static final String EVIDENCE_BAR = "1";
    private static final String EVIDENCE_NO = "1";
    private static final String REMARKS = "KSKSS";
    private static final String AUTHORITY_MODIFY_FLAG = "1";
    private static final String MODIFY_DETAILS = "test";
    private static final String NOTIFICATION_METHOD_CODE = "1";
    private static final Timestamp UPDATE_DATETIME_RP020 =
            Timestamp.valueOf(LocalDateTime.parse("2019-12-24 16:05:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    /* RP030 */
    private static final String ADVICE_NO = "1000000001";
    private static final String ADVICE_USER_ID = "G0000010";
    private static final String ADVICE_DETAILS = "sdasdasdasadd sdasdsda sdasdasda sdasda sdasdasda.asdsa";
    private static final String NOTICE_DATE_RP030 = "20191129";
    private static final String NOTIFICATION_DATE_RP030 = "20191129";
    private static final Timestamp UPDATE_DATETIME_RP030 =
            Timestamp.valueOf(LocalDateTime.parse("2019-12-23 18:41:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    /* RP040 */
    private static final String INVESTIGATION_NO = "1000000001";
    private static final String NOTICE_DESTINATION_CODE = "1";
    private static final String INVEST_IMPL_PLAN_TIME = "201912251413";
    private static final int INVEST_IMPL_NUMBER_PEOPLE = 6;
    private static final String NECESSARY_DOCUMENT = "fghfg";
    private static final String CONTACT = "問合せ窓口_部署（区市町村向け） window2@gmail.com TEL_123456780 FAX_987654320";
    private static final String TEXTADDRESS = "貴管理組合が管理する";
    private static final Timestamp UPDATE_DATETIME_RP040 =
            Timestamp.valueOf(LocalDateTime.parse("2019-12-23 18:41:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private static final String NOTICE_DATE_RP040 = "20191129";

    /* RP050 */
    private static final String SUPERVISED_NOTICE_NO = "1000000001";
    private static final String SUPERVISED_NOTICE_TYPE_CODE = "2";
    private static final String MAILING_CODE = "4";
    private static final String PERIOD_EVIDENCE = "5";
    private static final String LAST_NOTICE_DATE = "20191228";
    private static final String LAST_DOCUMENT_NO = "第　号";
    private static final String NOTIFICATION_FORMAT_NAME = "1";
    private static final String NOTIFICATION_TIMELIMIT = "20200930";
    private static final int SUPERVISED_NOTICE_COUNT = 3;
    private static final String TEXTADDRESS1 = "東京都";
    private static final String TEXTADDRESS2 = "文京区";
    private static final String NOTICE_DATE_RP050 = "20191129";
    private static final Timestamp UPDATE_DATETIME_RP050 =
            Timestamp.valueOf(LocalDateTime.parse("2019-12-23 18:41:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    /* RP060 */
    private static final String LAST_NOTICE_DATE_RP060 = "20191228";

    /* TBL120 Entity */
    private static final String USER_ID_TBL120 = "10000001";
    private static final String LOGIN_ID_TBL120 = "G0000001";
    private static final LocalDateTime ACCOUNT_LOCK_TIME_TBL120 = LocalDateTime.now();
    private static final String BIGINING_PASSWORD_CHANGE_FLAG_TBL120 = "0";
    private static final String CITY_CODE_TBL120 = "111111";
    private static final String DELETE_FLAG_TBL120 = "0";
    private static final LocalDateTime LAST_TIME_LOGIN_TIME_TBL120 = LocalDateTime.now();
    private static final int LOGIN_ERROR_COUNT_TBL120 = 0;
    private static final String MAIL_ADDRESS_TBL120 = "abc@gmail.com";
    private static final String PASSWORD_TBL120 = "password_hash";
    private static final LocalDateTime PASSWORD_PERIOD_TBL120 = LocalDateTime.now().plus(365, ChronoUnit.DAYS);
    private static final String STOP_REASON_TBL120 = "stop reason";
    private static final LocalDateTime STOP_TIME_TBL120 = LocalDateTime.now();
    private static final String TEL_NO_TBL120 = "09999999";
    private static final LocalDateTime UPDATE_DATETIME_TBL120 = LocalDateTime.now();
    private static final String UPDATE_USER_ID_TBL120 = "G0000001";
    private static final String USER_NAME_TBL120 = "username";
    private static final String USER_NAME_PHONETIC_TBL120 = "username phonetic";

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-01/区分:N/チェック内容:get Report010 when Document Info Form Not Exist
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenDocumentInfoFormNotExist() throws BusinessException {
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(null);
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        assertThat(result).isEqualToComparingFieldByField(new RP010Vo());
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-02/区分:N/チェック内容:get Report010 when Document Exist And  Change Count Is Equals Zero
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenDocumentInfoFormExistAndChangeCountEqualsZero() throws BusinessException {
        TBL200Entity tbl200Entity = generateTBL200Entity();
        tbl200Entity.setChangeCount(0);
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setTbl200Entity(tbl200Entity);

        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        assertThat(result.getNotificationFormatName()).isEqualTo("別記第１号様式（第４条及び第５条関係）");
        assertThat(result.getReportTitle()).isEqualTo("マンション管理状況届出書");
        assertThat(result.getNotificationTypeLabelNew()).isEqualTo("新規");
        assertThat(result.getNotificationTypeLabelUpd()).isEqualTo("更新");
        assertThat(result.getEvidence()).isEqualTo("第15条第１項、第３項及び第４項並びに第16条第１項");
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-03/区分:N/チェック内容:get Report when Document  Change Count Greater Than Zero
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenDocumentInfoFormExistAndChangeCountGreaterThanZero() throws BusinessException {
        TBL200Entity tbl200Entity = generateTBL200Entity();
        tbl200Entity.setChangeCount(10);
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setTbl200Entity(tbl200Entity);

        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        assertThat(result.getNotificationFormatName()).isEqualTo("第２号様式（第４条及び第５条関係）");
        assertThat(result.getReportTitle()).isEqualTo("マンション管理状況届出事項変更等届出書");
        assertThat(result.getNotificationTypeLabelNew()).isEqualTo("新規届出からの変更");
        assertThat(result.getNotificationTypeLabelUpd()).isEqualTo("更新届出からの変更");
        assertThat(result.getEvidence()).isEqualTo("第15条第５項及び第16条第２項");
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-04/区分:N/チェック内容:get Report when current user type is Mansion
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenCurrentUserTypeIsMansion() throws BusinessException {
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.MANSION.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        assertThat(result.getContractZipCode()).isEqualTo("＊");
        assertThat(result.getContractZipCode()).isEqualTo("＊");
        assertThat(result.getContractTelno1()).isEqualTo("＊");
        assertThat(result.getContractTelno2()).isEqualTo("＊");
        assertThat(result.getContractTelno3()).isEqualTo("＊");
        assertThat(result.getContractAddress()).isEqualTo("＊");
        assertThat(result.getContractNamePhonetic()).isEqualTo("＊");
        assertThat(result.getContractName()).isEqualTo("＊");
        assertThat(result.getContractMailAddress()).isEqualTo("＊");
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-05/区分:N/チェック内容:get Report when current user type is TokyoStaff
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenCurrentUserTypeIsTokyoStaff() throws BusinessException {
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        String[] contractTelNo = CONTACT_TEL_NO.split("-");
        assertThat(result.getContractZipCode()).isEqualTo(CONTACT_ZIP_CODE);
        assertThat(result.getContractTelno1()).isEqualTo(contractTelNo[0]);
        assertThat(result.getContractTelno2()).isEqualTo(contractTelNo[1]);
        assertThat(result.getContractTelno3()).isEqualTo(contractTelNo[2]);
        assertThat(result.getContractAddress()).isEqualTo(CONTACT_ADDRESS);
        assertThat(result.getContractNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
        assertThat(result.getContractName()).isEqualTo(CONTACT_NAME);
        assertThat(result.getContractMailAddress()).isEqualTo(CONTACT_MAIL_ADDRESS);
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-06/区分:N/チェック内容:get Report when accept time is not null
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenAcceptTimeIsNotNull() throws BusinessException {
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setAcceptTime(ACCPET_TIME);
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        String expected = DateUtil.getFormatJapaneseDateForShow(ACCPET_TIME.toLocalDate());
        assertThat(result.getReceiptDate()).isEqualTo(expected);
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-07/区分:N/チェック内容:get Report when accept time is null
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenAcceptTimeIsNull() throws BusinessException {
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setAcceptTime(null);
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        String SPACE_FULLSIZE = "　";
        String expected = StringUtils.repeat(SPACE_FULLSIZE, 4)
                + "年" + StringUtils.repeat(SPACE_FULLSIZE, 2)
                + "月" + StringUtils.repeat(SPACE_FULLSIZE, 2)
                + "日";

        assertThat(result.getReceiptDate()).isEqualTo(expected);
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-08/区分:N/チェック内容:get Report when Lost Else Reason Else is not null
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenLostElseReasonElseIsNotNull() throws BusinessException {
        TBL200Entity tbl200Entity = generateTBL200Entity();
        tbl200Entity.setLostElseReasonElse(LOST_ELSE_REASON_ELSE);
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setTbl200Entity(tbl200Entity);

        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        String expected = "（" + LOST_ELSE_REASON_ELSE + "）";

        assertThat(result.getElseDetail()).isEqualTo(expected);
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-09/区分:N/チェック内容:get Report when Lost Else Reason Else is  null
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenLostElseReasonElseIsNull() throws BusinessException {
        TBL200Entity tbl200Entity = generateTBL200Entity();
        tbl200Entity.setLostElseReasonElse(null);
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        rp010TmpVo.setTbl200Entity(tbl200Entity);

        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);
        String SPACE_FULLSIZE = "　";
        String expected = "（" + StringUtils.repeat(SPACE_FULLSIZE, 6) + "）";

        assertThat(result.getElseDetail()).isEqualTo(expected);
    }

    /**
     * 案件ID:RP010/チェックリストID:UT-RP-001-10/区分:N/チェック内容:get Report when set other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenSetOtherField() throws BusinessException {
        RP010TmpVo rp010TmpVo = generateRP10Tmp();
        Mockito.when(tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO)).thenReturn(rp010TmpVo);
        prepareSecurityContextHolder(generateEntityTbl120(
                AccountLockFlag.LOCK.getFlag(),
                UserAvailabilityFlag.POSSIBLE.getFlag(),
                LoginStatusFlag.NOT_LOGGED_IN.getFlag(),
                String.valueOf(UserTypeCode.TOKYO_STAFF.getCode())));
        RP010Vo result = reportLogicImpl.getReportRP010(NOTIFICATION_NO);

        assertReport010(result);
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-11/区分:N/チェック内容:get Report when Evidence Bar is Equal One
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenEvidenceBarIsEqualOne() throws BusinessException {
        TBL210Entity tbl210Entity = generateTBL210Entity();
        tbl210Entity.setEvidenceBar("1");
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD042, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-12/区分:N/チェック内容:get Report when Evidence Bar is Equal Two
     * @throws BusinessException when has error
     */
    @Test
    public void getReportWhenEvidenceBarIsEqualTwo() throws BusinessException {
        TBL210Entity tbl210Entity = generateTBL210Entity();
        tbl210Entity.setEvidenceBar("2");
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD043, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-13/区分:N/チェック内容:get Report when set other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReport020WhenSetOtherField() throws BusinessException {
        TBL210Entity tbl210Entity = generateTBL210Entity();
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, null);

        assertReport020(result);
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-14/区分:N/チェック内容:get Report when previous screen is GEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport020WhenPreviousScreeenIsGEA0110() throws BusinessException {
        String previousScreen = "GEA0110";
        TBL210Entity tbl210Entity = generateTBL210Entity();
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-15/区分:N/チェック内容:get Report when previous screen is MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport020WhenPreviousScreeenIsMEA0110() throws BusinessException {
        String previousScreen = "MEA0110";
        TBL210Entity tbl210Entity = generateTBL210Entity();
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP020/チェックリストID:UT-RP-001-16/区分:N/チェック内容:get Report when previous screen is not GEA0110 or MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport020WhenPreviousScreeenIsNotGEA0110AndIsNotMEA0110() throws BusinessException {
        String previousScreen = "GGA0110";
        TBL210Entity tbl210Entity = generateTBL210Entity();
        Mockito.when(tbl210DAO.getAcceptByAcceptNo(ACCEPT_NO)).thenReturn(tbl210Entity);
        RP020Vo result = reportLogicImpl.getReportRP020(ACCEPT_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("印");
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-17/区分:N/チェック内容:get Report030 when Evidence Bar is Equal One
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenEvidenceBarIsEqualOne() throws BusinessException {
        TBL220Entity tbl220Entity = generateTBL220Entity();
        tbl220Entity.setEvidenceBar("1");
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD042, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-18/区分:N/チェック内容:get Report030 when Evidence Bar is Equal Two
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenEvidenceBarIsEqualTwo() throws BusinessException {
        TBL220Entity tbl220Entity = generateTBL220Entity();
        tbl220Entity.setEvidenceBar("2");
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD043, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-19/区分:N/チェック内容:get Report030 when set other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenSetOtherField() throws BusinessException {
        TBL220Entity tbl220Entity = generateTBL220Entity();
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, null);

        assertReport030(result);
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-20/区分:N/チェック内容:get Report030 when previous screen is GEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenPreviousScreeenIsGEA0110() throws BusinessException {
        String previousScreen = "GEA0110";
        TBL220Entity tbl220Entity = generateTBL220Entity();
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-21/区分:N/チェック内容:get Report030 when previous screen is MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenPreviousScreeenIsMEA0110() throws BusinessException {
        String previousScreen = "MEA0110";
        TBL220Entity tbl220Entity = generateTBL220Entity();
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-22/区分:N/チェック内容:get Report030 when previous screen is not GEA0110 or MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport030WhenPreviousScreeenIsNotGEA0110AndNotMEA0110() throws BusinessException {
        String previousScreen = "GGA0110";
        TBL220Entity tbl220Entity = generateTBL220Entity();
        Mockito.when(tbl220DAO.getAdvisoryNoticeById(ADVICE_NO)).thenReturn(tbl220Entity);
        RP030Vo result = reportLogicImpl.getReportRP030(ADVICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("印");
    }

    /**
     * 案件ID:RP030/チェックリストID:UT-RP-001-23/区分:N/チェック内容:get Report040 check investImplPlanDatetime is correct
     * @throws BusinessException when has error
     */
    @Test
    public void getReport040CheckInvestImplPlanDatetimeIsCorrect() throws BusinessException {
        TBL230Entity tbl230Entity = generateTBL230Entity();
        Mockito.when(tbl230DAO.getSurveyById(INVESTIGATION_NO)).thenReturn(tbl230Entity);
        RP040Vo result = reportLogicImpl.getReportRP040(INVESTIGATION_NO, null);

        LocalDateTime localDateTime = LocalDateTime.parse(INVEST_IMPL_PLAN_TIME, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String jpFiscalString = DateUtil.getEraNameFiscalYearAsString(localDateTime.toLocalDate());
        StringBuilder expected = new StringBuilder(CommonConstants.BLANK);
        String SPACE_FULL_SIZE = CommonConstants.SPACE_FULL_SIZE;
        expected.append(jpFiscalString);
        expected.append(StringUtils.repeat(SPACE_FULL_SIZE, 2));
        expected.append(localDateTime.getMonthValue());
        expected.append("月");
        expected.append(StringUtils.repeat(SPACE_FULL_SIZE, 2));
        expected.append(localDateTime.getDayOfMonth());
        expected.append("日");
        expected.append(SPACE_FULL_SIZE).append("（").append(SPACE_FULL_SIZE);
        expected.append(DateUtil.getDayOfWeekInJapanese(localDateTime.toLocalDate()).charAt(0));
        expected.append(SPACE_FULL_SIZE).append("）").append(StringUtils.repeat(SPACE_FULL_SIZE, 2));

        // 年号＋年＋”年□□”＋MM＋”月□□”＋DD＋”日”＋”□（□” ＋曜日 ＋”□）□□
        assertThat(result.getInvestImplPlanDatetime()).isEqualTo(expected.toString());

        String[] hh12Format = localDateTime.format(DateTimeFormatter.ofPattern("hh;a")).split(";"); // get HH12|(AM|PM)
        if ("AM".equals(hh12Format[1])) {
            // 午前・午後
            assertThat("true".equals(result.getIsAm())).isTrue();
        } else {
            // 午前・午後
            assertThat("false".equals(result.getIsAm())).isTrue();
        }
        expected.setLength(0);
        expected.append(StringUtils.repeat(SPACE_FULL_SIZE, 2));
        expected.append(hh12Format[0]);
        expected.append("時").append(SPACE_FULL_SIZE).append("から");

        // □□”＋HH１２＋”時□から”
        assertThat(result.getInvestImplPlanDatetimeTwo()).isEqualTo(expected.toString());
    }

    /**
     * 案件ID:RP040/チェックリストID:UT-RP-001-24/区分:N/チェック内容:get Report040 check other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReport040CheckOtherField() throws BusinessException {
        TBL230Entity tbl230Entity = generateTBL230Entity();
        Mockito.when(tbl230DAO.getSurveyById(INVESTIGATION_NO)).thenReturn(tbl230Entity);
        RP040Vo result = reportLogicImpl.getReportRP040(INVESTIGATION_NO, null);
        assertReport040(result);
    }

    /**
     * 案件ID:RP040/チェックリストID:UT-RP-001-25/区分:N/チェック内容:get Report040 when previous screen is GEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport040WhenPreviousScreeenIsGEA0110() throws BusinessException {
        String previousScreen = "GEA0110";
        TBL230Entity tbl230Entity = generateTBL230Entity();
        Mockito.when(tbl230DAO.getSurveyById(INVESTIGATION_NO)).thenReturn(tbl230Entity);
        RP040Vo result = reportLogicImpl.getReportRP040(INVESTIGATION_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("");
    }

    /**
     * 案件ID:RP040/チェックリストID:UT-RP-001-26/区分:N/チェック内容:get Report040 when previous screen is MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport040WhenPreviousScreeenIsMEA0110() throws BusinessException {
        String previousScreen = "MEA0110";
        TBL230Entity tbl230Entity = generateTBL230Entity();
        Mockito.when(tbl230DAO.getSurveyById(INVESTIGATION_NO)).thenReturn(tbl230Entity);
        RP040Vo result = reportLogicImpl.getReportRP040(INVESTIGATION_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("");
    }

    /**
     * 案件ID:RP040/チェックリストID:UT-RP-001-27/区分:N/チェック内容:get Report040 when previous screen is not GEA0110 or MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport040WhenPreviousScreeenIsNotGEA0110AndNotMEA0110() throws BusinessException {
        String previousScreen = "GGA0110";
        TBL230Entity tbl230Entity = generateTBL230Entity();
        Mockito.when(tbl230DAO.getSurveyById(INVESTIGATION_NO)).thenReturn(tbl230Entity);
        RP040Vo result = reportLogicImpl.getReportRP040(INVESTIGATION_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("印");
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-28/区分:N/チェック内容:get Report050 when Evidence Bar is Equal One
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenEvidenceBarIsEqualOne() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        tbl240Entity.setEvidenceBar("1");
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD044, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-29/区分:N/チェック内容:get Report050 when Evidence Bar is Equal Two
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenEvidenceBarIsEqualTwo() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        tbl240Entity.setEvidenceBar("2");
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD043, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-30/区分:N/チェック内容:get Report050 when set other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenSetOtherField() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, null);

        assertReport050(result);
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-31/区分:N/チェック内容:get Report050 when previous screen is GEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenPreviousScreeenIsGEA0110() throws BusinessException {
        String previousScreen = "GEA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-32/区分:N/チェック内容:get Report050 when previous screen is MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenPreviousScreeenIsMEA0110() throws BusinessException {
        String previousScreen = "MEA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("");
    }

    /**
     * 案件ID:RP050/チェックリストID:UT-RP-001-33/区分:N/チェック内容:get Report050 when previous screen is not GEA0110 or MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport050WhenPreviousScreeenIsNotGEA0110AndNotMEA0110() throws BusinessException {
        String previousScreen = "GGA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP050Vo result = reportLogicImpl.getReportRP050(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStampName()).isEqualTo("印");
    }

    /**
     * 案件ID:RP060/チェックリストID:UT-RP-001-34/区分:N/チェック内容:get Report060 when Evidence Bar is Equal One
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenEvidenceBarIsEqualOne() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        tbl240Entity.setEvidenceBar("1");
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD044, EVIDENCE_NO));
    }

    /**
     * 案件ID:RP060/チェックリストID:UT-RP-001-35/区分:N/チェック内容:get Report060 when Evidence Bar is Equal Two
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenEvidenceBarIsEqualTwo() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        tbl240Entity.setEvidenceBar("2");
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, null);

        assertThat(result.getEvidenceNo()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD043, EVIDENCE_NO));
    }

    /**案件
     * ID:RP060/チェックリストID:UT-RP-001-36/区分:N/チェック内容:get Report060 when set other field
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenSetOtherField() throws BusinessException {
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, null);

        assertReport060(result);
    }

    /**
     * 案件ID:RP060/チェックリストID:UT-RP-001-37/区分:N/チェック内容:get Report060 when previous screen is GEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenPreviousScreeenIsGEA0110() throws BusinessException {
        String previousScreen = "GEA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("");
    }

    /**
     * 案件ID:RP060/チェックリストID:UT-RP-001-38/区分:N/チェック内容:get Report060 when previous screen is MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenPreviousScreeenIsMEA0110() throws BusinessException {
        String previousScreen = "MEA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("");
    }

    /**
     * 案件ID:RP060/チェックリストID:UT-RP-001-39/区分:N/チェック内容:get Report060 when previous screen is not GEA0110 or MEA0110
     * @throws BusinessException when has error
     */
    @Test
    public void getReport060WhenPreviousScreeenIsNotGEA0110AndNotMEA0110() throws BusinessException {
        String previousScreen = "GGA0110";
        TBL240Entity tbl240Entity = generateTBL240Entity();
        Mockito.when(tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(SUPERVISED_NOTICE_NO)).thenReturn(tbl240Entity);
        RP060Vo result = reportLogicImpl.getReportRP060(SUPERVISED_NOTICE_NO, previousScreen);

        assertThat(result.getStamp()).isEqualTo("印");
    }

    private void assertReport060(RP060Vo rp060Vo) throws BusinessException {
        assertThat(rp060Vo.getTxtAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(rp060Vo.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(rp060Vo.getNoticeDate()).isEqualTo(convertDateToJpString(NOTICE_DATE_RP050));
        assertThat(rp060Vo.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(rp060Vo.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(rp060Vo.getSender()).isEqualTo(SENDER);
        assertThat(rp060Vo.getTextMailing1()).isEqualTo(TEXTADDRESS1);
        assertThat(rp060Vo.getPeriodEvidence()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD045, PERIOD_EVIDENCE));
        assertThat(rp060Vo.getTextMailing2()).isEqualTo(TEXTADDRESS2);
        assertThat(rp060Vo.getAddress()).isEqualTo(ADDRESS);
        assertThat(rp060Vo.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(rp060Vo.getNotificationFormatName()).isEqualTo(NOTIFICATION_FORMAT_NAME);
        assertThat(rp060Vo.getNotificationTimelimit()).isEqualTo(convertDateToJpString(NOTIFICATION_TIMELIMIT));
        assertThat(rp060Vo.getContact()).isEqualTo(CONTACT);
        assertThat(rp060Vo.getLastNoticeDate()).isEqualTo(convertDateToJpString(LAST_NOTICE_DATE_RP060));
    }

    private void assertReport050(RP050Vo rp050Vo) throws BusinessException {
        assertThat(rp050Vo.getTxtAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(rp050Vo.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(rp050Vo.getNoticeDate()).isEqualTo(convertDateToJpString(NOTICE_DATE_RP050));
        assertThat(rp050Vo.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(rp050Vo.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(rp050Vo.getSender()).isEqualTo(SENDER);
        assertThat(rp050Vo.getTextMailing1()).isEqualTo(TEXTADDRESS1);
        assertThat(rp050Vo.getPeriodEvidence()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD045, PERIOD_EVIDENCE));
        assertThat(rp050Vo.getTextMailing2()).isEqualTo(TEXTADDRESS2);
        assertThat(rp050Vo.getAddress()).isEqualTo(ADDRESS);
        assertThat(rp050Vo.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(rp050Vo.getNotificationFormatName()).isEqualTo(NOTIFICATION_FORMAT_NAME);
        assertThat(rp050Vo.getNotificationTimelimit()).isEqualTo(convertDateToJpString(NOTIFICATION_TIMELIMIT));
        assertThat(rp050Vo.getContact()).isEqualTo(CONTACT);
    }

    private void assertReport040(RP040Vo rp040Vo) throws BusinessException {
        assertThat(rp040Vo.getAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(rp040Vo.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(rp040Vo.getNoticeDate()).isEqualTo(convertDateToJpString(NOTICE_DATE_RP040));
        assertThat(rp040Vo.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(rp040Vo.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(rp040Vo.getSender()).isEqualTo(SENDER);
        assertThat(rp040Vo.getTextMailing1()).isEqualTo(TEXTADDRESS);
        assertThat(rp040Vo.getAddress()).isEqualTo(ADDRESS);
        assertThat(rp040Vo.getInvestImplNumberPeople()).isEqualTo(INVEST_IMPL_NUMBER_PEOPLE + "");
        assertThat(rp040Vo.getNecessaryDocument()).isEqualTo(NECESSARY_DOCUMENT);
        assertThat(rp040Vo.getContact()).isEqualTo(CONTACT);
    }

    private void assertReport030(RP030Vo rp030Vo) throws BusinessException {
        rp030Vo.setAppendixNo(APPENDIX_NO);
        rp030Vo.setDocumentNo(DOCUMENT_NO);
        rp030Vo.setNoticeDate(convertDateToJpString(NOTICE_DATE_RP030));
        rp030Vo.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        rp030Vo.setRecipientNameUser(RECIPIENT_NAME_USER);
        rp030Vo.setSender(SENDER);
        rp030Vo.setNotificationDate(convertDateToJpString(NOTIFICATION_DATE_RP030));
        rp030Vo.setStampName("印");
        rp030Vo.setAddress(ADDRESS);
        rp030Vo.setApartmentName(APARTMENT_NAME);
        rp030Vo.setAdviceDetails(ADVICE_DETAILS);
        rp030Vo.setEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, EVIDENCE_BAR));
    }

    private void assertReport020(RP020Vo rp020Vo) {
        assertThat(rp020Vo.getNoticeDate()).isEqualTo(DateUtil.getFormatJapaneseDateForShow(NOTICE_DATE));
        assertThat(rp020Vo.getAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(rp020Vo.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(rp020Vo.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(rp020Vo.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(rp020Vo.getSender()).isEqualTo(SENDER);
        assertThat(rp020Vo.getStampName()).isEqualTo("印");
        assertThat(rp020Vo.getNotificationDate()).isEqualTo(DateUtil.getFormatJapaneseDateForShow(NOTIFICATION_DATE));
        assertThat(rp020Vo.getEvidenceBar()).isEqualTo(CodeUtil.getLabel(CommonConstants.CD041, EVIDENCE_BAR));
    }

    private void assertReport010(RP010Vo rp010Vo) {
        assertThat(rp010Vo.getNotificationDate()).isEqualTo(DateUtil.getFormatJapaneseDateForShow(NOTIFICATION_DATE));
        assertThat(rp010Vo.getReceiptNumber()).isEqualTo(RECEPTION_NO);
        assertThat(rp010Vo.getSender()).isEqualTo(APARTMENT_NAME + "管理組合");
        assertThat(rp010Vo.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
        assertThat(rp010Vo.getApartmentZipCode()).isEqualTo(ZIP_CODE);
        assertThat(rp010Vo.getApartmentAddress()).isEqualTo(CITY_NAME + ADDRESS);
        assertThat(rp010Vo.getApartmentNameKana()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(rp010Vo.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(rp010Vo.getGroupYesChk()).isEqualTo(GROUP_YESNO_CODE);
        assertThat(rp010Vo.getNumberOfDoor()).isEqualTo(CommonUtils.formatPrice(ObjectUtils.toString(HOUSE_NUMBER)));
        assertThat(rp010Vo.getNumberOfFloor()).isEqualTo(ObjectUtils.toString(FLOOR_NUMBER));
        assertThat(rp010Vo.getConstructionDate()).isEqualTo(
                BUILT_DATE.getYear() + "年" + CommonUtils.leftPadZero(BUILT_DATE.getMonthValue(), 2) + "月" + CommonUtils.leftPadZero(BUILT_DATE.getDayOfMonth(), 2) + "日");
        assertThat(rp010Vo.getOwnerShipChk()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(rp010Vo.getLeaseholdRightsChk()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(rp010Vo.getFixedTermLeaseholdRightsChk()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(rp010Vo.getLandRightsElseChk()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(rp010Vo.getLandRightsElse()).isEqualTo(LAND_RIGHTS_ELSE);
        assertThat(rp010Vo.getUseforNotChk()).isEqualTo(USEFOR_CODE);
        assertThat(rp010Vo.getUseforStoreChk()).isEqualTo(USEFOR_CODE);
        assertThat(rp010Vo.getUseforOfficeChk()).isEqualTo(USEFOR_CODE);
        assertThat(rp010Vo.getUseforElseChk()).isEqualTo(USEFOR_CODE);
        assertThat(rp010Vo.getUseforElse()).isEqualTo(USEFOR_ELSE);
        assertThat(rp010Vo.getAllDelegatesChk()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(rp010Vo.getParkDelegatesChk()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(rp010Vo.getSelfManagementChk()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(rp010Vo.getManagementFormTypeElseChk()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(rp010Vo.getManagementFormTypeElse()).isEqualTo(MANAGEMENT_FORM_ELSE);
        assertThat(rp010Vo.getManagerNameKana()).isEqualTo(MANAGER_NAME_PHONETIC);
        assertThat(rp010Vo.getManagerName()).isEqualTo(MANAGER_NAME);
        assertThat(rp010Vo.getManagerZipCode()).isEqualTo(MANAGER_ZIP_CODE);
        String[] managerTelno = MANAGER_TEL_NO.split("-");
        assertThat(rp010Vo.getManagerTelno1()).isEqualTo(managerTelno[CommonConstants.NUM_0]);
        assertThat(rp010Vo.getManagerTelno2()).isEqualTo(managerTelno[CommonConstants.NUM_1]);
        assertThat(rp010Vo.getManagerTelno3()).isEqualTo(managerTelno[CommonConstants.NUM_2]);
        assertThat(rp010Vo.getManagerAddress()).isEqualTo(MANAGER_ADDRESS);
        assertThat(rp010Vo.getGroupHaveChk()).isEqualTo(GROUP_CODE);
        assertThat(rp010Vo.getGroupNotChk()).isEqualTo(GROUP_CODE);
        assertThat(rp010Vo.getManagerHaveChk()).isEqualTo(MANAGER_CODE);
        assertThat(rp010Vo.getManagerNotChk()).isEqualTo(MANAGER_CODE);
        assertThat(rp010Vo.getManagementRuleHaveChk()).isEqualTo(RULE_CODE);
        assertThat(rp010Vo.getManagementRuleNotChk()).isEqualTo(RULE_CODE);
        assertThat(rp010Vo.getRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
        assertThat(rp010Vo.getOpenOneyearOverHaveChk()).isEqualTo(OPEN_ONEYEAR_OVER);
        assertThat(rp010Vo.getOpenOneyearOverNotChk()).isEqualTo(OPEN_ONEYEAR_OVER);
        assertThat(rp010Vo.getMinutesHaveChk()).isEqualTo(MINUTES);
        assertThat(rp010Vo.getMinutesNotChk()).isEqualTo(MINUTES);
        assertThat(rp010Vo.getManagementCostHaveChk()).isEqualTo(MANAGEMENT_COST_CODE);
        assertThat(rp010Vo.getManagementCostNotChk()).isEqualTo(MANAGEMENT_COST_CODE);
        assertThat(rp010Vo.getRepairCostHaveChk()).isEqualTo(REPAIR_COST_CODE);
        assertThat(rp010Vo.getRepairCostNotChk()).isEqualTo(REPAIR_COST_CODE);
        assertThat(rp010Vo.getRepairMonthlyCost()).isEqualTo(CommonUtils.formatPrice(ObjectUtils.toString(REPAIR_MONTHLY_COST)));
        assertThat(rp010Vo.getRepairPlanImpHaveChk()).isEqualTo(REPAIR_PLAN_CODE);
        assertThat(rp010Vo.getRepairPlanImpNotChk()).isEqualTo(REPAIR_PLAN_CODE);
        assertThat(rp010Vo.getRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
        assertThat(rp010Vo.getLongtermRepairPlanHaveChk()).isEqualTo(LONG_REPAIR_PLAN_CODE);
        assertThat(rp010Vo.getLongtermRepairPlanNotChk()).isEqualTo(LONG_REPAIR_PLAN_CODE);
        assertThat(rp010Vo.getLongtermRepairLatestYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
        assertThat(rp010Vo.getLongtermRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
        assertThat(rp010Vo.getLongtermRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
        assertThat(rp010Vo.getArrearageRuleHaveChk()).isEqualTo(ARREARAGE_RULE_CODE);
        assertThat(rp010Vo.getArrearageRuleNotChk()).isEqualTo(ARREARAGE_RULE_CODE);
        assertThat(rp010Vo.getSegmentHaveChk()).isEqualTo(SEGMENT_CODE);
        assertThat(rp010Vo.getSegmentNotChk()).isEqualTo(SEGMENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitZeroPercentChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitFivePercentChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitTenPercentChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitFifteenPercentChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitTwentyPercentChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitTwentyPercentOverChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitUnknowChk()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(rp010Vo.getEmptyDwellingUnitNumber()).isEqualTo(CommonUtils.formatPrice(ObjectUtils.toString(EMPTY_NUMBER)));
        assertThat(rp010Vo.getRentalDwellingUnitZeroPercentChk()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitFivePercentChk()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitTenPercentChk()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitTwentyPercentChk()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitTwentyPercentOverChk()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitPercent()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(rp010Vo.getRentalDwellingUnitNumber()).isEqualTo(CommonUtils.formatPrice(ObjectUtils.toString(RENTAL_NUMBER)));
        assertThat(rp010Vo.getSeismicDiagnosisDoneChk()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
        assertThat(rp010Vo.getEarthquakeResistanceHaveChk()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
        assertThat(rp010Vo.getEarthquakeResistanceNotChk()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
        assertThat(rp010Vo.getSeismicDiagnosisNotChk()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
        assertThat(rp010Vo.getSeismicRetrofitHaveChk()).isEqualTo(SEISMIC_RETROFIT_CODE);
        assertThat(rp010Vo.getSeismicRetrofitNotChk()).isEqualTo(SEISMIC_RETROFIT_CODE);
        assertThat(rp010Vo.getDesignDocumentHaveChk()).isEqualTo(DESIGN_DOCUMENT_CODE);
        assertThat(rp010Vo.getDesignDocumentNotChk()).isEqualTo(DESIGN_DOCUMENT_CODE);
        assertThat(rp010Vo.getRepairHistoryHaveChk()).isEqualTo(REPAIR_HISTORY_CODE);
        assertThat(rp010Vo.getRepairHistoryNotChk()).isEqualTo(REPAIR_HISTORY_CODE);
        assertThat(rp010Vo.getVoluntaryOrganizationHaveChk()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
        assertThat(rp010Vo.getVoluntaryOrganizationNotChk()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
        assertThat(rp010Vo.getDisasterPreventionManualHaveChk()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
        assertThat(rp010Vo.getDisasterPreventionManualNotChk()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
        assertThat(rp010Vo.getDisasterPreventionStockpileHaveChk()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
        assertThat(rp010Vo.getDisasterPreventionStockpileNotChk()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
        assertThat(rp010Vo.getNeedSupportListHaveChk()).isEqualTo(NEED_SUPPORT_LIST_CODE);
        assertThat(rp010Vo.getNeedSupportListNotChk()).isEqualTo(NEED_SUPPORT_LIST_CODE);
        assertThat(rp010Vo.getDisasterPreventionRegularHaveChk()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
        assertThat(rp010Vo.getDisasterPreventionRegularNotChk()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
        assertThat(rp010Vo.getSlopeHaveChk()).isEqualTo(SLOPE_CODE);
        assertThat(rp010Vo.getSlopeNotChk()).isEqualTo(SLOPE_CODE);
        assertThat(rp010Vo.getRailingHaveChk()).isEqualTo(RAILING_CODE);
        assertThat(rp010Vo.getRailingNotChk()).isEqualTo(RAILING_CODE);
        assertThat(rp010Vo.getElevatorHaveChk()).isEqualTo(ELEVATOR_CODE);
        assertThat(rp010Vo.getElevatorNotChk()).isEqualTo(ELEVATOR_CODE);
        assertThat(rp010Vo.getLedHaveChk()).isEqualTo(LED_CODE);
        assertThat(rp010Vo.getLedNotChk()).isEqualTo(LED_CODE);
        assertThat(rp010Vo.getHeatShieldingHaveChk()).isEqualTo(HEAT_SHIELDING_CODE);
        assertThat(rp010Vo.getHeatShieldingNotChk()).isEqualTo(HEAT_SHIELDING_CODE);
        assertThat(rp010Vo.getEquipmentChargeHaveChk()).isEqualTo(EQUIPMENT_CHARGE_CODE);
        assertThat(rp010Vo.getEquipmentChargeNotChk()).isEqualTo(EQUIPMENT_CHARGE_CODE);
        assertThat(rp010Vo.getCommunityHaveChk()).isEqualTo(COMMUNITY_CODE);
        assertThat(rp010Vo.getCommunityNotChk()).isEqualTo(COMMUNITY_CODE);
        assertThat(rp010Vo.getContactPropertyChairmanChk()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(rp010Vo.getContactPropertyDistinguishingChk()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(rp010Vo.getContactPropertyApartmentChk()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(rp010Vo.getContactPropertyElseChk()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(rp010Vo.getContactPropertyElse()).isEqualTo(CONTACT_PROPERTY_ELSE);
        assertThat(rp010Vo.getReceiptPersonInCharge()).isEqualTo(RECEIPT_PERSON_IN_CHARGE);
        assertThat(rp010Vo.getReceiptNote()).isEqualTo(RECEIPT_NOTE);
        assertThat(rp010Vo.getChangeReason()).isEqualTo("変更理由");
        assertThat(rp010Vo.getLostElseReasonChk()).isEqualTo(CHANGE_REASON_CODE);
        assertThat(rp010Vo.getChangeLbl()).isEqualTo("変更");
        assertThat(rp010Vo.getLostElseReasonChk()).isEqualTo(CHANGE_REASON_CODE);
        assertThat(rp010Vo.getLostElseReasonLbl()).isEqualTo("建物の滅失その他の事由");
        assertThat(rp010Vo.getExceptChk()).isEqualTo(LOST_ELSE_REASON_CODE);
        assertThat(rp010Vo.getExceptLbl()).isEqualTo("建物を除却したため");
        assertThat(rp010Vo.getLostChk()).isEqualTo(LOST_ELSE_REASON_CODE);
        assertThat(rp010Vo.getLostLbl()).isEqualTo("区分所有建物ではなくなったため");
        assertThat(rp010Vo.getElseChk()).isEqualTo(LOST_ELSE_REASON_CODE);
        assertThat(rp010Vo.getElseLbl()).isEqualTo("その他");
    }

    private RP010TmpVo generateRP10Tmp() {
        RP010TmpVo rp010TmpVo = new RP010TmpVo();
        rp010TmpVo.setTbl200Entity(generateTBL200Entity());
        rp010TmpVo.setAcceptTime(ACCPET_TIME);
        rp010TmpVo.setReceiptPersonInCharge(RECEIPT_PERSON_IN_CHARGE);
        rp010TmpVo.setReceiptNote(RECEIPT_NOTE);
        rp010TmpVo.setCityName(CITY_NAME);
        return rp010TmpVo;
    }

    private TBL200Entity generateTBL200Entity() {
        TBL200EntityPK pk = new TBL200EntityPK();
        pk.setNotificationNo(NOTIFICATION_NO);
        pk.setApartmentId(APARTMENT_ID);
        TBL200Entity entity = new TBL200Entity();
        entity.setId(pk);
        entity.setReceptionNo(RECEPTION_NO);
        entity.setAcceptStatus(ACCEPT_STATUS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setZipCode(ZIP_CODE);
        entity.setCityCode(CITY_CODE);
        entity.setAddress(ADDRESS);
        entity.setNotificationType(NOTIFICATION_TYPE);
        entity.setNotificationDate(NOTIFICATION_DATE);
        entity.setNotificationGroupName(NOTIFICATION_GROUP_NAME);
        entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME);
        entity.setTempKbn(TEMP_KBN);
        entity.setAdviceDoneFlag(ADVICE_DONE_FLAG);
        entity.setChangeCount(CHANGE_COUNT);
        entity.setNotificationCount(NOTIFICATION_COUNT);
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
        entity.setGroupCode(GROUP_CODE);
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
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        return entity;
    }

    private TBL210Entity generateTBL210Entity() {
        TBL210Entity entity = new TBL210Entity();
        entity.setAcceptNo(ACCEPT_NO);
        entity.setNotificationNo(NOTIFICATION_NO);
        entity.setAcceptTime(ACCEPT_TIME);
        entity.setAcceptUserId(ACCEPT_USER_ID);
        entity.setAcceptUserName(ACCEPT_USER_NAME);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setNotificationDate(NOTIFICATION_DATE);
        entity.setEvidenceBar(EVIDENCE_BAR);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setRemarks(REMARKS);
        entity.setAuthorityModifyFlag(AUTHORITY_MODIFY_FLAG);
        entity.setModifyDetails(MODIFY_DETAILS);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_RP020);
        return entity;
    }

    private TBL220Entity generateTBL220Entity() {
        TBL220Entity entity = new TBL220Entity();
        entity.setAdviceNo(ADVICE_NO);
        entity.setNotificationNo(NOTIFICATION_NO);
        entity.setAdviceUserId(ADVICE_USER_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE_RP030);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setNotificationDate(NOTIFICATION_DATE_RP030);
        entity.setEvidenceBar(EVIDENCE_BAR);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setAdviceDetails(ADVICE_DETAILS);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_RP030);
        return entity;
    }

    private TBL230Entity generateTBL230Entity() {
        TBL230Entity entity = new TBL230Entity();
        entity.setInvestigationNo(INVESTIGATION_NO);
        entity.setApartmentId(APARTMENT_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE_RP040);
        entity.setNoticeDestinationCode(NOTICE_DESTINATION_CODE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setInvestImplPlanTime(INVEST_IMPL_PLAN_TIME);
        entity.setInvestImplNumberPeople(INVEST_IMPL_NUMBER_PEOPLE);
        entity.setNecessaryDocument(NECESSARY_DOCUMENT);
        entity.setContact(CONTACT);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_RP040);
        entity.setTextaddress(TEXTADDRESS);
        return entity;
    }

    private TBL240Entity generateTBL240Entity() {
        TBL240Entity entity = new TBL240Entity();
        entity.setSupervisedNoticeNo(SUPERVISED_NOTICE_NO);
        entity.setApartmentId(APARTMENT_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE_RP050);
        entity.setSupervisedNoticeTypeCode(SUPERVISED_NOTICE_TYPE_CODE);
        entity.setSender(SENDER);
        entity.setMailingCode(MAILING_CODE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setEvidenceBar(EVIDENCE_BAR);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setPeriodEvidence(PERIOD_EVIDENCE);
        entity.setLastNoticeDate(LAST_NOTICE_DATE);
        entity.setLastDocumentNo(LAST_DOCUMENT_NO);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setNotificationFormatName(NOTIFICATION_FORMAT_NAME);
        entity.setNotificationTimelimit(NOTIFICATION_TIMELIMIT);
        entity.setContact(CONTACT);
        entity.setNotificationNo(NOTIFICATION_NO);
        entity.setSupervisedNoticeCount(SUPERVISED_NOTICE_COUNT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME_RP050);
        entity.setTextaddress1(TEXTADDRESS1);
        entity.setTextaddress2(TEXTADDRESS2);
        entity.setLastNoticeDate(LAST_NOTICE_DATE_RP060);
        return entity;
    }

    private TBL120Entity generateEntityTbl120(String accountLockFlag, String accountAvailableFlag,
            String loginStatusFlag, String userType) {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID_TBL120);
        entity.setLoginId(LOGIN_ID_TBL120);
        entity.setAccountLockFlag(accountLockFlag);
        entity.setLoginStatusFlag(loginStatusFlag);
        entity.setAvailability(accountAvailableFlag);
        entity.setUserType(userType);
        entity.setPassword(PASSWORD_TBL120);
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

    private UserDetails prepareSecurityContextHolder(TBL120Entity entity) {
        UserPrincipal userDetails = UserPrincipal.create(entity, true);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userDetails;
    }

    private String convertDateToJpString(String dateString) throws BusinessException {
        try {
            // covert String to localdate with format yyyyMMdd
            LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
            return DateUtil.getFormatJapaneseDateForShow(localDate);
        } catch (DateTimeParseException ex) {
            throw new BusinessException();
        }
    }
}
