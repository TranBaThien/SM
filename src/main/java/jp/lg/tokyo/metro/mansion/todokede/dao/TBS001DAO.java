/*
 * @(#)TBS001DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/22
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBS001DAO extends JpaRepository<TBS001Entity, Long>{

	/**
	 * @Screen: Get sequence common
	 * @param tblId
	 * @param clName
	 * @return
	 */
	@Query("SELECT u FROM TBS001Entity u WHERE u.tblId = :tblId AND u.columnName =:clName AND u.deleteFlag='0'")
	public TBS001Entity findSequenceForTbl(@Param("tblId") String tblId, @Param("clName") String clName);
}
