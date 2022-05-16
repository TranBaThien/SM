package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBS001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;

@RunWith(SpringRunner.class)
public class SequenceLogicImplTest {
    
    @InjectMocks
    SequenceLogicImpl sequenceLogicImpl;
    
    @Mock
    private TBS001DAO tbs001dao;
    
    private static final String SEQUENCE_NO = "01";
    private static final String COLUMN_NAME = "APARTMENT_ID";
    private static final int  CURRENT_NO = 51;
    private static final String  DELETE_FLAG = "0";
    private static final int  INCREMENT_NO = 1;
    private static final String  PREFIX = "";
    private static final int  START_NO = 0;
    private static final String  TBL_ID = "TBL100";
    private static final Timestamp UPDATE_DATETIME = DateTimeUtil.getCurrentSystemDateTime();
    private static final String UPDATE_USER_ID = "100000009";
    
    private TBS001Entity newTBS001Entity() {
        TBS001Entity tbs001Entity = new TBS001Entity();
        
        tbs001Entity.setSequenceNo(SEQUENCE_NO);
        tbs001Entity.setColumnName(COLUMN_NAME);
        tbs001Entity.setCurrentNo(CURRENT_NO);
        tbs001Entity.setDeleteFlag(DELETE_FLAG);
        tbs001Entity.setIncrementNo(INCREMENT_NO);
        tbs001Entity.setPrefix(PREFIX);
        tbs001Entity.setStartNo(START_NO);
        tbs001Entity.setTblId(TBL_ID);
        tbs001Entity.setUpdateDatetime(UPDATE_DATETIME);
        tbs001Entity.setUpdateUserId(UPDATE_USER_ID);
        
        return tbs001Entity;
    }
    
    /**
         * 案件ID GLA0110 /チェックリストID:UT-GLA0110-009/区分:I/チェック内容:Test Find Sequence For Table When Success
    */
    @Test
    public void testFindSequenceForTblWhenSuccess() {
        //prepare data
        TBS001Entity entity = newTBS001Entity();
        Mockito.when(tbs001dao.findSequenceForTbl(Mockito.anyString(), Mockito.anyString())).thenReturn(entity);
        
        //execute
        entity = sequenceLogicImpl.findSequenceForTbl(TBL_ID, COLUMN_NAME);
        
        //assert Result
        assertResult(entity);
        
    }
    
    /**
         * 案件ID GLA0110 /チェックリストID:UT-GLA0110-010/区分:N/チェック内容:Test Find Sequence For Table When Failed
    */
    @Test
    public void testFindSequenceForTblWhenFailed() {
        //prepare data
        Mockito.when(tbs001dao.findSequenceForTbl(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        
        //execute
        TBS001Entity entity = sequenceLogicImpl.findSequenceForTbl(TBL_ID, COLUMN_NAME);
        
        //assert Result
        assertThat(entity).isEqualTo(null);
        
    }
    
    /**
         * 案件ID GLA0110 /チェックリストID:UT-GLA0110-010/区分:N/チェック内容:Test Find Sequence For Table When Failed
    */
    @Test
    public void saveWhenSuccess() {
        //prepare data
        TBS001Entity entity = newTBS001Entity();
        
        Mockito.when(tbs001dao.save(entity)).thenReturn(entity);
        //execute 
        boolean checkSave = sequenceLogicImpl.save(entity);
        
       assertTrue(checkSave);
    }
    
    /**
     * 案件ID GLA0110 /チェックリストID:UT-GLA0110-011/区分:N/チェック内容:Test Find Sequence For Table When Failed
*/
@Test
public void saveWhenFailed() {
    //prepare data
    TBS001Entity entity = null;
    
    //execute 
    boolean checkSave = sequenceLogicImpl.save(entity);
    
   assertFalse(checkSave);
}
    
    private void assertResult(TBS001Entity entity) {
        assertThat(entity.getColumnName()).isEqualTo(COLUMN_NAME);
        assertThat(entity.getCurrentNo()).isEqualTo(CURRENT_NO);
        assertThat(entity.getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(entity.getIncrementNo()).isEqualTo(INCREMENT_NO);
        assertThat(entity.getPrefix()).isEqualTo(PREFIX);
        assertThat(entity.getSequenceNo()).isEqualTo(SEQUENCE_NO);
        assertThat(entity.getStartNo()).isEqualTo(START_NO);
        assertThat(entity.getTblId()).isEqualTo(TBL_ID);
        assertThat(entity.getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(entity.getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
    }
    
    
}
