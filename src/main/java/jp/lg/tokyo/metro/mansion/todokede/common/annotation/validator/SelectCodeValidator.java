/*
 * @(#) SelectCodeValidator.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;

public class SelectCodeValidator implements ConstraintValidator<SelectCode, String> {

    String[] codes = {};

    @Override
    public void initialize(SelectCode constraint) {
        codes = constraint.code();
    }

    /**
     * returns true if the input is one of the Code values
     * @param input String
     * @param context ConstraintValidatorContext
     * @return boolean 
     **/
    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (null == input) {
            return false;
        }
        /* get list code form the Xml file*/
        for (String s : codes) {
            List<Code> codeList = CodeUtil.getList(s);
            for (Code code : codeList) {
                String check = code.getValue();
                if (input.equals(check)) {
                    return true;
                }
            }
        }
        return false;
    }

}
