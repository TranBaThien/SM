/*
 * @(#) UserPrincipal.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security;

import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.ChangedPasswordFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private Object entity;
    private String username;
    private String password;
    private boolean isPasswordNonExpire;
    private Collection<? extends GrantedAuthority> authorities;
    private CurrentUserInformation currentUserInformation;

    private UserPrincipal(TBL120Entity entity, Collection<? extends GrantedAuthority> authorities) {
        this.entity = entity;
        this.username = entity.getLoginId();
        this.password = entity.getPassword();
        if (!ObjectUtils.isEmpty(entity.getPasswordPeriod())) {
            this.isPasswordNonExpire = !(ChangedPasswordFlag.UNCHANGED.getFlag().equals(entity.getBiginingPasswordChangeFlag())
                    || LocalDateTime.now().isAfter(entity.getPasswordPeriod()));
        } else {
            this.isPasswordNonExpire =  true;
        }
        this.authorities = authorities;
        this.currentUserInformation = getCurrentUserInformation(entity);
    }

    private UserPrincipal(TBL110Entity entity, List<SimpleGrantedAuthority> authorities) {
        this.entity = entity;
        this.username = entity.getLoginId();
        this.password = entity.getPassword();
        if (!ObjectUtils.isEmpty(entity.getPasswordPeriod())) {
            this.isPasswordNonExpire = !(ChangedPasswordFlag.UNCHANGED.getFlag().equals(entity.getBiginingPasswordChangeFlag())
                    || LocalDateTime.now().isAfter(entity.getPasswordPeriod()));
        } else {
            this.isPasswordNonExpire =  true;
        }
        this.authorities = authorities;
        this.currentUserInformation = getCurrentUserInformation(entity);
    }

    public static UserPrincipal create(TBL120Entity entity, boolean isNonLocked) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CITY"));
        int entityUserTypeCode = Integer.parseInt(entity.getUserType());
        if (UserTypeCode.TOKYO_STAFF.getCode() == entityUserTypeCode) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TOKYO"));
        } else if (UserTypeCode.MAINTENANCER.getCode() == entityUserTypeCode
                || UserTypeCode.SYSTEM_ADMIN.getCode() == entityUserTypeCode) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TOKYO"));
            authorities.add(new SimpleGrantedAuthority("ROLE_MAINTENANCE"));
        }
        return new UserPrincipal(entity, authorities);
    }

    public static UserPrincipal create(TBL110Entity entity, boolean isNonLocked) {
        return new UserPrincipal(entity, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANSION")));
    }

    public Object getEntity() {
        return entity;
    }

    public CurrentUserInformation getCurrentUserInformation() {
        return currentUserInformation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isPasswordNonExpire() {
        return isPasswordNonExpire;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private CurrentUserInformation getCurrentUserInformation(TBL120Entity entity) {
        CurrentUserInformation information = new CurrentUserInformation();
        information.setUserId(entity.getUserId());
        information.setLoginId(entity.getLoginId());
        information.setDisplayName(entity.getUserName());
        information.setLastTimeLoginTime(entity.getLastTimeLoginTime());
        information.setUserTypeCode(UserTypeCode.parseFrom(Integer.parseInt(entity.getUserType())));
        information.setCityCode(entity.getCityCode());
        return information;
    }

    private CurrentUserInformation getCurrentUserInformation(TBL110Entity entity) {
        CurrentUserInformation information = new CurrentUserInformation();
        information.setUserId(entity.getApartmentId());
        information.setLoginId(entity.getLoginId());
        information.setDisplayName(entity.getTbl100().getApartmentName());
        information.setLastTimeLoginTime(entity.getLastTimeLoginTime());
        information.setUserTypeCode(UserTypeCode.MANSION);
        information.setCityCode(entity.getTbl100().getCityCode());
        return information;
    }
}
