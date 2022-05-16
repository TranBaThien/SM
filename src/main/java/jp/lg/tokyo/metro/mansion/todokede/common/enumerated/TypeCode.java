/*
 * @(#) TypeCode.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author hntvy
 *
 */
public enum TypeCode {
    NOTIFICATION(1, "届出"),
    CHANGE_NOTIFICATION(1,"変更届出"),
    NOTIFICATION_ACCEPTANCE(3, "届出受理"),
    CHANGE_NOTIFICATION_ACCEPTANCE(4, "変更届出受理"),
    ADVISORY_NOTICE(5, "助言通知"),
    FIELD_SURVEY_NOTIFICATION(6,"現地調査通知"),
    DUNNING_NOTICE_FIRST_TIME(7, "督促通知（一回目）"),
    DUNNING_NOTICE_AFTER_THE_SECOND(8, "督促通知（二回目以降）"),
    EMAIL_NOTIFICATION(9, "届出受理メール通知"),
    CHANGE_NOTIFICATION_ACCEPTANCE_EMAIL(10, "変更届出受理メール通知"),
    ADVICE_EMAIL_NOTIFICATION(11, "助言メール通知"),
    FIELD_SURVEY_EMAIL_NOTIFICATION(12, "現地調査メール通知");
    
    private final int code;
    private final String typeCode;
    
    /**
     * @param code int
     * @param typeCode String
     */
    private TypeCode(int code, String typeCode) {
        this.code = code;
        this.typeCode = typeCode;
    }
    
    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }
    
}
