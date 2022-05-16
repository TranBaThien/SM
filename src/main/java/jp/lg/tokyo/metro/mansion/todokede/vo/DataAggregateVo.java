/*
 * @(#) DataAggregateVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author LVTrinh1
 *
 */
public class DataAggregateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 集計項目 */
    private String[] chkSummaryItem;

    /** 集計単位 */
    private String rdoAggregateCredit;

    /** 集計期間開始 */
    private String cldPeriodFrom;

    /** 集計期間終了 */
    private String cldPeriodTo;

    /** 新築年月日開始 */
    private String cldNewBulidingFrom;

    /** 新築年月日終了 */
    private String cldNewBulidingTo;

    /** 建物戸数開始 */
    private String txtHouseNumberFrom;

    /** 建物戸数日終了 */
    private String txtHouseNumberTo;

    /** 届出状況 */
    private String rdoNotificationStatus;

    /** 受理状況（新規） */
    private String rdoAcceptanceStatusNew;

    /** 受理状況（変更） */
    private String rdoAcceptanceStatusChange;

    /** 都支援対象 */
    private String rdoSupportTarget;

    /** 管理組合 */
    private String rdoGroup;

    /** 管理者等 */
    private String rdoManager;

    /** 管理規約 */
    private String rdoRule;

    /** 年１回以上の開催 */
    private String rdoOneyearOver;

    /** 議事録の作成 */
    private String rdoMinutes;

    /** 管理費 */
    private String rdoManagementCost;

    /** 修繕積立金 */
    private String rdoRepairCost;

    /** 修繕の計画的な実施 （大規模な修繕工事） */
    private String rdoRepairPlan;

    /** 管理不全の兆候 */
    private String chkInadequateManagement;

    /** 集計項目 */
    private List<CodeVo> lstChkSummaryItem;

    /** 集計単位 */
    private List<CodeVo> lstRdoAggregateCredit;

    /** 区市町村 */
    private List<CityVo> lstCity;

    /** City code */
    private String cityCode;

    /** City Name */
    private String cityName;

    /** user role */
    private String userType;

    /** 届出状況 */
    private List<CodeVo> lstRdoNotificationStatus;

    /** 受理状況（新規） */
    private List<CodeVo> lstRdoAcceptanceStatusNew;

    /** 受理状況（変更） */
    private List<CodeVo> lstRdoAcceptanceStatusChange;

    /** 都支援対象 */
    private List<CodeVo> lstRdoSupportTarget;

    /** 管理組合 */
    private List<CodeVo> lstRdoGroup;

    /** 管理者等 */
    private List<CodeVo> lstRdoManager;

    /** 管理規約 */
    private List<CodeVo> lstRule;

    /** 年１回以上の開催 */
    private List<CodeVo> lstRdoOneyearOver;

    /** 議事録の作成 */
    private List<CodeVo> lstRdoMinutes;

    /** 管理費 */
    private List<CodeVo> lstRdoManagementCost;

    /** 修繕積立金 */
    private List<CodeVo> lstRdoRepairCost;

    /** 修繕の計画的な実施 （大規模な修繕工事） */
    private List<CodeVo> lstRdoRepairPlan;

    // ---------------------- Getter Setter -------------------------------//

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCldPeriodFrom() {
        return cldPeriodFrom;
    }

    public void setCldPeriodFrom(String cldPeriodFrom) {
        this.cldPeriodFrom = cldPeriodFrom;
    }

    public String getCldPeriodTo() {
        return cldPeriodTo;
    }

    public void setCldPeriodTo(String cldPeriodTo) {
        this.cldPeriodTo = cldPeriodTo;
    }

    public String getCldNewBulidingFrom() {
        return cldNewBulidingFrom;
    }

    public void setCldNewBulidingFrom(String cldNewBulidingFrom) {
        this.cldNewBulidingFrom = cldNewBulidingFrom;
    }

    public String getCldNewBulidingTo() {
        return cldNewBulidingTo;
    }

    public void setCldNewBulidingTo(String cldNewBulidingTo) {
        this.cldNewBulidingTo = cldNewBulidingTo;
    }

    public String getTxtHouseNumberFrom() {
        return txtHouseNumberFrom;
    }

    public void setTxtHouseNumberFrom(String txtHouseNumberFrom) {
        this.txtHouseNumberFrom = txtHouseNumberFrom;
    }

    public String getTxtHouseNumberTo() {
        return txtHouseNumberTo;
    }

    public void setTxtHouseNumberTo(String txtHouseNumberTo) {
        this.txtHouseNumberTo = txtHouseNumberTo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<CodeVo> getLstChkSummaryItem() {
        return lstChkSummaryItem;
    }

    public void setLstChkSummaryItem(List<CodeVo> lstChkSummaryItem) {
        this.lstChkSummaryItem = lstChkSummaryItem;
    }

    public List<CodeVo> getLstRdoAggregateCredit() {
        return lstRdoAggregateCredit;
    }

    public void setLstRdoAggregateCredit(List<CodeVo> lstRdoAggregateCredit) {
        this.lstRdoAggregateCredit = lstRdoAggregateCredit;
    }

    public List<CityVo> getLstCity() {
        return lstCity;
    }

    public void setLstCity(List<CityVo> lstCity) {
        this.lstCity = lstCity;
    }

    public List<CodeVo> getLstRdoNotificationStatus() {
        return lstRdoNotificationStatus;
    }

    public void setLstRdoNotificationStatus(List<CodeVo> lstRdoNotificationStatus) {
        this.lstRdoNotificationStatus = lstRdoNotificationStatus;
    }

    public List<CodeVo> getLstRdoAcceptanceStatusNew() {
        return lstRdoAcceptanceStatusNew;
    }

    public void setLstRdoAcceptanceStatusNew(List<CodeVo> lstRdoAcceptanceStatusNew) {
        this.lstRdoAcceptanceStatusNew = lstRdoAcceptanceStatusNew;
    }

    public List<CodeVo> getLstRdoAcceptanceStatusChange() {
        return lstRdoAcceptanceStatusChange;
    }

    public void setLstRdoAcceptanceStatusChange(List<CodeVo> lstRdoAcceptanceStatusChange) {
        this.lstRdoAcceptanceStatusChange = lstRdoAcceptanceStatusChange;
    }

    public List<CodeVo> getLstRdoSupportTarget() {
        return lstRdoSupportTarget;
    }

    public void setLstRdoSupportTarget(List<CodeVo> lstRdoSupportTarget) {
        this.lstRdoSupportTarget = lstRdoSupportTarget;
    }

    public List<CodeVo> getLstRdoGroup() {
        return lstRdoGroup;
    }

    public void setLstRdoGroup(List<CodeVo> lstRdoGroup) {
        this.lstRdoGroup = lstRdoGroup;
    }

    public List<CodeVo> getLstRdoManager() {
        return lstRdoManager;
    }

    public void setLstRdoManager(List<CodeVo> lstRdoManager) {
        this.lstRdoManager = lstRdoManager;
    }

    public List<CodeVo> getLstRule() {
        return lstRule;
    }

    public void setLstRule(List<CodeVo> lstRule) {
        this.lstRule = lstRule;
    }

    public List<CodeVo> getLstRdoOneyearOver() {
        return lstRdoOneyearOver;
    }

    public void setLstRdoOneyearOver(List<CodeVo> lstRdoOneyearOver) {
        this.lstRdoOneyearOver = lstRdoOneyearOver;
    }

    public List<CodeVo> getLstRdoMinutes() {
        return lstRdoMinutes;
    }

    public void setLstRdoMinutes(List<CodeVo> lstRdoMinutes) {
        this.lstRdoMinutes = lstRdoMinutes;
    }

    public List<CodeVo> getLstRdoManagementCost() {
        return lstRdoManagementCost;
    }

    public void setLstRdoManagementCost(List<CodeVo> lstRdoManagementCost) {
        this.lstRdoManagementCost = lstRdoManagementCost;
    }

    public List<CodeVo> getLstRdoRepairCost() {
        return lstRdoRepairCost;
    }

    public void setLstRdoRepairCost(List<CodeVo> lstRdoRepairCost) {
        this.lstRdoRepairCost = lstRdoRepairCost;
    }

    public List<CodeVo> getLstRdoRepairPlan() {
        return lstRdoRepairPlan;
    }

    public void setLstRdoRepairPlan(List<CodeVo> lstRdoRepairPlan) {
        this.lstRdoRepairPlan = lstRdoRepairPlan;
    }

    public String getChkInadequateManagement() {
        return chkInadequateManagement;
    }

    public void setChkInadequateManagement(String chkInadequateManagement) {
        this.chkInadequateManagement = chkInadequateManagement;
    }

    public String[] getChkSummaryItem() {
        return chkSummaryItem;
    }

    public void setChkSummaryItem(String[] chkSummaryItem) {
        this.chkSummaryItem = chkSummaryItem;
    }

    public String getRdoAggregateCredit() {
        return rdoAggregateCredit;
    }

    public void setRdoAggregateCredit(String rdoAggregateCredit) {
        this.rdoAggregateCredit = rdoAggregateCredit;
    }

    public String getRdoNotificationStatus() {
        return rdoNotificationStatus;
    }

    public void setRdoNotificationStatus(String rdoNotificationStatus) {
        this.rdoNotificationStatus = rdoNotificationStatus;
    }

    public String getRdoAcceptanceStatusNew() {
        return rdoAcceptanceStatusNew;
    }

    public void setRdoAcceptanceStatusNew(String rdoAcceptanceStatusNew) {
        this.rdoAcceptanceStatusNew = rdoAcceptanceStatusNew;
    }

    public String getRdoAcceptanceStatusChange() {
        return rdoAcceptanceStatusChange;
    }

    public void setRdoAcceptanceStatusChange(String rdoAcceptanceStatusChange) {
        this.rdoAcceptanceStatusChange = rdoAcceptanceStatusChange;
    }

    public String getRdoSupportTarget() {
        return rdoSupportTarget;
    }

    public void setRdoSupportTarget(String rdoSupportTarget) {
        this.rdoSupportTarget = rdoSupportTarget;
    }

    public String getRdoGroup() {
        return rdoGroup;
    }

    public void setRdoGroup(String rdoGroup) {
        this.rdoGroup = rdoGroup;
    }

    public String getRdoManager() {
        return rdoManager;
    }

    public void setRdoManager(String rdoManager) {
        this.rdoManager = rdoManager;
    }

    public String getRdoRule() {
        return rdoRule;
    }

    public void setRdoRule(String rdoRule) {
        this.rdoRule = rdoRule;
    }

    public String getRdoOneyearOver() {
        return rdoOneyearOver;
    }

    public void setRdoOneyearOver(String rdoOneyearOver) {
        this.rdoOneyearOver = rdoOneyearOver;
    }

    public String getRdoMinutes() {
        return rdoMinutes;
    }

    public void setRdoMinutes(String rdoMinutes) {
        this.rdoMinutes = rdoMinutes;
    }

    public String getRdoManagementCost() {
        return rdoManagementCost;
    }

    public void setRdoManagementCost(String rdoManagementCost) {
        this.rdoManagementCost = rdoManagementCost;
    }

    public String getRdoRepairCost() {
        return rdoRepairCost;
    }

    public void setRdoRepairCost(String rdoRepairCost) {
        this.rdoRepairCost = rdoRepairCost;
    }

    public String getRdoRepairPlan() {
        return rdoRepairPlan;
    }

    public void setRdoRepairPlan(String rdoRepairPlan) {
        this.rdoRepairPlan = rdoRepairPlan;
    }

    public DataAggregateVo() {
        super();
    }
}
