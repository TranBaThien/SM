/*
 * @(#) ChangePasswordVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class ChangePasswordVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String apartmentId;

    private String loginId;
    
    private String biginingPasswordChangeFlag;

    private String pwdPasswordNow;
    
    private String pwdPassword;

    private String pwdPasswordConfirm;
    
    private String txtMail;

    private String txtMailConfirm;
    
    private String isScreenMaa;
    
    private String loadMail;
    
    private String lnkReturn;
    
    private int userTypeCode;

    public ChangePasswordVo() {
        super();
    }

    public int getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(int userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPwdPasswordConfirm() {
        return pwdPasswordConfirm;
    }

    public void setPwdPasswordConfirm(String pwdPasswordConfirm) {
        this.pwdPasswordConfirm = pwdPasswordConfirm;
    }

    public String getPwdPasswordNow() {
        return pwdPasswordNow;
    }

    public void setPwdPasswordNow(String pwdPasswordNow) {
        this.pwdPasswordNow = pwdPasswordNow;
    }

    public String getTxtMailConfirm() {
        return txtMailConfirm;
    }

    public void setTxtMailConfirm(String txtMailConfirm) {
        this.txtMailConfirm = txtMailConfirm;
    }

    public String getPwdPassword() {
        return pwdPassword;
    }

    public void setPwdPassword(String pwdPassword) {
        this.pwdPassword = pwdPassword;
    }

    public String getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(String txtMail) {
        this.txtMail = txtMail;
    }

    public String getBiginingPasswordChangeFlag() {
        return biginingPasswordChangeFlag;
    }

    public void setBiginingPasswordChangeFlag(String biginingPasswordChangeFlag) {
        this.biginingPasswordChangeFlag = biginingPasswordChangeFlag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getIsScreenMaa() {
        return isScreenMaa;
    }

    public void setIsScreenMaa(String isScreenMaa) {
        this.isScreenMaa = isScreenMaa;
    }

    public String getLoadMail() {
        return loadMail;
    }

    public void setLoadMail(String loadMail) {
        this.loadMail = loadMail;
    }

    public String getLnkReturn() {
        return lnkReturn;
    }

    public void setLnkReturn(String lnkReturn) {
        this.lnkReturn = lnkReturn;
    }

}
