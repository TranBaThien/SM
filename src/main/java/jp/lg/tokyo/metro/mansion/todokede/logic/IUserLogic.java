/*
 * @(#) IUserLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;

import javax.transaction.SystemException;

/**
 * @author DLLy
 *
 */
public interface IUserLogic {
    GovernmentStaffInfoVo getGovernmentStaffUserLoginInfo(String userId) throws BusinessException, SystemException;
	
}
