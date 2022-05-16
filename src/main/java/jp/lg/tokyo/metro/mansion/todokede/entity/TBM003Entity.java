package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbm003 database table.
 * 
 */
@Entity
@Table(name="tbm003")
public class TBM003Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ADVICE_TEMPLATE_NO", unique=true, nullable=false, length=6)
	private String adviceTemplateNo;

	@Column(name="ADVICE_TEMPLATE_DETAIL", length=300)
	private String adviceTemplateDetail;

	@Column(name="ADVICE_TEMPLATE_OVERVIEW", length=20)
	private String adviceTemplateOverview;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	public TBM003Entity() {
	}

	public String getAdviceTemplateNo() {
		return this.adviceTemplateNo;
	}

	public void setAdviceTemplateNo(String adviceTemplateNo) {
		this.adviceTemplateNo = adviceTemplateNo;
	}

	public String getAdviceTemplateDetail() {
		return this.adviceTemplateDetail;
	}

	public void setAdviceTemplateDetail(String adviceTemplateDetail) {
		this.adviceTemplateDetail = adviceTemplateDetail;
	}

	public String getAdviceTemplateOverview() {
		return this.adviceTemplateOverview;
	}

	public void setAdviceTemplateOverview(String adviceTemplateOverview) {
		this.adviceTemplateOverview = adviceTemplateOverview;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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