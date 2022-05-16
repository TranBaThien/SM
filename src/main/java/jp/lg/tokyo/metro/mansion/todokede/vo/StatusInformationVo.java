/*
 * @(#) StatusInformationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author PDQuang
 *
 */
public class StatusInformationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reportStatus;

    private String acceptedStatus;

    private String urgeStatus;

    private String supportCode;

    private String countRecord;

    private List<String> listProgressRecordNo;

    public List<String> getListProgressRecordNo() {
        return listProgressRecordNo;
    }

    public void setListProgressRecordNo(List<String> listProgressRecordNo) {
        this.listProgressRecordNo = listProgressRecordNo;
    }

    public String getCountRecord() {
        return countRecord;
    }

    public void setCountRecord(String countRecord) {
        this.countRecord = countRecord;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(String acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }

    public String getUrgeStatus() {
        return urgeStatus;
    }

    public void setUrgeStatus(String urgeStatus) {
        this.urgeStatus = urgeStatus;
    }

    public String getSupportCode() {
        return supportCode;
    }

    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
