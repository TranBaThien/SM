/*
 * @(#)IUserCityManagementLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/17
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import javax.validation.Valid;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.UserCityForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserCityVo;

public interface IUserCityManagementLogic {

    UserCityVo getDataShow() throws BusinessException;

    UserCityVo getUserInfor(String userId) throws BusinessException;

    void saveUserInfor(@Valid UserCityForm form) throws BusinessException;

    TBL120Entity getGovernmentInfo(String userId);

    boolean checkExistLoginId(String txtLoginid);

}
