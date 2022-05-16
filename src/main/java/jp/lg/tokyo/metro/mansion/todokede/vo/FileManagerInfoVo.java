/*
 * @(#) FileManagerInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

/**
 * @author PVHung3
 *
 */
public class FileManagerInfoVo extends BaseVo implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String progressRecordAttachNo;
	private String progressRecordNo;

	private byte[] file;

	private String fileName;

	private String deleteFlag;

	public String getProgressRecordAttachNo() {
		return progressRecordAttachNo;
	}

	public void setProgressRecordAttachNo(String progressRecordAttachNo) {
		this.progressRecordAttachNo = progressRecordAttachNo;
	}

	public String getProgressRecordNo() {
		return progressRecordNo;
	}

	public void setProgressRecordNo(String progressRecordNo) {
		this.progressRecordNo = progressRecordNo;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
