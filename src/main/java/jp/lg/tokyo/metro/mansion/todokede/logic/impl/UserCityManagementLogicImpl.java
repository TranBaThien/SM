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
    private static final String USERID = "ユーザID";
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
        /* ログインID */
        String key = tbl120BDAO.getNewLoginId();
        vo.setTxtLoginid(convertTheLoginIdToSixDigix(key));
        /* 区市町村 */
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
            /* 種別 */
            vo.setRdoUserType(entity.getUserType());
            /* 区市町村 */
            if ((UserTypeCode.CITY.getCode() + BLANK).equals(vo.getRdoUserType())) {
                vo.setLstCity(entity.getCityCode());
                vo.setLstCitylist(getListCityCode(false));
            } else {
                vo.setLstCity(BLANK);
                vo.setLstCitylist(null);
            }
            /* 氏名 */
            vo.setTxtUserName(entity.getUserName());
            /* 氏名フリガナ */
            vo.setTxtUserNamePhonetic(entity.getUserNamePhonetic());
            /* メールアドレス */
            vo.setTxtMail(entity.getMailAddress());
            /* メールアドレス（確認） */
            vo.setTxtMailConfirm(entity.getMailAddress());
            //Split string ManagerTelNo by Dash
            String[] mnTelNo = entity.getTelNo().split(DASH);
            /* 電話番号1 */
            vo.setTxtPhonenumber1(mnTelNo[0]);
            /* 電話番号2 */
            vo.setTxtPhonenumber2(mnTelNo[1]);
            /* 電話番号3 */
            vo.setTxtPhonenumber3(mnTelNo[2]);
            /* 利用停止 */
            vo.setChkAvailability(entity.getAvailability());
            /* 備考 */
            vo.setTxtContact(entity.getStopReason());
            /* ログインID */
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
        /* NO3 ユーザ情報取得 */
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
            /* ユーザID */
            String keyNo = sequenceUtil.generateKey(CommonProperties.getProperty(TBL120), COL_USER_ID);
            entity.setUserId(keyNo);

            /* ログインID */
            int userTypeCode = Integer.parseInt(form.getRdoUserType());
            if (UserTypeCode.CITY.getCode() == userTypeCode || UserTypeCode.TOKYO_STAFF.getCode() == userTypeCode) {
                entity.setLoginId(CHAR_G + form.getTxtLoginid() + sequenceUtil.calculateCheckDigit(form.getTxtLoginid()));
            } else {
                entity.setLoginId(CHAR_A + form.getTxtLoginid() + sequenceUtil.calculateCheckDigit(form.getTxtLoginid()));
            }

            /* パスワード */
            entity.setPassword(new BCryptPasswordEncoder().encode(form.getPwdPassword()));
            /* ユーザ種別 */
            entity.setUserType(form.getRdoUserType());
            /* 氏名 */
            entity.setUserName(form.getTxtUserName());
            /* 氏名フリガナ */
            entity.setUserNamePhonetic(form.getTxtUserNamePhonetic());
            /* 区市町村コード */
            if (UserTypeCode.CITY.getCode() == userTypeCode) {
                entity.setCityCode(form.getLstCity());
            } else {
                entity.setCityCode(TOKYO);
            }
            /* メールアドレス */
            entity.setMailAddress(form.getTxtMail());
            /* 電話番号 */
            entity.setTelNo(form.getTxtPhonenumber1() + DASH + form.getTxtPhonenumber2() + DASH + form.getTxtPhonenumber3());
            /* パスワード有効期限 */
            int period = Integer.parseInt(SessionUtil.getSystemSettingByKey(G_PASSWORD_VALID_PERIOD));
            LocalDateTime periodTime = LocalDateTime.now().plus(period, ChronoUnit.DAYS);
            entity.setPasswordPeriod(periodTime);
            /* ログイン失敗回数 */
            entity.setLoginErrorCount(NUM_0);
            /* アカウントロックフラグ */
            entity.setAccountLockFlag(CodeUtil.getValue(CD038, CD038_UNLOCK));
            /* アカウントロック日時 */
            entity.setAccountLockTime(null);
            /* 最終ログイン日時 */
            entity.setLastTimeLoginTime(null);
            /* 利用可否 */
            entity.setAvailability(form.getChkAvailability());
            /* 利用可否変更日時 */
            entity.setStopTime(null);
            /* 利用可否変更理由 */
            entity.setStopReason(form.getTxtContact());
            /* 初期化パスワード変更フラグ */
            entity.setBiginingPasswordChangeFlag(CodeUtil.getValue(CD025, CD025_UNCHANGED));
            /* ログイン中フラグ */
            entity.setLoginStatusFlag(CodeUtil.getValue(CD026, CD026_NOT_LOGGED_IN));
            /* 削除フラグ */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* 更新ユーザID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* 更新日時 */
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
            /* 氏名 */
            entity.setUserName(form.getTxtUserName());
            /* 氏名フリガナ */
            entity.setUserNamePhonetic(form.getTxtUserNamePhonetic());
            /* 区市町村コード */
            int userTypeCode = Integer.parseInt(form.getRdoUserType());
            if (UserTypeCode.CITY.getCode() == userTypeCode) {
                entity.setCityCode(form.getLstCity());
            }
            /* メールアドレス */
            entity.setMailAddress(form.getTxtMail());
            /* 電話番号 */
            entity.setTelNo(form.getTxtPhonenumber1() + DASH + form.getTxtPhonenumber2() + DASH + form.getTxtPhonenumber3());
            /* 利用可否 */
            entity.setAvailability(form.getChkAvailability());
            /* 利用可否変更日時 */
            if (entity.getAvailability().equals(CodeUtil.getValue(CD024, CD024_IMPOSSIBLE))) {
                entity.setStopTime(LocalDateTime.now());
            }else {
                entity.setStopTime(null);
            }
            /* 利用可否変更理由 */
            entity.setStopReason(form.getTxtContact());
            /* 削除フラグ */
            entity.setDeleteFlag(CodeUtil.getValue(CD001, CD001_UNDELETEFLAG));
            /* 更新ユーザID */
            entity.setUpdateUserId(SecurityUtil.getUserLoggedInInfo().getUserId());
            /* 更新日時 */
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
