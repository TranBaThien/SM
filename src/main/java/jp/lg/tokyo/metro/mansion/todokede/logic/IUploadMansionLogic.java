/*
 * @(#) IUploadMansionLogic.java
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

import javax.transaction.SystemException;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.MansionInfoUploadForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.ZAA0150ErrorVo;

/**
 * @author nbvhoang
 *
 */
public interface IUploadMansionLogic {
    List<ZAA0150ErrorVo> uploadCSV(List<MansionInfoUploadForm> lstMansion, String fileCountMax,
            List<String> lengthOfRowList) throws SystemException;

    List<MansionInfoUploadForm> getDataFormCsv(byte[] file, List<String> lengthOfRowList) throws BusinessException;
}
