/*
 * @(#) TBM003DAOTest.java
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.TestConfig;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM003Entity;

/**
 * @author nvlong1
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional(propagation = Propagation.REQUIRED)
public class TBM003DAOTest {

    @Autowired
    private TBM003DAO tbm003DAO;

    private final String ADVICE_TEMPLATE_NO = "100001";
    private final String ADVICE_TEMPLATE_OVERVIEW = "Template 1";
    private final String ADVICE_TEMPLATE_DETAIL = "abc";
    private final String DELETE_FLAG = "0";
    private final String UPDATE_USER_ID = "0";
    private final Timestamp UPDATE_DATETIME =  Timestamp.valueOf(LocalDateTime.parse("2019-11-10 17:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    /**
     * 案件ID:GFA0110/チェックリストID:UT- GFA0110-007-01/区分:N/チェック内容:Test Get Advice Template Content Success
     */
    @Test
    public void getAdviceTemplateContent_WhenExist_ShouldBeExist() {
        // Prepare data
        List<TBM003Entity> expecteds = generateTBM003Entity();

        // Execute
        List<TBM003Entity> actuals = tbm003DAO.getAdviceTemplateContent();

        // Check result
        assertThat(actuals.size() == expecteds.size()).isTrue();
        int i = 0;
        for (TBM003Entity actual : actuals) {
            assertEntity(actual, expecteds.get(i++));
        }
    }

    private void assertEntity(TBM003Entity entity, TBM003Entity expected) {
        assertThat(entity.getAdviceTemplateNo()).isEqualTo(expected.getAdviceTemplateNo());
        assertThat(entity.getAdviceTemplateOverview()).isEqualTo(expected.getAdviceTemplateOverview());
        assertThat(entity.getAdviceTemplateDetail()).isEqualTo(expected.getAdviceTemplateDetail());
        assertThat(entity.getDeleteFlag()).isEqualTo(expected.getDeleteFlag());
        assertThat(entity.getUpdateUserId()).isEqualTo(expected.getUpdateUserId());
        assertThat(entity.getUpdateDatetime()).isEqualTo(expected.getUpdateDatetime());
    }
    
    private List<TBM003Entity> generateTBM003Entity() {

        List<TBM003Entity> listEntity = new ArrayList<TBM003Entity>();
        TBM003Entity entity = new TBM003Entity();
        entity.setAdviceTemplateNo(ADVICE_TEMPLATE_NO);
        entity.setAdviceTemplateOverview(ADVICE_TEMPLATE_OVERVIEW);
        entity.setAdviceTemplateDetail(ADVICE_TEMPLATE_DETAIL);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setUpdateUserId(UPDATE_USER_ID);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        listEntity.add(entity);

        return listEntity;
    }
}
