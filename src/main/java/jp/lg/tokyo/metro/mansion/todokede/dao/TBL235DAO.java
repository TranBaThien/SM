/*
 * @(#) TBL235DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL235Entity;

/**
 * @author PTLuan
 *
 */
@Repository
public interface TBL235DAO extends JpaRepository<TBL235Entity, String> {

    /**
     * get survey by apartmentId and temporary
     * @Screen GGA0110
     * @param apartmentId String
     * @param tempKbn String
     * @return TBL235Entity
     * @author PTLuan
     */
    @Query("SELECT tb "
            + "FROM TBL235Entity tb "
            + "WHERE tb.apartmentId = :apartmentId "
            + "and tb.deleteFlag = '0' "
            + "and tb.tempKbn = :tempKbn ")
    TBL235Entity getSurveyByApartmentIdAndTempKbn(@Param("apartmentId") String apartmentId, @Param("tempKbn") String tempKbn);

    /**
     * get TempNo by apartmentId
     * @Screen GGA0110
     * @param apartmentId String
     * @return list TempNo
     * @author PTLuan
     */
    @Query("SELECT tb.tempNo "
            + "FROM TBL235Entity tb "
            + "WHERE tb.apartmentId = :apartmentId "
            + "and tb.deleteFlag = '0' ")
    List<String> getTempNoByApartmentId(@Param("apartmentId") String apartmentId);

    /**
     * get TempNo By ApartmentId And TempKBN
     * @Screen GGA0110
     * @param apartmentId String
     * @param tempKbn String
     * @return list TempNo
     * @author PTLuan
     */
    @Query("SELECT tb.tempNo "
            + "FROM TBL235Entity tb "
            + "WHERE tb.apartmentId = :apartmentId "
            + "and tb.deleteFlag = '0' "
            + "and tb.tempKbn = :tempKbn ")
    List<String> getTempNoByApartmentIdAndTempKBN(@Param("apartmentId") String apartmentId, @Param("tempKbn") String tempKbn);
}
