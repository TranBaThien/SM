package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.*;


/**
 * The persistent class for the tbl215 database table.
 * 
 */
@Entity
@Table(name="tbl215")
public class TBL215Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TEMP_NO", unique=true, nullable=false, length=10)
	private String tempNo;

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

	@Column(name="NOTIFICATION_NO", length=10)
	private String notificationNo;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="REMARKS", length=300)
	private String remarks;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="TEMP_KBN", length=1)
	private String tempKbn;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBL215Entity() {
	}

	public String getTempNo() {
		return this.tempNo;
	}

	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}

	public LocalDateTime getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(LocalDateTime acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(String acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getAppendixNo() {
		return appendixNo;
	}

	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	public String getAuthorityModifyFlag() {
		return authorityModifyFlag;
	}

	public void setAuthorityModifyFlag(String authorityModifyFlag) {
		this.authorityModifyFlag = authorityModifyFlag;
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

	public String getModifyDetails() {
		return modifyDetails;
	}

	public void setModifyDetails(String modifyDetails) {
		this.modifyDetails = modifyDetails;
	}

	public LocalDate getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(LocalDate noticeDate) {
		this.noticeDate = noticeDate;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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