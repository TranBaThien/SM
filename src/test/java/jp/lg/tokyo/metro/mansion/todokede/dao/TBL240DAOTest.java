/*
 * @(#) TBL240DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbtung
 * Create Date: Dec 24, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL240;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nbtung
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL240DAOTest {

    @Autowired
    private TBL240DAO tbl240DAO;

    @Autowired
    private SequenceUtil sequenceUtil;

    private final String APARTMENT_ID = "1000000002";
    private final String APPENDIX_NO = "第場合号様式";
    private final String DOCUMENT_NO = "2019八丈町文";
    private final String NOTICE_DATE = "20191224";
    private final String SUPERVISED_NOTICE_TYPE_CODE = "1回目用";
    private final String SENDER = "東京都知事森下　一男";
    private final String MAILING_CODE = "4";
    private final String RECIPIENT_NAME_APARTMENT = "Tokyo";
    private final String RECIPIENT_NAME_USER = "Tokyo";
    private final String EVIDENCE_BAR = "16";
    private final String EVIDENCE_NO = "2";
    private final String PERIOD_EVIDENCE = "第５条第１項";
    private final String LAST_NOTICE_DATE = "";
    private final String LAST_DOCUMENT_NO = "";
    private final String ADDRESS = "東京都小笠原村小笠原村";
    private final String NOTIFICATION_FORMAT_NAME = "";
    private final String NOTIFICATION_TIMELIMIT = "20200930";
    private final String CONTACT = "";
    private final String NOTIFICATION_NO = "";
    private final int SUPERVISED_NOTICE_COUNT = 1;
    private final String UPDATE_USER_ID = "G0000025";
    private final String TEXTADDRESS1 = "";
    private final String TEXTADDRESS2 = "";
    private final String APARTMENT_NAME = "アパート 1";

    private final Timestamp UPDATE_DATE_TIME = Timestamp
            .valueOf(LocalDateTime.parse("2019-12-30 15:16:39", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    @Autowired
    @Before
    public void setUp() throws Exception {

    }

    private TBL240Entity generateTBL240Entity() {

        TBL240Entity entity = new TBL240Entity();

        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL240),
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

    /**
     * 案件ID：GIA0110／チェックリストID：UT- GIA0110-001／区分：N／チェック内容：Test save data to tbl240
     * when success
     * 
     * @throws BusinessException
     */
    @Test
    public void testSaveTBL240Success() throws BusinessException {
        // Prepare data
        TBL240Entity expected = generateTBL240Entity();

        // Execute
        TBL240Entity result = tbl240DAO.save(expected);

        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    /**
     * 案件ID：GIA0110／チェックリストID：UT- GIA0110-002／区分：E／チェック内容：Test save data to tbl240
     * when fail and throw exception
     * 
     * @throws BusinessException
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testSaveTBL240WhenFailThrowException() throws BusinessException {
        // Execute
        tbl240DAO.save(null);
    }

    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-038／区分：E／チェック内容：test Get Supervised Notice
     * By SupervisedNoticeNo When Not Exist Should Be Null
     */
    @Test
    public void testGetSupervisedNoticeBySupervisedNoticeNo_WhenNotExist_ShouldBeNull() {
        // Execute
        TBL240Entity actual = tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(null);
        // Check result
        assertThat(actual).isEqualTo(null);
    }

    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-039／区分: I／チェック内容：test Get Supervised Notice
     * By SupervisedNoticeNo When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-005.sql")
    public void testGetSupervisedNoticeBySupervisedNoticeNo_WhenExist_ShouldBeExist() {
        // Execute
        TBL240Entity actual = tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo("1000000001");
        // Check result
        assertTBL240Entity(actual);
    }

    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-040／区分：E／チェック内容： Test Get Supervised
     * Notice By ApartmentId And NotificationNo When Not Exist Should Be Null
     */
    @Test
    public void testGetSupervisedNoticeByApartmentIdAndNotificationNo_WhenNotExist_ShouldBeNull() {
        // Execute
        List<TBL240Entity> actual = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(null);
        // Check result
        Assert.assertEquals(actual.size(), 0);
    }

    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-041／区分：N／チェック内容： Test Get Supervised
     * Notice By ApartmentId And NotificationNo When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-006.sql")
    public void testGetSupervisedNoticeByApartmentIdAndNotificationNo_WhenExist_ShouldBeExist() {
        // Execute
        List<TBL240Entity> actual = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo("1000000001");
        // Check result
        assertListTBL240Entity(actual);
    }
    
    /**
     * 案件ID：GJA0120／チェックリストID：UT- GJA0120-009／区分：E／チェック内容： Test Get Supervised
     * Notice By ApartmentId When Not Exist Should Be Null
     */
    @Test
    public void testGetSupervisedNoticeByApartmentId_WhenNotExist_ShouldBeNull() {
        // Execute
        List<TBL240Entity> actual = tbl240DAO.getSupervisedNoticeByApartmentId(null);
        // Check result
        Assert.assertEquals(actual.size(), 0);
    }
    
    /**
     * 案件ID：GJA0120／チェックリストID：UT- GJA0120-010／区分: I／チェック内容： Test Get Supervised
     * Notice By ApartmentId When Exist Should Be Exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GJA0120-010.sql")
    public void testGetSupervisedNoticeByApartmentId_WhenExist_ShouldBeExist() {
        // Execute
        List<TBL240Entity> actual = tbl240DAO.getSupervisedNoticeByApartmentId("1000000001");
        // Check result
        assertListTBL240Entity2(actual);
    }

    private void assertListTBL240Entity(List<TBL240Entity> listTbl240Entity) {
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeNo()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(0).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(0).getAppendixNo()).isEqualTo("第1");
        assertThat(listTbl240Entity.get(0).getDocumentNo()).isEqualTo("2019住住マ　第　号号");
        assertThat(listTbl240Entity.get(0).getNoticeDate()).isEqualTo("20191230");
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeTypeCode()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getSender()).isEqualTo("東京都知事　小池　百合子");
        assertThat(listTbl240Entity.get(0).getMailingCode()).isEqualTo("4");
        assertThat(listTbl240Entity.get(0).getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(0).getRecipientNameUser()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getEvidenceBar()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getEvidenceNo()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getPeriodEvidence()).isEqualTo("4");
        assertThat(listTbl240Entity.get(0).getLastNoticeDate()).isEqualTo("20191228");
        assertThat(listTbl240Entity.get(0).getLastDocumentNo()).isEqualTo("第　号");
        assertThat(listTbl240Entity.get(0).getAddress()).isEqualTo("東京都東京都Da Nang");
        assertThat(listTbl240Entity.get(0).getApartmentName()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(0).getNotificationFormatName()).isEqualTo("1");
        assertThat(listTbl240Entity.get(0).getNotificationTimelimit()).isEqualTo("20200930");
        assertThat(listTbl240Entity.get(0).getContact()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getNotificationNo()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeCount()).isEqualTo(3);
        assertThat(listTbl240Entity.get(0).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl240Entity.get(0).getUpdateUserId()).isEqualTo("G0000004");
        assertThat(listTbl240Entity.get(0).getUpdateDatetime()).isEqualTo(UPDATE_DATE_TIME);
        assertThat(listTbl240Entity.get(0).getTextaddress1()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getTextaddress2()).isEqualTo("されます。");

        assertThat(listTbl240Entity.get(1).getSupervisedNoticeNo()).isEqualTo("1000000002");
        assertThat(listTbl240Entity.get(1).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(1).getAppendixNo()).isEqualTo("第1");
        assertThat(listTbl240Entity.get(1).getDocumentNo()).isEqualTo("2019住住マ　第　号号");
        assertThat(listTbl240Entity.get(1).getNoticeDate()).isEqualTo("20191230");
        assertThat(listTbl240Entity.get(1).getSupervisedNoticeTypeCode()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getSender()).isEqualTo("東京都知事　小池　百合子");
        assertThat(listTbl240Entity.get(1).getMailingCode()).isEqualTo("4");
        assertThat(listTbl240Entity.get(1).getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(1).getRecipientNameUser()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getEvidenceBar()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getEvidenceNo()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getPeriodEvidence()).isEqualTo("4");
        assertThat(listTbl240Entity.get(1).getLastNoticeDate()).isEqualTo("20191228");
        assertThat(listTbl240Entity.get(1).getLastDocumentNo()).isEqualTo("第　号");
        assertThat(listTbl240Entity.get(1).getAddress()).isEqualTo("東京都東京都Da Nang");
        assertThat(listTbl240Entity.get(1).getApartmentName()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(1).getNotificationFormatName()).isEqualTo("1");
        assertThat(listTbl240Entity.get(1).getNotificationTimelimit()).isEqualTo("20200930");
        assertThat(listTbl240Entity.get(1).getContact()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getNotificationNo()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(1).getSupervisedNoticeCount()).isEqualTo(3);
        assertThat(listTbl240Entity.get(1).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl240Entity.get(1).getUpdateUserId()).isEqualTo("G0000004");
        assertThat(listTbl240Entity.get(1).getUpdateDatetime()).isEqualTo(UPDATE_DATE_TIME);
        assertThat(listTbl240Entity.get(1).getTextaddress1()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getTextaddress2()).isEqualTo("されます。");
    }

    private void assertListTBL240Entity2(List<TBL240Entity> listTbl240Entity) {
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeNo()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(0).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(0).getAppendixNo()).isEqualTo("第1");
        assertThat(listTbl240Entity.get(0).getDocumentNo()).isEqualTo("2019住住マ　第　号号");
        assertThat(listTbl240Entity.get(0).getNoticeDate()).isEqualTo("20191230");
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeTypeCode()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getSender()).isEqualTo("東京都知事　小池　百合子");
        assertThat(listTbl240Entity.get(0).getMailingCode()).isEqualTo("4");
        assertThat(listTbl240Entity.get(0).getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(0).getRecipientNameUser()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getEvidenceBar()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getEvidenceNo()).isEqualTo("2");
        assertThat(listTbl240Entity.get(0).getPeriodEvidence()).isEqualTo("4");
        assertThat(listTbl240Entity.get(0).getLastNoticeDate()).isEqualTo("20191228");
        assertThat(listTbl240Entity.get(0).getLastDocumentNo()).isEqualTo("第　号");
        assertThat(listTbl240Entity.get(0).getAddress()).isEqualTo("東京都東京都Da Nang");
        assertThat(listTbl240Entity.get(0).getApartmentName()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(0).getNotificationFormatName()).isEqualTo("1");
        assertThat(listTbl240Entity.get(0).getNotificationTimelimit()).isEqualTo("20200930");
        assertThat(listTbl240Entity.get(0).getContact()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getNotificationNo()).isEqualTo(null);
        assertThat(listTbl240Entity.get(0).getSupervisedNoticeCount()).isEqualTo(3);
        assertThat(listTbl240Entity.get(0).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl240Entity.get(0).getUpdateUserId()).isEqualTo("G0000004");
        assertThat(listTbl240Entity.get(0).getUpdateDatetime()).isEqualTo(UPDATE_DATE_TIME);
        assertThat(listTbl240Entity.get(0).getTextaddress1()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(0).getTextaddress2()).isEqualTo("されます。");

        assertThat(listTbl240Entity.get(1).getSupervisedNoticeNo()).isEqualTo("1000000002");
        assertThat(listTbl240Entity.get(1).getApartmentId()).isEqualTo("1000000001");
        assertThat(listTbl240Entity.get(1).getAppendixNo()).isEqualTo("第1");
        assertThat(listTbl240Entity.get(1).getDocumentNo()).isEqualTo("2019住住マ　第　号号");
        assertThat(listTbl240Entity.get(1).getNoticeDate()).isEqualTo("20191230");
        assertThat(listTbl240Entity.get(1).getSupervisedNoticeTypeCode()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getSender()).isEqualTo("東京都知事　小池　百合子");
        assertThat(listTbl240Entity.get(1).getMailingCode()).isEqualTo("4");
        assertThat(listTbl240Entity.get(1).getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(1).getRecipientNameUser()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getEvidenceBar()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getEvidenceNo()).isEqualTo("2");
        assertThat(listTbl240Entity.get(1).getPeriodEvidence()).isEqualTo("4");
        assertThat(listTbl240Entity.get(1).getLastNoticeDate()).isEqualTo("20191228");
        assertThat(listTbl240Entity.get(1).getLastDocumentNo()).isEqualTo("第　号");
        assertThat(listTbl240Entity.get(1).getAddress()).isEqualTo("東京都東京都Da Nang");
        assertThat(listTbl240Entity.get(1).getApartmentName()).isEqualTo("Tokyo");
        assertThat(listTbl240Entity.get(1).getNotificationFormatName()).isEqualTo("1");
        assertThat(listTbl240Entity.get(1).getNotificationTimelimit()).isEqualTo("20200930");
        assertThat(listTbl240Entity.get(1).getContact()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getNotificationNo()).isEqualTo(null);
        assertThat(listTbl240Entity.get(1).getSupervisedNoticeCount()).isEqualTo(3);
        assertThat(listTbl240Entity.get(1).getDeleteFlag()).isEqualTo("0");
        assertThat(listTbl240Entity.get(1).getUpdateUserId()).isEqualTo("G0000004");
        assertThat(listTbl240Entity.get(1).getUpdateDatetime()).isEqualTo(UPDATE_DATE_TIME);
        assertThat(listTbl240Entity.get(1).getTextaddress1()).isEqualTo("されます。");
        assertThat(listTbl240Entity.get(1).getTextaddress2()).isEqualTo("されます。");
    }

    private void assertTBL240Entity(TBL240Entity tbl240Entity) {
        assertThat(tbl240Entity.getSupervisedNoticeNo()).isEqualTo("1000000001");
        assertThat(tbl240Entity.getApartmentId()).isEqualTo("1000000001");
        assertThat(tbl240Entity.getAppendixNo()).isEqualTo("第1");
        assertThat(tbl240Entity.getDocumentNo()).isEqualTo("2019住住マ　第　号号");
        assertThat(tbl240Entity.getNoticeDate()).isEqualTo("20191230");
        assertThat(tbl240Entity.getSupervisedNoticeTypeCode()).isEqualTo("2");
        assertThat(tbl240Entity.getSender()).isEqualTo("東京都知事　小池　百合子");
        assertThat(tbl240Entity.getMailingCode()).isEqualTo("4");
        assertThat(tbl240Entity.getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(tbl240Entity.getRecipientNameUser()).isEqualTo("されます。");
        assertThat(tbl240Entity.getEvidenceBar()).isEqualTo("2");
        assertThat(tbl240Entity.getEvidenceNo()).isEqualTo("2");
        assertThat(tbl240Entity.getPeriodEvidence()).isEqualTo("4");
        assertThat(tbl240Entity.getLastNoticeDate()).isEqualTo("20191228");
        assertThat(tbl240Entity.getLastDocumentNo()).isEqualTo("第　号");
        assertThat(tbl240Entity.getAddress()).isEqualTo("東京都東京都Da Nang");
        assertThat(tbl240Entity.getApartmentName()).isEqualTo("Tokyo");
        assertThat(tbl240Entity.getNotificationFormatName()).isEqualTo("1");
        assertThat(tbl240Entity.getNotificationTimelimit()).isEqualTo("20200930");
        assertThat(tbl240Entity.getContact()).isEqualTo("されます。");
        assertThat(tbl240Entity.getNotificationNo()).isEqualTo(null);
        assertThat(tbl240Entity.getSupervisedNoticeCount()).isEqualTo(3);
        assertThat(tbl240Entity.getDeleteFlag()).isEqualTo("0");
        assertThat(tbl240Entity.getUpdateUserId()).isEqualTo("G0000004");
        assertThat(tbl240Entity.getUpdateDatetime()).isEqualTo(UPDATE_DATE_TIME);
        assertThat(tbl240Entity.getTextaddress1()).isEqualTo("されます。");
        assertThat(tbl240Entity.getTextaddress2()).isEqualTo("されます。");
    }
}
