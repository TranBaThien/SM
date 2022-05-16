/*
 * @(#) GKA0120Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISummaryDataDetailsLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataPagingVo;

@Controller
@AllowCity
public class GKA0120Controller {

    private static final Logger logger = LogManager.getLogger(GKA0120Controller.class);
    private static final String GKA0120 = "GKA0120";
    private static final String APARTMENT_ID_IS_REQUIRED = "ApartmentId is required";
    private static final String PAGING_MODEL_NAME = "pagingVo";
    private static final String DOWNLOAD_CSV_MODEL_NAME = "apartmentId";
    private static final String SUMMARY_MANSION_MODEL_NAME = "summaryMansion";
    private static final String GKA0120_SHOW = "/GKA0120/show";
    private static final String GKA0120_SEARCH = "/GKA0120/search";
    private static final String GKA0120_DOWNLOAD_CSV = "/GKA0120/csvSuperVise/";
    private static final String GKA0120_CSVFILE_MAX = "GKA0120_CSVFILE_MAX";

    @Autowired
    protected ISummaryDataDetailsLogic summaryDataDetailsLogic;

    /**
     * Show GKA0120 screen
     * 
     * @param dataPagingVo SummaryDataPagingVo
     * @param model Model
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return
     */
    @PostMapping(GKA0120_SHOW)
    public String show(SummaryDataPagingVo dataPagingVo, Model model, HttpServletRequest request,
            HttpServletResponse response) {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));
        try {
            if (dataPagingVo != null && dataPagingVo.getApartmentIds() != null) {

                // Copy properties vo to form
                SummaryDataPagingForm form = new SummaryDataPagingForm();
                CommonUtils.copyProperties(form, dataPagingVo);

                // Get summary mansion
                summaryMansion(form, model);

                logger.info(getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
            } else {
                logger.error(APARTMENT_ID_IS_REQUIRED);
                throw new BusinessException(APARTMENT_ID_IS_REQUIRED);
            }
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        return GKA0120;
    }

    /**
     * @param form SummaryDataPagingForm
     * @param model Model
     * @return
     */
    @PostMapping(GKA0120_SEARCH)
    public String summaryMansion(@ModelAttribute(SUMMARY_MANSION_MODEL_NAME) @Valid SummaryDataPagingForm form,
            Model model) {

        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, currentMethodName));

        // set default paging
        setDefaultPaging(form);

        // No1. マンション情報/届出情報取得
        PagingVo<SummaryDataDetailsVo> pageSummaryDataDetails = summaryDataDetailsLogic.getSummaryDataDetails(form);

        model.addAttribute(PAGING_MODEL_NAME, pageSummaryDataDetails);
        model.addAttribute(DOWNLOAD_CSV_MODEL_NAME, convertArrayToStringForDownloadCsv(form.getApartmentIds()));
        model.addAttribute(SUMMARY_MANSION_MODEL_NAME, form);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, currentMethodName));
        return GKA0120;
    }

    /**
     * output CSV
     * 
     * @param apartmentIds String
     * @param request HttpServletRequest
     * @param outputStream OutputStream
     * @param response HttpServletResponse
     * @throws BusinessException throw exception when outputCsv fail
     */
    @PostMapping(GKA0120_DOWNLOAD_CSV)
    public ResponseEntity<?> outputCsvSuperVise(@RequestParam(name = "apartmentIds") String[] apartmentIds,
            HttpServletRequest request, OutputStream outputStream, HttpServletResponse response)
            throws BusinessException {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I,
                    Thread.currentThread().getStackTrace()[1].getMethodName()));
            int csvMax = Integer.parseInt(SessionUtil.getSystemSettingByKey(GKA0120_CSVFILE_MAX));
            Map<String, String> message = new HashedMap<String, String>();
            if (apartmentIds.length > csvMax) {
                message.put("message",MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0127), csvMax));
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
            } 
            response.setHeader("Content-Type", "text/csv");
            summaryDataDetailsLogic.outputCsv(apartmentIds, response.getOutputStream());
            String fileName = "集計データ詳細".concat(DateTimeUtil.getCurrentDateForCsv()) + ".csv";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"");
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1100_I, fileName));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
    }

    /**
     * Set default page is 1
     * 
     * @param form SummaryDataPagingForm
     */
    private void setDefaultPaging(SummaryDataPagingForm form) {
        if (form.getPage() == null) {
            form.setPage(PageUtil.FIRST_PAGE);
        }
    }

    /**
     * Convert array to string.
     * 
     * @param apartmentIds String[]
     * @return String
     */
    private String convertArrayToStringForDownloadCsv(String[] apartmentIds) {
        String strApartmentIds = Arrays.toString(apartmentIds);
        strApartmentIds = strApartmentIds.substring(1, strApartmentIds.length() - 1);
        return strApartmentIds;
    }

}
