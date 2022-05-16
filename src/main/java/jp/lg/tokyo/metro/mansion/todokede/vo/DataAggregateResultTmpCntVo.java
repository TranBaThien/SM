/*
 * @(#) DataAggregateResultTmpCntVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class DataAggregateResultTmpCntVo {

    private String cnt;

    private String aggregateCredit;

    public DataAggregateResultTmpCntVo() {
        super();
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getAggregateCredit() {
        return aggregateCredit;
    }

    public void setAggregateCredit(String aggregateCredit) {
        this.aggregateCredit = aggregateCredit;
    }
}
