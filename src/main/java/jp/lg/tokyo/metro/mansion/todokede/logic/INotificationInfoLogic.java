/*
 * @(#) INotificationInfoLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationAcceptanceForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationExportForm;
import jp.lg.tokyo.metro.mansion.todokede.form.NotificationRegistrationForm;
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoImportCsvVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;

import javax.transaction.SystemException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author DLLy
 */
public interface INotificationInfoLogic {

    /**
     * Get notification info by notificationNo
     *
     * @param notificationNo
     * @return
     * @throws BusinessException
     */
    NotificationInfoVo getNotificationInfo(String notificationNo) throws SystemException;

    /**
     * Save notification info
     *
	 * @param vo
     * @param idScreen
     * @param user
     * @param nextNotificationDateMax
     * @param notificationRenewalPeriod
     * @return boolean
     * @throws BusinessException
     */
    void deleteTemporaryAndSaveNotification(NotificationInfoVo vo, String idScreen, CurrentUserInformation user, String nextNotificationDateMax, String notificationRenewalPeriod) throws SystemException, BusinessException;
    
    /**
     * Get data form CSV
     * @param file
     * @return
     */
   List<NotificationInfoImportCsvVo> getDataFormCsv(byte[] file);

    /**
     * saveAcceptanceNotification
     * @param apartmentId String
     * @param registrationForm NotificationRegistrationForm
     * @param acceptanceForm NotificationAcceptanceForm
     * @param userLoggedInInfo CurrentUserInformation
     * @return AcceptNo
     */
    String saveAcceptanceNotification(String apartmentId,
                                      NotificationRegistrationForm registrationForm,
                                      NotificationAcceptanceForm acceptanceForm,
                                      CurrentUserInformation userLoggedInInfo) throws BusinessException, SystemException;
    
    /**
     * Method export CSV file in screen Registration Notification
     * @param form
     * @param ops
     */
    boolean exportCsv (NotificationExportForm form, OutputStream ops) throws IOException;
}
