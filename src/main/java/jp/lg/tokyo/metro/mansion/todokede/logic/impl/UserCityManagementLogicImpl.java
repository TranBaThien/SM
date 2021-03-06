/*
 * @(#)UserCityManagementLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.BLANK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD001;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD001_UNDELETEFLAG;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD024;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD024_IMPOSSIBLE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD025;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD025_UNCHANGED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD026;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD026_NOT_LOGGED_IN;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD038;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.CD038_UNLOCK;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.COL_USER_ID;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.G_PASSWORD_VALID_PERIOD;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.LOG_LG1050_I;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MSG_ADD_FAILED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.MSG_UPDATE_FAILED;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.NUM_0;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.UND_LINE;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.ZERO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120BDAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.UserCityForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserCityManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserCityVo;

/**
 * @author PTLuan
 *
 */
@Service
public class UserCityManagementLogicImpl implements IUserCityManagementLogic {
    private static final String DASH = "-";
    private static final String CHAR_A = "A";
    private static final String CHAR_G = "G";
    private static final String USERID = "?????????ID";
    private static final String TOKYO = "130001";
    private static final Logger logger = LogManager.getLogger(UserCityManagementLogicImpl.class);
    private static final String DATA_TBL120_IS_NULL = "TBL120Entity is null";

    @Autowired
    private TBL120DAO tbl120DAO;
    @Autowired
    private TBL120BDAO tbl120BDAO;
    @Autowired
    private TBM001DAO tbm001DAO;
    @Autowired
    SequenceUtil sequenceUtil;

    /**
     * Get data show add user
     * @author PTLuan
     */
    @Override
    public UserCityVo getDataShow() {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        UserCityVo vo = new UserCityVo();
        /* ????????????ID */
        String key = tbl120BDAO.getNewLoginId();
        vo.setTxtLoginid(convertTheLoginIdToSixDigix(key));
        /* ???????????? */
        vo.setLstCitylist(getListCityCode(false));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    /**
     * Convert the LoginId
     * @author PTLuan
     */
    private String convertTheLoginIdToSixDigix(String loginId) {
        int idSize = 6 - loginId.length();
        for (int i = 0; i < idSize; i++) {
            loginId = ZERO + loginId;
        }
        return loginId;
    }

    /**
     * Get data show add user
     * @author PTLuan
     * @throws BusinessException When telNo don't contain blank
     */
    @Override
    public UserCityVo getUserInfor(String userId) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        UserCityVo vo = new UserCityVo();
        try {
            TBL120Entity entity = tbl120DAO.getGovernmentStaffInfo(userId);
            if (entity == null) {
                throw new BusinessException(DATA_TBL120_IS_NULL);
            }
            vo.setUserId(userId);
            vo.setUpdateTime(entity.getUpdateDatetime().toString());
            /* ?????? */
            vo.setRdoUserType(entity.getUserType());
            /* ???????????? */
            if ((UserTypeCode.CITY.getCode() + BLANK).equals(vo.getRdoUserType())) {
                vo.setLstCity(entity.getCityCode());
                vo.setLstCitylist(getListCityCode(false));
            } else {
                vo.setLstCity(BLANK);
                vo.setLstCitylist(null);
            }
            /* ?????? */
            vo.setTxtUserName(entity.getUserName());
            /* ?????????????????? */
            vo.setTxtUserNamePhonetic(entity.getUserNamePhonetic());
            /* ????????????????????? */
            vo.setTxtMail(entity.getMailAddress());
            /* ????????????????????????????????? */
            vo.setTxtMailConfirm(entity.getMailAddress());
            //Split string ManagerTelNo by Dash
            String[] mnTelNo = entity.getTelNo().split(DASH);
            /* ????????????1 */
            vo.setTxtPhonenumber1(mnTelNo[0]);
            /* ????????????2 */
            vo.setTxtPhonenumber2(mnTelNo[1]);
            /* ????????????3 */
            vo.setTxtPhonenumber3(mnTelNo[2]);
            /* ???????????? */
            vo.setChkAvailability(entity.getAvailability());
            /* ?????? */
            vo.setTxtContact(entity.getStopReason());
            /* ????????????ID */
            vo.setTxtLoginid(entity.getLoginId().substring(1, 7));
        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return vo;
    }

    /**
     * Get list city
     * @author PTLuan
     */
    private List<CodeVo> getListCityCode(boolean isAddUser) {
        List<TBM001Entity> list = tbm001DAO.getMunicipalMasterNotDelete();
        List<CodeVo> listCode = new ArrayList<CodeVo>();
        for (TBM001Entity one : list) {
            CodeVo code = new CodeVo();
            code.setValue(one.getCityCode());
            code.setLabel(one.getCityName());
            listCode.add(code);
        }
        return listCode;
    }

    /**
     * Add/Edit user information.
     * @param form UserCityForm
     * @author PTLuan
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUserInfor(UserCityForm form) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        /* NO3 ????????????????????? */
        if ("".equals(form.getUserId())) {
            registerUserInfor(form);
        } else {
            updateUserInfor(form);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    /**
     * Add user information.
     * 
     * @param form UserCityForm
     * @author PTLuan
     * @return Is true if User info add complete successfully.
     * @throws BusinessException When set data error
     */
    private void registerUserInfor(UserCityForm form) throws BusinessException {
        try {
            TBL120Entity entity = new TBL120Entity();
            /* ?????????ID */
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL120), COL_USER_ID);
            entity.setUserId(keyNo);

            /* ????????????ID */
            int userTypeCode = Integer.parseInt(form.getRdoUserType());
            if (UserTypeCode.CITY.getCode() == userTypeCode || UserTypeCode.TOKYO_STAFF.getCode() == userTypeCode) {
                entity.setLoginId(CHAR_G + form.getTxtLoginid() + sequenceUtil.calculateCheckDigit(form.getTxtLoginid()));
            } else {
                entity.setLoginId(CHAR_A + form.getTxtLoginid() + sequenceUtil.calculateCheckDigit(form.getTxtLoginid()));
            }

            /* ??????????????? */
            entity.setPassword(new BCryptPasswordEncoder().encode(form.getPwdPassword()));
            /* ??????????????? */
            entity.setUserType(form.getRdoUserType());
            /* ?????? */
            entity.setUserName(form.getTxtUserName());
            /* ?????????????????? */
            entity.setUserNamePhonetic(form.getTxtUserNamePhonetic());
            /* ????????????????????? */
            if (UserTypeCode.CITY.getCode() == userTypeCode) {
                entity.setCityCode(form.getLstCity());
            } else {
                entity.setCityCode(TOKYO);
            }
            /* ????????????????????? */
            entity.setMailAddress(form.getTxtMail());
            /* ???????????? */
            entity.setTelNo(form.getTxtPhonenumber1() + DASH + form.getTxtPhonenumber2() + DASH + form.getTxtPhonenumber3());
            /* ??????????????????????????? */
            int period = Integer.parseInt(SessionUtil.getSystemSettingByKey(G_PASSWORD_VALID_PERIOD));
            LocalDateTime periodTime = LocalDateTime.now().plus(period, ChronoUnit.DAYS);
            entity.setPasswordPeriod(periodTime);
            /* ???????????????????????? */
            entity.setLoginErrorCount(NUM_0);
            /* ????????????????????????????????? */
            entity.setAccountLockFlag(CodeUtil.getValue(CD038, CD038_UNLOCK));
            /* ?????????????????????????????? */
            entity.setAccountLockTime(null);
            /* ???????????????????????? */
            entity.setLastTimeLoginTime(null);
            /* ???????????? */
            entity.setAvailability(form.getChkAvailability());
            /* ???????????????????????? */
            entity.setStopTime(null);
            /* ???????????????????????? */
            entity.setStopReason(form.getTxtContact());
            /* ??????????????????????????????????????? */
            entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CD025, CD025_UNCHANGED));
            /* ???????????????????????? */
            entity.setLoginStatusFlag(CodeUtil.getValue(CD026, CD026_NOT_LOGGED_IN));
            /* ??????????????? */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* ???????????????ID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* ???????????? */
            entity.setUpdateDatetime(LocalDateTime.now());
            logger.info(MessageUtil.getMessage(LOG_LG1050_I, new String[] { TBL120, USERID }));
            tbl120DAO.save(entity);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1050_I, TBL120, entity.getUserId()));
        } catch (Exception e) {
            throw new BusinessException(String.format(MSG_ADD_FAILED, TBL120));
        }
    }

    /**
     * Update user information.
     * 
     * @param form UserCityForm
     * @author PTLuan
     * @return Is true if User info updated complete successfully.
     * @throws BusinessException When set data error
     */
    private void updateUserInfor(UserCityForm form) throws BusinessException {
        try {
            TBL120Entity entity = tbl120DAO.getGovernmentStaffInfo(form.getUserId());
            if (entity == null) {
                throw new BusinessException(DATA_TBL120_IS_NULL);
            }
            /* ?????? */
            entity.setUserName(form.getTxtUserName());
            /* ?????????????????? */
            entity.setUserNamePhonetic(form.getTxtUserNamePhonetic());
            /* ????????????????????? */
            int userTypeCode = Integer.parseInt(form.getRdoUserType());
            if (UserTypeCode.CITY.getCode() == userTypeCode) {
                entity.setCityCode(form.getLstCity());
            }
            /* ????????????????????? */
            entity.setMailAddress(form.getTxtMail());
            /* ???????????? */
            entity.setTelNo(form.getTxtPhonenumber1() + DASH + form.getTxtPhonenumber2() + DASH + form.getTxtPhonenumber3());
            /* ???????????? */
            entity.setAvailability(form.getChkAvailability());
            /* ???????????????????????? */
            if (entity.getAvailability().equals(CodeUtil.getValue(CD024, CD024_IMPOSSIBLE))) {
                entity.setStopTime(LocalDateTime.now());
            }else {
                entity.setStopTime(null);
            }
            /* ???????????????????????? */
            entity.setStopReason(form.getTxtContact());
            /* ??????????????? */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* ???????????????ID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* ???????????? */
            entity.setUpdateDatetime(LocalDateTime.now());
            logger.info(MessageUtil.getMessage(LOG_LG1050_I, new String[] { TBL120, USERID }));
            tbl120DAO.save(entity);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL120, entity.getUserId()));
        }catch(Exception e) {
            throw new BusinessException(String.format(MSG_UPDATE_FAILED, TBL120));
        }
    }

    /**
     * Get login status flag
     * @param userId String
     * @author PTLuan
     */
    @Override
    public TBL120Entity getGovernmentInfo(String userId) {
        return tbl120DAO.getGovernmentStaffInfo(userId);
    }

    /**
     * Check exist loginId
     * @param txtLoginId String
     * @author PTLuan
     */
    @Override
    public boolean checkExistLoginId(String txtLoginId) {
        List<TBL120Entity> entitys = tbl120DAO.getGovernmentStaffInfoByLikeLoginId(UND_LINE + txtLoginId + UND_LINE);
        if (entitys.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
