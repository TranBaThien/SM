/*
 * @(#) GlobalExceptionHandler.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/09
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
    private static final String ZAA0130 = "ZAA0130";

    /**
     * handle for Business Exception
     * @param req HttpServletRequest
     * @param ex Exception
     * @return String
     */
    @ExceptionHandler(BusinessException.class)
    public String handleError(HttpServletRequest req, Exception ex) {
    	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.toString() + " : " + ex.getMessage()), ex);
        return ZAA0130;

    }
    
    /**
     * handle for  Exception
     * 
     * @param req HttpServletRequest
     * @param ex Exception
     * @return String
     */
    @ExceptionHandler(Exception.class)
    public String handleErrorException(HttpServletRequest req, Exception ex) {
    	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.toString() + " : " + ex.getMessage()),ex);
        return ZAA0130;

    }
}
