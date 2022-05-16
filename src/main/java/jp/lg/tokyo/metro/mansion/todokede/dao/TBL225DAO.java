/*
 * @(#) TBL225DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Dec 6, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL225Entity;

/**
 * @author nvlong1
 *
 */
@Repository
public interface TBL225DAO extends JpaRepository<TBL225Entity, String> {

    /**
     * Method get advice notification temporary storage
     * 
     * @param newestNotificationNo
     * @param temporaryStorage
     * @Screen GFA0110
     * @return TBL225Entity
     */
    @Query("SELECT tb FROM TBL225Entity tb WHERE tb.notificationNo = :newestNotificationNo and tb.tempKbn = :temporaryStorage and tb.deleteFlag='0'")
    TBL225Entity getAdviceNotificationTemporaryStorage(@Param("newestNotificationNo") String newestNotificationNo, @Param("temporaryStorage") String temporaryStorage);

    /**
     * Method delete by notificationNo and tempKbn
     * 
     * @param newestNotificationNo
     * @param tempStorageByCodeUser
     * @Screen GFA0110
     */
    @Modifying
    @Query("DELETE FROM TBL225Entity tb WHERE tb.notificationNo = :newestNotificationNo and tb.tempKbn = :tempStorageByCodeUser and tb.deleteFlag='0'")
    void deleteTemporarilySavedData(@Param("newestNotificationNo") String newestNotificationNo, @Param("tempStorageByCodeUser") String tempStorageByCodeUser);

    /**
     * find temporary data By NotificationNo And Delete Flag False
     * @screen MDA0110
     * @param notificationNo
     * @return TBL225Entity
     */
    @Query("SELECT tb FROM TBL225Entity tb WHERE tb.notificationNo = :notificationNo AND tb.deleteFlag = '0'")
    List<TBL225Entity> findByNotificationNoAndDeleteFlagFalse(@Param("notificationNo") String notificationNo);
    
    /**
     * get TempNo by notificationNo
     * @Screen GFA0110
     * @param notificationNo String
     * @return list TempNo
     * @author NVLong
     */
    @Query("SELECT tb.tempNo FROM TBL225Entity tb WHERE tb.notificationNo = :notificationNo and tb.deleteFlag = '0'")
    List<String> getTempNoByNotificationNo(@Param("notificationNo") String notificationNo);
}
