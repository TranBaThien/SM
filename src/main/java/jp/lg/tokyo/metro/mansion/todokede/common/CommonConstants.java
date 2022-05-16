/*
 * @(#)CommonConstants.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common;

import java.time.format.DateTimeFormatter;

/**
 * 本システムの定数を定義するクラスです。
 *
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.0
 * <pre>
 * ■変更履歴■
 * 2019/11/16 新規作成
 * </pre>
 */
public class CommonConstants {

    /**
     * Constants for encoding
     */
    public static final String ENCODE_UTF8 = "UTF-8";

    //yyyyMMdd
    public static final String YYYYMMDD = "yyyyMMdd";
    /* yyyyMMddhhmm */
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    //Format datetime: yyyy/MM/dd HH:mm
    public static final String FORMAT_DATETIME_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";

    public static final String FORMAT_DATETIME_EXPORT_CSV = "yyyyMMdd_HHmmss";

    //yyyy年MM月dd日
    public static final String DATE_FORMAT_TYPE_SEIREKI = "yyyy年MM月dd日";

    public static final String SLASH_YYYYMMDD = "yyyy/MM/dd";

    public static final String JP_CAL_STRING = "和暦";

    public static final String YEAR_STRING = "年";

    public static final String MONTH_STRING = "月";

    public static final String DAY_STRING = "日";

    public static final String SLASH = "/";

    public static final String APP_NAME = "/apartment";
    /**
     * Constants for format file import
     */
    public static final String[] FORMAT_FILE_IMPORT_CSV = {"csv"};

    /**
     * Constants for format of date time
     */
    public static final String TIME_FORMAT = "yyyyMMddHHmmss";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static final DateTimeFormatter SLASH_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static final String BLANK = "";
    public static final String COMMA = ",";

    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String SEVEN = "7";

    public static final String ZERO_ONE = "01";
    public static final String ZERO_FIVE = "05";
    public static final String ZERO_SIX = "06";

    public static final String ON = "on";

    public static final String OFF = "off";

    public static final String STR_13 = "13";
    public static final String STR_14 = "14";
    public static final String STR_20 = "20";
    public static final String STR_30 = "30";
    public static final String STR_34 = "34";
    public static final String STR_50 = "50";
    public static final String STR_100 = "100";
    public static final String STR_120 = "120";
    public static final String STR_300 = "300";
    public static final String STR_12 = "12";
    public static final String STR_11 = "11";
    public static final String STR_10 = "10";
    public static final String STR_9 = "9";
    public static final String STR_8 = "8";
    public static final String STR_7 = "7";
    public static final String STR_6 = "6";
    public static final String STR_5 = "5";
    public static final String STR_4 = "4";
    public static final String STR_3 = "3";
    public static final String STR_2 = "2";
    public static final String STR_1 = "1";
    public static final String STR_0 = "0";
    public static final String STR_TOTAL = "全";
    public static final String STR_CASE = "件";

    public static final int NUM_0 = 0;

    public static final int NUM_1 = 1;

    public static final int NUM_2 = 2;

    public static final int NUM_3 = 3;

    public static final int NUM_4 = 4;

    public static final int NUM_5 = 5;

    public static final int NUM_6 = 6;

    public static final int NUM_7 = 7;

    public static final int NUM_8 = 8;

    public static final int NUM_9 = 9;

    public static final int NUM_10 = 10;

    public static final int NUM_11 = 11;

    public static final int NUM_12 = 12;

    public static final int NUM_15 = 15;

    public static final int NUM_20 = 20;

    public static final int NUM_30 = 30;

    public static final int NUM_34 = 34;

    public static final int NUM_50 = 50;

    public static final int NUM_100 = 100;

    public static final int NUM_120 = 120;

    public static final int NUM_300 = 300;

    public static final String SPACE_HALF_SIZE = " ";

    public static final String SPACE_FULL_SIZE = "　";

    public static final int DOWNLOAD_BUFFSIZE = 1024;

    public static final String UND_LINE = "_";
    public static final String DOT = ".";
    public static final String DASH = "-";
    public static final String CRLF_LINE = "\r\n";
    public static final String LF_LINE = "\n";
    public static final String BR_LINE = "<br/>";
    public static final String PDF_REPORT_LINE = "&#xD;&#xA;";
    public static final String STR_STAR = "※";
    public static final String DECIMAL_FORMAT = "###,###,###,###";

    /**
     * COMPANY_LOGIN_LOGIC_IMPL
     * */
    public static final String TYPE_HASH = "typeHash";

    public static final String EMAIL_CONTENT_KEY = "CONTENT";
    public static final String NEXT_NOTIFICATION_DATE_MAX_KEY = "NEXT_NOTIFICATION_DATE_MAX";
    public static final String NOTIFICATION_RENEWAL_PERIOD_KEY = "NOTIFICATION_RENEWAL_PERIOD";
    public static final String PASSWORD_CHANGE_GUIDE = "PASSWORD_CHANGE_GUIDE";

    /**
     * Table name
     */
    public static final String TBL100 = "TBL100";
    public static final String TBL105 = "TBL105";
    public static final String TBL110 = "TBL110";
    public static final String TBL120 = "TBL120";
    public static final String TBL200 = "TBL200";
    public static final String TBL205 = "TBL205";
    public static final String TBL210 = "TBL210";
    public static final String TBL215 = "TBL215";
    public static final String TBL220 = "TBL220";
    public static final String TBL225 = "TBL225";
    public static final String TBL230 = "TBL230";
    public static final String TBL235 = "TBL235";
    public static final String TBL240 = "TBL240";
    public static final String TBL300 = "TBL300";
    public static final String TBL310 = "TBL310";
    public static final String TBL400 = "TBL400";
    public static final String TBS001 = "TBS001";

    /**
     * Sequence column NOTIFICATION_NO
     */
    public static final String COL_NOTIFICATION_NO = "NOTIFICATION_NO";
    public static final String COL_APARTMENT_ID = "APARTMENT_ID";
    public static final String COL_TEMP_NO = "TEMP_NO";
    //Confirm remove
    public static final String COL_PROPERTY_NUMBER = "PROPERTY_NUMBER";
    public static final String COL_HISTORY_NUMBER = "HISTORY_NUMBER";
    public static final String COL_LOGIN_ID = "LOGIN_ID";
    public static final String COL_USER_ID = "USER_ID";
    public static final String COL_RECEPTION_NO = "RECEPTION_NO";
    public static final String COL_ACCEPT_NO = "ACCEPT_NO";
    public static final String COL_ADVICE_NO = "ADVICE_NO";
    public static final String COL_INVESTIGATION_NO = "INVESTIGATION_NO";
    public static final String COL_SUPERVISED_NOTICE_NO = "SUPERVISED_NOTICE_NO";
    public static final String COL_PROGRESS_RECORD_NO = "PROGRESS_RECORD_NO";
    public static final String COL_PROGRESS_RECORD_ATTACH_NO = "PROGRESS_RECORD_ATTACH_NO";
    public static final String COL_APPLICATION_NO = "APPLICATION_NO";
    public static final String COL_NOTIFICATION_COUNT = "NOTIFICATION_COUNT";

    public static final String SEQ_PREFIX_M = "M";
    public static final String SEQ_PREFIX_G = "G";
    public static final String SEQ_PREFIX_A = "A";

    public static final String TOKYO_SUPPORT_CODE = "06";

    //Format message
    public static final String MS_I0001 = "message.I0001";
    public static final String MS_I0002 = "message.I0002";

    public static final String MS_E0001 = "message.E0001";
    public static final String MS_E0002 = "message.E0002";
    public static final String MS_E0003 = "message.E0003";
    public static final String MS_E0004 = "message.E0004";
    public static final String MS_E0005 = "message.E0005";
    public static final String MS_E0006 = "message.E0006";
    public static final String MS_E0007 = "message.E0007";
    public static final String MS_E0008 = "message.E0008";
    public static final String MS_E0009 = "message.E0009";
    public static final String MS_E0010 = "message.E0010";
    public static final String MS_E0011 = "message.E0011";
    public static final String MS_E0012 = "message.E0012";
    public static final String MS_E0013 = "message.E0013";
    public static final String MS_E0014 = "message.E0014";
    public static final String MS_E0015 = "message.E0015";
    public static final String MS_E0016 = "message.E0016";
    public static final String MS_E0023 = "message.E0023";
    public static final String MS_E0100 = "message.E0100";
    public static final String MS_E0101 = "message.E0101";
    public static final String MS_E0104 = "message.E0104";
    public static final String MS_E0105 = "message.E0105";
    public static final String MS_E0106 = "message.E0106";
    public static final String MS_E0107 = "message.E0107";
    public static final String MS_E0108 = "message.E0108";
    public static final String MS_E0109 = "message.E0109";
    public static final String MS_E0110 = "message.E0110";
    public static final String MS_E0111 = "message.E0111";
    public static final String MS_E0112 = "message.E0112";
    public static final String MS_E0113 = "message.E0113";
    public static final String MS_E0114 = "message.E0114";
    public static final String MS_E0116 = "message.E0116";
    public static final String MS_E0117 = "message.E0117";
    public static final String MS_E0118 = "message.E0118";
    public static final String MS_E0119 = "message.E0119";
    public static final String MS_E0120 = "message.E0120";
    public static final String MS_E0121 = "message.E0121";
    public static final String MS_E0122 = "message.E0122";
    public static final String MS_E0123 = "message.E0123";
    public static final String MS_E0124 = "message.E0124";
    public static final String MS_E0125 = "message.E0125";
    public static final String MS_E0126 = "message.E0126";
    public static final String MS_E0127 = "message.E0127";
    public static final String MS_E0128 = "message.E0128";
    public static final String MS_E0129 = "message.E0129";
    public static final String MS_E0130 = "message.E0130";
    public static final String MS_E0131 = "message.E0131";
    public static final String MS_E0132 = "message.E0132";
    public static final String MS_E0133 = "message.E0133";
    public static final String MS_E0134 = "message.E0134";
    public static final String MS_E0135 = "message.E0135";
    public static final String MS_E0136 = "message.E0136";
    public static final String MS_E0137 = "message.E0137";
    public static final String MS_E0138 = "message.E0138";
    public static final String MS_E0139 = "message.E0139";
    public static final String MS_E0140 = "message.E0140";
    public static final String MS_E0141 = "message.E0141";
    public static final String MS_E0142 = "message.E0142";
    public static final String MS_E0143 = "message.E0143";
    public static final String MS_E0144 = "message.E0144";
    public static final String MS_E0145 = "message.E0145";
    public static final String MS_E0146 = "message.E0146";
    public static final String MS_E0147 = "message.E0147";
    public static final String MS_E0148 = "message.E0148";

    //Common properties
    public static final String LBL_CORRESPOND_DATE = "label.CorrespondDate";
    public static final String LBL_CORRESPOND_TYPE_CODE = "label.CorrespondTypeCode";
    public static final String LBL_PROGRESS_RECORD_OVERVIEW = "label.ProgressRecordOverview";

    //Log message
    public static final String LOG_LG0010_I = "message.LG0010-I";
    public static final String LOG_LG0020_I = "message.LG0020-I";
    public static final String LOG_LG0030_I = "message.LG0030-I";
    public static final String LOG_LG0040_I = "message.LG0040-I";
    public static final String LOG_LG0060_I = "message.LG0060-I";
    public static final String LOG_LG1010_I = "message.LG1010-I";
    public static final String LOG_LG1020_I = "message.LG1020-I";
    public static final String LOG_LG1030_I = "message.LG1030-I";
    public static final String LOG_LG1040_I = "message.LG1040-I";
    public static final String LOG_LG1090_I = "message.LG1090-I";
    public static final String LOG_LG9900_E = "message.LG9900-E";
    public static final String LOG_LG1060_I = "message.LG1060-I";
    public static final String LOG_LG1110_I = "message.LG1110-I";
    public static final String LOG_LG1050_I = "message.LG1050-I";
    public static final String LOG_LG1070_I = "message.LG1070-I";
    public static final String LOG_LG1080_I = "message.LG1080-I";
    public static final String LOG_LG1100_I = "message.LG1100-I";
    public static final String LOG_LG1120_I = "message.LG1120-I";
    public static final String LOG_LG0110_W = "message.LG0110-W";

    //LOGIN_FORM field
    public static final String FORM_PASSWORD = "パスワード";
    public static final String FORM_LOGIN_ID = "ログインID";

    //SETTING PROPERTIES NAME
    public static final String ST_M_ACCOUNT_LOCK_PERIOD = "M_ACCOUT_LOCK_PERIOD";
    public static final String ST_M_LOGIN_INVALIDITY_TIMES_MAX = "M_LOGIN_INVALIDITY_TIMES_MAX";
    public static final String ST_M_PASSWORD_VALID_PERIOD = "M_PASSWORD_VALID_PERIOD";
    public static final String ST_SESSION_TIMEOUT_PERIOD = "SESSION_TIMEOUT_PERIOD";
    public static final String ST_G_ACCOUNT_LOCK_PERIOD = "G_ACCOUT_LOCK_PERIOD";
    public static final String ST_G_LOGIN_INVALIDITY_TIMES_MAX = "G_LOGIN_INVALIDITY_TIMES_MAX";
    public static final String ST_G_PASSWORD_VALID_PERIOD = "G_PASSWORD_VALID_PERIOD";
    public static final String ST_ADVICE_DETAILS_INDENTION_MAX = "ADVICE_DETAILS_INDENTION_MAX";
    public static final String ST_SEARCH_MAX = "SEARCH_MAX";
    public static final String ST_LIST_DISPLAY_MAX = "LIST_DISPLAY_MAX";
    public static final String CITY_LOGIN_URL = "CITY_LOGIN_URL";
    public static final String CITY_NOTICE_HTML_PATH = "CITY_NOTICE_HTML_PATH";
    public static final String APARTMENT_LOGIN_URL = "APARTMENT_LOGIN_URL";
    public static final String APARTMENT_NOTICE_HTML_PATH = "APARTMENT_NOTICE_HTML_PATH";
    public static final String CITY_ONETIME_PASSWORD_PERIOD = "CITY_ONETIME_PASSWORD_PERIOD";
    public static final String APARTMENT_ONETIME_PASSWORD_PERIOD = "APARTMENT_ONETIME_PASSWORD_PERIOD";
    public static final String MAIL_CONTACT_NAME = "MAIL_CONTACT_NAME";
    public static final String ADVICE_DETAILS_INDENTION_MAX = "ADVICE_DETAILS_INDENTION_MAX";
    public static final String SURVEY_CONTACT_INDENTION_MAX = "GGA0110_CONTACT_INDENTION_MAX";
    public static final String GGA0110_CONTACT_CHARACTER_MAX = "GGA0110_CONTACT_CHARACTER_MAX";
    public static final String GGA0110_TEXT_ADDRESS_PATH = "GGA0110_TEXT_ADDRESS_PATH";
    public static final String TERMS_HTML_PATH = "TERMS_HTML_PATH";
    public static final String GJA0110_SEARCH_MAX = "GJA0110_SEARCH_MAX";
    public static final String GJA0110_LIST_DISPLAY_MAX = "GJA0110_LIST_DISPLAY_MAX";
    public static final String GKA0120_LIST_DISPLAY_MAX = "GKA0120_LIST_DISPLAY_MAX";
    public static final String GIA0110_TEXT_ADDRESS_PATH = "GIA0110_TEXT_ADDRESS_PATH";
    //Session Properties
    public static final String SYSTEM_SETTING = "systemSettings";

    //Screen ID
    public static final String SCREEN_ID = "SCREEN_ID";
    public static final String SCREEN_ID_MAA0110 = "MAA0110";
    public static final String SCREEN_ID_MBA0110 = "MBA0110";
    public static final String SCREEN_ID_GAA0110 = "GAA0110";
    public static final String SCREEN_ID_SBA0110 = "SBA0110";
    public static final String SCREEN_ID_GBA0110 = "GBA0110";
    public static final String SCREEN_ID_GEA0110 = "GEA0110";
    public static final String SCREEN_ID_MEA0110 = "MEA0110";
    public static final String SCREEN_ID_MDA0110 = "MDA0110";
    public static final String SCREEN_ID_GDA0110 = "GDA0110";
    public static final String SCREEN_ID_GJA0120 = "GJA0120";
    public static final String SCREEN_ID_GCA0120 = "GCA0120";

    // Report
    public static final String RP010_CREATE = "RP010Create";
    public static final String RP010_UPDATE = "RP010Update";
    public static final String RP010 = "RP010";
    public static final String RP020 = "RP020";
    public static final String RP030 = "RP030";
    public static final String RP040 = "RP040";
    public static final String RP050 = "RP050";
    public static final String RP060 = "RP060";

    //CD Code
    public static final String CD001 = "CD001";
    public static final String CD002 = "CD002";
    public static final String CD003 = "CD003";
    public static final String CD004 = "CD004";
    public static final String CD005 = "CD005";
    public static final String CD006 = "CD006";
    public static final String CD007 = "CD007";
    public static final String CD008 = "CD008";
    public static final String CD009 = "CD009";
    public static final String CD010 = "CD010";
    public static final String CD011 = "CD011";
    public static final String CD012 = "CD012";
    public static final String CD013 = "CD013";
    public static final String CD014 = "CD014";
    public static final String CD016 = "CD016";
    public static final String CD017 = "CD017";
    public static final String CD018 = "CD018";
    public static final String CD019 = "CD019";
    public static final String CD020 = "CD020";
    public static final String CD021 = "CD021";
    public static final String CD022 = "CD022";
    public static final String CD023 = "CD023";
    public static final String CD024 = "CD024";
    public static final String CD025 = "CD025";
    public static final String CD026 = "CD026";
    public static final String CD027 = "CD027";
    public static final String CD029 = "CD029";
    public static final String CD030 = "CD030";
    public static final String CD031 = "CD031";
    public static final String CD032 = "CD032";
    public static final String CD033 = "CD033";
    public static final String CD036 = "CD036";
    public static final String CD037 = "CD037";
    public static final String CD038 = "CD038";
    public static final String CD039 = "CD039";
    public static final String CD040 = "CD040";
    public static final String CD041 = "CD041";
    public static final String CD042 = "CD042";
    public static final String CD043 = "CD043";
    public static final String CD044 = "CD044";
    public static final String CD045 = "CD045";
    public static final String CD046 = "CD046";
    public static final String CD047 = "CD047";
    public static final String CD048 = "CD048";
    public static final String CD049 = "CD049";
    public static final String CD050 = "CD050";
    public static final String CD051 = "CD051";
    public static final String CD052 = "CD052";
    public static final String CD053 = "CD053";
    public static final String CD054 = "CD054";
    public static final String CD055 = "CD055";
    public static final String CD056 = "CD056";
    public static final String CD057 = "CD057";
    public static final String CD058 = "CD058";

    /** CD002.ある */
    public static final String CD002_HAVE = "ある";
    /** CD002.回答しない */
    public static final String CD002_NO_ANSWER = "回答しない";
    /** CD003.新規 */
    public static final String CD003_NEW = "新規";
    /** CD003.更新 */
    public static final String CD003_UPDATE = "更新";
    /** CD004.変更 */
    public static final String CD004_CHANGE = "変更";
    /** CD004.建物の滅失その他の事由 */
    public static final String CD004_LOSS_OF_BUILDINGS_OR_OTHER_REASONS = "建物の滅失その他の事由";
    /** CD005.棟別管理組合が中心となり、管理を行っている */
    public static final String CD005_BUILDING_BASED_MANAGEMENT_UNION_PLAYS_A_CENTRAL_ROLE_AND_MANAGES = "棟別管理組合が中心となり、管理を行っている";
    /** CD005.団地管理組合が中心となり、管理を行っている */
    public static final String CD005_THE_ESTATE_MANAGEMENT_UNION_PLAYS_A_CENTRAL_ROLE_AND_MANAGES_IT = "団地管理組合が中心となり、管理を行っている";
    /** CD005.その他 */
    public static final String CD005_OTHER = "その他";
    /** CD005.回答しない */
    public static final String CD005_NO_ANSWER = "回答しない";
    /** CD006.所有権 */
    public static final String CD006_OWNERSHIP = "所有権";
    /** CD006.借地権 */
    public static final String CD006_LEASEHOLD = "借地権";
    /** CD006.定期借地権 */
    public static final String CD006_FIXED_TERM_LEASE = "定期借地権";
    /** CD006.その他 */
    public static final String CD006_OTHER = "その他";
    /** CD006.回答しない */
    public static final String CD006_NO_ANSWER = "回答しない";
    /** CD007.なし */
    public static final String CD007_NONE = "なし";
    /** CD007.店舗 */
    public static final String CD007_STORE = "店舗";
    /** CD007.事務所 */
    public static final String CD007_THE_OFFICE = "事務所";
    /** CD007.その他 */
    public static final String CD007_OTHER = "その他";
    /** CD007.回答しない */
    public static final String CD007_NO_ANSWER = "回答しない";
    /** CD008.全部委託 */
    public static final String CD008_ENTRUST_ALL = "全部委託";
    /** CD008.一部委託 */
    public static final String CD008_PARTIALLY_COMMISSIONED = "一部委託";
    /** CD008.その他 */
    public static final String CD008_OTHER = "その他";
    /** CD008.回答しない */
    public static final String CD008_NO_ANSWER = "回答しない";
    /** CD010.回答しない */
    public static final String CD010_NO_ANSWER = "回答しない";
    /** CD011.実施済 */
    public static final String CD011_IMPLEMENTED = "実施済";
    /** CD011.未実施 */
    public static final String CD011_NOT_IMPLEMENTED = "未実施";
    /** CD011.回答しない */
    public static final String CD011_NO_ANSWER = "回答しない";
    /** CD012.回答しない */
    public static final String CD012_NO_ANSWER = "回答しない";
    /** CD013.区分所有者等 */
    public static final String CD013_CATEGORY_OWNER_ETC = "区分所有者等";
    /** CD013.その他 */
    public static final String CD013_OTHER = "その他";
    /** CD014.団地管理組合である */
    public static final String CD014_IT_IS_HOUSING_COMPLEX_MANAGEMENT_UNION = "団地管理組合である";
    /** CD014.団地管理組合ではない */
    public static final String CD014_NOT_A_HOUSING_COMPLEX_UNION = "団地管理組合ではない";
    /** CD014.回答しない */
    public static final String CD014_NO_ANSWER = "回答しない";
    /** CD016.未審査 */
    public static final String CD016_UNREVIEWED = "未審査";
    /** CD016.承認 */
    public static final String CD016_APPROVAL = "承認";
    /** CD016.却下 */
    public static final String CD016_REJECTION = "却下";
    /** CD017.メールで通知する */
    public static final String CD017_NOTIFI_BY_EMAIL = "メールで通知する";
    /** CD017.郵送で通知する */
    public static final String CD017_NOTIFI_BY_MAIL = "郵送で通知する";
    /** CD023.有効 */
    public static final String CD023_VALID = "有効";
    /** CD023.無効 */
    public static final String CD023_INVALID = "無効";
    /** CD024.利用可能 */
    public static final String CD024_POSSIBLE = "利用可能";
    /** CD024.利用不可 */
    public static final String CD024_IMPOSSIBLE = "利用不可";
    /** CD025.未変更 */
    public static final String CD025_UNCHANGED = "未変更";
    /** CD025.変更済 */
    public static final String CD025_CHANGED = "変更済";
    /** CD026.ログインしていない */
    public static final String CD026_NOT_LOGGED_IN = "ログインしていない";
    /** CD026.ログイン中 */
    public static final String CD026_LOGGING_IN = "ログイン中";
    /** CD027.都職員 */
    public static final String CD027_TOKYO_STAFF = "都職員";
    /** CD027.区市町村 */
    public static final String CD027_CITY = "区市町村";
    /** CD027.システム管理者 */
    public static final String CD027_SYSTEM_ADMIN = "システム管理者";
    /** CD027.保守業者 */
    public static final String CD027_MAINTENANCER = "保守業者";
    /** CD030.未受理 */
    public static final String CD030_UNACCEPTED = "未受理";
    /** CD030.受理済 */
    public static final String CD030_ACCEPTED = "受理済";
    /** CD030.指定しない */
    public static final String CD030_NOT_SPECIFIED = "指定しない";
    /** CD031.一時保存_管理組合 */
    public static final String CD031_TEMPORARY_TYPE_MANSION = "一時保存_管理組合";
    /** CD031.一時保存_区市町村 */
    public static final String CD031_TEMPORARY_TYPE_CITY = "一時保存_区市町村";
    /** CD031.一時保存_都職員 */
    public static final String CD031_TEMPORARY_TYPE_TOKYO_STAFF = "一時保存_都職員";
    /** CD031.一時保存_システム管理者 */
    public static final String CD031_TEMPORARY_TYPE_SYS_ADMIN = "一時保存_システム管理者";
    /** CD031.一時保存_保守業者 */
    public static final String CD031_TEMPORARY_TYPE_MAINTENANCE_CONTRACTOR = "一時保存_保守業者";
    /** CD031.正式 */
    public static final String CD031_TEMPORARY_TYPE_FORMAL = "正式";
    /** CD033.届出 */
    public static final String CD033_NOTIFICATION = "届出";
    /** CD033.変更届出 */
    public static final String CD033_CHANGE_NOTIFICATION = "変更届出";
    /** CD033.届出受理 */
    public static final String CD033_NOTIFICATION_ACCEPTANCE = "届出受理";
    /** CD033.変更届出受理 */
    public static final String CD033_CHANGE_NOTIFICATION_ACCEPTANCE = "変更届出受理";
    /** CD033.助言メール通知 */
    public static final String CD033_ADVICE_EMAIL_NOTIFICATION = "助言メール通知";
    /** CD033.助言通知 */
    public static final String CD033_ADVICE_NOTICE = "助言通知";
    /** CD033.督促通知（一回目）*/
    public static final String CD033_DUNNING_NOTICE_1ST_TIME = "督促通知（一回目）";
    /** CD033.督促通知（二回目以降）*/
    public static final String CD033_DUNNING_NOTICE_OVER_2ND_TIME = "督促通知（二回目以降）";
    /** CD033.届出受理メール通知*/
    public static final String CD033_NOTI_RECEIPT_EMAIL_NOTI = "届出受理メール通知";
    /** CD033.変更届出受理メール通知*/
    public static final String CD033_CHANGE_NOTI_EMAIL_NOTI = "変更届出受理メール通知";
    /** CD038.ロック */
    public static final String CD038_LOCK = "ロック";
    /** CD038.アンロック */
    public static final String CD038_UNLOCK = "アンロック";
    /** CD039.職権訂正である */
    public static final String CD039_AUTHORITY_CORRECTION = "職権訂正である";
    /** CD039.職権訂正ではない */
    public static final String CD039_NOT_AMENDMENT = "職権訂正ではない";
    /** CD040.建物を除却したため */
    public static final String CD040_THE_BUILDING_WAS_RETIRED = "建物を除却したため";
    /** CD040.区分所有建物ではなくなったため */
    public static final String CD040_BECAUSE_IT_IS_NO_LONGER_A_CO_OWNED_BUILDING = "区分所有建物ではなくなったため";
    /** CD040.その他 */
    public static final String CD040_OTHER = "その他";

    /** CD046.ユーザ情報通知メール用 */
    public static final String CD046_FOR_USER_INFO_NOTI_EMAIL = "ユーザ情報通知メール用";
    /** CD046.審査結果通知メール用 */
    public static final String CD046_FOR_EXAM_RESULT_NOTI_EMAIL = "審査結果通知メール用";
    /** CD046.パスワード再発行通知メール用（管理組合向け） */
    public static final String CD046_FOR_PASSWORD_REISSUE_NOTI_EMAIL_FOR_MA = "パスワード再発行通知メール用（管理組合向け）";
    /** CD046.パスワード再発行通知メール用（区市町村向け） */
    public static final String CD046_FOR_PASSWORD_REISSUE_NOTI_EMAIL_FOR_CITY = "パスワード再発行通知メール用（区市町村向け）";
    /** CD046.届出受理通知メール用 */
    public static final String CD046_FOR_NOTI_ACCEPT_EMAIL = "届出受理通知メール用";
    /** CD046.助言内容通知メール用 */
    public static final String CD046_FOR_ADVICE_NOTI_EMAIL = "助言内容通知メール用";
    /** CD046.現地調査通知メール用 */
    public static final String CD046_FOR_SURVEY_NOTI_EMAIL = "現地調査通知メール用";
    /** CD046.現地調査通知書用  */
    public static final String CD046_FOR_FIELD_SURVEY_NOTICE = "現地調査通知書用";
    /** CD049.除却 */
    public static final String CD049_REMOVAL = "除却";
    /** CD049.除却しない */
    public static final String CD049_NOT_UPDATED = "除却しない";
    /** CD052.除却しない */
    public static final String CD052_NO_ANSWER = "回答しない";

    public static final String MDA0110_FILE_MAX_SIZE = "MDA0110_IMPORT_FILE_SIZE_MAX";

    public static final String GEB0110_FILE_SIZE_MAX = "GEB0110_FILE_SIZE_MAX";

    public static final String GIA0110_CONTACT_CHARACTER_MAX = "GIA0110_CONTACT_CHARACTER_MAX";

    public static final String GEB0110_FILE_COUNT_MAX = "GEB0110_FILE_COUNT_MAX";

    public static final String AAA0110_FILE_SIZE_MAX = "AAA0110_FILE_SIZE_MAX";

    public static final String AAA0110_FILE_COUNT_MAX = "AAA0110_FILE_COUNT_MAX";

    public static final String GEC0110_FILE_SIZE_MAX = "GEC0110_FILE_SIZE_MAX";

    public static final String GEC0110_FILE_COUNT_MAX = "GEC0110_FILE_COUNT_MAX";

    public static final String CD001_UNDELETEFLAG = "未削除";

    public static final String CD001_DELETEFLAG = "削除";

    public static final String CD033_FIELDSURVEYNOTIFICATION = "現地調査通知";

    public static final String CD033_FIELDSURVEYMAILNOTIFICATION = "現地調査メール通知";

    // Mail ID
    public static final String TEMPLATE_ML010 = "ML010.vm";
    public static final String TEMPLATE_ML020 = "ML020.vm";
    public static final String TEMPLATE_ML030 = "ML030.vm";
    public static final String TEMPLATE_ML035 = "ML035.vm";
    public static final String TEMPLATE_ML040 = "ML040.vm";
    public static final String TEMPLATE_ML050 = "ML050.vm";
    public static final String TEMPLATE_ML060 = "ML060.vm";

    // City name
    public static final String CITY_NAME_TOKYO = "東京都";

    public static final String G_PASSWORD_VALID_PERIOD = "G_PASSWORD_VALID_PERIOD";
    //Format file upload for screen GEB0110
    public static final String[] FORMAT_FILE_UPLOAD = new String[] {"txt", "csv", "png", "jpg", "gif", "bmp"};
    //Format file import for screen MDA0110
    public static final String[] FORMAT_FILE_IMPORT = new String[] {"csv"};

    public static final String MSG_ADD_FAILED = "%sテーブルへの新規データ追加が失敗した。";
    public static final String MSG_UPDATE_FAILED = "%sテーブルへのデータ更新が失敗した。";
    public static final String MSG_FILE_IS_NULL = "ファイルがヌルです";

    /**
     * Mansion info upload
     * FL040
     * AAA0110
     */
    public static final String MI_PROPERTY_NUMBER = "物件番号";
    public static final String MI_APARTMENT_NAME = "マンション名";
    public static final String MI_APARTMENT_NAME_PHONETIC = "マンション名フリガナ";
    public static final String MI_ZIPCODE = "郵便番号";
    public static final String MI_CITY_NAME = "区市町村名";
    public static final String MI_ADDRESS = "住所";
    public static final String MI_NOTIFICATION_KBN = "届出必須区分";
    public static final String MI_SUPPORT = "都支援対象";
    public static final String MI_BUILD_YEAR = "新築年";
    public static final String MI_BUILD_MONTH = "新築月";
    public static final String MI_BUILD_DAY = "新築日";
    public static final String MI_FLOOR_NUMBER = "建物階数";
    public static final String MI_HOUSE_NUMBER = "建物戸数";
    public static final String MI_SITE_AREA = "敷地面積";
    public static final String MI_TOTAL_FLOOR_AREA = "延床面積";
    public static final String MI_BUILDING_AREA = "建築面積";
    public static final String MI_BUILDING_STRUCTURE = "建物構造";
    public static final String MI_REGISTRATION_NO = "登記番号";
    public static final String MI_REGISTRATION_ADDRESS  = "登記_所在";
    public static final String MI_REGISTRATION_HOUSE_NO = "登記_家屋番号";
    public static final String MI_REAL_ESTATE_NO = "不動産番号";
    public static final String MI_PRELIMINARY_1 = "予備１";
    public static final String MI_PRELIMINARY_2 = "予備2";
    public static final String MI_PRELIMINARY_3 = "予備3";
    public static final String MI_PRELIMINARY_4 = "予備4";
    public static final String MI_PRELIMINARY_5 = "予備5";

    /**
     * Progress Record Upload Agrument Error Message
     * FL050
     * GEC0110
     */
    public static final String PR_APARTMENT_ID = "マンションID";
    public static final String PR_CORRESPOND_DATE = "対応日";
    public static final String PR_CORRESPOND_TIME = "対応時間";
    public static final String PR_CORRESPOND_TYPE = "対応種別";
    public static final String PR_NOTICE_TYPE = "通知書種別";
    public static final String PR_PROGRESS_RECORD_OVERVIEW = "経過記録概要";
    public static final String PR_PROGRESS_RECORD_DETAIL = "経過記録詳細";

    /**
     * Progress Record Upload Agrument Error Message
     * FL050
     * GEC0110
     */
    public static final String PR_ERR_APARTMENT_ID = "アップロードファイルのマンションID";
    public static final String PR_ERR_CORRESPOND_DATE = "アップロードファイルの対応日";
    public static final String PR_ERR_CORRESPOND_TIME = "アップロードファイルの対応時間";
    public static final String PR_ERR_CORRESPOND_TYPE = "アップロードファイルの対応種別";
    public static final String PR_ERR_CORRESPOND_TYPE_2 = "都支援対象変更";
    public static final String PR_ERR_NOTICE_TYPE = "アップロードファイルの通知書種別";
    public static final String PR_ERR_PROGRESS_RECORD_OVERVIEW = "アップロードファイルの経過記録概要";
    public static final String PR_ERR_PROGRESS_RECORD_DETAIL = "アップロードファイルの経過記録詳細";

    /**
     * Error ID for ZAA0150
     * use in UploadMansionInfoImpl, ProgressRecordBatchRegistrationLogicImpl
     */
    public static final String E0002 = "E0002";
    public static final String E0003 = "E0003";
    public static final String E0004 = "E0004";
    public static final String E0005 = "E0005";
    public static final String E0006 = "E0006";
    public static final String E0007 = "E0007";
    public static final String E0008 = "E0008";
    public static final String E0012 = "E0012";
    public static final String E0013 = "E0013";
    public static final String E0014 = "E0014";
    public static final String E0015 = "E0015";
    public static final String E0016 = "E0016";
    public static final String E0120 = "E0120";
    public static final String E0121 = "E0121";
    public static final String E0123 = "E0123";
    public static final String E0100 = "E0100";
    public static final String E0117 = "E0117";
    public static final String E0136 = "E0136";
    public static final String E0143 = "E0143";
    public static final String E0147 = "E0147";

    /**
     * Mail parameters
     */
    public static final String ML_CITY_NAME = "cityName";
    public static final String ML_WINDOW_BELONG_TO = "windowBelong";
    public static final String ML_WINDOW_TEL_NO = "windowTelNo";
    public static final String ML_WINDOW_FAX_NO = "windowFaxNo";
    public static final String ML_WINDOW_MAIL_ADDRESS = "windowMailAddress";
    public static final String ML_APARTMENT_NAME = "apartmentName";
    public static final String ML_CONTACT_NAME = "contactName";
    public static final String ML_LOGIN_URL = "loginUrl";
    public static final String ML_PASSWORD_PERIOD = "passwordPeriod";
    public static final String ML_PASSWORD = "password";
    public static final String ML_LOGIN_ID = "loginId";
    public static final String ML_JUDGE_REMARKS = "judgeRemarks";
   public static final String ML_CITY_NAME_JP = "問合せ窓口_区市町村";
   public static final String ML_WINDOW_BELONG_TO_JP = "問合せ窓口_部署";
   public static final String ML_WINDOW_TEL_NO_JP = "問合せ窓口_電話番号";
   public static final String ML_WINDOW_FAX_NO_JP = "問合せ窓口_FAX番号";
   public static final String ML_WINDOW_MAIL_ADDRESS_JP = "問合せ窓口_メールアドレス";
   public static final String ML_APARTMENT_NAME_JP = "マンション名";
   public static final String ML_CONTACT_NAME_JP = "連絡先氏名";
   public static final String ML_LOGIN_URL_JP = "ログイン用URL";
   public static final String ML_PASSWORD_PERIOD_JP = "ワンタイムパスワード有効期限";
   public static final String ML_PASSWORD_JP = "ワンタイムパスワード";
   public static final String ML_LOGIN_ID_JP = "ログインID";
   public static final String ML_JUDGE_REMARKS_JP = "審査備考";
    /**
     * list screen
     */
    public static final String[] LST_SCREENS = new String[] {"AAA0110","ABA0110","ABB0110","GAA0110","GBA0110","GCA0110","GCA0120","GDA0110","GEA0110","GEB0110","GEC0110","GFA0110","GGA0110","GIA0110","GJA0110","GJA0120","GKA0110","GKA0120","GLA0110","MAA0110","MBA0110","MCA0110","MDA0110","MEA0110","SAA0110","SBA0110","SCA0110","ZAA0110","ZAA0120","ZAA0130","ZAA0140","ZAA0150"};

    public static final String CURRENT_SCREEN = "currentScreen";
    public static final String PREVIOUS_SCREEN = "previousScreen";

    /**
     * Sort parameter for screen GJA0110
     * "届出日（昇順）", "届出日（降順）", "マンション名（昇順)", "マンション名（降順）", "住所（昇順）", "住所（降順）"
     * */
    public static final String SORT_NOTIFICATION_DATE_ASC = "届出日（昇順）";
    public static final String SORT_NOTIFICATION_DATE_DESC = "届出日（降順）";
    public static final String SORT_APARTMENT_NAME_ASC = "マンション名（昇順)";
    public static final String SORT_APARTMENT_NAME_DESC = "マンション名（降順）";
    public static final String SORT_ADDRESS_ASC = "住所（昇順）";
    public static final String SORT_ADDRESS_DESC = "住所（降順）";

    public static final String ERA_PATH = "classpath:era.xml";

}
