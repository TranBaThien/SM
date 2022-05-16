/*
 * @(#) TBM003DAO.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nvlong1
 * Create Date: Nov 28, 2019
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBM003Entity;

/**
 * @author nvlong1
 *
 */
@Repository
public interface TBM003DAO extends JpaRepository<TBM003Entity, String>{

	/**
	 * Method get Advice Template Content
	 * 
	 * @return List<TBM003Entity>
	 * @Screen GFA0110
	 */
	@Query("SELECT tb FROM TBM003Entity tb WHERE tb.deleteFlag='0'")
	public List<TBM003Entity> getAdviceTemplateContent();
	
}
