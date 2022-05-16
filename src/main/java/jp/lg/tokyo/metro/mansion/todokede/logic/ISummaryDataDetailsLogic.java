/*
 * @(#) ISummaryDataDetailsLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.io.IOException;
import java.io.OutputStream;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SummaryDataPagingForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.SummaryDataDetailsVo;

public interface ISummaryDataDetailsLogic {

	PagingVo<SummaryDataDetailsVo> getSummaryDataDetails(SummaryDataPagingForm dataPagingForm);

    void outputCsv(String[] apartmentIds, OutputStream outputStream) throws BusinessException, IOException;


}
