package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The persistent class for the tbm001 database table.
 * 
 */
@Entity
@Table(name="tbm001")
@NamedQuery(name="TBM001Entity.findAll", query="SELECT t FROM TBM001Entity t")
public class TBM001Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CITY_CODE", unique=true, nullable=false, length=6)
	private String cityCode;

	@Column(name="CHIEF_NAME", length=16)
	private String chiefName;

	@Column(name="CITY_NAME", length=5)
	private String cityName;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="DOCUMENT_NO", length=10)
	private String documentNo;

	@Column(name="UPDATE_DATETIME")
	private LocalDateTime updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="USER_AUTHORITY", length=1)
	private String userAuthority;
	
	@Column(name="CITY_HEAD_WORD", length=3)
	private String cityHeadWord;

	//bi-directional many-to-one association to TBL120Entity
	@OneToMany(mappedBy="tbm001")
	private List<TBL120Entity> tbl120s;
	
	public TBM001Entity() {
	}

	public String getCityHeadWord() {
		return cityHeadWord;
	}

	public void setCityHeadWord(String cityHeadWord) {
		this.cityHeadWord = cityHeadWord;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getChiefName() {
		return this.chiefName;
	}

	public void setChiefName(String chiefName) {
		this.chiefName = chiefName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public LocalDateTime getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUserAuthority() {
		return this.userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	public List<TBL120Entity> getTbl120s() {
		return this.tbl120s;
	}

	public void setTbl120s(List<TBL120Entity> tbl120s) {
		this.tbl120s = tbl120s;
	}

	public TBL120Entity addTbl120(TBL120Entity tbl120) {
		getTbl120s().add(tbl120);
		tbl120.setTbm001(this);

		return tbl120;
	}

	public TBL120Entity removeTbl120(TBL120Entity tbl120) {
		getTbl120s().remove(tbl120);
		tbl120.setTbm001(null);

		return tbl120;
	}

}