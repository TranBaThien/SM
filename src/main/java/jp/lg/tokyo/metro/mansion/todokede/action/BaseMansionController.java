/*
 * @(#)BaseMansionController.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/18
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMunicipalMasterInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.INotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.SystemSettingLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil.isNumeric;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils.*;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author PVHung3
 */
@Controller
public abstract class BaseMansionController {

    public static final String CONTACT_MAIL_LABEL = "連絡先メールアドレス";
    public static final String CONTACT_MAIL_CONFIRM_LABEL = "連絡先メールアドレス";
    public static final String CONTACT_TEL_NUMBER_LABEL = "連絡先電話番号";
    public static final String CONTACT_PROPERTY_ELSE_LABEL = "連絡先属性その他";
    public static final String LONG_REPAIR_PLAN_YEAR_TO_LABEL = "長期修繕計画_年度2";
    public static final String LONG_REPAIR_PLAN_YEAR_FROM_LABEL = "長期修繕計画_年度1";
    public static final String TXT_LONG_REPAIR_PLAN_PERIOD_LABEL = "長期修繕計画_計画期間";
    public static final String RDO_LONG_REPAIR_PLAN_LABEL = "長期修繕計画_最新作成年";
    public static final String RDO_REPAIR_PLAN_LABEL = "修繕の計画的な実施（大規模な修繕工事）_直近実施年";
    public static final String RDO_REPAIR_COST_LABEL = "修繕積立金_㎡当たり月額";
    public static final String RDO_RULE_LABEL = "管理規約_最終改正年";
    public static final String TXT_MANAGER_LABEL = "管理業者電話番号";
    public static final String TXT_MANAGER_1_LABEL = "管理業者電話番号1";
    public static final String TXT_MANAGER_2_LABEL = "管理業者電話番号2";
    public static final String TXT_MANAGER_3_LABEL = "管理業者電話番号3";
    public static final String TXT_MANAGER_ADDRESS_LABEL = "管理業者住所";
    public static final String TXT_MANAGER_ZIP_CODE_LABEL = "管理業者郵便番号";
    public static final String TXT_MANAGER_ZIP_CODE_1_LABEL = "管理業者郵便番号1";
    public static final String TXT_MANAGER_ZIP_CODE_2_LABEL = "管理業者郵便番号2";
    public static final String TXT_MANAGER_NAME_PHONETIC_LABEL = "管理業者名フリガナ";
    public static final String TXT_MANAGER_NAME_LABEL = "管理業者名";
    public static final String TXT_MANAGER_FORM_ELSE_LABEL = "管理形態_その他";
    public static final String TXT_USER_FOR_ELSE_LABEL = "併設用途_その他";
    public static final String TXT_LAND_RIGHT_ELSE_LABEL = "土地の権利_その他";
    public static final String TXT_GROUP_FORM_ELSE_LABEL = "管理の形態_その他";
    public static final String TXT_LOST_REASON_ELSE_LABEL = "棟数";
    public static final String TXT_LOST_ELSE_REASON_ELSE_LABEL = "建物の滅失その他の事由_その他";

    @Autowired
    protected IMansionInfoLogic mansionInfoLogic;
    @Autowired
    protected INotificationInfoLogic notificationInfoLogic;
    @Autowired
    protected IMunicipalMasterInfoLogic municipalMasterInfoLogic;
    @Autowired
    private SystemSettingLogic systemSettingLogic;

    /**
     *
     * @param apartmentId String
     * @return MansionInfoVo
     * @throws SystemException when cannot get from logic
     */
    protected MansionInfoVo getMansionInfo(String apartmentId) throws SystemException {
        MansionInfoVo vo = mansionInfoLogic.getMansionInformationById(apartmentId);
        if (Objects.nonNull(vo)) {
            //区市町村マスター情報取得/ Get city master information
            MunicipalMasterInfoVo municipalMasterInfoVo = new MunicipalMasterInfoVo();
            if (isNotBlank(vo.getCityCode())) {
                municipalMasterInfoVo = getMunicipalMasterInfo(vo.getCityCode());
            }
            //Append string for address
            String strAddress = vo.getZipCode().concat(CommonConstants.SPACE_FULL_SIZE) + municipalMasterInfoVo.getCityName() + vo.getAddress();
            vo.setLblApartmentAddress(strAddress);
            vo.setCityName(municipalMasterInfoVo.getCityName());
            return vo;
        }
        return null;
    }

    /**
     *
     * @param notificationNo String
     * @return NotificationInfoVo
     * @throws SystemException when cannot get from logic
     */
    protected NotificationInfoVo getNotificationInfo(String notificationNo) throws SystemException {
        return notificationInfoLogic.getNotificationInfo(notificationNo);
    }

    /**
     * @param cityCode String
     * @return MunicipalMasterInfoVo
     */
    protected MunicipalMasterInfoVo getMunicipalMasterInfo(String cityCode) throws SystemException {
        return municipalMasterInfoLogic.getMunicipalMasterInfo(cityCode);
    }

    /**
     * check is user already logged in or not
     * @return true if user already logged in, else return false
     */
    protected boolean isAuthenticated() {
        return !SecurityUtil.isAnonymousUser();
    }

    /**
     * get next screen after login if user try to access login page again
     * @return screenId
     */
    protected String getNextScreenAfterLogin() {
        if (!ObjectUtils.isEmpty(SecurityUtil.getUserPrinciple()) && !SecurityUtil.getUserPrinciple().isPasswordNonExpire()) {
            return CommonConstants.SCREEN_ID_SBA0110;
        }
        if (!ObjectUtils.isEmpty(SecurityUtil.getUserLoggedInInfo())
                && SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.MANSION
                && SecurityUtil.getUserLoggedInInfo().getUserTypeCode() != UserTypeCode.NONE) {
            return CommonConstants.SCREEN_ID_GBA0110;
        }
        return CommonConstants.SCREEN_ID_MBA0110;
    }

    /**
     * append system setting to session
     * @param session HttpSession
     */
    protected void appendSystemSetting(HttpSession session) {
        if (ObjectUtils.isEmpty(session.getAttribute(CommonConstants.SYSTEM_SETTING))) {
            List<TBM004Entity> entities = systemSettingLogic.findAllNotDeleted();
            Map<String, String> settingMap = entities.stream().collect(Collectors.toMap(TBM004Entity::getSetTargetNameEng, TBM004Entity::getSetPoint));
            session.setAttribute(CommonConstants.SYSTEM_SETTING, settingMap);
        }
    }

    /**
     * append login error message to model
     * @param model Model
     * @param session HttpSession
     */
    protected void appendLoginMessage(Model model, HttpSession session) {
        if (session != null) {
            Object exceptionObj = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (!ObjectUtils.isEmpty(exceptionObj)) {
                LoginErrorMessageVo e = (LoginErrorMessageVo) exceptionObj;
                model.addAttribute("isError", true);
                model.addAttribute("errorMessage", e.getMessage());
            }

            model.addAttribute("loginId", session.getAttribute("txtLoginId"));
            model.addAttribute("password", session.getAttribute("pwdPassword"));

            session.removeAttribute("txtLoginId");
            session.removeAttribute("pwdPassword");
        }
    }

    /**
     * append login notice info to login page
     * @param model Model
     * @param key String notice key
     */
    protected void appendNotice(Model model, String key) {
        TBM004Entity entity = systemSettingLogic.getSystemSettingByKey(key);
        if (!ObjectUtils.isEmpty(entity)) {
            model.addAttribute("loginNotice", entity);
        }
    }

    /**
     * get login page notice by key
     * @param key String key name
     * @return content of notice in html
     */
    protected String getStaticHtmlFileAsString(final String key) throws IOException {
        String noticePath = SessionUtil.getSystemSettingByKey(key);
        return Files.readString(Paths.get("/var/www/html" + noticePath), StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    protected <T, K> List<T> copyListCodeInfos(List<K> lstSource)
            throws IllegalAccessException, InvocationTargetException {
        if (!CollectionUtils.isEmpty(lstSource)) {
            List<T> lstDest = new ArrayList<>();
            for (K k : lstSource) {
                T vo = (T) new CodeVo();
                BeanUtils.copyProperties(vo, k);

                lstDest.add(vo);
            }
            return lstDest;
        }
        return new ArrayList<>();
    }

    /**
     * initNotificationInfo
     * @param mansionInfoVo MansionInfoVo
     * @param notificationInfoVo NotificationInfoVo
     * @param cityName String
     * @param idScreen String
     */
    void initNotificationInfo(MansionInfoVo mansionInfoVo, NotificationInfoVo notificationInfoVo, String cityName, String idScreen, String activeBtn) {
        if (Objects.nonNull(notificationInfoVo)) {
            if (isBlank(notificationInfoVo.getApartmentName())) {
                notificationInfoVo.setApartmentName(mansionInfoVo.getApartmentName());
            }

            if (isBlank(notificationInfoVo.getApartmentNamePhonetic())) {
                notificationInfoVo.setApartmentNamePhonetic(mansionInfoVo.getApartmentNamePhonetic());
            }

            if (isBlank(notificationInfoVo.getCityCode())) {
                notificationInfoVo.setCityCode(mansionInfoVo.getCityCode());
            }

            if (isBlank(notificationInfoVo.getZipCode())) {
                String[] apartmentZipCode = CommonUtils.lstZipCode(mansionInfoVo.getZipCode());
                notificationInfoVo.setTxtApartmentZipCode1(apartmentZipCode[NUM_0]);
                notificationInfoVo.setTxtApartmentZipCode2(apartmentZipCode[NUM_1]);
            }

            notificationInfoVo.setLblApartmentAddress1(cityName);
            notificationInfoVo.setTxtApartmentAddress2(isBlank(notificationInfoVo.getAddress()) ? mansionInfoVo.getAddress() : notificationInfoVo.getAddress());

            if (SCREEN_ID_MDA0110.equals(idScreen)) {
            	if (!StringUtils.isEmpty(activeBtn) && STR_1.equals(activeBtn)) {
            	    if (StringUtils.isEmpty(mansionInfoVo.getNewestNotificationNo())) {
                        notificationInfoVo.setNotificationType(CodeUtil.getValue(CD003, CD003_NEW));
                    } else {
                        notificationInfoVo.setNotificationType(CodeUtil.getValue(CD003, CD003_UPDATE));
                    }
            	}

                notificationInfoVo.setChangeReasonCode(BLANK);
            }

            if (!SCREEN_ID_MDA0110.equals(idScreen)
                    && CodeUtil.getValue(CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(notificationInfoVo.getChangeReasonCode())) {
                notificationInfoVo.setLostElseReasonCode(notificationInfoVo.getLostElseReasonCode());
                notificationInfoVo.setLostElseReasonElse(notificationInfoVo.getLostElseReasonElse());
            } else {
                notificationInfoVo.setLostElseReasonCode(BLANK);
                notificationInfoVo.setLostElseReasonElse(BLANK);
            }

            if (isBlank(notificationInfoVo.getGroupYesnoCode())) {
                notificationInfoVo.setGroupYesnoCode(CodeUtil.getValue(CD014, CD014_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getGroupForm())) {
                notificationInfoVo.setGroupForm(CodeUtil.getValue(CD005, CD005_NO_ANSWER));
            }

            if (null == notificationInfoVo.getHouseNumber()) {
                notificationInfoVo.setHouseNumber(mansionInfoVo.getHouseNumber());
            }

            if (null == notificationInfoVo.getFloorNumber()) {
                notificationInfoVo.setFloorNumber(mansionInfoVo.getFloorNumber());
            }

            String buildYear = mansionInfoVo.getBuildYear();
            String buildMonth = mansionInfoVo.getBuildMonth();
            String buildDay = mansionInfoVo.getBuildDay();
            if (Objects.isNull(notificationInfoVo.getBuiltDate())
                    && isNotBlank(buildYear)
                    && isNotBlank(buildMonth)
                    && isNotBlank(buildDay)
                    && isNumeric(buildYear)
                    && isNumeric(buildMonth)
                    && isNumeric(buildDay)) {
                notificationInfoVo.setBuiltDate(LocalDate.of(
                        parseInt(buildYear),
                        parseInt(buildMonth),
                        parseInt(buildDay)));
            }

            if (isBlank(notificationInfoVo.getLandRightsCode())) {
                notificationInfoVo.setLandRightsCode(CodeUtil.getValue(CD006, CD006_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getUseforCode())) {
                notificationInfoVo.setUseforCode(CodeUtil.getValue(CD007, CD007_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getManagementFormCode())) {
                notificationInfoVo.setManagementFormCode(CodeUtil.getValue(CD008, CD008_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getLongRepairPlanCode())) {
                notificationInfoVo.setLongRepairPlanCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getArrearageRuleCode())) {
                notificationInfoVo.setArrearageRuleCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getSegmentCode())) {
                notificationInfoVo.setSegmentCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getEmptyPercentCode())) {
                notificationInfoVo.setEmptyPercentCode(CodeUtil.getValue(CD010, CD010_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getRentalPercentCode())) {
                notificationInfoVo.setRentalPercentCode(CodeUtil.getValue(CD052, CD052_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getSeismicDiagnosisCode())) {
                notificationInfoVo.setSeismicDiagnosisCode(CodeUtil.getValue(CD011, CD011_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getEarthquakeResistanceCode())) {
                notificationInfoVo.setEarthquakeResistanceCode(CodeUtil.getValue(CD012, CD012_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getSeismicRetrofitCode())) {
                notificationInfoVo.setSeismicRetrofitCode(CodeUtil.getValue(CD011, CD011_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getDesignDocumentCode())) {
                notificationInfoVo.setDesignDocumentCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getRepairHistoryCode())) {
                notificationInfoVo.setRepairHistoryCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getVoluntaryOrganizationCode())) {
                notificationInfoVo.setVoluntaryOrganizationCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getDisasterPreventionManualCode())) {
                notificationInfoVo.setDisasterPreventionManualCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getDisasterPreventionStockpileCode())) {
                notificationInfoVo.setDisasterPreventionStockpileCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getNeedSupportListCode())) {
                notificationInfoVo.setNeedSupportListCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getDisasterPreventionRegularCode())) {
                notificationInfoVo.setDisasterPreventionRegularCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getSlopeCode())) {
                notificationInfoVo.setSlopeCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getRailingCode())) {
                notificationInfoVo.setRailingCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getElevatorCode())) {
                notificationInfoVo.setElevatorCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getLedCode())) {
                notificationInfoVo.setLedCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getHeatShieldingCode())) {
                notificationInfoVo.setHeatShieldingCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getEquipmentChargeCode())) {
                notificationInfoVo.setEquipmentChargeCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

            if (isBlank(notificationInfoVo.getCommunityCode())) {
                notificationInfoVo.setCommunityCode(CodeUtil.getValue(CD002, CD002_NO_ANSWER));
            }

        }
    }

    /**
     * checkCorrelation for MDA0110
     * @param form
     * @param errorMessages
     * @param idScreen
     * @param userType
     */
    void checkCorrelation(NotificationRegistrationForm form, List<String> errorMessages, String idScreen, UserTypeCode userType) {
    	checkCorrelation(form, new NotificationAcceptanceForm(), errorMessages, idScreen, userType);
    }
    
    /**
     * checkCorrelation
     * @param form NotificationRegistrationForm
     * @param errorMessages List of errorMessages
     * @param idScreen String
     * @param userType UserTypeCode
     */
    void checkCorrelation(NotificationRegistrationForm form, NotificationAcceptanceForm acceptanceForm, List<String> errorMessages, String idScreen, UserTypeCode userType) {
        if (Arrays.asList(SCREEN_ID_MDA0110, SCREEN_ID_GDA0110).contains(idScreen)) {
            boolean isUpdate = checkIsUpdateNotification(form.getNewestNotificationNo(), form.getNextNotificationDate());
            //届出年月日
            if (isNotBlank(form.getNewestNotificationNo())
                    && Objects.nonNull(form.getNotificationDateTBL200())
                    && DateTimeUtil.getLocalDateFromString2(form.getInfoAreaCommon().getCalNotificationDate()).isBefore(form.getNotificationDateTBL200())) {
                errorMessages.add(format(getMessage(MS_E0125), "届出年月日"));
            }

            //届出者_管理組合名
            //届出者_届出者氏名
            if (isBlank(form.getInfoAreaCommon().getTxtNotificationPersonName()) && isBlank(form.getInfoAreaCommon().getTxtNotificationGroupName())) {
                errorMessages.add(format(getMessage(MS_E0142), "届出者氏名", "管理組合名"));
            }

            //変更理由
            if (isUpdate && isBlank(form.getInfoAreaCommon().getRdoChangeReason()) && !SCREEN_ID_GDA0110.equals(idScreen)) {
                errorMessages.add(format(getMessage(MS_E0001), "変更理由"));
            }

            //建物の滅失その他の事由
            if (CodeUtil.getValue(CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(form.getInfoAreaCommon().getRdoChangeReason())
                    && isBlank(form.getInfoAreaCommon().getRdoLostElseReasonCode())) {
                errorMessages.add(format(getMessage(MS_E0001), "建物の滅失その他の事由"));
            }

            //建物の滅失その他の事由_その他
            if (CodeUtil.getValue(CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(form.getInfoAreaCommon().getRdoChangeReason())
                    && CodeUtil.getValue(CD040, CD040_OTHER).equals(form.getInfoAreaCommon().getRdoLostElseReasonCode())) {
                String lostElseReasonElse = form.getInfoAreaCommon().getTxtLostElseReasonElse();
                if (isBlank(lostElseReasonElse)) {
                    errorMessages.add(format(getMessage(MS_E0002), TXT_LOST_ELSE_REASON_ELSE_LABEL));
                } else {
                    if (lostElseReasonElse.length() > 30) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_LOST_ELSE_REASON_ELSE_LABEL, STR_30));
                    } else if (!isNotSpecialCharacter(lostElseReasonElse)) {
                        errorMessages.add(format(getMessage(MS_E0015), TXT_LOST_ELSE_REASON_ELSE_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD014, CD014_IT_IS_HOUSING_COMPLEX_MANAGEMENT_UNION).equals(form.getInfoAreaCommon().getRdoGroupYesno())) {
                //棟数
                String apartmentNumber = form.getInfoAreaCommon().getTxtApartmentNumber();
                if (isNotBlank(apartmentNumber)) {
                    if (apartmentNumber.length() > 3) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_LOST_REASON_ELSE_LABEL, STR_3));
                    } else if (!isHalfSizeRatio(apartmentNumber)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_LOST_REASON_ELSE_LABEL));
                    }
                }

                if (CodeUtil.getValue(CD005, CD005_OTHER).equals(form.getInfoAreaCommon().getRdoGroupForm())) {
                    //管理の形態_その他
                    String groupFormElse = form.getInfoAreaCommon().getTxtGroupFormElse();
                    if (isBlank(groupFormElse)) {
                        errorMessages.add(format(getMessage(MS_E0002), TXT_GROUP_FORM_ELSE_LABEL));
                    } else {
                        if (groupFormElse.length() > 30) {
                            errorMessages.add(format(getMessage(MS_E0012), TXT_GROUP_FORM_ELSE_LABEL, STR_30));
                        } else if (!isNotSpecialCharacter(groupFormElse)) {
                            errorMessages.add(format(getMessage(MS_E0015), TXT_GROUP_FORM_ELSE_LABEL));
                        }
                    }
                }
            }

            if (CodeUtil.getValue(CD006, CD006_OTHER).equals(form.getInfoAreaCommon().getRdoLandRights())) {
                //土地の権利_その他
                String landRightsElse = form.getInfoAreaCommon().getTxtLandRightsElse();
                if (isBlank(landRightsElse)) {
                    errorMessages.add(format(getMessage(MS_E0002), TXT_LAND_RIGHT_ELSE_LABEL));
                } else {
                    if (landRightsElse.length() > 30) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_LAND_RIGHT_ELSE_LABEL, STR_30));
                    } else if (!isNotSpecialCharacter(landRightsElse)) {
                        errorMessages.add(format(getMessage(MS_E0015), TXT_LAND_RIGHT_ELSE_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD007, CD007_OTHER).equals(form.getInfoAreaCommon().getRdoUsefor())) {
                //併設用途_その他
                String useforElse = form.getInfoAreaCommon().getTxtUseforElse();
                if (isBlank(useforElse)) {
                    errorMessages.add(format(getMessage(MS_E0002), TXT_USER_FOR_ELSE_LABEL));
                } else {
                    if (useforElse.length() > 30) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_USER_FOR_ELSE_LABEL, STR_30));
                    } else if (!isNotSpecialCharacter(useforElse)) {
                        errorMessages.add(format(getMessage(MS_E0015), TXT_USER_FOR_ELSE_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD008, CD008_OTHER).equals(form.getInfoAreaCommon().getRdoManagementForm())) {
                //管理形態_その他
                String managementFormElse = form.getInfoAreaCommon().getTxtManagementFormElse();
                if (isBlank(managementFormElse)) {
                    errorMessages.add(format(getMessage(MS_E0002), TXT_MANAGER_FORM_ELSE_LABEL));
                } else {
                    if (managementFormElse.length() > 30) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_FORM_ELSE_LABEL, STR_30));
                    } else if (!isNotSpecialCharacter(managementFormElse)) {
                        errorMessages.add(format(getMessage(MS_E0015), TXT_MANAGER_FORM_ELSE_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD008, CD008_ENTRUST_ALL).equals(form.getInfoAreaCommon().getRdoManagementForm())
                    || CodeUtil.getValue(CD008, CD008_PARTIALLY_COMMISSIONED).equals(form.getInfoAreaCommon().getRdoManagementForm())) {
                //管理業者名
                String txtManagerName = form.getInfoAreaCommon().getTxtManagerName();
                if (!StringUtils.isEmpty(txtManagerName)) {
                    if (txtManagerName.length() > 34) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_NAME_LABEL, STR_34));
                    } else if (!isNotSpecialCharacter(txtManagerName)) {
                        errorMessages.add(format(getMessage(MS_E0015), TXT_MANAGER_NAME_LABEL));
                    }
                }

                //管理業者名フリガナ
                String txtManagerNamePhonetic = form.getInfoAreaCommon().getTxtManagerNamePhonetic();
                if (!StringUtils.isEmpty(txtManagerNamePhonetic)) {
                    if (txtManagerNamePhonetic.length() > 34) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_NAME_PHONETIC_LABEL, STR_34));
                    } else if (!isKatakanaString(txtManagerNamePhonetic)) {
                        errorMessages.add(format(getMessage(MS_E0007), TXT_MANAGER_NAME_PHONETIC_LABEL));
                    }
                }

                String txtManagerZipCode1 = form.getInfoAreaCommon().getTxtManagerZipCode1();
                String txtManagerZipCode2 = form.getInfoAreaCommon().getTxtManagerZipCode2();
                if (StringUtils.isEmpty(txtManagerZipCode1) != StringUtils.isEmpty(txtManagerZipCode2)) {
                    errorMessages.add(format(getMessage(MS_E0100), TXT_MANAGER_ZIP_CODE_LABEL));
                } else if (!StringUtils.isEmpty(txtManagerZipCode1)) {
                    //管理業者郵便番号1
                    if (!isHalfSizeRatio(txtManagerZipCode1)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_MANAGER_ZIP_CODE_1_LABEL));
                    } else if (txtManagerZipCode1.length() < 3) {
                        errorMessages.add(format(getMessage(MS_E0013), TXT_MANAGER_ZIP_CODE_1_LABEL, STR_3));
                    } else if (txtManagerZipCode1.length() > 3) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_ZIP_CODE_1_LABEL, STR_3));
                    }

                    //管理業者郵便番号2
                    if (!isHalfSizeRatio(txtManagerZipCode2)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_MANAGER_ZIP_CODE_2_LABEL));
                    } else if (txtManagerZipCode2.length() < 4) {
                        errorMessages.add(format(getMessage(MS_E0013), TXT_MANAGER_ZIP_CODE_2_LABEL, STR_4));
                    } else if (txtManagerZipCode2.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_ZIP_CODE_2_LABEL, STR_4));
                    }
                }

                //管理業者住所
                String txtManagerAddress = form.getInfoAreaCommon().getTxtManagerAddress();
                if (!StringUtils.isEmpty(txtManagerAddress)) {
                    if (txtManagerAddress.length() > 100) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_ADDRESS_LABEL, STR_100));
                    } else if (!isFullSizeString(txtManagerAddress)) {
                        errorMessages.add(format(getMessage(MS_E0006), TXT_MANAGER_ADDRESS_LABEL));
                    }
                }

                String txtManagerTelno1 = form.getInfoAreaCommon().getTxtManagerTelno1();
                String txtManagerTelno2 = form.getInfoAreaCommon().getTxtManagerTelno2();
                String txtManagerTelno3 = form.getInfoAreaCommon().getTxtManagerTelno3();

                if (StringUtils.isEmpty(txtManagerTelno1) != StringUtils.isEmpty(txtManagerTelno2)
                        || StringUtils.isEmpty(txtManagerTelno1) != StringUtils.isEmpty(txtManagerTelno3)) {
                    errorMessages.add(format(getMessage(MS_E0100), TXT_MANAGER_LABEL));
                } else if (!StringUtils.isEmpty(txtManagerTelno1)) {
                    //管理業者電話番号1
                    if (!isHalfSizeRatio(txtManagerTelno1)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_MANAGER_1_LABEL));
                    } else  if (txtManagerTelno1.length() > 5) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_1_LABEL, STR_5));
                    }

                    //管理業者電話番号2
                    if (!isHalfSizeRatio(txtManagerTelno2)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_MANAGER_2_LABEL));
                    } else if (txtManagerTelno2.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_2_LABEL, STR_4));
                    }

                        //管理業者電話番号3
                    if (!isHalfSizeRatio(txtManagerTelno3)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_MANAGER_3_LABEL));
                    } else if (txtManagerTelno3.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_MANAGER_3_LABEL, STR_4));
                    }

                        //管理業者電話番号1＋ハイフン＋管理業者電話番号2＋ハイフン＋管理業者電話番号3の桁数＞13桁の場合、エラー
                    if ((txtManagerTelno1.length() + txtManagerTelno2.length() + txtManagerTelno3.length() + NUM_2) > 13) {
                        errorMessages.add(format(getMessage(MS_E0011), TXT_MANAGER_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRule())) {
                //管理規約_最終改正年
                String ruleChangeYear = form.getInfoAreaCommon().getTxtRuleChangeYear();
                if (isBlank(ruleChangeYear)) {
                    errorMessages.add(format(getMessage(MS_E0002), RDO_RULE_LABEL));
                } else {
                    if (ruleChangeYear.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), RDO_RULE_LABEL, STR_4));
                    } else if (!isHalfSizeRatio(ruleChangeYear)) {
                        errorMessages.add(format(getMessage(MS_E0003), RDO_RULE_LABEL));
                    } else if (isNumeric(ruleChangeYear) && (parseInt(ruleChangeYear) > LocalDate.now().getYear())) {
                        errorMessages.add(format(getMessage(MS_E0132), RDO_RULE_LABEL, "未来年"));
                    }
                }
            }

            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoOneyearOver())) {
                //議事録
                if (isBlank(form.getInfoAreaCommon().getRdoMinutes())) {
                    errorMessages.add(format(getMessage(MS_E0001), "議事録"));
                }
            }

            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRepairCost())) {
                //修繕積立金_㎡当たり月額
                String repairMonthlycost = form.getInfoAreaCommon().getTxtRepairMonthlycost();
                if (isBlank(repairMonthlycost)) {
                    errorMessages.add(format(getMessage(MS_E0002), RDO_REPAIR_COST_LABEL));
                } else {
                    if (repairMonthlycost.length() > 7) {
                        errorMessages.add(format(getMessage(MS_E0012), RDO_REPAIR_COST_LABEL, STR_7));
                    } else if (!isHalfSizeRatio(repairMonthlycost)) {
                        errorMessages.add(format(getMessage(MS_E0003), RDO_REPAIR_COST_LABEL));
                    }
                }
            }

            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoRepairPlan())) {
                //修繕の計画的な実施（大規模な修繕工事）_直近実施年
                String repairNearestYear = form.getInfoAreaCommon().getTxtRepairNearestYear();
                if (isBlank(repairNearestYear)) {
                    errorMessages.add(format(getMessage(MS_E0002), RDO_REPAIR_PLAN_LABEL));
                } else {
                    if (repairNearestYear.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), RDO_REPAIR_PLAN_LABEL, STR_4));
                    } else if (!isHalfSizeRatio(repairNearestYear)) {
                        errorMessages.add(format(getMessage(MS_E0003), RDO_REPAIR_PLAN_LABEL));
                    } else if (isNumeric(repairNearestYear) && (parseInt(repairNearestYear) > LocalDate.now().getYear())) {
                        errorMessages.add(format(getMessage(MS_E0132), RDO_REPAIR_PLAN_LABEL, "未来年"));
                    }
                }
            }

            if (CodeUtil.getValue(CD002, CD002_HAVE).equals(form.getInfoAreaCommon().getRdoLongRepairPlan())) {
                //長期修繕計画_最新作成年
                String repairPlanYear = form.getInfoAreaCommon().getTxtLongRepairPlanYear();
                if (isNotBlank(repairPlanYear)) {
                    if (repairPlanYear.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), RDO_LONG_REPAIR_PLAN_LABEL, STR_4));
                    } else if (!isHalfSizeRatio(repairPlanYear)) {
                        errorMessages.add(format(getMessage(MS_E0003), RDO_LONG_REPAIR_PLAN_LABEL));
                    } else if (isNumeric(repairPlanYear) && (parseInt(repairPlanYear) > LocalDate.now().getYear())) {
                        errorMessages.add(format(getMessage(MS_E0132), RDO_LONG_REPAIR_PLAN_LABEL, "未来年"));
                    }
                }

                //長期修繕計画_計画期間
                String longRepairPlanPeriod = form.getInfoAreaCommon().getTxtLongRepairPlanPeriod();
                if (isNotBlank(longRepairPlanPeriod)) {
                    if (longRepairPlanPeriod.length() > 2) {
                        errorMessages.add(format(getMessage(MS_E0012), TXT_LONG_REPAIR_PLAN_PERIOD_LABEL, STR_2));
                    } else if (!isHalfSizeRatio(longRepairPlanPeriod)) {
                        errorMessages.add(format(getMessage(MS_E0003), TXT_LONG_REPAIR_PLAN_PERIOD_LABEL));
                    }
                }

                //長期修繕計画_年度1
                String longRepairPlanYearFrom = form.getInfoAreaCommon().getTxtLongRepairPlanYearFrom();
                if (isNotBlank(longRepairPlanYearFrom)) {
                    if (longRepairPlanYearFrom.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), LONG_REPAIR_PLAN_YEAR_FROM_LABEL, STR_4));
                    } else if (!isHalfSizeRatio(longRepairPlanYearFrom)) {
                        errorMessages.add(format(getMessage(MS_E0003), LONG_REPAIR_PLAN_YEAR_FROM_LABEL));
                    }
                }

                //長期修繕計画_年度2
                String longRepairPlanYearTo = form.getInfoAreaCommon().getTxtLongRepairPlanYearTo();
                if (isNotBlank(longRepairPlanYearTo)) {
                    if (longRepairPlanYearTo.length() > 4) {
                        errorMessages.add(format(getMessage(MS_E0012), LONG_REPAIR_PLAN_YEAR_TO_LABEL, STR_4));
                    } else if (!isHalfSizeRatio(longRepairPlanYearTo)) {
                        errorMessages.add(format(getMessage(MS_E0003), LONG_REPAIR_PLAN_YEAR_TO_LABEL));
                    }
                }

                if (isNotBlank(longRepairPlanYearFrom) && isNotBlank(longRepairPlanYearTo)
                        && isNumeric(longRepairPlanYearFrom) && isNumeric(longRepairPlanYearTo)
                        && parseInt(longRepairPlanYearFrom) > parseInt(longRepairPlanYearTo)) {
                    errorMessages.add(format(getMessage(MS_E0129), "長期修繕計画_年度の開始", "長期修繕計画_年度の終了"));
                }
            }

            if (CodeUtil.getValue(CD013, CD013_OTHER).equals(form.getInfoAreaCommon().getRdoContactProperty())) {
                //連絡先属性その他
                String contactPropertyElse = form.getInfoAreaCommon().getTxtContactPropertyElse();
                if (isNotBlank(contactPropertyElse)) {
                    if (contactPropertyElse.length() > 30) {
                        errorMessages.add(format(getMessage(MS_E0012), CONTACT_PROPERTY_ELSE_LABEL, STR_30));
                    } else if (!isNotSpecialCharacter(contactPropertyElse)) {
                        errorMessages.add(format(getMessage(MS_E0015), CONTACT_PROPERTY_ELSE_LABEL));
                    }
                } else {
                    errorMessages.add(format(getMessage(MS_E0002), CONTACT_PROPERTY_ELSE_LABEL));
                }
            }

            //連絡先電話番号1＋ハイフン＋連絡先電話番号2＋ハイフン＋連絡先電話番号3の桁数＞13桁の場合、エラー
            String contactTelNo1 = form.getInfoAreaCommon().getTxtContactTelno1();
            String contactTelNo2 = form.getInfoAreaCommon().getTxtContactTelno2();
            String contactTelNo3 = form.getInfoAreaCommon().getTxtContactTelno3();
            if (isNotBlank(contactTelNo1)
                    && isNotBlank(contactTelNo2)
                    && isNotBlank(contactTelNo3)
                    && ((contactTelNo1.length()
                    + contactTelNo2.length()
                    + contactTelNo3.length()
                    + NUM_2) > 13)) {
                errorMessages.add(format(getMessage(MS_E0011), CONTACT_TEL_NUMBER_LABEL));
            }

            //連絡先メールアドレス
            String contactMail = form.getInfoAreaCommon().getTxtContactMail();
            String contactMailConfirm = form.getInfoAreaCommon().getTxtContactMailConfirm();
            if (UserTypeCode.MANSION.equals(userType)) {
                if (isBlank(contactMail)) {
                    errorMessages.add(format(getMessage(MS_E0002), CONTACT_MAIL_LABEL));
                }
                if (isBlank(contactMailConfirm)) {
                    errorMessages.add(format(getMessage(MS_E0002), CONTACT_MAIL_CONFIRM_LABEL));
                }

                if (!isAlphaNumericForMail(contactMail)) {
                    errorMessages.add(format(getMessage(MS_E0005), CONTACT_MAIL_LABEL));
                } else if (!CheckUtil.isMailAddress(contactMail)) {
                    errorMessages.add(format(getMessage(MS_E0009), CONTACT_MAIL_LABEL));
                } else if (contactMail.length() > 120) {
                    errorMessages.add(format(getMessage(MS_E0012), CONTACT_MAIL_LABEL, STR_120));
                }
            } else {
                if (isNotBlank(contactMail)) {
                    if (!isAlphaNumericForMail(contactMail)) {
                        errorMessages.add(format(getMessage(MS_E0005), CONTACT_MAIL_LABEL));
                    } else if (!CheckUtil.isMailAddress(contactMail)) {
                        errorMessages.add(format(getMessage(MS_E0009), CONTACT_MAIL_LABEL));
                    } else if (contactMail.length() > 120) {
                        errorMessages.add(format(getMessage(MS_E0012), CONTACT_MAIL_LABEL, STR_120));
                    }
                }
            }

            if ((CommonConstants.SCREEN_ID_GDA0110.equals(idScreen) && acceptanceForm.isBtnCorrectionOn() && !Objects.equals(contactMail, contactMailConfirm))
            		|| (CommonConstants.SCREEN_ID_MDA0110.equals(idScreen) && !Objects.equals(contactMail, contactMailConfirm))) {
            	errorMessages.add(format(getMessage(MS_E0105), CONTACT_MAIL_LABEL, CONTACT_MAIL_CONFIRM_LABEL));
            }
        }
    }

    /**
     *
     * @param mansionInfoForm MansionInfoForm
     * @param errorsExclusive List of String
     * @return boolean
     * @throws SystemException from getMansionInfo
     */
    protected boolean checkExclusive(MansionInfoForm mansionInfoForm, List<String> errorsExclusive) throws SystemException {
        MansionInfoVo latestMansion = getMansionInfo(mansionInfoForm.getApartmentId());
        String mansionNotificationNo = isBlank(mansionInfoForm.getNewestNotificationNo()) ? null : mansionInfoForm.getNewestNotificationNo();
        String latestMansionNotificationNo = Objects.nonNull(latestMansion) && isBlank(latestMansion.getNewestNotificationNo()) ? null : latestMansion.getNewestNotificationNo();
        if (Objects.equals(mansionNotificationNo, latestMansionNotificationNo)) {
                return false;
        } else {
            errorsExclusive.add(format(getMessage(MS_E0112)));
            return true;
        }
    }

    /**
     * Method check update or add new
     *
     * @param newestNotificationNo
     * @param nextNotificationDate
     * @return
     */
    protected boolean checkIsUpdateNotification(String newestNotificationNo, LocalDate nextNotificationDate) {
    	if (isBlank(newestNotificationNo) || nextNotificationDate == null) {
    		return false;
    	}
        return isNotBlank(newestNotificationNo) && LocalDate.now().isBefore(nextNotificationDate);
    }
}
