/*
 * @(#)ReissueManagementUserLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SequenceUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IReissueUserManagementLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.ReissueUserManagementVo;

/**
 * @author hbthinh
 *
 */

@Service
public class ReissueUserManagementLogicImpl implements IReissueUserManagementLogic {

    private static final Logger logger = LogManager.getLogger(ReissueUserManagementLogicImpl.class);
    
        
    @Autowired
    private TBL110DAO tbl110DAO;
    
    @Autowired
    private SequenceUtil sequenceUtil;
    
    /**
     * 
     * Update data to table TBL110 in case reissue user management information
     * 
     *  @param apartmentId String
     *  @param ressReissueUserManagementVo ReissueUserManagementVo
     */
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean updateUserLogin(String apartmentId, ReissueUserManagementVo ressReissueUserManagementVo) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        TBL110Entity entity = tbl110DAO.getUserByApartmentId(apartmentId);

        if (entity != null) {
            // Set data from Reissue Management Vo to TBL110Entity
            // Generate login id by sequence code util
            String loginId = sequenceUtil.generateKey(TBL110, CommonConstants.COL_LOGIN_ID, UserTypeCode.MANSION);
            entity.setLoginId(loginId);
            entity.setPassword(ressReissueUserManagementVo.getPwdPassword());
            entity.setPasswordPeriod(ressReissueUserManagementVo.getPasswordPeriod());
            entity.setLoginErrorCount(ressReissueUserManagementVo.getLoginErrorCount());
            entity.setAccountLockFlag(ressReissueUserManagementVo.getAcountLockFlag());
            entity.setAccountLockTime(ressReissueUserManagementVo.getAccountLockTime());
            entity.setLastTimeLoginTime(ressReissueUserManagementVo.getLastTimeLoginTime());
            entity.setBiginingPasswordChangeFlag(ressReissueUserManagementVo.getBindingPasswordChangeFlag());
            entity.setDeleteFlag(ressReissueUserManagementVo.getDeleteFlag());
            entity.setUpdateUserId(ressReissueUserManagementVo.getUpdateUserId());
            entity.setUpdateDatetime(ressReissueUserManagementVo.getUpdateDateTime());

            // Save data to table TBL110
            tbl110DAO.save(entity);
            
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return true;
        }
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return false;
    }
    
    /**
     * Get data from table TBL110 by apartment Id in case reissue user management information 
     * 
     * @param apartmentId String
     * @return vo ReissueUserManagementVo
     * @when execute Exception throws BusinessException
     */
    @Override
    public ReissueUserManagementVo getUserLoginInformation(String apartmentId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        ReissueUserManagementVo vo = new ReissueUserManagementVo();
        TBL110Entity entity = tbl110DAO.getUserByApartmentId(apartmentId);
        
        if (entity != null) {
            vo.setLblLoginidNow(entity.getLoginId());
            vo.setApartmentId(apartmentId);
            vo.setLastTimeLoginTime(entity.getLastTimeLoginTime());
          
            
            if (entity.getUpdateDatetime() != null) {
                vo.setUpdateDateTimeInitial(entity.getUpdateDatetime().toString());
            }
        }
            
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            
        return vo;            
    }
    
    /**
     * Get data from table TBL110 by Login Id in case reissue user management information 
     * 
     * @param loginId String
     * @return 
     * @ when execute Exception throws BusinessException
     */
    @Override
    public ReissueUserManagementVo getUserLoginInformationByLoginId(String loginId) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        ReissueUserManagementVo vo = new ReissueUserManagementVo();
        TBL110Entity entity = tbl110DAO.findByLoginId(loginId);
        if (entity != null) {
            vo.setLoginStatusFlag(entity.getLoginStatusFlag());
            vo.setUpdateDateTime(entity.getUpdateDatetime());
            if (entity.getUpdateDatetime() !=null) {
                vo.setUpdateDateTimeInitial(entity.getUpdateDatetime().toString());
            }
        }
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        return vo;
    }
}
