/*
 * @(#) RP010Vo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author pdquang
 *
 */
public class RP010Vo {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String notificationFormatName;
    private String reportTitle;
    private String receiptNumber;
    private String notificationTypeChkNew;
    private String notificationTypeLabelNew;
    private String notificationTypeChkUpd;
    private String notificationTypeLabelUpd;
    private String evidence;
    private String notificationDate;
    private String sender;
    private String notificationPersonName;
    private String apartmentZipCode;
    private String apartmentAddress;
    private String apartmentNameKana;
    private String apartmentName;
    private String groupYesChk;
    private String apartmentNumber;
    private String groupFormChk1;
    private String groupFormChk2;
    private String groupFormChk3;
    private String groupFormLabel3;
    private String groupNoChk;
    private String numberOfDoor;
    private String numberOfFloor;
    private String constructionDate;
    private String ownerShipChk;
    private String leaseholdRightsChk;
    private String fixedTermLeaseholdRightsChk;
    private String landRightsElseChk;
    private String landRightsElse;
    private String useforNotChk;
    private String useforStoreChk;
    private String useforOfficeChk;
    private String useforElseChk;
    private String useforElse;
    private String allDelegatesChk;
    private String parkDelegatesChk;
    private String selfManagementChk;
    private String managementFormTypeElseChk;
    private String managementFormTypeElse;
    private String managerNameKana;
    private String managerName;
    private String managerZipCode;
    private String managerTelno1;
    private String managerTelno2;
    private String managerTelno3;
    private String managerAddress;
    private String groupHaveChk;
    private String groupNotChk;
    private String managerHaveChk;
    private String managerNotChk;
    private String managementRuleHaveChk;
    private String managementRuleNotChk;
    private String ruleChangeYear;
    private String openOneyearOverHaveChk;
    private String openOneyearOverNotChk;
    private String minutesHaveChk;
    private String minutesNotChk;
    private String managementCostHaveChk;
    private String managementCostNotChk;
    private String repairCostHaveChk;
    private String repairCostNotChk;
    private String repairMonthlyCost;
    private String repairPlanImpHaveChk;
    private String repairPlanImpNotChk;
    private String repairNearestYear;
    private String longtermRepairPlanHaveChk;
    private String longtermRepairPlanNotChk;
    private String longtermRepairLatestYear;
    private String longtermRepairPlanPeriod;
    private String longtermRepairPlanYearFrom;
    private String longtermRepairPlanYearTo;
    private String arrearageRuleHaveChk;
    private String arrearageRuleNotChk;
    private String segmentHaveChk;
    private String segmentNotChk;
    private String emptyDwellingUnitZeroPercentChk;
    private String emptyDwellingUnitFivePercentChk;
    private String emptyDwellingUnitTenPercentChk;
    private String emptyDwellingUnitFifteenPercentChk;
    private String emptyDwellingUnitTwentyPercentChk;
    private String emptyDwellingUnitTwentyPercentOverChk;
    private String emptyDwellingUnitUnknowChk;
    private String emptyDwellingUnitNumber;
    private String rentalDwellingUnitZeroPercentChk;
    private String rentalDwellingUnitFivePercentChk;
    private String rentalDwellingUnitTenPercentChk;
    private String rentalDwellingUnitTwentyPercentChk;
    private String rentalDwellingUnitTwentyPercentOverChk;
    private String rentalDwellingUnitPercent;
    private String rentalDwellingUnitNumber;
    private String seismicDiagnosisDoneChk;
    private String earthquakeResistanceHaveChk;
    private String earthquakeResistanceNotChk;
    private String seismicDiagnosisNotChk;
    private String seismicRetrofitHaveChk;
    private String seismicRetrofitNotChk;
    private String designDocumentHaveChk;
    private String designDocumentNotChk;
    private String repairHistoryHaveChk;
    private String repairHistoryNotChk;
    private String voluntaryOrganizationHaveChk;
    private String voluntaryOrganizationNotChk;
    private String disasterPreventionManualHaveChk;
    private String disasterPreventionManualNotChk;
    private String disasterPreventionStockpileHaveChk;
    private String disasterPreventionStockpileNotChk;
    private String needSupportListHaveChk;
    private String needSupportListNotChk;
    private String disasterPreventionRegularHaveChk;
    private String disasterPreventionRegularNotChk;
    private String slopeHaveChk;
    private String slopeNotChk;
    private String railingHaveChk;
    private String railingNotChk;
    private String elevatorHaveChk;
    private String elevatorNotChk;
    private String ledHaveChk;
    private String ledNotChk;
    private String heatShieldingHaveChk;
    private String heatShieldingNotChk;
    private String equipmentChargeHaveChk;
    private String equipmentChargeNotChk;
    private String communityHaveChk;
    private String communityNotChk;
    private String contactPropertyChairmanChk;
    private String contactPropertyDistinguishingChk;
    private String contactPropertyApartmentChk;
    private String contactPropertyElseChk;
    private String contactPropertyElse;
    private String contractZipCode;
    private String contractTelno1;
    private String contractTelno2;
    private String contractTelno3;
    private String contractAddress;
    private String contractNamePhonetic;
    private String contractName;
    private String contractMailAddress;
    private String receiptDate;
    private String receiptPersonInCharge;
    private String receiptNote;
    private String changeReason;
    private String changeChk;
    private String changeLbl;
    private String lostElseReasonChk;
    private String lostElseReasonLbl;
    private String bracket;
    private String exceptChk;
    private String exceptLbl;
    private String lostChk;
    private String lostLbl;
    private String elseChk;
    private String elseLbl;
    private String elseDetail;

    public String getNotificationFormatName() {
        return notificationFormatName;
    }

    public void setNotificationFormatName(String notificationFormatName) {
        this.notificationFormatName = notificationFormatName;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getNotificationTypeChkNew() {
        return notificationTypeChkNew;
    }

    public void setNotificationTypeChkNew(String notificationTypeChkNew) {
        this.notificationTypeChkNew = notificationTypeChkNew;
    }

    public String getNotificationTypeLabelNew() {
        return notificationTypeLabelNew;
    }

    public void setNotificationTypeLabelNew(String notificationTypeLabelNew) {
        this.notificationTypeLabelNew = notificationTypeLabelNew;
    }

    public String getNotificationTypeChkUpd() {
        return notificationTypeChkUpd;
    }

    public void setNotificationTypeChkUpd(String notificationTypeChkUpd) {
        this.notificationTypeChkUpd = notificationTypeChkUpd;
    }

    public String getNotificationTypeLabelUpd() {
        return notificationTypeLabelUpd;
    }

    public void setNotificationTypeLabelUpd(String notificationTypeLabelUpd) {
        this.notificationTypeLabelUpd = notificationTypeLabelUpd;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getNotificationPersonName() {
        return notificationPersonName;
    }

    public void setNotificationPersonName(String notificationPersonName) {
        this.notificationPersonName = notificationPersonName;
    }

    public String getApartmentZipCode() {
        return apartmentZipCode;
    }

    public void setApartmentZipCode(String apartmentZipCode) {
        this.apartmentZipCode = apartmentZipCode;
    }

    public String getApartmentAddress() {
        return apartmentAddress;
    }

    public void setApartmentAddress(String apartmentAddress) {
        this.apartmentAddress = apartmentAddress;
    }

    public String getApartmentNameKana() {
        return apartmentNameKana;
    }

    public void setApartmentNameKana(String apartmentNameKana) {
        this.apartmentNameKana = apartmentNameKana;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getGroupYesChk() {
        return groupYesChk;
    }

    public void setGroupYesChk(String groupYesChk) {
        this.groupYesChk = groupYesChk;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getGroupFormChk1() {
        return groupFormChk1;
    }

    public void setGroupFormChk1(String groupFormChk1) {
        this.groupFormChk1 = groupFormChk1;
    }

    public String getGroupFormChk2() {
        return groupFormChk2;
    }

    public void setGroupFormChk2(String groupFormChk2) {
        this.groupFormChk2 = groupFormChk2;
    }

    public String getGroupFormChk3() {
        return groupFormChk3;
    }

    public void setGroupFormChk3(String groupFormChk3) {
        this.groupFormChk3 = groupFormChk3;
    }

    public String getGroupFormLabel3() {
        return groupFormLabel3;
    }

    public void setGroupFormLabel3(String groupFormLabel3) {
        this.groupFormLabel3 = groupFormLabel3;
    }

    public String getGroupNoChk() {
        return groupNoChk;
    }

    public void setGroupNoChk(String groupNoChk) {
        this.groupNoChk = groupNoChk;
    }

    public String getNumberOfDoor() {
        return numberOfDoor;
    }

    public void setNumberOfDoor(String numberOfDoor) {
        this.numberOfDoor = numberOfDoor;
    }

    public String getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(String numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }

    public String getConstructionDate() {
        return constructionDate;
    }

    public void setConstructionDate(String constructionDate) {
        this.constructionDate = constructionDate;
    }

    public String getOwnerShipChk() {
        return ownerShipChk;
    }

    public void setOwnerShipChk(String ownerShipChk) {
        this.ownerShipChk = ownerShipChk;
    }

    public String getLeaseholdRightsChk() {
        return leaseholdRightsChk;
    }

    public void setLeaseholdRightsChk(String leaseholdRightsChk) {
        this.leaseholdRightsChk = leaseholdRightsChk;
    }

    public String getFixedTermLeaseholdRightsChk() {
        return fixedTermLeaseholdRightsChk;
    }

    public void setFixedTermLeaseholdRightsChk(String fixedTermLeaseholdRightsChk) {
        this.fixedTermLeaseholdRightsChk = fixedTermLeaseholdRightsChk;
    }

    public String getLandRightsElseChk() {
        return landRightsElseChk;
    }

    public void setLandRightsElseChk(String landRightsElseChk) {
        this.landRightsElseChk = landRightsElseChk;
    }

    public String getLandRightsElse() {
        return landRightsElse;
    }

    public void setLandRightsElse(String landRightsElse) {
        this.landRightsElse = landRightsElse;
    }

    public String getUseforNotChk() {
        return useforNotChk;
    }

    public void setUseforNotChk(String useforNotChk) {
        this.useforNotChk = useforNotChk;
    }

    public String getUseforStoreChk() {
        return useforStoreChk;
    }

    public void setUseforStoreChk(String useforStoreChk) {
        this.useforStoreChk = useforStoreChk;
    }

    public String getUseforOfficeChk() {
        return useforOfficeChk;
    }

    public void setUseforOfficeChk(String useforOfficeChk) {
        this.useforOfficeChk = useforOfficeChk;
    }

    public String getUseforElseChk() {
        return useforElseChk;
    }

    public void setUseforElseChk(String useforElseChk) {
        this.useforElseChk = useforElseChk;
    }

    public String getUseforElse() {
        return useforElse;
    }

    public void setUseforElse(String useforElse) {
        this.useforElse = useforElse;
    }

    public String getAllDelegatesChk() {
        return allDelegatesChk;
    }

    public void setAllDelegatesChk(String allDelegatesChk) {
        this.allDelegatesChk = allDelegatesChk;
    }

    public String getParkDelegatesChk() {
        return parkDelegatesChk;
    }

    public void setParkDelegatesChk(String parkDelegatesChk) {
        this.parkDelegatesChk = parkDelegatesChk;
    }

    public String getSelfManagementChk() {
        return selfManagementChk;
    }

    public void setSelfManagementChk(String selfManagementChk) {
        this.selfManagementChk = selfManagementChk;
    }

    public String getManagementFormTypeElseChk() {
        return managementFormTypeElseChk;
    }

    public void setManagementFormTypeElseChk(String managementFormTypeElseChk) {
        this.managementFormTypeElseChk = managementFormTypeElseChk;
    }

    public String getManagementFormTypeElse() {
        return managementFormTypeElse;
    }

    public void setManagementFormTypeElse(String managementFormTypeElse) {
        this.managementFormTypeElse = managementFormTypeElse;
    }

    public String getManagerNameKana() {
        return managerNameKana;
    }

    public void setManagerNameKana(String managerNameKana) {
        this.managerNameKana = managerNameKana;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerZipCode() {
        return managerZipCode;
    }

    public void setManagerZipCode(String managerZipCode) {
        this.managerZipCode = managerZipCode;
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

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getGroupHaveChk() {
        return groupHaveChk;
    }

    public void setGroupHaveChk(String groupHaveChk) {
        this.groupHaveChk = groupHaveChk;
    }

    public String getGroupNotChk() {
        return groupNotChk;
    }

    public void setGroupNotChk(String groupNotChk) {
        this.groupNotChk = groupNotChk;
    }

    public String getManagerHaveChk() {
        return managerHaveChk;
    }

    public void setManagerHaveChk(String managerHaveChk) {
        this.managerHaveChk = managerHaveChk;
    }

    public String getManagerNotChk() {
        return managerNotChk;
    }

    public void setManagerNotChk(String managerNotChk) {
        this.managerNotChk = managerNotChk;
    }

    public String getManagementRuleHaveChk() {
        return managementRuleHaveChk;
    }

    public void setManagementRuleHaveChk(String managementRuleHaveChk) {
        this.managementRuleHaveChk = managementRuleHaveChk;
    }

    public String getManagementRuleNotChk() {
        return managementRuleNotChk;
    }

    public void setManagementRuleNotChk(String managementRuleNotChk) {
        this.managementRuleNotChk = managementRuleNotChk;
    }

    public String getRuleChangeYear() {
        return ruleChangeYear;
    }

    public void setRuleChangeYear(String ruleChangeYear) {
        this.ruleChangeYear = ruleChangeYear;
    }

    public String getOpenOneyearOverHaveChk() {
        return openOneyearOverHaveChk;
    }

    public void setOpenOneyearOverHaveChk(String openOneyearOverHaveChk) {
        this.openOneyearOverHaveChk = openOneyearOverHaveChk;
    }

    public String getOpenOneyearOverNotChk() {
        return openOneyearOverNotChk;
    }

    public void setOpenOneyearOverNotChk(String openOneyearOverNotChk) {
        this.openOneyearOverNotChk = openOneyearOverNotChk;
    }

    public String getMinutesHaveChk() {
        return minutesHaveChk;
    }

    public void setMinutesHaveChk(String minutesHaveChk) {
        this.minutesHaveChk = minutesHaveChk;
    }

    public String getMinutesNotChk() {
        return minutesNotChk;
    }

    public void setMinutesNotChk(String minutesNotChk) {
        this.minutesNotChk = minutesNotChk;
    }

    public String getManagementCostHaveChk() {
        return managementCostHaveChk;
    }

    public void setManagementCostHaveChk(String managementCostHaveChk) {
        this.managementCostHaveChk = managementCostHaveChk;
    }

    public String getManagementCostNotChk() {
        return managementCostNotChk;
    }

    public void setManagementCostNotChk(String managementCostNotChk) {
        this.managementCostNotChk = managementCostNotChk;
    }

    public String getRepairCostHaveChk() {
        return repairCostHaveChk;
    }

    public void setRepairCostHaveChk(String repairCostHaveChk) {
        this.repairCostHaveChk = repairCostHaveChk;
    }

    public String getRepairCostNotChk() {
        return repairCostNotChk;
    }

    public void setRepairCostNotChk(String repairCostNotChk) {
        this.repairCostNotChk = repairCostNotChk;
    }

    public String getRepairMonthlyCost() {
        return repairMonthlyCost;
    }

    public void setRepairMonthlyCost(String repairMonthlyCost) {
        this.repairMonthlyCost = repairMonthlyCost;
    }

    public String getRepairPlanImpHaveChk() {
        return repairPlanImpHaveChk;
    }

    public void setRepairPlanImpHaveChk(String repairPlanImpHaveChk) {
        this.repairPlanImpHaveChk = repairPlanImpHaveChk;
    }

    public String getRepairPlanImpNotChk() {
        return repairPlanImpNotChk;
    }

    public void setRepairPlanImpNotChk(String repairPlanImpNotChk) {
        this.repairPlanImpNotChk = repairPlanImpNotChk;
    }

    public String getRepairNearestYear() {
        return repairNearestYear;
    }

    public void setRepairNearestYear(String repairNearestYear) {
        this.repairNearestYear = repairNearestYear;
    }

    public String getLongtermRepairPlanHaveChk() {
        return longtermRepairPlanHaveChk;
    }

    public void setLongtermRepairPlanHaveChk(String longtermRepairPlanHaveChk) {
        this.longtermRepairPlanHaveChk = longtermRepairPlanHaveChk;
    }

    public String getLongtermRepairPlanNotChk() {
        return longtermRepairPlanNotChk;
    }

    public void setLongtermRepairPlanNotChk(String longtermRepairPlanNotChk) {
        this.longtermRepairPlanNotChk = longtermRepairPlanNotChk;
    }

    public String getLongtermRepairLatestYear() {
        return longtermRepairLatestYear;
    }

    public void setLongtermRepairLatestYear(String longtermRepairLatestYear) {
        this.longtermRepairLatestYear = longtermRepairLatestYear;
    }

    public String getLongtermRepairPlanPeriod() {
        return longtermRepairPlanPeriod;
    }

    public void setLongtermRepairPlanPeriod(String longtermRepairPlanPeriod) {
        this.longtermRepairPlanPeriod = longtermRepairPlanPeriod;
    }

    public String getLongtermRepairPlanYearFrom() {
        return longtermRepairPlanYearFrom;
    }

    public void setLongtermRepairPlanYearFrom(String longtermRepairPlanYearFrom) {
        this.longtermRepairPlanYearFrom = longtermRepairPlanYearFrom;
    }

    public String getLongtermRepairPlanYearTo() {
        return longtermRepairPlanYearTo;
    }

    public void setLongtermRepairPlanYearTo(String longtermRepairPlanYearTo) {
        this.longtermRepairPlanYearTo = longtermRepairPlanYearTo;
    }

    public String getArrearageRuleHaveChk() {
        return arrearageRuleHaveChk;
    }

    public void setArrearageRuleHaveChk(String arrearageRuleHaveChk) {
        this.arrearageRuleHaveChk = arrearageRuleHaveChk;
    }

    public String getArrearageRuleNotChk() {
        return arrearageRuleNotChk;
    }

    public void setArrearageRuleNotChk(String arrearageRuleNotChk) {
        this.arrearageRuleNotChk = arrearageRuleNotChk;
    }

    public String getSegmentHaveChk() {
        return segmentHaveChk;
    }

    public void setSegmentHaveChk(String segmentHaveChk) {
        this.segmentHaveChk = segmentHaveChk;
    }

    public String getSegmentNotChk() {
        return segmentNotChk;
    }

    public void setSegmentNotChk(String segmentNotChk) {
        this.segmentNotChk = segmentNotChk;
    }

    public String getEmptyDwellingUnitZeroPercentChk() {
        return emptyDwellingUnitZeroPercentChk;
    }

    public void setEmptyDwellingUnitZeroPercentChk(String emptyDwellingUnitZeroPercentChk) {
        this.emptyDwellingUnitZeroPercentChk = emptyDwellingUnitZeroPercentChk;
    }

    public String getEmptyDwellingUnitFivePercentChk() {
        return emptyDwellingUnitFivePercentChk;
    }

    public void setEmptyDwellingUnitFivePercentChk(String emptyDwellingUnitFivePercentChk) {
        this.emptyDwellingUnitFivePercentChk = emptyDwellingUnitFivePercentChk;
    }

    public String getEmptyDwellingUnitTenPercentChk() {
        return emptyDwellingUnitTenPercentChk;
    }

    public void setEmptyDwellingUnitTenPercentChk(String emptyDwellingUnitTenPercentChk) {
        this.emptyDwellingUnitTenPercentChk = emptyDwellingUnitTenPercentChk;
    }

    public String getEmptyDwellingUnitFifteenPercentChk() {
        return emptyDwellingUnitFifteenPercentChk;
    }

    public void setEmptyDwellingUnitFifteenPercentChk(String emptyDwellingUnitFifteenPercentChk) {
        this.emptyDwellingUnitFifteenPercentChk = emptyDwellingUnitFifteenPercentChk;
    }

    public String getEmptyDwellingUnitTwentyPercentChk() {
        return emptyDwellingUnitTwentyPercentChk;
    }

    public void setEmptyDwellingUnitTwentyPercentChk(String emptyDwellingUnitTwentyPercentChk) {
        this.emptyDwellingUnitTwentyPercentChk = emptyDwellingUnitTwentyPercentChk;
    }

    public String getEmptyDwellingUnitTwentyPercentOverChk() {
        return emptyDwellingUnitTwentyPercentOverChk;
    }

    public void setEmptyDwellingUnitTwentyPercentOverChk(String emptyDwellingUnitTwentyPercentOverChk) {
        this.emptyDwellingUnitTwentyPercentOverChk = emptyDwellingUnitTwentyPercentOverChk;
    }

    public String getEmptyDwellingUnitUnknowChk() {
        return emptyDwellingUnitUnknowChk;
    }

    public void setEmptyDwellingUnitUnknowChk(String emptyDwellingUnitUnknowChk) {
        this.emptyDwellingUnitUnknowChk = emptyDwellingUnitUnknowChk;
    }

    public String getEmptyDwellingUnitNumber() {
        return emptyDwellingUnitNumber;
    }

    public void setEmptyDwellingUnitNumber(String emptyDwellingUnitNumber) {
        this.emptyDwellingUnitNumber = emptyDwellingUnitNumber;
    }

    public String getRentalDwellingUnitZeroPercentChk() {
        return rentalDwellingUnitZeroPercentChk;
    }

    public void setRentalDwellingUnitZeroPercentChk(String rentalDwellingUnitZeroPercentChk) {
        this.rentalDwellingUnitZeroPercentChk = rentalDwellingUnitZeroPercentChk;
    }

    public String getRentalDwellingUnitFivePercentChk() {
        return rentalDwellingUnitFivePercentChk;
    }

    public void setRentalDwellingUnitFivePercentChk(String rentalDwellingUnitFivePercentChk) {
        this.rentalDwellingUnitFivePercentChk = rentalDwellingUnitFivePercentChk;
    }

    public String getRentalDwellingUnitTenPercentChk() {
        return rentalDwellingUnitTenPercentChk;
    }

    public void setRentalDwellingUnitTenPercentChk(String rentalDwellingUnitTenPercentChk) {
        this.rentalDwellingUnitTenPercentChk = rentalDwellingUnitTenPercentChk;
    }

    public String getRentalDwellingUnitTwentyPercentChk() {
        return rentalDwellingUnitTwentyPercentChk;
    }

    public void setRentalDwellingUnitTwentyPercentChk(String rentalDwellingUnitTwentyPercentChk) {
        this.rentalDwellingUnitTwentyPercentChk = rentalDwellingUnitTwentyPercentChk;
    }

    public String getRentalDwellingUnitTwentyPercentOverChk() {
        return rentalDwellingUnitTwentyPercentOverChk;
    }

    public void setRentalDwellingUnitTwentyPercentOverChk(String rentalDwellingUnitTwentyPercentOverChk) {
        this.rentalDwellingUnitTwentyPercentOverChk = rentalDwellingUnitTwentyPercentOverChk;
    }

    public String getRentalDwellingUnitPercent() {
        return rentalDwellingUnitPercent;
    }

    public void setRentalDwellingUnitPercent(String rentalDwellingUnitPercent) {
        this.rentalDwellingUnitPercent = rentalDwellingUnitPercent;
    }

    public String getRentalDwellingUnitNumber() {
        return rentalDwellingUnitNumber;
    }

    public void setRentalDwellingUnitNumber(String rentalDwellingUnitNumber) {
        this.rentalDwellingUnitNumber = rentalDwellingUnitNumber;
    }

    public String getSeismicDiagnosisDoneChk() {
        return seismicDiagnosisDoneChk;
    }

    public void setSeismicDiagnosisDoneChk(String seismicDiagnosisDoneChk) {
        this.seismicDiagnosisDoneChk = seismicDiagnosisDoneChk;
    }

    public String getEarthquakeResistanceHaveChk() {
        return earthquakeResistanceHaveChk;
    }

    public void setEarthquakeResistanceHaveChk(String earthquakeResistanceHaveChk) {
        this.earthquakeResistanceHaveChk = earthquakeResistanceHaveChk;
    }

    public String getEarthquakeResistanceNotChk() {
        return earthquakeResistanceNotChk;
    }

    public void setEarthquakeResistanceNotChk(String earthquakeResistanceNotChk) {
        this.earthquakeResistanceNotChk = earthquakeResistanceNotChk;
    }

    public String getSeismicDiagnosisNotChk() {
        return seismicDiagnosisNotChk;
    }

    public void setSeismicDiagnosisNotChk(String seismicDiagnosisNotChk) {
        this.seismicDiagnosisNotChk = seismicDiagnosisNotChk;
    }

    public String getSeismicRetrofitHaveChk() {
        return seismicRetrofitHaveChk;
    }

    public void setSeismicRetrofitHaveChk(String seismicRetrofitHaveChk) {
        this.seismicRetrofitHaveChk = seismicRetrofitHaveChk;
    }

    public String getSeismicRetrofitNotChk() {
        return seismicRetrofitNotChk;
    }

    public void setSeismicRetrofitNotChk(String seismicRetrofitNotChk) {
        this.seismicRetrofitNotChk = seismicRetrofitNotChk;
    }

    public String getDesignDocumentHaveChk() {
        return designDocumentHaveChk;
    }

    public void setDesignDocumentHaveChk(String designDocumentHaveChk) {
        this.designDocumentHaveChk = designDocumentHaveChk;
    }

    public String getDesignDocumentNotChk() {
        return designDocumentNotChk;
    }

    public void setDesignDocumentNotChk(String designDocumentNotChk) {
        this.designDocumentNotChk = designDocumentNotChk;
    }

    public String getRepairHistoryHaveChk() {
        return repairHistoryHaveChk;
    }

    public void setRepairHistoryHaveChk(String repairHistoryHaveChk) {
        this.repairHistoryHaveChk = repairHistoryHaveChk;
    }

    public String getRepairHistoryNotChk() {
        return repairHistoryNotChk;
    }

    public void setRepairHistoryNotChk(String repairHistoryNotChk) {
        this.repairHistoryNotChk = repairHistoryNotChk;
    }

    public String getVoluntaryOrganizationHaveChk() {
        return voluntaryOrganizationHaveChk;
    }

    public void setVoluntaryOrganizationHaveChk(String voluntaryOrganizationHaveChk) {
        this.voluntaryOrganizationHaveChk = voluntaryOrganizationHaveChk;
    }

    public String getVoluntaryOrganizationNotChk() {
        return voluntaryOrganizationNotChk;
    }

    public void setVoluntaryOrganizationNotChk(String voluntaryOrganizationNotChk) {
        this.voluntaryOrganizationNotChk = voluntaryOrganizationNotChk;
    }

    public String getDisasterPreventionManualHaveChk() {
        return disasterPreventionManualHaveChk;
    }

    public void setDisasterPreventionManualHaveChk(String disasterPreventionManualHaveChk) {
        this.disasterPreventionManualHaveChk = disasterPreventionManualHaveChk;
    }

    public String getDisasterPreventionManualNotChk() {
        return disasterPreventionManualNotChk;
    }

    public void setDisasterPreventionManualNotChk(String disasterPreventionManualNotChk) {
        this.disasterPreventionManualNotChk = disasterPreventionManualNotChk;
    }

    public String getDisasterPreventionStockpileHaveChk() {
        return disasterPreventionStockpileHaveChk;
    }

    public void setDisasterPreventionStockpileHaveChk(String disasterPreventionStockpileHaveChk) {
        this.disasterPreventionStockpileHaveChk = disasterPreventionStockpileHaveChk;
    }

    public String getDisasterPreventionStockpileNotChk() {
        return disasterPreventionStockpileNotChk;
    }

    public void setDisasterPreventionStockpileNotChk(String disasterPreventionStockpileNotChk) {
        this.disasterPreventionStockpileNotChk = disasterPreventionStockpileNotChk;
    }

    public String getNeedSupportListHaveChk() {
        return needSupportListHaveChk;
    }

    public void setNeedSupportListHaveChk(String needSupportListHaveChk) {
        this.needSupportListHaveChk = needSupportListHaveChk;
    }

    public String getNeedSupportListNotChk() {
        return needSupportListNotChk;
    }

    public void setNeedSupportListNotChk(String needSupportListNotChk) {
        this.needSupportListNotChk = needSupportListNotChk;
    }

    public String getDisasterPreventionRegularHaveChk() {
        return disasterPreventionRegularHaveChk;
    }

    public void setDisasterPreventionRegularHaveChk(String disasterPreventionRegularHaveChk) {
        this.disasterPreventionRegularHaveChk = disasterPreventionRegularHaveChk;
    }

    public String getDisasterPreventionRegularNotChk() {
        return disasterPreventionRegularNotChk;
    }

    public void setDisasterPreventionRegularNotChk(String disasterPreventionRegularNotChk) {
        this.disasterPreventionRegularNotChk = disasterPreventionRegularNotChk;
    }

    public String getSlopeHaveChk() {
        return slopeHaveChk;
    }

    public void setSlopeHaveChk(String slopeHaveChk) {
        this.slopeHaveChk = slopeHaveChk;
    }

    public String getSlopeNotChk() {
        return slopeNotChk;
    }

    public void setSlopeNotChk(String slopeNotChk) {
        this.slopeNotChk = slopeNotChk;
    }

    public String getRailingHaveChk() {
        return railingHaveChk;
    }

    public void setRailingHaveChk(String railingHaveChk) {
        this.railingHaveChk = railingHaveChk;
    }

    public String getRailingNotChk() {
        return railingNotChk;
    }

    public void setRailingNotChk(String railingNotChk) {
        this.railingNotChk = railingNotChk;
    }

    public String getElevatorHaveChk() {
        return elevatorHaveChk;
    }

    public void setElevatorHaveChk(String elevatorHaveChk) {
        this.elevatorHaveChk = elevatorHaveChk;
    }

    public String getElevatorNotChk() {
        return elevatorNotChk;
    }

    public void setElevatorNotChk(String elevatorNotChk) {
        this.elevatorNotChk = elevatorNotChk;
    }

    public String getLedHaveChk() {
        return ledHaveChk;
    }

    public void setLedHaveChk(String ledHaveChk) {
        this.ledHaveChk = ledHaveChk;
    }

    public String getLedNotChk() {
        return ledNotChk;
    }

    public void setLedNotChk(String ledNotChk) {
        this.ledNotChk = ledNotChk;
    }

    public String getHeatShieldingHaveChk() {
        return heatShieldingHaveChk;
    }

    public void setHeatShieldingHaveChk(String heatShieldingHaveChk) {
        this.heatShieldingHaveChk = heatShieldingHaveChk;
    }

    public String getHeatShieldingNotChk() {
        return heatShieldingNotChk;
    }

    public void setHeatShieldingNotChk(String heatShieldingNotChk) {
        this.heatShieldingNotChk = heatShieldingNotChk;
    }

    public String getEquipmentChargeHaveChk() {
        return equipmentChargeHaveChk;
    }

    public void setEquipmentChargeHaveChk(String equipmentChargeHaveChk) {
        this.equipmentChargeHaveChk = equipmentChargeHaveChk;
    }

    public String getEquipmentChargeNotChk() {
        return equipmentChargeNotChk;
    }

    public void setEquipmentChargeNotChk(String equipmentChargeNotChk) {
        this.equipmentChargeNotChk = equipmentChargeNotChk;
    }

    public String getCommunityHaveChk() {
        return communityHaveChk;
    }

    public void setCommunityHaveChk(String communityHaveChk) {
        this.communityHaveChk = communityHaveChk;
    }

    public String getCommunityNotChk() {
        return communityNotChk;
    }

    public void setCommunityNotChk(String communityNotChk) {
        this.communityNotChk = communityNotChk;
    }

    public String getContactPropertyChairmanChk() {
        return contactPropertyChairmanChk;
    }

    public void setContactPropertyChairmanChk(String contactPropertyChairmanChk) {
        this.contactPropertyChairmanChk = contactPropertyChairmanChk;
    }

    public String getContactPropertyDistinguishingChk() {
        return contactPropertyDistinguishingChk;
    }

    public void setContactPropertyDistinguishingChk(String contactPropertyDistinguishingChk) {
        this.contactPropertyDistinguishingChk = contactPropertyDistinguishingChk;
    }

    public String getContactPropertyApartmentChk() {
        return contactPropertyApartmentChk;
    }

    public void setContactPropertyApartmentChk(String contactPropertyApartmentChk) {
        this.contactPropertyApartmentChk = contactPropertyApartmentChk;
    }

    public String getContactPropertyElseChk() {
        return contactPropertyElseChk;
    }

    public void setContactPropertyElseChk(String contactPropertyElseChk) {
        this.contactPropertyElseChk = contactPropertyElseChk;
    }

    public String getContactPropertyElse() {
        return contactPropertyElse;
    }

    public void setContactPropertyElse(String contactPropertyElse) {
        this.contactPropertyElse = contactPropertyElse;
    }

    public String getContractZipCode() {
        return contractZipCode;
    }

    public void setContractZipCode(String contractZipCode) {
        this.contractZipCode = contractZipCode;
    }

    public String getContractTelno1() {
        return contractTelno1;
    }

    public void setContractTelno1(String contractTelno1) {
        this.contractTelno1 = contractTelno1;
    }

    public String getContractTelno2() {
        return contractTelno2;
    }

    public void setContractTelno2(String contractTelno2) {
        this.contractTelno2 = contractTelno2;
    }

    public String getContractTelno3() {
        return contractTelno3;
    }

    public void setContractTelno3(String contractTelno3) {
        this.contractTelno3 = contractTelno3;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractNamePhonetic() {
        return contractNamePhonetic;
    }

    public void setContractNamePhonetic(String contractNamePhonetic) {
        this.contractNamePhonetic = contractNamePhonetic;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractMailAddress() {
        return contractMailAddress;
    }

    public void setContractMailAddress(String contractMailAddress) {
        this.contractMailAddress = contractMailAddress;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptPersonInCharge() {
        return receiptPersonInCharge;
    }

    public void setReceiptPersonInCharge(String receiptPersonInCharge) {
        this.receiptPersonInCharge = receiptPersonInCharge;
    }

    public String getReceiptNote() {
        return receiptNote;
    }

    public void setReceiptNote(String receiptNote) {
        this.receiptNote = receiptNote;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getChangeChk() {
        return changeChk;
    }

    public void setChangeChk(String changeChk) {
        this.changeChk = changeChk;
    }

    public String getChangeLbl() {
        return changeLbl;
    }

    public void setChangeLbl(String changeLbl) {
        this.changeLbl = changeLbl;
    }

    public String getLostElseReasonChk() {
        return lostElseReasonChk;
    }

    public void setLostElseReasonChk(String lostElseReasonChk) {
        this.lostElseReasonChk = lostElseReasonChk;
    }

    public String getLostElseReasonLbl() {
        return lostElseReasonLbl;
    }

    public void setLostElseReasonLbl(String lostElseReasonLbl) {
        this.lostElseReasonLbl = lostElseReasonLbl;
    }

    public String getBracket() {
        return bracket;
    }

    public void setBracket(String bracket) {
        this.bracket = bracket;
    }

    public String getExceptChk() {
        return exceptChk;
    }

    public void setExceptChk(String exceptChk) {
        this.exceptChk = exceptChk;
    }

    public String getExceptLbl() {
        return exceptLbl;
    }

    public void setExceptLbl(String exceptLbl) {
        this.exceptLbl = exceptLbl;
    }

    public String getLostChk() {
        return lostChk;
    }

    public void setLostChk(String lostChk) {
        this.lostChk = lostChk;
    }

    public String getLostLbl() {
        return lostLbl;
    }

    public void setLostLbl(String lostLbl) {
        this.lostLbl = lostLbl;
    }

    public String getElseChk() {
        return elseChk;
    }

    public void setElseChk(String elseChk) {
        this.elseChk = elseChk;
    }

    public String getElseLbl() {
        return elseLbl;
    }

    public void setElseLbl(String elseLbl) {
        this.elseLbl = elseLbl;
    }

    public String getElseDetail() {
        return elseDetail;
    }

    public void setElseDetail(String elseDetail) {
        this.elseDetail = elseDetail;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
