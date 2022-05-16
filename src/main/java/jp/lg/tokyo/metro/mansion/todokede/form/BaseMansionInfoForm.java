/*
 * @(#) BaseMansionInfoForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author DLLy
 *
 */
public class BaseMansionInfoForm extends BaseForm {

	private String apartmentId;

	private String address;

	@NotBlank
	@MaxSize(val = 50)
	@SpecialCharacters
	@FieldName("マンション名")
	@Position(1)
	private String apartmentName;

	@NotBlank
	@Katakana
	@MaxSize(val = 100)
	@FieldName("マンション名フリガナ")
	@Position(2)
	private String apartmentNamePhonetic;

	private String newestNotificationNo;

	@HaftSizeNumber
	@MaxSize(val = 2)
	@FieldName("階数")
	@Position(10)
	private int floorNumber;

	private String cityCode;

	private String cityName;

	private String zipCode;

	public BaseMansionInfoForm() {
	}

	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getApartmentNamePhonetic() {
		return apartmentNamePhonetic;
	}

	public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
		this.apartmentNamePhonetic = apartmentNamePhonetic;
	}

	public String getNewestNotificationNo() {
		return newestNotificationNo;
	}

	public void setNewestNotificationNo(String newestNotificationNo) {
		this.newestNotificationNo = newestNotificationNo;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
