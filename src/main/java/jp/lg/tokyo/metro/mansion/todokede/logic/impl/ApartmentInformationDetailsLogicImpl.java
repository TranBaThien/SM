/*
 * @(#) ApartmentInformationDetailsLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_ACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.DASH;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_0;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_1;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_2;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.STR_1;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.STR_2;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.STR_3;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.STR_8;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.STR_9;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.Status;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL210DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL230DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL210Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IApartmentInformationDetailsLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentBtnStatusVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentInformationDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentStatusInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentUserInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.BasicReportInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoAreaCommonVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationRegistrationVo;

@Service
public class ApartmentInformationDetailsLogicImpl implements IApartmentInformationDetailsLogic {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String RE_FORMAT_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";

    @Autowired
    TBL200DAO tbl200DAO;

    @Autowired
    TBL100DAO tbl100DAO;

    @Autowired
    TBL210DAO tbl210DAO;

    @Autowired
    TBL230DAO tbl230DAO;

    @Autowired
    TBL240DAO tbl240DAO;

    @Autowired
    TBL110DAO tbl110DAO;

    @Autowired
    private TBM001DAO tbm001DAO;

    private static final Logger logger = LogManager.getLogger(ApartmentInformationDetailsLogicImpl.class);
    
    @Override
    public ApartmentInformationDetailsVo getMansionInformation(String apartmentId, String notificationNo)
            throws BusinessException, InvocationTargetException, IllegalAccessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ApartmentInformationDetailsVo vo = new ApartmentInformationDetailsVo();
        TBL100Entity tbl100Entity = tbl100DAO.getMansionInformationById(apartmentId);
        if (ObjectUtils.isEmpty(tbl100Entity)) {
            throw new BusinessException();
        }
        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
        TBL200Entity tbl200Entity = tbl200DAO.getNotificationByNotificationNoAndApartmentID(apartmentId, notificationNo);
        if (ObjectUtils.isEmpty(tbl110Entity) || ObjectUtils.isEmpty(tbl200Entity)) {
            throw new BusinessException();
        }
        TBL210Entity tbl210Entity = tbl210DAO.getAcceptByNotificationNo(notificationNo);
        String modifyDetails = Optional.ofNullable(tbl210Entity).map(TBL210Entity::getModifyDetails).orElse(null);
        vo.setApartmentStatusInfoVo(getApartmentStatusInfo(apartmentId,tbl100Entity, tbl200Entity, modifyDetails));
        vo.setApartmentBtnStatusVo(getApartmentBtnStatus(apartmentId,tbl100Entity, tbl200Entity,tbl110Entity, vo.getApartmentStatusInfoVo()));
        vo.setNotificationRegistrationVo(getNotificationRegistration(tbl100Entity, tbl200Entity));
        vo.setApartmentUserInfoVo(getUserInformation(apartmentId));
        vo.setCorrectionDetails(modifyDetails);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    private ApartmentStatusInfoVo getApartmentStatusInfo(String apartmentId, TBL100Entity tbl100Entity,
            TBL200Entity tbl200Entity, String modifyDetails) throws  InvocationTargetException, IllegalAccessException {
        ApartmentStatusInfoVo statusInfoVo = new ApartmentStatusInfoVo();
        //Set noti status
        if (tbl100Entity.getNextNotificationDate() != null && LocalDate.now().isBefore(tbl100Entity.getNextNotificationDate())) {
            statusInfoVo.setLblNotificationStatus(Status.REPORTED.getStatus());
        } else {
            statusInfoVo.setLblNotificationStatus(Status.UNREPORTED.getStatus());
        }

        List<CodeVo> cd030 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD030));
//        TBL210Entity tbl210Entity = tbl210DAO.getAcceptByNotificationNo(tbl100Entity.getNewestNotificationNo());
        // Set accept status
        String acceptStatus;

        if (Status.UNREPORTED.getStatus().equals(statusInfoVo.getLblNotificationStatus())) {
            acceptStatus = getLabelByValue(cd030, STR_1);
        } else {
            if (StringUtils.isNotBlank(modifyDetails)) {
                acceptStatus = getLabelByValue(cd030, tbl200Entity.getAcceptStatus()) + Status.AUTHORITY_CORRECTION.getStatus();
            } else {
                acceptStatus = getLabelByValue(cd030, tbl200Entity.getAcceptStatus());
            }
        }
        statusInfoVo.setLblAcceptedStatus(acceptStatus);

        //Set support status
       // List<CodeVo> lstAcceptedStatus = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD021));
        
       // statusInfoVo.setLblSupportStatus(getLabelByValue(lstAcceptedStatus, tbl100Entity.getSupport()));
        statusInfoVo.setLblSupportStatus(CodeUtil.getLabel(CommonConstants.CD021, tbl100Entity.getSupport()));

        //Set urge status
        List<TBL240Entity> tbl240Entities = tbl240DAO.getSupervisedNoticeByApartmentId(apartmentId);

        List<CodeVo> cd048 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD048));

        String urgeStatus;
        if (ObjectUtils.isEmpty(tbl240Entities)) {
            urgeStatus = getLabelByValue(cd048, STR_1);
        } else if (tbl240Entities.size() == 1) {
            urgeStatus = getLabelByValue(cd048, STR_2);
        } else {
            urgeStatus = getLabelByValue(cd048, STR_3);
        }
        statusInfoVo.setLblUrgeStatus(urgeStatus);

        //Set advice status
        List<CodeVo> cd037 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD037));
        String adviceStatus = BLANK;
        if (statusInfoVo.getLblAcceptedStatus() != null && statusInfoVo.getLblAcceptedStatus().equals(getLabelByValue(cd030, STR_1))) {
            adviceStatus = getLabelByValue(cd037, STR_1);
        } else if (Status.REPORTED.getStatus().equals(statusInfoVo.getLblNotificationStatus())) {
            if (STR_2.equals(tbl200Entity.getAdviceDoneFlag())) {
                adviceStatus = getLabelByValue(cd037, STR_1);
            } else {
                adviceStatus = getLabelByValue(cd037, STR_2);
            }
        }
        statusInfoVo.setLblAdviceStatus(adviceStatus);

        List<TBL230Entity> tbl230Entities = tbl230DAO.getSurveysByApartmentId(apartmentId);

        String surveyStatus;
        if (ObjectUtils.isEmpty(tbl230Entities)) {
            surveyStatus = getLabelByValue(cd037, STR_1);
        } else {
            surveyStatus = getLabelByValue(cd037, STR_2);
        }
        statusInfoVo.setLblSurveyStatus(surveyStatus);
        return statusInfoVo;
    }

    private ApartmentBtnStatusVo getApartmentBtnStatus(String apartmentId, TBL100Entity tbl100Entity,
            TBL200Entity tbl200Entity, TBL110Entity tbl110Entity, ApartmentStatusInfoVo statusInfoVo) {
        ApartmentBtnStatusVo vo = new ApartmentBtnStatusVo();
        boolean isAdvice1 = false;
        boolean isAdvice2 = false;
        vo.setApartmentId(apartmentId);
        vo.setNewestNotificationNo(tbl100Entity.getNewestNotificationNo());

        if (Status.UNREPORTED.getStatus().equals(statusInfoVo.getLblNotificationStatus())) {
            vo.setBtnNotificationActive(false);
            vo.setBtnDemandActive(false);
        } else {
            vo.setBtnNotificationActive(true);
            vo.setBtnDemandActive(true);
        }
        if (StringUtils.isNotEmpty(statusInfoVo.getLblAcceptedStatus()) && !statusInfoVo.getLblAcceptedStatus().contains(CD030_ACCEPTED)) {
            vo.setBtnNotificationChangeActive(true);
        } else {
            isAdvice1 = true;
            vo.setBtnNotificationChangeActive(false);
        }
        if (!STR_1.equals(tbl200Entity.getAdviceDoneFlag())) {
            isAdvice2 = true;
        }
        if (isAdvice1 && isAdvice2) {
            vo.setBtnAdviceActive(false);
        } else {
            vo.setBtnAdviceActive(true);
        }
        if (!STR_1.equals(SecurityUtil.getUserLoggedInInfo().getUserAuthority())) {
            vo.setBtnInvestigationShow(false);
            vo.setBtnAdviceShow(false);
        } else {
            vo.setBtnAdviceShow(true);
            vo.setBtnInvestigationShow(true);
        }

        if (!STR_2.equals(tbl110Entity.getValidityFlag())) {
            vo.setBtnLoginIdActivationActive(true);
        } else {
            vo.setBtnLoginIdActivationActive(false);
        }

        if (!STR_2.equals(tbl110Entity.getAvailability())) {
            vo.setBtnResumeActive(true);
            vo.setBtnSuspensionActive(false);
        } else {
            vo.setBtnSuspensionActive(true);
            vo.setBtnResumeActive(false);
        }

        if (UserTypeCode.CITY.equals(SecurityUtil.getUserLoggedInInfo().getUserTypeCode())) {
            vo.setBtnLoginIdReissueShow(false);
        } else {
            vo.setBtnLoginIdReissueShow(true);
        }
        return vo;
    }
    
    /**
     * Get notification for Registration
     * @param tbl100Entity
     * @param tbl200Entity
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private NotificationRegistrationVo getNotificationRegistration(TBL100Entity tbl100Entity, TBL200Entity tbl200Entity)
            throws InvocationTargetException, IllegalAccessException {
        NotificationRegistrationVo vo = new NotificationRegistrationVo();
        //Set flag for display received number
        vo.setScreenGJA0120(true);
        BasicReportInfoVo reportInfoVo = new BasicReportInfoVo();
        if (StringUtils.isBlank(tbl200Entity.getApartmentName())) {
            reportInfoVo.setTxtApartmentName(tbl100Entity.getApartmentName());
        } else {
            reportInfoVo.setTxtApartmentName(tbl200Entity.getApartmentName());
        }
        if (StringUtils.isBlank(tbl200Entity.getApartmentNamePhonetic())) {
            reportInfoVo.setTxtApartmentNamePhonetic(tbl100Entity.getApartmentNamePhonetic());
        } else {
            reportInfoVo.setTxtApartmentNamePhonetic(tbl200Entity.getApartmentNamePhonetic());
        }
        if (StringUtils.isBlank(tbl200Entity.getZipCode())) {
            //Split Zip Code by Dash
            String[] zipCode = CommonUtils.lstZipCode(tbl100Entity.getZipCode());
            reportInfoVo.setTxtApartmentZipCode1(zipCode[NUM_0]);
            reportInfoVo.setTxtApartmentZipCode2(zipCode[NUM_1]);
        } else {
            String[] zipCode = CommonUtils.lstZipCode(tbl200Entity.getZipCode());
            reportInfoVo.setTxtApartmentZipCode1(zipCode[NUM_0]);
            reportInfoVo.setTxtApartmentZipCode2(zipCode[NUM_1]);
        }

        TBM001Entity tbm001Entity = tbm001DAO.getMunicipalMasterInfo(tbl100Entity.getCityCode());
        reportInfoVo.setLblApartmentAddress1(tbm001Entity.getCityName());
        if (StringUtils.isBlank(tbl200Entity.getAddress())) {
            reportInfoVo.setTxtApartmentAddress2(tbl100Entity.getAddress());
        } else {
            reportInfoVo.setTxtApartmentAddress2(tbl200Entity.getAddress());
        }
        vo.setBasicReportInfo(reportInfoVo);

        NotificationInfoAreaCommonVo areaCommonVo = new NotificationInfoAreaCommonVo();
        areaCommonVo.setInActiveItem(true);
        areaCommonVo.setLblReceiptNumber(tbl200Entity.getReceptionNo());
        areaCommonVo.setCalNotificationDate(!ObjectUtils.isEmpty(tbl200Entity.getNotificationDate()) ? DateTimeUtil.getLocalDateAsString2(tbl200Entity.getNotificationDate()) : CommonConstants.BLANK);
        areaCommonVo.setTxtNotificationGroupName(tbl200Entity.getNotificationGroupName());
        areaCommonVo.setTxtNotificationPersonName(tbl200Entity.getNotificationPersonName());
        areaCommonVo.setLstNotificationType(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD003)));
        areaCommonVo.setRdoNotificationType(tbl200Entity.getNotificationType());
        areaCommonVo.setLstChangeReasonCode(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD004)));
        areaCommonVo.setRdoChangeReason(tbl200Entity.getChangeReasonCode());
        areaCommonVo.setLstLostElseReasonCode(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD040)));
        if (STR_2.equals(tbl200Entity.getChangeReasonCode())) {
            areaCommonVo.setRdoLostElseReasonCode(tbl200Entity.getLostElseReasonCode());
            if (STR_9.equals(tbl200Entity.getLostElseReasonCode())) {
                areaCommonVo.setTxtLostElseReasonElse(tbl200Entity.getLostElseReasonElse());
            }
        }
        areaCommonVo.setLstGroupYesno(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD014)));
        if (StringUtils.isBlank(tbl200Entity.getGroupYesnoCode())) {
            areaCommonVo.setRdoGroupYesno(STR_9);
        } else {
            areaCommonVo.setRdoGroupYesno(tbl200Entity.getGroupYesnoCode());
        }
        areaCommonVo.setTxtApartmentNumber(CommonUtils.formatNumber(tbl200Entity.getApartmentNumber()));
        areaCommonVo.setLstGroupForm(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD005)));
        if (StringUtils.isBlank(tbl200Entity.getGroupForm())) {
            areaCommonVo.setRdoGroupForm(STR_9);
        } else {
            areaCommonVo.setRdoGroupForm(tbl200Entity.getGroupForm());
            if (STR_8.equals(tbl200Entity.getGroupForm())) {
                areaCommonVo.setTxtGroupFormElse(tbl200Entity.getGroupFormElse());
            }
        }
        if (tbl200Entity.getHouseNumber() == null) {
            areaCommonVo.setTxtHouseNumber(CommonUtils.formatNumber(tbl100Entity.getHouseNumber()));
        } else {
            areaCommonVo.setTxtHouseNumber(CommonUtils.formatNumber(tbl200Entity.getHouseNumber()));
        }
        if (tbl200Entity.getFloorNumber() == null) {
            areaCommonVo.setTxtFloorNumber(CommonUtils.formatNumber(tbl100Entity.getFloorNumber()));
        } else {
            areaCommonVo.setTxtFloorNumber(CommonUtils.formatNumber(tbl200Entity.getFloorNumber()));
        }
        if (tbl200Entity.getBuiltDate() == null) {
            areaCommonVo.setCalBuiltDate(tbl100Entity.getBuildYear() + "/" + tbl100Entity.getBuildMonth() + "/" + tbl100Entity.getBuildDay());
        } else {
            areaCommonVo.setCalBuiltDate(DateTimeUtil.getLocalDateAsString2(tbl200Entity.getBuiltDate()));
        }
        areaCommonVo.setLstLandRights(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD006)));
        if (StringUtils.isBlank(tbl200Entity.getLandRightsCode())) {
            areaCommonVo.setRdoLandRights(STR_9);
        } else {
            areaCommonVo.setRdoLandRights(tbl200Entity.getLandRightsCode());
            if (STR_8.equals(tbl200Entity.getLandRightsCode())) {
                areaCommonVo.setTxtLandRightsElse(tbl200Entity.getLandRightsElse());
            }
        }
        areaCommonVo.setLstUsefor(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD007)));
        if (StringUtils.isBlank(tbl200Entity.getUseforCode())) {
            areaCommonVo.setRdoUsefor(STR_9);
        } else {
            areaCommonVo.setRdoUsefor(tbl200Entity.getUseforCode());
            if (STR_8.equals(tbl200Entity.getUseforCode())) {
                areaCommonVo.setTxtUseforElse(tbl200Entity.getUseforElse());
            }
        }
        areaCommonVo.setLstManagementForm(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD008)));
        if (StringUtils.isBlank(tbl200Entity.getManagementFormCode())) {
            areaCommonVo.setRdoManagementForm(STR_9);
        } else {
            areaCommonVo.setRdoManagementForm(tbl200Entity.getManagementFormCode());
            if (STR_8.equals(tbl200Entity.getManagementFormCode())) {
                areaCommonVo.setTxtManagementFormElse(tbl200Entity.getManagementFormElse());
            }
        }
        areaCommonVo.setTxtManagerName(tbl200Entity.getManagerName());
        areaCommonVo.setTxtManagerNamePhonetic(tbl200Entity.getManagerNamePhonetic());
        if (StringUtils.isNotBlank(tbl200Entity.getManagerZipCode())) {
            //Split Manager Zip Code by Dash
            String[] mnZipCode = tbl200Entity.getManagerZipCode().split(DASH);
            areaCommonVo.setTxtManagerZipCode1(mnZipCode[NUM_0]);
            areaCommonVo.setTxtManagerZipCode2(mnZipCode[NUM_1]);
        }
        areaCommonVo.setTxtManagerAddress(tbl200Entity.getManagerAddress());
        if (StringUtils.isNotBlank(tbl200Entity.getManagerTelNo())) {
            //Split Manager TelNo by Dash
            String[] mnTelNo = tbl200Entity.getManagerTelNo().split(DASH);
            areaCommonVo.setTxtManagerTelno1(mnTelNo[NUM_0]);
            areaCommonVo.setTxtManagerTelno2(mnTelNo[NUM_1]);
            areaCommonVo.setTxtManagerTelno3(mnTelNo[NUM_2]);
        }
        areaCommonVo.setLstCd053(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD053)));
        if (StringUtils.isBlank(tbl200Entity.getGroupYesnoCode())) {
            areaCommonVo.setRdoGroupYesno(STR_9);
        } else {
            areaCommonVo.setRdoGroupYesno(tbl200Entity.getGroupYesnoCode());
        }
        areaCommonVo.setLstCd009(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD009)));
        areaCommonVo.setRdoManager(tbl200Entity.getManagerCode());
        areaCommonVo.setRdoRule(tbl200Entity.getRuleCode());
        areaCommonVo.setTxtRuleChangeYear(tbl200Entity.getRuleChangeYear());
        areaCommonVo.setRdoOneyearOver(tbl200Entity.getOpenOneyearOver());
        areaCommonVo.setRdoMinutes(tbl200Entity.getMinutes());
        areaCommonVo.setRdoManagementCost(tbl200Entity.getManagementCostCode());
        areaCommonVo.setRdoRepairCost(tbl200Entity.getRepairCostCode());
        areaCommonVo.setTxtRepairMonthlycost(CommonUtils.formatNumber(tbl200Entity.getRepairMonthlyCost()));
        areaCommonVo.setRdoRepairPlan(tbl200Entity.getRepairPlanCode());
        areaCommonVo.setTxtRepairNearestYear(tbl200Entity.getRepairNearestYear());
        List<CodeVo> lstCd002 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD002));
        areaCommonVo.setLstCd002(lstCd002);
        if (StringUtils.isBlank(tbl200Entity.getLongRepairPlanCode())) {
            areaCommonVo.setRdoLongRepairPlan(STR_9);
        } else {
            areaCommonVo.setRdoLongRepairPlan(tbl200Entity.getLongRepairPlanCode());
        }
        areaCommonVo.setTxtLongRepairPlanYear(tbl200Entity.getLongRepairPlanYear());
        areaCommonVo.setTxtLongRepairPlanPeriod(tbl200Entity.getLongRepairPlanPeriodAsString());
        areaCommonVo.setTxtLongRepairPlanYearFrom(tbl200Entity.getLongRepairPlanYearFrom());
        areaCommonVo.setTxtLongRepairPlanYearTo(tbl200Entity.getLongRepairPlanYearTo());
        if (StringUtils.isBlank(tbl200Entity.getArrearageRuleCode())) {
            areaCommonVo.setRdoArrearageRule(STR_9);
        } else {
            areaCommonVo.setRdoArrearageRule(tbl200Entity.getArrearageRuleCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getSegmentCode())) {
            areaCommonVo.setRdoSegment(STR_9);
        } else {
            areaCommonVo.setRdoSegment(tbl200Entity.getSegmentCode());
        }
        areaCommonVo.setLstCd010(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD010)));
        if (StringUtils.isBlank(tbl200Entity.getEmptyPercentCode())) {
            areaCommonVo.setRdoEmptyPercent(STR_9);
        } else {
            areaCommonVo.setRdoEmptyPercent(tbl200Entity.getEmptyPercentCode());
        }
        areaCommonVo.setTxtEmptyNumber(CommonUtils.formatNumber(tbl200Entity.getEmptyNumber()));
        areaCommonVo.setLstCd052(copyListCodeInfos(CodeUtil.getList(CommonConstants.CD052)));
        if (StringUtils.isBlank(tbl200Entity.getRentalPercentCode())) {
            areaCommonVo.setRdoRentalPercent(STR_9);
        } else {
            areaCommonVo.setRdoRentalPercent(tbl200Entity.getRentalPercentCode());
        }
        areaCommonVo.setTxtRentalNumber(CommonUtils.formatNumber(tbl200Entity.getRentalNumber()));
        List<CodeVo> lstCd011 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD011));
        areaCommonVo.setLstCd011(lstCd011);
        if (StringUtils.isBlank(tbl200Entity.getSeismicDiagnosisCode())) {
            areaCommonVo.setRdoSeismicDiagnosis(STR_9);
        } else {
            areaCommonVo.setRdoSeismicDiagnosis(tbl200Entity.getSeismicDiagnosisCode());
        }
        List<CodeVo> lstCd012 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD012));
        areaCommonVo.setLstCd012(lstCd012);
        if (StringUtils.isBlank(tbl200Entity.getEarthquakeResistanceCode())) {
            areaCommonVo.setRdoEarthquakeResistance(STR_9);
        } else {
            areaCommonVo.setRdoEarthquakeResistance(tbl200Entity.getEarthquakeResistanceCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getSeismicRetrofitCode())) {
            areaCommonVo.setRdoSeismicRetrofit(STR_9);
        } else {
            areaCommonVo.setRdoSeismicRetrofit(tbl200Entity.getSeismicRetrofitCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getDesignDocumentCode())) {
            areaCommonVo.setRdoDesignDocument(STR_9);
        } else {
            areaCommonVo.setRdoDesignDocument(tbl200Entity.getDesignDocumentCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getRepairHistoryCode())) {
            areaCommonVo.setRdoRepairHistory(STR_9);
        } else {
            areaCommonVo.setRdoRepairHistory(tbl200Entity.getRepairHistoryCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getVoluntaryOrganizationCode())) {
            areaCommonVo.setRdoVoluntaryOrganization(STR_9);
        } else {
            areaCommonVo.setRdoVoluntaryOrganization(tbl200Entity.getVoluntaryOrganizationCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getDisasterPreventionManualCode())) {
            areaCommonVo.setRdoDisasterPreventionManual(STR_9);
        } else {
            areaCommonVo.setRdoDisasterPreventionManual(tbl200Entity.getDisasterPreventionManualCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getDisasterPreventionStockpileCode())) {
            areaCommonVo.setRdoDisasterPreventionStockpile(STR_9);
        } else {
            areaCommonVo.setRdoDisasterPreventionStockpile(tbl200Entity.getDisasterPreventionStockpileCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getNeedSupportListCode())) {
            areaCommonVo.setRdoNeedSupportList(STR_9);
        } else {
            areaCommonVo.setRdoNeedSupportList(tbl200Entity.getNeedSupportListCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getDisasterPreventionRegularCode())) {
            areaCommonVo.setRdoDisasterPreventionRegular(STR_9);
        } else {
            areaCommonVo.setRdoDisasterPreventionRegular(tbl200Entity.getDisasterPreventionRegularCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getSlopeCode())) {
            areaCommonVo.setRdoSlope(STR_9);
        } else {
            areaCommonVo.setRdoSlope(tbl200Entity.getSlopeCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getRailingCode())) {
            areaCommonVo.setRdoRailing(STR_9);
        } else {
            areaCommonVo.setRdoRailing(tbl200Entity.getRailingCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getElevatorCode())) {
            areaCommonVo.setRdoElevator(STR_9);
        } else {
            areaCommonVo.setRdoElevator(tbl200Entity.getElevatorCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getLedCode())) {
            areaCommonVo.setRdoLed(STR_9);
        } else {
            areaCommonVo.setRdoLed(tbl200Entity.getLedCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getHeatShieldingCode())) {
            areaCommonVo.setRdoHeatShielding(STR_9);
        } else {
            areaCommonVo.setRdoHeatShielding(tbl200Entity.getHeatShieldingCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getEquipmentChargeCode())) {
            areaCommonVo.setRdoEquipmentCharge(STR_9);
        } else {
            areaCommonVo.setRdoEquipmentCharge(tbl200Entity.getEquipmentChargeCode());
        }
        if (StringUtils.isBlank(tbl200Entity.getCommunityCode())) {
            areaCommonVo.setRdoCommunity(STR_9);
        } else {
            areaCommonVo.setRdoCommunity(tbl200Entity.getCommunityCode());
        }
        areaCommonVo.setLstCd013(CodeUtil.getListCodeVo(CommonConstants.CD013));
        if (StringUtils.isNotBlank(tbl200Entity.getContactPropertyCode())) {
            areaCommonVo.setRdoContactProperty(tbl200Entity.getContactPropertyCode());
            if (STR_8.equals(tbl200Entity.getContactPropertyCode())) {
                areaCommonVo.setTxtContactPropertyElse(tbl200Entity.getContactPropertyElse());
            }
        }
        if (StringUtils.isNotBlank(tbl200Entity.getContactZipCode())) {
            //Split Contact Zip Code by Dash
            String[] ctZipCode = tbl200Entity.getContactZipCode().split(DASH);
            areaCommonVo.setTxtContactZipCode1(ctZipCode[NUM_0]);
            areaCommonVo.setTxtContactZipCode2(ctZipCode[NUM_1]);
        }
        areaCommonVo.setTxtContactAddress(tbl200Entity.getContactAddress());
        if (StringUtils.isNotBlank(tbl200Entity.getContactTelNo())) {
            //Split Contact TelNo by Dash
            String[] ctTelNo = tbl200Entity.getContactTelNo().split(DASH);
            areaCommonVo.setTxtContactTelno1(ctTelNo[NUM_0]);
            areaCommonVo.setTxtContactTelno2(ctTelNo[NUM_1]);
            areaCommonVo.setTxtContactTelno3(ctTelNo[NUM_2]);
        }
        areaCommonVo.setTxtContactName(tbl200Entity.getContactName());
        areaCommonVo.setTxtContactNamePhonetic(tbl200Entity.getContactNamePhonetic());
        areaCommonVo.setTxtContactMail(tbl200Entity.getContactMailAddress());
        areaCommonVo.setTxtContactMailConfirm(BLANK);
        areaCommonVo.setTxaOptional(tbl200Entity.getOptional());

        vo.setInfoAreaCommon(areaCommonVo);
        return vo;
    }

    private String getLabelByValue(List<CodeVo> codeVos, String code) {
        List<CodeVo> codeVos1 = codeVos.stream().filter(item -> item.getValue().equals(code)).collect(Collectors.toList());
        if (codeVos1 == null || codeVos1.isEmpty()) {
            return null;
        }
        return codeVos1.get(0).getLabel();
    }

    private <T, K> List<T> copyListCodeInfos(List<K> lstSource)
            throws IllegalAccessException, InvocationTargetException {
        if (!CollectionUtils.isEmpty(lstSource)) {
            List<T> lstDest = new ArrayList<T>();
            int size = lstSource.size();
            for (int i = 0; i < size; i++) {
                @SuppressWarnings("unchecked")
            T vo = (T)new CodeVo();
                BeanUtils.copyProperties(vo, lstSource.get(i));

                lstDest.add(vo);
            }
            return lstDest;
        }
        return new ArrayList<T>();
    }


    @Override
    public String getMansionInfoUpdateTime(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL100Entity entity = tbl100DAO.getMansionInformationById(apartmentId);
        if (Objects.nonNull(entity)) {
            return new SimpleDateFormat(TIME_FORMAT).format(entity.getUpdateDatetime());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    @Override
    public ApartmentUserInfoVo getUserInformation(String apartmentId) throws BusinessException, InvocationTargetException, IllegalAccessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ApartmentUserInfoVo vo = new ApartmentUserInfoVo();
        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
        if (Objects.isNull(tbl110Entity)) {
            throw new BusinessException();
        }
        vo.setUpdateDatetime(new SimpleDateFormat(TIME_FORMAT).format(tbl110Entity.getUpdateDatetime()));
        vo.setLblLoginid(tbl110Entity.getLoginId());

        List<CodeVo> cd023 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD023));
        vo.setLblUserStatus(getLabelByValue(cd023, tbl110Entity.getValidityFlag()));

        List<CodeVo> cd024 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD024));
        vo.setLblValidity(getLabelByValue(cd024, tbl110Entity.getAvailability()));

        List<CodeVo> cd026 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD026));
        vo.setLblLoginStatus(getLabelByValue(cd026, tbl110Entity.getLoginStatusFlag()));

        if (tbl110Entity.getLastTimeLoginTime() != null) {
            vo.setLblFinalLoginDate(!ObjectUtils.isEmpty(tbl110Entity.getLastTimeLoginTime()) ? DateTimeUtil.formatDateTime(tbl110Entity.getLastTimeLoginTime(), DateTimeFormatter.ofPattern(RE_FORMAT_YYYYMMDDHHMM)) : StringUtils.EMPTY);
        }
        vo.setLblLoginErrorCount(tbl110Entity.getLoginErrorCount() + "回");
        vo.setLblFinalPwdDate(!ObjectUtils.isEmpty(tbl110Entity.getPasswordPeriod()) ? DateTimeUtil.formatDateTime(tbl110Entity.getPasswordPeriod(), DateTimeFormatter.ofPattern(RE_FORMAT_YYYYMMDDHHMM)): StringUtils.EMPTY);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @Override
    public void enableLoginID(String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
        if (Objects.isNull(tbl110Entity)) {
            throw new BusinessException();
        }
        tbl110Entity.setValidityFlag(CommonConstants.STR_1);
        tbl110Entity.setLoginValidityTime(DateTimeUtil.getCurrentSystemDateTime().toLocalDateTime());
        tbl110Entity.setDeleteFlag(CommonConstants.STR_0);
        tbl110Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        String updateTime = new SimpleDateFormat(TIME_FORMAT).format(DateTimeUtil.getCurrentSystemDateTime());
        tbl110Entity.setUpdateDatetime(DateTimeUtil.convertStringToTimestamp(updateTime));
        tbl110DAO.save(tbl110Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL110, tbl110Entity.getApartmentId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public void resumingUse(String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
        if (ObjectUtils.isEmpty(tbl110Entity)) {
            throw new BusinessException();
        }
        tbl110Entity.setAvailability(CommonConstants.STR_1);
        tbl110Entity.setStopTime(DateTimeUtil.getCurrentSystemDateTime().toLocalDateTime());
        tbl110Entity.setDeleteFlag(CommonConstants.STR_0);
        tbl110Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        String updateTime = new SimpleDateFormat(TIME_FORMAT).format(DateTimeUtil.getCurrentSystemDateTime());
        tbl110Entity.setUpdateDatetime(DateTimeUtil.convertStringToTimestamp(updateTime));
        tbl110DAO.save(tbl110Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL110, tbl110Entity.getApartmentId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public void stopUse(String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
        if (ObjectUtils.isEmpty(tbl110Entity)) {
            throw new BusinessException();
        }
        tbl110Entity.setAvailability(CommonConstants.STR_2);
        tbl110Entity.setStopTime(DateTimeUtil.getCurrentSystemDateTime().toLocalDateTime());
        tbl110Entity.setDeleteFlag(CommonConstants.STR_0);
        tbl110Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        String updateTime = new SimpleDateFormat(TIME_FORMAT).format(DateTimeUtil.getCurrentSystemDateTime());
        tbl110Entity.setUpdateDatetime(DateTimeUtil.convertStringToTimestamp(updateTime));
        tbl110DAO.save(tbl110Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL110, tbl110Entity.getApartmentId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

	@Override
	public ApartmentInformationDetailsVo getMansionInformationGJA0120(MansionInfoVo mansionInfoVo) throws BusinessException, IllegalAccessException, InvocationTargetException {
	    logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName())); 
	    ApartmentInformationDetailsVo vo = new ApartmentInformationDetailsVo();
	        if (ObjectUtils.isEmpty(mansionInfoVo)) {
	            throw new BusinessException();
	        }
	        TBL100Entity tbl100Entity = new TBL100Entity();
	        //Copy data from Object vo to entity
	        BeanUtils.copyProperties(tbl100Entity, mansionInfoVo);
	        tbl100Entity.setSupport(mansionInfoVo.getRdoSupportCode());
	        
	        String apartmentId = mansionInfoVo.getApartmentId();
	        String newestNotificationNo = mansionInfoVo.getNewestNotificationNo();
	        //init data
	        TBL200Entity tbl200Entity = new TBL200Entity();
	        TBL210Entity tbl210Entity = new TBL210Entity();
	        //Check Newest No not null
	        if (StringUtils.isNotEmpty(newestNotificationNo)) {
	        	//届出情報取得
	 	        tbl200Entity = tbl200DAO.getNotificationInfo(newestNotificationNo);
	 	       
	 	        tbl210Entity = tbl210DAO.getAcceptByNotificationNo(newestNotificationNo);
	        }
	        
	        
	        //
	        List<TBL230Entity> lstTbl230Entity = tbl230DAO.getSurveysByApartmentId(apartmentId);
	        vo.setApartmentStatusInfoVo(new ApartmentStatusInfoVo()); 
	        String serveyLabel = CommonConstants.BLANK;
	        String urgeStatus = CommonConstants.BLANK;
	        //デコード（CD021）して設定する
	       // vo.getApartmentStatusInfoVo().setLblSupportStatus(CodeUtil.getLabel(CommonConstants.CD021, mansionInfoVo.getRdoSupportCode()));
	        
	        //レコードなしの場合：未通知（CD037）をデコードして設定する
	        if (CollectionUtils.isEmpty(lstTbl230Entity)) {
	        	serveyLabel = CodeUtil.getLabel(CommonConstants.CD037, CommonConstants.STR_1);
	        } else {//レコードありの場合：通知済（CD037）をデコードして設定する。
	        	serveyLabel = CodeUtil.getLabel(CommonConstants.CD037, CommonConstants.STR_2);
	        }
	        
	        vo.getApartmentStatusInfoVo().setLblSurveyStatus(serveyLabel);
	        
	        List<TBL240Entity> lstTbl240Entity = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(apartmentId);
	        //レコードなしの場合：未通知（CD048）をデコードして設定する。
	        if (CollectionUtils.isEmpty(lstTbl240Entity)) {
	        	urgeStatus = CodeUtil.getLabel(CommonConstants.CD048, CommonConstants.STR_1);
	        } else if (lstTbl240Entity.size() == 1) { // １件の場合：通知済（1回目用）（CD048）をデコードして設定する。
	        	urgeStatus = CodeUtil.getLabel(CommonConstants.CD048, CommonConstants.STR_2);
	        } else { // ２件以上（２件含む）の場合：通知済（2回目以降用）（CD048）をデコードして設定する。
	        	urgeStatus = CodeUtil.getLabel(CommonConstants.CD048, CommonConstants.STR_3);
	        }
	        vo.getApartmentStatusInfoVo().setLblUrgeStatus(urgeStatus);
	        
	        TBL110Entity tbl110Entity = tbl110DAO.getUserByApartmentId(apartmentId);
	        
	        String modifyDetails = Optional.ofNullable(tbl210Entity).map(TBL210Entity::getModifyDetails).orElse(null);
	        vo.setApartmentStatusInfoVo(getApartmentStatusInfo(apartmentId,tbl100Entity, tbl200Entity, modifyDetails));
	        vo.setApartmentBtnStatusVo(getApartmentBtnStatus(apartmentId,tbl100Entity, tbl200Entity,tbl110Entity, vo.getApartmentStatusInfoVo()));
	        vo.setNotificationRegistrationVo(getNotificationRegistration(tbl100Entity, tbl200Entity));
	        vo.setApartmentUserInfoVo(getUserInformation(apartmentId));
	        vo.setCorrectionDetails(modifyDetails);
	        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
	        return vo;
	}
}