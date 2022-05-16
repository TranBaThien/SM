package jp.lg.tokyo.metro.mansion.todokede.common.utils;

/**
 * コードファクトリ<br>
 * Factory code
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CodeFactory {

	/**
	 * コンストラクタ使用禁止<br>
	 * Cấm sử dụng constructor	 
	 */
	private CodeFactory() {}

	/**
	 * ファクトリメソッド<br>
	 * Method factory	 
	 * @param value 値
	 * @param labels ラベル
	 * @return コード
	 */
	public static Code create(String value, String... labels) {
		if (labels.length == 1) {
			return new SimpleCode(value, labels[0]);
		} else {
			return new DevidedLabelCode(value, labels);
		}
	}
}
