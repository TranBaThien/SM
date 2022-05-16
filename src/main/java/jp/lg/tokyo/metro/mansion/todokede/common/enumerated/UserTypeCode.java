/*
 * @(#)FileName.java 2019/MM/dd
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/MM/dd
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

import java.util.Arrays;

public enum UserTypeCode {
	NONE(0, ""),
	TOKYO_STAFF(1, "都職員"),
	CITY(2, "区市町村"),
	SYSTEM_ADMIN(3, "システム管理者"),
	MAINTENANCER(4, "保守業者"),
	MANSION(5, "管理組合");
	
	private final int code;
    private final String userTypeName;
    
	UserTypeCode(int code, String userTypeName) {
		this.code = code;
		this.userTypeName = userTypeName;
	}

	public int getCode() {
		return code;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public static UserTypeCode parseFrom(int code) {
		return Arrays.stream(UserTypeCode.values()).filter(type -> type.getCode() == code).findFirst().orElse(null);
	}

}
