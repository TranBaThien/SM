/*
 * @(#) ProgressRecordRegistrationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/24
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author PVHung3
 *
 */
public class ProgressRecordRegistrationVo extends BaseMansionInfoVo {
    private static final long serialVersionUID = 1L;
    
    private String progressRecordNo;
    
    private String lstCorrespondTypeCode;
    
    private String chkConfirm;
    
    private String txtProgressRecordOverview;
    
    private String calCorrespondDate;
    
    private String lstNoticeTypeCode;
    
    private String txaProgressRecordDetail;
    
    private String rdoSupportCode;
    
    private List<FileManagerInfoVo> fileInfos;
    
    private List<CodeVo> lstSupportCode;
    
    private List<CodeVo> lstResponseTypeCode;
    
    private List<CodeVo> listNoticeTypeCodes;

    private String[] selectedFiles;
    
    private String filFileFromName1;
    
    private String filFileFromName2;
    
    private String filFileFromName3;
    
    private MultipartFile filFileFrom1;
    private MultipartFile filFileFrom2;
    private MultipartFile filFileFrom3;
    
    private String fileMaxSize;
    private String fileMaxCount;
    
    
    public String getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(String fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    public String getFileMaxCount() {
        return fileMaxCount;
    }

    public void setFileMaxCount(String fileMaxCount) {
        this.fileMaxCount = fileMaxCount;
    }

    public List<CodeVo> getLstResponseTypeCode() {
        return lstResponseTypeCode;
    }

    public void setLstResponseTypeCode(List<CodeVo> lstResponseTypeCode) {
        this.lstResponseTypeCode = lstResponseTypeCode;
    }

    public List<CodeVo> getListNoticeTypeCodes() {
        return listNoticeTypeCodes;
    }

    public void setListNoticeTypeCodes(List<CodeVo> listNoticeTypeCodes) {
        this.listNoticeTypeCodes = listNoticeTypeCodes;
    }

    public MultipartFile getFilFileFrom1() {
        return filFileFrom1;
    }

    public void setFilFileFrom1(MultipartFile filFileFrom1) {
        this.filFileFrom1 = filFileFrom1;
    }

    public MultipartFile getFilFileFrom2() {
        return filFileFrom2;
    }

    public void setFilFileFrom2(MultipartFile filFileFrom2) {
        this.filFileFrom2 = filFileFrom2;
    }

    public MultipartFile getFilFileFrom3() {
        return filFileFrom3;
    }

    public void setFilFileFrom3(MultipartFile filFileFrom3) {
        this.filFileFrom3 = filFileFrom3;
    }

    public String getFilFileFromName1() {
        return filFileFromName1;
    }

    public void setFilFileFromName1(String filFileFromName1) {
        this.filFileFromName1 = filFileFromName1;
    }

    public String getFilFileFromName2() {
        return filFileFromName2;
    }

    public void setFilFileFromName2(String filFileFromName2) {
        this.filFileFromName2 = filFileFromName2;
    }

    public String getFilFileFromName3() {
        return filFileFromName3;
    }

    public void setFilFileFromName3(String filFileFromName3) {
        this.filFileFromName3 = filFileFromName3;
    }

    public String[] getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(String[] selectedFiles) {
        this.selectedFiles = selectedFiles;
    }

    public String getLstCorrespondTypeCode() {
        return lstCorrespondTypeCode;
    }

    public void setLstCorrespondTypeCode(String lstCorrespondTypeCode) {
        this.lstCorrespondTypeCode = lstCorrespondTypeCode;
    }

    public String getChkConfirm() {
        return chkConfirm;
    }

    public void setChkConfirm(String chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    public String getTxtProgressRecordOverview() {
        return txtProgressRecordOverview;
    }

    public void setTxtProgressRecordOverview(String txtProgressRecordOverview) {
        this.txtProgressRecordOverview = txtProgressRecordOverview;
    }

    public String getCalCorrespondDate() {
        return calCorrespondDate;
    }

    public void setCalCorrespondDate(String calCorrespondDate) {
        this.calCorrespondDate = calCorrespondDate;
    }

    public String getLstNoticeTypeCode() {
        return lstNoticeTypeCode;
    }

    public void setLstNoticeTypeCode(String lstNoticeTypeCode) {
        this.lstNoticeTypeCode = lstNoticeTypeCode;
    }

    public String getTxaProgressRecordDetail() {
        return txaProgressRecordDetail;
    }

    public void setTxaProgressRecordDetail(String txaProgressRecordDetail) {
        this.txaProgressRecordDetail = txaProgressRecordDetail;
    }

    public String getRdoSupportCode() {
        return rdoSupportCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setRdoSupportCode(String rdoSupportCode) {
        this.rdoSupportCode = rdoSupportCode;
    }

    public List<FileManagerInfoVo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<FileManagerInfoVo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public String getProgressRecordNo() {
        return progressRecordNo;
    }

    public void setProgressRecordNo(String progressRecordNo) {
        this.progressRecordNo = progressRecordNo;
    }

    public List<CodeVo> getLstSupportCode() {
        return lstSupportCode;
    }

    public void setLstSupportCode(List<CodeVo> lstSupportCode) {
        this.lstSupportCode = lstSupportCode;
    }
    
}
