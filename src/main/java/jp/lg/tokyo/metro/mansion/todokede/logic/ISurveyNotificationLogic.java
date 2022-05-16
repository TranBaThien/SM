/*
 * @(#)ISurveyNotificationLogic.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/30
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.logic;

import javax.validation.Valid;

import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import jp.lg.tokyo.metro.mansion.todokede.form.SurveyForm;
import jp.lg.tokyo.metro.mansion.todokede.vo.SurveyVo;

public interface ISurveyNotificationLogic {

    void getSurveyNotificationInfo(SurveyVo vo, String cityCode) throws BusinessException;

    boolean restoreSurveyNotificationInfo(SurveyForm form) throws BusinessException;

    void saveSurvey(@Valid SurveyForm form) throws BusinessException;

    void saveTemporary(@Valid SurveyForm form) throws BusinessException;

    void checkTemporaryData(SurveyVo vo) throws BusinessException;
}
