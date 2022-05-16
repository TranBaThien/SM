/*
 * @(#) PhoneNumberValidatorTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: Dec 15, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.anotation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.PhoneNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.PhoneNumberValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class PhoneNumberValidatorTest {
    @Mock
    PhoneNumber phoneNumber;
    
    @Mock
    ConstraintValidatorContext context;
    
    @InjectMocks
    PhoneNumberValidator phoneNumberValidator;
        
    /**
     * 案件PhoneNumberValidator／チェックリストID：UT- PhoneNumberValidator-001 ／区分：E／チェック内容：Test Is Valid When Input Is Not Phone Number Format
    */
    @Test
    public void testIsValidWhenInputIsNotPhoneNumberFormat() {
        // prepare data
        String input = "test123456";
        // execute
        phoneNumberValidator.initialize(phoneNumber);
        boolean result = phoneNumberValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件PhoneNumberValidator／チェックリストID：UT- PhoneNumberValidator-002 ／区分：N／チェック内容：Test Is Valid When Input Is Null
    */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;
        // execute
        phoneNumberValidator.initialize(phoneNumber);
        boolean result = phoneNumberValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
}
