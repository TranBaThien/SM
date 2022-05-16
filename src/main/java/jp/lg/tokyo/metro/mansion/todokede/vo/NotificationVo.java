/*
 * @(#) NotificationVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hntvy
 *
 */
public class NotificationVo {
	private String notificationStatus;
	private List<NoticeVo> noticeVos;
	/**
	 * @return the notificationStatus
	 */
	public String getNotificationStatus() {
		return notificationStatus;
	}
	/**
	 * @param notificationStatus the notificationStatus to set
	 */
	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	/**
	 * @return the noticeVos
	 */
	public List<NoticeVo> getNoticeVos() {
		return noticeVos;
	}
	/**
	 * @param noticeVos the noticeVos to set
	 */
	public void setNoticeVos(List<NoticeVo> noticeVos) {
		this.noticeVos = noticeVos;
	}
	
	/**
	 * @param notificationStatus
	 * @param noticeVos
	 */
	public NotificationVo(String notificationStatus, List<NoticeVo> noticeVos) {
		super();
		this.notificationStatus = notificationStatus;
		this.noticeVos = noticeVos;
	}
	/**
	 * 
	 */
	public NotificationVo() {
		
	}

}