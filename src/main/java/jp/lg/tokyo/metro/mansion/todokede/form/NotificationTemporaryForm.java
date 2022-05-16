/*
 * @(#) NotificationRegistrationForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author DLLy
 * Create Date: 2020/01/19
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMansionInfoVo;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author DLLy
 *
 */
public class NotificationTemporaryForm extends BaseMansionInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Valid
	private BasicNotificationInfoForm basicReportInfo;
	@Valid
	private NotificationInfoCommonTemporaryForm infoAreaCommon;

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

	public BasicNotificationInfoForm getBasicReportInfo() {
		return basicReportInfo;
	}
	public void setBasicReportInfo(BasicNotificationInfoForm basicReportInfo) {
		this.basicReportInfo = basicReportInfo;
	}
	public NotificationInfoCommonTemporaryForm getInfoAreaCommon() {
		return infoAreaCommon;
	}
	public void setInfoAreaCommon(NotificationInfoCommonTemporaryForm infoAreaCommon) {
		this.infoAreaCommon = infoAreaCommon;
	}
    public String getAcceptStatus() {
        return acceptStatus;
    }
    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
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
	public String getFileMaxSize() {
		return fileMaxSize;
	}
	public void setFileMaxSize(String fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	public boolean isUpdateNotification() {
		return updateNotification;
	}
	public void setUpdateNotification(boolean updateNotification) {
		this.updateNotification = updateNotification;
	}
	public boolean isScreenGJA0120() {
		return screenGJA0120;
	}
	public void setScreenGJA0120(boolean screenGJA0120) {
		this.screenGJA0120 = screenGJA0120;
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
