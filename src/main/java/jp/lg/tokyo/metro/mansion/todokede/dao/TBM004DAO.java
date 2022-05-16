/*
 * @(#) TBM004DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 28, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM004Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TBM004DAO extends JpaRepository<TBM004Entity, String> {
    /**
     * find All Setting Not Deleted
     * @screen GAA0110, MAA0110
     * @return List of TBM004Entity
     */
    @Query("SELECT t from TBM004Entity t WHERE t.deleteFlag = '0'")
    List<TBM004Entity> findAllNotDeleted();

    /**
     * @screen GAA0110, MAA0110
     * @param settingName String
     * @return TBM004Entity
     */
    @Query("SELECT t FROM TBM004Entity t WHERE t.setTargetNameEng = :settingName AND t.deleteFlag = '0'")
    TBM004Entity findBySetTargetNameEng(@Param("settingName") String settingName);

}
