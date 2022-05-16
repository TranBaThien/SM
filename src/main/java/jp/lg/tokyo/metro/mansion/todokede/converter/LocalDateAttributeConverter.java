/*
 * @(#) LocalDateAttributeConverter.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author lhloc
 * Create Date: 2019/11/25
 * Version: 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.converter;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.DateTimeUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

@Converter
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        if (ObjectUtils.isEmpty(localDate)) {
            return null;
        }
        return DateTimeUtil.getLocalDateAsString(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(String localDateString) {
        if (StringUtils.isEmpty(localDateString)) {
            return null;
        }
        return DateTimeUtil.getLocalDateFromString(localDateString);
    }
}
