/*
 * @(#) GAA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/GAA0110")
public class GAA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GAA0110Controller.class);

    /**
     * show GAA0110
     * @param session HttpSession
     * @param model Model
     * @return GAA0110
     */
    @GetMapping
    public String show(HttpSession session, Model model) throws BusinessException{
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (isAuthenticated()) {
            return "redirect:" + getNextScreenAfterLogin();
        }
        appendSystemSetting(session);
        appendLoginMessage(model, session);
        appendNotice(model, CommonConstants.CITY_NOTICE_HTML_PATH);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return CommonConstants.SCREEN_ID_GAA0110;
    }

    /**
     * get notice
     * @return content of notice file
     */
    @GetMapping(value = "/notice", produces = "text/plain; charset=utf-8")
    @ResponseBody
    public String notice() {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String notice;
        try {
            notice = getStaticHtmlFileAsString(CommonConstants.CITY_NOTICE_HTML_PATH);
        } catch (IOException e) {
            logger.error(MessageUtil.getMessage(CommonConstants.LOG_LG9900_E, e.getMessage()));
            notice = Strings.EMPTY;
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return notice;
    }
}
