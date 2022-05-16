/*
 * @(#) ManagementAssociationLogicImplTest.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2020/06/01
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

/**
 * @author tbthien
 *
 */
@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class CityLogicImplTest {

    @InjectMocks
    private CityLogicImpl citylogicImpl;
    
    @Mock
    TBM001DAO tbm001dao;
    
    private List<TBM001Entity> generateListCity() {
        
        int i;
        
        List<TBM001Entity> list = new ArrayList<TBM001Entity>();
        
        for(i = 0; i < 63; i++) {
            TBM001Entity entity = new TBM001Entity();
            
            entity.setCityName("文京区");
            entity.setCityCode("131059"); 
            
            list.add(entity);
        }
        
        return list;
        
    }
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-01/区分:N/チェック内容: test get list city information success
     * 
     */
    @Test
    public void getListCity_WhenAllConditionsAreMet_ThenReturnListCity() {
        
        List<TBM001Entity> list = generateListCity();
        
        Mockito.when(tbm001dao.getListCity()).thenReturn(list);
        
        List<CityVo> listcity = citylogicImpl.getListCity();
        
        CityVo cvo = new CityVo();
        
        cvo.setCityCode("131059"); 
        cvo.setCityName("文京区");
        
        for(CityVo vo : listcity) {
            assertThat(vo.getCityCode()).isEqualTo(cvo.getCityCode());
            assertThat(vo.getCityName()).isEqualTo(cvo.getCityName());
        }
        
    }
    
    /**
     * 案件ID:GCA0110/チェックリストID:UT- GCA0110-02/区分:N/チェック内容: test get list city information success
     * 
     */
    @Test(expected = NullPointerException.class)
    public void getListCity_WhenListTBM001EntityIsNull() {
        
        Mockito.when(tbm001dao.getListCity()).thenReturn(null);
        
        List<CityVo> listcity = citylogicImpl.getListCity();
    }
}
