package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The persistent class for the TBL120 database table.
 * 
 */
@Entity
@Table(name="TBL120")
public class TBL120Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false, length=10)
	private String userId;

	@Column(name="ACCOUNT_LOCK_FLAG", length=1)
	private String accountLockFlag;

	@Column(name="ACCOUNT_LOCK_TIME")
	private LocalDateTime accountLockTime;

	@Column(name="AVAILABILITY", length=1)
	private String availability;

	@Column(name="BIGINING_PASSWORD_CHANGE_FLAG", length=1)
	private String biginingPasswordChangeFlag;

	@Column(name="CITY_CODE", length=6)
	private String cityCode;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="LAST_TIME_LOGIN_TIME", length=14)
	private LocalDateTime lastTimeLoginTime;

	@Column(name="LOGIN_ERROR_COUNT")
	private int loginErrorCount;

	@Column(name="LOGIN_ID", length=8)
	private String loginId;

	@Column(name="LOGIN_STATUS_FLAG", length=1)
	private String loginStatusFlag;

	@Column(name="MAIL_ADDRESS", length=120)
	private String mailAddress;

	@Column(name="PASSWORD", length=100)
	private String password;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="PASSWORD_PERIOD", length=14)
	private LocalDateTime passwordPeriod;

	@Column(name="STOP_REASON", length=30)
	private String stopReason;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="STOP_TIME", length=14)
	private LocalDateTime stopTime;

	@Column(name="TEL_NO", length=13)
	private String telNo;

	@Column(name="UPDATE_DATETIME")
	private LocalDateTime updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="USER_NAME", length=16)
	private String userName;

	@Column(name="USER_NAME_PHONETIC", length=16)
	private String userNamePhonetic;

	@Column(name="USER_TYPE", length=1)
	private String userType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CITY_CODE", referencedColumnName = "CITY_CODE", insertable = false, updatable = false)
	private TBM001Entity tbm001;

	public TBL120Entity() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountLockFlag() {
		return this.accountLockFlag;
	}

	public void setAccountLockFlag(String accountLockFlag) {
		this.accountLockFlag = accountLockFlag;
	}

	public LocalDateTime getAccountLockTime() {
		return this.accountLockTime;
	}

	public void setAccountLockTime(LocalDateTime accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	public String getAvailability() {
		return this.availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getBiginingPasswordChangeFlag() {
		return this.biginingPasswordChangeFlag;
	}

	public void setBiginingPasswordChangeFlag(String biginingPasswordChangeFlag) {
		this.biginingPasswordChangeFlag = biginingPasswordChangeFlag;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public LocalDateTime getLastTimeLoginTime() {
		return this.lastTimeLoginTime;
	}

	public void setLastTimeLoginTime(LocalDateTime lastTimeLoginTime) {
		this.lastTimeLoginTime = lastTimeLoginTime;
	}

	public int getLoginErrorCount() {
		return this.loginErrorCount;
	}

	public void setLoginErrorCount(int loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginStatusFlag() {
		return this.loginStatusFlag;
	}

	public void setLoginStatusFlag(String loginStatusFlag) {
		this.loginStatusFlag = loginStatusFlag;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getPasswordPeriod() {
		return this.passwordPeriod;
	}

	public void setPasswordPeriod(LocalDateTime passwordPeriod) {
		this.passwordPeriod = passwordPeriod;
	}

	public String getStopReason() {
		return this.stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public LocalDateTime getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public LocalDateTime getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNamePhonetic() {
		return this.userNamePhonetic;
	}

	public void setUserNamePhonetic(String userNamePhonetic) {
		this.userNamePhonetic = userNamePhonetic;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public TBM001Entity getTbm001() {
		return this.tbm001;
	}

	public void setTbm001(TBM001Entity tbm001) {
		this.tbm001 = tbm001;
	}
}