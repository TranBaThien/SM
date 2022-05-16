/*
 * @(#) ML010Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: Dec 24, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;


public class ML010Vo extends BaseMailVo {

    private String apartmentName;
    private String contactName;
    private String loginUrl;
    private String loginId;
    
    public String getLoginId() {
        return loginId;
    }
    
    public void setLoginId(String loginId) {
        this.loginId = loginId;
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
    
    public String getLoginUrl() {
        return loginUrl;
    }
    
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }


}
