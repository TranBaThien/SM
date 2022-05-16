/*
 * @(#) TBL105DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/27
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBL105Entity;

/**
 * @author PVHung3
 *
 */
@Repository
public interface TBL105DAO extends JpaRepository<TBL105Entity, String> {

}
