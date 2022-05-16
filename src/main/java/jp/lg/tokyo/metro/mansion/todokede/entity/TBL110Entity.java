package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateTimeAttributeConverter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The persistent class for the TBL110 database table.
 * 
 */
@Entity
@Table(name="TBL110")
public class TBL110Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="APARTMENT_ID", unique=true, nullable=false, length=10)
	private String apartmentId;

	@Column(name="ACCOUNT_LOCK_FLAG", length=1)
	private String accountLockFlag;

	@Column(name="ACCOUNT_LOCK_TIME")
	private LocalDateTime accountLockTime;

	@Column(name="AVAILABILITY", length=1)
	private String availability;

	@Column(name="BIGINING_PASSWORD_CHANGE_FLAG", length=1)
	private String biginingPasswordChangeFlag;

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

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="LOGIN_VALIDITY_TIME", length=14)
	private LocalDateTime loginValidityTime;

	@Column(name="PASSWORD", length=100)
	private String password;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="PASSWORD_PERIOD", length=14)
	private LocalDateTime passwordPeriod;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="STOP_TIME", length=14)
	private LocalDateTime stopTime;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="VALIDITY_FLAG", length=1)
	private String validityFlag;

	//bi-directional one-to-one association to TBL100Entity
	@OneToOne(mappedBy="tbl110", fetch=FetchType.LAZY)
	private TBL100Entity tbl100;

	public TBL110Entity() {
	}

	public String getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getAccountLockFlag() {
		return this.accountLockFlag;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public LocalDateTime getLoginValidityTime() {
		return this.loginValidityTime;
	}

	public void setLoginValidityTime(LocalDateTime loginValidityTime) {
		this.loginValidityTime = loginValidityTime;
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

	public LocalDateTime getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	public Timestamp getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getValidityFlag() {
		return this.validityFlag;
	}

	public void setValidityFlag(String validityFlag) {
		this.validityFlag = validityFlag;
	}

	public TBL100Entity getTbl100() {
		return this.tbl100;
	}

	public void setTbl100(TBL100Entity tbl100) {
		this.tbl100 = tbl100;
	}

}