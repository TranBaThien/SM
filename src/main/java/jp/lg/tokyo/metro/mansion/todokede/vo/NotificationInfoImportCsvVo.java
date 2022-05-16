/*
 * @(#) NotificationInfoImportCsv.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/12/18
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationExportForm;

import java.io.Serializable;
/**
 * @author PVHung3
 *
 */
public class NotificationInfoImportCsvVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@CsvBindByPosition(position = 0)
	@CsvBindByName(column = "マンション名")
	private String apartmentName;

	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = "マンション名フリガナ")
	private String apartmentNamePhonetic;

	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "マンション郵便番号上三桁")
	private String apartmentZipCode1;

	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "マンション郵便番号下四桁")
	private String apartmentZipCode2;

	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "住所")
	private String apartmentAddress2;

	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "届出年月日")
	private String notificationDate;

	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "届出者（管理組合名）")
	private String notificationGroupName;

	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "届出者（届出者氏名）")
	private String notificationPersonName;

	@CsvBindByPosition(position = 8)
	@CsvBindByName(column = "変更理由")
	private String changeReason;

	@CsvBindByPosition(position = 9)
	@CsvBindByName(column = "建物の滅失その他の事由")
	private String lostElseReasonCode;

	@CsvBindByPosition(position = 10)
	@CsvBindByName(column = "建物の滅失その他の事由_その他")
	private String lostElseReasonElse;

	@CsvBindByPosition(position = 11)
	@CsvBindByName(column = "団地管理組合か否か")
	private String groupYesno;

	@CsvBindByPosition(position = 12)
	@CsvBindByName(column = "棟数")
	private String apartmentNumber;

	@CsvBindByPosition(position = 13)
	@CsvBindByName(column = "管理の形態")
	private String groupForm;

	@CsvBindByPosition(position = 14)
	@CsvBindByName(column = "管理の形態のその他")
	private String groupFormElse;

	@CsvBindByPosition(position = 15)
	@CsvBindByName(column = "戸数")
	private String houseNumber;

	@CsvBindByPosition(position = 16)
	@CsvBindByName(column = "階数")
	private String floorNumber;

	@CsvBindByPosition(position = 17)
	@CsvBindByName(column = "新築年月日")
	private String builtDate;

	@CsvBindByPosition(position = 18)
	@CsvBindByName(column = "土地の権利")
	private String landRights;

	@CsvBindByPosition(position = 19)
	@CsvBindByName(column = "土地の権利のその他")
	private String landRightsElse;

	@CsvBindByPosition(position = 20)
	@CsvBindByName(column = "併設用途 ")
	private String userfor;

	@CsvBindByPosition(position = 21)
	@CsvBindByName(column = "併設用途のその他")
	private String userforElse;

	@CsvBindByPosition(position = 22)
	@CsvBindByName(column = "管理形態 ")
	private String managementForm;

	@CsvBindByPosition(position = 23)
	@CsvBindByName(column = "管理形態のその他")
	private String managementFormElse;

	@CsvBindByPosition(position = 24)
	@CsvBindByName(column = "管理業者名")
	private String managerName;

	@CsvBindByPosition(position = 25)
	@CsvBindByName(column = "管理業者名フリガナ")
	private String managerNamePhonetic;

	@CsvBindByPosition(position = 26)
	@CsvBindByName(column = "管理業者郵便番号上三桁")
	private String managerZipCode1;

	@CsvBindByPosition(position = 27)
	@CsvBindByName(column = "管理業者郵便番号下四桁")
	private String managerZipCode2;

	@CsvBindByPosition(position = 28)
	@CsvBindByName(column = "管理業者住所")
	private String managerAddress;

	@CsvBindByPosition(position = 29)
	@CsvBindByName(column = "管理業者電話番号1 ")
	private String managerTelno1;

	@CsvBindByPosition(position = 30)
	@CsvBindByName(column = "管理業者電話番号2 ")
	private String managerTelno2;

	@CsvBindByPosition(position = 31)
	@CsvBindByName(column = "管理業者電話番号3 ")
	private String managerTelno3;

	@CsvBindByPosition(position = 32)
	@CsvBindByName(column = "管理組合 ")
	private String group;

	@CsvBindByPosition(position = 33)
	@CsvBindByName(column = "管理者等 ")
	private String manager;

	@CsvBindByPosition(position = 34)
	@CsvBindByName(column = "管理規約 ")
	private String rule;

	@CsvBindByPosition(position = 35)
	@CsvBindByName(column = "管理規約の最終改正年 ")
	private String ruleChangeYear;

	@CsvBindByPosition(position = 36)
	@CsvBindByName(column = "年1回以上の開催")
	private String oneyearOver;

	@CsvBindByPosition(position = 37)
	@CsvBindByName(column = "議事録")
	private String minutes;

	@CsvBindByPosition(position = 38)
	@CsvBindByName(column = "管理費")
	private String managementCost;

	@CsvBindByPosition(position = 39)
	@CsvBindByName(column = "修繕積立金")
	private String repairCost;

	@CsvBindByPosition(position = 40)
	@CsvBindByName(column = "修繕積立金の㎡当たり月額　 ")
	private String repairMonthlycost;

	@CsvBindByPosition(position = 41)
	@CsvBindByName(column = "修繕の計画的な実施（大規模な修繕工事） ")
	private String repairPlan;

	@CsvBindByPosition(position = 42)
	@CsvBindByName(column = "修繕の計画的な実施（大規模な修繕工事）の直近実施年 ")
	private String repairNearestYear;

	@CsvBindByPosition(position = 43)
	@CsvBindByName(column = "長期修繕計画")
	private String longRepairPlan;

	@CsvBindByPosition(position = 44)
	@CsvBindByName(column = "長期修繕計画の最新作成年")
	private String longRepairPlanYear;

	@CsvBindByPosition(position = 45)
	@CsvBindByName(column = "長期修繕計画の計画期間")
	private String longRepairPlanPeriod;

	@CsvBindByPosition(position = 46)
	@CsvBindByName(column = "長期修繕計画の年度")
	private String longRepairPlanYearFrom;

	@CsvBindByPosition(position = 47)
	@CsvBindByName(column = "長期修繕計画の年度")
	private String longRepairPlanYearTo;

	@CsvBindByPosition(position = 48)
	@CsvBindByName(column = "滞納対応に関するルール")
	private String arrearageRule;

	@CsvBindByPosition(position = 49)
	@CsvBindByName(column = "区分所有者等名簿等")
	private String segment;

	@CsvBindByPosition(position = 50)
	@CsvBindByName(column = "空き住戸_【割合】 ")
	private String emptyPercent;

	@CsvBindByPosition(position = 51)
	@CsvBindByName(column = "空き住戸_【戸数】 ")
	private String emptyNumber;

	@CsvBindByPosition(position = 52)
	@CsvBindByName(column = "賃貸化住戸_【割合】")
	private String rentalPercent;

	@CsvBindByPosition(position = 53)
	@CsvBindByName(column = "賃貸化住戸_【戸数】")
	private String rentalNumber;

	@CsvBindByPosition(position = 54)
	@CsvBindByName(column = "耐震診断 ")
	private String seismicDiagnosis;

	@CsvBindByPosition(position = 55)
	@CsvBindByName(column = "耐震性")
	private String earthquakeResistance;

	@CsvBindByPosition(position = 56)
	@CsvBindByName(column = "耐震改修 ")
	private String seismicRetrofit;

	@CsvBindByPosition(position = 57)
	@CsvBindByName(column = "設計図書 ")
	private String designDocument;

	@CsvBindByPosition(position = 58)
	@CsvBindByName(column = "修繕履歴 ")
	private String repairHistory;

	@CsvBindByPosition(position = 59)
	@CsvBindByName(column = "自主防災組織")
	private String voluntaryOrganization;

	@CsvBindByPosition(position = 60)
	@CsvBindByName(column = "防災マニュアル ")
	private String disasterPreventionManual;

	@CsvBindByPosition(position = 61)
	@CsvBindByName(column = "防災用品の備蓄 ")
	private String disasterPreventionStockpile;

	@CsvBindByPosition(position = 62)
	@CsvBindByName(column = "避難行動要支援者名簿 ")
	private String needSupportList;

	@CsvBindByPosition(position = 63)
	@CsvBindByName(column = "防災訓練の定期的な実施")
	private String disasterPreventionRegular;

	@CsvBindByPosition(position = 64)
	@CsvBindByName(column = "エントランスのバリアフリー化（スロープの設置など） ")
	private String slope;

	@CsvBindByPosition(position = 65)
	@CsvBindByName(column = "共用廊下等への手すりの設置 ")
	private String railing;

	@CsvBindByPosition(position = 66)
	@CsvBindByName(column = "エレベーターの設置")
	private String elevator;

	@CsvBindByPosition(position = 67)
	@CsvBindByName(column = "共用部分のLED化")
	private String led;

	@CsvBindByPosition(position = 68)
	@CsvBindByName(column = "開口部の遮熱性能の向上（二重窓・外断熱等）")
	private String heatShielding;

	@CsvBindByPosition(position = 69)
	@CsvBindByName(column = "電気自動車等用充電設備の設置等")
	private String equipmentCharge;

	@CsvBindByPosition(position = 70)
	@CsvBindByName(column = "地域コミュニティの形成等の取組")
	private String community;

	@CsvBindByPosition(position = 71)
	@CsvBindByName(column = "連絡先属性")
	private String contactProperty;

	@CsvBindByPosition(position = 72)
	@CsvBindByName(column = "連絡先属性その他")
	private String contactPropertyElse;

	@CsvBindByPosition(position = 73)
	@CsvBindByName(column = "連絡先郵便番号上三桁 ")
	private String contactZipCode1;

	@CsvBindByPosition(position = 74)
	@CsvBindByName(column = "連絡先郵便番号下四桁 ")
	private String contactZipCode2;

	@CsvBindByPosition(position = 75)
	@CsvBindByName(column = "連絡先住所")
	private String contactAddress;

	@CsvBindByPosition(position = 76)
	@CsvBindByName(column = "連絡先電話番号1")
	private String contactTelno1;

	@CsvBindByPosition(position = 77)
	@CsvBindByName(column = "連絡先電話番号2")
	private String contactTelno2;

	@CsvBindByPosition(position = 78)
	@CsvBindByName(column = "連絡先電話番号3")
	private String contactTelno3;

	@CsvBindByPosition(position = 79)
	@CsvBindByName(column = "連絡先氏名")
	private String contactName;

	@CsvBindByPosition(position = 80)
	@CsvBindByName(column = "連絡先氏名フリガナ")
	private String contactNamePhonetic;

	@CsvBindByPosition(position = 81)
	@CsvBindByName(column = "連絡先メールアドレス ")
	private String contactMail;

	@CsvBindByPosition(position = 82)
	@CsvBindByName(column = "自由記述欄")
	private String optional;

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getApartmentNamePhonetic() {
		return apartmentNamePhonetic;
	}

	public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
		this.apartmentNamePhonetic = apartmentNamePhonetic;
	}

	public String getApartmentZipCode1() {
		return apartmentZipCode1;
	}

	public void setApartmentZipCode1(String apartmentZipCode1) {
		this.apartmentZipCode1 = apartmentZipCode1;
	}

	public String getApartmentZipCode2() {
		return apartmentZipCode2;
	}

	public void setApartmentZipCode2(String apartmentZipCode2) {
		this.apartmentZipCode2 = apartmentZipCode2;
	}

	public String getApartmentAddress2() {
		return apartmentAddress2;
	}

	public void setApartmentAddress2(String apartmentAddress2) {
		this.apartmentAddress2 = apartmentAddress2;
	}

	public String getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationGroupName() {
		return notificationGroupName;
	}

	public void setNotificationGroupName(String notificationGroupName) {
		this.notificationGroupName = notificationGroupName;
	}

	public String getNotificationPersonName() {
		return notificationPersonName;
	}

	public void setNotificationPersonName(String notificationPersonName) {
		this.notificationPersonName = notificationPersonName;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getLostElseReasonCode() {
		return lostElseReasonCode;
	}

	public void setLostElseReasonCode(String lostElseReasonCode) {
		this.lostElseReasonCode = lostElseReasonCode;
	}

	public String getLostElseReasonElse() {
		return lostElseReasonElse;
	}

	public void setLostElseReasonElse(String lostElseReasonElse) {
		this.lostElseReasonElse = lostElseReasonElse;
	}

	public String getGroupYesno() {
		return groupYesno;
	}

	public void setGroupYesno(String groupYesno) {
		this.groupYesno = groupYesno;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getGroupForm() {
		return groupForm;
	}

	public void setGroupForm(String groupForm) {
		this.groupForm = groupForm;
	}

	public String getGroupFormElse() {
		return groupFormElse;
	}

	public void setGroupFormElse(String groupFormElse) {
		this.groupFormElse = groupFormElse;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getBuiltDate() {
		return builtDate;
	}

	public void setBuiltDate(String builtDate) {
		this.builtDate = builtDate;
	}

	public String getLandRights() {
		return landRights;
	}

	public void setLandRights(String landRights) {
		this.landRights = landRights;
	}

	public String getLandRightsElse() {
		return landRightsElse;
	}

	public void setLandRightsElse(String landRightsElse) {
		this.landRightsElse = landRightsElse;
	}

	public String getUserfor() {
		return userfor;
	}

	public void setUserfor(String userfor) {
		this.userfor = userfor;
	}

	public String getUserforElse() {
		return userforElse;
	}

	public void setUserforElse(String userforElse) {
		this.userforElse = userforElse;
	}

	public String getManagementForm() {
		return managementForm;
	}

	public void setManagementForm(String managementForm) {
		this.managementForm = managementForm;
	}

	public String getManagementFormElse() {
		return managementFormElse;
	}

	public void setManagementFormElse(String managementFormElse) {
		this.managementFormElse = managementFormElse;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerNamePhonetic() {
		return managerNamePhonetic;
	}

	public void setManagerNamePhonetic(String managerNamePhonetic) {
		this.managerNamePhonetic = managerNamePhonetic;
	}

	public String getManagerZipCode1() {
		return managerZipCode1;
	}

	public void setManagerZipCode1(String managerZipCode1) {
		this.managerZipCode1 = managerZipCode1;
	}

	public String getManagerZipCode2() {
		return managerZipCode2;
	}

	public void setManagerZipCode2(String managerZipCode2) {
		this.managerZipCode2 = managerZipCode2;
	}

	public String getManagerAddress() {
		return managerAddress;
	}

	public void setManagerAddress(String managerAddress) {
		this.managerAddress = managerAddress;
	}

	public String getManagerTelno1() {
		return managerTelno1;
	}

	public void setManagerTelno1(String managerTelno1) {
		this.managerTelno1 = managerTelno1;
	}

	public String getManagerTelno2() {
		return managerTelno2;
	}

	public void setManagerTelno2(String managerTelno2) {
		this.managerTelno2 = managerTelno2;
	}

	public String getManagerTelno3() {
		return managerTelno3;
	}

	public void setManagerTelno3(String managerTelno3) {
		this.managerTelno3 = managerTelno3;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRuleChangeYear() {
		return ruleChangeYear;
	}

	public void setRuleChangeYear(String ruleChangeYear) {
		this.ruleChangeYear = ruleChangeYear;
	}

	public String getOneyearOver() {
		return oneyearOver;
	}

	public void setOneyearOver(String oneyearOver) {
		this.oneyearOver = oneyearOver;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getManagementCost() {
		return managementCost;
	}

	public void setManagementCost(String managementCost) {
		this.managementCost = managementCost;
	}

	public String getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(String repairCost) {
		this.repairCost = repairCost;
	}

	public String getRepairMonthlycost() {
		return repairMonthlycost;
	}

	public void setRepairMonthlycost(String repairMonthlycost) {
		this.repairMonthlycost = repairMonthlycost;
	}

	public String getRepairPlan() {
		return repairPlan;
	}

	public void setRepairPlan(String repairPlan) {
		this.repairPlan = repairPlan;
	}

	public String getRepairNearestYear() {
		return repairNearestYear;
	}

	public void setRepairNearestYear(String repairNearestYear) {
		this.repairNearestYear = repairNearestYear;
	}

	public String getLongRepairPlan() {
		return longRepairPlan;
	}

	public void setLongRepairPlan(String longRepairPlan) {
		this.longRepairPlan = longRepairPlan;
	}

	public String getLongRepairPlanYear() {
		return longRepairPlanYear;
	}

	public void setLongRepairPlanYear(String longRepairPlanYear) {
		this.longRepairPlanYear = longRepairPlanYear;
	}

	public String getLongRepairPlanPeriod() {
		return longRepairPlanPeriod;
	}

	public void setLongRepairPlanPeriod(String longRepairPlanPeriod) {
		this.longRepairPlanPeriod = longRepairPlanPeriod;
	}

	public String getLongRepairPlanYearFrom() {
		return longRepairPlanYearFrom;
	}

	public void setLongRepairPlanYearFrom(String longRepairPlanYearFrom) {
		this.longRepairPlanYearFrom = longRepairPlanYearFrom;
	}

	public String getLongRepairPlanYearTo() {
		return longRepairPlanYearTo;
	}

	public void setLongRepairPlanYearTo(String longRepairPlanYearTo) {
		this.longRepairPlanYearTo = longRepairPlanYearTo;
	}

	public String getArrearageRule() {
		return arrearageRule;
	}

	public void setArrearageRule(String arrearageRule) {
		this.arrearageRule = arrearageRule;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getEmptyPercent() {
		return emptyPercent;
	}

	public void setEmptyPercent(String emptyPercent) {
		this.emptyPercent = emptyPercent;
	}

	public String getEmptyNumber() {
		return emptyNumber;
	}

	public void setEmptyNumber(String emptyNumber) {
		this.emptyNumber = emptyNumber;
	}

	public String getRentalPercent() {
		return rentalPercent;
	}

	public void setRentalPercent(String rentalPercent) {
		this.rentalPercent = rentalPercent;
	}

	public String getRentalNumber() {
		return rentalNumber;
	}

	public void setRentalNumber(String rentalNumber) {
		this.rentalNumber = rentalNumber;
	}

	public String getSeismicDiagnosis() {
		return seismicDiagnosis;
	}

	public void setSeismicDiagnosis(String seismicDiagnosis) {
		this.seismicDiagnosis = seismicDiagnosis;
	}

	public String getEarthquakeResistance() {
		return earthquakeResistance;
	}

	public void setEarthquakeResistance(String earthquakeResistance) {
		this.earthquakeResistance = earthquakeResistance;
	}

	public String getSeismicRetrofit() {
		return seismicRetrofit;
	}

	public void setSeismicRetrofit(String seismicRetrofit) {
		this.seismicRetrofit = seismicRetrofit;
	}

	public String getDesignDocument() {
		return designDocument;
	}

	public void setDesignDocument(String designDocument) {
		this.designDocument = designDocument;
	}

	public String getRepairHistory() {
		return repairHistory;
	}

	public void setRepairHistory(String repairHistory) {
		this.repairHistory = repairHistory;
	}

	public String getVoluntaryOrganization() {
		return voluntaryOrganization;
	}

	public void setVoluntaryOrganization(String voluntaryOrganization) {
		this.voluntaryOrganization = voluntaryOrganization;
	}

	public String getDisasterPreventionManual() {
		return disasterPreventionManual;
	}

	public void setDisasterPreventionManual(String disasterPreventionManual) {
		this.disasterPreventionManual = disasterPreventionManual;
	}

	public String getDisasterPreventionStockpile() {
		return disasterPreventionStockpile;
	}

	public void setDisasterPreventionStockpile(String disasterPreventionStockpile) {
		this.disasterPreventionStockpile = disasterPreventionStockpile;
	}

	public String getNeedSupportList() {
		return needSupportList;
	}

	public void setNeedSupportList(String needSupportList) {
		this.needSupportList = needSupportList;
	}

	public String getDisasterPreventionRegular() {
		return disasterPreventionRegular;
	}

	public void setDisasterPreventionRegular(String disasterPreventionRegular) {
		this.disasterPreventionRegular = disasterPreventionRegular;
	}

	public String getSlope() {
		return slope;
	}

	public void setSlope(String slope) {
		this.slope = slope;
	}

	public String getRailing() {
		return railing;
	}

	public void setRailing(String railing) {
		this.railing = railing;
	}

	public String getElevator() {
		return elevator;
	}

	public void setElevator(String elevator) {
		this.elevator = elevator;
	}

	public String getLed() {
		return led;
	}

	public void setLed(String led) {
		this.led = led;
	}

	public String getHeatShielding() {
		return heatShielding;
	}

	public void setHeatShielding(String heatShielding) {
		this.heatShielding = heatShielding;
	}

	public String getEquipmentCharge() {
		return equipmentCharge;
	}

	public void setEquipmentCharge(String equipmentCharge) {
		this.equipmentCharge = equipmentCharge;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getContactProperty() {
		return contactProperty;
	}

	public void setContactProperty(String contactProperty) {
		this.contactProperty = contactProperty;
	}

	public String getContactPropertyElse() {
		return contactPropertyElse;
	}

	public void setContactPropertyElse(String contactPropertyElse) {
		this.contactPropertyElse = contactPropertyElse;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContactZipCode1() {
		return contactZipCode1;
	}

	public void setContactZipCode1(String contactZipCode1) {
		this.contactZipCode1 = contactZipCode1;
	}

	public String getContactZipCode2() {
		return contactZipCode2;
	}

	public void setContactZipCode2(String contactZipCode2) {
		this.contactZipCode2 = contactZipCode2;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactTelno1() {
		return contactTelno1;
	}

	public void setContactTelno1(String contactTelno1) {
		this.contactTelno1 = contactTelno1;
	}

	public String getContactTelno2() {
		return contactTelno2;
	}

	public void setContactTelno2(String contactTelno2) {
		this.contactTelno2 = contactTelno2;
	}

	public String getContactTelno3() {
		return contactTelno3;
	}

	public void setContactTelno3(String contactTelno3) {
		this.contactTelno3 = contactTelno3;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNamePhonetic() {
		return contactNamePhonetic;
	}

	public void setContactNamePhonetic(String contactNamePhonetic) {
		this.contactNamePhonetic = contactNamePhonetic;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}
	
	/**
	 * Set form in case export CSV
	 * @param form
	 */
	public void setFormFrom(NotificationExportForm form) {
		this.setApartmentName(form.getBasicReportInfo().getTxtApartmentName());
		this.setApartmentNamePhonetic(form.getBasicReportInfo().getTxtApartmentNamePhonetic());
		this.setApartmentZipCode1(form.getBasicReportInfo().getTxtApartmentZipCode1());
		this.setApartmentZipCode2(form.getBasicReportInfo().getTxtApartmentZipCode2());
		this.setApartmentAddress2(form.getBasicReportInfo().getTxtApartmentAddress2());
		this.setNotificationDate(form.getInfoAreaCommon().getCalNotificationDate());
		this.setNotificationGroupName(form.getInfoAreaCommon().getTxtNotificationGroupName());
		this.setNotificationPersonName(form.getInfoAreaCommon().getTxtNotificationPersonName());
		this.setChangeReason(form.getInfoAreaCommon().getRdoChangeReason());
		this.setLostElseReasonCode(form.getInfoAreaCommon().getRdoLostElseReasonCode());
		this.setLostElseReasonElse(form.getInfoAreaCommon().getTxtLostElseReasonElse());
		this.setGroupYesno(form.getInfoAreaCommon().getRdoGroupYesno());
		this.setApartmentNumber(form.getInfoAreaCommon().getTxtApartmentNumber());
		this.setGroupForm(form.getInfoAreaCommon().getRdoGroupForm());
		this.setGroupFormElse(form.getInfoAreaCommon().getTxtGroupFormElse());
		this.setHouseNumber(form.getInfoAreaCommon().getTxtHouseNumber());
		this.setFloorNumber(form.getInfoAreaCommon().getTxtFloorNumber());
		this.setBuiltDate(form.getInfoAreaCommon().getCalBuiltDate());
		this.setLandRights(form.getInfoAreaCommon().getRdoLandRights());
		this.setLandRightsElse(form.getInfoAreaCommon().getTxtLandRightsElse());
		this.setUserfor(form.getInfoAreaCommon().getRdoUsefor());
		this.setUserforElse(form.getInfoAreaCommon().getTxtUseforElse());
		this.setManagementForm(form.getInfoAreaCommon().getRdoManagementForm());
		this.setManagementFormElse(form.getInfoAreaCommon().getTxtManagementFormElse());
		this.setManagerName(form.getInfoAreaCommon().getTxtManagerName());
		this.setManagerNamePhonetic(form.getInfoAreaCommon().getTxtManagerNamePhonetic());
		this.setManagerZipCode1(form.getInfoAreaCommon().getTxtManagerZipCode1());
		this.setManagerZipCode2(form.getInfoAreaCommon().getTxtManagerZipCode2());
		this.setManagerAddress(form.getInfoAreaCommon().getTxtManagerAddress());
		this.setManagerTelno1(form.getInfoAreaCommon().getTxtManagerTelno1());
		this.setManagerTelno2(form.getInfoAreaCommon().getTxtManagerTelno2());
		this.setManagerTelno3(form.getInfoAreaCommon().getTxtManagerTelno3());
		this.setGroup(form.getInfoAreaCommon().getRdoGroup());
		this.setManager(form.getInfoAreaCommon().getRdoManager());
		this.setRule(form.getInfoAreaCommon().getRdoRule());
		this.setRuleChangeYear(form.getInfoAreaCommon().getTxtRuleChangeYear());
		this.setOneyearOver(form.getInfoAreaCommon().getRdoOneyearOver());
		this.setMinutes(form.getInfoAreaCommon().getRdoMinutes());
		this.setManagementCost(form.getInfoAreaCommon().getRdoManagementCost());
		this.setRepairCost(form.getInfoAreaCommon().getRdoRepairCost());
		this.setRepairMonthlycost(form.getInfoAreaCommon().getTxtRepairMonthlycost());
		this.setRepairPlan(form.getInfoAreaCommon().getRdoRepairPlan());
		this.setRepairNearestYear(form.getInfoAreaCommon().getTxtRepairNearestYear());
		this.setLongRepairPlan(form.getInfoAreaCommon().getRdoLongRepairPlan());
		this.setLongRepairPlanYear(form.getInfoAreaCommon().getTxtLongRepairPlanYear());
		this.setLongRepairPlanPeriod(form.getInfoAreaCommon().getTxtLongRepairPlanPeriod());
		this.setLongRepairPlanYearFrom(form.getInfoAreaCommon().getTxtLongRepairPlanYearFrom());
		this.setLongRepairPlanYearTo(form.getInfoAreaCommon().getTxtLongRepairPlanYearTo());
		this.setArrearageRule(form.getInfoAreaCommon().getRdoArrearageRule());
		this.setSegment(form.getInfoAreaCommon().getRdoSegment());
		this.setEmptyPercent(form.getInfoAreaCommon().getRdoEmptyPercent());
		this.setEmptyNumber(form.getInfoAreaCommon().getTxtEmptyNumber());
		this.setRentalPercent(form.getInfoAreaCommon().getRdoRentalPercent());
		this.setRentalNumber(form.getInfoAreaCommon().getTxtRentalNumber());
		this.setSeismicDiagnosis(form.getInfoAreaCommon().getRdoSeismicDiagnosis());
		this.setEarthquakeResistance(form.getInfoAreaCommon().getRdoEarthquakeResistance());
		this.setSeismicRetrofit(form.getInfoAreaCommon().getRdoSeismicRetrofit());
		this.setDesignDocument(form.getInfoAreaCommon().getRdoDesignDocument());
		this.setRepairHistory(form.getInfoAreaCommon().getRdoRepairHistory());
		this.setVoluntaryOrganization(form.getInfoAreaCommon().getRdoVoluntaryOrganization());
		this.setDisasterPreventionManual(form.getInfoAreaCommon().getRdoDisasterPreventionManual());
		this.setDisasterPreventionStockpile(form.getInfoAreaCommon().getRdoDisasterPreventionStockpile());
		this.setNeedSupportList(form.getInfoAreaCommon().getRdoNeedSupportList());
		this.setDisasterPreventionRegular(form.getInfoAreaCommon().getRdoDisasterPreventionRegular());
		this.setSlope(form.getInfoAreaCommon().getRdoSlope());
		this.setRailing(form.getInfoAreaCommon().getRdoRailing());
		this.setElevator(form.getInfoAreaCommon().getRdoElevator());
		this.setLed(form.getInfoAreaCommon().getRdoLed());
		this.setHeatShielding(form.getInfoAreaCommon().getRdoHeatShielding());
		this.setEquipmentCharge(form.getInfoAreaCommon().getRdoEquipmentCharge());
		this.setCommunity(form.getInfoAreaCommon().getRdoCommunity());
		this.setContactProperty(form.getInfoAreaCommon().getRdoContactProperty());
		this.setContactPropertyElse(form.getInfoAreaCommon().getTxtContactPropertyElse());
		this.setContactZipCode1(form.getInfoAreaCommon().getTxtContactZipCode1());
		this.setContactZipCode2(form.getInfoAreaCommon().getTxtContactZipCode2());
		this.setContactAddress(form.getInfoAreaCommon().getTxtContactAddress());
		this.setContactTelno1(form.getInfoAreaCommon().getTxtContactTelno1());
		this.setContactTelno2(form.getInfoAreaCommon().getTxtContactTelno2());
		this.setContactTelno3(form.getInfoAreaCommon().getTxtContactTelno3());
		this.setContactName(form.getInfoAreaCommon().getTxtContactName());
		this.setContactNamePhonetic(form.getInfoAreaCommon().getTxtContactNamePhonetic());
		this.setContactMail(form.getInfoAreaCommon().getTxtContactMail());
		this.setOptional(form.getInfoAreaCommon().getTxaOptional());
	}
}
