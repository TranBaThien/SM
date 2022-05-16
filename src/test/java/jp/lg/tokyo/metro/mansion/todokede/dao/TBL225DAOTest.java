/*
 * @(#) TBL225DAOTest.java
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;

/**
 * @author nvlong1
 *
 */
public class TBL225DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL225DAO tbl225DAO;

    private final String TEMP_NO = "2000000000";
    private final String NOTIFICATION_NO = "2000000000";
    private final String TEMP_KBN = "3";
    private final String ADVICE_USER_ID = "1000000010";
    private final String APPENDIX_NO = "第　　号様式(第　　関係）";
    private final String DOCUMENT_NO = "2019住住マ　第　号";
    private final LocalDate NOTICE_DATE = LocalDate.of(2019,12,14);
    private final String RECIPIENT_NAME_APARTMENT = "Viet Nam";
    private final String RECIPIENT_NAME_USER = "区分所有者　0";
    private final String SENDER = "東京都知事小池　百合子";
    private final LocalDate NOTIFICATION_DATE = LocalDate.of(2019,11,29);
    private final String EVIDENCE_BAR = "2";
    private final String EVIDENCE_NO = "1";
    private final String ADDRESS = "Da Nang";
    private final String APARTMENT_NAME = "Viet Nam";
    private final String ADVICE_DETAILS = "abc abc abc abc abc";
    private final String NOTIFICATION_METHOD_CODE = "2";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "1000000010";
    private final Timestamp UPDATE_DATETIME = null;

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-01/区分:N/チェック内容:Test get Advice Notification Temporary Storage success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GFA0110-004.sql")
    public void getAdviceNotificationTemporaryStorage_WhenExist_ShouldBeExist() {
        // Execute
        TBL225Entity actual = tbl225DAO.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO, TEMP_KBN);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-02/区分:E/チェック内容:Test get Advice Notification Temporary Storage Return Null When Not Exist
     */
    @Test
    public void getAdviceNotificationTemporaryStorage_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL225Entity actual = tbl225DAO.getAdviceNotificationTemporaryStorage(null, null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-03/区分:N/チェック内容:Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL225Entity expected = generateTBL225Entity();

        // Execute
        TBL225Entity actual = tbl225DAO.save(expected);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-04/区分:E/チェック内容:Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl225DAO.save(null);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-05/区分:N/チェック内容:Test Delete Where NotificationNo And TempKbn When Success 
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    @Sql(value = "classpath:/sql/UT-GFA0110-004.sql")
    public void delete_Where_NotificationNo_And_TempKbn_WhenSuccess() {
        // Execute
        tbl225DAO.deleteTemporarilySavedData(NOTIFICATION_NO, TEMP_KBN);

        // Check result
        TBL225Entity actual = tbl225DAO.getAdviceNotificationTemporaryStorage(NOTIFICATION_NO, TEMP_KBN);
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-06/区分:E/チェック内容:Test Delete Where NotificationNo And TempKbn When Fail
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void delete_Where_NotificationNo_And_TempKbn_WhenFail() {
        // Execute
        tbl225DAO.deleteTemporarilySavedData(null, null);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-07/区分:I/チェック内容:Test get Temp No By Notification No Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GFA0110-005.sql")
    public void getTempNoByNotificationNo_WhenExist_ShouldBeExist() {
        // Prepare data
        List<String> expected = new ArrayList<String>();
        expected.add("2000000000");
        expected.add("3000000000");

        // Execute
        List<String> actual = tbl225DAO.getTempNoByNotificationNo(NOTIFICATION_NO);

        // Check result
        assertThat(actual.size() == 2).isTrue();
        int i = 0;
        for (String str : actual) {
            assertThat(str).isEqualTo(expected.get(i++));
        }
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-004-08/区分:E/チェック内容:Test get Temp No By Notification No When Not Found
     */
    @Test
    public void getTempNoByNotificationNo_WhenNotFound() {
        // Execute
        List<String> actual = tbl225DAO.getTempNoByNotificationNo(NOTIFICATION_NO);
        // Check result
        assertThat(actual.size() == 0).isTrue();
    }

    /**
     * 案件ID:MDA0110/チェックリストID:UT- MDA0110-001-01/区分:N/チェック内容:Test Find By NotificationNo And Delete Flag False When NotificationNo Is Null
     */
    @Test
    public void findByNotificationNoAndDeleteFlagFalse_WhenNotificationNoIsNull_ThenReturnNull() {
        // Execute
        List<TBL225Entity> actual = tbl225DAO.findByNotificationNoAndDeleteFlagFalse(null);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID:MDA0110/チェックリストID:UT- MDA0110-001-02/区分:N/チェック内容:Test Find By NotificationNo And Delete Flag False When NotificationNo Is Not Existed in Database
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MDA0110-001.sql")
    public void findByNotificationNoAndDeleteFlagFalse_WhenNotificationNoIsNotExistedInDB_ThenReturnNull() {
        // Execute
        String notificationNo = "10000001";
        List<TBL225Entity> actual = tbl225DAO.findByNotificationNoAndDeleteFlagFalse(notificationNo);

        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID:MDA0110/チェックリストID:UT- MDA0110-001-03/区分:I/チェック内容:Test Find By NotificationNo And Delete Flag False When NotificationNo Is Existed in Database
     */
    @Test
    @Sql(value = "classpath:/sql/UT-MDA0110-001.sql")
    public void findByNotificationNoAndDeleteFlagFalse_WhenNotificationNoIsExistedInDB_ThenReturnTBL225Entity() {
        // Execute
        List<TBL225Entity> actual = tbl225DAO.findByNotificationNoAndDeleteFlagFalse(NOTIFICATION_NO);

        // Check result
        actual.forEach(this::assertEntity);
    }

    private void assertEntity(TBL225Entity entity) {
        assertThat(entity.getTempNo()).isEqualTo(TEMP_NO);
        assertThat(entity.getNotificationNo()).isEqualTo(NOTIFICATION_NO);
        assertThat(entity.getTempKbn()).isEqualTo(TEMP_KBN);
        assertThat(entity.getAdviceUserId()).isEqualTo(ADVICE_USER_ID);
        assertThat(entity.getAppendixNo()).isEqualTo(APPENDIX_NO);
        assertThat(entity.getDocumentNo()).isEqualTo(DOCUMENT_NO);
        assertThat(entity.getNoticeDate()).isEqualTo(NOTICE_DATE);
        assertThat(entity.getRecipientNameApartment()).isEqualTo(RECIPIENT_NAME_APARTMENT);
        assertThat(entity.getRecipientNameUser()).isEqualTo(RECIPIENT_NAME_USER);
        assertThat(entity.getSender()).isEqualTo(SENDER);
        assertThat(entity.getNotificationDate()).isEqualTo(NOTIFICATION_DATE);
        assertThat(entity.getEvidenceBar()).isEqualTo(EVIDENCE_BAR);
        assertThat(entity.getEvidenceNo()).isEqualTo(EVIDENCE_NO);
        assertThat(entity.getAddress()).isEqualTo(ADDRESS);
        assertThat(entity.getApartmentName()).isEqualTo(APARTMENT_NAME);
        assertThat(entity.getAdviceDetails()).isEqualTo(ADVICE_DETAILS);
        assertThat(entity.getNotificationMethodCode()).isEqualTo(NOTIFICATION_METHOD_CODE);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
    }

    private TBL225Entity generateTBL225Entity() {
        TBL225Entity entity = new TBL225Entity();

        entity.setTempNo(TEMP_NO);
        entity.setNotificationNo(NOTIFICATION_NO);
        entity.setTempKbn(TEMP_KBN);
        entity.setAdviceUserId(ADVICE_USER_ID);
        entity.setAppendixNo(APPENDIX_NO);
        entity.setDocumentNo(DOCUMENT_NO);
        entity.setNoticeDate(NOTICE_DATE);
        entity.setRecipientNameApartment(RECIPIENT_NAME_APARTMENT);
        entity.setRecipientNameUser(RECIPIENT_NAME_USER);
        entity.setSender(SENDER);
        entity.setNotificationDate(NOTIFICATION_DATE);
        entity.setEvidenceBar(EVIDENCE_BAR);
        entity.setEvidenceNo(EVIDENCE_NO);
        entity.setAddress(ADDRESS);
        entity.setApartmentName(APARTMENT_NAME);
        entity.setAdviceDetails(ADVICE_DETAILS);
        entity.setNotificationMethodCode(NOTIFICATION_METHOD_CODE);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);

        return entity;
    }
}
