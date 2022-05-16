/*
 * @(#) TBL220DAOTest.java
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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;

/**
 * @author nvlong1
 *
 */
public class TBL220DAOTest extends AbstractDaoTest {

    @Autowired
    private TBL220DAO tbl220DAO;

    private final String ADVICE_NO = "2000000000";
    private final String NOTIFICATION_NO = "";
    private final String ADVICE_USER_ID = "G0000010";
    private final String APPENDIX_NO = "第　　号様式(第　　関係）";
    private final String DOCUMENT_NO = "";
    private final String NOTICE_DATE = "20191129";
    private final String RECIPIENT_NAME_APARTMENT = "Viet Nam";
    private final String RECIPIENT_NAME_USER = "区分所有者　0";
    private final String SENDER = "東京都知事山本　泰人";
    private final String NOTIFICATION_DATE = "20191129";
    private final String EVIDENCE_BAR = "1";
    private final String EVIDENCE_NO = "1";
    private final String ADDRESS = "Da Nang";
    private final String APARTMENT_NAME = "Viet Nam";
    private final String ADVICE_DETAILS = "sg,";
    private final String NOTIFICATION_METHOD_CODE = "1";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "1000000010";
    private final Timestamp UPDATE_DATETIME = null;
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-003-01/区分:N/チェック内容:Test Get Advisory Notice By Id Success
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GFA0110-003.sql")
    public void getAdvisoryNoticeById_WhenExist_ShouldBeExist() {
        // Execute
        TBL220Entity actual = tbl220DAO.getAdvisoryNoticeById(ADVICE_NO);

        // Check result
        assertEntity(actual);
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-003-02/区分:E/チェック内容:Test Get Advisory Notice By Id Return Null When Not Exist
     */
    @Test
    public void getAdvisoryNoticeById_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL220Entity actual = tbl220DAO.getAdvisoryNoticeById(null);
        // Check result
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-003-03/区分:I/チェック内容:Test save success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        TBL220Entity expected = generateTBL220Entity();

        // Execute
        TBL220Entity actual = tbl220DAO.save(expected);

        // Check result
        assertEntity(actual);
    }

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-003-04/区分:E/チェック内容:Test save fail will throw exception
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl220DAO.save(null);
    }

    private void assertEntity(TBL220Entity entity) {
        assertThat(entity.getAdviceNo()).isEqualTo(ADVICE_NO);
        assertThat(entity.getNotificationNo()).isEqualTo(NOTIFICATION_NO);
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

    private TBL220Entity generateTBL220Entity() {
        TBL220Entity entity = new TBL220Entity();

        entity.setAdviceNo(ADVICE_NO);
        entity.setNotificationNo(NOTIFICATION_NO);
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
