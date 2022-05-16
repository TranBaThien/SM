/*
 * @(#) ReissuePasswordAfterSaveVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/29
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ReissuePasswordAfterSaveVo {

    /** 区市町村コード */
    private String cityCode;

    /** 宛先アドレス */
    private String contactMailAddress;

    /** マンション名 */
    private String apartmentName;

    /** 連絡先氏名 */
    private String contactName;

    /** ワンタイムパスワード有効期限 */
    private Integer passwordPeriod;

    /** ログインURL */
    private String loginUrl;

    /** ワンタイムパスワード */
    private String password;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getPasswordPeriod() {
        return passwordPeriod;
    }

    public void setPasswordPeriod(Integer passwordPeriod) {
        this.passwordPeriod = passwordPeriod;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
