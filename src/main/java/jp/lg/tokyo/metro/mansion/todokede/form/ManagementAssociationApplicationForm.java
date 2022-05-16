/*
 * @(#) ManagementAssociationApplicationForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/29
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;


import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FullSizeCharacter;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HaftSizeNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Katakana;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Mail;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MinSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.RequiredCityCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AlphanumbericType;
import jp.lg.tokyo.metro.mansion.todokede.vo.CityVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;

/**
 * @author lthai
 *
 */
public class ManagementAssociationApplicationForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String chkTermsConditions;
    /**
     * マンション名
     */
    @NotBlank
    @Position(value = 1)
    @MaxSize(val = 50)
    @SpecialCharacters
    @FieldName("基本情報のマンション名")
    private String txtApartmentName;

    /**
     * マンション名フリガナ
     */
    @NotBlank
    @Position(value = 2)
    @Katakana
    @MaxSize(val = 100)
    @FieldName("基本情報のマンション名フリガナ")
    private String txtApartmentNamePhonetic;

    /**
     * 郵便番号1
     */
    @NotBlank
    @Position(value = 3)
    @HaftSizeNumber
    @MinSize(val = 3)
    @MaxSize(val = 3)
    @FieldName("基本情報の郵便番号1")
    private String txtApartmentZipCode1;

    /**
     * 郵便番号2
     */
    @NotBlank
    @Position(value = 4)
    @HaftSizeNumber
    @MinSize(val = 4)
    @MaxSize(val = 4)
    @FieldName("基本情報の郵便番号2")
    private String txtApartmentZipCode2;

    /**
     * 住所1
     */
    @RequiredCityCode
    @Position(value = 5)
    @FieldName("基本情報の住所1")
    private String lstApartmentAddress1;
    
    /**
     * List drop down for city code
     */
    private List<CityVo> lstCityCodeApartmentAddress1;

    /**
     * 住所2
     */
    @NotBlank
    @Position(value = 6)
    @FullSizeCharacter
    @MaxSize(val = 100)
    @FieldName("基本情報の住所2")
    private String txtApartmentAddress2;

    /**
     * 属性
     */
    private List<CodeVo> lstContactProperty;
    
    /**
     * 属性
     */
    @Position(value = 7)
    @SelectCode(code = "CD013")
    @FieldName("連絡先の属性")
    private String rdoContactProperty;

    /**
     * 属性_その他
     */
    @Position(value = 8)
    @MaxSize(val = 30)
    @SpecialCharacters
    @FieldName("連絡先の属性－その他")
    private String txtContactPropertyElse;

    /**
     * 郵便番号1
     */
    @NotBlank
    @Position(value = 9)
    @HaftSizeNumber
    @MinSize(val = 3)
    @MaxSize(val = 3)
    @FieldName("連絡先の郵便番号1")
    private String txtContactZipCode1;

    /**
     * 郵便番号2
     */
    @NotBlank
    @Position(value = 10)
    @HaftSizeNumber
    @MinSize(val = 4)
    @MaxSize(val = 4)
    @FieldName("連絡先の郵便番号2")
    private String txtContactZipCode2;

    /**
     * 住所
     */
    @NotBlank
    @Position(value = 11)
    @FullSizeCharacter
    @MaxSize(val = 100)
    @FieldName("連絡先の住所")
    private String txtContactAddress;

    /**
     * 電話番号1
     */
    @NotBlank
    @Position(value = 12)
    @HaftSizeNumber
    @MaxSize(val = 5)
    @FieldName("連絡先の電話番号1")
    private String txtContactTelno1;

    /**
     * 電話番号2
     */
    @NotBlank
    @Position(value = 13)
    @HaftSizeNumber
    @MaxSize(val = 4)
    @FieldName("連絡先の電話番号2")
    private String txtContactTelno2;

    /**
     * 電話番号3
     */
    @NotBlank
    @Position(value = 14)
    @HaftSizeNumber
    @MaxSize(val = 4)
    @FieldName("連絡先の電話番号3")
    private String txtContactTelno3;

    /**
     * 氏名
     */
    @NotBlank
    @Position(value = 15)
    @MaxSize(val = 34)
    @SpecialCharacters
    @FieldName("連絡先の氏名")
    private String txtContactName;

    /**
     * 氏名フリガナ
     */
    @NotBlank
    @Position(value = 16)
    @Katakana
    @MaxSize(val = 34)
    @FieldName("連絡先の氏名フリガナ")
    private String txtContactNamePhonetic;

    /**
     * メールアドレス
     */
    @NotBlank
    @Position(value = 17)
    @HalfsizeAlphanumeric(type = AlphanumbericType.FOR_MAIL)
    @Mail
    @MaxSize(val = 120)
    @FieldName("連絡先のメールアドレス")
    private String txtContactMail;

    /**
     * メールアドレス（確認）
     */
    @NotBlank
    @Position(value = 18)
    @FieldName("連絡先のメールアドレス（確認）")
    private String txtContactMailRe;

    /**
     * 仮パスワード
     */
    @NotBlank
    @Position(value = 19)
    @HalfsizeAlphanumeric
    @Size(min = 8, max = 16)
    @FieldName("ログイン情報－仮パスワード")
    private String txtTempPassword;

    /**
     * 仮パスワード（確認）
     */
    @NotBlank
    @Position(value = 20)
    @FieldName("ログイン情報－仮パスワード（確認）")
    private String txtTempPasswordRe;

    /**
     * constructor
     */
    public ManagementAssociationApplicationForm() {
        super();
    }

    /**
     * @return the txtApartmentName
     */
    public String getTxtApartmentName() {
        return txtApartmentName;
    }

    /**
     * @param txtApartmentName the txtApartmentName to set
     */
    public void setTxtApartmentName(String txtApartmentName) {
        this.txtApartmentName = txtApartmentName;
    }

    /**
     * @return the txtApartmentNamePhonetic
     */
    public String getTxtApartmentNamePhonetic() {
        return txtApartmentNamePhonetic;
    }

    /**
     * @param txtApartmentNamePhonetic the txtApartmentNamePhonetic to set
     */
    public void setTxtApartmentNamePhonetic(String txtApartmentNamePhonetic) {
        this.txtApartmentNamePhonetic = txtApartmentNamePhonetic;
    }

    /**
     * @return the txtApartmentZipCode1
     */
    public String getTxtApartmentZipCode1() {
        return txtApartmentZipCode1;
    }

    /**
     * @param txtApartmentZipCode1 the txtApartmentZipCode1 to set
     */
    public void setTxtApartmentZipCode1(String txtApartmentZipCode1) {
        this.txtApartmentZipCode1 = txtApartmentZipCode1;
    }

    /**
     * @return the txtApartmentZipCode2
     */
    public String getTxtApartmentZipCode2() {
        return txtApartmentZipCode2;
    }

    /**
     * @param txtApartmentZipCode2 the txtApartmentZipCode2 to set
     */
    public void setTxtApartmentZipCode2(String txtApartmentZipCode2) {
        this.txtApartmentZipCode2 = txtApartmentZipCode2;
    }

    /**
     * @return the lstApartmentAddress1
     */
    public String getLstApartmentAddress1() {
        return lstApartmentAddress1;
    }

    /**
     * @param lstApartmentAddress1 the lstApartmentAddress1 to set
     */
    public void setLstApartmentAddress1(String lstApartmentAddress1) {
        this.lstApartmentAddress1 = lstApartmentAddress1;
    }
    
    /**
     * @return the lstCityCodeApartmentAddress1
     */
    public List<CityVo> getLstCityCodeApartmentAddress1() {
        return lstCityCodeApartmentAddress1;
    }

    /**
     * @param lstCityCodeApartmentAddress1 the lstCityCodeApartmentAddress1 to set
     */
    public void setLstCityCodeApartmentAddress1(List<CityVo> lstCityCodeApartmentAddress1) {
        this.lstCityCodeApartmentAddress1 = lstCityCodeApartmentAddress1;
    }

    /**
     * @return the txtApartmentAddress2
     */
    public String getTxtApartmentAddress2() {
        return txtApartmentAddress2;
    }

    /**
     * @param txtApartmentAddress2 the txtApartmentAddress2 to set
     */
    public void setTxtApartmentAddress2(String txtApartmentAddress2) {
        this.txtApartmentAddress2 = txtApartmentAddress2;
    }
    
    /**
     * @return the lstContactProperty
     */
    public List<CodeVo> getLstContactProperty() {
        return lstContactProperty;
    }

    /**
     * @param lstContactProperty the lstContactProperty to set
     */
    public void setLstContactProperty(List<CodeVo> lstContactProperty) {
        this.lstContactProperty = lstContactProperty;
    }
    
    /**
     * @return the rdoContactProperty
     */
    public String getRdoContactProperty() {
        return rdoContactProperty;
    }

    /**
     * @param rdoContactProperty the rdoContactProperty to set
     */
    public void setRdoContactProperty(String rdoContactProperty) {
        this.rdoContactProperty = rdoContactProperty;
    }

    /**
     * @return the txtContactPropertyElse
     */
    public String getTxtContactPropertyElse() {
        return txtContactPropertyElse;
    }

    /**
     * @param txtcontactpropertyelse the txtContactPropertyElse to set
     */
    public void setTxtContactPropertyElse(String txtContactPropertyElse) {
        this.txtContactPropertyElse = txtContactPropertyElse;
    }

    /**
     * @return the txtContactZipCode1
     */
    public String getTxtContactZipCode1() {
        return txtContactZipCode1;
    }

    /**
     * @param txtContactZipCode1 the txtContactZipCode1 to set
     */
    public void setTxtContactZipCode1(String txtContactZipCode1) {
        this.txtContactZipCode1 = txtContactZipCode1;
    }

    /**
     * @return the txtContactZipCode2
     */
    public String getTxtContactZipCode2() {
        return txtContactZipCode2;
    }

    /**
     * @param txtContactZipCode2 the txtContactZipCode2 to set
     */
    public void setTxtContactZipCode2(String txtContactZipCode2) {
        this.txtContactZipCode2 = txtContactZipCode2;
    }

    /**
     * @return the txtContactAddress
     */
    public String getTxtContactAddress() {
        return txtContactAddress;
    }

    /**
     * @param txtContactAddress the txtContactAddress to set
     */
    public void setTxtContactAddress(String txtContactAddress) {
        this.txtContactAddress = txtContactAddress;
    }

    /**
     * @return the txtContactTelno1
     */
    public String getTxtContactTelno1() {
        return txtContactTelno1;
    }

    /**
     * @param txtContactTelno1 the txtContactTelno1 to set
     */
    public void setTxtContactTelno1(String txtContactTelno1) {
        this.txtContactTelno1 = txtContactTelno1;
    }

    /**
     * @return the txtContactTelno2
     */
    public String getTxtContactTelno2() {
        return txtContactTelno2;
    }

    /**
     * @param txtContactTelno2 the txtContactTelno2 to set
     */
    public void setTxtContactTelno2(String txtContactTelno2) {
        this.txtContactTelno2 = txtContactTelno2;
    }

    /**
     * @return the txtContactTelno3
     */
    public String getTxtContactTelno3() {
        return txtContactTelno3;
    }

    /**
     * @param txtContactTelno3 the txtContactTelno3 to set
     */
    public void setTxtContactTelno3(String txtContactTelno3) {
        this.txtContactTelno3 = txtContactTelno3;
    }

    /**
     * @return the txtContactName
     */
    public String getTxtContactName() {
        return txtContactName;
    }

    /**
     * @param txtContactName the txtContactName to set
     */
    public void setTxtContactName(String txtContactName) {
        this.txtContactName = txtContactName;
    }

    /**
     * @return the txtContactNamePhonetic
     */
    public String getTxtContactNamePhonetic() {
        return txtContactNamePhonetic;
    }

    /**
     * @param txtContactNamePhonetic the txtContactNamePhonetic to set
     */
    public void setTxtContactNamePhonetic(String txtContactNamePhonetic) {
        this.txtContactNamePhonetic = txtContactNamePhonetic;
    }

    /**
     * @return the txtContactMail
     */
    public String getTxtContactMail() {
        return txtContactMail;
    }

    /**
     * @param txtContactMail the txtContactMail to set
     */
    public void setTxtContactMail(String txtContactMail) {
        this.txtContactMail = txtContactMail;
    }

    /**
     * @return the txtContactMailRe
     */
    public String getTxtContactMailRe() {
        return txtContactMailRe;
    }

    /**
     * @param txtContactMailRe the txtContactMailRe to set
     */
    public void setTxtContactMailRe(String txtContactMailRe) {
        this.txtContactMailRe = txtContactMailRe;
    }

    /**
     * @return the txtTempPassword
     */
    public String getTxtTempPassword() {
        return txtTempPassword;
    }

    /**
     * @param txtTempPassword the txtTempPassword to set
     */
    public void setTxtTempPassword(String txtTempPassword) {
        this.txtTempPassword = txtTempPassword;
    }

    /**
     * @return the txtTempPasswordRe
     */
    public String getTxtTempPasswordRe() {
        return txtTempPasswordRe;
    }

    /**
     * @param txtTempPasswordRe the txtTempPasswordRe to set
     */
    public void setTxtTempPasswordRe(String txtTempPasswordRe) {
        this.txtTempPasswordRe = txtTempPasswordRe;
    }

    public String getChkTermsConditions() {
        return chkTermsConditions;
    }

    public void setChkTermsConditions(String chkTermsConditions) {
        this.chkTermsConditions = chkTermsConditions;
    }

}
