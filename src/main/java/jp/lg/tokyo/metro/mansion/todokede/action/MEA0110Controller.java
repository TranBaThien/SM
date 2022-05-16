/*
 * @(#) MEA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/06
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMansion;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ReportForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReportLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecorInfoWrapperVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP020Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP050Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP060Vo;

@AllowMansion
@Controller
@RequestMapping("/MEA0110")
public class MEA0110Controller extends BaseMansionController {

    private static final Logger logger = LogManager.getLogger(MEA0110Controller.class);
    private static final String MEA0110 = "MEA0110";
    private static final String MEA0110_SHOW = "/show";
    private static final String MEA0110_REPORT = "/report";
    private static final String MANSION_INFO_MODEL_NAME = "mansionInfo";
    private static final String PROGRESS_RECORD_LIST_MODLE_NAME = "MEA0110";

    @Autowired
    private IProgressRecordLogic progressRecordLogic;

    @Autowired
    protected IReportLogic reportLogic;

    /**
     * Show MEA0110 screen.
     * @param model Model
     * @param apartmentId String
     * @return String
     * @throws BusinessException when has error
     */
    @PostMapping(MEA0110_SHOW)
    public String show(Model model, String apartmentId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            //マンション情報取得 Get mansion info
            MansionInfoVo mansionInfo = getMansionInfo(apartmentId);
            if (mansionInfo == null || StringUtils.isEmpty(mansionInfo.getApartmentId())) {
                //Return error screen
                throw new BusinessException();
            }
            //経過記録情報/受理情報/ユーザログイン（都区市町村）情報取得
            ProgressRecorInfoWrapperVo progressRecord = progressRecordLogic.getProgressRecordInfoForMEA0110(apartmentId);

            model.addAttribute(MANSION_INFO_MODEL_NAME, mansionInfo);
            model.addAttribute(PROGRESS_RECORD_LIST_MODLE_NAME, progressRecord);
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

            return MEA0110;
        } catch (Exception e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Show report.
     * @param reportform ReportForm
     * @param model Model
     * @return String
     * @throws BusinessException when has error
     */
    @PostMapping(MEA0110_REPORT)
    public String getReport(ReportForm reportform, Model model) throws BusinessException {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        String reportName = reportform.getReportName();
        String relatedNumber = reportform.getRelatedNumber();
        String loginUserId = SecurityUtil.getUserLoggedInInfo().getUserId();
        switch (reportName) {
        case CommonConstants.RP010_CREATE:
            RP010Vo rp010VoCreate = reportLogic.getReportRP010(relatedNumber);
            model.addAttribute("rp010Vo", rp010VoCreate);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP010_CREATE;
        case CommonConstants.RP010_UPDATE:
            RP010Vo rp010VoUpdate = reportLogic.getReportRP010(relatedNumber);
            model.addAttribute("rp010Vo", rp010VoUpdate);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, CommonConstants.RP010, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP010_UPDATE;
        case CommonConstants.RP020 :
            RP020Vo rp020Vo = reportLogic.getReportRP020(relatedNumber, MEA0110);
            model.addAttribute("rp020Vo", rp020Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP020;
        case CommonConstants.RP030:
            RP030Vo rp030Vo = reportLogic.getReportRP030(relatedNumber, MEA0110);
            model.addAttribute("advisoryNotice", rp030Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP030;
        case CommonConstants.RP040:
            RP040Vo rp040Vo = reportLogic.getReportRP040(relatedNumber, MEA0110);
            model.addAttribute("RP040Vo", rp040Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP040;
        case CommonConstants.RP050:
            RP050Vo rp050Vo = reportLogic.getReportRP050(relatedNumber, MEA0110);
            model.addAttribute("rp050Vo", rp050Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP050;
        case CommonConstants.RP060:
            RP060Vo rp060Vo = reportLogic.getReportRP060(relatedNumber, MEA0110);
            model.addAttribute("rp060Vo", rp060Vo);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1120_I, loginUserId, reportName, relatedNumber));
            logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            return CommonConstants.RP060;
        default:
            break;
        }
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return MEA0110;
    }

}
