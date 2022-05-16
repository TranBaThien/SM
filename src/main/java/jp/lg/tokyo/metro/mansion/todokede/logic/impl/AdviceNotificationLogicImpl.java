/*
 * @(#) AdviceNotificationLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 27, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1050_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1070_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL200;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL220;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL225;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL300;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MailUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL200DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL220DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL225DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL300DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM003DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM003Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.AdviceNotificationForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IAdviceNotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceTemplateVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML050Vo;

/**
 * @author nvlong1
 *
 */
@Service
public class AdviceNotificationLogicImpl implements IAdviceNotificationLogic {

    private static final String CATEGORY_OWNER = "区分所有者";
    private static final String PRESIDENT_OF_THE_MANAGEMENT_UNION = "管理組合理事長";
    private static final String LONG = "長";
    private static final String GOVERNOR_OF_TOKYO = "東京都知事";
    
    @Autowired
    private SequenceUtil sequenceUtil;
    @Autowired
    private TBL100DAO tbl100DAO;
    @Autowired
    private TBL200DAO tbl200DAO;
    @Autowired
    private TBL220DAO tbl220DAO;
    @Autowired
    private TBL225DAO tbl225DAO;
    @Autowired
    private TBL300DAO tbl300DAO;
    @Autowired
    private TBM001DAO tbm001DAO;
    @Autowired 
    private TBM003DAO tbm003DAO;
    @Autowired
    private IEMailLogic mailLogic;
    
    private static final Logger logger = LogManager.getLogger(AdviceNotificationLogicImpl.class);

    private void save(AdviceNotificationForm form) {
        
        saveDataToTbl220(form, new TBL220Entity());
        
        saveDataToTbl300No1(form, new TBL300Entity());
        
        saveDataToTbl200(form);
    }

    /**
     * save data to table tbl220
     * @param form AdviceNotificationForm
     * @param tbl220Entity TBL220Entity
     * @author nvlong1
     */
    private void saveDataToTbl220(AdviceNotificationForm form, TBL220Entity tbl220Entity) {
        
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL220), CommonConstants.COL_ADVICE_NO);
        tbl220Entity.setAdviceNo(keyNo);
        tbl220Entity.setNotificationNo(form.getNewestNotificationNo());
        tbl220Entity.setAdviceUserId(SecurityUtil.getUserLoggedInInfo().getLoginId());
        tbl220Entity.setAppendixNo(form.getTxtAppendixNo());
        tbl220Entity.setDocumentNo(form.getTxtDocumentNo());
        tbl220Entity.setNoticeDate(
                LocalDate
                .parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD))
                .format(DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD)));
        tbl220Entity.setRecipientNameApartment(form.getLblRecipientNameApartment());
        tbl220Entity.setRecipientNameUser(form.getLblRecipientNameUser());
        tbl220Entity.setSender(form.getTxtSender());
        tbl220Entity.setNotificationDate(
                LocalDate
                .parse(form.getLblNotificationDate(), DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD))
                .format(DateTimeFormatter.ofPattern(CommonConstants.YYYYMMDD)));
        tbl220Entity.setEvidenceBar(CodeUtil.getValue(CommonConstants.CD041, form.getLblEvidenceBar()));
        tbl220Entity.setEvidenceNo(form.getEvidenceNo());
        tbl220Entity.setAddress(form.getLblAddress());
        tbl220Entity.setApartmentName(form.getLblApartmentName());
        tbl220Entity.setAdviceDetails(form.getTxtAdviceDetails());
        tbl220Entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        tbl220Entity.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
        tbl220Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl220Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        
        //setting keyAdviceNo
        form.setKeyAdviceNo(keyNo);
        
        tbl220DAO.save(tbl220Entity);
        logger.info(MessageUtil.getMessage(LOG_LG1050_I, TBL220, tbl220Entity.getAdviceNo()));
    }

    /**
     * save data to table tbl300
     * @param form AdviceNotificationForm
     * @param tbl300Entity TBL300Entity
     * @author nvlong1
     */
    private void saveDataToTbl300No1(AdviceNotificationForm form, TBL300Entity tbl300Entity) {
        
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO);
        tbl300Entity.setProgressRecordNo(keyNo);
        tbl300Entity.setApartmentId(form.getApartmentId());
        tbl300Entity.setCorrespondDate(DateTimeUtil.convertFormatDate(
                DateTimeUtil.formatDateTime(LocalDateTime.now(), CommonConstants.DATE_TIME_FORMATTER)));
        tbl300Entity.setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_ADVICE_NOTICE));
        tbl300Entity.setCorrespondTypeCode(null);
        tbl300Entity.setNoticeTypeCode(null);
        tbl300Entity.setRelatedNumber(form.getKeyAdviceNo());
        tbl300Entity.setProgressRecordOverview(null);
        tbl300Entity.setProgressRecordDetail(null);
        tbl300Entity.setSupportCode(null);
        tbl300Entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        tbl300Entity.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
        tbl300Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl300Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        
        tbl300DAO.save(tbl300Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL300, tbl300Entity.getProgressRecordNo() }));
    }

    /**
     * save data to table tbl200
     * @param form AdviceNotificationForm
     * @author nvlong1
     */
    private void saveDataToTbl200(AdviceNotificationForm form) {
        TBL200Entity tbl200Entity = tbl200DAO.getNotificationInfo(form.getNewestNotificationNo());
        tbl200Entity.setAdviceDoneFlag(CodeUtil.getValue(CommonConstants.CD011, CommonConstants.CD011_IMPLEMENTED));
        tbl200Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl200Entity.setUpdateDatetime(LocalDateTime.now());
        
        tbl200DAO.save(tbl200Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL200, tbl200Entity.getId().toString() }));
    }

    @Override
    public AdviceNotificationVo getAdviceNotification(String newestNotificationNo) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        AdviceNotificationVo adviceNotificationVo = new AdviceNotificationVo();
        AdviceTemplateVo adviceTemplateVo = null;
        List<AdviceTemplateVo> adviceTemplateVoes = new ArrayList<AdviceTemplateVo>();

        //マンション情報取得
        TBL200Entity entityTbl200 = tbl200DAO.getNotificationInfo(newestNotificationNo);
        
        //区市町村マスター情報取得
        TBM001Entity entityTbm001 = tbm001DAO.getMunicipalMasterInfo(entityTbl200.getCityCode());
        
        //助言テンプレートの内容取得
        List<TBM003Entity> entitiesTbm003 = tbm003DAO.getAdviceTemplateContent();
        
        //一時保存データ取得

        if (entityTbl200 != null && entityTbm001 != null && entitiesTbm003 != null) {
            
            adviceNotificationVo.setNotificationNo(entityTbl200.getId().getNotificationNo());

            // 様式名
            String txtAppendixNo = String.format("第%s%s号様式（第%s%s関係）", 
                    CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE, 
                    CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE);
            adviceNotificationVo.setTxtAppendixNo(txtAppendixNo);
            
            // 文書番号
            String txtDocumentNo = String.format("%s%s%s第%s号", 
                    DateUtil.getOnlyFiscalJapaneseYearAsStringForSetting(), 
                    entityTbm001.getDocumentNo(), CommonConstants.SPACE_FULL_SIZE, CommonConstants.SPACE_FULL_SIZE);
            adviceNotificationVo.setTxtDocumentNo(txtDocumentNo);

            //通知年月日
            adviceNotificationVo.setCalNoticeDate(DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD).format(LocalDate.now()));
            
            // 宛先 (マンション名)
            adviceNotificationVo.setLblRecipientNameApartment(entityTbl200.getApartmentName());
            
            // 宛先 (氏名等)
            if (CodeUtil.getValue(CommonConstants.CD013, CommonConstants.CD013_CATEGORY_OWNER_ETC).equals(entityTbl200.getContactPropertyCode())) {
                adviceNotificationVo.setLblRecipientNameUser(
                        CATEGORY_OWNER + CommonConstants.SPACE_FULL_SIZE + entityTbl200.getNotificationPersonName());
            } else {
                adviceNotificationVo.setLblRecipientNameUser(PRESIDENT_OF_THE_MANAGEMENT_UNION);
            }

            // 発信者名
            if (CodeUtil.getValue(CommonConstants.CD027, CommonConstants.CD027_CITY)
                    .equals(String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode()))) {
                adviceNotificationVo.setTxtSender(entityTbm001.getCityName() + LONG + CommonConstants.SPACE_FULL_SIZE +  entityTbm001.getChiefName());
            } else {
                adviceNotificationVo.setTxtSender(GOVERNOR_OF_TOKYO + CommonConstants.SPACE_FULL_SIZE + entityTbm001.getChiefName());
            }
            
            // 届出年月日    
            adviceNotificationVo.setLblNotificationDate(DateTimeUtil.formatDate(entityTbl200.getNotificationDate(), CommonConstants.DATE_FORMATTER));
            
            // 根拠条文1,2
            if (CodeUtil.getValue(CommonConstants.CD003, CommonConstants.CD003_NEW).equals(entityTbl200.getNotificationType())) {
                
                adviceNotificationVo.setLblEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, entityTbl200.getNotificationType()));
                List<Code> listCD042 = CodeUtil.getList(CommonConstants.CD042);
                adviceNotificationVo.setLstEvidenceNo(getEvidenceNoesByListCD(listCD042));
                
            } else if (CodeUtil.getValue(CommonConstants.CD003, CommonConstants.CD003_UPDATE).equals(entityTbl200.getNotificationType())) {
                
                adviceNotificationVo.setLblEvidenceBar(CodeUtil.getLabel(CommonConstants.CD041, entityTbl200.getNotificationType()));
                List<Code> listCD043 = CodeUtil.getList(CommonConstants.CD043);
                adviceNotificationVo.setLstEvidenceNo(getEvidenceNoesByListCD(listCD043));
                
            }
            // 所在地
            adviceNotificationVo.setLblAddress(CommonConstants.CITY_NAME_TOKYO + entityTbm001.getCityName() + entityTbl200.getAddress());
            
            // マンション名
            adviceNotificationVo.setLblApartmentName(entityTbl200.getApartmentName());

            // 助言テンプレート
            adviceTemplateVoes.add(new AdviceTemplateVo(CommonConstants.BLANK,CommonConstants.BLANK));
            for (int i = 0; i < entitiesTbm003.size(); i++) {
                adviceTemplateVo = new AdviceTemplateVo();
                adviceTemplateVo.setAdviceTemplateDetail(entitiesTbm003.get(i).getAdviceTemplateDetail());
                adviceTemplateVo.setAdviceTemplateOverview(entitiesTbm003.get(i).getAdviceTemplateOverview());
                adviceTemplateVoes.add(adviceTemplateVo);
            }
            adviceNotificationVo.setLstAdvice(adviceTemplateVoes);
            adviceNotificationVo.setTxtAdviceDetails(CommonConstants.BLANK);
            
            //set contact mail address for check even inactive radio メールで通知する
            adviceNotificationVo.setContactMailAddress(entityTbl200.getContactMailAddress());
        } else {
            throw new BusinessException("Fail when get data");
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return adviceNotificationVo;
    }

    /**
     * Check what type code user is
     * @return String
     */
    private String getTempStorageByCodeUser() {
        
        String typeCodeUser = String.valueOf(SecurityUtil.getUserLoggedInInfo().getUserTypeCode().getCode());
        
        String temporaryStorage = null;
        
        if (CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.TOKYO_STAFF.getUserTypeName()).equals(typeCodeUser)) {
            temporaryStorage = CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_TOKYO_STAFF);
        } else if (CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.CITY.getUserTypeName()).equals(typeCodeUser)) {
            temporaryStorage = CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_CITY);
        } else if (CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.SYSTEM_ADMIN.getUserTypeName()).equals(typeCodeUser)) {
            temporaryStorage = CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_SYS_ADMIN);
        } else if (CodeUtil.getValue(CommonConstants.CD027, UserTypeCode.MAINTENANCER.getUserTypeName()).equals(typeCodeUser)) {
            temporaryStorage = CodeUtil.getValue(CommonConstants.CD031, CommonConstants.CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR);
        }
        
        return temporaryStorage;
    }

    /**
     * @param listCD List<>
     * @return String[]
     */
    private String[] getEvidenceNoesByListCD(List<Code> listCD) {
        
        String[] evidenceNo = new String[listCD.size() + 1];
        evidenceNo[0] = CommonConstants.BLANK;
        int index = 1;
        for (Code code : listCD) {
            evidenceNo[index++] = code.getLabel();
        }
        return evidenceNo;
    }

    @Override
    public String exclusiveCheck(String apartmentIdPreviousScreen, String newestNotificationNoPreviousScreen) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL100Entity entity = tbl100DAO.getMansionInformationById(apartmentIdPreviousScreen);
        if (entity.getNewestNotificationNo().equals(newestNotificationNoPreviousScreen)) {
            if (CodeUtil.getValue(CommonConstants.CD011, CommonConstants.CD011_IMPLEMENTED)
                    .equals(getAdviceDoneFlag(newestNotificationNoPreviousScreen))) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return CommonConstants.CD011_IMPLEMENTED;
            } else {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return CommonConstants.CD011_NOT_IMPLEMENTED;
            }
        } 
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.ZERO;
    }
    
    @Override
    public TBL220Entity getAdvisoryNoticeById(String adviceNumber) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL220Entity tbl220Entity = tbl220DAO.getAdvisoryNoticeById(adviceNumber);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl220Entity;
    }
    
    /**
     * Prepare data for sent mail
     * @param apartmentId String
     * @return ML050Vo
     * @throws BusinessException when get getCommonTemplateMail fail
     */
    private ML050Vo getML050Template(String apartmentId) throws BusinessException {
        ML050Vo ml050Vo = mailLogic.getCommonTemplateMail(ML050Vo.class);
        TBL100Entity entity = tbl100DAO.getMansionInformationById(apartmentId);
        
        ml050Vo.setContactMailAddress(entity.getMailAddress());
        ml050Vo.setApartmentName(entity.getApartmentName());
        ml050Vo.setContactName(entity.getNewestNotificationName());
        ml050Vo.setLoginUrl(SessionUtil.getSystemSettingByKey(CommonConstants.CITY_LOGIN_URL));
        
        return ml050Vo;
    }

    /**
     * Sent and save mail
     * @param form AdviceNotificationForm
     * @throws BusinessException when getML050Template fail or sendML050 fail
     * @author nvlong1
     */
    private void sentMailAndSaveMail(AdviceNotificationForm form) throws BusinessException {
        ML050Vo ml050Vo = getML050Template(form.getApartmentId());
        sendML050(ml050Vo);
        saveDataToTbl300No2(form, new TBL300Entity());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1110_I, ml050Vo.getMailSubject(), ml050Vo.getContactMailAddress()));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveAdvice(@Valid AdviceNotificationForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        //delete table temporarily don't check code user
        deleteTemporarilySavedData(form.getNewestNotificationNo(), CommonConstants.ONE);

        //save data register
        save(form);

        if (CodeUtil.getValue(CommonConstants.CD017, CommonConstants.CD017_NOTIFI_BY_EMAIL).equals(form.getRdoNotificationMethod())) {
            sentMailAndSaveMail(form);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * Sent mail
     * @param ml050Vo ML050Vo
     * @author nvlong1
     */
    private void sendML050(ML050Vo ml050Vo) {
        mailLogic.sendMailWithContent(ml050Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml050Vo), settingPropertyForML050(ml050Vo), null);
    }

    /**
     * Setting data property for sent mail
     * @param ml050Vo ML050Vo
     * @return Map<>
     * @author nvlong1
     */
    private Map<String, Object> settingPropertyForML050(ML050Vo ml050Vo) {
        Map<String, Object> model = new HashMap<>();
        model.put("apartmentName", ml050Vo.getApartmentName());
        model.put("contactName", ml050Vo.getContactName());
        model.put("loginUrl", ml050Vo.getLoginUrl());
        model.put("cityName", ml050Vo.getCityName());
        model.put("windowBelong", ml050Vo.getWindowBelong());
        model.put("windowTelNo", ml050Vo.getWindowTelNo());
        model.put("windowFaxNo", ml050Vo.getWindowFaxNo());
        model.put("windowMailAddress", ml050Vo.getWindowMailAddress());
        return model;
    }

    /**
     * Save data to table Tbl300
     * @param form AdviceNotificationForm
     * @param tbl300Entity TBL300Entity
     */
    private void saveDataToTbl300No2(AdviceNotificationForm form, TBL300Entity tbl300Entity) {
        
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL300), CommonConstants.COL_PROGRESS_RECORD_NO);
        tbl300Entity.setProgressRecordNo(keyNo);
        tbl300Entity.setApartmentId(form.getApartmentId());
        tbl300Entity.setCorrespondDate(DateTimeUtil.convertFormatDate(
                DateTimeUtil.formatDateTime(LocalDateTime.now(), CommonConstants.DATE_TIME_FORMATTER)));
        tbl300Entity.setTypeCode(CodeUtil.getValue(CommonConstants.CD033, CommonConstants.CD033_ADVICE_EMAIL_NOTIFICATION));
        tbl300Entity.setCorrespondTypeCode(null);
        tbl300Entity.setNoticeTypeCode(null);
        tbl300Entity.setRelatedNumber(form.getKeyAdviceNo());
        tbl300Entity.setProgressRecordOverview(null);
        tbl300Entity.setProgressRecordDetail(null);
        tbl300Entity.setSupportCode(null);
        tbl300Entity.setNotificationMethodCode(null);
        tbl300Entity.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
        tbl300Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl300Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        
        tbl300DAO.save(tbl300Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL300, tbl300Entity.getProgressRecordNo() }));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void tmpSave(@Valid AdviceNotificationForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        //delete table temporarily check code user
        deleteTemporarilySavedData(form.getNewestNotificationNo(), CommonConstants.ZERO);
        
        //Save data to table Tbl225 successfully
        saveDataToTbl225(form, new TBL225Entity());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * save data to table 225
     * @param form AdviceNotificationForm
     * @param tbl225Entity TBL225Entity
     */
    private void saveDataToTbl225(AdviceNotificationForm form, TBL225Entity tbl225Entity) {
        String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL225), CommonConstants.COL_TEMP_NO);
        tbl225Entity.setTempNo(keyNo);
        tbl225Entity.setNotificationNo(form.getNewestNotificationNo());
        tbl225Entity.setTempKbn(getTempStorageByCodeUser());
        tbl225Entity.setAdviceUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl225Entity.setAppendixNo(form.getTxtAppendixNo());
        tbl225Entity.setDocumentNo(form.getTxtDocumentNo());
        tbl225Entity.setNoticeDate(LocalDate.parse(form.getCalNoticeDate(), DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD)));
        tbl225Entity.setRecipientNameApartment(form.getLblRecipientNameApartment());
        tbl225Entity.setRecipientNameUser(form.getLblRecipientNameUser());
        tbl225Entity.setSender(form.getTxtSender());
        tbl225Entity.setNotificationDate(LocalDate.parse(form.getLblNotificationDate(), 
                DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD)));
        tbl225Entity.setEvidenceBar(CodeUtil.getValue(CommonConstants.CD041, form.getLblEvidenceBar()));
        tbl225Entity.setEvidenceNo(form.getEvidenceNo());
        tbl225Entity.setAddress(form.getLblAddress());
        tbl225Entity.setApartmentName(form.getLblApartmentName());
        tbl225Entity.setAdviceDetails(form.getTxtAdviceDetails());
        tbl225Entity.setNotificationMethodCode(form.getRdoNotificationMethod());
        tbl225Entity.setDeleteFlag(CodeUtil.getValue(CommonConstants.CD001, CommonConstants.CD001_UNDELETEFLAG));
        tbl225Entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
        tbl225Entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        
        tbl225DAO.save(tbl225Entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL225, keyNo }));
    }

    /**
     * Delete temporarily saved data
     * @param newestNotificationNo String
     * @param checkLoggedInUser String
     */
    private void deleteTemporarilySavedData(String notificationNo, String checkLoggedInUser) {
        
        if (CommonConstants.ONE.equals(checkLoggedInUser)) {
            List<String> listTempNo = tbl225DAO.getTempNoByNotificationNo(notificationNo);
            
            for (String tempNo : listTempNo) {
                tbl225DAO.deleteById(tempNo);
                logger.info(MessageUtil.getMessage(LOG_LG1070_I, new String[] { TBL225, tempNo }));
            }
            
        } else {
            tbl225DAO.deleteTemporarilySavedData(notificationNo, getTempStorageByCodeUser());
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1070_I, new String[] { TBL225, getTempStorageByCodeUser() }));
        }
    }

    @Override
    public TBL225Entity getAdviceNotificationTemporaryStorage(String newestNotificationNo) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String temporaryStorage = getTempStorageByCodeUser();
        TBL225Entity tbl225Entity = tbl225DAO.getAdviceNotificationTemporaryStorage(newestNotificationNo, temporaryStorage);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl225Entity;
    }

    /**
     * Get advice done flag by notification for check exclusive
     * @param newestNotificationNo String
     * @return String
     */
    private String getAdviceDoneFlag(String newestNotificationNo) {
        TBL200Entity entityTbl200 = tbl200DAO.getNotificationInfo(newestNotificationNo);
        return entityTbl200.getAdviceDoneFlag();
    }

    /**
     * @param notificationNo String
     * @return boolean
     */
    @Override
    public boolean getTemporaryAdviceNotification(String notificationNo) {
        return CollectionUtils.isNotEmpty(tbl225DAO.findByNotificationNoAndDeleteFlagFalse(notificationNo));
    }
}
