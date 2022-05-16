/*
 * @(#)TBL210DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/02
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL210Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TBL210DAO extends JpaRepository<TBL210Entity, String> {

    /**
     * Get Accept By Notification No.
     * @screen GJA0120
     * @param notificationNo String
     * @return TBL210Entity
     * @author nndo
     */
    @Query(value = "SELECT * FROM tbl210 tb WHERE tb.NOTIFICATION_NO = :notificationNo AND tb.DELETE_FLAG = '0'",nativeQuery = true)
    TBL210Entity getAcceptByNotificationNo(@Param("notificationNo") String notificationNo);

    /**
    * Get Accept By AcceptNo
    * @screen GEA0110, GDA0110
    * @param acceptNo String
    * @return TBL210Entity
    */
    @Query(value = "SELECT tb FROM TBL210Entity tb WHERE tb.acceptNo = :acceptNo AND tb.deleteFlag = '0'")
    public TBL210Entity getAcceptByAcceptNo(@Param("acceptNo") String acceptNo);
    
}
