/*
 * @(#) BaseMailVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/04
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

public class BaseMailVo {

    /** メールテンプレート */
    private String mailTemplate;

    /** メール件名 */
    private String mailSubject;

    /** メール送信元 */
    private String mailSendFrom;

    /** メール返信先 */
    private String mailReplyTo;

    /** 宛先アドレス */
    private String contactMailAddress;

    /** 問合せ窓口_区市町村 */
    private String cityName;

    /** 問合せ窓口_部署 */
    private String windowBelong;

    /** 問合せ窓口_電話番号 */
    private String windowTelNo;

    /** 問合せ窓口_FAX番号 */
    private String windowFaxNo;

    /** 問合せ窓口_メールアドレス */
    private String windowMailAddress;

    public String getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(String mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailSendFrom() {
        return mailSendFrom;
    }

    public void setMailSendFrom(String mailSendFrom) {
        this.mailSendFrom = mailSendFrom;
    }

    public String getMailReplyTo() {
        return mailReplyTo;
    }

    public void setMailReplyTo(String mailReplyTo) {
        this.mailReplyTo = mailReplyTo;
    }

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWindowBelong() {
        return windowBelong;
    }

    public void setWindowBelong(String windowBelong) {
        this.windowBelong = windowBelong;
    }

    public String getWindowTelNo() {
        return windowTelNo;
    }

    public void setWindowTelNo(String windowTelNo) {
        this.windowTelNo = windowTelNo;
    }

    public String getWindowFaxNo() {
        return windowFaxNo;
    }

    public void setWindowFaxNo(String windowFaxNo) {
        this.windowFaxNo = windowFaxNo;
    }

    public String getWindowMailAddress() {
        return windowMailAddress;
    }

    public void setWindowMailAddress(String windowMailAddress) {
        this.windowMailAddress = windowMailAddress;
    }

}
