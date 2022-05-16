/*
 * @(#) RP020Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author pdquang
 *
 */
public class RP020Vo {

    /**
     * 様式名
     */
    private String appendixNo;

    /**
     * 文書番号
     */
    private String documentNo;

    /**
     * 通知年月日
     */
    private String noticeDate;

    /**
     * 宛名先(マンション名)
     */
    private String recipientNameApartment;

    /**
     * 宛名先(氏名等)
     */
    private String recipientNameUser;

    /**
     * 発信者名
     */
    private String sender;

    /**
     * 印章
     */
    private String stampName;

    /**
     * 届出年月日
     */
    private String notificationDate;

    /**
     * 届出根拠条文（条）
     */
    private String evidenceBar;

    /**
     * 届出根拠条文（項）
     */
    private String evidenceNo;

    public String getAppendixNo() {
        return appendixNo;
    }

    public void setAppendixNo(String appendixNo) {
        this.appendixNo = appendixNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getRecipientNameApartment() {
        return recipientNameApartment;
    }

    public void setRecipientNameApartment(String recipientNameApartment) {
        this.recipientNameApartment = recipientNameApartment;
    }

    public String getRecipientNameUser() {
        return recipientNameUser;
    }

    public void setRecipientNameUser(String recipientNameUser) {
        this.recipientNameUser = recipientNameUser;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getEvidenceBar() {
        return evidenceBar;
    }

    public void setEvidenceBar(String evidenceBar) {
        this.evidenceBar = evidenceBar;
    }

    public String getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

}
