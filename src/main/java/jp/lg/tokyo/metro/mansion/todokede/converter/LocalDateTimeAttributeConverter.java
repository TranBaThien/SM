/*
 * @(#) LocalDateTimeAttributeConverter.java
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
import java.time.LocalDateTime;

@Converter
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return null;
        }
        return DateTimeUtil.getLocalDateTimeAsString(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String localDateString) {
        if (StringUtils.isEmpty(localDateString)) {
            return null;
        }
        return DateTimeUtil.getLocalDateTimeFromString(localDateString);
    }
}
