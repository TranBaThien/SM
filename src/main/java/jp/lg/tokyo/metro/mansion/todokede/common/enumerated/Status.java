/*
 * @(#)Status.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

public enum Status {
	UNREPORTED("未届"),
	REPORTED("届出済"),
	AUTHORITY_CORRECTION("職権訂正有");

    private final String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
	
}
