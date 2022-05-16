/*
 * @(#) UserLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.GovernmentStaffInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.Objects;

/**
 * @author DLLy
 */
@Service
public class UserLogicImpl implements IUserLogic {
    private static final Logger logger = LogManager.getLogger(UserLogicImpl.class);
    @Autowired
    private TBL120DAO tbl120DAO;

    /**
     * @param userId
     * @return
     * @throws BusinessException
     */
    @Override
    public GovernmentStaffInfoVo getGovernmentStaffUserLoginInfo(String userId) throws BusinessException, SystemException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (StringUtils.isBlank(userId)) throw new BusinessException("Require userId");

        TBL120Entity entity = tbl120DAO.getGovernmentStaffInfo(userId);
        GovernmentStaffInfoVo governmentStaffInfoVo = new GovernmentStaffInfoVo();

        if (Objects.nonNull(entity)) {
            CommonUtils.copyProperties(governmentStaffInfoVo, entity);
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return governmentStaffInfoVo;
    }
}
