/*
 * @(#)CheckUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.apache.commons.validator.GenericValidator;
import org.apache.oro.text.perl.MalformedPerl5PatternException;
import org.apache.oro.text.perl.Perl5Util;
import org.springframework.web.multipart.MultipartFile;

/**
 * チェックに関する共通機能を提供するクラスです。 <br>
 * Class cung cấp chức năng common lien quan tới check.
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.0
 */

public class CheckUtil {

	/**
	 * 入力文字列が半角空白文字だけで構成されているかチェックします。
	 * 空文字列とNullの場合はtrueを返します。<br/>
	 * Check xem chuỗi kí tự input có đang được cấu thành chỉ bằng kí tự rỗng 1byte không
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về true
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isBlank(String s) {
		if (s == null) {
			return true;
		}
		if (s.trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 入力文字列が半角空白文字および空白文字だけで構成されているかチェックします。
	 * 空文字列とNullの場合はtrueを返します。<br/>
	 * Check xem chuỗi kí tự input có đang được cấu thành chỉ bằng kí tự rỗng và kí tự rỗng 1byte không
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về true
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isBlankW(String s) {
		if (s == null) {
			return true;
		}
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) != ' ') && (s.charAt(i) != '　')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 入力文字列に含まれる文字が数字だけか否かを判定します。
	 * 空文字列とNullの場合はfalseを返します。<br/>
	 * Sẽ phán định xem kí tự được bao gồm trong chuỗi kí tự input có chỉ là chữ số hay không
     * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isDigits(String s) {
		if (s == null) {
			return true;
		}
		return s.matches("[0-9]*");
	}

	/**
	 * 入力文字列が正規表現パターンにマッチしているかを判定します。
	 * 空文字列とNullの場合はfalseを返します。<br/>
	 * Sẽ phán định xem chuổi kí tự input có đang match vào pattern biểu hiện chính qui không
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isPatternMatch(String pattern, String s) {
		if (s == null) {
			return true;
		}
		Perl5Util p5u = new Perl5Util();
		try {
			return p5u.match(pattern, s);

		} catch (MalformedPerl5PatternException e) {
			return false;
		}
	}

	/**
	 * 入力文字列のメールアドレスとしての妥当性を判定します。
	 * 空文字列とNullの場合はfalseを返します。<br/>
	 * 全角文字が含まれるとfalseを返します。<br/>
	 * Sẽ phán định tính thỏa đáng như là Mail address của chuổi kí tự input
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isMailAddress(String s) {
        try {
            boolean result = GenericValidator.isEmail(s);
        	// 全角文字が存在する場合は、falseを返す。
            if (result && hasFullwidhtChar(s)) {
        		result = false;
        	}
            // CT8customize_仕1800034_20180724_START
            if (s.contains(" ") || s.contains("　")) {
            	return false;
            }
            // CT8customize_仕1800034_20180724_END
        	return result;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
	}

	/**
	 * 全角文字が含まれる場合、trueを返します。
	 * 全角文字が含まれない場合、falseを返します。
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	private static boolean hasFullwidhtChar(String s) {
		boolean result = false;
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (String.valueOf(chars[i]).getBytes().length >= 2) {
				// 全角文字がある場合
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 入力文字列のメールアドレスとしての妥当性を判定します。
	 * 空文字列とNullの場合はfalseを返します。<br/>
	 * commons-validator-1.4.1.jarの仕様により、<br/>
	 * 全角文字が含まれいてもtrueを返します。<br/>
	 * Sẽ phán định tính thỏa đáng như là Mail address của chuổi kí tự input
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isMailAddressAllowFullwidthChar(String s) {
        try {
            return GenericValidator.isEmail(s);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
	}

	/**
	 * 入力文字列のＵＲＬとしての妥当性を判定します。
	 * 空文字列とNullの場合はfalseを返します。
	 * プロトコルは、http、https、ftpを許可します。<br/>
	 * Sẽ phán định tính thỏa đáng như là URL của chuỗi kí tự input
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * Protocol thì cho phép http、https、ftp
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isURL(String s) {
		return isPatternMatch("/^(https?|ftp):\\/\\/[\\w-.,:;~^\\/?@&=+$%#!]+$/", s);
	}

	/**
	 * 入力文字列の郵便番号としての妥当性(数字３桁-数字４桁であること)を判定します。
	 * 空文字列とNullの場合はfalseを返します。<br/>
	 * Sẽ phán định tính thỏa đáng (Phải là 3 kí tự chữ số-4 kí tự chữ số) như là No bưu điện của chuỗi kí tự input
	 * Trường hợp là chuỗi kí tự rỗng và Null thì sẽ trả về false
	 * @param s 判定対象文字列
	 * @return  判定結果
	 */
	public static boolean isZipCode(String s) {
		return isPatternMatch("/[0-9]{3}-[0-9]{4}/", s);
	}

	/**
	 * 引数の２つの文字列が両方とも指定されていることをチェックします。
	 * 両方とも指定されている場合はtrueを、指定されていない場合はfalseを返します。<br/>
	 * Sẽ check việc cả 2 chuỗi kí tự của tham số phải đang được chỉ định
	 * Trường hợp cả 2 đang được chỉ định thì trả vể true、Trường hợp chưa được chỉ định thì trả về false
	 * @param str1 チェック対象文字列を指定します。<br/>
	 * Sẽ Chỉ định chuỗi kí tự đối tượng check
	 * @param str2 チェック対象文字列を指定します。<br/>
	 * Sẽ Chỉ định chuỗi kí tự đối tượng check
	 */
	public static boolean checkBothRequired(String str1, String str2) {

		if ((str1.equals("")) && (str2.equals(""))) {
			return true;
		}
		if ((!str1.equals("")) && (!str2.equals(""))) {
			return true;
		}
		return false;
	}

	/**
	 * 引数の２つの文字列のいずれかが指定されていることをチェックします。
	 * いずれかが指定されている場合はtrueを、両方とも指定されていない場合はfalseを返します。
	 * Sẽ check cái nào trong số 2 chuỗi kí tự của tham số đang được chỉ định
	 * Trường hợp cái nào đó đang được chỉ định thì trả về true、trường hợp cả 2 cái chưa được chỉ định thì trả về false
	 * @param str1 チェック対象文字列を指定します。<br/>
	 * Sẽ Chỉ định chuỗi kí tự đối tượng check
	 * @param str2 チェック対象文字列を指定します。<br/>
	 * Sẽ Chỉ định chuỗi kí tự đối tượng check
	 */
	public static boolean checkOneRequired(String str1, String str2) {

		if (!str1.equals("")) {
			return true;
		}
		if (!str2.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 和暦日付の妥当性をチェックします。
	 * 元号、年、月、日のすべてが入力されている場合は、西暦変換を行い、
	 * 西暦日付を文字列(yyyymmdd形式)で返します。
	 * 元号、年、月、日がすべて指定されていない場合、または元号のみが
	 * 指定されている場合は、空文字列を返します。
	 * 年、月、日のいずれかの指定がない場合や暦日上存在しない日付の場合は
	 * nullを返します。  <br/>
	 * Sẽ check tính thỏa đáng của Date lịch Nhật 和暦日付
	 * Trường hợp toàn bộ 元号、年、月、日 đang được input thì tiến hành thay đổi lịch dương
	 * Trả Data lịch dương 西暦日付 bằng Chuỗi kí tự (kiểu yyyymmdd)
	 * Trường hợp toàn bộ 元号、年、月、日 chưa được chỉ định hoặc trường hợp chỉ 元号
	 * đang được chỉ định thì trả về chuỗi kí tự rỗng
	 * Trường hợp cái nào của 年、月、日 không có chỉ định và trường hợp Date mà không tồn tại trên 暦日上 thì
	 * trả về null
	 * @param g 元号
	 * @param y 年
	 * @param m 月
	 * @param d 日
	 * @return  西暦年月日(yyyymmdd形式)
	 */
	public static String checkJapaneseDate(String g, String y, String m, String d) {
		if (!isBlank(g) && !isBlank(y) && !isBlank(m) && !isBlank(d)) {
			DateUtil du = new DateUtil();
			du.setJapaneseDate(g, y, m, d);
			if (!du.getDate().equals("")) {
				return du.getDate();
			} else {
				return null;
			}
		} else {
			return "";
		}
	}

	/**
	 * 和暦年月の妥当性をチェックします。
	 * 元号、年、月のすべてが入力されている場合は、西暦変換を行い、
	 * 西暦年月を文字列(yyyymm形式)で返します。
	 * 元号、年、月がすべて指定されていない場合、または元号のみが
	 * 指定されている場合は、空文字列を返します。
	 * 年、月のいずれかの指定がない場合や暦日上存在しない年月の場合は
	 * nullを返します。<br/>
	 * Check tính thỏa đáng của 和暦年月
	 * Trường hợp toàn bộ 元号、年、月、 đang được input thì tiến hành thay đổi lịch dương
	 * Trả Data lịch dương 西暦日付 bằng Chuỗi kí tự (kiểu yyyymm)
	 * Trường hợp toàn bộ 元号、年、月 chưa được chỉ định hoặc trường hợp chỉ 元号
	 * đang được chỉ định thì trả về chuỗi kí tự rỗng
	 * Trường hợp cái nào của 年、月、không có chỉ định và trường hợp Date mà không tồn tại trên 暦日上
	 * thì trả về null
	 * @param g 元号
	 * @param y 年
	 * @param m 月
	 * @return  西暦年月(yyyymm形式)
	 */
	public static String checkJapaneseYearMonth(String g, String y, String m) {
		if (isBlank(g) && isBlank(y) && isBlank(m)) {
			DateUtil du = new DateUtil();
			du.setJapaneseYearMonth(g, y, m);
			if (du.getYearMonth().equals("")) {
				return du.getYearMonth();
			} else {
				return null;
			}
		} else {
			return "";
		}
	}

	/**
	 * 判定対象文字列（数字）が数字のみで構成されていることを判定します。<br>
	 * Phan đoan chuỗi đối tượng phan đoan (chữ số) phải đang được cấu thanh chỉ bằng chữ số.
	 * @param number 判定対象文字列<br>
	 * Chuỗi đối tượng phan đoan
	 * @return       判定結果<br>
	 * Kết quả phan đoan
	 */
	public static boolean isNumeric(String number) {
		if (isBlank(number)) {
			return false;
		} else {
			return number.matches("[0-9]+");
		}
	}

	/**
	 * 判定対象文字列（数字）が数字のみで構成され、指定した長さであることを判定します。<br>
	 * Phan đoan chuỗi đối tượng phan đoan (chữ số) phải đang được cấu thanh chỉ bằng chữ số va la độ dai đa chỉ định.
	 * @param number 判定対象文字列<br>
	 * Chuỗi đối tượng phan đoan
	 * @param count  判定対象文字列の長さ<br>
	 * Độ dai chuỗi đối tượng phan đoan
	 * @return       判定結果<br>
	 * Kết quả phan đoan
	 */
	public static boolean isFixNumeric(String number, int count) {
		if (isBlank(number) || count <= 0) {
			return false;
		} else {
			return number.matches("[0-9]{" + count + "}");
		}
	}

	/**
	 * <p>JISX0208文字集合であることをチェックする<br>
	 * Check phải la tập hợp ky tự JISX0208<br>
	 * JISX0208文字集合：JIS第一・第二水準漢字<br>
	 * Tập hợp ky tự JISX0208：JIS thứ 1・Kanji chuẩn thứ 2</p>
	 * @param value 任意の文字列 <br>
	 * Chuỗi tuy y
	 * @return OKの場合true、NGの場合false <br>
	 * Trường hợp OK true、Trường hợp NG false
	 */
	private static boolean checkJisX0208(String value) {
		if (value == null || value.length() == 0) return true;

		try {
			String converted = new String(value.getBytes("JIS0208"), "JIS0208");
			return value.equals(converted);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("実行環境がJIS X0208をサポートしていません。", e);
		}
	}

	/**
	 * EUC_JPで表現できることをチェックする<br>
	 * Check phải co thể thực hiện bằng EUC_JP
	 * @param value 任意の文字列<br>
	 * Chuỗi tuy y
	 * @return OKの場合true、NGの場合false<br>
	 * Trường hợp OK true、Trường hợp NG false
	 */
	private static boolean checkEUCJP(String value) {
		try {
			String converted = new String(value.getBytes("EUC_JP"), "EUC_JP");
			return value.equals(converted);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("実行環境がEUC_JPをサポートしていません。", e);
		}
	}

	/**
	 * 制御コードであることをチェックします。<br>
	 * Check phải la code control.
	 * @param value 任意の文字列<br>
	 * Chuỗi tuy y
	 * @return OKの場合true、NGの場合false<br>
	 * Trường hợp OK thi true、Trường hợp NG thi false
	 */
	private static boolean checkControl(String value) {
		CharacterIterator iter = new StringCharacterIterator(value);
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			if (Character.isISOControl(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * JIS X0208チェック(JISX0208 or CRLF)<pre>
	 * 【説明】
	 * 渡された文字列がJISX0208の範囲、又は改行コードであるかチェックします。
	 * </pre>
	 *
	 *
	 * Check JIS X0208(JISX0208 or CRLF)<pre>
	 * 【Giải thich】
	 * Check xem chuỗi đa được truyền la phạm vi JISX0208,hay la la code ngắt dong.
	 *  </pre>
	 *
	 * @param value チェック対象文字列<br>
	 * Chuỗi đối tượng check
	 * @return	チェック結果(true:OK false:NG)<br>
	 * Kết quả check (true:OK false:NG)<br>
	 * 			Nullや空文字が渡された場合は、trueを返します。<br>
	 * Trường hợp Null hay ky tự rỗng đa được truyền thi trả về true.
	 */
	public static boolean isValidX0208(String value) {
		value = correctLineCodeAndMappingDiff(value);
		if (value.length() == 0) return true;

		return checkJisX0208(value);
	}

	/**
	 *  全角スペース(0x3000) 前後入力チェック<pre>
	 * 【説明】
	 * 渡された文字列の前後に全角スペースが入っていないかをチェックします。
	 * </pre>
	 * Check input trước sau space 2byte (0x3000) <pre>
	 * 【Giải thich】
	 * Check xem co co khong co space 2byte ở trước sau chuỗi đa được truyền khong.
	 * </pre>
	 * @param value チェック対象文字列<br>
	 * Chuỗi đối tượng check
	 * @return	チェック結果(true:OK false:NG)<br>
	 * Kết quả check (true:OK false:NG)<br>
	 * 			Nullや空文字が渡された場合は、trueを返します。<br>
	 *  Trường hợp Null hay ky tự rỗng đa được truyền thi tra về true.
	 */
	private static boolean fullSizeTrimCheck(String value) {
		if (value == null || value.length() == 0) return true;

		int hex;
		int start = 0;
		int end = value.length() -1;
		hex = (int)value.charAt(start);
		if (hex == 0x3000) {
			return false;
		}
		hex = (int)value.charAt(end);
		if (hex == 0x3000) {
			return false;
		}
		return true;
	}

	/**
	 * 範囲チェック(文字範囲)<pre>
	 * 【説明】
	 * 渡された文字列が全銀協の規定するカナ文字列の範囲にあるかどうかをチェックします。
	 * </pre>
	 * Check phạm vi (phạm vi ky tự)<pre>
	 * 【Giải thich】
	 * Check xem chuỗi đa được truyền co nằm trong phạm vi chuỗi kana quy định của 全銀協 khong?
	 * </pre>
	 * @param value チェック対象文字列<br>
	 * Chuỗi đối tượng check
	 * @return	チェック結果(true:OK false:NG)<br>
	 * 			Kết quả check(true:OK false:NG)<br>
	 *          Nullや空文字が渡された場合は、trueを返します。<br>
	 *          Trường hợp Null hay ky tự rỗng đa được truyền thi trả về true.
	 *
	*/
	private static boolean checkJis0208ZenginKana(String value) {
		if (value == null || value.length() == 0) return true;

		int hex;
		for (int i = 0;(value.length() > i); i++) {
			hex = (int)value.charAt(i);
			if ((( hex >= 0x30A1 ) && (hex <= 0x30F6))
				|| (( hex >= 0x30FB ) && (hex <= 0x30FE))
				|| (hex == 0x3000)){
					//何もせず、処理を継続します。
					//Xử ly tiếp theo ma khong lam gi hết.
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 全銀テレ為替文字チェック<pre>
	 * 【説明】
	 * 入力文字列が全銀テレ為替文字(但しX0208の範囲)の範囲に入っているかチェックします。
	 * 入力文字列の前後にスペースが入っている場合はエラーとする。
	 * </pre>
	 * Check ky tự chuyển tiền điện bao(điện tin) toan bộ ngan hang<pre>
	 * 【Giải thich】
	 * Check xem chuỗi input co nằm trong phạm vi 全銀テレ為替文字(tuy nhien la phạm vi của X0208) khong?
	 * Trường hợp co space ở trước sau chuỗi input thi xem như error.
	 * </pre>
	 * @param value チェック対象文字列<br>
	 * Chuỗi đối tượng check
	 * @return	チェック結果(true:OK false:NG)<br>
	 * Kết quả check (true:OK false:NG)
	 */
	public static boolean isValidBank(String value)	{
		if (!checkJis0208ZenginKana(value)) {
			return false;
		}
		if (!fullSizeTrimCheck(value)) {
			return false;
		}
		return true;
	}

	/**
	 * ユーザ定義（OxE000-0xF8FF）を除く文字列のみで構成されていることをチェックします。<br>
	 * Check phải đang được cấu thanh chỉ bằng chuỗi ma loại trừ định nghĩa user （OxE000-0xF8FF）.
	 * @param value 任意の文字列<br>
	 * Chuỗi tuy y
	 * @return OKの場合true、NGの場合false<br>
	 * Trường hợp OK thi true、Trường hợp NG thi false
	 */
	public static boolean isNoUserDefined(String value) {
		if (value == null || value.length() == 0) return true;

		CharacterIterator iter = new StringCharacterIterator(value);
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			if ((c >= '\uE000') && (c <= '\uF8FF')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 有効な文字のみで構成された文字列であることをチェックします。<br>
	 * Check phải la chuỗi đa được cấu thanh chỉ bằng ky tự hữu hiệu.
	 * @param value 任意の文字列<br>
	 *  Chuỗi tuy y
	 * @return OKの場合true、NGの場合false<br>
	 * Trường hợp OK thi true、Trường hợp NG thi false
	 */
	public static boolean isValidString(String value) {
		String cnv = correctLineCodeAndMappingDiff(value);
		if (cnv.length() == 0)                 return true;

		if (checkControl(cnv))                 return false;
		if (!checkJisX0208(replaceAscii(cnv))) return false;
		return checkEUCJP(cnv);
	}

	/**
	 * 改行コードとJavaとOSのコードマッピングの異なる文字セットを訂正する<br>
	 * Đinh chinh set ky tự khac nhau của code mapping của OS va Java với code ngắt dong
	 * @param value 任意の文字<br>
	 * Chuỗi tuy y
	 * @return 訂正後の文字列<br>
	 *  Chuỗi sau đinh chinh
	 */
	private static String correctLineCodeAndMappingDiff(String value) {
		if (value == null) return "";
		return value.replaceAll("(\\r|\\n|\u2015|\uff5e|\u2225|\uff0d|\uffe0|\uffe1|\uffe2)", "");
	}

	/**
	 * ASCII文字集合を除く文字列を取得する<br>
	 * Get chuỗi loại trừ tập hợp ky tự ASCII
	 * @param value 任意の文字列<br>
	 * Chuỗi tuy y
	 * @return ASCII文字を除く文字列<br>
	 * Chuỗi loại trừ ky tự ASCII
	 */
	private static String replaceAscii(String value) {
		StringBuffer result = new StringBuffer();
		CharacterIterator iter = new StringCharacterIterator(value);
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			if (!((c >= '\u0000') && (c <= '\u007F'))) {
				result.append(String.valueOf(c));
			}
		}
		return result.toString();
	}

	/**
	 * Check file max size all item
	 * @param form
	 * @param strMaxSize vd: 10M
	 * @return
	 */
	public static boolean isNotMaxSizeFileUpload (MultipartFile file, String strMaxSize) {
		
		double fileSize = file.getSize();
		//Mb
		long megabyte = 1024L * 1024L;
		String strFileConfig = strMaxSize;
		//Split number contain vd: 10M
		long maxSize = Long.parseLong(strFileConfig.substring(0, strFileConfig.length() - 1));
		//Convert from byte to MB
		double fileSizeFinal = fileSize / megabyte;
		//If filesize > maxsize
		if (fileSizeFinal > maxSize) {
			return false;
		}
		return true;
	}
}
