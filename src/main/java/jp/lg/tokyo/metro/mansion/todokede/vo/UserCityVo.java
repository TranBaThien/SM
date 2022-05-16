/*
 * @(#)UserCityVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

/**
 * @author ptluan
 *
 */
public class UserCityVo {

    private String userId;
    private String updateTime;
    private String rdoUserType;
    private List<CodeVo> rdoUserTypeList;
    private String lstCity;
    private List<CodeVo> lstCitylist;
    private String txtUserName;
    private String txtUserNamePhonetic;
    private String txtMail;
    private String txtMailConfirm;
    private String txtPhonenumber1;
    private String txtPhonenumber2;
    private String txtPhonenumber3;
    private String chkAvailability;
    private String txtContact;
    private String txtLoginid;
    private String pwdPassword;
    private String pwdPasswordConfirm;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLstCity() {
        return lstCity;
    }

    public void setLstCity(String lstCity) {
        this.lstCity = lstCity;
    }

    public List<CodeVo> getLstCitylist() {
        return lstCitylist;
    }

    public void setLstCitylist(List<CodeVo> lstCitylist) {
        this.lstCitylist = lstCitylist;
    }

    public List<CodeVo> getRdoUserTypeList() {
        return rdoUserTypeList;
    }

    public void setRdoUserTypeList(List<CodeVo> rdoUserTypeList) {
        this.rdoUserTypeList = rdoUserTypeList;
    }

    public String getRdoUserType() {
        return rdoUserType;
    }

    public void setRdoUserType(String rdoUserType) {
        this.rdoUserType = rdoUserType;
    }

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }

    public String getTxtUserNamePhonetic() {
        return txtUserNamePhonetic;
    }

    public void setTxtUserNamePhonetic(String txtUserNamePhonetic) {
        this.txtUserNamePhonetic = txtUserNamePhonetic;
    }

    public String getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(String txtMail) {
        this.txtMail = txtMail;
    }

    public String getTxtMailConfirm() {
        return txtMailConfirm;
    }

    public void setTxtMailConfirm(String txtMailConfirm) {
        this.txtMailConfirm = txtMailConfirm;
    }

    public String getTxtPhonenumber1() {
        return txtPhonenumber1;
    }

    public void setTxtPhonenumber1(String txtPhonenumber1) {
        this.txtPhonenumber1 = txtPhonenumber1;
    }

    public String getTxtPhonenumber2() {
        return txtPhonenumber2;
    }

    public void setTxtPhonenumber2(String txtPhonenumber2) {
        this.txtPhonenumber2 = txtPhonenumber2;
    }

    public String getTxtPhonenumber3() {
        return txtPhonenumber3;
    }

    public void setTxtPhonenumber3(String txtPhonenumber3) {
        this.txtPhonenumber3 = txtPhonenumber3;
    }

    public String getTxtContact() {
        return txtContact;
    }

    public void setTxtContact(String txtContact) {
        this.txtContact = txtContact;
    }

    public String getTxtLoginid() {
        return txtLoginid;
    }

    public void setTxtLoginid(String txtLoginid) {
        this.txtLoginid = txtLoginid;
    }

    public String getPwdPassword() {
        return pwdPassword;
    }

    public void setPwdPassword(String pwdPassword) {
        this.pwdPassword = pwdPassword;
    }

    public String getPwdPasswordConfirm() {
        return pwdPasswordConfirm;
    }

    public void setPwdPasswordConfirm(String pwdPasswordConfirm) {
        this.pwdPasswordConfirm = pwdPasswordConfirm;
    }

    public String getChkAvailability() {
        if(chkAvailability == null) {
            return CommonConstants.ONE;
        }else {
            return this.chkAvailability;
        }

    }

    
    /**
     * Set is 2 when it is unchecked
     * @param chkAvailability String
     */
    public void setChkAvailability(String chkAvailability) {
        if(chkAvailability == null) {
            this.chkAvailability = CommonConstants.ONE;
        }else {
            this.chkAvailability = chkAvailability;
        }
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
