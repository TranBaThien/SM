/*
 * @(#) GSAuthenticationFailedHandler.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security.gs;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;

public class GSAuthenticationFailedHandler extends BaseAuthenticationHandler implements AuthenticationFailureHandler {
    private Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private TBL120DAO tbl120DAO;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {
        String loginId = httpServletRequest.getParameter("txtLoginId");
        Optional<TBL120Entity> optional = tbl120DAO.findByLoginId(loginId);
        LoginErrorMessageVo errorMessage;
        if (optional.isPresent()) {
            TBL120Entity entity = optional.get();

            int lockTimePeriod = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_ACCOUNT_LOCK_PERIOD));
            if (isAccountLocked(entity.getAccountLockFlag(), entity.getAccountLockTime(), lockTimePeriod)) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "アカウントロック"));
                errorMessage = generateMessage(loginId, CommonConstants.MS_E0101, null);
            } else {
                int maxLoginAttempt = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_G_LOGIN_INVALIDITY_TIMES_MAX));
                updateFailedInfo(entity, maxLoginAttempt);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "ログイン認証不可"));

                tbl120DAO.save(entity);
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL120, entity.getUserId()));
                errorMessage = generateMessage(loginId, CommonConstants.MS_E0107, "ログインIDまたはパスワード");
            }
        } else {
            errorMessage = generateMessage(loginId, CommonConstants.MS_E0107, "ログインIDまたはパスワード");
            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0030_I, loginId, "アカウント不正"));
        }

        updateParameterInSession(httpServletRequest, errorMessage);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, "onAuthenticationFailure"));
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/GAA0110");
    }

}
