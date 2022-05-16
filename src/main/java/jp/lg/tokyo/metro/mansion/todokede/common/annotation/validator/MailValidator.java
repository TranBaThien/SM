/*
 * @(#) MailValidator.java
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Mail;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CheckUtil;

public class MailValidator implements ConstraintValidator<Mail, String> {


    @Override
    public void initialize(Mail constraint) {
    }

    /**
     * returns true if the input is one of the Code values
     * @param input String
     * @param context ConstraintValidatorContext
     * @return boolean 
     **/
    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(input)) {
            return true;
        } else {
            return CheckUtil.isMailAddress(input);
        }
        
    }

}
