/*
 * @(#)SurveyNotificationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.APARTMENT_LOGIN_URL;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD001;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD001_UNDELETEFLAG;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD017;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD017_NOTIFI_BY_EMAIL;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_CITY;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_SYS_ADMIN;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD033;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD033_FIELDSURVEYMAILNOTIFICATION;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD033_FIELDSURVEYNOTIFICATION;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD046;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD046_FOR_FIELD_SURVEY_NOTICE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_INVESTIGATION_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_PROGRESS_RECORD_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_TEMP_NO;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.FORMAT_DATETIME_YYYYMMDDHHMM;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LF_LINE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1050_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1070_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1110_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MSG_ADD_FAILED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SLASH_YYYYMMDD;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.SPACE_FULL_SIZE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL230;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL235;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.YYYYMMDDHHMM;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MailUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL230DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL235DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM002DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL235Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SurveyForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISurveyNotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML060Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SurveyVo;

@Service
public class SurveyNotificationLogicImpl implements ISurveyNotificationLogic {


    private static final String TXTAPPENDIX = "??????????????????????????????????????????";
    private static final String FIRST = "???";
    private static final String ISSUE = "???";
    private static final String LONG = "???";
    private static final String SENDER = "???????????????";
    private static final String ADDRESS = "?????????";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String USERTYPE_INCORRECT_MESSAGE = "User Type Code must be TOKYO_STAFF or CITY or SYSTEM_ADMIN or MAINTENANCER";
    private static final String MESSAGE_GET_DATA_FAIL = "Fail when get data";

    @Autowired
    private TBL200DAO tbl200DAO;
    @Autowired
    private TBL230DAO tbl230DAO;
    @Autowired
    private TBL235DAO tbl235DAO;
    @Autowired
    private TBL300DAO tbl300DAO;
    @Autowired
    private TBM001DAO tbm001DAO;
    @Autowired
    private TBM002DAO tbm002DAO;
    @Autowired
    private IEMailLogic mailLogic;
    @Autowired
    private SequenceUtil sequenceUtil;
    private static final Logger logger = LogManager.getLogger(SurveyNotificationLogicImpl.class);

    /**
     * Get data show initialization.
     * @param vo SurveyVo
     * @param cityCode String
     * @author PTLuan
     * @throws BusinessException BusinessException
     */
    @Override
    public void getSurveyNotificationInfo(SurveyVo vo, String cityCode) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL200Entity tbl200 = null;
        String cityCodeTemp = null;
        if (vo.getNewestNotificationNo() != null) {
            tbl200 = tbl200DAO.getNotificationInfo(vo.getNewestNotificationNo());
        }
        if (tbl200 != null) {
            cityCodeTemp = tbl200.getCityCode();
            /* Field hidden */
            /* ?????????????????? */
            vo.setNotificationPersonName(tbl200.getNotificationPersonName());
        } else {
            cityCodeTemp = vo.getCityCode();
        }
        /* ???????????????????????????????????? */
        TBM001Entity tbm001 = null;
        tbm001 = tbm001DAO.getMunicipalMasterInfo(cityCodeTemp);
        if (tbm001 == null) {
            throw new BusinessException(MESSAGE_GET_DATA_FAIL);
        }
        /* ????????? */
        vo.setTxtAppendixNo(TXTAPPENDIX);
        /* ???????????? */
        vo.setTxtDocumentNo(DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting()
                + tbm001.getDocumentNo() + SPACE_FULL_SIZE + FIRST + SPACE_FULL_SIZE + ISSUE);
        /* ??????????????? */
        vo.setCalNoticeDate(new SimpleDateFormat(SLASH_YYYYMMDD).format(new Timestamp(System.currentTimeMillis())));
        /* ??????(??????????????????) */
        vo.setTxtRecipientNameApartment(vo.getApartmentName());
        vo.setTxtRecipientNameApartmentTemp(vo.getApartmentName());
        /* ???????????? */
        UserTypeCode userType = SecurityUtil.getUserLoggedInInfo().getUserTypeCode();
        if (UserTypeCode.CITY.getCode() == userType.getCode()) {
            vo.setTxtSender(tbm001.getCityName() + LONG + SPACE_FULL_SIZE + tbm001.getChiefName());
        } else {
            vo.setTxtSender(SENDER + SPACE_FULL_SIZE + tbm001.getChiefName());
        }
        /* ????????? */
        vo.setLblAddress(ADDRESS + tbm001.getCityName() + (tbl200 == null ? vo.getAddress() : tbl200.getAddress()));
        /* ??????????????????????????? */
        vo.setCalInvestImplPlanTime(new SimpleDateFormat(FORMAT_DATETIME_YYYYMMDDHHMM).format(new Timestamp(System.currentTimeMillis())));
        vo.setTxtInvestImplNumberPeople(BLANK);
        vo.setTxtNecessaryDocument(BLANK);
        /* ?????????????????????????????????????????? */
        String usedCode = CodeUtil.getValue(CD046, CD046_FOR_FIELD_SURVEY_NOTICE);
        TBM002Entity tbm002 = tbm002DAO.getWindowInformation(cityCode, usedCode);
        if (tbm002 != null) {
            vo.setTxaContact(tbm002.getWindowBelong() + LF_LINE
                    + tbm002.getWindowMailAddress() + LF_LINE
                    + tbm002.getWindowTelNo() + LF_LINE
                    + tbm002.getWindowFaxNo());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * check data temporary
     * @param form SurveyVo
     * @author PTLuan
     * @throws BusinessException  When get TempKPN false
     */
    public void checkTemporaryData(SurveyVo form) throws BusinessException {
        try {
            String code = getTempKPN();
            TBL235Entity entity = tbl235DAO.getSurveyByApartmentIdAndTempKbn(form.getApartmentId(), code);
            if (null == entity) {
                form.setHasDataStore(FALSE);
            } else {
                form.setHasDataStore(TRUE);
            }
        } catch (Exception e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * Register survey notification info
     * @param form SurveyForm
     * @author PTLuan
     */
    private void surveyNotificationRegister(SurveyForm form) throws BusinessException {
        /* Save TBL230 */
        saveDataToTbl230(form, new TBL230Entity());
        /* Save TBL300_1 */
        saveDataToTbl300(form, new TBL300Entity(), true);
    }

    /**
     * Sent mail and save mail infor
     * @param form SurveyForm
     * @author PTLuan
     */
    private void sentMailAndSaveMail(SurveyForm form) throws BusinessException {
        /* ????????????????????????????????????????????? */
        saveDataToTbl300(form, new TBL300Entity(), false);
        /* ??????????????? */
        ML060Vo ml060Vo = getML060Template(form);
        sendML060(ml060Vo);
        logger.info(MessageUtil.getMessage(LOG_LG1110_I, ml060Vo.getMailSubject(), ml060Vo.getContactMailAddress()));
    }

    /**
     * save history
     * @param form SurveyForm
     * @param entity TBL300Entity
     * @param inveseNo boolean
     * @throws BusinessException When set data error
     */
    private void saveDataToTbl300(SurveyForm form, TBL300Entity entity, boolean isHisRegisterSurvey) throws BusinessException {
        try {
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), COL_PROGRESS_RECORD_NO);
            /* ?????????????????? */
            form.setProgressRecordNo(keyNo);
            String currentDateFormatString = new SimpleDateFormat(YYYYMMDDHHMM).format(new Timestamp(System.currentTimeMillis()));
            entity.setProgressRecordNo(keyNo);
            /* ???????????????ID */
            entity.setApartmentId(form.getApartmentId());
            /* ???????????? */
            entity.setCorrespondDate(currentDateFormatString);
            if (isHisRegisterSurvey) {
                /* ??????????????? */
                entity.setTypeCode(CodeUtil.getValue(CD033, CD033_FIELDSURVEYNOTIFICATION));
                /* ???????????? */
                entity.setNotificationMethodCode(form.getRdoNotificationMethod());
            } else {
                /* ??????????????? */
                entity.setTypeCode(CodeUtil.getValue(CD033, CD033_FIELDSURVEYMAILNOTIFICATION));
            }
            entity.setRelatedNumber(form.getInveseNo());
            /* ??????????????? */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* ????????????????????? */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* ???????????? */
            entity.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
            logger.info(MessageUtil.getMessage(LOG_LG1050_I, new String[] { TBL300, keyNo }));
            tbl300DAO.save(entity);
        } catch (Exception ex) {
            throw new BusinessException(String.format(MSG_ADD_FAILED, TBL300));
        }
    }

    /**
     * @param form SurveyForm
     * @param entity TBL230Entity
     * @return boolean
     * @throws BusinessException When set data error
     */
    private void saveDataToTbl230(SurveyForm form, TBL230Entity entity) throws BusinessException {
        try {
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL230), COL_INVESTIGATION_NO);
            /* ???????????????????????? */
            form.setInveseNo(keyNo);
            entity.setInvestigationNo(keyNo);
            /* ???????????????ID */
            entity.setApartmentId(form.getApartmentId());
            /* ????????? */
            entity.setAppendixNo(form.getTxtAppendixNo());
            /* ???????????? */
            entity.setDocumentNo(form.getTxtDocumentNo());
            /* ??????????????? */
            entity.setNoticeDate(DateTimeUtil.convertFormatDateOnly(form.getCalNoticeDate()));
            /* ???????????????????????? */
            entity.setNoticeDestinationCode(form.getRdoNoticeDestination());
            /* ??????(??????????????????) */
            entity.setRecipientNameApartment(form.getTxtRecipientNameApartment());
            /* ??????(?????????) */
            entity.setRecipientNameUser(form.getTxtRecipientNameUser());
            /* ??????????????? */
            entity.setTextaddress(form.getTxtTextAdress());
            /* ????????? */
            entity.setSender(form.getTxtSender());
            /* ????????? */
            entity.setAddress(form.getAddress());
            /* ?????????????????? */
            entity.setApartmentName(form.getApartmentName());
            /* ???????????????????????? */
            entity.setInvestImplPlanTime(DateTimeUtil.convertFormatDate(form.getCalInvestImplPlanTime()));
            /* ??????????????? */
            entity.setInvestImplNumberPeople(Integer.parseInt(form.getTxtInvestImplNumberPeople()));
            /* ???????????? */
            entity.setNecessaryDocument(form.getTxtNecessaryDocument());
            /* ??????????????? */
            entity.setContact(form.getTxaContact());
            /* ???????????? */
            entity.setNotificationMethodCode(form.getRdoNotificationMethod());
            /* ??????????????? */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* ???????????????ID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* ???????????? */
            entity.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
            logger.info(MessageUtil.getMessage(LOG_LG1050_I, new String[] { TBL230, keyNo }));
            tbl230DAO.save(entity);
        } catch (Exception ex) {
            throw new BusinessException(String.format(MSG_ADD_FAILED, TBL230));
        }
    }

    /**
     * GetML060 template
     * @param vo SurveyForm
     * @return Vo SurveyForm
     * @author PTLuan
     */
    private ML060Vo getML060Template(SurveyForm vo) throws BusinessException {
        ML060Vo ml060Vo = mailLogic.getCommonTemplateMail(ML060Vo.class);
        String url = SessionUtil.getSystemSettingByKey(APARTMENT_LOGIN_URL);
        ml060Vo.setContactMailAddress(vo.getMailAddress());
        ml060Vo.setApartmentName(vo.getApartmentName());
        ml060Vo.setContactName(vo.getNewestNotificationNo());
        ml060Vo.set???oginUrl(url);
        return ml060Vo;
    }

    /**
     * send mail
     * @param ml060Vo ML060Vo
     * @author PTLuan
     */
    private void sendML060(ML060Vo ml060Vo) throws BusinessException {
        mailLogic.sendMailWithContent(ml060Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml060Vo),
                settingPropertyForML060(ml060Vo), null);
    }

    /**
     * Setting property for mail
     * @param mL060Vo ML060Vo
     * @return A map
     * @author PTLuan
     */
    private Map<String, Object> settingPropertyForML060(ML060Vo ml060Vo) {
        Map<String, Object> model = new HashMap<>();
        model.put("apartmentName", ml060Vo.getApartmentName());
        model.put("contactName", ml060Vo.getContactName());
        model.put("loginUrl", ml060Vo.get???oginUrl());
        model.put("cityName", ml060Vo.getCityName());
        model.put("windowBelong", ml060Vo.getWindowBelong());
        model.put("windowTelNo", ml060Vo.getWindowTelNo());
        model.put("windowFaxNo", ml060Vo.getWindowFaxNo());
        model.put("windowMailAddress", ml060Vo.getWindowMailAddress());
        return model;
    }

    /**
     * Delete temporarily data
     * @param form SurveyForm
     * @param isTemporary boolean
     * @author PTLuan
     */
    private void deleteTemporarilyData(@Valid SurveyForm form, boolean isTemporary) throws BusinessException {
        String tempKPN;
        List<String> list = null;
        if (isTemporary) {
            tempKPN = getTempKPN();
            list = tbl235DAO.getTempNoByApartmentIdAndTempKBN(form.getApartmentId(), tempKPN);
        } else {
            list = tbl235DAO.getTempNoByApartmentId(form.getApartmentId());
        }
        for (String one : list) {
            logger.info(MessageUtil.getMessage(LOG_LG1070_I, new String[] { TBL235, one }));
            tbl235DAO.deleteById(one);
        }
    }

    /**
     * Save temporarily data
     * @param form SurveyForm
     * @author PTLuan
     */
    private void saveTemporarilyData(@Valid SurveyForm form) throws BusinessException {
        try {
            TBL235Entity entity = new TBL235Entity();
            /* ?????????????????? */
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL235), COL_TEMP_NO);
            form.setTempNo(form.getTempNo());
            entity.setTempNo(keyNo);
            /* ???????????????ID */
            entity.setApartmentId(form.getApartmentId());
            /* ?????????????????? */
            String tempKPN;
            tempKPN = getTempKPN();
            entity.setTempKbn(tempKPN);
            /* ????????? */
            entity.setAppendixNo(form.getTxtAppendixNo());
            /* ???????????? */
            entity.setDocumentNo(form.getTxtDocumentNo());
            /* ??????????????? */
            entity.setNoticeDate(DateTimeUtil.getLocalDateFromString(form.getCalNoticeDate(), DateTimeFormatter.ofPattern(SLASH_YYYYMMDD)));
            /* ???????????????????????? */
            entity.setNoticeDestinationCode(form.getRdoNoticeDestination());
            /* ??????(??????????????????) */
            entity.setRecipientNameApartment(form.getTxtRecipientNameApartment());
            /* ??????(?????????) */
            entity.setRecipientNameUser(form.getTxtRecipientNameUser());
            /* ????????? */
            entity.setSender(form.getTxtSender());
            /* ??????????????? */
            entity.setTextaddress(form.getTxtTextAdress());
            /* ????????? */
            entity.setAddress(form.getLblAddress());
            /* ?????????????????? */
            entity.setApartmentName(form.getApartmentName());
            /* ???????????????????????? */
            entity.setInvestImplPlanTime(DateTimeUtil.convertFormatDate(form.getCalInvestImplPlanTime()));
            /* ??????????????? */
            entity.setInvestImplNumberPeople(form.getTxtInvestImplNumberPeople());
            /* ???????????? */
            entity.setNecessaryDocument(form.getTxtNecessaryDocument());
            /* ??????????????? */
            entity.setContact(form.getTxaContact());
            /* ???????????? */
            entity.setNotificationMethodCode(form.getRdoNotificationMethod());
            /* ??????????????? */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* ???????????????ID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* ???????????? */
            entity.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
            logger.info(MessageUtil.getMessage(LOG_LG1050_I, new String[] { TBL235, entity.getTempNo() }));
            tbl235DAO.save(entity);
        } catch (Exception e) {
            throw new BusinessException(String.format(MSG_ADD_FAILED, TBL235));
        }
    }

    /**
     * Restore survey notification info
     * @param form SurveyForm
     * @return boolean
     * @author PTLuan
     */
    @Override
    public boolean restoreSurveyNotificationInfo(SurveyForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        /* ??????????????????????????? */
        String code;
        code = getTempKPN();
        TBL235Entity entity = tbl235DAO.getSurveyByApartmentIdAndTempKbn(form.getApartmentId(), code);
        if (null == entity) {
            form.setHasDataStore(FALSE);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return false;
        } else {
            /* ????????? */
            form.setTxtAppendixNo(entity.getAppendixNo());
            /* ???????????? */
            form.setTxtDocumentNo(entity.getDocumentNo());
            /* ??????????????? */
            form.setCalNoticeDate(DateTimeUtil.formatDate(entity.getNoticeDate(), DateTimeFormatter.ofPattern(SLASH_YYYYMMDD)));
            /* ??????????????? */
            form.setRdoNoticeDestination(entity.getNoticeDestinationCode());
            /* ??????(??????????????????) */
            form.setTxtRecipientNameApartment(entity.getRecipientNameApartment());
            /* ??????(?????????) */
            form.setTxtRecipientNameUser(entity.getRecipientNameUser());
            /* ???????????? */
            form.setTxtSender(entity.getSender());
            /* ??????????????? */
            form.setTxtTextAdress(entity.getTextaddress());
            /* ????????? */
            form.setLblAddress(entity.getAddress());
            /* ??????????????????????????? */
            form.setCalInvestImplPlanTime(DateTimeUtil.convertReFormatDate(entity.getInvestImplPlanTime()));
            /* ?????????????????? */
            form.setTxtInvestImplNumberPeople(entity.getInvestImplNumberPeople());
            /* ????????????????????? */
            form.setTxtNecessaryDocument(entity.getNecessaryDocument());
            /* ?????????????????? */
            form.setTxaContact(entity.getContact());
            /* ???????????? */
            form.setRdoNotificationMethod(entity.getNotificationMethodCode());
            form.setHasDataStore(TRUE);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return true;
    }

    /**
     * Save survey
     * @param form SurveyForm
     * @author PTLuan
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveSurvey(SurveyForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        /* NO4 ??????????????????????????? */
        deleteTemporarilyData(form, false);
        /* NO5 ??????????????????/???????????????????????? */
        surveyNotificationRegister(form);
        /* NO7 ??????????????? */
        /* NO8 ????????????????????????????????????????????? */
        if (form.getRdoNotificationMethod().equals(CodeUtil.getValue(CD017, CD017_NOTIFI_BY_EMAIL))) {
            sentMailAndSaveMail(form);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * Save temporary
     * @param form SurveyForm
     * @author PTLuan
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveTemporary(SurveyForm form) throws BusinessException {
        /* NO4 ??????????????????????????? */
        deleteTemporarilyData(form, true);
        /* NO5 ??????????????????????????? */
        saveTemporarilyData(form);
        form.setHasDataStore(TRUE);
    }

    /**
     * Get temKPN
     * @return String
     * @author PTLuan
     */
    String getTempKPN() throws BusinessException {
        String tempKPN;
        UserTypeCode userType = SecurityUtil.getUserLoggedInInfo().getUserTypeCode();
        switch (userType) {
        case CITY:
            tempKPN = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_CITY);
            break;
        case TOKYO_STAFF:
            tempKPN = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_TOKYO_STAFF);
            break;
        case SYSTEM_ADMIN:
            tempKPN = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_SYS_ADMIN);
            break;
        case MAINTENANCER:
            tempKPN = CodeUtil.getValue(CD031, CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR);
            break;
        default:
            throw new BusinessException(USERTYPE_INCORRECT_MESSAGE);
        }
        return tempKPN;
    }
}
