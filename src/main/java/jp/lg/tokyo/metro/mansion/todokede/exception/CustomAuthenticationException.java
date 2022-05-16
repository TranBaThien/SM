/*
 * @(#) CustomAuthenticationException.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/21
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author PVHung3
 *
 */
public class CustomAuthenticationException extends AuthenticationException{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String loginId;

	public CustomAuthenticationException(String loginId, String msg) {
		super(msg);
		this.loginId = loginId;
	}

	public CustomAuthenticationException(String loginId, String msg, Throwable t) {
		super(msg, t);
		this.loginId = loginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
