/*
 * @(#) KatakanaValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Katakana;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.KatakanaValidator;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class KatakanaValidatorTest {
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    Katakana katakana;
    
    @InjectMocks
    KatakanaValidator katakanaValidator;
    
    /**
     * 案件KatakanaValidator／チェックリストID：UT- KatakanaValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Katakana
     */
    @Test
    public void testIsValidWhenInputIsKatakana() {
        // prepare data
        String input = "コンニチハ";
        
        // execute
        katakanaValidator.initialize(katakana);
        boolean result = katakanaValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件KatakanaValidator／チェックリストID：UT- KatakanaValidator-002 ／区分：N／チェック内容：Test Is Valid When Input Is Not Katakana
     */
    @Test
    public void testIsValidWhenInputIsNotKatakana() {
        // prepare data
        String input = "test123456";
        
        // execute
        katakanaValidator.initialize(katakana);
        boolean result = katakanaValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
