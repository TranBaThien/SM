/*
 * @(#) TBL100DAOTest.java
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
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

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationCustomVo;

/**
 * @author nvlong1
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL100DAOTest {

    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL300DAO tbl300DAO;

    private final String APARTMENT_ID = "1000000001";
    private final String ADDRESS = "tokyo";
    private final String APARTMENT_LOST_FLAG = "1";
    private final String APARTMENT_NAME = "abc";
    private final String APARTMENT_NAME_PHONETIC = "abc phonetic";
    private final String BUILD_DAY = "10";
    private final String BUILD_MONTH = "02";
    private final String BUILD_YEAR = "2020";
    private final double BUILDING_AREA = 0.1;
    private final String BUILDING_STRUCTURE = "0101010101";
    private final String CITY_NAME = "kyoto";
    private final Integer FLOOR_NUMBER = 111;
    private final Integer HOUSE_NUMBER = 222;
    private final String NEWEST_NOTIFICATION_ADDRESS = "tokyo";
    private final String NEWEST_NOTIFICATION_NAME = "osaka";
    private final String NEWEST_NOTIFICATION_NO = "123456";
    private final String NOTIFICATION_KBN = "1";
    private final String PRELIMINARY1 = "00001";
    private final String PRELIMINARY2 = "00002";
    private final String PRELIMINARY3 = "0000003";
    private final String PRELIMINARY4 = "00004";
    private final String PRELIMINARY5 = "00005";
    private final String PROPERTY_NUMBER = "0008";
    private final String REAL_ESTATE_NO = "1111111111113";
    private final String REGISTRATION_ADDRESS = "kyoto";
    private final String REGISTRATION_HOUSE_NO = "osaka";
    private final String REGISTRATION_NO = "000008";
    private final String REPASSWORD_MAIL_ADDRESS = "abcd@gmail.com";
    private final double SITE_AREA = 0.1;
    private final String SUPPORT = "1";
    private final double TOTAL_FLOOR_AREA = 0.1;
    private final String ZIP_CODE = "12345678";
    private final Timestamp UPDATE_DATETIME = null;
    private final String CITY_CODE = "111111";
    private final String MAIL_ADDRESS = "vutran26999@gmail.com";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "G00000011";

    /* Create TBL300Entity */
    private final String PROGRESS_RECORD_NO_1 = "000001";
    private final String APARTMENT_ID_1 = "2000000000";
    private final String CORRESPOND_DATE_1 = "201912040529";
    private final String CORRESPOND_TYPE_CODE_1 = "0";
    private final String DELETE_FLAG_1 = "0";
    private final String NOTICE_TYPE_CODE_1 = "1";
    private final String NOTIFICATION_METHOD_CODE_1 = "0";
    private final String PROGRESS_RECORD_DETAIL_1 = "abcdef";
    private final String PROGRESS_RECORD_OVERVIEW_1 = "0000004";
    private final String RELATED_NUMBER_1 = "2000000000";
    private final String SUPPORT_CODE_1 = "0";
    private final String TYPE_CODE_1 = "3";
    private final Timestamp UPDATE_DATETIME_1 = new Timestamp(new Date().getTime());
    private final String UPDATE_USER_ID_1 = "1111";

    private final String APARTMENT_ID1 = "000002";
    
    private void assertEntityWithTBL100(TBL100Entity entity) {
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getApartmentLostFlag()).isEqualTo(APARTMENT_LOST_FLAG);
        assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(entity.getBuildDay()).isEqualTo(BUILD_DAY);
        assertThat(entity.getBuildMonth()).isEqualTo(BUILD_MONTH);
        assertThat(entity.getBuildYear()).isEqualTo(BUILD_YEAR);
        assertThat(entity.getBuildingArea()).isEqualTo(BUILDING_AREA);
        assertThat(entity.getBuildingStructure()).isEqualTo(BUILDING_STRUCTURE);
        assertThat(entity.getCityName()).isEqualTo(CITY_NAME);
        assertThat(entity.getFloorNumber()).isEqualTo(FLOOR_NUMBER);
        assertThat(entity.getHouseNumber()).isEqualTo(HOUSE_NUMBER);
        assertThat(entity.getNewestNotificationAddress()).isEqualTo(NEWEST_NOTIFICATION_ADDRESS);
        assertThat(entity.getNewestNotificationName()).isEqualTo(NEWEST_NOTIFICATION_NAME);
        assertThat(entity.getNewestNotificationNo()).isEqualTo(NEWEST_NOTIFICATION_NO);
        assertThat(entity.getNotificationKbn()).isEqualTo(NOTIFICATION_KBN);
        assertThat(entity.getPreliminary1()).isEqualTo(PRELIMINARY1);
        assertThat(entity.getPreliminary2()).isEqualTo(PRELIMINARY2);
        assertThat(entity.getPreliminary3()).isEqualTo(PRELIMINARY3);
        assertThat(entity.getPreliminary4()).isEqualTo(PRELIMINARY4);
        assertThat(entity.getPreliminary5()).isEqualTo(PRELIMINARY5);
        assertThat(entity.getPropertyNumber()).isEqualTo(PROPERTY_NUMBER);
        assertThat(entity.getRealEstateNo()).isEqualTo(REAL_ESTATE_NO);
        assertThat(entity.getRegistrationAddress()).isEqualTo(REGISTRATION_ADDRESS);
        assertThat(entity.getRegistrationHouseNo()).isEqualTo(REGISTRATION_HOUSE_NO);
        assertThat(entity.getRegistrationNo()).isEqualTo(REGISTRATION_NO);
        assertThat(entity.getRepasswordMailAddress()).isEqualTo(REPASSWORD_MAIL_ADDRESS);
        assertThat(entity.getSiteArea()).isEqualTo(SITE_AREA);
        assertThat(entity.getSupport()).isEqualTo(SUPPORT);
        assertThat(entity.getTotalFloorArea()).isEqualTo(TOTAL_FLOOR_AREA);
        assertThat(entity.getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-001-01/区分:I/チェック内容:Test get Mansion Information By Id Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GFA0110-001.sql")
    public void getMansionInformationById_WhenExist_ShouldBeExist() {
        // Execute
        TBL100Entity actual = tbl100DAO.getMansionInformationById(APARTMENT_ID);

        // Check result
        assertEntity(actual);
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-001-02/区分:E/チェック内容:Test Get Mansion Information By Id Return Null When Not Exist
     */
    @Test
    public void getMansionInformationById_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL100Entity actual = tbl100DAO.getMansionInformationById(APARTMENT_ID);
        // Check result
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID：GEB0110／チェックリストID：UT- GEB0110-001-01／区分：N／チェック内容：Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL100Entity expected = generateTBL100Entity(APARTMENT_ID);

        // Execute
        TBL100Entity actual = tbl100DAO.save(expected);

        // Check result
        assertEntity(actual);
    }


	/**
     *案件ID：GEB0110／チェックリストID：UT- GEB0110-001-02／区分：E／チェック内容：Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl100DAO.save(null);
    }

    /**
     * 案件ID:MBA0110/チェックリストID:UT- MBA0110-002-01/区分:I/チェック内容:Test Get Latest Progress Information Date By Mansion Id Success
     */
    @Sql(value = "classpath:/sql/UT- MBA0110-002-01.sql")
    @Test
    public void getLatestProgressInformationDate_WhenExist_ShouldBeExist() {
        
        List<String> expected = generateListCorrespondate();

        // Execute
        List<String> actual = tbl100DAO.getLatestProgressInformationDate(APARTMENT_ID);
        // Check result
        Assert.assertEquals(actual, expected);

    }

    @Test
    public void getLatestProgressInformationDate_WhenExist_ShouldBeNull() {
        // Prepare data
        
        // Execute
        List<String> actual = tbl100DAO.getLatestProgressInformationDate(APARTMENT_ID1);
        // Check result
        Assert.assertEquals(actual.size(), 0);

    }
    
    private List<String> generateListCorrespondate() {
        List<String> list = new ArrayList<String>();
        list.add(CORRESPOND_DATE_1);
        return list;
    }

    private void assertEntity(TBL100Entity entity) {
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getApartmentLostFlag()).isEqualTo(APARTMENT_LOST_FLAG);
        assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(entity.getBuildDay()).isEqualTo(BUILD_DAY);
        assertThat(entity.getBuildMonth()).isEqualTo(BUILD_MONTH);
        assertThat(entity.getBuildYear()).isEqualTo(BUILD_YEAR);
        assertThat(entity.getBuildingArea()).isEqualTo(BUILDING_AREA);
        assertThat(entity.getBuildingStructure()).isEqualTo(BUILDING_STRUCTURE);
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getCityName()).isEqualTo(CITY_NAME);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getFloorNumber()).isEqualTo(FLOOR_NUMBER);
        assertThat(entity.getHouseNumber()).isEqualTo(HOUSE_NUMBER);
        assertThat(entity.getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getNewestNotificationAddress()).isEqualTo(NEWEST_NOTIFICATION_ADDRESS);
        assertThat(entity.getNewestNotificationName()).isEqualTo(NEWEST_NOTIFICATION_NAME);
        assertThat(entity.getNewestNotificationNo()).isEqualTo(NEWEST_NOTIFICATION_NO);
        assertThat(entity.getNotificationKbn()).isEqualTo(NOTIFICATION_KBN);
        assertThat(entity.getPreliminary1()).isEqualTo(PRELIMINARY1);
        assertThat(entity.getPreliminary2()).isEqualTo(PRELIMINARY2);
        assertThat(entity.getPreliminary3()).isEqualTo(PRELIMINARY3);
        assertThat(entity.getPreliminary4()).isEqualTo(PRELIMINARY4);
        assertThat(entity.getPreliminary5()).isEqualTo(PRELIMINARY5);
        assertThat(entity.getPropertyNumber()).isEqualTo(PROPERTY_NUMBER);
        assertThat(entity.getRealEstateNo()).isEqualTo(REAL_ESTATE_NO);
        assertThat(entity.getRegistrationAddress()).isEqualTo(REGISTRATION_ADDRESS);
        assertThat(entity.getRegistrationHouseNo()).isEqualTo(REGISTRATION_HOUSE_NO);
        assertThat(entity.getRegistrationNo()).isEqualTo(REGISTRATION_NO);
        assertThat(entity.getRepasswordMailAddress()).isEqualTo(REPASSWORD_MAIL_ADDRESS);
        assertThat(entity.getSiteArea()).isEqualTo(SITE_AREA);
        assertThat(entity.getSupport()).isEqualTo(SUPPORT);
        assertThat(entity.getTotalFloorArea()).isEqualTo(TOTAL_FLOOR_AREA);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
    }
    
    private TBL100Entity generateTBL100Entity(String APARTMENT_ID) {
    	TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
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
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setZipCode(ZIP_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        return entity;
    }

    private TBL300Entity newTBL300Entity() {
        TBL300Entity entity = new TBL300Entity();

        entity.setProgressRecordNo(PROGRESS_RECORD_NO_1);
        entity.setApartmentId(APARTMENT_ID_1);
        entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE_1);
        entity.setDeleteFlag(DELETE_FLAG_1);
        entity.setNoticeTypeCode(NOTICE_TYPE_CODE_1);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE_1);
        entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL_1);
        entity.setRelatedNumber(RELATED_NUMBER_1);
        entity.setSupportCode(SUPPORT_CODE_1);
        entity.setTypeCode(TYPE_CODE_1);
        entity.setUpdateDatetime(UPDATE_DATETIME_1);
        entity.setUpdateUserId(UPDATE_USER_ID_1);
        entity.setCorrespondDate(CORRESPOND_DATE_1);

        return entity;
    }
    
    private TBL100Entity generateTBL100Entity1(String APARTMENT_ID) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(APARTMENT_ID);
        entity.setAddress("東五反田");
        entity.setApartmentName("マンション名17");
        entity.setCityCode("131091");
        entity.setCityName("東五反田");
        entity.setZipCode("141-0022");
        entity.setDeleteFlag("0");
        return entity;
    }
    
    /**
     * create apartmentId list
     * 
     * @return List<String> list Apartment Id
     */
    private List<String> listApartmentId() {
        List<String> apartmentIdList = new ArrayList<String>();
        apartmentIdList.add(APARTMENT_ID);
        return apartmentIdList;
    }
    
    /**
     * create apartmentId list null
     * 
     * @return List<String> list Apartment Id
     */
    private List<String> listApartmentId2() {
        List<String> apartmentIdList = new ArrayList<String>();
        apartmentIdList.add("123");
        return apartmentIdList;
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-001/区分:N/チェック内容:Test get Count By apartmentId Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEC0110-001.sql")
    public void getListApartmentId() {
        // Execute
        List<String> actual = tbl100DAO.getListApartmentId();
        List<String> lstApartmentId = listApartmentId();

        // Check result
        assertThat(actual).isEqualTo(lstApartmentId);
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GEC0110-003/区分:N/チェック内容:Test get cityCode By apartmentId When exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEC0110-001.sql")
    public void getCityCodeByApartmentId_WhenExist() {
        // Execute
        String cityCode = tbl100DAO.getCityCodeByApartmentId(APARTMENT_ID);

        // Check result
        assertThat(cityCode).isEqualTo("111111");
    }
    
    /**
     * 案件ID:GEC0110/チェックリストID:UT- GFA0110-004/区分:N/チェック内容:Test get cityCode By apartmentId When not exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEC0110-001.sql")
    public void getCityCodeByApartmentId_WhenNotExist() {
        // Execute
        String cityCode = tbl100DAO.getCityCodeByApartmentId("123");

        // Check result
        assertThat(cityCode).isEqualTo(null);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-001	/区分:N/チェック内容:Test get cityCode By apartmentId When exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-AAA0110-001.sql")
    public void getCount_WhenExist() {
        // Execute
        int actual = tbl100DAO.getCount(APARTMENT_NAME, ZIP_CODE, BUILD_YEAR);

        // Check result
        assertThat(actual).isEqualTo(1);
    }
    
    /**
     * 案件ID:AAA0110/チェックリストID:UT- AAA0110-002	/区分:N/チェック内容:Test get cityCode By apartmentId When not exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-AAA0110-001.sql")
    public void getCount_WhenNotExist() {
        // Execute
        int actual = tbl100DAO.getCount(APARTMENT_NAME, "a", BUILD_YEAR);

        // Check result
        assertThat(actual).isEqualTo(0);
    }
    
    /**
     * 案件ID：AAA0110／チェックリストID：UT- UT- AAA0110-009／区分：N／チェック内容：Test save success
     */
    @Test
    public void save_WhenSuccess() {
        // Prepare data
        TBL100Entity expected = generateTBL100Entity(APARTMENT_ID);

        // Execute
        TBL100Entity actual = tbl100DAO.save(expected);

        // Check result
        assertEntityWithTBL100(actual);
    }
    
    /**
     * 案件ID:GCA0120/チェックリストID:UT- GCA0120-003/区分:N/チェック内容: test get List Registration Apartment Information
     * 
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GCA0120-003.sql")
    public void test_GetRegistration_Apartment_Information() {
        TBL100Entity entity1 = generateTBL100Entity1("1000000013"); 
        
        List<TBL100Entity> listActual = tbl100DAO.getRegistrationApartmentInformation("マンション名17", "141-0022", "131091");
        for (TBL100Entity entity : listActual) {
            assertThat(entity.getApartmentId()).isEqualTo(entity1.getApartmentId());
            assertThat(entity.getZipCode()).isEqualTo(entity1.getZipCode());
            assertThat(entity.getCityCode()).isEqualTo(entity1.getCityCode());
            assertThat(entity.getApartmentName()).isEqualTo(entity1.getApartmentName());
        }
        
    }
}
