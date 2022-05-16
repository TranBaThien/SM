/*
 * @(#)DateUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

public class DateTimeUtil {

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static DateTimeFormatter dateTimeFormatterCustom = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter dateTimeFormatterCustom2 = DateTimeFormatter.ofPattern("yyyy/MM/dd:HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    private static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    
    private static final String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    
    private static final String RE_FORMAT_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";

    private DateTimeUtil() {}

	/**
	 *format DateTime
	 * @param localDateTime LocalDateTime
	 * @param formatter DateTimeFormatter
	 * @return String of {#localDateTime} as format {#formatter}
	 */
    public static String formatDateTime(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return localDateTime.format(formatter);
    }

    public static String formatDate(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    public static LocalDate getLocalDateFromString(String dateString, DateTimeFormatter formatter) {
    	return LocalDate.parse(dateString, formatter);
	}

	public static LocalDateTime getLocalDateTimeFromString(String dateString, DateTimeFormatter formatter) {
		return LocalDateTime.parse(dateString, formatter);
	}

    public static String getLocalDateTimeAsString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
    
    public static String getLocalDateTimeAsStringCustom(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatterCustom);
    }
    
    public static String getLocalDateTimeAsStringCustom2(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatterCustom2);
    }
    
    public static LocalDateTime getLocalDateTimeFromString(String datetimeString) {
        return LocalDateTime.parse(datetimeString, dateTimeFormatter);
    }
    
    /**
     * Convert format date from yyyy/MM/dd to yyyyMMdd
     * @param localDateString
     * @return
     */
    public static LocalDate getLocalDateFromString2(String localDateString) {
        return LocalDate.parse(localDateString, dateFormatter2);
    }

    public static String getLocalDateAsString(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }
    
    public static String getLocalDateAsString2(LocalDate localDate) {
        return localDate.format(dateFormatter2);
    }

    public static LocalDate getLocalDateFromString(String localDateString) {
        return LocalDate.parse(localDateString, dateFormatter);
    }

    public static String getLocaltimeAsString(LocalTime localTime) {
        return localTime.format(timeFormatter);
    }

    public static LocalTime getLocalTimeFromString(String localTimeString) {
        return LocalTime.parse(localTimeString, timeFormatter);
    }
    
    /**
     * Convert dateTime To String
     * @param dateTime
     * @return
     */
	public static Timestamp convertStringToTimestamp(String dateTime) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			Date date = (Date) formatter.parse(dateTime);
			java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Convert format date from yyyy/MM/dd HH:mm to yyyyMMddHHmm
	 * @param inputDate
	 * @return
	 */
	public static String convertFormatDate(String inputDate) {
		try {
			Date date = new SimpleDateFormat(RE_FORMAT_YYYYMMDDHHMM).parse(inputDate);
		   return new SimpleDateFormat(FORMAT_YYYYMMDDHHMM).format(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Convert format date from yyyyMMddHHmm to yyyy/MM/dd HH:mm
	 * @param inputDate
	 * @return
	 */
	public static String convertReFormatDate(String inputDate) {
		try {
			Date date = new SimpleDateFormat(FORMAT_YYYYMMDDHHMM).parse(inputDate);
		   return new SimpleDateFormat(RE_FORMAT_YYYYMMDDHHMM).format(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Convert format date from yyyy/MM/dd to yyyyMMdd
	 * @param inputDate
	 * @return
	 */
	public static String convertFormatDateOnly(String inputDate) {
		try {
			Date date = new SimpleDateFormat(CommonConstants.SLASH_YYYYMMDD).parse(inputDate);
		   return new SimpleDateFormat(CommonConstants.YYYYMMDD).format(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * Convert format date from yyyyMMdd to yyyy/MM/dd
	 * @param inputDate
	 * @return
	 */
	public static String convertReFormatDateOnly(String inputDate) {
		try {
			Date date = new SimpleDateFormat(CommonConstants.YYYYMMDD).parse(inputDate);
		   return new SimpleDateFormat(CommonConstants.SLASH_YYYYMMDD).format(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Get current date time of system
	 * @return
	 */
	public static Timestamp getCurrentSystemDateTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Get current date for export CSV
	 * @return
	 */
	public static String getCurrentDateForCsv() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.FORMAT_DATETIME_EXPORT_CSV);
		return sdf.format(date);
	}
}
