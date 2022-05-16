/*
 * @(#) ML035Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ML035Vo extends BaseMailVo {

    /** 連絡先氏名 */
    private String contactName;

    /** ワンタイムパスワード有効期限 */
    private Integer passwordPeriod;

    /** ワンタイムパスワード */
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
