/*
 * @(#) MunicipalMasterInfoLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/12/26
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMunicipalMasterInfoLogic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.SystemException;

@RunWith(SpringRunner.class)
public class MunicipalMasterInfoLogicImplTest {
    @InjectMocks
    private IMunicipalMasterInfoLogic municipalMasterInfoLogic = new MunicipalMasterInfoLogicImpl();
    @Mock
    private TBM001DAO tbm001DAO;

    /**
     * 案件ID：MDA0110／チェックリストID：getMunicipalMasterInfo-001／区分：N／観点ID：01／チェック内容：Test Get Municipal Master Information When CityCode Is Blank
     *
     */
    @Test
    public void getMunicipalMasterInfo_whenCityCodeIsBlank_thenReturnNull() throws SystemException {
        String cityCode = "";
        Assert.assertNull(municipalMasterInfoLogic.getMunicipalMasterInfo(cityCode));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getMunicipalMasterInfo-002／区分：N／観点ID：02／チェック内容：Test Get Municipal Master Information When CityCode Is Invalid
     *
     */
    @Test
    public void getMunicipalMasterInfo_whenCityCodeIsInvalid_thenReturnNull() throws SystemException {
        String cityCode = "123";
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(cityCode)).thenReturn(null);
        Assert.assertNull(municipalMasterInfoLogic.getMunicipalMasterInfo(cityCode));
    }

    /**
     * 案件ID：MDA0110／チェックリストID：getMunicipalMasterInfo-003／区分: I／観点ID：03／チェック内容：Test Get Municipal Master Information When CityCode Is Valid
     *
     */
    @Test
    public void getMunicipalMasterInfo_whenCityCodeIsValid_thenReturnMunicipalMasterInfoVo() throws SystemException {
        String cityCode = "130001";
        TBM001Entity entity = new TBM001Entity();
        entity.setCityCode(cityCode);
        Mockito.when(tbm001DAO.getMunicipalMasterInfo(cityCode)).thenReturn(entity);
        Assert.assertEquals(cityCode, municipalMasterInfoLogic.getMunicipalMasterInfo(cityCode).getCityCode());
    }

}