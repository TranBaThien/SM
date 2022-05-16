/*
 * @(#) ApartmentInformationDetailsVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class ApartmentInformationDetailsVo implements Serializable {

	private  ApartmentStatusInfoVo apartmentStatusInfoVo;

	private  NotificationRegistrationVo notificationRegistrationVo;

	private String correctionDetails;

	private  ApartmentUserInfoVo apartmentUserInfoVo;

	private ApartmentBtnStatusVo apartmentBtnStatusVo;

	public ApartmentInformationDetailsVo() {}

	public ApartmentStatusInfoVo getApartmentStatusInfoVo() {
		return apartmentStatusInfoVo;
	}

	public void setApartmentStatusInfoVo(ApartmentStatusInfoVo apartmentStatusInfoVo) {
		this.apartmentStatusInfoVo = apartmentStatusInfoVo;
	}

	public NotificationRegistrationVo getNotificationRegistrationVo() {
		return notificationRegistrationVo;
	}

	public void setNotificationRegistrationVo(NotificationRegistrationVo notificationRegistrationVo) {
		this.notificationRegistrationVo = notificationRegistrationVo;
	}

	public String getCorrectionDetails() {
		return correctionDetails;
	}

	public void setCorrectionDetails(String correctionDetails) {
		this.correctionDetails = correctionDetails;
	}

	public ApartmentUserInfoVo getApartmentUserInfoVo() {
		return apartmentUserInfoVo;
	}

	public void setApartmentUserInfoVo(ApartmentUserInfoVo apartmentUserInfoVo) {
		this.apartmentUserInfoVo = apartmentUserInfoVo;
	}

	public ApartmentBtnStatusVo getApartmentBtnStatusVo() {
		return apartmentBtnStatusVo;
	}

	public void setApartmentBtnStatusVo(ApartmentBtnStatusVo apartmentBtnStatusVo) {
		this.apartmentBtnStatusVo = apartmentBtnStatusVo;
	}
}
