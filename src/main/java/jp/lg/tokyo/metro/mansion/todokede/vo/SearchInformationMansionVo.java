/*
 * @(#) SearchInformationMansionVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * The Class SearchInformationMansionVo.
 */
public class SearchInformationMansionVo implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user login. */
    private String userLogin;

    private String previousScreen;

    private String action;

    /** マンションID. */
    @Size(max = 10)
    @FieldName("マンションID")
    private String txtApartmentId;

    /** マンション名. */
    @Size(max = 50)
    @FieldName("マンション名")
    private String txtApartmentName;

    /** マンション名フリガナ. */
    @Size(max = 100)
    @FieldName("ンション名フリガナ")
    private String txtApartmentNamePhonetic;

    /** マンション住所2. */
    @Size(max = 100)
    @FieldName("マンション住所2")
    private String txtApartmentAddress2;

    /** 受付番号. */
    @Size(max = 8)
    @FieldName("受付番号")
    private String txtReceptNum;

    /** 届出状況. */
    private String rdoNotificationStatus;

    /** 受理状況. */
    private String rdoAcceptStatus;

    /** 助言状況. */
    private String rdoAdviceStatus;

    /** 督促通知状況. */
    private String rdoSuperviseStatus;

    /** 届出日 - 範囲開始. */
    @Size(max = 10)
    @FieldName("届出日 - 範囲開始")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String calNotificationDateFrom;

    /** 届出日 - 範囲終了. */
    @Size(max = 10)
    @FieldName("届出日 - 範囲終了")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String calNotificationDateTo;

    /** 新築年月日 - 範囲開始. */
    @Size(max = 10)
    @FieldName("新築年月日 - 範囲開始")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String calBuiltDateFrom;

    /** 新築年月日 - 範囲終了. */
    @Size(max = 10)
    @FieldName("新築年月日 - 範囲終了")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private String calBuiltDateTo;

    /** 戸数 - 範囲開始. */
    @Size(max = 6)
    @FieldName("戸数 - 範囲開始")
    private String txtHouseNumberFrom;

    /** 戸数 - 範囲終了. */
    @Size(max = 6)
    @FieldName("戸数 - 範囲終了")
    private String txtHouseNumberTo;

    /** 階数 - 範囲開始. */
    @Size(max = 2)
    @FieldName("階数 - 範囲開始")
    private String txtFloorNumberFrom;

    /** 階数 - 範囲終了. */
    @Size(max = 2)
    @FieldName("階数 - 範囲終了")
    private String txtFloorNumberTo;

    /** 建物の滅失  */
    private boolean chkApartmentLost;

    /** List 受理状況 */
    private List<CodeVo> lstAcceptStatus;

    /** The sort parameter. */
    private String sortParameter;

    /** The lst sort parameter. */
    private List<String> lstSortParameter;

    /** List 届出状況 */
    private List<CodeVo> lstNotificationStatus;

    /** List 助言状況. */
    private List<CodeVo> lstAdviceStatus;

    /** List 督促通知状況 */
    private List<CodeVo> lstSuperViseStatus;

    /** The result search vo list. */
    private List<ResultSearchVo> resultSearchVoList;

    private Integer page;

    private Integer size;
    
    private String buttonId;
    
    public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Checks if is chk apartment lost.
     *
     * @return true, if is chk apartment lost
     */
    public boolean isChkApartmentLost() {
        return chkApartmentLost;
    }

    /**
     * Sets the chk apartment lost.
     *
     * @param chkAparmentLost the new chk apartment lost
     */
    public void setChkApartmentLost(boolean chkAparmentLost) {
        this.chkApartmentLost = chkAparmentLost;
    }

    /**
     * Gets the sort parameter.
     *
     * @return the sort parameter
     */
    public String getSortParameter() {
        return sortParameter;
    }

    /**
     * Sets the sort parameter.
     *
     * @param sortParameter the new sort parameter
     */
    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    /**
     * Gets the lst sort parameter.
     *
     * @return the lst sort parameter
     */
    public List<String> getLstSortParameter() {
        return lstSortParameter;
    }

    /**
     * Sets the lst sort parameter.
     *
     * @param lstSortParameter the new lst sort parameter
     */
    public void setLstSortParameter(List<String> lstSortParameter) {
        this.lstSortParameter = lstSortParameter;
    }

    /**
     * Gets the lst accept status.
     *
     * @return the lst accept status
     */
    public List<CodeVo> getLstAcceptStatus() {
        return lstAcceptStatus;
    }

    /**
     * Sets the lst accept status.
     *
     * @param lstAcceptStatus the new lst accept status
     */
    public void setLstAcceptStatus(List<CodeVo> lstAcceptStatus) {
        this.lstAcceptStatus = lstAcceptStatus;
    }

    /**
     * Gets the lst notification status.
     *
     * @return the lst notification status
     */
    public List<CodeVo> getLstNotificationStatus() {
        return lstNotificationStatus;
    }

    /**
     * Sets the lst notification status.
     *
     * @param lstNotificationStatus the new lst notification status
     */
    public void setLstNotificationStatus(List<CodeVo> lstNotificationStatus) {
        this.lstNotificationStatus = lstNotificationStatus;
    }

    /**
     * Gets the lst advice status.
     *
     * @return the lst advice status
     */
    public List<CodeVo> getLstAdviceStatus() {
        return lstAdviceStatus;
    }

    /**
     * Sets the lst advice status.
     *
     * @param lstAdviceStatus the new lst advice status
     */
    public void setLstAdviceStatus(List<CodeVo> lstAdviceStatus) {
        this.lstAdviceStatus = lstAdviceStatus;
    }

    /**
     * Gets the lst super vise status.
     *
     * @return the lst super vise status
     */
    public List<CodeVo> getLstSuperViseStatus() {
        return lstSuperViseStatus;
    }

    /**
     * Sets the lst super vise status.
     *
     * @param lstSuperViseStatus the new lst super vise status
     */
    public void setLstSuperViseStatus(List<CodeVo> lstSuperViseStatus) {
        this.lstSuperViseStatus = lstSuperViseStatus;
    }

    /**
     * Gets the result search vo list.
     *
     * @return the result search vo list
     */
    public List<ResultSearchVo> getResultSearchVoList() {
        return resultSearchVoList;
    }

    /**
     * Sets the result search vo list.
     *
     * @param resultSearchVoList the new result search vo list
     */
    public void setResultSearchVoList(List<ResultSearchVo> resultSearchVoList) {
        this.resultSearchVoList = resultSearchVoList;
    }

    /**
     * Gets the txt apartment id.
     *
     * @return the txtApartmentId
     */
    public String getTxtApartmentId() {
        return txtApartmentId;
    }

    /**
     * Sets the txt apartment id.
     *
     * @param txtApartmentId the txtApartmentId to set
     */
    public void setTxtApartmentId(String txtApartmentId) {
        this.txtApartmentId = txtApartmentId;
    }

    /**
     * Gets the txt apartment name.
     *
     * @return the txtApartmentName
     */
    public String getTxtApartmentName() {
        return txtApartmentName;
    }

    /**
     * Sets the txt apartment name.
     *
     * @param txtApartmentName the txtApartmentName to set
     */
    public void setTxtApartmentName(String txtApartmentName) {
        this.txtApartmentName = txtApartmentName;
    }

    /**
     * Gets the txt apartment name phonetic.
     *
     * @return the txtApartmentNamePhonetic
     */
    public String getTxtApartmentNamePhonetic() {
        return txtApartmentNamePhonetic;
    }

    /**
     * Sets the txt apartment name phonetic.
     *
     * @param txtApartmentNamePhonetic the txtApartmentNamePhonetic to set
     */
    public void setTxtApartmentNamePhonetic(String txtApartmentNamePhonetic) {
        this.txtApartmentNamePhonetic = txtApartmentNamePhonetic;
    }

    /**
     * Gets the txt apartment address 2.
     *
     * @return the txtApartmentAddress2
     */
    public String getTxtApartmentAddress2() {
        return txtApartmentAddress2;
    }

    /**
     * Sets the txt apartment address 2.
     *
     * @param txtApartmentAddress2 the txtApartmentAddress2 to set
     */
    public void setTxtApartmentAddress2(String txtApartmentAddress2) {
        this.txtApartmentAddress2 = txtApartmentAddress2;
    }

    /**
     * Gets the txt recept num.
     *
     * @return the txtReceptNum
     */
    public String getTxtReceptNum() {
        return txtReceptNum;
    }

    /**
     * Sets the txt recept num.
     *
     * @param txtReceptNum the txtReceptNum to set
     */
    public void setTxtReceptNum(String txtReceptNum) {
        this.txtReceptNum = txtReceptNum;
    }

    /**
     * Gets the rdo notification status.
     *
     * @return the rdoNotificationStatus
     */
    public String getRdoNotificationStatus() {
        return rdoNotificationStatus;
    }

    /**
     * Sets the rdo notification status.
     *
     * @param rdoNotificationStatus the rdoNotificationStatus to set
     */
    public void setRdoNotificationStatus(String rdoNotificationStatus) {
        this.rdoNotificationStatus = rdoNotificationStatus;
    }

    /**
     * Gets the rdo accept status.
     *
     * @return the rdoAcceptStatus
     */
    public String getRdoAcceptStatus() {
        return rdoAcceptStatus;
    }

    /**
     * Sets the rdo accept status.
     *
     * @param rdoAcceptStatus the rdoAcceptStatus to set
     */
    public void setRdoAcceptStatus(String rdoAcceptStatus) {
        this.rdoAcceptStatus = rdoAcceptStatus;
    }

    /**
     * Gets the rdo advice status.
     *
     * @return the rdoAdviceStatus
     */
    public String getRdoAdviceStatus() {
        return rdoAdviceStatus;
    }

    /**
     * Sets the rdo advice status.
     *
     * @param rdoAdviceStatus the rdoAdviceStatus to set
     */
    public void setRdoAdviceStatus(String rdoAdviceStatus) {
        this.rdoAdviceStatus = rdoAdviceStatus;
    }

    /**
     * Gets the rdo supervise status.
     *
     * @return the rdoSuperviseStatus
     */
    public String getRdoSuperviseStatus() {
        return rdoSuperviseStatus;
    }

    /**
     * Sets the rdo supervise status.
     *
     * @param rdoSuperviseStatus the rdoSuperviseStatus to set
     */
    public void setRdoSuperviseStatus(String rdoSuperviseStatus) {
        this.rdoSuperviseStatus = rdoSuperviseStatus;
    }

    /**
     * Gets the cal notification date from.
     *
     * @return the calNotificationDateFrom
     */
    public String getCalNotificationDateFrom() {
        return calNotificationDateFrom;
    }

    /**
     * Sets the cal notification date from.
     *
     * @param calNotificationDateFrom the calNotificationDateFrom to set
     */
    public void setCalNotificationDateFrom(String calNotificationDateFrom) {
        this.calNotificationDateFrom = calNotificationDateFrom;
    }

    /**
     * Gets the cal notification date to.
     *
     * @return the calNotificationDateTo
     */
    public String getCalNotificationDateTo() {
        return calNotificationDateTo;
    }

    /**
     * Sets the cal notification date to.
     *
     * @param calNotificationDateTo the calNotificationDateTo to set
     */
    public void setCalNotificationDateTo(String calNotificationDateTo) {
        this.calNotificationDateTo = calNotificationDateTo;
    }

    /**
     * Gets the cal built date from.
     *
     * @return the calBuiltDateFrom
     */
    public String getCalBuiltDateFrom() {
        return calBuiltDateFrom;
    }

    /**
     * Sets the cal built date from.
     *
     * @param calBuiltDateFrom the calBuiltDateFrom to set
     */
    public void setCalBuiltDateFrom(String calBuiltDateFrom) {
        this.calBuiltDateFrom = calBuiltDateFrom;
    }

    /**
     * Gets the cal built date to.
     *
     * @return the calBuiltDateTo
     */
    public String getCalBuiltDateTo() {
        return calBuiltDateTo;
    }

    /**
     * Sets the cal built date to.
     *
     * @param calBuiltDateTo the calBuiltDateTo to set
     */
    public void setCalBuiltDateTo(String calBuiltDateTo) {
        this.calBuiltDateTo = calBuiltDateTo;
    }

    /**
     * Gets the txt house number from.
     *
     * @return the txtHouseNumberFrom
     */
    public String getTxtHouseNumberFrom() {
        return txtHouseNumberFrom;
    }

    /**
     * Sets the txt house number from.
     *
     * @param txtHouseNumberFrom the txtHouseNumberFrom to set
     */
    public void setTxtHouseNumberFrom(String txtHouseNumberFrom) {
        this.txtHouseNumberFrom = txtHouseNumberFrom;
    }

    /**
     * Gets the txt house number to.
     *
     * @return the txtHouseNumberTo
     */
    public String getTxtHouseNumberTo() {
        return txtHouseNumberTo;
    }

    /**
     * Sets the txt house number to.
     *
     * @param txtHouseNumberTo the txtHouseNumberTo to set
     */
    public void setTxtHouseNumberTo(String txtHouseNumberTo) {
        this.txtHouseNumberTo = txtHouseNumberTo;
    }

    /**
     * Gets the txt floor number from.
     *
     * @return the txtFloorNumberFrom
     */
    public String getTxtFloorNumberFrom() {
        return txtFloorNumberFrom;
    }

    /**
     * Sets the txt floor number from.
     *
     * @param txtFloorNumberFrom the txtFloorNumberFrom to set
     */
    public void setTxtFloorNumberFrom(String txtFloorNumberFrom) {
        this.txtFloorNumberFrom = txtFloorNumberFrom;
    }

    /**
     * Gets the txt floor number to.
     *
     * @return the txtFloorNumberTo
     */
    public String getTxtFloorNumberTo() {
        return txtFloorNumberTo;
    }

    /**
     * Sets the txt floor number to.
     *
     * @param txtFloorNumberTo the txtFloorNumberTo to set
     */
    public void setTxtFloorNumberTo(String txtFloorNumberTo) {
        this.txtFloorNumberTo = txtFloorNumberTo;
    }

    /**
     * Gets the user login.
     *
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the user login.
     *
     * @param userLogin the new user login
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /** The txt city code. */
    private String txtCityCode;

    /**
     * Gets the txt city code.
     *
     * @return the txt city code
     */
    public String getTxtCityCode() {
        return txtCityCode;
    }

    /**
     * Sets the txt city code.
     *
     * @param txtCityCode the new txt city code
     */
    public void setTxtCityCode(String txtCityCode) {
        this.txtCityCode = txtCityCode;
    }

    /**
     * Instantiates a new search information mansion vo.
     */
    public SearchInformationMansionVo() {
        super();
    }

    /**
     * Gets the serial version UID.
     *
     * @return the serial version UID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getPreviousScreen() {
        return previousScreen;
    }

    public void setPreviousScreen(String previousScreen) {
        this.previousScreen = previousScreen;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
