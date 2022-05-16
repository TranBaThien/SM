/*
 * @(#) SecurityUtil
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

public class SecurityUtil {
    private SecurityUtil() {
        throw new IllegalStateException("Utility class.");
    }

    public static UserPrincipal getUserPrinciple() {
        if (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())
                || ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAnonymousUser() {
        return  (ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())
                || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

    public static String getCurrentLoginId() {
    	try {
            CurrentUserInformation information = getUserLoggedInInfo();
            if (ObjectUtils.isEmpty(information)) {
                return null;
            }
            return information.getLoginId();
    	} catch (Exception ex) {
    		//Check in case not yet login
    		return null;
    	}
    }

    public static CurrentUserInformation getUserLoggedInInfo() {
        UserPrincipal principal = getUserPrinciple();
        if (ObjectUtils.isEmpty(principal)) {
            return null;
        }
        return principal.getCurrentUserInformation();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}
