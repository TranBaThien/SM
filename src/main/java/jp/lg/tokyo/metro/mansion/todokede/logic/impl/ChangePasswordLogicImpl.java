/*
 * @(#) ChangePasswordLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nhvu
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL100;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL105;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL105DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.ChangePasswordForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IChangePasswordLogic;

@Service
public class ChangePasswordLogicImpl implements IChangePasswordLogic {

    private static final Logger logger = LogManager.getLogger(ChangePasswordLogicImpl.class);

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    TBL110DAO tbl110DAO;

    @Autowired
    TBL120DAO tbl120DAO;

    @Autowired
    TBL100DAO tbl100DAO;

    @Autowired
    TBL105DAO tbl105DAO;

    @Autowired
    private SequenceUtil sequenceUtil;

    @Transactional
    @Override
    public boolean save(Object obj) {
        return false;
    }

    @Override
    public boolean delete(Object obj) {
        return false;
    }

    /** ユーザログイン情報更新 Update user login TBL110 **/
    @Override
    public TBL110Entity changePasswordTBL110(ChangePasswordForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        Map<String, String> getSystemSettings = SessionUtil.getSystemSettings();
        String passworDay = getSystemSettings.get(CommonConstants.ST_M_PASSWORD_VALID_PERIOD);
        TBL110Entity entity = findByLoginToTBL110(form.getLoginId());
        entity.setPassword(encoder.encode(form.getPwdPassword()));
        LocalDateTime passwordPeriodNow = LocalDateTime.now();
        passwordPeriodNow = passwordPeriodNow.plusDays(Integer.parseInt(passworDay));
        entity.setPasswordPeriod(passwordPeriodNow);
        entity.setLoginErrorCount(CommonConstants.NUM_0);
        entity.setBiginingPasswordChangeFlag(CommonConstants.STR_1);
        entity.setUpdateUserId(form.getUpdateUserId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL110, entity.getApartmentId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl110DAO.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void changerPasswordTBL120(ChangePasswordForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            throw new BusinessException();
        }
        Map<String, String> getSystemSettings = SessionUtil.getSystemSettings();
        String passworDay = getSystemSettings.get(CommonConstants.ST_G_PASSWORD_VALID_PERIOD);
        TBL120Entity entity = findByLoginToTBL120(form.getLoginId());
        entity.setPassword(encoder.encode(form.getPwdPassword()));
        LocalDateTime passwordPeriod = LocalDateTime.now();
        LocalDateTime passwordPeriodNow = passwordPeriod.plusDays(Integer.parseInt(passworDay));
        entity.setPasswordPeriod(passwordPeriodNow);
        entity.setLoginErrorCount(CommonConstants.NUM_0);
        entity.setBiginingPasswordChangeFlag(CommonConstants.STR_1);
        entity.setUpdateUserId(form.getUpdateUserId());
        entity.setUpdateDatetime(LocalDateTime.now());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL120, entity.getUserId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        /** ユーザログイン情報更新 Update user login TBL120 **/
        tbl120DAO.save(entity);
    }

    @Override
    public TBL110Entity findByLoginToTBL110(String loginID) throws BusinessException {
        return tbl110DAO.findByLoginId(loginID);

    }

    @Override
    public TBL120Entity findByLoginToTBL120(String loginID) throws BusinessException {
        Optional<TBL120Entity> optinalEntity = tbl120DAO.findByLoginId(loginID);
        return optinalEntity.get();

    }

    @Override
    public TBL100Entity updateTBL100(ChangePasswordForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String cd025 = CommonConstants.STR_0;
        TBL100Entity entity = tbl100DAO.getMansionInformationById(form.getApartmentId());
        if (cd025.equals(form.getBiginingPasswordChangeFlag())) {
            entity.setRepasswordMailAddress(form.getTxtMail());
        }
        entity.setUpdateUserId(form.getApartmentId());
        Timestamp timestampnow = DateTimeUtil.getCurrentSystemDateTime();
        entity.setUpdateDatetime(timestampnow);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, new String[] { TBL100, entity.getApartmentId() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl100DAO.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateApartmentInfor(ChangePasswordForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (form == null) {
            throw new BusinessException();
        }
        // マンション情報更新/マンション情報履歴登録
        changePasswordTBL110(form);
        if (form.getUserTypeCode() == UserTypeCode.MANSION.getCode() || form.getUserTypeCode() == UserTypeCode.NONE.getCode()) {
            updateTBL100(form);
            updateTBL105(form);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public TBL105Entity updateTBL105(ChangePasswordForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        TBL100Entity entity2 = tbl100DAO.getMansionInformationById(form.getApartmentId());
        TBL105Entity entity = new TBL105Entity();
       
        BeanUtils.copyProperties(entity2, entity);
        //In case Mansion and other
        entity.setUpdateUserId(form.getApartmentId());

        //Set Sequence id
        entity.setHistoryNumber(sequenceUtil.generateKey(TBL105, CommonConstants.COL_HISTORY_NUMBER));
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        entity.setDeleteFlag(CommonConstants.STR_0);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, new String[] { TBL105, entity.getHistoryNumber() }));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return tbl105DAO.save(entity);
    }
}
