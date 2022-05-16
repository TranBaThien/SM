/*
 * @(#)UserListLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120BDAO;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserListLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

@Service
public class UserListLogicImpl implements IUserListLogic {

    @Autowired
    TBL120BDAO tbl120BDao;
    private static final Logger logger = LogManager.getLogger(UserListLogicImpl.class);

    /**
     * Get list User info
     * @return UserInforVo
     * @author ptluan
     * @throws BusinessException 
     */
    @Override
    public List<UserInforVo> getListUserInfo() throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    	try {
	        List<Code> cd024 = CodeUtil.getList(CommonConstants.CD024);
	        Map<String,String> mapcd024 = new HashMap<String, String>();
	        for (Code one : cd024) {
	            mapcd024.put(one.getValue(), one.getLabel());
	        }
	
	        List<Code> cd026 = CodeUtil.getList(CommonConstants.CD026);
	        Map<String,String> mapcd026 = new HashMap<String, String>();
	        for (Code one : cd026) {
	            mapcd026.put(one.getValue(), one.getLabel());
	        }
	
	        List<Code> cd027 = CodeUtil.getList(CommonConstants.CD027);
	        Map<String,String> mapcd027 = new HashMap<String, String>();
	        for (Code one : cd027) {
	            mapcd027.put(one.getValue(), one.getLabel());
	        }
	
	        List<UserInforVo> vo = tbl120BDao.getListUserB();
	        for (UserInforVo one : vo) {
	            /* Set City Name */
	            if (one.getUserType() != null && !one.getUserType().equals((CommonConstants.BLANK + UserTypeCode.CITY.getCode()))) {
	                one.setCityName(CommonConstants.DASH);
	            }
	            /* Set UserType */
	            one.setUserType(mapcd027.get(one.getUserType()));
	            /* Set lblLoginStatus */
	            one.setLoginStatusFlag(mapcd026.get(one.getLoginStatusFlag()));
	            /* Set lblUsageStatus */
	            one.setAvailability(mapcd024.get(one.getAvailability()));
	        }
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
	        return vo;
    	} catch (Exception ex) {
    	    logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.getMessage()));
    		throw new BusinessException(ex.getMessage());
    	}
    }
}
