/*
 * @(#) TBL300DAO.java
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

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL300Entity;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBL300DAO extends JpaRepository<TBL300Entity, String> {

	/**
	 * Get progress record
	 * @Screen: GEB0110 
	 * @param progressRecordNo
	 * @return
	 */
	@Query("SELECT tb FROM TBL300Entity tb WHERE tb.progressRecordNo = :progressRecordNo AND tb.deleteFlag='0'")
	public TBL300Entity getProgressRecord(@Param("progressRecordNo") String progressRecordNo);

	/**
	 * Get list progress record by Id
	 * @Screen: GEB0110
	 * @param apartmentId
	 * @return
	 */
	@Query("SELECT tb FROM TBL300Entity tb WHERE tb.apartmentId = :apartmentId AND tb.deleteFlag='0' ORDER BY tb.correspondDate DESC")
	public List<TBL300Entity> getListProgressRecordById(@Param("apartmentId") String apartmentId);

    /**
     * Get list progress record by Date
     * @Screen: MBA0110
     * @param apartmentId String
     * @return
     */
    @Query("SELECT T3 FROM TBL300Entity T3 WHERE T3.apartmentId = :apartmentId AND T3.correspondDate >= :correspondDate "
        + "AND (T3.typeCode = '3' OR T3.typeCode = '4' OR T3.typeCode = '5' OR T3.typeCode = '6' ) "
        + "AND T3.deleteFlag = '0' ORDER BY T3.correspondDate DESC")
    public List<TBL300Entity> getProgressRecordFromDate(@Param("apartmentId") String apartmentId,
         @Param("correspondDate") String correspondDate);

     /**
     * method list progress record by progressRecordNo
     * 
     * @Screen: GEA0110
     * @param progressRecordNos
     * @return List<TBL300Entity>
     */
	@Query("SELECT tb FROM TBL300Entity tb WHERE tb.progressRecordNo IN :progressRecordNos AND tb.deleteFlag='0' ORDER BY tb.correspondDate DESC")
	public List<TBL300Entity> getListProgressRecordByProgressRecordNos(@Param("progressRecordNos") List<String> progressRecordNos);

	/**
	 * @screen GDA0110
	 * @param apartmentId String
	 * @param newestNotificationNo String
	 * @return List<TBL300Entity>
	 */
	@Query("SELECT tb3 " +
			"FROM TBL300Entity tb3  " +
			"WHERE tb3.apartmentId = :apartmentId AND tb3.relatedNumber = :newestNotificationNo AND tb3.deleteFlag='0'")
	List<TBL300Entity> findByByApartmentIdAndRelatedNumber(@Param("apartmentId") String apartmentId,
														   @Param("newestNotificationNo") String newestNotificationNo);

	/**
	 * method get Max date common
	 * 
	 * @Screen: GEB0110
	 * @param apartmentId
	 * @param correspondDate
	 * @return
	 */
	@Query("SELECT T3 FROM TBL300Entity T3 WHERE T3.apartmentId = :apartmentId AND T3.correspondDate > :correspondDate AND T3.deleteFlag = '0' ORDER BY T3.correspondDate ASC")
	public List<TBL300Entity> getProgressRecordMaxDateTime(@Param("apartmentId") String apartmentId, @Param("correspondDate") String correspondDate);
}
