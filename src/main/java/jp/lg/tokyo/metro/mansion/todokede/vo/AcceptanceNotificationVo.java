/*
 * @(#) AcceptanceNotificationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class AcceptanceNotificationVo implements Serializable {
    private static final long serialVersionUID = 5525L;
    private String txtAppendixNo;
    private String txtDocumentNo;
    private String calNoticeDate;
    private String lblRecipientNameApartment;
    private String lblRecipientNameUser;
    private String txtSender;
    private String lblNotificationDate;
    private String lblEvidenceBar;
    private String lstEvidenceNo;
    private String txaRemarks;
    private String txaOrrectionDetails;
    private String rdoNotificationMethod;
    private boolean btnCorrectionOn;
    private boolean chkConfirm;
    private String btnCorrectionValue;

    public String getTxtAppendixNo() {
        return txtAppendixNo;
    }

    public void setTxtAppendixNo(String txtAppendixNo) {
        this.txtAppendixNo = txtAppendixNo;
    }

    public String getTxtDocumentNo() {
        return txtDocumentNo;
    }

    public void setTxtDocumentNo(String txtDocumentNo) {
        this.txtDocumentNo = txtDocumentNo;
    }

    public String getCalNoticeDate() {
        return calNoticeDate;
    }

    public void setCalNoticeDate(String calNoticeDate) {
        this.calNoticeDate = calNoticeDate;
    }

    public String getLblRecipientNameApartment() {
        return lblRecipientNameApartment;
    }

    public void setLblRecipientNameApartment(String lblRecipientNameApartment) {
        this.lblRecipientNameApartment = lblRecipientNameApartment;
    }

    public String getLblRecipientNameUser() {
        return lblRecipientNameUser;
    }

    public void setLblRecipientNameUser(String lblRecipientNameUser) {
        this.lblRecipientNameUser = lblRecipientNameUser;
    }

    public String getTxtSender() {
        return txtSender;
    }

    public void setTxtSender(String txtSender) {
        this.txtSender = txtSender;
    }

    public String getLblNotificationDate() {
        return lblNotificationDate;
    }

    public void setLblNotificationDate(String lblNotificationDate) {
        this.lblNotificationDate = lblNotificationDate;
    }

    public String getLblEvidenceBar() {
        return lblEvidenceBar;
    }

    public void setLblEvidenceBar(String lblEvidenceBar) {
        this.lblEvidenceBar = lblEvidenceBar;
    }

    public String getLstEvidenceNo() {
        return lstEvidenceNo;
    }

    public void setLstEvidenceNo(String lstEvidenceNo) {
        this.lstEvidenceNo = lstEvidenceNo;
    }

    public String getTxaRemarks() {
        return txaRemarks;
    }

    public void setTxaRemarks(String txaRemarks) {
        this.txaRemarks = txaRemarks;
    }

    public String getTxaOrrectionDetails() {
        return txaOrrectionDetails;
    }

    public void setTxaOrrectionDetails(String txaOrrectionDetails) {
        this.txaOrrectionDetails = txaOrrectionDetails;
    }

    public String getRdoNotificationMethod() {
        return rdoNotificationMethod;
    }

    public void setRdoNotificationMethod(String rdoNotificationMethod) {
        this.rdoNotificationMethod = rdoNotificationMethod;
    }

    public boolean isBtnCorrectionOn() {
        return btnCorrectionOn;
    }

    public void setBtnCorrectionOn(boolean btnCorrectionOn) {
        this.btnCorrectionOn = btnCorrectionOn;
    }

    public boolean isChkConfirm() {
        return chkConfirm;
    }

    public void setChkConfirm(boolean chkConfirm) {
        this.chkConfirm = chkConfirm;
    }

    public String getBtnCorrectionValue() {
        return btnCorrectionValue;
    }

    public void setBtnCorrectionValue(String btnCorrectionValue) {
        this.btnCorrectionValue = btnCorrectionValue;
    }
}
