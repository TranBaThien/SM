/*
 * @(#) INotificationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hntvy
 * Create Date: Dec 4, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.vo.NotificationVo;

/**
 * @author hntvy
 *
 */
public interface INotificationLogic {

	public NotificationVo getApartmentNotification(String apartmentId);
}
