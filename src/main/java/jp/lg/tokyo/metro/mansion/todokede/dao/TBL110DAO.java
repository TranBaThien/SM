/*
 * @(#) TBL110DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/21
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL110Entity;


@Repository
public interface TBL110DAO extends JpaRepository<TBL110Entity, Long>{
	public List<TBL110Entity> findAll();

	/**
	 * Find Management Association by login id.
	 * @screen SCA0110,ZAA0110, MAA0110, SBA0110
	 * @param loginId
	 * @return TBL110Entity
	 */
	@Query("SELECT u FROM TBL110Entity u WHERE loginId=:loginId and u.deleteFlag = '0'")
	public TBL110Entity findByLoginId(@Param("loginId") String loginId);
//
//	public int updateLoginSuccess(@Param("userId") String userId,
//			@Param("loginCount") Integer loginCount,
//			@Param("lastActivityDateTime") LocalDateTime lastActivityDateTime,
//			@Param("lastLoginDateTime") LocalDateTime lastLoginDateTime);

	/**
     * Get Management Association info (MAA0110).
     * @screen SCA0110
     * @param loginId
     * @param mailAddress
     * @param availability
     * @param validityFlag
     * @return TBL110Entity
     * @author tqvu1
     */
    @Query(""
            + "SELECT tbl110"
            + " FROM TBL110Entity tbl110"
            + " WHERE tbl110.loginId = :loginId" // ユーザログイン（管理組合）TBL110.ログインID=画面入力.ログインID
            + " AND tbl110.tbl100.repasswordMailAddress = :mailAddress" // マンション情報TBL100.メールアドレス=画面入力.メールアドレス
            + " AND tbl110.validityFlag = :validityFlag" // ユーザログイン（管理組合）TBL110.有効区分=有効（CD023）
            + " AND tbl110.availability = :availability" // ユーザログイン（管理組合）TBL110.利用可否=利用可能（CD024）
            + " AND tbl110.deleteFlag = '0'" // ユーザログイン（管理組合）TBL110.削除フラグ=0（未削除）
            + " AND tbl110.tbl100.deleteFlag = '0'") // マンション情報TBL100.削除フラグ=0（未削除）
    TBL110Entity getManagementAssociationInfo(
            @Param("loginId") String loginId,
            @Param("mailAddress") String mailAddress,
            @Param("availability") String availability,
            @Param("validityFlag") String validityFlag);


	/**
	 * Get User info by apartment id .
	 * @screen GLA0110, GJA0120
	 * @param apartmentId
	 * @return TBL110Entity
	 * @author nndo
	 */
	@Query(value = "SELECT * FROM tbl110 tb WHERE tb.APARTMENT_ID = :apartmentId AND tb.DELETE_FLAG = '0'", nativeQuery = true)
	public TBL110Entity getUserByApartmentId(@Param("apartmentId") String apartmentId);


}
