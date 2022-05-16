/*
 * @(#) AMAuthenticationSuccessHandler.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.am;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.ChangedPasswordFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.PasswordChangeGuide;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.BaseAuthenticationHandler;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import jp.lg.tokyo.metro.mansion.todokede.vo.LoginErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;

public class AMAuthenticationSuccessHandler extends BaseAuthenticationHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private TBL110DAO tbl110DAO;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        TBL110Entity entity = (TBL110Entity) principal.getEntity();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        int lockTimePeriod = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD));
        int sessionTimeoutInMinute = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_SESSION_TIMEOUT_PERIOD));
        LoginErrorMessageVo errorMessage = validateAccount(entity, lockTimePeriod, sessionTimeoutInMinute);
        if (!ObjectUtils.isEmpty(errorMessage)) {
            updateParameterInSession(httpServletRequest, errorMessage);
            SecurityContextHolder.clearContext();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, "管理組合ログイン"));
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/MAA0110");
            return;
        }

        updateLoginInfo(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, entity.getLoginId()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, "管理組合ログイン"));
        updateScreenInSession(CommonConstants.SCREEN_ID_MAA0110);
        
        httpServletResponse.addCookie(addScreenIdCookie(CommonConstants.SCREEN_ID_MAA0110));
        HttpSession session = httpServletRequest.getSession();
        updateMaxInactiveInterval(session, sessionTimeoutInMinute * 60);
        if (ChangedPasswordFlag.UNCHANGED.getFlag().equals(entity.getBiginingPasswordChangeFlag())) {
            session.setAttribute(CommonConstants.PASSWORD_CHANGE_GUIDE, PasswordChangeGuide.INITIAL_LOGIN_OR_ISSUANCE);
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/SBA0110");
        } else if ((!ObjectUtils.isEmpty(entity.getPasswordPeriod())) && LocalDateTime.now().isAfter(entity.getPasswordPeriod())) {
            session.setAttribute(CommonConstants.PASSWORD_CHANGE_GUIDE, PasswordChangeGuide.EXPIRED);
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/SBA0110");
        } else {
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/MBA0110");
        }

    }

    private void updateLoginInfo(TBL110Entity entity) {
        entity.setLastTimeLoginTime(LocalDateTime.now());
        entity.setLoginStatusFlag(LoginStatusFlag.LOGGED_IN.getFlag());
        entity.setAccountLockFlag(AccountLockFlag.UNLOCK.getFlag());
        entity.setLoginErrorCount(0);
        entity.setAccountLockTime(null);
        entity.setUpdateUserId(entity.getApartmentId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
        tbl110DAO.save(entity);
    }
}
