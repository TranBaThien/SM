package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl240 database table.
 * 
 */
@Entity
@Table(name="tbl240")
public class TBL240Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUPERVISED_NOTICE_NO", unique=true, nullable=false, length=10)
	private String supervisedNoticeNo;
	
	@Column(name="APARTMENT_ID", length=10)
	private String apartmentId;

	@Column(name="ADDRESS", length=108)
	private String address;

	@Column(name="APARTMENT_NAME", length=50)
	private String apartmentName;

	@Column(name="APPENDIX_NO", length=20)
	private String appendixNo;

	@Column(name="CONTACT", length=300)
	private String contact;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="DOCUMENT_NO", length=20)
	private String documentNo;

	@Column(name="EVIDENCE_BAR", length=1)
	private String evidenceBar;

	@Column(name="EVIDENCE_NO", length=1)
	private String evidenceNo;

	@Column(name="LAST_DOCUMENT_NO", length=20)
	private String lastDocumentNo;

	@Column(name="LAST_NOTICE_DATE", length=8)
	private String lastNoticeDate;

	@Column(name="MAILING_CODE", length=1)
	private String mailingCode;

	@Column(name="NOTICE_DATE", length=8)
	private String noticeDate;

	@Column(name="NOTIFICATION_FORMAT_NAME", length=1)
	private String notificationFormatName;

	@Column(name="NOTIFICATION_NO", length=10)
	private String notificationNo;

	@Column(name="NOTIFICATION_TIMELIMIT", length=8)
	private String notificationTimelimit;

	@Column(name="PERIOD_EVIDENCE", length=1)
	private String periodEvidence;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="SUPERVISED_NOTICE_COUNT")
	private int supervisedNoticeCount;

	@Column(name="SUPERVISED_NOTICE_TYPE_CODE", length=1)
	private String supervisedNoticeTypeCode;
	
	@Column(name="TEXTADDRESS1", length=31)
	private String textaddress1;
	
	@Column(name="TEXTADDRESS2", length=6)
	private String textaddress2;
	
	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	//bi-directional many-to-one association to TBL100Entity
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APARTMENT_ID", insertable = false, updatable = false)
	private TBL100Entity tbl100;

	public TBL240Entity() {
	}
	
	public String getTextaddress1() {
		return textaddress1;
	}

	public void setTextaddress1(String textaddress1) {
		this.textaddress1 = textaddress1;
	}

	public String getTextaddress2() {
		return textaddress2;
	}

	public void setTextaddress2(String textaddress2) {
		this.textaddress2 = textaddress2;
	}

	/**
	 * @return the supervisedNoticeNo
	 */
	public String getSupervisedNoticeNo() {
		return supervisedNoticeNo;
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	/**
	 * @param supervisedNoticeNo the supervisedNoticeNo to set
	 */
	public void setSupervisedNoticeNo(String supervisedNoticeNo) {
		this.supervisedNoticeNo = supervisedNoticeNo;
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
	 * @return the appendixNo
	 */
	public String getAppendixNo() {
		return appendixNo;
	}

	/**
	 * @param appendixNo the appendixNo to set
	 */
	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
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
	 * @return the lastDocumentNo
	 */
	public String getLastDocumentNo() {
		return lastDocumentNo;
	}

	/**
	 * @param lastDocumentNo the lastDocumentNo to set
	 */
	public void setLastDocumentNo(String lastDocumentNo) {
		this.lastDocumentNo = lastDocumentNo;
	}

	/**
	 * @return the lastNoticeDate
	 */
	public String getLastNoticeDate() {
		return lastNoticeDate;
	}

	/**
	 * @param lastNoticeDate the lastNoticeDate to set
	 */
	public void setLastNoticeDate(String lastNoticeDate) {
		this.lastNoticeDate = lastNoticeDate;
	}

	/**
	 * @return the mailingCode
	 */
	public String getMailingCode() {
		return mailingCode;
	}

	/**
	 * @param mailingCode the mailingCode to set
	 */
	public void setMailingCode(String mailingCode) {
		this.mailingCode = mailingCode;
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

	/**
	 * @return the notificationFormatName
	 */
	public String getNotificationFormatName() {
		return notificationFormatName;
	}

	/**
	 * @param notificationFormatName the notificationFormatName to set
	 */
	public void setNotificationFormatName(String notificationFormatName) {
		this.notificationFormatName = notificationFormatName;
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
	 * @return the notificationTimelimit
	 */
	public String getNotificationTimelimit() {
		return notificationTimelimit;
	}

	/**
	 * @param notificationTimelimit the notificationTimelimit to set
	 */
	public void setNotificationTimelimit(String notificationTimelimit) {
		this.notificationTimelimit = notificationTimelimit;
	}

	/**
	 * @return the periodEvidence
	 */
	public String getPeriodEvidence() {
		return periodEvidence;
	}

	/**
	 * @param periodEvidence the periodEvidence to set
	 */
	public void setPeriodEvidence(String periodEvidence) {
		this.periodEvidence = periodEvidence;
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
	 * @return the supervisedNoticeCount
	 */
	public int getSupervisedNoticeCount() {
		return supervisedNoticeCount;
	}

	/**
	 * @param supervisedNoticeCount the supervisedNoticeCount to set
	 */
	public void setSupervisedNoticeCount(int supervisedNoticeCount) {
		this.supervisedNoticeCount = supervisedNoticeCount;
	}

	/**
	 * @return the supervisedNoticeTypeCode
	 */
	public String getSupervisedNoticeTypeCode() {
		return supervisedNoticeTypeCode;
	}

	/**
	 * @param supervisedNoticeTypeCode the supervisedNoticeTypeCode to set
	 */
	public void setSupervisedNoticeTypeCode(String supervisedNoticeTypeCode) {
		this.supervisedNoticeTypeCode = supervisedNoticeTypeCode;
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

	public TBL100Entity getTbl100() {
		return this.tbl100;
	}

	public void setTbl100(TBL100Entity tbl100) {
		this.tbl100 = tbl100;
	}

}