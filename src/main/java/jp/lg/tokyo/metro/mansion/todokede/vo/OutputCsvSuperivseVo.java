/*
 * @(#) OutputCsvSuperivseVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class OutputCsvSuperivseVo implements Serializable {


    // TBL240 _ Supervise

    private String outputApartmentID;
    private String outputApartmentName;

    private String superviseNoticeType;
    private String appendixNo;
    private String documentNo;
    private String noticeDate;
    private String recipientNameApartment;
    private String recipientNameUser;
    private String sender;
    private String textMailing1;

    private String evidenceBar;
    private String evidenceNo;
    private String periodEvidence;
    private String lastNoticeDate;
    private String lastDocumentNo;
    private String textMailing2;

    private String address;
    private String apartmentName;
    private String notificationFormatName;
    private String notificationTimeLimit;
    private String contact;

    public String getOutputApartmentID() {
        return outputApartmentID;
    }

    public void setOutputApartmentID(String outputApartmentID) {
        this.outputApartmentID = outputApartmentID;
    }

    public String getOutputApartmentName() {
        return outputApartmentName;
    }

    public void setOutputApartmentName(String outputApartmentName) {
        this.outputApartmentName = outputApartmentName;
    }

    public String getSuperviseNoticeType() {
        return superviseNoticeType;
    }

    public void setSuperviseNoticeType(String superviseNoticeType) {
        this.superviseNoticeType = superviseNoticeType;
    }

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

    public String getTextMailing1() {
        return textMailing1;
    }

    public void setTextMailing1(String textMailing1) {
        this.textMailing1 = textMailing1;
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

    public String getPeriodEvidence() {
        return periodEvidence;
    }

    public void setPeriodEvidence(String periodEvidence) {
        this.periodEvidence = periodEvidence;
    }

    public String getLastNoticeDate() {
        return lastNoticeDate;
    }

    public void setLastNoticeDate(String lastNoticeDate) {
        this.lastNoticeDate = lastNoticeDate;
    }

    public String getLastDocumentNo() {
        return lastDocumentNo;
    }

    public void setLastDocumentNo(String lastDocumentNo) {
        this.lastDocumentNo = lastDocumentNo;
    }

    public String getTextMailing2() {
        return textMailing2;
    }

    public void setTextMailing2(String textMailing2) {
        this.textMailing2 = textMailing2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getNotificationFormatName() {
        return notificationFormatName;
    }

    public void setNotificationFormatName(String notificationFormatName) {
        this.notificationFormatName = notificationFormatName;
    }

    public String getNotificationTimeLimit() {
        return notificationTimeLimit;
    }

    public void setNotificationTimeLimit(String notificationTimeLimit) {
        this.notificationTimeLimit = notificationTimeLimit;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }





    public OutputCsvSuperivseVo() {
    }
}


