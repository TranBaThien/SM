/*
 * @(#)ApplicationProperties.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.transaction.SystemException;

/**
 * <p>本システムで使用する基本的なアプリケーションプロパティクラス。</p>
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.0
 *
 * <pre>
 * ■変更履歴■
 * 2019/11/16 新規作成
 * </pre>
 */
public class ApplicationProperties {

    /**
     * プロパティファイル名
     */
    public static final String PROPERTIES_FILE = "application.properties";

    /**
     * プロパティ
     */
    private static Properties props = new Properties();

    /**
     * スタティックイニシャライザ。 プロパティのデフォルト値の設定と読み込みを行う。
     */
    static {
        try {
            InputStream in = (ApplicationProperties.class).getResourceAsStream("/" + PROPERTIES_FILE);
            props.load(in);
            in.close();
        } catch (IOException e) {
            try {
				throw new SystemException("can not load : " + PROPERTIES_FILE);
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }

    /**
     * コンストラクタ.
     */
    private ApplicationProperties() {
    }

    /**
     * 指定されたキーのプロパティを返す。
     *
     * @param key 指定されたキー
     * @return String　指定されたキーのプロパティ
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * プロパティのすべてのキーを返す。
     *
     * @return Enumeration　プロパティのすべてのキー
     */
    @SuppressWarnings("unchecked")
	public static Enumeration<String> propertyNames() {
        return (Enumeration<String>) props.propertyNames();
    }
}
