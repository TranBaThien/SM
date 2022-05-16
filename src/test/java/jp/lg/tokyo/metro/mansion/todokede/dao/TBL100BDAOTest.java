/**
 * @author nmtan
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvInformationSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSummaryDataDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSuperivseVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;

public class TBL100BDAOTest extends AbstractDaoTest {

    private final String APARTMENT_ID = "1000006";
    private final String PROPERTY_NUMBER = "0013";
    private final String APARTMENT_NAME = "Tokyo";
    private final String APARTMENT_NAME_PHONETIC = "Vinh Trung";
    private final String ZIP_CODE = "131-041";
    private final String CITY_CODE = "130001";
    private final String ADDRESS = "HCM";
    private final String REPASSWORD_MAIL_ADDRESS = "";
    private final String MAIL_ADDRESS = "minhtan@gmail.com";
    private final String NEWEST_NOTIFICATION_NO = "2000000000";
    private final String NEWEST_NOTIFICATION_NAME = "Demo";
    private final String NEWEST_NOTIFICATION_ADDRESS = "";
    private final String NOTIFICATION_KBN = "";
    private final String SUPPORT = "2";
    private final String BUILD_YEAR = "2019";
    private final String BUILD_MONTH = "12";
    private final String BUILD_DAY = "8";
    private final int FLOOR_NUMBER = 1;
    private final int HOUSE_NUMBER = 1;
    private final double SITE_AREA = 0;
    private final double TOTAL_FLOOR_AREA = 0;
    private final double BUILDING_AREA = 0;
    private final String BUILDING_STRUCTURE = "";
    private final String REGISTRATION_NO = "";
    private final String REGISTRATION_ADDRESS = "";
    private final String REGISTRATION_HOUSE_NO = "";
    private final String REAL_ESTATE_NO = "";
    private final String CITY_NAME = "";
    private final String APARTMENT_LOST_FLAG = "";
    private final String PRELIMINARY1 = "";
    private final String PRELIMINARY2 = "";
    private final String PRELIMINARY3 = "";
    private final String PRELIMINARY4 = "";
    private final String PRELIMINARY5 = "";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "G0000018";
    private final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
    private final String NOTIFICATION_NO = "2000000000";
    private final String RECEPTION_NO = "1000000002";
    private final String ACCEPT_STATUS = "1";
    private final String NOTIFICATION_TYPE = "2";
    private final LocalDate NOTIFICATION_DATE = LocalDate.now();
    private final String NOTIFICATION_GROUP_NAME = "0";
    private final String NOTIFICATION_PERSON_NAME = "0";
    private final String TEMP_KBN = "0";
    private final String ADVICE_DONE_FLAG = "1";
    private final int CHANGE_COUNT = 0;
    private final int NOTIFICATION_COUNT = 0;
    private final String CHANGE_REASON_CODE = "1";
    private final String LOST_ELSE_REASON_CODE = "9";
    private final String LOST_ELSE_REASON_ELSE = "reason test";
    private final String GROUP_YESNO_CODE = "1";
    private final int APARTMENT_NUMBER = 123;
    private final String GROUP_FORM = "1";
    private final String GROUP_FORM_ELSE = "";
    private final LocalDate BUILT_DATE = LocalDate.now();
    private final String LAND_RIGHTS_CODE = "0";
    private final String LAND_RIGHTS_ELSE = "0";
    private final String USEFOR_CODE = "0";
    private final String USEFOR_ELSE = "0";
    private final String MANAGEMENT_FORM_CODE = "0";
    private final String MANAGEMENT_FORM_ELSE = "0";
    private final String MANAGER_NAME = "test manager name";
    private final String MANAGER_NAME_PHONETIC = "test manager name phonetic";
    private final String MANAGER_ZIP_CODE = "999-5555";
    private final String MANAGER_ADDRESS = "0";
    private final String MANAGER_TEL_NO = "111-222-3333";
    private final String GROUP_CODE = "0";
    private final String MANAGER_CODE = "0";
    private final String RULE_CODE = "1";
    private final String RULE_CHANGE_YEAR = "2019";
    private final String OPEN_ONEYEAR_OVER = "1";
    private final String MINUTES = "2";
    private final String MANAGEMENT_COST_CODE = "1";
    private final String REPAIR_COST_CODE = "2";
    private final int REPAIR_MONTHLY_COST = 465413216;
    private final String REPAIR_PLAN_CODE = "1";
    private final String REPAIR_NEAREST_YEAR = "2020";
    private final String LONG_REPAIR_PLAN_CODE = "0";
    private final String LONG_REPAIR_PLAN_YEAR = "0";
    private final int LONG_REPAIR_PLAN_PERIOD = 0;
    private final String LONG_REPAIR_PLAN_YEAR_FROM = "0";
    private final String LONG_REPAIR_PLAN_YEAR_TO = "0";
    private final String ARREARAGE_RULE_CODE = "0";
    private final String SEGMENT_CODE = "0";
    private final String EMPTY_PERCENT_CODE = "0";
    private final int EMPTY_NUMBER = 0;
    private final String RENTAL_PERCENT_CODE = "0";
    private final int RENTAL_NUMBER = 0;
    private final String SEISMIC_DIAGNOSIS_CODE = "0";
    private final String EARTHQUAKE_RESISTANCE_CODE = "0";
    private final String SEISMIC_RETROFIT_CODE = "0";
    private final String DESIGN_DOCUMENT_CODE = "0";
    private final String REPAIR_HISTORY_CODE = "0";
    private final String VOLUNTARY_ORGANIZATION_CODE = "0";
    private final String DISASTER_PREVENTION_MANUAL_CODE = "0";
    private final String DISASTER_PREVENTION_STOCKPILE_CODE = "0";
    private final String NEED_SUPPORT_LIST_CODE = "0";
    private final String DISASTER_PREVENTION_REGULAR_CODE = "0";
    private final String SLOPE_CODE = "0";
    private final String RAILING_CODE = "0";
    private final String ELEVATOR_CODE = "0";
    private final String LED_CODE = "0";
    private final String HEAT_SHIELDING_CODE = "0";
    private final String EQUIPMENT_CHARGE_CODE = "0";
    private final String COMMUNITY_CODE = "0";
    private final String CONTACT_PROPERTY_CODE = "0";
    private final String CONTACT_PROPERTY_ELSE = "2";
    private final String CONTACT_ZIP_CODE = "132-041";
    private final String CONTACT_ADDRESS = "Tokyo";
    private final String CONTACT_TEL_NO = "098-478-8011";
    private final String CONTACT_NAME = "0";
    private final String CONTACT_NAME_PHONETIC = "0";
    private final String CONTACT_MAIL_ADDRESS = "test@test.com";
    private final String OPTIONAL = "Test Optional";
    private final LocalDateTime UPDATE_DATETIME_TBL200 = LocalDateTime.now();
    private final String APPENDIX_NO = "第場合号様式";
    private final String DOCUMENT_NO = "2019八丈町文書";
    private final String NOTICE_DATE = "20191224";
    private final String SUPERVISED_NOTICE_TYPE_CODE = "1回目用";
    private final String SENDER = "東京都知事森下　一男";
    private final String MAILING_CODE = "4";

    //TBL240
    private final String RECIPIENT_NAME_APARTMENT = "Tokyo";
    private final String RECIPIENT_NAME_USER = "されます。";
    private final String EVIDENCE_BAR = "16";
    private final String EVIDENCE_NO = "2";
    private final String PERIOD_EVIDENCE = "第５条第１項";
    private final String LAST_NOTICE_DATE = "";
    private final String LAST_DOCUMENT_NO = "";
    private final String NOTIFICATION_FORMAT_NAME = "";
    private final String NOTIFICATION_TIMELIMIT = "20200930";
    private final String CONTACT = "";
    private final int SUPERVISED_NOTICE_COUNT = 1;
    private final String TEXTADDRESS1 = "";
    private final String TEXTADDRESS2 = "";

    //FORM
    private final String TXT_APARTMENT_NAME = "Tokyo 1";
    private final String TXT_APARTMENT_ID = "1000000319";
    private final String TXT_APARTMENT_NAME_PHONETIC = "ミンタン";
    private final String TXT_ADDRESS = "Da Nang";
    private final String TXT_RECEPT_NUM = "2";
    private final String RDO_NOTIFICATION_STATUS = "1";
    private final String RDO_ACCEPT_STATUS = "1";
    private final String RDO_ADVICE_STATUS = "3";
    private final String RDO_SUPERVISE_STATUS = "1";
    private final String CAL_NOTIFICATION_DATE_FROM = "2003/11/29";
    private final String CAL_NOTIFICATION_DATE_TO = "2019/11/29";
    private final String CAL_BUILD_DATE_FROM = "2020/09/30";
    private final String CAL_BUILD_DATE_TO = "2021/09/30";
    private final String TXT_HOUSE_NUMBER_FROM = "1";
    private final String TXT_HOUSE_NUMBER_TO = "5";
    private final String TXT_FLOOR_NUMBER_FROM = "1";
    private final String TXT_FLOOR_NUMBER_TO = "5";
    private final boolean CHECK_APARTMENT_LOST = true;
    private final String TXT_SORT_PARAMETER = "住所（降順）";
    private final String TXT_CITY_CODE = "130001";


    @Autowired
    private TBL100BDAO tbl100BDAO;
    @Autowired
    private TBL100DAO tbl100DAO;
    @Autowired
    private TBL200DAO tbl200DAO;
    @Autowired
    private TBL240DAO tbl240DAO;
    @Autowired
    private SequenceUtil sequenceUtil;



    @Test
    public void getCSVInfo_WhenExist_ShouldBeExist() {
        // Prepare data
        TBL200Entity expected = generateTBL200Entity();
        tbl200DAO.save(expected);

        //TBL100 Data
        TBL100Entity expectedTbl100 = generateTBL100Entity();
        tbl100DAO.save(expectedTbl100);

        //TBL240 Data
        TBL240Entity expectedTbl240 = generateTBL240Entity();
        tbl240DAO.save(expectedTbl240);


        // Execute
        List<OutputCsvSuperivseVo> actualList = tbl100BDAO.getMansionAndWriteCsv(APARTMENT_ID);

        if (actualList.size() > 0) {
            OutputCsvSuperivseVo vo = actualList.get(0);
            // Check result
            assertThat(vo.getOutputApartmentID()).isEqualTo(expectedTbl100.getApartmentId());
            assertThat(vo.getOutputApartmentName()).isEqualTo(expectedTbl100.getApartmentName());
            assertThat(vo.getSuperviseNoticeType()).isEqualTo(expectedTbl240.getSupervisedNoticeTypeCode());
            assertThat(vo.getDocumentNo()).isEqualTo(expectedTbl240.getDocumentNo());
            assertThat(vo.getNoticeDate()).isEqualTo(expectedTbl240.getNoticeDate());
            assertThat(vo.getRecipientNameApartment()).isEqualTo(expectedTbl240.getRecipientNameApartment());
            assertThat(vo.getRecipientNameUser()).isEqualTo(expectedTbl240.getRecipientNameUser());
            assertThat(vo.getSender()).isEqualTo(expectedTbl240.getSender());
            assertThat(vo.getEvidenceBar()).isEqualTo(expectedTbl240.getEvidenceBar());
            assertThat(vo.getEvidenceNo()).isEqualTo(expectedTbl240.getEvidenceNo());
            assertThat(vo.getPeriodEvidence()).isEqualTo(expectedTbl240.getPeriodEvidence());
            assertThat(vo.getDocumentNo()).isEqualTo(expectedTbl240.getLastDocumentNo());
            assertThat(vo.getTextMailing2()).isEqualTo(expectedTbl240.getTextaddress2());
            assertThat(vo.getApartmentName()).isEqualTo(expectedTbl240.getApartmentName());
            assertThat(vo.getNotificationFormatName()).isEqualTo(expectedTbl240.getNotificationFormatName());
            assertThat(vo.getNotificationTimeLimit()).isEqualTo(expectedTbl240.getNotificationTimelimit());
            assertThat(vo.getContact()).isEqualTo(expectedTbl240.getContact());
        }

    }

    @Test
    public void getCSVInfo_WhenNotExist_ShouldBeNull() {
        TBL200Entity expected = generateTBL200Entity();
        tbl200DAO.save(expected);

        //TBL100 Data
        TBL100Entity expectedTbl100 = generateTBL100Entity();
        tbl100DAO.save(expectedTbl100);

        //TBL240 Data
        TBL240Entity expectedTbl240 = generateTBL240Entity();
        tbl240DAO.save(expectedTbl240);


        // Execute
        List<OutputCsvSuperivseVo> actualList = tbl100BDAO.getMansionAndWriteCsv(null);


        assertThat(actualList.size()).isEqualTo(0);


    }


    @Test
    public void getCSVInformation_WhenExist_ShouldBeExist() {
        // Prepare data
        TBL200Entity expected = generateTBL200Entity();
        tbl200DAO.save(expected);

        //TBL100 Data
        TBL100Entity expectedTbl100 = generateTBL100Entity();
        tbl100DAO.save(expectedTbl100);

        //TBL240 Data
        TBL240Entity expectedTbl240 = generateTBL240Entity();
        tbl240DAO.save(expectedTbl240);


        // Execute
        List<OutputCsvInformationSearchVo> actualList = tbl100BDAO.getInformationSearchWriteCSV(APARTMENT_ID);

        if (actualList.size() > 0) {
            OutputCsvInformationSearchVo vo = actualList.get(0);
            // Check result
            assertThat(vo.getApartmentId()).isEqualTo(expectedTbl100.getApartmentId());
            assertThat(vo.getApartmentName()).isEqualTo(expectedTbl100.getApartmentName());
            assertThat(vo.getZipCode()).isEqualTo(expectedTbl100.getZipCode());
            assertThat(vo.getGroupForm()).isEqualTo(expected.getGroupForm());

        }

    }

    @Test
    public void getCSVInfoMansionInfo_WhenNotExist_ShouldBeNull() {
        TBL200Entity expected = generateTBL200Entity();
        tbl200DAO.save(expected);

        //TBL100 Data
        TBL100Entity expectedTbl100 = generateTBL100Entity();
        tbl100DAO.save(expectedTbl100);

        //TBL240 Data
        TBL240Entity expectedTbl240 = generateTBL240Entity();
        tbl240DAO.save(expectedTbl240);


        // Execute
        List<OutputCsvInformationSearchVo> actualList = tbl100BDAO.getInformationSearchWriteCSV(null);


        // Check result

        assertThat(actualList.size()).isEqualTo(0);


    }


    /**
     * 案件ID：GJA0110／チェックリストID：UT-ABB0110-001-0005／区分：N／チェック内容：Test Get Result Mansion Info when Form is Null
     */
    @Test
    public void getListResultSearch_WhenExist_ShouldBeNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isEqualTo(0);
    }

    /**
     * 案件ID：GJA0110／チェックリストID：UT-ABB0110-001-0005／区分：N／チェック内容：Test Get Result Mansion Info when Form is not null
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist(){
        // Execute
        SearchInformationMansionForm form = generateForm();
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);


    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_NameNullAndIdNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentId(TXT_APARTMENT_ID);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }
    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_NameNotNullAndIdNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentName(TXT_APARTMENT_NAME);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
    }



    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_NamePhoneticNotNull()  {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentNamePhonetic("ミンタン");
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);

    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_AddressNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtApartmentAddress2(TXT_ADDRESS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-002.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_CalBuildDateFormNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setCalBuiltDateTo(CAL_BUILD_DATE_FROM);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
    }
    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_CalBuildDateToNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setCalBuiltDateTo(CAL_BUILD_DATE_TO);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }


    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_CalNotificationDateFromNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setCalNotificationDateFrom(CAL_NOTIFICATION_DATE_FROM);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_CalNotificationDateToNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setCalNotificationDateTo(CAL_BUILD_DATE_TO);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_RdoAcceptStatusNotNull()  {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setRdoAcceptStatus(RDO_ACCEPT_STATUS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_RdoNotificationNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setRdoNotificationStatus(RDO_NOTIFICATION_STATUS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }



    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_RdoSuperviseNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setRdoSuperviseStatus(RDO_SUPERVISE_STATUS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_RdoAdviseStatusNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setRdoAdviceStatus(RDO_ADVICE_STATUS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()>0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_RdoNotificationStatusNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setRdoNotificationStatus(RDO_NOTIFICATION_STATUS);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()>0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_HouseNumberFromNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtHouseNumberFrom(TXT_HOUSE_NUMBER_FROM);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_HouseNumberToNotNull() {
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtHouseNumberTo(TXT_HOUSE_NUMBER_TO);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_FloorNumberFromNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtFloorNumberFrom(TXT_FLOOR_NUMBER_FROM);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GJA0110-001.sql")
    public void getListResultSearch_WhenExist_ShouldBeExist_FloorNumberToNotNull(){
        // Execute
        SearchInformationMansionForm form = new SearchInformationMansionForm();
        form.setTxtHouseNumberTo(TXT_HOUSE_NUMBER_TO);
        Pageable pageable = PageRequest.of(0, 50);
        Page<ResultSearchVo> actual = tbl100BDAO.searchInformationMansionPaging(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertCheckResult(actual);
    }

    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-005/区分:I/チェック内容:Get Summary Data Details Interface When Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GKA0120-001.sql")
    public void getSummaryDataDetails_Interface_WhenExist(){
        // Execute
        SummaryDataPagingForm form = new SummaryDataPagingForm();
        form.setApartmentIds(new String[] { "1080000159" });
        Pageable pageable = PageRequest.of(0, 50);
        Page<SummaryDataDetailsVo> actual = tbl100BDAO.getSummaryDataDetails(pageable,form);
        actual.getContent().get(0).getApartmentId();
        assertThat(actual.getContent().get(0).getApartmentId()).isEqualTo("1080000159");
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-006/区分:N/チェック内容:Get Summary Data Details When Not Exist Should Be Null
     */
    @Test
    public void getSummaryDataDetails_WhenNotExist_ShouldBeNull(){
        // Execute
        SummaryDataPagingForm form = new SummaryDataPagingForm();
        form.setApartmentIds(new String[] { "1080000159" });
        Pageable pageable = PageRequest.of(0, 50);
        Page<SummaryDataDetailsVo> actual = tbl100BDAO.getSummaryDataDetails(pageable,form);
        assertThat(actual.getTotalElements()).isEqualTo(0);
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-007/区分:N/チェック内容:Get Summary Data Details When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GKA0120-001.sql")
    public void getSummaryDataDetails_WhenExist_ShouldBeExist(){
        // Execute
        SummaryDataPagingForm form = new SummaryDataPagingForm();
        form.setApartmentIds(new String[] { "1080000159" });
        Pageable pageable = PageRequest.of(0, 50);
        Page<SummaryDataDetailsVo> actual = tbl100BDAO.getSummaryDataDetails(pageable,form);
        assertThat(actual.getTotalElements()).isGreaterThan(0);
        assertThat(actual).isNotNull();
        assertThat(actual.getContent().get(0).getApartmentId()).isEqualTo("1080000159");
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-008/区分:I/チェック内容:Get Summary Data Details Write CSV Interface When Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GKA0120-001.sql")
    public void getSummaryDataDetailsWriteCSV_Interface_WhenExist() {
        // Execute
        List<OutputCsvSummaryDataDetailsVo> actualList = tbl100BDAO.getSummaryDataDetailsWriteCSV(new String[] { "1080000159" });
        assertThat(actualList.get(0).getApartmentId()).isEqualTo("1080000159");
    }
    
    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-009/区分:N/チェック内容:Get Summary Data Details Write CSV When Exist Should Be Exist Newest Notification No Is Not Null
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GKA0120-001.sql")
    public void getSummaryDataDetailsWriteCSV_WhenExist_ShouldBeExist_NewestNotificationNo_IsNotNull() {
        // Execute
        List<OutputCsvSummaryDataDetailsVo> actualList = tbl100BDAO.getSummaryDataDetailsWriteCSV(new String[] { "1080000159" });
        OutputCsvSummaryDataDetailsVo vo = actualList.get(0);
        // Check result
        assertSummaryDataDetailsWriteCSV_NewestNotificationNo_IsNotNull(vo);
    }
    
    private void assertSummaryDataDetailsWriteCSV_NewestNotificationNo_IsNotNull(OutputCsvSummaryDataDetailsVo vo) {
        assertThat(vo.getAcceptＳtatus()).isEqualTo("指定しない");
        assertThat(vo.getAddress()).isEqualTo("１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル");
        assertThat(vo.getApartmentId()).isEqualTo("1080000159");
        assertThat(vo.getApartmentName()).isEqualTo("中央区１マンション５９中央区１マンション５９中央区１マンション５９中央区１マンション５９中央区１マン");
        assertThat(vo.getApartmentNamePhonetic()).isEqualTo("チュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションニ");
        assertThat(vo.getApartmentNumber()).isEqualTo("0");
        assertThat(vo.getArrearageRule()).isEqualTo("回答しない");
        assertThat(vo.getBuiltDate()).isEqualTo("2000年11月30日");
        assertThat(vo.getChangeCount()).isEqualTo("0");
        assertThat(vo.getChangeReason()).isEqualTo("建物の滅失その他の事由");
        assertThat(vo.getCityCode()).isEqualTo("中央区");
        assertThat(vo.getCommunity()).isEqualTo("回答しない");
        assertThat(vo.getContactAddress()).isEqualTo("東京都品川区");
        assertThat(vo.getContactMailAddress()).isEqualTo("long@gmail.com");
        assertThat(vo.getContactName()).isEqualTo("マンションイチ");
        assertThat(vo.getContactNamePhonetic()).isEqualTo("マンションイチ");
        assertThat(vo.getContactProperty()).isEqualTo("その他");
        assertThat(vo.getContactPropertyElse()).isEqualTo(null);
        assertThat(vo.getContactTelNo()).isEqualTo("0905-571-889");
        assertThat(vo.getContactZip()).isEqualTo("140-0022");
        assertThat(vo.getDesignDocument()).isEqualTo("回答しない");
        assertThat(vo.getDisasterPreventionManual()).isEqualTo("回答しない");
        assertThat(vo.getDisasterPreventionRegular()).isEqualTo("回答しない");
        assertThat(vo.getDisasterPreventionStockpile()).isEqualTo("回答しない");
        assertThat(vo.getEarthquakeResistance()).isEqualTo("回答しない");
        assertThat(vo.getElevator()).isEqualTo("回答しない");
        assertThat(vo.getEmptyNumber()).isEqualTo("0");
        assertThat(vo.getEmptyPercent()).isEqualTo("回答しない");
        assertThat(vo.getEquipmentCharge()).isEqualTo("回答しない");
        assertThat(vo.getFloorNumber()).isEqualTo("11");
        assertThat(vo.getGroup()).isEqualTo("ない");
        assertThat(vo.getGroupForm()).isEqualTo("回答しない");
        assertThat(vo.getGroupFormElse()).isEqualTo(null);
        assertThat(vo.getGroupYesno()).isEqualTo("回答しない");
        assertThat(vo.getHeatShielding()).isEqualTo("回答しない");
        assertThat(vo.getHouseNumber()).isEqualTo("208");
        assertThat(vo.getLandRights()).isEqualTo("回答しない");
        assertThat(vo.getLandRightsElse()).isEqualTo(null);
        assertThat(vo.getLed()).isEqualTo("回答しない");
        assertThat(vo.getLongRepairPlan()).isEqualTo("回答しない");
        assertThat(vo.getLongRepairPlanPeriod()).isEqualTo("0");
        assertThat(vo.getLongRepairPlanYear()).isEqualTo(null);
        assertThat(vo.getLongRepairPlanYearFrom()).isEqualTo(null);
        assertThat(vo.getLongRepairPlanYearTo()).isEqualTo(null);
        assertThat(vo.getLostElseReason()).isEqualTo("その他");
        assertThat(vo.getLostElseReasonElse()).isEqualTo(null);
        assertThat(vo.getManagementCost()).isEqualTo("ない");
        assertThat(vo.getManagementForm()).isEqualTo("回答しない");
        assertThat(vo.getManagementFormElse()).isEqualTo(null);
        assertThat(vo.getManager()).isEqualTo("いない");
        assertThat(vo.getManagerAddress()).isEqualTo(null);
        assertThat(vo.getManagerName()).isEqualTo(null);
        assertThat(vo.getManagerNamePhonetic()).isEqualTo(null);
        assertThat(vo.getManagerTelNo()).isEqualTo(null);
        assertThat(vo.getManagerZipCode()).isEqualTo(null);
        assertThat(vo.getMinutes()).isEqualTo("ない");
        assertThat(vo.getNeedSupportList()).isEqualTo("回答しない");
        assertThat(vo.getNewestNotificationNo()).isEqualTo("1100000006");
        assertThat(vo.getNotificationDate()).isEqualTo("2020年01月17日");
        assertThat(vo.getNotificationGroupName()).isEqualTo("Long");
        assertThat(vo.getNotificationPersonName()).isEqualTo("山田");
        assertThat(vo.getNotificationType()).isEqualTo("更新");
        assertThat(vo.getOpenOneyearOver()).isEqualTo("ない");
        assertThat(vo.getOptional()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRentalNumber()).isEqualTo("0");
        assertThat(vo.getRentalPercent()).isEqualTo("回答しない");
        assertThat(vo.getRepairCost()).isEqualTo("ない");
        assertThat(vo.getRepairHistory()).isEqualTo("回答しない");
        assertThat(vo.getRepairMonthlyCost()).isEqualTo("0");
        assertThat(vo.getRepairNearestYear()).isEqualTo(null);
        assertThat(vo.getRepairPlan()).isEqualTo("ない");
        assertThat(vo.getRule()).isEqualTo("ない");
        assertThat(vo.getRuleChangeYear()).isEqualTo(null);
        assertThat(vo.getSegment()).isEqualTo("回答しない");
        assertThat(vo.getSeismicDiagnosis()).isEqualTo("回答しない");
        assertThat(vo.getSeismicRetrofit()).isEqualTo("回答しない");
        assertThat(vo.getSlope()).isEqualTo("回答しない");
        assertThat(vo.getUsefor()).isEqualTo("回答しない");
        assertThat(vo.getUseforElse()).isEqualTo(null);
        assertThat(vo.getVoluntaryOrganization()).isEqualTo("回答しない");
        assertThat(vo.getZipCode()).isEqualTo("104-0110");
    }

    /**
     * 案件ID:GKA0120/チェックリストID:UT- GKA0120-010/区分:N/チェック内容:Get Summary Data Details Write CSV When Exist Should Be Exist Newest Notification No Is Null
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GKA0120-002.sql")
    public void getSummaryDataDetailsWriteCSV_WhenExist_ShouldBeExist_ewestNotificationNo_IsNull() {
        // Execute
        List<OutputCsvSummaryDataDetailsVo> actualList = tbl100BDAO.getSummaryDataDetailsWriteCSV(new String[] { "1080000159" });
        OutputCsvSummaryDataDetailsVo vo = actualList.get(0);
        // Check result
        assertSummaryDataDetailsWriteCSV_NewestNotificationNo_IsNull(vo);
    }

    private void assertSummaryDataDetailsWriteCSV_NewestNotificationNo_IsNull(OutputCsvSummaryDataDetailsVo vo) {
        assertThat(vo.getAcceptＳtatus()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getAddress()).isEqualTo("１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル１－１－５９　１ビル");
        assertThat(vo.getApartmentId()).isEqualTo("1080000159");
        assertThat(vo.getApartmentName()).isEqualTo("中央区１マンション５９中央区１マンション５９中央区１マンション５９中央区１マンション５９中央区１マン");
        assertThat(vo.getApartmentNamePhonetic()).isEqualTo("チュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションゴウジュキュチュオウマンションニ");
        assertThat(vo.getApartmentNumber()).isEqualTo(null);
        assertThat(vo.getArrearageRule()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getBuiltDate()).isEqualTo("2000年11月30日");
        assertThat(vo.getChangeCount()).isEqualTo(null);
        assertThat(vo.getChangeReason()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getCityCode()).isEqualTo("中央区");
        assertThat(vo.getCommunity()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getContactAddress()).isEqualTo(null);
        assertThat(vo.getContactMailAddress()).isEqualTo(null);
        assertThat(vo.getContactName()).isEqualTo(null);
        assertThat(vo.getContactNamePhonetic()).isEqualTo(null);
        assertThat(vo.getContactProperty()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getContactPropertyElse()).isEqualTo(null);
        assertThat(vo.getContactTelNo()).isEqualTo(null);
        assertThat(vo.getContactZip()).isEqualTo(null);
        assertThat(vo.getDesignDocument()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getDisasterPreventionManual()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getDisasterPreventionRegular()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getDisasterPreventionStockpile()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getEarthquakeResistance()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getElevator()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getEmptyNumber()).isEqualTo(null);
        assertThat(vo.getEmptyPercent()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getEquipmentCharge()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getFloorNumber()).isEqualTo(null);
        assertThat(vo.getGroup()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getGroupForm()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getGroupFormElse()).isEqualTo(null);
        assertThat(vo.getGroupYesno()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getHeatShielding()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getHouseNumber()).isEqualTo(null);
        assertThat(vo.getLandRights()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getLandRightsElse()).isEqualTo(null);
        assertThat(vo.getLed()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getLongRepairPlan()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getLongRepairPlanPeriod()).isEqualTo(null);
        assertThat(vo.getLongRepairPlanYear()).isEqualTo(null);
        assertThat(vo.getLongRepairPlanYearFrom()).isEqualTo(null);
        assertThat(vo.getLongRepairPlanYearTo()).isEqualTo(null);
        assertThat(vo.getLostElseReason()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getLostElseReasonElse()).isEqualTo(null);
        assertThat(vo.getManagementCost()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getManagementForm()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getManagementFormElse()).isEqualTo(null);
        assertThat(vo.getManager()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getManagerAddress()).isEqualTo(null);
        assertThat(vo.getManagerName()).isEqualTo(null);
        assertThat(vo.getManagerNamePhonetic()).isEqualTo(null);
        assertThat(vo.getManagerTelNo()).isEqualTo(null);
        assertThat(vo.getManagerZipCode()).isEqualTo(null);
        assertThat(vo.getMinutes()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getNeedSupportList()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getNewestNotificationNo()).isEqualTo(null);
        assertThat(vo.getNotificationDate()).isEqualTo(null);
        assertThat(vo.getNotificationGroupName()).isEqualTo(null);
        assertThat(vo.getNotificationPersonName()).isEqualTo(null);
        assertThat(vo.getNotificationType()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getOpenOneyearOver()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getOptional()).isEqualTo(null);
        assertThat(vo.getRentalNumber()).isEqualTo(null);
        assertThat(vo.getRentalPercent()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRepairCost()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRepairHistory()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRepairMonthlyCost()).isEqualTo(null);
        assertThat(vo.getRepairNearestYear()).isEqualTo(null);
        assertThat(vo.getRepairPlan()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRule()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getRuleChangeYear()).isEqualTo(null);
        assertThat(vo.getSegment()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getSeismicDiagnosis()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getSeismicRetrofit()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getSlope()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getUsefor()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getUseforElse()).isEqualTo(null);
        assertThat(vo.getVoluntaryOrganization()).isEqualTo(CommonConstants.BLANK);
        assertThat(vo.getZipCode()).isEqualTo("1040110");
    }

    private TBL100Entity generateTBL100Entity() {

        TBL100Entity entity = new TBL100Entity();

        entity.setApartmentId(APARTMENT_ID);
        entity.setPropertyNumber(PROPERTY_NUMBER);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setApartmentNamePhonetic(APARTMENT_NAME_PHONETIC);
        entity.setZipCode(ZIP_CODE);
        entity.setCityCode(CITY_CODE);
        entity.setAddress(ADDRESS);
        entity.setRepasswordMailAddress(REPASSWORD_MAIL_ADDRESS);
        entity.setMailAddress(MAIL_ADDRESS);
        entity.setNewestNotificationNo(NEWEST_NOTIFICATION_NO);
        entity.setNewestNotificationName(NEWEST_NOTIFICATION_NAME);
        entity.setNewestNotificationAddress(NEWEST_NOTIFICATION_ADDRESS);
        entity.setNotificationKbn(NOTIFICATION_KBN);
        entity.setSupport(SUPPORT);
        entity.setBuildYear(BUILD_YEAR);
        entity.setBuildMonth(BUILD_MONTH);
        entity.setBuildDay(BUILD_DAY);
        entity.setFloorNumber(FLOOR_NUMBER);
        entity.setHouseNumber(HOUSE_NUMBER);
        entity.setSiteArea(SITE_AREA);
        entity.setTotalFloorArea(TOTAL_FLOOR_AREA);
        entity.setBuildingArea(BUILDING_AREA);
        entity.setBuildingStructure(BUILDING_STRUCTURE);
        entity.setRegistrationNo(REGISTRATION_NO);
        entity.setRegistrationAddress(REGISTRATION_ADDRESS);
        entity.setRegistrationHouseNo(REGISTRATION_HOUSE_NO);
        entity.setRealEstateNo(REAL_ESTATE_NO);
        entity.setCityName(CITY_NAME);
        entity.setApartmentLostFlag(APARTMENT_LOST_FLAG);
        entity.setPreliminary1(PRELIMINARY1);
        entity.setPreliminary2(PRELIMINARY2);
        entity.setPreliminary3(PRELIMINARY3);
        entity.setPreliminary4(PRELIMINARY4);
        entity.setPreliminary5(PRELIMINARY5);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);

        return entity;
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
        entity.setUpdateDatetime(UPDATE_DATETIME_TBL200);

        return entity;
    }

    private TBL240Entity generateTBL240Entity() {

        TBL240Entity entity = new TBL240Entity();

        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty("TBL240"),
                CommonConstants.COL_SUPERVISED_NOTICE_NO);

        String deleteFlag = CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG);
        String lblSupervisedNoticeTypeCode = CodeUtil.getValue(CommonConstants.CD022, SUPERVISED_NOTICE_TYPE_CODE);
        String mailingCode = CodeUtil.getValue(CommonConstants.CD018, MAILING_CODE);
        String evidenceBar = CodeUtil.getValue(CommonConstants.CD041, EVIDENCE_BAR);
        String evidenceNo = CodeUtil.getValue(CommonConstants.CD043, EVIDENCE_NO);
        String periodEvidence = CodeUtil.getValue(CommonConstants.CD045, PERIOD_EVIDENCE);
        Timestamp updateDateTime = DateTimeUtil.getCurrentSystemDateTime();

        entity.setSupervisedNoticeNo(keyNo);
        entity.setApartmentId(APARTMENT_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setSupervisedNoticeTypeCode(lblSupervisedNoticeTypeCode);
        entity.setSender(SENDER);
        entity.setMailingCode(mailingCode);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setTextaddress1(TEXTADDRESS1);
        entity.setTextaddress2(TEXTADDRESS2);
        entity.setEvidenceBar(evidenceBar);
        entity.setEvidenceNo(evidenceNo);
        entity.setPeriodEvidence(periodEvidence);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setNotificationFormatName(NOTIFICATION_FORMAT_NAME);
        entity.setNotificationTimelimit(NOTIFICATION_TIMELIMIT);
        entity.setContact(CONTACT);
        entity.setNotificationNo(NOTIFICATION_NO);
        entity.setDeleteFlag(deleteFlag);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(updateDateTime);
        entity.setSupervisedNoticeCount(SUPERVISED_NOTICE_COUNT);
        entity.setLastDocumentNo(LAST_DOCUMENT_NO);
        entity.setLastNoticeDate(LAST_NOTICE_DATE);

        return entity;
    }

    private SearchInformationMansionForm generateForm() {

        SearchInformationMansionForm form = new SearchInformationMansionForm();

        form.setTxtApartmentId(TXT_APARTMENT_ID);
        form.setTxtApartmentName(TXT_APARTMENT_NAME);
        form.setTxtApartmentNamePhonetic(TXT_APARTMENT_NAME_PHONETIC);
        form.setTxtApartmentAddress2(TXT_ADDRESS);
        form.setRdoNotificationStatus(RDO_NOTIFICATION_STATUS);
        form.setRdoAcceptStatus(RDO_ACCEPT_STATUS);
        form.setRdoAdviceStatus(RDO_ADVICE_STATUS);
        form.setRdoSuperviseStatus(RDO_SUPERVISE_STATUS);
        form.setCalNotificationDateFrom(CAL_NOTIFICATION_DATE_FROM);
        form.setCalBuiltDateTo(CAL_BUILD_DATE_TO);
        form.setTxtHouseNumberFrom(TXT_HOUSE_NUMBER_FROM);
        form.setTxtHouseNumberTo(TXT_HOUSE_NUMBER_TO);
        form.setTxtFloorNumberFrom(TXT_FLOOR_NUMBER_FROM);
        form.setTxtCityCode(TXT_CITY_CODE);
        form.setTxtFloorNumberTo(TXT_FLOOR_NUMBER_TO);
        form.setSortParameter(TXT_SORT_PARAMETER);
        return form;
    }

    private void assertCheckResult(Page<ResultSearchVo> resultSearchVoPage){

        assertThat(resultSearchVoPage.getContent().get(0).getLblApartmentId()).isEqualTo("1000000319");
        assertThat(resultSearchVoPage.getContent().get(0).getLnkApartmentName()).isEqualTo("Tokyo 1");
        assertThat(resultSearchVoPage.getContent().get(0).getCityCode()).isEqualTo("130001");
        assertThat(resultSearchVoPage.getContent().get(0).getLblAddress()).isEqualTo("Da Nang");

    }

    
}
