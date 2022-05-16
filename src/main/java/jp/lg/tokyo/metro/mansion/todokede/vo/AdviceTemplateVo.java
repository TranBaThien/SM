/*
 * @(#) AdviceTemplateVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 29, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

/**
 * @author nvlong1
 *
 */
public class AdviceTemplateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String adviceTemplateDetail;

    private String adviceTemplateOverview;

    public AdviceTemplateVo() {
        super();
    }

    /**
     * @param adviceTemplateDetail String
     * @param adviceTemplateOverview String
     */
    public AdviceTemplateVo(String adviceTemplateDetail, String adviceTemplateOverview) {
        super();
        this.adviceTemplateDetail = adviceTemplateDetail;
        this.adviceTemplateOverview = adviceTemplateOverview;
    }

    public String getAdviceTemplateDetail() {
        return adviceTemplateDetail;
    }

    public void setAdviceTemplateDetail(String adviceTemplateDetail) {
        this.adviceTemplateDetail = adviceTemplateDetail;
    }

    public String getAdviceTemplateOverview() {
        return adviceTemplateOverview;
    }

    public void setAdviceTemplateOverview(String adviceTemplateOverview) {
        this.adviceTemplateOverview = adviceTemplateOverview;
    }

}
