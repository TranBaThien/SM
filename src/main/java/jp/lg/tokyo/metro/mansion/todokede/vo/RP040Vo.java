/*
 * @(#) RP040Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class RP040Vo {
    private String appendixNo;
    private String documentNo;
    private String noticeDate;
    private String recipientNameApartment;
    private String recipientNameUser;
    private String sender;
    private String stamp;
    private String textMailing1;
    private String address;
    private String investImplPlanDatetime;
    private String investImplPlanDatetimeTwo;
    private String investImplNumberPeople;
    private String necessaryDocument;
    private String contact;
    private String isAm;

    public String getInvestImplPlanDatetimeTwo() {
        return investImplPlanDatetimeTwo;
    }

    public void setInvestImplPlanDatetimeTwo(String investImplPlanDatetimeTwo) {
        this.investImplPlanDatetimeTwo = investImplPlanDatetimeTwo;
    }

    public String getIsAm() {
        return isAm;
    }

    public void setIsAm(String isAm) {
        this.isAm = isAm;
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

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTextMailing1() {
        return textMailing1;
    }

    public void setTextMailing1(String textMailing1) {
        this.textMailing1 = textMailing1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInvestImplPlanDatetime() {
        return investImplPlanDatetime;
    }

    public void setInvestImplPlanDatetime(String investImplPlanDatetime) {
        this.investImplPlanDatetime = investImplPlanDatetime;
    }

    public String getInvestImplNumberPeople() {
        return investImplNumberPeople;
    }

    public void setInvestImplNumberPeople(String investImplNumberPeople) {
        this.investImplNumberPeople = investImplNumberPeople;
    }

    public String getNecessaryDocument() {
        return necessaryDocument;
    }

    public void setNecessaryDocument(String necessaryDocument) {
        this.necessaryDocument = necessaryDocument;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
