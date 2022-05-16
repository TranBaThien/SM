/*
 * @(#) RP060Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class RP060Vo extends BaseMansionInfoVo {

    private static final long serialVersionUID = 1L;

    /**
     * 様式名
     */
    private String txtAppendixNo;

    /**
     * 文書番号
     */
    private String documentNo;

    /**
     * 通知年月日
     */
    private String noticeDate;

    /**
     * 宛名(マンション名)
     */
    private String recipientNameApartment;

    /**
     * 宛名(氏名等)
     */
    private String recipientNameUser;

    /**
     * 差出人
     */
    private String sender;

    /**
     * 印章
     */
    private String stamp;

    /**
     * 文中宛名1
     */
    private String textMailing1;

    /**
     * 根拠条文1
     */
    private String evidenceBar;

    /**
     * 根拠条文2
     */
    private String evidenceNo;

    /**
     * 期限に関する規定
     */
    private String periodEvidence;

    /**
     * 前回通知年月日
     */
    private String lastNoticeDate;

    /**
     * 前回文書番号
     */
    private String lastDocumentNo;

    /**
     * 文中宛名2
     */
    private String textMailing2;

    /**
     * 所在地
     */
    private String address;

    /**
     * マンション名
     */
    private String apartmentName;

    /**
     * 届出様式名
     */
    private String notificationFormatName;

    /**
     * 提出期限
     */
    private String notificationTimelimit;

    /**
     * 担当・連絡先
     */
    private String contact;

    /**
     * 
     */
    public RP060Vo() {
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
     * @return the documentNo
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    /**
     * @return the noticeDate
     */
    public String getNoticeDate() {
        return noticeDate;
    }

    /**
     * @param noticeDate the noticeDate to set
     */
    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    /**
     * @return the recipientNameApartment
     */
    public String getRecipientNameApartment() {
        return recipientNameApartment;
    }

    /**
     * @param recipientNameApartment the recipientNameApartment to set
     */
    public void setRecipientNameApartment(String recipientNameApartment) {
        this.recipientNameApartment = recipientNameApartment;
    }

    /**
     * @return the recipientNameUser
     */
    public String getRecipientNameUser() {
        return recipientNameUser;
    }

    /**
     * @param recipientNameUser the recipientNameUser to set
     */
    public void setRecipientNameUser(String recipientNameUser) {
        this.recipientNameUser = recipientNameUser;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the stamp
     */
    public String getStamp() {
        return stamp;
    }

    /**
     * @param stamp the stamp to set
     */
    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    /**
     * @return the textMailing1
     */
    public String getTextMailing1() {
        return textMailing1;
    }

    /**
     * @param textMailing1 the textMailing1 to set
     */
    public void setTextMailing1(String textMailing1) {
        this.textMailing1 = textMailing1;
    }

    /**
     * @return the evidenceBar
     */
    public String getEvidenceBar() {
        return evidenceBar;
    }

    /**
     * @param evidenceBar the evidenceBar to set
     */
    public void setEvidenceBar(String evidenceBar) {
        this.evidenceBar = evidenceBar;
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
     * @return the periodEvidence
     */
    public String getPeriodEvidence() {
        return periodEvidence;
    }

    /**
     * @param periodEvidence the periodEvidence to set
     */
    public void setPeriodEvidence(String periodEvidence) {
        this.periodEvidence = periodEvidence;
    }

    /**
     * @return the lastNoticeDate
     */
    public String getLastNoticeDate() {
        return lastNoticeDate;
    }

    /**
     * @param lastNoticeDate the lastNoticeDate to set
     */
    public void setLastNoticeDate(String lastNoticeDate) {
        this.lastNoticeDate = lastNoticeDate;
    }

    /**
     * @return the lastDocumentNo
     */
    public String getLastDocumentNo() {
        return lastDocumentNo;
    }

    /**
     * @param lastDocumentNo the lastDocumentNo to set
     */
    public void setLastDocumentNo(String lastDocumentNo) {
        this.lastDocumentNo = lastDocumentNo;
    }

    /**
     * @return the textMailing2
     */
    public String getTextMailing2() {
        return textMailing2;
    }

    /**
     * @param textMailing2 the textMailing2 to set
     */
    public void setTextMailing2(String textMailing2) {
        this.textMailing2 = textMailing2;
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
     * @return the notificationFormatName
     */
    public String getNotificationFormatName() {
        return notificationFormatName;
    }

    /**
     * @param notificationFormatName the notificationFormatName to set
     */
    public void setNotificationFormatName(String notificationFormatName) {
        this.notificationFormatName = notificationFormatName;
    }

    /**
     * @return the notificationTimelimit
     */
    public String getNotificationTimelimit() {
        return notificationTimelimit;
    }

    /**
     * @param notificationTimelimit the notificationTimelimit to set
     */
    public void setNotificationTimelimit(String notificationTimelimit) {
        this.notificationTimelimit = notificationTimelimit;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
