/*
 * @(#) LevelMessage.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

/**
 * @author PVHung3
 *
 */
public class LevelMessage {
	private int level;
	private String message;

	public LevelMessage(int level) {
		super();
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
