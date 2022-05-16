/*
 * @(#) PasswordChangeGuide.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/20
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

import java.util.Arrays;

public enum PasswordChangeGuide {
    INITIAL_LOGIN_OR_ISSUANCE (1, "初回ログイン・パスワード初期化（パスワード再発行後）"),
    EXPIRED (2, "パスワード有効期限切れ（定期更新）"),
    FROM_MENU (3, "メニューから");

    private int code;
    private String description;

    PasswordChangeGuide(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static PasswordChangeGuide parseFrom(int code) {
        return Arrays.stream(PasswordChangeGuide.values()).filter(type -> type.getCode() == code).findFirst().orElse(null);
    }

    public static PasswordChangeGuide parseFrom(String codeString) {
        return Arrays.stream(PasswordChangeGuide.values()).filter(type -> String.valueOf(type.getCode()).equals(codeString)).findFirst().orElse(null);
    }
}
