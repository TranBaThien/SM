/*
 * @(#) ICityLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

/**
 * @author tbthien
 *
 */
public interface ICityLogic {

    /**
     * Get List CityName and CityCode
     * 
     * @return list
     */
    List<CityVo> getListCity();
    
}
