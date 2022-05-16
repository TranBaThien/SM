package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TBL310 database table.
 * 
 */
@Entity
@Table(name="TBL310")
public class TBL310Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROGRESS_RECORD_ATTACH_NO", unique=true, nullable=false, length=10)
	private String progressRecordAttachNo;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Lob
	@Column(name="FILE")
	private byte[] file;

	@Column(name="FILE_NAME", length=50)
	private String fileName;

	@Column(name="PROGRESS_RECORD_NO", length=10)
	private String progressRecordNo;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROGRESS_RECORD_NO", referencedColumnName = "PROGRESS_RECORD_NO", insertable = false, updatable = false)
	private TBL300Entity tBL300Entity;

	public TBL310Entity() { 
	}
	public TBL310Entity(String progressRecordAttachNo, String fileName, String progressRecordNo) { 
		this.progressRecordAttachNo = progressRecordAttachNo;
		this.fileName =  fileName;
		this.progressRecordNo = progressRecordNo;
	}

	public String getProgressRecordAttachNo() {
		return this.progressRecordAttachNo;
	}

	public void setProgressRecordAttachNo(String progressRecordAttachNo) {
		this.progressRecordAttachNo = progressRecordAttachNo;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public byte[] getFile() {
		return this.file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProgressRecordNo() {
		return this.progressRecordNo;
	}

	public void setProgressRecordNo(String progressRecordNo) {
		this.progressRecordNo = progressRecordNo;
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

	public TBL300Entity gettBL300Entity() {
		return tBL300Entity;
	}

	public void settBL300Entity(TBL300Entity tBL300Entity) {
		this.tBL300Entity = tBL300Entity;
	}

}