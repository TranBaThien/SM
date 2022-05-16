package jp.lg.tokyo.metro.mansion.todokede.common.utils;

/**
 * コード
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public interface Code {

	/**
	 * 値を取得します。    
	 * @return 値
	 */
	public abstract String getValue();

	/**
	 * 値を設定します。   
	 * @param value 値
	 */
	public abstract void setValue(String value);

	/**
	 * ラベルを取得します。
	 * @return ラベル
	 */
	public abstract String getLabel();

	/**
	 * 指定した位置からラベルを取得します。
	 * @param pos 位置
	 * @return ラベル
	 */
	public abstract String getLabelFrom(int pos); 

	/**
	 * ラベルを設定します。 
	 * @param label ラベル
	 */
	public abstract void setLabel(String label);
	

}