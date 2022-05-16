/*
 * @(#) SessionUtil.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/03
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

public class SessionUtil {
    private static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(false);
    }

    public static Map<String, String> getSystemSettings() {
        HttpSession session = getSession();
        if (!ObjectUtils.isEmpty(session)) {
            Object object = session.getAttribute(CommonConstants.SYSTEM_SETTING);
            if (!ObjectUtils.isEmpty(object)) 
            	return (Map<String, String>) object;
        }
        return null;
    }

    public static String getSystemSettingByKey(String key) {
        Map<String, String> settings = getSystemSettings();
        if (CollectionUtils.isEmpty(settings)) {
            return null;
        }
        return settings.get(key);
    }

    public static String getSystemSettingByKey(String key, String defaultValue) {
        String value = getSystemSettingByKey(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static void invalidate() {
        HttpSession session = getSession();
        if (session != null) {
            session.invalidate();
        }
    }

    public static void setScreenId(String screenId) {
        HttpSession session = getSession();
        if (!ObjectUtils.isEmpty(session)) {
            session.setAttribute(CommonConstants.SCREEN_ID, screenId);
        }
    }

    public static String getScreenId() {
        HttpSession session = getSession();
        if (!ObjectUtils.isEmpty(session)) {
            Object screenIdObj = session.getAttribute(CommonConstants.SCREEN_ID);
            return ObjectUtils.isEmpty(screenIdObj) ? null : screenIdObj.toString();
        }
        return null;
    }
    
    /**
     * Get previous screen
     * @return
     */
    public static String getPreviousScreenId() {
        HttpSession session = getSession();
        if (!ObjectUtils.isEmpty(session)) {
            Object screenIdObj = session.getAttribute(CommonConstants.PREVIOUS_SCREEN);
            return ObjectUtils.isEmpty(screenIdObj) ? null : screenIdObj.toString();
        }
        return null;
    }

    public static void deleteUnNeedData() {
        HttpSession session = getSession();
        if (ObjectUtils.isEmpty(session)) {
            return;
        }
        Collections.list(session.getAttributeNames())
                .stream()
                .filter(item -> !item.equals(CommonConstants.SYSTEM_SETTING))
                .forEach(session::removeAttribute);
    }
}
