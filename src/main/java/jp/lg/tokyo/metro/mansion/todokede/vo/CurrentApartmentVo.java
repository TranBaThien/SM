/*
 * @(#) CurrentApartmentVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author hntvy
 *
 */
public class CurrentApartmentVo {
	private String apartmentID;
	private String typeCode;
	private String notificationNo;
	private String receptionNo;
	/**
	 * @return the apartmentID
	 */
	public String getApartmentID() {
		return apartmentID;
	}
	/**
	 * @param apartmentID the apartmentID to set
	 */
	public void setApartmentID(String apartmentID) {
		this.apartmentID = apartmentID;
	}
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @return the notificationNo
	 */
	public String getNotificationNo() {
		return notificationNo;
	}
	/**
	 * @param notificationNo the notificationNo to set
	 */
	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}
	/**
	 * @return the receptionNo
	 */
	public String getReceptionNo() {
		return receptionNo;
	}
	/**
	 * @param receptionNo the receptionNo to set
	 */
	public void setReceptionNo(String receptionNo) {
		this.receptionNo = receptionNo;
	}
	
}
