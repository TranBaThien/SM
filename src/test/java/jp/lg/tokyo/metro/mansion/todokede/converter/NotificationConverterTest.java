/*
 * @(#) NotificationConverterTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.converter;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.BasicReportInfoForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationInfoAreaCommonForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.AcceptanceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.BasicReportInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoAreaCommonVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationRegistrationVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.transaction.SystemException;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD011_NOT_IMPLEMENTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_UNACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.DASH;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(CodeUtilConfig.class)
public class NotificationConverterTest {
    
    private static final String CAL_NOTIFICATION_DATE = "2019/12/28";
    private static final String DOCUMENT_NO = "DOCUMENT_NO";
    private static final String CITY_NAME = "CITY_NAME";
    private static final String CHIEF_NAME = "CHIEF_NAME";
    private static final String NEW_NOTIFICATION = "1";
    private static final String UPDATE_NOTIFICATION = "2";
    private static final String TOKYO = "1";
    private static final String SECTION_OWNER = "2";
    private static final String APARTMENT_NAME = "APARTMENT_NAME";
    private static final String NOTIFICATION_PERSONAL_NAME = "NOTIFICATION_PERSONAL_NAME";
    

    private static final LocalDate NEXT_NOTIFICATION_DATE = LocalDate.of(2025,12,12);
    private static final int NOTIFICATION_COUNT = 1;
    private static final int CHANGE_COUNT = 1;
    private static final String CITY_CODE = "CITY_CODE";
    private static final String APARTMENT_NAME_PHONETIC = "APARTMENT_NAME_PHONETIC";
    private static final String APARTMENT_ID = "APARTMENT_ID";
    private static final String NOTIFICATION_NO = "NOTIFICATION_NO";
    private static final String TXA_OPTIONAL = "TXA_OPTIONAL";
    private static final String TXT_CONTACT_MAIL = "contact@gmail.com";
    private static final String TXT_CONTACT_NAME_PHONETIC = "TXT_CONTACT_NAME_PHONETIC";
    private static final String TXT_CONTACT_NAME = "TXT_CONTACT_NAME";
    private static final String TXT_CONTACT_TEL_NO_1 = "TXT_CONTACT_TEL_NO_1";
    private static final String TXT_CONTACT_TEL_NO_2 = "TXT_CONTACT_TEL_NO_2";
    private static final String TXT_CONTACT_TEL_NO_3 = "TXT_CONTACT_TEL_NO_3";
    private static final String TXT_CONTACT_ADDRESS = "TXT_CONTACT_ADDRESS";
    private static final String TXT_CONTACT_ZIP_CODE_2 = "TXT_CONTACT_ZIP_CODE_2";
    private static final String TXT_CONTACT_ZIP_CODE_1 = "TXT_CONTACT_ZIP_CODE_1";
    private static final String TXT_CONTACT_PROPERTY_ELSE = "TXT_CONTACT_PROPERTY_ELSE";
    private static final String RDO_CONTACT_PROPERTY = "9";
    private static final String RDO_COMMUNITY = "RDO_COMMUNITY";
    private static final String RDO_EQUIPMENT_CHARGE = "RDO_EQUIPMENT_CHARGE";
    private static final String RDO_HEAT_SHIELDING = "RDO_HEAT_SHIELDING";
    private static final String RDO_LED = "RDO_LED";
    private static final String RDO_ELEVATOR = "RDO_ELEVATOR";
    private static final String RDO_RAILING = "RDO_RAILING";
    private static final String RDO_SLOPE = "RDO_SLOPE";
    private static final String RDO_DISASTER_PREVENTION_REGULAR = "RDO_DISASTER_PREVENTION_REGULAR";
    private static final String RDO_NEED_SUPPORT_LIST = "RDO_NEED_SUPPORT_LIST";
    private static final String RDO_DISASTER_PREVENTION_STOCKPILE = "RDO_DISASTER_PREVENTION_STOCKPILE";
    private static final String RDO_DISASTER_PREVENTION_MANUAL = "RDO_DISASTER_PREVENTION_MANUAL";
    private static final String RDO_VOLUNTARY_ORGANIZATION = "RDO_VOLUNTARY_ORGANIZATION";
    private static final String RDO_REPAIR_HISTORY = "RDO_REPAIR_HISTORY";
    private static final String RDO_DESIGN_DOCUMENT = "RDO_DESIGN_DOCUMENT";
    private static final String RDO_SEISMIC_RETROFIT = "RDO_SEISMIC_RETROFIT";
    private static final String RDO_EARTHQUAKE_RESISTANCE = "RDO_EARTHQUAKE_RESISTANCE";
    private static final String RDO_SEISMIC_DIAGNOSIS = "RDO_SEISMIC_DIAGNOSIS";
    private static final String TXT_RETAIL_NUMBER = "20";
    private static final String RDO_RENTAL_PERCENT = "50";
    private static final String TXT_EMPTY_NUMBER = "2";
    private static final String RDO_EMPTY_PERCENT = "50";
    private static final String RDO_SEGMENT = "RDO_SEGMENT";
    private static final String RDO_ARREARAGE_RULE = "RDO_ARREARAGE_RULE";
    private static final String TXT_LONG_REPAIR_PLAN_YEAR_TO = "2030";
    private static final String TXT_LONG_REPAIR_PLAN_YEAR_FROM = "2020";
    private static final String TXT_LONG_REPAIR_PLAN_PERIOD = "2";
    private static final String TXT_LONG_REPAIR_PLAN_YEAR = "10";
    private static final String RDO_LONG_REPAIR_PLAN = "1";
    private static final String TXT_REPAIR_NEAREST_YEAR = "TXT_REPAIR_NEAREST_YEAR";
    private static final String RDO_REPAIR_PLAN = "1";
    private static final String TXT_REPAIR_MONTHLY_COST = "5000";
    private static final String RDO_REPAIR_COST = "1";
    private static final String RDO_MANAGEMENT_COST = "3000";
    private static final String RDO_MINUTES = "60";
    private static final String RDO_ONE_YEAR_OVER = "RDO_ONE_YEAR_OVER";
    private static final String TXT_RULE_CHANGE_YEAR = "2020";
    private static final String RDO_RULE = "1";
    private static final String RDO_MANAGEMENT = "RDO_MANAGEMENT";
    private static final String RDO_GROUP = "RDO_GROUP";
    private static final String TXT_MANAGER_TEL_NO_3 = "TXT_MANAGER_TEL_NO_3";
    private static final String TXT_MANAGER_TEL_NO_2 = "TXT_MANAGER_TEL_NO_2";
    private static final String TXT_MANAGER_TEL_NO_1 = "TXT_MANAGER_TEL_NO_1";
    private static final String TXT_MANAGER_ADDRESS = "TXT_MANAGER_ADDRESS";
    private static final String TXT_MANAGER_ZIP_CODE_2 = "TXT_MANAGER_ZIP_CODE_2";
    private static final String TXT_MANAGER_ZIP_CODE_1 = "TXT_MANAGER_ZIP_CODE_1";
    private static final String TXT_MANAGER_NAME_PHONETIC = "TXT_MANAGER_NAME_PHONETIC";
    private static final String TXT_MANAGER_NAME = "TXT_MANAGER_NAME";
    private static final String TXT_MANAGEMENT_FORM_ELSE = "TXT_MANAGEMENT_FORM_ELSE";
    private static final String RDO_MANAGEMENT_FORM_ENTRUST_ALL = "2";
    private static final String RDO_MANAGEMENT_FORM = "8";
    private static final String TXT_USER_FOR_ELSE = "TXT_USER_FOR_ELSE";
    private static final String RDO_USER_FOR = "8";
    private static final String RDO_USER_FOR_ENTRUST_ALL = "2";
    private static final String TXT_LAND_RIGHT_ELSE = "TXT_LAND_RIGHT_ELSE";
    private static final String RDO_LAND_RIGHT = "8";
    private static final String CAL_BUILT_DATE = "2019/12/12";
    private static final LocalDate BUILT_DATE = LocalDate.now();
    private static final String TXT_FLOOR_NUMBER = "12";
    private static final int FLOOR_NUMBER = 12;
    private static final String TXT_HOUSE_NUMBER = "12";
    private static final int HOUSE_NUMBER = 12;
    private static final String TXT_GROUP_FORM_ELSE = "TXT_GROUP_FORM_ELSE";
    private static final String RDO_GROUP_FORM = "8";
    private static final String TXT_APARTMENT_NUMBER = "12";
    private static final int APARTMENT_NUMBER = 12;
    private static final String RDO_GROUP_YESNO = "1";
    private static final String TXT_LOST_REASON = "TXT_LOST_REASON";
    private static final String RDO_CHANGE_REASON = "2";
    private static final String TXT_NOTIFICATION_PERSON_NAME = "TXT_NOTIFICATION_PERSON_NAME";
    private static final String TXT_NOTIFICATION_GROUP_NAME = "TXT_NOTIFICATION_GROUP_NAME";
    private static final String RDO_NOTIFICATION_TYPE = "1";
    private static final String TXT_APARTMENT_ADDRESS_1 = "TXT_APARTMENT_ADDRESS_1";
    private static final String TXT_APARTMENT_ADDRESS_2 = "TXT_APARTMENT_ADDRESS_2";
    private static final String TXT_APARTMENT_ZIP_CODE_2 = "TXT_APARTMENT_ZIP_CODE_2";
    private static final String TXT_APARTMENT_ZIP_CODE_1 = "TXT_APARTMENT_ZIP_CODE_1";
    private static final String RDO_LOST_ELSE_REASON_CODE = "9";
    
    private static final String MODIFY_DETAILS = "MODIFYDETAILS";
    private static final String APPENDIX_NO = "APPENDIXNO";
    private static final String DOCUMENTNO = "DOCUMENTNO";
    private static final LocalDate NOTTICE_DATE = LocalDate.now();
    private static final String RECIPIENT_NAME_APARTMENT = "RECIPIENTNAMEAPARTMENT";
    private static final String RECIPIENT_NAME_USER = "RECIPIENTNAMEUSER";
    private static final String EVIDENCE_BAR = "1";
    private static final String EVIDENCE_BAR_1= "2";
    private static final String EVIDENCE_NO = "EVIDENCENO";
    private static final String REMARK = "REMARK";
    private static final String NOTIFICATION_METHOD_CODE = "REMARK";
    private static final String RECEPTION_NO = "RECEPTIONNO";
    
    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-001／区分: I／チェック内容：連絡先プロパティがセクションの所有者ではない場合の受諾通知の取得をテストする
     */
    @Test
    public void getAcceptanceNotificationWhenContactPropertyIsNotSectionOwner() {
        //Prepare data
        NotificationRegistrationVo notificationRegistrationVo = generateNotificationRegistrationVo(UPDATE_NOTIFICATION, TOKYO);
        MunicipalMasterInfoVo municipalMasterInfoVo = generateMunicipalMasterInfoVo();

        //Execute
        AcceptanceNotificationVo acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(notificationRegistrationVo, municipalMasterInfoVo, UserTypeCode.TOKYO_STAFF);

        //Assert
        assertResultGDA0110_001(acceptanceNotificationVo);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-002／区分：N／チェック内容：連絡先プロパティが空で、ログインしているユーザーが市の役割であり、通知タイプが新しい場合、受入通知の取得をテストします
     */
    @Test
    public void getAcceptanceNotificationWhenContactPropertyIsEmptyAndLoggedInUserIsCityRoleAndNotificationTypeIsNew() {
        //Prepare data
        NotificationRegistrationVo notificationRegistrationVo = generateNotificationRegistrationVo(NEW_NOTIFICATION, "");
        MunicipalMasterInfoVo municipalMasterInfoVo = generateMunicipalMasterInfoVo();

        //Execute
        AcceptanceNotificationVo acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(notificationRegistrationVo, municipalMasterInfoVo, UserTypeCode.CITY);

        //Assert
        assertResultGDA0110_002(acceptanceNotificationVo);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-003／区分：N／チェック内容：連絡先プロパティがセクションの所有者であり、ログインしているユーザーが市の役割ではなく、通知タイプが更新されている場合の受諾通知の取得をテストします
     */
    @Test
    public void getAcceptanceNotificationWhenContactPropertyIsSectionOwnerAndLoggedInUserIsTokyoRoleAndNotificationTypeIsUpdate() {
        //Prepare data
        NotificationRegistrationVo notificationRegistrationVo = generateNotificationRegistrationVo(UPDATE_NOTIFICATION, SECTION_OWNER);
        MunicipalMasterInfoVo municipalMasterInfoVo = generateMunicipalMasterInfoVo();

        //Execute
        AcceptanceNotificationVo acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(notificationRegistrationVo, municipalMasterInfoVo, UserTypeCode.TOKYO_STAFF);

        //Assert
        assertResultGDA0110_003(acceptanceNotificationVo);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-004／区分：E／チェック内容：通知登録フォームから通知に変換
     */
    @Test
    public void convertToNotificationFromNotificationRegistrationFormWhenIsOtherManagementForm() {
        //Prepare data
        NotificationRegistrationForm form = generateNotificationRegistrationForm();

        //Execute
        NotificationInfoVo result = NotificationConverter.toNotificationInfoVo(form);

        //Assert
        assertResultGDA0110_007(result);
    }
    
    private void assertResultGDA0110_007(NotificationInfoVo vo) {
        assertThat(vo.getNotificationNo()).isEqualTo(NOTIFICATION_NO);
        assertThat(vo.getAcceptStatus()).isEqualTo(CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED));
        assertThat(vo.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(vo.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(vo.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(vo.getZipCode()).isEqualTo(new StringBuilder()
                .append(TXT_APARTMENT_ZIP_CODE_1)
                .append(DASH)
                .append(TXT_APARTMENT_ZIP_CODE_2)
                .toString());
        assertThat(vo.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(vo.getAddress()).isEqualTo(TXT_APARTMENT_ADDRESS_2);
        assertThat(vo.getNotificationType()).isEqualTo(RDO_NOTIFICATION_TYPE);
        assertThat(vo.getNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateFromString2(CAL_NOTIFICATION_DATE));
        assertThat(vo.getNotificationGroupName()).isEqualTo(TXT_NOTIFICATION_GROUP_NAME);
        assertThat(vo.getNotificationPersonName()).isEqualTo(TXT_NOTIFICATION_PERSON_NAME);
        assertThat(vo.getAdviceDoneFlag()).isEqualTo(CodeUtil.getValue(CommonConstants.CD011, CD011_NOT_IMPLEMENTED));
        assertThat(vo.getChangeCount()).isEqualTo(CHANGE_COUNT);
        assertThat(vo.getNotificationCount()).isEqualTo(NOTIFICATION_COUNT);
        assertThat(vo.getChangeReasonCode()).isEqualTo(RDO_CHANGE_REASON);
        assertThat(vo.getLostElseReasonCode()).isEqualTo(RDO_LOST_ELSE_REASON_CODE);
        assertThat(vo.getGroupYesnoCode()).isEqualTo(RDO_GROUP_YESNO);
        assertThat(vo.getApartmentNumber()).isEqualTo(Integer.parseInt(TXT_APARTMENT_NUMBER));
        assertThat(vo.getGroupForm()).isEqualTo(RDO_GROUP_FORM);
        assertThat(vo.getGroupFormElse()).isEqualTo(TXT_GROUP_FORM_ELSE);
        assertThat(vo.getHouseNumber()).isEqualTo(Integer.parseInt(TXT_HOUSE_NUMBER));
        assertThat(vo.getFloorNumber()).isEqualTo(Integer.parseInt(TXT_FLOOR_NUMBER));
        assertThat(vo.getBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateFromString2(CAL_BUILT_DATE));
        assertThat(vo.getLandRightsCode()).isEqualTo(RDO_LAND_RIGHT);
        assertThat(vo.getLandRightsElse()).isEqualTo(TXT_LAND_RIGHT_ELSE);
        assertThat(vo.getUseforCode()).isEqualTo(RDO_USER_FOR);
        assertThat(vo.getUseforElse()).isEqualTo(TXT_USER_FOR_ELSE);
        assertThat(vo.getManagementFormCode()).isEqualTo(RDO_MANAGEMENT_FORM);
        assertThat(vo.getManagementFormElse()).isEqualTo(TXT_MANAGEMENT_FORM_ELSE);
        assertThat(vo.getManagerName()).isEqualTo(null);
        assertThat(vo.getManagerNamePhonetic()).isEqualTo(null);
        assertThat(vo.getManagerZipCode()).isEqualTo(null);                
        assertThat(vo.getManagerAddress()).isEqualTo(null);
        assertThat(vo.getManagerTelNo()).isEqualTo(null);
        assertThat(vo.getGroupCode()).isEqualTo(RDO_GROUP);
        assertThat(vo.getManagerCode()).isEqualTo(RDO_MANAGEMENT);
        assertThat(vo.getRuleCode()).isEqualTo(RDO_RULE);
        assertThat(vo.getRuleChangeYear()).isEqualTo(TXT_RULE_CHANGE_YEAR);
        assertThat(vo.getOpenOneyearOver()).isEqualTo(RDO_ONE_YEAR_OVER);
        assertThat(vo.getMinutes()).isEqualTo(RDO_MINUTES);
        assertThat(vo.getManagementCostCode()).isEqualTo(RDO_MANAGEMENT_COST);
        assertThat(vo.getRepairCostCode()).isEqualTo(RDO_REPAIR_COST);
        assertThat(vo.getRepairMonthlyCost()).isEqualTo(Integer.parseInt(CommonUtils.removeComma(TXT_REPAIR_MONTHLY_COST)));
        assertThat(vo.getRepairPlanCode()).isEqualTo(RDO_REPAIR_PLAN);
        assertThat(vo.getRepairNearestYear()).isEqualTo(TXT_REPAIR_NEAREST_YEAR);
        assertThat(vo.getLongRepairPlanCode()).isEqualTo(RDO_LONG_REPAIR_PLAN);
        assertThat(vo.getLongRepairPlanYear()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR);
        assertThat(vo.getLongRepairPlanPeriod()).isEqualTo(Integer.parseInt(TXT_LONG_REPAIR_PLAN_PERIOD));
        assertThat(vo.getLongRepairPlanYearFrom()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR_FROM);
        assertThat(vo.getLongRepairPlanYearTo()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR_TO);
        assertThat(vo.getArrearageRuleCode()).isEqualTo(RDO_ARREARAGE_RULE);
        assertThat(vo.getSegmentCode()).isEqualTo(RDO_SEGMENT);
        assertThat(vo.getEmptyPercentCode()).isEqualTo(RDO_EMPTY_PERCENT);
        assertThat(vo.getEmptyNumber()).isEqualTo(Integer.parseInt(TXT_EMPTY_NUMBER));
        assertThat(vo.getRentalPercentCode()).isEqualTo(RDO_RENTAL_PERCENT);
        assertThat(vo.getRentalNumber()).isEqualTo(Integer.parseInt(TXT_RETAIL_NUMBER));
        assertThat(vo.getSeismicDiagnosisCode()).isEqualTo(RDO_SEISMIC_DIAGNOSIS);
        assertThat(vo.getEarthquakeResistanceCode()).isEqualTo(RDO_EARTHQUAKE_RESISTANCE);
        assertThat(vo.getSeismicRetrofitCode()).isEqualTo(RDO_SEISMIC_RETROFIT);
        assertThat(vo.getDesignDocumentCode()).isEqualTo(RDO_DESIGN_DOCUMENT);
        assertThat(vo.getRepairHistoryCode()).isEqualTo(RDO_REPAIR_HISTORY);
        assertThat(vo.getVoluntaryOrganizationCode()).isEqualTo(RDO_VOLUNTARY_ORGANIZATION);
        assertThat(vo.getDisasterPreventionManualCode()).isEqualTo(RDO_DISASTER_PREVENTION_MANUAL);
        assertThat(vo.getDisasterPreventionStockpileCode()).isEqualTo(RDO_DISASTER_PREVENTION_STOCKPILE);
        assertThat(vo.getNeedSupportListCode()).isEqualTo(RDO_NEED_SUPPORT_LIST);
        assertThat(vo.getDisasterPreventionRegularCode()).isEqualTo(RDO_DISASTER_PREVENTION_REGULAR);
        assertThat(vo.getSlopeCode()).isEqualTo(RDO_SLOPE);
        assertThat(vo.getRailingCode()).isEqualTo(RDO_RAILING);
        assertThat(vo.getElevatorCode()).isEqualTo(RDO_ELEVATOR);
        assertThat(vo.getLedCode()).isEqualTo(RDO_LED);
        assertThat(vo.getHeatShieldingCode()).isEqualTo(RDO_HEAT_SHIELDING);
        assertThat(vo.getEquipmentChargeCode()).isEqualTo(RDO_EQUIPMENT_CHARGE);
        assertThat(vo.getCommunityCode()).isEqualTo(RDO_COMMUNITY);
        assertThat(vo.getContactPropertyCode()).isEqualTo(RDO_CONTACT_PROPERTY);
        assertThat(vo.getContactPropertyElse()).isEqualTo(TXT_CONTACT_PROPERTY_ELSE);
        assertThat(vo.getContactZipCode()).isEqualTo(new StringBuilder()
                .append(TXT_CONTACT_ZIP_CODE_1)
                .append(DASH)
                .append(TXT_CONTACT_ZIP_CODE_2)
                .toString());
        assertThat(vo.getContactAddress()).isEqualTo(TXT_CONTACT_ADDRESS);
        assertThat(vo.getContactTelNo()).isEqualTo(new StringBuilder()
                .append(TXT_CONTACT_TEL_NO_1)
                .append(DASH)
                .append(TXT_CONTACT_TEL_NO_2)
                .append(DASH)
                .append(TXT_CONTACT_TEL_NO_3)
                .toString());
        assertThat(vo.getContactName()).isEqualTo(TXT_CONTACT_NAME);
        assertThat(vo.getContactNamePhonetic()).isEqualTo(TXT_CONTACT_NAME_PHONETIC);
        assertThat(vo.getContactMailAddress()).isEqualTo(TXT_CONTACT_MAIL);
        assertThat(vo.getOptional()).isEqualTo(TXA_OPTIONAL);
        assertThat(vo.getNextNotificationDate()).isEqualTo(NEXT_NOTIFICATION_DATE);
    }
        
    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-007／区分：N／チェック内容：通知登録フォームから通知に変換
     */
    @Test
    public void convertToNotificationFromNotificationRegistrationFormWhenIsEnTrustAllManagementForm() {
        //Prepare data
        NotificationRegistrationForm form = generateNotificationRegistrationForm1();

        //Execute
        NotificationInfoVo result = NotificationConverter.toNotificationInfoVo(form);

        //Assert
        assertResultGDA0110_007_1(result);
    }
    
    private void assertResultGDA0110_007_1(NotificationInfoVo vo) {
        assertThat(vo.getNotificationNo()).isEqualTo(NOTIFICATION_NO);
        assertThat(vo.getAcceptStatus()).isEqualTo(CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED));
        assertThat(vo.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(vo.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(vo.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(vo.getZipCode()).isEqualTo(new StringBuilder()
                .append(TXT_APARTMENT_ZIP_CODE_1)
                .append(DASH)
                .append(TXT_APARTMENT_ZIP_CODE_2)
                .toString());
        assertThat(vo.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(vo.getAddress()).isEqualTo(TXT_APARTMENT_ADDRESS_2);
        assertThat(vo.getNotificationType()).isEqualTo(RDO_NOTIFICATION_TYPE);
        assertThat(vo.getNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateFromString2(CAL_NOTIFICATION_DATE));
        assertThat(vo.getNotificationGroupName()).isEqualTo(TXT_NOTIFICATION_GROUP_NAME);
        assertThat(vo.getNotificationPersonName()).isEqualTo(TXT_NOTIFICATION_PERSON_NAME);
        assertThat(vo.getAdviceDoneFlag()).isEqualTo(CodeUtil.getValue(CommonConstants.CD011, CD011_NOT_IMPLEMENTED));
        assertThat(vo.getChangeCount()).isEqualTo(CHANGE_COUNT);
        assertThat(vo.getNotificationCount()).isEqualTo(NOTIFICATION_COUNT);
        assertThat(vo.getChangeReasonCode()).isEqualTo(RDO_CHANGE_REASON);
        assertThat(vo.getLostElseReasonCode()).isEqualTo(RDO_LOST_ELSE_REASON_CODE);
        assertThat(vo.getGroupYesnoCode()).isEqualTo(RDO_GROUP_YESNO);
        assertThat(vo.getApartmentNumber()).isEqualTo(Integer.parseInt(TXT_APARTMENT_NUMBER));
        assertThat(vo.getGroupForm()).isEqualTo(RDO_GROUP_FORM);
        assertThat(vo.getGroupFormElse()).isEqualTo(TXT_GROUP_FORM_ELSE);
        assertThat(vo.getHouseNumber()).isEqualTo(Integer.parseInt(TXT_HOUSE_NUMBER));
        assertThat(vo.getFloorNumber()).isEqualTo(Integer.parseInt(TXT_FLOOR_NUMBER));
        assertThat(vo.getBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateFromString2(CAL_BUILT_DATE));
        assertThat(vo.getLandRightsCode()).isEqualTo(RDO_LAND_RIGHT);
        assertThat(vo.getLandRightsElse()).isEqualTo(TXT_LAND_RIGHT_ELSE);
        assertThat(vo.getUseforCode()).isEqualTo(RDO_USER_FOR);
        assertThat(vo.getUseforElse()).isEqualTo(TXT_USER_FOR_ELSE);
        assertThat(vo.getManagementFormCode()).isEqualTo(RDO_MANAGEMENT_FORM_ENTRUST_ALL);
        assertThat(vo.getManagerName()).isEqualTo(TXT_MANAGER_NAME);
        assertThat(vo.getManagerNamePhonetic()).isEqualTo(TXT_MANAGER_NAME_PHONETIC);
        assertThat(vo.getManagerZipCode()).isEqualTo(new StringBuilder()
                .append(TXT_MANAGER_ZIP_CODE_1)
                .append(DASH)                
                .toString());
        assertThat(vo.getManagerAddress()).isEqualTo(TXT_MANAGER_ADDRESS);
        assertThat(vo.getManagerTelNo()).isEqualTo(new StringBuilder()
                .append(TXT_MANAGER_TEL_NO_1)
                .append(DASH)
                .append(TXT_MANAGER_TEL_NO_2)
                .append(DASH)
                .append(TXT_MANAGER_TEL_NO_3)
                .toString());
        assertThat(vo.getGroupCode()).isEqualTo(RDO_GROUP);
        assertThat(vo.getManagerCode()).isEqualTo(RDO_MANAGEMENT);
        assertThat(vo.getRuleCode()).isEqualTo(RDO_RULE);
        assertThat(vo.getRuleChangeYear()).isEqualTo(TXT_RULE_CHANGE_YEAR);
        assertThat(vo.getOpenOneyearOver()).isEqualTo(RDO_ONE_YEAR_OVER);
        assertThat(vo.getMinutes()).isEqualTo(RDO_MINUTES);
        assertThat(vo.getManagementCostCode()).isEqualTo(RDO_MANAGEMENT_COST);
        assertThat(vo.getRepairCostCode()).isEqualTo(RDO_REPAIR_COST);
        assertThat(vo.getRepairMonthlyCost()).isEqualTo(Integer.parseInt(CommonUtils.removeComma(TXT_REPAIR_MONTHLY_COST)));
        assertThat(vo.getRepairPlanCode()).isEqualTo(RDO_REPAIR_PLAN);
        assertThat(vo.getRepairNearestYear()).isEqualTo(TXT_REPAIR_NEAREST_YEAR);
        assertThat(vo.getLongRepairPlanCode()).isEqualTo(RDO_LONG_REPAIR_PLAN);
        assertThat(vo.getLongRepairPlanYear()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR);
        assertThat(vo.getLongRepairPlanPeriod()).isEqualTo(Integer.parseInt(TXT_LONG_REPAIR_PLAN_PERIOD));
        assertThat(vo.getLongRepairPlanYearFrom()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR_FROM);
        assertThat(vo.getLongRepairPlanYearTo()).isEqualTo(TXT_LONG_REPAIR_PLAN_YEAR_TO);
        assertThat(vo.getArrearageRuleCode()).isEqualTo(RDO_ARREARAGE_RULE);
        assertThat(vo.getSegmentCode()).isEqualTo(RDO_SEGMENT);
        assertThat(vo.getEmptyPercentCode()).isEqualTo(RDO_EMPTY_PERCENT);
        assertThat(vo.getEmptyNumber()).isEqualTo(Integer.parseInt(TXT_EMPTY_NUMBER));
        assertThat(vo.getRentalPercentCode()).isEqualTo(RDO_RENTAL_PERCENT);
        assertThat(vo.getRentalNumber()).isEqualTo(Integer.parseInt(TXT_RETAIL_NUMBER));
        assertThat(vo.getSeismicDiagnosisCode()).isEqualTo(RDO_SEISMIC_DIAGNOSIS);
        assertThat(vo.getEarthquakeResistanceCode()).isEqualTo(RDO_EARTHQUAKE_RESISTANCE);
        assertThat(vo.getSeismicRetrofitCode()).isEqualTo(RDO_SEISMIC_RETROFIT);
        assertThat(vo.getDesignDocumentCode()).isEqualTo(RDO_DESIGN_DOCUMENT);
        assertThat(vo.getRepairHistoryCode()).isEqualTo(RDO_REPAIR_HISTORY);
        assertThat(vo.getVoluntaryOrganizationCode()).isEqualTo(RDO_VOLUNTARY_ORGANIZATION);
        assertThat(vo.getDisasterPreventionManualCode()).isEqualTo(RDO_DISASTER_PREVENTION_MANUAL);
        assertThat(vo.getDisasterPreventionStockpileCode()).isEqualTo(RDO_DISASTER_PREVENTION_STOCKPILE);
        assertThat(vo.getNeedSupportListCode()).isEqualTo(RDO_NEED_SUPPORT_LIST);
        assertThat(vo.getDisasterPreventionRegularCode()).isEqualTo(RDO_DISASTER_PREVENTION_REGULAR);
        assertThat(vo.getSlopeCode()).isEqualTo(RDO_SLOPE);
        assertThat(vo.getRailingCode()).isEqualTo(RDO_RAILING);
        assertThat(vo.getElevatorCode()).isEqualTo(RDO_ELEVATOR);
        assertThat(vo.getLedCode()).isEqualTo(RDO_LED);
        assertThat(vo.getHeatShieldingCode()).isEqualTo(RDO_HEAT_SHIELDING);
        assertThat(vo.getEquipmentChargeCode()).isEqualTo(RDO_EQUIPMENT_CHARGE);
        assertThat(vo.getCommunityCode()).isEqualTo(RDO_COMMUNITY);
        assertThat(vo.getContactPropertyCode()).isEqualTo(RDO_CONTACT_PROPERTY);
        assertThat(vo.getContactPropertyElse()).isEqualTo(TXT_CONTACT_PROPERTY_ELSE);
        assertThat(vo.getContactZipCode()).isEqualTo(new StringBuilder()
                .append(TXT_CONTACT_ZIP_CODE_1)
                .append(DASH)
                .append(TXT_CONTACT_ZIP_CODE_2)
                .toString());
        assertThat(vo.getContactAddress()).isEqualTo(TXT_CONTACT_ADDRESS);
        assertThat(vo.getContactTelNo()).isEqualTo(new StringBuilder()
                .append(TXT_CONTACT_TEL_NO_1)
                .append(DASH)
                .append(TXT_CONTACT_TEL_NO_2)
                .append(DASH)
                .append(TXT_CONTACT_TEL_NO_3)
                .toString());
        assertThat(vo.getContactName()).isEqualTo(TXT_CONTACT_NAME);
        assertThat(vo.getContactNamePhonetic()).isEqualTo(TXT_CONTACT_NAME_PHONETIC);
        assertThat(vo.getContactMailAddress()).isEqualTo(TXT_CONTACT_MAIL);
        assertThat(vo.getOptional()).isEqualTo(TXA_OPTIONAL);
        assertThat(vo.getNextNotificationDate()).isEqualTo(NEXT_NOTIFICATION_DATE);
    }
    
    private NotificationRegistrationForm generateNotificationRegistrationForm() {
        BasicReportInfoForm basicReportInfoForm = new BasicReportInfoForm();
        basicReportInfoForm.setTxtApartmentZipCode1(TXT_APARTMENT_ZIP_CODE_1);
        basicReportInfoForm.setTxtApartmentZipCode2(TXT_APARTMENT_ZIP_CODE_2);
        basicReportInfoForm.setTxtApartmentAddress2(TXT_APARTMENT_ADDRESS_2);
        basicReportInfoForm.setTxtApartmentName(APARTMENT_NAME);
        basicReportInfoForm.setTxtApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);

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
        form.setNotificationNo(NOTIFICATION_NO);
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
    
    private NotificationRegistrationForm generateNotificationRegistrationForm1() {
        BasicReportInfoForm basicReportInfoForm = new BasicReportInfoForm();
        basicReportInfoForm.setTxtApartmentZipCode1(TXT_APARTMENT_ZIP_CODE_1);
        basicReportInfoForm.setTxtApartmentZipCode2(TXT_APARTMENT_ZIP_CODE_2);
        basicReportInfoForm.setTxtApartmentAddress2(TXT_APARTMENT_ADDRESS_2);
        basicReportInfoForm.setTxtApartmentName(APARTMENT_NAME);
        basicReportInfoForm.setTxtApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);

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
        infoAreaCommon.setRdoManagementForm(RDO_MANAGEMENT_FORM_ENTRUST_ALL);
        infoAreaCommon.setTxtManagementFormElse(TXT_MANAGEMENT_FORM_ELSE);
        infoAreaCommon.setTxtManagerName(TXT_MANAGER_NAME);
        infoAreaCommon.setTxtManagerNamePhonetic(TXT_MANAGER_NAME_PHONETIC);
        infoAreaCommon.setTxtManagerZipCode1(TXT_MANAGER_ZIP_CODE_1);
        infoAreaCommon.setTxtManagerZipCode2(CommonConstants.BLANK);
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
        form.setNotificationNo(NOTIFICATION_NO);
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
        
    private TBL215Entity generateTBL215Entity() {
        TBL215Entity entity = new TBL215Entity();
        
        entity.setModifyDetails(MODIFY_DETAILS);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENTNO);
        entity.setNoticeDate(NOTTICE_DATE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setNotificationDate(NEXT_NOTIFICATION_DATE);
        entity.setEvidenceBar(EVIDENCE_BAR);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setRemarks(REMARK);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        
        return entity;
    }
    
    private TBL215Entity generateTBL215Entity1() {
        TBL215Entity entity = new TBL215Entity();
        
        entity.setModifyDetails(MODIFY_DETAILS);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENTNO);
        entity.setNoticeDate(NOTTICE_DATE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setNotificationDate(NEXT_NOTIFICATION_DATE);
        entity.setEvidenceBar(EVIDENCE_BAR_1);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setRemarks(REMARK);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        
        return entity;
    }
    
    private void assertResultGDA0110_003(AcceptanceNotificationVo acceptanceNotificationVo) {
        assertThat(acceptanceNotificationVo.getTxtAppendixNo()).isEqualTo("別記第　　号様式（第　　条関係）");
        assertThat(acceptanceNotificationVo.getTxtDocumentNo()).isEqualTo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + DOCUMENT_NO + "　第　号");
        assertThat(acceptanceNotificationVo.getLblRecipientNameApartment()).isEqualTo(APARTMENT_NAME);
        assertThat(acceptanceNotificationVo.getLblRecipientNameUser()).isEqualTo("区分所有者　" + NOTIFICATION_PERSONAL_NAME);
        assertThat(acceptanceNotificationVo.getTxtSender()).isEqualTo("東京都知事　" + CHIEF_NAME);
        assertThat(acceptanceNotificationVo.getLblNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateFromString(CAL_NOTIFICATION_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        assertThat(acceptanceNotificationVo.getLblEvidenceBar()).isEqualTo("16");
    }

    private void assertResultGDA0110_002(AcceptanceNotificationVo acceptanceNotificationVo) {
        assertThat(acceptanceNotificationVo.getTxtAppendixNo()).isEqualTo("別記第　　号様式（第　　条関係）");
        assertThat(acceptanceNotificationVo.getTxtDocumentNo()).isEqualTo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting()+DOCUMENT_NO + "　第　号");
        assertThat(acceptanceNotificationVo.getLblRecipientNameApartment()).isEqualTo(APARTMENT_NAME);
        assertThat(acceptanceNotificationVo.getLblRecipientNameUser()).isEqualTo("");
        assertThat(acceptanceNotificationVo.getTxtSender()).isEqualTo(CITY_NAME + "長　" + CHIEF_NAME);
        assertThat(acceptanceNotificationVo.getLblNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateFromString(CAL_NOTIFICATION_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        assertThat(acceptanceNotificationVo.getLblEvidenceBar()).isEqualTo("15");
    }

    private void assertResultGDA0110_001(AcceptanceNotificationVo acceptanceNotificationVo) {
        assertThat(acceptanceNotificationVo.getTxtAppendixNo()).isEqualTo("別記第　　号様式（第　　条関係）");
        assertThat(acceptanceNotificationVo.getTxtDocumentNo()).isEqualTo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + DOCUMENT_NO + "　第　号");
        assertThat(acceptanceNotificationVo.getLblRecipientNameApartment()).isEqualTo(APARTMENT_NAME);
        assertThat(acceptanceNotificationVo.getLblRecipientNameUser()).isEqualTo("管理組合理事長");
        assertThat(acceptanceNotificationVo.getTxtSender()).isEqualTo("東京都知事　" + CHIEF_NAME);
        assertThat(acceptanceNotificationVo.getLblNotificationDate()).isEqualTo(DateTimeUtil.getLocalDateFromString(CAL_NOTIFICATION_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        assertThat(acceptanceNotificationVo.getLblEvidenceBar()).isEqualTo("16");
    }

    private NotificationRegistrationVo generateNotificationRegistrationVo(String notificationType, String contactProperty) {
        NotificationRegistrationVo vo = new NotificationRegistrationVo();
        vo.setInfoAreaCommon(generateNotificationInfoAreaVo(notificationType, contactProperty));

        BasicReportInfoVo basicReportInfoVo = new BasicReportInfoVo();
        basicReportInfoVo.setTxtApartmentName(APARTMENT_NAME);
        vo.setBasicReportInfo(basicReportInfoVo);

        return vo;
    }
    
    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-008-001／区分：N／ Test To Acceptance NotificationVo When Sucess 1
     */
    @Test
    public void testToAcceptanceNotificationVoWhenSucess1 () {
        //prepare data
        TBL215Entity tbl215Entity = generateTBL215Entity();
        
        //execute
        AcceptanceNotificationVo result = NotificationConverter.toAcceptanceNotificationVo(tbl215Entity);
        
        //assert 
        assertThat(result.getTxaOrrectionDetails()).isEqualTo(MODIFY_DETAILS);
    }
    
    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-008-002／区分：N／ Test To Acceptance NotificationVo When Sucess 2
     */
    @Test
    public void testToAcceptanceNotificationVoWhenSucess2 () {
        //prepare data
        TBL215Entity tbl215Entity = generateTBL215Entity1();
        
        //execute
        AcceptanceNotificationVo result = NotificationConverter.toAcceptanceNotificationVo(tbl215Entity);
        
        //assert 
        assertThat(result.getTxaOrrectionDetails()).isEqualTo(MODIFY_DETAILS);
    }
    
    /**
     * 案件ID：MDA0110／チェックリストID：UT-ID：MDA0110-009／区分：N／ Test To Notification Registration Vo When Screen Id Is MDA0110 And IsRestored
     */
    @Test
    public void testToNotificationRegistrationVoWhenScreenIdIsMDA0110AndIsRestored() throws SystemException {
        //prepare data
        MansionInfoVo mansionInfoVo = new MansionInfoVo();
        NotificationInfoVo notificationInfoVo = generateNotificationInfoVo();
        String idScreen = "MDA0110";
        //execute
        NotificationRegistrationVo result = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, notificationInfoVo, idScreen, true);
        
        //assert
        assertThat(result.getInfoAreaCommon().getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(notificationInfoVo.getBuiltDate()));

    }
    
    /**
     * 案件ID：MDA0110／チェックリストID：UT-ID：MDA0110-010／区分：N／ Test To Notification Registration Vo When Screen Id Is MDA0110 And Not Is Restored
     */
    @Test
    public void testToNotificationRegistrationVoWhenScreenIdIsMDA0110AndNotIsRestored() throws SystemException {
        //prepare data
        MansionInfoVo mansionInfoVo = new MansionInfoVo();
        NotificationInfoVo notificationInfoVo = generateNotificationInfoVo();
        String idScreen = "MDA0110";
        //execute
        NotificationRegistrationVo result = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, notificationInfoVo, idScreen, false);
        
        //assert
        assertThat(result.getInfoAreaCommon().getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(notificationInfoVo.getBuiltDate()));

    }
    
    /**
     * 案件ID：MDA0110／チェックリストID：UT-ID：MDA0110-011／区分：N／ Test To Notification Registration Vo When Screen Id Is Not MDA0110 And Is Restored
     */
    @Test
    public void testToNotificationRegistrationVoScreenIdIsNotMDA0110AndIsRestored () throws SystemException {
        //prepare data
        MansionInfoVo mansionInfoVo = new MansionInfoVo();
        NotificationInfoVo notificationInfoVo = generateNotificationInfoVo();
        String idScreen = "GDA0110";
        //execute
        NotificationRegistrationVo result = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, notificationInfoVo, idScreen, true);
        
        //assert 
        assertThat(result.getInfoAreaCommon().getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(notificationInfoVo.getBuiltDate()));
    }
    
    /**
     * 案件ID：MDA0110／チェックリストID：UT-ID：MDA0110-012／区分：N／ Test To Notification Registration Vo When Screen Id Is Not MDA0110 And Is Not Restored
     */
    @Test
    public void testToNotificationRegistrationVoScreenIdIsNotMDA0110AndNotIsRestored () throws SystemException {
        //prepare data
        MansionInfoVo mansionInfoVo = new MansionInfoVo();
        NotificationInfoVo notificationInfoVo = generateNotificationInfoVo();
        String idScreen = "GDA0110";
        //execute
        NotificationRegistrationVo result = NotificationConverter.toNotificationRegistrationVo(mansionInfoVo, notificationInfoVo, idScreen, false);
        
        //assert 
        assertThat(result.getInfoAreaCommon().getCalBuiltDate()).isEqualTo(DateTimeUtil.getLocalDateAsString2(notificationInfoVo.getBuiltDate()));
    }
    private MunicipalMasterInfoVo generateMunicipalMasterInfoVo() {
        MunicipalMasterInfoVo vo = new MunicipalMasterInfoVo();
        vo.setDocumentNo(DOCUMENT_NO);
        vo.setCityName(CITY_NAME);
        vo.setChiefName(CHIEF_NAME);
        return vo;
    }

    private NotificationInfoAreaCommonVo generateNotificationInfoAreaVo(String notificationType, String contactProperty) {
        NotificationInfoAreaCommonVo infoAreaCommon = new NotificationInfoAreaCommonVo();
        infoAreaCommon.setRdoContactProperty(contactProperty);
        infoAreaCommon.setRdoNotificationType(notificationType);
        infoAreaCommon.setCalNotificationDate(CAL_NOTIFICATION_DATE);
        infoAreaCommon.setTxtNotificationPersonName(NOTIFICATION_PERSONAL_NAME);
        return infoAreaCommon;
    }
    
    private NotificationInfoVo generateNotificationInfoVo () {    
        NotificationInfoVo notificationInfoVo = new NotificationInfoVo();
        
        notificationInfoVo.setApartmentName(NOTIFICATION_NO);
        notificationInfoVo.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        notificationInfoVo.setTxtApartmentZipCode1(TXT_APARTMENT_ZIP_CODE_1);
        notificationInfoVo.setTxtApartmentZipCode2(TXT_APARTMENT_ZIP_CODE_2);
        notificationInfoVo.setLblApartmentAddress1(TXT_APARTMENT_ADDRESS_1);
        notificationInfoVo.setTxtApartmentAddress2(TXT_APARTMENT_ADDRESS_2);
        notificationInfoVo.setReceptionNo(RECEPTION_NO);
        notificationInfoVo.setNotificationDate(LocalDate.now());
        notificationInfoVo.setNotificationGroupName(TXT_NOTIFICATION_GROUP_NAME);
        notificationInfoVo.setNotificationPersonName(TXT_NOTIFICATION_PERSON_NAME);
        notificationInfoVo.setNotificationType(RDO_NOTIFICATION_TYPE);
        notificationInfoVo.setChangeReasonCode(RDO_CHANGE_REASON);
        notificationInfoVo.setLostElseReasonCode(RDO_LOST_ELSE_REASON_CODE);
        notificationInfoVo.setLostElseReasonElse(TXT_LOST_REASON);
        notificationInfoVo.setGroupYesnoCode(RDO_GROUP_YESNO);
        notificationInfoVo.setApartmentNumber(APARTMENT_NUMBER);
        notificationInfoVo.setGroupForm(RDO_GROUP_FORM);
        notificationInfoVo.setGroupFormElse(TXT_GROUP_FORM_ELSE);
        notificationInfoVo.setHouseNumber(HOUSE_NUMBER);
        notificationInfoVo.setFloorNumber(FLOOR_NUMBER);
        notificationInfoVo.setBuiltDate(BUILT_DATE);
        notificationInfoVo.setLandRightsCode(RDO_LAND_RIGHT);
        notificationInfoVo.setLandRightsElse(TXT_LAND_RIGHT_ELSE);
        notificationInfoVo.setUseforCode(RDO_USER_FOR);
        notificationInfoVo.setUseforElse(TXT_USER_FOR_ELSE);
        notificationInfoVo.setManagementFormCode(RDO_MANAGEMENT_FORM);
        notificationInfoVo.setManagementFormElse(TXT_MANAGEMENT_FORM_ELSE);
        
        return notificationInfoVo;
    }
}
