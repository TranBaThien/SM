/*
 * @(#) TBL310DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/24
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBL310DAO extends JpaRepository<TBL310Entity, String> {

	/**
	 * Get progress Record attach file
	 * @Screen: GEB0110
	 * @param progressRecordNo
	 * @return
	 */
	@Query("SELECT new jp.lg.tokyo.metro.mansion.todokede.entity.TBL310Entity(tb.progressRecordAttachNo, tb.fileName, tb.progressRecordNo) FROM TBL310Entity tb WHERE tb.progressRecordNo = :progressRecordNo and tb.deleteFlag='0'")
	public List<TBL310Entity> getProgressRecord(@Param("progressRecordNo") String progressRecordNo);
	
	/**
	 * Get progress record by ID
	 * @Screen: GEB0110
	 * @param progressRecordAttachNo
	 * @return
	 */
	@Query("SELECT tb FROM TBL310Entity tb WHERE tb.progressRecordAttachNo = :progressRecordAttachNo and tb.deleteFlag='0'")
	public TBL310Entity findProgressRecordFileById(@Param("progressRecordAttachNo") String progressRecordAttachNo);
}
