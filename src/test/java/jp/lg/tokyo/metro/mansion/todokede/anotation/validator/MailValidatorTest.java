/*
 * @(#) MailValidatorTest.java 
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

import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Mail;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.validator.MailValidator;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class MailValidatorTest {
    @Mock
    ConstraintValidatorContext context;
    
    @Mock
    Mail mail;
    
    @InjectMocks
    MailValidator mailValidator;
    
    /**
     * 案件MailValidator／チェックリストID：UT- MailValidator-001 ／区分：N／チェック内容：Test Is Valid When Input Is Mail
     */
    @Test
    public void testIsValidWhenInputIsMail() {
        // prepare data
        String input = "test@gmail.com";
        
        // execute
        mailValidator.initialize(mail);
        boolean result = mailValidator.isValid(input, context);
        
        //result result
        assertTrue(result);
    }
    
    /**
     * 案件MailValidator／チェックリストID：UT- MailValidator-002 ／区分：N／チェック内容：Test Is Valid When Input Is Not Mail
     */
    @Test
    public void testIsValidWhenInputIsNotMail() {
        // prepare data
        String input = "test123456";
        
        // execute
        mailValidator.initialize(mail);
        boolean result = mailValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
    
    /**
     * 案件MailValidator／チェックリストID：UT- MailValidator-003 ／区分：N／チェック内容：Test Is Valid When Input Is Null
     */
    @Test
    public void testIsValidWhenInputIsNull() {
        // prepare data
        String input = null;
        
        // execute
        mailValidator.initialize(mail);
        boolean result = mailValidator.isValid(input, context);
        
        //result result
        assertFalse(result);
    }
}
