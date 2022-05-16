/*
 * @(#) HaftSizeNumberValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HaftSizeNumber;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.HaftSizeNumberValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class HaftSizeNumberValidatorTest {
    
    @InjectMocks
    HaftSizeNumberValidator halfSizeNumberValidator;
    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    HaftSizeNumber halfSizeNumber;
    
    /**
     * 案件HaftSizeNumberValidator／チェックリストID：UT- HaftSizeNumberValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is HalfSize Number
     */
    @Test
    public void testIsValidWhenInputIsHalfSizeNumber( ) {
        // prepare data
        String input = "1234567890";
        
        //execute
        halfSizeNumberValidator.initialize(halfSizeNumber);
        boolean result = halfSizeNumberValidator.isValid(input, context);
        
        //assert Result
        assertTrue(result);
    }
    
    /**
     * 案件HaftSizeNumberValidator／チェックリストID：UT- HaftSizeNumberValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Is Not HalfSize Number
     */
    @Test
    public void testIsValidWhenInputIsNotHalfSizeNumber( ) {
        // prepare data
        String input = "abcdefg";
        
        //execute
        halfSizeNumberValidator.initialize(halfSizeNumber);
        boolean result = halfSizeNumberValidator.isValid(input, context);
        
        //assert Result
        assertFalse(result);
    }
    
    /**
     * 案件HaftSizeNumberValidator／チェックリストID：UT- HaftSizeNumberValidator-003 ／区分：N／チェック内容：Test Is Valid When Input Is Null
     */
    @Test
    public void testIsValidWhenInputIsNull( ) {
        // prepare data
        String input = null;
        
        //execute
        halfSizeNumberValidator.initialize(halfSizeNumber);
        boolean result = halfSizeNumberValidator.isValid(input, context);
        
        //assert Result
        assertTrue(result);
    }
}
