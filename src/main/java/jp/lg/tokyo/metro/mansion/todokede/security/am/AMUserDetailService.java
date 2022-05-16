/*
 * @(#) AMUserDetailService.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.security.am;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AccountLockFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.security.UserPrincipal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AMUserDetailService implements UserDetailsService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private TBL110DAO dao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, loginId));
        TBL110Entity entity = dao.findByLoginId(loginId);
        if (ObjectUtils.isEmpty(entity)) {
            throw new UsernameNotFoundException(loginId);
        }
        return UserPrincipal.create(entity, !isAccountLocked(entity));
    }

    private boolean isAccountLocked(TBL110Entity entity) {
        int lockTimePeriod = Integer.parseInt(SessionUtil.getSystemSettingByKey(CommonConstants.ST_M_ACCOUNT_LOCK_PERIOD));
        LocalDateTime lockTime = entity.getAccountLockTime();
        return AccountLockFlag.LOCK.getFlag().equals(entity.getAccountLockFlag())
                && !ObjectUtils.isEmpty(lockTime)
                && lockTime.plus(lockTimePeriod, ChronoUnit.MINUTES).isAfter(LocalDateTime.now());
    }
}
