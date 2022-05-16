/*
 * @(#) DataAggregateResultItemVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

public class DataAggregateResultItemVo {

    private String summaryCd;

    private String lnkSummaryItem;

    private String lblSummaryItemAll;

    private List<String> lstIdMansion;

    public DataAggregateResultItemVo() {
        super();
    }

    public String getSummaryCd() {
        return summaryCd;
    }

    public void setSummaryCd(String summaryCd) {
        this.summaryCd = summaryCd;
    }

    public String getLnkSummaryItem() {
        return lnkSummaryItem;
    }

    public void setLnkSummaryItem(String lnkSummaryItem) {
        this.lnkSummaryItem = lnkSummaryItem;
    }

    public String getLblSummaryItemAll() {
        return lblSummaryItemAll;
    }

    public void setLblSummaryItemAll(String lblSummaryItemAll) {
        this.lblSummaryItemAll = lblSummaryItemAll;
    }

    public List<String> getLstIdMansion() {
        return lstIdMansion;
    }

    public void setLstIdMansion(List<String> lstIdMansion) {
        this.lstIdMansion = lstIdMansion;
    }
}
