/*
 * @(#) TBL235DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL235Entity;

/**
 * @author tqvu1
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL235DAOTest {

    @Autowired
    private TBL235DAO tbl235DAO;

    private final String TEMP_NO = "1";
    private final String APARTMENT_ID = "1000000001";
    private final String TEMP_KBN = "1";
    private final String APPENDIX_NO = "第　　号様式（第　　条関係）";
    private final String DOCUMENT_NO = "2019中央区文書番号初期値第　号";
    private final LocalDate NOTICE_DATE = LocalDate.parse("20191225", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private final String NOTICE_DESTINATION_CODE = "1";
    private final String RECIPIENT_NAME_APARTMENT = "Tokyo";
    private final String RECIPIENT_NAME_USER = "管理組合理事長";
    private final String SENDER = "東京都知事山本　泰人";
    private final String TEXTADDRESS = "貴管理組合が管理する";
    private final String ADDRESS = "0001　130001Da Nang";
    private final String APARTMENT_NAME = "Tokyo";
    private final String INVEST_IMPL_PLAN_TIME = "201912251413";
    private final String INVEST_IMPL_NUMBER_PEOPLE = "6";
    private final String NECESSARY_DOCUMENT = "fghfg";
    private final String CONTACT = "問合せ窓口_部署（区市町村向け） window2@gmail.com TEL_123456780 FAX_987654320";
    private final String NOTIFICATION_METHOD_CODE = "2";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "1000000030";
    private final Timestamp UPDATE_DATETIME = Timestamp.valueOf(LocalDateTime.parse("2019-12-25 02:08:48", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-002-01/区分:I/チェック内容:Test Get Survey By Apartment Id And Temp Kbn success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-002.sql")
    public void getSurveyByApartmentIdAndTempKbn_WhenExist_ShouldBeExist() {
        // Execute
        TBL235Entity actual = tbl235DAO.getSurveyByApartmentIdAndTempKbn(APARTMENT_ID, TEMP_KBN);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-002-02/区分:E/チェック内容:Test Get Survey By Apartment Id And Temp Kbn success When Not Found
     */
    @Test
    public void getSurveyByApartmentIdAndTempKbn_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL235Entity actual = tbl235DAO.getSurveyByApartmentIdAndTempKbn(null, null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-002-03/区分:N/チェック内容:Test Get Temp No By ApartmentId AndTemp KBN success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-002-03.sql")
    public void getTempNoByApartmentIdAndTempKbn_WhenExist_ShouldBeExist() {
        // Prepare data
        List<String> expected = new ArrayList<String>();
        expected.add("1");
        expected.add("2");

        // Execute
        List<String> actual = tbl235DAO.getTempNoByApartmentIdAndTempKBN(APARTMENT_ID, TEMP_KBN);

        // Check result
        assertThat(actual.size() == 2).isTrue();
        int i = 0;
        for (String str : actual) {
            assertThat(str).isEqualTo(expected.get(i++));
        }
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-04/区分:E/チェック内容:get Temp No By ApartmentId AndTemp KBN  When Not Found
     */
    @Test
    public void getTempNoByApartmentIdAndTempKBN_WhenNotFound() {
        // Execute
        List<String> actual = tbl235DAO.getTempNoByApartmentIdAndTempKBN(APARTMENT_ID, TEMP_KBN);
        // Check result
        assertThat(actual.size()).isEqualTo(0);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-05/区分:N/チェック内容:get Temp No By Apartment Id success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-002-03.sql")
    public void getTempNoByApartmentId_WhenExist_ShouldBeExist() {
        // Prepare data
        List<String> expected = new ArrayList<String>();
        expected.add("1");
        expected.add("2");

        // Execute
        List<String> actual = tbl235DAO.getTempNoByApartmentId(APARTMENT_ID);

        // Check result
        assertThat(actual.size() == 2).isTrue();
        int i = 0;
        for (String str : actual) {
            assertThat(str).isEqualTo(expected.get(i++));
        }
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-06/区分:E/チェック内容:get Temp No By Apartment Id  When Not Found
     */
    @Test
    public void getTempNoByApartmentId_WhenNotFound() {
        // Execute
        List<String> actual = tbl235DAO.getTempNoByApartmentId(APARTMENT_ID);
        // Check result
        assertThat(actual.size()).isEqualTo(0);
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-07/区分:N/チェック内容:delete By Id success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GGA0110-002.sql")
    public void deleteByIdSuccess() {
        // Execute
        tbl235DAO.deleteById(TEMP_NO);
        
        // Check result
        Optional<TBL235Entity> actual = tbl235DAO.findById(APARTMENT_ID);
        assertThat(actual.isPresent()).isFalse();
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-08/区分:E/チェック内容:delete By Id throw exception
     */
    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteByIdSuccessThrowException() {
        // Execute
        tbl235DAO.deleteById(TEMP_NO);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-09/区分:N/チェック内容:save success
     */
    @Test
    public void saveSuccess() {
        // Prepare data
        TBL235Entity tbl235Entity = generateTBL235Entity();

        // Execute
        TBL235Entity actual = tbl235DAO.save(tbl235Entity);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT- GGA0110-001-10/区分:E/チェック内容:save throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void saveThrowException() {
        // Execute
        tbl235DAO.save(null);
    }
    
    private void assertEntity(TBL235Entity entity) {
        assertThat(entity.getTempNo()).isEqualTo(TEMP_NO);
        assertThat(entity.getApartmentId()).isEqualTo(APARTMENT_ID);
        assertThat(entity.getTempKbn()).isEqualTo(TEMP_KBN);
        assertThat(entity.getAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(entity.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(entity.getNoticeDate()).isEqualTo(NOTICE_DATE);
        assertThat(entity.getNoticeDestinationCode()).isEqualTo(NOTICE_DESTINATION_CODE);
        assertThat(entity.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(entity.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(entity.getSender()).isEqualTo(SENDER);
        assertThat(entity.getTextaddress()).isEqualTo(TEXTADDRESS);
        assertThat(entity.getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getInvestImplPlanTime()).isEqualTo(INVEST_IMPL_PLAN_TIME);
        assertThat(entity.getInvestImplNumberPeople()).isEqualTo(INVEST_IMPL_NUMBER_PEOPLE);
        assertThat(entity.getNecessaryDocument()).isEqualTo(NECESSARY_DOCUMENT);
        assertThat(entity.getContact()).isEqualTo(CONTACT);
        assertThat(entity.getNotificationMethodCode()).isEqualTo(NOTIFICATION_METHOD_CODE);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);

    }

    private TBL235Entity generateTBL235Entity() {
        TBL235Entity entity = new TBL235Entity();
        entity.setTempNo(TEMP_NO);
        entity.setApartmentId(APARTMENT_ID);
        entity.setTempKbn(TEMP_KBN);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setNoticeDestinationCode(NOTICE_DESTINATION_CODE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setTextaddress(TEXTADDRESS);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setInvestImplPlanTime(INVEST_IMPL_PLAN_TIME);
        entity.setInvestImplNumberPeople(INVEST_IMPL_NUMBER_PEOPLE);
        entity.setNecessaryDocument(NECESSARY_DOCUMENT);
        entity.setContact(CONTACT);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        return entity;
    }

}
