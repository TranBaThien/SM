/*
 * @(#) TBS001DAOTest.java
 *
 * Copyright(C) 2020 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Jan 6, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;

/**
 * @author nvlong1
 *
 */
@Sql(value = "classpath:/sql/UT-TBS001-001.sql")
public class TBS001DAOTest extends AbstractDaoTest {

    @Autowired
    private TBS001DAO tbs001dao;

    private final String SEQUENCE_NO_TBL100_APARTMENT_ID = "01";
    private final String COLUMN_NAME_TBL100_APARTMENT_ID = "APARTMENT_ID";
    private final int CURRENT_NO_TBL100_APARTMENT_ID = 48;
    private final String DELETE_FLAG_TBL100_APARTMENT_ID = "0";
    private final int INCREMENT_NO_TBL100_APARTMENT_ID = 1;
    private final String PREFIX_TBL100_APARTMENT_ID = null;
    private final int START_NO_TBL100_APARTMENT_ID = 0;
    private final String TBL_ID_TBL100_APARTMENT_ID = "TBL100";
    private final Timestamp UPDATE_DATETIME_TBL100_APARTMENT_ID = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 14:29:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL100_APARTMENT_ID = "G0000030";

    private void assertTbl100ApartmentId(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL100_APARTMENT_ID);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL100_APARTMENT_ID);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL100_APARTMENT_ID);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL100_APARTMENT_ID);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL100_APARTMENT_ID);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL100_APARTMENT_ID);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL100_APARTMENT_ID);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL100_APARTMENT_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL100_APARTMENT_ID);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL100_APARTMENT_ID);
    }

    private final String SEQUENCE_NO_TBL105_HISTORY_NUMBER = "03";
    private final String COLUMN_NAME_TBL105_HISTORY_NUMBER = "HISTORY_NUMBER";
    private final int CURRENT_NO_TBL105_HISTORY_NUMBER = 1000000138;
    private final String DELETE_FLAG_TBL105_HISTORY_NUMBER = "0";
    private final int INCREMENT_NO_TBL105_HISTORY_NUMBER = 1;
    private final String PREFIX_TBL105_HISTORY_NUMBER = "M";
    private final int START_NO_TBL105_HISTORY_NUMBER = 1000000000;
    private final String TBL_ID_TBL105_HISTORY_NUMBER = "TBL105";
    private final Timestamp UPDATE_DATETIME_TBL105_HISTORY_NUMBER = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 17:56:35", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL105_HISTORY_NUMBER = "G0000018";

    private void assertTbl105HistoryNumber(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL105_HISTORY_NUMBER);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL105_HISTORY_NUMBER);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL105_HISTORY_NUMBER);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL105_HISTORY_NUMBER);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL105_HISTORY_NUMBER);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL105_HISTORY_NUMBER);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL105_HISTORY_NUMBER);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL105_HISTORY_NUMBER);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL105_HISTORY_NUMBER);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL105_HISTORY_NUMBER);

    }

    private final String SEQUENCE_NO_TBL110_LOGIN_ID = "04";
    private final String COLUMN_NAME_TBL110_LOGIN_ID = "LOGIN_ID";
    private final int CURRENT_NO_TBL110_LOGIN_ID = 62;
    private final String DELETE_FLAG_TBL110_LOGIN_ID = "0";
    private final int INCREMENT_NO_TBL110_LOGIN_ID = 1;
    private final String PREFIX_TBL110_LOGIN_ID = "M";
    private final int START_NO_TBL110_LOGIN_ID = 0;
    private final String TBL_ID_TBL110_LOGIN_ID = "TBL110";
    private final Timestamp UPDATE_DATETIME_TBL110_LOGIN_ID = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 14:29:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL110_LOGIN_ID = "G0000030";

    private void assertTbl110LoginId(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL110_LOGIN_ID);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL110_LOGIN_ID);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL110_LOGIN_ID);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL110_LOGIN_ID);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL110_LOGIN_ID);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL110_LOGIN_ID);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL110_LOGIN_ID);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL110_LOGIN_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL110_LOGIN_ID);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL110_LOGIN_ID);

    }

    private final String SEQUENCE_NO_TBL120_LOGIN_ID = "05";
    private final String COLUMN_NAME_TBL120_LOGIN_ID = "LOGIN_ID";
    private final int CURRENT_NO_TBL120_LOGIN_ID = 0;
    private final String DELETE_FLAG_TBL120_LOGIN_ID = "0";
    private final int INCREMENT_NO_TBL120_LOGIN_ID = 1;
    private final String PREFIX_TBL120_LOGIN_ID = "G,A";
    private final int START_NO_TBL120_LOGIN_ID = 0;
    private final String TBL_ID_TBL120_LOGIN_ID = "TBL120";
    private final Timestamp UPDATE_DATETIME_TBL120_LOGIN_ID = null;
    private final String UPDATE_USER_ID_TBL120_LOGIN_ID = null;

    private void assertTbl120LoginId(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL120_LOGIN_ID);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL120_LOGIN_ID);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL120_LOGIN_ID);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL120_LOGIN_ID);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL120_LOGIN_ID);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL120_LOGIN_ID);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL120_LOGIN_ID);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL120_LOGIN_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL120_LOGIN_ID);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL120_LOGIN_ID);

    }

    private final String SEQUENCE_NO_TBL120_USER_ID = "06";
    private final String COLUMN_NAME_TBL120_USER_ID = "USER_ID";
    private final int CURRENT_NO_TBL120_USER_ID = 1000000067;
    private final String DELETE_FLAG_TBL120_USER_ID = "0";
    private final int INCREMENT_NO_TBL120_USER_ID = 1;
    private final String PREFIX_TBL120_USER_ID = null;
    private final int START_NO_TBL120_USER_ID = 1000000000;
    private final String TBL_ID_TBL120_USER_ID = "TBL120";
    private final Timestamp UPDATE_DATETIME_TBL120_USER_ID = Timestamp.valueOf(LocalDateTime.parse("2020-01-04 18:40:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL120_USER_ID = "G0000030";

    private void assertTbl120UserId(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL120_USER_ID);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL120_USER_ID);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL120_USER_ID);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL120_USER_ID);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL120_USER_ID);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL120_USER_ID);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL120_USER_ID);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL120_USER_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL120_USER_ID);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL120_USER_ID);

    }

    private final String SEQUENCE_NO_TBL200_NOTIFICATION_NO = "07";
    private final String COLUMN_NAME_TBL200_NOTIFICATION_NO = "NOTIFICATION_NO";
    private final int CURRENT_NO_TBL200_NOTIFICATION_NO = 1000000004;
    private final String DELETE_FLAG_TBL200_NOTIFICATION_NO = "0";
    private final int INCREMENT_NO_TBL200_NOTIFICATION_NO = 1;
    private final String PREFIX_TBL200_NOTIFICATION_NO = null;
    private final int START_NO_TBL200_NOTIFICATION_NO = 1000000000;
    private final String TBL_ID_TBL200_NOTIFICATION_NO = "TBL200";
    private final Timestamp UPDATE_DATETIME_TBL200_NOTIFICATION_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 10:22:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL200_NOTIFICATION_NO = "G0000020";

    private void assertTbl200NotificationNo(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL200_NOTIFICATION_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL200_NOTIFICATION_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL200_NOTIFICATION_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL200_NOTIFICATION_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL200_NOTIFICATION_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL200_NOTIFICATION_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL200_NOTIFICATION_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL200_NOTIFICATION_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL200_NOTIFICATION_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL200_NOTIFICATION_NO);

    }

    private final String SEQUENCE_NO_TBL200_RECEPTION_NO = "08";
    private final String COLUMN_NAME_TBL200_RECEPTION_NO = "RECEPTION_NO";
    private final int CURRENT_NO_TBL200_RECEPTION_NO = 1000000013;
    private final String DELETE_FLAG_TBL200_RECEPTION_NO = "0";
    private final int INCREMENT_NO_TBL200_RECEPTION_NO = 1;
    private final String PREFIX_TBL200_RECEPTION_NO = null;
    private final int START_NO_TBL200_RECEPTION_NO = 1000000000;
    private final String TBL_ID_TBL200_RECEPTION_NO = "TBL200";
    private final Timestamp UPDATE_DATETIME_TBL200_RECEPTION_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 17:56:35", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL200_RECEPTION_NO = "G0000018";

    private void assertTbl200ReceptionNo(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL200_RECEPTION_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL200_RECEPTION_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL200_RECEPTION_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL200_RECEPTION_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL200_RECEPTION_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL200_RECEPTION_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL200_RECEPTION_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL200_RECEPTION_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL200_RECEPTION_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL200_RECEPTION_NO);

    }

    private final String SEQUENCE_NO_TBL210_ACCEPT_NO = "09";
    private final String COLUMN_NAME_TBL210_ACCEPT_NO = "ACCEPT_NO";
    private final int CURRENT_NO_TBL210_ACCEPT_NO = 1000000003;
    private final String DELETE_FLAG_TBL210_ACCEPT_NO = "0";
    private final int INCREMENT_NO_TBL210_ACCEPT_NO = 1;
    private final String PREFIX_TBL210_ACCEPT_NO = null;
    private final int START_NO_TBL210_ACCEPT_NO = 1000000000;
    private final String TBL_ID_TBL210_ACCEPT_NO = "TBL210";
    private final Timestamp UPDATE_DATETIME_TBL210_ACCEPT_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 10:22:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL210_ACCEPT_NO = "G0000020";

    private void assertTbl210AcceptNo(TBS001Entity entity) {

        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL210_ACCEPT_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL210_ACCEPT_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL210_ACCEPT_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL210_ACCEPT_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL210_ACCEPT_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL210_ACCEPT_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL210_ACCEPT_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL210_ACCEPT_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL210_ACCEPT_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL210_ACCEPT_NO);

    }

    private final String SEQUENCE_NO_TBL220_ADVICE_NO = "10";
    private final String COLUMN_NAME_TBL220_ADVICE_NO = "ADVICE_NO";
    private final int CURRENT_NO_TBL220_ADVICE_NO = 1000000031;
    private final String DELETE_FLAG_TBL220_ADVICE_NO = "0";
    private final int INCREMENT_NO_TBL220_ADVICE_NO = 1;
    private final String PREFIX_TBL220_ADVICE_NO = null;
    private final int START_NO_TBL220_ADVICE_NO = 1000000000;
    private final String TBL_ID_TBL220_ADVICE_NO = "TBL220";
    private final Timestamp UPDATE_DATETIME_TBL220_ADVICE_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 10:09:24", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL220_ADVICE_NO = "G0000010";

    private void assertTbl220AcceptNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL220_ADVICE_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL220_ADVICE_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL220_ADVICE_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL220_ADVICE_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL220_ADVICE_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL220_ADVICE_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL220_ADVICE_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL220_ADVICE_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL220_ADVICE_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL220_ADVICE_NO);

    }

    private final String SEQUENCE_NO_TBL230_INVESTIGATION_NO = "11";
    private final String COLUMN_NAME_TBL230_INVESTIGATION_NO = "INVESTIGATION_NO";
    private final int CURRENT_NO_TBL230_INVESTIGATION_NO = 1000000063;
    private final String DELETE_FLAG_TBL230_INVESTIGATION_NO = "0";
    private final int INCREMENT_NO_TBL230_INVESTIGATION_NO = 1;
    private final String PREFIX_TBL230_INVESTIGATION_NO = null;
    private final int START_NO_TBL230_INVESTIGATION_NO = 1000000000;
    private final String TBL_ID_TBL230_INVESTIGATION_NO = "TBL230";
    private final Timestamp UPDATE_DATETIME_TBL230_INVESTIGATION_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-05 18:13:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL230_INVESTIGATION_NO = "G0000020";

    private void assertTbl230InvestigationNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL230_INVESTIGATION_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL230_INVESTIGATION_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL230_INVESTIGATION_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL230_INVESTIGATION_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL230_INVESTIGATION_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL230_INVESTIGATION_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL230_INVESTIGATION_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL230_INVESTIGATION_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL230_INVESTIGATION_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL230_INVESTIGATION_NO);

    }

    private final String SEQUENCE_NO_TBL240_SUPERVISED_NOTICE_NO = "12";
    private final String COLUMN_NAME_TBL240_SUPERVISED_NOTICE_NO = "SUPERVISED_NOTICE_NO";
    private final int CURRENT_NO_TBL240_SUPERVISED_NOTICE_NO = 1000000097;
    private final String DELETE_FLAG_TBL240_SUPERVISED_NOTICE_NO = "0";
    private final int INCREMENT_NO_TBL240_SUPERVISED_NOTICE_NO = 1;
    private final String PREFIX_TBL240_SUPERVISED_NOTICE_NO = null;
    private final int START_NO_TBL240_SUPERVISED_NOTICE_NO = 1000000000;
    private final String TBL_ID_TBL240_SUPERVISED_NOTICE_NO = "TBL240";
    private final Timestamp UPDATE_DATETIME_TBL240_SUPERVISED_NOTICE_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 13:25:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL240_SUPERVISED_NOTICE_NO = "G0000020";

    private void assertTbl240SupervisedNoticeNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL240_SUPERVISED_NOTICE_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL240_SUPERVISED_NOTICE_NO);

    }

    private final String SEQUENCE_NO_TBL300_PROGRESS_RECORD_NO = "13";
    private final String COLUMN_NAME_TBL300_PROGRESS_RECORD_NO = "PROGRESS_RECORD_NO";
    private final int CURRENT_NO_TBL300_PROGRESS_RECORD_NO = 1000000449;
    private final String DELETE_FLAG_TBL300_PROGRESS_RECORD_NO = "0";
    private final int INCREMENT_NO_TBL300_PROGRESS_RECORD_NO = 1;
    private final String PREFIX_TBL300_PROGRESS_RECORD_NO = null;
    private final int START_NO_TBL300_PROGRESS_RECORD_NO = 1000000000;
    private final String TBL_ID_TBL300_PROGRESS_RECORD_NO = "TBL300";
    private final Timestamp UPDATE_DATETIME_TBL300_PROGRESS_RECORD_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-06 13:25:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL300_PROGRESS_RECORD_NO = "G0000018";

    private void assertTbl300ProgressRecordNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL300_PROGRESS_RECORD_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL300_PROGRESS_RECORD_NO);

    }

    private final String SEQUENCE_NO_TBL310_PROGRESS_RECORD_ATTACH_NO = "14";
    private final String COLUMN_NAME_TBL310_PROGRESS_RECORD_ATTACH_NO = "PROGRESS_RECORD_ATTACH_NO";
    private final int CURRENT_NO_TBL310_PROGRESS_RECORD_ATTACH_NO = 1000000141;
    private final String DELETE_FLAG_TBL310_PROGRESS_RECORD_ATTACH_NO = "0";
    private final int INCREMENT_NO_TBL310_PROGRESS_RECORD_ATTACH_NO = 1;
    private final String PREFIX_TBL310_PROGRESS_RECORD_ATTACH_NO = null;
    private final int START_NO_TBL310_PROGRESS_RECORD_ATTACH_NO = 1000000000;
    private final String TBL_ID_TBL310_PROGRESS_RECORD_ATTACH_NO = "TBL310";
    private final Timestamp UPDATE_DATETIME_TBL310_PROGRESS_RECORD_ATTACH_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-05 22:46:56", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL310_PROGRESS_RECORD_ATTACH_NO = "G0000018";

    private void assertTbl310ProgressRecordAttachNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL310_PROGRESS_RECORD_ATTACH_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL310_PROGRESS_RECORD_ATTACH_NO);

    }

    private final String SEQUENCE_NO_TBL400_APPLICATION_NO = "15";
    private final String COLUMN_NAME_TBL400_APPLICATION_NO = "APPLICATION_NO";
    private final int CURRENT_NO_TBL400_APPLICATION_NO = 1000000013;
    private final String DELETE_FLAG_TBL400_APPLICATION_NO = "0";
    private final int INCREMENT_NO_TBL400_APPLICATION_NO = 1;
    private final String PREFIX_TBL400_APPLICATION_NO = null;
    private final int START_NO_TBL400_APPLICATION_NO = 1000000000;
    private final String TBL_ID_TBL400_APPLICATION_NO = "TBL400";
    private final Timestamp UPDATE_DATETIME_TBL400_APPLICATION_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-03 16:27:36", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL400_APPLICATION_NO = "G0000018";

    private void assertTbl400Application_no(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL400_APPLICATION_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL400_APPLICATION_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL400_APPLICATION_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL400_APPLICATION_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL400_APPLICATION_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL400_APPLICATION_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL400_APPLICATION_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL400_APPLICATION_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL400_APPLICATION_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL400_APPLICATION_NO);

    }

    private final String SEQUENCE_NO_TBL205_TEMP_NO = "16";
    private final String COLUMN_NAME_TBL205_TEMP_NO = "TEMP_NO";
    private final int CURRENT_NO_TBL205_TEMP_NO = 1000000009;
    private final String DELETE_FLAG_TBL205_TEMP_NO = "0";
    private final int INCREMENT_NO_TBL205_TEMP_NO = 1;
    private final String PREFIX_TBL205_TEMP_NO = null;
    private final int START_NO_TBL205_TEMP_NO = 1000000000;
    private final String TBL_ID_TBL205_TEMP_NO = "TBL205";
    private final Timestamp UPDATE_DATETIME_TBL205_TEMP_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-02 09:51:38", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL205_TEMP_NO = "G0000002";

    private void assertTbl205TempNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL205_TEMP_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL205_TEMP_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL205_TEMP_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL205_TEMP_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL205_TEMP_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL205_TEMP_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL205_TEMP_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL205_TEMP_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL205_TEMP_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL205_TEMP_NO);

    }

    private final String SEQUENCE_NO_TBL215_TEMP_NO = "17";
    private final String COLUMN_NAME_TBL215_TEMP_NO = "TEMP_NO";
    private final int CURRENT_NO_TBL215_TEMP_NO = 1000000000;
    private final String DELETE_FLAG_TBL215_TEMP_NO = "0";
    private final int INCREMENT_NO_TBL215_TEMP_NO = 1;
    private final String PREFIX_TBL215_TEMP_NO = null;
    private final int START_NO_TBL215_TEMP_NO = 1000000000;
    private final String TBL_ID_TBL215_TEMP_NO = "TBL215";
    private final Timestamp UPDATE_DATETIME_TBL215_TEMP_NO = null;
    private final String UPDATE_USER_ID_TBL215_TEMP_NO = null;

    private void assertTbl215TempNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL215_TEMP_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL215_TEMP_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL215_TEMP_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL215_TEMP_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL215_TEMP_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL215_TEMP_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL215_TEMP_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL215_TEMP_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL215_TEMP_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL215_TEMP_NO);

    }

    private final String SEQUENCE_NO_TBL225_TEMP_NO = "18";
    private final String COLUMN_NAME_TBL225_TEMP_NO = "TEMP_NO";
    private final int CURRENT_NO_TBL225_TEMP_NO = 1000000017;
    private final String DELETE_FLAG_TBL225_TEMP_NO = "0";
    private final int INCREMENT_NO_TBL225_TEMP_NO = 1;
    private final String PREFIX_TBL225_TEMP_NO = null;
    private final int START_NO_TBL225_TEMP_NO = 1000000000;
    private final String TBL_ID_TBL225_TEMP_NO = "TBL225";
    private final Timestamp UPDATE_DATETIME_TBL225_TEMP_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-04 16:26:49", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL225_TEMP_NO = "G0000010";

    private void assertTbl225TempNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL225_TEMP_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL225_TEMP_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL225_TEMP_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL225_TEMP_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL225_TEMP_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL225_TEMP_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL225_TEMP_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL225_TEMP_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL225_TEMP_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL225_TEMP_NO);

    }

    private final String SEQUENCE_NO_TBL235_TEMP_NO = "19";
    private final String COLUMN_NAME_TBL235_TEMP_NO = "TEMP_NO";
    private final int CURRENT_NO_TBL235_TEMP_NO = 1000000021;
    private final String DELETE_FLAG_TBL235_TEMP_NO = "0";
    private final int INCREMENT_NO_TBL235_TEMP_NO = 1;
    private final String PREFIX_TBL235_TEMP_NO = null;
    private final int START_NO_TBL235_TEMP_NO = 1000000000;
    private final String TBL_ID_TBL235_TEMP_NO = "TBL235";
    private final Timestamp UPDATE_DATETIME_TBL235_TEMP_NO = Timestamp.valueOf(LocalDateTime.parse("2020-01-05 18:06:03", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    private final String UPDATE_USER_ID_TBL235_TEMP_NO = "G0000030";

    private void assertTbl235TempNo(TBS001Entity entity) {
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO_TBL235_TEMP_NO);
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME_TBL235_TEMP_NO);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO_TBL235_TEMP_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG_TBL235_TEMP_NO);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO_TBL235_TEMP_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX_TBL235_TEMP_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO_TBL235_TEMP_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID_TBL235_TEMP_NO);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME_TBL235_TEMP_NO);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID_TBL235_TEMP_NO);

    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-001/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl100 And Column Name Is Apartment Id
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl100AndColumnNameIsApartmentId() {
        String tblId = "TBL100";
        String columnName = "APARTMENT_ID";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl100ApartmentId(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-002/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl105 And Column Name Is History Number
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl105AndColumnNameIsHistoryNumber() {
        String tblId = "TBL105";
        String columnName = "HISTORY_NUMBER";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl105HistoryNumber(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-003/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl110 And Column Name Is Login Id
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl110AndColumnNameIsLoginId() {
        String tblId = "TBL110";
        String columnName = "LOGIN_ID";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl110LoginId(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-004/区分:I/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl120 And Column Name Is Login Id
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl120AndColumnNameIsLoginId() {
        String tblId = "TBL120";
        String columnName = "LOGIN_ID";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl120LoginId(entity);
    };

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-005/区分:I/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl120 And Column Name Is User Id
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl120AndColumnNameIsUserId() {
        String tblId = "TBL120";
        String columnName = "USER_ID";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl120UserId(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-006/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl200 And Column Name Is Notification No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl200AndColumnNameIsNotificationNo() {
        String tblId = "TBL200";
        String columnName = "NOTIFICATION_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl200NotificationNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-007/区分:I/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl200 And Column Name Is Reception No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl200AndColumnNameIsReceptionNo() {
        String tblId = "TBL200";
        String columnName = "RECEPTION_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl200ReceptionNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-008/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl210 And Column Name Is Accept No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl210AndColumnNameIsAcceptNo() {
        String tblId = "TBL210";
        String columnName = "ACCEPT_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl210AcceptNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-009/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl220 And Column Name Is Advice No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl220AndColumnNameIsAdviceNo() {
        String tblId = "TBL220";
        String columnName = "ADVICE_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl220AcceptNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-010/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl230 And Column Name Is Investigation No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl230AndColumnNameIsInvestigationNo() {
        String tblId = "TBL230";
        String columnName = "INVESTIGATION_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl230InvestigationNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-011/区分:I/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl240 And Column Name Is Supervised Notice No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl240AndColumnNameIsSupervisedNoticeNo() {
        String tblId = "TBL240";
        String columnName = "SUPERVISED_NOTICE_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl240SupervisedNoticeNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-012/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl300 And Column Name Is Progress Record No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl300AndColumnNameIsProgressRecordNo() {
        String tblId = "TBL300";
        String columnName = "PROGRESS_RECORD_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl300ProgressRecordNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-013/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl310 And Column Name Is Progress Record Attach No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl310AndColumnNameIsProgressRecordAttachNo() {
        String tblId = "TBL310";
        String columnName = "PROGRESS_RECORD_ATTACH_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl310ProgressRecordAttachNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-014/区分:I/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl400 And Column Name Is Application No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl400AndColumnNameIsApplicationNo() {
        String tblId = "TBL400";
        String columnName = "APPLICATION_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl400Application_no(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-015/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl205 And Column Name Is Temp No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl205AndColumnNameIsTempNo() {
        String tblId = "TBL205";
        String columnName = "TEMP_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl205TempNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-016/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl215 And Column Name Is Temp No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl215AndColumnNameIsTempNo() {
        String tblId = "TBL215";
        String columnName = "TEMP_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl215TempNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-017/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl225 And Column Name Is Temp No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl225AndColumnNameIsTempNo() {
        String tblId = "TBL225";
        String columnName = "TEMP_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl225TempNo(entity);
    }

    /**
     * 案件ID:TBS001/チェックリストID:UT-TBS001-018/区分:N/チェック内容:test find Sequence For Tbl When Tbl Id Is Tbl235 And Column Name Is Temp No
     */
    @Test
    public void testFindSequenceForTblWhenTblIdIsTbl235AndColumnNameIsTempNo() {
        String tblId = "TBL235";
        String columnName = "TEMP_NO";
        TBS001Entity entity = tbs001dao.findSequenceForTbl(tblId, columnName);
        assertTbl235TempNo(entity);
    }

}
