/*
 * @(#) MinSizeValidator.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MinSize;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinSizeValidator implements ConstraintValidator<MinSize, String> {
    private int val = 0;

    @Override
    public void initialize(MinSize constraintAnnotation) {
        val = constraintAnnotation.val();
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(input)) {
            return false;
        }
        /* return false if length input < Min length */
        return input.length() >= val;
    }

}
