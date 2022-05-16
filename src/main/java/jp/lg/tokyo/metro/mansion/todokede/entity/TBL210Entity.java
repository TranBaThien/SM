package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.lg.tokyo.metro.mansion.todokede.converter.*;


/**
 * The persistent class for the tbl210 database table.
 * 
 */
@Entity
@Table(name="tbl210")
public class TBL210Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACCEPT_NO", unique=true, nullable=false, length=10)
	private String acceptNo;
	
	@Column(name="NOTIFICATION_NO", length=10)
	private String notificationNo;

	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name="ACCEPT_TIME", length=14)
	private LocalDateTime acceptTime;

	@Column(name="ACCEPT_USER_ID", length=10)
	private String acceptUserId;

	@Column(name="ACCEPT_USER_NAME", length=16)
	private String acceptUserName;

	@Column(name="APPENDIX_NO", length=20)
	private String appendixNo;

	@Column(name="AUTHORITY_MODIFY_FLAG", length=1)
	private String authorityModifyFlag;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="DOCUMENT_NO", length=20)
	private String documentNo;

	@Column(name="EVIDENCE_BAR", length=1)
	private String evidenceBar;

	@Column(name="EVIDENCE_NO", length=1)
	private String evidenceNo;

	@Column(name="MODIFY_DETAILS", length=500)
	private String modifyDetails;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTICE_DATE", length=8)
	private LocalDate noticeDate;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTIFICATION_DATE", length=8)
	private LocalDate notificationDate;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="REMARKS", length=300)
	private String remarks;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBL210Entity() {
	}

	public String getAcceptNo() {
		return this.acceptNo;
	}

	public void setAcceptNo(String acceptNo) {
		this.acceptNo = acceptNo;
	}

	public String getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}

	public LocalDateTime getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(LocalDateTime acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptUserId() {
		return this.acceptUserId;
	}

	public void setAcceptUserId(String acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public String getAcceptUserName() {
		return this.acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getAppendixNo() {
		return this.appendixNo;
	}

	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	public String getAuthorityModifyFlag() {
		return this.authorityModifyFlag;
	}

	public void setAuthorityModifyFlag(String authorityModifyFlag) {
		this.authorityModifyFlag = authorityModifyFlag;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getEvidenceBar() {
		return this.evidenceBar;
	}

	public void setEvidenceBar(String evidenceBar) {
		this.evidenceBar = evidenceBar;
	}

	public String getEvidenceNo() {
		return this.evidenceNo;
	}

	public void setEvidenceNo(String evidenceNo) {
		this.evidenceNo = evidenceNo;
	}

	public String getModifyDetails() {
		return this.modifyDetails;
	}

	public void setModifyDetails(String modifyDetails) {
		this.modifyDetails = modifyDetails;
	}

	public LocalDate getNoticeDate() {
		return this.noticeDate;
	}

	public void setNoticeDate(LocalDate noticeDate) {
		this.noticeDate = noticeDate;
	}

	public LocalDate getNotificationDate() {
		return this.notificationDate;
	}

	public void setNotificationDate(LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationMethodCode() {
		return this.notificationMethodCode;
	}

	public void setNotificationMethodCode(String notificationMethodCode) {
		this.notificationMethodCode = notificationMethodCode;
	}

	public String getRecipientNameApartment() {
		return this.recipientNameApartment;
	}

	public void setRecipientNameApartment(String recipientNameApartment) {
		this.recipientNameApartment = recipientNameApartment;
	}

	public String getRecipientNameUser() {
		return this.recipientNameUser;
	}

	public void setRecipientNameUser(String recipientNameUser) {
		this.recipientNameUser = recipientNameUser;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}