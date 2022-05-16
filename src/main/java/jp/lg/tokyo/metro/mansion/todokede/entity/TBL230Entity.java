package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl230 database table.
 * 
 */
@Entity
@Table(name="tbl230")
public class TBL230Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="INVESTIGATION_NO", unique=true, nullable=false, length=10)
	private String investigationNo;
	
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

	@Column(name="INVEST_IMPL_NUMBER_PEOPLE")
	private int investImplNumberPeople;

	@Column(name="INVEST_IMPL_PLAN_TIME", length=12)
	private String investImplPlanTime;

	@Column(name="NECESSARY_DOCUMENT", length=100)
	private String necessaryDocument;

	@Column(name="NOTICE_DATE", length=8)
	private String noticeDate;

	@Column(name="NOTICE_DESTINATION_CODE", length=1)
	private String noticeDestinationCode;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;
	
	@Column(name="TEXTADDRESS", length=31)
	private String textaddress;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	//bi-directional many-to-one association to TBL100Entity
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APARTMENT_ID", insertable = false, updatable = false)
	private TBL100Entity tbl100;

	public TBL230Entity() {
	}

	public String getTextaddress() {
		return textaddress;
	}

	public void setTextaddress(String textaddress) {
		this.textaddress = textaddress;
	}

	/**
	 * @return the apartmentId
	 */
	public String getApartmentId() {
		return apartmentId;
	}

	/**
	 * @param apartmentId the apartmentId to set
	 */
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getInvestigationNo() {
		return investigationNo;
	}

	public void setInvestigationNo(String investigationNo) {
		this.investigationNo = investigationNo;
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
	 * @return the investImplNumberPeople
	 */
	public int getInvestImplNumberPeople() {
		return investImplNumberPeople;
	}

	/**
	 * @param investImplNumberPeople the investImplNumberPeople to set
	 */
	public void setInvestImplNumberPeople(int investImplNumberPeople) {
		this.investImplNumberPeople = investImplNumberPeople;
	}

	/**
	 * @return the investImplPlanTime
	 */
	public String getInvestImplPlanTime() {
		return investImplPlanTime;
	}

	/**
	 * @param investImplPlanTime the investImplPlanTime to set
	 */
	public void setInvestImplPlanTime(String investImplPlanTime) {
		this.investImplPlanTime = investImplPlanTime;
	}

	/**
	 * @return the necessaryDocument
	 */
	public String getNecessaryDocument() {
		return necessaryDocument;
	}

	/**
	 * @param necessaryDocument the necessaryDocument to set
	 */
	public void setNecessaryDocument(String necessaryDocument) {
		this.necessaryDocument = necessaryDocument;
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
	 * @return the noticeDestinationCode
	 */
	public String getNoticeDestinationCode() {
		return noticeDestinationCode;
	}

	/**
	 * @param noticeDestinationCode the noticeDestinationCode to set
	 */
	public void setNoticeDestinationCode(String noticeDestinationCode) {
		this.noticeDestinationCode = noticeDestinationCode;
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

	public TBL100Entity getTbl100() {
		return this.tbl100;
	}

	public void setTbl100(TBL100Entity tbl100) {
		this.tbl100 = tbl100;
	}

}