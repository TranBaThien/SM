/*
 * @(#) FullSizeCharacterValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FullSizeCharacter;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;

public class FullSizeCharacterValidator implements ConstraintValidator<FullSizeCharacter, String> {

    @Override
    public void initialize(FullSizeCharacter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        /* returns true if all input characters are full size */
        if (StringUtils.isBlank(input)) {
            return true;
        }
        return CommonUtils.isFullSizeString(input);
    }

}
