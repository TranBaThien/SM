/*
 * @(#) HaftSizeNumber.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author ptluan
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.HaftSizeNumberValidator;

@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Documented
@Constraint(validatedBy = HaftSizeNumberValidator.class)
public @interface HaftSizeNumber {
    
    String message() default "{ }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
