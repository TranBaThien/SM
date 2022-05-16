/*
 * @(#) GovernmentStaffInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.time.LocalDateTime;

/**
 * @author DLLy
 *
 */
public class GovernmentStaffInfoVo extends BaseVo {

	private String userId;

	private String accountLockFlag;

	private LocalDateTime accountLockTime;

	private String availability;

	private String biginingPasswordChangeFlag;

	private String cityCode;

	private LocalDateTime lastTimeLoginTime;

	private int loginErrorCount;

	private String loginId;

	private String loginStatusFlag;

	private String mailAddress;

	private String password;

	private LocalDateTime passwordPeriod;

	private String stopReason;

	private String stopTime;

	private String telNo;

	private LocalDateTime updateDatetime;

	private String updateUserId;

	private String userAuthority;

	private String userName;

	private String userNamePhonetic;

	private String userType;

	public GovernmentStaffInfoVo() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountLockFlag() {
		return accountLockFlag;
	}

	public void setAccountLockFlag(String accountLockFlag) {
		this.accountLockFlag = accountLockFlag;
	}

	public LocalDateTime getAccountLockTime() {
		return accountLockTime;
	}

	public void setAccountLockTime(LocalDateTime accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getBiginingPasswordChangeFlag() {
		return biginingPasswordChangeFlag;
	}

	public void setBiginingPasswordChangeFlag(String biginingPasswordChangeFlag) {
		this.biginingPasswordChangeFlag = biginingPasswordChangeFlag;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public LocalDateTime getLastTimeLoginTime() {
		return lastTimeLoginTime;
	}

	public void setLastTimeLoginTime(LocalDateTime lastTimeLoginTime) {
		this.lastTimeLoginTime = lastTimeLoginTime;
	}

	public int getLoginErrorCount() {
		return loginErrorCount;
	}

	public void setLoginErrorCount(int loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginStatusFlag() {
		return loginStatusFlag;
	}

	public void setLoginStatusFlag(String loginStatusFlag) {
		this.loginStatusFlag = loginStatusFlag;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getPasswordPeriod() {
		return passwordPeriod;
	}

	public void setPasswordPeriod(LocalDateTime passwordPeriod) {
		this.passwordPeriod = passwordPeriod;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNamePhonetic() {
		return userNamePhonetic;
	}

	public void setUserNamePhonetic(String userNamePhonetic) {
		this.userNamePhonetic = userNamePhonetic;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
