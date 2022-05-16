/*
 * @(#) NumberWithDecimalPointLimitValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.NumberWithDecimalPointLimit;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;

public class NumberWithDecimalPointLimitValidator implements ConstraintValidator<NumberWithDecimalPointLimit, String> {

    int val = 0;

    @Override
    public void initialize(NumberWithDecimalPointLimit constraintAnnotation) {
        val = constraintAnnotation.val();
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        /* returns true if number of after */
        return CommonUtils.isDecimalPointLimit(input, val);
    }

}
