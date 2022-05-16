/*
 * @(#) IUserRegistrationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.form.ManagementAssociationApplicationForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;

/**
 * @author lthai
 *
 */
public interface IUserRegistrationLogic {
    /**
     * Save management association application information
     * 
     * @param form ManagementAssociationApplicationForm
     */
    void saveManagementAssociationApplicationInformation(ManagementAssociationApplicationForm form);

    /**
     * Get municipal master information
     * 
     * @return
     */
    List<CityVo> getMunicipalMasterInformation();
}
    