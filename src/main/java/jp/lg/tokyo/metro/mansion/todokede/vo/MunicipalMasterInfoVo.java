/*
 * @(#) MunicipalMasterInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author DLLy
 */
public class MunicipalMasterInfoVo extends BaseVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cityCode;

	private String chiefName;

	private String cityName;

	private String deleteFlag;

	private String documentNo;

	private LocalDateTime updateDatetime;

	private String updateUserId;

	private String userAuthority;
	
	private String cityHeadWord;

	public MunicipalMasterInfoVo() {

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCityHeadWord() {
		return cityHeadWord;
	}

	public void setCityHeadWord(String cityHeadWord) {
		this.cityHeadWord = cityHeadWord;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getChiefName() {
		return chiefName;
	}

	public void setChiefName(String chiefName) {
		this.chiefName = chiefName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public LocalDateTime getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}
}
