/*
 * @(#) ProgressRecordLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL240DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300BDAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IProgressRecordLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecorInfoWrapperVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordAcceptUserVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

/**
 * @author PDQuang
 *
 */
@Service
public class ProgressRecordLogicImpl implements IProgressRecordLogic {

    private static final String DETAIL_VIEW = "0";
    private static final String REPORT_DISPLAY = "1";
    private static final String REGISTERED = "を登録しました";
    private static final String EMAILED = "を郵送しました<br/>（";
    private static final String CHANGED = "に変更しました<br/>（";
    private static final String SUPPORTED = "対応しました。<br/>（";
    private static final String BRACKETS = "）";
    private static final String CORRECTED_BY = "により訂正されました";
    private static final String EMAILED_NOTIFICATION = "届出受理通知をメールで通知しました";
    private static final String EMAILED_CHANGE_NOTIFICATION = "変更届出受理通知をメールで通知しました";
    private static final String EMAILED_ADVICE_NOTIFICATION = "助言通知をメールで通知しました";
    private static final String EMAILED_SURVEY_NOTIFICATION = "現地調査通知をメールで通知しました";
    private static final String PROGRESS_RECORD_INFORMATION = "経過記録情報";
    private static final String STR_05 = "05";
    private static final String STR_06 = "06";

    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL200DAO tbl200DAO;

    @Autowired
    private TBL240DAO tbl240DAO;

    @Autowired
    private TBL300BDAO tbl300BDAO;

    private static final Logger logger = LogManager.getLogger(ProgressRecordLogicImpl.class);
    
    @Override
    public StatusInformationVo getStatusInformation(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        StatusInformationVo statusInfo = null;
        TBL100Entity entityTbl100 = tbl100DAO.getMansionInformationById(apartmentId);

        if (entityTbl100 != null) {
            List<TBL240Entity> entityTbl240 = tbl240DAO.getSupervisedNoticeByApartmentIdAndNotificationNo(apartmentId);
            statusInfo = new StatusInformationVo();
            // check next notification date after system date, reportstatus is reported
            if (checkDateAfterSysDate(entityTbl100.getNextNotificationDate())) {
                // reported
                statusInfo.setReportStatus(CodeUtil.getLabel(CommonConstants.CD036, CommonConstants.STR_2));
                // set accept status form bitification info
                TBL200Entity entityTbl200 = tbl200DAO.getNotificationInfo(entityTbl100.getNewestNotificationNo());
                if (entityTbl200 != null) {
                    statusInfo.setAcceptedStatus(CodeUtil.getLabel(CommonConstants.CD030, entityTbl200.getAcceptStatus()));
                } else {
                    logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                    return null;
                }
            } else {
                // Unreported
                statusInfo.setReportStatus(CodeUtil.getLabel(CommonConstants.CD036, CommonConstants.STR_1));
                statusInfo.setAcceptedStatus(CodeUtil.getLabel(CommonConstants.CD030, CommonConstants.STR_1));
            }

            if (CollectionUtils.isEmpty(entityTbl240)) {
                statusInfo.setUrgeStatus(CodeUtil.getLabel(CommonConstants.CD037, CommonConstants.STR_1));
            } else {
                statusInfo.setUrgeStatus(CodeUtil.getLabel(CommonConstants.CD037, CommonConstants.STR_2));
            }
            statusInfo.setSupportCode(CodeUtil.getLabel(CommonConstants.CD021, entityTbl100.getSupport()));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return statusInfo;
    }

    /**
     * Check date is after system date
     * @param next Notification Date
     * @return boolean
     * @author pdquang
     */
    private Boolean checkDateAfterSysDate(LocalDate nextNotificationDate) {
        if (nextNotificationDate != null) {
            LocalDate systemDate = LocalDate.now();
            // get system date by localdate
            return nextNotificationDate.isAfter(systemDate);
        } else {
            return false;
        }
    }

    @Override
    public List<ProgressRecordDetailsVo> getListProgressRecordDetail(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<ProgressRecordDetailsVo> listVo = tbl300BDAO.getListProgressRecordDetailByApartmentId(apartmentId);
        List<ProgressRecordDetailsVo> listProgressRecordDetails = new ArrayList<ProgressRecordDetailsVo>();
        String progressRecordDetail = CommonConstants.BLANK;
        String reportName = CommonConstants.BLANK;
        String correspondDate = CommonConstants.BLANK;

        if (!CollectionUtils.isEmpty(listVo)) {
            ProgressRecordDetailsVo proRecordDetail = null;
            // edit date for add into list progress record detail
            for (ProgressRecordDetailsVo vo : listVo) {
                proRecordDetail = new ProgressRecordDetailsVo();
                // convert fomart date ymd
                correspondDate = DateTimeUtil.convertReFormatDate(vo.getCorrespondDate());
                proRecordDetail.setCorrespondDate(correspondDate);
                proRecordDetail.setProgressRecordNo(vo.getProgressRecordNo());
                proRecordDetail.setRelatedNumber(vo.getRelatedNumber());

                if (StringUtils.isNotEmpty(vo.getTypeCode())) {
                    // 帳票
                    proRecordDetail.setTypeCode(CodeUtil.getLabel(CommonConstants.CD033, vo.getTypeCode()));
                    proRecordDetail.setBtnDisplay(REPORT_DISPLAY);
                    reportName = getReportNameByTypeCode(vo.getTypeCode());
                    proRecordDetail.setReportName(reportName);
                    setProgressDetailByTypeCode(proRecordDetail, vo.getTypeCode(),
                                                vo.getAuthorityModifyFlag(), vo.getUserType());
                } else {
                    // 詳細
                    proRecordDetail.setTypeCode(CodeUtil.getLabel(CommonConstants.CD019, vo.getCorrespondTypeCode()));
                    proRecordDetail.setBtnDisplay(DETAIL_VIEW);
                    proRecordDetail.setReportName(CommonConstants.BLANK);
                    progressRecordDetail = getItemByCorrespondTypeCode(vo.getCorrespondTypeCode(),
                                                                       vo.getProgressRecordOverview(),
                                                                       vo.getNoticeTypeCode(), vo.getSupportCode());
                    proRecordDetail.setProgressRecordDetail(progressRecordDetail);
                }
                listProgressRecordDetails.add(proRecordDetail);
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return listProgressRecordDetails;
    }

    /**
     * set progress detail by type code
     * @param pro Record Detail
     * @param type Code
     * @param authority Modify Flag
     * @param user Type
     */
    private void setProgressDetailByTypeCode(ProgressRecordDetailsVo proRecordDetail, String typeCode,
                                             String authorityModifyFlag, String userType) {
        StringBuilder progressRecord = new StringBuilder(CommonConstants.BLANK);
        switch (typeCode) {
        case CommonConstants.STR_9:
            proRecordDetail.setProgressRecordDetail(EMAILED_NOTIFICATION);
            break;
        case CommonConstants.STR_10:
            proRecordDetail.setProgressRecordDetail(EMAILED_CHANGE_NOTIFICATION);
            break;
        case CommonConstants.STR_11:
            proRecordDetail.setProgressRecordDetail(EMAILED_ADVICE_NOTIFICATION);
            break;
        case CommonConstants.STR_12:
            proRecordDetail.setProgressRecordDetail(EMAILED_SURVEY_NOTIFICATION);
            break;
        case CommonConstants.STR_3:
        case CommonConstants.STR_4:
            if (CommonConstants.STR_1.equals(authorityModifyFlag)) {
                progressRecord.append(CodeUtil.getLabel(CommonConstants.CD033, typeCode));
                progressRecord.append(REGISTERED);
                progressRecord.append(CommonConstants.BR_LINE);
                progressRecord.append(CommonConstants.STR_STAR);
                progressRecord.append(CodeUtil.getLabel(CommonConstants.CD027, userType));
                progressRecord.append(CORRECTED_BY);
                proRecordDetail.setProgressRecordDetail(progressRecord.toString());
                break;
            }
            proRecordDetail.setProgressRecordDetail(CodeUtil.getLabel(CommonConstants.CD033, typeCode) + REGISTERED);
            break;
        default:
            proRecordDetail.setProgressRecordDetail(CodeUtil.getLabel(CommonConstants.CD033, typeCode) + REGISTERED);
            break;
        }
    }

    /**
     * get progress record detail by correspond type code
     * @param correspond Type Code
     * @param progress Record Overview
     * @param notice Type Code
     * @param support Code
     * @return String
     * @author pdquang
     */
    private String getItemByCorrespondTypeCode(String correspondTypeCode, String progressRecordOverview,
                                              String noticeTypeCode, String supportCode) {
        String progressRecordDetail = CommonConstants.BLANK;

        if (STR_05.equals(correspondTypeCode)) {
            progressRecordDetail = CodeUtil.getLabel(CommonConstants.CD020, noticeTypeCode) + EMAILED
                                   + progressRecordOverview + BRACKETS;
        } else if (STR_06.equals(correspondTypeCode)) {
            progressRecordDetail = CodeUtil.getLabel(CommonConstants.CD021, supportCode) + CHANGED
                                   + progressRecordOverview + BRACKETS;
        } else {
            progressRecordDetail = CodeUtil.getLabel(CommonConstants.CD019, correspondTypeCode) + SUPPORTED
                                   + progressRecordOverview + BRACKETS;
        }
        return progressRecordDetail;
    }

    /**
     * get report name by typeCode from tbl300
     * @param type Code
     * @return reportName
     * @author pdquang
     */
    private String getReportNameByTypeCode(String typeCode) {
        String reportName = CommonConstants.BLANK;

        // set type report
        switch (typeCode) {
        case CommonConstants.STR_1:
            reportName = CommonConstants.RP010_CREATE;
            break;
        case CommonConstants.STR_2:
            reportName = CommonConstants.RP010_UPDATE;
            break;
        case CommonConstants.STR_3:
        case CommonConstants.STR_4:
        case CommonConstants.STR_9:
        case CommonConstants.STR_10:
            reportName = CommonConstants.RP020;
            break;
        case CommonConstants.STR_5:
        case CommonConstants.STR_11:
            reportName = CommonConstants.RP030;
            break;
        case CommonConstants.STR_6:
        case CommonConstants.STR_12:
            reportName = CommonConstants.RP040;
            break;
        case CommonConstants.STR_7:
            reportName = CommonConstants.RP050;
            break;
        case CommonConstants.STR_8:
            reportName = CommonConstants.RP060;
            break;
        default:
            break;
        }
        return reportName;
    }

    @Override
    public ProgressRecorInfoWrapperVo getProgressRecordInfoForMEA0110(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ProgressRecorInfoWrapperVo wrapperVo = new ProgressRecorInfoWrapperVo();

        // 経過記録情報/受理情報/ユーザログイン（都区市町村）情報取得
        List<ProgressRecordAcceptUserVo> progressRecordAcceptUserList =
                tbl300BDAO.getProgressRecordAcceptUser(apartmentId, getTypeCodeForGetProgressRecordAcceptUser());

        List<ProgressRecordInfoVo> progressRecordList = new ArrayList<>();
        String lblCount = null;
        String lblNoInfoMessage = null;

        // Set progressRecordList
        for (ProgressRecordAcceptUserVo item : progressRecordAcceptUserList) {
            progressRecordList.add(formatToDisplay(item));
        }

        // Set lblCount and lblNoInfoMessage
        // 経過記録情報が存在しない場合は、本項目は非表示
        // 上記以外の場合、本項目は非表示
        // 経過記録情報がない場合は、メッセージ（I0002　｛0｝：経過記録情報）を設定する。
        if (CollectionUtils.isEmpty(progressRecordList)) {
            lblCount = CommonConstants.BLANK;
            lblNoInfoMessage = MessageUtil.getMessage(CommonConstants.MS_I0002, PROGRESS_RECORD_INFORMATION);
        } else {
            lblCount = formatLblTotal(progressRecordList.size());
        }

        wrapperVo.setProgressRecordList(progressRecordList);
        wrapperVo.setLblCount(lblCount);
        wrapperVo.setLblNoInfoMessage(lblNoInfoMessage);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return wrapperVo;
    }

    /**
     * Get typeCodeList for get progress record acceptance user.
     * @return codeList
     * @author tqvu1
     */
    private List<String> getTypeCodeForGetProgressRecordAcceptUser() {
        List<Code> cd033List = CodeUtil.getList(CommonConstants.CD033);
        List<String> codeList = new ArrayList<>();
        for (Code cd033 : cd033List) {
            if (!CommonConstants.CD033_DUNNING_NOTICE_1ST_TIME.equals(cd033.getLabel())
                    && !CommonConstants.CD033_DUNNING_NOTICE_OVER_2ND_TIME.equals(cd033.getLabel())) {
                codeList.add(cd033.getValue());
            }
        }
        return codeList;
    }

    /**
     * Format data before display.
     * @param ProgressRecordAcceptUserVo vo
     * @return ProgressRecordInfoVo
     * @author tqvu1
     */
    private ProgressRecordInfoVo formatToDisplay(ProgressRecordAcceptUserVo vo) {
        ProgressRecordInfoVo result = new ProgressRecordInfoVo();
        result.setLblCorrespondDate(DateTimeUtil.convertReFormatDate(vo.getCorrespondDate()));
        result.setLblProgressRecordType(CodeUtil.getLabel(CommonConstants.CD033, vo.getTypeCode()));
        // Set progress record detail
        StringBuilder sb = new StringBuilder(CommonConstants.BLANK);
        String typeCode = vo.getTypeCode();

        // 種別コード（CD033）により以下の通りに設定する
        // ①「届出受理」、「変更届出受理」の場合、かつ職権訂正フラグが職権訂正である（CD039）の場合
        if ((CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_NOTIFICATION_ACCEPTANCE).equals(typeCode)
                || CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_CHANGE_NOTIFICATION_ACCEPTANCE).equals(typeCode))
                && CodeUtil.getValue(CommonConstants.CD039, CommonConstants.CD039_AUTHORITY_CORRECTION).equals(vo.getAuthorityModifyFlag())) {
            // 種別コードのデコード値＋”を登録しました＋改行＋”※”＋ユーザ種別（CD027）のデコード値＋”により訂正されました”
            sb.append(CodeUtil.getLabel(CommonConstants.CD033, typeCode));
            sb.append(REGISTERED);
            sb.append(CommonConstants.BR_LINE);
            sb.append(CommonConstants.STR_STAR);
            sb.append(CodeUtil.getLabel(CommonConstants.CD027, vo.getUserType()));
            sb.append(CORRECTED_BY);
        } else if (CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_NOTI_RECEIPT_EMAIL_NOTI).equals(typeCode)) {
            // ②種別コード（CD033）が届出受理メール通知の場合、
            sb.append(EMAILED_NOTIFICATION);
        } else if (CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_CHANGE_NOTI_EMAIL_NOTI).equals(typeCode)) {
            // ③種別コード（CD033）が変更届出受理メール通知の場合、
            sb.append(EMAILED_CHANGE_NOTIFICATION);
        } else if (CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_ADVICE_EMAIL_NOTIFICATION).equals(typeCode)) {
            // ④種別コード（CD033）が助言メール通知の場合、
            sb.append(EMAILED_ADVICE_NOTIFICATION);
        } else if (CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_FIELDSURVEYMAILNOTIFICATION).equals(typeCode)) {
            // ⑤種別コード（CD033）が現地調査メール通知の場合、
            sb.append(EMAILED_SURVEY_NOTIFICATION);
        } else {
            // 上記以外の場合
            sb.append(CodeUtil.getLabel(CommonConstants.CD033, vo.getTypeCode()));
            sb.append(REGISTERED);
        }
        result.setLblProgressRecordDetail(sb.toString());

        // set other
        result.setRelatedNumber(vo.getRelatedNumber());
        result.setReportName(getReportNameByTypeCode(vo.getTypeCode()));
        return result;
    }

    /**
     * Format to display label total.
     * @param Integer total
     * @return String
     * @author tqvu1
     */
    private String formatLblTotal(Integer total) {
        StringBuilder sb = new StringBuilder(CommonConstants.BLANK);
        sb.append(CommonConstants.STR_TOTAL);
        sb.append(CommonConstants.SPACE_HALF_SIZE);
        sb.append(total);
        sb.append(CommonConstants.SPACE_HALF_SIZE);
        sb.append(CommonConstants.STR_CASE);
        return sb.toString();
    }

}