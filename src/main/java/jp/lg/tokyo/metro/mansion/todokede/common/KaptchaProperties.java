/*
 * @(#) KaptchaProperties.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kaptcha")
public class KaptchaProperties {

    private String border = "no";
    private String textproducerFontColor = "black";
    private int textproducerCharSpace = 5;

    /**
     * 验证码过期时间，单位秒
     */
    private int expireSeconds = 5 * 60;

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getTextproducerFontColor() {
        return textproducerFontColor;
    }

    public void setTextproducerFontColor(String textproducerFontColor) {
        this.textproducerFontColor = textproducerFontColor;
    }

    public int getTextproducerCharSpace() {
        return textproducerCharSpace;
    }

    public void setTextproducerCharSpace(int textproducerCharSpace) {
        this.textproducerCharSpace = textproducerCharSpace;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
