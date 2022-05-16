/*
 * @(#) ManagementAssociationCustomVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/26
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author tbthien
 *
 */
public class ManagementAssociationCustomVo {

    private String applicationNo;
    
    private LocalDateTime applicationTime;
    
    private String apartmentName;
    
    private String address;
    
    private String cityCode;
    
    private String cityName;
    
    private String judgeResult;
    
    private String apartmentNamePhonetic;
    
    private String zipCode;
    
    private String contactPropertyCode;
    
    private String contactPropertyElse;
    
    private String contactZipCode;
    
    private String contactAddress;
    
    private String contactTelNo;
    
    private String contactName;
    
    private String contactNamePhonetic;
    
    private String contactMailAddress;
    
    private String judgeRemarks;
    
    private String resultApartmentName;
    
    private String resultZipCode;
    
    private String resultAddress;
    
    private Date updateDatetime;
    
    private String apartmentId;

        
    public ManagementAssociationCustomVo() {
        
    }
    
    /**
     * 
     * @param applicationNo String
     * @param applicationTime String
     * @param apartmentName String
     * @param address String
     * @param cityCode String
     * @param cityName String
     * @param judgeResult String
     */
    public ManagementAssociationCustomVo(String applicationNo, LocalDateTime applicationTime, String apartmentName,
            String address, String cityCode, String cityName, String judgeResult) {        
        this.applicationNo = applicationNo;
        this.applicationTime = applicationTime;
        this.apartmentName = apartmentName;
        this.address = address;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.judgeResult = judgeResult;
    }
    
    /**
     * 
     * @param applicationNo String
     * @param address String
     * @param cityCode String
     * @param judgeResult String
     * @param apartmentNamePhonetic String
     * @param zipCode String
     * @param apartmentName String
     * @param contactPropertyCode String
     * @param contactPropertyElse String
     * @param contactZipCode String
     * @param contactAddress String
     * @param contactTelNo String
     * @param contactName String
     * @param contactNamePhonetic String
     * @param contactMailAddress String
     * @param resultApartmentName String
     * @param resultZipCode String
     * @param resultAddress String
     * @param updateDatetime String
     * @param apartmentId String
     * @param cityName String
     */
    public ManagementAssociationCustomVo(String applicationNo, String address, String cityCode, String judgeResult, String judgeRemarks,
            String apartmentNamePhonetic, String zipCode, String apartmentName, String contactPropertyCode,
            String contactPropertyElse, String contactZipCode, String contactAddress, String contactTelNo,
            String contactName, String contactNamePhonetic, String contactMailAddress, String resultApartmentName,
            String resultZipCode, String resultAddress, Date updateDatetime, String apartmentId, String cityName) {
        this.applicationNo = applicationNo;
        this.address = address;
        this.cityCode = cityCode;
        this.judgeResult = judgeResult;
        this.judgeRemarks = judgeRemarks;
        this.apartmentNamePhonetic = apartmentNamePhonetic;
        this.zipCode = zipCode;
        this.apartmentName = apartmentName;
        this.contactPropertyCode = contactPropertyCode;
        this.contactPropertyElse = contactPropertyElse;
        this.contactZipCode = contactZipCode;
        this.contactAddress = contactAddress;
        this.contactTelNo = contactTelNo;
        this.contactName = contactName;
        this.contactNamePhonetic = contactNamePhonetic;
        this.contactMailAddress = contactMailAddress;
        this.resultApartmentName = resultApartmentName;
        this.resultZipCode = resultZipCode;
        this.resultAddress = resultAddress;
        this.updateDatetime = updateDatetime;
        this.apartmentId = apartmentId;
        this.cityName = cityName;
    }
    
    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public LocalDateTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalDateTime applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
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

    public String getContactPropertyCode() {
        return contactPropertyCode;
    }

    public void setContactPropertyCode(String contactPropertyCode) {
        this.contactPropertyCode = contactPropertyCode;
    }

    public String getContactPropertyElse() {
        return contactPropertyElse;
    }

    public void setContactPropertyElse(String contactPropertyElse) {
        this.contactPropertyElse = contactPropertyElse;
    }

    public String getContactZipCode() {
        return contactZipCode;
    }

    public void setContactZipCode(String contactZipCode) {
        this.contactZipCode = contactZipCode;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactTelNo() {
        return contactTelNo;
    }

    public void setContactTelNo(String contactTelNo) {
        this.contactTelNo = contactTelNo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNamePhonetic() {
        return contactNamePhonetic;
    }

    public void setContactNamePhonetic(String contactNamePhonetic) {
        this.contactNamePhonetic = contactNamePhonetic;
    }

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
    }

    public String getJudgeRemarks() {
        return judgeRemarks;
    }

    public void setJudgeRemarks(String judgeRemarks) {
        this.judgeRemarks = judgeRemarks;
    }

    public String getResultApartmentName() {
        return resultApartmentName;
    }

    public void setResultApartmentName(String resultApartmentName) {
        this.resultApartmentName = resultApartmentName;
    }

    public String getResultZipCode() {
        return resultZipCode;
    }

    public void setResultZipCode(String resultZipCode) {
        this.resultZipCode = resultZipCode;
    }

    public String getResultAddress() {
        return resultAddress;
    }

    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }
    
}
