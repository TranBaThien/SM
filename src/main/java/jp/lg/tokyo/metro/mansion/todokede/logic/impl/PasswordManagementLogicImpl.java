/*
 * @(#) PasswordManagementLoginImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL100;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MailUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IEMailLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IPasswordManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML035Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ManagementAssociationApplicationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissuePasswordAfterSaveVo;

@Service
public class PasswordManagementLogicImpl implements IPasswordManagementLogic {

    private static final Logger logger = LogManager.getLogger(PasswordManagementLogicImpl.class);
    private static final String APARTMENT_ID = "APARTMENT_ID";
    private static final String LOGIN_ID = "LOGIN_ID";
    private static final String MSG_GET_DATA_FAILED = "Could not find data %s with %s= %s";
    private static final String MSG_NO_REFERENCE_TABLE = "No data for table %s reference with table %s with %s= %s";

    @Autowired
    private TBL120DAO tbl120DAO;

    @Autowired
    private TBL110DAO tbl110DAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEMailLogic mailLogic;

    @Override
    public GovernmentStaffInfoVo getGovernmentStaffUserLoginInfo(String loginId, String mailAddress, String availability) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        GovernmentStaffInfoVo vo = null;
        try {
            TBL120Entity entity =  tbl120DAO.getGovernmentStaffUserLoginInfo(loginId, mailAddress, availability);
            vo = new GovernmentStaffInfoVo();
            CommonUtils.copyProperties(vo, entity);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return null;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }


    @Override
    public ManagementAssociationApplicationVo getManagementAssociationInfo(
            String loginId, String mailAddress, String availability, String validityFlag) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ManagementAssociationApplicationVo vo = null;
        try {
            TBL110Entity entity = tbl110DAO.getManagementAssociationInfo(loginId, mailAddress, availability, validityFlag);
            vo = new ManagementAssociationApplicationVo();
            CommonUtils.copyProperties(vo, entity);
        } catch (Exception ex) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
            return null;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @Override
    public ReissuePasswordAfterSaveVo saveDataToTbl120(String loginId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ReissuePasswordAfterSaveVo vo = null;
        try {
            Optional<TBL120Entity> optional = tbl120DAO.findByLoginId(loginId);
            if (optional.isPresent()) {
                TBL120Entity entity = optional.get();
                final Integer cityOneTimePasswordPeriod =
                        CommonUtils.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.CITY_ONETIME_PASSWORD_PERIOD));
                final String cityLoginUrl = SessionUtil.getSystemSettingByKey(CommonConstants.CITY_LOGIN_URL);
                final String defaultPassword = generatePassword();

                entity.setPassword(passwordEncoder.encode(defaultPassword));
                entity.setPasswordPeriod(LocalDateTime.now().plusMinutes(cityOneTimePasswordPeriod));
                entity.setLoginErrorCount(CommonConstants.NUM_0);
                entity.setAccountLockFlag(CodeUtil.getValue(CommonConstants.CD038, CommonConstants.CD038_UNLOCK));
                entity.setAccountLockTime(null);
                entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CommonConstants.CD025, CommonConstants.CD025_UNCHANGED));
                entity.setLoginStatusFlag(CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_NOT_LOGGED_IN));
                entity.setUpdateUserId(loginId);
                entity.setUpdateDatetime(LocalDateTime.now());

                entity = tbl120DAO.save(entity);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { CommonConstants.TBL120, entity.getUserId() }));
            
                vo = new ReissuePasswordAfterSaveVo();
                vo.setContactMailAddress(entity.getMailAddress());
                vo.setContactName(entity.getUserName());
                vo.setCityCode(entity.getCityCode());
                vo.setPasswordPeriod(cityOneTimePasswordPeriod);
                vo.setLoginUrl(cityLoginUrl);
                vo.setPassword(defaultPassword);
                
            } else {
                throw new BusinessException(String.format(MSG_GET_DATA_FAILED, TBL120, LOGIN_ID, loginId));
            }
        } catch (Exception e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            throw new BusinessException(e, e.getMessage());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @Override
    public ReissuePasswordAfterSaveVo saveDataToTbl110(String loginId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        ReissuePasswordAfterSaveVo vo = null;
        TBL110Entity entity = tbl110DAO.findByLoginId(loginId);
        if (entity != null) {
            final Integer apartmentOneTimePasswordPeriod =
                    CommonUtils.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.APARTMENT_ONETIME_PASSWORD_PERIOD));
            final String aparatmentLoginUrl = SessionUtil.getSystemSettingByKey(CommonConstants.APARTMENT_LOGIN_URL);
            final String defaultPassword = generatePassword();

            entity.setPassword(passwordEncoder.encode(defaultPassword));
            entity.setPasswordPeriod(LocalDateTime.now().plusMinutes(apartmentOneTimePasswordPeriod));
            entity.setLoginErrorCount(CommonConstants.NUM_0);
            entity.setAccountLockFlag(CodeUtil.getValue(CommonConstants.CD038, CommonConstants.CD038_UNLOCK));
            entity.setAccountLockTime(null);
            entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CommonConstants.CD025, CommonConstants.CD025_UNCHANGED));
            entity.setLoginStatusFlag(CodeUtil.getValue(CommonConstants.CD026, CommonConstants.CD026_NOT_LOGGED_IN));
            entity.setUpdateUserId(loginId);
            entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());

            entity = tbl110DAO.save(entity);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { CommonConstants.TBL110, entity.getApartmentId() }));
            
            vo = new ReissuePasswordAfterSaveVo();
            TBL100Entity tbl100 = entity.getTbl100();
            if (tbl100 != null) {
                vo.setContactMailAddress(tbl100.getRepasswordMailAddress());
                vo.setApartmentName(tbl100.getApartmentName());
                String contactName = tbl100.getNewestNotificationName();
                // 最新届出者氏名がNULLの場合
                if (CheckUtil.isBlank(contactName)) {
                    contactName = SessionUtil.getSystemSettingByKey(CommonConstants.MAIL_CONTACT_NAME);
                }
                vo.setContactName(contactName);
            } else {
                throw new BusinessException(
                        String.format(MSG_NO_REFERENCE_TABLE, TBL100, TBL110, APARTMENT_ID, entity.getApartmentId()));
            }
            vo.setPasswordPeriod(apartmentOneTimePasswordPeriod);
            vo.setLoginUrl(aparatmentLoginUrl);
            vo.setPassword(defaultPassword);
        } else {
            throw new BusinessException(String.format(MSG_GET_DATA_FAILED, TBL110, LOGIN_ID, loginId));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    @Override
    public ML030Vo getML030Template(ReissuePasswordAfterSaveVo reissuePasswordAfterSaveVo) throws BusinessException {
        try {
            ML030Vo ml030Vo = mailLogic.getCommonTemplateMail(ML030Vo.class);
            ml030Vo.setContactMailAddress(reissuePasswordAfterSaveVo.getContactMailAddress());
            ml030Vo.setApartmentName(reissuePasswordAfterSaveVo.getApartmentName());
            ml030Vo.setContactName(reissuePasswordAfterSaveVo.getContactName());
            ml030Vo.setPasswordPeriod(reissuePasswordAfterSaveVo.getPasswordPeriod());
            ml030Vo.setLoginUrl(reissuePasswordAfterSaveVo.getLoginUrl());
            ml030Vo.setPassword(reissuePasswordAfterSaveVo.getPassword());
            return ml030Vo;
        } catch (Exception e) {
            throw new BusinessException(e, e.getMessage());
        }
    }

    @Override
    public ML035Vo getML035Template(ReissuePasswordAfterSaveVo reissuePasswordAfterSaveVo) throws BusinessException {
        try {
            ML035Vo ml035Vo = mailLogic.getCommonTemplateMail(ML035Vo.class);
            ml035Vo.setContactMailAddress(reissuePasswordAfterSaveVo.getContactMailAddress());
            ml035Vo.setContactName(reissuePasswordAfterSaveVo.getContactName());
            ml035Vo.setPasswordPeriod(reissuePasswordAfterSaveVo.getPasswordPeriod());
            ml035Vo.setPassword(reissuePasswordAfterSaveVo.getPassword());
            return ml035Vo;
        } catch (Exception e) {
            throw new BusinessException(e, e.getMessage());
        }
    }

    @Override
    public void sendML030(ML030Vo ml030Vo) throws BusinessException {
        mailLogic.sendMailWithContent(ml030Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml030Vo), MailUtil.getML030Parameters(ml030Vo), null);
    }

    @Override
    public void sendML035(ML035Vo ml035Vo) throws BusinessException {
        mailLogic.sendMailWithContent(ml035Vo.getMailTemplate(), MailUtil.convertToEmailInfo(ml035Vo), MailUtil.getML035Parameters(ml035Vo), null);
    }

    /**
     * Function to generate random pre-hash password with 16 characters halfsize alphanumberic.
     * @return String
     * @author tqvu1
     */
    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

}
