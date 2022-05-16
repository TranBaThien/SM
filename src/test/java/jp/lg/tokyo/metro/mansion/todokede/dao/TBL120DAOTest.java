/*
 * @(#) TBL120DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;

public class TBL120DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL120DAO tbl120DAO;

    private final String USER_ID = "1000000011";
    private final String LOGIN_ID = "G0000011";
    private final String LOGIN_ID_TWO_SIX = "_000001_";
    private final String BIGINING_PASSWORD_CHANGE_FLAG = "0";
    private final String PASSWORD = "$2a$10$ZE.sliJb44AqxbsXhZBHJekKyHvI341AcSvo1.nxAf3uRtutSuPGq";
    private final String USER_NAME = "user name 11";
    private final String USER_NAME_PHONETIC = "ユーザー名11";
    private final String USER_TYPE = "1";
    private final String CITY_CODE = "111111";
    private final String MAIL_ADDRESS = "vutran26999@gmail.com";
    private final String TEL_NO = "0969696969";
    private final int LOGIN_ERROR_COUNT = 0;
    private final String ACCOUNT_LOCK_FLAG = "0";
    private final String ACCOUNT_AVAILABLE_FLAG = "1";
    private final String LOGIN_STATUS_FLAG = "0";
    private final String DELETE_FLAG = "0";


    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-01／区分: I／チェック内容：Test get Government Staff User Login Info success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-003.sql")
    public void getGovernmentStaffUserLoginInfo_WhenExist_ShouldBeExist() {
        // Execute
        TBL120Entity actual = tbl120DAO.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-02／区分：E／チェック内容：Test get Government Staff User Login Info return null when not exist
     */
    @Test
    public void getGovernmentStaffUserLoginInfo_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL120Entity actual = tbl120DAO.getGovernmentStaffUserLoginInfo(LOGIN_ID, MAIL_ADDRESS, ACCOUNT_AVAILABLE_FLAG);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-03／区分：N／チェック内容：Test Find By Login Id success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-003.sql")
    public void findById_WhenExist_ShoudlBeExist() {
        // Execute
        Optional<TBL120Entity> actual = tbl120DAO.findByLoginId(LOGIN_ID);

        // Check result
        assertThat(actual.isPresent()).isTrue();
        assertEntity(actual.get());
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-04／区分：E／チェック内容：Test Find By Login Id return null when login id not exist
     */
    @Test
    public void findById_WhenNotExist_ShoudlBeNull() {
        // Execute
        Optional<TBL120Entity> actual = tbl120DAO.findByLoginId(LOGIN_ID);

        // Check result
        assertThat(actual).isEqualTo(Optional.empty());
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-05／区分：I／チェック内容：Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL120Entity expected = generateTBL120Entity();

        // Execute
        TBL120Entity actual = tbl120DAO.save(expected);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID：SCA0110／チェックリストID：UT- SCA0110-001-06／区分：E／チェック内容：Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl120DAO.save(null);
    }

    private void assertEntity(TBL120Entity entity) {
        assertThat(entity.getUserId()).isEqualTo(USER_ID);
        assertThat(entity.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(entity.getAccountLockFlag()).isEqualTo(ACCOUNT_LOCK_FLAG);
        assertThat(entity.getAvailability()).isEqualTo(ACCOUNT_AVAILABLE_FLAG);
        assertThat(entity.getLoginStatusFlag()).isEqualTo(LOGIN_STATUS_FLAG);
        assertThat(entity.getUserType()).isEqualTo(USER_TYPE);
        assertThat(entity.getPassword()).isEqualTo(PASSWORD);
        assertThat(entity.getUserType()).isEqualTo(USER_TYPE);
        assertThat(entity.getUserName()).isEqualTo(USER_NAME);
        assertThat(entity.getUserNamePhonetic()).isEqualTo(USER_NAME_PHONETIC);
        assertThat(entity.getLoginErrorCount()).isEqualTo(LOGIN_ERROR_COUNT);
        assertThat(entity.getCityCode()).isEqualTo(CITY_CODE);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getMailAddress()).isEqualTo(MAIL_ADDRESS);
        assertThat(entity.getTelNo()).isEqualTo(TEL_NO);
        assertThat(entity.getBiginingPasswordChangeFlag()).isEqualTo(BIGINING_PASSWORD_CHANGE_FLAG);
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-013／区分：N／チェック内容：LoginIdが見つかり、フラグを削除するときにデータベースを取得するテスト= 0
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GAA0110-013.sql")
    public void testGetDatabaseWhenLoginIdFoundAndDeleteFlagIs0() {
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId("G0000001");
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get().getLoginId()).isEqualTo("G0000001");
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-014／区分：E／チェック内容：LoginIdが見つかり、フラグを削除するときにデータベースを取得するテスト= 1
     */

    @Sql(value = "classpath:/sql/UT-GAA0110-014.sql")
    @Test
    public void testGetDatabaseWhenLoginIdFoundAndDeleteFlagIs1() {
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId("G0000001");
        assertThat(optional.isPresent()).isFalse();
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-015／区分：E／チェック内容：LoginIdが見つからない場合のデータベース取得のテスト
     */
    @Test
    public void testGetDatabaseWhenLoginIdNotFound() {
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId("G0000001");
        assertThat(optional.isPresent()).isFalse();
    }

    /**
     * 案件ID：GAA0110／チェックリストID：UT-GAA0110-016／区分：N／チェック内容：テスト更新データベース
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GAA0110-013.sql")
    public void testUpdateDatabase() {
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId("G0000001");
        assertThat(optional.isPresent()).isTrue();

        TBL120Entity entity = optional.get();
        assertThat(entity.getLoginErrorCount()).isEqualTo(0);

        entity.setLoginErrorCount(3);
        tbl120DAO.save(entity);

        Optional<TBL120Entity> resultOptional = tbl120DAO.findByLoginId("G0000001");
        assertThat(resultOptional.isPresent()).isTrue();
        assertThat(resultOptional.get().getLoginErrorCount()).isEqualTo(3);
    }

    private TBL120Entity generateTBL120Entity() {
        TBL120Entity entity = new TBL120Entity();
        entity.setUserId(USER_ID);
        entity.setLoginId(LOGIN_ID);
        entity.setAccountLockFlag(ACCOUNT_LOCK_FLAG);
        entity.setAvailability(ACCOUNT_AVAILABLE_FLAG);
        entity.setLoginStatusFlag(LOGIN_STATUS_FLAG);
        entity.setUserType(USER_TYPE);
        entity.setPassword(PASSWORD);
        entity.setUserType(USER_TYPE);
        entity.setUserName(USER_NAME);
        entity.setUserNamePhonetic(USER_NAME_PHONETIC);
        entity.setLoginErrorCount(LOGIN_ERROR_COUNT);
        entity.setCityCode(CITY_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setMailAddress(MAIL_ADDRESS);
        entity.setTelNo(TEL_NO);
        entity.setBiginingPasswordChangeFlag(BIGINING_PASSWORD_CHANGE_FLAG);
        return entity;
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0001／区分：E／チェック内容：Test get Government Staff info when UserId not exist.
     */
    @Test
    public void getGovernmentStaffInfo_WhenNotExist_ShoudlBeNull() {
        // Execute
        TBL120Entity actual = tbl120DAO.getGovernmentStaffInfo(USER_ID);
        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0002／区分：N／チェック内容：Test get Government Staff info when UserId exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SCA0110-003.sql")
    public void getGovernmentStaffInfo_WhenExist_ShoudlBeExist() {
        // Execute
        TBL120Entity actual = tbl120DAO.getGovernmentStaffInfo(USER_ID);
        assertEntity(actual);
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0003／区分：N／チェック内容：Test get GovernmentStaffs Info by like loginId complete return null
     */
    @Test
    public void getGovernmentStaffInfoByLikeLoginId_WhenExist_ShouldBeNull() {
        // Execute
        List<TBL120Entity> actual = tbl120DAO.getGovernmentStaffInfoByLikeLoginId(LOGIN_ID_TWO_SIX);
        assertThat(actual).isEqualTo(new ArrayList<TBL120Entity>());
    }

    /**
     * 案件ID：ABB0110／チェックリストID：UT-ABB0110-001-0004／区分：N／チェック内容：Test get GovernmentStaffs Info by like loginId complete return one record
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-001.sql")
    public void getGovernmentStaffInfoByLikeLoginId_WhenExist_ShouldBeExist() {
        // Execute
        List<TBL120Entity> actual = tbl120DAO.getGovernmentStaffInfoByLikeLoginId(LOGIN_ID_TWO_SIX);
        assertEntity(actual.get(0));
    }
    
    /**
     * 案件ID：SBA0110／チェックリストID：UT- SBA0110-001-01／区分：N／チェック内容：Test Find By Login Id success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-SBA0110-001.sql")
    public void findByIdWhenExistInMunicipalities() {
        Optional<TBL120Entity> actual = tbl120DAO.findByLoginId("G0000011");
        // Check result
        assertThat(actual.isPresent()).isTrue();
        assertEntity(actual.get());
    }
}
