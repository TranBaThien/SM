/*
 * @(#) TBL200DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL200Entity;
import jp.lg.tokyo.metro.mansion.todokede.vo.RP010TmpVo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author DLLy
 */
@Repository
public interface TBL200DAO extends JpaRepository<TBL200Entity, String> {

    /**
     * get notification info by notificationNo
     * @screen GGA0110, GEA0110, GFA0110,  GDA0110, MBA0110, MDA0110
     * @param notificationNo String
     * @return TBL200Entity
     */
    @Query("SELECT tb FROM TBL200Entity tb WHERE tb.id.notificationNo = :notificationNo AND tb.deleteFlag='0'")
    TBL200Entity getNotificationInfo(@Param("notificationNo") String notificationNo);

    /**
     * Get Notification By Notification No And Apartment ID .
     * @screen GJA0120
     * @param apartmentId String
     * @param notificationNo String
     * @return TBL200Entity
     * @author nndo
     */
    @Query(value = "SELECT tb FROM TBL200Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.id.notificationNo = :notificationNo "
            + "AND tb.deleteFlag = '0'")
    TBL200Entity getNotificationByNotificationNoAndApartmentID(
            @Param("apartmentId") String apartmentId,
            @Param("notificationNo") String notificationNo);

    /**
     * Get report010 by notificationNo, citycode, deleteFlag form tbl200, tbl210 and deleteFlag
     * @screen GEA0110
     * @param notificationNo String
     * @return RP010TmpVo
     */
    @Query(value = "SELECT new jp.lg.tokyo.metro.mansion.todokede.vo.RP010TmpVo("
            + "tbl200, tbl210.acceptTime, tbl210.acceptUserName, tbl210.remarks, tbm001.cityName) "
            + "FROM TBL200Entity tbl200 "
            + "LEFT JOIN TBL210Entity tbl210 ON (tbl210.notificationNo = tbl200.id.notificationNo AND tbl210.deleteFlag = '0') "
            + "LEFT JOIN TBM001Entity tbm001 ON (tbm001.cityCode = tbl200.cityCode AND tbm001.deleteFlag = '0') "
            + "WHERE tbl200.id.notificationNo = :notificationNo AND tbl200.deleteFlag = '0'")
    RP010TmpVo getDocumentInfoForm(@Param("notificationNo") String notificationNo);

}
