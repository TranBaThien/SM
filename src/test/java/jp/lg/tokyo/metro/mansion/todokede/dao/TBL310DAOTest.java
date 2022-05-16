/*
 * @(#) TBL110DAOTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pvthinh
 * Create Date: 2019/12/18
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM003Entity;

public class TBL310DAOTest extends AbstractDaoTest{

    @Autowired
    private TBL310DAO tbl310DAO;
    
    @Autowired
    private TBL300DAO tbl300DAO;
    /* Create TBL310Entity */
    private final String PROGRESS_RECORD_ATTACH_NO = "122223";
    private final String DELETE_FLAG = "1";
    private final byte[] FILE = { 1, 2, 3 };
    private final String FILE_NAME = "11111";
    private final String PROGRESS_RECORD_NO = "4444";
    private final Timestamp UPDATE_DATETIME = new Timestamp(new Date().getTime());
    private final String UPDATE_USER_ID = "44444";

    /* Create TBL300Entity */
    private final String PROGRESS_RECORD_NO_TBL300 = "22222";
    private final String APARTMENT_ID_300 = "2000000000";
    private final String CORRESPOND_DATE = "201912040529";
    private final String TYPE_CODE = "3";
    private final String CORRESPOND_TYPE_CODE = "";
    private final String NOTICE_TYPE_CODE = "";
    private final String RELATED_NUMBER = "1000000001";
    private final String PROGRESS_RECORD_OVERVIEW = "HntVy";
    private final String PROGRESS_RECORD_DETAIL = "";
    private final String SUPPORT_CODE = "";
    private final String NOTIFICATION_METHOD_CODE = "2";
    private final String DELETE_FLAG_300 = "0";
    private final String UPDATE_USER_ID_300 = "1000000010";
    private final Timestamp UPDATE_DATETIME_300 = new Timestamp(new Date().getTime());
    private final TBL310Entity TBL310ENTITY = null;
    

    
    @Test
    @Sql(value = "classpath:/sql/UT-GEB0110-11.sql")
    public void testGetProgressRecord_WhenExist_ShouldBeExist() {
        // Execute
        List<TBL310Entity> listTbl310Entity = tbl310DAO.getProgressRecord("11");
        // Check result
        assertListTBL310Entity(listTbl310Entity);
    }
    
    private void assertListTBL310Entity(List<TBL310Entity> listTbl310Entity) {
        assertThat(listTbl310Entity.get(0).getProgressRecordAttachNo()).isEqualTo("122223");
        assertThat(listTbl310Entity.get(0).getProgressRecordNo()).isEqualTo("11");
        assertThat(listTbl310Entity.get(0).getUpdateDatetime()).isEqualTo(null);
        assertThat(listTbl310Entity.get(0).getUpdateUserId()).isEqualTo(null);
        assertThat(listTbl310Entity.get(0).getFileName()).isEqualTo("11111");

    }

    @Test
    public void deleteBtIdWhenSuccess() {
        // Prepare data
        TBL310Entity expected = getTBL310Entity(PROGRESS_RECORD_ATTACH_NO);

        // Execute
        tbl310DAO.save(expected);

        // Check result
        tbl310DAO.deleteById(PROGRESS_RECORD_ATTACH_NO);
    }
    

    /**
     * 案件ID:GEB0110/チェックリストID:UT- GEB0110-001-06/区分:I/チェック内容:Test save Success
     */
    @Test
    public void save_WhenSuccess_ShouldBeCreated() {
        // Prepare data
        List<TBL310Entity> expected = listTBL310Entity(PROGRESS_RECORD_ATTACH_NO);

        // Execute
        List<TBL310Entity> actual = tbl310DAO.saveAll(expected);

        // Check result
        asertEntityWithoutTBL310(actual);
    }
    
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void save_WhenFail_ShouldBeThrowException() {
        tbl310DAO.saveAll(null);
    }
    

    private void asertEntityWithoutTBL310(List<TBL310Entity> actual) {
        assertThat(actual.get(0).getProgressRecordAttachNo()).isEqualTo(PROGRESS_RECORD_ATTACH_NO);
        assertThat(actual.get(0).getDeleteFlag()).isEqualTo(DELETE_FLAG);
        assertThat(actual.get(0).getFile()).isEqualTo(FILE);
        assertThat(actual.get(0).getProgressRecordNo()).isEqualTo(PROGRESS_RECORD_NO);
        assertThat(actual.get(0).getUpdateDatetime()).isEqualTo(UPDATE_DATETIME);
        assertThat(actual.get(0).getUpdateUserId()).isEqualTo(UPDATE_USER_ID);
        
        
    }

    private List<TBL310Entity> listTBL310Entity(String PROGRESS_RECORD_ATTACH_NO) {
        List<TBL310Entity> listEntity = new ArrayList<TBL310Entity>();
        TBL310Entity entity = new TBL310Entity();
        
        entity.setProgressRecordAttachNo(PROGRESS_RECORD_ATTACH_NO);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setFile(FILE);
        entity.setFileName(FILE_NAME);
        entity.setProgressRecordNo(PROGRESS_RECORD_NO);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        listEntity.add(entity);
    
        return listEntity;
    }
    
    private TBL310Entity getTBL310Entity(String PROGRESS_RECORD_ATTACH_NO) {
        TBL310Entity entity = new TBL310Entity();
    
        entity.setProgressRecordAttachNo(PROGRESS_RECORD_ATTACH_NO);
        entity.setDeleteFlag(DELETE_FLAG);
        entity.setFile(FILE);
        entity.setFileName(FILE_NAME);
        entity.setProgressRecordNo(PROGRESS_RECORD_NO);
        entity.setUpdateDatetime(UPDATE_DATETIME);
        entity.setUpdateUserId(UPDATE_USER_ID);
        
        return entity;
    }


}
