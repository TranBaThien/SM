package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.Serializable;

/**
 * シンプルなコード<br>
 * SimpleCode 
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
//CT8customize_A001_M1720C90055_20180709_START
//public class SimpleCode implements Code {
public class SimpleCode implements Code, Serializable {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 2435133083737446396L;
//CT8customize_A001_M1720C90055_20180709_END

	/**
	 * 値
	 */
	private String value = "";

	/**
	 * ラベル   label
	 */
	private String label;

	/**
	 * コンストラクタ
	 */
	public SimpleCode() {}

	/**
	 * コンストラクタ
	 * @param value 値
	 * @param label ラベル
	 */
	public SimpleCode(String value, String label) {
		super();
		this.value = value;
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#getValue()
	 */
	public String getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#getLabel(int)
	 */
	public String getLabelFrom(int pos) {
		if (pos < 0) {
			throw new IllegalArgumentException("posに負の数は指定できません。");
		} else if (pos == 0) {
			return label;
		} else {
			return ""; // posが0より大きい場合、空文字を返す              trả về ki tự rỗng trong trương hợp pos lớn hon 0
      
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Code) {
			Code another = (Code) obj;
			return getValue().equals(another.getValue());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

}
