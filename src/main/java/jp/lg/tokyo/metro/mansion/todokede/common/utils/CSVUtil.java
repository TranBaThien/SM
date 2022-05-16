package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CSV操作に関するユーティリティ<br>
 * utility lien quan thao tac CSV 
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CSVUtil {

	/**
	 * CSV形式の文字列をプレーンな文字列の配列に変換する。<br>
	 * Chuyển đổi chuỗi ki tự của kiễu CSV thanh array của chuỗi ki tự plane   
	 *
	 * @param input CSV形式の文字列
	 * @return プレーンな文字列の配列
	 */
	public static String[] parseLine(String input) {
		return parseLine(input, CSVFormat.SEP, false);
	}

	/**
	 * CSV形式の文字列をプレーンな文字列の配列に変換する。<br>
	 * Chuyển đổi chuỗi ki tự của kiễu CSV thanh array của chuỗi ki tự plane  
	 * @param input CSV形式の文字列
	 * @param sep セパレータ
	 * @param trim トリムする場合true、その他の場合false<br>
	 * Trường hợp trim thi la true、 trường hơp khac thi la false  
	 * @return プレーンな文字列の配列
	 */
	public static String[] parseLine(String input, String sep, boolean trim) {
		List<String> results = new ArrayList<String>();

		CSVFormat csv = new CSVFormat(sep);
		Iterator<String> it = csv.parse(input);
		while(it.hasNext()) {
			if (trim)  {
				results.add(((String) it.next()).trim());
			} else {
				results.add((String) it.next());
			}
		}
		return (String[]) results.toArray(new String[0]);
	}

	/**
	 * プレーンな文字列の配列をCSV形式の文字列に変換する。<br>
	 * 	Chuyển đổi array của chuỗi ki tự plane thanh chuỗi ki tự của kiễu CSV   
	 * @param inputs 文字列
	 * @return CSV形式の文字列
	 */
	public static String formatLine(String[] inputs) {
		return formatLine(inputs, CSVFormat.SEP, false);
	}

	/**
	 * プレーンな文字列の配列をCSV形式の文字列に変換する。<br>
	 * Chuyển đổi array của chuỗi ki tự plane thanh chuỗi ki tự của kiễu CSV    
	 * @param inputs 文字列
	 * @param sep セパレータ
	 * @param trim トリムする場合true、その他の場合false<br>
	 * Trường hợp trim thi la true、 trường hơp khac thi la false   
	 * @return CSV形式の文字列
	 */
	public static String formatLine(String[] inputs, String sep, boolean trim) {
		List<String> temp = new ArrayList<String>();

		CSVFormat csv = new CSVFormat(sep);
		for (String input : inputs) {
			if (trim) {
				temp.add(input.trim());
			} else {
				temp.add(input);
			}
		}
		return csv.format(temp.toArray(new String[0]));
	}
}
