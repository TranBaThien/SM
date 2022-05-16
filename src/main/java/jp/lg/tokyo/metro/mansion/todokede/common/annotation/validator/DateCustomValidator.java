/*
 * @(#) DateCustomValidator.java
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
import org.apache.commons.validator.GenericValidator;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;

public class DateCustomValidator implements ConstraintValidator<DateCustom, String> {

    String pattern = "";

    @Override
    public void initialize(DateCustom constraint) {
        pattern = constraint.pattern();
    }

    /**
     * returns true if the input is one of the Code values
     * @param input String
     * @param context ConstraintValidatorContext
     * @return boolean 
     **/
    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (pattern == null || StringUtils.isBlank(input)) {
            return true;
        }
        return GenericValidator.isDate(input, pattern, true);
    }

}
