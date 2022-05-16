/*
 * @(#) ApartmentUserInfoVo.java
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

public class ApartmentUserInfoVo implements Serializable {

    //	ユーザ情報
    /**
     * ログインID
     */
    private String lblLoginid;

    /**
     * ユーザ状態
     */
    private String lblUserStatus;

    /**
     * 利用状態
     */
    private String lblValidity;

    /**
     * ログイン状態
     */
    private String lblLoginStatus;

    /**
     * 最終ログイン日時
     */
    private String lblFinalLoginDate;

    /**
     * ログイン失敗回数
     */
    private String lblLoginErrorCount;

	/**
	 * 最終パスワード更新日時
	 */
	private String lblFinalPwdDate;

	/**
	 * 更新日時
	 */
	private String updateDatetime;

	/**
	 * コンストラクタ
	 */
	public ApartmentUserInfoVo() {
		super();
	}

	/**
	 * @return the lblLoginid
	 */
	public String getLblLoginid() {
		return lblLoginid;
	}

	/**
	 * @param lblLoginid the lblLoginid to set
	 */
	public void setLblLoginid(String lblLoginid) {
		this.lblLoginid = lblLoginid;
	}

	/**
	 * @return the lblUserStatus
	 */
	public String getLblUserStatus() {
		return lblUserStatus;
	}

	/**
	 * @param lblUserStatus the lblUserStatus to set
	 */
	public void setLblUserStatus(String lblUserStatus) {
		this.lblUserStatus = lblUserStatus;
	}

	/**
	 * @return the lblValidity
	 */
	public String getLblValidity() {
		return lblValidity;
	}

	/**
	 * @param lblValidity the lblValidity to set
	 */
	public void setLblValidity(String lblValidity) {
		this.lblValidity = lblValidity;
	}

	/**
	 * @return the lblLoginStatus
	 */
	public String getLblLoginStatus() {
		return lblLoginStatus;
	}

	/**
	 * @param lblLoginStatus the lblLoginStatus to set
	 */
	public void setLblLoginStatus(String lblLoginStatus) {
		this.lblLoginStatus = lblLoginStatus;
	}

	/**
	 * @return the lblFinalLoginDate
	 */
	public String getLblFinalLoginDate() {
		return lblFinalLoginDate;
	}

	/**
	 * @param lblFinalLoginDate the lblFinalLoginDate to set
	 */
	public void setLblFinalLoginDate(String lblFinalLoginDate) {
		this.lblFinalLoginDate = lblFinalLoginDate;
	}

	/**
	 * @return the lblLoginErrorCount
	 */
	public String getLblLoginErrorCount() {
		return lblLoginErrorCount;
	}

	/**
	 * @param lblLoginErrorCount the lblLoginErrorCount to set
	 */
	public void setLblLoginErrorCount(String lblLoginErrorCount) {
		this.lblLoginErrorCount = lblLoginErrorCount;
	}

	/**
	 * @return the lblFinalPwdDate
	 */
	public String getLblFinalPwdDate() {
		return lblFinalPwdDate;
	}

	/**
	 * @param lblFinalPwdDate the lblFinalPwdDate to set
	 */
	public void setLblFinalPwdDate(String lblFinalPwdDate) {
		this.lblFinalPwdDate = lblFinalPwdDate;
	}

	/**
	 * @return the updateDatetime
	 */
	public String getUpdateDatetime() {
		return updateDatetime;
	}

	/**
	 * @param updateDatetime the lblFinalPwdDate to set
	 */
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
}
