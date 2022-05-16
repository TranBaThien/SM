/*
 * @(#) GKA0110Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author LVTrinh1
 *
 */
public class GKA0110Vo {

	private String apartmentId;

	private String correspondDate;

	private String correspondMonthYear;

	private String year;

	private String cityCode;

	private String typeCode;

	public GKA0110Vo() {
		super();
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getCorrespondDate() {
		return correspondDate;
	}

	public void setCorrespondDate(String correspondDate) {
		this.correspondDate = correspondDate;
	}

	public String getCorrespondMonthYear() {
		return correspondMonthYear;
	}

	public void setCorrespondMonthYear(String correspondMonthYear) {
		this.correspondMonthYear = correspondMonthYear;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
