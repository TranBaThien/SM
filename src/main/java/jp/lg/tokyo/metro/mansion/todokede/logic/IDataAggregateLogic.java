/*
 * @(#) IDataAggregateLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author LVTrinh1
 * Create Date: 2020/01/15
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import javax.servlet.ServletOutputStream;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateResultVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.DataAggregateVo;

public interface IDataAggregateLogic extends IBaseLogic<Object> {

    /**
             * チェック（相関）
     *
     * @param vo           DataAggregateVo
     * @param errorMessage error message
     * @return
     */
    public boolean isValidCorrelation(DataAggregateVo vo, List<String> errorMessage);

    /**
     * Retrieve the data to be displayed
     *
     * @param vo DataAggregateResultVo
     * @return data
     */
    public List<DataAggregateResultVo> getAggregateRecord(DataAggregateVo vo);

    /**
     * Get label Summary Item
     *
     * @param chkSummaryItem item checked
     * @return list
     */
    public List<String> getLstLblSummaryItem(String[] chkSummaryItem);

    public void outputCsv(List<DataAggregateResultVo> dataAggregationResultVo, List<String> lstLblSummaryItem, ServletOutputStream outputStream) throws BusinessException;

}
