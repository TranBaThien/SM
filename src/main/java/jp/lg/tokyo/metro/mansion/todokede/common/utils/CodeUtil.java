package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.FilterUtil.Property;
import jp.lg.tokyo.metro.mansion.todokede.vo.CodeVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

/**
 * コードユーティリティ
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CodeUtil {
	/**
	 * コンテキストに格納するコードリストのマップのキー
	 */
	public static final String CODEMAP = CodeUtil.class.getName();

	/**
	 * コードユーティリティが保有するコードリストのマップ
	 */
	private static final Map<String, CodeList> codeMap = new HashMap<String, CodeList>();

	/**
	 * コンストラクタ使用禁止
	 */
	private CodeUtil() {}

	/**
	 * コードのリストを取得します。
	 * @param id ID
	 * @return コードのリスト 
	 */
	public static List<Code> getList(String id) {
		CodeList list = codeMap.get(id);
		return list.getList();
	}

	/**
	 * 値からラベルを取得します。値に対して複数のラベルが
	 * 存在するか、まったく存在しない場合、実行時例外を
	 * スローします。
	 * @param id ID
	 * @param value 値
	 * @return ラベル 
	 * @see java.lang.IllegalArgumentException
	 **/
	public static String getLabel(String id, String value) {
		CodeList list = codeMap.get(id);
		return list.getLabel(value);
	}

	/**
	 * 値からラベルを取得します。値に対して複数のラベルが
	 * 存在するか、まったく存在しない場合、実行時例外を
	 * スローします。
	 * @param id ID
	 * @param value 値
	 * @return DevidedLabelCode
	 * @see java.lang.IllegalArgumentException
	 **/
	public static DevidedLabelCode getLabels(String id, String value) {
		CodeList list = codeMap.get(id);
		return list.getLabels(value);
	}

	/**
	 * ラベルから値を取得します。ラベルに対して複数の値が
	 * 存在した場合、実行時例外をスローします。ラベルに対して
	 * 値がない場合、空文字を返します。
	 * @param id ID
	 * @param label ラベル
	 * @return 値
	 * @see java.lang.IllegalStateException
	 */
	public static  String getValue(String id, String label) {
		CodeList list = codeMap.get(id);
		return list.getValue(label);
	}

	/**
	 * ラベルから値を取得します。ラベルに対して複数の値が
	 * 存在した場合、または値がない場合、デフォルト値を返します。<br>
	 * @param id ID
	 * @param label ラベル
	 * @param defaultValue デフォルト値
	 * @return 値
	 */
	public static  String getValue(String id, String label, String defaultValue) {
		CodeList list = codeMap.get(id);
		return list.getValue(label, defaultValue);
	}

	/**
	 * 指定したラベルのprefixに前方一致するコードのリストを取得します。<br>
	 * @param id ID
	 * @param label ラベルのprefix
	 * @return コードのリスト
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public static List<Code> searchPrefix(String id, String label) {
		CodeList list = codeMap.get(id);
		return list.searchPrefix(label);
	}

	/**
	 * 指定したラベルのprefixに前方一致するコードのリストを取得します。
	 * @param id ID
	 * @param label ラベルのPrefix
	 * @param indexes 位置
	 * @return コードのリスト
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public static List<Code> searchPrefix(String id, String label, int... indexes) {
		CodeList list = codeMap.get(id);
		return list.searchPrefix(label, indexes);
	}

	/**
	 * 指定したラベルのpartに部分一致するコードのリストを取得します。<br>
	 * @param id ID
	 * @param label ラベルのpart
	 * @return コードのリスト
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public static List<Code> searchPart(String id, String label) {
		CodeList list = codeMap.get(id);
		return list.searchPart(label);
	}

	/**
	 * 指定したラベルのPrefixに前方一致するコードのリストを取得します。
	 * @param id ID
	 * @param label ラベルのPrefix
	 * @param indexes 位置
	 * @return コードのリスト
	 * @see jp.lg.tokyo.metro.mansion.todokede.common.utils.or.jacic.ct.common.util.Code
	 */
	public static List<Code> searchPart(String id, String label, int... indexes) {
		CodeList list = codeMap.get(id);
		return list.searchPart(label, indexes);
	}

	/**
	 * 値のPrefixから仮想的にコードのリストを生成し取得します。
	 * @param id ID
	 * @param value 値のPrefix
	 * @return 仮想コードのリスト
	 */
	public static List<Code> getVirtualCode(String id, String value) {
		CodeList list = codeMap.get(id);
		return list.getVirtualCode(value);
	}

	/**
	 * 指定した値のPrefixに前方一致するコードのリストを取得します。
	 * @param id ID
	 * @param value 値のPrefix
	 * @return 仮想コードのリスト
	 */
	public static List<Code> searchPrefixOfValue(String id, String value) {
		CodeList list = codeMap.get(id);
		return list.searchPrefixOfValue(value);
	}
	
	/**
	 * 値のPrefixから仮想コードのリストを生成し取得します。
	 * コードのリストから共通部分のラベルを取得します。
	 * @param list コードのリスト
	 * @return 共通部分のラベル
	 * Label của phần common 
	 */
	public static String getShareLabel(List<? extends Code> list) {
		return CodeList.getShareLabel(list);
	}

	/**
	 * コードのリストから異なる部分のラベルを取得します。
	 * @param list コードのリスト
	 * @return 異なるのラベル
	 */
	public static List<String> getDifferentLabel(List<? extends Code> list) {
		return CodeList.getDifferentLabel(list);
	}

	/**
	 * コードのリストの２つの要素からラベルの異なる文字列の位置を取得します。
	 * @param list コードのリスト
	 * @return ラベルの異なる文字列の位置
	 */
	public static int indexOfDifferentPart(List<? extends Code> list) {
		return CodeList.indexOfDifferentPart(list);
	}

    /**
     * コード値のチェック
     * @param id ID
     * @param value 値
     * @return boolean 
     */
    public static boolean checkValue(String id, String value) {
        List<Code> list = getList(id);
        List<Code> result =
            FilterUtil.equalFilter(list, true, new Property("value", value));

        return ((result != null) && (result.size() == 1));
    }

	/**
	 * コードユーティリティのヘルパークラス<br>
	 * コンテキストに格納するコードリストのマップを格納するために使用します。<br>
	 * 一般のクラスからこのクラスを使用しないでください。 
	 */
	public static class CodeMap {

		/**
		 * コードリストのマップにコードリストを設定します。
		 * @param list コードリスト	 
		 */
		public void addCodeList(CodeList list) {
			CodeList temp = codeMap.get(list.getId());
			if (temp != null) {
				List<Code> codes = list.getList();
				for (Code code :codes) {
					if (!codes.contains(code)) {
						temp.addCode(code);
					}
				}
			} else {
				codeMap.put(list.getId(), list);
			}
		}

		/**
		 * コードリストのマップを取得します。
		 * @return コードリストのマップ
		 */
		public Map<String, CodeList> getMap() {
			return codeMap;
		}
	}

	public static List<CodeVo> getListCodeVo(String codeId) {
		try {
			List<Code> lstSource = CodeUtil.getList(codeId);
			if (!CollectionUtils.isEmpty(lstSource)) {
				List<CodeVo> lstDest = new ArrayList<>();
				for (Code code : lstSource) {
					CodeVo vo = new CodeVo();
					BeanUtils.copyProperties(vo, code);
					lstDest.add(vo);
				}
				return lstDest;
			}
			return new ArrayList<>();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
