/*
 * @(#) ZAA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.util.List;
import javax.servlet.http.HttpSession;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */
@Controller
public class ZAA0150Controller {
    private static final Logger logger = LogManager.getLogger(ZAA0150Controller.class);
    private static final String ZAA0150 = "ZAA0150";
    private static final String MESSAGE_MAX = "エラー件数が100件を超えたため、表示を打ち切りました。";
    private static final String ERROR_LIST_1_MODEL_NAME = "errorList1";

    /**
     * show list mansion upload error
     *
     * @param model Model
     * @param session HttpSession
     * @return
     */
    @GetMapping("/ZAA0150")
    public String show(Model model, HttpSession session) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        //get error list from AAA0110/GEC0110
        @SuppressWarnings("unchecked")
        List<ZAA0150ErrorVo> errorList = (List<ZAA0150ErrorVo>)session.getAttribute(ERROR_LIST_1_MODEL_NAME);
        session.removeAttribute(ERROR_LIST_1_MODEL_NAME);

        if (errorList != null && errorList.size() > 100) {
            for (int i = errorList.size(); i > 100; i--) {
                errorList.remove(i - 1);
            }
            errorList.add(new ZAA0150ErrorVo(CommonConstants.BLANK, CommonConstants.BLANK, MESSAGE_MAX));
        }
        model.addAttribute("previousScreen", session.getAttribute(CommonConstants.PREVIOUS_SCREEN));
        model.addAttribute("errorList", errorList);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ZAA0150;
    }
}
