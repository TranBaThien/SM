/*
 * @(#) ReportLogicImpl.java 2019/12/05
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/05
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.transaction.SystemException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL210DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL220DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL230DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL210Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010TmpVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;

/**
 * @author PDQuang
 *
 */
@Service
public class ReportLogicImpl implements IReportLogic {

    private static final Logger logger = LogManager.getLogger(ReportLogicImpl.class);

    private static final String STAMP_NAME = "印";
    private static final String FROM_TIME = "時　から";
    private static final String STR_TRUE = "true";
    private static final String STR_FLASE = "false";
    private static final String BRACKETS_OPEN = "　（　";
    private static final String BRACKETS_CLOSE = "　）　　";
    private static final String BRACKETS_OPEN_1 = "（";
    private static final String BRACKETS_CLOSE_1 = "）";
    private static final String FORM_1 = "別記第１号様式（第４条及び第５条関係）";
    private static final String FORM_2 = "第２号様式（第４条及び第５条関係）";
    private static final String REPORT_TITLE_1 = "マンション管理状況届出書";
    private static final String REPORT_TITLE_2 = "マンション管理状況届出事項変更等届出書";
    private static final String ASTERISK = "＊";
    private static final String NEW = "新規";
    private static final String UPDATE = "更新";
    private static final String NEW_CHANGE = "新規届出からの変更";
    private static final String UPDATE_CHANGE = "更新届出からの変更";
    private static final String EVIDENCE_1 = "第15条第１項、第３項及び第４項並びに第16条第１項";
    private static final String EVIDENCE_2 = "第15条第５項及び第16条第２項";
    private static final String SENDER = "管理組合";
    private static final String CHANGE_REASON = "変更理由";
    private static final String CHANGE = "変更";
    private static final String LOST_ELSE_REASON = "建物の滅失その他の事由";
    private static final String EXCEPT = "建物を除却したため";
    private static final String LOST = "区分所有建物ではなくなったため";
    private static final String ELSE = "その他";


    @Autowired
    private TBL200DAO tbl200DAO;

    @Autowired
    private TBL210DAO tbl210DAO;

    @Autowired
    private TBL220DAO tbl220DAO;

    @Autowired
    private TBL230DAO tbl230DAO;

    @Autowired
    private TBL240DAO tbl240DAO;

    @Override
    public RP010Vo getReportRP010(String notificationNo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            RP010TmpVo documentInfoForm = tbl200DAO.getDocumentInfoForm(notificationNo);
            RP010Vo rp010Vo = new RP010Vo();
            if (null != documentInfoForm) {
                TBL200Entity tbl200Entity = documentInfoForm.getTbl200Entity();

                // Copy properties
                CommonUtils.copyProperties(rp010Vo, tbl200Entity);

                // マンション管理状況届出書
                if (tbl200Entity.getChangeCount() == CommonConstants.NUM_0) {
                    rp010Vo.setNotificationFormatName(FORM_1);
                    rp010Vo.setReportTitle(REPORT_TITLE_1);
                    rp010Vo.setNotificationTypeLabelNew(NEW);
                    rp010Vo.setNotificationTypeLabelUpd(UPDATE);
                    rp010Vo.setEvidence(EVIDENCE_1);
                } else {
                    // マンション管理状況届出事項変更等届出書
                    rp010Vo.setNotificationFormatName(FORM_2);
                    rp010Vo.setReportTitle(REPORT_TITLE_2);
                    rp010Vo.setNotificationTypeLabelNew(NEW_CHANGE);
                    rp010Vo.setNotificationTypeLabelUpd(UPDATE_CHANGE);
                    rp010Vo.setEvidence(EVIDENCE_2);
                }

                // Set value noticationdate
                String notificationDate = DateUtil.getFormatJapaneseDateForShow(tbl200Entity.getNotificationDate());
                rp010Vo.setNotificationDate(notificationDate);
                rp010Vo.setReceiptNumber(tbl200Entity.getReceptionNo());
                rp010Vo.setNotificationTypeChkNew(tbl200Entity.getNotificationType());
                rp010Vo.setNotificationTypeChkUpd(tbl200Entity.getNotificationType());
                rp010Vo.setSender(tbl200Entity.getApartmentName() + SENDER);
                rp010Vo.setNotificationPersonName(tbl200Entity.getNotificationPersonName());
                rp010Vo.setApartmentZipCode(tbl200Entity.getZipCode());

                // set value apartmentAddress by cityName
                if (StringUtils.isNotEmpty(documentInfoForm.getCityName())) {
                    rp010Vo.setApartmentAddress(documentInfoForm.getCityName() + tbl200Entity.getAddress());
                }

                rp010Vo.setApartmentNameKana(tbl200Entity.getApartmentNamePhonetic());
                rp010Vo.setApartmentName(tbl200Entity.getApartmentName());
                rp010Vo.setGroupYesChk(tbl200Entity.getGroupYesnoCode());
                String apartmentNumber = tbl200Entity.getApartmentNumberAsString();
                rp010Vo.setApartmentNumber(apartmentNumber);
                rp010Vo.setGroupFormChk1(tbl200Entity.getGroupForm());
                rp010Vo.setGroupFormChk2(tbl200Entity.getGroupForm());
                rp010Vo.setGroupFormChk3(tbl200Entity.getGroupForm());
                rp010Vo.setGroupFormLabel3(tbl200Entity.getGroupFormElse());
                rp010Vo.setGroupNoChk(tbl200Entity.getGroupYesnoCode());

                String numberOfDoor = tbl200Entity.getHouseNumberAsString();
                rp010Vo.setNumberOfDoor(CommonUtils.formatPrice(numberOfDoor));
                String numberOfFloor = tbl200Entity.getFloorNumberAsString();
                rp010Vo.setNumberOfFloor(numberOfFloor);

                if (tbl200Entity.getBuiltDate() != null) {
                    LocalDate constructionDate = tbl200Entity.getBuiltDate();
                    rp010Vo.setConstructionDate(constructionDate.getYear() + CommonConstants.YEAR_STRING + CommonUtils.leftPadZero(constructionDate.getMonthValue(), 2)
                                                + CommonConstants.MONTH_STRING + CommonUtils.leftPadZero(constructionDate.getDayOfMonth(), 2)
                                                + CommonConstants.DAY_STRING);
                }

                rp010Vo.setOwnerShipChk(tbl200Entity.getLandRightsCode());
                rp010Vo.setLeaseholdRightsChk(tbl200Entity.getLandRightsCode());
                rp010Vo.setFixedTermLeaseholdRightsChk(tbl200Entity.getLandRightsCode());
                rp010Vo.setLandRightsElseChk(tbl200Entity.getLandRightsCode());
                rp010Vo.setLandRightsElse(tbl200Entity.getLandRightsElse());
                rp010Vo.setUseforNotChk(tbl200Entity.getUseforCode());
                rp010Vo.setUseforStoreChk(tbl200Entity.getUseforCode());
                rp010Vo.setUseforOfficeChk(tbl200Entity.getUseforCode());
                rp010Vo.setUseforElseChk(tbl200Entity.getUseforCode());
                rp010Vo.setUseforElse(tbl200Entity.getUseforElse());
                rp010Vo.setAllDelegatesChk(tbl200Entity.getManagementFormCode());
                rp010Vo.setParkDelegatesChk(tbl200Entity.getManagementFormCode());
                rp010Vo.setSelfManagementChk(tbl200Entity.getManagementFormCode());
                rp010Vo.setManagementFormTypeElseChk(tbl200Entity.getManagementFormCode());
                rp010Vo.setManagementFormTypeElse(tbl200Entity.getManagementFormElse());
                rp010Vo.setManagerNameKana(tbl200Entity.getManagerNamePhonetic());
                rp010Vo.setManagerName(tbl200Entity.getManagerName());
                rp010Vo.setManagerZipCode(tbl200Entity.getManagerZipCode());

                if (StringUtils.isNotEmpty(tbl200Entity.getManagerTelNo())) {
                    String[] managerTelno = tbl200Entity.getManagerTelNo().split("-");
                    rp010Vo.setManagerTelno1(managerTelno[CommonConstants.NUM_0]);
                    rp010Vo.setManagerTelno2(managerTelno[CommonConstants.NUM_1]);
                    rp010Vo.setManagerTelno3(managerTelno[CommonConstants.NUM_2]);
                }

                rp010Vo.setManagerAddress(tbl200Entity.getManagerAddress());
                rp010Vo.setGroupHaveChk(tbl200Entity.getGroupCode());
                rp010Vo.setGroupNotChk(tbl200Entity.getGroupCode());
                rp010Vo.setManagerHaveChk(tbl200Entity.getManagerCode());
                rp010Vo.setManagerNotChk(tbl200Entity.getManagerCode());
                rp010Vo.setManagementRuleHaveChk(tbl200Entity.getRuleCode());
                rp010Vo.setManagementRuleNotChk(tbl200Entity.getRuleCode());
                rp010Vo.setRuleChangeYear(tbl200Entity.getRuleChangeYear());
                rp010Vo.setOpenOneyearOverHaveChk(tbl200Entity.getOpenOneyearOver());
                rp010Vo.setOpenOneyearOverNotChk(tbl200Entity.getOpenOneyearOver());
                rp010Vo.setMinutesHaveChk(tbl200Entity.getMinutes());
                rp010Vo.setMinutesNotChk(tbl200Entity.getMinutes());
                rp010Vo.setManagementCostHaveChk(tbl200Entity.getManagementCostCode());
                rp010Vo.setManagementCostNotChk(tbl200Entity.getManagementCostCode());
                rp010Vo.setRepairCostHaveChk(tbl200Entity.getRepairCostCode());
                rp010Vo.setRepairCostNotChk(tbl200Entity.getRepairCostCode());

                String repairMonthlyCost = tbl200Entity.getRepairMonthlyCostAsString();
                rp010Vo.setRepairMonthlyCost(CommonUtils.formatPrice(repairMonthlyCost));

                rp010Vo.setRepairPlanImpHaveChk(tbl200Entity.getRepairPlanCode());
                rp010Vo.setRepairPlanImpNotChk(tbl200Entity.getRepairPlanCode());
                rp010Vo.setRepairNearestYear(tbl200Entity.getRepairNearestYear());
                rp010Vo.setLongtermRepairPlanHaveChk(tbl200Entity.getLongRepairPlanCode());
                rp010Vo.setLongtermRepairPlanNotChk(tbl200Entity.getLongRepairPlanCode());
                rp010Vo.setLongtermRepairLatestYear(tbl200Entity.getLongRepairPlanYear());

                String longtermRepairPlanPeriod = tbl200Entity.getLongRepairPlanPeriodAsString();
                rp010Vo.setLongtermRepairPlanPeriod(longtermRepairPlanPeriod);

                rp010Vo.setLongtermRepairPlanYearFrom(tbl200Entity.getLongRepairPlanYearFrom());
                rp010Vo.setLongtermRepairPlanYearTo(tbl200Entity.getLongRepairPlanYearTo());
                rp010Vo.setArrearageRuleHaveChk(tbl200Entity.getArrearageRuleCode());
                rp010Vo.setArrearageRuleNotChk(tbl200Entity.getArrearageRuleCode());
                rp010Vo.setSegmentHaveChk(tbl200Entity.getSegmentCode());
                rp010Vo.setSegmentNotChk(tbl200Entity.getSegmentCode());
                rp010Vo.setEmptyDwellingUnitZeroPercentChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitFivePercentChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitTenPercentChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitFifteenPercentChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitTwentyPercentChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitTwentyPercentOverChk(tbl200Entity.getEmptyPercentCode());
                rp010Vo.setEmptyDwellingUnitUnknowChk(tbl200Entity.getEmptyPercentCode());
                String emptyDwellingUnitNumber = tbl200Entity.getEmptyNumberAsString();
                rp010Vo.setEmptyDwellingUnitNumber(CommonUtils.formatPrice(emptyDwellingUnitNumber));

                rp010Vo.setRentalDwellingUnitZeroPercentChk(tbl200Entity.getRentalPercentCode());
                rp010Vo.setRentalDwellingUnitFivePercentChk(tbl200Entity.getRentalPercentCode());
                rp010Vo.setRentalDwellingUnitTenPercentChk(tbl200Entity.getRentalPercentCode());
                rp010Vo.setRentalDwellingUnitTwentyPercentChk(tbl200Entity.getRentalPercentCode());
                rp010Vo.setRentalDwellingUnitTwentyPercentOverChk(tbl200Entity.getRentalPercentCode());
                rp010Vo.setRentalDwellingUnitPercent(tbl200Entity.getRentalPercentCode());

                String rentalDwellingUnitNumber = tbl200Entity.getRentalNumberAsString();
                rp010Vo.setRentalDwellingUnitNumber(CommonUtils.formatPrice(rentalDwellingUnitNumber));

                rp010Vo.setSeismicDiagnosisDoneChk(tbl200Entity.getSeismicDiagnosisCode());
                rp010Vo.setEarthquakeResistanceHaveChk(tbl200Entity.getEarthquakeResistanceCode());
                rp010Vo.setEarthquakeResistanceNotChk(tbl200Entity.getEarthquakeResistanceCode());
                rp010Vo.setSeismicDiagnosisNotChk(tbl200Entity.getSeismicDiagnosisCode());
                rp010Vo.setSeismicRetrofitHaveChk(tbl200Entity.getSeismicRetrofitCode());
                rp010Vo.setSeismicRetrofitNotChk(tbl200Entity.getSeismicRetrofitCode());
                rp010Vo.setDesignDocumentHaveChk(tbl200Entity.getDesignDocumentCode());
                rp010Vo.setDesignDocumentNotChk(tbl200Entity.getDesignDocumentCode());
                rp010Vo.setRepairHistoryHaveChk(tbl200Entity.getRepairHistoryCode());
                rp010Vo.setRepairHistoryNotChk(tbl200Entity.getRepairHistoryCode());
                rp010Vo.setVoluntaryOrganizationHaveChk(tbl200Entity.getVoluntaryOrganizationCode());
                rp010Vo.setVoluntaryOrganizationNotChk(tbl200Entity.getVoluntaryOrganizationCode());
                rp010Vo.setDisasterPreventionManualHaveChk(tbl200Entity.getDisasterPreventionManualCode());
                rp010Vo.setDisasterPreventionManualNotChk(tbl200Entity.getDisasterPreventionManualCode());
                rp010Vo.setDisasterPreventionStockpileHaveChk(tbl200Entity.getDisasterPreventionStockpileCode());
                rp010Vo.setDisasterPreventionStockpileNotChk(tbl200Entity.getDisasterPreventionStockpileCode());
                rp010Vo.setNeedSupportListHaveChk(tbl200Entity.getNeedSupportListCode());
                rp010Vo.setNeedSupportListNotChk(tbl200Entity.getNeedSupportListCode());
                rp010Vo.setDisasterPreventionRegularHaveChk(tbl200Entity.getDisasterPreventionRegularCode());
                rp010Vo.setDisasterPreventionRegularNotChk(tbl200Entity.getDisasterPreventionRegularCode());
                rp010Vo.setSlopeHaveChk(tbl200Entity.getSlopeCode());
                rp010Vo.setSlopeNotChk(tbl200Entity.getSlopeCode());
                rp010Vo.setRailingHaveChk(tbl200Entity.getRailingCode());
                rp010Vo.setRailingNotChk(tbl200Entity.getRailingCode());
                rp010Vo.setElevatorHaveChk(tbl200Entity.getElevatorCode());
                rp010Vo.setElevatorNotChk(tbl200Entity.getElevatorCode());
                rp010Vo.setLedHaveChk(tbl200Entity.getLedCode());
                rp010Vo.setLedNotChk(tbl200Entity.getLedCode());
                rp010Vo.setHeatShieldingHaveChk(tbl200Entity.getHeatShieldingCode());
                rp010Vo.setHeatShieldingNotChk(tbl200Entity.getHeatShieldingCode());
                rp010Vo.setEquipmentChargeHaveChk(tbl200Entity.getEquipmentChargeCode());
                rp010Vo.setEquipmentChargeNotChk(tbl200Entity.getEquipmentChargeCode());
                rp010Vo.setCommunityHaveChk(tbl200Entity.getCommunityCode());
                rp010Vo.setCommunityNotChk(tbl200Entity.getCommunityCode());
                rp010Vo.setContactPropertyChairmanChk(tbl200Entity.getContactPropertyCode());
                rp010Vo.setContactPropertyDistinguishingChk(tbl200Entity.getContactPropertyCode());
                rp010Vo.setContactPropertyApartmentChk(tbl200Entity.getContactPropertyCode());
                rp010Vo.setContactPropertyElseChk(tbl200Entity.getContactPropertyCode());
                rp010Vo.setContactPropertyElse(tbl200Entity.getContactPropertyElse());

                // check user type code is mansion
                if (SecurityUtil.getUserLoggedInInfo().getUserTypeCode() == UserTypeCode.MANSION) {
                    rp010Vo.setContractZipCode(ASTERISK);
                    rp010Vo.setContractTelno1(ASTERISK);
                    rp010Vo.setContractTelno2(ASTERISK);
                    rp010Vo.setContractTelno3(ASTERISK);
                    rp010Vo.setContractAddress(ASTERISK);
                    rp010Vo.setContractNamePhonetic(ASTERISK);
                    rp010Vo.setContractName(ASTERISK);
                    rp010Vo.setContractMailAddress(ASTERISK);
                } else {
                    rp010Vo.setContractZipCode(tbl200Entity.getContactZipCode());
                    String[] contractTelNo = tbl200Entity.getContactTelNo().split("-");
                    rp010Vo.setContractTelno1(contractTelNo[CommonConstants.NUM_0]);
                    rp010Vo.setContractTelno2(contractTelNo[CommonConstants.NUM_1]);
                    rp010Vo.setContractTelno3(contractTelNo[CommonConstants.NUM_2]);
                    rp010Vo.setContractAddress(tbl200Entity.getContactAddress());
                    rp010Vo.setContractNamePhonetic(tbl200Entity.getContactNamePhonetic());
                    rp010Vo.setContractName(tbl200Entity.getContactName());
                    rp010Vo.setContractMailAddress(tbl200Entity.getContactMailAddress());
                }

                if (documentInfoForm.getAcceptTime() != null) {
                    LocalDate receiptDate = documentInfoForm.getAcceptTime().toLocalDate();
                    rp010Vo.setReceiptDate(DateUtil.getFormatJapaneseDateForShow(receiptDate));
                } else {
                    rp010Vo.setReceiptDate(StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, CommonConstants.NUM_4)
                                          + CommonConstants.YEAR_STRING + StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, CommonConstants.NUM_2)
                                          + CommonConstants.MONTH_STRING + StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, CommonConstants.NUM_2)
                                          + CommonConstants.DAY_STRING);
                }

                rp010Vo.setReceiptPersonInCharge(documentInfoForm.getReceiptPersonInCharge());
                rp010Vo.setReceiptNote(documentInfoForm.getReceiptNote());
                rp010Vo.setChangeReason(CHANGE_REASON);
                rp010Vo.setChangeChk(tbl200Entity.getChangeReasonCode());
                rp010Vo.setChangeLbl(CHANGE);
                rp010Vo.setLostElseReasonChk(tbl200Entity.getChangeReasonCode());
                rp010Vo.setLostElseReasonLbl(LOST_ELSE_REASON);
                rp010Vo.setExceptChk(tbl200Entity.getLostElseReasonCode());
                rp010Vo.setExceptLbl(EXCEPT);
                rp010Vo.setLostChk(tbl200Entity.getLostElseReasonCode());
                rp010Vo.setLostLbl(LOST);
                rp010Vo.setElseChk(tbl200Entity.getLostElseReasonCode());
                rp010Vo.setElseLbl(ELSE);
                if (tbl200Entity.getLostElseReasonElse() != null) {
                    rp010Vo.setElseDetail(BRACKETS_OPEN_1 + tbl200Entity.getLostElseReasonElse()
                                          + BRACKETS_CLOSE_1);
                } else {
                    rp010Vo.setElseDetail(BRACKETS_OPEN_1 + StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, 6) + BRACKETS_CLOSE_1);
                }
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp010Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public RP020Vo getReportRP020(String acceptNo, String preScreenId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            TBL210Entity tbl210Entity = tbl210DAO.getAcceptByAcceptNo(acceptNo);
            RP020Vo rp020Vo = new RP020Vo();

            if (tbl210Entity != null) {
                //Copy properties
                CommonUtils.copyProperties(rp020Vo, tbl210Entity);

                String noticeDate = DateUtil.getFormatJapaneseDateForShow(tbl210Entity.getNoticeDate());
                rp020Vo.setNoticeDate(noticeDate);

                // set stamp name by id screen GEA0110 or MEA0110
                if (CommonConstants.SCREEN_ID_GEA0110.equals(preScreenId)
                    || CommonConstants.SCREEN_ID_MEA0110.equals(preScreenId)) {
                    rp020Vo.setStampName(CommonConstants.BLANK);
                } else {
                    rp020Vo.setStampName(STAMP_NAME);
                }

                String notificationDate = DateUtil.getFormatJapaneseDateForShow(tbl210Entity.getNotificationDate());
                rp020Vo.setNotificationDate(notificationDate);
                rp020Vo.setEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl210Entity.getEvidenceBar()));
                if (CommonConstants.STR_1.equals(tbl210Entity.getEvidenceBar())) {
                    rp020Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD042,tbl210Entity.getEvidenceNo()));
                } else {
                    rp020Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD043,tbl210Entity.getEvidenceNo()));
                }
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp020Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public RP030Vo getReportRP030(String adviceNo, String preScreenId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            TBL220Entity tbl220Entity = tbl220DAO.getAdvisoryNoticeById(adviceNo);
            RP030Vo rp030Vo = new RP030Vo();

            if (tbl220Entity != null) {
                //Copy properties
                CommonUtils.copyProperties(rp030Vo, tbl220Entity);
                rp030Vo.setAppendixNo(tbl220Entity.getAppendixNo());
                rp030Vo.setDocumentNo(tbl220Entity.getDocumentNo());
                rp030Vo.setNoticeDate(convertDateToJpString(tbl220Entity.getNoticeDate()));
                rp030Vo.setRecipientNameApartment(tbl220Entity.getRecipientNameApartment());
                rp030Vo.setRecipientNameUser(tbl220Entity.getRecipientNameUser());
                rp030Vo.setSender(tbl220Entity.getSender());

                // 前画面IDがMEA0110やGEA0110の場合、本項目は非表示
                if (CommonConstants.SCREEN_ID_GEA0110.equals(preScreenId)
                    || CommonConstants.SCREEN_ID_MEA0110.equals(preScreenId)) {
                    rp030Vo.setStampName(CommonConstants.BLANK);
                } else {
                    rp030Vo.setStampName(STAMP_NAME);
                }
                rp030Vo.setNotificationDate(convertDateToJpString(tbl220Entity.getNotificationDate()));
                rp030Vo.setEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl220Entity.getEvidenceBar()));
                if (CommonConstants.STR_1.equals(tbl220Entity.getEvidenceBar())) {
                    rp030Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD042,tbl220Entity.getEvidenceNo()));
                } else {
                    rp030Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD043,tbl220Entity.getEvidenceNo()));
                }
                rp030Vo.setAddress(tbl220Entity.getAddress());
                rp030Vo.setApartmentName(tbl220Entity.getApartmentName());
                rp030Vo.setAdviceDetails(tbl220Entity.getAdviceDetails());
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp030Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public RP040Vo getReportRP040(String inveseNo, String preScreenId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            RP040Vo rp040Vo = new RP040Vo();
            TBL230Entity tbl230Entity = tbl230DAO.getSurveyById(inveseNo);

            if (tbl230Entity != null) {
                //Copy properties
                CommonUtils.copyProperties(rp040Vo, tbl230Entity);
                /* 通知年月日 */
                rp040Vo.setNoticeDate(convertDateToJpString(tbl230Entity.getNoticeDate()));

                // 前画面IDがMEA0110やGEA0110の場合、本項目は非表示
                if (CommonConstants.SCREEN_ID_GEA0110.equals(preScreenId)
                    || CommonConstants.SCREEN_ID_MEA0110.equals(preScreenId)) {
                    rp040Vo.setStamp(CommonConstants.BLANK);
                } else {
                    rp040Vo.setStamp(STAMP_NAME);
                }
                rp040Vo.setTextMailing1(tbl230Entity.getTextaddress());

                /* 調査の実施予定日時 */
                if (StringUtils.isNotEmpty(tbl230Entity.getInvestImplPlanTime())) {
                    LocalDateTime planDate = LocalDateTime.parse(tbl230Entity.getInvestImplPlanTime(),
                                                                 DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDDHHMM));
                    String financialYear =  DateUtil.getEraNameFiscalYearAsString(planDate.toLocalDate());
                    StringBuilder planDateTime = new StringBuilder();
                    planDateTime.append(financialYear);
                    planDateTime.append(StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, CommonConstants.NUM_2));
                    planDateTime.append(planDate.getMonthValue());
                    planDateTime.append(CommonConstants.MONTH_STRING);
                    planDateTime.append(StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, CommonConstants.NUM_2));
                    planDateTime.append(planDate.getDayOfMonth());
                    planDateTime.append(CommonConstants.DAY_STRING);
                    planDateTime.append(BRACKETS_OPEN);
                    planDateTime.append(DateUtil.getDayOfWeekInJapanese(planDate.toLocalDate()).charAt(CommonConstants.NUM_0));
                    planDateTime.append(BRACKETS_CLOSE);
                    rp040Vo.setInvestImplPlanDatetime(planDateTime.toString());
                    // get HH12|(AM|PM)
                    String[] hh12Format = planDate.format(DateTimeFormatter.ofPattern("hh;a")).split(";");
                    if ("AM".equals(hh12Format[CommonConstants.NUM_1])) {
                        // 午前・午後
                        rp040Vo.setIsAm(STR_TRUE);
                    } else {
                        // 午前・午後
                        rp040Vo.setIsAm(STR_FLASE);
                    }

                    String planTwo = StringUtils.repeat(CommonConstants.SPACE_FULL_SIZE, 2) + hh12Format[CommonConstants.NUM_0] + FROM_TIME;
                    rp040Vo.setInvestImplPlanDatetimeTwo(planTwo);
                }
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp040Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public RP050Vo getReportRP050(String supervisedNoticeNo, String preScreenId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            TBL240Entity tbl240Entity = tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(supervisedNoticeNo);
            RP050Vo rp050Vo = new RP050Vo();
            if (tbl240Entity != null) {
                /* Copy properties */
                CommonUtils.copyProperties(rp050Vo, tbl240Entity);

                rp050Vo.setTxtAppendixNo(tbl240Entity.getAppendixNo());

                /* 通知年月日 */
                rp050Vo.setNoticeDate(convertDateToJpString(tbl240Entity.getNoticeDate()));

                /* 前画面IDがMEA0110やGEA0110の場合、本項目は非表示  */
                if (CommonConstants.SCREEN_ID_GEA0110.equals(preScreenId)
                    || CommonConstants.SCREEN_ID_MEA0110.equals(preScreenId)) {
                    rp050Vo.setStampName(CommonConstants.BLANK);
                } else {
                    rp050Vo.setStampName(STAMP_NAME);
                }
                rp050Vo.setPeriodEvidence(CodeUtil.getLabel(CommonConstants.CD045, tbl240Entity.getPeriodEvidence()));
                rp050Vo.setTextMailing1(tbl240Entity.getTextaddress1());
                rp050Vo.setTextMailing2(tbl240Entity.getTextaddress2());
                rp050Vo.setEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl240Entity.getEvidenceBar()));
                if (CommonConstants.STR_1.equals(tbl240Entity.getEvidenceBar())) {
                    rp050Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD044, tbl240Entity.getEvidenceNo()));
                } else {
                    rp050Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD043, tbl240Entity.getEvidenceNo()));
                }
                /* 提出期限 */
                rp050Vo.setNotificationTimelimit(convertDateToJpString(tbl240Entity.getNotificationTimelimit()));
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp050Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    @Override
    public RP060Vo getReportRP060(String supervisedNoticeNo, String preScreenId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            TBL240Entity tbl240Entity = tbl240DAO.getSupervisedNoticeBySupervisedNoticeNo(supervisedNoticeNo);
            RP060Vo rp060Vo = new RP060Vo();
            if (tbl240Entity != null) {
                /* Copy properties */
                CommonUtils.copyProperties(rp060Vo, tbl240Entity);
                rp060Vo.setTxtAppendixNo(tbl240Entity.getAppendixNo());

                /* 通知年月日 */
                rp060Vo.setNoticeDate(convertDateToJpString(tbl240Entity.getNoticeDate()));

                /* 前画面IDがMEA0110やGEA0110の場合、本項目は非表示  */
                if (CommonConstants.SCREEN_ID_GEA0110.equals(preScreenId)
                    || CommonConstants.SCREEN_ID_MEA0110.equals(preScreenId)) {
                    rp060Vo.setStamp(CommonConstants.BLANK);
                } else {
                    rp060Vo.setStamp(STAMP_NAME);
                }
                rp060Vo.setPeriodEvidence(CodeUtil.getLabel(CommonConstants.CD045, tbl240Entity.getPeriodEvidence()));
                String lastNoticeDate = convertDateToJpString(tbl240Entity.getLastNoticeDate());
                rp060Vo.setLastNoticeDate(lastNoticeDate);
                rp060Vo.setTextMailing1(tbl240Entity.getTextaddress1());
                rp060Vo.setTextMailing2(tbl240Entity.getTextaddress2());
                rp060Vo.setEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl240Entity.getEvidenceBar()));
                if (CommonConstants.STR_1.equals(tbl240Entity.getEvidenceBar())) {
                    rp060Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD044, tbl240Entity.getEvidenceNo()));
                } else {
                    rp060Vo.setEvidenceNo(CodeUtil.getLabel(CommonConstants.CD043, tbl240Entity.getEvidenceNo()));
                }
                /* 提出期限 */
                rp060Vo.setNotificationTimelimit(convertDateToJpString(tbl240Entity.getNotificationTimelimit()));
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return rp060Vo;
        } catch (SystemException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * convert string fomart to date format Japan
     * @param date String
     * @return
     */
    private String convertDateToJpString(String dateString) throws BusinessException {
        try {
            // covert String to localdate with format yyyyMMdd
            LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));
            return DateUtil.getFormatJapaneseDateForShow(localDate);
        } catch (DateTimeParseException ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException();
        }
    }

}
