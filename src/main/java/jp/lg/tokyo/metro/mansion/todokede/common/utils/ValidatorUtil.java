package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

//20090109_TT4A0013_END
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * バリデーションに関するユーティリティ<br>
 * Tiện ích liên quan đến validation
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class ValidatorUtil {

	/** commonロガー */
	private static final Logger LOG = LogManager.getLogger(ValidatorUtil.class);

    /**
     * バリデーション定義ファイルを読取り、指定されたフォーム（Bean）を検証します<br>
     * Đọc lấy file định nghĩa validation、kiểm chứng form （Bean） đả được chỉ định
     * @param formName フォーム（Bean）名
     * @param bean Bean
     * @param apps メッセージリソース
     * @return エラーメッセージの配列
     */
    public static String[] validate(String formName, Object bean, ResourceBundle apps) {

        // バリデーション定義からBeanに対するバリデーションを作成する
      // Tạo validation đối với Bean từ định nghĩa validation
        Validator validator = new Validator(Definition.get(), formName);
        validator.setParameter(Validator.BEAN_PARAM, bean);

        ValidatorResults results;
        try {
        	// バリデーションを実行する
          // Thực thi validation

            results = validator.validate();
        } catch (ValidatorException e) {
            throw new IllegalStateException("バリデーション定義に誤りがあります。", e);
        }

    	// バリデーション結果を解析する
      // Giải tich kết quả validation
        return parseResults(formName, bean, results, Definition.get(), apps);
    }

    /**
     * バリデーション結果を解析し、エラーメッセージを返却します。<br>
     * Giải tich kết quả validation、trả về error message
     * @param formName フォーム（Bean）名
     * @param bean Bean
     * @param results バリデーション結果
     * @param res バリデーションリソース
     * @param app メッセージリソース
     * @return エラーメッセージの配列
     */
    @SuppressWarnings("rawtypes")
	private static String[] parseResults(String formName, Object bean, ValidatorResults results, ValidatorResources res, ResourceBundle app) {

    	List<String> msgs = new ArrayList<String>();

        // フォーム名からフォームを取得する
        // Get form từ ten form
        Form form = res.getForm(Locale.getDefault(), formName);

        // 結果からプロパティ名を取得する.
        // Get ten property từ kết quả
        Iterator properties = results.getPropertyNames().iterator();
        while (properties.hasNext()) {
        	// プロパティ名を取得する
          // Get ten property
            String property = (String) properties.next();

            // プロパティ名から検証結果を取得する
              // Get kết quả kiểm chứng từ ten property
            ValidatorResult result = results.getValidatorResult(property);

            // プロパティ名に対する検証アクションのループ
            // Loop của action kiểm chứng đối với ten property
            Iterator keys = result.getActions();
            while (keys.hasNext()) {
            	// 検証名を取得する
              // Get ten kiểm chứng
                String name = (String) keys.next();

                // 検証名から検証アクションを取得する
                // Get action kiểm chứng từ ten kiểm chứng
                ValidatorAction action = res.getValidatorAction(name);

                // 失敗の場合、プロパティの表示名を取得し、メッセージに追加する
                // Trường hợp thất bại thi get ten hiển thị của property rồi add vao message
                if (!result.isValid(name)) {
                    // リソースからメッセージを取得する
                    //get resource từ message
                    String message = app.getString(action.getMsg());
                    // プロパティ名からメッセージの引数を取得する
                     // Get tham số của message từ ten property
                    Object[] args = getArgs(name, form.getField(property), app);
                    // メッセージを生成する
                     // Tạo message
                    msgs.add(MessageFormat.format(message, args));
                }
            }
        }
        return msgs.toArray(new String[0]);
    }

    /**
     * 引数を表現する文字列の配列を取得します。<br>
     * Get array của chuỗi ki tự hiển thị tham số
     * @param name 検証名
     * @param field フィールド
     * @param app メッセージリソース
     * @return 引数を表現する文字列の配列
     */
    private static Object[] getArgs(String name, Field field, ResourceBundle app) {
        // 準備
    	Object result[] = new Object[4];
        Arg args[] = {field.getArg(name, 0), field.getArg(name, 1), field.getArg(name, 2), field.getArg(name, 3)};

        for(int i = 0; i < args.length; i++) {
            // nullの場合、無視
            // Trường hợp null、bỏ qua
            if(args[i] == null) continue;

            // 引数名を取得する
            // Get ten tham số
            if(args[i].isResource()) {
                result[i] = app.getString(args[i].getKey());
            } else {
                result[i] = args[i].getKey();
            }
        }
        return result;
    }

    /**
     * バリデーション定義のデータホルダークラス<br>
     *  Class data holder  của định nghia validation
     */
    public static class Definition {

    	private static ValidatorResources definition = null;

    	/**
    	 * バリデーション定義を取得します。<br>
    	 *  Get định nghia validation
    	 * @return バリデーション定義
    	 */
    	public static synchronized ValidatorResources get() {
    		return definition;
    	}

    	/**
    	 * バリデーション定義を設定します。<br>
    	 * setting định nghia validation
    	 * @param path バリデーション定義ファイルのパス名
    	 * @return 設定したバリデーション定義
    	 */
    	public static synchronized ValidatorResources set(String path) {
    		if (path != null && path.length() > 0 && definition == null) {
    			// バリデーション定義ファイルのURLを取得する
				String[] rules = path.split("\\s*,\\s*");
				List<URL> ins = new ArrayList<URL>();
				for (String rule : rules) {
				    ins.add(ValidatorUtil.class.getResource(rule.trim()));
				}
				try {
					// バリデーション定義ファイルからバリデーション定義を作成する
					definition = new ValidatorResources(ins.toArray(new URL[0]));
					LOG.debug("バリデーション定義ファイルをロードしました。");
				} catch (Exception e) {
//					LOG.error(MessageUtil.getMessage(CommonConstants.LGAZ1006_E
//							,"バリデーション定義ファイルのロードに失敗しました。"), e);
				}
    		}
    		return definition;
    	}
    }
}
