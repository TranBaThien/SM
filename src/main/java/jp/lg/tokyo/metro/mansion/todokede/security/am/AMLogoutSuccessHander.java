/*
 * @(#)AMLogoutSuccessHander.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.am;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

public class AMLogoutSuccessHander implements LogoutSuccessHandler {

    public AMLogoutSuccessHander() {
        super();
    }
    
    private static final String TBL110 = "TBL110";
    private Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private TBL110DAO tbl110DAO;

    /**
     * 
     * Update Table TBL110, delete session when logout
     *  
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onLogoutSuccess(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse, final Authentication authentication)
            throws IOException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        TBL110Entity entity = tbl110DAO.findByLoginId(principal.getUsername());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        
        // Update table TBL110
        updateLoginInfo(entity);        
        
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute(CommonConstants.SYSTEM_SETTING);
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/MAA0110");
    }

    /**
     * Update Table TBL110 when session destroy execute
     * @param TBL110Entity entity
     * @return 
     */
    private void updateLoginInfo(TBL110Entity entity) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        entity.setLoginStatusFlag(LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUpdateUserId(entity.getApartmentId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());

        tbl110DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

    }

}
