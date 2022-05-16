/*
 * @(#) ITemporaryNotificationInfoLogic.java
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
import jp.lg.tokyo.metro.mansion.todokede.security.CurrentUserInformation;
import jp.lg.tokyo.metro.mansion.todokede.vo.AcceptanceNotificationVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationInfoVo;

import javax.transaction.SystemException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author DLLy
 *
 */
public interface ITemporaryNotificationInfoLogic {

	/**
	 * Get notification info by notificationNo
	 * @param apartmentId, status, tempKbn, notificationNo
	 * @param isCreated
	 * @return
	 * @throws BusinessException
	 */
	NotificationInfoVo getTemporaryData(String apartmentId, String status, String tempKbn, boolean isCreated) throws SystemException;

    /**
     * Delete temporary saved data
     * @param apartmentId, status
     * @return
     * @throws BusinessException
     */
    void deleteTemporarySavedData(String apartmentId, String status, String tempKbn);

	/**
	 * Save temporary notification info
	 *
	 * @param vo
	 * @param user
	 * @param tempKbn
	 * @param idScreen
	 * @return
	 * @throws BusinessException
	 */
	void deleteAndSaveTemporaryData(NotificationInfoVo vo, CurrentUserInformation user, String tempKbn, String idScreen) throws SystemException, InvocationTargetException, IllegalAccessException;

	/**
	 *
	 * @param apartmentId String
	 * @param acceptanceForm NotificationAcceptanceForm
	 * @param tempKbn String
	 * @param updatedUserInfo CurrentUserInformation
	 * @throws BusinessException when Apartment not found with apartmentId
	 */
	void saveTemporaryAcceptance(String apartmentId,
								 NotificationAcceptanceForm acceptanceForm,
								 String tempKbn,
								 CurrentUserInformation updatedUserInfo) throws BusinessException;

	void deleteAcceptanceTemporaryData(String notificationNo);

	/**
	 * チェックはアクティブな受け入れ復元ボタンです
	 * check is Active Acceptance Restore Button
	 * @param newestNotificationNo String
	 * @param tempKbn String
	 * @return true if temporarily data existed
	 */
	boolean isActiveAcceptanceRestoreButton(String newestNotificationNo, String tempKbn);

	/**
	 * get Acceptance Temporary Data
	 * @param notificationNo String
	 * @param tempKbn String
	 * @return AcceptanceNotificationVo
	 */
	AcceptanceNotificationVo getAcceptanceTemporaryData(String notificationNo, String tempKbn);
}
