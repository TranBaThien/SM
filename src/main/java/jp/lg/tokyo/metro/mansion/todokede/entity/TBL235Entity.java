package jp.lg.tokyo.metro.mansion.todokede.entity;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateAttributeConverter;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;


/**
 * The persistent class for the tbl235 database table.
 * 
 */
@Entity
@Table(name="tbl235")
public class TBL235Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TEMP_NO", unique=true, nullable=false, length=10)
	private String tempNo;

	@Column(name="ADDRESS", length=108)
	private String address;

	@Column(name="APARTMENT_ID", length=10)
	private String apartmentId;

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
	private String investImplNumberPeople;

	@Column(name="INVEST_IMPL_PLAN_TIME", length=12)
	private String investImplPlanTime;

	@Column(name="NECESSARY_DOCUMENT", length=100)
	private String necessaryDocument;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTICE_DATE", length=8)
	private LocalDate noticeDate;

	@Column(name="NOTICE_DESTINATION_CODE", length=1)
	private String noticeDestinationCode;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="RECIPIENT_NAME_APARTMENT", length=50)
	private String recipientNameApartment;

	@Column(name="RECIPIENT_NAME_USER", length=26)
	private String recipientNameUser;

	@Column(name="SENDER", length=25)
	private String sender;

	@Column(name="TEMP_KBN", length=1)
	private String tempKbn;

	@Column(name="TEXTADDRESS", length=31)
	private String textaddress;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBL235Entity() {
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

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getAppendixNo() {
		return appendixNo;
	}

	public void setAppendixNo(String appendixNo) {
		this.appendixNo = appendixNo;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getInvestImplNumberPeople() {
		return investImplNumberPeople;
	}

	public void setInvestImplNumberPeople(String investImplNumberPeople) {
		this.investImplNumberPeople = investImplNumberPeople;
	}

	public String getInvestImplPlanTime() {
		return investImplPlanTime;
	}

	public void setInvestImplPlanTime(String investImplPlanTime) {
		this.investImplPlanTime = investImplPlanTime;
	}

	public String getNecessaryDocument() {
		return necessaryDocument;
	}

	public void setNecessaryDocument(String necessaryDocument) {
		this.necessaryDocument = necessaryDocument;
	}

	public LocalDate getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(LocalDate noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getNoticeDestinationCode() {
		return noticeDestinationCode;
	}

	public void setNoticeDestinationCode(String noticeDestinationCode) {
		this.noticeDestinationCode = noticeDestinationCode;
	}

	public String getNotificationMethodCode() {
		return notificationMethodCode;
	}

	public void setNotificationMethodCode(String notificationMethodCode) {
		this.notificationMethodCode = notificationMethodCode;
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

	public String getTextaddress() {
		return textaddress;
	}

	public void setTextaddress(String textaddress) {
		this.textaddress = textaddress;
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