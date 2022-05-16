/*
 * @(#) MaxSizeValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MaxSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.MaxSizeValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class MaxSizeValidatorTest {
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    MaxSize maxSize;
    
    @InjectMocks
    MaxSizeValidator maxSizeValidator;
    
    private static final int maxSizeVal = 9;
    
    /**
     * 案件MaxSizeValidator／チェックリストID：UT- MaxSizeValidator-001 ／区分：E／チェック内容：Test Is Valid When Input Have Maxlength Greater Than MaxSize
     */
    @Test
    public void testIsValidWhenInputHaveMaxlengthGreaterThanMaxSize() {
        // prepare data
        String input = "1234567890";
        Mockito.when(maxSize.val()).thenReturn(maxSizeVal);
        // execute       
                
        boolean result = maxSizeValidator.isValid(input, context);
        maxSizeValidator.initialize(maxSize);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件MaxSizeValidator／チェックリストID：UT- MaxSizeValidator-002 ／区分：N／チェック内容：Test Is Valid When Input Have Maxlength Smaller Than MaxSize
     */
    @Test
    public void testIsValidWhenInputHaveMaxlengthSmallerThanMaxSize() {
        // prepare data
        String input = "12345678";
        Mockito.when(maxSize.val()).thenReturn(maxSizeVal);
        // execute
        maxSizeValidator.initialize(maxSize);
        boolean result = maxSizeValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件MaxSizeValidator／チェックリストID：UT- MaxSizeValidator-003 ／区分：N／チェック内容：Test Is Valid When Input Is Null
     */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;
        Mockito.when(maxSize.val()).thenReturn(maxSizeVal);
        
        // execute
        maxSizeValidator.initialize(maxSize);
        boolean result = maxSizeValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
}
