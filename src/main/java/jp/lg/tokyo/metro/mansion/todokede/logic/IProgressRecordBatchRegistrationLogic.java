/*
 * @(#) IProgressRecordBatchRegistrationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author nbvhoang
 * Create Date: 2019/12/16
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.form.ProgressRecordForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */
public interface IProgressRecordBatchRegistrationLogic {
    List<ZAA0150ErrorVo> uploadCSV(List<ProgressRecordForm> lstProgressForm, String fileCountMax,
            List<String> lengthOfRowList);

    List<ProgressRecordForm> getDataFormCsv(byte[] file, List<String> lengthOfRowList);
}
