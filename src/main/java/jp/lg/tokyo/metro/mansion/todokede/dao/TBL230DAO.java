/*
 * @(#)TBL230DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/04
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL230Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBL230DAO extends JpaRepository<TBL230Entity, String> {

    /**
    * Get survey by inveseNo
    * @screen GEA0110
    * @param inveseNo
    * @return TBL230Entity
    */
    @Query("SELECT tb FROM TBL230Entity tb WHERE tb.investigationNo = :investigationNo and tb.deleteFlag='0'")
    TBL230Entity getSurveyById(@Param("investigationNo") String inveseNo);

    /**
     * Get Surveys By Notification No.
     * @screen GJA0120
     * @param apartmentId
     * @return TBL230Entity
     * @author nndo
     */
    @Query(value = "SELECT * FROM tbl230 tb WHERE tb.APARTMENT_ID = :apartmentId AND tb.DELETE_FLAG = '0'", nativeQuery = true)
    List<TBL230Entity> getSurveysByApartmentId(@Param("apartmentId") String apartmentId);

}
