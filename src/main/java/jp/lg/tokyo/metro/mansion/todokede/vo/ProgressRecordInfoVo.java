/*
 * @(#) ProgressRecordInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ProgressRecordInfoVo {

    /** 対応日時 */
    private String lblCorrespondDate;
    
    /** 種別 */
    private String lblProgressRecordType;
 
    /** 経過記録詳細 */
    private String lblProgressRecordDetail;
    
    private String typeCode;

    private String relatedNumber;

    private String reportName;

    public String getLblCorrespondDate() {
        return lblCorrespondDate;
    }

    public void setLblCorrespondDate(String lblCorrespondDate) {
        this.lblCorrespondDate = lblCorrespondDate;
    }

    public String getLblProgressRecordType() {
        return lblProgressRecordType;
    }

    public void setLblProgressRecordType(String lblProgressRecordType) {
        this.lblProgressRecordType = lblProgressRecordType;
    }

    public String getLblProgressRecordDetail() {
        return lblProgressRecordDetail;
    }

    public void setLblProgressRecordDetail(String lblProgressRecordDetail) {
        this.lblProgressRecordDetail = lblProgressRecordDetail;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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

}
