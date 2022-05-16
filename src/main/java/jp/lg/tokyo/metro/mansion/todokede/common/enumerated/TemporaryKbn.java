/*
 * @(#) TemporaryKbn.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/17
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum TemporaryKbn {
    MANSION(1, "一時保存_管理組合"),
    CITY(2, "一時保存_管理組合"),
    TOKYO(3, "一時保存_都職員"),
    SYSTEM_ADMIN(4, "一時保存_システム管理者"),
    MAINTENANCE(5, "一時保存_保守業者"),
    FORMAL(6, "正式");

    private int key;
    private String value;
    TemporaryKbn(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
