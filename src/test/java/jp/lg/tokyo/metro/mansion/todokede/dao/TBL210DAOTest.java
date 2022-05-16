/*
 * @(#) TBL210DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019-12-26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.DeleteFlag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL210Entity;

/**
 * @author pdquang
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL210DAOTest {
    @Autowired
    private TBL210DAO tbl210DAO;
    
    private final LocalDateTime ACCEPT_TIME = LocalDateTime.parse("2019-10-10 10:10:10", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDate NOTICE_DATE = LocalDate.parse("20191212", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
    private final LocalDate NOTIFICATION_DATE = LocalDate.parse("20191212", DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));

    /**
    * 案件ID:GEA0110/チェックリストID:UT- GEA0110-034-/区分:E/チェック内容: Test Get Accept By AcceptNo When Not Exist Should Be Null
    */
    @Test
    public void testGetAcceptByAcceptNo_WhenNotExist_ShouldBeNull() {
     // Execute
        TBL210Entity actual = tbl210DAO.getAcceptByAcceptNo(null);
        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
    * 案件ID:GEA0110/チェックリストID:UT- GEA0110-035-/区分:N/チェック内容: Test Get Accept By AcceptNo When Exist Should Be Exist
    */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-003.sql")
    public void testGetAcceptByAcceptNo_WhenExist_ShouldBeExist() {
        // Execute
        TBL210Entity actual = tbl210DAO.getAcceptByAcceptNo("1000000001");
        // Check result
        assertTbl210Entity(actual);
    }

    /**
     * 案件ID:GJA0120/チェックリストID:UT- GJA0120-005/区分:E/チェック内容: Test Get Accept By Notification No When Not Exist Should Be Null
     */
    @Test
    public void testGetAcceptByNotificationNo_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL210Entity actual = tbl210DAO.getAcceptByAcceptNo(null);
        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
    * 案件ID:GJA0120/チェックリストID:UT- GJA0120-006/区分:I/チェック内容: Test Get Accept By Notification No When Exist Should Be Exist
    */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-003.sql")
    public void testGetAcceptByNotificationNo_WhenExist_ShouldBeExist() {
        // Execute
        TBL210Entity actual = tbl210DAO.getAcceptByNotificationNo("1000000002");
        // Check result
        assertTbl210Entity(actual);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-011／区分：N／チェック内容：TBL210への保存データのテスト
     */
    @Test
    public void saveData() {
        TBL210Entity entity = prepareDataGDA0110_011();
        entity = tbl210DAO.save(entity);

        assertThat(entity.getAcceptNo()).isEqualTo("1000010001");
        assertThat(entity.getNotificationNo()).isEqualTo("1000010001");
        assertThat(entity.getAcceptTime()).isEqualTo(LocalDateTime.of(2019,12,12,12,12,12));
        assertThat(entity.getAcceptUserId()).isEqualTo("1000010001");
        assertThat(entity.getAcceptUserName()).isEqualTo("UserName");
        assertThat(entity.getAppendixNo()).isEqualTo("AppendixNo");
        assertThat(entity.getDocumentNo()).isEqualTo("DocumentNo");
        assertThat(entity.getNoticeDate()).isEqualTo(LocalDate.of(2019,12,12));
        assertThat(entity.getRecipientNameApartment()).isEqualTo("RecipientNameApartment");
        assertThat(entity.getRecipientNameUser()).isEqualTo("RecipientNameUser");
        assertThat(entity.getSender()).isEqualTo("Sender");
        assertThat(entity.getNotificationDate()).isEqualTo(LocalDate.of(2019,12,12));
        assertThat(entity.getEvidenceBar()).isEqualTo("2");
        assertThat(entity.getEvidenceNo()).isEqualTo("1");
        assertThat(entity.getRemarks()).isEqualTo("Remarks");
        assertThat(entity.getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(entity.getModifyDetails()).isEqualTo("ModifyDetails");
        assertThat(entity.getNotificationMethodCode()).isEqualTo("1");
        assertThat(entity.getDeleteFlag()).isEqualTo(DeleteFlag.NOT_DELETE.getFlag());
        assertThat(entity.getUpdateUserId()).isEqualTo("1000010001");
        assertThat(entity.getUpdateDatetime()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2019,12,12,12,12,12)));
    }

    private TBL210Entity prepareDataGDA0110_011() {
        final String CORRECTION = "1";
        TBL210Entity entity = new TBL210Entity();
        entity.setAcceptNo("1000010001");
        entity.setNotificationNo("1000010001");
        entity.setAcceptTime(LocalDateTime.of(2019,12,12,12,12,12));
        entity.setAcceptUserId("1000010001");
        entity.setAcceptUserName("UserName");
        entity.setAppendixNo("AppendixNo");
        entity.setDocumentNo("DocumentNo");
        entity.setNoticeDate(LocalDate.of(2019,12,12));
        entity.setRecipientNameApartment("RecipientNameApartment");
        entity.setRecipientNameUser("RecipientNameUser");
        entity.setSender("Sender");
        entity.setNotificationDate(LocalDate.of(2019,12,12));
        entity.setEvidenceBar("2");
        entity.setEvidenceNo("1");
        entity.setRemarks("Remarks");
        entity.setAuthorityModifyFlag(CORRECTION);
        entity.setModifyDetails("ModifyDetails");
        entity.setNotificationMethodCode("1");
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId("1000010001");
        entity.setUpdateDatetime(Timestamp.valueOf(LocalDateTime.of(2019,12,12,12,12,12)));
        return entity;
    }
    
    private void assertTbl210Entity(TBL210Entity entityTbl210) {
        assertThat(entityTbl210.getAcceptNo()).isEqualTo("1000000001");
        assertThat(entityTbl210.getNotificationNo()).isEqualTo("1000000002");
        assertThat(entityTbl210.getAcceptTime()).isEqualTo(ACCEPT_TIME);
        assertThat(entityTbl210.getAcceptUserId()).isEqualTo("1000000020");
        assertThat(entityTbl210.getAcceptUserName()).isEqualTo("QQ");
        assertThat(entityTbl210.getAppendixNo()).isEqualTo("1000000001");
        assertThat(entityTbl210.getDocumentNo()).isEqualTo("1000000002");
        assertThat(entityTbl210.getNoticeDate()).isEqualTo(NOTICE_DATE);
        assertThat(entityTbl210.getRecipientNameApartment()).isEqualTo(null);
        assertThat(entityTbl210.getRecipientNameUser()).isEqualTo(null);
        assertThat(entityTbl210.getSender()).isEqualTo("Abcd");
        assertThat(entityTbl210.getNotificationDate()).isEqualTo(NOTIFICATION_DATE);
        assertThat(entityTbl210.getEvidenceBar()).isEqualTo("1");
        assertThat(entityTbl210.getEvidenceNo()).isEqualTo("1");
        assertThat(entityTbl210.getRemarks()).isEqualTo("KSKSS");
        assertThat(entityTbl210.getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(entityTbl210.getModifyDetails()).isEqualTo("test");
        assertThat(entityTbl210.getNotificationMethodCode()).isEqualTo("1");
        assertThat(entityTbl210.getDeleteFlag()).isEqualTo("0");
        assertThat(entityTbl210.getUpdateUserId()).isEqualTo(null);
        assertThat(entityTbl210.getUpdateDatetime()).isEqualTo(null);
    }
}
