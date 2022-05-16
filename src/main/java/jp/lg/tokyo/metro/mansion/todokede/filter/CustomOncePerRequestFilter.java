/*
 * @(#) CustomOnePerRequestFilter.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/29
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.filter;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.vo.LoginErrorMessageVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.WebAttributes;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomOncePerRequestFilter extends GenericFilterBean {
    private Log logger = LogFactory.getLog(this.getClass());

    private static final String USERNAME_OR_PASSWORD = "ログインIDまたはパスワード";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String requestURI = httpServletRequest.getRequestURI();
        //Get screen Name from URI request: Ex GEB0110
        String contextPath = httpServletRequest.getContextPath();
        String replace = requestURI.replace(contextPath, CommonConstants.BLANK);
        if (replace.length() > 7) {
            String uriScreen = replace.substring(1, 8);
            //Check URI is  list screen of App
            if (isContainsValue(uriScreen)) {
                Object currentScreenNm = httpServletRequest.getSession().getAttribute(CommonConstants.CURRENT_SCREEN);
                httpServletRequest.getSession().setAttribute(CommonConstants.CURRENT_SCREEN, uriScreen);
                //Check not equal name request with current session
                if (!uriScreen.equals(currentScreenNm)) {
                    httpServletRequest.getSession().setAttribute(CommonConstants.PREVIOUS_SCREEN, currentScreenNm);
                }
            }
        }

        HttpSession session = httpServletRequest.getSession(true);
        final String gsLoginPath = contextPath + "/gs/login";
        final String amLoginPath = contextPath + "/am/login";
        final String gsLoginUrl = contextPath + "/GAA0110";
        final String amLoginUrl = contextPath + "/MAA0110";
        if (!StringUtils.isEmpty(requestURI) && (requestURI.equals(gsLoginPath) || requestURI.equals(amLoginPath))) {
            if (requestURI.equals(gsLoginPath)) {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, "都区市町村ログイン"));
            } else {
                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, "管理組合ログイン"));
            }
            String loginId = httpServletRequest.getParameter("txtLoginId");
            String password = httpServletRequest.getParameter("pwdPassword");

            LoginErrorMessageVo errorMessage = null;
            if (StringUtils.isEmpty(loginId)) {
                String message = MessageUtil.getMessage(CommonConstants.MS_E0002, CommonConstants.FORM_LOGIN_ID);
                if (StringUtils.isEmpty(password)) {
                    message = message + "<br />" + MessageUtil.getMessage(CommonConstants.MS_E0002, CommonConstants.FORM_PASSWORD);
                }
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0002, loginId, message);
            } else if (StringUtils.isEmpty(password)) {
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0002, loginId, MessageUtil.getMessage(CommonConstants.MS_E0002, CommonConstants.FORM_PASSWORD));
            } else if (loginId.length() != 8) {
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0107, loginId, MessageUtil.getMessage(CommonConstants.MS_E0107, USERNAME_OR_PASSWORD));
            } else if (password.length() < 8 || password.length() > 16) {
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0107, loginId, MessageUtil.getMessage(CommonConstants.MS_E0107, USERNAME_OR_PASSWORD));
            } else if (checkNotAlphaNumeric(loginId)) {
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0107, loginId, MessageUtil.getMessage(CommonConstants.MS_E0107, USERNAME_OR_PASSWORD));
            } else if (checkNotAlphaNumeric(password)) {
                errorMessage = new LoginErrorMessageVo(CommonConstants.MS_E0107, loginId, MessageUtil.getMessage(CommonConstants.MS_E0107, USERNAME_OR_PASSWORD));
            }

            if (!ObjectUtils.isEmpty(errorMessage)) {
                session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
                session.setAttribute("txtLoginId", loginId);
                session.setAttribute("pwdPassword", password);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                if (requestURI.equals(gsLoginPath)) {
                    httpServletResponse.sendRedirect(gsLoginUrl);
                } else {
                    httpServletResponse.sendRedirect(amLoginUrl);
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }

    /**
     *
     * @param arr
     * @param targetValue
     * @return
     */
    private boolean isContainsValue(String screenName) {

        for(String s: CommonConstants.LST_SCREENS){
            if(s.equals(screenName))
                return true;
        }
        return false;
    }

    /**
     * check is Alpha Numeric
     * @param s String
     * @return true if only contain alpha numeric
     */
    private boolean checkNotAlphaNumeric(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if ((!Character.isLetterOrDigit(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
}
