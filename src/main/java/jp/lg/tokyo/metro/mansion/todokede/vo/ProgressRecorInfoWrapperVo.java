/*
 * @(#) ProgressRecorInfoWrapperVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/24
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

/**
 * @author tqvu1
 *
 */
public class ProgressRecorInfoWrapperVo {

    private List<ProgressRecordInfoVo> progressRecordList;

    private String lblCount;

    private String lblNoInfoMessage;

    public List<ProgressRecordInfoVo> getProgressRecordList() {
        return progressRecordList;
    }

    public void setProgressRecordList(List<ProgressRecordInfoVo> progressRecordList) {
        this.progressRecordList = progressRecordList;
    }

    public String getLblCount() {
        return lblCount;
    }

    public void setLblCount(String lblCount) {
        this.lblCount = lblCount;
    }

    public String getLblNoInfoMessage() {
        return lblNoInfoMessage;
    }

    public void setLblNoInfoMessage(String lblNoInfoMessage) {
        this.lblNoInfoMessage = lblNoInfoMessage;
    }

}
