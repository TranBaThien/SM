/*
 * @(#) TBL205DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author dlly
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL205Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author DLLy
 */
@Repository
public interface TBL205DAO extends JpaRepository<TBL205Entity, String> {

    /**
     * method get Temporary Data
     *
     * @Screen: MDA0110
     * @param apartmentId String
     * @param status String
     * @param tempKbn String
     * @param isChanged String
     * @return TBL205Entity
     */
    @Query("SELECT tb FROM TBL205Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.acceptStatus = :status AND tb.tempKbn = :tempKbn " 
            + "AND ((:isChanged is NULL AND tb.changeCount = 0) OR (tb.changeCount > 0)) "
            + "AND tb.deleteFlag = '0'")
    TBL205Entity getTemporaryData(
            @Param("apartmentId") String apartmentId, 
            @Param("status") String status, 
            @Param("tempKbn") String tempKbn, 
            @Param("isChanged") String isChanged);

    /**
     * count By ApartmentId And Status
     *
     * @Screen: MDA0110
     * @param apartmentId String
     * @param status String
     * @return TBL205Entity
     */
    @Query("SELECT COUNT (tb.id.tempNo) FROM TBL205Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.acceptStatus = :status "
            + "AND tb.deleteFlag = '0'")
    int countByApartmentIdAndStatus(@Param("apartmentId") String apartmentId, @Param("status") String status);

    /**
     * count By ApartmentId And Status And TempKbn
     *
     * @Screen: MDA0110
     * @param apartmentId String
     * @param status String
     * @param tempKbn String
     * @return TBL205Entity
     */
    @Query("SELECT COUNT (tb.id.tempNo) FROM TBL205Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.acceptStatus = :status "
            + "AND tb.tempKbn = :tempKbn AND tb.deleteFlag = '0'")
    int countByApartmentIdAndStatusAndTempKbn(
            @Param("apartmentId") String apartmentId,
            @Param("status") String status,
            @Param("tempKbn") String tempKbn);

    /**
     * delete By ApartmentId And Status
     *
     * @Screen: MDA0110
     * @param apartmentId String
     * @param status String
     */
    @Modifying
    @Query("DELETE FROM TBL205Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.acceptStatus = :status AND tb.deleteFlag = '0'")
    void deleteByApartmentIdAndStatus(@Param("apartmentId") String apartmentId, @Param("status") String status);

    /**
     * delete By ApartmentId And Status And TempKbn
     *
     * @Screen: MDA0110
     * @param apartmentId String
     * @param status String
     * @param tempKbn String
     */
    @Modifying
    @Query("DELETE FROM TBL205Entity tb WHERE tb.id.apartmentId = :apartmentId AND tb.acceptStatus = :status AND tb.tempKbn = :tempKbn "
            + "AND tb.deleteFlag = '0'")
    void deleteByApartmentIdAndStatusAndTempKbn(
            @Param("apartmentId") String apartmentId,
            @Param("status") String status,
            @Param("tempKbn") String tempKbn);

}
