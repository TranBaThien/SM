/*
 * @(#) SystemSettingLogicImpl.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/06
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.dao.TBM004DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.SystemSettingLogic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemSettingLogicImpl implements SystemSettingLogic {

    private final TBM004DAO tbm004DAO;

    public SystemSettingLogicImpl(TBM004DAO tbm004DAO) {
        this.tbm004DAO = tbm004DAO;
    }

    /**
     * キーでシステム設定を取得する
     * Get system setting by key
     * @param key
     * @return
     */
    @Override
    public TBM004Entity getSystemSettingByKey(String key) {
        return tbm004DAO.findBySetTargetNameEng(key);
    }

    /**
     * find All Not Deleted
     * @return List of TBM004Entity
     */
    @Override
    public List<TBM004Entity> findAllNotDeleted() {
        return tbm004DAO.findAllNotDeleted();
    }
}
