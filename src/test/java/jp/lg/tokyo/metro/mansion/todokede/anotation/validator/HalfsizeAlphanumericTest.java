/*
 * @(#) HalfsizeAlphanumericTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.HalfsizeAlphanumeric;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.HalfsizeAlphanumericValidator;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class HalfsizeAlphanumericTest {
    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    HalfsizeAlphanumeric halfsizeAlphanumeric;
    
    @InjectMocks
    HalfsizeAlphanumericValidator halfsizeAlphanumericValidator;
    
    /**
     * 案件HalfsizeAlphanumericValidator／チェックリストID：UT- HalfsizeAlphanumericValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Halfsize Alphanumeric
     */
    @Test
    public void testIsValidWhenInputIsHalfsizeAlphanumeric() {
        // prepare data
        String input = "test123456";
        
        // execute
        halfsizeAlphanumericValidator.initialize(halfsizeAlphanumeric);
        boolean result = halfsizeAlphanumericValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件HalfsizeAlphanumericValidator／チェックリストID：UT- HalfsizeAlphanumericValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Is Not Halfsize Alphanumeric
     */
    @Test
    public void testIsValidWhenInputIsNotHalfsizeAlphanumeric() {
        // prepare data
        String input = "@#$%^&**&";
        
        // execute
        halfsizeAlphanumericValidator.initialize(halfsizeAlphanumeric);
        boolean result = halfsizeAlphanumericValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
