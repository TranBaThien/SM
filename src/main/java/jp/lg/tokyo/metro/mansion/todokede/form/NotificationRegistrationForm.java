/*
 * @(#) NotificationRegistrationForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/12
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMansionInfoVo;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDate;

public class NotificationRegistrationForm extends BaseMansionInfoVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Valid
	private BasicReportInfoForm basicReportInfo;
	@Valid
	private NotificationInfoAreaCommonForm infoAreaCommon;

	private String acceptStatus;
	private String notificationNo;
	private int changeCount;
	private int notificationCount;
	private LocalDate notificationDateTBL200;
	private String fileMaxSize;
	private boolean updateNotification;
	private boolean screenGJA0120;
	private boolean canRestore;
	private String chkConfirm;
	private String screenId;

    public String getAcceptStatus() {
        return acceptStatus;
    }
    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }
    public boolean isScreenGJA0120() {
		return screenGJA0120;
	}
	public void setScreenGJA0120(boolean screenGJA0120) {
		this.screenGJA0120 = screenGJA0120;
	}
	public boolean isUpdateNotification() {
		return updateNotification;
	}
	public void setUpdateNotification(boolean updateNotification) {
		this.updateNotification = updateNotification;
	}
	public String getFileMaxSize() {
		return fileMaxSize;
	}
	public void setFileMaxSize(String fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BasicReportInfoForm getBasicReportInfo() {
		return basicReportInfo;
	}
	public void setBasicReportInfo(BasicReportInfoForm basicReportInfo) {
		this.basicReportInfo = basicReportInfo;
	}
	public NotificationInfoAreaCommonForm getInfoAreaCommon() {
		return infoAreaCommon;
	}
	public void setInfoAreaCommon(NotificationInfoAreaCommonForm infoAreaCommon) {
		this.infoAreaCommon = infoAreaCommon;
	}
	public String getNotificationNo() {
		return notificationNo;
	}
	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}
	public int getChangeCount() {
		return changeCount;
	}
	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}
	public int getNotificationCount() {
		return notificationCount;
	}
	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}
	public LocalDate getNotificationDateTBL200() {
		return notificationDateTBL200;
	}
	public void setNotificationDateTBL200(LocalDate notificationDateTBL200) {
		this.notificationDateTBL200 = notificationDateTBL200;
	}
	public boolean isCanRestore() {
		return canRestore;
	}
	public void setCanRestore(boolean canRestore) {
		this.canRestore = canRestore;
	}
    public String getChkConfirm() {
        return chkConfirm;
    }
    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }
    public String getScreenId() {
        return screenId;
    }
    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }
}
