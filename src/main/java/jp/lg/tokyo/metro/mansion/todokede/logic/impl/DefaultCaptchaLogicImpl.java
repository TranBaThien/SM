/*
 * @(#) DefaultCaptchaServiceImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICaptchaLogic;

@Service
public class DefaultCaptchaLogicImpl implements ICaptchaLogic {

    @Autowired
    protected HttpSession httpSession;

    private static final Logger logger = LogManager.getLogger(DefaultCaptchaLogicImpl.class);
    
    @Override
    public void save(String sessionId, String kaptcha) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, kaptcha);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }

    @Override
    public boolean match(String sessionId, String kaptchaCheck) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String captcha = (String) httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        boolean result = false;
        if (kaptchaCheck.equalsIgnoreCase(captcha)) {
            result = true;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return result;
    }

}
