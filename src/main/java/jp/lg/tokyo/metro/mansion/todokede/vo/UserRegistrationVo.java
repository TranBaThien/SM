/*
 * @(#) UserRegistrationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/12/20
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class UserRegistrationVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String lblRowNumber;
    private String lblApartmentName2;
    private String lblApartmentZipCode2;
    private String lblApartmentAddress2;
    private String cityCode;
    private String address;
    private String apartmentId;
    
    public String getLblRowNumber() {
        return lblRowNumber;
    }
    
    public void setLblRowNumber(String lblRowNumber) {
        this.lblRowNumber = lblRowNumber;
    }
    
    public String getLblApartmentName2() {
        return lblApartmentName2;
    }
    
    public void setLblApartmentName2(String lblApartmentName2) {
        this.lblApartmentName2 = lblApartmentName2;
    }
    
    public String getLblApartmentZipCode2() {
        return lblApartmentZipCode2;
    }
    
    public void setLblApartmentZipCode2(String lblApartmentZipCode2) {
        this.lblApartmentZipCode2 = lblApartmentZipCode2;
    }
    
    public String getLblApartmentAddress2() {
        return lblApartmentAddress2;
    }
    
    public void setLblApartmentAddress2(String lblApartmentAddress2) {
        this.lblApartmentAddress2 = lblApartmentAddress2;
    }
    
    public String getCityCode() {
        return cityCode;
    }
    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

}
