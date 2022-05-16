/*
 * @(#) NotificationInfoVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.time.LocalDate;

/**
 * @author DLLy
 */
public class NotificationInfoVo extends BaseMansionInfoVo {

	private static final long serialVersionUID = 1L;

	private String notificationNo;

    private String acceptStatus;

    private String adviceDoneFlag;

    private Integer apartmentNumber;

    private String arrearageRuleCode;

    private LocalDate builtDate;

    private int changeCount;

    private String changeReasonCode;

    private String communityCode;

    private String contactAddress;

    private String contactMailAddress;

    private String contactName;

    private String contactNamePhonetic;

    private String contactPropertyCode;

    private String contactPropertyElse;

    private String contactTelNo;

    private String contactZipCode;

    private String designDocumentCode;

    private String disasterPreventionManualCode;

    private String disasterPreventionRegularCode;

    private String disasterPreventionStockpileCode;

    private String earthquakeResistanceCode;

    private String elevatorCode;

    private Integer emptyNumber;

    private String emptyPercentCode;

    private String equipmentChargeCode;

    private String groupCode;

    private String groupForm;

    private String groupFormElse;

    private String groupYesnoCode;

    private String heatShieldingCode;

    private Integer houseNumber;

    private String landRightsCode;

    private String landRightsElse;

    private String ledCode;

    private String longRepairPlanCode;

    private Integer longRepairPlanPeriod;

    private String longRepairPlanYear;

    private String longRepairPlanYearFrom;

    private String longRepairPlanYearTo;

    private String lostElseReasonCode;

    private String lostElseReasonElse;

    private String managementCostCode;

    private String managementFormCode;

    private String managementFormElse;

    private String managerAddress;

    private String managerCode;

    private String managerName;

    private String managerNamePhonetic;

    private String managerTelNo;

    private String managerZipCode;

    private String minutes;

    private String needSupportListCode;

    private int notificationCount;

    private LocalDate notificationDate;

    private String notificationGroupName;

    private String notificationPersonName;

    private String notificationType;

    private String openOneyearOver;

    private String optional;

    private String railingCode;

    private String receptionNo;

    private Integer rentalNumber;

    private String rentalPercentCode;

    private String repairCostCode;

    private String repairHistoryCode;

    private Integer repairMonthlyCost;

    private String repairNearestYear;

    private String repairPlanCode;

    private String ruleChangeYear;

    private String ruleCode;

    private String segmentCode;

    private String seismicDiagnosisCode;

    private String seismicRetrofitCode;

    private String slopeCode;

    private String tempKbn;

    private String useforCode;

    private String useforElse;

    private String voluntaryOrganizationCode;

    private String txtApartmentZipCode1;

    private String txtApartmentZipCode2;

    private String lblApartmentAddress1;

    private String txtApartmentAddress2;

    private String txtManagerZipCode1;

    private String txtManagerZipCode2;

    private String txtManagerTelno1;

    private String txtManagerTelno2;

    private String txtManagerTelno3;

    private String txtContactZipCode1;

    private String txtContactZipCode2;

    private String txtContactTelno1;

    private String txtContactTelno2;

    private String txtContactTelno3;

    public NotificationInfoVo() {

    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getAdviceDoneFlag() {
        return adviceDoneFlag;
    }

    public void setAdviceDoneFlag(String adviceDoneFlag) {
        this.adviceDoneFlag = adviceDoneFlag;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getArrearageRuleCode() {
        return arrearageRuleCode;
    }

    public void setArrearageRuleCode(String arrearageRuleCode) {
        this.arrearageRuleCode = arrearageRuleCode;
    }

    public LocalDate getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(LocalDate builtDate) {
        this.builtDate = builtDate;
    }

    public int getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(int changeCount) {
        this.changeCount = changeCount;
    }

    public String getChangeReasonCode() {
        return changeReasonCode;
    }

    public void setChangeReasonCode(String changeReasonCode) {
        this.changeReasonCode = changeReasonCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactMailAddress() {
        return contactMailAddress;
    }

    public void setContactMailAddress(String contactMailAddress) {
        this.contactMailAddress = contactMailAddress;
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

    public String getContactPropertyCode() {
        return contactPropertyCode;
    }

    public void setContactPropertyCode(String contactPropertyCode) {
        this.contactPropertyCode = contactPropertyCode;
    }

    public String getContactPropertyElse() {
        return contactPropertyElse;
    }

    public void setContactPropertyElse(String contactPropertyElse) {
        this.contactPropertyElse = contactPropertyElse;
    }

    public String getContactTelNo() {
        return contactTelNo;
    }

    public void setContactTelNo(String contactTelNo) {
        this.contactTelNo = contactTelNo;
    }

    public String getContactZipCode() {
        return contactZipCode;
    }

    public void setContactZipCode(String contactZipCode) {
        this.contactZipCode = contactZipCode;
    }

    public String getDesignDocumentCode() {
        return designDocumentCode;
    }

    public void setDesignDocumentCode(String designDocumentCode) {
        this.designDocumentCode = designDocumentCode;
    }

    public String getDisasterPreventionManualCode() {
        return disasterPreventionManualCode;
    }

    public void setDisasterPreventionManualCode(String disasterPreventionManualCode) {
        this.disasterPreventionManualCode = disasterPreventionManualCode;
    }

    public String getDisasterPreventionRegularCode() {
        return disasterPreventionRegularCode;
    }

    public void setDisasterPreventionRegularCode(String disasterPreventionRegularCode) {
        this.disasterPreventionRegularCode = disasterPreventionRegularCode;
    }

    public String getDisasterPreventionStockpileCode() {
        return disasterPreventionStockpileCode;
    }

    public void setDisasterPreventionStockpileCode(String disasterPreventionStockpileCode) {
        this.disasterPreventionStockpileCode = disasterPreventionStockpileCode;
    }

    public String getEarthquakeResistanceCode() {
        return earthquakeResistanceCode;
    }

    public void setEarthquakeResistanceCode(String earthquakeResistanceCode) {
        this.earthquakeResistanceCode = earthquakeResistanceCode;
    }

    public String getElevatorCode() {
        return elevatorCode;
    }

    public void setElevatorCode(String elevatorCode) {
        this.elevatorCode = elevatorCode;
    }

    public Integer getEmptyNumber() {
        return emptyNumber;
    }

    public void setEmptyNumber(Integer emptyNumber) {
        this.emptyNumber = emptyNumber;
    }

    public String getEmptyPercentCode() {
        return emptyPercentCode;
    }

    public void setEmptyPercentCode(String emptyPercentCode) {
        this.emptyPercentCode = emptyPercentCode;
    }

    public String getEquipmentChargeCode() {
        return equipmentChargeCode;
    }

    public void setEquipmentChargeCode(String equipmentChargeCode) {
        this.equipmentChargeCode = equipmentChargeCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    public String getGroupYesnoCode() {
        return groupYesnoCode;
    }

    public void setGroupYesnoCode(String groupYesnoCode) {
        this.groupYesnoCode = groupYesnoCode;
    }

    public String getHeatShieldingCode() {
        return heatShieldingCode;
    }

    public void setHeatShieldingCode(String heatShieldingCode) {
        this.heatShieldingCode = heatShieldingCode;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLandRightsCode() {
        return landRightsCode;
    }

    public void setLandRightsCode(String landRightsCode) {
        this.landRightsCode = landRightsCode;
    }

    public String getLandRightsElse() {
        return landRightsElse;
    }

    public void setLandRightsElse(String landRightsElse) {
        this.landRightsElse = landRightsElse;
    }

    public String getLedCode() {
        return ledCode;
    }

    public void setLedCode(String ledCode) {
        this.ledCode = ledCode;
    }

    public String getLongRepairPlanCode() {
        return longRepairPlanCode;
    }

    public void setLongRepairPlanCode(String longRepairPlanCode) {
        this.longRepairPlanCode = longRepairPlanCode;
    }

    public Integer getLongRepairPlanPeriod() {
        return longRepairPlanPeriod;
    }

    public void setLongRepairPlanPeriod(Integer longRepairPlanPeriod) {
        this.longRepairPlanPeriod = longRepairPlanPeriod;
    }

    public String getLongRepairPlanYear() {
        return longRepairPlanYear;
    }

    public void setLongRepairPlanYear(String longRepairPlanYear) {
        this.longRepairPlanYear = longRepairPlanYear;
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

    public String getManagementCostCode() {
        return managementCostCode;
    }

    public void setManagementCostCode(String managementCostCode) {
        this.managementCostCode = managementCostCode;
    }

    public String getManagementFormCode() {
        return managementFormCode;
    }

    public void setManagementFormCode(String managementFormCode) {
        this.managementFormCode = managementFormCode;
    }

    public String getManagementFormElse() {
        return managementFormElse;
    }

    public void setManagementFormElse(String managementFormElse) {
        this.managementFormElse = managementFormElse;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
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

    public String getManagerTelNo() {
        return managerTelNo;
    }

    public void setManagerTelNo(String managerTelNo) {
        this.managerTelNo = managerTelNo;
    }

    public String getManagerZipCode() {
        return managerZipCode;
    }

    public void setManagerZipCode(String managerZipCode) {
        this.managerZipCode = managerZipCode;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getNeedSupportListCode() {
        return needSupportListCode;
    }

    public void setNeedSupportListCode(String needSupportListCode) {
        this.needSupportListCode = needSupportListCode;
    }

    public int getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getOpenOneyearOver() {
        return openOneyearOver;
    }

    public void setOpenOneyearOver(String openOneyearOver) {
        this.openOneyearOver = openOneyearOver;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getRailingCode() {
        return railingCode;
    }

    public void setRailingCode(String railingCode) {
        this.railingCode = railingCode;
    }

    public String getReceptionNo() {
        return receptionNo;
    }

    public void setReceptionNo(String receptionNo) {
        this.receptionNo = receptionNo;
    }

    public Integer getRentalNumber() {
        return rentalNumber;
    }

    public void setRentalNumber(Integer rentalNumber) {
        this.rentalNumber = rentalNumber;
    }

    public String getRentalPercentCode() {
        return rentalPercentCode;
    }

    public void setRentalPercentCode(String rentalPercentCode) {
        this.rentalPercentCode = rentalPercentCode;
    }

    public String getRepairCostCode() {
        return repairCostCode;
    }

    public void setRepairCostCode(String repairCostCode) {
        this.repairCostCode = repairCostCode;
    }

    public String getRepairHistoryCode() {
        return repairHistoryCode;
    }

    public void setRepairHistoryCode(String repairHistoryCode) {
        this.repairHistoryCode = repairHistoryCode;
    }

    public Integer getRepairMonthlyCost() {
        return repairMonthlyCost;
    }

    public void setRepairMonthlyCost(Integer repairMonthlyCost) {
        this.repairMonthlyCost = repairMonthlyCost;
    }

    public String getRepairNearestYear() {
        return repairNearestYear;
    }

    public void setRepairNearestYear(String repairNearestYear) {
        this.repairNearestYear = repairNearestYear;
    }

    public String getRepairPlanCode() {
        return repairPlanCode;
    }

    public void setRepairPlanCode(String repairPlanCode) {
        this.repairPlanCode = repairPlanCode;
    }

    public String getRuleChangeYear() {
        return ruleChangeYear;
    }

    public void setRuleChangeYear(String ruleChangeYear) {
        this.ruleChangeYear = ruleChangeYear;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getSeismicDiagnosisCode() {
        return seismicDiagnosisCode;
    }

    public void setSeismicDiagnosisCode(String seismicDiagnosisCode) {
        this.seismicDiagnosisCode = seismicDiagnosisCode;
    }

    public String getSeismicRetrofitCode() {
        return seismicRetrofitCode;
    }

    public void setSeismicRetrofitCode(String seismicRetrofitCode) {
        this.seismicRetrofitCode = seismicRetrofitCode;
    }

    public String getSlopeCode() {
        return slopeCode;
    }

    public void setSlopeCode(String slopeCode) {
        this.slopeCode = slopeCode;
    }

    public String getTempKbn() {
        return tempKbn;
    }

    public void setTempKbn(String tempKbn) {
        this.tempKbn = tempKbn;
    }

    public String getUseforCode() {
        return useforCode;
    }

    public void setUseforCode(String useforCode) {
        this.useforCode = useforCode;
    }

    public String getUseforElse() {
        return useforElse;
    }

    public void setUseforElse(String useforElse) {
        this.useforElse = useforElse;
    }

    public String getVoluntaryOrganizationCode() {
        return voluntaryOrganizationCode;
    }

    public void setVoluntaryOrganizationCode(String voluntaryOrganizationCode) {
        this.voluntaryOrganizationCode = voluntaryOrganizationCode;
    }

    public String getTxtApartmentZipCode1() {
        return txtApartmentZipCode1;
    }

    public void setTxtApartmentZipCode1(String txtApartmentZipCode1) {
        this.txtApartmentZipCode1 = txtApartmentZipCode1;
    }

    public String getTxtApartmentZipCode2() {
        return txtApartmentZipCode2;
    }

    public void setTxtApartmentZipCode2(String txtApartmentZipCode2) {
        this.txtApartmentZipCode2 = txtApartmentZipCode2;
    }

    public String getLblApartmentAddress1() {
        return lblApartmentAddress1;
    }

    public void setLblApartmentAddress1(String lblApartmentAddress1) {
        this.lblApartmentAddress1 = lblApartmentAddress1;
    }

    public String getTxtApartmentAddress2() {
        return txtApartmentAddress2;
    }

    public void setTxtApartmentAddress2(String txtApartmentAddress2) {
        this.txtApartmentAddress2 = txtApartmentAddress2;
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
}
