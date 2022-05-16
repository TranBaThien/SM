/*
 * @(#) TemporaryNotificationInfoLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.DeleteFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.converter.NotificationConverter;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL205DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL215DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205EntityPK;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.AcceptanceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.transaction.SystemException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author DLLy
 */
@Service
public class TemporaryNotificationInfoLogicImpl implements ITemporaryNotificationInfoLogic {
    private static final Logger logger = LogManager.getLogger(TemporaryNotificationInfoLogicImpl.class);

    @Autowired
    private SequenceUtil sequenceUtil;
    @Autowired
    private TBL205DAO tbl205DAO;
    @Autowired
    private TBL215DAO tbl215DAO;
    @Autowired
    private TBL100DAO tbl100DAO;

    /**
     * @param apartmentId, status, tempKbn, notificationType
     * @param isCreated
     * @return
     */
    @Override
    public NotificationInfoVo getTemporaryData(String apartmentId, String status, String tempKbn, boolean isCreated) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL205Entity tbl205Entity = tbl205DAO.getTemporaryData(apartmentId, status, tempKbn, isCreated ? null : STR_1);
        if (Objects.nonNull(tbl205Entity)) {
            NotificationInfoVo notificationInfoVo = new NotificationInfoVo();
            //Copy properties
            CommonUtils.copyProperties(notificationInfoVo, tbl205Entity);
            notificationInfoVo.setApartmentId(tbl205Entity.getId().getApartmentId());

            if (isNotBlank(tbl205Entity.getZipCode())) {
                //Split Zip Code by Dash
                String[] zipCode = tbl205Entity.getZipCode().split(DASH);
                notificationInfoVo.setTxtApartmentZipCode1(zipCode[NUM_0]);
                notificationInfoVo.setTxtApartmentZipCode2(zipCode[NUM_1]);
            }

            if (isNotBlank(tbl205Entity.getManagerZipCode())) {
                //Split Manager Zip Code by Dash
                String[] mnZipCode = tbl205Entity.getManagerZipCode().split(DASH);
                notificationInfoVo.setTxtManagerZipCode1(mnZipCode[NUM_0]);
                notificationInfoVo.setTxtManagerZipCode2(mnZipCode[NUM_1]);
            }

            if (isNotBlank(tbl205Entity.getContactZipCode())) {
                //Split Contact Zip Code by Dash
                String[] ctZipCode = tbl205Entity.getContactZipCode().split(DASH);
                notificationInfoVo.setTxtContactZipCode1(ctZipCode[NUM_0]);
                notificationInfoVo.setTxtContactZipCode2(ctZipCode[NUM_1]);
            }

            if (isNotBlank(tbl205Entity.getManagerTelNo())) {
                //Split Manager TelNo by Dash
                String[] mnTelNo = tbl205Entity.getManagerTelNo().split(DASH);
                notificationInfoVo.setTxtManagerTelno1(mnTelNo[NUM_0]);
                notificationInfoVo.setTxtManagerTelno2(mnTelNo[NUM_1]);
                notificationInfoVo.setTxtManagerTelno3(mnTelNo[NUM_2]);
            }

            if (isNotBlank(tbl205Entity.getContactTelNo())) {
                //Split Contact TelNo by Dash
                String[] ctTelNo = tbl205Entity.getContactTelNo().split(DASH);
                notificationInfoVo.setTxtContactTelno1(ctTelNo[NUM_0]);
                notificationInfoVo.setTxtContactTelno2(ctTelNo[NUM_1]);
                notificationInfoVo.setTxtContactTelno3(ctTelNo[NUM_2]);
            }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return notificationInfoVo;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    /**
     * @param apartmentId, status
     * @return void
     */
    @Override
    public void deleteTemporarySavedData(String apartmentId, String status, String tempKbn) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (StringUtils.isBlank(tempKbn)) {
            if (tbl205DAO.countByApartmentIdAndStatus(apartmentId, status) > 0) {
                tbl205DAO.deleteByApartmentIdAndStatus(apartmentId, status);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, TBL205, apartmentId));
            }
        } else {
            if (tbl205DAO.countByApartmentIdAndStatusAndTempKbn(apartmentId, status, tempKbn) > 0) {
                tbl205DAO.deleteByApartmentIdAndStatusAndTempKbn(apartmentId, status, tempKbn);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, TBL205, apartmentId));
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * @param vo, userId, tempKbn, idScreen
     * @return BusinessException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteAndSaveTemporaryData(NotificationInfoVo vo, CurrentUserInformation user, String tempKbn, String idScreen) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        //一時保存データ削除 / Delete temporarily saved data
        deleteTemporarySavedData(vo.getApartmentId(), CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED), tempKbn);

        //届出情報（一時保存）（TBL205）/ Save data in TBL205
        saveDataToTbl205(vo, user, tempKbn, idScreen);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * 一時的な受け入れを保存する
     * save temporary acceptance
     *
     * @param apartmentId     String
     * @param acceptanceForm  NotificationAcceptanceForm
     * @param tempKbn         String
     * @param updatedUserInfo CurrentUserInformation
     * @throws BusinessException when Apartment not found with apartmentId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveTemporaryAcceptance(String apartmentId,
                                        NotificationAcceptanceForm acceptanceForm,
                                        String tempKbn,
                                        CurrentUserInformation updatedUserInfo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL100Entity tbl100Entity = tbl100DAO.getMansionInformationById(apartmentId);
        if (ObjectUtils.isEmpty(tbl100Entity)) {
            throw new BusinessException();
        }

        tbl215DAO.deleteByNotificationNoAndTempKbn(tbl100Entity.getNewestNotificationNo(), tempKbn);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, TBL215, tbl100Entity.getNewestNotificationNo()));

        TBL215Entity tbl215Entity = generateTBL215Entity(tbl100Entity.getNewestNotificationNo(), acceptanceForm, tempKbn, updatedUserInfo);
        tbl215Entity = tbl215DAO.save(tbl215Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL215, tbl215Entity.getTempNo()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * 一時的に保存された受け入れデータを削除する
     * delete temporary saved acceptance data
     *
     * @param notificationNo String
     */
    @Override
    public void deleteAcceptanceTemporaryData(String notificationNo) {
        tbl215DAO.deleteByNotificationNo(notificationNo);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, TBL215, notificationNo));
    }

    /**
     * チェックはアクティブな受け入れ復元ボタンです
     * check is Active Acceptance Restore Button
     *
     * @param newestNotificationNo String
     * @param tempKbn              String
     * @return true if temporarily data existed
     */
    @Override
    public boolean isActiveAcceptanceRestoreButton(String newestNotificationNo, String tempKbn) {
        return tbl215DAO.countByNotificationNoAndTempKbn(newestNotificationNo, tempKbn) > 0;
    }

    /**
     * get Acceptance TemporaryData
     *
     * @param notificationNo String
     * @param tempKbn        String
     * @return AcceptanceNotificationVo
     */
    @Override
    public AcceptanceNotificationVo getAcceptanceTemporaryData(String notificationNo, String tempKbn) {
        return tbl215DAO.findByNotificationNoAndTempKbn(notificationNo, tempKbn)
                .map(NotificationConverter::toAcceptanceNotificationVo)
                .orElse(null);
    }

    /**
     * @param vo, userId
     * @return
     * @throws
     */
    private void saveDataToTbl205(NotificationInfoVo vo, CurrentUserInformation user, String tempKbn, String idScreen) throws SystemException {
        TBL205Entity entity = new TBL205Entity();
        //convert input to Entity
        CommonUtils.copyProperties(entity, vo);
        boolean isCreated = isBlank(vo.getNotificationNo());

        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL205), COL_TEMP_NO);
        entity.setId(new TBL205EntityPK(keyNo, vo.getApartmentId()));
        entity.setAcceptStatus(CodeUtil.getValue(CD030, CD030_UNACCEPTED));

        if (SCREEN_ID_MDA0110.equals(idScreen)) {
            entity.setChangeCount(isCreated ? NUM_0 : vo.getChangeCount() + NUM_1);
        } else if (SCREEN_ID_GDA0110.equals(idScreen)) {
            entity.setChangeCount(vo.getChangeCount());
        }

        entity.setNotificationCount(SCREEN_ID_MDA0110.equals(idScreen) && isCreated ? vo.getNotificationCount() + NUM_1 : vo.getNotificationCount());

        entity.setReceptionNo(null);
        entity.setAdviceDoneFlag(null);
        entity.setTempKbn(tempKbn);
        entity.setBuiltDate(vo.getBuiltDate());

        entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
        entity.setUpdateUserId(user.getUserId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        tbl205DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL205, entity.getId().toString()));
    }

    /**
     * generate TBL215Entity
     * @param notificationNo String
     * @param form NotificationAcceptanceForm
     * @param tempKbn String
     * @param updatedUserInfo CurrentUserInformation
     * @return TBL215Entity
     */
    private TBL215Entity generateTBL215Entity(String notificationNo,
                                              NotificationAcceptanceForm form,
                                              String tempKbn,
                                              CurrentUserInformation updatedUserInfo) {
        final String CORRECTION = "1";
        final String NOT_CORRECTION = "0";
        final String NEW_NOTIFICATION = "1";
        final String UPDATE_NOTIFICATION = "2";
        TBL215Entity entity = new TBL215Entity();
        String tempNo = sequenceUtil.generateKey(TBL215, COL_TEMP_NO);
        entity.setTempNo(tempNo);
        entity.setNotificationNo(notificationNo);
        entity.setTempKbn(tempKbn);
        entity.setAcceptUserId(updatedUserInfo.getUserId());
        entity.setAcceptUserName(updatedUserInfo.getDisplayName());
        entity.setAppendixNo(form.getTxtAppendixNo());
        entity.setDocumentNo(form.getTxtDocumentNo());
        entity.setNoticeDate(LocalDate.parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setRecipientNameApartment(form.getLblRecipientNameApartment());
        entity.setRecipientNameUser(form.getLblRecipientNameUser());
        entity.setSender(form.getTxtSender());
        entity.setNoticeDate(LocalDate.parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setEvidenceBar("15".equals(form.getLblEvidenceBar()) ? NEW_NOTIFICATION : UPDATE_NOTIFICATION);
        entity.setEvidenceNo(form.getLstEvidenceNo());
        entity.setRemarks(form.getTxaRemarks());
        entity.setAuthorityModifyFlag(StringUtils.isEmpty(form.getTxaOrrectionDetails()) ? NOT_CORRECTION : CORRECTION);
        entity.setModifyDetails(form.getTxaOrrectionDetails());
        entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId(updatedUserInfo.getUserId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        entity.setNotificationDate(LocalDate.parse(form.getLblNotificationDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        return entity;
    }
}