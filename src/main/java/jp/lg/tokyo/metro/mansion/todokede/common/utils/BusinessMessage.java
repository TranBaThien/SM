package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.Serializable;

/**
 * ビジネス例外
 * ロジック層で発生した出力メッセージを保持するクラス。
 *
 * @author Hitachi Information Systems CO., Ltd.
 */
public class BusinessMessage implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * メッセージID
     */
    private String messageId = null;

    /**
     * メッセージの代替値
     */
    private Object[] values = null;

    /**
     * メッセージIDを返す。
     * @return メッセージID
     */
    public String getMessageId() {
        return (this.messageId);
    }

    /**
     * メッセージの代替値を返す。
     * @return メッセージの代替値
     */
    public Object[] getValues() {
        return (this.values);
    }

    /**
     * 置き換える値のないメッセージを作成するコンストラクタ。
     * @param messageId メッセージID
     */
    public BusinessMessage(String messageId) {
        this(messageId, new Object[0]);
    }

    /**
     * 指定された置き換える値を持つメッセージを作成するコンストラクタ。
     * Constructor tạo message co trị thay thế ma đa được chỉ định.
     * @param messageId メッセージID
     * @param values メッセージの代替値
     */
    public BusinessMessage(String messageId, Object... values) {
        this.messageId = messageId;
        this.values = values;
    }

}
