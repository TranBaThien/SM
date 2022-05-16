/*
 * @(#) ProgressRecordBatchRegistrationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordBatchRegistrationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */
@Service
public class ProgressRecordBatchRegistrationLogicImpl implements IProgressRecordBatchRegistrationLogic {

    private static final Logger logger = LogManager.getLogger(ProgressRecordBatchRegistrationLogicImpl.class);

    private static final String NOT_FOUND = "NOT FOUND";
    private static final String EMPTY_STRING = "";

    @Autowired
    private SequenceUtil sequenceUtil;

    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL300DAO tbl300DAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<ZAA0150ErrorVo> uploadCSV(List<ProgressRecordForm> lstProgress, String fileCountMax, List<String> lengthOfRowList) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<ZAA0150ErrorVo> validProgressRecord = validProgressRecord(lstProgress, fileCountMax, lengthOfRowList);

        if (CollectionUtils.isEmpty(validProgressRecord)) {
            for (ProgressRecordForm progress : lstProgress) {
                saveDataToTbl300(progress);
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return validProgressRecord;
    }

    @SuppressWarnings("resource")
    @Override
    public List<ProgressRecordForm> getDataFormCsv(byte[] file, List<String> lengthOfRowList) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        try {
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(file), CommonConstants.ENCODE_UTF8);
            BufferedReader reader = new BufferedReader(isr);

            ColumnPositionMappingStrategy<ProgressRecordForm> columnPostion = new ColumnPositionMappingStrategy<ProgressRecordForm>();
            columnPostion.setType(ProgressRecordForm.class);

            CsvToBean<ProgressRecordForm> csvToBean = new CsvToBeanBuilder<ProgressRecordForm>(reader)
                    .withType(ProgressRecordForm.class).withMappingStrategy(columnPostion)
                    .withIgnoreLeadingWhiteSpace(true).build();

            List<ProgressRecordForm> lstProgress = csvToBean.parse();

            CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(file)));
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                int a = nextRecord.length;
                lengthOfRowList.add(String.valueOf(a));
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return lstProgress;
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    // Save data to TBL300
    private TBL300Entity saveDataToTbl300(ProgressRecordForm form) {

        TBL300Entity tbl300Entity = new TBL300Entity();
        String progressRecordNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300),
                CommonConstants.COL_PROGRESS_RECORD_NO);

        tbl300Entity.setProgressRecordNo(progressRecordNo);
        tbl300Entity.setApartmentId(form.getApartmentId());
        tbl300Entity.setCorrespondDate(form.getCorrespondDate().concat(form.getCorrespondTime()));
        tbl300Entity.setCorrespondTypeCode(CodeUtil.getValue(CommonConstants.CD019, form.getCorrespondType()));
        tbl300Entity.setNoticeTypeCode(CodeUtil.getValue(CommonConstants.CD020, form.getNoticeType()));
        tbl300Entity.setProgressRecordOverview(form.getProgressRecordOverview());
        tbl300Entity.setProgressRecordDetail(form.getProgressRecordDetail());
        tbl300Entity.setDeleteFlag(CommonConstants.ZERO);
        tbl300Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl300Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { CommonConstants.TBL300, progressRecordNo }));
        return tbl300DAO.save(tbl300Entity);
    }

    // define error message

    // required message
    private String requiredMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0002, name);
    }

    // half size error message
    private String halfSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0003, name);
    }

    // half size alphanumeric error message
    private String halfSizeAlphaMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0005, name);
    }

    // full size error message
    private String fullSizeMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0006, name);
    }

    // max message
    private String maxMessage(String name, String max) {
        return MessageUtil.getMessage(CommonConstants.MS_E0012, name, max);
    }

    // special character message
    private String specialChar(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0015, name);
    }

    // Date message
    private String dateMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0008, name);
    }

    // Exist ApartmenId Message
    private String existMessage() {
        return MessageUtil.getMessage(CommonConstants.MS_E0117);
    }

    // NotContain Message
    private String notContaintMessage(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0100, name);
    }

    // check file row message
    private String fileRow(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0147, name);
    }

    // check file row message
    private String formatMessage() {
        return MessageUtil.getMessage(CommonConstants.MS_E0136);
    }
    
    // check file row message
    private String message0143(String name) {
        return MessageUtil.getMessage(CommonConstants.MS_E0143, name);
    }

    private List<ZAA0150ErrorVo> validProgressRecord(List<ProgressRecordForm> lstProgress, String fileCountMax, List<String> lengthOfRowList) {

        List<ZAA0150ErrorVo> listProgressRecordError = new ArrayList<ZAA0150ErrorVo>();

        // validate mansion info upload
        for (int i = 0; i < lstProgress.size(); i++) {
            ProgressRecordForm progress = lstProgress.get(i);

            // apartmentId
            // check required
            if (StringUtils.isEmpty(progress.getApartmentId())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_APARTMENT_ID,
                        CommonConstants.E0002, requiredMessage(CommonConstants.PR_ERR_APARTMENT_ID)));

            // check alpha numeric
            } else if (!isAlphaNumeric(progress.getApartmentId())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_APARTMENT_ID,
                        CommonConstants.E0005, halfSizeAlphaMessage(CommonConstants.PR_ERR_APARTMENT_ID)));

            // check max length
            } else if (lengthOf(progress.getApartmentId()) > 10) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_APARTMENT_ID,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_APARTMENT_ID, CommonConstants.STR_10)));
            }

            // correspondDate
            if (StringUtils.isEmpty(progress.getCorrespondDate())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_DATE,
                        CommonConstants.E0002, requiredMessage(CommonConstants.PR_ERR_CORRESPOND_DATE)));

            // check halfsize
            } else if (!isHalfSizeRatio(progress.getCorrespondDate())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_DATE,
                        CommonConstants.E0003, halfSizeMessage(CommonConstants.PR_ERR_CORRESPOND_DATE)));

            // check date
            } else if (!isTrueDate(progress.getCorrespondDate())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_DATE,
                        CommonConstants.E0008, dateMessage(CommonConstants.PR_ERR_CORRESPOND_DATE)));

            // check max length
            } else if (lengthOf(progress.getCorrespondDate()) > 8) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_DATE,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_CORRESPOND_DATE, CommonConstants.STR_8)));
            }

            // correspondTime
            // check required
            if (StringUtils.isEmpty(progress.getCorrespondTime())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TIME,
                        CommonConstants.E0002, requiredMessage(CommonConstants.PR_ERR_CORRESPOND_TIME)));

            // check halfsize
            } else if (!isHalfSizeRatio(progress.getCorrespondTime())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TIME,
                        CommonConstants.E0003, halfSizeMessage(CommonConstants.PR_ERR_CORRESPOND_TIME)));

            // check time
            } else if (!isTrueTime(progress.getCorrespondTime())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TIME,
                        CommonConstants.E0008, dateMessage(CommonConstants.PR_ERR_CORRESPOND_TIME)));

            // check max length
            } else if (lengthOf(progress.getCorrespondTime()) > 4) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TIME,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_CORRESPOND_TIME, CommonConstants.STR_4)));
            }

            // correspondType
            // check required
            if (StringUtils.isEmpty(progress.getCorrespondType())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TYPE,
                        CommonConstants.E0002, requiredMessage(CommonConstants.PR_ERR_CORRESPOND_TYPE)));

            // check fullsize
            } else if (!isFullSizeString(progress.getCorrespondType())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TYPE,
                        CommonConstants.E0006, fullSizeMessage(CommonConstants.PR_ERR_CORRESPOND_TYPE)));

            // check max length
            } else if (lengthOf(progress.getCorrespondType()) > 20) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_CORRESPOND_TYPE,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_CORRESPOND_TYPE, CommonConstants.STR_20)));
            }

            // noticeType
            // check fullsize
            if (!isFullSizeString(progress.getNoticeType())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_NOTICE_TYPE,
                        CommonConstants.E0006, fullSizeMessage(CommonConstants.PR_ERR_NOTICE_TYPE)));

            // check max length
            } else if (lengthOf(progress.getNoticeType()) > 13) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_NOTICE_TYPE,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_NOTICE_TYPE, CommonConstants.STR_13)));
            }

            // progressRecordOverview
            // check required
            if (StringUtils.isEmpty(progress.getProgressRecordOverview())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_PROGRESS_RECORD_OVERVIEW,
                        CommonConstants.E0002, requiredMessage(CommonConstants.PR_ERR_PROGRESS_RECORD_OVERVIEW)));

            // check max length
            } else if (lengthOf(progress.getProgressRecordOverview()) > 30) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_PROGRESS_RECORD_OVERVIEW,
                        CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_PROGRESS_RECORD_OVERVIEW, CommonConstants.STR_30)));

            // check special character
            } else if (!CommonUtils.isNotSpecialCharacter(progress.getProgressRecordOverview())) {
                listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_PROGRESS_RECORD_OVERVIEW,
                        CommonConstants.E0015, specialChar(CommonConstants.PR_ERR_PROGRESS_RECORD_OVERVIEW)));
            }

            // progressRecordDetail
            // check max length
            if (lengthOf(progress.getProgressRecordDetail()) > 300) {
                listProgressRecordError
                        .add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_PROGRESS_RECORD_DETAIL,
                                CommonConstants.E0012, maxMessage(CommonConstants.PR_ERR_PROGRESS_RECORD_DETAIL, CommonConstants.STR_300)));
            // check special character
            } else if (!CommonUtils.isNotSpecialCharacter(progress.getProgressRecordDetail())) {
                listProgressRecordError
                        .add(new ZAA0150ErrorVo(String.valueOf(i + 1), CommonConstants.PR_PROGRESS_RECORD_DETAIL,
                                CommonConstants.E0015, specialChar(CommonConstants.PR_ERR_PROGRESS_RECORD_DETAIL)));
            }
        }
        
        if (listProgressRecordError.isEmpty()) {
            List<String> apartmentIdList = tbl100DAO.getListApartmentId();
            
            for (int j = 0; j < lstProgress.size(); j++) {
                ProgressRecordForm progress = lstProgress.get(j);
             // マンション情報テーブル（TBL100）で同じマンションIDが存在しない、エラー / check if ApartmentId not exist in TBL100 -> error
                if (!apartmentIdList.contains(progress.getApartmentId())) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.PR_APARTMENT_ID,
                            CommonConstants.E0117, existMessage()));
                }

                // ログインユーザチェック
                // if user type code is City
                if (SecurityUtil.getUserLoggedInInfo().getUserTypeCode().equals(UserTypeCode.CITY)) {
                    // ログインユーザが区市町村ユーザの場合、マンション情報の区市町村コードとセッション情報のログインユーザの区市町村コードと異なる場合、エラーとする。
                    //if userLoggedInInfo.cityCode != apartmentInfo.cityCode -> error
                    if (!SecurityUtil.getUserLoggedInInfo().getCityCode().equals(tbl100DAO.getCityCodeByApartmentId(progress.getApartmentId()))) {
                        listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1),
                                CommonConstants.PR_APARTMENT_ID, CommonConstants.E0117, existMessage()));
                    }
                }

                // 対応種別の値がCD019のデコード値以外の場合、エラー
                if (CodeUtil.getValue(CommonConstants.CD019, progress.getCorrespondType(), NOT_FOUND).equals(NOT_FOUND)) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.PR_CORRESPOND_TYPE,
                            CommonConstants.E0100, notContaintMessage(CommonConstants.PR_CORRESPOND_TYPE)));
                }

                // 対応種別の値がCD019「都支援対象変更」の場合、エラー
                if (CodeUtil.getLabel(CommonConstants.CD019, CommonConstants.ZERO_SIX).equals(progress.getCorrespondType())) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.PR_CORRESPOND_TYPE,
                            CommonConstants.E0143, message0143(CommonConstants.PR_ERR_CORRESPOND_TYPE_2)));
                }

                // 対応種別(correspondType)が通知書郵送の場合は本項目が入力されていない、通知書郵送以外の場合は空白でない場合、エラー
                if (CodeUtil.getLabel(CommonConstants.CD019, CommonConstants.ZERO_FIVE)
                        .equals(progress.getCorrespondType())
                        && !GenericValidator.isBlankOrNull(progress.getNoticeType())) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.PR_NOTICE_TYPE,
                            CommonConstants.E0002, requiredMessage(CommonConstants.PR_NOTICE_TYPE)));

                }
                // 対応種別(correspondType)が通知書郵送の場合、通知書種別の値がCD020のデコード値以外の場合、エラー
                else if (CodeUtil.getLabel(CommonConstants.CD019, CommonConstants.ZERO_FIVE).equals(progress.getCorrespondType())
                        && CodeUtil.getValue(CommonConstants.CD020, progress.getNoticeType(), NOT_FOUND)
                                .equals(NOT_FOUND)) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), CommonConstants.PR_NOTICE_TYPE,
                            CommonConstants.E0100, notContaintMessage(CommonConstants.PR_NOTICE_TYPE)));
                }

                // 項目個数チェック
                if (Integer.parseInt(lengthOfRowList.get(j)) != 7) {
                    listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(j + 1), EMPTY_STRING, CommonConstants.E0136, formatMessage()));
                }
            }
        }

        // 上限数のチェック / check file if row > GEC0110_FILE_COUNT_MAX
        int countMax = Integer.parseInt(fileCountMax);
        if (lstProgress.size() > countMax) {
            listProgressRecordError.add(new ZAA0150ErrorVo(String.valueOf(1), EMPTY_STRING, CommonConstants.E0147, fileRow(fileCountMax)));
        }

        return listProgressRecordError;
    }

    /**
     * get Length of String, if null -> length = 0
     *
     * @param str String
     * @return
     */
    private int lengthOf(String str) {
        return (null == str) ? 0 : str.length();
    }

    /**
     * alpha numeric check
     *
     * @param input String
     * @return
     */
    public static boolean isAlphaNumeric(String input) {

        if (null == input) {
            return true;
        }

        return CheckUtil.isPatternMatch("/^[A-Za-z0-9+-.@/]*$/", input);
    }

    /**
     * halfsize ratio check
     *
     * @param ratio String
     * @return
     */
    public static boolean isHalfSizeRatio(String ratio) {
        if (null == ratio) {
            return true;
        }
        return CheckUtil.isPatternMatch("/^[\\.0-9]*$/", ratio);
    }

    /**
     * fullsize string check
     *
     * @param input String
     * @return
     */
    public static boolean isFullSizeString(String input) {

        if (null == input) {
            return true;
        }
        return CheckUtil.isValidX0208(input);
    }

    /**
     * check date
     *
     * @param strDate String
     * @return
     */
    private boolean isTrueDate(String strDate) {
        try {
            LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (localDate == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * check time
     *
     * @param strTime String
     * @return
     */
    private boolean isTrueTime(String strTime) {
        try {
            LocalTime localTime = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HHmm"));
            if (localTime == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }

    }
}
