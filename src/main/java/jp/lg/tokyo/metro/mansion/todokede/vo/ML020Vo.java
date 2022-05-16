/*
 * @(#) ML020Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: Dec 24, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;


public class ML020Vo extends BaseMailVo {
    
    private String apartmentName;
    private String contactName;
    private String judgeRemarks;

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
    
    public String getJudgeRemarks() {
        return judgeRemarks;
    }
    
    public void setJudgeRemarks(String judgeRemarks) {
        this.judgeRemarks = judgeRemarks;
    }


}
