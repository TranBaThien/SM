/*
 * @(#) FullSizeCharacterValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FullSizeCharacter;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.FullSizeCharacterValidator;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class FullSizeCharacterValidatorTest {
    
    @InjectMocks
    FullSizeCharacterValidator fullSizeCharacterValidator;
    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    FullSizeCharacter fullsizeCharacter;
    
    /**
     * 案件FullSizeCharacterValidator／チェックリストID：UT- FullSizeCharacterValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Full Size
     */
    @Test
    public void testIsValidWhenInputIsFullSize() {
        // prepare data
        String input = "ｔｅｓｔ";
        
        // execute
        fullSizeCharacterValidator.initialize(fullsizeCharacter);
        boolean result = fullSizeCharacterValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件FullSizeCharacterValidator／チェックリストID：UT- FullSizeCharacterValidator-002 ／区分：N／チェック内容：Test Is Valid When Input Is Not Full Size
     */
    @Test
    public void testIsValidWhenInputIsNotFullSize() {
        // prepare data
        String input = "test";
        
        // execute
        fullSizeCharacterValidator.initialize(fullsizeCharacter);
        boolean result = fullSizeCharacterValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
