/*
 * @(#) AcceptStatus.java
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
public enum AcceptStatus {
    
    UNACCEPTED(1, "未届"),
    ACCEPTED(2, "受理済"),
    NOT_SPECIFIED(3, "指定しない");
    
    /**
     * @param code int
     * @param acceptStatus String
     */
    private AcceptStatus(int code, String acceptStatus) {
        this.code = code;
        this.acceptStatus = acceptStatus;
    }
    
    private final int code;
    private final String acceptStatus;
    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }
    
    /**
     * @return the acceptStatus
     */
    public String getAcceptStatus() {
        return acceptStatus;
    }
    
    
}
