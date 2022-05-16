package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import org.apache.commons.digester3.Digester;

public class AbstractCodeXMLReadImpl {

	/**
	 * ダイジェスターを構成する。<br>
	 * Cấu thanh digester
	 * @param map コードマップ<br>
	 * 				Code map
	 * @return ダイジェスター<br>
	 * 				digester
	 */
	protected static Digester constructDigester(CodeUtil.CodeMap map) {
		Digester digester = new Digester();

		digester.addObjectCreate("root/code", CodeList.class);
		digester.addSetProperties("root/code", "id", "id");

		digester.addObjectCreate("root/code/property", SimpleCode.class);
		digester.addSetProperties("root/code/property", "value", "value");
		digester.addSetProperties("root/code/property", "label", "label");
		digester.addSetNext("root/code/property", "addCode", SimpleCode.class.getName());
		digester.addSetNext("root/code", "addCodeList", CodeList.class.getName());

		digester.push(map);
		return  digester;
	}
}
