/*
 * @(#) BusinessException.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author PVHung3
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.lg.tokyo.metro.mansion.todokede.common.utils.BusinessMessage;


/**
 * ビジネス例外。ビジネス例外はメッセージをもつ。
 * 本例外はビジネスロジック層からプレゼンテーション層にthrowする。
 *
 * @author Hitachi Information Systems CO., Ltd.
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	/** ルートレベルのメッセージ */
    private List<BusinessMessage> rootErrors = new ArrayList<>();
	/** フィールドバインドのメッセージ */
    private Map<String, BusinessMessage> fieldErrors = new LinkedHashMap<>();

    /**
     * コンストラクタ
     */
    public BusinessException() {
        super();
    }

    /**
     * コンストラクタ
     * @param message メッセージおよびメッセージID
     */
    public BusinessException(String message) {
        super(message);
        rootErrors.add(new BusinessMessage(message));
    }

    /**
     * コンストラクタ。
     *
     * @param message メッセージおよびメッセージID
     * @param args メッセージ引数
     */
    public BusinessException(String message, Object... args) {
        super(message);
        rootErrors.add(new BusinessMessage(message, args));
    }

    /**
     * コンストラクタ
     *
     * @param field フィールド
     * @param message メッセージおよびメッセージID
     * @param args メッセージ引数
     */
    public BusinessException(String field, String message, Object... args) {
        super(message);
        fieldErrors.put(field, new BusinessMessage(message, args));
    }

    /**
     * コンストラクタ。
     *
     * @param cause 発生元例外
     * @param message メッセージおよびメッセージID
     * @param args メッセージ引数
     */
    public BusinessException(Throwable cause, String message, Object... args) {
        super(message, cause);
        rootErrors.add(new BusinessMessage(message, args));
    }

    /**
     * コンストラクタ
     *
     * @param businessMessage ビジネスメッセージ
     */
    public BusinessException(BusinessMessage businessMessage) {
        super(businessMessage.getMessageId());
        rootErrors.add(businessMessage);
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージおよびメッセージID
     * @param businessMessage ビジネスメッセージ
     */
    public BusinessException(String message, BusinessMessage businessMessage) {
        super(message);
        rootErrors.add(businessMessage);
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージおよびメッセージID
     * @param businessMessageList ビジネスメッセージのリスト
     */
    public BusinessException(String message, List<BusinessMessage> businessMessageList) {
        super(message);
        rootErrors.addAll(businessMessageList);
    }

    /**
     * ビジネスメッセージのListを返す。
     * @return 出力メッセージのList
     */
    public List<BusinessMessage> getMessageList() {
    	List<BusinessMessage> result = new ArrayList<>();
    	result.addAll(rootErrors);
    	result.addAll(fieldErrors.values());
        return result;
    }

    /**
     * ルートレベルのメッセージを取得する。
     * @return ルートレベルのメッセージ
     */
    public List<BusinessMessage> getRootErrors() {
    	return rootErrors;
    }

    /**
     * フィールドバインドのメッセージを取得する。
     * @return フィールドバインドのメッセージ
     */
    public Map<String, BusinessMessage> getFieldErrors() {
    	return fieldErrors;
    }

    /**
     * 出力メッセージを追加する。
     * @param businessMessage ビジネスメッセージ
     */
    public void addMessage(BusinessMessage businessMessage) {
        rootErrors.add(businessMessage);
    }

    /**
     * メッセージを追加する。
     * @param field フィールド
     * @param businessMessage ビジネスメッセージ
     */
    public void addMessage(String field, BusinessMessage businessMessage) {
    	fieldErrors.put(field, businessMessage);
    }

    /**
     * ッセージを追加する。
     * @param message メッセージID
     * @param args メッセージ引数
     */
    public void addMessage(String message, Object... args) {
        rootErrors.add(new BusinessMessage(message, args));
    }

    /**
     * メッセージを追加する。
     * @param field フィールド
     * @param message メッセージID
     * @param args メッセージ引数
     */
    public void addMessage(String field, String message, Object... args) {
        fieldErrors.put(field, new BusinessMessage(message, args));
    }


    /**
     * メッセージが１件以上ある場合、当例外をスローする
     * @throws BusinessException メッセージが１件以上ある場合
     */
    public void throwsIfPresent() throws BusinessException {
    	if (getMessageList().size() > 0) throw this;
    }

}
