/*
 * @(#) ReissuePasswordForm.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.form;

import javax.validation.constraints.NotBlank;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;

public class ReissuePasswordForm {

    @NotBlank
    @Position(1)
    @FieldName("ログインID")
    /** ログインID */
    private String txtLoginId;

    @NotBlank
    @Position(2)
    @FieldName("メールアドレス")
    /** メールアドレス */
    private String txtMail;

    /** パズル */
    private String catpchaText;

    private String previousUrl;

    /** 期限分 */
    private String lblTime;

    public String getTxtLoginId() {
        return txtLoginId;
    }

    public void setTxtLoginId(String txtLoginId) {
        this.txtLoginId = txtLoginId;
    }

    public String getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(String txtMail) {
        this.txtMail = txtMail;
    }

    public String getCatpchaText() {
        return catpchaText;
    }

    public void setCatpchaText(String catpchaText) {
        this.catpchaText = catpchaText;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public String getLblTime() {
        return lblTime;
    }

    public void setLblTime(String lblTime) {
        this.lblTime = lblTime;
    }

}
