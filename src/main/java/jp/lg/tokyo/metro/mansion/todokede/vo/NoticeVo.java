/*
 * @(#) NoticeVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author hntvy
 *
 */
public class NoticeVo {
	private String corresponDate;
	private String notice;
	/**
	 * @param corresponDate
	 * @param notice
	 */
	public NoticeVo(String corresponDate, String notice) {
		this.corresponDate = corresponDate;
		this.notice = notice;
	}
	/**
	 * @return the corresponDate
	 */
	public String getCorresponDate() {
		return corresponDate;
	}
	/**
	 * @param corresponDate the corresponDate to set
	 */
	public void setCorresponDate(String corresponDate) {
		this.corresponDate = corresponDate;
	}
	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}
	/**
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}
	/**
	 * 
	 */
	public NoticeVo() {
		super();
	}
	
}
