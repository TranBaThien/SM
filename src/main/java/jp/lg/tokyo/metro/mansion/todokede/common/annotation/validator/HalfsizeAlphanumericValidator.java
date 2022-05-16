/*
 * @(#) HalfsizeAlphanumericValidator.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.AlphanumbericType;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;

public class HalfsizeAlphanumericValidator implements ConstraintValidator<HalfsizeAlphanumeric, String> {

    AlphanumbericType type;

    @Override
    public void initialize(HalfsizeAlphanumeric constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        if (AlphanumbericType.FOR_MAIL.equals(type)) {
            return CommonUtils.isAlphaNumericForMail(value);
        }
        /* returns true if all input characters are half size Alphanumeric */
        return CommonUtils.isAlphaNumeric(value);
    }

}
