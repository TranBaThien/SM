package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CSV書式を表現するクラス<br>
 * Class biểu hiện format CSV
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CSVFormat {

	/**
	 * Separator of default
	 */
	public static final String SEP = ",";

	/**
	 * コンストラクタ<br>
	 * Constructor
	 */
	public CSVFormat() {
		this(SEP);
	}

	/**
	 * コンストラクタ<br>
	 *Constructor
	 * @param sep セパレータ<br>
	 * Separator
	 */
	public CSVFormat(String sep) {
		fieldsep = sep;
	}

	/**
	 * 現在のフィールド<br>
	 * Field hiện tại
	 */
	protected List <String> list = new ArrayList <String> ();

	/**
	 * セパレータ<br>
	 * Separator
	 */
	protected String fieldsep = null;

	/**
	 * 入力された文字列をフィールドに分割しIteratorを取得します。<br>
	 * Phan chia chuỗi ki tự đa được input thanh field rồi get Iterator .
	 * @param line 文字列<br>
	 * Chuỗi ki tự
	 * @return java.util.Iterator
	 */
	public Iterator<String> parse(String line) {
		StringBuffer sb = new StringBuffer();
		list.clear();
		int i = 0;

		if (line.length() == 0) {
			list.add(line);
			return list.iterator();
		}

		do {
			sb.setLength(0);
			if (i < line.length() && line.charAt(i) == '"') {
				i = advanceQuoted(line, sb, ++i); // 引用符をスキップ
			} else {
				i = advancePlain(line, sb, i);
			}
			list.add(sb.toString());
			i++;
		} while (i < line.length());
		// M1720CL0125 20180829 START
		if (line.endsWith(fieldsep)) {
			list.add("");
		}
		// M1720CL0125 20180829 END
		return list.iterator();
	}

	/**
	 * 引用符つきのフィールドに対し、次の区切りの位置を取得します。<br>
	 * Get vị tri ngăn cach tiếp theo đối với field gắn quotation mark .
	 * @param s  文字列
	 * @param sb StringBuilder
	 * @param i Start Position
	 * @return int
	 */
	private int advanceQuoted(String s, StringBuffer sb, int i) {
		int j;

		for (j = i; j < s.length(); j++) {
			// エスケープなしの引用符であれば、フィールドの終わりと見なす
			if (s.charAt(j) == '"' && s.charAt(j-1) != '\\') {
				int k = s.indexOf(fieldsep, j);
				if (k == -1) {
					k += s.length();
					for (k -= j; k-- > 0;) {
						sb.append(s.charAt(j++));
					}
				} else {
					--k; // 引用符を除く
					for (k -= j; k-- > 0;) {
						sb.append(s.charAt(j++));
					}
					++j; // 引用符をスキップする

				}
				break;
			}
			sb.append(s.charAt(j)); // 普通の文字
		}

		return j;
	}

	/**
	 * 引用符なしのフィールドに対し、次の区切りの位置を取得します。<br>
	 * Get vị tri ngăn cach tiếp theo đối với field khong gắn  quotation mark.
	 * @param s  文字列
	 * @param sb StringBuilder
	 * @param i Start Position
	 * @return int
	 */
	private int advancePlain(String s, StringBuffer sb, int i) {

		int j = s.indexOf(fieldsep, i);
		if (j == -1) {
			sb.append(s.substring(i));
			return s.length();
		} else {
			sb.append(s.substring(i, j));
			return j;
		}
	}

	/**
	 * 文字列の配列をCSV形式に変換します。<br>
	 * Chuyển đổi array của chuỗi ki tự thanh hinh thức CSV.
	 * @param lines 文字列の配列<br>
	 * Array của chuỗi ki tự
	 * @return CSV形式の文字列<br>
	 *  Chuỗi ki tự của hinh thức CSV
	 */
	public String format(String[] lines) {
		StringBuffer sb = new StringBuffer();

		for (int i=0; i<lines.length; i++) {
			String replaced = lines[i].replaceAll("\\\"", "\\\"\\\"");
			if (lines[i].equals(replaced) && lines[i].indexOf(fieldsep) == -1) {
				sb.append(lines[i]);
			} else {
				sb.append("\"" + replaced + "\"");
			}
			if (i != lines.length-1) {
				sb.append(fieldsep);
			}
		}
		return sb.toString();
	}

}
