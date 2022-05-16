package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the TBL300 database table.
 * 
 */
@Entity
@Table(name="TBL300")
public class TBL300Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROGRESS_RECORD_NO", unique=true, nullable=false, length=10)
	private String progressRecordNo;
	
	@Column(name="APARTMENT_ID", length=10)
	private String apartmentId;

	@Column(name="CORRESPOND_DATE", length=12)
	private String correspondDate;

	@Column(name="CORRESPOND_TYPE_CODE", length=2)
	private String correspondTypeCode;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="NOTICE_TYPE_CODE", length=1)
	private String noticeTypeCode;

	@Column(name="NOTIFICATION_METHOD_CODE", length=1)
	private String notificationMethodCode;

	@Column(name="PROGRESS_RECORD_DETAIL", length=300)
	private String progressRecordDetail;

	@Column(name="PROGRESS_RECORD_OVERVIEW", length=30)
	private String progressRecordOverview;

	@Column(name="RELATED_NUMBER", length=10)
	private String relatedNumber;

	@Column(name="SUPPORT_CODE", length=1)
	private String supportCode;

	@Column(name="TYPE_CODE", length=2)
	private String typeCode;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	//bi-directional many-to-one association to TBL100Entity
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APARTMENT_ID", referencedColumnName = "APARTMENT_ID", insertable=false, updatable=false)
	private TBL100Entity tBL100Entity;
	
	@OneToMany(mappedBy="tBL300Entity", fetch = FetchType.LAZY)
	private List<TBL310Entity> tbl310Entity;

	public TBL300Entity() {
	}

	public String getProgressRecordNo() {
		return this.progressRecordNo;
	}

	public void setProgressRecordNo(String progressRecordNo) {
		this.progressRecordNo = progressRecordNo;
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getCorrespondDate() {
		return this.correspondDate;
	}

	public void setCorrespondDate(String correspondDate) {
		this.correspondDate = correspondDate;
	}

	public String getCorrespondTypeCode() {
		return this.correspondTypeCode;
	}

	public void setCorrespondTypeCode(String correspondTypeCode) {
		this.correspondTypeCode = correspondTypeCode;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getNoticeTypeCode() {
		return this.noticeTypeCode;
	}

	public void setNoticeTypeCode(String noticeTypeCode) {
		this.noticeTypeCode = noticeTypeCode;
	}

	public String getNotificationMethodCode() {
		return this.notificationMethodCode;
	}

	public void setNotificationMethodCode(String notificationMethodCode) {
		this.notificationMethodCode = notificationMethodCode;
	}

	public String getProgressRecordDetail() {
		return this.progressRecordDetail;
	}

	public void setProgressRecordDetail(String progressRecordDetail) {
		this.progressRecordDetail = progressRecordDetail;
	}

	public String getProgressRecordOverview() {
		return this.progressRecordOverview;
	}

	public void setProgressRecordOverview(String progressRecordOverview) {
		this.progressRecordOverview = progressRecordOverview;
	}

	public String getRelatedNumber() {
		return this.relatedNumber;
	}

	public void setRelatedNumber(String relatedNumber) {
		this.relatedNumber = relatedNumber;
	}

	public String getSupportCode() {
		return this.supportCode;
	}

	public void setSupportCode(String supportCode) {
		this.supportCode = supportCode;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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

	public TBL100Entity gettBL100Entity() {
		return tBL100Entity;
	}

	public void settBL100Entity(TBL100Entity tBL100Entity) {
		this.tBL100Entity = tBL100Entity;
	}

	public List<TBL310Entity> getTbl310Entity() {
		return tbl310Entity;
	}

	public void setTbl310Entity(List<TBL310Entity> tbl310Entity) {
		this.tbl310Entity = tbl310Entity;
	}
}