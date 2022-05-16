package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateAttributeConverter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;


/**
 * The persistent class for the tbl225 database table.
 * 
 */
@Entity
@Table(name="tbl225")
public class TBL225Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TEMP_NO", unique=true, nullable=false, length=10)
	private String tempNo;

	@Column(name="ADDRESS", length=108)
	private String address;

	@Column(name="ADVICE_DETAILS", length=1000)
	private String adviceDetails;

	@Column(name="ADVICE_USER_ID", length=10)
	private String adviceUserId;

	@Column(name="APARTMENT_NAME", length=50)
	private String apartmentName;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="DOCUMENT_NO", length=20)
	private String documentNo;

	@Column(name="EVIDENCE_BAR", length=1)
	private String evidenceBar;

	@Column(name="EVIDENCE_NO", length=1)
	private String evidenceNo;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTICE_DATE", length=8)
	private LocalDate noticeDate;

	@Column(name="APPENDIX_NO", length=20)
	private String appendixNo;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTIFICATION_DATE", length=8)
	private LocalDate notificationDate;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="NOTIFICATION_NO", length=10)
	private String notificationNo;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="TEMP_KBN", length=1)
	private String tempKbn;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBL225Entity() {
	}

	public String getTempNo() {
		return tempNo;
	}

	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdviceDetails() {
		return adviceDetails;
	}

	public void setAdviceDetails(String adviceDetails) {
		this.adviceDetails = adviceDetails;
	}

	public String getAdviceUserId() {
		return adviceUserId;
	}

	public void setAdviceUserId(String adviceUserId) {
		this.adviceUserId = adviceUserId;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
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

	public String getEvidenceBar() {
		return evidenceBar;
	}

	public void setEvidenceBar(String evidenceBar) {
		this.evidenceBar = evidenceBar;
	}

	public String getEvidenceNo() {
		return evidenceNo;
	}

	public void setEvidenceNo(String evidenceNo) {
		this.evidenceNo = evidenceNo;
	}

	public LocalDate getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(LocalDate noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getAppendixNo() {
		return appendixNo;
	}

	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	public LocalDate getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationMethodCode() {
		return notificationMethodCode;
	}

	public void setNotificationMethodCode(String notificationMethodCode) {
		this.notificationMethodCode = notificationMethodCode;
	}

	public String getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}

	public String getRecipientNameApartment() {
		return recipientNameApartment;
	}

	public void setRecipientNameApartment(String recipientNameApartment) {
		this.recipientNameApartment = recipientNameApartment;
	}

	public String getRecipientNameUser() {
		return recipientNameUser;
	}

	public void setRecipientNameUser(String recipientNameUser) {
		this.recipientNameUser = recipientNameUser;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTempKbn() {
		return tempKbn;
	}

	public void setTempKbn(String tempKbn) {
		this.tempKbn = tempKbn;
	}

	public Timestamp getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}