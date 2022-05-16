/*
 * @(#) SystemSettingLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;

import java.util.List;

public interface SystemSettingLogic {
    TBM004Entity getSystemSettingByKey(String key);

    List<TBM004Entity> findAllNotDeleted();
}
