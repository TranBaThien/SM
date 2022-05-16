/*
 * @(#) GFA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 27, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.SystemException;
import javax.validation.Valid;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.AdviceNotificationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IAdviceNotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;

@AllowCity
@Controller
public class GFA0110Controller extends BaseMansionController {

    @Autowired
    protected IAdviceNotificationLogic adviceNotificationLogic;
    
    @Autowired
    protected IReportLogic reportLogic;
    
    private static final Logger logger = LogManager.getLogger(GFA0110Controller.class);
    private static final String ID_SCREEN = "GFA0110";
    private static final String RP030 = "RP030";
    private static final String ADVISORY_NOTICE_JP = "助言通知";
    private static final String REGISTRATION = "登録";
    private static final String ADVICE_LINE_BREAK = "助言内容の改行";
    private static final String ADVICE_AND_NOTIFICATION_INFO = "助言内容と通知情報";
    private static final String TEMPORARILY_SAVED = "一時保存";
    private static final String TEMPORARY_STORAGE_DATA = "一時保存データ";
    private static final String MANSION_INFO = "mansionInfo";
    private static final String ADVICE_NOTIFICATION = "adviceNotification";
    private static final String ACTION_REGISTER = "action=register";
    private static final String ACTION_TEMP = "action=temp";
    private static final String CHANGE_NOTIFICATION = "changeNotification";
    private static final String TRUE = "true";
    private static final String SEND_EMAIL = "sendEmail";
    private static final String COMPLETE = "complete";
    private static final String CALL_REPORT = "callreport";
    private static final String MESSAGEE_ERROR = "messageeError";
    private static final String MESSAGEE_ERRORS = "messageeErrors";
    private static final String MESSAGEE_SUCCESS = "messageSuccess";
    private static final String ADVISORY_NOTICE_EN = "advisoryNotice";
    private static final String GFA0110_SHOW = "/GFA0110/{apartmentId}";
    private static final String GFA0110_SAVE = "/GFA0110/save";
    private static final String GFA0110_REPORT = "/GFA0110/RP030";
    private static final String GFA0110_RESTORE = "/GFA0110/ReStore";
    private static final String INACTIVE_BUTTON_RESTOTE = "INACTIVE_BUTTON_RESTOTE";
    private static final String CHECK_ON_CONFIRM = "checkOnConfirm";

    /**
     * @param model Model
     * @param apartmentId String
     * @return String
     * @throws SystemException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception when getMansionInfo fail or getAdviceNotification fail or copyListCodeInfos fail
     */
    @PostMapping(GFA0110_SHOW)
    public String show(Model model, String apartmentId) throws BusinessException, SystemException, IllegalAccessException, InvocationTargetException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        // Get mansion info/マンション情報取得
        MansionInfoVo mansionInfo = getMansionInfo(apartmentId);
        if (mansionInfo == null || StringUtils.isEmpty(mansionInfo.getApartmentId())) {
            //Return error screen
            throw new BusinessException();
        }
        // Get advice notification
        AdviceNotificationVo adviceNotificationVo = 
                adviceNotificationLogic.getAdviceNotification(mansionInfo.getNewestNotificationNo());

        if (adviceNotificationVo != null) {
            //Prepare data for display
            List<CodeVo> lstRdoNotificationMethod = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD017));
            adviceNotificationVo.setLstRdoNotificationMethod(lstRdoNotificationMethod);
            adviceNotificationVo.setApartmentId(mansionInfo.getApartmentId());
            adviceNotificationVo.setNewestNotificationNo(mansionInfo.getNewestNotificationNo());
            //set advice details indention max 
            adviceNotificationVo.setAdviceDetailsIndentionMax(
                    Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ADVICE_DETAILS_INDENTION_MAX)));
        }
        // Get advice notification temp for check inactive button restore
        model.addAttribute(INACTIVE_BUTTON_RESTOTE, checkDisabledButtonRestore(mansionInfo.getNewestNotificationNo()));
        
        model.addAttribute(MANSION_INFO, mansionInfo);
        model.addAttribute(ADVICE_NOTIFICATION, adviceNotificationVo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ID_SCREEN;
    }
    
    /**
     * @param form AdviceNotificationForm
     * @param result BindingResult
     * @param request WebRequest
     * @param errors Errors
     * @return ModelAndView
     * @throws BusinessException when saveAdvice fail
     * @throws SystemException when getMansionInfo fail
     */
    @PostMapping(value = GFA0110_SAVE, params = {ACTION_REGISTER})
    public ModelAndView save(@ModelAttribute(ADVICE_NOTIFICATION) @Valid AdviceNotificationForm form, 
            BindingResult result, WebRequest request, Errors errors) throws BusinessException, SystemException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));

        ModelAndView md = new ModelAndView(ID_SCREEN,ADVICE_NOTIFICATION, form);
        MansionInfoVo mansionInfo = getMansionInfo(form.getApartmentId());
        md.addObject(MANSION_INFO, mansionInfo);

        // Get advice notification temp for check inactive button restore
        md.addObject(INACTIVE_BUTTON_RESTOTE, checkDisabledButtonRestore(mansionInfo.getNewestNotificationNo()));
        
        //check Confirm
        if (StringUtils.isEmpty(form.getChkConfirm())) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        } else {
            md.addObject(CHECK_ON_CONFIRM, TRUE);
        }

        List<String> errorsMesage = new ArrayList<>();
        /* get error message form BindingResult */
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
            md.addObject(MESSAGEE_ERRORS, errorsMesage);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //Check Correlation/入力チェック（相関）
        if (!correlationCheck(form, errorsMesage)) {
            md.addObject(MESSAGEE_ERRORS, errorsMesage);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //Check Exclusive/排他チェック
        if (!exclusiveCheck(form, errorsMesage)) {
            md.addObject(MESSAGEE_ERROR, errorsMesage);
            md.addObject(CHANGE_NOTIFICATION, TRUE);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //Save data advice notification registration
        adviceNotificationLogic.saveAdvice(form);
        
        //check what notification method is
        if (form.getRdoNotificationMethod().equals(CodeUtil.getValue(CommonConstants.CD017, CommonConstants.CD017_NOTIFI_BY_MAIL))) {
            md.addObject(CALL_REPORT, TRUE);
        } else {
            md.addObject(SEND_EMAIL, TRUE);
        }

        // return page success
        String messageSuccess = MessageUtil.getMessage(CommonConstants.MS_I0001, ADVISORY_NOTICE_JP, REGISTRATION);
        md.addObject(MESSAGEE_SUCCESS, messageSuccess);
        md.addObject(ADVICE_NOTIFICATION, form);
        md.addObject(COMPLETE, TRUE);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));

        return md;
    }

    /**
     * @param form AdviceNotificationForm
     * @param result BindingResult
     * @return ModelAndView
     * @throws SystemException when getMansionInfo fail
     * @throws BusinessException 
     */
    @PostMapping(value = GFA0110_SAVE, params = {ACTION_TEMP})
    public ModelAndView tmpSave(@ModelAttribute(ADVICE_NOTIFICATION) @Valid AdviceNotificationForm form, 
            BindingResult result) throws SystemException, BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        ModelAndView md = new ModelAndView(ID_SCREEN,ADVICE_NOTIFICATION, form);

        MansionInfoVo mansionInfo = getMansionInfo(form.getApartmentId());
        md.addObject(MANSION_INFO, mansionInfo);

        // Get advice notification temp for check inactive button restore
        md.addObject(INACTIVE_BUTTON_RESTOTE, checkDisabledButtonRestore(mansionInfo.getNewestNotificationNo()));
        
        //check Confirm
        if (!StringUtils.isEmpty(form.getChkConfirm())) {
            md.addObject(CHECK_ON_CONFIRM, TRUE);
        }
        List<String> errorsMesage = new ArrayList<>();
        /* get error message form BindingResult */
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
            md.addObject(MESSAGEE_ERRORS, errorsMesage);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }

        //Check Correlation/入力チェック（相関）
        if (!correlationCheck(form, errorsMesage)) {
            md.addObject(MESSAGEE_ERRORS, errorsMesage);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }
        
        //Check Exclusive/排他チェック
        if (!exclusiveCheck(form, errorsMesage)) {
            md.addObject(MESSAGEE_ERROR, errorsMesage);
            md.addObject(CHANGE_NOTIFICATION, TRUE);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return md;
        }
        
        //save data and delete table temporarily check code user
        adviceNotificationLogic.tmpSave(form);

        String messageSuccess = MessageUtil.getMessage(CommonConstants.MS_I0001, ADVICE_AND_NOTIFICATION_INFO,TEMPORARILY_SAVED);
        md.addObject(MESSAGEE_SUCCESS, messageSuccess);
        md.addObject(ADVICE_NOTIFICATION, form);
        md.addObject(COMPLETE, TRUE);
        md.addObject(INACTIVE_BUTTON_RESTOTE, false);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));

        return md;
    }

    /**
     * @param form AdviceNotificationForm
     * @param model Model
     * @return ModelAndView
     * @throws SystemException when getMansionInfo fail
     */
    @PostMapping(GFA0110_RESTORE)
    public ModelAndView restore(@ModelAttribute(ADVICE_NOTIFICATION) AdviceNotificationForm form, Model model) throws BusinessException, SystemException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        List<String> errorsMesage = new ArrayList<>();

        //Get data temporary storage for prepare show screen
        MansionInfoVo mansionInfo = getMansionInfo(form.getApartmentId());
        model.addAttribute(MANSION_INFO, mansionInfo);
        TBL225Entity tbl225Entity = adviceNotificationLogic.getAdviceNotificationTemporaryStorage(mansionInfo.getNewestNotificationNo());
        
        if (tbl225Entity != null) {
            //Prepare data for display
            form.setTxtAppendixNo(tbl225Entity.getAppendixNo());
            form.setTxtDocumentNo(tbl225Entity.getDocumentNo());
            form.setCalNoticeDate(tbl225Entity.getNoticeDate().format(CommonConstants.DATE_FORMATTER));
            form.setLblRecipientNameApartment(tbl225Entity.getRecipientNameApartment());
            form.setLblRecipientNameUser(tbl225Entity.getRecipientNameUser());
            form.setTxtSender(tbl225Entity.getSender());
            form.setLblNotificationDate(tbl225Entity.getNotificationDate().format(CommonConstants.DATE_FORMATTER));
            form.setLblEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, tbl225Entity.getEvidenceBar()));
            form.setEvidenceNo(tbl225Entity.getEvidenceNo());
            form.setLblAddress(tbl225Entity.getAddress()); 
            form.setLblApartmentName(tbl225Entity.getApartmentName());
            form.setTxtAdviceDetails(tbl225Entity.getAdviceDetails());
            form.setRdoNotificationMethod(tbl225Entity.getNotificationMethodCode());
        } else {
            //show error if data temporary storage is null
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0123,TEMPORARY_STORAGE_DATA, CommonConstants.BLANK));
            model.addAttribute(MESSAGEE_ERRORS, errorsMesage);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return new ModelAndView(ID_SCREEN,ADVICE_NOTIFICATION, form);
    }

    /**
     * @param adviceNo String
     * @param model Model
     * @return String
     * @throws BusinessException when get report fail
     */
    @PostMapping(GFA0110_REPORT)
    public String getReportRP030(String adviceNo, Model model) throws BusinessException {
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        //Prepare data advisory notice and show report
        RP030Vo rp030Vo = reportLogic.getReportRP030(adviceNo, ID_SCREEN);
        model.addAttribute(ADVISORY_NOTICE_EN, rp030Vo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, SecurityUtil.getCurrentLoginId(), RP030, adviceNo));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return RP030;
    }

    private boolean correlationCheck(AdviceNotificationForm form, List<String> errorsMesage) {
        if (form.getNumRowAdvice() > form.getAdviceDetailsIndentionMax()) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0116, ADVICE_LINE_BREAK, 
                    String.valueOf(form.getAdviceDetailsIndentionMax())));
            return false;
        }
        return true;
    }

    private boolean exclusiveCheck(AdviceNotificationForm form, List<String> errorsMesage) throws BusinessException {
        String result = adviceNotificationLogic.exclusiveCheck(form.getApartmentId(), form.getNewestNotificationNo());
        if (CommonConstants.ZERO.equals(result)) {
            //if notificationNo don't equal then show message MS_E0112
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0112, CommonConstants.BLANK));
            return false;
        } else if (CommonConstants.CD011_IMPLEMENTED.equals(result)) {
            //if notificationNo equal but advice done flag is implemented then show message MS_E0106
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0106, CommonConstants.BLANK));
            return false;
        }
        return true;
    }
    
    private boolean checkDisabledButtonRestore(String newestNotificationNo) {
        TBL225Entity adviceNotificationTemp = adviceNotificationLogic.getAdviceNotificationTemporaryStorage(newestNotificationNo);
        return adviceNotificationTemp == null;
    }
}
