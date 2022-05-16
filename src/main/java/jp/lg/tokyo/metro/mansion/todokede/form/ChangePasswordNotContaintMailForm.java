/*
 * @(#) ChangePasswordNotContaintMailForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;

public class ChangePasswordNotContaintMailForm extends BaseForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String apartmentId;

    private String loginId;
    
    private String biginingPasswordChangeFlag;

    @NotBlank
    @HalfsizeAlphanumeric
    @Size(min = 8, max = 16)
    @Position(value = 1)
    @FieldName("現在のパスワード")
    private String pwdPasswordNow;
    
    @NotBlank
    @HalfsizeAlphanumeric
    @Size(min = 8, max = 16)
    @Position(value = 2)
    @FieldName("新しいパスワード")
    private String pwdPassword;

    @NotBlank
    @Position(value = 3)
    @FieldName("新しいパスワード（確認）")
    private String pwdPasswordConfirm;
    
    private String isScreenMaa;
    
    private String loadMail;
    
    private String lnkReturn;
    
    private int userTypeCode;
    
    public int getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(int userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public ChangePasswordNotContaintMailForm() {
        super();
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

    public String getPwdPassword() {
        return pwdPassword;
    }

    public void setPwdPassword(String pwdPassword) {
        this.pwdPassword = pwdPassword;
    }

    public String getBiginingPasswordChangeFlag() {
        return biginingPasswordChangeFlag;
    }

    public void setBiginingPasswordChangeFlag(String biginingPasswordChangeFlag) {
        this.biginingPasswordChangeFlag = biginingPasswordChangeFlag;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
