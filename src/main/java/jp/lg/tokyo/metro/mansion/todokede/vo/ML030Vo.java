/*
 * @(#) ML030Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ML030Vo extends BaseMailVo {

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
