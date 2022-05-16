/*
 * @(#) TBL215DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2020/01/02
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
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

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBL215DAOTest {

    @Autowired
    private TBL215DAO tbl215DAO;

    /**
     *案件ID：GDA0110／チェックリストID：UT-GDA0110-013／区分：N／チェック内容：データが存在する場合のTBL215からのデータ削除のテスト
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GDA0110-013.sql")
    public void deleteDataFromTBL215WhenDataIsExist() {
        String notificationNo = "1000000002";

        List<TBL215Entity> entities = tbl215DAO.findAll();
        assertThat(entities.size()).isEqualTo(1);

        tbl215DAO.deleteByNotificationNo(notificationNo);

        entities = tbl215DAO.findAll();
        assertThat(entities.size()).isEqualTo(0);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-014／区分：N／チェック内容：データが存在しない場合のTBL215からのデータ削除のテスト
     */
    @Test
    public void deleteDataFromTBL215WhenDataIsNotExist() {
        String notificationNo = "1000000002";
        List<TBL215Entity> entities = tbl215DAO.findAll();
        assertThat(entities.size()).isEqualTo(0);

        tbl215DAO.deleteByNotificationNo(notificationNo);

        entities = tbl215DAO.findAll();
        assertThat(entities.size()).isEqualTo(0);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-015／区分：N／チェック内容：TBL215からのデータ削除のテストデータが存在するときにログインしたユーザーによる
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GDA0110-013.sql")
    public void deleteDataFromTBL215ByUserLoggedInWhenDataIsExist() {
        String notificationNo = "1000000002";
        String tempKbn = "3";

        int amountRecordBeforeDelete = tbl215DAO.countByNotificationNoAndTempKbn(notificationNo, tempKbn);
        assertThat(amountRecordBeforeDelete).isEqualTo(1);

        tbl215DAO.deleteByNotificationNoAndTempKbn(notificationNo, tempKbn);

        amountRecordBeforeDelete = tbl215DAO.countByNotificationNoAndTempKbn(notificationNo, tempKbn);
        assertThat(amountRecordBeforeDelete).isEqualTo(0);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-016／区分：N／チェック内容：TBL215からのデータ削除のテストデータが存在しないときにログインしたユーザーによる
     */
    @Test
    public void deleteDataFromTBL215ByUserLoggedInWhenDataIsNotExist() {
        String notificationNo = "1000000002";
        String tempKbn = "3";

        int amountRecordBeforeDelete = tbl215DAO.countByNotificationNoAndTempKbn(notificationNo, tempKbn);
        assertThat(amountRecordBeforeDelete).isEqualTo(0);

        tbl215DAO.deleteByNotificationNoAndTempKbn(notificationNo, tempKbn);

        amountRecordBeforeDelete = tbl215DAO.countByNotificationNoAndTempKbn(notificationNo, tempKbn);
        assertThat(amountRecordBeforeDelete).isEqualTo(0);
    }

    /**
     * 案件ID：GDA0110／チェックリストID：UT-GDA0110-017／区分: I／チェック内容：TBL215からのデータ取得のテストユーザーによるログインおよび通知
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GDA0110-013.sql")
    public void getDataFromTBL215ByUserLoggedInAndNotificationNo() {
        String notificationNo = "1000000002";
        String tempKbn = "3";

        Optional<TBL215Entity> optional = tbl215DAO.findByNotificationNoAndTempKbn(notificationNo, tempKbn);
        assertThat(optional.isPresent()).isTrue();

        TBL215Entity entity = optional.get();
        assertThat(entity.getTempNo()).isEqualTo("1000000004");
        assertThat(entity.getNotificationNo()).isEqualTo("1000000002");
        assertThat(entity.getTempKbn()).isEqualTo("3");
        assertThat(entity.getAcceptTime()).isNull();
        assertThat(entity.getAcceptUserId()).isEqualTo("1000000001");
        assertThat(entity.getAcceptUserName()).isEqualTo("間には全");
        assertThat(entity.getAppendixNo()).isEqualTo("別記第　　号様式（第　a　条関係）");
        assertThat(entity.getDocumentNo()).isEqualTo("令和元年住住マf　第　a号を設定する。");
        assertThat(entity.getNoticeDate()).isEqualTo(LocalDate.of(2019,12,29));
        assertThat(entity.getRecipientNameApartment()).isEqualTo("Tokyo");
        assertThat(entity.getRecipientNameUser()).isEqualTo("管理組合理事長");
        assertThat(entity.getSender()).isEqualTo("東京都知事　s小池a　百合子");
        assertThat(entity.getNotificationDate()).isNull();
        assertThat(entity.getEvidenceBar()).isEqualTo("2");
        assertThat(entity.getEvidenceNo()).isEqualTo("1");
        assertThat(entity.getRemarks()).isEqualTo("");
        assertThat(entity.getAuthorityModifyFlag()).isEqualTo("2");
        assertThat(entity.getModifyDetails()).isEqualTo("");
        assertThat(entity.getNotificationMethodCode()).isEqualTo("1");
        assertThat(entity.getDeleteFlag()).isEqualTo("0");
        assertThat(entity.getUpdateUserId()).isEqualTo("1000000001");
        assertThat(entity.getUpdateDatetime()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2019,12,27,13,59,58)));
    }
}
