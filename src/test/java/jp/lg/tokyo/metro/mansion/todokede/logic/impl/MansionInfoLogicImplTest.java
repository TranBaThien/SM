/*
 * @(#)FileName.java 2019/MM/dd
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pvthinh
 * Create Date: 2019/12/04
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.SystemException;

@RunWith(SpringRunner.class)
public class MansionInfoLogicImplTest {
    @InjectMocks
    private IMansionInfoLogic mansionInfoLogic = new MansionInfoLogicImpl();
    @Mock
    private TBL100DAO tbl100DAO;
    private final String TEMP_NO = "1000000001";
    private final String APARTMENT_ID = "1000000001";
    /**
     * 案件ID：MDA0110／チェックリストID：getMansionInformationById-001／区分：N／観点ID：01／チェック内容：Test Get Mansion Information By Id When ApartmentId Is Blank
     *
     */
    @Test()
    public void getMansionInformationById_whenApartmentIdIsBlank_thenReturnNull() throws SystemException {
        String apartmentId = "";
        Assert.assertNull(mansionInfoLogic.getMansionInformationById(apartmentId));

    }

    /**
     * 案件ID：MDA0110／チェックリストID：getMansionInformationById-002／区分：E／観点ID：02／チェック内容：Test Get Mansion Information By Id When ApartmentId Is Invalid
     *
     */
    @Test()
    public void getMansionInformationById_whenApartmentIdIsInvalid_thenReturnNull() throws SystemException {
        String apartmentId = "123";
        Mockito.when(tbl100DAO.getMansionInformationById(apartmentId)).thenReturn(null);
        Assert.assertNull(mansionInfoLogic.getMansionInformationById(apartmentId));

    }

    /**
     * 案件ID：MDA0110／チェックリストID：getMansionInformationById-003／区分: I／観点ID：03／チェック内容：Test Get Mansion Information By Id When ApartmentId Is Valid
     *
     */
    @Test
    public void getMansionInformationById_whenApartmentIdIsValid_thenReturnMansionInfoVo() throws SystemException {
        String apartmentId = "1000000001";
        TBL100Entity entity = new TBL100Entity();
        entity.setApartmentId(apartmentId);
        Mockito.when(tbl100DAO.getMansionInformationById(apartmentId)).thenReturn(entity);
        Assert.assertEquals(apartmentId, mansionInfoLogic.getMansionInformationById(apartmentId).getApartmentId());

    }

    /**
     * 案件ID：MDA0110／チェックリストID：isExistMansionWithSupportCd-001／区分：N／観点ID：03／チェック内容：Test is Exist Mansion With SupportCd When SupportCd Is 1 And tbl100Entity Is Not Null
     *
     */
    @Test
    public void isExistMansionWithSupportCd_When_SupportCdIs1_And_tbl100EntityIsNotNull() throws SystemException {
        TBL100Entity tbl100Entity = new TBL100Entity();
        Mockito.when(tbl100DAO.getMansiondByIdAndSupportCd(Mockito.anyString(), Mockito.anyString())).thenReturn(tbl100Entity);
        assertThat(mansionInfoLogic.isExistMansionWithSupportCd(APARTMENT_ID,"1")).isEqualTo(true);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：isExistMansionWithSupportCd-002／区分: I／観点ID：03／チェック内容：Test is Exist Mansion With SupportCd When SupportCd Is 1 And tbl100Entity Is  Null
     *
     */
    @Test
    public void isExistMansionWithSupportCd_When_SupportCdIs1_And_tbl100EntityIsNull() throws SystemException {
        Mockito.when(tbl100DAO.getMansiondByIdAndSupportCd(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        assertThat(mansionInfoLogic.isExistMansionWithSupportCd(APARTMENT_ID,"1")).isEqualTo(false);
    }

    /**
     * 案件ID：MDA0110／チェックリストID：isExistMansionWithSupportCd-002／区分：E／観点ID：03／チェック内容：Test is Exist Mansion With SupportCd When SupportCd Is 1 And tbl100Entity Is  Null
     *
     */
    @Test
    public void isExistMansionWithSupportCd_When_SupportCdIs2_And_tbl100EntityIsNull() throws SystemException {
        Mockito.when(tbl100DAO.getMansiondByIdAndSupportCd(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        assertThat(mansionInfoLogic.isExistMansionWithSupportCd(APARTMENT_ID,"2")).isEqualTo(false);
    }
}
