/*
 * @(#) UserAuthority.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.enumerated;

/**
 * @author hntvy
 *
 */
public enum UserAuthority {
    CAN_BE_USE(1, "助言通知、現地調査通知の使用可能"),
    CANNOT_BE_USE(2,"助言通知、現地調査通知の使用不可");
    
    private final int code;
    private final String userAuthority;
    
    /**
     * @param code int
     * @param userAuthority String
     */
    private UserAuthority(int code, String userAuthority) {
        this.code = code;
        this.userAuthority = userAuthority;
    }
    
    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @return the userAuthority
     */
    public String getUserAuthority() {
        return userAuthority;
    }

}
