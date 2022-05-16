/*
 * @(#)StringUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.security.MessageDigest;
import java.util.Properties;

import javax.transaction.SystemException;

import org.apache.commons.lang3.RandomStringUtils;


/**
*
* <p>
* 文字列に関する共通機能を提供するクラスです。<br />
* Là class sẽ cung cấp chức năng common liên quan đến chuỗi ký tự.<br />
* This is the class which supplies common function related to string. 
* </p>
* 
* @author  PVHung3
* @version 1.0
* 
*/
public class StringUtil {
	
	// 半角カタカナ・全角カタカナ変換テーブル
	private static final String kanaHanZenTbl[][] = {
		// 2文字構成の濁点付き半角カナ
		{ "ｶﾞ", "ガ" }, { "ｷﾞ", "ギ" }, { "ｸﾞ", "グ" }, { "ｹﾞ", "ゲ" }, { "ｺﾞ", "ゴ" }, 
		{ "ｻﾞ", "ザ" }, { "ｼﾞ", "ジ" }, { "ｽﾞ", "ズ" }, { "ｾﾞ", "ゼ" }, { "ｿﾞ", "ゾ" },
		{ "ﾀﾞ", "ダ" }, { "ﾁﾞ", "ヂ" }, { "ﾂﾞ", "ヅ" }, { "ﾃﾞ", "デ" }, { "ﾄﾞ", "ド" },
		{ "ﾊﾞ", "バ" }, { "ﾋﾞ", "ビ" }, { "ﾌﾞ", "ブ" }, { "ﾍﾞ", "ベ" }, { "ﾎﾞ", "ボ" }, 
		{ "ﾊﾟ", "パ" }, { "ﾋﾟ", "ピ" }, { "ﾌﾟ", "プ" }, { "ﾍﾟ", "ペ" }, { "ﾎﾟ", "ポ" }, 
		{ "ｳﾞ", "ヴ" },
		// 1文字構成の半角カナ
		{ "ｱ", "ア" }, { "ｲ", "イ" }, { "ｳ", "ウ" }, { "ｴ", "エ" }, { "ｵ", "オ" }, 
		{ "ｶ", "カ" }, { "ｷ", "キ" }, { "ｸ", "ク" }, { "ｹ", "ケ" }, { "ｺ", "コ" }, 
		{ "ｻ", "サ" }, { "ｼ", "シ" }, { "ｽ", "ス" }, { "ｾ", "セ" }, { "ｿ", "ソ" }, 
		{ "ﾀ", "タ" }, { "ﾁ", "チ" }, { "ﾂ", "ツ" }, { "ﾃ", "テ" }, { "ﾄ", "ト" }, 
		{ "ﾅ", "ナ" }, { "ﾆ", "ニ" }, { "ﾇ", "ヌ" }, { "ﾈ", "ネ" }, { "ﾉ", "ノ" }, 
		{ "ﾊ", "ハ" }, { "ﾋ", "ヒ" }, { "ﾌ", "フ" }, { "ﾍ", "ヘ" }, { "ﾎ", "ホ" }, 
		{ "ﾏ", "マ" }, { "ﾐ", "ミ" }, { "ﾑ", "ム" }, { "ﾒ", "メ" }, { "ﾓ", "モ" }, 
		{ "ﾔ", "ヤ" }, { "ﾕ", "ユ" }, { "ﾖ", "ヨ" }, 
		{ "ﾗ", "ラ" }, { "ﾘ", "リ" }, { "ﾙ", "ル" }, { "ﾚ", "レ" }, { "ﾛ", "ロ" }, 
		{ "ﾜ", "ワ" }, { "ｦ", "ヲ" }, { "ﾝ", "ン" }, 
		{ "ｧ", "ァ" }, { "ｨ", "ィ" }, { "ｩ", "ゥ" }, { "ｪ", "ェ" }, { "ｫ", "ォ" }, 
		{ "ｬ", "ャ" }, { "ｭ", "ュ" }, { "ｮ", "ョ" }, { "ｯ", "ッ" }, 
		{ "｡", "。" }, { "｢", "「" }, { "｣", "」" }, { "､", "、" }, { "･", "・" }, 
		{ "ｰ", "ー" }, { "", "" }
	};
	
	/**
	 * <p>
	 * 文字列からSHA-1ハッシュ値を取得します。<br />
	 * Get trị hash SHA-1 từ chuỗi ký tự.<br />	 
	 * Get SHA-1 hash value from string.
	 * </p>
	 * @param s ハッシュ値に変換する文字列を指定します。<br/>
	 * 			Chỉ định chuỗi ký tự sẽ chuyển đổi thành trị hash.<br/>	 
	 *          Specify string which converts into hash value. 
	 * @return ハッシュ値が返されます。<br/>
	 * 			Được trả về trị hash.<br/>	 
	 *          Return hash value. 
	 * @throws SystemException 
	 */
	public static byte[] getHash(String s) throws SystemException {
		
		if (s == null)
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(s.getBytes("UTF-8"));
			return md.digest();
		} catch (Exception e) {
			throw new SystemException(
					"StringUtil.getHash()でハッシュ値の取得に失敗しました。");
		}
	}
	
	/**
	 * <p>
	 * 文字列からSHA-1ハッシュ値を取得します。paddingで指定された文字列を付加して
	 * 変換するので、辞書アタックによる攻撃でも破られにくくなります。<br />
	 * Get trị hash SHA-1 từ chuỗi ký tự. Vi sẽ gắn thêm chuỗi ký tự đã được chỉ
	 * định tại padding để chuyển đổi cho nên dù bị tấn công bởi attach từ điển cũng khó bị hỏng.<br />	 
	 *  Get SHA-1 hash value from string. Because we add specified string by padding and convert, 
	 *  so it become difficult to be broken even by attack by dictionary attack. 
	 * </p>
	 * @param s       ハッシュ値に変換する文字列を指定します。<br />
	 * 				Chỉ định chuỗi ký tự sẽ chuyển đổi thành trị hash.<br /> 
	 *                 Specify string which converts into  hash value.
	 * @param padding 変換対象文字列に付加する文字列を指定します。<br />
	 * 					Chỉ định chuổi ký tự sẽ gắn vào chuỗi ký tự đối tượng chuyển đổi.<br />	 
	 *                 Specify strings which adds in string to be converted
	 * @return ハッシュ値が返されます。<br />
	 * 			Được trả về trị hash.<br />	 
	 *          Return hash value. 
	 * @throws SystemException 
	 */
	public static byte[] getHash(String s, String padding) throws SystemException {

		return getHash(s + padding);
	}

	/**
	 * 文字列の先頭の半角空白を除去します。<br />
	 * Loại bỏ blank 1byte đầu chuỗi ký tự.<br />	 
	 * Remove half-width blank at the beginning of string.
	 * @param  s 変換前文字列<br />
	 * 			Chuỗi ký tự trước khi chuyển đổi<br />	 
	 *          String before converting
	 * @return 変換後文字列<br />
	 * 			Chuỗi ký tự sau khi chuyển đổi<br />	 
	 *          String after converting 
	 */
	public static String trimLeft(String s) {
		
		// 空白文字の除去
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				return s.substring(i);
			}
		}
		return "";
	}
	
	/**
	 * 文字列の末尾の半角空白を除去します。<br />
	 * Loại bỏ blank 1byte cuối chuỗi ký tự.<br />	 
	 * Remove half-width blank at the end of string. 
	 * @param  s 変換前文字列<br />
	 * 			Chuỗi ký tự trước khi chuyển đổi<br />	 
	 *            string before converting
	 * @return 変換後文字列<br />
	 * 			Chuỗi ký tự sau khi chuyển đổi<br />	 
	 *          string after converting
	 */
	public static String trimRight(String s) {
		
		// 空白文字の除去
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) != ' ') {
				return s.substring(0, i + 1);
			}
		}
		return "";
	}
	
	/**
	 * 文字列の先頭の全角空白および半角空白を除去します。<br />
	 * Loại bỏ blank 2byte và blank 1byte đầu chuỗi ký tự.<br />	 
	 * Remove half-width blank and full-width blank at the beginning of string. 
	 * @param  s 変換前文字列<br />
	 * 			Chuỗi ký tự trước khi chuyển đổi<br />	 
	 *            string before converting
	 * @return 変換後文字列<br />
	 *			Chuỗi ký tự sau khi chuyển đổi<br />	 
	 *          string after converting
	 */
	public static String trimLeftW(String s) {
		
		// 空白文字の除去
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) != ' ') && (s.charAt(i) != '　')){
				return s.substring(i);
			}
		}
		return "";
	}
	
	/**
	 * 文字列の末尾の全角空白および半角空白を除去します。<br />
	 * Loại bỏ blank 2byte và blank 1byte cuối chuỗi ký tự.<br />
	 * Remove half-width blank and full-width blank at the end of string. 
	 * @param  s 変換前文字列<br />
	 * 			Chuỗi ký tự trước khi chuyển đổi<br />
	 * 			string before converting
	 * @return   変換後文字列<br />
	 * 			Chuỗi ký tự sau khi chuyển đổi<br />
	 * 			string after converting
	 */
	public static String trimRightW(String s) {
		
		// 空白文字の除去
		for (int i = s.length() - 1; i >= 0; i--) {
			if ((s.charAt(i) != ' ') && (s.charAt(i) != '　')) {
				return s.substring(0, i + 1);
			}
		}
		return "";
	}
	
	/**
	 * 文字列の前後の全角空白および半角空白を除去します。<br/>
	 * Loại bỏ blank 2byte và blank 1byte trước và sau chuỗi ký tự.<br/>
	 * Remove half-width blank and full-width blank at the end/beginning of string. 
	 * @param  s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return   変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String trimW(String s) {
		return trimRightW(trimLeftW(s));
	}
	
	/**
	 * <p>文字列中の半角空白を除去します。<br/>
	 * Loại bỏ blank 1byte trong chuỗi ký tự.<br/>
	 * Remove half-width blank at the middle of string.</p> 
	 * <p>入力文字列がnullの場合、空文字列を返します。<br/>
	 * Trường hợp chuỗi ký tự input là null thì trả về chuỗi ký tự rỗng.<br/>	 
	 * If input string is null then return blank string. </p>
	 * @param  s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return   変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String spaceCut(String s) {

		// 入力文字列がnullの場合、空白を返す
		if (s == null) {
			return "";
		}
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>文字列中の全角空白および半角空白を除去します。<br/>
	 * Loại bỏ blank 2byte và blank 1byte trong chuỗi ký tự.<br/>	 
	 * Remove half-width blank and full-width blank at the middle of string.</p> 
	 * <p>入力文字列がnullの場合、空文字列を返します。<br/>
	 * Trường hợp chuỗi ký tự input là null thì trả về chuỗi ký tự rỗng.<br/>	 
	 * If input string is null then return blank string.</p>
	 * @param  s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return   変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String spaceCutW(String s) {
		
		// 入力文字列がnullの場合、空白を返す
		if (s == null) {
			return "";
		}
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) != ' ') && (s.charAt(i) != '　')) {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>文字列中の改行コードをＨＴＭＬの改行タグに置き換えます。<br/>
	 * Thay thế code đổi dòng trong chuỗi ký tự bằng tag đổi dòng của ＨＴＭＬ.</p>	 
	 * <p>入力文字列がnullの場合、空文字列を返します。<br/>
	 * Trường hợp chuỗi ký tự input là null thì trả về chuỗi ký tự rỗng.</p>	 
	 * @param  s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi	 
	 * @return   変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi	 
	 */
	public static String lineSeparator2br(String s) {
		
		// 入力文字列がnullの場合、空白を返す
		if (s == null) {
			return "";
		}
		// システムプロパティの取得
		Properties p = System.getProperties();
		// 改行コードの変換
		return s.replaceAll(p.getProperty("line.separator"), "<br>");
	}
	
	/**
	 * <p>ランダム文字列（英字）を取得します。<br/>
	 * Get chuỗi ký tự random （chữ tiếng Anh）.<br/>	 
	 * Get random string(aphabetical character).</p> 
	 * @param count 取得する文字数<br/>
	 * 				Số ký tự sẽ get	<br/>
	 * 				number of string to get
	 * @return      ランダム文字列<br/>
	 * 				Chuỗi ký tự random	<br/>
	 * 				random string
	 */
	public static String getRandomAlphabetic(int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}
	
	/**
	 * <p>ランダム文字列（数字）を取得します。<br/>
	 * Get chuỗi ký tự random （số）.	 <br/>
	 * Get random string(numerical character).</p> 
	 * @param count 取得する文字数<br/>
	 * 				Số ký tự sẽ get<br/>
	 * 				number of string to get
	 * @return      ランダム文字列<br/>
	 * 				Chuỗi ký tự random<br/>
	 * 				random string
	 */
	public static String getRandomNumeric(int count) {
		return RandomStringUtils.randomNumeric(count);
	}
	
	/**
	 * <p>ランダム文字列（英数字）を取得します。<br/>
	 * Get chuỗi ký tự random （chữ số tiếng Anh）.<br/>	 
	 * Get random string(alphanumeric character ).</p> 
	 * @param count 取得する文字数<br/>
	 * 				Số ký tự sẽ get<br/>
	 * 				number of string to get
	 * @return      ランダム文字列<br/>
	 * 				Chuỗi ký tự random<br/>
	 * 				random string
	 */
	public static String getRandomAlphanumeric(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}
	
	/**
	 * <p>入力文字列に含まれる全角カタカナを全角ひらがなに変換します。<br/>
	 * Chuyển đổi Kanakata 2byte có chứa trong chuỗi ký tự input thành Hiragana 2byte.<br/>	 
	 * Convert full-width Katakana which is included in input string into full-width Hiragana.</p> 
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String zenKatakana2ZenHiragana(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// 文字を変換します。
			sb.append(zenKatakana2ZenHiragana(c));
		}
		// 変換後文字列を戻します
		return sb.toString();
	}

	/**
	 * <p>入力として指定された全角カタカナ１文字を全角ひらがなに変換します。<br/>
	 * Chuyển đổi 1 ký tự Kanakata 2byte đã được chỉ định là input thành Hiragana 2byte.<br/>	 
	 * Convert 1 character of full-width Katakana which was specified as input into full-width Hiragana.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  変換後文字<br/>
	 * 			ký tự sau khi chuyển đổi<br/>
	 * 			character after converting
	 */
	public static Character zenKatakana2ZenHiragana(Character c) {
		
		// Unicode全角カタカナのコード範囲であるか調べます。
		if (isZenKatakana(c)) {
			// 全角カナ文字から0x0060を減算して全角かな文字に変換します。
			return new Character((char) (c.charValue() - (new Character((char)0x0060)).charValue()));
		} else { 
			// 全角カタカナ以外なら変換せずにそのまま戻り値へセットします。
			return c;
		}
	}
	
	/**
	 * <p>入力として指定された文字が全角カタカナかどうか判定します。<br/>
	 * Phán định xem ký tự đã được chỉ định là input có phải là Katakana 2byte hay không.<br/>	 
	 * Judge whether string which was specified as input is full-width Katakana or not.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  判定結果<br/>
	 * 			Kết quả phán định<br/>
	 * 			judging result
	 */
	public static boolean isZenKatakana(Character c) {
		
		// Unicode全角カタカナのコード範囲であるか調べます。
		if (c.compareTo(new Character((char)0x30a1)) >= 0
				&& c.compareTo(new Character((char)0x30f3)) <= 0) {
			// 全角カタカナ文字ならtrueを返します。
			return true;
		} else { 
			// 全角カタカナ以外ならfalseを返します。
			return false;
		}
	}
	
	/**
	 * <p>文字列に含まれる全角ひらがなを全角カタカナに変換します。<br/>
	 * Chuyển đổi Hiragana 2byte có chứa trong chuỗi ký tự input thành Katakana 2byte.<br/>	 
	 * Convert full-width Hiragana which is included in string into full-width Katakana.</p> 
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String zenHiragana2ZenKatakana(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// 文字を変換します。
			sb.append(zenHiragana2ZenKatakana(c));
		}
		// 変換後文字列を戻します
		return sb.toString();
	}

	/**
	 * <p>入力として指定された全角ひらがな１文字を全角カタカナに変換します。<br/>
	 * Chuyển đổi 1 ký tự Hiragana 2byte đã được chỉ định là input thành Katakana 2byte.<br/>	 
	 * Convert 1 character of full-width Hiragana which was specified as input into full-width Katakana.<p/>
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  変換後文字<br/>
	 * 			ký tự sau khi chuyển đổi<br/>
	 * 			character after converting
	 */
	public static Character zenHiragana2ZenKatakana(Character c) {
		
		// Unicode全角ひらがなのコード範囲であるか調べます。
		if (isZenHiragana(c)) {
			// 全角ひらかな文字に0x0060を加算して全角カタカナ文字に変換します
			return new Character((char) (c.charValue() + (new Character((char)0x0060)).charValue()));
		} else { 
			// 全角カタカナ以外なら変換せずにそのまま戻り値へセットします。
			return c;
		}
	}

	/**
	 * <p>入力として指定された文字が全角ひらがなかどうか判定します。<br/>
	 * Phán định xem ký tự đã được chỉ định là input có phải là Hiragana 2byte hay không.<br/>	 
	 * Judge whether string which was specified as input is full-width Hiragana or not.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  判定結果<br/>
	 * 			Kết quả phán định<br/>
	 * 			judging result
	 */
	public static boolean isZenHiragana(Character c) {
		
		// Unicode全角ひらがなのコード範囲であるか調べます。
		if (c.compareTo(new Character((char)0x3041)) >= 0
				&& c.compareTo(new Character((char)0x3093)) <= 0) {
			// 全角ひらがな文字ならtrueを返します。
			return true;
		} else { 
			// 全角ひらがな以外ならfalseを返します。
			return false;
		}
	}
	
	/**
	 * <p>文字列に含まれる半角ラテン文字列を全角ラテン文字列に変換します。<br/>
	 * Chuyển đổi chuỗi ký tự Latin 1byte có chứa trong chuỗi ký tự thành chuỗi ký tự Latin 2byte.<br/>	 
	 * Convert half-width Latin string which is included in string into full-width Latin string.</p>
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String hanLatin2Zen(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// 文字を変換します。
			sb.append(hanLatin2Zen(c));
		}
		//変換後文字列を戻します
		return sb.toString();
	}
	
	/**
	 * <p>入力として指定された半角ラテン文字１文字をを全角ラテン文字に変換します。<br/>
	 * Chuyển đổi 1 ký tự chữ Latin 1byte đã được chỉ định là input thành chữ Latin 2byte.<br/>	 
	 * Convert 1 character of half-width Latin character which was specified as input into full-width Latin character.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  変換後文字<br/>
	 * 			ký tự sau khi chuyển đổi<br/>
	 * 			character after converting
	 */
	public static Character hanLatin2Zen(Character c) {
		
		// Unicode半角ラテン文字のコード範囲であるか調べます。
		if (isHanLatin(c)) {
			// 変換文字に0xfee0を加算して全角文字に変換します
			return new Character((char) (c.charValue() + (new Character((char)0xfee0)).charValue()));
		} else { 
			// 半角ラテン文字以外なら変換せずにそのまま戻り値へセットします。
			return c;
		}
	}

	/**
	 * <p>入力として指定された文字が半角ラテン文字かどうか判定します。<br/>
	 * Phán định xem ký tự đã được chỉ định là input có phải là chữ Latin 1byte hay không.<br/>	 
	 * Judge whether string which was specified as input is half-width Latin character or not.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  判定結果<br/>
	 * 			kết quả phán định<br/>
	 * 			judging result
	 */
	public static boolean isHanLatin(Character c) {
		
		// Unicode半角ラテン文字のコード範囲であるか調べます。
		if (c.compareTo(new Character((char)0x0021)) >= 0
				&& c.compareTo(new Character((char)0x007e)) <= 0) {
			// 半角ラテン文字ならtrueを返します。
			return true;
		} else { 
			// 半角ラテン文字以外ならtrueを返します。
			return false;
		}
	}

	/**
	 * <p>文字列に含まれる全角ラテン文字を半角ラテン文字に変換します。<br/>
	 * Chuyển đổi chữ Latin 2byte có chứa trong chuỗi ký tự thành chữ Latin 1byte.<br/>	 
	 * Convert full-width Latin character which was included in string into half-width Latin character.</p>
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>	
	 * 			string after converting
	 */
	public static String zenLatin2Han(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// 文字を変換します。
			sb.append(zenLatin2Han(c));
		}
		//変換後文字列を戻します
		return sb.toString();
		
	}

	/**
	 * <p>入力として指定された全角ラテン文字１文字をを半角ラテン文字に変換します。<br/>
	 * Chuyển đổi 1 ký tự chữ Latin 2byte đã được chỉ định là input thành chữ Latin 1byte.<br/>	 
	 * Convert 1 character of full-width Latin character which was specified as input into half-width Latin character.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  変換後文字<br/>
	 * 			ký tự sau khi chuyển đổi<br/>
	 * 			character after converting
	 */
	public static Character zenLatin2Han(Character c) {
		
		// Unicode半角ラテン文字のコード範囲であるか調べます。
		if (isZenLatin(c)) {
			// 変換文字に0xfee0を減算して半角文字に変換します
			return new Character((char) (c.charValue() - (new Character((char)0xfee0)).charValue()));
		} else { 
			// 半角ラテン文字以外なら変換せずにそのまま戻り値へセットします。
			return c;
		}
	}

	/**
	 * <p>入力として指定された文字が全角ラテン文字かどうか判定します。<br/>
	 * Phán định xem ký tự đã được chỉ định là input có phải là chữ Latin 2byte hay không.<br/>	 
	 * Judge whether string which was specified as input is full-width Latin character or not.</p> 
	 * @param c 変換前文字<br/>
	 * 			ký tự trước khi chuyển đổi<br/>
	 * 			character before converting
	 * @return  判定結果<br/>
	 * 			kết quả phán định<br/>
	 * 			judging result
	 */
	public static boolean isZenLatin(Character c) {
		
		// Unicode全角ラテン文字のコード範囲(！から〜)であるか調べます
		if (c.compareTo(new Character((char)0xff01)) >= 0
				&& c.compareTo(new Character((char)0xff5e)) <= 0) {
			// 全角ラテン文字ならtrueを返します。
			return true;
		} else { 
			// 全角ラテン文字以外ならtrueを返します。
			return false;
		}
	}

	/**
	 * <p>文字列に含まれる半角カナを全角カナに変換します。<br/>
	 * Chuyển đổi Kana 1byte có chứa trong chuỗi ký tự thành kana 2byte.<br/>	 
	 * Convert half-width Kana which was included in string into full-width Kana.</p>
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String hanKatakana2Zen(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0, j = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// Unicode半角カタカナのコード範囲か調べます            
			if (c.compareTo(new Character((char)0xff61)) >= 0
					&& c.compareTo(new Character((char)0xff9f)) <= 0) {
				// 半角全角変換テーブルから半角カナにマッチするエントリを探し、
				// 対応する全角カナを取得して戻り文字列へセットします
				for (j = 0; j < kanaHanZenTbl.length; j++) {
					if (s.substring(i).startsWith(kanaHanZenTbl[j][0])) {
						sb.append(kanaHanZenTbl[j][1]);
						i += kanaHanZenTbl[j][0].length() - 1;
						break;
					}
				}
				// 半角全角変換テーブルに半角カナにマッチするエントリがなければ
				// 変換せずにそのまま戻り文字列へセットします
				if (j >= kanaHanZenTbl.length) {
					sb.append(s.substring(i, i + 1));
				}
			} else {
				// Unicode半角カタカナ以外なら変換せずにそのまま戻り文字列へセットします
				sb.append(s.substring(i, i + 1));
			}
		}
		// 変換後文字列を戻します
		return sb.toString();
	}

	/**
	 * <p>文字列に含まれる全角カナを半角カナに変換するメソッド<br/>
	 * Là method chuyển đổi Kana 2byte có chứa trong chuỗi ký tự thành kana 1byte.<br/>	 
	 * This is the method for converting full-width Kana which was included in string into half-width Kana.</p> 
	 * @param s 変換前文字列<br/>
	 * 			Chuỗi ký tự trước khi chuyển đổi<br/>
	 * 			string before converting
	 * @return  変換後文字列<br/>
	 * 			Chuỗi ký tự sau khi chuyển đổi<br/>
	 * 			string after converting
	 */
	public static String zenKatakana2Han(String s) {
		
		// 変換後文字列の定義
		StringBuffer sb = new StringBuffer();

		// パラメータの文字列を先頭から1文字づつ調べます
		for (int i = 0, j = 0; i < s.length(); i++) {
			// 文字列から１文字取り出します
			Character c = new Character(s.substring(i, i + 1).charAt(0));
			// Unicode全角カタカナのコード範囲か調べます
			if (c.compareTo(new Character((char)0x30a1)) >= 0
					&& c.compareTo(new Character((char)0x30fc)) <= 0) {
				// 半角全角変換テーブルから全角カナにマッチするエントリを探し、
				// 対応する半角カナを取得して戻り文字列へセットします
				for (j = 0; j < kanaHanZenTbl.length; j++) {
					if (s.substring(i).startsWith(kanaHanZenTbl[j][1])) {
						sb.append(kanaHanZenTbl[j][0]);
						break;
					}
				}
				// 半角全角変換テーブルの全角カナにマッチするエントリがなければ
				if (j >= kanaHanZenTbl.length) {
					sb.append(s.substring(i, i + 1));
				}
			} else {
				// 全角カタカナ以外なら変換せずにそのまま戻り文字列へセットします
				sb.append(s.substring(i, i + 1));
			}
		}
		// 変換後文字列を戻します
		return sb.toString();
	}

}
