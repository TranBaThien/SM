/*
 * @(#) DataAggregateResultVo.java
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

public class DataAggregateResultVo {

    private String aggregateCredit;

    private String aggregateCreditDsp;

    private List<DataAggregateResultItemVo> lstSummaryItem;

    public DataAggregateResultVo() {
        super();
    }

    public String getAggregateCredit() {
        return aggregateCredit;
    }

    public void setAggregateCredit(String aggregateCredit) {
        this.aggregateCredit = aggregateCredit;
    }

    public List<DataAggregateResultItemVo> getLstSummaryItem() {
        return lstSummaryItem;
    }

    public void setLstSummaryItem(List<DataAggregateResultItemVo> lstSummaryItem) {
        this.lstSummaryItem = lstSummaryItem;
    }

    public String getAggregateCreditDsp() {
        return aggregateCreditDsp;
    }

    public void setAggregateCreditDsp(String aggregateCreditDsp) {
        this.aggregateCreditDsp = aggregateCreditDsp;
    }

}
