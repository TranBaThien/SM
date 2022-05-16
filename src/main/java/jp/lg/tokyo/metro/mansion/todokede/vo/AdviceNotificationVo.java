/*
 * @(#) AdviceNotificationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 27, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

/**
 * @author nvlong1
 *
 */
public class AdviceNotificationVo extends BaseMansionInfoVo {

    private static final long serialVersionUID = 1L;

    private String notificationNo;

    private String txtAppendixNo;

    private String txtDocumentNo;

    private String calNoticeDate;

    private String lblRecipientNameApartment;

    private String lblRecipientNameUser;

    private String txtSender;

    private String lblNotificationDate;

    private String lblEvidenceBar;

    private String[] lstEvidenceNo;

    private String evidenceNo;

    private String lblAddress;

    private String lblApartmentName;

    private List<AdviceTemplateVo> lstAdvice;

    private String advice;

    private String txtAdviceDetails;

    private String chkConfirm;

    private List<CodeVo> lstRdoNotificationMethod;

    private String rdoNotificationMethod;

    private int numRowAdvice;

    private String newestNotificationNo;

    private String keyAdviceNo;

    private String contactMailAddress;

    private int adviceDetailsIndentionMax;

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
    }

    public String getKeyAdviceNo() {
        return keyAdviceNo;
    }

    public void setKeyAdviceNo(String keyAdviceNo) {
        this.keyAdviceNo = keyAdviceNo;
    }

    public String getRdoNotificationMethod() {
        return rdoNotificationMethod;
    }

    public void setRdoNotificationMethod(String rdoNotificationMethod) {
        this.rdoNotificationMethod = rdoNotificationMethod;
    }

    public String getNewestNotificationNo() {
        return newestNotificationNo;
    }

    public void setNewestNotificationNo(String newestNotificationNo) {
        this.newestNotificationNo = newestNotificationNo;
    }

    public int getNumRowAdvice() {
        return numRowAdvice;
    }

    public void setNumRowAdvice(int numRowAdvice) {
        this.numRowAdvice = numRowAdvice;
    }

    public List<CodeVo> getLstRdoNotificationMethod() {
        return lstRdoNotificationMethod;
    }

    public void setLstRdoNotificationMethod(List<CodeVo> lstRdoNotificationMethod) {
        this.lstRdoNotificationMethod = lstRdoNotificationMethod;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getCalNoticeDate() {
        return calNoticeDate;
    }

    public void setCalNoticeDate(String calNoticeDate) {
        this.calNoticeDate = calNoticeDate;
    }

    public String getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    public String getChkConfirm() {
        return chkConfirm;
    }

    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    public String getLblRecipientNameApartment() {
        return lblRecipientNameApartment;
    }

    public void setLblRecipientNameApartment(String lblRecipientNameApartment) {
        this.lblRecipientNameApartment = lblRecipientNameApartment;
    }

    public String getTxtAppendixNo() {
        return txtAppendixNo;
    }

    public void setTxtAppendixNo(String txtAppendixNo) {
        this.txtAppendixNo = txtAppendixNo;
    }

    public String getLblRecipientNameUser() {
        return lblRecipientNameUser;
    }

    public void setLblRecipientNameUser(String lblRecipientNameUser) {
        this.lblRecipientNameUser = lblRecipientNameUser;
    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }

    public String getTxtDocumentNo() {
        return txtDocumentNo;
    }

    public void setTxtDocumentNo(String txtDocumentNo) {
        this.txtDocumentNo = txtDocumentNo;
    }

    public String getTxtSender() {
        return txtSender;
    }

    public void setTxtSender(String txtSender) {
        this.txtSender = txtSender;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<AdviceTemplateVo> getLstAdvice() {
        return lstAdvice;
    }

    public void setLstAdvice(List<AdviceTemplateVo> lstAdvice) {
        this.lstAdvice = lstAdvice;
    }

    public String getTxtAdviceDetails() {
        return txtAdviceDetails;
    }

    public void setTxtAdviceDetails(String txtAdviceDetails) {
        this.txtAdviceDetails = txtAdviceDetails;
    }

    public int getAdviceDetailsIndentionMax() {
        return adviceDetailsIndentionMax;
    }

    public void setAdviceDetailsIndentionMax(int adviceDetailsIndentionMax) {
        this.adviceDetailsIndentionMax = adviceDetailsIndentionMax;
    }

}
