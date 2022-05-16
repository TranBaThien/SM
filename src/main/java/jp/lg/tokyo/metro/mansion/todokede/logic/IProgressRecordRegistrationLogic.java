/*
 * @(#) IProgressRecordRegistrationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/09
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.FileManagerInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordRegistrationVo;

public interface IProgressRecordRegistrationLogic extends IBaseLogic<Object> {

    /**
	 * Get progressRecord from table TBL300 by progressRecordNo
	 * @param progressRecordNo
	 * @return
	 * @throws BusinessException
	 */
	public ProgressRecordRegistrationVo getProgressRecordTbl300(String progressRecordNo) throws BusinessException;
	
	/**
	 * Method get file from tbl310 
	 * @param progressRecordNo
	 * @return get list file
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public List<FileManagerInfoVo> getProgressRecordFileInfos(String progressRecordNo) throws Exception;

	/**
	 * Method get file from tbl310 for download file
	 * @param progressRecordAttachNo
	 * @return get  file
	 */
	public FileManagerInfoVo findProgressRecordFileById(String progressRecordAttachNo);
	/**
	 * Method check record update is latest in tbl300
	 * @param progressRecordNo
	 * @param dateTime
	 * @return true is latest
	 */
	public boolean isUpdateLatestTbl300(String progressRecordNo, String dateTime);
	
	/**
	 * Method check input correspondDate is max
	 * @param appartmentId
	 * @param corrsponseDate
	 * @return
	 */
	public boolean isValidMaxCorrespondDate(String appartmentId, String corrsponseDate);
}
