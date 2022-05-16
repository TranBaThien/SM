/*
 * @(#) NumberWithDecimalPointValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.NumberWithDecimalPoint;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;

public class NumberWithDecimalPointValidator implements ConstraintValidator<NumberWithDecimalPoint, String> {

    @Override
    public void initialize(NumberWithDecimalPoint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        /* returns true if all input characters are Decimal Point */
        return CommonUtils.isDecimalPoint(input);
    }

}
