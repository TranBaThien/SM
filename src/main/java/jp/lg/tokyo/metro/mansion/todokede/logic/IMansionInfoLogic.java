/*
 * @(#)IMansionInfoLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;

import javax.transaction.SystemException;

/**
 * @author PVHung3
 *
 */
public interface IMansionInfoLogic {

	/**
	 * Get mansion info by ApartmentId
	 * @param apartmentId
	 * @return
	 * @throws BusinessException
	 */
	MansionInfoVo getMansionInformationById(String apartmentId) throws SystemException;
	
	/**
	 * Check exist mansion with supportCd
	 * @param apartmentId
	 * @param supportCd
	 * @return
	 */
	boolean isExistMansionWithSupportCd(String apartmentId, String supportCd);
}
