/*
 * @(#) SummaryDataDetailsVo.java
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

public class SummaryDataDetailsVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String lblCount;
    
    private String lblRowNumber;

    private String lblApartmentName;
     
    private String zipCode;
    
    private String cityName;
    
    private String address;
    
    private String lblAddress;
    
    private String apartmentId;

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getLblApartmentName() {
        return lblApartmentName;
    }

    public void setLblApartmentName(String lblApartmentName) {
        this.lblApartmentName = lblApartmentName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLblAddress() {
    	this.lblAddress = this.zipCode + "　" + this.cityName + this.address;
        return this.lblAddress;
    }

	public String getLblCount() {
		return lblCount;
	}

	public void setLblCount(String lblCount) {
		this.lblCount = lblCount;
	}

	public String getLblRowNumber() {
		return lblRowNumber;
	}

	public void setLblRowNumber(String lblRowNumber) {
		this.lblRowNumber = lblRowNumber;
	}
    
    

}
