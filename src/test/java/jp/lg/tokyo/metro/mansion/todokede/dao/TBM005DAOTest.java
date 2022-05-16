/*
 * @(#) TBM005DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: 07/01/2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM005Entity;

/**
 * @author nvlong1
 *
 */
public class TBM005DAOTest extends AbstractDaoTest {

    private final String USED_CODE_NOT_EXIST = "00";
    private final String USED_CODE = "07";
    private final String MAIL_TEMPLATE = "パスワード再発行通知メール（区市町村向け）";
    private final String MAIL_SUBJECT = "【東京都マンション管理状況届出】パスワード再発行完了のお知らせ";
    private final String MAIL_SEND_FROM = "mansiondev@cmcglobal.com.vn";
    private final String MAIL_REPLY_TO = "to@gmail.com";
    private final String DELETE_FLAG = "0";

    @Autowired
    private TBM005DAO tbm005DAO;

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-037/区分:N/チェック内容:Get Template Mail By Used Code When Exist Should Be Exist
     */
    @Test
    public void getTemplateMailByUsedCode_WhenExist_ShouldBeExist() {
        // Prepare data
        assertEntity(tbm005DAO.getTemplateMailByUsedCode(USED_CODE));
    }

    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-038/区分:E/チェック内容:Get Template Mail By Used Code When Exist Should Be Null
     */
    @Test
    public void getTemplateMailByUsedCode_WhenNotExist_ShouldBeNull() {
        // Execute
        TBM005Entity  actual = tbm005DAO.getTemplateMailByUsedCode(USED_CODE_NOT_EXIST);
        assertThat(actual).isEqualTo(null);
    }
    
    /**
     * 案件ID:GGA0110/チェックリストID:UT-GGA0110-038/区分:I/チェック内容:Get Template Mail By Used Code When Exist Should Be Exist
     */
    @Test
    public void getTemplateMailByUsedCode_WhenExist_ShouldBeExist_1() {
        // execute
        TBM005Entity result = tbm005DAO.getTemplateMailByUsedCode(USED_CODE);
        
        // assert Result
        assertThat(result.getUsedCode()).isEqualTo(USED_CODE);
    }

    private void assertEntity(TBM005Entity entity) {
        assertThat(entity.getUsedCode()).isEqualTo(USED_CODE);
        assertThat(entity.getMailTemplate()).isEqualTo(MAIL_TEMPLATE);
        assertThat(entity.getMailSubject()).isEqualTo(MAIL_SUBJECT);
        assertThat(entity.getMailSendFrom()).isEqualTo(MAIL_SEND_FROM);
        assertThat(entity.getMailReplyTo()).isEqualTo(MAIL_REPLY_TO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getUpdateUserId()).isEqualTo(null);
        assertThat(entity.getUpdateDatetime()).isEqualTo(null);
    }
}
