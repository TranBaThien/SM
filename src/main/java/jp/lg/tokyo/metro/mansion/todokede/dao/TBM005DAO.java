/*
 * @(#) TBM005DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/11/29
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM005Entity;

public interface TBM005DAO extends JpaRepository<TBM005Entity, String> {

    /**
     * Get template mail using usedCode.
     * @param usedCode
     * @return TBM005Entity
     * @author tqvu1
     */
    @Query("SELECT tbm005"
            + " FROM TBM005Entity tbm005"
            + " WHERE tbm005.usedCode = :usedCode"
            + " AND tbm005.deleteFlag = '0'")
    TBM005Entity getTemplateMailByUsedCode(@Param("usedCode") String usedCode); 

}
