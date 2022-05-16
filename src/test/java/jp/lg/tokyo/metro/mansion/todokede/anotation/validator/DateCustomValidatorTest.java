/*
 * @(#) DateCustomValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.DateCustom;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.DateCustomValidator;

/**
 * @author hbthinh
 *
 */

@RunWith(SpringRunner.class)
public class DateCustomValidatorTest {
    
    @InjectMocks
    DateCustomValidator dateCustomValidator;
    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    DateCustom dateCustom;
    
    /**
     * 案件DateCustomValidator／チェックリストID：UT- DateCustomValidator-001 ／区分：N／チェック内容：Test Date Custom Validator When Sucess
     */
    @Test
    public void testDateCustomValidatorWhenSucess()
    {   
        // prepare data
        Mockito.when(dateCustom.pattern()).thenReturn("2019/12/12");
        
        // execute
        dateCustomValidator.initialize(dateCustom);
        boolean result = dateCustomValidator.isValid("2019/12/12  ", context);
        
        // assert Result
        assertTrue(result);
    }
    
    /**
     * 案件DateCustomValidator／チェックリストID：UT- DateCustomValidator-002 ／区分：N／チェック内容：Test Date Custom Validator When Input Is Blank
     */
    @Test
    public void testDateCustomValidatorWhenInputIsBlank()
    {   
        // prepare data
        Mockito.when(dateCustom.pattern()).thenReturn("2019/12/12");
        
        //execute
        dateCustomValidator.initialize(dateCustom);
        boolean result =  dateCustomValidator.isValid(CommonConstants.BLANK, context);
        
        //assert Result
        assertTrue(result);
    }
    
    /**
     * 案件DateCustomValidator／チェックリストID：UT- DateCustomValidator-003 ／区分：N／チェック内容：Test Date Custom Validator When Input Is Null
     */
    @Test
    public void testDateCustomValidatorWhenInputIsNull()
    {   
        // prepare data
        Mockito.when(dateCustom.pattern()).thenReturn("2019/12/12");
        
        //execute
        dateCustomValidator.initialize(dateCustom);
        boolean result =  dateCustomValidator.isValid(null, context);
        
        //assert Result
        assertTrue(result);
    }
    
    /**
     * 案件DateCustomValidator／チェックリストID：UT- DateCustomValidator-004 ／区分：E／チェック内容：Test Date Custom Validator When Failed
     */
    @Test
    public void testDateCustomValidatorWhenFailed()
    {   
        // prepare data
        Mockito.when(dateCustom.pattern()).thenReturn("2019/12/12");
        
        //execute
        dateCustomValidator.initialize(dateCustom);
        boolean result =  dateCustomValidator.isValid("2019/12/11", context);
        
        //assert Result
        assertFalse(result);
    }
}
