/*
 * @(#)SequenceLogicImpl.java 2019/11/22
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description: Sequence class create new
 *
 * Create Date: 2019/11/22
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.logic.impl;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.lg.tokyo.metro.mansion.todokede.dao.TBS001DAO;
import jp.lg.tokyo.metro.mansion.todokede.entity.TBS001Entity;
import jp.lg.tokyo.metro.mansion.todokede.logic.ISequenceLogic;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.TBS001;

/**
 * @author PVHung3
 *
 */
@Service
public class SequenceLogicImpl implements ISequenceLogic {
	
	private static final Logger logger = LogManager.getLogger(SequenceLogicImpl.class);

	@Autowired
	private TBS001DAO tBS001DAO;
	
	@Override
	public TBS001Entity findSequenceForTbl(String tblId, String clName) {
		
		return tBS001DAO.findSequenceForTbl(tblId, clName);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean save(TBS001Entity entity) {

        tBS001DAO.save(entity);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1060_I, TBS001, entity.getSequenceNo()));
        return true;
	}

}
