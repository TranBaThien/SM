package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractCodeCSVReadImpl {

	/** commonロガー */
	private static final Logger LOG = LogManager.getLogger(AbstractCodeCSVReadImpl.class);

	/**
	 * コードのマップを構成する。<br>
	 *  Cấu thành map của code
	 * @param reader ラインリーダ
	 * @return コードのマップ
	 * @throws IOException IO例外
	 */
	protected static CodeUtil.CodeMap constructCodeMap(LineNumberReader reader) throws IOException {
		CodeUtil.CodeMap map = new CodeUtil.CodeMap();

		String id = null;
		String line = null;
		while (null != (line = reader.readLine())) {
			if (line.trim().startsWith("#")) {
				// #で開始の場合、コメント行
				LOG.trace(reader.getLineNumber() + "行目はコメントです。");
				continue;
			}
			// CSV解析
			String[] datas = CSVUtil.parseLine(line, ",", true);
			if (datas.length < 3) {
				// 3行未満の場合、スキップ（無視）する行
				LOG.trace(reader.getLineNumber() + "行目をスキップしました。");
			} else {
				// 3行以上の場合
				if (!datas[0].equals(id)) {
					// 前回と異なるIDの場合、先行してCodeList生成
					map.addCodeList(new CodeList(datas[0]));
				}
				// Code生成
				String[] labelParts = Arrays.copyOfRange(datas, 2, datas.length);
				CodeList list = (CodeList) map.getMap().get(datas[0]);
				list.addCode(CodeFactory.create(datas[1], labelParts));
				id = datas[0];
				LOG.trace(reader.getLineNumber() + "行目は" + datas.length + "列のデータです。");
			}
		}
		return map;
	}
}
