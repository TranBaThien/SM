/*
 * @(#) IApartmentInformationDetailsLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/11/28
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentInformationDetailsVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ApartmentUserInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.MansionInfoVo;

import java.lang.reflect.InvocationTargetException;

public interface IApartmentInformationDetailsLogic {

    ApartmentInformationDetailsVo getMansionInformation(String apartmentId, String notificationNo)
            throws BusinessException, InvocationTargetException, IllegalAccessException;

    String getMansionInfoUpdateTime(String apartmentId);

    ApartmentUserInfoVo getUserInformation(String apartmentId) throws BusinessException, InvocationTargetException, IllegalAccessException;

    void enableLoginID(String apartmentId) throws BusinessException;

    void resumingUse(String apartmentId) throws BusinessException;

    void stopUse(String apartmentId) throws BusinessException;
    

    ApartmentInformationDetailsVo getMansionInformationGJA0120(MansionInfoVo mansionInfoVo) throws BusinessException, IllegalAccessException, InvocationTargetException;
}
