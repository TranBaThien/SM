/*
 * @(#)ReissueUserManagementForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;

/**
 * @author hbthinh
 *
 */
public class ReissueUserManagementForm extends BaseForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 現在のログインID
    private String lblLoginidNow;
    
    // ログインID
    private String txtLoginId;
    
    // パスワード
    @NotBlank   
    @HalfsizeAlphanumeric
    @Size(min = 8, max = 16)
    @Position(1)
    @FieldName("パスワード")
    private String pwdPassword;
    
    // パスワード（確認）
    @NotBlank
    @Position(2)
    @FieldName("パスワード（確認）")
    private String pwdPasswordConfirm;
    
    // 最終ログイン日時
    
    private String lblLastTimeLoginTime;
    
    
    // マンションID
    private String apartmentId;    
    
    
    // 更新日時
    private String updateDateTimeInitial;
    /**
     * @return the lblLoginidNow
     */
    public String getLblLoginidNow() {
        return lblLoginidNow;
    }

    /**
     * @param lblLoginidNow the lblLoginidNow to set
     */
    public void setLblLoginidNow(String lblLoginidNow) {
        this.lblLoginidNow = lblLoginidNow;
    }

    /**
     * @return the txtLoginId
     */
    public String getTxtLoginId() {
        return txtLoginId;
    }

    /**
     * @param txtLoginId the txtLoginId to set
     */
    public void setTxtLoginId(String txtLoginId) {
        this.txtLoginId = txtLoginId;
    }

    /**
     * @return the pwdPassword
     */
    public String getPwdPassword() {
        return pwdPassword;
    }

    /**
     * @param pwdPassword the pwdPassword to set
     */
    public void setPwdPassword(String pwdPassword) {
        this.pwdPassword = pwdPassword;
    }

    /**
     * @return the pwdPasswordConfirm
     */
    public String getPwdPasswordConfirm() {
        return pwdPasswordConfirm;
    }

    /**
     * @param pwdPasswordConfirm the pwdPasswordConfirm to set
     */
    public void setPwdPasswordConfirm(String pwdPasswordConfirm) {
        this.pwdPasswordConfirm = pwdPasswordConfirm;
    }

    
    
    /**
     * @return the lblLastTimeLoginTime
     */
    public String getLblLastTimeLoginTime() {
        return lblLastTimeLoginTime;
    }

    /**
     * @param lblLastTimeLoginTime the lblLastTimeLoginTime to set
     */
    public void setLblLastTimeLoginTime(String lblLastTimeLoginTime) {
        this.lblLastTimeLoginTime = lblLastTimeLoginTime;
    }

    /**
     * @return the apartmentId
     */
    public String getApartmentId() {
        return apartmentId;
    }

    /**
     * @param apartmentId the apartmentId to set
     */
    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the updateDateTimeInitial
     */
    public String getUpdateDateTimeInitial() {
        return updateDateTimeInitial;
    }

    /**
     * @param updateDateTimeInitial the updateDateTimeInitial to set
     */
    public void setUpdateDateTimeInitial(String updateDateTimeInitial) {
        this.updateDateTimeInitial = updateDateTimeInitial;
    }

}
