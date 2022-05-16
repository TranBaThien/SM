/*
 * @(#)ReissueUserManagementVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 3, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * @author hbthinh
 *
 */
public class ReissueUserManagementVo extends BaseMansionInfoVo {
    
    private static final long serialVersionUID = 1L;
    
    //現在のログインID
    private String lblLoginidNow;
    
    // ログインID
    private String txtLoginId;
    
    // パスワード

    private String pwdPassword;
    
    // パスワード（確認）

    private String pwdPasswordConfirm;    
    
    
    // 最終ログイン日時
    private String lblLastTimeLoginTime;
    
    // 最終ログイン日時
    private LocalDateTime lastTimeLoginTime;
    
    // マンションID
    private String apartmentId;
    
    // パスワード有効期限
    private LocalDateTime passwordPeriod;
    
    // ログイン失敗回数
    private int loginErrorCount;
    
    // ログイン失敗回数
    private String acountLockFlag;
    
    // アカウントロック日時
    private LocalDateTime accountLockTime;
    
    
    
    // 初期化パスワード変更フラグ
    private String bindingPasswordChangeFlag;
    
    // ログイン中フラグ
    private String loginStatusFlag;
    
    // 削除フラグ
    private String deleteFlag;
    
    // 更新ユーザID 
    private String updateUserId;
    
    // 更新日時
    private Timestamp updateDateTime;
    
   // 更新日時
    private String updateDateTimeInitial;
    
    private String apartmentName;
    
    private String apartmentNamePhonetic;
    
    private String address;
    
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
     * @return the passwordPeriod
     */
    public LocalDateTime getPasswordPeriod() {
        return passwordPeriod;
    }
    
    /**
     * @return the loginErrorCount
     */
    public int getLoginErrorCount() {
        return loginErrorCount;
    }

    /**
     * @param loginErrorCount the loginErrorCount to set
     */
    public void setLoginErrorCount(int loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    /**
     * @param passwordPeriod the passwordPeriod to set
     */
    public void setPasswordPeriod(LocalDateTime passwordPeriod) {
        this.passwordPeriod = passwordPeriod;
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
     * @return the acountLockFlag
     */
    public String getAcountLockFlag() {
        return acountLockFlag;
    }

    /**
     * @param acountLockFlag the acountLockFlag to set
     */
    public void setAcountLockFlag(String acountLockFlag) {
        this.acountLockFlag = acountLockFlag;
    }

    /**
     * @return the accountLockTime
     */
    public LocalDateTime getAccountLockTime() {
        return accountLockTime;
    }

    /**
     * @param accountLockTime the accountLockTime to set
     */
    public void setAccountLockTime(LocalDateTime accountLockTime) {
        this.accountLockTime = accountLockTime;
    }


    /**
     * @return the bindingPasswordChangeFlag
     */
    public String getBindingPasswordChangeFlag() {
        return bindingPasswordChangeFlag;
    }

    /**
     * @param bindingPasswordChangeFlag the bindingPasswordChangeFlag to set
     */
    public void setBindingPasswordChangeFlag(String bindingPasswordChangeFlag) {
        this.bindingPasswordChangeFlag = bindingPasswordChangeFlag;
    }
        
    /**
     * @return the loginStatusFlag
     */
    public String getLoginStatusFlag() {
        return loginStatusFlag;
    }

    /**
     * @param loginStatusFlag the loginStatusFlag to set
     */
    public void setLoginStatusFlag(String loginStatusFlag) {
        this.loginStatusFlag = loginStatusFlag;
    }

    /**
     * @return the deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @return the updateUserId
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * @param updateUserId the updateUserId to set
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * @return the updateDateTime
     */
    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    /**
     * @param updateDateTime the updateDateTime to set
     */
    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
    
    

    /**
     * @return the apartmentName
     */
    public String getApartmentName() {
        return apartmentName;
    }

    /**
     * @param apartmentName the apartmentName to set
     */
    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    /**
     * @return the apartmentNamePhonetic
     */
    public String getApartmentNamePhonetic() {
        return apartmentNamePhonetic;
    }

    /**
     * @param apartmentNamePhonetic the apartmentNamePhonetic to set
     */
    public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
        this.apartmentNamePhonetic = apartmentNamePhonetic;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the lastTimeLoginTime
     */
    public LocalDateTime getLastTimeLoginTime() {
        return lastTimeLoginTime;
    }

    /**
     * @param lastTimeLoginTime the lastTimeLoginTime to set
     */
    public void setLastTimeLoginTime(LocalDateTime lastTimeLoginTime) {
        this.lastTimeLoginTime = lastTimeLoginTime;
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
