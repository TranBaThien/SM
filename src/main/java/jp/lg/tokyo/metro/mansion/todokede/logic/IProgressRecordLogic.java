/*
 * @(#) IProgressRecordLogic.java 2019/12/03
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 *
 * Create Date: 2019/12/03
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecorInfoWrapperVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ProgressRecordDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.StatusInformationVo;

public interface IProgressRecordLogic {

    /**
    * Get status information from table TBL100, TBL200 and TBL 240 by apartmentId, notificationNo
    * @param apartmentId String
    * @return StatusInformationVo
    * @throws Business Exception
    * @author pdquang
    */
    StatusInformationVo getStatusInformation(String apartmentId) throws BusinessException;
   
    /**
    * Get progressRecord from table TBL300 by apartmentId
    * @param apartmentId String
    * @return ProgressRecordDetailsVo
    * @throws Business Exception
    * @author pdquang
    */
    List<ProgressRecordDetailsVo> getListProgressRecordDetail(String apartmentId) throws BusinessException;

    /**
    * Get progress record informaton for screen MEA0110
    * @return listrogressRecordInfoVo
    * @author tqvu1
    */
    ProgressRecorInfoWrapperVo getProgressRecordInfoForMEA0110(String apartmentId);

}
