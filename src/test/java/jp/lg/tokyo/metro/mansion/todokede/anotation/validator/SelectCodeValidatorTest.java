/*
 * @(#) SelectCodeValidatorTest.java 
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
import static org.mockito.Mockito.mockitoSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.SelectCode;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.SelectCodeValidator;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.Code;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeList;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CodeUtil;
import jp.lg.tokyo.metro.mansion.todokede.config.CodeUtilConfig;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
@Import(value = CodeUtilConfig.class)
public class SelectCodeValidatorTest {    
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    SelectCode selectCode;
    
    @Mock
    HttpSession session;
    
//    @Mock
//    Map<String, CodeList> codeMap ;
    
    @InjectMocks
    SelectCodeValidator selectCodeValidator;
    
    /**
     * 案件SelectCodeValidator／チェックリストID：UT- SelectCodeValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Code Is Exist
    */
    @Test
    public void testIsValidWhenInputCodeIsExist( ) {
        // prepare data     
        String input = "1";
        Mockito.when(selectCode.code()).thenReturn(new String[]{"CD002"});    
        
      //execute
        selectCodeValidator.initialize(selectCode);
        boolean result = selectCodeValidator.isValid(input, context);
        
        //assert Result
        assertTrue(result);
    }
    
    /**
     * 案件SelectCodeValidator／チェックリストID：UT- SelectCodeValidator-002 ／区分：E／チェック内容：Test Is Valid When Input Code Is Not Exist
    */
    @Test
    public void testIsValidWhenInputCodeIsNotExist( ) {
        // prepare data
        String input = "6";
        
        //execute
        selectCodeValidator.initialize(selectCode);
        Mockito.when(selectCode.code()).thenReturn(new String[]{"CD002"});
        selectCodeValidator.initialize(selectCode);
        boolean result = selectCodeValidator.isValid(input, context);
        
        //assert Result
        assertFalse(result);
    }
    
    /**
     * 案件SelectCodeValidator／チェックリストID：UT- SelectCodeValidator-003 ／区分：E／チェック内容：Test Is Valid When Input Code Is Null
    */
    @Test
    public void testIsValidWhenInputIsNull( ) {
        // prepare data
        String input = null;
        
        //execute
        selectCodeValidator.initialize(selectCode);
        boolean result = selectCodeValidator.isValid(input, context);
        
        //assert Result
        assertFalse(result);
    }
}   
