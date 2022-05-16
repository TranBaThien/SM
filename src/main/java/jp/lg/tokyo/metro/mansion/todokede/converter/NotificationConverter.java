/*
 * @(#) NotificationInformationConverter.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.converter;

import static java.util.Objects.nonNull;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD002;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD002_HAVE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD004;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD005;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD005_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD006;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD006_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD007;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD007_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008_ENTRUST_ALL;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD008_PARTIALLY_COMMISSIONED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD011_NOT_IMPLEMENTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD013;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD013_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD014;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD014_IT_IS_HOUSING_COMPLEX_MANAGEMENT_UNION;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_UNACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD040;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD040_OTHER;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.DASH;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SCREEN_ID_MDA0110;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import javax.transaction.SystemException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationTemporaryForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.AcceptanceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.BasicReportInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoAreaCommonVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationRegistrationVo;

public class NotificationConverter {

    /**
     * convert to Notification Registration Vo from Mansion info vo and notification vo
     *
     * @param mansionInfoVo      MansionInfoVo
     * @param notificationInfoVo NotificationInfoVo
     * @param idScreen           String
     * @param isRestored         boolean
     * @return NotificationRegistrationVo
     * @throws SystemException when error when exception happen
     */
    public static NotificationRegistrationVo toNotificationRegistrationVo(MansionInfoVo mansionInfoVo,
                                                                          NotificationInfoVo notificationInfoVo,
                                                                          String idScreen,
                                                                          boolean isRestored) throws SystemException {
        NotificationRegistrationVo ntRegistrationVo = new NotificationRegistrationVo();
        if (nonNull(mansionInfoVo) && nonNull(notificationInfoVo)) {
            BasicReportInfoVo basicReportInfoVo = new BasicReportInfoVo();
            NotificationInfoAreaCommonVo areaCommonVo = new NotificationInfoAreaCommonVo();

            basicReportInfoVo.setTxtApartmentName(notificationInfoVo.getApartmentName());
            basicReportInfoVo.setTxtApartmentNamePhonetic(notificationInfoVo.getApartmentNamePhonetic());
            basicReportInfoVo.setTxtApartmentZipCode1(notificationInfoVo.getTxtApartmentZipCode1());
            basicReportInfoVo.setTxtApartmentZipCode2(notificationInfoVo.getTxtApartmentZipCode2());
            basicReportInfoVo.setLblApartmentAddress1(notificationInfoVo.getLblApartmentAddress1());
            basicReportInfoVo.setTxtApartmentAddress2(notificationInfoVo.getTxtApartmentAddress2());
            //Notification info area common
            areaCommonVo.setLblReceiptNumber(notificationInfoVo.getReceptionNo());
            areaCommonVo.setCalNotificationDate(
                    DateTimeUtil.getLocalDateAsString2(SCREEN_ID_MDA0110.equals(idScreen) ?
                            LocalDate.now() :
                            notificationInfoVo.getNotificationDate()));
            areaCommonVo.setTxtNotificationGroupName(notificationInfoVo.getNotificationGroupName());
            areaCommonVo.setTxtNotificationPersonName(notificationInfoVo.getNotificationPersonName());
            areaCommonVo.setLstNotificationType(CodeUtil.getListCodeVo(CommonConstants.CD003));
            areaCommonVo.setRdoNotificationType(notificationInfoVo.getNotificationType());
            areaCommonVo.setLstChangeReasonCode(CodeUtil.getListCodeVo(CommonConstants.CD004));
            areaCommonVo.setRdoChangeReason(notificationInfoVo.getChangeReasonCode());
            areaCommonVo.setLstLostElseReasonCode(CodeUtil.getListCodeVo(CommonConstants.CD040));
            areaCommonVo.setRdoLostElseReasonCode(notificationInfoVo.getLostElseReasonCode());
            areaCommonVo.setTxtLostElseReasonElse(notificationInfoVo.getLostElseReasonElse());
            areaCommonVo.setLstGroupYesno(CodeUtil.getListCodeVo(CommonConstants.CD014));
            areaCommonVo.setRdoGroupYesno(notificationInfoVo.getGroupYesnoCode());
            areaCommonVo.setTxtApartmentNumber(CommonUtils.formatNumber(notificationInfoVo.getApartmentNumber()));
            areaCommonVo.setLstGroupForm(CodeUtil.getListCodeVo(CommonConstants.CD005));
            areaCommonVo.setRdoGroupForm(notificationInfoVo.getGroupForm());
            areaCommonVo.setTxtGroupFormElse(notificationInfoVo.getGroupFormElse());
            areaCommonVo.setTxtHouseNumber(CommonUtils.formatNumber(notificationInfoVo.getHouseNumber()));
            areaCommonVo.setTxtFloorNumber(CommonUtils.formatNumber(notificationInfoVo.getFloorNumber()));
            areaCommonVo.setCalBuiltDate(Objects.nonNull(notificationInfoVo.getBuiltDate()) ? DateTimeUtil.getLocalDateAsString2(notificationInfoVo.getBuiltDate()) : CommonConstants.BLANK);
            areaCommonVo.setLstLandRights(CodeUtil.getListCodeVo(CommonConstants.CD006));
            areaCommonVo.setRdoLandRights(notificationInfoVo.getLandRightsCode());
            areaCommonVo.setTxtLandRightsElse(notificationInfoVo.getLandRightsElse());
            areaCommonVo.setLstUsefor(CodeUtil.getListCodeVo(CommonConstants.CD007));
            areaCommonVo.setRdoUsefor(notificationInfoVo.getUseforCode());
            areaCommonVo.setTxtUseforElse(notificationInfoVo.getUseforElse());
            areaCommonVo.setLstManagementForm(CodeUtil.getListCodeVo(CommonConstants.CD008));
            areaCommonVo.setRdoManagementForm(notificationInfoVo.getManagementFormCode());
            areaCommonVo.setTxtManagementFormElse(notificationInfoVo.getManagementFormElse());
            areaCommonVo.setTxtManagerName(notificationInfoVo.getManagerName());
            areaCommonVo.setTxtManagerNamePhonetic(notificationInfoVo.getManagerNamePhonetic());
            areaCommonVo.setTxtManagerZipCode1(notificationInfoVo.getTxtManagerZipCode1());
            areaCommonVo.setTxtManagerZipCode2(notificationInfoVo.getTxtManagerZipCode2());
            areaCommonVo.setTxtManagerAddress(notificationInfoVo.getManagerAddress());
            areaCommonVo.setTxtManagerTelno1(notificationInfoVo.getTxtManagerTelno1());
            areaCommonVo.setTxtManagerTelno2(notificationInfoVo.getTxtManagerTelno2());
            areaCommonVo.setTxtManagerTelno3(notificationInfoVo.getTxtManagerTelno3());
            areaCommonVo.setLstCd053(CodeUtil.getListCodeVo(CommonConstants.CD053));
            areaCommonVo.setRdoGroup(notificationInfoVo.getGroupCode());
            areaCommonVo.setLstCd009(CodeUtil.getListCodeVo(CommonConstants.CD009));
            areaCommonVo.setRdoManager(notificationInfoVo.getManagerCode());
            areaCommonVo.setRdoRule(notificationInfoVo.getRuleCode());
            areaCommonVo.setTxtRuleChangeYear(notificationInfoVo.getRuleChangeYear());
            areaCommonVo.setRdoOneyearOver(notificationInfoVo.getOpenOneyearOver());
            areaCommonVo.setRdoMinutes(notificationInfoVo.getMinutes());
            areaCommonVo.setRdoManagementCost(notificationInfoVo.getManagementCostCode());
            areaCommonVo.setRdoRepairCost(notificationInfoVo.getRepairCostCode());
            areaCommonVo.setTxtRepairMonthlycost(ObjectUtils.toString(notificationInfoVo.getRepairMonthlyCost()));
            areaCommonVo.setRdoRepairPlan(notificationInfoVo.getRepairPlanCode());
            areaCommonVo.setTxtRepairNearestYear(notificationInfoVo.getRepairNearestYear());
            List<CodeVo> lstCd002 = CodeUtil.getListCodeVo(CommonConstants.CD002);
            areaCommonVo.setLstCd002(lstCd002);
            areaCommonVo.setRdoLongRepairPlan(notificationInfoVo.getLongRepairPlanCode());
            areaCommonVo.setTxtLongRepairPlanYear(notificationInfoVo.getLongRepairPlanYear());
            areaCommonVo.setTxtLongRepairPlanPeriod(ObjectUtils.toString(notificationInfoVo.getLongRepairPlanPeriod()));
            areaCommonVo.setTxtLongRepairPlanYearFrom(notificationInfoVo.getLongRepairPlanYearFrom());
            areaCommonVo.setTxtLongRepairPlanYearTo(notificationInfoVo.getLongRepairPlanYearTo());
            areaCommonVo.setRdoArrearageRule(notificationInfoVo.getArrearageRuleCode());
            areaCommonVo.setRdoSegment(notificationInfoVo.getSegmentCode());
            areaCommonVo.setLstCd010(CodeUtil.getListCodeVo(CommonConstants.CD010));
            areaCommonVo.setRdoEmptyPercent(notificationInfoVo.getEmptyPercentCode());
            areaCommonVo.setTxtEmptyNumber(ObjectUtils.toString(notificationInfoVo.getEmptyNumber()));
            areaCommonVo.setLstCd052(CodeUtil.getListCodeVo(CommonConstants.CD052));
            areaCommonVo.setRdoRentalPercent(notificationInfoVo.getRentalPercentCode());
            areaCommonVo.setTxtRentalNumber(ObjectUtils.toString(notificationInfoVo.getRentalNumber()));
            List<CodeVo> lstCd011 = CodeUtil.getListCodeVo(CommonConstants.CD011);
            areaCommonVo.setLstCd011(lstCd011);
            areaCommonVo.setRdoSeismicDiagnosis(notificationInfoVo.getSeismicDiagnosisCode());
            List<CodeVo> lstCd012 = CodeUtil.getListCodeVo(CommonConstants.CD012);
            areaCommonVo.setLstCd012(lstCd012);
            areaCommonVo.setRdoEarthquakeResistance(notificationInfoVo.getEarthquakeResistanceCode());
            areaCommonVo.setRdoSeismicRetrofit(notificationInfoVo.getSeismicRetrofitCode());
            areaCommonVo.setRdoDesignDocument(notificationInfoVo.getDesignDocumentCode());
            areaCommonVo.setRdoRepairHistory(notificationInfoVo.getRepairHistoryCode());
            areaCommonVo.setRdoVoluntaryOrganization(notificationInfoVo.getVoluntaryOrganizationCode());
            areaCommonVo.setRdoDisasterPreventionManual(notificationInfoVo.getDisasterPreventionManualCode());
            areaCommonVo.setRdoDisasterPreventionStockpile(notificationInfoVo.getDisasterPreventionStockpileCode());
            areaCommonVo.setRdoNeedSupportList(notificationInfoVo.getNeedSupportListCode());
            areaCommonVo.setRdoDisasterPreventionRegular(notificationInfoVo.getDisasterPreventionRegularCode());
            areaCommonVo.setRdoSlope(notificationInfoVo.getSlopeCode());
            areaCommonVo.setRdoRailing(notificationInfoVo.getRailingCode());
            areaCommonVo.setRdoElevator(notificationInfoVo.getElevatorCode());
            areaCommonVo.setRdoLed(notificationInfoVo.getLedCode());
            areaCommonVo.setRdoHeatShielding(notificationInfoVo.getHeatShieldingCode());
            areaCommonVo.setRdoEquipmentCharge(notificationInfoVo.getEquipmentChargeCode());
            areaCommonVo.setRdoCommunity(notificationInfoVo.getCommunityCode());
            areaCommonVo.setLstCd013(CodeUtil.getListCodeVo(CommonConstants.CD013));
            areaCommonVo.setRdoContactProperty((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? null : notificationInfoVo.getContactPropertyCode());
            areaCommonVo.setTxtContactPropertyElse((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getContactPropertyElse());
            areaCommonVo.setTxtContactZipCode1((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getTxtContactZipCode1());
            areaCommonVo.setTxtContactZipCode2((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getTxtContactZipCode2());
            areaCommonVo.setTxtContactAddress((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getContactAddress());
            areaCommonVo.setTxtContactTelno1((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getTxtContactTelno1());
            areaCommonVo.setTxtContactTelno2((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getTxtContactTelno2());
            areaCommonVo.setTxtContactTelno3((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getTxtContactTelno3());
            areaCommonVo.setTxtContactName((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getContactName());
            areaCommonVo.setTxtContactNamePhonetic((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getContactNamePhonetic());
            areaCommonVo.setTxtContactMail((SCREEN_ID_MDA0110.equals(idScreen) && !isRestored) ? BLANK : notificationInfoVo.getContactMailAddress());
            areaCommonVo.setTxtContactMailConfirm(BLANK);
            areaCommonVo.setTxaOptional(notificationInfoVo.getOptional());

            //Set data into whole object
            CommonUtils.copyProperties(ntRegistrationVo, mansionInfoVo);

            ntRegistrationVo.setNotificationNo(notificationInfoVo.getNotificationNo());
            ntRegistrationVo.setChangeCount(notificationInfoVo.getChangeCount());
            ntRegistrationVo.setNotificationCount(notificationInfoVo.getNotificationCount());
            ntRegistrationVo.setNotificationDateTBL200(notificationInfoVo.getNotificationDate());
            ntRegistrationVo.setAcceptStatus(notificationInfoVo.getAcceptStatus());

            //Set basic info
            ntRegistrationVo.setBasicReportInfo(basicReportInfoVo);
            //Register common area
            ntRegistrationVo.setInfoAreaCommon(areaCommonVo);
            ntRegistrationVo.setScreenId(idScreen);
        }
        return ntRegistrationVo;
    }

    /**
     * Convert to acceptance notificationVo
     *
     * @param notificationRegistrationVo NotificationRegistrationVo
     * @param municipalMasterInfoVo      MunicipalMasterInfoVo
     * @param userTypeCode               UserTypeCode
     * @return AcceptanceNotificationVo
     */
    public static AcceptanceNotificationVo toAcceptanceNotificationVo(NotificationRegistrationVo notificationRegistrationVo,
                                                                      MunicipalMasterInfoVo municipalMasterInfoVo,
                                                                      UserTypeCode userTypeCode) {
        final String SECTION_OWNER = "2";
        final String NEW = "1";
        NotificationInfoAreaCommonVo infoAreaCommon = notificationRegistrationVo.getInfoAreaCommon();
        String documentNo = DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting() + "%s　第　号";
        AcceptanceNotificationVo vo = new AcceptanceNotificationVo();
        vo.setTxtAppendixNo("別記第　　号様式（第　　条関係）");
        vo.setTxtDocumentNo(String.format(documentNo, municipalMasterInfoVo.getDocumentNo()));
        vo.setCalNoticeDate(DateTimeUtil.formatDate(LocalDate.now(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        vo.setLblRecipientNameApartment(notificationRegistrationVo.getBasicReportInfo().getTxtApartmentName());
        if (StringUtils.isBlank(infoAreaCommon.getRdoContactProperty())) {
            vo.setLblRecipientNameUser("");
        } else if (infoAreaCommon.getRdoContactProperty().equals(SECTION_OWNER)) {
            vo.setLblRecipientNameUser("区分所有者　" + infoAreaCommon.getTxtNotificationPersonName());
        } else {
            vo.setLblRecipientNameUser("管理組合理事長");
        }

        if (UserTypeCode.CITY == userTypeCode) {
            vo.setTxtSender(municipalMasterInfoVo.getCityName() + "長　" + municipalMasterInfoVo.getChiefName());
        } else {
            vo.setTxtSender("東京都知事　" + municipalMasterInfoVo.getChiefName());
        }
        vo.setLblNotificationDate(infoAreaCommon.getCalNotificationDate());
        if (NEW.equals(infoAreaCommon.getRdoNotificationType())) {
            vo.setLblEvidenceBar("15");
        } else {
            vo.setLblEvidenceBar("16");
        }
        vo.setTxaRemarks("");
        return vo;
    }

    /**
     * to Acceptance NotificationVo from TBL215Entity
     *
     * @param entity TBL215Entity
     * @return AcceptanceNotificationVo
     */
    public static AcceptanceNotificationVo toAcceptanceNotificationVo(TBL215Entity entity) {
        final String NEW_NOTIFICATION = "1";
        AcceptanceNotificationVo vo = new AcceptanceNotificationVo();
        vo.setTxaOrrectionDetails(entity.getModifyDetails());
        vo.setTxtAppendixNo(entity.getAppendixNo());
        vo.setTxtDocumentNo(entity.getDocumentNo());
        vo.setCalNoticeDate(DateTimeUtil.formatDate(entity.getNoticeDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        vo.setLblRecipientNameApartment(entity.getRecipientNameApartment());
        vo.setLblRecipientNameUser(entity.getRecipientNameUser());
        vo.setTxtSender(entity.getSender());
        vo.setLblNotificationDate(DateTimeUtil.formatDate(entity.getNotificationDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        vo.setLblEvidenceBar(NEW_NOTIFICATION.equals(entity.getEvidenceBar()) ? "15" : "16");
        vo.setLstEvidenceNo(entity.getEvidenceNo());
        vo.setTxaRemarks(entity.getRemarks());
        vo.setRdoNotificationMethod(entity.getNotificationMethodCode());
        return vo;
    }

    public static NotificationInfoVo toNotificationInfoVo(NotificationRegistrationForm form) {
        if (Objects.nonNull(form)) {
            NotificationInfoVo vo = new NotificationInfoVo();
            vo.setReceptionNo(form.getInfoAreaCommon().getLblReceiptNumber());
            vo.setNotificationNo(form.getNotificationNo());
            vo.setAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED));
            vo.setApartmentId(form.getApartmentId());
            vo.setApartmentName(form.getBasicReportInfo().getTxtApartmentName());
            vo.setApartmentNamePhonetic(form.getBasicReportInfo().getTxtApartmentNamePhonetic());
            vo.setZipCode(new StringBuilder()
                    .append(form.getBasicReportInfo().getTxtApartmentZipCode1())
                    .append(DASH)
                    .append(form.getBasicReportInfo().getTxtApartmentZipCode2())
                    .toString());
            vo.setCityCode(form.getCityCode());
            vo.setCityName(form.getCityName());
            vo.setAddress(form.getBasicReportInfo().getTxtApartmentAddress2());
            vo.setTxtApartmentAddress2(form.getBasicReportInfo().getTxtApartmentAddress2());
            vo.setNotificationType(form.getInfoAreaCommon().getRdoNotificationType());
            if (isNotBlank(form.getInfoAreaCommon().getCalNotificationDate())) {
                vo.setNotificationDate(DateTimeUtil.getLocalDateFromString2(form.getInfoAreaCommon().getCalNotificationDate()));
            }
            vo.setNotificationGroupName(form.getInfoAreaCommon().getTxtNotificationGroupName());
            vo.setNotificationPersonName(form.getInfoAreaCommon().getTxtNotificationPersonName());
            vo.setAdviceDoneFlag(CodeUtil.getValue(CommonConstants.CD011, CD011_NOT_IMPLEMENTED));
            vo.setChangeCount(form.getChangeCount());
            vo.setNotificationCount(form.getNotificationCount());
            vo.setChangeReasonCode(form.getInfoAreaCommon().getRdoChangeReason());

            if (CodeUtil.getValue(CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(form.getInfoAreaCommon().getRdoChangeReason())) {
                vo.setLostElseReasonCode(form.getInfoAreaCommon().getRdoLostElseReasonCode());
                if (CodeUtil.getValue(CD040, CD040_OTHER).equals(form.getInfoAreaCommon().getRdoLostElseReasonCode())) {
                    vo.setLostElseReasonElse(form.getInfoAreaCommon().getTxtLostElseReasonElse());
                }
            }

            vo.setGroupYesnoCode(form.getInfoAreaCommon().getRdoGroupYesno());
            vo.setGroupForm(form.getInfoAreaCommon().getRdoGroupForm());
            if (CodeUtil.getValue(CD014, CD014_IT_IS_HOUSING_COMPLEX_MANAGEMENT_UNION).equals(form.getInfoAreaCommon().getRdoGroupYesno())) {
                if (isNotBlank(form.getInfoAreaCommon().getTxtApartmentNumber())) {
                    vo.setApartmentNumber(Integer.parseInt(form.getInfoAreaCommon().getTxtApartmentNumber()));
                }
                if (CodeUtil.getValue(CD005, CD005_OTHER).equals(form.getInfoAreaCommon().getRdoGroupForm())) {
                    vo.setGroupFormElse(form.getInfoAreaCommon().getTxtGroupFormElse());
                }
            }

            if (isNotBlank(form.getInfoAreaCommon().getTxtHouseNumber())) {
                vo.setHouseNumber(Integer.parseInt(form.getInfoAreaCommon().getTxtHouseNumber()));
            }
            if (isNotBlank(form.getInfoAreaCommon().getTxtFloorNumber())) {
                vo.setFloorNumber(Integer.parseInt(form.getInfoAreaCommon().getTxtFloorNumber()));
            }
            if (isNotBlank(form.getInfoAreaCommon().getCalBuiltDate())) {
                vo.setBuiltDate(DateTimeUtil.getLocalDateFromString2(form.getInfoAreaCommon().getCalBuiltDate()));
            }

            vo.setLandRightsCode(form.getInfoAreaCommon().getRdoLandRights());
            if (CodeUtil.getValue(CD006, CD006_OTHER).equals(form.getInfoAreaCommon().getRdoLandRights())) {
                vo.setLandRightsElse(form.getInfoAreaCommon().getTxtLandRightsElse());
            }

            vo.setUseforCode(form.getInfoAreaCommon().getRdoUsefor());
            if (CodeUtil.getValue(CD007, CD007_OTHER).equals(form.getInfoAreaCommon().getRdoUsefor())) {
                vo.setUseforElse(form.getInfoAreaCommon().getTxtUseforElse());
            }

            vo.setManagementFormCode(form.getInfoAreaCommon().getRdoManagementForm());
            if (CodeUtil.getValue(CD008, CD008_OTHER).equals(form.getInfoAreaCommon().getRdoManagementForm())) {
                vo.setManagementFormElse(form.getInfoAreaCommon().getTxtManagementFormElse());
            }

            if (CodeUtil.getValue(CD008, CD008_ENTRUST_ALL).equals(form.getInfoAreaCommon().getRdoManagementForm())
                    || CodeUtil.getValue(CD008, CD008_PARTIALLY_COMMISSIONED).equals(form.getInfoAreaCommon().getRdoManagementForm())) {
                vo.setManagerName(form.getInfoAreaCommon().getTxtManagerName());
                vo.setManagerNamePhonetic(form.getInfoAreaCommon().getTxtManagerNamePhonetic());

                String txtManagerZipCode1 = form.getInfoAreaCommon().getTxtManagerZipCode1();
                String txtManagerZipCode2 = form.getInfoAreaCommon().getTxtManagerZipCode2();
                if (StringUtils.isEmpty(txtManagerZipCode1) == StringUtils.isEmpty(txtManagerZipCode2)
                        && !StringUtils.isEmpty(txtManagerZipCode1)) {
                    vo.setManagerZipCode(new StringBuilder()
                            .append(form.getInfoAreaCommon().getTxtManagerZipCode1())
                            .append(DASH)
                            .append(form.getInfoAreaCommon().getTxtManagerZipCode2())
                            .toString());
                }

                vo.setManagerAddress(form.getInfoAreaCommon().getTxtManagerAddress());

                String txtManagerTelno1 = form.getInfoAreaCommon().getTxtManagerTelno1();
                String txtManagerTelno2 = form.getInfoAreaCommon().getTxtManagerTelno2();
                String txtManagerTelno3 = form.getInfoAreaCommon().getTxtManagerTelno3();
                if (!StringUtils.isEmpty(txtManagerTelno1)
                        && !StringUtils.isEmpty(txtManagerTelno2)
                        && !StringUtils.isEmpty(txtManagerTelno3)) {
                    vo.setManagerTelNo(new StringBuilder()
                            .append(form.getInfoAreaCommon().getTxtManagerTelno1())
                            .append(DASH)
                            .append(form.getInfoAreaCommon().getTxtManagerTelno2())
                            .append(DASH)
                            .append(form.getInfoAreaCommon().getTxtManagerTelno3())
                            .toString());
                }
            }

            vo.setGroupCode(form.getInfoAreaCommon().getRdoGroup());
            vo.setManagerCode(form.getInfoAreaCommon().getRdoManager());

            vo.setRuleCode(form.getInfoAreaCommon().getRdoRule());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRule())) {
                vo.setRuleChangeYear(form.getInfoAreaCommon().getTxtRuleChangeYear());
            }

            vo.setOpenOneyearOver(form.getInfoAreaCommon().getRdoOneyearOver());
            vo.setMinutes(form.getInfoAreaCommon().getRdoMinutes());
            vo.setManagementCostCode(form.getInfoAreaCommon().getRdoManagementCost());

            vo.setRepairCostCode(form.getInfoAreaCommon().getRdoRepairCost());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRepairCost())) {
                if (isNotBlank(form.getInfoAreaCommon().getTxtRepairMonthlycost())) {
                    vo.setRepairMonthlyCost(Integer.parseInt(CommonUtils.removeComma(form.getInfoAreaCommon().getTxtRepairMonthlycost())));
                }
            }

            vo.setRepairPlanCode(form.getInfoAreaCommon().getRdoRepairPlan());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRepairPlan())) {
                vo.setRepairNearestYear(form.getInfoAreaCommon().getTxtRepairNearestYear());
            }

            vo.setLongRepairPlanCode(form.getInfoAreaCommon().getRdoLongRepairPlan());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoLongRepairPlan())) {
                vo.setLongRepairPlanYear(form.getInfoAreaCommon().getTxtLongRepairPlanYear());
                if (isNotBlank(form.getInfoAreaCommon().getTxtLongRepairPlanPeriod())) {
                    vo.setLongRepairPlanPeriod(Integer.parseInt(form.getInfoAreaCommon().getTxtLongRepairPlanPeriod()));
                }
                vo.setLongRepairPlanYearFrom(form.getInfoAreaCommon().getTxtLongRepairPlanYearFrom());
                vo.setLongRepairPlanYearTo(form.getInfoAreaCommon().getTxtLongRepairPlanYearTo());
            }

            vo.setArrearageRuleCode(form.getInfoAreaCommon().getRdoArrearageRule());
            vo.setSegmentCode(form.getInfoAreaCommon().getRdoSegment());
            vo.setEmptyPercentCode(form.getInfoAreaCommon().getRdoEmptyPercent());
            if (isNotBlank(form.getInfoAreaCommon().getTxtEmptyNumber())) {
                vo.setEmptyNumber(Integer.parseInt(form.getInfoAreaCommon().getTxtEmptyNumber()));
            }
            vo.setRentalPercentCode(form.getInfoAreaCommon().getRdoRentalPercent());
            if (isNotBlank(form.getInfoAreaCommon().getTxtRentalNumber())) {
                vo.setRentalNumber(Integer.parseInt(form.getInfoAreaCommon().getTxtRentalNumber()));
            }
            vo.setSeismicDiagnosisCode(form.getInfoAreaCommon().getRdoSeismicDiagnosis());
            vo.setEarthquakeResistanceCode(form.getInfoAreaCommon().getRdoEarthquakeResistance());
            vo.setSeismicRetrofitCode(form.getInfoAreaCommon().getRdoSeismicRetrofit());
            vo.setDesignDocumentCode(form.getInfoAreaCommon().getRdoDesignDocument());
            vo.setRepairHistoryCode(form.getInfoAreaCommon().getRdoRepairHistory());
            vo.setVoluntaryOrganizationCode(form.getInfoAreaCommon().getRdoVoluntaryOrganization());
            vo.setDisasterPreventionManualCode(form.getInfoAreaCommon().getRdoDisasterPreventionManual());
            vo.setDisasterPreventionStockpileCode(form.getInfoAreaCommon().getRdoDisasterPreventionStockpile());
            vo.setNeedSupportListCode(form.getInfoAreaCommon().getRdoNeedSupportList());
            vo.setDisasterPreventionRegularCode(form.getInfoAreaCommon().getRdoDisasterPreventionRegular());
            vo.setSlopeCode(form.getInfoAreaCommon().getRdoSlope());
            vo.setRailingCode(form.getInfoAreaCommon().getRdoRailing());
            vo.setElevatorCode(form.getInfoAreaCommon().getRdoElevator());
            vo.setLedCode(form.getInfoAreaCommon().getRdoLed());
            vo.setHeatShieldingCode(form.getInfoAreaCommon().getRdoHeatShielding());
            vo.setEquipmentChargeCode(form.getInfoAreaCommon().getRdoEquipmentCharge());
            vo.setCommunityCode(form.getInfoAreaCommon().getRdoCommunity());

            vo.setContactPropertyCode(form.getInfoAreaCommon().getRdoContactProperty());
            if (CodeUtil.getValue(CD013, CD013_OTHER).equals(form.getInfoAreaCommon().getRdoContactProperty())) {
                vo.setContactPropertyElse(form.getInfoAreaCommon().getTxtContactPropertyElse());
            }

            vo.setContactZipCode(new StringBuilder()
                    .append(form.getInfoAreaCommon().getTxtContactZipCode1())
                    .append(DASH)
                    .append(form.getInfoAreaCommon().getTxtContactZipCode2())
                    .toString());
            vo.setContactAddress(form.getInfoAreaCommon().getTxtContactAddress());
            vo.setContactTelNo(new StringBuilder()
                    .append(form.getInfoAreaCommon().getTxtContactTelno1())
                    .append(DASH)
                    .append(form.getInfoAreaCommon().getTxtContactTelno2())
                    .append(DASH)
                    .append(form.getInfoAreaCommon().getTxtContactTelno3())
                    .toString());
            vo.setContactName(form.getInfoAreaCommon().getTxtContactName());
            vo.setContactNamePhonetic(form.getInfoAreaCommon().getTxtContactNamePhonetic());
            vo.setContactMailAddress(form.getInfoAreaCommon().getTxtContactMail());
            vo.setOptional(form.getInfoAreaCommon().getTxaOptional());
            vo.setNextNotificationDate(form.getNextNotificationDate());
            return vo;
        }
        return null;
    }

    public static NotificationInfoVo toNotificationInfoVo(NotificationTemporaryForm temporaryForm) {
        if (Objects.nonNull(temporaryForm)) {
            NotificationInfoVo vo = new NotificationInfoVo();
            vo.setReceptionNo(temporaryForm.getInfoAreaCommon().getLblReceiptNumber());
            vo.setNotificationNo(temporaryForm.getNotificationNo());
            vo.setAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED));
            vo.setApartmentId(temporaryForm.getApartmentId());
            vo.setApartmentName(temporaryForm.getBasicReportInfo().getTxtApartmentName());
            vo.setApartmentNamePhonetic(temporaryForm.getBasicReportInfo().getTxtApartmentNamePhonetic());

            String zipCode1 = temporaryForm.getBasicReportInfo().getTxtApartmentZipCode1();
            String zipCode2 = temporaryForm.getBasicReportInfo().getTxtApartmentZipCode2();
            if (!(isBlank(zipCode1) && isBlank(zipCode2))) {
                vo.setZipCode(new StringBuilder()
                        .append(zipCode1)
                        .append(DASH)
                        .append(zipCode2)
                        .toString());
            }

            vo.setCityCode(temporaryForm.getCityCode());
            vo.setCityName(temporaryForm.getCityName());
            vo.setAddress(temporaryForm.getBasicReportInfo().getTxtApartmentAddress2());
            vo.setTxtApartmentAddress2(temporaryForm.getBasicReportInfo().getTxtApartmentAddress2());
            vo.setNotificationType(temporaryForm.getInfoAreaCommon().getRdoNotificationType());
            if (isNotBlank(temporaryForm.getInfoAreaCommon().getCalNotificationDate())) {
                vo.setNotificationDate(DateTimeUtil.getLocalDateFromString2(temporaryForm.getInfoAreaCommon().getCalNotificationDate()));
            }
            vo.setNotificationGroupName(temporaryForm.getInfoAreaCommon().getTxtNotificationGroupName());
            vo.setNotificationPersonName(temporaryForm.getInfoAreaCommon().getTxtNotificationPersonName());
            vo.setAdviceDoneFlag(CodeUtil.getValue(CommonConstants.CD011, CD011_NOT_IMPLEMENTED));
            vo.setChangeCount(temporaryForm.getChangeCount());
            vo.setNotificationCount(temporaryForm.getNotificationCount());
            vo.setChangeReasonCode(temporaryForm.getInfoAreaCommon().getRdoChangeReason());

            if (CodeUtil.getValue(CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(temporaryForm.getInfoAreaCommon().getRdoChangeReason())) {
                vo.setLostElseReasonCode(temporaryForm.getInfoAreaCommon().getRdoLostElseReasonCode());
                if (CodeUtil.getValue(CD040, CD040_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoLostElseReasonCode())) {
                    vo.setLostElseReasonElse(temporaryForm.getInfoAreaCommon().getTxtLostElseReasonElse());
                }
            }

            vo.setGroupYesnoCode(temporaryForm.getInfoAreaCommon().getRdoGroupYesno());
            if (CodeUtil.getValue(CD014, CD014_IT_IS_HOUSING_COMPLEX_MANAGEMENT_UNION).equals(temporaryForm.getInfoAreaCommon().getRdoGroupYesno())) {
                if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtApartmentNumber())) {
                    vo.setApartmentNumber(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtApartmentNumber()));
                }
                vo.setGroupForm(temporaryForm.getInfoAreaCommon().getRdoGroupForm());
                if (CodeUtil.getValue(CD005, CD005_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoGroupForm())) {
                    vo.setGroupFormElse(temporaryForm.getInfoAreaCommon().getTxtGroupFormElse());
                }
            }

            if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtHouseNumber())) {
                vo.setHouseNumber(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtHouseNumber()));
            }
            if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtFloorNumber())) {
                vo.setFloorNumber(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtFloorNumber()));
            }
            if (isNotBlank(temporaryForm.getInfoAreaCommon().getCalBuiltDate())) {
                vo.setBuiltDate(DateTimeUtil.getLocalDateFromString2(temporaryForm.getInfoAreaCommon().getCalBuiltDate()));
            }

            vo.setLandRightsCode(temporaryForm.getInfoAreaCommon().getRdoLandRights());
            if (CodeUtil.getValue(CD006, CD006_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoLandRights())) {
                vo.setLandRightsElse(temporaryForm.getInfoAreaCommon().getTxtLandRightsElse());
            }

            vo.setUseforCode(temporaryForm.getInfoAreaCommon().getRdoUsefor());
            if (CodeUtil.getValue(CD007, CD007_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoUsefor())) {
                vo.setUseforElse(temporaryForm.getInfoAreaCommon().getTxtUseforElse());
            }

            vo.setManagementFormCode(temporaryForm.getInfoAreaCommon().getRdoManagementForm());
            if (CodeUtil.getValue(CD008, CD008_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoManagementForm())) {
                vo.setManagementFormElse(temporaryForm.getInfoAreaCommon().getTxtManagementFormElse());
            }

            if (CodeUtil.getValue(CD008, CD008_ENTRUST_ALL).equals(temporaryForm.getInfoAreaCommon().getRdoManagementForm())
                    || CodeUtil.getValue(CD008, CD008_PARTIALLY_COMMISSIONED).equals(temporaryForm.getInfoAreaCommon().getRdoManagementForm())) {
                vo.setManagerName(temporaryForm.getInfoAreaCommon().getTxtManagerName());
                vo.setManagerNamePhonetic(temporaryForm.getInfoAreaCommon().getTxtManagerNamePhonetic());

                String txtManagerZipCode1 = temporaryForm.getInfoAreaCommon().getTxtManagerZipCode1();
                String txtManagerZipCode2 = temporaryForm.getInfoAreaCommon().getTxtManagerZipCode2();
                if (StringUtils.isEmpty(txtManagerZipCode1) != StringUtils.isEmpty(txtManagerZipCode2)
                        && !StringUtils.isEmpty(txtManagerZipCode1)) {
                    vo.setManagerZipCode(new StringBuilder()
                            .append(temporaryForm.getInfoAreaCommon().getTxtManagerZipCode1())
                            .append(DASH)
                            .append(temporaryForm.getInfoAreaCommon().getTxtManagerZipCode2())
                            .toString());
                }

                vo.setManagerAddress(temporaryForm.getInfoAreaCommon().getTxtManagerAddress());

                String txtManagerTelno1 = temporaryForm.getInfoAreaCommon().getTxtManagerTelno1();
                String txtManagerTelno2 = temporaryForm.getInfoAreaCommon().getTxtManagerTelno2();
                String txtManagerTelno3 = temporaryForm.getInfoAreaCommon().getTxtManagerTelno3();
                if (!(isBlank(txtManagerTelno1)
                        && isBlank(txtManagerTelno2)
                        && isBlank(txtManagerTelno3))) {
                    vo.setManagerTelNo(new StringBuilder()
                            .append(temporaryForm.getInfoAreaCommon().getTxtManagerTelno1())
                            .append(DASH)
                            .append(temporaryForm.getInfoAreaCommon().getTxtManagerTelno2())
                            .append(DASH)
                            .append(temporaryForm.getInfoAreaCommon().getTxtManagerTelno3())
                            .toString());
                }
            }

            vo.setGroupCode(temporaryForm.getInfoAreaCommon().getRdoGroup());
            vo.setManagerCode(temporaryForm.getInfoAreaCommon().getRdoManager());

            vo.setRuleCode(temporaryForm.getInfoAreaCommon().getRdoRule());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(temporaryForm.getInfoAreaCommon().getRdoRule())) {
                vo.setRuleChangeYear(temporaryForm.getInfoAreaCommon().getTxtRuleChangeYear());
            }

            vo.setOpenOneyearOver(temporaryForm.getInfoAreaCommon().getRdoOneyearOver());
            vo.setMinutes(temporaryForm.getInfoAreaCommon().getRdoMinutes());
            vo.setManagementCostCode(temporaryForm.getInfoAreaCommon().getRdoManagementCost());

            vo.setRepairCostCode(temporaryForm.getInfoAreaCommon().getRdoRepairCost());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(temporaryForm.getInfoAreaCommon().getRdoRepairCost())) {
                if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtRepairMonthlycost())) {
                    vo.setRepairMonthlyCost(Integer.parseInt(CommonUtils.removeComma(temporaryForm.getInfoAreaCommon().getTxtRepairMonthlycost())));
                }
            }

            vo.setRepairPlanCode(temporaryForm.getInfoAreaCommon().getRdoRepairPlan());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(temporaryForm.getInfoAreaCommon().getRdoRepairPlan())) {
                vo.setRepairNearestYear(temporaryForm.getInfoAreaCommon().getTxtRepairNearestYear());
            }

            vo.setLongRepairPlanCode(temporaryForm.getInfoAreaCommon().getRdoLongRepairPlan());
            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(temporaryForm.getInfoAreaCommon().getRdoLongRepairPlan())) {
                vo.setLongRepairPlanYear(temporaryForm.getInfoAreaCommon().getTxtLongRepairPlanYear());
                if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtLongRepairPlanPeriod())) {
                    vo.setLongRepairPlanPeriod(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtLongRepairPlanPeriod()));
                }
                vo.setLongRepairPlanYearFrom(temporaryForm.getInfoAreaCommon().getTxtLongRepairPlanYearFrom());
                vo.setLongRepairPlanYearTo(temporaryForm.getInfoAreaCommon().getTxtLongRepairPlanYearTo());
            }

            vo.setArrearageRuleCode(temporaryForm.getInfoAreaCommon().getRdoArrearageRule());
            vo.setSegmentCode(temporaryForm.getInfoAreaCommon().getRdoSegment());
            vo.setEmptyPercentCode(temporaryForm.getInfoAreaCommon().getRdoEmptyPercent());
            if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtEmptyNumber())) {
                vo.setEmptyNumber(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtEmptyNumber()));
            }
            vo.setRentalPercentCode(temporaryForm.getInfoAreaCommon().getRdoRentalPercent());
            if (isNotBlank(temporaryForm.getInfoAreaCommon().getTxtRentalNumber())) {
                vo.setRentalNumber(Integer.parseInt(temporaryForm.getInfoAreaCommon().getTxtRentalNumber()));
            }
            vo.setSeismicDiagnosisCode(temporaryForm.getInfoAreaCommon().getRdoSeismicDiagnosis());
            vo.setEarthquakeResistanceCode(temporaryForm.getInfoAreaCommon().getRdoEarthquakeResistance());
            vo.setSeismicRetrofitCode(temporaryForm.getInfoAreaCommon().getRdoSeismicRetrofit());
            vo.setDesignDocumentCode(temporaryForm.getInfoAreaCommon().getRdoDesignDocument());
            vo.setRepairHistoryCode(temporaryForm.getInfoAreaCommon().getRdoRepairHistory());
            vo.setVoluntaryOrganizationCode(temporaryForm.getInfoAreaCommon().getRdoVoluntaryOrganization());
            vo.setDisasterPreventionManualCode(temporaryForm.getInfoAreaCommon().getRdoDisasterPreventionManual());
            vo.setDisasterPreventionStockpileCode(temporaryForm.getInfoAreaCommon().getRdoDisasterPreventionStockpile());
            vo.setNeedSupportListCode(temporaryForm.getInfoAreaCommon().getRdoNeedSupportList());
            vo.setDisasterPreventionRegularCode(temporaryForm.getInfoAreaCommon().getRdoDisasterPreventionRegular());
            vo.setSlopeCode(temporaryForm.getInfoAreaCommon().getRdoSlope());
            vo.setRailingCode(temporaryForm.getInfoAreaCommon().getRdoRailing());
            vo.setElevatorCode(temporaryForm.getInfoAreaCommon().getRdoElevator());
            vo.setLedCode(temporaryForm.getInfoAreaCommon().getRdoLed());
            vo.setHeatShieldingCode(temporaryForm.getInfoAreaCommon().getRdoHeatShielding());
            vo.setEquipmentChargeCode(temporaryForm.getInfoAreaCommon().getRdoEquipmentCharge());
            vo.setCommunityCode(temporaryForm.getInfoAreaCommon().getRdoCommunity());

            vo.setContactPropertyCode(temporaryForm.getInfoAreaCommon().getRdoContactProperty());
            if (CodeUtil.getValue(CD013, CD013_OTHER).equals(temporaryForm.getInfoAreaCommon().getRdoContactProperty())) {
                vo.setContactPropertyElse(temporaryForm.getInfoAreaCommon().getTxtContactPropertyElse());
            }

            String contactZipCode1 = temporaryForm.getInfoAreaCommon().getTxtContactZipCode1();
            String contactZipCode2 = temporaryForm.getInfoAreaCommon().getTxtContactZipCode2();
            if (!(isBlank(contactZipCode1) && isBlank(contactZipCode2))) {
                vo.setContactZipCode(new StringBuilder()
                        .append(contactZipCode1)
                        .append(DASH)
                        .append(contactZipCode2)
                        .toString());
            }
            vo.setContactAddress(temporaryForm.getInfoAreaCommon().getTxtContactAddress());

            String contactTelNo1 = temporaryForm.getInfoAreaCommon().getTxtContactTelno1();
            String contactTelNo2 = temporaryForm.getInfoAreaCommon().getTxtContactTelno2();
            String contactTelNo3 = temporaryForm.getInfoAreaCommon().getTxtContactTelno3();
            if (!(isBlank(contactTelNo1) && isBlank(contactTelNo2) && isBlank(contactTelNo3))) {
                vo.setContactTelNo(new StringBuilder()
                        .append(temporaryForm.getInfoAreaCommon().getTxtContactTelno1())
                        .append(DASH)
                        .append(temporaryForm.getInfoAreaCommon().getTxtContactTelno2())
                        .append(DASH)
                        .append(temporaryForm.getInfoAreaCommon().getTxtContactTelno3())
                        .toString());
            }

            vo.setContactName(temporaryForm.getInfoAreaCommon().getTxtContactName());
            vo.setContactNamePhonetic(temporaryForm.getInfoAreaCommon().getTxtContactNamePhonetic());
            vo.setContactMailAddress(temporaryForm.getInfoAreaCommon().getTxtContactMail());
            vo.setOptional(temporaryForm.getInfoAreaCommon().getTxaOptional());
            vo.setNextNotificationDate(temporaryForm.getNextNotificationDate());
            return vo;
        }
        return null;
    }
}
