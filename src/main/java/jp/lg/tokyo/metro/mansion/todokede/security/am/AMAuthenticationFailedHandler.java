/*
 * @(#) AMAuthenticationFailedHandler.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.am;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.BaseAuthenticationHandler;
import jp.lg.tokyo.metro.mansion.todokede.vo.LoginErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;

public class AMAuthenticationFailedHandler extends BaseAuthenticationHandler implements AuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private TBL110DAO tbl110DAO;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {
        String loginId = httpServletRequest.getParameter("txtLoginId");
        TBL110Entity entity = tbl110DAO.findByLoginId(loginId);
        LoginErrorMessageVo errorMessage;

        if (!ObjectUtils.isEmpty(entity)) {
            int lockTimePeriod = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD));
            if (isAccountLocked(entity.getAccountLockFlag(), entity.getAccountLockTime(), lockTimePeriod)) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "アカウントロック"));
                errorMessage = generateMessage(loginId, CommonConstants.MS_E0101, null);
            } else {
                int maxLoginAttempt = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_M_LOGIN_INVALIDITY_TIMES_MAX));
                updateFailedInfo(entity, maxLoginAttempt);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "ログイン認証不可"));

                tbl110DAO.save(entity);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
                errorMessage = generateMessage(loginId, CommonConstants.MS_E0107, "ログインIDまたはパスワード");
            }
        } else {
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "アカウント不正"));
            errorMessage = generateMessage(loginId, CommonConstants.MS_E0107, "ログインIDまたはパスワード");
        }

        updateParameterInSession(httpServletRequest, errorMessage);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/MAA0110");
    }
}
