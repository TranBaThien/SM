package jp.lg.tokyo.metro.mansion.todokede.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ObjectUtils;

import jp.lg.tokyo.metro.mansion.todokede.converter.LocalDateAttributeConverter;


/**
 * The persistent class for the tbl200 database table.
 * 
 */
@Entity
@Table(name="tbl200")
public class TBL200Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TBL200EntityPK id;

	@Column(name="ACCEPT_STATUS", length=1)
	private String acceptStatus;

	@Column(name="ADDRESS", length=100)
	private String address;

	@Column(name="ADVICE_DONE_FLAG", length=1)
	private String adviceDoneFlag;

	@Column(name="APARTMENT_NAME", length=50)
	private String apartmentName;

	@Column(name="APARTMENT_NAME_PHONETIC", length=100)
	private String apartmentNamePhonetic;

	@Column(name="APARTMENT_NUMBER")
	private Integer apartmentNumber;

	@Column(name="ARREARAGE_RULE_CODE", length=1)
	private String arrearageRuleCode;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="BUILT_DATE", length=8)
	private LocalDate builtDate;

	@Column(name="CHANGE_COUNT")
	private int changeCount;

	@Column(name="CHANGE_REASON_CODE", length=1)
	private String changeReasonCode;

	@Column(name="CITY_CODE", length=6)
	private String cityCode;

	@Column(name="COMMUNITY_CODE", length=1)
	private String communityCode;

	@Column(name="CONTACT_ADDRESS", length=100)
	private String contactAddress;

	@Column(name="CONTACT_MAIL_ADDRESS", length=120)
	private String contactMailAddress;

	@Column(name="CONTACT_NAME", length=34)
	private String contactName;

	@Column(name="CONTACT_NAME_PHONETIC", length=34)
	private String contactNamePhonetic;

	@Column(name="CONTACT_PROPERTY_CODE", length=1)
	private String contactPropertyCode;

	@Column(name="CONTACT_PROPERTY_ELSE", length=30)
	private String contactPropertyElse;

	@Column(name="CONTACT_TEL_NO", length=13)
	private String contactTelNo;

	@Column(name="CONTACT_ZIP_CODE", length=8)
	private String contactZipCode;

	@Column(name="DELETE_FLAG", length=1)
	private String deleteFlag;

	@Column(name="DESIGN_DOCUMENT_CODE", length=1)
	private String designDocumentCode;

	@Column(name="DISASTER_PREVENTION_MANUAL_CODE", length=1)
	private String disasterPreventionManualCode;

	@Column(name="DISASTER_PREVENTION_REGULAR_CODE", length=1)
	private String disasterPreventionRegularCode;

	@Column(name="DISASTER_PREVENTION_STOCKPILE_CODE", length=1)
	private String disasterPreventionStockpileCode;

	@Column(name="EARTHQUAKE_RESISTANCE_CODE", length=1)
	private String earthquakeResistanceCode;

	@Column(name="ELEVATOR_CODE", length=1)
	private String elevatorCode;

	@Column(name="EMPTY_NUMBER")
	private Integer emptyNumber;

	@Column(name="EMPTY_PERCENT_CODE", length=1)
	private String emptyPercentCode;

	@Column(name="EQUIPMENT_CHARGE_CODE", length=1)
	private String equipmentChargeCode;

	@Column(name="FLOOR_NUMBER")
	private Integer floorNumber;

	@Column(name="GROUP_CODE", length=1)
	private String groupCode;

	@Column(name="GROUP_FORM", length=1)
	private String groupForm;

	@Column(name="GROUP_FORM_ELSE", length=30)
	private String groupFormElse;

	@Column(name="GROUP_YESNO_CODE", length=1)
	private String groupYesnoCode;

	@Column(name="HEAT_SHIELDING_CODE", length=1)
	private String heatShieldingCode;

	@Column(name="HOUSE_NUMBER")
	private Integer houseNumber;

	@Column(name="LAND_RIGHTS_CODE", length=1)
	private String landRightsCode;

	@Column(name="LAND_RIGHTS_ELSE", length=30)
	private String landRightsElse;

	@Column(name="LED_CODE", length=1)
	private String ledCode;

	@Column(name="LONG_REPAIR_PLAN_CODE", length=1)
	private String longRepairPlanCode;

	@Column(name="LONG_REPAIR_PLAN_PERIOD")
	private Integer longRepairPlanPeriod;

	@Column(name="LONG_REPAIR_PLAN_YEAR", length=4)
	private String longRepairPlanYear;

	@Column(name="LONG_REPAIR_PLAN_YEAR_FROM", length=4)
	private String longRepairPlanYearFrom;

	@Column(name="LONG_REPAIR_PLAN_YEAR_TO", length=4)
	private String longRepairPlanYearTo;

	@Column(name="LOST_ELSE_REASON_CODE", length=1)
	private String lostElseReasonCode;

	@Column(name="LOST_ELSE_REASON_ELSE", length=30)
	private String lostElseReasonElse;

	@Column(name="MANAGEMENT_COST_CODE", length=1)
	private String managementCostCode;

	@Column(name="MANAGEMENT_FORM_CODE", length=1)
	private String managementFormCode;

	@Column(name="MANAGEMENT_FORM_ELSE", length=30)
	private String managementFormElse;

	@Column(name="MANAGER_ADDRESS", length=100)
	private String managerAddress;

	@Column(name="MANAGER_CODE", length=1)
	private String managerCode;

	@Column(name="MANAGER_NAME", length=34)
	private String managerName;

	@Column(name="MANAGER_NAME_PHONETIC", length=34)
	private String managerNamePhonetic;

	@Column(name="MANAGER_TEL_NO", length=13)
	private String managerTelNo;

	@Column(name="MANAGER_ZIP_CODE", length=8)
	private String managerZipCode;

	@Column(name="MINUTES", length=1)
	private String minutes;

	@Column(name="NEED_SUPPORT_LIST_CODE", length=1)
	private String needSupportListCode;

	@Column(name="NOTIFICATION_COUNT")
	private int notificationCount;

	@Convert(converter = LocalDateAttributeConverter.class)
	@Column(name="NOTIFICATION_DATE", length=8)
	private LocalDate notificationDate;

	@Column(name="NOTIFICATION_GROUP_NAME", length=50)
	private String notificationGroupName;

	@Column(name="NOTIFICATION_PERSON_NAME", length=20)
	private String notificationPersonName;

	@Column(name="NOTIFICATION_TYPE", length=1)
	private String notificationType;

	@Column(name="OPEN_ONEYEAR_OVER", length=1)
	private String openOneyearOver;

	@Column(name="OPTIONAL", length=300)
	private String optional;

	@Column(name="RAILING_CODE", length=1)
	private String railingCode;

	@Column(name="RECEPTION_NO", length=10)
	private String receptionNo;

	@Column(name="RENTAL_NUMBER")
	private Integer rentalNumber;

	@Column(name="RENTAL_PERCENT_CODE", length=1)
	private String rentalPercentCode;

	@Column(name="REPAIR_COST_CODE", length=1)
	private String repairCostCode;

	@Column(name="REPAIR_HISTORY_CODE", length=1)
	private String repairHistoryCode;

	@Column(name="REPAIR_MONTHLY_COST")
	private Integer repairMonthlyCost;

	@Column(name="REPAIR_NEAREST_YEAR", length=4)
	private String repairNearestYear;

	@Column(name="REPAIR_PLAN_CODE", length=1)
	private String repairPlanCode;

	@Column(name="RULE_CHANGE_YEAR", length=4)
	private String ruleChangeYear;

	@Column(name="RULE_CODE", length=1)
	private String ruleCode;

	@Column(name="SEGMENT_CODE", length=1)
	private String segmentCode;

	@Column(name="SEISMIC_DIAGNOSIS_CODE", length=1)
	private String seismicDiagnosisCode;

	@Column(name="SEISMIC_RETROFIT_CODE", length=1)
	private String seismicRetrofitCode;

	@Column(name="SLOPE_CODE", length=1)
	private String slopeCode;

	@Column(name="TEMP_KBN", length=1)
	private String tempKbn;

	@Column(name="UPDATE_DATETIME")
	private LocalDateTime updateDatetime;

	@Column(name="UPDATE_USER_ID", length=10)
	private String updateUserId;

	@Column(name="USEFOR_CODE", length=1)
	private String useforCode;

	@Column(name="USEFOR_ELSE", length=30)
	private String useforElse;

	@Column(name="VOLUNTARY_ORGANIZATION_CODE", length=1)
	private String voluntaryOrganizationCode;

	@Column(name="ZIP_CODE", length=8)
	private String zipCode;

	//bi-directional many-to-one association to TBL100Entity
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APARTMENT_ID", nullable=false, insertable=false, updatable=false)
	private TBL100Entity tbl100;
	
	public TBL200Entity() {
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public TBL200EntityPK getId() {
		return this.id;
	}

	public void setId(TBL200EntityPK id) {
		this.id = id;
	}

	public String getAcceptStatus() {
		return this.acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdviceDoneFlag() {
		return this.adviceDoneFlag;
	}

	public void setAdviceDoneFlag(String adviceDoneFlag) {
		this.adviceDoneFlag = adviceDoneFlag;
	}

	public String getApartmentName() {
		return this.apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getApartmentNamePhonetic() {
		return this.apartmentNamePhonetic;
	}

	public void setApartmentNamePhonetic(String apartmentNamePhonetic) {
		this.apartmentNamePhonetic = apartmentNamePhonetic;
	}

	public Integer getApartmentNumber() {
		return this.apartmentNumber;
	}

	public void setApartmentNumber(Integer apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getArrearageRuleCode() {
		return this.arrearageRuleCode;
	}

	public void setArrearageRuleCode(String arrearageRuleCode) {
		this.arrearageRuleCode = arrearageRuleCode;
	}

	public LocalDate getBuiltDate() {
		return this.builtDate;
	}

	public void setBuiltDate(LocalDate builtDate) {
		this.builtDate = builtDate;
	}

	public int getChangeCount() {
		return this.changeCount;
	}

	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}

	public String getChangeReasonCode() {
		return this.changeReasonCode;
	}

	public void setChangeReasonCode(String changeReasonCode) {
		this.changeReasonCode = changeReasonCode;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCommunityCode() {
		return this.communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactMailAddress() {
		return this.contactMailAddress;
	}

	public void setContactMailAddress(String contactMailAddress) {
		this.contactMailAddress = contactMailAddress;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNamePhonetic() {
		return this.contactNamePhonetic;
	}

	public void setContactNamePhonetic(String contactNamePhonetic) {
		this.contactNamePhonetic = contactNamePhonetic;
	}

	public String getContactPropertyCode() {
		return this.contactPropertyCode;
	}

	public void setContactPropertyCode(String contactPropertyCode) {
		this.contactPropertyCode = contactPropertyCode;
	}

	public String getContactPropertyElse() {
		return this.contactPropertyElse;
	}

	public void setContactPropertyElse(String contactPropertyElse) {
		this.contactPropertyElse = contactPropertyElse;
	}

	public String getContactTelNo() {
		return this.contactTelNo;
	}

	public void setContactTelNo(String contactTelNo) {
		this.contactTelNo = contactTelNo;
	}

	public String getContactZipCode() {
		return this.contactZipCode;
	}

	public void setContactZipCode(String contactZipCode) {
		this.contactZipCode = contactZipCode;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDesignDocumentCode() {
		return this.designDocumentCode;
	}

	public void setDesignDocumentCode(String designDocumentCode) {
		this.designDocumentCode = designDocumentCode;
	}

	public String getDisasterPreventionManualCode() {
		return this.disasterPreventionManualCode;
	}

	public void setDisasterPreventionManualCode(String disasterPreventionManualCode) {
		this.disasterPreventionManualCode = disasterPreventionManualCode;
	}

	public String getDisasterPreventionRegularCode() {
		return this.disasterPreventionRegularCode;
	}

	public void setDisasterPreventionRegularCode(String disasterPreventionRegularCode) {
		this.disasterPreventionRegularCode = disasterPreventionRegularCode;
	}

	public String getDisasterPreventionStockpileCode() {
		return this.disasterPreventionStockpileCode;
	}

	public void setDisasterPreventionStockpileCode(String disasterPreventionStockpileCode) {
		this.disasterPreventionStockpileCode = disasterPreventionStockpileCode;
	}

	public String getEarthquakeResistanceCode() {
		return this.earthquakeResistanceCode;
	}

	public void setEarthquakeResistanceCode(String earthquakeResistanceCode) {
		this.earthquakeResistanceCode = earthquakeResistanceCode;
	}

	public String getElevatorCode() {
		return this.elevatorCode;
	}

	public void setElevatorCode(String elevatorCode) {
		this.elevatorCode = elevatorCode;
	}

	public Integer getEmptyNumber() {
		return this.emptyNumber;
	}

	public void setEmptyNumber(Integer emptyNumber) {
		this.emptyNumber = emptyNumber;
	}

	public String getEmptyPercentCode() {
		return this.emptyPercentCode;
	}

	public void setEmptyPercentCode(String emptyPercentCode) {
		this.emptyPercentCode = emptyPercentCode;
	}

	public String getEquipmentChargeCode() {
		return this.equipmentChargeCode;
	}

	public void setEquipmentChargeCode(String equipmentChargeCode) {
		this.equipmentChargeCode = equipmentChargeCode;
	}

	public Integer getFloorNumber() {
		return this.floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupForm() {
		return this.groupForm;
	}

	public void setGroupForm(String groupForm) {
		this.groupForm = groupForm;
	}

	public String getGroupFormElse() {
		return this.groupFormElse;
	}

	public void setGroupFormElse(String groupFormElse) {
		this.groupFormElse = groupFormElse;
	}

	public String getGroupYesnoCode() {
		return this.groupYesnoCode;
	}

	public void setGroupYesnoCode(String groupYesnoCode) {
		this.groupYesnoCode = groupYesnoCode;
	}

	public String getHeatShieldingCode() {
		return this.heatShieldingCode;
	}

	public void setHeatShieldingCode(String heatShieldingCode) {
		this.heatShieldingCode = heatShieldingCode;
	}

	public Integer getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getLandRightsCode() {
		return this.landRightsCode;
	}

	public void setLandRightsCode(String landRightsCode) {
		this.landRightsCode = landRightsCode;
	}

	public String getLandRightsElse() {
		return this.landRightsElse;
	}

	public void setLandRightsElse(String landRightsElse) {
		this.landRightsElse = landRightsElse;
	}

	public String getLedCode() {
		return this.ledCode;
	}

	public void setLedCode(String ledCode) {
		this.ledCode = ledCode;
	}

	public String getLongRepairPlanCode() {
		return this.longRepairPlanCode;
	}

	public void setLongRepairPlanCode(String longRepairPlanCode) {
		this.longRepairPlanCode = longRepairPlanCode;
	}

	public Integer getLongRepairPlanPeriod() {
		return this.longRepairPlanPeriod;
	}

	public void setLongRepairPlanPeriod(Integer longRepairPlanPeriod) {
		this.longRepairPlanPeriod = longRepairPlanPeriod;
	}

	public String getLongRepairPlanYear() {
		return this.longRepairPlanYear;
	}

	public void setLongRepairPlanYear(String longRepairPlanYear) {
		this.longRepairPlanYear = longRepairPlanYear;
	}

	public String getLongRepairPlanYearFrom() {
		return this.longRepairPlanYearFrom;
	}

	public void setLongRepairPlanYearFrom(String longRepairPlanYearFrom) {
		this.longRepairPlanYearFrom = longRepairPlanYearFrom;
	}

	public String getLongRepairPlanYearTo() {
		return this.longRepairPlanYearTo;
	}

	public void setLongRepairPlanYearTo(String longRepairPlanYearTo) {
		this.longRepairPlanYearTo = longRepairPlanYearTo;
	}

	public String getLostElseReasonCode() {
		return this.lostElseReasonCode;
	}

	public void setLostElseReasonCode(String lostElseReasonCode) {
		this.lostElseReasonCode = lostElseReasonCode;
	}

	public String getLostElseReasonElse() {
		return this.lostElseReasonElse;
	}

	public void setLostElseReasonElse(String lostElseReasonElse) {
		this.lostElseReasonElse = lostElseReasonElse;
	}

	public String getManagementCostCode() {
		return this.managementCostCode;
	}

	public void setManagementCostCode(String managementCostCode) {
		this.managementCostCode = managementCostCode;
	}

	public String getManagementFormCode() {
		return this.managementFormCode;
	}

	public void setManagementFormCode(String managementFormCode) {
		this.managementFormCode = managementFormCode;
	}

	public String getManagementFormElse() {
		return this.managementFormElse;
	}

	public void setManagementFormElse(String managementFormElse) {
		this.managementFormElse = managementFormElse;
	}

	public String getManagerAddress() {
		return this.managerAddress;
	}

	public void setManagerAddress(String managerAddress) {
		this.managerAddress = managerAddress;
	}

	public String getManagerCode() {
		return this.managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerNamePhonetic() {
		return this.managerNamePhonetic;
	}

	public void setManagerNamePhonetic(String managerNamePhonetic) {
		this.managerNamePhonetic = managerNamePhonetic;
	}

	public String getManagerTelNo() {
		return this.managerTelNo;
	}

	public void setManagerTelNo(String managerTelNo) {
		this.managerTelNo = managerTelNo;
	}

	public String getManagerZipCode() {
		return this.managerZipCode;
	}

	public void setManagerZipCode(String managerZipCode) {
		this.managerZipCode = managerZipCode;
	}

	public String getMinutes() {
		return this.minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getNeedSupportListCode() {
		return this.needSupportListCode;
	}

	public void setNeedSupportListCode(String needSupportListCode) {
		this.needSupportListCode = needSupportListCode;
	}

	public int getNotificationCount() {
		return this.notificationCount;
	}

	public void setNotificationCount(int notificationCount) {
		this.notificationCount = notificationCount;
	}

	public LocalDate getNotificationDate() {
		return this.notificationDate;
	}

	public void setNotificationDate(LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationGroupName() {
		return this.notificationGroupName;
	}

	public void setNotificationGroupName(String notificationGroupName) {
		this.notificationGroupName = notificationGroupName;
	}

	public String getNotificationPersonName() {
		return this.notificationPersonName;
	}

	public void setNotificationPersonName(String notificationPersonName) {
		this.notificationPersonName = notificationPersonName;
	}

	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getOpenOneyearOver() {
		return this.openOneyearOver;
	}

	public void setOpenOneyearOver(String openOneyearOver) {
		this.openOneyearOver = openOneyearOver;
	}

	public String getOptional() {
		return this.optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getRailingCode() {
		return this.railingCode;
	}

	public void setRailingCode(String railingCode) {
		this.railingCode = railingCode;
	}

	public String getReceptionNo() {
		return this.receptionNo;
	}

	public void setReceptionNo(String receptionNo) {
		this.receptionNo = receptionNo;
	}

	public Integer getRentalNumber() {
		return this.rentalNumber;
	}

	public void setRentalNumber(Integer rentalNumber) {
		this.rentalNumber = rentalNumber;
	}

	public String getRentalPercentCode() {
		return this.rentalPercentCode;
	}

	public void setRentalPercentCode(String rentalPercentCode) {
		this.rentalPercentCode = rentalPercentCode;
	}

	public String getRepairCostCode() {
		return this.repairCostCode;
	}

	public void setRepairCostCode(String repairCostCode) {
		this.repairCostCode = repairCostCode;
	}

	public String getRepairHistoryCode() {
		return this.repairHistoryCode;
	}

	public void setRepairHistoryCode(String repairHistoryCode) {
		this.repairHistoryCode = repairHistoryCode;
	}

	public Integer getRepairMonthlyCost() {
		return this.repairMonthlyCost;
	}

	public void setRepairMonthlyCost(Integer repairMonthlyCost) {
		this.repairMonthlyCost = repairMonthlyCost;
	}

	public String getRepairNearestYear() {
		return this.repairNearestYear;
	}

	public void setRepairNearestYear(String repairNearestYear) {
		this.repairNearestYear = repairNearestYear;
	}

	public String getRepairPlanCode() {
		return this.repairPlanCode;
	}

	public void setRepairPlanCode(String repairPlanCode) {
		this.repairPlanCode = repairPlanCode;
	}

	public String getRuleChangeYear() {
		return this.ruleChangeYear;
	}

	public void setRuleChangeYear(String ruleChangeYear) {
		this.ruleChangeYear = ruleChangeYear;
	}

	public String getRuleCode() {
		return this.ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getSegmentCode() {
		return this.segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getSeismicDiagnosisCode() {
		return this.seismicDiagnosisCode;
	}

	public void setSeismicDiagnosisCode(String seismicDiagnosisCode) {
		this.seismicDiagnosisCode = seismicDiagnosisCode;
	}

	public String getSeismicRetrofitCode() {
		return this.seismicRetrofitCode;
	}

	public void setSeismicRetrofitCode(String seismicRetrofitCode) {
		this.seismicRetrofitCode = seismicRetrofitCode;
	}

	public String getSlopeCode() {
		return this.slopeCode;
	}

	public void setSlopeCode(String slopeCode) {
		this.slopeCode = slopeCode;
	}

	public String getTempKbn() {
		return this.tempKbn;
	}

	public void setTempKbn(String tempKbn) {
		this.tempKbn = tempKbn;
	}

	public LocalDateTime getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(LocalDateTime updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUseforCode() {
		return this.useforCode;
	}

	public void setUseforCode(String useforCode) {
		this.useforCode = useforCode;
	}

	public String getUseforElse() {
		return this.useforElse;
	}

	public void setUseforElse(String useforElse) {
		this.useforElse = useforElse;
	}

	public String getVoluntaryOrganizationCode() {
		return this.voluntaryOrganizationCode;
	}

	public void setVoluntaryOrganizationCode(String voluntaryOrganizationCode) {
		this.voluntaryOrganizationCode = voluntaryOrganizationCode;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public TBL100Entity getTbl100() {
		return this.tbl100;
	}

	public void setTbl100(TBL100Entity tbl100) {
		this.tbl100 = tbl100;
	}
	
    public String getApartmentNumberAsString() {
        return ObjectUtils.toString(this.apartmentNumber);
    }

    public String getRepairMonthlyCostAsString() {
        return ObjectUtils.toString(this.repairMonthlyCost);
    }

    public String getEmptyNumberAsString() {
        return ObjectUtils.toString(this.emptyNumber);
    }

    public String getRentalNumberAsString() {
        return ObjectUtils.toString(this.rentalNumber);
    }

    public String getHouseNumberAsString() {
        return ObjectUtils.toString(this.houseNumber);
    }

    public String getFloorNumberAsString() {
        return ObjectUtils.toString(this.floorNumber);
    }

    public String getLongRepairPlanPeriodAsString() {
        return ObjectUtils.toString(this.longRepairPlanPeriod);
    }
}