/*
 * @(#) ApartmentStatusInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nndo
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class ApartmentStatusInfoVo implements Serializable {

    //	ステータス情報
    /**
     * 届出状況
     */
    private String lblNotificationStatus;

    /**
     * 受理状況
     */
    private String lblAcceptedStatus;

    /**
     * 都支援対象
     */
    private String lblSupportStatus;

    /**
     * 督促通知
     */
    private String lblUrgeStatus;

    /**
     * 督促通知
     */
    private String lblAdviceStatus;

    /**
     * 現地調査通知
     */
    private String lblSurveyStatus;

    
	/**
	 * コンストラクタ
	 */
	public ApartmentStatusInfoVo() {
		super();
	}

	/**
	 * @return the lblNotificationStatus
	 */
	public String getLblNotificationStatus() {
		return lblNotificationStatus;
	}

	/**
	 * @param lblNotificationStatus the lblNotificationStatus to set
	 */
	public void setLblNotificationStatus(String lblNotificationStatus) {
		this.lblNotificationStatus = lblNotificationStatus;
	}

	/**
	 * @return the lblAcceptedStatus
	 */
	public String getLblAcceptedStatus() {
		return lblAcceptedStatus;
	}

	/**
	 * @param lblAcceptedStatus the lblAcceptedStatus to set
	 */
	public void setLblAcceptedStatus(String lblAcceptedStatus) {
		this.lblAcceptedStatus = lblAcceptedStatus;
	}

	/**
	 * @return the lblSupportStatus
	 */
	public String getLblSupportStatus() {
		return lblSupportStatus;
	}

	/**
	 * @param lblSupportStatus the lblSupportStatus to set
	 */
	public void setLblSupportStatus(String lblSupportStatus) {
		this.lblSupportStatus = lblSupportStatus;
	}

	/**
	 * @return the lblUrgeStatus
	 */
	public String getLblUrgeStatus() {
		return lblUrgeStatus;
	}

	/**
	 * @param lblUrgeStatus the lblUrgeStatus to set
	 */
	public void setLblUrgeStatus(String lblUrgeStatus) {
		this.lblUrgeStatus = lblUrgeStatus;
	}

	/**
	 * @return the lblAdviceStatus
	 */
	public String getLblAdviceStatus() {
		return lblAdviceStatus;
	}

	/**
	 * @param lblAdviceStatus the lblAdviceStatus to set
	 */
	public void setLblAdviceStatus(String lblAdviceStatus) {
		this.lblAdviceStatus = lblAdviceStatus;
	}

	/**
	 * @return the lblSurveyStatus
	 */
	public String getLblSurveyStatus() {
		return lblSurveyStatus;
	}

	/**
	 * @param lblSurveyStatus the lblSurveyStatus to set
	 */
	public void setLblSurveyStatus(String lblSurveyStatus) {
		this.lblSurveyStatus = lblSurveyStatus;
	}


}
