/*
 * @(#) SpecialCharactersValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;

public class SpecialCharactersValidator implements ConstraintValidator<SpecialCharacters, String> {

    @Override
    public void initialize(SpecialCharacters constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        /* returns true if all input characters don't contain character special */
        return CommonUtils.isNotSpecialCharacter(value);
    }

}
