/*
 * @(#) NumberWithDecimalPointLimitValidatorTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: Dec 15, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.anotation.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.NumberWithDecimalPointLimit;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.NumberWithDecimalPointLimitValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class NumberWithDecimalPointLimitValidatorTest {
    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    NumberWithDecimalPointLimit numberWithDecimalPointLimit;
    
    @InjectMocks
    NumberWithDecimalPointLimitValidator numberWithDecimalPointLimitValidator;
    
    private final int numberWithDecimalPointLimitVal = 3;
    /**
     * 案件NumberWithDecimalPointLimitValidator／チェックリストID：UT- NumberWithDecimalPointLimitValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Number With Decimal Point Limit
    */
    @Test
    public void testIsValidWhenInputIsNumberWithDecimalPointLimit() {
        // prepare data
        String input = "1000.000";
        Mockito.when(numberWithDecimalPointLimit.val()).thenReturn(numberWithDecimalPointLimitVal);
        // execute       
        numberWithDecimalPointLimitValidator.initialize(numberWithDecimalPointLimit);       
        boolean result = numberWithDecimalPointLimitValidator.isValid(input, context);
       
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件NumberWithDecimalPointLimitValidator／チェックリストID：UT- NumberWithDecimalPointLimitValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Is Not Number With Decimal Point Limit
    */
    @Test
    public void testIsValidWhenInputIsNotNumberWithDecimalPointLimit() {
        // prepare data
        String input = "12345678";
        Mockito.when(numberWithDecimalPointLimit.val()).thenReturn(numberWithDecimalPointLimitVal);
        // execute
        numberWithDecimalPointLimitValidator.initialize(numberWithDecimalPointLimit);
        boolean result = numberWithDecimalPointLimitValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件NumberWithDecimalPointLimitValidator／チェックリストID：UT- NumberWithDecimalPointLimitValidator-003 ／区分：N／チェック内容：Test Is Valid When Input Is Null
    */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;
        Mockito.when(numberWithDecimalPointLimit.val()).thenReturn(numberWithDecimalPointLimitVal);
        
        // execute
        numberWithDecimalPointLimitValidator.initialize(numberWithDecimalPointLimit);
        boolean result = numberWithDecimalPointLimitValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
}
