/*
 * @(#) LoginErrorMessageVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

import java.io.Serializable;

public class LoginErrorMessageVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String messageCode;
    private String loginId;
    private String message;

    public LoginErrorMessageVo(String messageCode, String loginId, String message) {
        this.messageCode = messageCode;
        this.loginId = loginId;
        this.message = message;
    }

    public LoginErrorMessageVo() {
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
