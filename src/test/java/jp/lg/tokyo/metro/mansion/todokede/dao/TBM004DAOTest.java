/*
 * @(#) TBM004DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2020/01/06
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TBM004DAOTest extends AbstractDaoTest {

    final int SIZE = 49;

    @Autowired
    private TBM004DAO tbm004DAO;

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-018／区分：N／チェック内容：削除されていないすべてのシステム設定のテスト
     */
    @Test
    public void testFindAllNotDeleted() {
        List<TBM004Entity> entities = tbm004DAO.findAllNotDeleted();
        entities.forEach(entity -> assertThat(entity.getDeleteFlag()).isEqualTo("0"));
    }

    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-019／区分：N／チェック内容：SetTargetNameEngによる検索のテスト
     */
    @Test
    public void testFindBySetTargetNameEng() {
        TBM004Entity entity = tbm004DAO.findBySetTargetNameEng("G_ACCOUT_LOCK_PERIOD");
        assertThat(entity.getSetPoint()).isEqualTo("60");
    }
    
    /**
     *案件ID：MAA0110／チェックリストID：UT-MAA0110-020／区分：I／チェック内容：SetTargetNameEng_1による検索のテスト
     */
    @Test
    public void testFindBySetTargetNameEng_1() {
        //execute
        TBM004Entity entity = tbm004DAO.findBySetTargetNameEng("G_ACCOUT_LOCK_PERIOD");
        
        //assert Result
        assertThat(entity.getSetTargetNameEng()).isEqualTo("G_ACCOUT_LOCK_PERIOD");
    }
}
