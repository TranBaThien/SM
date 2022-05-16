package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 複数パートから構成するラベルをもつコード\<br>
 *Code co label cấu thanh từ nhiều part 
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class DevidedLabelCode implements Code {

	/**
	 * 値
	 */
	private String value = "";

	/**
	 * ラベルのリスト <br>  list label
	 */
	private List<String> labels = new ArrayList<String>();

	/**
	 * コンストラクタ    <br>   constactor
	 */
	public DevidedLabelCode() {}

	/**
	 * コンストラクタ
	 * @param value 値
	 * @param labels パートのラベルの配列   Array của label cua part
	 */
	public DevidedLabelCode(String value, String... labels) {
		this.value = value;
		for (String label : labels) {
			this.labels.add(label);
		}
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
	 * @see jp.or.jacic.ct.common.util.Code#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		labels.add(label);
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#getLabel()
	 */
	public String getLabel() {
		StringBuffer result = new StringBuffer();
		for (String label : labels) {
			if (label != null) result.append(label);
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see jp.or.jacic.ct.common.util.Code#getLabelFrom(int)
	 */
	public String getLabelFrom(int pos) {
		if (pos < 0) {
			throw new IllegalArgumentException("posに負の数は指定できません。");
		} else if (labels.size() > pos) {
			return labels.get(pos);
		} else {
			return ""; // posがsizeより大きい場合、空文字を返す     trường hợp pos lớn hơn size thi trả về ki tự rỗng
		}
	}

	/**
	 * パートのラベルを設定します。<br>Setting label của part
	 * @param i パートのラベル位置    <br> vị tri label của part
	 * @param label パートのラベル   <br> label của part
	 */
	public void setLabelTo(int i, String label) {
		labels.set(i, label);
	}

	/**
	 * パートのラベルのリストを取得します。<br>
	 * Get list của label của part
	 * @return パートのラベルのリスト <br>
	 * list label của part 
	 */
	public List<String> getLabels() {
		return labels;
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
