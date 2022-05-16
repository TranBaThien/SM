/*
 * @(#) MunicipalMasterInfoLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Objects;

import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBM001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM001Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.IMunicipalMasterInfoLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.MunicipalMasterInfoVo;

/**
 * @author DLLy
 */
@Service
public class MunicipalMasterInfoLogicImpl implements IMunicipalMasterInfoLogic {

    @Autowired
    private TBM001DAO tbm001DAO;

    private static final Logger logger = LogManager.getLogger(MunicipalMasterInfoLogicImpl.class);
    
    /**
     * @param cityCode
     * @return
     */
    @Override
    public MunicipalMasterInfoVo getMunicipalMasterInfo(String cityCode) throws SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (isNotBlank(cityCode)) {
            TBM001Entity entity = tbm001DAO.getMunicipalMasterInfo(cityCode);
            if (Objects.nonNull(entity)) {
                MunicipalMasterInfoVo municipalMasterInfoVo = new MunicipalMasterInfoVo();
                CommonUtils.copyProperties(municipalMasterInfoVo, entity);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return municipalMasterInfoVo;
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return null;
    }
}
