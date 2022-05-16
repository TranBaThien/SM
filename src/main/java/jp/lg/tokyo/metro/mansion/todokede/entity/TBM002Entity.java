package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbm002 database table.
 * 
 */
@Entity
@Table(name="tbm002")
public class TBM002Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TBM002EntityPK id;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="UPDATE_DATETIME")
	private Timestamp updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="WINDOW_BELONG", length=20)
	private String windowBelong;

	@Column(name="WINDOW_CITY_NAME", length=5)
	private String windowCityName;

	@Column(name="WINDOW_FAX_NO", length=13)
	private String windowFaxNo;

	@Column(name="WINDOW_MAIL_ADDRESS", length=120)
	private String windowMailAddress;

	@Column(name="WINDOW_TEL_NO", length=13)
	private String windowTelNo;

	public TBM002Entity() {
	}

	public TBM002EntityPK getId() {
		return this.id;
	}

	public void setId(TBM002EntityPK id) {
		this.id = id;
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

	public String getWindowBelong() {
		return this.windowBelong;
	}

	public void setWindowBelong(String windowBelong) {
		this.windowBelong = windowBelong;
	}

	public String getWindowCityName() {
		return this.windowCityName;
	}

	public void setWindowCityName(String windowCityName) {
		this.windowCityName = windowCityName;
	}

	public String getWindowFaxNo() {
		return this.windowFaxNo;
	}

	public void setWindowFaxNo(String windowFaxNo) {
		this.windowFaxNo = windowFaxNo;
	}

	public String getWindowMailAddress() {
		return this.windowMailAddress;
	}

	public void setWindowMailAddress(String windowMailAddress) {
		this.windowMailAddress = windowMailAddress;
	}

	public String getWindowTelNo() {
		return this.windowTelNo;
	}

	public void setWindowTelNo(String windowTelNo) {
		this.windowTelNo = windowTelNo;
	}
}