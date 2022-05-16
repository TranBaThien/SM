/*
 * @(#) ReportStatus.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 11, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author hntvy
 *
 */
public enum ReportStatus {
	UNREPORTED(1, "未届"),
	REVIEW(2, "変更届出済（審査中）"),
	ACCEPTED(3,"変更届出済（受理済）"),
	CHANGE_NOTIFICATION_COMPLETED(4,"変更届出済");
	
	private final int code;
	private final String reportStatus;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @return the reportStatus
	 */
	public String getReportStatus() {
		return reportStatus;
	}
	/**
	 * @param code
	 * @param reportStatus
	 */
	private ReportStatus(int code, String reportStatus) {
		this.code = code;
		this.reportStatus = reportStatus;
	}
	
}
