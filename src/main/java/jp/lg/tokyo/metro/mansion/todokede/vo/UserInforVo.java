/*
 * @(#)UserInforVo.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.vo;

/**
 * @author ptluan
 *
 */
public class UserInforVo {
    private String cityCode;
    private String userId;
    private String loginId;
    private String userName;
    private String cityName;
    private String userType;
    private String loginStatusFlag;
    private String availability;
    private String loginErrorCount;

    public UserInforVo() {
        super();
    }

    /**
     * @param cityCode String
     * @param userId String
     * @param loginId String
     * @param userName String
     * @param cityName String
     * @param userType String
     * @param loginStatusFlag String
     * @param availability String
     * @param loginErrorCount String
     */
    public UserInforVo(String cityCode, String userId, String loginId, String userName, String cityName, String userType,
            String loginStatusFlag, String availability, String loginErrorCount) {
        super();
        this.cityCode = cityCode;
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
        this.cityName = cityName;
        this.userType = userType;
        this.loginStatusFlag = loginStatusFlag;
        this.availability = availability;
        this.loginErrorCount = loginErrorCount;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginStatusFlag() {
        return loginStatusFlag;
    }

    public void setLoginStatusFlag(String loginStatusFlag) {
        this.loginStatusFlag = loginStatusFlag;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(String loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
}
