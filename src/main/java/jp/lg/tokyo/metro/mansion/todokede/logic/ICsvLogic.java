/*
 * @(#) ICsvLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author pdquang
 * Create Date: Dec 12, 2019
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.io.OutputStream;
import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;

/**
 * @author pdquang
 *
 */
public interface ICsvLogic {
    
    /**
     * Export file csv of progress record details
     * @param outputStream outstream
     * @throws Business Exception 
     */
    void exportCsvProgressRecord(List<String> listProgressRecordNo, OutputStream outputStream) throws BusinessException;
    
}
