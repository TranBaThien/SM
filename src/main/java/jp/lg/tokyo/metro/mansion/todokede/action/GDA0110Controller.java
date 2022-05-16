/*
 * @(#) GDA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static java.text.MessageFormat.format;
import static java.util.Optional.ofNullable;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0002;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SCREEN_ID_GDA0110;
import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.TemporaryKbn;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.converter.NotificationConverter;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.AcceptanceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.BasicReportInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoAreaCommonVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationRegistrationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;

@Controller
@AllowCity
@RequestMapping("/GDA0110")
public class GDA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GDA0110Controller.class);

    private final ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic;
    protected final IReportLogic reportLogic;

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_EXCLUSIVE = "errorExclusive";
    private static final String NOTIFICATION_REGISTRATION = "notificationRegistration";
    private static final String NOTIFICATION_ACCEPTANCE = "notificationAcceptance";
    private static final String MANSION_INFO = "mansionInfo";
    private static final String IS_ACTIVE_RESTORE_BUTTON = "isActiveRestoreButton";
    public static final String IS_STORE_TEMPORARILY_COMPLETE = "isStoreTemporarilyComplete";
    public static final String IS_ACCEPTANCE_COMPLETE = "isAcceptanceComplete";
    public static final String ACCEPT_NO = "acceptNo";
    public static final String SEND_EMAIL_CODE = "1";
    public static final String IS_SEND_EMAIL = "isSendEMail";

    public GDA0110Controller(ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic, IReportLogic reportLogic) {
        this.temporaryNotificationInfoLogic = temporaryNotificationInfoLogic;
        this.reportLogic = reportLogic;
    }

    /**
     * show
     * @param mansionInfoForm MansionInfoForm
     * @param model Model
     * @return String
     * @throws SystemException when convert Vo
     */
    @PostMapping("/show")
    public String show(@ModelAttribute(MANSION_INFO) MansionInfoForm mansionInfoForm, Model model) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String apartmentId = mansionInfoForm.getApartmentId();
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        MunicipalMasterInfoVo municipalMasterInfoVo = getMunicipalMasterInfo(mansionInfoVo.getCityCode());
        NotificationInfoVo notificationInfoVo = getNotificationInfo(mansionInfoVo.getNewestNotificationNo());

        initNotificationInfo(
                mansionInfoVo,
                notificationInfoVo,
                ofNullable(municipalMasterInfoVo).map(MunicipalMasterInfoVo::getCityName).orElse(BLANK),
                SCREEN_ID_GDA0110,
                null);

        NotificationRegistrationVo notificationRegistrationVo = NotificationConverter.toNotificationRegistrationVo(
                mansionInfoVo, notificationInfoVo, SCREEN_ID_GDA0110, false);
        notificationRegistrationVo.getInfoAreaCommon().setInActiveItem(true);

        AcceptanceNotificationVo acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(
                notificationRegistrationVo, municipalMasterInfoVo, SecurityUtil.getUserLoggedInInfo().getUserTypeCode());
        acceptanceNotificationVo.setBtnCorrectionValue("職権訂正する");
        acceptanceNotificationVo.setBtnCorrectionOn(false);

        boolean isActiveRestoreButton = temporaryNotificationInfoLogic.isActiveAcceptanceRestoreButton(
                mansionInfoVo.getNewestNotificationNo(), getTempKbn());
        addDataToModel(model, mansionInfoVo, notificationRegistrationVo, isActiveRestoreButton, acceptanceNotificationVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GDA0110;
    }

    /**
     * register Notification Acceptance
     * @param mansionInfoForm MansionInfoForm
     * @param registrationForm NotificationRegistrationForm
     * @param acceptanceForm NotificationAcceptanceForm
     * @return screenId
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    @PostMapping("/acceptance")
    public String registerNotificationAcceptance(
            @ModelAttribute(MANSION_INFO) MansionInfoForm mansionInfoForm,
            @ModelAttribute(NOTIFICATION_REGISTRATION) @Valid NotificationRegistrationForm registrationForm,
            BindingResult registrationFormResult,
            @ModelAttribute(NOTIFICATION_ACCEPTANCE) @Valid NotificationAcceptanceForm acceptanceForm,
            BindingResult acceptanceFormResult,
            Errors errors,
            Model model)
            throws BusinessException, SystemException, InvocationTargetException, IllegalAccessException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String apartmentId = mansionInfoForm.getApartmentId();
        List<String> errorMessages = new ArrayList<>();
        List<String> errorExclusive = new ArrayList<>();

        getErrorsFromBindingResult(errorMessages, registrationFormResult, acceptanceFormResult);
        validateForm(registrationForm, acceptanceForm, errorMessages, SecurityUtil.getUserLoggedInInfo().getUserTypeCode());
        CommonUtils.copyProperties(mansionInfoForm, registrationForm);

        if (errorMessages.isEmpty()) {
            checkExclusive(mansionInfoForm, errorExclusive);

            if (errorExclusive.isEmpty()) {
                String acceptNo = notificationInfoLogic.saveAcceptanceNotification(
                        apartmentId, registrationForm, acceptanceForm, SecurityUtil.getUserLoggedInInfo());
                model.addAttribute(IS_ACCEPTANCE_COMPLETE, true);
                model.addAttribute(ACCEPT_NO, acceptNo);

                if (SEND_EMAIL_CODE.equals(acceptanceForm.getRdoNotificationMethod())) {
                    model.addAttribute(IS_SEND_EMAIL, true);
                }
            }
        }

        getDataToShowOnScreen(
                apartmentId,
                model,
                errorMessages,
                errorExclusive,
                false,
                registrationForm,
                acceptanceForm);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GDA0110;
    }

    /**
     * store Temporarily Notification Acceptance
     * @param mansionInfoForm MansionInfoForm
     * @param registrationForm NotificationRegistrationForm
     * @param acceptanceForm NotificationAcceptanceForm
     * @param model Model
     * @return screenId
     * @throws BusinessException BusinessException
     * @throws SystemException SystemException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    @PostMapping("/temporarily/store")
    public String storeTemporarilyNotificationAcceptance(
            @ModelAttribute(MANSION_INFO) MansionInfoForm mansionInfoForm,
            @ModelAttribute(NOTIFICATION_REGISTRATION) @Valid NotificationRegistrationForm registrationForm,
            BindingResult registrationFormResult,
            @ModelAttribute(NOTIFICATION_ACCEPTANCE) @Valid NotificationAcceptanceForm acceptanceForm,
            BindingResult acceptanceFormResult,
            Errors errors,
            Model model) throws BusinessException, SystemException, InvocationTargetException, IllegalAccessException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String apartmentId = mansionInfoForm.getApartmentId();
        List<String> errorMessages = new ArrayList<>();
        List<String> errorExclusive = new ArrayList<>();

        getErrorsFromBindingResult(errorMessages, registrationFormResult, acceptanceFormResult);
        validateForm(registrationForm, acceptanceForm, errorMessages, SecurityUtil.getUserLoggedInInfo().getUserTypeCode());

        CommonUtils.copyProperties(mansionInfoForm, registrationForm);

        if (errorMessages.isEmpty()) {
            checkExclusive(mansionInfoForm, errorExclusive);

            if (errorExclusive.isEmpty()) {
                temporaryNotificationInfoLogic.saveTemporaryAcceptance(apartmentId, acceptanceForm, getTempKbn(), SecurityUtil.getUserLoggedInInfo());
                model.addAttribute(IS_STORE_TEMPORARILY_COMPLETE, true);
            }
        }

        getDataToShowOnScreen(
                apartmentId,
                model,
                errorMessages,
                errorExclusive,
                false,
                registrationForm,
                acceptanceForm);       

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GDA0110;
    }



    /**
     * restoreTemporarilyNotificationAcceptance
     * @param mansionInfoForm MansionInfoForm
     * @param model Model
     * @return screenId
     * @throws SystemException SystemException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    @PostMapping("/temporarily/restore")
    public String restoreTemporarilyNotificationAcceptance(
            @ModelAttribute(MANSION_INFO) MansionInfoForm mansionInfoForm,
            @ModelAttribute(NOTIFICATION_REGISTRATION) NotificationRegistrationForm registrationForm,
            @ModelAttribute(NOTIFICATION_ACCEPTANCE) NotificationAcceptanceForm acceptanceForm,
            Model model) throws SystemException, InvocationTargetException, IllegalAccessException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<String> errorMessages = new ArrayList<>();

        String apartmentId = mansionInfoForm.getApartmentId();
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        MunicipalMasterInfoVo municipalMasterInfoVo = getMunicipalMasterInfo(mansionInfoVo.getCityCode());

        NotificationRegistrationVo registrationVo = new NotificationRegistrationVo();
        formToVo(registrationVo, registrationForm);

        AcceptanceNotificationVo acceptanceNotificationVo = temporaryNotificationInfoLogic.getAcceptanceTemporaryData
                (mansionInfoVo.getNewestNotificationNo(), getTempKbn());

        boolean isActiveRestoreButton = true;
        if (ObjectUtils.isEmpty(acceptanceNotificationVo)) {
            isActiveRestoreButton = false;
            acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(
                    registrationVo, municipalMasterInfoVo, SecurityUtil.getUserLoggedInInfo().getUserTypeCode());
            errorMessages.add(MessageUtil.getMessage(CommonConstants.MS_E0123, "一時保存データ"));
            model.addAttribute(ERROR_MESSAGE, errorMessages);
        }
        acceptanceNotificationVo.setBtnCorrectionOn(acceptanceForm.isBtnCorrectionOn());
        acceptanceNotificationVo.setChkConfirm(acceptanceForm.isChkConfirm());
        acceptanceNotificationVo.setBtnCorrectionValue(acceptanceForm.getBtnCorrectionValue());

        addDataToModel(model, mansionInfoVo, registrationVo, isActiveRestoreButton, acceptanceNotificationVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GDA0110;
    }

    /**
     * show Report RP020
     * @param relatedNumber String
     * @param model Model
     * @return RP020
     * @throws BusinessException when can not get Report
     */
    @GetMapping("/{relatedNumber}/report")
    public String showReport(@PathVariable String relatedNumber, Model model) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        RP020Vo rp020Vo = reportLogic.getReportRP020(relatedNumber, CommonConstants.SCREEN_ID_GDA0110);
        model.addAttribute("rp020Vo", rp020Vo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, SecurityUtil.getCurrentLoginId(), CommonConstants.RP020, relatedNumber));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.RP020;
    }

    @PostMapping("/active")
    public String active(@ModelAttribute(MANSION_INFO) MansionInfoForm mansionInfoForm,
                         @ModelAttribute(NOTIFICATION_ACCEPTANCE) NotificationAcceptanceForm acceptanceForm,
                         Model model) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String apartmentId = mansionInfoForm.getApartmentId();
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        MunicipalMasterInfoVo municipalMasterInfoVo = getMunicipalMasterInfo(mansionInfoVo.getCityCode());
        NotificationInfoVo notificationInfoVo = getNotificationInfo(mansionInfoVo.getNewestNotificationNo());

        initNotificationInfo(
                mansionInfoVo,
                notificationInfoVo,
                ofNullable(municipalMasterInfoVo).map(MunicipalMasterInfoVo::getCityName).orElse(BLANK),
                SCREEN_ID_GDA0110,
                null);

        NotificationRegistrationVo notificationRegistrationVo = NotificationConverter.toNotificationRegistrationVo(
                mansionInfoVo, notificationInfoVo, SCREEN_ID_GDA0110, false);
        notificationRegistrationVo.getInfoAreaCommon().setInActiveItem(false);
        notificationRegistrationVo.setUpdateNotification(true);

        AcceptanceNotificationVo acceptanceNotificationVo = new AcceptanceNotificationVo();
        CommonUtils.copyProperties(acceptanceNotificationVo, acceptanceForm);
        acceptanceNotificationVo.setBtnCorrectionValue("職権訂正をキャンセルする");
        acceptanceNotificationVo.setBtnCorrectionOn(true);

        boolean isActiveRestoreButton = temporaryNotificationInfoLogic.isActiveAcceptanceRestoreButton(
                mansionInfoVo.getNewestNotificationNo(), getTempKbn());
        addDataToModel(model, mansionInfoVo, notificationRegistrationVo, isActiveRestoreButton, acceptanceNotificationVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GDA0110;
    }

    private void getErrorsFromBindingResult(List<String> errorMessages, BindingResult ...bindingResults) {
        for (BindingResult bindingResult : bindingResults) {
            CommonUtils.getErrorsFromBindingResult(bindingResult, errorMessages);
        }
    }

    private void getDataToShowOnScreen(String apartmentId,
                                       Model model,
                                       List<String> errorMessages,
                                       List<String> errorExclusive,
                                       boolean isRestored,
                                       NotificationRegistrationForm registrationForm,
                                       NotificationAcceptanceForm acceptanceForm)
            throws SystemException, InvocationTargetException, IllegalAccessException {
        MansionInfoVo mansionInfoVo = getMansionInfo(apartmentId);
        AcceptanceNotificationVo acceptanceNotificationVo;
        NotificationRegistrationVo registrationVo;
        MunicipalMasterInfoVo municipalMasterInfoVo = getMunicipalMasterInfo(mansionInfoVo.getCityCode());

        if (!errorMessages.isEmpty() || !errorExclusive.isEmpty()) {
            registrationVo = new NotificationRegistrationVo();
            formToVo(registrationVo, registrationForm);
        } else {
            NotificationInfoVo notificationInfoVo = getNotificationInfo(mansionInfoVo.getNewestNotificationNo());

            initNotificationInfo(
                    mansionInfoVo,
                    notificationInfoVo,
                    ofNullable(municipalMasterInfoVo).map(MunicipalMasterInfoVo::getCityName).orElse(BLANK),
                    SCREEN_ID_GDA0110,
                    null);

            registrationVo = NotificationConverter.toNotificationRegistrationVo(
                    mansionInfoVo,
                    notificationInfoVo,
                    SCREEN_ID_GDA0110,
                    isRestored);
            registrationVo.getInfoAreaCommon().setInActiveItem(registrationForm.getInfoAreaCommon().isInActiveItem());
        }

        boolean isActiveRestoreButton = true;

        if (isRestored) {
            acceptanceNotificationVo = temporaryNotificationInfoLogic.getAcceptanceTemporaryData
                    (mansionInfoVo.getNewestNotificationNo(), getTempKbn());

            if (ObjectUtils.isEmpty(acceptanceNotificationVo)) {
                isActiveRestoreButton = false;
                acceptanceNotificationVo = NotificationConverter.toAcceptanceNotificationVo(
                        registrationVo, municipalMasterInfoVo, SecurityUtil.getUserLoggedInInfo().getUserTypeCode());
                errorMessages.add(MessageUtil.getMessage(CommonConstants.MS_E0123, "一時保存データ"));
            }
        } else {
            acceptanceNotificationVo = new AcceptanceNotificationVo();
            BeanUtils.copyProperties(acceptanceNotificationVo, acceptanceForm);
        }

        model.addAttribute(ERROR_MESSAGE, errorMessages);
        model.addAttribute(ERROR_EXCLUSIVE, errorExclusive);
        addDataToModel(model, mansionInfoVo, registrationVo, isActiveRestoreButton, acceptanceNotificationVo);
    }

    private void addDataToModel(Model model,
                                MansionInfoVo mansionInfoVo,
                                NotificationRegistrationVo notificationRegistrationVo,
                                boolean isActiveRestoreButton,
                                AcceptanceNotificationVo acceptanceNotificationVo) {
        model.addAttribute(MANSION_INFO, mansionInfoVo);
        model.addAttribute(NOTIFICATION_REGISTRATION, notificationRegistrationVo);
        model.addAttribute(NOTIFICATION_ACCEPTANCE, acceptanceNotificationVo);
        model.addAttribute(IS_ACTIVE_RESTORE_BUTTON, isActiveRestoreButton);
        model.addAttribute(CommonConstants.CD041, CodeUtil.getListCodeVo(CommonConstants.CD041));
        model.addAttribute(CommonConstants.CD042, CodeUtil.getListCodeVo(CommonConstants.CD042));
        model.addAttribute(CommonConstants.CD043, CodeUtil.getListCodeVo(CommonConstants.CD043));
        model.addAttribute(CommonConstants.CD017, CodeUtil.getListCodeVo(CommonConstants.CD017));
    }

    private void validateForm(NotificationRegistrationForm registrationForm,
                              NotificationAcceptanceForm acceptanceForm,
                              List<String> errorMessages,
                              UserTypeCode userTypeCode) {

        if (registrationForm.isUpdateNotification()) {
            String txaCorrectionDetails = acceptanceForm.getTxaOrrectionDetails();
            if (StringUtils.isEmpty(txaCorrectionDetails)) {
                errorMessages.add(format(getMessage(MS_E0002), "訂正内容"));
            }
        }

        checkCorrelation(registrationForm, acceptanceForm, errorMessages, SCREEN_ID_GDA0110, userTypeCode);
    }

    private String getTempKbn() {
        switch (SecurityUtil.getUserLoggedInInfo().getUserTypeCode()) {
            case TOKYO_STAFF: return String.valueOf(TemporaryKbn.TOKYO.getKey());
            case SYSTEM_ADMIN: return String.valueOf(TemporaryKbn.SYSTEM_ADMIN.getKey());
            case MAINTENANCER: return String.valueOf(TemporaryKbn.MAINTENANCE.getKey());
            default: return String.valueOf(TemporaryKbn.CITY.getKey());
        }
    }

    private void formToVo(NotificationRegistrationVo registrationVo,
                          NotificationRegistrationForm registrationForm) throws InvocationTargetException, IllegalAccessException {
        BasicReportInfoVo basicReportInfo = new BasicReportInfoVo();
        NotificationInfoAreaCommonVo infoAreaCommon = new NotificationInfoAreaCommonVo();

        BeanUtils.copyProperties(basicReportInfo, registrationForm.getBasicReportInfo());
        BeanUtils.copyProperties(infoAreaCommon, registrationForm.getInfoAreaCommon());

        registrationVo.setBasicReportInfo(basicReportInfo);
        registrationVo.setInfoAreaCommon(infoAreaCommon);
        registrationVo.setNotificationNo(registrationForm.getNotificationNo());
        registrationVo.setChkConfirm(registrationForm.getChkConfirm());
        registrationVo.setAcceptStatus(registrationForm.getAcceptStatus());
        registrationVo.setChangeCount(registrationForm.getChangeCount());
        registrationVo.setNotificationCount(registrationForm.getNotificationCount());
        registrationVo.setNotificationDateTBL200(registrationForm.getNotificationDateTBL200());
        registrationVo.setFileMaxSize(registrationForm.getFileMaxSize());
        registrationVo.setUpdateNotification(registrationForm.isUpdateNotification());
        registrationVo.setCanRestore(registrationForm.isCanRestore());
        registrationVo.setScreenId(registrationForm.getScreenId());
    }
}
