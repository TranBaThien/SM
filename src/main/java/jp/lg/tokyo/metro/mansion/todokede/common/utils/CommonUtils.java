/*
 * @(#)CommonUtils.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.DASH;
import static jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants.DECIMAL_FORMAT;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import jp.lg.tokyo.metro.mansion.todokede.common.ApplicationProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.FieldName;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.Position;
import jp.lg.tokyo.metro.mansion.todokede.exception.BusinessException;
import net.bytebuddy.implementation.bytecode.Throw;


/**
 * CommonUtils
 *
 * @version 1.00
 * @since 1.00
 */
public class CommonUtils {

    private static final Logger log = LogManager.getLogger(CommonUtils.class);

    // 最大値定義

    private static final short MAX_COL_NUMBER = 256; // 最大列数

    private static final int MAX_ROW_NUMBER = 65536; // 最大行数

    private static final int MIN_YEAR = 1868; // 最大設定文字列

    private static final int MAX_YEAR = 3000; // 最大設定文字列

    private static final String[] JOINTSTOCK_COMPANY_KANA = { "カブシキガイシャ", "ユウゲンガイシャ", "ゴウシガイシャ", "ゴウメイガイシャ", "キョウドウガイシャ", "キョウギョウガイシャ", "キギョウクミアイ", "ゴウドウガイシャ" };

    private static final String[] JOINTSTOCK_KANJI = { "（株）", "（有）", "（資）", "（名）", "（同）", "（業）", "（企）", "（合）" };

    private static final String[] JOINTSTOCK_COMPANY_KANJI = { "株式会社", "有限会社", "合資会社", "合名会社", "協同会社", "協業会社", "企業組合", "合同会社" };

    /* PTLuan Define get message form BindingResult */
    /*必須*/
    private static final String NOTBLANK = "NotBlank";
    
    /*必須*/
    private static final String NOTEMPTY = "NotEmpty";
    
    /*必須*/
    private static final String REQUIREDCITYCODE = "RequiredCityCode";
    
    /*全角文字*/
    private static final String FULLSIZECHARACTOR = "FullSizeCharacter";
    /*半角数字*/
    private static final String HAFTSIZENUMBER = "HaftSizeNumber";
    /*半角英数字*/
    private static final String HAFTSIZEALPANUMERIC = "HalfsizeAlphanumeric";
    /*全角カタカナ*/
    private static final String KATAKANA = "Katakana";
    private static final String SPECIALCHARACTOR = "SpecialCharacters";
    /*日付*/
    private static final String DATETIMEFOMAT = "DateCustom";
    /*入力可能桁数*/
    private static final String SIZE = "Size";
    /* 最小 */
    private static final String MINSIZE = "MinSize";
    /* 最大 */
    private static final String MAXSIZE = "MaxSize";
    /*URL*/
    private static final String URL = "URL";
    /*電話番号*/
    private static final String PHONENUMBER = "PhoneNumber";
    /*メールアドレス*/
    private static final String MAIL = "Mail";
    /*半角数字小数点有り*/
    private static final String NUMBERWITHDECIMALPOINT = "NumberWithDecimalPoint";
    /*半角数字小数点有り*/
    private static final String NUMBERWITHDECIMALPOINTLIMIT = "NumberWithDecimalPointLimit";
    private static final String SELECTCODE = "SelectCode";

    private static final String MINSIZEVALIDBLANK = "MinSizeValidBlank";
    private static final int    REQUIREDLEVEL = 1;
    private static final int    TYPECHARLEVEL = 2;
    private static final int    FORMATLEVEL = 3;
    private static final int    SIZELEVEL = 4;
    private static final int    SPECIALCHARACTERLEVEL = 5;
    
    /**
     * copy all properties of source list to the return list in case that element of
     * source list and the returned list are the same class
     *
     * @param src
     *            source list
     * @return des destination list
     * @throws SystemException 
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> copyList(List<T> src) throws SystemException {

        if (src == null) {
            return new ArrayList<T>();
        }

        List<T> des = new ArrayList<T>(src.size());
        T desObject = null;
        T srcObject = null;

        // Loop through src and copy each item of src.
        for (int i = 0; i < src.size(); i++) {
            srcObject = src.get(i);
            try {
                // copy srcObject and assign desObject.
                desObject = (T) BeanUtils.cloneBean(srcObject);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                throw new SystemException();
            }
            des.add(desObject);
        }
        return des;
    }

    /**
     * sub String by delimiter
     *
     * @param str
     *            String
     * @param delimiter
     *            ("-" or " ") String
     * @param number
     *            int
     * @return String
     */
    public static String delimiterFirst(String str, String delimiter, int number) {

        String tmpStr = "";

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        str = str.trim();
        // if str does not contain delimiter, then return substring 0-> number.
        if (str.indexOf(delimiter) == -1) {
            return str.substring(0, number);
        }
        // if index of delimiter large than number, then return substring 0->
        // number.
        if (str.indexOf(delimiter) > number) {
            tmpStr = str.substring(0, number);
        } else {
            tmpStr = str.substring(0, str.indexOf(delimiter));
        }

        return tmpStr;
    }

    /**
     * sub String by delimiter, this string is combined string of 2 non-mandatory
     * string.
     *
     * @param str
     *            String
     * @param delimiter
     *            ("-" or " ") String
     * @param number
     *            int
     * @return String
     */
    public static String delimiterFirstNotMandatory(String str, String delimiter, int number) {

        String tmpStr = "";

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        if (str.indexOf(delimiter) == -1) {
            return str.substring(0, number);
        }

        if (str.indexOf(delimiter) > number) {
            tmpStr = str.substring(0, number);
        } else {
            tmpStr = str.substring(0, str.indexOf(delimiter));
        }

        return tmpStr;
    }

    /**
     * sub String by delimiter
     *
     * @param str
     *            String
     * @param delimiter
     *            ("-" or " ") String
     * @param start
     *            int
     * @param end
     *            int
     * @return String
     */
    public static String delimiterSecond(String str, String delimiter, int start, int end) {

        String tmpStr = "";
        String subString = "";

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        str = str.trim();

        if ((str.indexOf(delimiter) == -1) || (str.indexOf(delimiter) >= end)) {
            return str.substring(start - 1, end);
        }

        if ((str.indexOf(delimiter) > start) && (str.indexOf(delimiter) < end)) {
            tmpStr = str.substring(start - 1, str.indexOf(delimiter));
        }

        if ((str.indexOf(delimiter) < start)) {
            subString = str.substring(str.indexOf(delimiter) + 1, str.length());
            if (subString.indexOf(delimiter) == -1) {
                if (subString.length() < ((end - start) + 1)) {
                    tmpStr = subString.substring(0, subString.length());
                } else {
                    tmpStr = subString.substring(0, (end - start) + 1);
                }
            } else {
                if (subString.indexOf(delimiter) < end) {
                    tmpStr = subString.substring(0, subString.indexOf(delimiter));
                }
            }
        }

        return tmpStr;
    }

    /**
     * sub String by delimiter, this string is combined string of 2 non-mandatory
     * string.
     *
     * @param str
     *            String
     * @param delimiter
     *            ("-" or " ") String
     * @param start
     *            int
     * @param end
     *            int
     * @return String
     */
    public static String delimiterSecondNotMandatory(String str, String delimiter, int start, int end) {

        String tmpStr = "";
        String subString = "";

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        if ((str.indexOf(delimiter) == -1) || (str.indexOf(delimiter) >= end)) {
            return str.substring(start - 1, end);
        }

        if ((str.indexOf(delimiter) > start) && (str.indexOf(delimiter) < end)) {
            tmpStr = str.substring(start - 1, str.indexOf(delimiter));
        }

        if ((str.indexOf(delimiter) < start)) {
            subString = str.substring(str.indexOf(delimiter) + 1, str.length());
            if (subString.indexOf(delimiter) == -1) {
                if (subString.length() < ((end - start) + 1)) {
                    tmpStr = subString.substring(0, subString.length());
                } else {
                    tmpStr = subString.substring(0, (end - start) + 1);
                }
            } else {
                if (subString.indexOf(delimiter) < end) {
                    tmpStr = subString.substring(0, subString.indexOf(delimiter));
                }
            }
        }

        return tmpStr;
    }

    /**
     * sub String by delimiter number is the number of character will be cut
     *
     * @param str
     *            String
     * @param delimiter
     *            String
     * @param number
     *            int
     * @return String
     */
    public static String delimiterLast(String str, String delimiter, int number) {

        String tmpStr = "";
        int length = 0;

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        str = str.trim();

        length = str.length();
        if (str.indexOf(delimiter) == -1) {
            return str.substring(length - number, length);
        }

        if (str.lastIndexOf(delimiter) == str.indexOf(delimiter)) {
            tmpStr = str.substring(length - number, length);
        } else {
            tmpStr = str.substring(str.lastIndexOf(delimiter) + 1, length);
        }

        return tmpStr;
    }

    /**
     * Returns a new string that is a substring of this string
     *
     * @param str
     *            String
     * @param start
     *            int
     * @param end
     *            int
     * @return String
     */
    public static String subString(String str, int start, int end) {

        if (GenericValidator.isBlankOrNull(str)) {
            return CommonConstants.BLANK;
        }

        return str.substring(start, end);
    }

    /**
     * Check the input string is only contain full-size characters or not. Return
     * true when input string is only contain full-size characters
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isFullSizeString(String input) {

        if (null == input) {
            return false;
        }
        return CheckUtil.isValidX0208(input);
    }

    /**
     * Check the input string is only contain full-size characters or not. Return
     * true when input string is only contain full-size characters
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isFullSizeAllowString(String input) {

        if (!CheckUtil.isNoUserDefined(input)) {
            return false;
        } else if (!CheckUtil.isValidString(input)) {
            return false;
        }
        return true;
    }

    /**
     * Check the input string is only contain half-size characters or not. Return
     * true when input string is only contain half-size characters
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isHalfSizeString(String input) {

        if (null == input) {
            return false;
        }

        CharacterIterator iter = new StringCharacterIterator(input);

        // 蜊願ｧ偵メ繧ｧ繝・け
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if ((c < 0x0020) || (c > 0x007E)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check the input string is only contain alphaNumeric characters or not. Return
     * true when input string is only contain alphaNumeric characters
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isAlphaNumeric(String input) {

        if (null == input) {
            return false;
        }

        return CheckUtil.isPatternMatch("/^[A-Za-z0-9+-.@/: ]*$/", input);
    }

    /**
     * Check the input string is only contain alphaNumeric characters or not. Return
     * true when input string is only contain alphaNumeric characters
     * for mail address
     *
     * @param input
     *            String
     * @return boolean
     */
    public static boolean isAlphaNumericForMail(String input) {

        if (null == input) {
            return false;
        }

        return CheckUtil.isPatternMatch("/^[A-Za-z0-9.@]*$/", input);
    }

    /**
     * Check the input string is only contain full-size katakana characters or not.
     * Return true when input string is only contain full-size katakana characters
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isKatakanaString(String str) {

        if (null == str) {
            return false;
        }

        String pattern = "/^[ァ-・ヽヾ゛゜ー　]*$/";

        // 全角カタカナチェック
        return CheckUtil.isPatternMatch(pattern, str);
    }

    /**
     * Check the input string has first character exist in
     * list{"（株）","（有）","（資）","（名）","（同）","（業）","（企）"} Return true when input string
     * has first character exist in this list
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean checkFirstCharacter(String str) {

        String value = "";
        String[] charArray = { "（株）", "（有）", "（資）", "（名）", "（同）", "（業）", "（企）" };

        if (str.length() < 3) {
            return false;
        }
        value = str.substring(CommonConstants.NUM_0, CommonConstants.NUM_3);

        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i].equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Convert halfsize number to fullsize number
     *
     * @param halfWidthNumber
     *            String
     * @return fullWidthNumber String
     */
    public static String convertFullWidthNumber(String halfWidthNumber) {

        /* Declare full width number array */
        String[] fullWidthNumbers = { "０", "１", "２", "３", "４", "５", "６", "７", "８", "９" };
        /*
         * Loop on fullWidthNumbers and replace every number in halfWidthNumber with
         * corresponding element in fullWidthNumbers
         */
        for (int i = 0; i < fullWidthNumbers.length; i++) {
            halfWidthNumber = halfWidthNumber.replace(String.valueOf(i), fullWidthNumbers[i]);
        }
        /* Return halfWidthNumber after replaced with full width number */
        return halfWidthNumber;
    }

    /**
     * Convert an iterator to ordered array
     *
     * @param iterator
     *            Iterator
     * @param size
     *            int
     * @return String[]
     */
    public static String[] convertToOrderedArray(Iterator<String> iterator, int size) {

        String[] result = null;
        String value = null;
        String tempValue = null;
        int index = 0;
        /*
         * In case size of iterator is 0 then return null, end process
         */
        if (size == 0) {
            return null;
        }
        result = new String[size];
        while (iterator.hasNext()) {
            value = iterator.next();
            result[index++] = value;
        }

        /*
         * Sort string array
         */
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = i; j < result.length; j++) {
                /* Swap 2 elements */
                if (Integer.parseInt(result[i]) > Integer.parseInt(result[j])) {
                    tempValue = result[i];
                    result[i] = result[j];
                    result[j] = tempValue;
                }
            }
        }

        /* Return result after sorted */
        return result;
    }

    /**
     * Get minimum code in map
     *
     * @param map
     * @return String
     */
    public static String getMinCode(HashMap<String, HashMap<String, String>> map) {

        String minCode = null;
        Set<String> keySet = null;
        Iterator<String> iterator = null;
        String value = null;
        /* Get key set of map */
        keySet = map.keySet();
        /* Convert to iterator from key set */
        iterator = keySet.iterator();
        /* Get minimum code from iterator */
        while (iterator.hasNext()) {
            value = iterator.next();
            /*
             * In case code is -1 or -2 then continue
             */
            if ("-1".equals(value) || "-2".equals(value)) {
                continue;
            }
            /*
             * In case value is null then set value to minCode
             */
            if (minCode == null) {
                minCode = value;
            } else if (minCode.compareTo(value) > 0) {
                minCode = value;
            }
        }
        /* Return minimum code */
        return minCode;
    }

    /**
     * Format a number to a fixed length string.
     *
     * @param num
     * @param stringFormat
     * @return String
     */
    public static String createFixedString(long num, String stringFormat) {

        DecimalFormat df = new DecimalFormat(stringFormat);
        return df.format(num);
    }

    /**
     * Append half size space to a string to create a string with length = count.
     *
     * @param value
     *            String
     * @param count
     *            int
     * @return String
     */
    public static String appendHalfSizeSpace(String value, int count) {

        int parsedValue = 0;
        if ((value != null) && (!CommonConstants.BLANK.equals(value))) {
            try {
                parsedValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return CommonConstants.BLANK;
            }
            value = String.valueOf(parsedValue);
            for (int i = value.length(); i < count; i++) {
                value = value + CommonConstants.SPACE_HALF_SIZE;
            }
        }
        return value;
    }

    /**
     * format float number like 999.999
     *
     * @param value
     *            String
     * @return String
     */
    public static String formatFloatNumber(String value) {

        String rs = "";
        String integerPart = "";
        String floatPart = "";

        if (GenericValidator.isBlankOrNull(value)) {
            return rs;
        }

        if (value.contains(CommonConstants.DOT)) {
            integerPart = value.substring(0, value.indexOf(CommonConstants.DOT));
            floatPart = value.substring(value.indexOf(CommonConstants.DOT) + 1, value.length());
            if (integerPart.length() > 3 && !floatPart.contains(CommonConstants.DOT)) {
                integerPart = formatPrice(integerPart);
            }
            rs = integerPart + CommonConstants.DOT + floatPart;
        } else if (NumberUtils.isNumber(value)) {
            rs = formatPrice(value);
        } else {
            rs = value;
        }
        return rs;
    }

    /**
     * format price like 999,999,999
     *
     * @param value
     *            String
     * @return String
     */
    public static String formatPrice(String value) {

        String result = "";
        int j = 0;
        String str = "";
        if (GenericValidator.isBlankOrNull(value)) {
            return result;
        }

        while ((!value.equals(CommonConstants.BLANK)) && (value.charAt(0) == '0')) {
            if (value.length() == 1) {
                return String.valueOf(CommonConstants.NUM_0);
            } else {
                value = value.substring(1);
            }
        }

        if (value.length() > 3) {
            while (value.indexOf(',') != -1) {
                value = value.replace(",", CommonConstants.BLANK);
            }
            j = value.length() % 3;
            if (j == 0) {
                result = addComma(value);
            } else {
                str = value.substring(0, j);
                if (!"-".equals(str)) {
                    result = str + "," + addComma(value.substring(j));
                } else {
                    result = str + addComma(value.substring(j));
                }
            }
        } else {
            if (".".equals(value.substring(0, 1))) {
                value = "0" + value;
            }
            return value;
        }

        if (".".equals(result.substring(0, 1))) {
            result = "0" + result;
        }

        return result;
    }

    public static String formatInteger(String intNum) {
        try {
            return String.format("%,d", Long.valueOf(intNum));
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatInteger(int intNum) {
        try {
            return String.format("%,d", intNum);
        } catch (Exception e) {
            return "";
        }
    }

    private static String addComma(String value) {

        String result = "";
        int i = 0;

        if (GenericValidator.isBlankOrNull(value)) {
            return result;
        }
        if (value.length() > 0) {
            i = value.length() / 3;
            if ((i == 0) || (i == 1)) {
                return value;
            }
            for (int k = 0; k < i; k++) {
                result = result + value.substring(k * 3, (k * 3) + 3);
                result = result + ",";
            }

            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    public static String trimLeftZero(String value) {

        // 空白文字の除去

        if (null == value) {
            return "";
        }
        float percent = 0.0f;
        // divide 10
        try {
            percent = Integer.parseInt(value) / 10.0f;
            value = String.valueOf(percent);
        } catch (Exception e) {
            return "";
        }

        return value;
    }

    /**
     * 文字列の前後の全角空白および半角空白を除去します。 Remove half-width blank and full-width blank at the
     * end/beginning of string.
     *
     * @param s
     *            変換前文字列
     * @return 変換後文字列
     */
    public static String trim(String str) {

        if (str == null) {
            return null;
        }
        return StringUtil.trimW(str);
    }

    /**
     *
     * @param date
     * @return
     */



    /**
     * Check normal-figure sign having.
     *
     * @param str
     *            String
     * @return String
     */
    public static boolean isDigitSignHaving(String str) {

        return CheckUtil.isPatternMatch("/^[\\+\\-\\.\\d]*$/", str);
    }

    /**
     * Check valid password.
     *
     * @param str
     *            String
     * @return String
     */
//    public static boolean isPassword(String str) {
//        String passwordKind = ApplicationProperties.getProperty(CommonConstants.PASSWORD_KIND);
//        String regularExpression = passwordKind == null ? "^((?=.*[0-9a-zA-Z])(?!.*[^0-9a-zA-Z]).*|)$" : passwordKind;
//        String val = StringEscapeUtils.escapeJava(str);
//        return val.matches(regularExpression);
//    }

    /**
     * Copy all properties of source Object to des Object.
     *
     * @param dest
     *            Object
     * @param orig
     *            Object
     * @return void
     * @exception SystemException
     */
    public static void copyProperties(Object dest, Object orig) throws SystemException {
        try {
            ConvertUtils.register(new DateConverter(null), Date.class);
            ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SystemException();
        }
    }

    /**
     * sub date string by delimiter, this string is combined string of 3
     * non-mandatory string.
     *
     * @param str
     *            String
     * @param delimiter
     *            ("-" or " ") String
     * @return String
     */
    public static String[] delimiterDateNotMandatory(String str, String delimiter) {

        String[] tmpStr = new String[3];
        tmpStr[0] = CommonConstants.BLANK;
        tmpStr[1] = CommonConstants.BLANK;
        tmpStr[2] = CommonConstants.BLANK;
        int delimiterPosition = 0;
        String newStr = "";

        if (GenericValidator.isBlankOrNull(str) || (delimiter == null)) {
            return tmpStr;
        }

        if (str.indexOf(delimiter) == -1) {
            tmpStr[0] = str.substring(CommonConstants.NUM_0, CommonConstants.NUM_4);
            tmpStr[1] = str.substring(CommonConstants.NUM_4, CommonConstants.NUM_6);
            tmpStr[2] = str.substring(CommonConstants.NUM_6, CommonConstants.NUM_8);
            return tmpStr;
        } else {
            delimiterPosition = str.indexOf(delimiter);
            switch (delimiterPosition) {
            case 0:
                tmpStr[0] = CommonConstants.BLANK;
                newStr = str.substring(CommonConstants.NUM_1, str.length());
                switch (newStr.indexOf(delimiter)) {
                case -1:
                    tmpStr[1] = newStr.substring(CommonConstants.NUM_0, CommonConstants.NUM_2);
                    tmpStr[2] = newStr.substring(CommonConstants.NUM_2, CommonConstants.NUM_4);
                    break;
                case 0:
                    tmpStr[1] = CommonConstants.BLANK;
                    tmpStr[2] = newStr.substring(CommonConstants.NUM_1, CommonConstants.NUM_3);
                    break;
                case 2:
                    tmpStr[1] = newStr.substring(CommonConstants.NUM_0, CommonConstants.NUM_2);
                    tmpStr[2] = CommonConstants.BLANK;
                    break;
                default:
                }
                return tmpStr;
            case 4:
                tmpStr[0] = str.substring(CommonConstants.NUM_0, CommonConstants.NUM_4);
                tmpStr[1] = CommonConstants.BLANK;
                if (str.length() == 6) {
                    tmpStr[2] = CommonConstants.BLANK;
                } else {
                    tmpStr[2] = str.substring(CommonConstants.NUM_5, str.length());
                }
                return tmpStr;
            case 6:
                tmpStr[0] = str.substring(CommonConstants.NUM_0, CommonConstants.NUM_4);
                tmpStr[1] = str.substring(CommonConstants.NUM_4, CommonConstants.NUM_6);
                tmpStr[2] = CommonConstants.BLANK;
                return tmpStr;
            default:
            }
        }
        return new String[] { "", "", "" };
    }

    /**
     * get value of cell with row and column position.
     *
     * @param row
     *            HSSFRow
     * @param pos
     *            (cell position) short
     * @return String
     */
    public static String getCellValue(XSSFRow row, short pos) {

        /* Check row */
        if (row == null) {
            return null;
        }

        /* Get cell value */
        XSSFCell attributeCell = row.getCell(pos);

        /* Check value of cell */
        if (!isValidCell(attributeCell)) {
            return null;
        }

        /* Return value of cell */
        return getValue(attributeCell);
    }

    /**
     * check cell is existed or not.
     *
     * @param cell
     *            HSSFCell
     * @return boolean
     */
    public static boolean isValidCell(XSSFCell cell) {

        String stringCellValue = getValue(cell);
        return null != cell && null != stringCellValue && !"".equals(stringCellValue);
    }

    /**
     * get value of cell.
     *
     * @param cell
     *            HSSFCell
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String getValue(XSSFCell cell) {

        if (null == cell) {
            return null;
        }
        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
            return String.valueOf(cell.getStringCellValue().trim());
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
            return "";
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
            return String.valueOf(cell.getErrorCellValue());
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
            return String.valueOf(cell.getCellFormula());
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            Double number = cell.getNumericCellValue();
            return String.valueOf(number.longValue());
        }

        return null;
    }

    /**
     * Set value to cell locate by (rowPos, colPos).
     *
     * @param hSSFSheet
     * @param rowPos
     * @param colPos
     * @param value
     */
    public static void createFormatedRow(XSSFSheet xssfsheet, int curRowNum, int numCol, CellStyle cellStyle) {

        if (curRowNum == 1) return;

        XSSFRow templateRow = xssfsheet.getRow(1);
        XSSFCell templateCell = null;

        XSSFCell newCell = null;
        XSSFRow currentRow = xssfsheet.createRow(curRowNum);

        for (int j = 0; j < numCol; j++) {
            templateCell = templateRow.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
            XSSFCellStyle style = templateCell.getCellStyle();
            newCell = currentRow.createCell((short) j);
            newCell.setCellStyle(style);
        }
    }

    /**
     * Set value to cell locate by (rowPos, colPos).
     *
     * @param hssfsheet
     * @param rowPos
     * @param colPos
     * @param value
     */
    public static void setCellValue(XSSFSheet xssfsheet, int rowPos, short colPos, String value) {

        // Declare and initial variables using in this method.
        XSSFRow xSSFRow = null;
        XSSFCell xSSFCell = null;

        if (value != null && !"".equals(value)) {

            // getRow of sheet if end of sheet create row
            xSSFRow = xssfsheet.getRow(rowPos);
            if (xSSFRow == null) {
                xSSFRow = xssfsheet.createRow(rowPos);
            }

            // get cell of row if end of column create cell
            xSSFCell = xSSFRow.getCell(colPos);

            if (xSSFCell == null) {
                xSSFCell = xSSFRow.createCell(colPos);
            }

            // set encoding ENCODING_UTF_16
            // hSSFCell.setEncoding(HSSFCell.ENCODING_UTF_16);

            // add value
            xSSFCell.setCellValue(value);
        }
    }

    /**
     * Set value to cell locate by (rowPos, colPos).
     *
     * @param book
     *            HSSFWorkbook
     * @param sheetName
     * @param rowPos
     * @param colPos
     * @param value
     * @throws SystemException 
     */
    public static void setCellValue(HSSFWorkbook book, String sheetName, int rowPos, int colPos, String value) throws SystemException {

        // Declare and initial variables using in this method.
        HSSFRow row = null;
        HSSFCell cell = null;

        // check rowPos and colPos
        if (rowPos < 1 || rowPos > MAX_ROW_NUMBER) {
            throw new SystemException("行番号が不正です。範囲：1～" + MAX_ROW_NUMBER + " rowNum = " + rowPos);
        }
        if (colPos < 1 || colPos > MAX_COL_NUMBER) {
            throw new SystemException("列番号が不正です。範囲：1～" + MAX_COL_NUMBER + "colNum = " + colPos);
        }
        // シートの取得
        HSSFSheet sheet = book.getSheet(sheetName);
        if (sheet == null) {
            sheet = book.createSheet(sheetName);
        }
        row = sheet.getRow(rowPos);
        if (row == null) {
            row = sheet.createRow(rowPos);
        }
        // get cell of row. If end of column create cell
        cell = row.getCell((short) colPos);
        if (cell == null) {
            cell = row.createCell((short) colPos);
        }
        // set encoding ENCODING_UTF_16
        // cell.setEncoding(HSSFCell.ENCODING_UTF_16);

        // add value
        cell.setCellValue(value);
    }

    /**
     * Remove all space in string
     *
     * @param source
     * @return
     */
    public static String removeSpaces(String source) {

        return source.replaceAll("\\s", "");

    }

    /**
     * Remove all 2 byte spaces
     *
     * @param source
     *            String to be remove spaces
     * @return String with no space 2 byte
     */
    public static String removeSpaces2Byte(String source) {

        return source.replaceAll(CommonConstants.SPACE_FULL_SIZE, "");
    }

    /**
     * Remove all comma in string
     *
     * @param source
     * @return
     */
    public static String removeComma(String source) {
        return source.replaceAll(",", "");
    }

    /**
     * Convert date object to string object according to the pattern parameter
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * format date according to the pattern: yyyyMMdd. example: 20081230, 20080218
     *
     * @param year
     * @param month
     * @param day
     * @return
     * @throws SystemException 
     */
    public static String formatDate(String year, String month, String day) throws SystemException {

        // check whether year, month day is valid or not
        try {
            Integer.parseInt(year);
            Integer.parseInt(month);
            Integer.parseInt(day);

        } catch (NumberFormatException e) {
            throw new SystemException();
        }
        // if month has 1 digit, then append "0" to ahead.
        month = (month.length() == 1) ? "0" + month : month;

        // if day has 1 digit, then append "0" to ahead.
        day = (day.length() == 1) ? "0" + day : day;

        // if year has 2 digit, then append " " to ahead.
        year = (year.length() == 2) ? "  " + year : year;

        return year + month + day;

    }

    /**
     * parse Date and split yyyy, MM, dd into String[] arr according to pattern:
     * yyyyMMdd
     *
     * @param date
     * @return arr
     */
    public static String[] parseDate(String date) {

        // declare arr is of String[] type that contains yyyy, MM, dd
        String[] arr = new String[3];
        // get yyyy
        arr[0] = date.substring(0, 4);
        // get MM
        arr[1] = date.substring(4, 6);
        // get dd
        arr[2] = date.substring(6, 8);
        // return
        return arr;

    }

    /**
     * parse Date and split yyyy, MM, dd into String[] arr according to pattern:
     * yyyyMMdd
     *
     * @param date
     * @return arr
     */
    public static Calendar stringToDate(String date) {

        // declare arr is of String[] type that contains yyyy, MM, dd
        String[] arr = new String[3];
        Calendar calendar = Calendar.getInstance();

        // get yyyy
        arr[0] = date.substring(0, 4);
        // get MM
        arr[1] = date.substring(4, 6);
        // get dd
        arr[2] = date.substring(6, 8);

        calendar.set(Calendar.YEAR, Integer.parseInt(arr[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(arr[1]) - 1);
        calendar.set(Calendar.DATE, Integer.parseInt(arr[2]));

        // return
        return calendar;

    }

    public static boolean isFuture(Calendar calendar) {

        return calendar.compareTo(Calendar.getInstance()) >= 0;
    }

    /**
     * Decode String object to the origin object.
     *
     * @param obj
     * @return
     * @throws SystemException 
     */
    public static String encodeData(Object obj) throws SystemException {

        try {

            ByteArrayOutputStream aos = null;
            ObjectOutputStream oos = null;

            ByteArrayOutputStream aos2 = null;
            GZIPOutputStream gzip = null;

            byte[] bt = null;
            byte[] compress = null;

            aos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(aos);

            oos.writeObject(obj);
            oos.flush();
            oos.close();

            bt = aos.toByteArray();

            aos.flush();
            aos.close();

            aos2 = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(aos2);

            gzip.write(bt);
            gzip.flush();
            gzip.close();

            aos2.flush();
            aos2.close();

            compress = aos2.toByteArray();

            return Base64.getEncoder().encodeToString(compress);

        } catch (IOException e) {
            throw new SystemException("想定外例外発生！");
        }
    }

    /**
     * Encode Base64 Object
     *
     * @param String
     * @return
     * @throws SystemException 
     */
    public static Object decodeData(String str) throws SystemException {

        try {

            byte[] decompressedBytes = null;

            ByteArrayInputStream bais = null; // (4)
            GZIPInputStream gzip = null; // (⑤)

            byte[] buff = new byte[1024]; // " object (⑥) to store buffer with
            // 1024 bytes
            ByteArrayOutputStream baos = null; // (1-7) Declare a
            // "ByteArrayOutputStream"
            // object (⑦)

            ByteArrayInputStream bais2 = null; // (1-8) Declare a
            // "ByteArrayInputStream" object
            // (⑧)
            ObjectInputStream ois2 = null; // (1-9) Declare a
            // "ObjectInputStream" object (⑨)

            bais = new ByteArrayInputStream(Base64.getDecoder().decode(str));
            gzip = new GZIPInputStream(bais);
            baos = new ByteArrayOutputStream();
            while (bais.read(buff) > 0) {
                baos.write(buff);
            }

            gzip.close();
            baos.close();
            bais.close();

            decompressedBytes = baos.toByteArray();
            bais2 = new ByteArrayInputStream(decompressedBytes);
            ois2 = new ObjectInputStream(bais2);

            Object obj = ois2.readObject();

            ois2.close();

            return obj;

        } catch (IOException | ClassNotFoundException e) {
            throw new SystemException("想定外例外発生！");
        }
    }

    /**
     * Download file excel or file pdf
     *
     * @param response
     * @param fileName
     * @param downloadType
     * @param inputStream
     * @param wb
     * @param logId
     * @throws SystemException 
     */
    public static void download(HttpServletResponse response, String fileName, int downloadType, InputStream inputStream, HSSFWorkbook wb, String[] logId) throws SystemException {

        String contentType = "";
        String contentDisposition = "";
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        byte[] buffer = new byte[CommonConstants.DOWNLOAD_BUFFSIZE];
        String extension = fileName.substring(fileName.lastIndexOf("."));
        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd_HHmmss");
        String newFileName = String.format("%1$s_%2$s%3$s", "UserID", dateFormat.format(new Date()), extension);

        try {
            newFileName = URLEncoder.encode(newFileName, "UTF-8");
            contentType = "application/octet-stream";
            // attachment
            if (downloadType == 0) {
                contentDisposition = "inline; filename=" + newFileName;
            } else {
                contentDisposition = "attachment; filename=" + newFileName;
            }
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", contentDisposition);

            // Allow browser to cache download file
            response.setHeader("Pragma", "cache");
            response.setHeader("Cache-Control", "cache");

            logInfo(logId);
            if (wb != null) {
                wb.write(response.getOutputStream());
                // wb.flush();
                // wb.close();
                response.flushBuffer();
            } else if (inputStream != null) {
                bufferedInputStream = new BufferedInputStream(inputStream);
                bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                int byread = 0;
                do {
                    // read fixed-size buffer from input stream
                    byread = bufferedInputStream.read(buffer);
                    if (byread == -1) {
                        break;
                    } else {
                        // Write buffer to output stream
                        bufferedOutputStream.write(buffer, 0, byread);
                    }
                } while (true);
                bufferedOutputStream.flush();
                bufferedInputStream.close();
                bufferedOutputStream.close();
                response.flushBuffer();
            }
        } catch (IOException ex) {
            if (!isClientAbort(ex)) throw new SystemException("想定外例外発生！");
        }
    }

    private static void logInfo(String[] logs) throws SystemException {

        if (logs != null && log.isInfoEnabled()) {
            if (logs.length == 1) {
                log.info(MessageUtil.getMessage(logs[0]));

            } else if (logs.length == 2) {
                log.info(MessageUtil.getMessage(logs[0], logs[1]));

            } else if (logs.length == 3) {
                log.info(MessageUtil.getMessage(logs[0], logs[1], logs[2]));

            } else if (logs.length == 4) {
                log.info(MessageUtil.getMessage(logs[0], logs[1], logs[2], logs[3]));

            } else if (logs.length == 5) {
                log.info(MessageUtil.getMessage(logs[0], logs[1], logs[2], logs[3], logs[4]));

            }
        }
    }

    /**
     * Put HSSFWorkbook object to response object and start download.
     *
     * @param workbook
     *            HSSFWorkbook
     * @param response
     *            HttpServletResponse
     * @param fileName
     *            String
     * @throws SystemException 
     */
    public static void downloadFile(XSSFWorkbook workbook, HttpServletResponse response, String fileName) throws SystemException {
        try {
            // M1720S00005_20180711_START
            int length = 0;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] byteArray = new byte[CommonConstants.DOWNLOAD_BUFFSIZE];
            workbook.write(os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

            String extension = fileName.substring(fileName.lastIndexOf("."));
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd_HHmmss");
            String newFileName = String.format("%1$s_%2$s%3$s", title, dateFormat.format(new Date()), extension);
            OutputStream out = response.getOutputStream();
            // response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(newFileName, "UTF-8") + "\"");

            // Allow browser to cache download file
            response.setHeader("Pragma", "cache");
            response.setHeader("Cache-Control", "cache");
            // workbook.write(out);
            while ((length = inputStream.read(byteArray)) != -1) {
                out.write(byteArray, 0, length);
            }
            out.flush();
            out.close();
            inputStream.close();
            os.close();
            // M1720S00005_20180711_END
        } catch (Exception e) {
            if (!(e.getCause() instanceof SocketException) && !CommonUtils.isClientAbort(e)) throw new SystemException("想定外例外発生！");
        }
    }

    /**
     * connect firstStr, secondStr into 1 string. In case that length of (firstStr +
     * secondStr) is less than number, the return string is (firstStr + delimiter +
     * secondStr)
     *
     * @param firstStr
     *            String
     * @param secondStr
     *            String
     * @param delimiter
     *            String
     * @param number
     *            int
     * @return String
     */
    public static String connectNum(String firstStr, String secondStr, String delimiter, int number) {

        String tmpString = "";

        if ((GenericValidator.isBlankOrNull(firstStr)) || (GenericValidator.isBlankOrNull(secondStr))) {
            return CommonConstants.BLANK;
        }

        tmpString = firstStr + secondStr;

        if (null == delimiter) {
            return tmpString;
        }

        if (tmpString.length() == number) {

            return tmpString;
        }

        tmpString = firstStr + delimiter + secondStr;

        return tmpString;
    }

    /**
     * connect firstStr, secondStr(firstStr, secondStr are not mandatory) into 1
     * string. In case that length of (firstStr + secondStr) is less than number,
     * the return string is (firstStr + delimiter + secondStr)
     *
     * @param firstStr
     *            String
     * @param secondStr
     *            String
     * @param delimiter
     *            String
     * @param number
     *            int
     * @return String
     */
    public static String connectNumNotMandatory(String firstStr, String secondStr, String delimiter, int number) {

        String tmpString = "";

        if ((GenericValidator.isBlankOrNull(firstStr)) && (GenericValidator.isBlankOrNull(secondStr))) {
            return CommonConstants.BLANK;
        }

        tmpString = firstStr + secondStr;

        if (null == delimiter) {
            return tmpString;
        }

        if (tmpString.length() == number) {

            return tmpString;
        }

        tmpString = firstStr.trim() + delimiter + secondStr.trim();

        return tmpString;
    }

    /**
     * connect firstStr, secondStr, thirdStr into 1 string. In case that length of
     * (firstStr + secondStr + thirdStr) is less than number, the return string is
     * (firstStr + delimiter + secondStr + delimiter + thirdStr)
     *
     * @param subStr
     *            String[]
     * @param delimiter
     *            String
     * @param length
     *            String[]
     * @return String
     */
    public static String connectNum(String[] subStr, String delimiter, String[] length) {

        int totalLength = 0;
        String str = "";
        int[] len = { 0, 0, 0 };

        if ((subStr == null) || (length == null) || (subStr.length == 0) || (length.length == 0)) {

            return CommonConstants.BLANK;
        }

        for (int i = 0; i < subStr.length; i++) {
            if (subStr[i] == null) {
                return CommonConstants.BLANK;
            }
        }

        for (int i = 0; i < length.length; i++) {
            if ((!GenericValidator.isBlankOrNull(length[i])) && GenericValidator.isInt(length[i])) {
                len[i] = Integer.valueOf(length[i]).intValue();
                totalLength = totalLength + len[i];
                str = str + subStr[i];
            } else {
                return CommonConstants.BLANK;
            }
        }

        if (null == delimiter) {
            return str;
        }

        if (str.length() == totalLength) {
            return str;
        }

        if (subStr[0].length() < len[0]) {
            if ((subStr[1].length() < len[1]) && (subStr[2].length() <= len[2])) {
                str = subStr[0] + delimiter + subStr[1] + delimiter + subStr[2];
            }

            if ((subStr[1].length() >= len[1]) && (subStr[2].length() <= len[2])) {
                str = subStr[0] + delimiter + subStr[1] + subStr[2];
            }

        } else {

            if ((subStr[1].length() < len[1]) && (subStr[2].length() <= len[2])) {
                str = subStr[0] + subStr[1] + delimiter + subStr[2];
            }

            if ((subStr[1].length() >= len[1]) && (subStr[2].length() <= len[2])) {
                str = subStr[0] + subStr[1] + subStr[2];
            }
        }

        return str;
    }

    /**
     * connect firstStr, secondStr, thirdStr into 1 string(firstStr, secondStr,
     * thirdStr are not mandatory). In case that length of (firstStr + secondStr +
     * thirdStr) is less than number, the return string is (firstStr + delimiter +
     * secondStr + delimiter + thirdStr).
     *
     * @param subStr
     *            String[]
     * @param delimiter
     *            String
     * @return String
     */
    public static String connectNumNotMandatory(String[] subStr, String delimiter) {

        String str = "";
        int j = 0;

        if ((subStr == null) || (subStr.length == 0)) {
            return CommonConstants.BLANK;
        }

        for (int i = 0; i < subStr.length; i++) {
            if (GenericValidator.isBlankOrNull(subStr[i])) {
                str += delimiter;
            } else {
                if (i == 0) {
                    if (subStr[i].length() != 4) {
                        j = 0;
                        while (j < (4 - subStr[i].length())) {
                            str = str + '0';
                            j = j + 1;
                        }
                    }
                } else {
                    if (subStr[i].length() != 2) {
                        j = 0;
                        while (j < (2 - subStr[i].length())) {
                            str = str + '0';
                            j = j + 1;
                        }
                    }
                }

                str += subStr[i];
            }
        }

        return str;
    }

    /**
     * return a new date after adding daysToAdd
     *
     * @param date
     *            Date
     * @param daysToAdd
     *            int
     * @return new date with yyyyMMdd format
     */
    public static String addDaysToDate(Date date, int daysToAdd) throws Exception {

        FastDateFormat sdf = FastDateFormat.getInstance("yyyyMMdd");
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return sdf.format(now.getTime());

    }

    /**
     * Convert Date to String with 9999年99月99日 format
     *
     * @param date
     *            Date
     * @return String
     */
    public static String getJapaneseDate(Date date) {

        String dateStr = "";
        String newStr = "";
        String year = "";
        String month = "";
        String day = "";

        if (date == null) {
            return CommonConstants.BLANK;
        }

        dateStr = convertDateToString(date, CommonConstants.YYYYMMDD);
        year = subString(dateStr, 0, 4);
        month = subString(dateStr, 4, 6);
        day = subString(dateStr, 6, 8);
        newStr = year + CommonConstants.YEAR_STRING + month + CommonConstants.MONTH_STRING + day + CommonConstants.DAY_STRING;

        return newStr;
    }

    /**
     * convert Date to String following format
     *
     * @param date
     *            Date
     * @param format
     *            a format of date, such as yyyy/MM/dd, yyyyMMdd String
     * @return String
     */
    public static String convertDateToString(Date date, String format) {

        String str = "";
        FastDateFormat sformat = FastDateFormat.getInstance(format);

        if (date == null) {
            return CommonConstants.BLANK;
        }

        str = sformat.format(date);

        return str;
    }

    /**
     * Convert companyId to format 2-4-4
     *
     * @param companyId
     *            String
     * @return String
     */
    public static String getCompanyId(String companyId) {

        /* Declare variables */
        String split = "";
        String result = "";

        /* In case that 1st character of companyId is not "K" set companyId */
        /* Else set format 2-4-4 */
        if (companyId != null) {
            if (companyId.length() > 0) {
                split = companyId.substring(0, 1);
                if (("K".equals(split)) && (companyId.length() == 10)) {
                    result = companyId.substring(0, 2) + CommonConstants.DASH + companyId.substring(2, 6) + CommonConstants.DASH + companyId.substring(6);
                    return result;
                } else {
                    return companyId;
                }
            }
        } else {
            companyId = CommonConstants.BLANK;
        }

        /* Return data */
        return companyId;
    }

    /**
     * Convert engineerId to format 2-4-4
     *
     * @param engineerId
     *            String
     * @return String
     */
    public static String getEngineerId(String engineerId) {

        /* Declare variables */
        String split = "";
        String result = "";

        /* In case that 1st character of companyId is not "K" set companyId */
        /* Else set format 2-4-4 */
        if (engineerId != null) {
            if (engineerId.length() > 0) {
                split = engineerId.substring(0, 1);
                if (("E".equals(split)) && (engineerId.length() == 10)) {
                    result = engineerId.substring(0, 2) + CommonConstants.DASH + engineerId.substring(2, 6) + CommonConstants.DASH + engineerId.substring(6);
                    return result;
                } else {
                    return engineerId;
                }
            }
        }

        /* Return data */
        return engineerId;
    }

    /**
     * Convert String to format 2-4-4
     *
     * @param convertText
     *            String
     * @return String
     */
    public static String convertRegisNumberTo244(String convertText) {

        /* Declare variables */
        String split = "";

        /* In case that 1st character of companyId is not "K" set companyId */
        /* Else set format 2-4-4 */
        if (convertText != null) {
            if (convertText.length() > 0) {
                split = convertText.substring(0, 1);
                if (("C".equals(split) || "T".equals(split)) && (convertText.length() == 10)) {

                    StringBuffer buff = new StringBuffer();
                    buff.append(convertText.substring(0, 2));
                    buff.append(CommonConstants.DASH);
                    buff.append(convertText.substring(2, 6));
                    buff.append(CommonConstants.DASH);
                    buff.append(convertText.substring(6));
                    return buff.toString();
                } else {
                    return convertText;
                }
            }
        }

        /* Return data */
        return convertText;
    }

    /**
     * Convert Date to String with 9999年99月99日 format
     *
     * @param date
     *            String
     * @param space
     *            String
     * @return String
     */
    public static String convertToJapaneseDate(String date, String space) {

        String newStr = "";
        String year = "";
        String month = "";
        String day = "";

        if (date == null) {
            return CommonConstants.BLANK;
        }

        // 20080202
        if ((date != null) && ((date.trim().length() == 8) || (date.trim().length() == 14))) {
            year = subString(date, 0, 4);
            month = subString(date, 4, 6);
            day = subString(date, 6, 8);
            newStr = year + space + CommonConstants.YEAR_STRING + space + month + space + CommonConstants.MONTH_STRING + space + day + space + CommonConstants.DAY_STRING;
        } else if ((date != null) && (date.trim().length() == 19)) {
            // 2008/02/02 00:00:00
            year = subString(date, 0, 4);
            month = subString(date, 5, 7);
            day = subString(date, 8, 10);
            newStr = year + space + CommonConstants.YEAR_STRING + space + month + space + CommonConstants.MONTH_STRING + space + day + space + CommonConstants.DAY_STRING;
        } else if ((date != null) && (date.trim().length() == 6)) {
            // 200802
            year = subString(date, 0, 4);
            month = subString(date, 4, 6);
            newStr = year + space + CommonConstants.YEAR_STRING + space + month + space + CommonConstants.MONTH_STRING;
        } else if ((date != null) && (date.length() > 10)) {
            // 2008/02/02 00:00:00:00000
            year = subString(date, 0, 4);
            month = subString(date, 5, 7);
            day = subString(date, 8, 10);
            newStr = year + space + CommonConstants.YEAR_STRING + space + month + space + CommonConstants.MONTH_STRING + space + day + space + CommonConstants.DAY_STRING;
        }

        return newStr;
    }


    /**
     * Create xml byte based on Document object.
     *
     * @param document
     *            Document
     * @return byte[]
     * @throws SystemException 
     */
    public static byte[] convertXmlDocument(Document document) throws SystemException {

        // Blob xmlData;
        /* (1-1) Declare a "DOMImplementationRegistry" object (①). */
        DOMImplementationRegistry registry;

        /* (1-2) Declare a "DOMImplementationLS" object (②). */
        DOMImplementationLS domImplementationLS;

        /* (1-3) Declare a "LSSerializer" object (③). */
        LSSerializer lsSerializer;

        /* (1-4) Declare a "LSOutput" object (④). */
        LSOutput lsOutput;

        /* ((1-5) Declare a "ByteArrayOutputStream" object (⑤). */
        ByteArrayOutputStream xmlStream;

        /* (1-6) Declare a "Byte[]" object (⑥). */
        byte[] arrXmlData = null;

        /*
         * (5) Call method "newInstance" of "DOMImplementationRegistry" class and set
         * returned value to ③.
         */
        try {

            // Register property of DOMImplementationRegistry in System.
            System.setProperty("javax.xml.accessExternalSchema", "file, http");

            // Create an instance for "DOMImplementationRegistry" object.
            registry = DOMImplementationRegistry.newInstance();

            // Create a "DOMImplementationLS" object.
            domImplementationLS = (DOMImplementationLS) registry.getDOMImplementation(CommonConstants.BLANK);

            // Create "LSSerializer" object to write document.
            lsSerializer = domImplementationLS.createLSSerializer();

            // Prepare "LSOutput" object with "ByteArrayOutputStream" object.
            lsOutput = domImplementationLS.createLSOutput();

            // (7) Create an instance of "ByteArrayOutputStream" object and set returned
            // value to ⑤.
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            // (8) Call method "setByteStream" of ④ with 1 argument ⑤.
            lsOutput.setByteStream(outStream);

            // Convert data of "document" object to "byte[]" array.
            // (9) Call method "write" of ③ with 2 arguments are "document" object, ④.
            lsSerializer.write(document, lsOutput);

            // (10) Call method "getByteStream" of ④, cast returned value to
            // "ByteArrayOutputStream"
            // object and set to ⑤.
            xmlStream = (ByteArrayOutputStream) lsOutput.getByteStream();

            // (11) Call method "toByteArray" of ⑧ and set returned value to ⑨
            arrXmlData = xmlStream.toByteArray();

            // (12) Return ⑥ and end process.
            return arrXmlData;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new SystemException("想定外例外発生！");
        }
    }

    /**
     * format Id as XX-XXXX-XXXX
     *
     * @param id
     *            String
     * @return String
     */
    public static String formatId(String id) {

        if (id == null) {
            return "";
        } else if (id.length() != 10) {
            return id;
        }

        return id.substring(0, 2) + "-" + id.substring(2, 6) + "-" + id.substring(6, 10);
    }

    /**
     * format Location as [999ﾟ 99' 99"]
     *
     * @param location
     *            String
     * @return String
     */
    public static String formatLocation(String location) {

        String first = "";
        String second = "";
        String third = "";
        StringBuffer stringBuffer = new StringBuffer();
        if (GenericValidator.isBlankOrNull(location)) {
            return "";
        } else if (location.length() >= 5) {
            first = location.substring(0, 3);
            second = location.substring(3, 5);
            third = location.substring(5);
        } else if (location.length() >= 3) {
            first = location.substring(0, 3);
            second = location.substring(3);
        } else {
            first = location;
        }
        first = first.trim();
        second = second.trim();
        third = third.trim();
        if (!GenericValidator.isBlankOrNull(first)) {
            // M1720A00086_START
            stringBuffer.append(first + "ﾟ ");
            // M1720A00086_END
        } else {
            stringBuffer.append("ﾟ ");
        }

        if (!GenericValidator.isBlankOrNull(second)) {
            stringBuffer.append(second + "' ");
        } else {
            // M1720A00086_START
            stringBuffer.append("' ");
            // M1720A00086_END
        }

        if (!GenericValidator.isBlankOrNull(third)) {
            stringBuffer.append(third + "\"");
        } else {
            stringBuffer.append("\"");
        }

        return stringBuffer.toString();
    }

    /**
     * Format registration number
     *
     * @param number
     *            String
     * @return String
     */
    public static String formatRegistrationNumber(String number) {

        /* Declare variables */
        StringBuffer result = null;
        String jpName = "";

        if (!GenericValidator.isBlankOrNull(number)) {
            result = new StringBuffer(number);
            jpName = number.substring(0, 1);
            result.insert(result.indexOf(jpName) + 1, " ");
            result.insert(result.indexOf("-"), " ");
            result.insert(result.indexOf("-") + 1, " ");

            return result.toString();
        } else {
            return "";
        }
    }

    public static String formatRegNumber(final String registrationNumber) {

        String result = registrationNumber;
        // String space_hyphen_space = " - ";

        if (StringUtils.isEmpty(result)) {
            return "";
        }

        if ((result.startsWith("C") || result.startsWith("T")) && (result.length() == 10)) {
            StringBuilder stringBuilder = new StringBuilder(result);
            stringBuilder.insert(2, CommonConstants.DASH);
            stringBuilder.insert(7, CommonConstants.DASH);
            result = stringBuilder.toString();
        }

        return result;
    }

    public static String formatRegisNumber(final String registrationNumber) {
        String result = registrationNumber;
        String hyphen = "-";

        if (StringUtils.isEmpty(result)) {
            return "";
        }

        if ((result.startsWith("C") || result.startsWith("T")) && (result.length() == 10)) {
            StringBuilder stringBuilder = new StringBuilder(result);
            stringBuilder.insert(2, hyphen);
            stringBuilder.insert(7, hyphen);
            result = stringBuilder.toString();
        }

        return result;
    }

    /**
     * Format telephone
     *
     * @param number
     *            String
     * @return String
     */
    public static String formatTelephone(String number) {

        /* Declare variables */
        StringBuffer result = null;

        if (!GenericValidator.isBlankOrNull(number)) {
            result = new StringBuffer(number);

            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Split telephone
     *
     * @param number
     *            String
     * @return String
     */
    public static String[] splitTelephone(String number) {
        String[] result = { "", "", "" };

        if (!GenericValidator.isBlankOrNull(number)) {
            String[] temp = number.split("-");
            if (temp.length > 0) {
                result[0] = temp[0];
            }
            if (temp.length > 1) {
                result[1] = temp[1];
            }
            if (temp.length > 2) {
                result[2] = temp[2];
            }
        }
        return result;
    }

    /**
     * Split Fax
     *
     * @param number
     *            String
     * @return String
     */
    public static String[] splitFax(String number) {
        String[] result = { "", "", "" };

        if (!GenericValidator.isBlankOrNull(number)) {
            String[] temp = number.split("-");
            if (temp.length > 0) {
                result[0] = temp[0];
            }
            if (temp.length > 1) {
                result[1] = temp[1];
            }
            if (temp.length > 2) {
                result[2] = temp[2];
            }
        }
        return result;
    }

    /**
     * Format zip code
     *
     * @param number
     *            String
     * @return String
     */
    public static String formatZipCode(String number) {

        /* Declare variables */
        StringBuffer result = null;

        if (!GenericValidator.isBlankOrNull(number)) {
            result = new StringBuffer(number);
            // result.insert(3, " - ");
            if (result.length() >= 3) {
                result.insert(3, " - ");
            } else {
                result.append(" - ");
            }

            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Format ConstructionRegistrationNumber
     *
     * @param number
     *            String
     * @return String
     */
    public static String formatConstructionRegistrationNumber(String number) {

        /* Declare variables */
        StringBuffer result = null;

        if (!GenericValidator.isBlankOrNull(number)) {
            result = new StringBuffer(number);
            result.insert(4, " - ");

            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Format PermitNumber
     *
     * @param permitNumber
     *            String
     * @return String
     */
    public static String formatPermitNumber(String permitNumber) {

        if (GenericValidator.isBlankOrNull(permitNumber) || permitNumber.length() <= 2) {
            return "";
        } else {
            return combinePermitNumber(permitNumber.substring(0, 2), permitNumber.substring(2));
        }
    }

    /**
     * check min and max of year
     *
     * @param number
     *            String
     * @return String
     */
    public static boolean isExistingDate(String number) {

        boolean result = false;
        int value = -1;
        try {
            value = Integer.parseInt(number);
            result = isExistingDate(value);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    public static boolean isJoinStockCompany(String companyName, String[] jointStock) {

        boolean flag = false;
        for (int i = 0; i < jointStock.length; i++) {
            if (companyName.indexOf(jointStock[i]) >= 0) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static boolean isValidJoinStockCompany(String companyName) {

        boolean flag = true;
        for (int i = 0; i < JOINTSTOCK_KANJI.length; i++) {
            if (JOINTSTOCK_KANJI[i].equals(companyName)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            for (int i = 0; i < JOINTSTOCK_COMPANY_KANJI.length; i++) {
                if (JOINTSTOCK_COMPANY_KANJI[i].equals(companyName)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * Check JoinStockCompanyKana name
     *
     * @param companyName
     * @return boolean
     */
    public static boolean isJoinStockCompanyKana(String companyName) {

        return isJoinStockCompany(companyName, JOINTSTOCK_COMPANY_KANA);
    }

    /**
     * Check JoinStockCompanyKanj name
     *
     * @param companyName
     * @return boolean
     */
    public static boolean isJoinStockCompanyKanj(String companyName) {

        return isJoinStockCompany(companyName, JOINTSTOCK_COMPANY_KANJI);
    }

    /**
     * Check JoinStockKanj name
     *
     * @param companyName
     * @return boolean
     */
    public static boolean isJoinStockKanj(String companyName) {

        return isJoinStockCompany(companyName, JOINTSTOCK_KANJI);
    }

    /**
     * check min and max of year
     *
     * @param number
     *            int
     * @return String
     */
    public static boolean isExistingDate(int number) {

        boolean result = false;
        if (number >= MIN_YEAR && number < MAX_YEAR) {
            result = true;
        }
        return result;
    }

    /**
     * 入力値の前後一文字以外に" "（全角スペース）が含まれていること。。
     *
     * @param japaneseName
     *            String
     *
     * @return boolean オブジェクト
     */
    public static boolean isJapaneseName(String japaneseName) {

        if (!GenericValidator.isBlankOrNull(CommonUtils.trim(japaneseName))) {
            // Get index of the first double-byte space
            int firstIdx = japaneseName.indexOf(CommonConstants.SPACE_FULL_SIZE);
            // Get index of the last double-byte space
            int lastIdx = japaneseName.lastIndexOf(CommonConstants.SPACE_FULL_SIZE);
            // Trim begin and end double-byte space
            String japaneseNameTrim = StringUtil.trimW(japaneseName);
            // Split CompanyUserName to check CompanyUserName has 2 phrases
            String[] japaneseNameSplit = japaneseNameTrim.split(CommonConstants.SPACE_FULL_SIZE);
            // Check double-byte space before and behind input value
            if (firstIdx == 0 || lastIdx == (japaneseName.length() - 1) || japaneseNameSplit.length != 2) {
                return false;
            }
        }
        return true;
    }

    

    /**
     * this method is used to combine tofukenGovernorPermissionNumber,
     * countryTrafficMinisterPermissionNumber, hokkaidoGovernorPermissionNumber
     *
     * @param permitNumber01
     * @param permitNumber02
     * @return
     */
    public static String combinePermitNumber(String permitNumber01, String permitNumber02) {

        if (GenericValidator.isBlankOrNull(permitNumber01) || GenericValidator.isBlankOrNull(permitNumber02)) {
            return "";
        }
        try {
            return String.format("%1$s%2$s", permitNumber01, permitNumber02);

        } catch (NumberFormatException e) {
            String perNum02 = "000000";
            perNum02 += permitNumber02;
            return permitNumber01 + perNum02.substring(perNum02.length() - 6);
        }
    }

    /**
     * this method is used to split tofukenGovernorPermissionNumber,
     * countryTrafficMinisterPermissionNumber, hokkaidoGovernorPermissionNumber
     *
     * @param permitNumber
     * @return permitNumber01, permitNumber02
     */
    public static String[] splitPermitNumber(String permitNumber) {

        String[] permitNumbers = new String[2];
        if (GenericValidator.isBlankOrNull(permitNumber)) {
            permitNumbers[0] = "";
            permitNumbers[1] = "";

        } else if (permitNumber.length() > 2) {
            permitNumbers[0] = permitNumber.substring(0, 2);
            if (permitNumbers[0].compareTo("80") == 0 && permitNumber.length() == 8) {
                permitNumbers[1] = permitNumber.substring(3);
            } else if ((permitNumbers[0].compareTo("81") >= 0 && permitNumbers[0].compareTo("93") <= 0) && permitNumber.length() == 8) {
                permitNumbers[1] = permitNumber.substring(4);
            } else {
                permitNumbers[1] = permitNumber.substring(2);
            }
        } else if (permitNumber.length() == 2) {
            permitNumbers[0] = permitNumber.substring(0, 2);
            permitNumbers[1] = CommonConstants.BLANK;
        }
        return permitNumbers;
    }

    /**
     * Combine buildingConsultants.
     *
     * @param buildingConsultant01
     *            buildingConsultant01
     * @param buildingConsultant02
     *            buildingConsultant02
     * @return buildingConsultant
     */
    public static String combineBuildingConsultant(String buildingConsultant01, String buildingConsultant02) {

        return String.format("建%1$s-%2$s", buildingConsultant01, buildingConsultant02);
    }

    /**
     * Split buildingConsultants. 建01-345678 -> {"01", "345678"}
     *
     * @param buildingConsultant
     * @return
     */
    public static String[] splitBuildingConsultant(String buildingConsultant) {

        if (GenericValidator.isBlankOrNull(buildingConsultant)) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
        try {
            String[] buildingConsultants = buildingConsultant.split(CommonConstants.DASH);
            if (buildingConsultants[0].startsWith("建")) {
                buildingConsultants[0] = buildingConsultants[0].substring(1);
            }
            return buildingConsultants;
        } catch (Exception e) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
    }

    /**
     * Combine geologicalSurveyorDealer.
     *
     * @param geologicalSurveyorDealer01
     *            geologicalSurveyorDealer01
     * @param geologicalSurveyorDealer02
     *            geologicalSurveyorDealer02
     * @return buildingConsultant
     */
    public static String combineGeologicalSurveyorDealer(String geologicalSurveyorDealer01, String geologicalSurveyorDealer02) {

        return String.format("質%1$s-%2$s", geologicalSurveyorDealer01, geologicalSurveyorDealer02);
    }

    public static String[] splitGeologicalSurveyorDealer(String geologicalSurveyorDealer) {

        if (GenericValidator.isBlankOrNull(geologicalSurveyorDealer)) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
        try {
            String[] geologicalSurveyorDealers = geologicalSurveyorDealer.split(CommonConstants.DASH);
            if (geologicalSurveyorDealers[0].startsWith("質")) {
                geologicalSurveyorDealers[0] = geologicalSurveyorDealers[0].substring(1);
            }
            return geologicalSurveyorDealers;
        } catch (Exception e) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
    }

    /**
     * Combine compensationConsultant.
     *
     * @param compensationConsultant01
     *            compensationConsultant01
     * @param compensationConsultant02
     *            compensationConsultant02
     * @return compensationConsultant
     */
    public static String combineCompensationConsultant(String compensationConsultant01, String compensationConsultant02) {

        return String.format("補%1$s-%2$s", compensationConsultant01, compensationConsultant02);
    }

    public static String[] splitCompensationConsultant(String compensationConsultant) {

        if (GenericValidator.isBlankOrNull(compensationConsultant)) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
        try {
            String[] compensationConsultants = compensationConsultant.split(CommonConstants.DASH);
            if (compensationConsultants[0].startsWith("補")) {
                compensationConsultants[0] = compensationConsultants[0].substring(1);
            }
            return compensationConsultants;
        } catch (Exception e) {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
    }

    /**
     * Combine surveyorDealer.
     *
     * @param surveyorDealer01
     *            surveyorDealer01
     * @param surveyorDealer02
     *            surveyorDealer02
     * @return surveyorDealer
     */
    public static String combineSurveyorDealer(String surveyorDealer01, String surveyorDealer02) {

        return String.format("（%1$s）%2$s", surveyorDealer01, surveyorDealer02);
    }

    /**
     * Combine firstClassArchitect.
     *
     * @param firstClassArchitect01
     *            firstClassArchitect01
     * @param firstClassArchitect02
     *            firstClassArchitect02
     * @return firstClassArchitect
     */
    public static String combineFirstClassArchitect(String firstClassArchitect01, String firstClassArchitect02) {

        return String.format("%1$s-%2$s", firstClassArchitect01, firstClassArchitect02);
    }

    /**
     * Split registrationNumber.
     *
     * @param registrationNumber
     *            registrationNumber
     * @return array contains 2 registrationNumber except SurveyorDealer and
     *         FirstClassArchitect
     */
    public static String[] splitRegistrationNumber(String registrationNumber) {

        if (!GenericValidator.isBlankOrNull(registrationNumber)) {
            String[] strArr = registrationNumber.split(CommonConstants.DASH);

            if (strArr[0].length() >= 3) {
                if (strArr.length < 2) {
                    return new String[] { strArr[0].substring(1), CommonConstants.BLANK };
                } else {
                    return new String[] { strArr[0].substring(1), strArr[1] };
                }
            } else {
                if (strArr[0].length() < 1) {
                    return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
                } else {
                    if (strArr.length < 2) {
                        return new String[] { strArr[0].substring(1), CommonConstants.BLANK };
                    } else {
                        return new String[] { strArr[0].substring(1), strArr[1] };
                    }
                }
            }

        } else {
            return new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
    }

    /**
     * Split SurveyorDealer.
     *
     * @param surveyorDealer
     *            surveyorDealer
     * @return array contains 2 surveyDealer
     */
    public static String[] splitSurveyorDealer(String surveyorDealer) {

        Pattern p = Pattern.compile("[(|（|)|）]");
        String m[] = null;
        if (!GenericValidator.isBlankOrNull(surveyorDealer)) {
            m = p.split(surveyorDealer);
            if (m != null && m.length == 3) {
                m = new String[] { m[1], m[2] };
            } else {
                m = new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
            }
        } else {
            m = new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }

        return m;
    }

    /**
     * Split FirstClassArchitect.
     *
     * @param firstClassArchitect
     *            firstClassArchitect
     * @return array contains 2 firstClassArchitect
     */
    public static String[] splitFirstClassArchitect(String firstClassArchitect) {

        String[] m = null;
        if (!GenericValidator.isBlankOrNull(firstClassArchitect)) {
            int index = firstClassArchitect.indexOf("-");
            if (index < 0) {
                m = new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
            } else {
                String f1 = firstClassArchitect.substring(0, index);
                String f2 = firstClassArchitect.substring(index + 1);
                m = new String[] { f1, f2 };
            }
        } else {
            m = new String[] { CommonConstants.BLANK, CommonConstants.BLANK };
        }
        return m;
    }


    /**
     * Convert from W3C document to byte[].
     *
     * @param Node
     *            node
     * @return bytes array contains 2 firstClassArchitect
     */
    public static byte[] doc2bytes(Node node) {

        try {
            Source source = new DOMSource(node);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return out.toByteArray();
        } catch (TransformerException e) {
            log.error("変換失敗", e);
        }
        return null;
    }

    /**
     * Convert Node to XML
     *
     * @param node
     * @return StringObject
     */
    public static String xmlToString(Node node) {

        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerException e) {
            log.error("変換失敗", e);
        }
        return null;
    }

    /**
     * Return String format [99ﾟ 99' 99"]
     *
     * @param strSource
     *            String
     * @return String
     */
    public static String formatCoordinate(String strSource) {

        if (strSource == null) {
            return CommonConstants.BLANK;
        }

        StringBuffer rtnValue = new StringBuffer();
        if (CheckUtil.isDigits(strSource) && strSource.length() == 7) {
            String str1 = strSource.substring(0, 3).trim();
            String str2 = strSource.substring(3, 5).trim();
            String str3 = strSource.substring(5);
            rtnValue.append(str1);
            rtnValue.append("ﾟ ");
            rtnValue.append(str2);
            rtnValue.append("' ");
            rtnValue.append(str3);
            rtnValue.append("\"");
        }
        return rtnValue.toString();
    }

    /**
     * Check duplicate of 2 periods. Return true if they are duplicate.
     *
     * @param from1
     *            Date
     * @param to1
     *            Date
     * @param from2
     *            Date
     * @param to2
     *            Date
     * @return boolean
     */
    public static boolean isPeriodDuplicate(Date from1, Date to1, Date from2, Date to2) {

        if ((from1 != null) && (to1 != null) && (from2 != null) && (to2 != null)) {
            if (from1.after(to2) || to1.before(from2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether fileName is type of typeExtension
     *
     * @param filename
     * @param typeExtension
     * @return
     */
    public static boolean checkExtension(String fileName, String[] typeExtension) {

        if (GenericValidator.isBlankOrNull(fileName) || typeExtension == null || typeExtension.length == 0) {
            return false;
        }

        int indexOfDot = fileName.lastIndexOf(".");
        if (indexOfDot == -1) {
            return false;
        }

        String fileType = fileName.substring(indexOfDot + 1);

        for (String type : typeExtension) {
            if (fileType.equalsIgnoreCase(type)) {
                return true;
            }
        }

        return false;
    }

    public static String formatWaterLineName(String waterLineNameInput) {

        StringBuilder results = new StringBuilder();
        String[] waterLineNames = null;
        if (!GenericValidator.isBlankOrNull(waterLineNameInput.trim())) {
            waterLineNames = CommonUtils.splitWaterLine(waterLineNameInput);
            if (waterLineNames.length > 0) {
                results.append(waterLineNames[0]);
            }
            if ((waterLineNames.length > 1) && (!GenericValidator.isBlankOrNull(waterLineNames[1].trim()))) {
                results.append(CommonConstants.SPACE_FULL_SIZE);
                results.append(waterLineNames[1]);
            }
            if (waterLineNames.length > 2 && (!GenericValidator.isBlankOrNull(waterLineNames[2].trim()))) {
                results.append(CommonConstants.SPACE_FULL_SIZE);
                results.append(waterLineNames[2]);
            }
            if (waterLineNames.length > 3 && (!GenericValidator.isBlankOrNull(waterLineNames[3].trim()))) {
                results.append(CommonConstants.DASH);
                results.append(waterLineNames[3]);
            }
        }
        return results.toString();
    }


    /**
     * Format number.
     *
     * @param number
     * @param type
     * @return String
     */
    public static String formatNumber(String number, int type) {

        StringBuffer stringBuffer = null;
        switch (type) {
        case 1:
            stringBuffer = new StringBuffer("建 " + number);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 2:
            stringBuffer = new StringBuffer("質 " + number);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 3:
            stringBuffer = new StringBuffer("補 " + number);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 4:
            stringBuffer = new StringBuffer("（" + number);
            return stringBuffer.toString().replaceFirst("-", "） ");
        default:
            return "";
        }
    }

    public static String formatRegistrationNumberForDisplay(String number, int type) {
        if (GenericValidator.isBlankOrNull(number)) {
            return CommonConstants.BLANK;
        }

        StringBuffer stringBuffer = new StringBuffer(number);
        switch (type) {
        case 1:
            stringBuffer.insert(stringBuffer.indexOf("建") + 1, CommonConstants.SPACE_HALF_SIZE);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 2:
            stringBuffer.insert(stringBuffer.indexOf("質") + 1, CommonConstants.SPACE_HALF_SIZE);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 3:
            stringBuffer.insert(stringBuffer.indexOf("補") + 1, CommonConstants.SPACE_HALF_SIZE);
            stringBuffer.insert(stringBuffer.indexOf("-"), " ");
            stringBuffer.insert(stringBuffer.indexOf("-") + 1, " ");
            return stringBuffer.toString();

        case 4:
            int index = stringBuffer.indexOf("）");
            if (index < 0) {
                index = stringBuffer.indexOf(")");
            }
            stringBuffer.insert(index + 1, CommonConstants.SPACE_HALF_SIZE);
            return stringBuffer.toString();
        default:
            return "";
        }
    }

    public static String[] formatLengthCSV(String[] arr, int length) {

        String DEFAULT_VALUE = null;

        if (arr == null) {
            return null;

        } else if (arr.length >= length) {
            return arr;

        } else {
            String[] rtArr = new String[length];
            for (int i = 0; i < arr.length; i++) {
                rtArr[i] = arr[i];
            }
            for (int i = arr.length; i < length; i++) {
                rtArr[i] = DEFAULT_VALUE;
            }
            return rtArr;
        }
    }


    /**
     * This method is used to retrieve url help from application.properties file.
     *
     * @param key
     * @return url help
     */
    public static String getHelpUrl(String key) {
        String url = null;
        try {
            // get url from properties file.
            url = ApplicationProperties.getProperty(key);

        } catch (Exception e) {
            return "";
        }
        return (url != null) ? /* contextPath + */url : "";
    }

    /**
     * This method is used to remove type of company (KABU).
     *
     * @param strCmpny
     *            Company string need to delete type of company
     * @return company string
     */
    public static String removeKabuCompany(String strCmpny) {

        StringBuffer sb = new StringBuffer(strCmpny);
        int idx = -1;
        for (int i = 0; i < JOINTSTOCK_KANJI.length; i++) {
            idx = strCmpny.indexOf(JOINTSTOCK_KANJI[i]);
            if (idx >= 0) {
                sb.replace(idx, idx + 3, "");
                break;
            }
        }

        return removeJointStockCompany(sb.toString());
    }

    public static String sanitize(String value) {
        if (GenericValidator.isBlankOrNull(value)) {
            return value;
        }
        StringBuffer escaped = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            switch (value.charAt(i)) {
            case '<':
                escaped.append("&lt;");
                break;
            case '>':
                escaped.append("&gt;");
                break;
            case '&':
                escaped.append("&amp;");
                break;
            default:
                escaped.append(value.charAt(i));
                break;
            }
        }
        return escaped.toString();
    }

    public static String sanitizeForScript(String value) {
        if (GenericValidator.isBlankOrNull(value)) {
            return value;
        }
        StringBuffer escaped = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            switch (value.charAt(i)) {
            case '\"':
                escaped.append("\\\"");
                break;
            case '\'':
                escaped.append("\\\'");
                break;
            case '\\':
                escaped.append("\\\\");
                break;
            default:
                escaped.append(value.charAt(i));
                break;
            }
        }
        return escaped.toString();
    }

    /**
     * This method is used to remove type of joinstock company
     *
     * @param strCmpny
     *            Company string need to delete type of company
     * @return company string
     */
    public static String removeJointStockCompany(String strCmpny) {

        StringBuffer sb = new StringBuffer(strCmpny);
        int idx = -1;
        for (int i = 0; i < JOINTSTOCK_COMPANY_KANJI.length; i++) {
            idx = strCmpny.indexOf(JOINTSTOCK_COMPANY_KANJI[i]);
            if (idx >= 0) {
                sb.replace(idx, idx + 4, "");
                break;
            }
        }

        return sb.toString();
    }

    /**
     * This method is used check input 2 different company type
     *
     * @param strCmpny
     *            Company string need to delete type of company
     * @return company string
     */
    public static boolean checkDifferentFormKanji(String strCmp) {

        String tempStr = CommonConstants.BLANK;
        int cnt = 1;

        if (isJoinStockCompanyKanj(strCmp)) {
            tempStr = removeJointStockCompany(strCmp);

            for (int i = 0; i < JOINTSTOCK_COMPANY_KANJI.length; i++) {
                if (tempStr.contains(JOINTSTOCK_COMPANY_KANJI[i])) {
                    cnt++;
                }
            }
        }

        if (isJoinStockKanj(strCmp)) {
            tempStr = removeKabuCompany(strCmp);

            for (int i = 0; i < JOINTSTOCK_KANJI.length; i++) {
                if (tempStr.contains(JOINTSTOCK_KANJI[i])) {
                    cnt++;
                }
            }
        }

        if (cnt > 1) {
            return false;
        }
        return true;
    }

    /**
     * This method is used check one company type has input just one or many times
     *
     * @param strCmpny
     *            Company string need to delete type of company
     * @return company string
     */
    public static boolean checkDuplicateFormKanji(String strCmp) {

        String strToCheck = strCmp;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int cnt = 0;

        while (i < JOINTSTOCK_KANJI.length) {
            if (strToCheck.indexOf(JOINTSTOCK_KANJI[i]) < strToCheck.lastIndexOf(JOINTSTOCK_KANJI[i])) {
                cnt++;
            }
            if (cnt >= 1) {
                return false;
            }
            i++;
        }

        while (j < JOINTSTOCK_COMPANY_KANJI.length) {
            if (strToCheck.indexOf(JOINTSTOCK_COMPANY_KANJI[j]) < strToCheck.lastIndexOf(JOINTSTOCK_COMPANY_KANJI[j])) {
                cnt++;
            }

            if (cnt >= 1) {
                return false;
            }
            j++;
        }

        while (k < JOINTSTOCK_KANJI.length) {
            l = 0;
            while (l < JOINTSTOCK_COMPANY_KANJI.length) {
                if (strToCheck.indexOf(JOINTSTOCK_COMPANY_KANJI[l]) >= 0 && strToCheck.indexOf(JOINTSTOCK_KANJI[k]) >= 0) {
                    cnt++;
                }
                if (cnt >= 1) {
                    return false;
                }
                l++;
            }
            k++;
        }

        return true;
    }

    public static String getEmailAddrLog(String[] mailAddrs) {

        StringBuilder mailAddrLog = null;
        if (mailAddrs == null || mailAddrs.length == 0) {
            return "";
        }

        mailAddrLog = new StringBuilder("");
        for (String mailAddr : mailAddrs) {
            mailAddrLog.append(mailAddr);
            mailAddrLog.append(";");
        }

        mailAddrLog.deleteCharAt(mailAddrLog.length() - 1);
        return mailAddrLog.toString();
    }

    public static String getMailParamsLog(final Object content) {

        StringBuilder paramsLog = new StringBuilder("");
        Class<?> clazz = content.getClass();
        Method[] methods = clazz.getMethods();
        String methodName = null;
        Object val = null;

        for (Method method : methods) {
            methodName = method.getName();
            if (isGetterMethod(method) && isMyMethod(methodName)) {
                try {
                    val = method.invoke(content);
                    paramsLog.append(getPropertyName(methodName)).append(" = ").append(((val != null) ? val.toString() : "")).append(" ;");
                } catch (Exception e) {
                    log.error("メソッド起動失敗", e);
                }
            }
        }

        if (paramsLog.length() > 0) {
            paramsLog.deleteCharAt(paramsLog.length() - 1);
        }
        return paramsLog.toString();
    }

    /**
     *
     * @param method
     * @return
     */
    private static boolean isGetterMethod(Method method) {

        boolean takesNoParam = method != null && method.getParameterTypes().length == 0;

        if (takesNoParam == false) {
            return false;
        }

        String methodName = method.getName();
        boolean isGetter = methodName.startsWith("get")
                || methodName.startsWith("is");

        return  isGetter;
    }

    /**
     *
     * @param methodName
     * @return
     */
    private static boolean isMyMethod(String methodName) {

        boolean isOk = true;
        String[] objMethods = { "getClass", "toString", "hashCode", "compareTo", "equals", "wait", "notify", "notifyAll" };
        for (String objMethod : objMethods) {
            isOk = isOk && !objMethod.equals(methodName);
        }
        return isOk;
    }

    private static String getPropertyName(String methodName) {

        String property = null;
        if (GenericValidator.isBlankOrNull(methodName)) {
            return "";
        }

        property = methodName;
        if (property.startsWith("get")
                && property.length() > 3) {
            property = property.substring(3);
            property = property.substring(0, 1).toLowerCase() + property.substring(1);

        } else if (property.startsWith("is")
                && property.length() > 2) {
            property = property.substring(2);
            property = property.substring(0, 1).toLowerCase() + property.substring(1);
        }

        return property;
    }

    public static String concatPercent(String source) {

        if (!GenericValidator.isBlankOrNull(source)) {
            return source + "%";
        } else {
            return CommonConstants.BLANK;
        }
    }

    public static String getKanaIndex(String kanaInput) {

        String result = "";
        String[] kanaIndex = { "ワ", "ラ", "ヤ", "マ", "ハ", "ナ", "タ", "サ", "カ", "ア" };
        String[] kana0 = { "ワ", "ヲ", "ン" };
        String[] kana1 = { "ラ", "リ", "ル", "レ", "ロ" };
        String[] kana2 = { "ヤ", "ユ", "ヨ", "ャ", "ュ", "ョ" };
        String[] kana3 = { "マ", "ミ", "ム", "メ", "モ" };
        String[] kana4 = { "ハ", "ヒ", "フ", "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ" };
        String[] kana5 = { "ナ", "ニ", "ヌ", "ネ", "ノ" };
        String[] kana6 = { "タ", "チ", "ツ", "テ", "ト", "ダ", "ヂ", "ヅ", "デ", "ド", "ッ" };
        String[] kana7 = { "サ", "シ", "ス", "セ", "ソ", "ザ", "ジ", "ズ", "ゼ", "ゾ" };
        String[] kana8 = { "カ", "キ", "ク", "ケ", "コ", "ガ", "ギ", "グ", "ゲ", "ゴ" };
        String[] kana9 = { "ア", "イ", "ウ", "エ", "オ", "ァ", "ィ", "ゥ", "ェ", "ォ" };
        Object[] kanaArray = new Object[10];
        String firstChar = null;

        kanaArray[0] = kana0;
        kanaArray[1] = kana1;
        kanaArray[2] = kana2;
        kanaArray[3] = kana3;
        kanaArray[4] = kana4;
        kanaArray[5] = kana5;
        kanaArray[6] = kana6;
        kanaArray[7] = kana7;
        kanaArray[8] = kana8;
        kanaArray[9] = kana9;

        if (kanaInput != null) {
            firstChar = kanaInput.substring(0, 1);
        }
        for (int i = 0; i < kanaArray.length; i++) {
            String[] temp = (String[]) kanaArray[i];
            boolean hasTrue = false;
            for (int j = 0; j < temp.length; j++) {
                if (temp[j].equals(firstChar)) {
                    result = kanaIndex[i];
                    hasTrue = true;
                    break;
                }
            }
            if (hasTrue) {
                break;
            }
        }

        return result;
    }


    /**
     * Delete cookie used by multisubmit prevention
     *
     * @param name
     * @param request
     * @param response
     */
    public static void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    /**
     * Escapes the characters in a <code>String</code> to be suitable to pass to an
     * SQL query.
     *
     * @param sql
     *            the string to escape, may be null
     * @return a new String, escaped for SQL, <code>blank</code> if null string
     *         input
     */
    public static String escapeSql(String sql) {
        // Oracle12c_r2でエスケープ文字「％」と「＿」対応済みので、「％」と「＿」はエスケープの対象外です。
        // String pattern = "%％_＿'\\";
        String pattern = "%_'\\";
        StringBuffer buffer = new StringBuffer(CommonConstants.BLANK);
        if (sql != null) {
            for (int i = 0; i < sql.length(); i++) {
                if (pattern.indexOf(sql.charAt(i)) >= 0) {
                    if (sql.charAt(i) == '\'') {
                        buffer.append("\'");
                    } else {
                        buffer.append("\\");
                    }
                }
                buffer.append(sql.charAt(i));
            }
        }
        return buffer.toString();
    }

    /**
     * Add search condition before %
     *
     * @param source
     *            search condition
     * @return 'searchCondition%' ESCAPE '\\'
     */
    public static String prefixLike(String source) {
        return " '" + escapeSql(source) + "%' ESCAPE '\\' ";
    }

    /**
     * Add search condition after %
     *
     * @param source
     *            search condition
     * @return '%searchCondition' ESCAPE '\\'
     */
    public static String suffixLike(String source) {
        return " '%" + escapeSql(source) + "' ESCAPE '\\' ";
    }

    /**
     * Add search condition between %
     *
     * @param source
     *            search condition
     * @return '%searchCondition%' ESCAPE '\\'
     */
    public static String searchLike(String source) {
        return " '%" + escapeSql(source) + "%' ESCAPE '\\' ";
    }

    /**
     * Add search condition without %
     *
     * @param source
     *            search condition
     * @return 'searchCondition'
     */
    public static String concatEscape(String source) {
        return escapeSql(source);
    }

    /**
     * @param input
     *            bytes encoded with UTF-8.
     * @return decoded string
     */
    public static String decode(byte[] input) {

        char[] output = new char[input.length];
        int i = 0;
        int j = 0;
        while (i < input.length) {
            int b = input[i++] & 0xff;
            switch (b >>> 5) {
            default:
                output[j++] = (char) (b & 0x7f);
                break;
            case 6:
                int y = b & 0x1f;
                int x = input[i++] & 0x3f;
                output[j++] = (char) (y << 6 | x);
                break;
            case 7:
                assert (b & 0x10) == 0 : "UTF8Decoder does not handle 32-bit characters";
                int z = b & 0x0f;
                y = input[i++] & 0x3f;
                x = input[i++] & 0x3f;
                int asint = (z << 12 | y << 6 | x);
                output[j++] = (char) asint;
                break;
            }
        }
        return convertForSjis(new String(output, 0, j));
    }

    /**
     * 2008/06/06 Fixbug UAA0002 rename tag 修正前 => 修正後 発注機関分類コード => 発注機関コード 住所分類コード
     * => 施工場所コード 業務対象地域分類コード => 業務対象地域コード
     */
    public static final String[] internalTagNames = { "修正前", "発注機関分類コード", "住所分類コード", "業務対象地域分類コード" };
    public static final String[] externalTagNames = { "修正後", "発注機関コード", "施工場所コード", "業務対象地域コード" };

    public static void renameTag(Document document, String[] oldTagNames, String[] newTagNames) {

        NodeList nodeList = null;
        Node node = null;
        Element element = null;
        short nodeType = 0;
        for (int i = 0; i < oldTagNames.length; i++) {
            nodeList = document.getElementsByTagName(oldTagNames[i]);
            if (nodeList.getLength() == 0) {
                continue;
            }
            /* (3) Loop on from "0" to length of ① by "i" */
            for (int k = 0; k < nodeList.getLength(); k++) {
                /* (3-1) Assign ①[i] to ② */
                node = nodeList.item(k);
                /* (3-2) Call "getNodeType" function of ②, set returned value to ③ */
                nodeType = node.getNodeType();
                /* (3-3) In case ③ equals to "ELEMENT_NODE" property of "Node" class */
                if (nodeType == Node.ELEMENT_NODE) {
                    /*
                     * (3-3-1) Cast ② to "Element" object, then set returned value to ④
                     */
                    element = (Element) node;
                    document.renameNode(element, element.getNamespaceURI(), newTagNames[i]);
                }
            }
        }
    }

    // ++ Fix error SK2A0055
    public static boolean isEmSizeAlphaNumeric(String input) {

        if (null == input) {
            return true;
        }

        return CheckUtil.isPatternMatch("/^[Ａ-Ｚａ-ｚ０-９]*$/", input);
    }


    public static String removeZeroBefore(String value) {
        String returnValue = value;
        char zeroCharacter = '0';
        int index = 0;
        if (!GenericValidator.isBlankOrNull(value)) {
            int size = value.length();
            for (index = 0; index < size; index++) {
                if (zeroCharacter != value.charAt(index)) {
                    break;
                }
            }

            if (index < size - 1) {
                returnValue = value.substring(index);
            } else {
                returnValue = CommonConstants.BLANK;
            }
        }
        return returnValue;
    }


    /**
     * set print date
     */
    public static String setPrintDate(String processStartDate) {
        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(CommonConstants.TIME_FORMAT);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH時mm分ss秒");
        String returnDate = "";
        try {
            LocalDateTime dt = LocalDateTime.parse(processStartDate, simpleDateFormat);
            returnDate = format.format(dt);
        } catch (DateTimeParseException e) {
            log.error("変換失敗", e);
        }
        return returnDate;
    }

    /**
     * split ratio
     */
    public static String[] splitRatio(String ratio) {
        String[] result = null;
        int pos;
        if (GenericValidator.isBlankOrNull(ratio)) {
            return new String[] { "" };
        }
        pos = ratio.indexOf(".");
        if (pos != -1) {
            result = new String[] { ratio.substring(0, pos), ratio.substring(pos + 1) };
        } else {
            result = new String[] { ratio };
        }
        return result;
    }

    /**
     * check if ratio is number
     *
     * @param ratio
     * @return boolean
     */
    public static boolean isRatio(String ratio) {
        return CheckUtil.isPatternMatch("/^[\\.\\d]*$/", ratio);
    }

    /**
     * check if half size ratio is number
     *
     * @param ratio
     * @return boolean
     */
    public static boolean isHalfSizeRatio(String ratio) {
        return CheckUtil.isPatternMatch("/^[\\.0-9]*$/", ratio);
    }

    /**
     * check if ratio is number
     *
     * @param ratio
     * @return boolean
     */
    public static boolean isRatioNoPeriod(String ratio) {
        return CheckUtil.isPatternMatch("/^[\\d]*$/", ratio);
    }


    public static void releaseMemory() {
        System.runFinalization();
        System.gc();
    }

    /**
     * Use to convert some character which can not display in e-mail
     *
     * @param convert
     *            string need to convert
     * @return string is converted
     */
    public static String convertToUtf8(String convert) {

        convert = convert.replace('\u2015', '\u2014'); // ―(EM DASH)
        convert = convert.replace('\uff5e', '\u301c'); // ～(WAVE DASH)
        convert = convert.replace('\u2225', '\u2016'); // ∥(DOUBLE VERTICAL LINE)
        convert = convert.replace('\uff0d', '\u2212'); // ～(WAVE DASH)
        convert = convert.replace('\uffe0', '\u00a2'); // ～(WAVE DASH)
        convert = convert.replace('\uffe1', '\u00a3'); // ～(WAVE DASH)
        convert = convert.replace('\uffe2', '\u00ac'); // ～(WAVE DASH)

        return convert;
    }


    public static String getIdWithoutRepeat(String id) {
        if (id.indexOf("-") > 0) {
            return id.substring(0, id.indexOf("-"));
        } else {
            return id;
        }
    }


    /**
     * formatToJapaneseTimestamp
     *
     * @param date
     *            Date
     * @return yyyy年MM月dd日 HH:mm:ss
     */
    public static String formatToJapaneseTimestamp(Date date) {

        if (date == null) {
            return CommonConstants.BLANK;
        } else {
            return FastDateFormat.getInstance("yyyy年MM月dd日 HH:mm:ss").format(date);
        }
    }

    /**
     * formatToJapaneseTimestamp
     *
     * @param date
     *            Date
     * @return yyyy年MM月dd日 hh:mm:ss
     */
    public static String addDayToJapaneseDate(Date date, int daysToAdd) {

        if (date == null) {
            return CommonConstants.BLANK;
        }
        FastDateFormat sdf = FastDateFormat.getInstance("yyyy年MM月dd日");
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return sdf.format(now.getTime());
    }

    public static void sort(List<String> keys, List<Object[]> list) {
        Object[] obj = null;
        String karteId = null;
        String key = null;
        int pos = 0;
        if ((keys == null)
                || (list == null)
                || (keys.size() != list.size())) {
            return;
        }

        for (int i = 0; i < keys.size(); i++) {
            obj = list.get(i);
            karteId = (obj[1] == null) ? CommonConstants.BLANK : obj[1].toString();
            key = keys.get(i);
            if (!key.equals(karteId)) {
                pos = getKeyPosition(list, key, i);
                if (pos > -1) {
                    Collections.swap(list, i, pos);
                }
            }
        }
    }

    private static int getKeyPosition(List<Object[]> list, String key, int from) {
        int pos = 0;
        String karteId = null;
        Object[] obj = null;
        boolean found = false;
        if (list == null
                || list.isEmpty()
                || (from > list.size())) {
            return -1;
        }

        for (int i = from; i < list.size(); i++) {
            pos = i;
            obj = list.get(i);
            karteId = (obj[1] == null) ? CommonConstants.BLANK : obj[1].toString();
            if (key.equals(karteId)) {
                found = true;
                break;
            }
        }

        if (!found) {
            pos = -1;
        }

        return pos;
    }

    /**
     * Use to convert some character which can not display in SVF
     *
     * @param node
     */
    public static void convertToSjis(Node node) {

        if (node != null) {
            if (node.hasChildNodes()) {
                NodeList list = node.getChildNodes();

                for (int i = 0; i < list.getLength(); i++) {
                    convertToSjis(list.item(i));
                }
            } else {
                if (node.getNodeType() == Node.TEXT_NODE) {
                    String value = node.getNodeValue();
                    if (value != null) {
                        value = value.replace('\u2014', '\u2015'); // ―(EM DASH)
                        value = value.replace('\u301c', '\uff5e'); // ～(WAVE DASH)
                        value = value.replace('\u2016', '\u2225'); // ∥(DOUBLE VERTICAL LINE)
                        value = value.replace('\u2212', '\uff0d'); // ～(WAVE DASH)
                        value = value.replace('\u00a2', '\uffe0'); // ～(WAVE DASH)
                        value = value.replace('\u00a3', '\uffe1'); // ～(WAVE DASH)
                        value = value.replace('\u00ac', '\uffe2'); // ～(WAVE DASH)

                        node.setNodeValue(value);
                    }
                }
            }
        }
    }

    /**
     * 文字コードSJISで入出力する場合のコード変換を行う。<br>
     * （JAVAのコードマッピングは「SJIS:0x815C -> UTF-8:U+2014」と「UTF-8:U+2015 ->
     * SJIS:0x815C」であるため、<br>
     * 間に「UTF-8:U+2014 -> UTF-8:U+2015 」の変換が必要）
     *
     * @param value
     *            変換対象文字列
     * @return 変換後の文字列
     */
    public static String convertForSjis(String value) {
        if (value != null) {
            value = value.replace('\u2014', '\u2015'); // ―(EM DASH)
            value = value.replace('\u301c', '\uff5e'); // ～(WAVE DASH)
            value = value.replace('\u2016', '\u2225'); // ∥(DOUBLE VERTICAL LINE)
            value = value.replace('\u2212', '\uff0d'); // －(MINUS SIGN)
            // 以下はコード変換可能（文字化けしない）だが、コードポイントが変わるため、変換しておく。
            // （「SJIS:0x8191 -> UTF-8:U+00A2」と「UTF-8:U+FFE0 -> SJIS:0x8191」、「UTF-8:U+00A2 ->
            // SJIS:0x8191」がある）
            value = value.replace('\u00a2', '\uffe0'); // ￠(CENT SIGN)
            value = value.replace('\u00a3', '\uffe1'); // ￡(POUND SIGN)
            value = value.replace('\u00ac', '\uffe2'); // ￢(NOT SIGN)
            // 以下はJISX0208の範囲外のため対応しない
            // MIDLINE HORIZONTAL ELLIPSIS [U+22EF]
        }
        return value;
    }

    /**
     * split waterLineName
     *
     * @param waterLineName
     * @return index
     */
    public static String[] splitWaterLine(String waterLineName) {
        String[] waterLines = null;
        int waterLineName2Pos = -1;
        int waterLineName3Pos = -1;
        int waterLineNameOtherPos = -1;

        // init
        waterLines = new String[4];
        for (int i = 0; i < waterLines.length; i++) {
            waterLines[i] = "";
        }

        waterLineName2Pos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE);
        if (waterLineName2Pos != -1) {
            waterLines[0] = waterLineName.substring(0, waterLineName2Pos);
            waterLineName3Pos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE, waterLineName2Pos + 1);
            if (waterLineName3Pos != -1) {
                waterLines[1] = waterLineName.substring(waterLineName2Pos + 1, waterLineName3Pos);
                waterLineNameOtherPos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE, waterLineName3Pos + 1);
                if (waterLineNameOtherPos != -1) {
                    waterLines[2] = waterLineName.substring(waterLineName3Pos + 1, waterLineNameOtherPos);
                    waterLines[3] = waterLineName.substring(waterLineNameOtherPos + 1);
                } else {
                    waterLines[2] = waterLineName.substring(waterLineName3Pos + 1);
                }
            } else {
                waterLines[1] = waterLineName.substring(waterLineName2Pos + 1);
            }
        } else {
            waterLines[0] = waterLineName;
        }

        return waterLines;
    }

    /**
     * get index of waterLineNameOther in waterLineName
     *
     * @param waterLineName
     * @return index
     */
    public static int getWaterLineOtherPos(String waterLineName) {
        int waterLineName2Pos = -1;
        int waterLineName3Pos = -1;
        int waterLineNameOtherPos = -1;

        waterLineName2Pos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE);
        if (waterLineName2Pos != -1) {
            waterLineName3Pos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE, waterLineName2Pos + 1);
            if (waterLineName3Pos != -1) {
                waterLineNameOtherPos = waterLineName.indexOf(CommonConstants.SPACE_FULL_SIZE, waterLineName3Pos + 1);
            }
        }
        return waterLineNameOtherPos;
    }



    /**
     * Format float number (remove meaningless zeros, add zero for float number that
     * misses integer part). Throw exception in case input floatNum is illegal float
     * number. <br>
     * Ex: <br>
     * <code>0012.30</code> becomes <code>12.30</code> <br>
     * <code>.1</code> becomes <code>0.1</code> <br>
     * <code>-00.00</code> becomes <code>-0.00</code> <br>
     * <code>1.</code> throw <code>Exception</code> <br>
     * <code>.</code> throw <code>Exception</code> <br>
     * <code>1a</code> throw <code>Exception</code>
     *
     * @param floatNum
     *            The float number to be formated.
     * @throws Throw
     *             RuntimeException when floatNum is not a valid float number.
     */
    public static String formatToFloatNumber(String floatNum) throws Exception {

        if (GenericValidator.isBlankOrNull(floatNum)) {
            throw new Exception();
        } else if (floatNum.charAt(floatNum.length() - 1) == '.') {
            throw new Exception();
        }

        int index;
        StringBuffer result = null;

        // Check if floatNum is a legal float number
        Float.parseFloat(floatNum);

        if (floatNum.matches("[+-]?0+")) {
            result = new StringBuffer(floatNum.substring(0, floatNum.indexOf("0") + 1));
        } else if (floatNum.matches("[+-]?0+[1-9.][0-9.]+")) {
            result = new StringBuffer();
            index = 0;

            if (floatNum.charAt(index) == '-' || floatNum.charAt(index) == '+') {
                result.append(floatNum.charAt(index));
                index++;
            }
            while (floatNum.charAt(index) == '0') {
                index++;
            }

            result.append(floatNum.substring(index));

        } else {
            result = new StringBuffer(floatNum);
        }

        if (result.indexOf(".") >= 0) {

            // Add heading zero
            if (result.toString().matches("[+-]?[.][0-9]*")) {
                result.insert(result.indexOf("."), '0');
            }
        }
        return result.toString();
    }


    /**
     * String
     *
     * @param Stringの日付("yyyyMMddHHmmss")もしくはStringの日付("yyyyMMddHHmm")をDate型に変換する
     * @return Date
     */
    public static Date parseString(String date) {
        DateTimeFormatter sdf = null;
        if (date != null) {
            if (date.length() == 8) {
                sdf = DateTimeFormatter.ofPattern("yyyyMMdd");
            } else if (date.length() == 14) {
                sdf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            }
            if (sdf != null) {
                Date date1 = Date.from(LocalDateTime.parse(date, sdf).atZone(ZoneId.systemDefault()).toInstant());
                return date1;
            }
        }
        return null;
    }

    /**
     * formatToJapaneseTimestampB
     *
     * @param date
     *            Date
     * @return yyyy年MM月dd日 HH:mm
     */
    public static String formatToJapaneseTimestampB(Date date) {

        if (date == null) {
            return CommonConstants.BLANK;
        } else {
            return FastDateFormat.getInstance("yyyy年MM月dd日 HH:mm").format(date);
        }
    }

    /**
     * 半角英数字記号チェック
     *
     * @param input
     * @param sign
     * @return boolean
     */
    public static boolean isAlphaNumericSign(String input, String sign) {
        if (null == input || CommonConstants.BLANK.equals(input)) {
            return true;
        }
        return CheckUtil.isPatternMatch("/^[A-Za-z0-9" + sign + "]*$/", input);
    }


    /**
     * 番号のゼロサプレス(オールゼロならブランク）
     *
     * @param input
     * @return output ゼロサプレスした番号
     */
    public static String zeroSuppress(String input) {

        if (GenericValidator.isBlankOrNull(input)) {
            return "";
        }

        // ゼロサプレス
        String output = Integer.valueOf(input).toString();
        // 結果が0の場合、ブランクに変換
        if (output.equals("0")) {
            output = "";
        }

        return output;
    }

    /**
     * 値をLong型→Integer型までコンバートする。
     *
     * @param obj
     * @return output Integer型の値
     */
    public static int parseInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 半角数字を全角数字に変換する。
     *
     * @param param
     * @return data
     */
    public static String convertNumber1ByteTo2Byte(Object param) {
        String data = param.toString();
        data = data.toString().replaceAll("0", "０");
        data = data.toString().replaceAll("1", "１");
        data = data.toString().replaceAll("2", "２");
        data = data.toString().replaceAll("3", "３");
        data = data.toString().replaceAll("4", "４");
        data = data.toString().replaceAll("5", "５");
        data = data.toString().replaceAll("6", "６");
        data = data.toString().replaceAll("7", "７");
        data = data.toString().replaceAll("8", "８");
        data = data.toString().replaceAll("9", "９");
        return data;
    }

    public static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static String decode(String value) {
        return new String(Base64.getDecoder().decode(value));
    }


    public static String convertNullToEmpty(Object obj) {
        String result = "";
        if (obj != null) {
            result = String.valueOf(obj);
        }
        return result;
    }

    public static Object getObject(Class<?> cls, String[] properties, Object[] array) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (array == null) {
            return null;
        }
        if (properties.length == array.length) {
            Object obj = cls.newInstance();
            for (int i = 0; i < properties.length; i++) {
                BeanUtils.copyProperty(obj, properties[i], convertNullToEmpty(array[i]));
            }
            return obj;
        }
        return null;
    }

    public static Object covertItemPropertyNullToEmpty(Object o) {
        Class<?> cls = o.getClass().getSuperclass();
        Field[] Fields = cls.getDeclaredFields();

        for (Field field : Fields) {
            String FieldName = field.getName();
            PropertyDescriptor propertyDescriptor;
            try {
                propertyDescriptor = new PropertyDescriptor(FieldName, cls);
                Method set = propertyDescriptor.getWriteMethod();
                Method get = propertyDescriptor.getReadMethod();
                String value = CommonUtils.convertNullToEmpty(get.invoke(o, new Object[] {}));
                set.invoke(o, new Object[] { value });
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                continue;
            }
        }
        return o;
    }

    /**
     * 判断正負数がこと
     */
    public static boolean isPrice(String input) {
        // CT8customize_NC001_START

        if (null == input) {
            return true;
        }

        return CheckUtil.isPatternMatch("/^(-)?[1-9][0-9]*$/", input);
        // CT8customize_NC001_END
    }

    /**
     * 判断全角、半角英数字と特別Charがこと
     */
    public static boolean isFullSizeStringAndAlphaNumericWithSpecialChar(String input, String specialChar) {
        char[] array = input.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            String s = String.valueOf(c);
            if (!(isFullSizeString(s) || isAlphaNumeric(s) || specialChar.equals(s))) {
                return false;
            }
        }
        return true;
    }

    /**
     * String
     *
     * @param Stringの日付("yyyy-MM-dd HH:mm:ss")はStringの日付("yyyyMMdd")に変換する
     * @return Date
     */
    public static String shortDateForm(String date) {
        // CT8customize_NC001_START
        DateTimeFormatter dfFrom = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dfTo = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime parseDate;
        try {
            parseDate = LocalDateTime.parse(date, dfFrom);
            date = dfTo.format(parseDate);
        } catch (DateTimeParseException e) {
            return "";
        }
        return date;
        // CT8customize_NC001_END
    }

    //CT8customize_NC001_START
    /**
     * 編集した和暦年月日(例：平成18年3月12日)を取得します。
     * @param strDate
     * @return
     */
    public static String getJpnDate(String strDate){
        String result = "";
        if (!GenericValidator.isBlankOrNull(strDate)) {
            DateUtil dateUtil = new DateUtil();
            if (dateUtil.setDate(strDate)) {
                result = dateUtil.getFormatJapaneseDate().replace(" ", "");
            }
        }
        return result;
    }
    //CT8customize_NC001_END

    /**
     * 金額から金額（西暦）を得る。
     * @param amount 金額
     * @return 金額（西暦）
     */
    public static String getAmountJpn(String amount) {
        StringBuilder amountJpn = new StringBuilder();
        //CT8customize_NC001_START
        if (!GenericValidator.isBlankOrNull(amount)) {
            if(amount.length() > 8) {
                amountJpn.append(amount.substring(0, amount.length() - 8)).append("億");
                amountJpn.append(formatAmountJpn(amount.substring(amount.length() - 8 , amount.length() - 4), "万"));
                amountJpn.append(formatAmountJpn(amount, ""));
            } else if(amount.length() > 4) {
                amountJpn.append(amount.substring(0, amount.length() - 4)).append("万");
                amountJpn.append(formatAmountJpn(amount, ""));
            } else if(amount.length() > 0) {
                amountJpn.append(amount.substring(0));
            }
        }
        return amountJpn.append("円").toString();
        //CT8customize_NC001_END
    }

    //CT8customize_NC001_START
    /**
     * 金額から金額（西暦）を得る。
     * @param subAmount
     * @param typeFormat
     * @return
     */
    private static StringBuilder formatAmountJpn(String subAmount, String typeFormat) {
        StringBuilder amountJpn = new StringBuilder();
        if (Integer.parseInt(subAmount.substring(subAmount.length() - 4)) > 0) {
            amountJpn.append(Integer.parseInt(subAmount.substring(subAmount.length() - 4).toString())).append(typeFormat);
        }
        return amountJpn;
    }
    //CT8customize_NC001_END

    /**
     * Format zip code
     *
     * @param number
     *            String
     * @return String
     */
    public static String formatZipCodeNoSpace(String number) {

        /* Declare variables */
        StringBuffer result = null;

        if (!GenericValidator.isBlankOrNull(number)) {
            result = new StringBuffer(number);
            // result.insert(3, "-");
            if (result.indexOf("-") < 0) {
                if (result.length() >= 3) {
                    result.insert(3, "-");
                } else {
                    result.append("-");
                }
            }
            return result.toString();
        } else {
            return "";
        }
    }

    public static String getIdWithReplace(String id) {
        if (id.indexOf("-") > 0) {
            return id.replaceAll("-", "");
        } else {
            return id;
        }
    }

    /**
     *
     * @param userId
     * @param fileType
     * @return
     * @throws BusinessException
     */
    public static String createFileName(String userId, String fileType) {

        FastDateFormat dateFormat = null;
        dateFormat = FastDateFormat.getInstance("yyyyMMdd_HHmmssSSS");
        String newFileName = String.format("%1$s_%2$s%3$s", userId,
                        dateFormat.format(new Date()), fileType);

        return newFileName;
    }

    /**
     * org.apache.catalina.connector.ClientAbortExceptionの場合、処理なし
     * @param e
     * @return
     */
    public static boolean isClientAbort(Exception e) {
        // ユーザがファイルダウンロードのキャンセルを選択した場合
        return "org.apache.catalina.connector.ClientAbortException".equals(e.getClass().getName());
    }

    /**
     * TextAareaの内容は画面表示用フォーマット
     * @param text
     * @param type
     * @return String
     */
    // M1720A00032_START
    public static String formatTextAarea(String text, String type) {
        String result = "";
        if (StringUtils.isEmpty(text)) {
            return result;
        }
        result = CommonUtils.sanitize(text);
        result = result.replaceAll(type, "<br/>");
        return result;
    }
    // M1720A00032_END


    // M1712JATG-0048_20180814_START
    public static void setFieldNullToBlank(Object obj) {
        Class<?> supCls = obj.getClass().getSuperclass();
        Class<?> cls = obj.getClass();
        Field[] supFields = supCls.getDeclaredFields();
        Field[] fields = cls.getDeclaredFields();

        try {
            for (Field field : supFields) {
                // String型以外はスキップ
                if (field.getType() != String.class) {
                    continue;
                }
                // String型、かつ、nullであれば空文字を設定
                field.setAccessible(true);
                String value = (String) field.get(obj);
                if (value == null) {
                    field.set(obj, "");
                }
            }
            for (Field field : fields) {
                // String型以外はスキップ
                if (field.getType() != String.class) {
                    continue;
                }
                // String型、かつ、nullであれば空文字を設定
                field.setAccessible(true);
                String value = (String) field.get(obj);
                if (value == null) {
                    field.set(obj, "");
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            try {
                throw new SystemException("想定外条件発生");
            } catch (SystemException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    // M1712JATG-0048_20180814_END
    //CT8customize_NC001_END
    // M1720SK0073_20181003_START
    /**
     * change \n to \r\n;
     *
     * @param source
     *            String
     * @return String
     */
    public static String replaceOldNewLineCharacter(String source) {
        String result = source;
        if (!GenericValidator.isBlankOrNull(result)) {
            if (!result.contains(CommonConstants.CRLF_LINE)) {
                result = result.replace(CommonConstants.LF_LINE, CommonConstants.CRLF_LINE);
            }
        }
        if (result == null) {
            result = CommonConstants.BLANK;
        }

        return result;
    }

    /**
     * change \r\n to <br/>;
     *
     * @param source
     *            String
     * @return String
     */
    public static String replaceLineCharacterForDisplay(String source) {
        String result = source;
        if (!GenericValidator.isBlankOrNull(result)) {
            if (!result.contains(CommonConstants.BR_LINE)) {
                result = result.replace(CommonConstants.CRLF_LINE, CommonConstants.LF_LINE);
                result = result.replace(CommonConstants.LF_LINE, CommonConstants.BR_LINE);
            }
        }
        if (result == null) {
            result = CommonConstants.BLANK;
        }

        return result;
    }

    /**
     * change \r\n to &#xD;&#xA;
     *
     * @param source
     *            String
     * @return String
     */
    public static String replaceLineCharacterForPDFReport(String source) {
        String result = source;
        if (!GenericValidator.isBlankOrNull(result)) {
            if (!result.contains(CommonConstants.PDF_REPORT_LINE)) {
                result = result.replace(CommonConstants.CRLF_LINE, CommonConstants.LF_LINE);
                result = result.replace(CommonConstants.LF_LINE, CommonConstants.PDF_REPORT_LINE);
            }
        }
        if (result == null) {
            result = CommonConstants.BLANK;
        }

        return result;
    }
    // M1720SK0073_20181003_END

    // M1720QP0009_20181029_START
    /**
     * 日付妥当性チェック(yyyy/MM/dd)
     *
     * @param dateStr
     * @return true false
     */
    public static boolean isCorrectDate(String dateStr) {
        if (!GenericValidator.isBlankOrNull(dateStr) && dateStr.length() != 10) {
            return false;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(dateStr, df);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 日付妥当性チェック(yyyy/MM/dd HH:mm:ss)
     *
     * @param dateTimeStr
     * @return true false
     */
    public static boolean isCorrectDateTime(String dateTimeStr) {
        if (!GenericValidator.isBlankOrNull(dateTimeStr) && dateTimeStr.length() != 19) {
            return false;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(dateTimeStr, dtf);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
    // M1720QP0009_20181029_END
    
    /**
     * Check validation input special character
     * @param input
     * @return
     */
    public static boolean isNotSpecialCharacter(String input) {

        if (null == input) {
            return true;
        }
        return !CheckUtil.isPatternMatch("/[\"'&<>\\\\%]/", input);
    }

       /**
     * Returns true if the input is the decimal point
     * @param input
     * @return
     */
    public static boolean isDecimalPoint(String input) {
        if (null == input) {
            return true;
        }

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if the input is the phone number
     * @param input
     * @return
     */
    public static boolean isPhoneNumber(String input) {
        if (null == input) {
            return false;
        }

        if (input.length() != 13) {
            return false;
        }

        String phoneNumberRegex = "^(?:\\d{3}-\\d{4}-\\d{4}|\\d{4}-\\d{4}-\\d{3}|\\d{4}-\\d{3}-\\d{4}|\\d{5}-\\d{3}-\\d{3}|\\d{5}-\\d{2}-\\d{4}|\\d{5}-\\d{4}-\\d{2})$";
        return CheckUtil.isPatternMatch(phoneNumberRegex,input);
    }

    /**
     * Returns true if the input is corrected format of Email address
     * @param input
     * @return
     */
    public static boolean isEmail(String input) {
        String emailRegex = "/^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$/i";

        if (null == input || input.length() > 120) {
            return false;
        }

        return CheckUtil.isPatternMatch(emailRegex, input);
    }

    /**
     * Returns true if the input is in numeric format and exactly the number of digits after the decimal point
     * @param input: String
     * @param val : number allow after decimal point
     * @return
     */
    public static boolean isDecimalPointLimit(String input, int val) {
        if (isDecimalPoint(input)) {
            String[] decimalSplits = input.split("\\.");
            return (decimalSplits.length == 2 && decimalSplits[1].length() == val);
        }
        return false;
    }

    /**
     *  get error form BindingResult and mapping within error message
     * @param result
     * @param errorsMesage
     * @return list message error
     */
    public static void getErrorsFromBindingResult(BindingResult result, List<String> errorsMesage) {
        Class<? extends Object> clazz = null;
        if (result != null) {
            Object target = result.getTarget();
            if (target != null) {
                clazz = target.getClass();
            }
        }
        if (clazz == null) return;

        Map<String, LevelMessage> map = new HashMap<String, LevelMessage>();

        /* get all errors from BindingResult */
        for (Object ob : result.getAllErrors()) {
            /* get fieldErrors */
            String fieldErrors = ((FieldError) ob).getField();
            /* get annotation name */
            String anotationName = ((FieldError) ob).getCode();
            /* get all args */
            Object[] arguments = ((FieldError) ob).getArguments();
            String fieldName = "";
            String keyMap = "";
            try {
                String[] listField = fieldErrors.split("\\.");
                Field field = null;
                Class<? extends Object> rootClass = clazz;
                for(int i = 0; i < listField.length - 1; i++) {
                    Class<? extends Object> branchClass = rootClass.getDeclaredField(listField[i]).getType();
                    rootClass = branchClass;
                }
                field = rootClass.getDeclaredField(listField[listField.length - 1]);
                final FieldName fieldNameAnnotation = field.getAnnotation(FieldName.class);
                /* get field name japan */
                fieldName = fieldNameAnnotation.value();
                try {
                    final Position postionName = field.getAnnotation(Position.class);
                    /* get keyMap */
                    StringBuffer keyPos = new StringBuffer();
                    String key = String.valueOf(postionName.value()) + "";
                    for(int i = key.length(); i < 4; i ++) {
                        keyPos.append("0");
                    }
                    keyPos.append(key);
                    keyMap = keyPos.toString();
                }catch(Exception e) {
                    keyMap = fieldName;
                }
            } catch (NoSuchFieldException e) {
                log.error(e.getMessage());
            } catch (SecurityException e) {
                log.error(e.getMessage());
            }

            if (anotationName.equals(NOTBLANK) || anotationName.equals(NOTEMPTY) || anotationName.equals(SELECTCODE) || anotationName.equals(REQUIREDCITYCODE)) {
                if (checkPrioritize(map, keyMap, REQUIREDLEVEL)) {
                    if (anotationName.equals(SELECTCODE) || anotationName.equals(REQUIREDCITYCODE)) {
                        /* E0001 {0} は必須項目です。選択してください。 */
                        map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0001, fieldName));
                    } else {
                        /* E0002 {0} は必須項目です。入力してください。 */
                        map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0002, fieldName));
                    }
                }
            } else if (anotationName.equals(FULLSIZECHARACTOR) || anotationName.equals(HAFTSIZENUMBER)
                    || anotationName.equals(HAFTSIZEALPANUMERIC) || anotationName.equals(KATAKANA)
                    || anotationName.equals(NUMBERWITHDECIMALPOINT)) {
                if (checkPrioritize(map, keyMap, TYPECHARLEVEL)) {
                    switch (anotationName) {
                        case FULLSIZECHARACTOR:
                            /* E0006 {0} は全角文字で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0006, fieldName));
                            break;
                        case HAFTSIZENUMBER:
                            /* E0003 {0} は半角数字で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0003, fieldName));
                            break;
                        case HAFTSIZEALPANUMERIC:
                            /* E0005 {0} は半角英数字で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0005, fieldName));
                            break;
                        case KATAKANA:
                            /* E0007 {0} は全角カタカナで入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0007, fieldName));
                            break;
                        case NUMBERWITHDECIMALPOINT:
                            /* E0004 {0} は半角数字（小数点有り）で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0004, fieldName));
                            break;
                        case NUMBERWITHDECIMALPOINTLIMIT:
                            /* E0016 小数点以下は {0} 桁で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0016, fieldName));
                            break;
                        default:
                            break;
                    }
                }
            } else if (anotationName.equals(DATETIMEFOMAT) || anotationName.equals(URL)
                    || anotationName.equals(PHONENUMBER) || anotationName.equals(MAIL)) {
                if (checkPrioritize(map, keyMap, FORMATLEVEL)) {
                    switch (anotationName) {
                        case DATETIMEFOMAT:
                            /* E0008 {0} は日付形式で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0008, fieldName));
                            break;
                        case URL:
                            /* E0010 {0} はURLの形式で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0010, fieldName));
                            break;
                        case PHONENUMBER:
                            /* E0011 {0} は電話番号の形式で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0011, fieldName));
                            break;
                        case MAIL:
                            /* E0009 {0} はメールアドレスの形式で入力してください。 */
                            map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0009, fieldName));
                            break;
                        default:
                            break;
                    }
                }
            } else if (anotationName.equals(SIZE) || anotationName.equals(MINSIZE) || anotationName.equals(MAXSIZE) || anotationName.equals(MINSIZEVALIDBLANK)) {
                if (checkPrioritize(map, keyMap, SIZELEVEL)) {
                    int lengthArg = arguments.length;
                    switch (anotationName) {
                        case MINSIZE:
                        case MINSIZEVALIDBLANK:
                            /* E0013 {0} は{1} 文字以上入力してください。 */
                            if (lengthArg == 2) {
                                map.get(keyMap).setMessage(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0013), new Object[] { fieldName, arguments[1] }));
                            }
                            break;
                        case MAXSIZE:
                            /* E0012 {0} は{1} 文字以下で入力してください。 */
                            if (lengthArg == 2) {
                                map.get(keyMap).setMessage(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0012), new Object[] { fieldName, arguments[1] }));
                            }
                            break;
                        case SIZE:
                            /* E0014 {0} must be between {1} and {2} characters. */
                            if (lengthArg == 3) {
                                map.get(keyMap).setMessage(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0014), new Object[] { fieldName, arguments[2], arguments[1] }));
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else if (anotationName.equals(SPECIALCHARACTOR)){
                if (checkPrioritize(map, keyMap, SPECIALCHARACTERLEVEL)) {
                    /* E0015 {0} の入力値に使用禁止文字が含まれています。 */
                    map.get(keyMap).setMessage(getFormatMessage(CommonConstants.MS_E0015, fieldName));
                    break;
                }
            }
        }
        
        Map<String, LevelMessage> mapTree = new TreeMap<String, LevelMessage>(map);        
        mapTree.values().forEach(level -> {
            errorsMesage.add(level.getMessage());
        });
    }

    /**
     * Get message is formated
     * @param messageId
     * @param fieldName
     * @return
     */
    private static String getFormatMessage(String messageId, String fieldName) {
        return MessageFormat.format(MessageUtil.getMessage(messageId), new Object[] { fieldName });
    }

    /**
     * returns true if the error level takes precedence over the current one
     * @param map
     * @param item
     * @param level
     * @return 
     */
    private static boolean checkPrioritize(Map<String, LevelMessage> map, String keyMap, int level) {
        if (map.containsKey(keyMap)) {
            LevelMessage code = (LevelMessage) map.get(keyMap);
            if (code.getLevel() > level) {
                map.get(keyMap).setLevel(level);
                return true;
            } 
            return false;
        } else {
            map.put(keyMap, new LevelMessage(level));
            return true;
        }
    }
    
    /**
     * 
     * @param zipCode
     * @return
     */
    public static String[] lstZipCode(String zipCode) {
    	if (zipCode.contains(DASH)) {
    		return zipCode.split(DASH);
    	} else {
    		String zc1 = zipCode.substring(0, 3);
    		String zc2 = zipCode.substring(3, 7);
    		return new String[] {zc1, zc2};
    	}
    }

    /**
     * Left pad an int with zeros. 
     * @param number int
     * @param size int
     * @return String
     */
    public static String leftPadZero(int number, int size) {
        return org.apache.commons.lang3.StringUtils.leftPad(String.valueOf(number), size, CommonConstants.STR_0);
    }

    public static String formatNumber(Integer number) {
        if (number == null) {
            return CommonConstants.BLANK;
        }
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
        return df.format(number);
    }

}
