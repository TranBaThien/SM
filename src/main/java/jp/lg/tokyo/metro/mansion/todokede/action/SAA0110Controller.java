/*
 * @(#) SAA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;

@Controller
public class SAA0110Controller {

    private static final Logger logger = LogManager.getLogger(SAA0110Controller.class);
    private static final String SAA0110 = "SAA0110";

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Show term screen.
     * @param model Model
     * @return String
     */
    @GetMapping("/SAA0110")
    public String show(Model model) throws BusinessException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        String agreement = getContentTerm();
        model.addAttribute("agreement", agreement);
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return SAA0110;
    }

    /**
     * Get content term from path store in session setting.
     * @return String
     * @author tqvu1
     */
    private String getContentTerm() {
        // 「TERMS_HTML_PATH」の設定値から取得したWEBサーバパス配置されている規約のHTMLを読み込む。
        return templateEngine.process(SessionUtil.getSystemSettingByKey(CommonConstants.TERMS_HTML_PATH, "rule/rule.htm"), new Context());
    }

}
