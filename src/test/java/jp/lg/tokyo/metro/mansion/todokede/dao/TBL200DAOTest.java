/*
 * @(#) TBL200DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Dec 18, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010TmpVo;

/**
 * @author nvlong1
 *
 */
public class TBL200DAOTest extends AbstractDaoTest {

	@Autowired
	private TBL200DAO tbl200DAO;

    private final String NOTIFICATION_NO = "1000000001";
    private final String RECEPTION_NO = "1000000001";
    private final String ACCEPT_STATUS = "1";
    private final String APARTMENT_ID = "1000000001";
    private final String APARTMENT_NAME = "Tokyo";
    private final String APARTMENT_NAME_PHONETIC = "Vinh Trung";
    private final String ZIP_CODE = "131-041";
    private final String CITY_CODE = null;
    private final String ADDRESS = "東京都武蔵野市吉祥寺東町";
    private final String NOTIFICATION_TYPE = "2";
    private final LocalDate NOTIFICATION_DATE = LocalDate.parse("20191129", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private final String NOTIFICATION_GROUP_NAME = "0";
    private final String NOTIFICATION_PERSON_NAME = "0";
    private final String TEMP_KBN = null;
    private final String ADVICE_DONE_FLAG = "2";
    private final int CHANGE_COUNT = 0;
    private final int NOTIFICATION_COUNT = 0;
    private final String CHANGE_REASON_CODE = "1";
    private final String LOST_ELSE_REASON_CODE = "9";
    private final String LOST_ELSE_REASON_ELSE = "reason test";
    private final String GROUP_YESNO_CODE = "1";
    private final Integer APARTMENT_NUMBER = 123;
    private final String GROUP_FORM = "1";
    private final String GROUP_FORM_ELSE = null;
    private final int HOUSE_NUMBER = 12313;
    private final Integer FLOOR_NUMBER = 12;
    private final LocalDate BUILT_DATE = LocalDate.parse("20191125", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private final String LAND_RIGHTS_CODE = null;
    private final String LAND_RIGHTS_ELSE = "0";
    private final String USEFOR_CODE = null;
    private final String USEFOR_ELSE = "0";
    private final String MANAGEMENT_FORM_CODE = null;
    private final String MANAGEMENT_FORM_ELSE = "0";
    private final String MANAGER_NAME = "test manager name";
    private final String MANAGER_NAME_PHONETIC = "test manager name phonetic";
    private final String MANAGER_ZIP_CODE = "999-5555";
    private final String MANAGER_ADDRESS = "0";
    private final String MANAGER_TEL_NO = "111-222-3333";
    private final String GROUP_CODE = null;
    private final String MANAGER_CODE = null;
    private final String RULE_CODE = "1";
    private final String RULE_CHANGE_YEAR = "2019";
    private final String OPEN_ONEYEAR_OVER = "1";
    private final String MINUTES = "2";
    private final String MANAGEMENT_COST_CODE = "1";
    private final String REPAIR_COST_CODE = "2";
    private final Integer REPAIR_MONTHLY_COST = 465413216;
    private final String REPAIR_PLAN_CODE = "1";
    private final String REPAIR_NEAREST_YEAR = "2020";
    private final String LONG_REPAIR_PLAN_CODE = null;
    private final String LONG_REPAIR_PLAN_YEAR = "0";
    private final Integer LONG_REPAIR_PLAN_PERIOD = 0;
    private final String LONG_REPAIR_PLAN_YEAR_FROM = "0";
    private final String LONG_REPAIR_PLAN_YEAR_TO = "0";
    private final String ARREARAGE_RULE_CODE = null;
    private final String SEGMENT_CODE = null;
    private final String EMPTY_PERCENT_CODE = null;
    private final Integer EMPTY_NUMBER = 0;
    private final String RENTAL_PERCENT_CODE = null;
    private final Integer RENTAL_NUMBER = 0;
    private final String SEISMIC_DIAGNOSIS_CODE = null;
    private final String EARTHQUAKE_RESISTANCE_CODE = null;
    private final String SEISMIC_RETROFIT_CODE = null;
    private final String DESIGN_DOCUMENT_CODE = null;
    private final String REPAIR_HISTORY_CODE = null;
    private final String VOLUNTARY_ORGANIZATION_CODE = null;
    private final String DISASTER_PREVENTION_MANUAL_CODE = null;
    private final String DISASTER_PREVENTION_STOCKPILE_CODE = null;
    private final String NEED_SUPPORT_LIST_CODE = null;
    private final String DISASTER_PREVENTION_REGULAR_CODE = null;
    private final String SLOPE_CODE = null;
    private final String RAILING_CODE = null;
    private final String ELEVATOR_CODE = null;
    private final String LED_CODE = null;
    private final String HEAT_SHIELDING_CODE = null;
    private final String EQUIPMENT_CHARGE_CODE = null;
    private final String COMMUNITY_CODE = null;
    private final String CONTACT_PROPERTY_CODE = null;
    private final String CONTACT_PROPERTY_ELSE = "2";
    private final String CONTACT_ZIP_CODE = "132-041";
    private final String CONTACT_ADDRESS = "Tokyo";
    private final String CONTACT_TEL_NO = "098-098-8011";
    private final String CONTACT_NAME = "0";
    private final String CONTACT_NAME_PHONETIC = "0";
    private final String CONTACT_MAIL_ADDRESS = "adf";
    private final String OPTIONAL = "コンニチハ";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "1000000018";
	private final LocalDateTime UPDATE_DATETIME = LocalDateTime.parse("2019-12-24 16:05:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	private final LocalDateTime ACCEPT_TIME = LocalDateTime.parse("2019-10-10 10:10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	private final String ACCEPT_USER_NAME = "QQ";
	private final String REMARKS = "KSKSS";
	private final String CITY_NAME = "東京都";
	private final String CITY_CODE_2 = "130001";
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-002-01/区分:N/チェック内容:Test Get NotificationNo Information Success
	 */
	@Test
	@Sql(value = "classpath:/sql/UT-GFA0110-002.sql")
	public void getNotificationInfo_WhenExist_ShouldBeExist() {
		// Execute
		TBL200Entity actual = tbl200DAO.getNotificationInfo(NOTIFICATION_NO);

		// Check result
		assertEntity(actual);
	}

	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-002-02/区分:E/チェック内容:Test Get NotificationNo Information Return Null When Not Exist
	 */
	@Test
	public void getNotificationInfo_WhenNotExist_ShouldBeNull() {
		// Execute
		TBL200Entity actual = tbl200DAO.getNotificationInfo(null);
		// Check result
		assertThat(actual).isEqualTo(null);
	}
	
	/**
	 * 案件ID:GFA0110/チェックリストID:UT- GFA0110-002-03/区分:N/チェック内容:Test save success
	 */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL200Entity expected = generateTBL200Entity();

        // Execute
        TBL200Entity actual = tbl200DAO.save(expected);

        // Check result
        assertEntity(actual);
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-002-04/区分:E/チェック内容:Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl200DAO.save(null);
    }

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-001/区分:E/チェック内容:Test get notification by notificationNo and ApartmentID from tbl200 when success
	 */
	@Test
	@Sql(value = "classpath:/sql/UT-GFA0110-002.sql")
	public void getNotification_WhenExist_ShouldBeExist() {
		// Execute
		TBL200Entity actual = tbl200DAO.getNotificationByNotificationNoAndApartmentID(APARTMENT_ID, NOTIFICATION_NO);

		// Check result
		assertEntity(actual);
	}

	/**
	 * 案件ID:GJA0120/チェックリストID:UT- GJA0120-002/区分:E/チェック内容:Test get notification by notificationNo and ApartmentID from tbl200 when null
	 */
	@Test
	public void getNotification_WhenNotExist_ShouldBeNull() {
		// Execute
		TBL200Entity actual = tbl200DAO.getNotificationByNotificationNoAndApartmentID(null, null);
		// Check result
		assertThat(actual).isEqualTo(null);
	}

     /**
     * 案件ID:GEA0110/チェックリストID:UT- GEA0110-032-/区分:E/チェック内容:Test Document InfoForm When Not Exist  Should Be Null
     */
	@Test
	public void getDocumentInfoForm_WhenNotExist_ShouldBeNull() {
	    // Execute
	    RP010TmpVo pr010TmpVo = tbl200DAO.getDocumentInfoForm(null);
	    // Check result
	    assertThat(pr010TmpVo).isNull();
	}

    /**
    * 案件ID:GEA0110/チェックリストID:UT- GEA0110-033-/区分:E/チェック内容: Test Document InfoForm When Exist Should Be Exist
    */
	@Test
	@Sql(value = "classpath:/sql/UT-GEA0110-001.sql")
	public void getDocumentInfoForm_WhenExist_ShouldBeExist() {
	    // Execute
	    RP010TmpVo rp010TmpVo = tbl200DAO.getDocumentInfoForm(NOTIFICATION_NO);
	    // Check result
	    assertThat(rp010TmpVo).isNotNull();
	    assertRp010TmpVo(rp010TmpVo);
	}

    private void assertRp010TmpVo(RP010TmpVo entity) {
        assertThat(entity.getTbl200Entity().getId().getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getTbl200Entity().getId().getNotificationNo()).isEqualTo(NOTIFICATION_NO);
        assertThat(entity.getTbl200Entity().getReceptionNo()).isEqualTo(RECEPTION_NO);
        assertThat(entity.getTbl200Entity().getAcceptStatus()).isEqualTo(ACCEPT_STATUS);
        assertThat(entity.getTbl200Entity().getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getTbl200Entity().getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(entity.getTbl200Entity().getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(entity.getTbl200Entity().getCityCode()).isEqualTo(CITY_CODE_2);
        assertThat(entity.getTbl200Entity().getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getTbl200Entity().getNotificationType()).isEqualTo(NOTIFICATION_TYPE);
        assertThat(entity.getTbl200Entity().getNotificationDate()).isEqualTo(NOTIFICATION_DATE);
        assertThat(entity.getTbl200Entity().getNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
        assertThat(entity.getTbl200Entity().getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
        assertThat(entity.getTbl200Entity().getTempKbn()).isEqualTo(TEMP_KBN);
        assertThat(entity.getTbl200Entity().getAdviceDoneFlag()).isEqualTo(ADVICE_DONE_FLAG);
        assertThat(entity.getTbl200Entity().getChangeCount()).isEqualTo(CHANGE_COUNT);
        assertThat(entity.getTbl200Entity().getNotificationCount()).isEqualTo(NOTIFICATION_COUNT);
        assertThat(entity.getTbl200Entity().getChangeReasonCode()).isEqualTo(CHANGE_REASON_CODE);
        assertThat(entity.getTbl200Entity().getLostElseReasonCode()).isEqualTo(LOST_ELSE_REASON_CODE);
        assertThat(entity.getTbl200Entity().getLostElseReasonElse()).isEqualTo(LOST_ELSE_REASON_ELSE);
        assertThat(entity.getTbl200Entity().getGroupYesnoCode()).isEqualTo(GROUP_YESNO_CODE);
        assertThat(entity.getTbl200Entity().getApartmentNumber()).isEqualTo(APARTMENT_NUMBER);
        assertThat(entity.getTbl200Entity().getGroupForm()).isEqualTo(GROUP_FORM);
        assertThat(entity.getTbl200Entity().getGroupFormElse()).isEqualTo(GROUP_FORM_ELSE);
        assertThat(entity.getTbl200Entity().getHouseNumber()).isEqualTo(HOUSE_NUMBER);
        assertThat(entity.getTbl200Entity().getFloorNumber()).isEqualTo(FLOOR_NUMBER);
        assertThat(entity.getTbl200Entity().getBuiltDate()).isEqualTo(BUILT_DATE);
        assertThat(entity.getTbl200Entity().getLandRightsCode()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(entity.getTbl200Entity().getLandRightsElse()).isEqualTo(LAND_RIGHTS_ELSE);
        assertThat(entity.getTbl200Entity().getUseforCode()).isEqualTo(USEFOR_CODE);
        assertThat(entity.getTbl200Entity().getUseforElse()).isEqualTo(USEFOR_ELSE);
        assertThat(entity.getTbl200Entity().getManagementFormCode()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(entity.getTbl200Entity().getManagementFormElse()).isEqualTo(MANAGEMENT_FORM_ELSE);
        assertThat(entity.getTbl200Entity().getManagerName()).isEqualTo(MANAGER_NAME);
        assertThat(entity.getTbl200Entity().getManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
        assertThat(entity.getTbl200Entity().getManagerZipCode()).isEqualTo(MANAGER_ZIP_CODE);
        assertThat(entity.getTbl200Entity().getManagerAddress()).isEqualTo(MANAGER_ADDRESS);
        assertThat(entity.getTbl200Entity().getManagerTelNo()).isEqualTo(MANAGER_TEL_NO);
        assertThat(entity.getTbl200Entity().getGroupCode()).isEqualTo(GROUP_CODE);
        assertThat(entity.getTbl200Entity().getManagerCode()).isEqualTo(MANAGER_CODE);
        assertThat(entity.getTbl200Entity().getRuleCode()).isEqualTo(RULE_CODE);
        assertThat(entity.getTbl200Entity().getRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
        assertThat(entity.getTbl200Entity().getOpenOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
        assertThat(entity.getTbl200Entity().getMinutes()).isEqualTo(MINUTES);
        assertThat(entity.getTbl200Entity().getManagementCostCode()).isEqualTo(MANAGEMENT_COST_CODE);
        assertThat(entity.getTbl200Entity().getRepairCostCode()).isEqualTo(REPAIR_COST_CODE);
        assertThat(entity.getTbl200Entity().getRepairMonthlyCost()).isEqualTo(REPAIR_MONTHLY_COST);
        assertThat(entity.getTbl200Entity().getRepairPlanCode()).isEqualTo(REPAIR_PLAN_CODE);
        assertThat(entity.getTbl200Entity().getRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
        assertThat(entity.getTbl200Entity().getLongRepairPlanCode()).isEqualTo(LONG_REPAIR_PLAN_CODE);
        assertThat(entity.getTbl200Entity().getLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
        assertThat(entity.getTbl200Entity().getLongRepairPlanPeriod()).isEqualTo(LONG_REPAIR_PLAN_PERIOD);
        assertThat(entity.getTbl200Entity().getLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
        assertThat(entity.getTbl200Entity().getLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
        assertThat(entity.getTbl200Entity().getArrearageRuleCode()).isEqualTo(ARREARAGE_RULE_CODE);
        assertThat(entity.getTbl200Entity().getSegmentCode()).isEqualTo(SEGMENT_CODE);
        assertThat(entity.getTbl200Entity().getEmptyPercentCode()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(entity.getTbl200Entity().getEmptyNumber()).isEqualTo(EMPTY_NUMBER);
        assertThat(entity.getTbl200Entity().getRentalPercentCode()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(entity.getTbl200Entity().getRentalNumber()).isEqualTo(RENTAL_NUMBER);
        assertThat(entity.getTbl200Entity().getSeismicDiagnosisCode()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
        assertThat(entity.getTbl200Entity().getEarthquakeResistanceCode()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
        assertThat(entity.getTbl200Entity().getSeismicRetrofitCode()).isEqualTo(SEISMIC_RETROFIT_CODE);
        assertThat(entity.getTbl200Entity().getDesignDocumentCode()).isEqualTo(DESIGN_DOCUMENT_CODE);
        assertThat(entity.getTbl200Entity().getRepairHistoryCode()).isEqualTo(REPAIR_HISTORY_CODE);
        assertThat(entity.getTbl200Entity().getVoluntaryOrganizationCode()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
        assertThat(entity.getTbl200Entity().getDisasterPreventionManualCode()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
        assertThat(entity.getTbl200Entity().getDisasterPreventionStockpileCode()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
        assertThat(entity.getTbl200Entity().getNeedSupportListCode()).isEqualTo(NEED_SUPPORT_LIST_CODE);
        assertThat(entity.getTbl200Entity().getDisasterPreventionRegularCode()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
        assertThat(entity.getTbl200Entity().getSlopeCode()).isEqualTo(SLOPE_CODE);
        assertThat(entity.getTbl200Entity().getRailingCode()).isEqualTo(RAILING_CODE);
        assertThat(entity.getTbl200Entity().getElevatorCode()).isEqualTo(ELEVATOR_CODE);
        assertThat(entity.getTbl200Entity().getLedCode()).isEqualTo(LED_CODE);
        assertThat(entity.getTbl200Entity().getHeatShieldingCode()).isEqualTo(HEAT_SHIELDING_CODE);
        assertThat(entity.getTbl200Entity().getEquipmentChargeCode()).isEqualTo(EQUIPMENT_CHARGE_CODE);
        assertThat(entity.getTbl200Entity().getCommunityCode()).isEqualTo(COMMUNITY_CODE);
        assertThat(entity.getTbl200Entity().getContactPropertyCode()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(entity.getTbl200Entity().getContactPropertyElse()).isEqualTo(CONTACT_PROPERTY_ELSE);
        assertThat(entity.getTbl200Entity().getContactZipCode()).isEqualTo(CONTACT_ZIP_CODE);
        assertThat(entity.getTbl200Entity().getContactAddress()).isEqualTo(CONTACT_ADDRESS);
        assertThat(entity.getTbl200Entity().getContactTelNo()).isEqualTo(CONTACT_TEL_NO);
        assertThat(entity.getTbl200Entity().getContactName()).isEqualTo(CONTACT_NAME);
        assertThat(entity.getTbl200Entity().getContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
        assertThat(entity.getTbl200Entity().getContactMailAddress()).isEqualTo(CONTACT_MAIL_ADDRESS);
        assertThat(entity.getTbl200Entity().getOptional()).isEqualTo(OPTIONAL);
        assertThat(entity.getTbl200Entity().getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getTbl200Entity().getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getTbl200Entity().getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getAcceptTime()).isEqualTo(ACCEPT_TIME);
        assertThat(entity.getReceiptPersonInCharge()).isEqualTo(ACCEPT_USER_NAME);
        assertThat(entity.getReceiptNote()).isEqualTo(REMARKS);
        assertThat(entity.getCityName()).isEqualTo(CITY_NAME);
    }

	private void assertEntity(TBL200Entity entity) {
	    assertThat(entity.getId().getApartmentId()).isEqualTo(APARTMENT_ID);
	    assertThat(entity.getId().getNotificationNo()).isEqualTo(NOTIFICATION_NO);
	    assertThat(entity.getReceptionNo()).isEqualTo(RECEPTION_NO);
	    assertThat(entity.getAcceptStatus()).isEqualTo(ACCEPT_STATUS);
	    assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
	    assertThat(entity.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
	    assertThat(entity.getZipCode()).isEqualTo(ZIP_CODE);
	    assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
	    assertThat(entity.getAddress()).isEqualTo(ADDRESS);
	    assertThat(entity.getNotificationType()).isEqualTo(NOTIFICATION_TYPE);
	    assertThat(entity.getNotificationDate()).isEqualTo(NOTIFICATION_DATE);
	    assertThat(entity.getNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
	    assertThat(entity.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
	    assertThat(entity.getTempKbn()).isEqualTo(TEMP_KBN);
	    assertThat(entity.getAdviceDoneFlag()).isEqualTo(ADVICE_DONE_FLAG);
	    assertThat(entity.getChangeCount()).isEqualTo(CHANGE_COUNT);
	    assertThat(entity.getNotificationCount()).isEqualTo(NOTIFICATION_COUNT);
	    assertThat(entity.getChangeReasonCode()).isEqualTo(CHANGE_REASON_CODE);
	    assertThat(entity.getLostElseReasonCode()).isEqualTo(LOST_ELSE_REASON_CODE);
	    assertThat(entity.getLostElseReasonElse()).isEqualTo(LOST_ELSE_REASON_ELSE);
	    assertThat(entity.getGroupYesnoCode()).isEqualTo(GROUP_YESNO_CODE);
	    assertThat(entity.getApartmentNumber()).isEqualTo(APARTMENT_NUMBER);
	    assertThat(entity.getGroupForm()).isEqualTo(GROUP_FORM);
	    assertThat(entity.getGroupFormElse()).isEqualTo(GROUP_FORM_ELSE);
	    assertThat(entity.getHouseNumber()).isEqualTo(HOUSE_NUMBER);
	    assertThat(entity.getFloorNumber()).isEqualTo(FLOOR_NUMBER);
	    assertThat(entity.getBuiltDate()).isEqualTo(BUILT_DATE);
	    assertThat(entity.getLandRightsCode()).isEqualTo(LAND_RIGHTS_CODE);
	    assertThat(entity.getLandRightsElse()).isEqualTo(LAND_RIGHTS_ELSE);
	    assertThat(entity.getUseforCode()).isEqualTo(USEFOR_CODE);
	    assertThat(entity.getUseforElse()).isEqualTo(USEFOR_ELSE);
	    assertThat(entity.getManagementFormCode()).isEqualTo(MANAGEMENT_FORM_CODE);
	    assertThat(entity.getManagementFormElse()).isEqualTo(MANAGEMENT_FORM_ELSE);
	    assertThat(entity.getManagerName()).isEqualTo(MANAGER_NAME);
	    assertThat(entity.getManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
	    assertThat(entity.getManagerZipCode()).isEqualTo(MANAGER_ZIP_CODE);
	    assertThat(entity.getManagerAddress()).isEqualTo(MANAGER_ADDRESS);
	    assertThat(entity.getManagerTelNo()).isEqualTo(MANAGER_TEL_NO);
	    assertThat(entity.getGroupCode()).isEqualTo(GROUP_CODE);
	    assertThat(entity.getManagerCode()).isEqualTo(MANAGER_CODE);
	    assertThat(entity.getRuleCode()).isEqualTo(RULE_CODE);
	    assertThat(entity.getRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
	    assertThat(entity.getOpenOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
	    assertThat(entity.getMinutes()).isEqualTo(MINUTES);
	    assertThat(entity.getManagementCostCode()).isEqualTo(MANAGEMENT_COST_CODE);
	    assertThat(entity.getRepairCostCode()).isEqualTo(REPAIR_COST_CODE);
	    assertThat(entity.getRepairMonthlyCost()).isEqualTo(REPAIR_MONTHLY_COST);
	    assertThat(entity.getRepairPlanCode()).isEqualTo(REPAIR_PLAN_CODE);
	    assertThat(entity.getRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
	    assertThat(entity.getLongRepairPlanCode()).isEqualTo(LONG_REPAIR_PLAN_CODE);
	    assertThat(entity.getLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
	    assertThat(entity.getLongRepairPlanPeriod()).isEqualTo(LONG_REPAIR_PLAN_PERIOD);
	    assertThat(entity.getLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
	    assertThat(entity.getLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
	    assertThat(entity.getArrearageRuleCode()).isEqualTo(ARREARAGE_RULE_CODE);
	    assertThat(entity.getSegmentCode()).isEqualTo(SEGMENT_CODE);
	    assertThat(entity.getEmptyPercentCode()).isEqualTo(EMPTY_PERCENT_CODE);
	    assertThat(entity.getEmptyNumber()).isEqualTo(EMPTY_NUMBER);
	    assertThat(entity.getRentalPercentCode()).isEqualTo(RENTAL_PERCENT_CODE);
	    assertThat(entity.getRentalNumber()).isEqualTo(RENTAL_NUMBER);
	    assertThat(entity.getSeismicDiagnosisCode()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
	    assertThat(entity.getEarthquakeResistanceCode()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
	    assertThat(entity.getSeismicRetrofitCode()).isEqualTo(SEISMIC_RETROFIT_CODE);
	    assertThat(entity.getDesignDocumentCode()).isEqualTo(DESIGN_DOCUMENT_CODE);
	    assertThat(entity.getRepairHistoryCode()).isEqualTo(REPAIR_HISTORY_CODE);
	    assertThat(entity.getVoluntaryOrganizationCode()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
	    assertThat(entity.getDisasterPreventionManualCode()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
	    assertThat(entity.getDisasterPreventionStockpileCode()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
	    assertThat(entity.getNeedSupportListCode()).isEqualTo(NEED_SUPPORT_LIST_CODE);
	    assertThat(entity.getDisasterPreventionRegularCode()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
	    assertThat(entity.getSlopeCode()).isEqualTo(SLOPE_CODE);
	    assertThat(entity.getRailingCode()).isEqualTo(RAILING_CODE);
	    assertThat(entity.getElevatorCode()).isEqualTo(ELEVATOR_CODE);
	    assertThat(entity.getLedCode()).isEqualTo(LED_CODE);
	    assertThat(entity.getHeatShieldingCode()).isEqualTo(HEAT_SHIELDING_CODE);
	    assertThat(entity.getEquipmentChargeCode()).isEqualTo(EQUIPMENT_CHARGE_CODE);
	    assertThat(entity.getCommunityCode()).isEqualTo(COMMUNITY_CODE);
	    assertThat(entity.getContactPropertyCode()).isEqualTo(CONTACT_PROPERTY_CODE);
	    assertThat(entity.getContactPropertyElse()).isEqualTo(CONTACT_PROPERTY_ELSE);
	    assertThat(entity.getContactZipCode()).isEqualTo(CONTACT_ZIP_CODE);
	    assertThat(entity.getContactAddress()).isEqualTo(CONTACT_ADDRESS);
	    assertThat(entity.getContactTelNo()).isEqualTo(CONTACT_TEL_NO);
	    assertThat(entity.getContactName()).isEqualTo(CONTACT_NAME);
	    assertThat(entity.getContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
	    assertThat(entity.getContactMailAddress()).isEqualTo(CONTACT_MAIL_ADDRESS);
	    assertThat(entity.getOptional()).isEqualTo(OPTIONAL);
	    assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
	    assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
	    assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
	}
	
	private TBL200Entity generateTBL200Entity() {

		TBL200Entity entity = new TBL200Entity();

		TBL200EntityPK entityPK = new TBL200EntityPK();
		entityPK.setApartmentId(APARTMENT_ID);
		entityPK.setNotificationNo(NOTIFICATION_NO);

		entity.setId(entityPK);
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

}
