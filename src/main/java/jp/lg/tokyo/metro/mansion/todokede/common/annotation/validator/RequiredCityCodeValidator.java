/*
 * @(#) RequiredCityCodeValidator.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author CMC Global
 * Create Date: 2019/12/30
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.GenericValidator;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.RequiredCityCode;

public class RequiredCityCodeValidator implements ConstraintValidator<RequiredCityCode, String> {

	@Override
	public void initialize(RequiredCityCode constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		/* returns true if blank or null */
		return !GenericValidator.isBlankOrNull(value);
	}

}
