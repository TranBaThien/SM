/*
 * @(#)
 * SearchInformationMansionLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD005;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD006;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD007;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_ACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_NOT_SPECIFIED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD030_UNACCEPTED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD036;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SLASH_YYYYMMDD;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.opencsv.CSVWriter;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.PageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISearchInformationMansionLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvInformationSearchVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.OutputCsvSuperivseVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;

@Service
public class SearchInformationMansionLogicImpl implements ISearchInformationMansionLogic {

    private static final Logger logger = LogManager.getLogger(SearchInformationMansionLogicImpl.class);

    // Output csv Information Mansion Search
    private static final String APARTMENT_ID = "???????????????ID";
    private static final String APARTMENT_NAME = "??????????????????";
    private static final String APARTMENT_NAME_PHONETIC = "??????????????????????????????";
    private static final String ZIP_CODE = "????????????";
    private static final String CITY = "????????????";
    private static final String ADDRESS = "??????";
    private static final String NOTIFICATION_DATE = "?????????";
    private static final String NOTIFICATION_STATUS = "??????";
    private static final String ACCEPT_STATUS = "??????";
    private static final String BUILD_DATE = "???????????????";
    private static final String HOUSE_NUMBER = "??????";
    private static final String FLOOR_NUMBER = "??????";
    private static final String GROUP_FORM = "???????????????????????????";
    private static final String LAND_RIGHTS = "????????????";
    private static final String USE_FOR = "????????????";
    //Output CSV Supervise
    private static final String NO = "No.";
    private static final String OUTPUT_APARMENT_ID = "???????????????????????????ID";
    private static final String OUTPUT_APARTMENT_NAME = "??????????????????????????????";
    private static final String SUPERVISE_NOTICE_TYPE = "?????????????????????";
    private static final String APPENDIX_NO = "?????????";
    private static final String DOCUMENT_NO = "????????????";
    private static final String NOTICE_DATE = "???????????????";
    private static final String RECIPIENT_NAME_APARTMENT = "??????(??????????????????)";
    private static final String RECIPIENT_NAME_USER = "??????(?????????)";
    private static final String SENDER = "????????????";
    private static final String TEXT_MAILLING1 = "????????????1";
    private static final String EVIDENCE_BAR = "????????????1";
    private static final String EVIDENCE_NO = "????????????2";
    private static final String PERIOD_EVIDENCE = "????????????????????????";
    private static final String LAST_NOTICE_DATE = "?????????????????????";
    private static final String LAST_DOCUMENT_NO = "??????????????????";
    private static final String TEXT_MAILING2 = "????????????2";
    private static final String ADDRESS_CSV2 = "?????????";
    private static final String APARTMENT_NAME_CSV2 = "??????????????????";
    private static final String NOTIFICATION_FORMAT_NAME = "???????????????";
    private static final String NOTIFICATION_TIME_LIMIT = "????????????";
    private static final String CONTACT = "??????????????????";
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat myformatTime = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    IMansionInfoLogic mansionInfoLogic;

    @Autowired
    TBL100BDAO tbl100BDAO;

    @Autowired
    TBM001DAO tbm001DAO;

    @Autowired
    TBL100DAO tbl100DAO;

    @Override
    public PagingVo<ResultSearchVo> getListInformationMansion(SearchInformationMansionForm form) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    	try {
	        String userLogin = String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode());
	        int displayMaxRecordPerPage = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.GJA0110_LIST_DISPLAY_MAX));
	        Pageable pageable = PageUtil.getPageable(form.getPage(), displayMaxRecordPerPage);

	        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1030_I, form.toString()));
	        Page<ResultSearchVo> searchInformationMansionVoList = tbl100BDAO.searchInformationMansionPaging(pageable, form);

	        List<String> lstAparmentId = new ArrayList<>();
	        for (ResultSearchVo resultSearchVo : searchInformationMansionVoList) {
	            lstAparmentId.add(resultSearchVo.getLblApartmentId());
	        }

	        String[] aparmentIds = lstAparmentId.toArray(String[]::new);
	        String appartmentString = Arrays.toString(aparmentIds);
	        appartmentString = appartmentString.substring(1, appartmentString.length() - 1);

	        String finalAppartmentString = appartmentString;
	        List<ResultSearchVo> searchInformationMansionVoListWithAparmentIds = searchInformationMansionVoList.stream().map(element -> {
	            ResultSearchVo vo = new ResultSearchVo();
	            vo.setLblApartmentId(element.getLblApartmentId());
	            vo.setCityName(element.getCityName());
	            vo.setCityCode(element.getCityCode());
	            vo.setLblAddress(element.getCityCode() +  CommonConstants.DASH + element.getLblAddress() + CommonConstants.DASH + tbm001DAO.getCityNameByCityCode(element.getCityCode()));
	            vo.setLstIdApartment(finalAppartmentString);
	            vo.setLnkApartmentName(element.getLnkApartmentName());
	            vo.setNewestNotificationNo(element.getNewestNotificationNo());
	            vo.setUserLogin(userLogin);
	            String notifiCationDate = CommonConstants.DASH;

	            if (StringUtils.isNotEmpty(element.getLblNotificationDate())) {
	            	try {
						notifiCationDate = myformatTime.format(yyyyMMdd.parse(element.getLblNotificationDate()));
					} catch (Exception e) {
						notifiCationDate = CommonConstants.DASH;
					}
	            }

	            vo.setLblNotificationDate(notifiCationDate);

	            String lblNotificationStatus = CommonConstants.DASH;
	            try {
	            	lblNotificationStatus = myformatTime.format(yyyyMMdd.parse(element.getLblNotificationStatus()));

	                LocalDate notificationDateTime = LocalDate.parse(vo.getLblNotificationStatus(), DateTimeFormatter.ofPattern(SLASH_YYYYMMDD));

	                if (notificationDateTime.isBefore(DateTimeUtil.getCurrentSystemDateTime().toLocalDateTime().toLocalDate())) {
	                	lblNotificationStatus = CodeUtil.getLabel(CD036, CommonConstants.STR_1);
	                } else {
	                    lblNotificationStatus = CodeUtil.getLabel(CD036, CommonConstants.STR_2);
	                }
	            } catch (Exception e) {
	            	lblNotificationStatus = CommonConstants.DASH;
	            }
	            vo.setLblNotificationStatus(lblNotificationStatus);

	            String acceptStatus = CodeUtil.getLabel(CD030, element.getLblAcceptStatus());

	            if (StringUtils.isNotBlank(acceptStatus)) {
	                vo.setLblAcceptStatus(acceptStatus);
	            } else {
	                vo.setLblAcceptStatus(CommonConstants.DASH);
	            }
	            return vo;
	        }).collect(Collectors.toList());

	        Page<ResultSearchVo> pages = new PageImpl<>(
	                searchInformationMansionVoListWithAparmentIds,
	                pageable,
	                searchInformationMansionVoList.getTotalElements());
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1040_I, Integer.toString(searchInformationMansionVoListWithAparmentIds.size())));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
	        return new PagingVo<>(
	                pages,
	                PageUtil.getStartIndex(pages),
	                PageUtil.getLastIndex(pages),
	                PageUtil.getTotalPage(pages));
    	} catch (Exception ex) {
    		logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
    		throw ex;
    	}
    }

    @Override
    public void outputCsv(String[] apartmentIds, OutputStream outputStream) throws BusinessException, IOException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        String[] headerRecord = {NO, OUTPUT_APARMENT_ID, OUTPUT_APARTMENT_NAME,
                SUPERVISE_NOTICE_TYPE, APPENDIX_NO, DOCUMENT_NO, NOTICE_DATE, RECIPIENT_NAME_APARTMENT,
                RECIPIENT_NAME_USER, SENDER, TEXT_MAILLING1, EVIDENCE_BAR, EVIDENCE_NO, PERIOD_EVIDENCE, LAST_NOTICE_DATE, LAST_DOCUMENT_NO,
                TEXT_MAILING2, ADDRESS_CSV2, APARTMENT_NAME_CSV2, NOTIFICATION_FORMAT_NAME, NOTIFICATION_TIME_LIMIT, CONTACT};
        csvWriter.writeNext(headerRecord, true);

        for (int i = 0; i < apartmentIds.length; i++) {
            List<OutputCsvSuperivseVo> voList = tbl100BDAO.getMansionAndWriteCsv(apartmentIds[i]);
            if (!CollectionUtils.isEmpty(voList)) {
                OutputCsvSuperivseVo vo = voList.get(0);

                vo.setSuperviseNoticeType(CodeUtil.getValue(CommonConstants.CD022, vo.getSuperviseNoticeType()));
                if ((CommonConstants.STR_1).equals(vo.getEvidenceBar())) {
                    vo.setEvidenceNo(CodeUtil.getValue(CommonConstants.CD044, vo.getEvidenceNo()));
                } else if ((CommonConstants.STR_2).equals(vo.getEvidenceBar())) {
                    vo.setEvidenceNo(CodeUtil.getValue(CommonConstants.CD043, vo.getEvidenceNo()));
                }

                vo.setEvidenceBar(CodeUtil.getValue(CommonConstants.CD041, vo.getEvidenceBar()));
                vo.setPeriodEvidence(CodeUtil.getValue(CommonConstants.CD045, vo.getPeriodEvidence()));

                if(vo.getNoticeDate()!= null){
                    String noticeDateTemp = convertToJapanDate(vo.getNoticeDate());
                    vo.setNoticeDate(noticeDateTemp);
                }
                if(vo.getNotificationTimeLimit() != null){
                    String notificationTimeLimit = convertToJapanDate(vo.getNotificationTimeLimit());
                    vo.setNotificationTimeLimit(notificationTimeLimit);
                }

                String[] superviseMansionInfo = {String.valueOf(i + 1), vo.getOutputApartmentID(), vo.getOutputApartmentName(), vo.getSuperviseNoticeType(),
                        vo.getAppendixNo(), vo.getDocumentNo(), vo.getNoticeDate(), vo.getRecipientNameApartment(), vo.getRecipientNameUser(), vo.getSender(), vo.getTextMailing1(),
                        vo.getEvidenceBar(), vo.getEvidenceNo(), vo.getPeriodEvidence(), vo.getLastNoticeDate(), vo.getLastDocumentNo(), vo.getTextMailing2(), vo.getAddress(),
                        vo.getApartmentName(), vo.getNotificationFormatName(), vo.getNotificationTimeLimit(), vo.getContact()};
                csvWriter.writeNext(superviseMansionInfo, false);
            }

        }
        csvWriter.close();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public void outputCsvInformationMansion(String[] apartmentIds, OutputStream outputStream)
            throws BusinessException{
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            String[] headerRecord = {APARTMENT_ID, APARTMENT_NAME, APARTMENT_NAME_PHONETIC, ZIP_CODE, CITY, ADDRESS,
                    NOTIFICATION_DATE, NOTIFICATION_STATUS, ACCEPT_STATUS, BUILD_DATE, HOUSE_NUMBER,
                    FLOOR_NUMBER, GROUP_FORM, LAND_RIGHTS, USE_FOR};
            csvWriter.writeNext(headerRecord, true);

            for (int i = 0; i < apartmentIds.length; i++) {
                List<OutputCsvInformationSearchVo> voList = tbl100BDAO.getInformationSearchWriteCSV(apartmentIds[i]);

                if (!CollectionUtils.isEmpty(voList)) {
                    OutputCsvInformationSearchVo vo = voList.get(0);

                    if (vo.getNotificationDate() != null) {

                        LocalDate notificationDateTime = LocalDate.parse(vo.getNotificationDate(),
                                DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD));

                        vo.setNotificationDate(DateTimeUtil.getLocalDateAsString2(notificationDateTime));
                        if (notificationDateTime.isBefore(DateTimeUtil.getCurrentSystemDateTime().toLocalDateTime().toLocalDate())) {

                            vo.setNotificationStatus(CodeUtil.getLabel(CD036, CommonConstants.STR_1));
                            vo.setGroupForm(CommonConstants.BLANK);
                            vo.setLandRights(CommonConstants.BLANK);
                            vo.setUsefor(CommonConstants.BLANK);

                        } else {
                            vo.setNotificationStatus(CodeUtil.getLabel(CD036, CommonConstants.STR_2));
                            if (CommonConstants.STR_8.equals(vo.getGroupForm())) {
                                vo.setGroupForm("?????????" + "(" + vo.getGroupFormElse() + ")");
                            } else {
                                if (CodeUtil.getLabel(CD005, "1").equals(vo.getGroupForm())) {
                                    vo.setGroupForm(CodeUtil.getLabel(CD005, "1"));
                                } else if (CodeUtil.getLabel(CD005, "2").equals(vo.getGroupForm())) {
                                    vo.setGroupForm(CodeUtil.getLabel(CD005, "2"));
                                } else {
                                    vo.setGroupForm(CodeUtil.getLabel(CD005, "9"));
                                }
                            }
                            if (CommonConstants.STR_8.equals(vo.getLandRights())) {
                                vo.setGroupForm("?????????" + "(" + vo.getLandRightElse() + ")");
                            } else {
                                if (CodeUtil.getLabel(CD006, CommonConstants.STR_1).equals(vo.getLandRights())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD006, CommonConstants.STR_1));
                                } else if (CodeUtil.getLabel(CD006, CommonConstants.STR_2).equals(vo.getLandRights())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD006, CommonConstants.STR_2));
                                } else if (CodeUtil.getLabel(CD006, CommonConstants.STR_3).equals(vo.getLandRights())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD006, CommonConstants.STR_2));
                                } else {
                                    vo.setLandRights(CodeUtil.getLabel(CD006, CommonConstants.STR_9));
                                }
                            }
                            if (CommonConstants.STR_8.equals(vo.getUsefor())) {
                                vo.setUsefor("?????????" + "(" + vo.getUseForElse() + ")");
                            } else {
                                if (CodeUtil.getLabel(CD007, CommonConstants.STR_1).equals(vo.getUsefor())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD007, CommonConstants.STR_1));
                                } else if (CodeUtil.getLabel(CD007, CommonConstants.STR_2).equals(vo.getLandRights())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD007, CommonConstants.STR_2));
                                } else if (CodeUtil.getLabel(CD007, CommonConstants.STR_3).equals(vo.getLandRights())) {
                                    vo.setLandRights(CodeUtil.getLabel(CD007, CommonConstants.STR_3));
                                } else {
                                    vo.setLandRights(CodeUtil.getLabel(CD007, CommonConstants.STR_9));
                                }
                            }
                        }
                    } else if (vo.getAcceptStatus() != null) {
                        if (vo.getAcceptStatus().contains(CommonConstants.STR_1)) {
                            vo.setAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED));
                        } else if (vo.getAcceptStatus().contains(CommonConstants.STR_2)) {
                            vo.setAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CD030_ACCEPTED));
                        }
                        vo.setAcceptStatus(CodeUtil.getValue(CommonConstants.CD030, CD030_NOT_SPECIFIED));
                    }
                    if (vo.getBuildDay() != null || vo.getBuildMonth() != null || vo.getBuildYear() != null) {
                        vo.setBuiltDate(vo.getBuildYear() + CommonConstants.SLASH + vo.getBuildMonth() + CommonConstants.SLASH + vo.getBuildDay());
                    }

                    String[] outputSearchMansionInfo = {
                            vo.getApartmentId(), vo.getApartmentName(), vo.getZipCode(), vo.getCity(), vo.getAddress(),
                            vo.getNotificationDate(), vo.getNotificationStatus(), vo.getAcceptStatus(), vo.getBuiltDate(), vo.getHouseNumber(),
                            vo.getFloorNumber(), vo.getGroupForm(), vo.getLandRights(), vo.getUsefor()};
                    csvWriter.writeNext(outputSearchMansionInfo, false);
                }
            }
            csvWriter.close();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public boolean checkNewestNotification(String apartmentId, MultiValuedMap<String, String> mapNewestNotification) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String newestNotification = tbl100DAO.getNewestNotificationById(apartmentId);
        Collection<String> tempNotificationCollection = mapNewestNotification.get(apartmentId);
        for (String checked : tempNotificationCollection) {
            if (newestNotification.equals(checked)) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return true;
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return false;
    }

    private String convertToJapanDate(String date){
        DateTimeFormatter japaneseEraDtf = DateTimeFormatter.ofPattern("GGGGy???M???d???")
                .withChronology(JapaneseChronology.INSTANCE)
                .withLocale(Locale.JAPAN);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD);
        LocalDate gregorianDate = LocalDate.parse(date,formatter);
        JapaneseDate japaneseDate = JapaneseDate.from(gregorianDate);
        return japaneseDate.format(japaneseEraDtf);
    }
}

