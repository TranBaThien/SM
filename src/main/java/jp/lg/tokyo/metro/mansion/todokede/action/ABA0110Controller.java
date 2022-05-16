/*
 * @(#)ABA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/12/19
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.text.MessageFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMaintenance;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.IUserListLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.UserInforVo;

@AllowMaintenance
@Controller
public class ABA0110Controller {
    private static final String ABA0110 = "ABA0110";

    @Autowired
    private IUserListLogic userListLogic;
    private static final Logger logger = LogManager.getLogger(ABA0110Controller.class);

    /**
     * Call Show users info
     * @param error String
     * @param model Model
     * @return view
     * @author ptluan
     * @throws BusinessException 
     */
    @PostMapping(value = {"/ABA0110"})
    public String show(String error, Model model) throws BusinessException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<UserInforVo> vo = userListLogic.getListUserInfo();
        model.addAttribute("userList", vo);
        model.addAttribute("count", vo.size());
        if (error.equals("true")) {
            model.addAttribute("errorMessage", MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0113), new Object[] {}));
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return ABA0110;
    }
}
