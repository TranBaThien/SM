/*
 * @(#) ApartmentBtnStatusVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nndo
 * Create Date: 16/12/2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class ApartmentBtnStatusVo implements Serializable {

    /**
     * マンションID
     */
    private String apartmentId;

    /**
     * 最新届出番号
     */
    private String newestNotificationNo;
    // ボタンイベント
    /**
     * 届出
     */
    private boolean btnNotificationActive;

    /**
     * 変更届出
     */
    private boolean btnNotificationChangeActive;

    /**
     * 助言通知
     */
	private boolean btnAdviceActive;

	private boolean btnAdviceShow;

    /**
     * 現地調査通知
     */
    private boolean btnInvestigationShow;

    /**
     * 督促通知
     */
    private boolean btnDemandActive;

    /**
     * ログインID有効化
     */
    private boolean btnLoginIdActivationActive;

    /**
     * 利用再開
     */
    private boolean btnResumeActive;

    /**
     * 利用停止
     */
    private boolean btnSuspensionActive;

    /**
     * ログインID再発行
     */
    private boolean btnLoginIdReissueShow;
    

	/**
	 * コンストラクタ
	 */
	public ApartmentBtnStatusVo() {
		super();
	}

	/**
	 * @return the apartmentId
	 */
	public String getApartmentId() {
		return apartmentId;
	}

	/**
	 * @param apartmentId the apartmentId to set
	 */
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}

	/**
	 * @return the newestNotificationNo
	 */
	public String getNewestNotificationNo() {
		return newestNotificationNo;
	}

	/**
	 * @param newestNotificationNo the newestNotificationNo to set
	 */
	public void setNewestNotificationNo(String newestNotificationNo) {
		this.newestNotificationNo = newestNotificationNo;
	}

	/**
	 * @return the btnNotificationActive
	 */
	public boolean isBtnNotificationActive() {
		return btnNotificationActive;
	}

	/**
	 * @param btnNotificationActive the btnNotificationActive to set
	 */
	public void setBtnNotificationActive(boolean btnNotificationActive) {
		this.btnNotificationActive = btnNotificationActive;
	}

	/**
	 * @return the btnNotificationChangeActive
	 */
	public boolean isBtnNotificationChangeActive() {
		return btnNotificationChangeActive;
	}

	/**
	 * @param btnNotificationChangeActive the btnNotificationChangeActive to set
	 */
	public void setBtnNotificationChangeActive(boolean btnNotificationChangeActive) {
		this.btnNotificationChangeActive = btnNotificationChangeActive;
	}

	/**
	 * @return the btnAdviceActive
	 */
	public boolean isBtnAdviceActive() {
		return btnAdviceActive;
	}

	/**
	 * @param btnAdviceActive the btnAdviceActive to set
	 */
	public void setBtnAdviceActive(boolean btnAdviceActive) {
		this.btnAdviceActive = btnAdviceActive;
	}

	/**
	 * @return the btnInvestigationActive
	 */
	public boolean isBtnInvestigationShow() {
		return btnInvestigationShow;
	}

	/**
	 * @param btnInvestigationShow the btnInvestigationActive to set
	 */
	public void setBtnInvestigationShow(boolean btnInvestigationShow) {
		this.btnInvestigationShow = btnInvestigationShow;
	}

	/**
	 * @return the btnDemandActive
	 */
	public boolean isBtnDemandActive() {
		return btnDemandActive;
	}

	/**
	 * @param btnDemandActive the btnDemandActive to set
	 */
	public void setBtnDemandActive(boolean btnDemandActive) {
		this.btnDemandActive = btnDemandActive;
	}

	/**
	 * @return the btnLoginIdActivationActive
	 */
	public boolean isBtnLoginIdActivationActive() {
		return btnLoginIdActivationActive;
	}

	/**
	 * @param btnLoginIdActivationActive the btnLoginIdActivationActive to set
	 */
	public void setBtnLoginIdActivationActive(boolean btnLoginIdActivationActive) {
		this.btnLoginIdActivationActive = btnLoginIdActivationActive;
	}

	/**
	 * @return the btnResumeActive
	 */
	public boolean isBtnResumeActive() {
		return btnResumeActive;
	}

	/**
	 * @param btnResumeActive the btnResumeActive to set
	 */
	public void setBtnResumeActive(boolean btnResumeActive) {
		this.btnResumeActive = btnResumeActive;
	}

	/**
	 * @return the btnSuspensionActive
	 */
	public boolean isBtnSuspensionActive() {
		return btnSuspensionActive;
	}

	/**
	 * @param btnSuspensionActive the btnSuspensionActive to set
	 */
	public void setBtnSuspensionActive(boolean btnSuspensionActive) {
		this.btnSuspensionActive = btnSuspensionActive;
	}

	/**
	 * @return the btnLoginIdReissueShow
	 */
	public boolean isBtnLoginIdReissueShow() {
		return btnLoginIdReissueShow;
	}

	/**
	 * @param btnLoginIdReissueShow the btnLoginIdReissueActive to set
	 */
	public void setBtnLoginIdReissueShow(boolean btnLoginIdReissueShow) {
		this.btnLoginIdReissueShow = btnLoginIdReissueShow;
	}

	/**
	 * @return the btnAdviceShow
	 */
	public boolean isBtnAdviceShow() {
		return btnAdviceShow;
	}

	/**
	 * @param btnAdviceShow the btnLoginIdReissueActive to set
	 */
	public void setBtnAdviceShow(boolean btnAdviceShow) {
		this.btnAdviceShow = btnAdviceShow;
	}
}
