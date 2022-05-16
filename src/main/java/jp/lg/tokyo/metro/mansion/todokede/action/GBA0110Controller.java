/*
 * @(#) GBA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 4, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import static jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil.getMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowCity;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.ChangedPasswordFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.PasswordChangeGuide;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;

@Controller
@AllowCity
public class GBA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(GBA0110Controller.class);    
    
    @Autowired
    TBL120DAO tbl120DAO;
    
    private static final String GBA0110 = "GBA0110";
    

    /**
     * @param session HttpSession
     * @param model Model
     * @param request HttpServletRequest
     * @return screenId
     */
    @GetMapping("/GBA0110")
    public String show(HttpSession session, Model model, HttpServletRequest request) throws BusinessException{
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (!isNeedChanger()) {
            return "redirect:" + CommonConstants.SCREEN_ID_SBA0110;
        }
        Enumeration<String> exceptionObj = session.getAttributeNames();
        ArrayList<String> list = Collections.list(exceptionObj);
        //delete session expect system setting session.
        for (String item : list) {
            //Check session 
            if (!item.equals(CommonConstants.SYSTEM_SETTING)
                && !item.equals("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN")
                    && !item.equals(CommonConstants.PREVIOUS_SCREEN) && !item.equals(CommonConstants.CURRENT_SCREEN)
                    && !item.equals(CommonConstants.SCREEN_ID)) {
                session.removeAttribute(item);
            }
        }
        
        request.getSession().setAttribute(CommonConstants.PASSWORD_CHANGE_GUIDE, PasswordChangeGuide.FROM_MENU);
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                
        return GBA0110;
    }


    /**
     * @return
     */
    private boolean isNeedChanger() {
        String loginID = SecurityUtil.getUserLoggedInInfo().getLoginId();
        Optional<TBL120Entity> entity1 =  tbl120DAO.findByLoginId(loginID);
        TBL120Entity entity = entity1.get();
        if (ChangedPasswordFlag.UNCHANGED.getFlag().equals(entity.getBiginingPasswordChangeFlag())
                || LocalDateTime.now().isAfter(entity.getPasswordPeriod())) {
            return false;
        }
        return true;
    }
    
}
