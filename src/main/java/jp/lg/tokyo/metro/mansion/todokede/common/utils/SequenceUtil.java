/*
 * @(#)SequenceUtil.java 2019/11/22
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/22
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISequenceLogic;

@Component
public class SequenceUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(SequenceUtil.class);

	@Autowired
	private ISequenceLogic iSequenceLogic;
	
	/**
	 * Generate key for PK table TBL105, TBL120, TBL200, TBL210
	 * TBL220, TBL230, TBL240, TBL300, TBL310, TBL400, TBL205, TBL215, TBL225, TBL235
	 * Ex: 1000000001,1000000002
	 * @param tableId
	 * @param columnName
	 * @return current_no + incrementNo
	 */
	public String generateKey(String tableId, String columnName) {
		try {
			//Check condition tableId and column name is required
			if (StringUtils.isEmptyOrWhitespace(tableId) || StringUtils.isEmptyOrWhitespace(columnName)) {
				return CommonConstants.ZERO;
			}
			//Get sequence data
			TBS001Entity ett = this.iSequenceLogic.findSequenceForTbl(tableId, columnName);
			//Can't get data
			if (ett != null && this.iSequenceLogic.save(prepareDataForGenKey(ett))) {
				//Save data
				return String.valueOf(ett.getCurrentNo());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return CommonConstants.ZERO;
	}
	
	/**
	 * Method generate key for table TBL100
	 * Ex: 1016000001, 1016000002
	 * @param tableId
	 * @param columnName
	 * @param cityCode
	 * @return cityCode + current_no + incrementNo
	 */
	public String generateKey(String tableId, String columnName, String cityCode) {
		try {
			//Check condition tableId and column name is required
			if (StringUtils.isEmptyOrWhitespace(tableId) || StringUtils.isEmptyOrWhitespace(columnName)
					|| StringUtils.isEmptyOrWhitespace(cityCode)) {
				return CommonConstants.ZERO;
			}
			String currentNo = generateKey(tableId, columnName);
			return cityCode.concat(calculateFormatString(currentNo,false));
				
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return CommonConstants.ZERO;
	}
	
	/**
	 * Method generate key for table TBL110, TBL120
	 * Ex: M1234561, G1234561, A1234561
	 * @param tableId
	 * @param columnName
	 * @param userTypeCode
	 * @return append string M,G,A + currentNo + incrementNo + digit
	 */
	public String generateKey(String tableId, String columnName, UserTypeCode userTypeCode) {
		
		try {
			//Get current No
			String currentNo = generateKey(tableId, columnName);
			//append string
			return appendPrefixCurrentNo(String.valueOf(currentNo), userTypeCode);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return CommonConstants.ZERO;
	}
	
	/**
	 * Method append string M,G,A + currentNo + incrementNo + digit
	 * @param currentNo
	 * @param userTypeCode
	 * @return
	 */
	private String appendPrefixCurrentNo(String currentNo, UserTypeCode userTypeCode) {
		StringBuilder prefix = new StringBuilder("");
		// Check userTypeCode
		switch (userTypeCode) {
			case TOKYO_STAFF:
				prefix.append(CommonConstants.SEQ_PREFIX_G);
				break;
			case CITY:
				prefix.append(CommonConstants.SEQ_PREFIX_G);
				break;
			case SYSTEM_ADMIN:
				prefix.append(CommonConstants.SEQ_PREFIX_A);
				break;
			case MAINTENANCER:
				prefix.append(CommonConstants.SEQ_PREFIX_A);
				break;
			default:
				prefix.append(CommonConstants.SEQ_PREFIX_M);
				break;
				
		}
		//Get digit no with Sequence no
		String digitFromSeqNo = calculateCheckDigit(currentNo);
		
		return prefix.append(calculateFormatString(String.valueOf(currentNo),false)).append(digitFromSeqNo).toString();
	}
	
	/**
	 * Prepare data for update data
	 * @param ett
	 * @return TBS001Entity
	 */
	private TBS001Entity prepareDataForGenKey(TBS001Entity ett) {
		int currentNo = ett.getCurrentNo();
		// Check the first get current no
		if (currentNo == 0) {
			currentNo = ett.getStartNo();
		}
		// Get increment from DB
		int incrementNo = ett.getIncrementNo();

		// currentNo + increment = key
		ett.setCurrentNo(currentNo += incrementNo);
		// Set current date time for update Date
		ett.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
		ett.setUpdateUserId(!StringUtils.isEmpty(SecurityUtil.getCurrentLoginId()) ? SecurityUtil.getCurrentLoginId() : CommonConstants.BLANK);

		return ett;
	}
	
	/**
	 * Check format 6 digit number
	 * @param type
	 * @param hasCheckDigit
	 * @return format string 
	 */
	private String calculateFormatString(String value, boolean hasCheckDigit) {
		// if hasCheckDigit == true, then init value of leng = 1, otherwise,leng = 0
		int leng = hasCheckDigit ? 1 : 0;
		if (!StringUtils.isEmpty(value)) {
			leng += value.length();
		}
		//Append zero number to string
		StringBuilder fmtStr = new StringBuilder("");
		for (int i = 0; i < 6 - leng; i++) {
			fmtStr.append("0");
		}
		return fmtStr.append(value).toString();
	}
	
	/**
	 * Calculate digit after
	 * @param sequenceNumber
	 * @return digit number
	 */
	public String calculateCheckDigit(String sequenceNumber) {
		int digit = 0;
		int leng = sequenceNumber.length();

		// digit = digit + ((index of character) + 1) * (character at the index)
		for (int i = 0; i < leng; i++) {
			digit += (i + 1) * Integer.valueOf(sequenceNumber.substring(i, i + 1));
		}

		String sDigit = String.valueOf(digit);

		// return the last character
		return sDigit.substring(sDigit.length() - 1);
	}

}
