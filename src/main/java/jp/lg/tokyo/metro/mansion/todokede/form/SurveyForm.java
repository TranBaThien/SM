/*
 * @(#)SurveyForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.util.List;

import javax.validation.constraints.NotBlank;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HaftSizeNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;

public class SurveyForm extends BaseMansionInfoVo{
    private static final long serialVersionUID = 1L;
    private String mailAddress;
    private String inveseNo;
    private String lblAddress;
    private String progressRecordNo;
    private String notificationPersonName;
    private String tempNo;
    private List<CodeVo> rdoNoticeDestinationList;
    private List<CodeVo> rdoNotificationMethodList;
    private String hasDataStore;
    private int contactMax;
    private int contactBRMax;
    private String linkAddress;
    private String checkBoxConfirm;
    private String txtRecipientNameApartmentTemp;

    @NotBlank
    @Position(1)
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("様式名")
    private String txtAppendixNo;
    
    @NotBlank
    @Position(2)
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("文書番号")
    private String txtDocumentNo;

    @NotBlank
    @Position(3)
    @HalfsizeAlphanumeric
    @DateCustom(pattern = CommonConstants.SLASH_YYYYMMDD)
    @MaxSize(val = 10)
    @FieldName("通知年月日")
    private String calNoticeDate;

    @Position(4)
    @SelectCode(code = CommonConstants.CD018)
    @FieldName("通知書宛先")
    private String rdoNoticeDestination;

    @NotBlank
    @Position(5)
    @MaxSize(val = 50)
    @SpecialCharacters
    @FieldName("宛名(マンション名)")
    private String txtRecipientNameApartment;
    
    @NotBlank
    @Position(6)
    @MaxSize(val = 26)
    @SpecialCharacters
    @FieldName("宛名(氏名等)")
    private String txtRecipientNameUser; 

    @NotBlank
    @Position(7)
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("発信者名")
    private String txtSender;

    @NotBlank
    @Position(8)
    @MaxSize(val = 31)
    @SpecialCharacters
    @FieldName("本文中宛先")
    private String txtTextAdress;
    
    @NotBlank
    @Position(9)
    @HalfsizeAlphanumeric
    @DateCustom(pattern = CommonConstants.FORMAT_DATETIME_YYYYMMDDHHMM)
    @MaxSize(val = 16)
    @FieldName("調査の実施予定日時")
    private String calInvestImplPlanTime;

    @NotBlank
    @Position(10)
    @HaftSizeNumber
    @MaxSize(val = 1)
    @FieldName("調査を行う者")
    private String txtInvestImplNumberPeople;

    @NotBlank
    @Position(11)
    @MaxSize(val = 100)
    @SpecialCharacters
    @FieldName("必要となる書類")
    private String txtNecessaryDocument;

    @NotBlank
    @Position(12)
    @MaxSize(val = 300)
    @SpecialCharacters
    @FieldName("担当・連絡先")
    private String txaContact;

    @Position(13)
    @SelectCode(code = CommonConstants.CD017)
    @FieldName("通知方法")
    private String rdoNotificationMethod;

    /**
     * @return the mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * @param mailAddress the mailAddress to set
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * @return the inveseNo
     */
    public String getInveseNo() {
        return inveseNo;
    }

    /**
     * @param inveseNo the inveseNo to set
     */
    public void setInveseNo(String inveseNo) {
        this.inveseNo = inveseNo;
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
     * @return the progressRecordNo
     */
    public String getProgressRecordNo() {
        return progressRecordNo;
    }

    /**
     * @param progressRecordNo the progressRecordNo to set
     */
    public void setProgressRecordNo(String progressRecordNo) {
        this.progressRecordNo = progressRecordNo;
    }

    /**
     * @return the notificationPersonName
     */
    public String getNotificationPersonName() {
        return notificationPersonName;
    }

    /**
     * @param notificationPersonName the notificationPersonName to set
     */
    public void setNotificationPersonName(String notificationPersonName) {
        this.notificationPersonName = notificationPersonName;
    }

    /**
     * @return the tempNo
     */
    public String getTempNo() {
        return tempNo;
    }

    /**
     * @param tempNo the tempNo to set
     */
    public void setTempNo(String tempNo) {
        this.tempNo = tempNo;
    }

    /**
     * @return the rdoNoticeDestinationList
     */
    public List<CodeVo> getRdoNoticeDestinationList() {
        return rdoNoticeDestinationList;
    }

    /**
     * @param rdoNoticeDestinationList the rdoNoticeDestinationList to set
     */
    public void setRdoNoticeDestinationList(List<CodeVo> rdoNoticeDestinationList) {
        this.rdoNoticeDestinationList = rdoNoticeDestinationList;
    }

    /**
     * @return the rdoNotificationMethodList
     */
    public List<CodeVo> getRdoNotificationMethodList() {
        return rdoNotificationMethodList;
    }

    /**
     * @param rdoNotificationMethodList the rdoNotificationMethodList to set
     */
    public void setRdoNotificationMethodList(List<CodeVo> rdoNotificationMethodList) {
        this.rdoNotificationMethodList = rdoNotificationMethodList;
    }

    /**
     * @return the hasDataStore
     */
    public String getHasDataStore() {
        return hasDataStore;
    }

    /**
     * @param hasDataStore the hasDataStore to set
     */
    public void setHasDataStore(String hasDataStore) {
        this.hasDataStore = hasDataStore;
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
     * @return the rdoNoticeDestination
     */
    public String getRdoNoticeDestination() {
        return rdoNoticeDestination;
    }

    /**
     * @param rdoNoticeDestination the rdoNoticeDestination to set
     */
    public void setRdoNoticeDestination(String rdoNoticeDestination) {
        this.rdoNoticeDestination = rdoNoticeDestination;
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
     * @return the txtTextAdress
     */
    public String getTxtTextAdress() {
        return txtTextAdress;
    }

    /**
     * @param txtTextAdress the txtTextAdress to set
     */
    public void setTxtTextAdress(String txtTextAdress) {
        this.txtTextAdress = txtTextAdress;
    }

    /**
     * @return the calInvestImplPlanTime
     */
    public String getCalInvestImplPlanTime() {
        return calInvestImplPlanTime;
    }

    /**
     * @param calInvestImplPlanTime the calInvestImplPlanTime to set
     */
    public void setCalInvestImplPlanTime(String calInvestImplPlanTime) {
        this.calInvestImplPlanTime = calInvestImplPlanTime;
    }

    /**
     * @return the txtInvestImplNumberPeople
     */
    public String getTxtInvestImplNumberPeople() {
        return txtInvestImplNumberPeople;
    }

    /**
     * @param txtInvestImplNumberPeople the txtInvestImplNumberPeople to set
     */
    public void setTxtInvestImplNumberPeople(String txtInvestImplNumberPeople) {
        this.txtInvestImplNumberPeople = txtInvestImplNumberPeople;
    }

    /**
     * @return the txtNecessaryDocument
     */
    public String getTxtNecessaryDocument() {
        return txtNecessaryDocument;
    }

    /**
     * @param txtNecessaryDocument the txtNecessaryDocument to set
     */
    public void setTxtNecessaryDocument(String txtNecessaryDocument) {
        this.txtNecessaryDocument = txtNecessaryDocument;
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
     * @return the rdoNotificationMethod
     */
    public String getRdoNotificationMethod() {
        return rdoNotificationMethod;
    }

    /**
     * @param rdoNotificationMethod the rdoNotificationMethod to set
     */
    public void setRdoNotificationMethod(String rdoNotificationMethod) {
        this.rdoNotificationMethod = rdoNotificationMethod;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getContactMax() {
        return contactMax;
    }

    public void setContactMax(int contactMax) {
        this.contactMax = contactMax;
    }

    public int getContactBRMax() {
        return contactBRMax;
    }

    public void setContactBRMax(int contactBRMax) {
        this.contactBRMax = contactBRMax;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getCheckBoxConfirm() {
        return checkBoxConfirm;
    }

    public void setCheckBoxConfirm(String checkBoxConfirm) {
        this.checkBoxConfirm = checkBoxConfirm;
    }

    public String getTxtRecipientNameApartmentTemp() {
        return txtRecipientNameApartmentTemp;
    }

    public void setTxtRecipientNameApartmentTemp(String txtRecipientNameApartmentTemp) {
        this.txtRecipientNameApartmentTemp = txtRecipientNameApartmentTemp;
    }
}
