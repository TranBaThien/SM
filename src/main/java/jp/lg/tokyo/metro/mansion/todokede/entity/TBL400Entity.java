package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateTimeAttributeConverter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The persistent class for the TBL400 database table.
 * 
 */
@Entity
@Table(name="TBL400")
public class TBL400Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="APPLICATION_NO", unique=true, nullable=false, length=10)
	private String applicationNo;

	@Column(name="ADDRESS", length=100)
	private String address;

	@Column(name="APARTMENT_ID", length=10)
	private String apartmentId;

	@Column(name="APARTMENT_NAME", length=50)
	private String apartmentName;

	@Column(name="APARTMENT_NAME_PHONETIC", length=100)
	private String apartmentNamePhonetic;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="APPLICATION_TIME", length=14)
	private LocalDateTime applicationTime;

	@Column(name="CITY_CODE", length=6)
	private String cityCode;

	@Column(name="CONTACT_ADDRESS", length=100)
	private String contactAddress;

	@Column(name="CONTACT_MAIL_ADDRESS", length=120)
	private String contactMailAddress;

	@Column(name="CONTACT_NAME", length=34)
	private String contactName;

	@Column(name="CONTACT_NAME_PHONETIC", length=34)
	private String contactNamePhonetic;

	@Column(name="CONTACT_PROPERTY_CODE", length=1)
	private String contactPropertyCode;

	@Column(name="CONTACT_PROPERTY_ELSE", length=30)
	private String contactPropertyElse;

	@Column(name="CONTACT_TEL_NO", length=13)
	private String contactTelNo;

	@Column(name="CONTACT_ZIP_CODE", length=8)
	private String contactZipCode;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="JUDGE_REMARKS", length=300)
	private String judgeRemarks;

	@Column(name="JUDGE_RESULT", length=1)
	private String judgeResult;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="JUDGE_TIME", length=14)
	private LocalDateTime judgeTime;

	@Column(name="PASSWORD", length=100)
	private String password;

	@Column(name="RESULT_ADDRESS", length=100)
	private String resultAddress;

	@Column(name="RESULT_APARTMENT_NAME", length=50)
	private String resultApartmentName;

	@Column(name="RESULT_CITY_CODE", length=6)
	private String resultCityCode;

	@Column(name="RESULT_ZIP_CODE", length=8)
	private String resultZipCode;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="ZIP_CODE", length=8)
	private String zipCode;

	//bi-directional one-to-one association to Tbl100
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="APPLICATION_NO", nullable=false, insertable=false, updatable=false)
	private TBL100Entity tBL100Entity;

	public TBL400Entity() {
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApartmentId() {
		return this.apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getApartmentName() {
		return this.apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getApartmentNamePhonetic() {
		return this.apartmentNamePhonetic;
	}

	public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
		this.apartmentNamePhonetic = apartmentNamePhonetic;
	}

	public LocalDateTime getApplicationTime() {
		return this.applicationTime;
	}

	public void setApplicationTime(LocalDateTime applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactMailAddress() {
		return this.contactMailAddress;
	}

	public void setContactMailAddress(String contactMailAddress) {
		this.contactMailAddress = contactMailAddress;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNamePhonetic() {
		return this.contactNamePhonetic;
	}

	public void setContactNamePhonetic(String contactNamePhonetic) {
		this.contactNamePhonetic = contactNamePhonetic;
	}

	public String getContactPropertyCode() {
		return this.contactPropertyCode;
	}

	public void setContactPropertyCode(String contactPropertyCode) {
		this.contactPropertyCode = contactPropertyCode;
	}

	public String getContactPropertyElse() {
		return this.contactPropertyElse;
	}

	public void setContactPropertyElse(String contactPropertyElse) {
		this.contactPropertyElse = contactPropertyElse;
	}

	public String getContactTelNo() {
		return this.contactTelNo;
	}

	public void setContactTelNo(String contactTelNo) {
		this.contactTelNo = contactTelNo;
	}

	public String getContactZipCode() {
		return this.contactZipCode;
	}

	public void setContactZipCode(String contactZipCode) {
		this.contactZipCode = contactZipCode;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getJudgeRemarks() {
		return this.judgeRemarks;
	}

	public void setJudgeRemarks(String judgeRemarks) {
		this.judgeRemarks = judgeRemarks;
	}

	public String getJudgeResult() {
		return this.judgeResult;
	}

	public void setJudgeResult(String judgeResult) {
		this.judgeResult = judgeResult;
	}

	public LocalDateTime getJudgeTime() {
		return this.judgeTime;
	}

	public void setJudgeTime(LocalDateTime judgeTime) {
		this.judgeTime = judgeTime;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResultAddress() {
		return this.resultAddress;
	}

	public void setResultAddress(String resultAddress) {
		this.resultAddress = resultAddress;
	}

	public String getResultApartmentName() {
		return this.resultApartmentName;
	}

	public void setResultApartmentName(String resultApartmentName) {
		this.resultApartmentName = resultApartmentName;
	}

	public String getResultCityCode() {
		return this.resultCityCode;
	}

	public void setResultCityCode(String resultCityCode) {
		this.resultCityCode = resultCityCode;
	}

	public String getResultZipCode() {
		return this.resultZipCode;
	}

	public void setResultZipCode(String resultZipCode) {
		this.resultZipCode = resultZipCode;
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

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public TBL100Entity getTbl100() {
		return this.tBL100Entity;
	}

	public void setTbl100(TBL100Entity tBL100Entity) {
		this.tBL100Entity = tBL100Entity;
	}

}