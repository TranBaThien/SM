/*
 * @(#) NotificationInfoLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import com.opencsv.bean.*;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.DeleteFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.*;
import jp.lg.tokyo.metro.mansion.todokede.converter.NotificationConverter;
import jp.lg.tokyo.metro.mansion.todokede.dao.*;
import jp.lg.tokyo.metro.mansion.todokede.entity.*;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationExportForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.INotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ITemporaryNotificationInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML040Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoImportCsvVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.SystemException;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.*;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * @author DLLy
 */
@Service
public class NotificationInfoLogicImpl implements INotificationInfoLogic {
    private static final Logger logger = LogManager.getLogger(NotificationInfoLogicImpl.class);

    @Autowired
    private SequenceUtil sequenceUtil;
    @Autowired
    private TBL200DAO tbl200DAO;
    @Autowired
    private TBL300DAO tbl300DAO;
    @Autowired
    private TBL100DAO tbl100DAO;
    @Autowired
    private TBL105DAO tbl105DAO;
    @Autowired
    private TBL210DAO tbl210DAO;
    @Autowired
    private TBL110DAO tbl110DAO;
    @Autowired
    private TBL240DAO tbl240DAO;
    @Autowired
    private IEMailLogic mailLogic;
    @Autowired
    private ITemporaryNotificationInfoLogic temporaryNotificationInfoLogic;

    /**
     * @param notificationNo
     * @return
     */
    @Override
    public NotificationInfoVo getNotificationInfo(String notificationNo) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (isNotBlank(notificationNo)) {
            TBL200Entity tbl200Entity = tbl200DAO.getNotificationInfo(notificationNo);
            if (Objects.nonNull(tbl200Entity)) {
                NotificationInfoVo notificationInfoVo = new NotificationInfoVo();
                //Copy properties
                CommonUtils.copyProperties(notificationInfoVo, tbl200Entity);
                notificationInfoVo.setNotificationNo(tbl200Entity.getId().getNotificationNo());
                notificationInfoVo.setApartmentId(tbl200Entity.getId().getApartmentId());

                if (isNotBlank(tbl200Entity.getZipCode())) {
                    //Split Zip Code by Dash
                    String[] zipCode = tbl200Entity.getZipCode().split(DASH);
                    notificationInfoVo.setTxtApartmentZipCode1(zipCode[NUM_0]);
                    notificationInfoVo.setTxtApartmentZipCode2(zipCode[NUM_1]);
                }

                if (isNotBlank(tbl200Entity.getManagerZipCode())) {
                    //Split Manager Zip Code by Dash
                    String[] mnZipCode = tbl200Entity.getManagerZipCode().split(DASH);
                    notificationInfoVo.setTxtManagerZipCode1(mnZipCode[NUM_0]);
                    notificationInfoVo.setTxtManagerZipCode2(mnZipCode[NUM_1]);
                }

                if (isNotBlank(tbl200Entity.getContactZipCode())) {
                    //Split Contact Zip Code by Dash
                    String[] ctZipCode = tbl200Entity.getContactZipCode().split(DASH);
                    notificationInfoVo.setTxtContactZipCode1(ctZipCode[NUM_0]);
                    notificationInfoVo.setTxtContactZipCode2(ctZipCode[NUM_1]);
                }

                if (isNotBlank(tbl200Entity.getManagerTelNo())) {
                    //Split Manager TelNo by Dash
                    String[] mnTelNo = tbl200Entity.getManagerTelNo().split(DASH);
                    notificationInfoVo.setTxtManagerTelno1(mnTelNo[NUM_0]);
                    notificationInfoVo.setTxtManagerTelno2(mnTelNo[NUM_1]);
                    notificationInfoVo.setTxtManagerTelno3(mnTelNo[NUM_2]);
                }

                if (isNotBlank(tbl200Entity.getContactTelNo())) {
                    //Split Contact TelNo by Dash
                    String[] ctTelNo = tbl200Entity.getContactTelNo().split(DASH);
                    notificationInfoVo.setTxtContactTelno1(ctTelNo[NUM_0]);
                    notificationInfoVo.setTxtContactTelno2(ctTelNo[NUM_1]);
                    notificationInfoVo.setTxtContactTelno3(ctTelNo[NUM_2]);
                }
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return notificationInfoVo;
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    /**
     * @param vo, idScreen, userId, nextNotificationDateMax, notificationRenewalPeriod
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteTemporaryAndSaveNotification(NotificationInfoVo vo,
                                                   String idScreen,
                                                   CurrentUserInformation user,
                                                   String nextNotificationDateMax,
                                                   String notificationRenewalPeriod) throws SystemException, BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        //一時保存データ削除 / Delete temporarily saved data
        temporaryNotificationInfoLogic.deleteTemporarySavedData(vo.getApartmentId(), CodeUtil.getValue(CommonConstants.CD030, CD030_UNACCEPTED), null);
        saveNotificationRegistration(vo, idScreen, user, nextNotificationDateMax, notificationRenewalPeriod, null, null);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public List<NotificationInfoImportCsvVo> getDataFormCsv(byte[] file) {
        try {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(file), CommonConstants.ENCODE_UTF8);
            BufferedReader reader = new BufferedReader(isr);

            ColumnPositionMappingStrategy<NotificationInfoImportCsvVo> columnPosition = new ColumnPositionMappingStrategy<>();
            columnPosition.setType(NotificationInfoImportCsvVo.class);

            CsvToBean<NotificationInfoImportCsvVo> csvToBean = new CsvToBeanBuilder<NotificationInfoImportCsvVo>(reader)
                    .withType(NotificationInfoImportCsvVo.class)
                    .withMappingStrategy(columnPosition)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<NotificationInfoImportCsvVo> lstNotificationVo = csvToBean.parse();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            //Get record No1, because record no0 is header of CSV
            return lstNotificationVo;

        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    @Override
    public boolean exportCsv(NotificationExportForm form, OutputStream ops) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops))) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            CustomMappingStrategy<NotificationInfoImportCsvVo> mappingStrategy = new CustomMappingStrategy<>();
            mappingStrategy.setType(NotificationInfoImportCsvVo.class);

            NotificationInfoImportCsvVo vo = new NotificationInfoImportCsvVo();
            vo.setFormFrom(form);
            //Export csv from bean data,
            StatefulBeanToCsv<NotificationInfoImportCsvVo> beanToCsv = new StatefulBeanToCsvBuilder<NotificationInfoImportCsvVo>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .withOrderedResults(false)
                    .withApplyQuotesToAll(false)
                    .build();
            
            beanToCsv.write(vo);
            writer.flush();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return true;
        } catch (Exception e) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return false;
        }
    }

    /**
     * save Notification Registration
     *
     * @param vo
     * @param idScreen
     * @param user
     * @param nextNotificationDateMax
     * @param notificationRenewalPeriod
     * @param acceptNo
     * @param rdoNotificationMethod
     * @throws BusinessException
     */
    private void saveNotificationRegistration(NotificationInfoVo vo,
                                              String idScreen,
                                              CurrentUserInformation user,
                                              String nextNotificationDateMax,
                                              String notificationRenewalPeriod,
                                              String acceptNo,
                                              String rdoNotificationMethod) throws SystemException, BusinessException {
        //届出情報（TBL200）/ Save data in TBL200
        saveDataToTbl200(vo, user, idScreen);

        //経過記録登録(TBL300) / Save data in TBL300
        saveDataToTbl300(vo, idScreen, user, acceptNo, rdoNotificationMethod);

        //マンション情報（TBL100）and マンション情報履歴（TBL105) / Update data in TBL100 and TBL105
        updateDataToTbl100(vo, user, idScreen, nextNotificationDateMax, notificationRenewalPeriod);

        //督促通知（TBL240）/ Update data in TBL240
        updateDataToTbl240(vo, user);
    }

    /**
     * @param vo, user
     * @return
     * @throws BusinessException
     */
    private void updateDataToTbl240(NotificationInfoVo vo, CurrentUserInformation user) {
        List<TBL240Entity> entities = tbl240DAO.getSupervisedNoticeByApartmentId(vo.getApartmentId());
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(it -> {
                it.setNotificationNo(vo.getNotificationNo());
                it.setUpdateUserId(user.getUserId());
                it.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
            });
            tbl240DAO.saveAll(entities);
        }
    }

    /**
     * @param vo, entity, userId, isCreated, idScreen
     * @return
     * @throws BusinessException
     */
    private TBL200Entity saveDataToTbl200(NotificationInfoVo vo, CurrentUserInformation user, String idScreen) throws SystemException, BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        boolean isCreated = StringUtils.isBlank(vo.getNotificationNo());

        TBL200Entity entity = new TBL200Entity();
    	CommonUtils.copyProperties(entity, vo);
        entity.setRepairMonthlyCost(vo.getRepairMonthlyCost());
        entity.setUpdateUserId(user.getUserId());
        entity.setUpdateDatetime(LocalDateTime.now());
        entity.setTempKbn(CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_FORMAL));
        entity.setAdviceDoneFlag(CodeUtil.getValue(CD011, CD011_NOT_IMPLEMENTED));
        entity.setCityCode(vo.getCityCode());

        switch (idScreen) {
            case SCREEN_ID_MDA0110:
                boolean isUpdated = isNotBlank(vo.getNotificationNo()) && LocalDate.now().isBefore(vo.getNextNotificationDate());
                entity.setChangeCount(isUpdated ? (vo.getChangeCount() + NUM_1) : NUM_0);
                entity.setNotificationCount(isUpdated ? vo.getNotificationCount() : (vo.getNotificationCount() + NUM_1));
                String keyReceptionNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL200), COL_RECEPTION_NO);
                entity.setReceptionNo(keyReceptionNo);
                entity.setAcceptStatus(CodeUtil.getValue(CD030, CD030_UNACCEPTED));
                break;
            case SCREEN_ID_GDA0110:
                entity.setChangeCount(vo.getChangeCount());
                entity.setNotificationCount(vo.getNotificationCount());
                entity.setReceptionNo(vo.getReceptionNo());
                entity.setAcceptStatus(CodeUtil.getValue(CD030, CD030_ACCEPTED));
                break;
            default: 
                entity.setReceptionNo(vo.getReceptionNo());
                entity.setAcceptStatus(CodeUtil.getValue(CD030, CD030_NOT_SPECIFIED));
                break;
        }

        String notificationNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL200), COL_NOTIFICATION_NO);
    	entity.setId(new TBL200EntityPK(notificationNo, vo.getApartmentId()));
        entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));

        TBL200Entity result = tbl200DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL200, entity.getId().toString() }));
        
        if (Objects.isNull(result)) {
            throw new BusinessException(String.format(isCreated ? MSG_ADD_FAILED : MSG_UPDATE_FAILED, TBL200));
        }
        vo.setNotificationNo(result.getId().getNotificationNo());
        CommonUtils.copyProperties(vo, result);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return result;
    }

    /**
     * Save data to table TBL300 in case registering notification
     *
     * @param vo
     * @return boolean
     */
    private TBL300Entity saveDataToTbl300(NotificationInfoVo vo, String idScreen, CurrentUserInformation user, String acceptNo, String rdoNotificationMethod) {
        TBL300Entity entity = new TBL300Entity();
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO);
        entity.setProgressRecordNo(keyNo);
        entity.setApartmentId(vo.getApartmentId());
        entity.setUpdateUserId(user.getUserId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
        entity.setCorrespondDate(DateTimeUtil.getLocalDateTimeAsString(LocalDateTime.now()).substring(NUM_0, NUM_12));
        entity.setCorrespondTypeCode(null);
        entity.setNoticeTypeCode(null);
        entity.setProgressRecordOverview(null);
        entity.setSupportCode(null);

        switch (idScreen) {
            case SCREEN_ID_MDA0110: {
                entity.setTypeCode(vo.getChangeCount() == 0 ? CodeUtil.getValue(CD033, CD033_NOTIFICATION) : CodeUtil.getValue(CD033, CD033_CHANGE_NOTIFICATION));
                entity.setRelatedNumber(vo.getNotificationNo());
                break;
            }
            case SCREEN_ID_GDA0110: {
                entity.setTypeCode(vo.getChangeCount() == 0 ? CodeUtil.getValue(CD033, CD033_NOTIFICATION_ACCEPTANCE) : CodeUtil.getValue(CD033, CD033_CHANGE_NOTIFICATION_ACCEPTANCE));
                entity.setRelatedNumber(acceptNo);
                entity.setNotificationMethodCode(rdoNotificationMethod);
                break;
            }
            default:
                break;
        }
        entity = tbl300DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { CommonConstants.TBL300, entity.getProgressRecordNo() }));
        return entity;
    }

    /**
     * Update data to table TBL100 in case registering notification
     *
     * @param vo
     * @param user
     * @param idScreen
     * @param nextNotificationDateMax
     * @param notificationRenewalPeriod
     * @return boolean
     */
    private void updateDataToTbl100(NotificationInfoVo vo, CurrentUserInformation user, String idScreen, String nextNotificationDateMax, String notificationRenewalPeriod) throws SystemException {
        TBL100Entity entity100 = tbl100DAO.getMansionInformationById(vo.getApartmentId());
        if (SCREEN_ID_GDA0110.equals(idScreen)) {
            entity100.setApartmentName(vo.getApartmentName());
            entity100.setApartmentNamePhonetic(vo.getApartmentNamePhonetic());
            entity100.setZipCode(vo.getZipCode());
            entity100.setAddress(vo.getTxtApartmentAddress2());
            Optional.ofNullable(vo.getContactMailAddress()).ifPresent(entity100::setRepasswordMailAddress);
            entity100.setMailAddress(vo.getContactMailAddress());

            //Send nextNotificationDate to vo
            vo.setNextNotificationDate(entity100.getNextNotificationDate());

            if (Objects.nonNull(vo.getBuiltDate())) {
                entity100.setBuildYear(String.valueOf(vo.getBuiltDate().getYear()));
                entity100.setBuildMonth(String.valueOf(vo.getBuiltDate().getMonthValue()));
                entity100.setBuildDay(String.valueOf(vo.getBuiltDate().getDayOfMonth()));
            }

            entity100.setFloorNumber(vo.getFloorNumber());
            entity100.setHouseNumber(vo.getHouseNumber());
            if (CodeUtil.getValue(CommonConstants.CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(vo.getChangeReasonCode())) {
                entity100.setApartmentLostFlag(CodeUtil.getValue(CommonConstants.CD049, CD049_REMOVAL));
            }
        }

        entity100.setNextNotificationDate(
                (SCREEN_ID_GDA0110.equals(idScreen) && CodeUtil.getValue(CommonConstants.CD004, CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS).equals(vo.getChangeReasonCode()))
                        ? DateTimeUtil.getLocalDateFromString(nextNotificationDateMax)
                        : vo.getNotificationDate().plus(Integer.parseInt(notificationRenewalPeriod), ChronoUnit.YEARS));

        entity100.setNewestNotificationNo(vo.getNotificationNo());
        entity100.setNewestNotificationName(vo.getContactName());
        entity100.setNewestNotificationAddress(vo.getContactAddress());
        entity100.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        entity100.setUpdateUserId(user.getUserId());
        //マンション情報履歴（TBL105）/ Save data to table TBL100
        if (Objects.nonNull(tbl100DAO.save(entity100))) {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL100, entity100.getApartmentId() }));
            saveDataToTbl105(entity100);
        }
    }

    /**
     * Save data to TBL105
     *
     * @param entity100
     */
    private void saveDataToTbl105(TBL100Entity entity100) throws SystemException {
        TBL105Entity tbl105Entity = new TBL105Entity();
        CommonUtils.copyProperties(tbl105Entity, entity100);
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL105), CommonConstants.COL_HISTORY_NUMBER);
        tbl105Entity.setHistoryNumber(keyNo);
        tbl105Entity.setApartmentId(entity100.getApartmentId());
        //Save data backup
        tbl105DAO.save(tbl105Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL105, tbl105Entity.getHistoryNumber() }));
    }

    /**
     * save Acceptance Notification
     *
     * @param apartmentId      String
     * @param registrationForm NotificationRegistrationForm
     * @param acceptanceForm   NotificationAcceptanceForm
     * @param userLoggedInInfo CurrentUserInformation
     * @return acceptNo
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String saveAcceptanceNotification(String apartmentId,
                                             NotificationRegistrationForm registrationForm,
                                             NotificationAcceptanceForm acceptanceForm,
                                             CurrentUserInformation userLoggedInInfo) throws BusinessException, SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        final String LOST_BUILDING = "2";
        final String SEND_EMAIL = "1";
        TBL100Entity tbl100Entity = tbl100DAO.getMansionInformationById(apartmentId);
        if (ObjectUtils.isEmpty(tbl100Entity)) {
            throw new BusinessException();
        }

        temporaryNotificationInfoLogic.deleteAcceptanceTemporaryData(tbl100Entity.getNewestNotificationNo());

        NotificationInfoVo notificationInfoVo = NotificationConverter.toNotificationInfoVo(registrationForm);
        notificationInfoVo.setNotificationNo(null);

        TBL200Entity tbl200Entity = saveDataToTbl200(notificationInfoVo, userLoggedInInfo, SCREEN_ID_GDA0110);

        TBL210Entity tbl210Entity = convertToEntity(
                tbl200Entity.getId().getNotificationNo(),
                acceptanceForm,
                userLoggedInInfo.getUserId(),
                userLoggedInInfo.getDisplayName());
        tbl210Entity = tbl210DAO.save(tbl210Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL210, tbl210Entity.getAcceptNo()));

        TBL300Entity tbl300Entity = saveDataToTbl300(
                notificationInfoVo,
                SCREEN_ID_GDA0110,
                userLoggedInInfo,
                tbl210Entity.getAcceptNo(),
                acceptanceForm.getRdoNotificationMethod());

        String nextNotificationDateMax = SessionUtil.getSystemSettingByKey(NEXT_NOTIFICATION_DATE_MAX_KEY);
        String notificationRenewalPeriod = SessionUtil.getSystemSettingByKey(NOTIFICATION_RENEWAL_PERIOD_KEY);

        updateProgressRecordRelatedNumber(
                apartmentId,
                tbl100Entity.getNewestNotificationNo(),
                tbl200Entity.getId().getNotificationNo(),
                userLoggedInInfo.getUserId(),
                tbl300Entity.getUpdateDatetime());
        updateDataToTbl100(notificationInfoVo, userLoggedInInfo, SCREEN_ID_GDA0110, nextNotificationDateMax, notificationRenewalPeriod);

        if (LOST_BUILDING.equals(registrationForm.getInfoAreaCommon().getRdoChangeReason())) {
            updateMansionUser(apartmentId, userLoggedInInfo.getUserId());
        }

        if (SEND_EMAIL.equals(acceptanceForm.getRdoNotificationMethod())) {
            sendReportAcceptanceNotificationEmail(tbl100Entity);
            registerProgressRecord(
                    apartmentId,
                    tbl200Entity.getChangeCount(),
                    tbl210Entity.getAcceptNo(),
                    userLoggedInInfo.getUserId(),
                    tbl300Entity.getUpdateDatetime());
        }

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl210Entity.getAcceptNo();
    }

    private void updateProgressRecordRelatedNumber(String apartmentId,
                                                   String oldNotificationNo,
                                                   String newNotificationNo,
                                                   String updateUserId,
                                                   Timestamp updateDatetime) {
        tbl300DAO.findByByApartmentIdAndRelatedNumber(apartmentId, oldNotificationNo).forEach(entity -> {
            entity.setUpdateUserId(updateUserId);
            entity.setUpdateDatetime(updateDatetime);
            entity.setRelatedNumber(newNotificationNo);
            tbl300DAO.save(entity);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL300, entity.getProgressRecordNo()));
        });
    }

    private void registerProgressRecord(String apartmentId, int changeCount, String acceptNo, String updateUserId, Timestamp updateDatetime) {
        final String NOTIFICATION_ACCEPTANCE_MAIL = "9";
        final String CHANGE_NOTIFICATION_MAIL = "10";
        final String key = sequenceUtil.generateKey(TBL300, COL_PROGRESS_RECORD_NO);

        TBL300Entity entity = new TBL300Entity();
        entity.setProgressRecordNo(key);
        entity.setApartmentId(apartmentId);
        entity.setCorrespondDate(DateTimeUtil.formatDateTime(LocalDateTime.now(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        entity.setTypeCode(changeCount > 0 ? CHANGE_NOTIFICATION_MAIL : NOTIFICATION_ACCEPTANCE_MAIL);
        entity.setRelatedNumber(acceptNo);
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId(updateUserId);
        entity.setUpdateDatetime(updateDatetime);
        tbl300DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL300, key));
    }

    private void sendReportAcceptanceNotificationEmail(TBL100Entity entity) throws BusinessException {
        ML040Vo mailVo = mailLogic.getCommonTemplateMail(ML040Vo.class);
        mailVo.setContactMailAddress(entity.getMailAddress());
        mailVo.setApartmentName(entity.getApartmentName());
        mailVo.setContactName(entity.getNewestNotificationName());
        mailVo.setLoginUrl(SessionUtil.getSystemSettingByKey(CommonConstants.APARTMENT_LOGIN_URL));
        mailLogic.sendMailWithContent(mailVo.getMailTemplate(), MailUtil.convertToEmailInfo(mailVo), MailUtil.getML040Parameters(mailVo), null);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, mailVo.getMailSubject(), mailVo.getContactMailAddress()));
    }

    private void updateMansionUser(String apartmentId, String updateUserId) {
        final String INVALID = "2";
        TBL110Entity entity = tbl110DAO.getUserByApartmentId(apartmentId);
        entity.setValidityFlag(INVALID);
        entity.setLoginValidityTime(LocalDateTime.now());
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId(updateUserId);
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        tbl110DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
    }

    private TBL210Entity convertToEntity(String notificationNo,
                                         NotificationAcceptanceForm form,
                                         String acceptedUserId,
                                         String acceptedUsername) {
        final String NOT_CORRECTION = "0";
        final String CORRECTION = "1";
        TBL210Entity entity = new TBL210Entity();
        String entityId = sequenceUtil.generateKey(TBL210, COL_ACCEPT_NO);
        entity.setAcceptNo(entityId);
        entity.setNotificationNo(notificationNo);
        entity.setAcceptTime(LocalDateTime.now());
        entity.setAcceptUserId(acceptedUserId);
        entity.setAcceptUserName(acceptedUsername);
        entity.setAppendixNo(form.getTxtAppendixNo());
        entity.setDocumentNo(form.getTxtDocumentNo());
        entity.setNoticeDate(LocalDate.parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setRecipientNameApartment(form.getLblRecipientNameApartment());
        entity.setRecipientNameUser(form.getLblRecipientNameUser());
        entity.setSender(form.getTxtSender());
        entity.setNotificationDate(LocalDate.parse(form.getLblNotificationDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        entity.setEvidenceBar("15".equals(form.getLblEvidenceBar()) ? "1" : "2");
        entity.setEvidenceNo(form.getLstEvidenceNo());
        entity.setRemarks(form.getTxaRemarks());
        entity.setAuthorityModifyFlag(StringUtils.isEmpty(form.getTxaOrrectionDetails()) ? NOT_CORRECTION : CORRECTION);
        entity.setModifyDetails(form.getTxaOrrectionDetails());
        entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        entity.setDeleteFlag(DeleteFlag.NOT_DELETE.getFlag());
        entity.setUpdateUserId(acceptedUserId);
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        return entity;
    }

}
