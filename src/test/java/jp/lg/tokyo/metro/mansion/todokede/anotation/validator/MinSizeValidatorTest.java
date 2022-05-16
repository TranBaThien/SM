/*
 * @(#) MinSizeValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.MinSize;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.MinSizeValidator;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class MinSizeValidatorTest {
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    MinSize minSize;
    
    @InjectMocks
    MinSizeValidator minSizeValidator;
    
    private final int minSizeVal = 9;
    
    /**
     * 案件MinSizeValidator／チェックリストID：UT- MinSizeValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Have Maxlength Greater Than MinSize
     */
    @Test
    public void testIsValidWhenInputHaveMinlengthGreaterThanMinSize() {
        // prepare data
        String input = "1234567890";
        Mockito.when(minSize.val()).thenReturn(minSizeVal);
        // execute       
        minSizeValidator.initialize(minSize);       
        boolean result = minSizeValidator.isValid(input, context);
       
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件MinSizeValidator／チェックリストID：UT- MinSizeValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Have Maxlength Smaller Than MinSize
     */
    @Test
    public void testIsValidWhenInputHaveMinlengthSmallerThanMinSize() {
        // prepare data
        String input = "12345678";
        Mockito.when(minSize.val()).thenReturn(minSizeVal);
        // execute
        minSizeValidator.initialize(minSize);
        boolean result = minSizeValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件MinSizeValidator／チェックリストID：UT- MinSizeValidator-003 ／区分：E／チェック内容：Test Is Valid When Input Is Null
     */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;
        Mockito.when(minSize.val()).thenReturn(minSizeVal);
        
        // execute
        minSizeValidator.initialize(minSize);
        boolean result = minSizeValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
