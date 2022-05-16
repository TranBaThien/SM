/*
 * @(#) SupervisedNoticeLogicForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
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
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;

public class SupervisedNoticeLogicForm extends BaseForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 様式名
     */
    @NotBlank
    @MaxSize(val = 20)
    @FieldName("様式名")
    @Position(value = 1)
    @SpecialCharacters
    private String txtAppendixNo;

    /**
     * 文書番号
     */
    @NotBlank
    @MaxSize(val = 20)
    @FieldName("文書番号")
    @Position(value = 2)
    @SpecialCharacters
    private String txtDocumentNo;

    /**
     * 通知年月日
     */
    @NotBlank
    @HalfsizeAlphanumeric
    @DateCustom(pattern = "yyyy/MM/dd")
    @MaxSize(val = 10)
    @FieldName("通知年月日")
    @Position(value = 3)
    private String calNoticeDate;

    /**
     * 督促通知書種別
     */
    @FieldName("督促通知書種別")
    @Position(value = 4)
    private String lblSupervisedNoticeTypeCode;

    /**
     * 発信者名
     */
    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("発信者名")
    @Position(value = 5)
    private String txtSender;

    /**
     * 通知書宛先
     */
    @SelectCode(code = CommonConstants.CD018)
    @FieldName("通知書宛先")
    @Position(value = 6)
    private String rdoMailingCode;

    /**
     * 宛先(マンション名)
     */
    @NotBlank
    @MaxSize(val = 50)
    @SpecialCharacters
    @FieldName("宛先(マンション名)")
    @Position(value = 7)
    private String txtRecipientNameApartment;

    /**
     * 宛先(氏名等)
     */
    @NotBlank
    @MaxSize(val = 26)
    @SpecialCharacters
    @FieldName("宛先(氏名等)")
    @Position(value = 8)
    private String txtRecipientNameUser;

    /**
     * 本文中宛名1
     */
    @NotBlank
    @MaxSize(val = 31)
    @SpecialCharacters
    @FieldName("本文中宛名1")
    @Position(value = 9)
    private String txtTextAdress1;

    /**
     * 根拠条文1
     */

    @FieldName("根拠条文1")
    @Position(value = 10)
    private String lblEvidenceBar;

    /**
     * 根拠条文2
     */
    @SelectCode(code = CommonConstants.CD042)
    @FieldName("根拠条文2")
    @Position(value = 11)
    private String lstEvidenceNo;

    /**
     * 前回通知年月日
     */
    @FieldName("前回通知年月日")
    @Position(value = 12)
    private String lblLastNoticeDate;

    /**
     * 前回文書番号
     */
    @FieldName("前回文書番号")
    @Position(value = 13)
    private String lblLastDocumentNo;

    /**
     * 期限に関する規定
     */
    @FieldName("期限に関する規定")
    @Position(value = 14)
    private String lblPeriodEvidence;

    /**
     * 本文中宛名2
     */
    @NotBlank
    @MaxSize(val = 6)
    @SpecialCharacters
    @FieldName("本文中宛名2")
    @Position(value = 15)
    private String txtTextAdress2;

    /**
     * 本文中宛名2
     */
    @FieldName("本文中宛名2")
    @Position(value = 16)
    private String lblAddress;

    /**
     * マンション名
     */
    @FieldName("マンション名")
    @Position(value = 17)
    private String lblApartmentName;

    /**
     * 届出様式名
     */
    @FieldName("届出様式名")
    @Position(value = 18)
    private String lblNotificationFormatName;

    /**
     * 提出期限
     */
    @NotBlank
    @FieldName("提出期限")
    @Position(value = 19)
    @HalfsizeAlphanumeric
    @MaxSize(val = 10)
    @DateCustom(pattern = "yyyy/MM/dd")
    private String calNotificationTimelimit;

    /**
     * 担当・連絡先
     */
    @NotBlank
    @SpecialCharacters
    @MaxSize(val = 300)
    @FieldName("担当・連絡先")
    @Position(value = 20)
    private String txaContact;
    
    

    private String chkConfirm;

    private String evidenceNo;

    private List<CodeVo> lstEvidenceNoCode;

    private List<CodeVo> lstMailingCode;
    
    private String apartmentId;

    private String newestNotificationNo;
    
    private String supervisedNoticeNo;
    
    private String recipientNameInCaseAddressIsIndividualOwner;
    
    private int supervisedNoticeCount;
    
    private int txaContactMaxLength;
    
    private int contactIndentionMax;

    private String chkTermsConditions;
    /**
     * 
     */
    public SupervisedNoticeLogicForm() {
        super();
    }

    /**
     * @return the txtAppendixNo
     */
    public String getTxtAppendixNo() {
        return txtAppendixNo;
    }

    /**
     * @param txtAppendixNo the txtAppendixNo to set
     */
    public void setTxtAppendixNo(String txtAppendixNo) {
        this.txtAppendixNo = txtAppendixNo;
    }

    /**
     * @return the txtDocumentNo
     */
    public String getTxtDocumentNo() {
        return txtDocumentNo;
    }

    /**
     * @param txtDocumentNo the txtDocumentNo to set
     */
    public void setTxtDocumentNo(String txtDocumentNo) {
        this.txtDocumentNo = txtDocumentNo;
    }

    /**
     * @return the calNoticeDate
     */
    public String getCalNoticeDate() {
        return calNoticeDate;
    }

    /**
     * @param calNoticeDate the calNoticeDate to set
     */
    public void setCalNoticeDate(String calNoticeDate) {
        this.calNoticeDate = calNoticeDate;
    }

    /**
     * @return the txtSender
     */
    public String getTxtSender() {
        return txtSender;
    }

    /**
     * @param txtSender the txtSender to set
     */
    public void setTxtSender(String txtSender) {
        this.txtSender = txtSender;
    }

    /**
     * @return the rdoMailingCode
     */
    public String getRdoMailingCode() {
        return rdoMailingCode;
    }

    /**
     * @param rdoMailingCode the rdoMailingCode to set
     */
    public void setRdoMailingCode(String rdoMailingCode) {
        this.rdoMailingCode = rdoMailingCode;
    }

    /**
     * @return the txtRecipientNameApartment
     */
    public String getTxtRecipientNameApartment() {
        return txtRecipientNameApartment;
    }

    /**
     * @param txtRecipientNameApartment the txtRecipientNameApartment to set
     */
    public void setTxtRecipientNameApartment(String txtRecipientNameApartment) {
        this.txtRecipientNameApartment = txtRecipientNameApartment;
    }

    /**
     * @return the txtRecipientNameUser
     */
    public String getTxtRecipientNameUser() {
        return txtRecipientNameUser;
    }

    /**
     * @param txtRecipientNameUser the txtRecipientNameUser to set
     */
    public void setTxtRecipientNameUser(String txtRecipientNameUser) {
        this.txtRecipientNameUser = txtRecipientNameUser;
    }

    /**
     * @return the calNotificationTimelimit
     */
    public String getCalNotificationTimelimit() {
        return calNotificationTimelimit;
    }

    /**
     * @param calNotificationTimelimit the calNotificationTimelimit to set
     */
    public void setCalNotificationTimelimit(String calNotificationTimelimit) {
        this.calNotificationTimelimit = calNotificationTimelimit;
    }

    /**
     * @return the txaContact
     */
    public String getTxaContact() {
        return txaContact;
    }

    /**
     * @param txaContact the txaContact to set
     */
    public void setTxaContact(String txaContact) {
        this.txaContact = txaContact;
    }

    /**
     * @return the chkConfirm
     */
    public String getChkConfirm() {
        return chkConfirm;
    }

    /**
     * @param chkConfirm the chkConfirm to set
     */
    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    /**
     * @return the txtTextAdress1
     */
    public String getTxtTextAdress1() {
        return txtTextAdress1;
    }

    /**
     * @param txtTextAdress1 the txtTextAdress1 to set
     */
    public void setTxtTextAdress1(String txtTextAdress1) {
        this.txtTextAdress1 = txtTextAdress1;
    }

    /**
     * @return the txtTextAdress2
     */
    public String getTxtTextAdress2() {
        return txtTextAdress2;
    }

    /**
     * @param txtTextAdress2 the txtTextAdress2 to set
     */
    public void setTxtTextAdress2(String txtTextAdress2) {
        this.txtTextAdress2 = txtTextAdress2;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the lblSupervisedNoticeTypeCode
     */
    public String getLblSupervisedNoticeTypeCode() {
        return lblSupervisedNoticeTypeCode;
    }

    /**
     * @param lblSupervisedNoticeTypeCode the lblSupervisedNoticeTypeCode to set
     */
    public void setLblSupervisedNoticeTypeCode(String lblSupervisedNoticeTypeCode) {
        this.lblSupervisedNoticeTypeCode = lblSupervisedNoticeTypeCode;
    }

    /**
     * @return the lblEvidenceBar
     */
    public String getLblEvidenceBar() {
        return lblEvidenceBar;
    }

    /**
     * @param lblEvidenceBar the lblEvidenceBar to set
     */
    public void setLblEvidenceBar(String lblEvidenceBar) {
        this.lblEvidenceBar = lblEvidenceBar;
    }

    /**
     * @return the lblPeriodEvidence
     */
    public String getLblPeriodEvidence() {
        return lblPeriodEvidence;
    }

    /**
     * @param lblPeriodEvidence the lblPeriodEvidence to set
     */
    public void setLblPeriodEvidence(String lblPeriodEvidence) {
        this.lblPeriodEvidence = lblPeriodEvidence;
    }

    /**
     * @return the lblAddress
     */
    public String getLblAddress() {
        return lblAddress;
    }

    /**
     * @param lblAddress the lblAddress to set
     */
    public void setLblAddress(String lblAddress) {
        this.lblAddress = lblAddress;
    }

    /**
     * @return the lblNotificationFormatName
     */
    public String getLblNotificationFormatName() {
        return lblNotificationFormatName;
    }

    /**
     * @param lblNotificationFormatName the lblNotificationFormatName to set
     */
    public void setLblNotificationFormatName(String lblNotificationFormatName) {
        this.lblNotificationFormatName = lblNotificationFormatName;
    }

    /**
     * @return the lblApartmentName
     */
    public String getLblApartmentName() {
        return lblApartmentName;
    }

    /**
     * @param lblApartmentName the lblApartmentName to set
     */
    public void setLblApartmentName(String lblApartmentName) {
        this.lblApartmentName = lblApartmentName;
    }

    /**
     * @return the evidenceNo
     */
    public String getEvidenceNo() {
        return evidenceNo;
    }

    /**
     * @param evidenceNo the evidenceNo to set
     */
    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    /**
     * @return the lblLastNoticeDate
     */
    public String getLblLastNoticeDate() {
        return lblLastNoticeDate;
    }

    /**
     * @param lblLastNoticeDate the lblLastNoticeDate to set
     */
    public void setLblLastNoticeDate(String lblLastNoticeDate) {
        this.lblLastNoticeDate = lblLastNoticeDate;
    }

    /**
     * @return the lblLastDocumentNo
     */
    public String getLblLastDocumentNo() {
        return lblLastDocumentNo;
    }

    /**
     * @param lblLastDocumentNo the lblLastDocumentNo to set
     */
    public void setLblLastDocumentNo(String lblLastDocumentNo) {
        this.lblLastDocumentNo = lblLastDocumentNo;
    }

    /**
     * @return the apartmentID
     */
    public String getApartmentId() {
        return apartmentId;
    }

    /**
     * @param apartmentID the apartmentID to set
     */
    public void setApartmentId(String apartmentID) {
        this.apartmentId = apartmentID;
    }

    /**
     * @return the lstEvidenceNo
     */
    public String getLstEvidenceNo() {
        return lstEvidenceNo;
    }

    /**
     * @param lstEvidenceNo the lstEvidenceNo to set
     */
    public void setLstEvidenceNo(String lstEvidenceNo) {
        this.lstEvidenceNo = lstEvidenceNo;
    }

    /**
     * @return the lstEvidenceNoCode
     */
    public List<CodeVo> getLstEvidenceNoCode() {
        return lstEvidenceNoCode;
    }

    /**
     * @param lstEvidenceNoCode the lstEvidenceNoCode to set
     */
    public void setLstEvidenceNoCode(List<CodeVo> lstEvidenceNoCode) {
        this.lstEvidenceNoCode = lstEvidenceNoCode;
    }

    /**
     * @return the lstMailingCode
     */
    public List<CodeVo> getLstMailingCode() {
        return lstMailingCode;
    }

    /**
     * @param lstMailingCode the lstMailingCode to set
     */
    public void setLstMailingCode(List<CodeVo> lstMailingCode) {
        this.lstMailingCode = lstMailingCode;
    }

    /**
     * @return the newestNotificationNo
     */
    public String getNewestNotificationNo() {
        return newestNotificationNo;
    }

    /**
     * @param newestNotificationNo the newestNotificationNo to set
     */
    public void setNewestNotificationNo(String newestNotificationNo) {
        this.newestNotificationNo = newestNotificationNo;
    }

    /**
     * @return the supervisedNoticeNo
     */
    public String getSupervisedNoticeNo() {
        return supervisedNoticeNo;
    }

    /**
     * @param supervisedNoticeNo the supervisedNoticeNo to set
     */
    public void setSupervisedNoticeNo(String supervisedNoticeNo) {
        this.supervisedNoticeNo = supervisedNoticeNo;
    }

    /**
     * @return the recipientNameInCaseAddressIsIndividualOwner
     */
    public String getRecipientNameInCaseAddressIsIndividualOwner() {
        return recipientNameInCaseAddressIsIndividualOwner;
    }

    /**
     * @param recipientNameInCaseAddressIsIndividualOwner the recipientNameInCaseAddressIsIndividualOwner to set
     */
    public void setRecipientNameInCaseAddressIsIndividualOwner(String recipientNameInCaseAddressIsIndividualOwner) {
        this.recipientNameInCaseAddressIsIndividualOwner = recipientNameInCaseAddressIsIndividualOwner;
    }

    /**
     * @return the supervisedNoticeCount
     */
    public int getSupervisedNoticeCount() {
        return supervisedNoticeCount;
    }

    /**
     * @param supervisedNoticeCount the supervisedNoticeCount to set
     */
    public void setSupervisedNoticeCount(int supervisedNoticeCount) {
        this.supervisedNoticeCount = supervisedNoticeCount;
    }

    /**
     * @return the txaContactMaxLength
     */
    public int getTxaContactMaxLength() {
        return txaContactMaxLength;
    }

    /**
     * @param txaContactMaxLength the txaContactMaxLength to set
     */
    public void setTxaContactMaxLength(int txaContactMaxLength) {
        this.txaContactMaxLength = txaContactMaxLength;
    }

    /**
     * @return the contactIndentionMax
     */
    public int getContactIndentionMax() {
        return contactIndentionMax;
    }

    /**
     * @param contactIndentionMax the contactIndentionMax to set
     */
    public void setContactIndentionMax(int contactIndentionMax) {
        this.contactIndentionMax = contactIndentionMax;
    }

    public String getChkTermsConditions() {
        return chkTermsConditions;
    }

    public void setChkTermsConditions(String chkTermsConditions) {
        this.chkTermsConditions = chkTermsConditions;
    }
}
