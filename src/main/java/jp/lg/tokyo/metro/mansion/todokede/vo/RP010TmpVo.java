/*
 * @(#) RP010TmpVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/18
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;

/**
 * @author pdquang
 *
 */
public class RP010TmpVo implements Serializable {
    
    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private TBL200Entity tbl200Entity;
    
    private LocalDateTime acceptTime;
    
    private String receiptPersonInCharge;
    
    private String receiptNote;
    
    private String cityName;

    public RP010TmpVo() {
    }

    /**
     * @param tbl200Entity TBL200Entity
     * @param acceptTime LocalDateTime
     * @param receiptPersonInCharge String
     * @param receiptNote String
     * @param cityName String
     */
    public RP010TmpVo(TBL200Entity tbl200Entity, LocalDateTime acceptTime, String receiptPersonInCharge, String receiptNote,
                      String cityName) {
        this.tbl200Entity = tbl200Entity;
        this.acceptTime = acceptTime;
        this.receiptPersonInCharge = receiptPersonInCharge;
        this.receiptNote = receiptNote;
        this.cityName = cityName;
    }

    public TBL200Entity getTbl200Entity() {
        return tbl200Entity;
    }

    public void setTbl200Entity(TBL200Entity tbl200Entity) {
        this.tbl200Entity = tbl200Entity;
    }

    public LocalDateTime getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getReceiptPersonInCharge() {
        return receiptPersonInCharge;
    }

    public void setReceiptPersonInCharge(String receiptPersonInCharge) {
        this.receiptPersonInCharge = receiptPersonInCharge;
    }

    public String getReceiptNote() {
        return receiptNote;
    }

    public void setReceiptNote(String receiptNote) {
        this.receiptNote = receiptNote;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
