/*
 * @(#) TBL220DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Dec 3, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL220Entity;

/**
 * @author nvlong1
 *
 */
@Repository
public interface TBL220DAO extends JpaRepository<TBL220Entity, Long> {

	/**
     * Get advisory notice by adviceNumber
     * @screen GEA0110, GFA0110
     * @param acceptNo
     * @return TBL220Entity
	 */
	@Query("SELECT tb FROM TBL220Entity tb WHERE tb.adviceNo = :adviceNumber and tb.deleteFlag='0'")
	TBL220Entity getAdvisoryNoticeById(@Param("adviceNumber") String adviceNumber);

}
