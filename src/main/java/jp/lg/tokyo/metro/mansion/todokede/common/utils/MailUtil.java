/*
 * @(#) MailUtil.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2019/12/04
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.vo.BaseMailVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.EMailInfoVo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML030Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML035Vo;
import jp.lg.tokyo.metro.mansion.todokede.vo.ML040Vo;

public class MailUtil {

    public static EMailInfoVo convertToEmailInfo(BaseMailVo baseMailVo) {

        List<String> toList = new ArrayList<String>();
        toList.add(baseMailVo.getContactMailAddress());

        EMailInfoVo vo = new EMailInfoVo();
        vo.setTo(toList.stream().toArray(String[]::new));
        vo.setSubject(baseMailVo.getMailSubject());
        vo.setFrom(baseMailVo.getMailSendFrom());
        vo.setReplyTo(baseMailVo.getMailReplyTo());

        return vo;
    }

    public static Map<String, Object> getBaseParameters(BaseMailVo mailVo) {
        Map<String, Object> model = new HashMap<>();
        model.put(CommonConstants.ML_CITY_NAME, mailVo.getCityName());
        model.put(CommonConstants.ML_WINDOW_BELONG_TO, mailVo.getWindowBelong());
        model.put(CommonConstants.ML_WINDOW_TEL_NO, mailVo.getWindowTelNo());
        model.put(CommonConstants.ML_WINDOW_FAX_NO, mailVo.getWindowFaxNo());
        model.put(CommonConstants.ML_WINDOW_MAIL_ADDRESS, mailVo.getWindowMailAddress());
        return model;
    }

    /**
     * ML030メールパラメーターを生成する
     * Generate ML030 mail parameters
     * @param mailVo ML030Vo
     * @return Mail parameters as Map<String, Object>
     */
    public static Map<String, Object> getML030Parameters(ML030Vo mailVo) {
        Map<String, Object> model = getBaseParameters(mailVo);
        model.put(CommonConstants.ML_APARTMENT_NAME, mailVo.getApartmentName());
        model.put(CommonConstants.ML_CONTACT_NAME, mailVo.getContactName());
        model.put(CommonConstants.ML_PASSWORD_PERIOD, mailVo.getPasswordPeriod());
        model.put(CommonConstants.ML_LOGIN_URL, mailVo.getLoginUrl());
        model.put(CommonConstants.ML_PASSWORD, mailVo.getPassword());
        return model;
    }

    /**
     * ML035メールパラメーターを生成する
     * Generate ML035 mail parameters
     * @param mailVo ML035Vo
     * @return Mail parameters as Map<String, Object>
     */
    public static Map<String, Object> getML035Parameters(ML035Vo mailVo) {
        Map<String, Object> model = getBaseParameters(mailVo);
        model.put(CommonConstants.ML_CONTACT_NAME, mailVo.getContactName());
        model.put(CommonConstants.ML_PASSWORD_PERIOD, mailVo.getPasswordPeriod());
        model.put(CommonConstants.ML_PASSWORD, mailVo.getPassword());
        return model;
    }

    /**
     * ML040メールパラメーターを生成する
     * Generate ML040 mail parameters
     * @param mailVo ML040Vo
     * @return Mail parameters as Map<String, Object>
     */
    public static Map<String, Object> getML040Parameters(ML040Vo mailVo) {
        Map<String, Object> model = getBaseParameters(mailVo);
        model.put(CommonConstants.ML_APARTMENT_NAME, mailVo.getApartmentName());
        model.put(CommonConstants.ML_CONTACT_NAME, mailVo.getContactName());
        model.put(CommonConstants.ML_LOGIN_URL, mailVo.getLoginUrl());
        return model;
    }
}
