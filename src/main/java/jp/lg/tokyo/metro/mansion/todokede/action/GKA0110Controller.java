/*
 * @(#) GKA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.DataAggregateForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICityLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IDataAggregateLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultItemVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateVo;

@Controller
public class GKA0110Controller extends BaseMansionController {

    private static final String GKA0110_SHOW = "/GKA0110";
    private static final String GKA0110_AGGREGATE = "/GKA0110/aggregate";
    private static final String GKA0110_RETURN = "/GKA0110/return";
    private static final String GKA0110 = "GKA0110";
    private static final String AGGREGATE_MODEL_NAME = "dataAggregate";
    private static final String LST_LBL_SUMMARY_NAME = "lstLblSummaryItem";
    private static final String DATA_AGGREGATE_RESULT_NAME = "dataAggregateResultVo";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String NOT_SPECIFIED = "指定しない";
    private static final String AGGREGATE_MESS = "集計情報";
    public static final String DEF_STR_DATE_AGGREGATE = "DEF_STR_DATE_AGGREGATE";

    private static final Logger logger = LogManager.getLogger(GKA0110Controller.class);

    @Autowired
    private IDataAggregateLogic dataAggregateLogic;

    @Autowired
    private ICityLogic citylogic;

    @Autowired
    private TBM001DAO tbm001DAO;

    /**
     * Show GKA0110 screen
     *
     * @param model Model
     * @return String
     */
    @PostMapping(GKA0110_SHOW)
    public String show(Model model, HttpServletRequest request) throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));

            // Prepare data for display
            DataAggregateVo vo = new DataAggregateVo();
            prepare(vo);

            model.addAttribute(AGGREGATE_MODEL_NAME, vo);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        return GKA0110;
    }

    /**
     * Aggregate Mansion
     *
     * @param form    DataAggregateForm
     * @param result  result
     * @param request request
     * @param model   model
     * @return ModelAndView
     * @throws BusinessException Exception
     */
    @PostMapping(GKA0110_AGGREGATE)
    public ModelAndView aggregateMansion(@ModelAttribute(AGGREGATE_MODEL_NAME) @Valid DataAggregateForm form,
            BindingResult result, HttpServletRequest request, Model model) throws BusinessException {

        ModelAndView mdv = new ModelAndView(GKA0110, AGGREGATE_MODEL_NAME, form);
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));

            DataAggregateVo vo = new DataAggregateVo();
            HttpSession session = request.getSession();
            List<String> errorMessage = new ArrayList<>();

            BeanUtils.copyProperties(vo, form);

            // No1. チェック（単項目）
            if (result.hasErrors()) {
                CommonUtils.getErrorsFromBindingResult(result, errorMessage);
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                return mdv;
            }

            // No2. チェック（相関）
            if (dataAggregateLogic.isValidCorrelation(vo, errorMessage)) {
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                return mdv;
            }

            // 集計キー（集計項目）リスト設定
            List<String> lstLblSummaryItem = dataAggregateLogic.getLstLblSummaryItem(vo.getChkSummaryItem());
            List<DataAggregateResultVo> dataAggregateResultVo = dataAggregateLogic.getAggregateRecord(vo);


            // 集計期間開始が2020/04/01より過去の日付が入力され　または　未入力の場合、2020/04/01とする。集計期間終了が現在日より未来の日付が入力され　または　未入力の場合、現在日とする。
            LocalDate lcDateNow = LocalDate.now();
            String defDateAggregate = CommonProperties.getProperty(DEF_STR_DATE_AGGREGATE);
            // 集計期間開始
            String cldPeriodFrom = form.getCldPeriodFrom();
            if (StringUtils.isBlank(cldPeriodFrom) || DateTimeUtil.getLocalDateFromString2(cldPeriodFrom)
                    .isBefore(DateTimeUtil.getLocalDateFromString2(defDateAggregate))) {
                cldPeriodFrom = defDateAggregate;
                form.setCldPeriodFrom(cldPeriodFrom);
            }
            // 集計期間終了
            String cldPeriodTo = form.getCldPeriodTo();
            if (StringUtils.isBlank(cldPeriodTo) || DateTimeUtil.getLocalDateFromString2(cldPeriodTo).isAfter(lcDateNow)) {
                cldPeriodTo = DateTimeUtil.getLocalDateAsString2(lcDateNow);
                form.setCldPeriodTo(cldPeriodTo);
            }

            boolean isZero = true;
            for (DataAggregateResultVo resultVo : dataAggregateResultVo) {
                for (DataAggregateResultItemVo summaryItem : resultVo.getLstSummaryItem()) {
                    if (!"0".equals(summaryItem.getLblSummaryItemAll())) {
                        isZero = false;
                        break;
                    }
                }
            }

            if (isZero) {
                errorMessage.add(MessageUtil.getMessage(CommonConstants.MS_E0145, AGGREGATE_MESS));
                mdv.addObject(ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                return mdv;
            }

            // save information to session
            saveToSession(session, form, lstLblSummaryItem, dataAggregateResultVo);

            model.addAttribute(LST_LBL_SUMMARY_NAME, lstLblSummaryItem);
            model.addAttribute(DATA_AGGREGATE_RESULT_NAME, dataAggregateResultVo);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        return mdv;
    }

    /**
     * Prepare data for display
     *
     * @param vo      DataAggregateVo
     * @param request
     * @param model
     * @param session
     */
    private void prepare(DataAggregateVo vo) {
        try {
            int userType = SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode();
            List<CodeVo> lstRdoCD021 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD021));
            List<CodeVo> lstRdoCD030 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD030));
            List<CodeVo> lstRdoCD036 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD036));
            List<CodeVo> lstRdoCD054 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD054));
            List<CodeVo> lstRdoCD055 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD055));
            List<CodeVo> lstRdoCD056 = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD056));
            List<CodeVo> lstRdoAggregateCredit;

            if (UserTypeCode.CITY.getCode() == userType) {
                lstRdoAggregateCredit = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD058));

                String currCityCode = SecurityUtil.getUserLoggedInInfo().getCityCode();
                // 区市町村マスター情報取得
                TBM001Entity entityTbm001 = tbm001DAO.getMunicipalMasterInfo(currCityCode);
                String cityName = entityTbm001.getCityName();
                vo.setCityCode(currCityCode);
                vo.setCityName(cityName);
            } else {
                lstRdoAggregateCredit = copyListCodeInfos(CodeUtil.getList(CommonConstants.CD057));
                vo.setLstCity(citylogic.getListCity());
            }

            vo.setChkSummaryItem(new String[] {});
            vo.setUserType(String.valueOf(userType));
            vo.setLstChkSummaryItem(lstRdoCD056);
            vo.setLstRdoAggregateCredit(lstRdoAggregateCredit);
            vo.setLstRdoNotificationStatus(lstRdoCD036);
            vo.setLstRdoAcceptanceStatusNew(lstRdoCD030);
            vo.setLstRdoAcceptanceStatusChange(lstRdoCD030);
            vo.setLstRdoSupportTarget(lstRdoCD021);
            vo.setLstRdoGroup(lstRdoCD054);
            vo.setLstRdoManager(lstRdoCD055);
            vo.setLstRule(lstRdoCD054);
            vo.setLstRdoOneyearOver(lstRdoCD054);
            vo.setLstRdoMinutes(lstRdoCD054);
            vo.setLstRdoManagementCost(lstRdoCD054);
            vo.setLstRdoRepairCost(lstRdoCD054);
            vo.setLstRdoRepairPlan(lstRdoCD054);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            vo.setCldPeriodFrom(CommonProperties.getProperty(DEF_STR_DATE_AGGREGATE));
            vo.setCldPeriodTo(LocalDate.now().format(dateFormatter));
            vo.setRdoNotificationStatus(CodeUtil.getValue(CommonConstants.CD036, NOT_SPECIFIED));
            vo.setRdoAcceptanceStatusNew(CodeUtil.getValue(CommonConstants.CD030, NOT_SPECIFIED));
            vo.setRdoAcceptanceStatusChange(CodeUtil.getValue(CommonConstants.CD030, NOT_SPECIFIED));
            vo.setRdoSupportTarget(CodeUtil.getValue(CommonConstants.CD021, NOT_SPECIFIED));
            vo.setRdoGroup(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoManager(CodeUtil.getValue(CommonConstants.CD055, NOT_SPECIFIED));
            vo.setRdoRule(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoOneyearOver(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoMinutes(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoManagementCost(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoRepairCost(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
            vo.setRdoRepairPlan(CodeUtil.getValue(CommonConstants.CD054, NOT_SPECIFIED));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
    }

    @PostMapping(GKA0110_RETURN)
    public String returnScreen(Model model, HttpServletRequest request) throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));

            HttpSession session = request.getSession();
            if (session.getAttribute(AGGREGATE_MODEL_NAME) != null) {
                model.addAttribute(AGGREGATE_MODEL_NAME, session.getAttribute(AGGREGATE_MODEL_NAME));
            }

            if (session.getAttribute(LST_LBL_SUMMARY_NAME) != null) {
                model.addAttribute(LST_LBL_SUMMARY_NAME, session.getAttribute(LST_LBL_SUMMARY_NAME));
            }

            if (session.getAttribute(DATA_AGGREGATE_RESULT_NAME) != null) {
                model.addAttribute(DATA_AGGREGATE_RESULT_NAME, session.getAttribute(DATA_AGGREGATE_RESULT_NAME));
            }

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            throw new BusinessException(ex.getMessage());
        }

        return GKA0110;
    }

    private void saveToSession(HttpSession session, DataAggregateForm form, List<String> lstLblSummaryItem,
            List<DataAggregateResultVo> dataAggregateResultVo) {
        session.setAttribute(AGGREGATE_MODEL_NAME, form);
        session.setAttribute(LST_LBL_SUMMARY_NAME, lstLblSummaryItem);
        session.setAttribute(DATA_AGGREGATE_RESULT_NAME, dataAggregateResultVo);
    }

    @GetMapping("/GKA0110/csvSuperVise/")
    public void outputCsvSuperVise(HttpServletRequest request, OutputStream outputStream, HttpSession session,
            HttpServletResponse response) throws BusinessException {
        try {
            List<DataAggregateResultVo> dataAggregationResultVo = (List<DataAggregateResultVo>) session.getAttribute("dataAggregateResultVo");
            List<String> lstLblSummaryItem = (List<String>) session.getAttribute("lstLblSummaryItem");
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
            response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
            dataAggregateLogic.outputCsv(dataAggregationResultVo, lstLblSummaryItem, response.getOutputStream());
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }
}
