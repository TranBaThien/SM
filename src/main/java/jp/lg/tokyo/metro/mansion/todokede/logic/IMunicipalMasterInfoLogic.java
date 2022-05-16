/*
 * @(#) IMunicipalMasterInfoLogic.java
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
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;

import javax.transaction.SystemException;

/**
 * @author DLLy
 *
 */
public interface IMunicipalMasterInfoLogic {

	/**
	 * Get municipal master info by cityCode
	 * @param cityCode
	 * @return
	 * @throws BusinessException
	 */
	MunicipalMasterInfoVo getMunicipalMasterInfo(String cityCode) throws SystemException;

}
