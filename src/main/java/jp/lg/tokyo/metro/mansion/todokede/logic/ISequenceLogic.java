/*
 * @(#)ISequenceLogic.java 2019/11/22
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/11/22
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;

public interface ISequenceLogic {
	/**
	 * Metho get info sequence by name Table
	 * @param tblId
	 * @param clName
	 * @return
	 */
	public TBS001Entity findSequenceForTbl(String tblId, String clName);
	
	/**
	 * Save change data
	 * @param entity
	 * @return
	 */
	public boolean save(TBS001Entity entity);
	
}
