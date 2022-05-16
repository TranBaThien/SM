/*
 * @(#) GEA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoForm;
import jp.lg.tokyo.metro.mansion.todokede.form.ReportForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICsvLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

@AllowCity
@Controller
public class GEA0110Controller extends BaseMansionController {

    private static final Logger logger = LogManager.getLogger(GEA0110Controller.class);
    private static final String PROGRESS_RECORD = "経過記録";
    private static final String PROGRESS_RECORD_DETAILS = "経過記録詳細_";
    private static final String ZAA0130 = "ZAA0130";
    private static final String MESSAGE_FORM_NULL = "MansionInfoVo null";
    private static final String MESSAGE_FORM2_NULL = "StatusInforVo null";
    private static final String MESSAGE_CORRESPOND_DATE_NULL = "CorrespondDate null, ProgressRecordNo is ";

    @Autowired
    protected IProgressRecordLogic statusInformationLogic;

    @Autowired
    protected IReportLogic reportLogic;

    @Autowired
    protected ICsvLogic csvLogic;

    /**
     * @param model Model
     * @param form MansionInfoForm
     * @param request HttpServletRequest
     * @return String
     * @throws BusinessException BusinessException
     */
    @PostMapping(value = "/GEA0110/show")
    public String show(Model model, MansionInfoForm form, HttpServletRequest request) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        try {
            String apartmentId = form.getApartmentId();
            //Get mansion info
            MansionInfoVo vo = getMansionInfo(apartmentId);
            StatusInformationVo statusInforVo = statusInformationLogic.getStatusInformation(apartmentId);
            List<ProgressRecordDetailsVo> listProRecordVo = statusInformationLogic.getListProgressRecordDetail(apartmentId);
            if (vo == null || StringUtils.isEmpty(vo.getApartmentId())) {
                throw new BusinessException(MESSAGE_FORM_NULL);
            }

            if (statusInforVo == null) {
                throw new BusinessException(MESSAGE_FORM2_NULL);
            }

            String noInfoMessage = CommonConstants.BLANK;
            if (listProRecordVo != null && listProRecordVo.size() > CommonConstants.NUM_0) {

                model.addAttribute("listProgressRecord", listProRecordVo);
                List<String> list = new ArrayList<>();
                for (ProgressRecordDetailsVo proRecord : listProRecordVo) {
                    if (StringUtils.isBlank(proRecord.getCorrespondDate())) {
                        throw new BusinessException(MESSAGE_CORRESPOND_DATE_NULL + proRecord.getProgressRecordNo());
                    }
                    list.add(proRecord.getProgressRecordNo());
                }
                statusInforVo.setListProgressRecordNo(list);
                statusInforVo.setCountRecord(CommonUtils.formatPrice(Integer.toString(listProRecordVo.size())));
            } else {
                statusInforVo.setCountRecord(CommonConstants.ZERO);
                noInfoMessage = MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_I0002), PROGRESS_RECORD);
            }

            // add attribute message
            model.addAttribute("message", noInfoMessage);
            // add attribute status information
            model.addAttribute("statusInfo", statusInforVo);
            // City_code 
            model.addAttribute("mansionInfo", vo);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return ZAA0130;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return CommonConstants.SCREEN_ID_GEA0110;
    }

    /**
     * @param reportform ReportForm
     * @param model String
     * @return GEA0110 String
     * @throws BusinessException BusinessException
     */
    @PostMapping("/GEA0110/report")
    public String getReport(ReportForm reportform, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        String reportName = reportform.getReportName();
        String relatedNumber = reportform.getRelatedNumber();
        String screenIdResponse = CommonConstants.SCREEN_ID_GEA0110;
        // get user id form session
        String loginUserId = SecurityUtil.getUserLoggedInInfo().getUserId();

        // report with report name
        switch (reportName) {
        case CommonConstants.RP010_CREATE:
            RP010Vo rp010VoCreate = reportLogic.getReportRP010(relatedNumber);
            model.addAttribute("rp010Vo", rp010VoCreate);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
            screenIdResponse = CommonConstants.RP010_CREATE;
            break;
        case CommonConstants.RP010_UPDATE:
            RP010Vo rp010VoUpdate = reportLogic.getReportRP010(relatedNumber);
            model.addAttribute("rp010Vo", rp010VoUpdate);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
            screenIdResponse = CommonConstants.RP010_UPDATE;
            break;
        case CommonConstants.RP020 :
            RP020Vo rp020Vo = reportLogic.getReportRP020(relatedNumber, CommonConstants.SCREEN_ID_GEA0110);
            model.addAttribute("rp020Vo", rp020Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            screenIdResponse = CommonConstants.RP020;
            break;
        case CommonConstants.RP030:
            RP030Vo rp030Vo = reportLogic.getReportRP030(relatedNumber, CommonConstants.SCREEN_ID_GEA0110);
            model.addAttribute("advisoryNotice", rp030Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            screenIdResponse = CommonConstants.RP030;
            break;
        case CommonConstants.RP040:
            RP040Vo rp040Vo = reportLogic.getReportRP040(relatedNumber, CommonConstants.SCREEN_ID_GEA0110);
            model.addAttribute("RP040Vo", rp040Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            screenIdResponse = CommonConstants.RP040;
            break;
        case CommonConstants.RP050:
            RP050Vo rp050Vo = reportLogic.getReportRP050(relatedNumber, CommonConstants.SCREEN_ID_GEA0110);
            model.addAttribute("rp050Vo", rp050Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            screenIdResponse = CommonConstants.RP050;
            break;
        case CommonConstants.RP060:
            RP060Vo rp060Vo = reportLogic.getReportRP060(relatedNumber, CommonConstants.SCREEN_ID_GEA0110);
            model.addAttribute("rp060Vo", rp060Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            screenIdResponse = CommonConstants.RP060;
            break;
        default:
            break;
        }

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return screenIdResponse;
    }

    /**
     * export Csv
     * @param listProgressRecordNo listProgressRecordNo
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws BusinessException BusinessException
     */
    @GetMapping("/GEA0110/csv")
    public void exportCsv(@RequestParam(name = "listProgressRecordNo") List<String> listProgressRecordNo,
                                        HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

            // set header file csv
            response.setHeader("Content-Type", "text/csv");
            csvLogic.exportCsvProgressRecord(listProgressRecordNo, response.getOutputStream());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = PROGRESS_RECORD_DETAILS + sdf.format(date) + ".csv";
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1100_I, fileName));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

}
