/*
 * @(#) DataAggregateResultTmpVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class DataAggregateResultTmpVo {

    private String apartmentId;

    private String typeCode;

    private String notificationKbn;

    private String aggregateCredit;

    public DataAggregateResultTmpVo() {
        super();
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getNotificationKbn() {
        return notificationKbn;
    }

    public void setNotificationKbn(String notificationKbn) {
        this.notificationKbn = notificationKbn;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAggregateCredit() {
        return aggregateCredit;
    }

    public void setAggregateCredit(String aggregateCredit) {
        this.aggregateCredit = aggregateCredit;
    }

}
