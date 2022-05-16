/*
 * @(#) ZAA0150ErrorVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

/**
 * @author nbvhoang
 *
 */
public class ZAA0150ErrorVo implements Serializable {

    private String rowNum;
    private String messageId;
    private String apartmentName;
    private String messageContent;
    
    public String getRowNum() {
        return rowNum;
    }
    
    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getApartmentName() {
        return apartmentName;
    }
    
    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
    
    public String getMessageContent() {
        return messageContent;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    /**
     * @param messageId String
     * @param apartmentName String
     * @param messageContent String
     */
    public ZAA0150ErrorVo(String messageId, String apartmentName, String messageContent) {
        this.messageId = messageId;
        this.apartmentName = apartmentName;
        this.messageContent = messageContent;
    }
    
    /**
     * @param rowNum String
     * @param messageId String
     * @param apartmentName String
     * @param messageContent String
     */
    public ZAA0150ErrorVo(String rowNum, String messageId, String apartmentName, String messageContent) {
        this.rowNum = rowNum;
        this.messageId = messageId;
        this.apartmentName = apartmentName;
        this.messageContent = messageContent;
    }

}
