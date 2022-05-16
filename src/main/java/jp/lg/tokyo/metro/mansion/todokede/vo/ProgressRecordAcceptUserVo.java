/*
 * @(#) ProgressRecordAcceptUserInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

public class ProgressRecordAcceptUserVo {

    /** 対応日時 */
    private String correspondDate;

    /** 種別コード */
    private String typeCode;

    /** 通知方法 */
    private String notificationMethodCode;

    /** 職権訂正フラグ */
    private String authorityModifyFlag;

    /** 訂正内容 */
    private String modifyDetails;

    /** ユーザ種別 */
    private String userType;

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

    public String getAuthorityModifyFlag() {
        return authorityModifyFlag;
    }

    public void setAuthorityModifyFlag(String authorityModifyFlag) {
        this.authorityModifyFlag = authorityModifyFlag;
    }

    public String getModifyDetails() {
        return modifyDetails;
    }

    public void setModifyDetails(String modifyDetails) {
        this.modifyDetails = modifyDetails;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRelatedNumber() {
        return relatedNumber;
    }

    public void setRelatedNumber(String relatedNumber) {
        this.relatedNumber = relatedNumber;
    }

}
