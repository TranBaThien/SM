/*
 * @(#)CustomDateUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;


/**
 * @author PVHung3
 * @date 2019/11/16
 */
public class CustomDateUtil {

	/**
	 * 日付フォーマット
	 *
	 * @param str
	 * @return date
	 */
	public static Date getDate(String str) {
		Calendar calendar = getCalendar(str);
		return calendar.getTime();
	}

	/**
	 * 指定された日付・時刻文字列を、可能であれば Calendarクラスに変換します。 以下の形式の日付文字列を変換できます。
	 *
	 * ●変換可能な形式は以下となります。 yyyy/MM/dd yy/MM/dd yyyy-MM-dd yy-MM-dd yyyyMMdd
	 *
	 * 上記に以下の時間フィールドが組み合わされた状態 でも有効です。 HH:mm HH:mm:ss HH:mm:ss.SSS
	 *
	 * @param str
	 *            日付・時刻文字列。
	 * @return 変換後のCalendarクラス。
	 * @throws IllegalArgumentException
	 *             日付文字列が変換不可能な場合 または、矛盾している場合（例：2000/99/99）。
	 */
	public static Calendar getCalendar(String str) {
		str = format(str);
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);

		int yyyy = Integer.parseInt(str.substring(0, 4));
		int MM = Integer.parseInt(str.substring(5, 7));
		int dd = Integer.parseInt(str.substring(8, 10));
		int HH = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		int ss = cal.get(Calendar.SECOND);
		int SSS = cal.get(Calendar.MILLISECOND);
		cal.clear();
		cal.set(yyyy, MM - 1, dd);
		int len = str.length();
		switch (len) {
		case 10:
			break;
		case 16: // yyyy/MM/dd HH:mm
			HH = Integer.parseInt(str.substring(11, 13));
			mm = Integer.parseInt(str.substring(14, 16));
			cal.set(Calendar.HOUR_OF_DAY, HH);
			cal.set(Calendar.MINUTE, mm);
			break;
		case 19: // yyyy/MM/dd HH:mm:ss
			HH = Integer.parseInt(str.substring(11, 13));
			mm = Integer.parseInt(str.substring(14, 16));
			ss = Integer.parseInt(str.substring(17, 19));
			cal.set(Calendar.HOUR_OF_DAY, HH);
			cal.set(Calendar.MINUTE, mm);
			cal.set(Calendar.SECOND, ss);
			break;
		case 23:
		case 24:
		case 25:
		case 26:// yyyy/MM/dd HH:mm:ss.SSS
			HH = Integer.parseInt(str.substring(11, 13));
			mm = Integer.parseInt(str.substring(14, 16));
			ss = Integer.parseInt(str.substring(17, 19));
			SSS = 0;
			cal.set(Calendar.HOUR_OF_DAY, HH);
			cal.set(Calendar.MINUTE, mm);
			cal.set(Calendar.SECOND, ss);
			cal.set(Calendar.MILLISECOND, 0);
			break;
		default:
			throw new IllegalArgumentException("引数の文字列[" + str + "]は日付文字列に変換できません");
		}
		return cal;
	}

	/**
	 * 様々な日付、時刻文字列をデフォルトの日付・時刻フォーマット へ変換します。
	 *
	 * ●デフォルトの日付フォーマットは以下になります。 日付だけの場合：yyyy/MM/dd 日付+時刻の場合：yyyy/MM/dd
	 * HH:mm:ss.SSS
	 *
	 * @param str
	 *            変換対象の文字列
	 * @return デフォルトの日付・時刻フォーマット
	 * @throws IllegalArgumentException
	 *             日付文字列が変換不可能な場合
	 */
	private static String format(String str) {
		if (str == null || str.trim().length() < 8) {
			throw new IllegalArgumentException("引数の文字列[" + str + "]は日付文字列に変換できません");
		}
		str = str.trim();
		String yyyy = null;
		String MM = null;
		String dd = null;
		String HH = null;
		String mm = null;
		String ss = null;
		String SSS = null;
		// "-" or "/" が無い場合
		if (str.indexOf("/") == -1 && str.indexOf("-") == -1) {
			if (str.length() == 8) {
				yyyy = str.substring(0, 4);
				MM = str.substring(4, 6);
				dd = str.substring(6, 8);
				return yyyy + "/" + MM + "/" + dd;
			}
			String resultDate = null;
			yyyy = str.substring(0, 4);
			MM = str.substring(4, 6);
			dd = str.substring(6, 8);
			HH = str.substring(8, 10);
			mm = str.substring(10, 12);
			resultDate = yyyy + "/" + MM + "/" + dd + " " + HH + ":" + mm;
			if (str.length() == 14) {
				ss = str.substring(12, 14);
				resultDate = resultDate + ":" + ss;
			}
			return resultDate;
		}
		StringTokenizer token = new StringTokenizer(str, "_/-:. ");
		StringBuilder result = new StringBuilder();
		for (int i = 0; token.hasMoreTokens(); i++) {
			String temp = token.nextToken();
			switch (i) {
			case 0:// 年の部分
				yyyy = fillString(str, temp, "L", "20", 4);
				result.append(yyyy);
				break;
			case 1:// 月の部分
				MM = fillString(str, temp, "L", "0", 2);
				result.append("/" + MM);
				break;
			case 2:// 日の部分
				dd = fillString(str, temp, "L", "0", 2);
				result.append("/" + dd);
				break;
			case 3:// 時間の部分
				HH = fillString(str, temp, "L", "0", 2);
				result.append(" " + HH);
				break;
			case 4:// 分の部分
				mm = fillString(str, temp, "L", "0", 2);
				result.append(":" + mm);
				break;
			case 5:// 秒の部分
				ss = fillString(str, temp, "L", "0", 2);
				result.append(":" + ss);
				break;
			case 6:// ミリ秒の部分
				SSS = fillString(str, temp, "R", "0", 6);
				result.append("." + SSS);
				break;
			}
		}
		return result.toString();
	}

	private static String fillString(String strDate, String str, String position, String addStr, int len) {
		if (str.length() > len) {
			throw new IllegalArgumentException("引数の文字列[" + strDate + "]は日付文字列に変換できません");
		}
		return fillString(str, position, len, addStr);
	}

	/**
	 * 文字列[str]に対して、補充する文字列[addStr]を [position]の位置に[len]に満たすまで挿入します。
	 *
	 * ※[str]がnullや空リテラルの場合でも[addStr]を [len]に満たすまで挿入した結果を返します。
	 *
	 * @param str
	 *            対象文字列
	 * @param position
	 *            前に挿入 ⇒ L or l 後に挿入 ⇒ R or r
	 * @param len
	 *            補充するまでの桁数
	 * @param addStr
	 *            挿入する文字列
	 * @return 変換後の文字列。
	 */
	private static String fillString(String str, String position, int len, String addStr) {
		if (addStr == null || addStr.length() == 0) {
			throw new IllegalArgumentException("挿入する文字列の値が不正です。addStr=" + addStr);
		}
		if (str == null) {
			str = "";
		}
		StringBuilder sb = new StringBuilder(str);
		while (len > sb.length()) {
			if (position.equalsIgnoreCase("l")) {
				int sum = sb.length() + addStr.length();
				if (sum > len) {
					addStr = addStr.substring(0, addStr.length() - (sum - len));
					sb.insert(0, addStr);
				} else {
					sb.insert(0, addStr);
				}
			} else {
				sb.append(addStr);
			}
		}
		if (sb.length() == len) {
			return sb.toString();
		}
		return sb.toString().substring(0, len);
	}

	public static String getDate(String date, String pattern) {
		if (StringUtils.isEmpty(date)) {
			return "";
		}
		else
		{
			FastDateFormat format = FastDateFormat.getInstance(pattern);
			return format.format(getDate(date));
		}
	}

	public static String getCurrentDateTime() {
		FastDateFormat format = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss");
		return format.format(new Date());
	}

	public static String getCurrentDateTime(String pattern) {
		// CT8customize_NC001_START
		FastDateFormat format = FastDateFormat.getInstance(pattern);
		return format.format(new Date());
		// CT8customize_NC001_END
	}

	/**
	 * date(YYYYMM またYYYYMMDD)のフォーマット(YYYY年MM月 またYYYY年MM月DD日)設定
	 * @param date
	 * @return
	 */
	public static String dateFormat(String date) {
		// CT8customize_NC001_START
		String result = "";
		if (!StringUtils.isEmpty(date)) {
			if (date.length() == 6) {
				result = date.substring(0, 4) + CommonConstants.YEAR_STRING + date.substring(4, 6) + CommonConstants.MONTH_STRING;
			} else if (date.length() == 8) {
				result = date.substring(0, 4) + CommonConstants.YEAR_STRING + date.substring(4, 6) + CommonConstants.MONTH_STRING + date.substring(6, 8) + CommonConstants.DAY_STRING;
			}
		}
		return result;
		// CT8customize_NC001_END
	}

	/**
	 * 技術者の生年月日用
	 * @param String date
	 * @return boolean
	 */
	public static String formatEngineerBirthdate(String date, String pattern) {
		// M1720A00129_START
		String result = "";
		if (StringUtils.isEmpty(date)) {
			return result;
		}

		FastDateFormat format = FastDateFormat.getInstance(pattern);
		try {
			result = format.format(CustomDateUtil.getDate(date));
		} catch (Exception e) {
			//処理なし
			result = date;
		}
		return result;
		// M1720A00129_END
	}

	// M1720S00197_20180808_START
	/**
	 * 技術者の生年月日用（日付以外の場合、空白を返却）
	 * @param String date
	 * @return boolean
	 */
	public static String formatEngineerBirthdateForBlank(String date, String pattern) {
		String result = "";
		if (StringUtils.isEmpty(date)) {
			return result;
		}

		FastDateFormat format = FastDateFormat.getInstance(pattern);
		try {
			result = format.format(CustomDateUtil.getDate(date));
		} catch (Exception e) {
			// 空白を返却
			result = "";
		}
		return result;
	}
	// M1720S00197_20180808_END

	// M1712JATG-0066_20180823_START
	/**
	 * 技術者実績確認書出力用の生年月日（日付以外の場合、空白を返却、日付の場合、そのまま返却）
	 * @param String date
	 * @return boolean
	 */
	public static String formatEngineerBirthdateForBlank(String date) {
		String result = "";
		if (StringUtils.isEmpty(date)) {
			return result;
		}
		String pattern = CommonConstants.DATE_FORMAT_TYPE_SEIREKI;
		FastDateFormat format = FastDateFormat.getInstance(pattern);
		try {
			result = format.format(CustomDateUtil.getDate(date));
		} catch (Exception e) {
			// 空白を返却
			result = "";
		}
		if (!"".equals(result)) {
			// そのままdateを返却
			return date;
		}
		return result;
	}
	// M1712JATG-0066_20180823_END

	/**
	 * 編集した和暦年月日(例：平成18年3月12日)を取得します（技術者の生年月日用）。
	 * @param strDate
	 * @return
	 */
	public static String getJpnDateForEngineerBirthdate(String strDate){
		// M1720A00129_START
		String result = "";
		if (!GenericValidator.isBlankOrNull(strDate)) {
			DateUtil dateUtil = new DateUtil();
			if (dateUtil.setDate(strDate)) {
				result = dateUtil.getFormatJapaneseDate().replace(" ", "");
			} else {
				result = strDate;
			}
		}
		return result;
		// M1720A00129_END
	}
}
