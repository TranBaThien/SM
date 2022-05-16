/*
 * @(#) TBL300DAOTest.java
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity;

/**
 * @author nvlong1
 *
 */
public class TBL300DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL100DAO tbl100DAO;
    
    @Autowired
    private TBL300DAO tbl300DAO;

    /* Create TBL300Entity */
    private final String PROGRESS_RECORD_NO = "2000000000";
    private final String APARTMENT_ID_300 = "2000000000";
    private final String CORRESPOND_DATE = "201912040529";
    private final String TYPE_CODE = "3";
    private final String CORRESPOND_TYPE_CODE = "";
    private final String NOTICE_TYPE_CODE = "";
    private final String RELATED_NUMBER = "1000000001";
    private final String PROGRESS_RECORD_OVERVIEW = "HntVy";
    private final String PROGRESS_RECORD_DETAIL = "";
    private final String SUPPORT_CODE = "";
    private final String NOTIFICATION_METHOD_CODE = "2";
    private final String DELETE_FLAG_300 = "0";
    private final String UPDATE_USER_ID_300 = "1000000010";
    private final Timestamp UPDATE_DATETIME_300 = new Timestamp(new Date().getTime());
     TBL310Entity TBL310ENTITY = null;
    
    private final Timestamp UPDATE_DATETIME_1 = Timestamp.valueOf(LocalDateTime.parse("2019-12-24 19:42:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final Timestamp UPDATE_DATETIME_2 = Timestamp.valueOf(LocalDateTime.parse("2019-12-24 18:55:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    
    /* Create TBL100Entity */
    private final String APARTMENT_ID_100 = "2000000000";
    private final String PROPERTY_NUMBER = "0013";
    private final String APARTMENT_NAME = "Tokyo";
    private final String APARTMENT_NAME_PHONETIC = "Vinh Trung";
    private final String ZIP_CODE = "131-041";
    private final String CITY_CODE = "130001";
    private final String ADDRESS = "Da Nang";
    private final String REPASSWORD_MAIL_ADDRESS = "";
    private final String MAIL_ADDRESS = "vutran26999@gmail.com";
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
    private final String DELETE_FLAG_100 = "0";
    private final String UPDATE_USER_ID_100 = "G0000018";
    private final Timestamp UPDATE_DATETIME_100 = new Timestamp(new Date().getTime());
    
    private final String CORRESPOND_DATE1 = "201912040529";
    private final String CORRESPOND_DATE2 = "210010040529";
    private final String[] LIST_PROGRESS_RECORD_NO = {"1000000002","1000000003"};

    /**
      *  案件ID:GEB0110/チェックリストID:UT- GEB0110-001-03/区分:I/チェック内容:Test get Progress Record Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEB0110-10.sql")
    public void testgetProgressRecord_Exist_ShouldBeExist() {
        // Execute
        TBL300Entity actual = tbl300DAO.getProgressRecord("2000000000");
        // Check result
        assertEntity(actual);
    }
    
    @Test
    public void testgetProgressRecord_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL300Entity actual = tbl300DAO.getProgressRecord(null);
        // Check result
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     *  案件ID:GEB0110/チェックリストID:UT- GEB0110-001-04/区分:N/チェック内容:Test save Success
     *  案件ID:GEC0110/チェックリストID:UT- GEC0110-005/区分:N/チェック内容:Test save Success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL300Entity expected = generateTBL300Entity();

        // Execute
        TBL300Entity actual = tbl300DAO.save(expected);
       

        // Check result
        asertEntityWithoutTBL300(actual);
    }
    
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl300DAO.save(null);
    }
    
    /**
    * 案件ID:MBA0110/チェックリストID:UT- MBA0110-004-01/区分:E/チェック内容:Test get Mansion Information By Id Success
    */

    @Test
    @Sql(value = "classpath:/sql/UT- MBA0110-003-01.sql")
    public void getProgressRecordFromDate_WhenExist_ShouldBeExist() {              

        // Execute
        List<TBL300Entity> actual = tbl300DAO.getProgressRecordFromDate(APARTMENT_ID_100, CORRESPOND_DATE1);

        assertEntity(actual.get(0));
    }
    
    @Test
    public void testGetProgressRecordFromDate_WhenNull_ShouldBeNull() {

        // Execute
        
        List<TBL300Entity> actual = tbl300DAO.getProgressRecordFromDate(APARTMENT_ID_100, CORRESPOND_DATE2);
        // Check results

        Assert.assertEquals(actual.size(), 0);
    }

    /**
    * 案件ID:GEA0110/チェックリストID:UT- GEA0110-042/区分:E/チェック内容: Test method getListProgressRecordByProgressRecordNos when data is null
    */
    @Test
    public void testGetListProgressRecordByProgressRecordNos_WhenNull_ShouldBeNull() {
        // Execute
        List<TBL300Entity> listProRecordDetail = tbl300DAO.getListProgressRecordByProgressRecordNos(null);
        // Check result
        Assert.assertEquals(listProRecordDetail.size(), 0);
    }

    // 
    /**
    * 案件ID:GEA0110/チェックリストID:UT- GEA0110-043/区分:I/チェック内容: Test method getListProgressRecordByProgressRecordNos when data is exist
    */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-004.sql")
    public void testGetListProgressRecordByProgressRecordNos_WhenExist_ShouldBeExist() {
        // Execute
        List<TBL300Entity> listTbl300Entity = tbl300DAO.getListProgressRecordByProgressRecordNos(Arrays.asList(LIST_PROGRESS_RECORD_NO));
        // Check result
        Assert.assertEquals(listTbl300Entity.size(), 2);
        assertListTBL300Entity(listTbl300Entity);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-012／区分：N／チェック内容：TBL300への更新データのテスト
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GDA0110-012.sql")
    public void testUpdateTBL300() {
        String newNotificationNo = "2000000022";
        String updatedUserId = "1000000001";
        tbl300DAO.findByByApartmentIdAndRelatedNumber("1000000002", "1000000012").forEach(entity -> {
            entity.setUpdateUserId(updatedUserId);
            entity.setUpdateDatetime(Timestamp.valueOf(LocalDateTime.of(2019,12,12,12,12,12)));
            entity.setRelatedNumber(newNotificationNo);
            tbl300DAO.save(entity);
        });

        List<TBL300Entity> tbl300Entities = tbl300DAO.findByByApartmentIdAndRelatedNumber("1000000002", newNotificationNo);
        assertThat(tbl300Entities.size()).isEqualTo(1);
        assertThat(tbl300Entities.get(0).getUpdateUserId()).isEqualTo(updatedUserId);
        assertThat(tbl300Entities.get(0).getUpdateDatetime()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2019,12,12,12,12,12)));
        assertThat(tbl300Entities.get(0).getRelatedNumber()).isEqualTo(newNotificationNo);
    }

    private void assertListTBL300Entity(List<TBL300Entity> listTbl300Entity) {
        assertThat(listTbl300Entity.get(0).getProgressRecordNo()).isEqualTo("1000000002");
        assertThat(listTbl300Entity.get(0).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl300Entity.get(0).getCorrespondDate()).isEqualTo("201912240615");
        assertThat(listTbl300Entity.get(0).getTypeCode()).isEqualTo("4");
        assertThat(listTbl300Entity.get(0).getCorrespondTypeCode()).isEqualTo(null);
        assertThat(listTbl300Entity.get(0).getNoticeTypeCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(0).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listTbl300Entity.get(0).getProgressRecordOverview()).isEqualTo("Test progress");
        assertThat(listTbl300Entity.get(0).getProgressRecordDetail()).isEqualTo("test progress record details");
        assertThat(listTbl300Entity.get(0).getSupportCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(0).getNotificationMethodCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(0).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl300Entity.get(0).getUpdateUserId()).isEqualTo("1000000010");
        assertThat(listTbl300Entity.get(0).getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_2);

        assertThat(listTbl300Entity.get(1).getProgressRecordNo()).isEqualTo("1000000003");
        assertThat(listTbl300Entity.get(1).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl300Entity.get(1).getCorrespondDate()).isEqualTo("201912231840");
        assertThat(listTbl300Entity.get(1).getTypeCode()).isEqualTo("4");
        assertThat(listTbl300Entity.get(1).getCorrespondTypeCode()).isEqualTo(null);
        assertThat(listTbl300Entity.get(1).getNoticeTypeCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(1).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listTbl300Entity.get(1).getProgressRecordOverview()).isEqualTo("Test progress");
        assertThat(listTbl300Entity.get(1).getProgressRecordDetail()).isEqualTo("test progress record details");
        assertThat(listTbl300Entity.get(1).getSupportCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(1).getNotificationMethodCode()).isEqualTo("1");
        assertThat(listTbl300Entity.get(1).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl300Entity.get(1).getUpdateUserId()).isEqualTo("1000000010");
        assertThat(listTbl300Entity.get(1).getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_1);
    }
    
    private List<TBL300Entity> generateTBL300Entitys(){
        List<TBL300Entity> list = new ArrayList<>();
        TBL300Entity entity = generateTBL300Entity();
        list.add(entity);
        
        return list;
    }

    private TBL300Entity generateTBL300Entity() {
        
        TBL300Entity entity = new TBL300Entity();
        
        entity.setProgressRecordNo(PROGRESS_RECORD_NO);
        entity.setApartmentId(APARTMENT_ID_300);
        entity.setCorrespondDate(CORRESPOND_DATE);
        entity.setTypeCode(TYPE_CODE);
        entity.setCorrespondTypeCode(CORRESPOND_TYPE_CODE);
        entity.setNoticeTypeCode(NOTICE_TYPE_CODE);
        entity.setRelatedNumber(RELATED_NUMBER);
        entity.setProgressRecordOverview(PROGRESS_RECORD_OVERVIEW);
        entity.setProgressRecordDetail(PROGRESS_RECORD_DETAIL);
        entity.setSupportCode(SUPPORT_CODE);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG_300);
        entity.setUpdateUserId(UPDATE_USER_ID_300);
        entity.setUpdateDatetime(UPDATE_DATETIME_300);
        entity.setTbl310Entity(null);
        entity.settBL100Entity(null);
        return entity;
    }
    
     private TBL100Entity generateTBL100Entity() {
            
            TBL100Entity entity = new TBL100Entity();
            
            entity.setApartmentId(APARTMENT_ID_100);
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
            entity.setDeleteFlag(DELETE_FLAG_100);
            entity.setUpdateUserId(UPDATE_USER_ID_100);
            entity.setUpdateDatetime(UPDATE_DATETIME_100);
            
            return entity;
        }
     
     private void assertEntity(TBL300Entity expected) {
            assertThat(expected.getProgressRecordNo()).isEqualTo(PROGRESS_RECORD_NO);
            assertThat(expected.getApartmentId()).isEqualTo("2000000000");
            assertThat(expected.getCorrespondDate()).isEqualTo(CORRESPOND_DATE);
            assertThat(expected.getCorrespondTypeCode()).isEqualTo(null);
            assertThat(expected.getDeleteFlag()).isEqualTo("0");
            assertThat(expected.getNoticeTypeCode()).isEqualTo(null);
            assertThat(expected.getNotificationMethodCode()).isEqualTo("2");
            assertThat(expected.getProgressRecordDetail()).isEqualTo(null);
            assertThat(expected.getProgressRecordOverview()).isEqualTo("HntVy");
            assertThat(expected.getRelatedNumber()).isEqualTo("1000000001");
            assertThat(expected.getSupportCode()).isEqualTo(null);
            assertThat(expected.getTypeCode()).isEqualTo("3");
            assertThat(expected.getUpdateDatetime()).isEqualTo(null);
            assertThat(expected.getUpdateUserId()).isEqualTo("1000000010");
            
        }
     
     private void asertEntityWithoutTBL300(TBL300Entity actual) {
            assertThat(actual.getProgressRecordNo()).isEqualTo(PROGRESS_RECORD_NO);
            assertThat(actual.getApartmentId()).isEqualTo("2000000000");
            assertThat(actual.getTypeCode()).isEqualTo(TYPE_CODE);
            assertThat(actual.getCorrespondTypeCode()).isEqualTo(CORRESPOND_TYPE_CODE);
            assertThat(actual.getNoticeTypeCode()).isEqualTo(NOTICE_TYPE_CODE);
            assertThat(actual.getRelatedNumber()).isEqualTo(RELATED_NUMBER);
            assertThat(actual.getProgressRecordOverview()).isEqualTo(PROGRESS_RECORD_OVERVIEW);
            assertThat(actual.getProgressRecordDetail()).isEqualTo(PROGRESS_RECORD_DETAIL);
            assertThat(actual.getSupportCode()).isEqualTo(SUPPORT_CODE);
            assertThat(actual.getNotificationMethodCode()).isEqualTo(NOTIFICATION_METHOD_CODE);
            assertThat(actual.getDeleteFlag()).isEqualTo("0");
            assertThat(actual.getUpdateUserId()).isEqualTo("1000000010");
            assertThat(actual.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_300);
            
        }
      
}
