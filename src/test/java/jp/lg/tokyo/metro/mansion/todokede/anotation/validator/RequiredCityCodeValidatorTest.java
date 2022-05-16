/*
 * @(#) RequiredCityCodeValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.RequiredCityCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.RequiredCityCodeValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class RequiredCityCodeValidatorTest {
    @Mock
    RequiredCityCode requiredCityCode;
    
    @Mock
    ConstraintValidatorContext context;
    
    @InjectMocks
    RequiredCityCodeValidator requiredCityCodeValidator;
    
    /**
     * 案件RequiredCityCodeValidator／チェックリストID：UT- RequiredCityCodeValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Required CityCode
    */
    @Test
    public void testIsValidWhenInputIsRequiredCityCode() {
        // prepare data
        String input = "1";
   
        // execute       
        requiredCityCodeValidator.initialize(requiredCityCode);       
        boolean result = requiredCityCodeValidator.isValid(input, context);
       
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件RequiredCityCodeValidator／チェックリストID：UT- RequiredCityCodeValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Is Success
    */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = CommonConstants.BLANK;
        // execute
        requiredCityCodeValidator.initialize(requiredCityCode);
        boolean result = requiredCityCodeValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
