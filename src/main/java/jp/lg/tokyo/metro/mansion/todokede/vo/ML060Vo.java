/*
 * @(#) ML060Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ML060Vo extends BaseMailVo{
    private String apartmentName;
    private String contactName;
    private String loginUrl;

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
    public String getＬoginUrl() {
        return loginUrl;
    }
    public void setＬoginUrl(String ｌoginUrl) {
        this.loginUrl = ｌoginUrl;
    }
}
