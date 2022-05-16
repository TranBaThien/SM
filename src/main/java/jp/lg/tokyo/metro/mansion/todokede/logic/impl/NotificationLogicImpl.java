/*
 * @(#) NotificationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 4, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AcceptStatus;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.Notice;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.NotificationStatus;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.TypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.INotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.NoticeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationVo;

/**
 * @author hntvy
 *
 */
@Service
public class NotificationLogicImpl implements INotificationLogic {

    private static final String BLANK_SPACE = "\\s";
    @Autowired
    private TBL100DAO tbl100DAO;

    @Autowired
    private TBL200DAO tbl200DAO;

    @Autowired
    private TBL300DAO tbl300DAO;
 
    private static final Logger logger = LogManager.getLogger(NotificationLogicImpl.class);
    
    @Override
    public NotificationVo getApartmentNotification(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL100Entity entity = tbl100DAO.getMansionInformationById(apartmentId);
        TBL200Entity tbl200 = tbl200DAO.getNotificationInfo(entity.getNewestNotificationNo());
        LocalDate ldObj = LocalDate.now();
        NotificationVo notification = new NotificationVo();
        if (entity.getNextNotificationDate().isBefore(ldObj) || tbl200 == null) {
            notification.setNotificationStatus(NotificationStatus.UNREPORTED.getNotificationStatus());
        } else {
            if (AcceptStatus.UNACCEPTED.getCode() == Integer.parseInt(tbl200.getAcceptStatus())) {
                if (tbl200.getChangeCount() == CommonConstants.NUM_0) {
                    notification
                            .setNotificationStatus(NotificationStatus.REPORTED_UNDER_REVIEW.getNotificationStatus());
                } else {
                    notification.setNotificationStatus(
                            NotificationStatus.CHANGE_NOTIFICATION_COMPLETED_UNDER_REVIEW.getNotificationStatus());
                }
            } else if (AcceptStatus.ACCEPTED.getCode() == Integer.parseInt(tbl200.getAcceptStatus())) {
                if (tbl200.getChangeCount() == CommonConstants.NUM_0) {
                    notification.setNotificationStatus(NotificationStatus.REPORTED_ACCEPTED.getNotificationStatus());
                } else { 
                    notification.setNotificationStatus(
                            NotificationStatus.CHANGE_NOTIFICATION_ACCEPTED.getNotificationStatus());
                }
            }
        }
        
        List<String> correspondDates = tbl100DAO.getLatestProgressInformationDate(apartmentId);
        List<TBL300Entity> list = new ArrayList<TBL300Entity>();
        for (String correspondDate : correspondDates) {
            List<TBL300Entity> progressRecords = tbl300DAO.getProgressRecordFromDate(apartmentId, correspondDate);
            list.addAll(progressRecords);
        }
        List<TBL300Entity> sublist = new ArrayList<TBL300Entity>(list.subList(0, Math.min(list.size(), 3)));
        List<NoticeVo> noticeVos = new ArrayList<NoticeVo>();
        for (TBL300Entity obj : sublist) {
            NoticeVo notice = new NoticeVo();
            if (Integer.parseInt(obj.getTypeCode()) == TypeCode.NOTIFICATION_ACCEPTANCE.getCode()
                    || Integer.parseInt(obj.getTypeCode()) == TypeCode.CHANGE_NOTIFICATION_ACCEPTANCE.getCode()) {
                notice.setCorresponDate(splitDateTimeStringIntoDateOnly(DateTimeUtil.convertReFormatDate(obj.getCorrespondDate())));
                notice.setNotice(Notice.ACCEPTANCE_NOTICE.getNotice());
                noticeVos.add(notice);
            } else if (Integer.parseInt(obj.getTypeCode()) == TypeCode.ADVISORY_NOTICE.getCode()) {
                notice.setCorresponDate(splitDateTimeStringIntoDateOnly(DateTimeUtil.convertReFormatDate(obj.getCorrespondDate())));
                notice.setNotice(Notice.ADVISORY_NOTICE.getNotice());
                noticeVos.add(notice);

            } else if (Integer.parseInt(obj.getTypeCode()) == TypeCode.FIELD_SURVEY_NOTIFICATION.getCode()) {
                notice.setCorresponDate(splitDateTimeStringIntoDateOnly(DateTimeUtil.convertReFormatDate(obj.getCorrespondDate())));
                notice.setNotice(Notice.FIELD_SURVEY_NOTICE.getNotice());
                noticeVos.add(notice);
            }
        }
        notification.setNoticeVos(noticeVos);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return notification;

    }

    private String splitDateTimeStringIntoDateOnly(String dateStringFormat) {
        return dateStringFormat.split(BLANK_SPACE)[0];
    }

}
