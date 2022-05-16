/*
 * @(#) ProgressRecordDetailsVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PDQuang
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

/**
 * @author PDQuang
 *
 */
public class ProgressRecordDetailsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String correspondDate;

    private String typeCode;

    private String progressRecordDetail;

    private String progressRecordNo;
    
    private String correspondTypeCode;

    private String btnDisplay;

    private String relatedNumber;

    private String reportName;

    private String progressRecordOverview;

    private String supportCode;
    
    private String noticeTypeCode;

    private String authorityModifyFlag;

    private String userType;

    public ProgressRecordDetailsVo() {
    }

    public String getSupportCode() {
        return supportCode;
    }

    public void setSupportCode(String supportCode) {
        this.supportCode = supportCode;
    }

    public String getNoticeTypeCode() {
        return noticeTypeCode;
    }

    public void setNoticeTypeCode(String noticeTypeCode) {
        this.noticeTypeCode = noticeTypeCode;
    }

    public String getCorrespondTypeCode() {
        return correspondTypeCode;
    }

    public void setCorrespondTypeCode(String correspondTypeCode) {
        this.correspondTypeCode = correspondTypeCode;
    }

    public String getAuthorityModifyFlag() {
        return authorityModifyFlag;
    }

    public void setAuthorityModifyFlag(String authotityModifyFlag) {
        this.authorityModifyFlag = authotityModifyFlag;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProgressRecordOverview() {
        return progressRecordOverview;
    }

    public void setProgressRecordOverview(String progressRecordOverview) {
        this.progressRecordOverview = progressRecordOverview;
    }

    public String getRelatedNumber() {
        return relatedNumber;
    }

    public void setRelatedNumber(String relatedNumber) {
        this.relatedNumber = relatedNumber;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getProgressRecordNo() {
        return progressRecordNo;
    }

    public void setProgressRecordNo(String progressRecordNo) {
        this.progressRecordNo = progressRecordNo;
    }

    public String getBtnDisplay() {
        return btnDisplay;
    }

    public void setBtnDisplay(String btnDisplay) {
        this.btnDisplay = btnDisplay;
    }

    public String getCorrespondDate() {
        return correspondDate;
    }

    public void setCorrespondDate(String correspondDate) {
        this.correspondDate = correspondDate;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getProgressRecordDetail() {
        return progressRecordDetail;
    }

    public void setProgressRecordDetail(String progressRecordDetail) {
        this.progressRecordDetail = progressRecordDetail;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
