/*
 * @(#) TBL110DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/17
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;

import java.util.Optional;

public class TBL110DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL110DAO tbl110DAO;

    private final String USER_ID = "1000000011";
    private final String LOGIN_ID = "M0000011";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String CITY_CODE = "111111";
    private final String DELETE_FLAG = "0";
    private final int LOGIN_ERROR_COUNT = 0;
    private final String MAIL_ADDRESS = "vutran26999@gmail.com";
    private final String PASSWORD = "password_hash";
    private final String STOP_REASON = "stop reason";
    private final String TEL_NO = "09999999";
    private final String UPDATE_USER_ID = "M0000011";
    private final String USER_AUTHORITY = "1";
    private final String USER_NAME = "username 11";
    private final String USER_NAME_PHONETIC = "username phonetic";
    private final String USER_TYPE = "1";
    private final String ST_G_ACCOUNT_LOCK_PERIOD = "60";
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String VALIDITY_FLAG = "1";

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
    private final int FLOOR_NUMBER = 111;
    private final int HOUSE_NUMBER = 222;
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

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-01／区分：N／チェック内容：Test get Management Association Info success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-002.sql")
    public void getManagementAssociationInfo_WhenExist_ShouldBeExist() {
        // Execute
        TBL110Entity actual = tbl110DAO.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-02／区分：E／チェック内容：Test get Management Association Info  return null when not exist
     */
    @Test
    public void getManagementAssociationInfo_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL110Entity actual = tbl110DAO.getManagementAssociationInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG, VALIDITY_FLAG);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-03／区分：N／チェック内容：Test Find By Login Id success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-002.sql")
    public void findByLoginId_WhenExist_ShouldBeExist() {
        // Execute
        TBL110Entity actual = tbl110DAO.findByLoginId(LOGIN_ID);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-04／区分：E／チェック内容：Test Find By Login Id  return null when login id exist
     */
    @Test
    public void findByLoginId_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL110Entity actual = tbl110DAO.findByLoginId(LOGIN_ID);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-05／区分：N／チェック内容：Test save success
     * 案件ID：AAA0110／チェックリストID：UT- AAA0110-011／区分：N／チェック内容：Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL110Entity expected = generateTBL110Entity(APARTMENT_ID);

        // Execute
        TBL110Entity actual = tbl110DAO.save(expected);

        // Check result
        asertEntityWithoutTBL100(actual);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-002-06／区分：E／チェック内容：Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl110DAO.save(null);
    }

    /**
     * 案件ID：GJA0120／チェックリストID：UT- GJA0120-003／区分: I／チェック内容：Test Get User Info When Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-002.sql")
    public void getUserInfo_WhenExist_ShouldBeExist() {
        // Prepare data
        TBL110Entity expected = generateTBL110Entity(APARTMENT_ID);
        
        // Execute
        TBL110Entity actual = tbl110DAO.getUserByApartmentId(APARTMENT_ID);

        // Check result
        assertEntity(expected);
    }

    /**
     * 案件ID：GJA0120／チェックリストID：UT- GJA0120-004／区分：E／チェック内容：Test Get User Info Return Null When Not Exist
     */
    @Test
    public void getUserInfo_WhenExist_ShouldNotExist() {
        
        // Execute
        TBL110Entity actual = tbl110DAO.getUserByApartmentId(null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-013／区分: I／チェック内容：LoginIdが見つかり、フラグを削除するときにデータベースを取得するテスト= 0
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MAA0110-013.sql")
    public void testGetDatabaseWhenLoginIdFoundAndDeleteFlagIs0 () {
        TBL110Entity entity = tbl110DAO.findByLoginId("M0000016");
        assertThat(entity).isNotNull();
        assertThat(entity.getLoginId()).isEqualTo("M0000016");
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-014／区分：E／チェック内容：LoginIdが見つかり、フラグを削除するときにデータベースを取得するテスト= 1
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MAA0110-014.sql")
    public void testGetDatabaseWhenLoginIdFoundAndDeleteFlagIs1 () {
        TBL110Entity entity = tbl110DAO.findByLoginId("M0000016");
        assertThat(entity).isNull();
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-015／区分：E／チェック内容：LoginIdが見つからない場合のデータベース取得のテスト
     */
    @Test
    public void testGetDatabaseWhenLoginIdNotFound () {
        TBL110Entity entity = tbl110DAO.findByLoginId("M0000016");
        assertThat(entity).isNull();
    }

    /**
     * 案件ID：MAA0110／チェックリストID：UT-MAA0110-016／区分：N／チェック内容：テスト更新データベース
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MAA0110-013.sql")
    public void testUpdateDatabase () {
        TBL110Entity entity = tbl110DAO.findByLoginId("M0000016");
        assertThat(entity).isNotNull();

        assertThat(entity.getLoginErrorCount()).isEqualTo(0);

        entity.setLoginErrorCount(3);
        tbl110DAO.save(entity);

        TBL110Entity result = tbl110DAO.findByLoginId("M0000016");
        assertThat(result).isNotNull();
        assertThat(result.getLoginErrorCount()).isEqualTo(3);
    }

    private void assertEntity(TBL110Entity entity) {
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getAccountLockFlag()).isEqualTo(ACCOUNT_LOCK_FLAG);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getAvailability()).isEqualTo(ACCOUNT_AVAILABLE_FLAG);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getValidityFlag()).isEqualTo(VALIDITY_FLAG);
        assertThat(entity.getTbl100().getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getTbl100().getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getTbl100().getApartmentLostFlag()).isEqualTo(APARTMENT_LOST_FLAG);
        assertThat(entity.getTbl100().getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getTbl100().getApartmentNamePhonetic()).isEqualTo(APARTMENT_NAME_PHONETIC);
        assertThat(entity.getTbl100().getBuildDay()).isEqualTo(BUILD_DAY);
        assertThat(entity.getTbl100().getBuildMonth()).isEqualTo(BUILD_MONTH);
        assertThat(entity.getTbl100().getBuildYear()).isEqualTo(BUILD_YEAR);
        assertThat(entity.getTbl100().getBuildingArea()).isEqualTo(BUILDING_AREA);
        assertThat(entity.getTbl100().getBuildingStructure()).isEqualTo(BUILDING_STRUCTURE);
        assertThat(entity.getTbl100().getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getTbl100().getCityName()).isEqualTo(CITY_NAME);
        assertThat(entity.getTbl100().getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getTbl100().getFloorNumber()).isEqualTo(FLOOR_NUMBER);
        assertThat(entity.getTbl100().getHouseNumber()).isEqualTo(HOUSE_NUMBER);
        assertThat(entity.getTbl100().getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getTbl100().getNewestNotificationAddress()).isEqualTo(NEWEST_NOTIFICATION_ADDRESS);
        assertThat(entity.getTbl100().getNewestNotificationName()).isEqualTo(NEWEST_NOTIFICATION_NAME);
        assertThat(entity.getTbl100().getNewestNotificationNo()).isEqualTo(NEWEST_NOTIFICATION_NO);
        assertThat(entity.getTbl100().getNotificationKbn()).isEqualTo(NOTIFICATION_KBN);
        assertThat(entity.getTbl100().getPreliminary1()).isEqualTo(PRELIMINARY1);
        assertThat(entity.getTbl100().getPreliminary2()).isEqualTo(PRELIMINARY2);
        assertThat(entity.getTbl100().getPreliminary3()).isEqualTo(PRELIMINARY3);
        assertThat(entity.getTbl100().getPreliminary4()).isEqualTo(PRELIMINARY4);
        assertThat(entity.getTbl100().getPreliminary5()).isEqualTo(PRELIMINARY5);
        assertThat(entity.getTbl100().getPropertyNumber()).isEqualTo(PROPERTY_NUMBER);
        assertThat(entity.getTbl100().getRealEstateNo()).isEqualTo(REAL_ESTATE_NO);
        assertThat(entity.getTbl100().getRegistrationAddress()).isEqualTo(REGISTRATION_ADDRESS);
        assertThat(entity.getTbl100().getRegistrationHouseNo()).isEqualTo(REGISTRATION_HOUSE_NO);
        assertThat(entity.getTbl100().getRegistrationNo()).isEqualTo(REGISTRATION_NO);
        assertThat(entity.getTbl100().getRepasswordMailAddress()).isEqualTo(REPASSWORD_MAIL_ADDRESS);
        assertThat(entity.getTbl100().getSiteArea()).isEqualTo(SITE_AREA);
        assertThat(entity.getTbl100().getSupport()).isEqualTo(SUPPORT);
        assertThat(entity.getTbl100().getTotalFloorArea()).isEqualTo(TOTAL_FLOOR_AREA);
        assertThat(entity.getTbl100().getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getTbl100().getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(entity.getTbl100().getDeleteFlag()).isEqualTo(DELETE_FLAG);
    }

    private void asertEntityWithoutTBL100(TBL110Entity entity) {
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getAccountLockFlag()).isEqualTo(ACCOUNT_LOCK_FLAG);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getAvailability()).isEqualTo(ACCOUNT_AVAILABLE_FLAG);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getValidityFlag()).isEqualTo(VALIDITY_FLAG);
    }
    
    private TBL100Entity generateTBL100Entity(String apartmentId) {
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(apartmentId);
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

    private TBL110Entity generateTBL110Entity(String apartmentId) {
        TBL110Entity entity = new TBL110Entity();
        entity.setApartmentId(apartmentId);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setAvailability(ACCOUNT_AVAILABLE_FLAG);
        entity.setPassword(PASSWORD);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setValidityFlag(VALIDITY_FLAG);
        entity.setTbl100(generateTBL100Entity(apartmentId));
        return entity;
    }

}
