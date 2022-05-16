/*
 * @(#) TBL120DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PTLuan
 * Create Date: 2019/11/27
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL120Entity;

@Repository
public interface TBL120DAO extends JpaRepository<TBL120Entity, String> {

    /**
     * Find Government Staff by loginId.
     * @screen SCA0110, ZAA0110, GAA0110, SBA0110
     * @param loginId String
     * @return {@link Optional} of {@link TBL120Entity}
     */
    @Transactional(readOnly = true)
    @Query("select u from TBL120Entity u where u.loginId = :loginId and u.deleteFlag = '0'")
    Optional<TBL120Entity> findByLoginId(@Param("loginId") String loginId);

    /**
     * Find Government Staff by userId.
     * @screen ABB0110
     * @param userId String
     * @return TBL120Entity
     */
    @Query("SELECT tb FROM TBL120Entity tb WHERE tb.userId = :userId AND tb.deleteFlag = '0'")
    TBL120Entity getGovernmentStaffInfo(@Param("userId") String userId);

    /**
     * Get government staff info (GAA0110).
     * @screen SCA0110
     * @param loginId String
     * @param mailAddress String
     * @param availability String
     * @return TBL120Entity
     * @author tqvu1
     */
    @Query(""
            + "SELECT tbl120"
            + " FROM TBL120Entity tbl120"
            + " WHERE tbl120.loginId = :loginId" // ユーザログイン（区市町村）TBL120.ログインID=画面入力.ログインID
            + " AND tbl120.mailAddress = :mailAddress" // ユーザログイン（区市町村）TBL120.メールアドレス=画面入力.メールアドレス
            + " AND tbl120.availability = :availability" // ユーザログイン（区市町村）TBL120.利用可否=利用可能（CD024）
            + " AND tbl120.deleteFlag = '0'") // ユーザログイン（区市町村）TBL120.削除フラグ=0（未削除）
    TBL120Entity getGovernmentStaffUserLoginInfo(
            @Param("loginId") String loginId,
            @Param("mailAddress") String mailAddress,
            @Param("availability") String availability);

    /**
     * Screen ABB0110
     * Get government staff info.
     * @param loginId String
     * @return {@link List} of {@link TBL120Entity}
     * @author PTluan
     */
    @Query(""
            + "SELECT tbl120"
            + " FROM TBL120Entity tbl120"
            + " WHERE tbl120.loginId like :loginId"
            + " AND tbl120.deleteFlag = '0'")
    List<TBL120Entity> getGovernmentStaffInfoByLikeLoginId(@Param("loginId") String loginId);

}
