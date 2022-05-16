/*
 * @(#)UserCityForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FullSizeCharacter;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HaftSizeNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Katakana;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Mail;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MinSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AlphanumbericType;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;

public class UserCityForm {
    private String userId;
    private String updateTime;

    @NotBlank
    @Position(1)
    @SelectCode(code = "CD027")
    @FieldName("種別")
    private String rdoUserType;
    private List<CodeVo> rdoUserTypeList;

    @Position(2)
    @FieldName("区市町村")
    private String lstCity;
    private List<CodeVo> lstCitylist;

    @NotBlank
    @Position(3)
    @MaxSize(val = 16)
    @FullSizeCharacter
    @FieldName("氏名")
    private String txtUserName;

    @NotBlank
    @Position(4)
    @MaxSize(val = 16)
    @Katakana
    @FieldName("氏名フリガナ")
    private String txtUserNamePhonetic;
    
    @NotBlank
    @Position(5)
    @MaxSize(val = 120)
    @HalfsizeAlphanumeric(type = AlphanumbericType.FOR_MAIL)
    @Mail
    @FieldName("メールアドレス")
    private String txtMail;

    @NotBlank
    @Position(6)
    @FieldName("メールアドレス")
    private String txtMailConfirm;

    @NotBlank
    @Position(7)
    @MaxSize(val = 5)
    @HaftSizeNumber
    @FieldName("電話番号")
    private String txtPhonenumber1;

    @NotBlank
    @Position(8)
    @MaxSize(val = 4)
    @HaftSizeNumber
    @FieldName("電話番号")
    private String txtPhonenumber2;

    @NotBlank
    @Position(9)
    @MaxSize(val = 4)
    @HaftSizeNumber
    @FieldName("電話番号")
    private String txtPhonenumber3;

    @Position(10)
    @FieldName("利用停止")
    private String chkAvailability;

    @NotBlank
    @Position(12)
    @MaxSize(val = 6)
    @MinSize(val = 6)
    @HaftSizeNumber
    @FieldName("ログインID")
    private String txtLoginid;

    @NotBlank
    @Position(13)
    @Size(min = 8, max = 16)
    @HalfsizeAlphanumeric
    @FieldName("パスワード")
    private String pwdPassword;

    @NotBlank
    @Position(14)
    @FieldName("パスワード（確認）")
    private String pwdPasswordConfirm;

    @Position(11)
    @MaxSize(val = 30)
    @SpecialCharacters
    @FieldName("備考")
    private String txtContact;

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
