/*
 * @(#) ApproveNewUserRegistrationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/12/19
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

public class ApproveNewUserRegistrationVo extends BaseMansionInfoVo {
    
    private static final long serialVersionUID = 774690167728643274L;

    private String lblapartmentname1;
    
    private String lblapartmentnamephonetic;
    
    private String lblapartmentaddress1;
    
    private String lblContactProperty;
    
    private String lblContactZipCode;
    
    private String lblContactAddress;
    
    private String lblContractTel;
    
    private String lblContractName;
    
    private String lblContractNamePhonetic;
    
    private String lblContractMail;
    
    private String rdoApartmentSelect;
    
    private String lblRowNumber;
    
    private String lblApartmentName2;
    
    private String lblApartmentZipCode2;
    
    private String lblApartmentAddress2;
    
    private String rdoInspectionResult;
    
    private String txaNote;
    
    private String applicationNo;
    
    private String applicationTime;
    
    private String apartmentName;
    
    private String address;
    
    private String cityCode;
    
    private String cityName;
    
    private String judgeResult;    
    
    private String lblNoInfoMessage;
    
    private String contactName;
    
    private String loginUrl;
    
    private String loginId;
    
    private String judgeRemarks;
    
    private String contactMailAddress;
    
    private boolean display;
    
    private String chkConfirm;
    
    private boolean checkError;
    
    private List<UserRegistrationVo> listUserRegistrationVo;

    public List<UserRegistrationVo> getListUserRegistrationVo() {
        return listUserRegistrationVo;
    }

    public void setListUserRegistrationVo(List<UserRegistrationVo> listUserRegistrationVo) {
        this.listUserRegistrationVo = listUserRegistrationVo;
    }

    public ApproveNewUserRegistrationVo() {
        super();
    }

    public String getLblapartmentname1() {
        return lblapartmentname1;
    }

    public void setLblapartmentname1(String lblapartmentname1) {
        this.lblapartmentname1 = lblapartmentname1;
    }

    public String getLblapartmentnamephonetic() {
        return lblapartmentnamephonetic;
    }

    public void setLblapartmentnamephonetic(String lblapartmentnamephonetic) {
        this.lblapartmentnamephonetic = lblapartmentnamephonetic;
    }

    public String getLblapartmentaddress1() {
        return lblapartmentaddress1;
    }

    public void setLblapartmentaddress1(String lblapartmentaddress1) {
        this.lblapartmentaddress1 = lblapartmentaddress1;
    }

    public String getLblContactProperty() {
        return lblContactProperty;
    }

    public void setLblContactProperty(String lblContactProperty) {
        this.lblContactProperty = lblContactProperty;
    }

    public String getLblContactZipCode() {
        return lblContactZipCode;
    }

    public void setLblContactZipCode(String lblContactZipCode) {
        this.lblContactZipCode = lblContactZipCode;
    }

    public String getLblContactAddress() {
        return lblContactAddress;
    }

    public void setLblContactAddress(String lblContactAddress) {
        this.lblContactAddress = lblContactAddress;
    }

    public String getLblContractTel() {
        return lblContractTel;
    }

    public void setLblContractTel(String lblContractTel) {
        this.lblContractTel = lblContractTel;
    }

    public String getLblContractName() {
        return lblContractName;
    }

    public void setLblContractName(String lblContractName) {
        this.lblContractName = lblContractName;
    }

    public String getLblContractNamePhonetic() {
        return lblContractNamePhonetic;
    }

    public void setLblContractNamePhonetic(String lblContractNamePhonetic) {
        this.lblContractNamePhonetic = lblContractNamePhonetic;
    }

    public String getLblContractMail() {
        return lblContractMail;
    }

    public void setLblContractMail(String lblContractMail) {
        this.lblContractMail = lblContractMail;
    }

    public String getRdoApartmentSelect() {
        return rdoApartmentSelect;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
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

    public void setRdoApartmentSelect(String rdoApartmentSelect) {
        this.rdoApartmentSelect = rdoApartmentSelect;
    }

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

    public String getRdoInspectionResult() {
        return rdoInspectionResult;
    }

    public void setRdoInspectionResult(String rdoInspectionResult) {
        this.rdoInspectionResult = rdoInspectionResult;
    }

    public String getTxaNote() {
        return txaNote;
    }

    public void setTxaNote(String txaNote) {
        this.txaNote = txaNote;
    }

    public String getLblNoInfoMessage() {
        return lblNoInfoMessage;
    }

    public void setLblNoInfoMessage(String lblNoInfoMessage) {
        this.lblNoInfoMessage = lblNoInfoMessage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getJudgeRemarks() {
        return judgeRemarks;
    }

    public void setJudgeRemarks(String judgeRemarks) {
        this.judgeRemarks = judgeRemarks;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
    }

    public String getChkConfirm() {
        return chkConfirm;
    }

    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isCheckError() {
        return checkError;
    }

    public void setCheckError(boolean checkError) {
        this.checkError = checkError;
    }

}
