/*
 * @(#) RedirectInvalidSessionStrategy.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2020/02/03
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

/**
 * @author PVHung3
 *
 */
public class RedirectInvalidSessionStrategy implements InvalidSessionStrategy {

	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private boolean createNewSession = true;

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Cookie[] arr = request.getCookies();
		String screenId = CommonConstants.SCREEN_ID_MAA0110;
		for (Cookie cookie : arr) {
			String cookieName = cookie.getName();
			if (CommonConstants.SCREEN_ID.equals(cookieName)) {
				screenId = cookie.getValue();
				break;
			} 
		}
		
		String destinationUrl = CommonConstants.SLASH + screenId;
		
		if (createNewSession) {
			request.getSession();
		}
		 
		redirectStrategy.sendRedirect(request, response, destinationUrl);
	}
}
