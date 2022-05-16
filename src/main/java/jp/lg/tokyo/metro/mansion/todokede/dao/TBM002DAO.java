/*
 * @(#) TBM002DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM002Entity;

/**
 * @author PTLuan
 *
 */
@Repository
public interface TBM002DAO extends JpaRepository<TBM002Entity, String> {

    /**
     * @Screen GGA0110
     * Get window information use for send mail using usedCode.
     * @param usedCode String
     * @return TBM002Entity
     * @author tqvu1
     */
    @Query("SELECT tbm002"
            + " FROM TBM002Entity tbm002"
            + " WHERE tbm002.id.cityCode = :cityCode"
            + " AND tbm002.id.usedCode = :usedCode"
            + " AND tbm002.deleteFlag = '0'")
    TBM002Entity getWindowInformation(
            @Param("cityCode") String cityCode,
            @Param("usedCode") String usedCode);
}
