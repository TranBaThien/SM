/*
 * @(#) BaseMansionInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/24
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author PVHung3
 *
 */
public class BaseMansionInfoVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String apartmentId;

	private String address;

	private String lblApartmentAddress;

	private String apartmentName;

	private String apartmentNamePhonetic;

	private String mailAddress;
	
	private String newestNotificationNo;

	private Integer houseNumber;

	private Integer floorNumber;

	private String cityCode;

	private String cityName;

	private String zipCode;

	private LocalDate nextNotificationDate;

	private String buildDay;

	private String buildMonth;

	private String buildYear;

	private String updateDatetime;

	private String updateUserId;
	
	private String loginScreenId;
	
	public String getLoginScreenId() {
		return loginScreenId;
	}

	public void setLoginScreenId(String loginScreenId) {
		this.loginScreenId = loginScreenId;
	}

	public BaseMansionInfoVo() {

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

	public String getLblApartmentAddress() {
		return lblApartmentAddress;
	}

	public void setLblApartmentAddress(String lblApartmentAddress) {
		this.lblApartmentAddress = lblApartmentAddress;
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

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
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

	public LocalDate getNextNotificationDate() {
		return nextNotificationDate;
	}

	public void setNextNotificationDate(LocalDate nextNotificationDate) {
		this.nextNotificationDate = nextNotificationDate;
	}

	public String getBuildDay() {
		return buildDay;
	}

	public void setBuildDay(String buildDay) {
		this.buildDay = buildDay;
	}

	public String getBuildMonth() {
		return buildMonth;
	}

	public void setBuildMonth(String buildMonth) {
		this.buildMonth = buildMonth;
	}

	public String getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
