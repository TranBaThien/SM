/*
 * @(#) TBL300BDAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019-12-30
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordAcceptUserVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;

/**
 * @author pdquang
 *
 */
public class TBL300BDAOTest extends AbstractDaoTest{

    @Autowired
    private TBL300BDAO tbl300BDAO;

    private final String APARTMENT_ID = "1000000001";

    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-044／区分：E／チェック内容： Test method getListProgressRecordDetailByApartmentId when data is null
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testgetProgressRecord_WhenExist_ShouldBeExist() {
        // Execute
        List<ProgressRecordDetailsVo> actual = tbl300BDAO.getListProgressRecordDetailByApartmentId("");
        // Check result
        Assert.assertEquals(actual.size(), 0);
    }
    
    /**
     * 案件ID：GEA0110／チェックリストID：UT- GEA0110-045／区分：N／チェック内容： Test method getListProgressRecordDetailByApartmentId when data is exist
     */
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testGetListProgressRecordDetailByApartmentId_Exist_ShouldBeExist() {
        // Execute
        List<ProgressRecordDetailsVo> listProRecordDetail = tbl300BDAO.getListProgressRecordDetailByApartmentId(APARTMENT_ID);
        // Check result
        Assert.assertEquals(listProRecordDetail.size(), 2);
        assertListProRecordDetail(listProRecordDetail);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testGetListProgressRecordDetailByApartmentId_WhenNull_ShouldBeExist() {
        // Execute
        List<ProgressRecordDetailsVo> listProRecordDetail = tbl300BDAO.getListProgressRecordDetailByApartmentId(null);
        // Check result
        Assert.assertEquals(listProRecordDetail.size(), 0);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testGetProgressRecordAcceptUser_WhenExist_ShouldBeExist() {
        List<String> typeCodeList = new ArrayList<String>();
        typeCodeList.add("4");
        typeCodeList.add("1");
        
        // Execute
        List<ProgressRecordAcceptUserVo> actual = tbl300BDAO.getProgressRecordAcceptUser("", typeCodeList);
        // Check result
        Assert.assertEquals(actual.size(), 0);
    }
    
    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testGetProgressRecordAcceptUser_WhenExist_ShouldBeExist2() {
        List<String> typeCodeList = null;

        
        // Execute
        List<ProgressRecordAcceptUserVo> actual = tbl300BDAO.getProgressRecordAcceptUser(null, typeCodeList);
        // Check result
        Assert.assertEquals(actual.size(), 0);
    }

    @Test
    @Sql(value = "classpath:/sql/UT-GEA0110-002.sql")
    public void testGetProgressRecordAcceptUser_Exist_ShouldBeExist() {
        List<String> typeCodeList = new ArrayList<String>();
        typeCodeList.add("4");
        typeCodeList.add("1");
        // Execute
        List<ProgressRecordAcceptUserVo> listProRecordDetail = tbl300BDAO.getProgressRecordAcceptUser(APARTMENT_ID, typeCodeList);
        // Check result
        Assert.assertEquals(listProRecordDetail.size(), 2);
        assertListProgressRecordAcceptUser(listProRecordDetail);
    }

    private void assertListProRecordDetail(List<ProgressRecordDetailsVo> listProRecordDetail) {
        assertThat(listProRecordDetail.get(0).getProgressRecordNo()).isEqualTo("1000000002");
        assertThat(listProRecordDetail.get(0).getCorrespondDate()).isEqualTo("201912240615");
        assertThat(listProRecordDetail.get(0).getTypeCode()).isEqualTo("4");
        assertThat(listProRecordDetail.get(0).getCorrespondTypeCode()).isEqualTo(null);
        assertThat(listProRecordDetail.get(0).getNoticeTypeCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listProRecordDetail.get(0).getProgressRecordOverview()).isEqualTo("Test progress");
        assertThat(listProRecordDetail.get(0).getSupportCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getUserType()).isEqualTo("1");
        
        assertThat(listProRecordDetail.get(1).getProgressRecordNo()).isEqualTo("1000000003");
        assertThat(listProRecordDetail.get(1).getCorrespondDate()).isEqualTo("201912231840");
        assertThat(listProRecordDetail.get(1).getTypeCode()).isEqualTo("4");
        assertThat(listProRecordDetail.get(1).getCorrespondTypeCode()).isEqualTo(null);
        assertThat(listProRecordDetail.get(1).getNoticeTypeCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(1).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listProRecordDetail.get(1).getProgressRecordOverview()).isEqualTo("Test progress");
        assertThat(listProRecordDetail.get(1).getSupportCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(1).getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(listProRecordDetail.get(1).getUserType()).isEqualTo("1");
    }

    private void assertListProgressRecordAcceptUser(List<ProgressRecordAcceptUserVo> listProRecordDetail) {
        assertThat(listProRecordDetail.get(0).getCorrespondDate()).isEqualTo("201912240615");
        assertThat(listProRecordDetail.get(0).getTypeCode()).isEqualTo("4");
        assertThat(listProRecordDetail.get(0).getNotificationMethodCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listProRecordDetail.get(0).getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getUserType()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getModifyDetails()).isEqualTo("test");

        assertThat(listProRecordDetail.get(1).getCorrespondDate()).isEqualTo("201912231840");
        assertThat(listProRecordDetail.get(1).getTypeCode()).isEqualTo("4");
        assertThat(listProRecordDetail.get(0).getNotificationMethodCode()).isEqualTo("1");
        assertThat(listProRecordDetail.get(1).getRelatedNumber()).isEqualTo("1000000002");
        assertThat(listProRecordDetail.get(1).getAuthorityModifyFlag()).isEqualTo("1");
        assertThat(listProRecordDetail.get(0).getModifyDetails()).isEqualTo("test");
        assertThat(listProRecordDetail.get(1).getUserType()).isEqualTo("1");
    }
}
