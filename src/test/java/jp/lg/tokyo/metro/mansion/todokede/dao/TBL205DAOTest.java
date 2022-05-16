/*
 * @(#) NotificationInfoLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2020/01/04
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205EntityPK;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_0;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_1;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL205DAOTest {

    @Autowired
    private TBL205DAO tbl205DAO;

    private final String TEMP_NO = "1000000001";
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
    private final String TEMP_KBN = "3";
    private final String ADVICE_DONE_FLAG = "2";
    private final int CHANGE_COUNT = 0;
    private final int NOTIFICATION_COUNT = 0;
    private final String CHANGE_REASON_CODE = "1";
    private final String LOST_ELSE_REASON_CODE = "9";
    private final String LOST_ELSE_REASON_ELSE = "reason test";
    private final String GROUP_YESNO_CODE = "1";
    private final int APARTMENT_NUMBER = 123;
    private final String GROUP_FORM = "1";
    private final String GROUP_FORM_ELSE = null;
    private final int HOUSE_NUMBER = 12313;
    private final int FLOOR_NUMBER = 12;
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
    private final int REPAIR_MONTHLY_COST = 465413216;
    private final String REPAIR_PLAN_CODE = "1";
    private final String REPAIR_NEAREST_YEAR = "2020";
    private final String LONG_REPAIR_PLAN_CODE = null;
    private final String LONG_REPAIR_PLAN_YEAR = "0";
    private final int LONG_REPAIR_PLAN_PERIOD = 0;
    private final String LONG_REPAIR_PLAN_YEAR_FROM = "0";
    private final String LONG_REPAIR_PLAN_YEAR_TO = "0";
    private final String ARREARAGE_RULE_CODE = null;
    private final String SEGMENT_CODE = null;
    private final String EMPTY_PERCENT_CODE = null;
    private final int EMPTY_NUMBER = 0;
    private final String RENTAL_PERCENT_CODE = null;
    private final int RENTAL_NUMBER = 0;
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
    private final Timestamp UPDATE_DATETIME = new Timestamp(1578133844000L); //2020-01-04 10:30:44

	/**
	 * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-01／区分：E／チェック内容：Test save fail
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void save_WhenFail_ShouldBeThrowException() {
		tbl205DAO.save(null);
	}

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-02／区分：N／チェック内容：Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreatedOrUpdated() {
        // Prepare data
        TBL205Entity expected = generateTBL205Entity();

        // Execute
        TBL205Entity actual = tbl205DAO.save(expected);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-03／区分：I／チェック内容：Test Get Temporary Data When Exist
     */
    @Test()
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void getTemporaryData_WhenExist_ShouldBeExist() {
        // Execute
        TBL205Entity actual = tbl205DAO.getTemporaryData(APARTMENT_ID, ACCEPT_STATUS, TEMP_KBN, null);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-04／区分：N／チェック内容：Test Get Temporary Data When Not Exist
     */
    @Test()
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void getTemporaryData_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL205Entity actual = tbl205DAO.getTemporaryData(APARTMENT_ID, ACCEPT_STATUS, null, null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-05／区分：N／チェック内容：Test Count By ApartmentId And Status When Exist
     */
    @Test()
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void countByApartmentIdAndStatus_WhenExist_ShouldBeCountNumber() {
        // Execute
        int actual = tbl205DAO.countByApartmentIdAndStatus(APARTMENT_ID, ACCEPT_STATUS);

        // Check result
        assertThat(actual).isEqualTo(NUM_1);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-06／区分：N／チェック内容：Test Count By ApartmentId And Status When Not Exist
     */
    @Test()
    public void countByApartmentIdAndStatus_WhenNotExist_ShouldBeZero() {
        // Execute
        int actual = tbl205DAO.countByApartmentIdAndStatus(APARTMENT_ID, ACCEPT_STATUS);

        // Check result
        assertThat(actual).isEqualTo(NUM_0);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-07／区分：N／チェック内容：Test Count By ApartmentId And Status And TempKbn When Exist
     */
    @Test()
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void countByApartmentIdAndStatusAndTempKbn_WhenNotExist_ShouldBeCountNumber() {
        // Execute
        int actual = tbl205DAO.countByApartmentIdAndStatusAndTempKbn(APARTMENT_ID, ACCEPT_STATUS, TEMP_KBN);

        // Check result
        assertThat(actual).isEqualTo(NUM_1);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-08／区分：N／チェック内容：Test Count By ApartmentId And Status And TempKbn When Not Exist
     */
    @Test()
    public void countByApartmentIdAndStatusAndTempKbn_WhenNotExist_ShouldBeZero() {
        // Execute
        int actual = tbl205DAO.countByApartmentIdAndStatusAndTempKbn(APARTMENT_ID, ACCEPT_STATUS, TEMP_KBN);

        // Check result
        assertThat(actual).isEqualTo(NUM_0);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-09／区分：N／チェック内容：Test Delete By ApartmentId And Status When Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void deleteByApartmentIdAndStatus_WhenDataExist_CanDeleteSuccessfully() {
        List<TBL205Entity> entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_1);

        // Execute
        tbl205DAO.deleteByApartmentIdAndStatus(APARTMENT_ID, ACCEPT_STATUS);

        entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-10／区分：N／チェック内容：Test Delete By ApartmentId And Status When Cannot Delete
     */
    @Test
    public void deleteByApartmentIdAndStatus_WhenDataNotExist_CannotDelete() {
        List<TBL205Entity> entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);

        // Execute
        tbl205DAO.deleteByApartmentIdAndStatus(APARTMENT_ID, ACCEPT_STATUS);

        entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-11／区分：N／チェック内容：Test Delete By ApartmentId And Status And TempKbn When Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MDA0110-002.sql")
    public void deleteByApartmentIdAndStatusAndTempKbn_WhenDataExist_CanDeleteSuccessfully() {
        List<TBL205Entity> entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_1);

        // Execute
        tbl205DAO.deleteByApartmentIdAndStatusAndTempKbn(APARTMENT_ID, ACCEPT_STATUS, TEMP_KBN);

        entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：UT- MDA0110-002-12／区分：N／チェック内容：Test Delete By ApartmentId And Status And TempKbn When Cannot Delete
     */
    @Test
    public void deleteByApartmentIdAndStatusAndTempKbn_WhenDataNotExist_CannotDelete() {
        List<TBL205Entity> entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);

        // Execute
        tbl205DAO.deleteByApartmentIdAndStatusAndTempKbn(APARTMENT_ID, ACCEPT_STATUS, TEMP_KBN);

        entities = tbl205DAO.findAll();
        assertThat(entities.size()).isEqualTo(NUM_0);
    }

    private TBL205Entity generateTBL205Entity() {
        TBL205Entity entity = new TBL205Entity();

        entity.setId(new TBL205EntityPK(TEMP_NO, APARTMENT_ID));
        entity.setAcceptStatus(ACCEPT_STATUS);
        entity.setAddress(ADDRESS);
        entity.setAdviceDoneFlag(ADVICE_DONE_FLAG);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setApartmentNumber(APARTMENT_NUMBER);
        entity.setBuiltDate(BUILT_DATE);
        entity.setChangeCount(CHANGE_COUNT);
        entity.setChangeReasonCode(CHANGE_REASON_CODE);
        entity.setCityCode(CITY_CODE);
        entity.setCommunityCode(COMMUNITY_CODE);
        entity.setContactAddress(CONTACT_ADDRESS);
        entity.setContactMailAddress(CONTACT_MAIL_ADDRESS);
        entity.setContactName(CONTACT_NAME);
        entity.setContactNamePhonetic(CONTACT_NAME_PHONETIC);
        entity.setContactPropertyCode(CONTACT_PROPERTY_CODE);
        entity.setContactPropertyElse(CONTACT_PROPERTY_ELSE);
        entity.setContactTelNo(CONTACT_TEL_NO);
        entity.setContactZipCode(CONTACT_ZIP_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setDesignDocumentCode(DESIGN_DOCUMENT_CODE);
        entity.setDisasterPreventionManualCode(DISASTER_PREVENTION_MANUAL_CODE);
        entity.setDisasterPreventionRegularCode(DISASTER_PREVENTION_REGULAR_CODE);
        entity.setDisasterPreventionStockpileCode(DISASTER_PREVENTION_STOCKPILE_CODE);
        entity.setEarthquakeResistanceCode(EARTHQUAKE_RESISTANCE_CODE);
        entity.setElevatorCode(ELEVATOR_CODE);
        entity.setEmptyNumber(EMPTY_NUMBER);
        entity.setEmptyPercentCode(EMPTY_PERCENT_CODE);
        entity.setEquipmentChargeCode(EQUIPMENT_CHARGE_CODE);
        entity.setFloorNumber(FLOOR_NUMBER);
        entity.setGroupCode(GROUP_CODE);
        entity.setGroupForm(GROUP_FORM);
        entity.setGroupFormElse(GROUP_FORM_ELSE);
        entity.setGroupYesnoCode(GROUP_YESNO_CODE);
        entity.setHeatShieldingCode(HEAT_SHIELDING_CODE);
        entity.setHouseNumber(HOUSE_NUMBER);
        entity.setLandRightsCode(LAND_RIGHTS_CODE);
        entity.setLandRightsElse(LAND_RIGHTS_ELSE);
        entity.setLedCode(LED_CODE);
        entity.setLongRepairPlanCode(LONG_REPAIR_PLAN_CODE);
        entity.setLongRepairPlanPeriod(LONG_REPAIR_PLAN_PERIOD);
        entity.setLongRepairPlanYear(LONG_REPAIR_PLAN_YEAR);
        entity.setLongRepairPlanYearFrom(LONG_REPAIR_PLAN_YEAR_FROM);
        entity.setLongRepairPlanYearTo(LONG_REPAIR_PLAN_YEAR_TO);
        entity.setLostElseReasonCode(LOST_ELSE_REASON_CODE);
        entity.setLostElseReasonElse(LOST_ELSE_REASON_ELSE);
        entity.setManagementCostCode(MANAGEMENT_COST_CODE);
        entity.setManagementFormCode(MANAGEMENT_FORM_CODE);
        entity.setManagementFormElse(MANAGEMENT_FORM_ELSE);
        entity.setManagerAddress(MANAGER_ADDRESS);
        entity.setManagerCode(MANAGER_CODE);
        entity.setManagerName(MANAGER_NAME);
        entity.setManagerNamePhonetic(MANAGER_NAME_PHONETIC);
        entity.setManagerTelNo(MANAGER_TEL_NO);
        entity.setManagerZipCode(MANAGER_ZIP_CODE);
        entity.setMinutes(MINUTES);
        entity.setNeedSupportListCode(NEED_SUPPORT_LIST_CODE);
        entity.setNotificationCount(NOTIFICATION_COUNT);
        entity.setNotificationDate(NOTIFICATION_DATE);
        entity.setNotificationGroupName(NOTIFICATION_GROUP_NAME);
        entity.setNotificationPersonName(NOTIFICATION_PERSON_NAME);
        entity.setNotificationType(NOTIFICATION_TYPE);
        entity.setOpenOneyearOver(OPEN_ONEYEAR_OVER);
        entity.setOptional(OPTIONAL);
        entity.setRailingCode(RAILING_CODE);
        entity.setReceptionNo(RECEPTION_NO);
        entity.setRentalNumber(RENTAL_NUMBER);
        entity.setRentalPercentCode(RENTAL_PERCENT_CODE);
        entity.setRepairCostCode(REPAIR_COST_CODE);
        entity.setRepairHistoryCode(REPAIR_HISTORY_CODE);
        entity.setRepairMonthlyCost(REPAIR_MONTHLY_COST);
        entity.setRepairNearestYear(REPAIR_NEAREST_YEAR);
        entity.setRepairPlanCode(REPAIR_PLAN_CODE);
        entity.setRuleChangeYear(RULE_CHANGE_YEAR);
        entity.setRuleCode(RULE_CODE);
        entity.setSegmentCode(SEGMENT_CODE);
        entity.setSeismicDiagnosisCode(SEISMIC_DIAGNOSIS_CODE);
        entity.setSeismicRetrofitCode(SEISMIC_RETROFIT_CODE);
        entity.setSlopeCode(SLOPE_CODE);
        entity.setTempKbn(TEMP_KBN);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUseforCode(USEFOR_CODE);
        entity.setUseforElse(USEFOR_ELSE);
        entity.setVoluntaryOrganizationCode(VOLUNTARY_ORGANIZATION_CODE);
        entity.setZipCode(ZIP_CODE);
        entity.setArrearageRuleCode(ARREARAGE_RULE_CODE);

        return entity;
    }

    private void assertEntity(TBL205Entity actual) {
        assertThat(actual.getId().getTempNo()).isEqualTo(TEMP_NO);
        assertThat(actual.getId().getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(actual.getAcceptStatus()).isEqualTo(ACCEPT_STATUS);
        assertThat(actual.getAddress()).isEqualTo(ADDRESS);
        assertThat(actual.getAdviceDoneFlag()).isEqualTo(ADVICE_DONE_FLAG);
        assertThat(actual.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(actual.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(actual.getApartmentNumber()).isEqualTo(APARTMENT_NUMBER);
        assertThat(actual.getBuiltDate()).isEqualTo(BUILT_DATE);
        assertThat(actual.getChangeCount()).isEqualTo(CHANGE_COUNT);
        assertThat(actual.getChangeReasonCode()).isEqualTo(CHANGE_REASON_CODE);
        assertThat(actual.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(actual.getCommunityCode()).isEqualTo(COMMUNITY_CODE);
        assertThat(actual.getContactAddress()).isEqualTo(CONTACT_ADDRESS);
        assertThat(actual.getContactMailAddress()).isEqualTo(CONTACT_MAIL_ADDRESS);
        assertThat(actual.getContactName()).isEqualTo(CONTACT_NAME);
        assertThat(actual.getContactNamePhonetic()).isEqualTo(CONTACT_NAME_PHONETIC);
        assertThat(actual.getContactPropertyCode()).isEqualTo(CONTACT_PROPERTY_CODE);
        assertThat(actual.getContactPropertyElse()).isEqualTo(CONTACT_PROPERTY_ELSE);
        assertThat(actual.getContactTelNo()).isEqualTo(CONTACT_TEL_NO);
        assertThat(actual.getContactZipCode()).isEqualTo(CONTACT_ZIP_CODE);
        assertThat(actual.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(actual.getDesignDocumentCode()).isEqualTo(DESIGN_DOCUMENT_CODE);
        assertThat(actual.getDisasterPreventionManualCode()).isEqualTo(DISASTER_PREVENTION_MANUAL_CODE);
        assertThat(actual.getDisasterPreventionRegularCode()).isEqualTo(DISASTER_PREVENTION_REGULAR_CODE);
        assertThat(actual.getDisasterPreventionStockpileCode()).isEqualTo(DISASTER_PREVENTION_STOCKPILE_CODE);
        assertThat(actual.getEarthquakeResistanceCode()).isEqualTo(EARTHQUAKE_RESISTANCE_CODE);
        assertThat(actual.getElevatorCode()).isEqualTo(ELEVATOR_CODE);
        assertThat(actual.getEmptyNumber()).isEqualTo(EMPTY_NUMBER);
        assertThat(actual.getEmptyPercentCode()).isEqualTo(EMPTY_PERCENT_CODE);
        assertThat(actual.getEquipmentChargeCode()).isEqualTo(EQUIPMENT_CHARGE_CODE);
        assertThat(actual.getFloorNumber()).isEqualTo(FLOOR_NUMBER);
        assertThat(actual.getGroupCode()).isEqualTo(GROUP_CODE);
        assertThat(actual.getGroupForm()).isEqualTo(GROUP_FORM);
        assertThat(actual.getGroupFormElse()).isEqualTo(GROUP_FORM_ELSE);
        assertThat(actual.getGroupYesnoCode()).isEqualTo(GROUP_YESNO_CODE);
        assertThat(actual.getHeatShieldingCode()).isEqualTo(HEAT_SHIELDING_CODE);
        assertThat(actual.getHouseNumber()).isEqualTo(HOUSE_NUMBER);
        assertThat(actual.getLandRightsCode()).isEqualTo(LAND_RIGHTS_CODE);
        assertThat(actual.getLandRightsElse()).isEqualTo(LAND_RIGHTS_ELSE);
        assertThat(actual.getLedCode()).isEqualTo(LED_CODE);
        assertThat(actual.getLongRepairPlanCode()).isEqualTo(LONG_REPAIR_PLAN_CODE);
        assertThat(actual.getLongRepairPlanPeriod()).isEqualTo(LONG_REPAIR_PLAN_PERIOD);
        assertThat(actual.getLongRepairPlanYear()).isEqualTo(LONG_REPAIR_PLAN_YEAR);
        assertThat(actual.getLongRepairPlanYearFrom()).isEqualTo(LONG_REPAIR_PLAN_YEAR_FROM);
        assertThat(actual.getLongRepairPlanYearTo()).isEqualTo(LONG_REPAIR_PLAN_YEAR_TO);
        assertThat(actual.getLostElseReasonCode()).isEqualTo(LOST_ELSE_REASON_CODE);
        assertThat(actual.getLostElseReasonElse()).isEqualTo(LOST_ELSE_REASON_ELSE);
        assertThat(actual.getManagementCostCode()).isEqualTo(MANAGEMENT_COST_CODE);
        assertThat(actual.getManagementFormCode()).isEqualTo(MANAGEMENT_FORM_CODE);
        assertThat(actual.getManagementFormElse()).isEqualTo(MANAGEMENT_FORM_ELSE);
        assertThat(actual.getManagerAddress()).isEqualTo(MANAGER_ADDRESS);
        assertThat(actual.getManagerCode()).isEqualTo(MANAGER_CODE);
        assertThat(actual.getManagerName()).isEqualTo(MANAGER_NAME);
        assertThat(actual.getManagerNamePhonetic()).isEqualTo(MANAGER_NAME_PHONETIC);
        assertThat(actual.getManagerTelNo()).isEqualTo(MANAGER_TEL_NO);
        assertThat(actual.getManagerZipCode()).isEqualTo(MANAGER_ZIP_CODE);
        assertThat(actual.getMinutes()).isEqualTo(MINUTES);
        assertThat(actual.getNeedSupportListCode()).isEqualTo(NEED_SUPPORT_LIST_CODE);
        assertThat(actual.getNotificationCount()).isEqualTo(NOTIFICATION_COUNT);
        assertThat(actual.getNotificationDate()).isEqualTo(NOTIFICATION_DATE);
        assertThat(actual.getNotificationGroupName()).isEqualTo(NOTIFICATION_GROUP_NAME);
        assertThat(actual.getNotificationPersonName()).isEqualTo(NOTIFICATION_PERSON_NAME);
        assertThat(actual.getNotificationType()).isEqualTo(NOTIFICATION_TYPE);
        assertThat(actual.getOpenOneyearOver()).isEqualTo(OPEN_ONEYEAR_OVER);
        assertThat(actual.getOptional()).isEqualTo(OPTIONAL);
        assertThat(actual.getRailingCode()).isEqualTo(RAILING_CODE);
        assertThat(actual.getReceptionNo()).isEqualTo(RECEPTION_NO);
        assertThat(actual.getRentalNumber()).isEqualTo(RENTAL_NUMBER);
        assertThat(actual.getRentalPercentCode()).isEqualTo(RENTAL_PERCENT_CODE);
        assertThat(actual.getRepairCostCode()).isEqualTo(REPAIR_COST_CODE);
        assertThat(actual.getRepairHistoryCode()).isEqualTo(REPAIR_HISTORY_CODE);
        assertThat(actual.getRepairMonthlyCost()).isEqualTo(REPAIR_MONTHLY_COST);
        assertThat(actual.getRepairNearestYear()).isEqualTo(REPAIR_NEAREST_YEAR);
        assertThat(actual.getRepairPlanCode()).isEqualTo(REPAIR_PLAN_CODE);
        assertThat(actual.getRuleChangeYear()).isEqualTo(RULE_CHANGE_YEAR);
        assertThat(actual.getRuleCode()).isEqualTo(RULE_CODE);
        assertThat(actual.getSegmentCode()).isEqualTo(SEGMENT_CODE);
        assertThat(actual.getSeismicDiagnosisCode()).isEqualTo(SEISMIC_DIAGNOSIS_CODE);
        assertThat(actual.getSeismicRetrofitCode()).isEqualTo(SEISMIC_RETROFIT_CODE);
        assertThat(actual.getSlopeCode()).isEqualTo(SLOPE_CODE);
        assertThat(actual.getTempKbn()).isEqualTo(TEMP_KBN);
        assertThat(actual.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(actual.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(actual.getUseforCode()).isEqualTo(USEFOR_CODE);
        assertThat(actual.getUseforElse()).isEqualTo(USEFOR_ELSE);
        assertThat(actual.getVoluntaryOrganizationCode()).isEqualTo(VOLUNTARY_ORGANIZATION_CODE);
        assertThat(actual.getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(actual.getArrearageRuleCode()).isEqualTo(ARREARAGE_RULE_CODE);
    }
}

