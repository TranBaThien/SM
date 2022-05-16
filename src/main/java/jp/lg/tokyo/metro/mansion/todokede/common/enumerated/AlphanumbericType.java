/*
 * @(#) AlphanumbericType.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/02/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author tqvu1
 *
 */

public enum AlphanumbericType {

    FOR_MAIL("1"), FOR_DATE("2"), FOR_DATE_TIME("3"), FOR_OTHER("4");

    private String type;

    AlphanumbericType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
