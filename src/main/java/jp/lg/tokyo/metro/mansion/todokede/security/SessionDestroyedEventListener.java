/*
 * @(#)SessionDestroyedEventListener.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author: hbthinh
 * Create Date: Dec 2, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.security;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.LoginStatusFlag;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.UserTypeCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL110DAO;
import jp.lg.tokyo.metro.mansion.todokede.dao.TBL120DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL110;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBL120;

@Component
public class SessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {
    @Autowired
    private TBL110DAO tbl110DAO;
    
    @Autowired
    TBL120DAO tbl120DAO;
    
    private Log logger = LogFactory.getLog(this.getClass());
    
    /**
     * Update table TBL110, TBL120 when session destroy execute
     * @param event HttpSessionDestroyedEvent
     * @return 
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        for (SecurityContext securityContext : event.getSecurityContexts()) {
            UserPrincipal userPrincipal = (UserPrincipal) securityContext.getAuthentication().getPrincipal();

            if (userPrincipal != null) {
                if (userPrincipal.getCurrentUserInformation().getUserTypeCode() == UserTypeCode.MANSION) {
                        
                    TBL110Entity entity = tbl110DAO.findByLoginId(userPrincipal.getUsername());
                    //Update user login Table TB110_ユーザログイン更新  【更新テーブル】 ユーザログイン（管理組合）（TBL110）
                    updateLoginInfoTBL110(entity);
                
                } else if (userPrincipal.getCurrentUserInformation().getUserTypeCode() != UserTypeCode.NONE) {
                    
                    //Update user login Table TB120_ユーザログイン更新  【更新テーブル】 ユーザログイン（都区市町村）（TBL120））
                    Optional<TBL120Entity> optional = tbl120DAO.findByLoginId(userPrincipal.getUsername());
                    updateLoginInfoTBL120(optional.get());
                }

                HttpSession session = event.getSession();
                // Remove session_ セッション情報破棄
                session.invalidate();
            }
        }
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
        
    /**
     * Update Table TBL110 when session destroy execute
     * @param TBL110Entity entity
     * @return 
     */
    private void updateLoginInfoTBL110(TBL110Entity entity) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        entity.setLoginStatusFlag(LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUpdateUserId(entity.getApartmentId());
        entity.setUpdateDatetime(DateTimeUtil.getCurrentSystemDateTime());

        tbl110DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL110, entity.getApartmentId()));
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
    
    /**
     * Update Table TBL120 when session destroy execute
     * @param TBL120Entity entity
     * @return 
     */
    private void updateLoginInfoTBL120(TBL120Entity entity) {
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        
        entity.setLoginStatusFlag(LoginStatusFlag.NOT_LOGGED_IN.getFlag());
        entity.setUpdateUserId(entity.getUserId());
        entity.setUpdateDatetime(LocalDateTime.now());

        tbl120DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBL120, entity.getUserId()));
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG0020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
    }
    
    
    
}
