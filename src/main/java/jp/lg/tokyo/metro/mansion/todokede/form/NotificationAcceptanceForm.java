/*
 * @(#) NotificationAcceptanceForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HaftSizeNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;

public class NotificationAcceptanceForm implements Serializable {
    private static final long serialVersionUID = 12L;

    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("様式名")
    @Position(31)
    private String txtAppendixNo;

    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("文書番号")
    @Position(32)
    private String txtDocumentNo;

    @NotBlank
    @MaxSize(val = 10)
    @HalfsizeAlphanumeric
    @DateCustom(pattern = "yyyy/MM/dd")
    @FieldName("通知年月日")
    @Position(33)
    private String calNoticeDate;
    private String lblRecipientNameApartment;
    private String lblRecipientNameUser;

    @NotBlank
    @MaxSize(val = 20)
    @SpecialCharacters
    @FieldName("発信者名")
    @Position(34)
    private String txtSender;

    @NotBlank
    @DateCustom(pattern = "yyyy/MM/dd")
    @FieldName("届出日")
    @Position(35)
    private String lblNotificationDate;
    private String lblEvidenceBar;

    @SelectCode(code = {"CD042", "CD043"})
    @FieldName("根拠条文")
    @Position(36)
    private String lstEvidenceNo;

    @MaxSize(val = 300)
    @SpecialCharacters
    @FieldName("備考")
    @Position(37)
    private String txaRemarks;

    @MaxSize(val = 500)
    @SpecialCharacters
    @FieldName("訂正内容")
    @Position(38)
    private String txaOrrectionDetails;

    @SelectCode(code = "CD017")
    @FieldName("通知方法")
    @Position(39)
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
