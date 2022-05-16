/*
 * @(#) CustomErrorController.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;

/**
 * @author PVHung3
 * @support Nxthu2
 * @return ZAA0130に遷移する or ZAA0140に遷移する
 * 
 */
@Controller
public class CustomErrorController implements ErrorController {
	private static final Logger logger = LogManager.getLogger(CustomErrorController.class);
    /**
     * @param request HttpServletRequest
     * @return
     */
    @GetMapping(value = "/error")
    public String handleError(HttpServletRequest request, Exception ex) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
        	logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.toString() + " : " + ex.getMessage()),ex);
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "ZAA0140";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {                
                return "ZAA0130";
            }         
        }
        logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, ex.toString() + " : " + ex.getMessage()),ex);
        return "ZAA0130";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
