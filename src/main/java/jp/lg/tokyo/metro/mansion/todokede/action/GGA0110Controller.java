/*
 * @(#)GGA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD017;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD017_NOTIFI_BY_EMAIL;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD018;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.GGA0110_CONTACT_CHARACTER_MAX;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.GGA0110_TEXT_ADDRESS_PATH;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1010_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1020_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1120_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0104;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0116;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MS_E0123;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SURVEY_CONTACT_INDENTION_MAX;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SurveyForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISurveyNotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SurveyVo;

/**
 * @author ptluan
 *
 */
@Controller
@AllowCity
public class GGA0110Controller extends BaseMansionController {

    private static final String ONEPARAM_TEMPSAVE = "一時保存データ";
    private static final String MAXPEOPLENUMBER = "9名以下";
    private static final String PEOPLENUMBERNAME = "調査を行う者";
    private static final String NUMLINEOFBREAKNAME = "担当・連絡先の改行";
    private static final String REGIXENTER = "\r\n|\r|\n";
    private static final String RP040 = "RP040";
    private static final String GGA0110 = "GGA0110";
    private static final String GGA0110VO = "GGA0110Vo";
    private static final int MAXPEOPLE = 9;
    private static final int MINPEOPLE = 0;
    private static final String MINPEOPLENUMBER = "1名以上";
    private static final String ID_SCREEN = "GGA0110";
    private static final String RP040VO = "RP040Vo";
    private static final String TRUE = "true";
    private static final String REPORTPATH = "GGA0110/report";
    private static final String RESTOREPATH = "GGA0110/ReStore";
    private static final String MESSAGEERRORS = "messageeErrors";
    private static final String SAVEPATH = "/GGA0110/save";
    private static final String TEMPOPATH = "/GGA0110/TemporarySave";
    private static final String SHOWPATH = "/GGA0110";
    private static final String CALLREPORT = "callReport";
    private static final String REDIRECTGJA0120 = "redirecToGJA0120";
    private static final String MESSAGE_APARTMENTID_IS_NULL = "ApartmentId is null";
    private static final String MANSION_INFO_IS_NULL = "Mansion Infomation is null";
    private static final String SURVEY_FORM_IS_NULL = "Survey Form is null";
    private static final String INVESTNO_IS_NULL = "InvestNo is null";
    @Autowired
    protected IReportLogic reportLogic;
    @Autowired
    private ISurveyNotificationLogic surveyNotificationLogic;
    private static final Logger logger = LogManager.getLogger(GGA0110Controller.class);


    /**
     * @param form SurveyForm
     * @param model Model
     * @return Na
     * @throws BusinessException When get data false
     */
    @PostMapping(SHOWPATH)
    public ModelAndView show(SurveyForm form, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, currentMethodName));
        if (form.getApartmentId() == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_APARTMENTID_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MESSAGE_APARTMENTID_IS_NULL));
        }
        SurveyVo vo = new SurveyVo();
        ModelAndView md = new ModelAndView(GGA0110, GGA0110VO, vo);
        try {
            /* マンション情報取得 */
            MansionInfoVo mansionVo = getMansionInfo(form.getApartmentId());
            if (null == mansionVo) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MANSION_INFO_IS_NULL));
                throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, MANSION_INFO_IS_NULL));
            }
            BeanUtils.copyProperties(vo, mansionVo);
            surveyNotificationLogic.getSurveyNotificationInfo(vo, SecurityUtil.getUserLoggedInInfo().getCityCode());
            vo.setLinkAddress(SessionUtil.getSystemSettingByKey(GGA0110_TEXT_ADDRESS_PATH));
            vo.setContactMax(Integer.parseInt(SessionUtil.getSystemSettingByKey(GGA0110_CONTACT_CHARACTER_MAX)));
            vo.setContactBRMax(Integer.parseInt(SessionUtil.getSystemSettingByKey(SURVEY_CONTACT_INDENTION_MAX)));
            /* 一時保存データ取得 */
            surveyNotificationLogic.checkTemporaryData(vo);
            vo.setRdoNoticeDestinationList(copyListCodeInfos(CodeUtil.getList(CD018)));
            vo.setRdoNotificationMethodList(copyListCodeInfos(CodeUtil.getList(CD017)));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, currentMethodName));
        return md;
    }


    /**
     * @param form SurveyForm
     * @param result BindingResult
     * @param request WebRequest
     * @param errors Errors
     * @return Na
     * @throws BusinessException when save false
     */
    @PostMapping(SAVEPATH)
    public ModelAndView save(@ModelAttribute(GGA0110VO) @Valid SurveyForm form, BindingResult result,
            WebRequest request, Errors errors) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
        }
        ModelAndView md = new ModelAndView(GGA0110, GGA0110VO, form);
        List<String> errorsMesage = new ArrayList<String>();
        /* NO2 入力チェック（単項目） */
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
        }
        /* NO3 チェック（相関） */
        if (errorsMesage.isEmpty()) {
            checkCorrelation(form, errorsMesage);
        }
        if (errorsMesage.isEmpty()) {
            try {
                /* NO4 一時保存データ削除 */
                /* NO5 現地調査通知/経過記録情報登録 */
                /* NO7 メール送信 */
                /* NO8 経過記録情報登録（メール通知） */
                /* NO9 ログ出力 */
                surveyNotificationLogic.saveSurvey(form);
                if (!form.getRdoNotificationMethod()
                        .equals(CodeUtil.getValue(CD017, CD017_NOTIFI_BY_EMAIL))) {
                    /* NO10 完了メッセージ */
                    md.addObject(CALLREPORT, TRUE);
                }
                md.addObject(REDIRECTGJA0120, TRUE);
            } catch (BusinessException ex) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
                throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            }
        }
        if (!errorsMesage.isEmpty()) {
            md.addObject(MESSAGEERRORS, errorsMesage);
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return md;
    }

    /**
     * @param form SurveyForm
     * @param result BindingResult
     * @param request WebRequest
     * @param errors Errors
     * @return ModelAndView
     * @throws BusinessException when has error when save temporary
     */
    @PostMapping(TEMPOPATH)
    public ModelAndView temporarySave(@ModelAttribute(GGA0110VO) @Valid SurveyForm form, BindingResult result,
            WebRequest request, Errors errors) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
        }
        ModelAndView md = new ModelAndView(GGA0110, GGA0110VO, form);
        List<String> errorsMesage = new ArrayList<String>();
        /* NO2 入力チェック（単項目） */
        if (result.hasErrors()) {
            CommonUtils.getErrorsFromBindingResult(result, errorsMesage);
        }
        /* NO3 チェック（相関） */
        if (errorsMesage.isEmpty()) {
            checkCorrelation(form, errorsMesage);
        }
        if (errorsMesage.isEmpty()) {
            try {
                /* NO4 一時保存データ削除 */
                /* NO5 一時保存データ削除 */
                /* NO6 完了メッセージ */
                surveyNotificationLogic.saveTemporary(form);
                md.addObject("temporaryMessage", TRUE);
            } catch (BusinessException ex) {
                logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
                throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            }
        }
        if (!errorsMesage.isEmpty()) {
            md.addObject(MESSAGEERRORS, errorsMesage);
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return md;
    }


    /**
     * @param form SurveyForm
     * @return NA
     * @throws BusinessException When has error when restore
     */
    @PostMapping(RESTOREPATH)
    public ModelAndView reStore(@ModelAttribute(GGA0110VO) SurveyForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, SURVEY_FORM_IS_NULL));
        }
        ModelAndView md = new ModelAndView(GGA0110, GGA0110VO, form);
        List<String> errorsMesage = new ArrayList<String>();
        try {
            /* NO1届出情報（一時保存）取得 */
            boolean val = surveyNotificationLogic.restoreSurveyNotificationInfo(form);
            /* NO2 画面表示 */
            if (!val) {
                errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0123), ONEPARAM_TEMPSAVE));
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        if (!errorsMesage.isEmpty()) {
            md.addObject(MESSAGEERRORS, errorsMesage);
        }
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return md;
    }

    /**
     * @param inveseNo String
     * @param model Model
     * @return NA
     * @throws BusinessException When has error when call report
     */
    @PostMapping(REPORTPATH)
    public String getReportRP040(String inveseNo, Model model) throws BusinessException {
        logger.info(MessageUtil.getMessage(LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (null == inveseNo) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, INVESTNO_IS_NULL));
            throw new BusinessException(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, INVESTNO_IS_NULL));
        }
        RP040Vo vo = reportLogic.getReportRP040(inveseNo, ID_SCREEN);
        model.addAttribute(RP040VO, vo);
        logger.info(MessageUtil.getMessage(LOG_LG1120_I, SecurityUtil.getCurrentLoginId(), RP040, inveseNo));
        logger.info(MessageUtil.getMessage(LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return RP040;
    }


    /**
     * @param form SurveyForm
     * @param errorsMesage String
     */
    private void checkCorrelation(SurveyForm form, List<String> errorsMesage) {
        String contact = form.getTxaContact();
        int numberPerson = Integer.parseInt(form.getTxtInvestImplNumberPeople());
        /* 調査を行う者 */
        if (numberPerson > MAXPEOPLE) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0104), PEOPLENUMBERNAME, MAXPEOPLENUMBER));
        } else if (numberPerson == MINPEOPLE) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0104), PEOPLENUMBERNAME, MINPEOPLENUMBER));
        }
        /* 担当・連絡先 */
        if (contact != null && contact.split(REGIXENTER).length > form.getContactBRMax()) {
            errorsMesage.add(MessageFormat.format(MessageUtil.getMessage(MS_E0116), NUMLINEOFBREAKNAME, form.getContactBRMax() + BLANK));
        }
    }
}