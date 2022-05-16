package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.FilterUtil.Property;


/**
 * コードリスト<br>
 * List code 
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CodeList {

	/**
	 * ID
	 */
	private String id ="";

	/**
	 * コードのコレクション<br>
	 * Collection của code	 
	 */
	private List<Code> list = new ArrayList<Code>();

	/**
	 * コンストラクタ<br>
	 * Constructor	 
	 */
	public CodeList() {}

	/**
	 * コンスタラクタ<br>
	 * Constructor	 
	 * @param id ID
	 */
	public CodeList(String id) {
		this.id = id;
	}

	/**
	 * IDを取得します。<br>
	 * Get ID	 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * IDを設定します。<br>
	 * Setting ID	 
	 * @param id ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 値からラベルを取得します。値に対して複数のラベルが
	 * 存在するか、まったく存在しない場合、実行時例外を
	 * スローします。<br>
	 * Get Label	từ trị. Đối với trị nếu tồn tại nhiều label hay hoan toan khong tồn tại
	 * thi khi thực thi sẽ throw exception  
	 * @param value 値
	 * @return ラベル
	 */
	public String getLabel(String value) {
		List<Code> result = FilterUtil.equalFilter(list, true, new Property("value", value));
		if (result.size() == 1) {
			return result.get(0).getLabel();
		} else {
            return "";
		}
	}

	/**
	 * ラベルから値を取得します。ラベルに対して複数の値が
	 * 存在した場合、実行時例外をスローします。ラベルに対して
	 * 値がない場合、空文字を返します。<br>
	 * Get trị	từ label. Đối với label nếu tồn tại nhiều trị thi khi thực thi sẽ throw exception	 
	 * Đối với label nếu khong co trị thi trả về ky tự rỗng.	 
	 * @param label ラベル
	 * @return 値
	 * @see java.lang.IllegalStateException
	 */
	public String getValue(String label) {
		List<Code> result = FilterUtil.equalFilter(list, true, new Property("label", label));
		if (result.size() > 1) {
			throw new IllegalStateException("コードを特定できません。");
		} else if(result.size() == 1) {
			return result.get(0).getValue();
		} else {
			return "";
		}
	}

	/**
	 * 値からラベルを取得します。値に対して複数のラベルが
	 * 存在するか、まったく存在しない場合、実行時例外を
	 * スローします。<br>
	 * Get Label	từ trị. Đối với trị nếu tồn tại nhiều label hay hoan toan khong tồn tại
	 * thi khi thực thi sẽ throw exception 	 
	 * @param value 値
	 * @return DevidedLabelCode
	 */
	public DevidedLabelCode getLabels(String value) {
		List<Code> result = FilterUtil.equalFilter(list, true, new Property("value", value));
		if (result.size() == 1) {
			return (DevidedLabelCode) result.get(0);
		} else {
            return new DevidedLabelCode();
		}
	}

	/**
	 * ラベルから値を取得します。ラベルに対して複数の値が
	 * 存在した場合、または値がない場合、デフォルト値を返します。<br>
	 * Get trị	từ label. Đối với label nếu tồn tại nhiều trị hay trường hợp khong	 
	 * co trị thi trả về trị default.	 
	 * @param label ラベル
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public String getValue(String label, String defaultValue) {
		List<Code> result = FilterUtil.equalFilter(list, true, new Property("label", label));
		if (result.size() == 1) {
			return result.get(0).getValue();
		} else {
			return defaultValue;
		}
	}

	/**
	 * 指定した値のPrefixに前方一致するコードのリストを取得します。<br>
	 * Get list của code ma phu hợp phần đầu với Prefix của trị đa chỉ định.	 
	 * @param value 値のPrefix<br>
	 * Prefix của trị	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> searchPrefixOfValue(String value) {
		return FilterUtil.forwardFilter(list, true, new Property("value", value));
	}

	/**
	 * 指定したラベルのPrefixに前方一致するコードのリストを取得します。<br>
	 * Get list của code ma phu hợp phần đầu với Prefix của label đa chỉ định.	 
	 * @param label ラベルのPrefix<br>
	 * Prefix của label	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> searchPrefix(String label) {
		return FilterUtil.forwardFilter(list, true, new Property("label", label));
	}

	/**
	 * 指定したラベルのPrefixに前方一致するコードのリストを取得します。<br>
	 * Get list của code ma phu hợp phần đầu với Prefix của trị đa chỉ định.	 
	 * @param label ラベルのPrefix<br>
	 * Prefix của label	 
	 * @param indexes 位置<br>
	 * Vị tri	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> searchPrefix(String label, int... indexes) {
		return FilterUtil.forwardFilter(list, false, buildLabelFromProperty(label, indexes));
	}

	/**
	 * 指定したラベルのpartに部分一致するコードのリストを取得します。<br>
	 * Get list của code ma phu hợp 1 phần với part của label đa chỉ định.		 
	 * @param label ラベルのPart<br>
	 * Part của label	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> searchPart(String label) {
		return FilterUtil.containFilter(list, true, new Property("label", label));
	}

	/**
	 * 指定したラベルのpartに部分一致するコードのリストを取得します。<br>
	 * Get list của code ma phu hợp 1 phần với part của label đa chỉ định.		 
	 * @param label ラベルのPart<br>
	 * Part của label	 
	 * @param indexes 位置<br>
	 * Vị tri	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> searchPart(String label, int... indexes) {
		return FilterUtil.containFilter(list, false, buildLabelFromProperty(label, indexes));
	}

	/**
	 * i番目のlabelFromプロパティの配列を取得します。<br>
	 * Get mảng của property labelFrom của No thứ i
	 * @param label ラベル<br> Label
	 * @param indexes 位置<br> Vị trí
	 * @return Propertyの配列<br>
	 * Mang của Property	 
	 */
	private Property[] buildLabelFromProperty(String label, int... indexes) {
		List<Property> ps = new ArrayList<Property>();
		for (int i : indexes) {
			ps.add(new Property("labelFrom[" + i + "]", label));
		}
		return ps.toArray(new Property[0]);
	}

	/**
	 * コードのリストすべてを取得します。<br>
	 * Get toan bộ list của code	 
	 * @return コードのリスト<br>
	 * List code	 
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public List<Code> getList() {
		return  Collections.unmodifiableList(list);
	}

	/**
	 * 値のPrefixから仮想的にコードのリストを生成し取得します。<br>
	 * Tạo list code mang tinh giả định từ Prefix của trị rồi get.	 
	 * @param value 値のPrefix<br>
	 * Prefix của trị	 
	 * @return 仮想コードのリスト<br>
	 * List code giả định	 
	 */
	public List<Code> getVirtualCode(String value) {
		List<Code> result = new ArrayList<Code>();

		List<Code> temp = searchPrefixOfValue(value);
		int pos = indexOfDifferentPart(temp);
		for (Code c : temp) {
			String modValue = c.getValue().substring(value.length());
			String modLabel = c.getLabel().substring(pos);
			if (modValue.length() > 0) {
				result.add(CodeFactory.create(modValue, modLabel));
			}
		}
		return result;
	}

	/**
	 * コードのリストから共通部分のラベルを取得します。<br>
	 * Get label của phần common từ list code	 
	 * @param list コードのリスト<br>
	 * List code	 
	 * @return 共通部分のラベル<br>
	 * Label của phần common	 
	 */
	public static String getShareLabel(List<? extends Code> list) {
		if (list == null) throw new NullPointerException("引数にnullを指定しています。");

		if (list.size() >= 1) {
			String label = list.get(0).getLabel();
			return label.substring(0, indexOfDifferentPart(list));
		} else {
			return "";
		}
	}

	/**
	 * コードのリストから異なる部分のラベルを取得します。<br>
	 * Get label của phần khac từ list code	 
	 * @param list コードのリスト<br>
	 * List code	 
	 * @return 異なるのラベル<br>
	 * Label khac	 
	 */
	public static List<String> getDifferentLabel(List<? extends Code> list) {
		if (list == null) throw new NullPointerException("引数にnullを指定しています。");

		List<String> result = new ArrayList<String>();

		int pos = indexOfDifferentPart(list);
		for (Code code : list) {
			if (list.size() > 1) {
				String label = code.getLabel();
				result.add(label.substring(pos));
			} else {
				result.add("");
			}
		}
		return result;
	}

	/**
	 * コードのリストの２つの要素からラベルの異なる文字列の位置を取得します。<br>
	 * Get vị tri của chuỗi khac của label từ 2 yếu tố của list code	 
	 * @param list コードのリスト<br>
	 * List code	 
	 * @return ラベルの異なる文字列の位置<br>
	 * Vị tri của chuỗi khac của label 	 
	 */
	public static int indexOfDifferentPart(List<? extends Code> list) {

		if (list.size() > 1) {
			String one = list.get(0).getLabel();
			String ano = list.get(1).getLabel();

			int scanlen = (one.length() <= ano.length())? one.length(): ano.length();
			for (int i=0; i<scanlen; i++) {
				if (one.charAt(i) != ano.charAt(i)) return i;
			}
			return scanlen;
		} else {
			return 0;
		}
	}

	/**
	 * コードをコードリストに追加します。<br>
	 * Add them code vao list code	 <br>
	 * &#91;注意&#93;このメソッドはPlugInからのみ呼び出すことを想定しています。<br>
	 * &#91;Chú ý&#93;Đang giả định method nay chỉ gọi ra từ PlugIn	 
	 * @param code コード
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public void addCode(Code code) {
		list.add(code);
	}
}
