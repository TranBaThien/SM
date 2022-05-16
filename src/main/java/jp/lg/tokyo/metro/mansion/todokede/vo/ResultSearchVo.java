/*
 * @(#) ResultSearchVo.java
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


/**
 * The Class ResultSearchVo.
 */
public class ResultSearchVo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 件数. */
    private String lblCount;

    /**
     * Gets the serial version UID.
     *
     * @return the serial version UID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /** No. */
    private String lblRowNumber;

    /** マンションID. */
    private String lblApartmentId;

    /** マンション名. */

    private String lnkApartmentName;

    /** 住所. */
    private String lblAddress;

    /** 届出日. */
    private String lblNotificationDate;

    /** 届出. */
    private String lblNotificationStatus;

    /** 受理. */
    private String lblAcceptStatus;

    /** The city name. */
    private String cityName;

    /** The city code. */
    private String cityCode;

    private String newestNotificationNo;

    public String getNewestNotificationNo() {
        return newestNotificationNo;
    }

    public void setNewestNotificationNo(String newestNotificationNo) {
        this.newestNotificationNo = newestNotificationNo;
    }

    /** List Apartment. */
    private String  lstIdApartment;
    
    /** The user login. */
    private String userLogin;

    /**
     * Gets the city name.
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the city name.
     *
     * @param cityName the new city name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Gets the city code.
     *
     * @return the city code
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * Sets the city code.
     *
     * @param cityCode the new city code
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    /**
     * Gets the lst id apartment.
     *
     * @return the lst id apartment
     */
    public String getLstIdApartment() {
        return lstIdApartment;
    }

    /**
     * Sets the lst id apartment.
     *
     * @param lstIdApartment the new lst id apartment
     */
    public void setLstIdApartment(String lstIdApartment) {
        this.lstIdApartment = lstIdApartment;
    }

    /**
     * Gets the lbl count.
     *
     * @return the lbl count
     */
    public String getLblCount() {
        return lblCount;
    }

    /**
     * Sets the lbl count.
     *
     * @param lblCount the new lbl count
     */
    public void setLblCount(String lblCount) {
        this.lblCount = lblCount;
    }

    /**
     * Gets the lbl row number.
     *
     * @return the lbl row number
     */
    public String getLblRowNumber() {
        return lblRowNumber;
    }

    /**
     * Sets the lbl row number.
     *
     * @param lblRowNumber the new lbl row number
     */
    public void setLblRowNumber(String lblRowNumber) {
        this.lblRowNumber = lblRowNumber;
    }

    /**
     * Gets the lbl apartment id.
     *
     * @return the lbl apartment id
     */
    public String getLblApartmentId() {
        return lblApartmentId;
    }

    /**
     * Sets the lbl apartment id.
     *
     * @param lblApartmentId the new lbl apartment id
     */
    public void setLblApartmentId(String lblApartmentId) {
        this.lblApartmentId = lblApartmentId;
    }

    /**
     * Gets the lnk apartment name.
     *
     * @return the lnk apartment name
     */
    public String getLnkApartmentName() {
        return lnkApartmentName;
    }

    /**
     * Sets the lnk apartment name.
     *
     * @param lnkApartmentName the new lnk apartment name
     */
    public void setLnkApartmentName(String lnkApartmentName) {
        this.lnkApartmentName = lnkApartmentName;
    }

    /**
     * Gets the lbl address.
     *
     * @return the lbl address
     */
    public String getLblAddress() {
        return lblAddress;
    }

    /**
     * Sets the lbl address.
     *
     * @param lblAddress the new lbl address
     */
    public void setLblAddress(String lblAddress) {
        this.lblAddress = lblAddress;
    }

    /**
     * Gets the lbl notification date.
     *
     * @return the lbl notification date
     */
    public String getLblNotificationDate() {
        return lblNotificationDate;
    }

    /**
     * Sets the lbl notification date.
     *
     * @param lblNotificationDate the new lbl notification date
     */
    public void setLblNotificationDate(String lblNotificationDate) {
        this.lblNotificationDate = lblNotificationDate;
    }

    /**
     * Gets the lbl notification status.
     *
     * @return the lbl notification status
     */
    public String getLblNotificationStatus() {
        return lblNotificationStatus;
    }

    /**
     * Sets the lbl notification status.
     *
     * @param lblNotificationStatus the new lbl notification status
     */
    public void setLblNotificationStatus(String lblNotificationStatus) {
        this.lblNotificationStatus = lblNotificationStatus;
    }

    /**
     * Gets the lbl accept status.
     *
     * @return the lbl accept status
     */
    public String getLblAcceptStatus() {
        return lblAcceptStatus;
    }

    /**
     * Sets the lbl accept status.
     *
     * @param lblAcceptStatus the new lbl accept status
     */
    public void setLblAcceptStatus(String lblAcceptStatus) {
        this.lblAcceptStatus = lblAcceptStatus;
    }

    /**
     * Instantiates a new result search vo.
     */
    public ResultSearchVo() {
    }
    

    /**
     * Gets the user login.
     *
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the user login.
     *
     * @param userLogin the new user login
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}


