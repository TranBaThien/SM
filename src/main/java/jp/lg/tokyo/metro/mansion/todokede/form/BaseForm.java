/*
 * @(#)BaseForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/18
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

/**
 * @author PVHung3
 *
 */
public class BaseForm {
	
	private String updateDatetime;

	private String updateUserId;
	
	private String deleteFlag;


	public String getUpdateUserId() {
		return updateUserId;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
