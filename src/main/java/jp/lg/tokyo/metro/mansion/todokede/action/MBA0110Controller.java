/*
 * @(#) MBA0110Controller.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMansion;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.ChangedPasswordFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.PasswordChangeGuide;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SecurityUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.logic.INotificationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationVo;

/**
 * @author hntvy
 *
 */

@Controller
@AllowMansion
public class MBA0110Controller extends BaseMansionController {
    private static final Logger logger = LogManager.getLogger(MBA0110Controller.class);
    
    @Autowired
    TBL110DAO tbl110DAO;
    
    public static final String MBA0110 = "MBA0110";
    
    @Autowired
    public INotificationLogic notificationLogic;
    
    
    /**
     * @param session HttpSession
     * @param model Model
     * @param request HttpServletRequest
     * @return
     */
    @GetMapping(value = {"/MBA0110"})
    public String show(HttpSession session, Model model, HttpServletRequest request) throws BusinessException {
        logger.info(getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        if (!isNeedChanger()) {
            return "redirect:" + CommonConstants.SCREEN_ID_SBA0110;
        }
        Enumeration<String> exceptionObj = session.getAttributeNames();
        ArrayList<String> list = Collections.list(exceptionObj);
        //delete session expect system setting session.
        for (String item : list) {
            if (!item.equals(CommonConstants.SYSTEM_SETTING)
                && !item.equals("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN")) {
                session.removeAttribute(item);
            }
        }
         
        //Get current apartment notification
        String loginId = SecurityUtil.getUserLoggedInInfo().getLoginId();
        String apartmentId = tbl110DAO.findByLoginId(loginId).getApartmentId();
        NotificationVo vo = notificationLogic.getApartmentNotification(apartmentId);
        model.addAttribute("notification", vo);
        model.addAttribute("apartmentId", apartmentId);
        request.getSession().setAttribute(CommonConstants.PASSWORD_CHANGE_GUIDE, PasswordChangeGuide.FROM_MENU);
        logger.info(getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        return MBA0110;
    }


    /**
     * @return
     */
    private boolean isNeedChanger() {
        String loginID = SecurityUtil.getUserLoggedInInfo().getLoginId();
        TBL110Entity entity =  tbl110DAO.findByLoginId(loginID);
        if (ChangedPasswordFlag.UNCHANGED.getFlag().equals(entity.getBiginingPasswordChangeFlag())
                || LocalDateTime.now().isAfter(entity.getPasswordPeriod())) {
            return false;
        }
        return true;
    }

}
