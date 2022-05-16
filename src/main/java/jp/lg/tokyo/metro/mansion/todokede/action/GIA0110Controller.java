/*
 * @(#) GIA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;


import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.SystemException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SupervisedNoticeLogicForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISupervisedNoticeLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SupervisedNoticeLogicVo;

@Controller

@AllowCity

public class GIA0110Controller extends BaseMansionController {

    private static final Logger logger = LogManager.getLogger(GIA0110Controller.class);
    private static final String GIA0110 = "GIA0110";
    private static final String GIA0110_SHOW = "/GIA0110/show";
    private static final String GIA0110_SUBMIT = "/GIA0110/submit";
    private static final String GIA0110_HANDLE_CHANGE = "/GIA0110/handleChange";
    private static final String GIA0110_NOTIFICATION_DATE = "通知年月日";
    private static final String GIA0110_LAST_NOTIFICATION_DATE = "前回通知年月日";
    private static final String GIA0110_SUBMISSION_DATE = "提出期限";
    private static final String GIA0110_SYSTEM_DATE = "システム日時";
    private static final String GIA0110_CONTACT_INDENTION_MAX = "GIA0110_CONTACT_INDENTION_MAX";
    private static final String CONTACT_LINE_BREAK = "担当・連絡先の改行";
    private static final String GIA0110_USER_REGISTRATION = "userRegistration";
    private static final String GIA0110_REPORT = "/GIA0110/report";
    private static final String SLASH_STRING = "/";
    private static final String MANSION_INFO = "mansionInfo";
    private static final String FIRST_TIME_BAR = "15";
    private static final String SECOND_TIME_BAR = "16";
    private static final String RP050_VO = "rp050Vo";
    private static final String RP060_VO = "rp060Vo";
    private static final String BLANK_LST_EVIDENCE_NO = "0";
    private static final String NUM1 = "1";
    private static final String NUM2 = "2";
    private static final String NUM3 = "3";

    
    @Autowired
    protected ISupervisedNoticeLogic supervisedNoticeLogic;

    @Autowired
    protected IReportLogic reportLogic;

    /**
     * 
     * @param form SupervisedNoticeLogicForm
     * @param model Model
     * @return String
     * @throws BusinessException 
     */
    @PostMapping(GIA0110_SHOW)
    public String show(SupervisedNoticeLogicForm form, Model model) throws BusinessException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        try {

            MansionInfoVo vo = getMansionInfo(form.getApartmentId());

            model.addAttribute(MANSION_INFO, vo);

            SupervisedNoticeLogicVo supervisedNoticeLogicVo = supervisedNoticeLogic
                    .getSupervisedNoticeData(form.getApartmentId());

            supervisedNoticeLogicVo.setNewestNotificationNo(vo.getNewestNotificationNo());

            // Prepare data for display
            prepare(supervisedNoticeLogicVo);

            model.addAttribute(GIA0110_USER_REGISTRATION, supervisedNoticeLogicVo);

            model.addAttribute("linkAddress", SessionUtil.getSystemSettingByKey(CommonConstants.GIA0110_TEXT_ADDRESS_PATH));

        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        return GIA0110;
    }

    /**
     * 
     * @param form SupervisedNoticeLogicForm
     * @param result BindingResult
     * @return ModelAndView
     * @throws SystemException SystemException
     */
    @PostMapping(GIA0110_SUBMIT)
    public ModelAndView save(@ModelAttribute("userRegistration") @Valid SupervisedNoticeLogicForm form,
            BindingResult result) throws SystemException {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        ModelAndView md = new ModelAndView(GIA0110);

        MansionInfoVo vo = getMansionInfo(form.getApartmentId());

        md.addObject(MANSION_INFO, vo);

        List<String> errorsMessage = new ArrayList<>();

        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMessage);
        }

        if (errorsMessage.isEmpty()) {
            checkCorrelation(form, errorsMessage);
        }

        if (errorsMessage.isEmpty()) {
            if (!supervisedNoticeLogic.exclusiveCheckReportStatus(form)) {
                md.addObject("popupError1", MessageUtil.getMessage(CommonConstants.MS_E0118));
            } else if (!supervisedNoticeLogic.exclusiveCheckDunningNoticeCount(form)) {
                md.addObject("popupError2", MessageUtil.getMessage(CommonConstants.MS_E0119));
            } else {
                String supervisedNoticeNo = "";
                try {
                    supervisedNoticeNo = supervisedNoticeLogic.superviseNoticeRegister(form);
                    form.setSupervisedNoticeNo(supervisedNoticeNo);
                    md.addObject("directReport", true);
                } catch (Exception ex) {
                    logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
                }
            }
        } else {
            md.addObject("errorMessage", errorsMessage);
        }

        md.addObject(GIA0110_USER_REGISTRATION, form);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        return md;

    }

    /**
     * 
     * @param request HttpServletRequest
     * @param model Model
     * @return String
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception Exception
     */
    @GetMapping(value = GIA0110_HANDLE_CHANGE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String handleChange(HttpServletRequest request, Model model) throws IllegalAccessException, InvocationTargetException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String evidenceNo = request.getParameter("evidenceNo");
        String evidenceBar = request.getParameter("evidenceBar");
        
        String returnPeriodEvidence = CommonConstants.BLANK;
        String returnFormatName = CommonConstants.BLANK;
        
        if (!BLANK_LST_EVIDENCE_NO.equals(evidenceNo) && !BLANK_LST_EVIDENCE_NO.equals(evidenceBar)) {
            int offset = 0;
            if (SECOND_TIME_BAR.equals(evidenceBar)) {
                offset = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD044)).size();
            }

            returnPeriodEvidence = CodeUtil.getLabel(CommonConstants.CD045,
                    String.valueOf(Integer.parseInt(evidenceNo) + offset));

            if ((evidenceBar.equals(FIRST_TIME_BAR) && evidenceNo.equals(NUM3))
                    || (evidenceBar.equals(SECOND_TIME_BAR) && evidenceNo.equals(NUM2))) {
                returnFormatName = CodeUtil.getLabel(CommonConstants.CD032, NUM2);
            } else {
                returnFormatName = CodeUtil.getLabel(CommonConstants.CD032, NUM1);
            }

        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return String.format("%s,%s",returnPeriodEvidence,returnFormatName);
    }

    /**
     * Prepare data for display
     * 
     * @param vo SupervisedNoticeLogicVo
     * @throws BusinessException 
     */
    private void prepare(SupervisedNoticeLogicVo vo) throws BusinessException {
        try {

            vo.setTxaContactMaxLength(SessionUtil.getSystemSettings().get(CommonConstants.GIA0110_CONTACT_CHARACTER_MAX));
            
            vo.setContactIndentionMax(Integer.parseInt(SessionUtil.getSystemSettingByKey(GIA0110_CONTACT_INDENTION_MAX)));

            List<CodeVo> lstMailingCode = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD018));

            vo.setLstMailingCode(lstMailingCode);

            List<CodeVo> lstEvidenceNoCode = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD044));

            if (("16".equals(vo.getLblEvidenceBar()))) {
                lstEvidenceNoCode = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD043));
            }

            vo.setLstEvidenceNoCode(lstEvidenceNoCode);
            

        } catch (Exception e) {
        	throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Check correlation
     * 
     * @param form SupervisedNoticeLogicForm
     * @param errors ListErrorsMesage
     */
    private void checkCorrelation(SupervisedNoticeLogicForm form, List<String> errorsMesage) {
        final String txaContact = form.getTxaContact();
        final String calNoticeDate = form.getCalNoticeDate();
        final String lblLastNoticeDate = form.getLblLastNoticeDate();
        final String calNotificationTimelimit = form.getCalNotificationTimelimit();

        final LocalDate localDate = LocalDate.now();

        if (localDate.isAfter(DateTimeUtil.getLocalDateFromString(calNoticeDate.replace(SLASH_STRING, CommonConstants.BLANK)))) {
            errorsMesage.add(
                    MessageUtil.getMessage(CommonConstants.MS_E0148, GIA0110_NOTIFICATION_DATE, GIA0110_SYSTEM_DATE));
        }

        if (!StringUtils.isBlank(lblLastNoticeDate)
                && DateTimeUtil.getLocalDateFromString(lblLastNoticeDate.replace(SLASH_STRING, CommonConstants.BLANK))
                .isAfter(DateTimeUtil.getLocalDateFromString(calNoticeDate.replace(SLASH_STRING, CommonConstants.BLANK)))) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0148, GIA0110_NOTIFICATION_DATE,
                    GIA0110_LAST_NOTIFICATION_DATE));
        }

        if (DateTimeUtil.getLocalDateFromString(calNoticeDate.replace(SLASH_STRING, CommonConstants.BLANK))
                .isAfter(DateTimeUtil.getLocalDateFromString(calNotificationTimelimit.replace(SLASH_STRING,CommonConstants.BLANK)))) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0148, GIA0110_SUBMISSION_DATE, GIA0110_NOTIFICATION_DATE));
        }

        if (txaContact.split(System.getProperty("line.separator")).length > form.getContactIndentionMax()) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0116, CONTACT_LINE_BREAK, String.valueOf(form.getContactIndentionMax())));
        }

        if (txaContact.length() > form.getTxaContactMaxLength()) {
            errorsMesage.add(MessageUtil.getMessage(CommonConstants.MS_E0116, CONTACT_LINE_BREAK, String.valueOf(form.getTxaContactMaxLength())));
        }
    }

    /**
     * 
     * @param supervisedNoticeNoForReport String
     * @param model Model
     * @return String
     * @throws BusinessException BusinessException
     */
    @PostMapping(GIA0110_REPORT)
    public String getReport(String supervisedNoticeNoForReport, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));

        try {

            TBL240Entity entity240 = supervisedNoticeLogic
                    .getSupervisedNoticeBySupervisedNoticeNo(supervisedNoticeNoForReport);

            // get user id form session
            String loginUserId = SecurityUtil.getUserLoggedInInfo().getUserId();
            
            if (entity240.getSupervisedNoticeCount() == CommonConstants.NUM_1) {
                RP050Vo rp050Vo = reportLogic.getReportRP050(supervisedNoticeNoForReport, GIA0110);
                model.addAttribute(RP050_VO, rp050Vo);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP050, supervisedNoticeNoForReport));
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return CommonConstants.RP050; 
            } else {
                RP060Vo rp060Vo = reportLogic.getReportRP060(supervisedNoticeNoForReport, GIA0110);
                model.addAttribute(RP060_VO, rp060Vo);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP060, supervisedNoticeNoForReport));
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
                return CommonConstants.RP060;
            }
        } catch (Exception e) {
            MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
}
