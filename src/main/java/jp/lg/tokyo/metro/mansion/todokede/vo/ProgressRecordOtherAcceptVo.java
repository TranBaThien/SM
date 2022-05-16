/*
 * @(#) ProgressRecordOtherAcceptInforVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/19
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ProgressRecordOtherAcceptVo {

    /** 対応日時 */
    private String correspondDate;

    /** 種別コード */
    private String typeCode;

    /** 通知方法 */
    private String notificationMethodCode;

    /** 関連番号 */
    private String relatedNumber;

    public String getCorrespondDate() {
        return correspondDate;
    }

    public void setCorrespondDate(String correspondDate) {
        this.correspondDate = correspondDate;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getNotificationMethodCode() {
        return notificationMethodCode;
    }

    public void setNotificationMethodCode(String notificationMethodCode) {
        this.notificationMethodCode = notificationMethodCode;
    }

    public String getRelatedNumber() {
        return relatedNumber;
    }

    public void setRelatedNumber(String relatedNumber) {
        this.relatedNumber = relatedNumber;
    }

}
