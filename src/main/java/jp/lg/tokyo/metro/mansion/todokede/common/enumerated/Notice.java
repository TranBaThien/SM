/*
 * @(#) Notice.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 16, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author hntvy
 *
 */
public enum Notice {
	ACCEPTANCE_NOTICE("受理通知があります。経過記録から確認してください。"),
	ADVISORY_NOTICE("助言通知があります。経過記録から確認してください。"),
	FIELD_SURVEY_NOTICE("現地調査通知があります。経過記録から確認してください。");

	private String notice;

	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @param notice
	 */
	private Notice(String notice) {
		this.notice = notice;
	}

	
	
}
