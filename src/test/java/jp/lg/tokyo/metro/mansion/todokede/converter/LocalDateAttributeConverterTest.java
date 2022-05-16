/*
 * @(#) LocalDateAttributeConverterTest.java 
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author hbthinh
 * Create Date: Dec 15, 2020
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;

/**
 * @author hbthinh
 *
 */
@RunWith(SpringRunner.class)
public class LocalDateAttributeConverterTest {
    
    @InjectMocks
    LocalDateAttributeConverter localDateAttributeConverter;
    
    private static final LocalDate LOCALDATE_NOW = LocalDate.now();
    private static final String LOCALDATE_STRING = "20200108"; 
    
    /**
     * 案件LocalDateAttributeConverter／チェックリストID：UT- LocalDateAttributeConverter-001 ／区分：N／チェック内容： Test Convert To Database Column When Sucess
    */
    @Test
    public void testConvertToDatabaseColumnWhenSucess() {
        // execute
        String result = localDateAttributeConverter.convertToDatabaseColumn(LOCALDATE_NOW);
        
        assertThat(result).isEqualTo(DateTimeUtil.getLocalDateAsString(LOCALDATE_NOW));
    }
    
    /**
     * 案件LocalDateAttributeConverter／チェックリストID：UT- LocalDateAttributeConverter-002 ／区分：E／チェック内容： Test Convert To Database Column When Input Is Null
    */
    @Test
    public void testConvertToDatabaseColumnWhenInputIsNull() {
        // execute
        String result = localDateAttributeConverter.convertToDatabaseColumn(null);
        
        assertThat(result).isEqualTo(null);
    }
    
    /**
     * 案件LocalDateAttributeConverter／チェックリストID：UT- LocalDateAttributeConverter-003 ／区分：N／チェック内容： Test Convert To Entity Attribute When Sucess
    */
    @Test
    public void testConvertToEntityAttributeWhenSucess() {
        // execute
        LocalDate result = localDateAttributeConverter.convertToEntityAttribute(LOCALDATE_STRING);
        
        assertThat(result).isEqualTo(DateTimeUtil.getLocalDateFromString(LOCALDATE_STRING));
    }
    
    /**
     * 案件LocalDateAttributeConverter／チェックリストID：UT- LocalDateAttributeConverter-004／区分：E／チェック内容： Test Convert To Entity Attribute When Input Is Null
    */
    @Test
    public void testConvertToEntityAttributeWhenInputIsNull() {
        // execute
        LocalDate result = localDateAttributeConverter.convertToEntityAttribute(CommonConstants.BLANK);
        
        // assert Result
        assertNull(result);
    }
}
