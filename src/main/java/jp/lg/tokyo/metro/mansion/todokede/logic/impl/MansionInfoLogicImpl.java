/*
 * @(#) MansionInfoLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/24
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL100DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL100Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMansionInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;

/**
 * @author PVHung3
 */
@Service
public class MansionInfoLogicImpl implements IMansionInfoLogic {
    @Autowired
    private TBL100DAO tbl100DAO;
    
    private static final Logger logger = LogManager.getLogger(MansionInfoLogicImpl.class);
    
    /**
     * Flag check is object
     */
    private static final String OBJECT_NUMB = "1";

    /**
     * @param apartmentId
     * @return
     */
    public MansionInfoVo getMansionInformationById(String apartmentId) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (isNotBlank(apartmentId)) {
            TBL100Entity entity = tbl100DAO.getMansionInformationById(apartmentId);
            if (nonNull(entity)) {
                MansionInfoVo info = new MansionInfoVo();
                //Copy properties
                CommonUtils.copyProperties(info, entity);
                info.setRdoSupportCode(entity.getSupport());
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return info;
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }

    @Override
    public boolean isExistMansionWithSupportCd(String apartmentId, String supportCd) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (OBJECT_NUMB.equals(supportCd)) {
            TBL100Entity entity = tbl100DAO.getMansiondByIdAndSupportCd(apartmentId, supportCd);
            // Check exist mansion with supportCd.
            if (nonNull(entity)) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return true;
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return false;
    }
}
