/*
 * @(#) CityLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICityLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

/**
 * @author tbthien
 *
 */
@Service
public class CityLogicImpl implements ICityLogic {

    @Autowired
    private TBM001DAO tbm001dao;
    
    private static final Logger logger = LogManager.getLogger(CityLogicImpl.class);
    
    /**
     * Get List CityName and CityCode
     * 
     * @return
     */
    @Override
    public List<CityVo> getListCity() {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        // Get list city from TBM001
        List<TBM001Entity> listCity = tbm001dao.getListCity();
        
        List<CityVo> listCityVo = new ArrayList<CityVo>();
        
        // browse the city list
        for (TBM001Entity city : listCity) {
            CityVo cityvo = new CityVo();
            
            cityvo.setCityCode(city.getCityCode());
            cityvo.setCityName(city.getCityName());
            
            // add new cityvo element to list cityvo
            listCityVo.add(cityvo);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return listCityVo;
    }
    
}
