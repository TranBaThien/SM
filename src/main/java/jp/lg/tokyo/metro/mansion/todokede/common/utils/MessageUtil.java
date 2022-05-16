/*
 * @(#)MessageUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 *
 * <p>
 * メッセージの取得・編集機能を提供するクラスです。 strutsのリソースファイルからメッセージ文字列を取得するので、メッセージ管理を
 * 一元化することができます。
 * </p>
 *
 * @author PVHung3
 * @version 1.0
 *
 */
public class MessageUtil {

	// リソースバンドル
	private static PropertyResourceBundle resourceBundle;

	private static String editMessage(String key, String... args) {

		if (resourceBundle == null) {
			init();
		}
		// メッセージの取得
		String message = (String) resourceBundle.handleGetObject(key);
		// 可変値の置換
		for (int i=0; i<args.length; i++) {
			message = message.replace("{" + i +"}", args[i]);
		}

		if ((message == null) || (message.equals(""))) {
			// メッセージの取得に失敗したら例外を投げる
			throw new RuntimeException(
					"MessageUtil.getMessage()でメッセージの取得に失敗しました。　キー：" + key);
		} else {
			// 取得したメッセージを返す
			return message;
		}

	}

	// 初期化処理
	private static void init() {
		// InputStreamの生成
		try (InputStream fis = MessageUtil.class.getResourceAsStream("/message.properties")){
			// PropertyResourceBundleの生成
			resourceBundle = new PropertyResourceBundle(fis);

		} catch (Exception ex) {
			throw new RuntimeException("MessageUtilでリソースの取得に失敗しました。");
		}
	}

	/**
	 * keyの値によりリソースファイルよりメッセージを取得します。 リソースファイルの取得に失敗した場合は空文字列を返します。
	 * @param key リソースファイルのキー
	 * @param args メッセージ引数（メッセージ文字列内の{0}と置き換えます）
	 * @return メッセージ文字列
	 */
	public static String getMessage(String key, String... args) {
		// メッセージの取得
		return editMessage(key, args);
	}


}
