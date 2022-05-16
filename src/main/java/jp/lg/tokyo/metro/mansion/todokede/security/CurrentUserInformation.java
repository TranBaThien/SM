/*
 * @(#) CurrentUserInformation
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security;

import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CurrentUserInformation implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
    private String loginId;
    private LocalDateTime lastTimeLoginTime;
    private String displayName;
    private UserTypeCode userTypeCode;
    private String cityCode;
    private String userAuthority;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public LocalDateTime getLastTimeLoginTime() {
        return lastTimeLoginTime;
    }

    public void setLastTimeLoginTime(LocalDateTime lastTimeLoginTime) {
        this.lastTimeLoginTime = lastTimeLoginTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUserTypeCode(UserTypeCode userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public UserTypeCode getUserTypeCode() {
        return userTypeCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(String userAuthority) {
        this.userAuthority = userAuthority;
    }
}
