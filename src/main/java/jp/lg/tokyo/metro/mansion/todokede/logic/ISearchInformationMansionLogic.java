/*
 * @(#) ISearchInformationMansionLogic.java
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
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.commons.collections4.MultiValuedMap;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchInformationMansionForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.PagingVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ResultSearchVo;

public interface ISearchInformationMansionLogic {

    PagingVo<ResultSearchVo> getListInformationMansion(SearchInformationMansionForm form)
            throws BusinessException, ParseException, SQLException;

    void outputCsv(String[]apartmentIds, OutputStream outputStream) throws BusinessException, IOException;

    void outputCsvInformationMansion(String[] apartmentIds, OutputStream outputStream)
            throws BusinessException;

    boolean checkNewestNotification(String aparmentId, MultiValuedMap<String, String> mapNewestNotification) throws BusinessException;

}
