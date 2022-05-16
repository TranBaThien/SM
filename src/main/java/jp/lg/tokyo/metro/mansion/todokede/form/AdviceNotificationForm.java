/*
 * @(#) AdviceNotificationForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 28, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceTemplateVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;

/**
 * @author nvlong1
 *
 */
public class AdviceNotificationForm extends BaseForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String apartmentId;
    private String apartmentName;
    private String apartmentNamePhonetic;
    private String address;
    private String notificationNo;
    private int numRowAdvice;
    private String keyAdviceNo;
    private String newestNotificationNo;
    private String lblRecipientNameApartment;
    private String lblRecipientNameUser;
    private String lblAddress;
    private String lblApartmentName;
    private List<CodeVo> lstRdoNotificationMethod;
    private String lblNotificationDate;
    private String lblEvidenceBar;
    private String[] lstEvidenceNo;
    private List<AdviceTemplateVo> lstAdvice;
    private String advice;
    private String chkConfirm;
    private String contactMailAddress;
    private int adviceDetailsIndentionMax;
    private String lblApartmentAddress;
    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("様式名")
    @Position(value = 1)
    private String txtAppendixNo;

    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("文書番号")
    @Position(value = 2)
    private String txtDocumentNo;

    @NotBlank
    @MaxSize(val = 10)
    @HalfsizeAlphanumeric
    @DateCustom(pattern = CommonConstants.SLASH_YYYYMMDD)
    @FieldName("通知年月日")
    @Position(value = 3)
    private String calNoticeDate;

    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("発信者名")
    @Position(value = 4)
    private String txtSender;

    @SelectCode(code = {CommonConstants.CD042, CommonConstants.CD043})
    @FieldName("根拠条文")
    @Position(value = 5)
    private String evidenceNo;

    @NotBlank
    @MaxSize(val = 1000)
    @SpecialCharacters
    @FieldName("助言内容")
    @Position(value = 6)
    private String txtAdviceDetails;

    @SelectCode(code = CommonConstants.CD017)
    @FieldName("通知方法")
    @Position(value = 7)
    private String rdoNotificationMethod;

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentNamePhonetic() {
        return apartmentNamePhonetic;
    }

    public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
        this.apartmentNamePhonetic = apartmentNamePhonetic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }

    public int getNumRowAdvice() {
        return numRowAdvice;
    }

    public void setNumRowAdvice(int numRowAdvice) {
        this.numRowAdvice = numRowAdvice;
    }

    public String getKeyAdviceNo() {
        return keyAdviceNo;
    }

    public void setKeyAdviceNo(String keyAdviceNo) {
        this.keyAdviceNo = keyAdviceNo;
    }

    public String getNewestNotificationNo() {
        return newestNotificationNo;
    }

    public void setNewestNotificationNo(String newestNotificationNo) {
        this.newestNotificationNo = newestNotificationNo;
    }

    public String getLblRecipientNameApartment() {
        return lblRecipientNameApartment;
    }

    public void setLblRecipientNameApartment(String lblRecipientNameApartment) {
        this.lblRecipientNameApartment = lblRecipientNameApartment;
    }

    public String getLblRecipientNameUser() {
        return lblRecipientNameUser;
    }

    public void setLblRecipientNameUser(String lblRecipientNameUser) {
        this.lblRecipientNameUser = lblRecipientNameUser;
    }

    public String getLblAddress() {
        return lblAddress;
    }

    public void setLblAddress(String lblAddress) {
        this.lblAddress = lblAddress;
    }

    public String getLblApartmentName() {
        return lblApartmentName;
    }

    public void setLblApartmentName(String lblApartmentName) {
        this.lblApartmentName = lblApartmentName;
    }

    public List<CodeVo> getLstRdoNotificationMethod() {
        return lstRdoNotificationMethod;
    }

    public void setLstRdoNotificationMethod(List<CodeVo> lstRdoNotificationMethod) {
        this.lstRdoNotificationMethod = lstRdoNotificationMethod;
    }

    public String getLblNotificationDate() {
        return lblNotificationDate;
    }

    public void setLblNotificationDate(String lblNotificationDate) {
        this.lblNotificationDate = lblNotificationDate;
    }

    public String getLblEvidenceBar() {
        return lblEvidenceBar;
    }

    public void setLblEvidenceBar(String lblEvidenceBar) {
        this.lblEvidenceBar = lblEvidenceBar;
    }

    public String[] getLstEvidenceNo() {
        return lstEvidenceNo;
    }

    public void setLstEvidenceNo(String[] lstEvidenceNo) {
        this.lstEvidenceNo = lstEvidenceNo;
    }

    public List<AdviceTemplateVo> getLstAdvice() {
        return lstAdvice;
    }

    public void setLstAdvice(List<AdviceTemplateVo> lstAdvice) {
        this.lstAdvice = lstAdvice;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getChkConfirm() {
        return chkConfirm;
    }

    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    public String getTxtAppendixNo() {
        return txtAppendixNo;
    }

    public void setTxtAppendixNo(String txtAppendixNo) {
        this.txtAppendixNo = txtAppendixNo;
    }

    public String getTxtDocumentNo() {
        return txtDocumentNo;
    }

    public void setTxtDocumentNo(String txtDocumentNo) {
        this.txtDocumentNo = txtDocumentNo;
    }

    public String getCalNoticeDate() {
        return calNoticeDate;
    }

    public void setCalNoticeDate(String calNoticeDate) {
        this.calNoticeDate = calNoticeDate;
    }

    public String getTxtSender() {
        return txtSender;
    }

    public void setTxtSender(String txtSender) {
        this.txtSender = txtSender;
    }

    public String getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    public String getTxtAdviceDetails() {
        return txtAdviceDetails;
    }

    public void setTxtAdviceDetails(String txtAdviceDetails) {
        this.txtAdviceDetails = txtAdviceDetails;
    }

    public String getRdoNotificationMethod() {
        return rdoNotificationMethod;
    }

    public void setRdoNotificationMethod(String rdoNotificationMethod) {
        this.rdoNotificationMethod = rdoNotificationMethod;
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

    public int getAdviceDetailsIndentionMax() {
        return adviceDetailsIndentionMax;
    }

    public void setAdviceDetailsIndentionMax(int adviceDetailsIndentionMax) {
        this.adviceDetailsIndentionMax = adviceDetailsIndentionMax;
    }

    public String getLblApartmentAddress() {
        return lblApartmentAddress;
    }

    public void setLblApartmentAddress(String lblApartmentAddress) {
        this.lblApartmentAddress = lblApartmentAddress;
    }

}
