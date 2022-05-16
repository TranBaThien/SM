/*
 * @(#) NumberWithDecimalPointValidatorTest.java 
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
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.NumberWithDecimalPoint;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.NumberWithDecimalPointValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class NumberWithDecimalPointValidatorTest {
    
    @Mock
    NumberWithDecimalPoint numberWithDecimalPoint;
    
    @Mock
    ConstraintValidatorContext context;
    
    @InjectMocks
    NumberWithDecimalPointValidator numberWithDecimalPointValidator;
    
    /**
     * 案件NumberWithDecimalPointValidator／チェックリストID：UT- NumberWithDecimalPointValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Number With Decimal Point 
    */
    @Test
    public void testIsValidWhenInputIsNumberWithDecimalPoint() {
        // prepare data
        String input = "1000.000";
   
        // execute       
        numberWithDecimalPointValidator.initialize(numberWithDecimalPoint);       
        boolean result = numberWithDecimalPointValidator.isValid(input, context);
       
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件NumberWithDecimalPointValidator／チェックリストID：UT- NumberWithDecimalPointValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Is Not Number With Decimal Point 
    */
    @Test
    public void testIsValidWhenInputIsNotNumberWithDecimalPointt() {
        // prepare data
        String input = "test123456";
        // execute
        numberWithDecimalPointValidator.initialize(numberWithDecimalPoint);
        boolean result = numberWithDecimalPointValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
}
