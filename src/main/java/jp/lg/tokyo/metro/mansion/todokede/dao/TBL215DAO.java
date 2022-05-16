/*
 * @(#) TBL215DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/12/17
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL215Entity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TBL215DAO extends JpaRepository<TBL215Entity, String> {

    /**
     * find TBL215Entity By NotificationNo And TempKbn
     * @screen GDA0110
     * @param notificationNo String
     * @param tempKbn String
     * @return {@link Optional} of {@link TBL215Entity}
     */
    @Query("SELECT tb FROM TBL215Entity tb WHERE tb.notificationNo = :notificationNo AND tb.tempKbn = :tempKbn")
    Optional<TBL215Entity> findByNotificationNoAndTempKbn(@Param("notificationNo") String notificationNo, @Param("tempKbn") String tempKbn);

    /**
     * count TBL215Entity By NotificationNo And TempKbn
     * @screen GDA0110
     * @param newestNotificationNo String
     * @param tempKbn String
     * @return number of TBL215Entity
     */
    @Query("SELECT COUNT (tb.tempNo) FROM TBL215Entity tb WHERE tb.notificationNo = :notificationNo AND tb.tempKbn = :tempKbn")
    int countByNotificationNoAndTempKbn(@Param("notificationNo") String newestNotificationNo, @Param("tempKbn") String tempKbn);

    /**
     * delete physically By NotificationNo
     * @screen GDA0110
     * @param newestNotificationNo String
     */
    @Modifying
    @Query("DELETE FROM TBL215Entity tb WHERE tb.notificationNo = :notificationNo")
    void deleteByNotificationNo(@Param("notificationNo") String newestNotificationNo);

    /**
     * soft delete TBL215Entity
     * @screen GDA0110
     * @param notificationNo String
     * @param tempKbn String
     */
    @Modifying
    @Query("DELETE FROM TBL215Entity tb WHERE tb.notificationNo = :notificationNo AND tb.tempKbn = :tempKbn")
    void deleteByNotificationNoAndTempKbn(@Param("notificationNo") String notificationNo, @Param("tempKbn") String tempKbn);
}
