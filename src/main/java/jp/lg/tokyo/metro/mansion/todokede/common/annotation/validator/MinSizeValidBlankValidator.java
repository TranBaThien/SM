/*
 * @(#) MinSizeValidBlankValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MinSizeValidBlank;

public class MinSizeValidBlankValidator implements ConstraintValidator<MinSizeValidBlank, String> {
    private int val = 0;

    @Override
    public void initialize(MinSizeValidBlank constraintAnnotation) {
        val = constraintAnnotation.val();
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        /* return false if length input < Min length */
        return input.length() >= val;
    }

}
