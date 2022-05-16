/*
 * @(#) ProgressRecordForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import com.opencsv.bean.CsvBindByPosition;

public class ProgressRecordForm {
    
    @CsvBindByPosition(position = 0)
    private    String apartmentId;
    
    @CsvBindByPosition(position = 1)
    private    String correspondDate;
    
    @CsvBindByPosition(position = 2)
    private    String correspondTime;
    
    @CsvBindByPosition(position = 3)
    private    String correspondType;
    
    @CsvBindByPosition(position = 4)
    private    String noticeType;
    
    @CsvBindByPosition(position = 5)
    private    String progressRecordOverview;
    
    @CsvBindByPosition(position = 6)
    private    String progressRecordDetail;

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getCorrespondDate() {
        return correspondDate;
    }

    public void setCorrespondDate(String correspondDate) {
        this.correspondDate = correspondDate;
    }

    public String getCorrespondTime() {
        return correspondTime;
    }

    public void setCorrespondTime(String correspondTime) {
        this.correspondTime = correspondTime;
    }

    public String getCorrespondType() {
        return correspondType;
    }

    public void setCorrespondType(String correspondType) {
        this.correspondType = correspondType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getProgressRecordOverview() {
        return progressRecordOverview;
    }

    public void setProgressRecordOverview(String progressRecordOverview) {
        this.progressRecordOverview = progressRecordOverview;
    }

    public String getProgressRecordDetail() {
        return progressRecordDetail;
    }

    public void setProgressRecordDetail(String progressRecordDetail) {
        this.progressRecordDetail = progressRecordDetail;
    }
    
}
