/*
 * @(#) MansionInfoUploadForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByPosition;

/**
 * @author nbvhoang
 *
 */
public class MansionInfoUploadForm implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @CsvBindByPosition(position = 0)
    private String propertyNumber;
    
    @CsvBindByPosition(position = 1)
    private String apartmentName;
    
    @CsvBindByPosition(position = 2)
    private String apartmentNamePhonetic;
    
    @CsvBindByPosition(position = 3)
    private String zipCode;
    
    @CsvBindByPosition(position = 4)
    private String cityName;
    
    @CsvBindByPosition(position = 5)
    private String address;
    
    @CsvBindByPosition(position = 6)
    private String notificationKbn;
    
    @CsvBindByPosition(position = 7)
    private String support;
    
    @CsvBindByPosition(position = 8)
    private String buildYear;
    
    @CsvBindByPosition(position = 9)
    private String buildMonth;
    
    @CsvBindByPosition(position = 10)
    private String buildDay;
    
    @CsvBindByPosition(position = 11)
    private String floorNumber;
    
    @CsvBindByPosition(position = 12)
    private String houseNumber;
    
    @CsvBindByPosition(position = 13)
    private String siteArea;
    
    @CsvBindByPosition(position = 14)
    private String totalFloorArea;
    
    @CsvBindByPosition(position = 15)
    private String buildingArea;
    
    @CsvBindByPosition(position = 16)
    private String buildingStructure;
    
    @CsvBindByPosition(position = 17)
    private String registrationNo;
    
    @CsvBindByPosition(position = 18)
    private String registrationAddress;
    
    @CsvBindByPosition(position = 19)
    private String registrationHouseNo;
    
    @CsvBindByPosition(position = 20)
    private String realEstateNo;
    
    @CsvBindByPosition(position = 21)
    private String preliminary1;
    
    @CsvBindByPosition(position = 22)
    private String preliminary2;
    
    @CsvBindByPosition(position = 23)
    private String preliminary3;
    
    @CsvBindByPosition(position = 24)
    private String preliminary4;
    
    @CsvBindByPosition(position = 25)
    private String preliminary5;

    public String getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(String propertyNumber) {
        this.propertyNumber = propertyNumber;
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

    public String getNotificationKbn() {
        return notificationKbn;
    }

    public void setNotificationKbn(String notificationKbn) {
        this.notificationKbn = notificationKbn;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getBuildMonth() {
        return buildMonth;
    }

    public void setBuildMonth(String buildMonth) {
        this.buildMonth = buildMonth;
    }

    public String getBuildDay() {
        return buildDay;
    }

    public void setBuildDay(String buildDay) {
        this.buildDay = buildDay;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getSiteArea() {
        return siteArea;
    }

    public void setSiteArea(String siteArea) {
        this.siteArea = siteArea;
    }

    public String getTotalFloorArea() {
        return totalFloorArea;
    }

    public void setTotalFloorArea(String totalFloorArea) {
        this.totalFloorArea = totalFloorArea;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getBuildingStructure() {
        return buildingStructure;
    }

    public void setBuildingStructure(String buildingStructure) {
        this.buildingStructure = buildingStructure;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getRegistrationHouseNo() {
        return registrationHouseNo;
    }

    public void setRegistrationHouseNo(String registrationHouseNo) {
        this.registrationHouseNo = registrationHouseNo;
    }

    public String getRealEstateNo() {
        return realEstateNo;
    }

    public void setRealEstateNo(String realEstateNo) {
        this.realEstateNo = realEstateNo;
    }

    public String getPreliminary1() {
        return preliminary1;
    }

    public void setPreliminary1(String preliminary1) {
        this.preliminary1 = preliminary1;
    }

    public String getPreliminary2() {
        return preliminary2;
    }

    public void setPreliminary2(String preliminary2) {
        this.preliminary2 = preliminary2;
    }

    public String getPreliminary3() {
        return preliminary3;
    }

    public void setPreliminary3(String preliminary3) {
        this.preliminary3 = preliminary3;
    }

    public String getPreliminary4() {
        return preliminary4;
    }

    public void setPreliminary4(String preliminary4) {
        this.preliminary4 = preliminary4;
    }

    public String getPreliminary5() {
        return preliminary5;
    }

    public void setPreliminary5(String preliminary5) {
        this.preliminary5 = preliminary5;
    }
    
}
