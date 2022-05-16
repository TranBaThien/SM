/*
 * @(#) SpecialCharactersValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SpecialCharacters;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.SpecialCharactersValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class SpecialCharactersValidatorTest {
    
    @Mock
    SpecialCharacters specialCharacters;
    
    @Mock 
    ConstraintValidatorContext context;
    
    @InjectMocks
    SpecialCharactersValidator specialCharactersValidator;
    
    /**
     * 案件SpecialCharactersValidator／チェックリストID：UT- SpecialCharactersValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Not Special Characters
    */
    @Test
    public void testIsValidWhenInputIsNotSpecialCharacters() {
        // prepare data
        String input = "1234567890";
        // execute       
                
        boolean result = specialCharactersValidator.isValid(input, context);
        specialCharactersValidator.initialize(specialCharacters);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件SpecialCharactersValidator／チェックリストID：UT- SpecialCharactersValidator-001 ／区分：E／チェック内容：Test Is Valid When Input Is Special Characters
    */
    @Test
    public void testIsValidWhenInputIsSpecialCharacters() {
        // prepare data
        String input = "$^&&%%";
        // execute
        specialCharactersValidator.initialize(specialCharacters);
        boolean result = specialCharactersValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件SpecialCharactersValidator／チェックリストID：UT- SpecialCharactersValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Null
    */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;

        
        // execute
        specialCharactersValidator.initialize(specialCharacters);
        boolean result = specialCharactersValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
}
