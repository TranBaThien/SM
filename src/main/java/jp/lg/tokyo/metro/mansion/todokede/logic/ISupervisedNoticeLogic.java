/*
 * @(#) ISupervisedNoticeLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SupervisedNoticeLogicForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.SupervisedNoticeLogicVo;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;

public interface ISupervisedNoticeLogic  {
	
	

	/**
	 * @param apartmentId
	 * @return TBL100Entity
	 * @throws BusinessException 
	 */
	TBL100Entity getApartmentInformation(String apartmentId) throws BusinessException;

	
	/**
	 * @param notificationNo
	 * @return TBL200Entity
	 * @throws BusinessException 
	 */
	TBL200Entity getNotificationInformation(String notificationNo) throws BusinessException;

	
	/**
	 * @param apartmentId, newestNotificationNo
	 * @return SupervisedNoticeLogicVo
	 * @throws BusinessException 
	 */
	SupervisedNoticeLogicVo getSupervisedNoticeData(String apartmentId) throws BusinessException;

	/**
	 * @param form
	 * @return String
	 * @throws BusinessException 
	 */
	String superviseNoticeRegister(SupervisedNoticeLogicForm form) throws BusinessException;


	/**
	 * @param SupervisedNoticeNo
	 * @return
	 * @throws BusinessException
	 */
	TBL240Entity getSupervisedNoticeBySupervisedNoticeNo(String supervisedNoticeNo) throws BusinessException;


	/**
	 * @param form
	 * @return Boolean
	 */
	boolean exclusiveCheckReportStatus(SupervisedNoticeLogicForm form);
	
	/**
	 * @param form
	 * @return Boolean
	 */
	boolean exclusiveCheckDunningNoticeCount(SupervisedNoticeLogicForm form);
	
}
