/*
 * @(#)CommonProperties.java 01-00 2019/11/16
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
 */
public class CommonProperties {

    /**
     * プロパティファイル名
     */
    public static final String PROPERTIES_FILE = "common.properties";

    /**
     * プロパティ
     */
    private static Properties props = new Properties();

    /**
     * スタティックイニシャライザ。 プロパティのデフォルト値の設定と読み込みを行う。
     */
    static {
        try {
            InputStream in = (CommonProperties.class).getResourceAsStream("/" + PROPERTIES_FILE);
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
     * 指定されたキーのプロパティを返す。
     *
     * @param key 指定されたキー
     * @return String　指定されたキーのプロパティ
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * getPropertyNames
     *
     * @return propertyNames
     */
    @SuppressWarnings("rawtypes")
	public static Enumeration getPropertyNames() {
    	return props.propertyNames();
    }
}
//CT8customize_NC007_END