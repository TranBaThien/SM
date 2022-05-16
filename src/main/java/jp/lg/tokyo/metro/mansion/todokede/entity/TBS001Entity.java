package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TBS001 database table.
 * 
 */
@Entity
@Table(name="TBS001")
//@NamedQuery(name="TBS001Entity.findAll", query="SELECT t FROM TBS001Entity t")
public class TBS001Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SEQUENCE_NO", unique=true, nullable=false, length=2)
	private String sequenceNo;

	@Column(name="COLUMN_NAME", length=64)
	private String columnName;

	@Column(name="CURRENT_NO")
	private int currentNo;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="INCREMENT_NO")
	private int incrementNo;

	@Column(name="PREFIX", length=10)
	private String prefix;

	@Column(name="START_NO")
	private int startNo;

	@Column(name="TBL_ID", length=6)
	private String tblId;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBS001Entity() {
	}

	public String getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getCurrentNo() {
		return this.currentNo;
	}

	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getIncrementNo() {
		return this.incrementNo;
	}

	public void setIncrementNo(int incrementNo) {
		this.incrementNo = incrementNo;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getStartNo() {
		return this.startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public String getTblId() {
		return this.tblId;
	}

	public void setTblId(String tblId) {
		this.tblId = tblId;
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

}