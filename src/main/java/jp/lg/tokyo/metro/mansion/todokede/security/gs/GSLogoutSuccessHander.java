/*
 * @(#)GSLogoutSuccessHander.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

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
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;

public class GSLogoutSuccessHander implements LogoutSuccessHandler {

    public GSLogoutSuccessHander() {
        super();
    }
    private static final String TBL120 ="TBL120";

    private Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private TBL120DAO tbl120DAO;

    /**
     * 
     * Update Table TBL120, delete session when logout
     *  
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onLogoutSuccess(final HttpServletRequest httpServletRequest,final HttpServletResponse httpServletResponse,
        final Authentication authentication) throws IOException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId(principal.getUsername());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        // Update user login Table TB120_ユーザログイン更新  【更新テーブル】 ユーザログイン（都区市町村）（TBL120））
        updateLoginInfo(optional.get());
        
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute(CommonConstants.SYSTEM_SETTING);
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/GAA0110");

    }

    /**
     * Update table TBL120 / 【更新条件: ユーザログイン（都区市町村）（TBL120)
     * @param entity
     * @return 
     */
    private void updateLoginInfo(TBL120Entity entity) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        entity.setLoginStatusFlag(LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUpdateUserId(entity.getUserId());
        entity.setUpdateDatetime(LocalDateTime.now());

        tbl120DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL120, entity.getUserId()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
}
