package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl220 database table.
 * 
 */
@Entity
@Table(name="tbl220")
public class TBL220Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ADVICE_NO", unique=true, nullable=false, length=10)
	private String adviceNo;
	
	@Column(name="NOTIFICATION_NO", length=10)
	private String notificationNo;

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

	@Column(name="NOTICE_DATE", length=8)
	private String noticeDate;

	@Column(name="APPENDIX_NO", length=20)
	private String appendixNo;

	@Column(name="NOTIFICATION_DATE", length=8)
	private String notificationDate;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBL220Entity() {
	}
	
	/**
	 * @return the notificationNo
	 */
	public String getNotificationNo() {
		return notificationNo;
	}

	/**
	 * @param notificationNo the notificationNo to set
	 */
	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}


	/**
	 * @return the adviceNo
	 */
	public String getAdviceNo() {
		return adviceNo;
	}

	/**
	 * @param adviceNo the adviceNo to set
	 */
	public void setAdviceNo(String adviceNo) {
		this.adviceNo = adviceNo;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the adviceDetails
	 */
	public String getAdviceDetails() {
		return adviceDetails;
	}

	/**
	 * @param adviceDetails the adviceDetails to set
	 */
	public void setAdviceDetails(String adviceDetails) {
		this.adviceDetails = adviceDetails;
	}

	/**
	 * @return the adviceUserId
	 */
	public String getAdviceUserId() {
		return adviceUserId;
	}

	/**
	 * @param adviceUserId the adviceUserId to set
	 */
	public void setAdviceUserId(String adviceUserId) {
		this.adviceUserId = adviceUserId;
	}

	/**
	 * @return the apartmentName
	 */
	public String getApartmentName() {
		return apartmentName;
	}

	/**
	 * @param apartmentName the apartmentName to set
	 */
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the documentNo
	 */
	public String getDocumentNo() {
		return documentNo;
	}

	/**
	 * @param documentNo the documentNo to set
	 */
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	/**
	 * @return the evidenceBar
	 */
	public String getEvidenceBar() {
		return evidenceBar;
	}

	/**
	 * @param evidenceBar the evidenceBar to set
	 */
	public void setEvidenceBar(String evidenceBar) {
		this.evidenceBar = evidenceBar;
	}

	/**
	 * @return the evidenceNo
	 */
	public String getEvidenceNo() {
		return evidenceNo;
	}

	/**
	 * @param evidenceNo the evidenceNo to set
	 */
	public void setEvidenceNo(String evidenceNo) {
		this.evidenceNo = evidenceNo;
	}

	/**
	 * @return the noticeDate
	 */
	public String getNoticeDate() {
		return noticeDate;
	}

	/**
	 * @param noticeDate the noticeDate to set
	 */
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getAppendixNo() {
		return appendixNo;
	}

	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	/**
	 * @return the notificationDate
	 */
	public String getNotificationDate() {
		return notificationDate;
	}

	/**
	 * @param notificationDate the notificationDate to set
	 */
	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	/**
	 * @return the notificationMethodCode
	 */
	public String getNotificationMethodCode() {
		return notificationMethodCode;
	}

	/**
	 * @param notificationMethodCode the notificationMethodCode to set
	 */
	public void setNotificationMethodCode(String notificationMethodCode) {
		this.notificationMethodCode = notificationMethodCode;
	}

	/**
	 * @return the recipientNameApartment
	 */
	public String getRecipientNameApartment() {
		return recipientNameApartment;
	}

	/**
	 * @param recipientNameApartment the recipientNameApartment to set
	 */
	public void setRecipientNameApartment(String recipientNameApartment) {
		this.recipientNameApartment = recipientNameApartment;
	}

	/**
	 * @return the recipientNameUser
	 */
	public String getRecipientNameUser() {
		return recipientNameUser;
	}

	/**
	 * @param recipientNameUser the recipientNameUser to set
	 */
	public void setRecipientNameUser(String recipientNameUser) {
		this.recipientNameUser = recipientNameUser;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the updateDatetime
	 */
	public Timestamp getUpdateDatetime() {
		return updateDatetime;
	}

	/**
	 * @param updateDatetime the updateDatetime to set
	 */
	public void setUpdateDatetime(Timestamp updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	/**
	 * @return the updateUserId
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}