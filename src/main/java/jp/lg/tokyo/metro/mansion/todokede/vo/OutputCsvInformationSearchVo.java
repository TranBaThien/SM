/*
 * @(#) OutputCsvInformationSearchVo.java
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

public class OutputCsvInformationSearchVo implements Serializable {


    // TBL100 _ Supervise

    private String apartmentId;
    private String apartmentName;

    private String apartmentNamePhonetic;
    private String zipCode;
    private String city;
    private String address;
    private String notificationDate;
    private String notificationStatus;
    private String acceptStatus;
    private String builtDate;
    private String buildDay;
    private String buildYear;
    private String buildMonth;

    private String houseNumber;
    private String floorNumber;
    private String groupForm;
    private String groupFormElse;
    private String landRights;
    private String landRightElse;
    private String usefor;
    private String useForElse;



    public String getBuildDay() {
        return buildDay;
    }

    public void setBuildDay(String buildDay) {
        this.buildDay = buildDay;
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

    public String getGroupFormElse() {
        return groupFormElse;
    }

    public void setGroupFormElse(String groupFormElse) {
        this.groupFormElse = groupFormElse;
    }

    public String getLandRightElse() {
        return landRightElse;
    }

    public void setLandRightElse(String landRightElse) {
        this.landRightElse = landRightElse;
    }

    public String getUseForElse() {
        return useForElse;
    }

    public void setUseForElse(String useForElse) {
        this.useForElse = useForElse;
    }



    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(String builtDate) {
        this.builtDate = builtDate;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getGroupForm() {
        return groupForm;
    }

    public void setGroupForm(String groupForm) {
        this.groupForm = groupForm;
    }

    public String getLandRights() {
        return landRights;
    }

    public void setLandRights(String landRights) {
        this.landRights = landRights;
    }

    public String getUsefor() {
        return usefor;
    }

    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }

    public OutputCsvInformationSearchVo() {
    }
}


