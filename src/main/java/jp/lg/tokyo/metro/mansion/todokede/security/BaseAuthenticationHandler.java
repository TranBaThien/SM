/*
 * @(#) BaseAuthenticationHandler.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserAvailabilityFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.LoginErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BaseAuthenticationHandler {
    private Log logger = LogFactory.getLog(this.getClass());
    private final String LOGIN_ID_PASSWORD = "ログインIDまたはパスワード";

    protected LoginErrorMessageVo validateAccount(TBL110Entity entity, int lockTimePeriod, int sessionTimeoutPeriod) {
        return getLoginErrorMessageVo(
                sessionTimeoutPeriod, lockTimePeriod,
                entity.getLoginId(),
                entity.getAccountLockFlag(),
                entity.getAccountLockTime(),
                entity.getLoginStatusFlag(),
                entity.getAvailability(),
                entity);
    }

    protected LoginErrorMessageVo validateAccount(TBL120Entity entity, int lockTimePeriod, int sessionTimeoutPeriod) {
        return getLoginErrorMessageVo(
                sessionTimeoutPeriod,
                lockTimePeriod,
                entity.getLoginId(),
                entity.getAccountLockFlag(),
                entity.getAccountLockTime(),
                entity.getLoginStatusFlag(),
                entity.getAvailability(),
                entity);
    }

    protected void updateFailedInfo(TBL120Entity entity, int maxLoginAttempt) {
        entity.setLoginErrorCount(entity.getLoginErrorCount() + 1);
        if (entity.getLoginErrorCount() >= maxLoginAttempt && !AccountLockFlag.LOCK.getFlag().equals(entity.getAccountLockFlag())) {
            LocalDateTime lockTime = LocalDateTime.now();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0040_I, entity.getLoginId(), lockTime.toString()));
            entity.setAccountLockFlag(AccountLockFlag.LOCK.getFlag());
            entity.setAccountLockTime(lockTime);
        }
        entity.setUpdateDatetime(LocalDateTime.now());
    }

    protected void updateFailedInfo(TBL110Entity entity, int maxLoginAttempt) {
        if (AccountLockFlag.LOCK.getFlag().equals(entity.getAccountLockFlag())) {
            return;
        }
        entity.setLoginErrorCount(entity.getLoginErrorCount() + 1);
        if (entity.getLoginErrorCount() >= maxLoginAttempt && !AccountLockFlag.LOCK.getFlag().equals(entity.getAccountLockFlag())) {
            LocalDateTime lockTime = LocalDateTime.now();
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0040_I, entity.getLoginId(), lockTime.toString()));
            entity.setAccountLockFlag(AccountLockFlag.LOCK.getFlag());
            entity.setAccountLockTime(lockTime);
        }
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());
    }

    protected void updateScreenInSession(String screenId) {
        SessionUtil.deleteUnNeedData();
        SessionUtil.setScreenId(screenId);
    }
    
    /**
     * Method add screenId to Cookie
     * @param screenId
     * @return
     */
    protected Cookie addScreenIdCookie(String screenId) {
    	 Cookie cookie = new Cookie(CommonConstants.SCREEN_ID, screenId);
         cookie.setMaxAge(12 * 60 * 60); // expires in 0.5 days
         //cookie.setSecure(true);
         //cookie.setHttpOnly(true);
         cookie.setPath(CommonConstants.APP_NAME);
         return cookie;
    }

    /**
     * Add session timeout to session
     * @param session HttpSession
     * @param sessionTimeoutInSecond int
     */
    protected void updateMaxInactiveInterval(HttpSession session, int sessionTimeoutInSecond) {
        session.setMaxInactiveInterval(sessionTimeoutInSecond);
    }

    protected void updateParameterInSession(HttpServletRequest httpServletRequest, LoginErrorMessageVo errorMessage) {
        HttpSession session = httpServletRequest.getSession();

        String loginId = httpServletRequest.getParameter("txtLoginId");
        String password = httpServletRequest.getParameter("pwdPassword");
        session.setAttribute("txtLoginId", loginId);
        session.setAttribute("pwdPassword", password);
        session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }


    private LoginErrorMessageVo getLoginErrorMessageVo(int sessionTimeoutPeriod,
                                                       int lockTimePeriod,
                                                       String loginId,
                                                       String accountLockFlag,
                                                       LocalDateTime accountLockTime,
                                                       String loginStatusFlag,
                                                       String availableFlag,
                                                       Object entityObject) {
        String messageCode;
        String field = "";
        if (isAccountLocked(accountLockFlag, accountLockTime, lockTimePeriod)) {
            messageCode = CommonConstants.MS_E0101;
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "アカウントロック"));
            return generateMessage(loginId, messageCode, field);
        }
        if (entityObject instanceof TBL110Entity) {
            TBL110Entity tbl110Entity = (TBL110Entity) entityObject;
            if (!isAccountEnabled(tbl110Entity)) {
                messageCode = CommonConstants.MS_E0125;
                field = LOGIN_ID_PASSWORD;
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "ユーザログイン無効化"));
                return generateMessage(loginId, messageCode, field);
            }
        }
        if (isAccountUnavailable(availableFlag)) {
            messageCode = CommonConstants.MS_E0125;
            field = LOGIN_ID_PASSWORD;
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "ユーザログイン利用不可"));
            return generateMessage(loginId, messageCode, field);
        }
        if (isAccountLoggedIn(loginStatusFlag)) {
            messageCode = CommonConstants.MS_E0126;
            field = String.valueOf(sessionTimeoutPeriod);
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "ログイン中"));
            return generateMessage(loginId, messageCode, field);
        }
        return null;
    }

    protected LoginErrorMessageVo generateMessage(String loginId, String messageCode, String field) {
        LoginErrorMessageVo errorMessage = new LoginErrorMessageVo();
        errorMessage.setLoginId(loginId);
        errorMessage.setMessageCode(messageCode);

        if (StringUtils.isEmpty(field)) {
            errorMessage.setMessage(MessageUtil.getMessage(messageCode));
        } else {
            errorMessage.setMessage(MessageUtil.getMessage(messageCode, field));
        }
        return errorMessage;
    }

    // Check account is already logged in or not
    private boolean isAccountLoggedIn(String loginStatusFlag) {
        return LoginStatusFlag.LOGGED_IN.getFlag().equals(loginStatusFlag);
    }

    // Check account is available
    private boolean isAccountUnavailable(String availableFlag) {
        return UserAvailabilityFlag.IMPOSSIBLE.getFlag().equals(availableFlag);
    }

    // Check account is locked or not
    protected boolean isAccountLocked(String accountLockFlag, LocalDateTime accountLockTime, int lockTimePeriod) {
        return AccountLockFlag.LOCK.getFlag().equals(accountLockFlag)
                && !ObjectUtils.isEmpty(accountLockTime)
                && accountLockTime.plus(lockTimePeriod, ChronoUnit.MINUTES).isAfter(LocalDateTime.now());
    }

    private boolean isAccountEnabled(TBL110Entity entity) {
        return entity.getValidityFlag().equals("1");
    }

}
