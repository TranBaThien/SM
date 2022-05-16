/*
 * @(#) ReportForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019-12-27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

/**
 * @author pdquang
 *
 */
public class ReportForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String reportName;
    
    private String relatedNumber;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getRelatedNumber() {
        return relatedNumber;
    }

    public void setRelatedNumber(String relatedNumber) {
        this.relatedNumber = relatedNumber;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
