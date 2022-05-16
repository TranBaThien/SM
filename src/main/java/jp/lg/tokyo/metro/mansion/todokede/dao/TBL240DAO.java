/*
 * @(#)TBL240DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * @author CMC Global
 * Create Date: 2019/12/02
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL240Entity;

import java.util.List;

/**
 * @author PDQuang
 *
 */
@Repository
public interface TBL240DAO extends JpaRepository<TBL240Entity, String> {

	/**
	 * Get Supervised Notice By Apartment Id.
	 * 
	 * @screen GJA0120, MDA0110
	 * @param apartmentId
	 * @return TBL240Entity
	 * @author nndo
	 */
	@Query(value = "SELECT * FROM tbl240 tb WHERE tb.APARTMENT_ID = :apartmentId AND tb.NOTIFICATION_NO IS NULL AND tb.DELETE_FLAG = '0'", nativeQuery = true)
	List<TBL240Entity> getSupervisedNoticeByApartmentId(@Param("apartmentId") String apartmentId);

	/**
	 * get Supervised Notice By supervisedNoticeNo
	 * 
	 * @screen GEA0110
	 * @param supervisedNoticeNo
	 * @return TBL240Entity
	 */
	@Query("SELECT tb FROM TBL240Entity tb WHERE tb.supervisedNoticeNo = :supervisedNoticeNo and tb.deleteFlag='0'")
	TBL240Entity getSupervisedNoticeBySupervisedNoticeNo(@Param("supervisedNoticeNo") String supervisedNoticeNo);

	/**
	 * get Supervised Notice By ApartmentId
	 * 
	 * @screen GIA0110
	 * @param apartmentId
	 * @return TBL240Entity
	 */
	@Query(value = "SELECT * FROM tbl240 tb WHERE tb.APARTMENT_ID = :apartmentId AND tb.NOTIFICATION_NO IS NULL AND tb.DELETE_FLAG = '0' ORDER BY tb.SUPERVISED_NOTICE_COUNT DESC LIMIT 1", nativeQuery = true)
	TBL240Entity getSupervisedNoticeByApartmentIdAndNotificationNoIsNotNull(@Param("apartmentId") String apartmentId);

	/**
	 * get Supervised Notice By ApartmentId And NotificationNo is not null
	 * 
	 * @screen GEA0110
	 * @param apartmentId
	 * @return List<TBL240Entity>
	 */
	@Query(value = "SELECT * FROM tbl240 tb WHERE tb.APARTMENT_ID = :apartmentId AND tb.NOTIFICATION_NO IS NOT NULL AND tb.DELETE_FLAG = '0'", nativeQuery = true)
	List<TBL240Entity> getSupervisedNoticeByApartmentIdAndNotificationNo(@Param("apartmentId") String apartmentId);
}
