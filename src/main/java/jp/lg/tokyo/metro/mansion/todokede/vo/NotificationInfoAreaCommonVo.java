/*
 * @(#) NotificationInfoCommonVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/13
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author PVHung3
 *
 */
public class NotificationInfoAreaCommonVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	String	lblReceiptNumber;
	private	String	calNotificationDate;
	private	String	txtNotificationGroupName;
	private	String	txtNotificationPersonName;
	private List<CodeVo> lstNotificationType;
	private	String	rdoNotificationType;
	private List<CodeVo> lstChangeReasonCode;
	private	String	rdoChangeReason;
	private List<CodeVo> lstLostElseReasonCode;
	private	String	rdoLostElseReasonCode;
	private	String	txtLostElseReasonElse;
	private List<CodeVo> lstGroupYesno;
	private	String	rdoGroupYesno;
	private	String	txtApartmentNumber;
	private List<CodeVo> lstGroupForm;
	private	String	rdoGroupForm;
	private	String	txtGroupFormElse;
	private	String	txtHouseNumber;
	private	String	txtFloorNumber;
	private	String	calBuiltDate;
	private List<CodeVo> lstLandRights;
	private	String	rdoLandRights;
	private	String	txtLandRightsElse;
	private List<CodeVo> lstUsefor;
	private	String	rdoUsefor;
	private	String	txtUseforElse;
	private List<CodeVo> lstManagementForm;
	private	String	rdoManagementForm;
	private	String	txtManagementFormElse;
	private	String	txtManagerName;
	private	String	txtManagerNamePhonetic;
	private	String	txtManagerZipCode1;
	private	String	txtManagerZipCode2;
	private	String	txtManagerAddress;
	private	String	txtManagerTelno1;
	private	String	txtManagerTelno2;
	private	String	txtManagerTelno3;
	private List<CodeVo> lstCd053;
	private	String	rdoGroup;
	private List<CodeVo> lstCd009;
	private	String	rdoManager;
	private	String	rdoRule;
	private	String	txtRuleChangeYear;
	private	String	rdoOneyearOver;
	private	String	rdoMinutes;
	private	String	rdoManagementCost;
	private	String	rdoRepairCost;
	private	String	txtRepairMonthlycost;
	private	String	rdoRepairPlan;
	private	String	txtRepairNearestYear;
	private List<CodeVo> lstCd002;
	private	String	rdoLongRepairPlan;
	private	String	txtLongRepairPlanYear;
	private	String	txtLongRepairPlanPeriod;
	private	String	txtLongRepairPlanYearFrom;
	private	String	txtLongRepairPlanYearTo;
	private	String	rdoArrearageRule;
	private	String	rdoSegment;
	private List<CodeVo> lstCd010;
	private	String	rdoEmptyPercent;
	private	String	txtEmptyNumber;
	private List<CodeVo> lstCd052;
	private	String	rdoRentalPercent;
	private	String	txtRentalNumber;
	private List<CodeVo> lstCd011;
	private List<CodeVo> lstCd012;
	private List<CodeVo> lstCd013;
	private	String	rdoSeismicDiagnosis;
	private	String	rdoEarthquakeResistance;
	private	String	rdoSeismicRetrofit;
	private	String	rdoDesignDocument;
	private	String	rdoRepairHistory;
	private	String	rdoVoluntaryOrganization;
	private	String	rdoDisasterPreventionManual;
	private	String	rdoDisasterPreventionStockpile;
	private	String	rdoNeedSupportList;
	private	String	rdoDisasterPreventionRegular;
	private	String	rdoSlope;
	private	String	rdoRailing;
	private	String	rdoElevator;
	private	String	rdoLed;
	private	String	rdoHeatShielding;
	private	String	rdoEquipmentCharge;
	private	String	rdoCommunity;
	private	String	rdoContactProperty;
	private	String	txtContactPropertyElse;
	private	String	txtContactZipCode1;
	private	String	txtContactZipCode2;
	private	String	txtContactAddress;
	private	String	txtContactTelno1;
	private	String	txtContactTelno2;
	private	String	txtContactTelno3;
	private	String	txtContactName;
	private	String	txtContactNamePhonetic;
	private	String	txtContactMail;
	private	String	txtContactMailConfirm;
	private	String	txaOptional;
	private String chkConfirm;
	private boolean inActiveItem;
	

	public boolean isInActiveItem() {
		return inActiveItem;
	}
	public void setInActiveItem(boolean inActiveItem) {
		this.inActiveItem = inActiveItem;
	}
	public String getChkConfirm() {
		return chkConfirm;
	}
	public void setChkConfirm(String chkConfirm) {
		this.chkConfirm = chkConfirm;
	}
	public List<CodeVo> getLstCd011() {
		return lstCd011;
	}
	public void setLstCd011(List<CodeVo> lstCd011) {
		this.lstCd011 = lstCd011;
	}
	public List<CodeVo> getLstCd012() {
		return lstCd012;
	}
	public void setLstCd012(List<CodeVo> lstCd012) {
		this.lstCd012 = lstCd012;
	}
	public List<CodeVo> getLstCd013() {
		return lstCd013;
	}
	public void setLstCd013(List<CodeVo> lstCd013) {
		this.lstCd013 = lstCd013;
	}
	public List<CodeVo> getLstCd052() {
		return lstCd052;
	}
	public void setLstCd052(List<CodeVo> lstCd052) {
		this.lstCd052 = lstCd052;
	}
	public List<CodeVo> getLstCd010() {
		return lstCd010;
	}
	public void setLstCd010(List<CodeVo> lstCd010) {
		this.lstCd010 = lstCd010;
	}
	public List<CodeVo> getLstCd002() {
		return lstCd002;
	}
	public void setLstCd002(List<CodeVo> lstCd002) {
		this.lstCd002 = lstCd002;
	}
	public List<CodeVo> getLstCd009() {
		return lstCd009;
	}
	public void setLstCd009(List<CodeVo> lstCd009) {
		this.lstCd009 = lstCd009;
	}
	public List<CodeVo> getLstCd053() {
		return lstCd053;
	}
	public void setLstCd053(List<CodeVo> lstCd053) {
		this.lstCd053 = lstCd053;
	}
	public List<CodeVo> getLstManagementForm() {
		return lstManagementForm;
	}
	public void setLstManagementForm(List<CodeVo> lstManagementForm) {
		this.lstManagementForm = lstManagementForm;
	}
	public List<CodeVo> getLstUsefor() {
		return lstUsefor;
	}
	public void setLstUsefor(List<CodeVo> lstUsefor) {
		this.lstUsefor = lstUsefor;
	}
	public List<CodeVo> getLstLandRights() {
		return lstLandRights;
	}
	public void setLstLandRights(List<CodeVo> lstLandRights) {
		this.lstLandRights = lstLandRights;
	}
	public List<CodeVo> getLstGroupForm() {
		return lstGroupForm;
	}
	public void setLstGroupForm(List<CodeVo> lstGroupForm) {
		this.lstGroupForm = lstGroupForm;
	}
	public List<CodeVo> getLstGroupYesno() {
		return lstGroupYesno;
	}
	public void setLstGroupYesno(List<CodeVo> lstGroupYesno) {
		this.lstGroupYesno = lstGroupYesno;
	}
	public List<CodeVo> getLstLostElseReasonCode() {
		return lstLostElseReasonCode;
	}
	public void setLstLostElseReasonCode(List<CodeVo> lstLostElseReasonCode) {
		this.lstLostElseReasonCode = lstLostElseReasonCode;
	}
	public List<CodeVo> getLstNotificationType() {
		return lstNotificationType;
	}
	public void setLstNotificationType(List<CodeVo> lstNotificationType) {
		this.lstNotificationType = lstNotificationType;
	}
	public List<CodeVo> getLstChangeReasonCode() {
		return lstChangeReasonCode;
	}
	public void setLstChangeReasonCode(List<CodeVo> lstChangeReasonCode) {
		this.lstChangeReasonCode = lstChangeReasonCode;
	}
	public String getLblReceiptNumber() {
		return lblReceiptNumber;
	}
	public void setLblReceiptNumber(String lblReceiptNumber) {
		this.lblReceiptNumber = lblReceiptNumber;
	}
	public String getCalNotificationDate() {
		return calNotificationDate;
	}
	public void setCalNotificationDate(String calNotificationDate) {
		this.calNotificationDate = calNotificationDate;
	}
	public String getTxtNotificationGroupName() {
		return txtNotificationGroupName;
	}
	public void setTxtNotificationGroupName(String txtNotificationGroupName) {
		this.txtNotificationGroupName = txtNotificationGroupName;
	}
	public String getTxtNotificationPersonName() {
		return txtNotificationPersonName;
	}
	public void setTxtNotificationPersonName(String txtNotificationPersonName) {
		this.txtNotificationPersonName = txtNotificationPersonName;
	}
	public String getRdoNotificationType() {
		return rdoNotificationType;
	}
	public void setRdoNotificationType(String rdoNotificationType) {
		this.rdoNotificationType = rdoNotificationType;
	}
	public String getRdoChangeReason() {
		return rdoChangeReason;
	}
	public void setRdoChangeReason(String rdoChangeReason) {
		this.rdoChangeReason = rdoChangeReason;
	}
	public String getRdoLostElseReasonCode() {
		return rdoLostElseReasonCode;
	}
	public void setRdoLostElseReasonCode(String rdoLostElseReasonCode) {
		this.rdoLostElseReasonCode = rdoLostElseReasonCode;
	}
	public String getTxtLostElseReasonElse() {
		return txtLostElseReasonElse;
	}
	public void setTxtLostElseReasonElse(String txtLostElseReasonElse) {
		this.txtLostElseReasonElse = txtLostElseReasonElse;
	}
	public String getRdoGroupYesno() {
		return rdoGroupYesno;
	}
	public void setRdoGroupYesno(String rdoGroupYesno) {
		this.rdoGroupYesno = rdoGroupYesno;
	}
	public String getTxtApartmentNumber() {
		return txtApartmentNumber;
	}
	public void setTxtApartmentNumber(String txtApartmentNumber) {
		this.txtApartmentNumber = txtApartmentNumber;
	}
	public String getRdoGroupForm() {
		return rdoGroupForm;
	}
	public void setRdoGroupForm(String rdoGroupForm) {
		this.rdoGroupForm = rdoGroupForm;
	}
	public String getTxtGroupFormElse() {
		return txtGroupFormElse;
	}
	public void setTxtGroupFormElse(String txtGroupFormElse) {
		this.txtGroupFormElse = txtGroupFormElse;
	}
	public String getTxtHouseNumber() {
		return txtHouseNumber;
	}
	public void setTxtHouseNumber(String txtHouseNumber) {
		this.txtHouseNumber = txtHouseNumber;
	}
	public String getTxtFloorNumber() {
		return txtFloorNumber;
	}
	public void setTxtFloorNumber(String txtFloorNumber) {
		this.txtFloorNumber = txtFloorNumber;
	}
	public String getCalBuiltDate() {
		return calBuiltDate;
	}
	public void setCalBuiltDate(String calBuiltDate) {
		this.calBuiltDate = calBuiltDate;
	}
	public String getRdoLandRights() {
		return rdoLandRights;
	}
	public void setRdoLandRights(String rdoLandRights) {
		this.rdoLandRights = rdoLandRights;
	}
	public String getTxtLandRightsElse() {
		return txtLandRightsElse;
	}
	public void setTxtLandRightsElse(String txtLandRightsElse) {
		this.txtLandRightsElse = txtLandRightsElse;
	}
	public String getRdoUsefor() {
		return rdoUsefor;
	}
	public void setRdoUsefor(String rdoUsefor) {
		this.rdoUsefor = rdoUsefor;
	}
	public String getTxtUseforElse() {
		return txtUseforElse;
	}
	public void setTxtUseforElse(String txtUseforElse) {
		this.txtUseforElse = txtUseforElse;
	}
	public String getRdoManagementForm() {
		return rdoManagementForm;
	}
	public void setRdoManagementForm(String rdoManagementForm) {
		this.rdoManagementForm = rdoManagementForm;
	}
	public String getTxtManagementFormElse() {
		return txtManagementFormElse;
	}
	public void setTxtManagementFormElse(String txtManagementFormElse) {
		this.txtManagementFormElse = txtManagementFormElse;
	}
	public String getTxtManagerName() {
		return txtManagerName;
	}
	public void setTxtManagerName(String txtManagerName) {
		this.txtManagerName = txtManagerName;
	}
	public String getTxtManagerNamePhonetic() {
		return txtManagerNamePhonetic;
	}
	public void setTxtManagerNamePhonetic(String txtManagerNamePhonetic) {
		this.txtManagerNamePhonetic = txtManagerNamePhonetic;
	}
	public String getTxtManagerZipCode1() {
		return txtManagerZipCode1;
	}
	public void setTxtManagerZipCode1(String txtManagerZipCode1) {
		this.txtManagerZipCode1 = txtManagerZipCode1;
	}
	public String getTxtManagerZipCode2() {
		return txtManagerZipCode2;
	}
	public void setTxtManagerZipCode2(String txtManagerZipCode2) {
		this.txtManagerZipCode2 = txtManagerZipCode2;
	}
	public String getTxtManagerAddress() {
		return txtManagerAddress;
	}
	public void setTxtManagerAddress(String txtManagerAddress) {
		this.txtManagerAddress = txtManagerAddress;
	}
	public String getTxtManagerTelno1() {
		return txtManagerTelno1;
	}
	public void setTxtManagerTelno1(String txtManagerTelno1) {
		this.txtManagerTelno1 = txtManagerTelno1;
	}
	public String getTxtManagerTelno2() {
		return txtManagerTelno2;
	}
	public void setTxtManagerTelno2(String txtManagerTelno2) {
		this.txtManagerTelno2 = txtManagerTelno2;
	}
	public String getTxtManagerTelno3() {
		return txtManagerTelno3;
	}
	public void setTxtManagerTelno3(String txtManagerTelno3) {
		this.txtManagerTelno3 = txtManagerTelno3;
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
	public String getTxtRuleChangeYear() {
		return txtRuleChangeYear;
	}
	public void setTxtRuleChangeYear(String txtRuleChangeYear) {
		this.txtRuleChangeYear = txtRuleChangeYear;
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
	public String getTxtRepairMonthlycost() {
		return txtRepairMonthlycost;
	}
	public void setTxtRepairMonthlycost(String txtRepairMonthlycost) {
		this.txtRepairMonthlycost = txtRepairMonthlycost;
	}
	public String getRdoRepairPlan() {
		return rdoRepairPlan;
	}
	public void setRdoRepairPlan(String rdoRepairPlan) {
		this.rdoRepairPlan = rdoRepairPlan;
	}
	public String getTxtRepairNearestYear() {
		return txtRepairNearestYear;
	}
	public void setTxtRepairNearestYear(String txtRepairNearestYear) {
		this.txtRepairNearestYear = txtRepairNearestYear;
	}
	public String getRdoLongRepairPlan() {
		return rdoLongRepairPlan;
	}
	public void setRdoLongRepairPlan(String rdoLongRepairPlan) {
		this.rdoLongRepairPlan = rdoLongRepairPlan;
	}
	public String getTxtLongRepairPlanYear() {
		return txtLongRepairPlanYear;
	}
	public void setTxtLongRepairPlanYear(String txtLongRepairPlanYear) {
		this.txtLongRepairPlanYear = txtLongRepairPlanYear;
	}
	public String getTxtLongRepairPlanPeriod() {
		return txtLongRepairPlanPeriod;
	}
	public void setTxtLongRepairPlanPeriod(String txtLongRepairPlanPeriod) {
		this.txtLongRepairPlanPeriod = txtLongRepairPlanPeriod;
	}
	public String getTxtLongRepairPlanYearFrom() {
		return txtLongRepairPlanYearFrom;
	}
	public void setTxtLongRepairPlanYearFrom(String txtLongRepairPlanYearFrom) {
		this.txtLongRepairPlanYearFrom = txtLongRepairPlanYearFrom;
	}
	public String getTxtLongRepairPlanYearTo() {
		return txtLongRepairPlanYearTo;
	}
	public void setTxtLongRepairPlanYearTo(String txtLongRepairPlanYearTo) {
		this.txtLongRepairPlanYearTo = txtLongRepairPlanYearTo;
	}
	public String getRdoArrearageRule() {
		return rdoArrearageRule;
	}
	public void setRdoArrearageRule(String rdoArrearageRule) {
		this.rdoArrearageRule = rdoArrearageRule;
	}
	public String getRdoSegment() {
		return rdoSegment;
	}
	public void setRdoSegment(String rdoSegment) {
		this.rdoSegment = rdoSegment;
	}
	public String getRdoEmptyPercent() {
		return rdoEmptyPercent;
	}
	public void setRdoEmptyPercent(String rdoEmptyPercent) {
		this.rdoEmptyPercent = rdoEmptyPercent;
	}
	public String getTxtEmptyNumber() {
		return txtEmptyNumber;
	}
	public void setTxtEmptyNumber(String txtEmptyNumber) {
		this.txtEmptyNumber = txtEmptyNumber;
	}
	public String getRdoRentalPercent() {
		return rdoRentalPercent;
	}
	public void setRdoRentalPercent(String rdoRentalPercent) {
		this.rdoRentalPercent = rdoRentalPercent;
	}
	public String getTxtRentalNumber() {
		return txtRentalNumber;
	}
	public void setTxtRentalNumber(String txtRentalNumber) {
		this.txtRentalNumber = txtRentalNumber;
	}
	public String getRdoSeismicDiagnosis() {
		return rdoSeismicDiagnosis;
	}
	public void setRdoSeismicDiagnosis(String rdoSeismicDiagnosis) {
		this.rdoSeismicDiagnosis = rdoSeismicDiagnosis;
	}
	public String getRdoEarthquakeResistance() {
		return rdoEarthquakeResistance;
	}
	public void setRdoEarthquakeResistance(String rdoEarthquakeResistance) {
		this.rdoEarthquakeResistance = rdoEarthquakeResistance;
	}
	public String getRdoSeismicRetrofit() {
		return rdoSeismicRetrofit;
	}
	public void setRdoSeismicRetrofit(String rdoSeismicRetrofit) {
		this.rdoSeismicRetrofit = rdoSeismicRetrofit;
	}
	public String getRdoDesignDocument() {
		return rdoDesignDocument;
	}
	public void setRdoDesignDocument(String rdoDesignDocument) {
		this.rdoDesignDocument = rdoDesignDocument;
	}
	public String getRdoRepairHistory() {
		return rdoRepairHistory;
	}
	public void setRdoRepairHistory(String rdoRepairHistory) {
		this.rdoRepairHistory = rdoRepairHistory;
	}
	public String getRdoVoluntaryOrganization() {
		return rdoVoluntaryOrganization;
	}
	public void setRdoVoluntaryOrganization(String rdoVoluntaryOrganization) {
		this.rdoVoluntaryOrganization = rdoVoluntaryOrganization;
	}
	public String getRdoDisasterPreventionManual() {
		return rdoDisasterPreventionManual;
	}
	public void setRdoDisasterPreventionManual(String rdoDisasterPreventionManual) {
		this.rdoDisasterPreventionManual = rdoDisasterPreventionManual;
	}
	public String getRdoDisasterPreventionStockpile() {
		return rdoDisasterPreventionStockpile;
	}
	public void setRdoDisasterPreventionStockpile(String rdoDisasterPreventionStockpile) {
		this.rdoDisasterPreventionStockpile = rdoDisasterPreventionStockpile;
	}
	public String getRdoNeedSupportList() {
		return rdoNeedSupportList;
	}
	public void setRdoNeedSupportList(String rdoNeedSupportList) {
		this.rdoNeedSupportList = rdoNeedSupportList;
	}
	public String getRdoDisasterPreventionRegular() {
		return rdoDisasterPreventionRegular;
	}
	public void setRdoDisasterPreventionRegular(String rdoDisasterPreventionRegular) {
		this.rdoDisasterPreventionRegular = rdoDisasterPreventionRegular;
	}
	public String getRdoSlope() {
		return rdoSlope;
	}
	public void setRdoSlope(String rdoSlope) {
		this.rdoSlope = rdoSlope;
	}
	public String getRdoRailing() {
		return rdoRailing;
	}
	public void setRdoRailing(String rdoRailing) {
		this.rdoRailing = rdoRailing;
	}
	public String getRdoElevator() {
		return rdoElevator;
	}
	public void setRdoElevator(String rdoElevator) {
		this.rdoElevator = rdoElevator;
	}
	public String getRdoLed() {
		return rdoLed;
	}
	public void setRdoLed(String rdoLed) {
		this.rdoLed = rdoLed;
	}
	public String getRdoHeatShielding() {
		return rdoHeatShielding;
	}
	public void setRdoHeatShielding(String rdoHeatShielding) {
		this.rdoHeatShielding = rdoHeatShielding;
	}
	public String getRdoEquipmentCharge() {
		return rdoEquipmentCharge;
	}
	public void setRdoEquipmentCharge(String rdoEquipmentCharge) {
		this.rdoEquipmentCharge = rdoEquipmentCharge;
	}
	public String getRdoCommunity() {
		return rdoCommunity;
	}
	public void setRdoCommunity(String rdoCommunity) {
		this.rdoCommunity = rdoCommunity;
	}
	public String getRdoContactProperty() {
		return rdoContactProperty;
	}
	public void setRdoContactProperty(String rdoContactProperty) {
		this.rdoContactProperty = rdoContactProperty;
	}
	public String getTxtContactPropertyElse() {
		return txtContactPropertyElse;
	}
	public void setTxtContactPropertyElse(String txtContactPropertyElse) {
		this.txtContactPropertyElse = txtContactPropertyElse;
	}
	public String getTxtContactZipCode1() {
		return txtContactZipCode1;
	}
	public void setTxtContactZipCode1(String txtContactZipCode1) {
		this.txtContactZipCode1 = txtContactZipCode1;
	}
	public String getTxtContactZipCode2() {
		return txtContactZipCode2;
	}
	public void setTxtContactZipCode2(String txtContactZipCode2) {
		this.txtContactZipCode2 = txtContactZipCode2;
	}
	public String getTxtContactAddress() {
		return txtContactAddress;
	}
	public void setTxtContactAddress(String txtContactAddress) {
		this.txtContactAddress = txtContactAddress;
	}
	public String getTxtContactTelno1() {
		return txtContactTelno1;
	}
	public void setTxtContactTelno1(String txtContactTelno1) {
		this.txtContactTelno1 = txtContactTelno1;
	}
	public String getTxtContactTelno2() {
		return txtContactTelno2;
	}
	public void setTxtContactTelno2(String txtContactTelno2) {
		this.txtContactTelno2 = txtContactTelno2;
	}
	public String getTxtContactTelno3() {
		return txtContactTelno3;
	}
	public void setTxtContactTelno3(String txtContactTelno3) {
		this.txtContactTelno3 = txtContactTelno3;
	}
	public String getTxtContactName() {
		return txtContactName;
	}
	public void setTxtContactName(String txtContactName) {
		this.txtContactName = txtContactName;
	}
	public String getTxtContactNamePhonetic() {
		return txtContactNamePhonetic;
	}
	public void setTxtContactNamePhonetic(String txtContactNamePhonetic) {
		this.txtContactNamePhonetic = txtContactNamePhonetic;
	}
	public String getTxtContactMail() {
		return txtContactMail;
	}
	public void setTxtContactMail(String txtContactMail) {
		this.txtContactMail = txtContactMail;
	}
	public String getTxtContactMailConfirm() {
		return txtContactMailConfirm;
	}
	public void setTxtContactMailConfirm(String txtContactMailConfirm) {
		this.txtContactMailConfirm = txtContactMailConfirm;
	}
	public String getTxaOptional() {
		return txaOptional;
	}
	public void setTxaOptional(String txaOptional) {
		this.txaOptional = txaOptional;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
