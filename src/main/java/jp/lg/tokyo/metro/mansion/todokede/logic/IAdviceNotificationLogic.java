/*
 * @(#) IAdviceNotificationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 27, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import javax.validation.Valid;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.AdviceNotificationForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.AdviceNotificationVo;

/**
 * @author nvlong1
 *
 */
public interface IAdviceNotificationLogic {

    /**
     * Get data advice notification for prepare show screen
     * @param newestNotificationNo String
     * @return AdviceNotificationVo
     * @throws BusinessException when get data for AdviceNotification fail 
     * @author nvlong1
     */
    public AdviceNotificationVo getAdviceNotification(String newestNotificationNo) throws BusinessException;

    /**
     * compare notificationNo to perform processing or show error
     * @param apartmentId String
     * @param newestNotificationNo String
     * @return String
     * @author nvlong1
     */
    public String exclusiveCheck(String apartmentId, String newestNotificationNo) throws BusinessException;

    /**
     * Save data temporarily saved data
     * @param form AdviceNotificationForm
     * @author nvlong1
     */
    public void tmpSave(@Valid AdviceNotificationForm form) throws BusinessException;

    /**
     * Get data temporary storage for prepare show screen
     * @param newestNotificationNo String
     * @return TBL225Entity
     * @author nvlong1
     */
    public TBL225Entity getAdviceNotificationTemporaryStorage(String newestNotificationNo);

    /**
     * get data advisory notice for prepare show report
     * @param adviceNo String
     * @return TBL220Entity
     * @author nvlong1
     */
    public TBL220Entity getAdvisoryNoticeById(String adviceNo);

    /**
     * @param notificationNo String
     * @return 
     */
    boolean getTemporaryAdviceNotification(String notificationNo);

    /**
     * Save data advice notification registration 
     * @param form AdviceNotificationForm
     * @throws BusinessException when send mail fail
     * @author nvlong1
     */
    public void saveAdvice(@Valid AdviceNotificationForm form) throws BusinessException;

}
