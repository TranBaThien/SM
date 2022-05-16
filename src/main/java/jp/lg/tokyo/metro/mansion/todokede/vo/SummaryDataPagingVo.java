/*
 * @(#) SummaryDataPagingVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/01/31
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author tqvu1
 *
 */

public class SummaryDataPagingVo {

    private String[] apartmentIds;

    private Integer page;

    public String[] getApartmentIds() {
        return apartmentIds;
    }

    public void setApartmentIds(String[] apartmentIds) {
        this.apartmentIds = apartmentIds;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
