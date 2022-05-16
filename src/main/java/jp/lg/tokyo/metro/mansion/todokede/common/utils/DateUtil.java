/*
 * @(#)DateUtil.java 01-00 2019/11/16
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * CREATE DATE: 2019/11/16
 * Version 1.0
 */
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.enumerated.JDayOfWeek;

/**
 * <p>
 * 和暦日付と西暦日付の相互変換、暦日チェック等の機能を提供するクラスです。<br>
 * システムによって異なる元号コードや元号名称、元号略称等をXMLファイルで
 * 定義して外部から指定することが可能です。<br>
 * XMLファイルの指定がない場合は、クラス内に定義されている定数を使用して処理を行います。</p>
 * <p>
 * </p>
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class DateUtil {

	// 西暦日付格納変数
	private Calendar date;

	// 和暦日付格納変数
	private JDate jdate;

	// 各種日付が取得可能かどうかを示すフラグ
	private boolean canRead;

	// 日付チェックの厳密性
	private int checkMode;

	// 厳密性設定用変数
	/**
	 * <p>
	 * 和暦チェックを行う際の厳密性を設定するsetCheckMode()に指定する引数用の定数です。
	 * CHECK_MODE_SEVEREが指定されると、日レベルまで厳密にチェックを行います。<br />
	 * Là constant của dùng tham số sẽ chỉ định tại setCheckMode() mà sẽ setting tính nghiêm mật khi tiến hành check lịch Nhật 和暦
	 * Nếu CHECK_MODE_SEVERE được chỉ định thì sẽ tiến hành check nghiêm mật cho đến level ngay 日レベル
	 * </p>
	 */
	public static final int CHECK_MODE_SEVERE = 1; // 日レベルまでチェック

	/**
	 * <p>
	 * 和暦チェックを行う際の厳密性を設定するsetCheckMode()に指定する引数用の定数です。
	 * CHECK_MODE_MEDIUMが指定されると、同一月であれば新旧どちらの元号が指定されていても許容します。<br />
	 * Là constant của dùng tham số sẽ chỉ định tại setCheckMode() mà sẽ setting tính nghiêm mật khi tiến hành check lịch Nhật 和暦
	 * nếu CHECK_MODE_MEDIUM được chỉ định thì cho dù niên hiệu nào cũ mới nếu cùng tháng cũng sẽ cho phép chỉ định
	 * </p>
	 */
	public static final int CHECK_MODE_MEDIUM = 2; // 月レベルまでチェック

	/**
	 * <p>
	 * 和暦チェックを行う際の厳密性を設定するsetCheckMode()に指定する引数用の定数です。
	 * CHECK_MODE_LOOSEが指定されると、同一年であれば新旧どちらの元号が指定されていても許容します。<br />
	 * Là constant của dùng tham số sẽ chỉ định tại setCheckMode()mà sẽ setting tính nghiêm mật khi tiến hành check lịch Nhật 和暦
	 * nếu CHECK_MODE_LOOSE được chỉ định thì cho dù niên hiệu nào cũ mới nếu cùng năm cũng sẽ cho phép chỉ định
	 * </p>
	 */
	public static final int CHECK_MODE_LOOSE  = 3; // 年レベルまでチェック

	/**
	 * 元号リスト
	 */
	private static List<Era> eraList = null;

	/**
	 * 元号設定ファイルが指定されなかった場合のデフォルト値
	 */
	// CT8customize_NC007_START
	private static String constantTable[][] = {{"18680101", "29991231", CommonConstants.BLANK, 
		CommonConstants.BLANK, CommonConstants.BLANK, CommonConstants.BLANK}};
	
	static {
		try {
			String maxEraNumber = CommonConstants.STR_0;
	    	String keyName = CommonConstants.BLANK;
	    	String keyNumber = CommonConstants.BLANK;
			Enumeration e = CommonProperties.getPropertyNames();
	    	while (e.hasMoreElements()) {
	    		keyName = (String) e.nextElement();
	    		keyNumber = keyName.substring(keyName.length() - 1);
	    		
	    		if (CheckUtil.isDigits(keyNumber) 
	    			&& maxEraNumber.compareTo(keyNumber) < 0) {
	    			maxEraNumber = keyNumber;
	    		}
	    	}
	    	
	    	SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.YYYYMMDD);
	    	String currentDate = dateFormat.format(Calendar.getInstance().getTime());
	    	int eraCount = 0;
			for (int i = 1; i <= Integer.valueOf(maxEraNumber); i++) {
				if (currentDate.compareTo(CommonProperties.getProperty("EraFrom" + i)) >= 0) {
					eraCount++;
				}
			}
			
			constantTable = new String[eraCount][6];

			for (int i = 1; i <= eraCount; i++) {
				constantTable[i - 1][0] = CommonProperties.getProperty("EraFrom" + i);
				constantTable[i - 1][1] = CommonProperties.getProperty("EraTo" + i);
				constantTable[i - 1][2] = CommonProperties.getProperty("EraCode" + i);
				constantTable[i - 1][3] = CommonProperties.getProperty("EraName" + i);
				constantTable[i - 1][4] = CommonProperties.getProperty("EraShortName" + i);
				constantTable[i - 1][5] = CommonProperties.getProperty("EraAlphabeticName" + i);
			}
			constantTable[eraCount - 1][1] = "29991231";
			
		} catch (Exception e) {
			constantTable = new String[1][6];
			constantTable[0][0] = "18680101";
			constantTable[0][1] = "29991231";
			constantTable[0][2] = CommonConstants.BLANK;
			constantTable[0][3] = CommonConstants.BLANK;
			constantTable[0][4] = CommonConstants.BLANK;
			constantTable[0][5] = CommonConstants.BLANK;
		}
	}
	// CT8customize_NC007_END

	/**
	 * <p>
	 * DateUtilを構築します。
	 * クラス内に定義された定数に基づいて元号リストを設定します。<br/>
	 * Cấu trúc DateUtil
	 * Sẽ setting list niên hiệu dựa vào constant đã được định nghĩa trong class
	 * </p>
	 */
	// コンストラクタ（パス指定なし）
	public DateUtil() {
		this("");
	}

	/**
	 * <p>
	 * DateUtilを構築します。
	 * 引数で指定されたXMLファイルに基づいて元号リストを設定します。<br/>
	 * Cấu trúc  DateUtil
	 * Sẽ setting list niên hiệu dựa vào file XML đã được chỉ định bằng tham số
	 * </p>
	 *
	 * @param path 元号設定ファイルのパスを指定します。<br/> sẽ chỉ định path của file setting niên hiệu
	 */
	// コンストラクタ（パス指定有り）
	public DateUtil(String path) {
		// インスタンス変数を初期化する
		date  = Calendar.getInstance();
		jdate = new JDate();
		date.setLenient(false);
		checkMode = CHECK_MODE_SEVERE;
		canRead = false;
		// 元号リストの初期設定を行う
		synchronized (path) {
			if (eraList == null) {
				this.init(path);
			}
		}
	}

	/**
	 * <p>
	 * 和暦チェックを行う際の厳密性を指定します。<br />
	 * ここでの厳密性とは、元号の切替時点での許容範囲を表しています。
	 * Sẽ chỉ định tính nghiêm mật khi tiến hành check 和暦<br />
	 * Tính nghiêm mật ỡ đây là đang biểu thị phạm vi cho phép tại thời điểm chuyển đổi niên hiệu
	 * </p>
	 * <p>
	 * CHECK_MODE_SEVEREを指定した場合、日レベルまで厳密にチェックを行います。<br />
	 * CHECK_MODE_MEDIUMを指定した場合、同一月であれば新旧どちらの元号が指定されていても許容します。<br />
	 * CHECK_MODE_LOOSEを指定した場合、同一年であれば新旧どちらの元号が指定されていても許容します。<br/>
	 * Trường hợp đã chỉ định CHECK_MODE_SEVERE thì tiến hành check nghiêm mật cho đến level ngày 日レベル<br />
	 * Trường hợp đã chỉ định  CHECK_MODE_MEDIUM thì cho dù niên hiệu nào cũ mới nếu cùng tháng cũng sẽ cho phép chỉ định <br />
	 * Trường hợp đã chỉ định  CHECK_MODE_LOOSE thì cho dù niên hiệu nào cũ mới nếu cùng năm cũng sẽ cho phép chỉ định
	 * </p>
	 * <p>
	 * 例えば、昭和は64年1月7日に終了しましたが、CHECK_MODE_SEVEREでは昭和64年
	 * 1月8日を指定するとエラーになります。昭和64年1月8日は、厳密には平成元年
	 * 1月8日であり、指定された日付は存在しないからです。<br />
	 * CHECK_MODE_MEDIUMでは、同じ月である昭和64年1月31日までは正常となります。<br />
	 * CHECK_MODE_LOOSEでは、同じ年である昭和64年12月31日までは正常となります。
	 * </p>
	 * <p>
	 * Ví dụ , Chiêu Hòa 昭和 đã kết thúc vào ngày 64年1月7日 nhưng mà tại CHECK_MODE_SEVERE,
	 * nếu chỉ định Chiêu Hòa 昭和64年1月8日 , thì sẽ bị error . Vì Chiêu Hòa 昭和64年1月8日 thì nghiêm mật là
	 * Bình Thành Năm thứ 1 平成元年1月8日 , Date đã được chỉ định là không tồn tại .<br />
	 * Tại CHECK_MODE_MEDIUM, thì cho đến Chiêu Hòa 昭和64年1月31日 có cùng một tháng , là thành công .<br />
	 * Tại CHECK_MODE_LOOSE, thì cho đến Chiêu Hòa 昭和64年12月31日 có cùng một năm , là thành công.
	 * </p>
	 *
	 * @param checkMode 厳密性を示す定数を指定します。<br/>Chỉ định constant chỉ ra tính nghiêm mật.
	 */
	// チェックの厳密性の設定
	public void setCheckMode(int checkMode) {
		this.checkMode = checkMode;
	}

	/**
	 * <p>
	 * 和暦チェックを行う際の厳密性を取得します。<br />
	 * Get Tính nghiêm mật của khi tiến hành check lịch Nhật .<br />
	 * </p>
	 *
	 * @return 厳密性を示す値を返します。
	 * @return Trả về trị chỉ ra tính nghiêm mật.
	 */
	// チェックの厳密性の取得
	public int getCheckMode() {
		return checkMode;
	}

	/**
	 * 初期処理
	 * @param path パス
	 */
	private void init(String path) {

		// 元号リストを初期化する
		eraList = new ArrayList<Era>();

		// 元号リストを設定する
		if (path.equals("")) {
			// パスが指定されていない場合、デフォルト値で元号テーブルを設定する
			this.initByDefaultValue();
		} else {
			// パスが指定されている場合、XMLファイルで元号テーブルを設定する
			this.initByXml(path);
		}
	}

	/**
	 *  XML設定ファイルによる元号リストの初期化
	 * @param path パス
	 */
	private void initByXml(String path) {

		try {
		    JAXBContext context = JAXBContext.newInstance(EraRoot.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        File xml = ResourceUtils.getFile(path);
	        EraRoot eraRoot  = (EraRoot) unmarshaller.unmarshal(xml);
	        eraList.addAll(eraRoot.getEraList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  デフォルト値による元号リストの初期化
	 */
	private void initByDefaultValue() {
		for (int i = 0; i < constantTable.length; i++) {
			// 元号テーブル格納用クラスを生成する
			Era era = new Era();

			// 元号テーブル格納用クラスの各要素を設定する
			era.setStartDate(constantTable[i][0]);
			era.setEndDate(constantTable[i][1]);
			era.setCode(constantTable[i][2]);
			era.setName(constantTable[i][3]);
			era.setShortName(constantTable[i][4]);
			era.setAlphabeticName(constantTable[i][5]);

			// 元号リストへの要素を追加する
			eraList.add(era);
		}
	}

	/**
	 * <p>
	 * 西暦日付を設定します。<br />
	 * Setting Date lịch tây 西暦 .<br />
	 * </p>
	 *
	 * @param date 西暦年月日を文字列(yyyymmdd形式)で指定します。<br/>
	 * date Chỉ định Năm tháng ngày lịch tây 西暦年月日 bằng chuỗi kí tự(Hình thức yyyymmdd)
	 * @return 設定結果をbooleanで返します。<br />
	 *          日付に数字以外が含まれていた場合や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br />
	 *          Trả về kết quả setting bằng boolean.<br />
	 *          Trường hợp trong date 日付 đang bao gồm không phải chữ số 数字, hoặc trường hợp là date không tồn tại, thì được trả về false .<br />
	 *          Trường hợp được trả về false thì không thể sử dụng các method get .
	 */
	// 西暦日付設定
	public boolean setDate(String date) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			int yyyy = Integer.parseInt(date.substring(0, 4));
			int mm   = Integer.parseInt(date.substring(4, 6));
			int dd   = Integer.parseInt(date.substring(6, 8));

			return setDate(yyyy, mm, dd);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦日付を設定します。<br />
	 * Setting Date lịch tây .<br />
	 * </p>
	 *
	 * @param year  西暦年を文字列(yyyy形式)で指定します。<br/>
	 * 				Chỉ định Năm lịch tây tại chuỗi kí tự (Hình thức yyyy).
	 * @param month 西暦月を文字列(mm形式)で指定します。<br/>
	 * 				Chỉ định Tháng lịch tây tại chuỗi kí tự(Hình thức mm).
	 * @param day   西暦日を文字列(dd形式)で指定します。<br/>
	 * 				Chỉ định Ngày lịch tây tại chuỗi kí tự(Hình thức dd).
	 * @return 設定結果をbooleanで返します。<br/>
	 *          日付に数字以外が含まれていた場合や存在しない日付であった場合、falseが返されます。<br/>
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về kết quả setting tại boolean .<br/>
	 *          Trường hợp trong date bao gồm không phải chữ số , hoặc Trường hợp là date không tồn tại , thì được trả về false .<br />
	 *          Trường hợp được trả về false , thì không thể sử dụng các loại method get .
	 */
	// 西暦日付設定
	public boolean setDate(String year, String month, String day) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			int yyyy = Integer.parseInt(year);
			int mm   = Integer.parseInt(month);
			int dd   = Integer.parseInt(day);

			return setDate(yyyy, mm, dd);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦日付を設定します。<br/>
	 * Setting Date lịch tây .<br/>
	 * </p>
	 *
	 * @param year  西暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch tây bằng int .
	 * @param month 西暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch tây bằng int .
	 * @param day   西暦日をintで指定します。<br/>
	 * 				Chỉ định Ngày lịch tây bằng int.
	 * @return 設定結果をbooleanで返します。<br />
	 *          日付が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về kết quả setting bằng boolean.<br />
	 *          Trường hợp là Date mà date không tồn tại, thì được trả về false .<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get .
	 */
	// 西暦日付設定
	public boolean setDate(int year, int month, int day) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			date.set(year, month - 1, day);
			date.get(Calendar.YEAR);
			// 参照モードを可に設定する
			this.canRead = true;
			return convertJDate();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch tây . Nếu setting Năm tháng lịch tây tại method này , thì xem như là cái đã được chỉ định ngày 1 của năm tháng tương ứng、
	 * và được tiến hành chuyển đổi.</p>
	 * <p>
	 * 例えば1989年1月は、昭和64年1月に変換されます。
	 * Ví dụ 1989年1月 thì được chuyển đổi thành Chiêu hòa 64 tháng 1 昭和64年1月.
	 * </p>
	 *
	 * @param year  西暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch tây tại int .
	 * @param month 西暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch tây tại int .
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về kết quả setting tại boolean.<br />
	 *          Trường hợp Năm tháng là date không tồn tại, thì được trả về false .<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定
	public boolean setYearMonth(int year, int month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		return setDate(year, month, 1);
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>
	 * 例えば1989年1月は、昭和64年1月に変換されます。
	 * </p>
	 * <p>Setting Năm tháng lịch tây . Nếu setting Năm tháng lịch tây tại method này , thì xem như là cái đã được chỉ định Ngày 1 của Năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * <p>
	 * Ví dụ, 1989年1月 thì được chuyển đổi thành Chiêu hòa năm 64 tháng 1 昭和64年1月.
	 * </p>
	 * @param year  西暦年を文字列(yyyy形式)で指定します。<br/>
	 * 				Chỉ định năm lịch tây tại chuỗi kí tự (Hình thức yyyy).
	 * @param month 西暦月を文字列(mm形式)で指定します。<br/>
	 * 				Chỉ định tháng lịch tây tại chuỗi kí tự (Hình thức mm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về kết quả setting tại boolean .<br />
	 *          Trường hợp Năm tháng không phải chữ số, hoặc là date không tồn tại ,thì được trả về false .<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定
	public boolean setYearMonth(String year, String month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			int yyyy = Integer.parseInt(year);
			int mm   = Integer.parseInt(month);

			return setYearMonth(yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>
	 * 例えば1989年1月は、昭和64年1月に変換されます。
	 * </p>
	 * <p>Setting Năm tháng lịch tây .Nếu setting Năm tháng lịch tây bằng method này, thì xem như là cái đã được chỉ định NGày 1 của năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * <p>
	 * Ví dụ , 1989年1月 thì được chuyển đổi thành Chiêu Hòa năm 64 tháng 1 昭和64年1月 .
	 * </p>
	 * @param yearMonth 西暦年月を文字列(yyyymm形式)で指定します。<br/>
	 * 					Chỉ định Năm tháng lịch tây tại chuỗi kí tự (Hình thức yyyymm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về Kết quả setting tại boolean .<br />
	 *          Trường hợp Date không phải là chữ số, hoặc là date không tồn tại thì được trả về false .<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定
	public boolean setYearMonth(String yearMonth) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			int yyyy = Integer.parseInt(yearMonth.substring(0, 4));
			int mm   = Integer.parseInt(yearMonth.substring(4, 6));

			return setYearMonth(yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>
	 * 例えば1989年1月は、平成元年1月に変換されます。
	 * </p>
	 * <p>Setting Năm tháng lịch tây . Nếu setting Năm tháng lịch tây tại method này , thì xem như là cái đã được chỉ định Ngày cuối cùng của Năm tháng tương ứng ,
	 * và được tiến hành chuyển đổi .</p>
	 * <p>
	 * Ví dụ Tháng 1989年1月 thì được chuyển đổi thành Bình thành năm 1 tháng 1 平成元年1月 .
	 * </p>

	 * @param year  西暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch tây bằng int.
	 * @param month 西暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch tây bằng int .
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về Kết quả setting bằng boolean.<br />
	 *          Trường hợp Năm tháng không phải là chữ số , là date không tồn tại , thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定（月の最終日とみなして設定）
	public boolean setYearMonthAsLastDay(int year, int month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 最初に指定された月の１日とみなして日付を設定する
		if (setDate(year, month, 1)) {
			// 翌月の１日に設定して、さらに１日前に戻る
			addMonth(1);
			addDay(-1);
			// 処理を終了する
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>
	 * 例えば1989年1月は、平成元年1月に変換されます。
	 * </p>
	 * <p>Setting Năm tháng lịch tây .  Nếu setting Năm tháng lịch tây tại method này , thì xem như là cái đã được chỉ định Ngày cuối cùng của Năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * <p>
	 * Ví dụ Tháng 1989年1月 thì được chuyển đổi thành  Bình thành năm 1 tháng 1 平成元年1月.
	 * </p>
	 * @param year  西暦年を文字列(yyyy形式)で指定します。<br/>
	 * 				Chỉ định Năm lịch tây bằng chuỗi kí tự(Hình thức yyyy).
	 * @param month 西暦月を文字列(mm形式)で指定します。<br/>
	 * 				Chỉ định Tháng lịch tây bằng chuỗi kí tự(Hình thức mm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về Kết quả setting bằng boolean.<br />
	 *          Trường hợp Năm tháng không phải là chữ số , là date không tồn tại , thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定（月の最終日とみなして設定）
	public boolean setYearMonthAsLastDay(String year, String month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			int yyyy = Integer.parseInt(year);
			int mm   = Integer.parseInt(month);

			return setYearMonthAsLastDay(yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 西暦年月を設定します。このメソッドで西暦年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>
	 * 例えば1989年1月は、平成元年1月に変換されます。
	 * </p>
	 * <p>Setting Năm tháng lịch tây . Nếu setting Năm tháng lịch tây tại method này ,
	 * thì xem như là cái đã được chỉ định Ngày cuối cùng của Năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * <p>
	 * Vi dụ Tháng 1989年1月 thì được chuyển đổi thành  Bình thành năm 1 tháng 1 平成元年1月.
	 * </p>
	 * @param yearMonth 西暦年月を文字列(yyyymm形式)で指定します。<br/>
	 * 					Chỉ định Năm lịch tây bằng chuỗi kí tự(Hình thức yyyymm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về Kết quả setting bằng boolean.<br />
	 *          Trường hợp Năm tháng là date không tồn tại , thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 西暦年月の設定（月の最終日とみなして設定）
	public boolean setYearMonthAsLastDay(String yearMonth) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			int yyyy = Integer.parseInt(yearMonth.substring(0, 4));
			int mm   = Integer.parseInt(yearMonth.substring(4, 6));

			return setYearMonthAsLastDay(yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦日付を設定します。<br />
	 * Setting Date lịch nhật.
	 * </p>
	 *
	 * @param jdate 和暦年月日を文字列(gyyyymmdd形式)で指定します。<br/>
	 * 				Chỉ định Năm tháng ngày lịch nhật bằng chuỗi kí tự(Hình thức gyyyymmdd).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月日に数字以外が含まれていた場合や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về Kết quả setting bằng boolean .<br />
	 *          Trường hợp trong Năm tháng ngày đã bao gồm không phải chữ số , hoặc là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦日付設定
	public boolean setJapaneseDate(String jdate) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			String code = jdate.substring(0,1);
			int yy = Integer.parseInt(jdate.substring(1, 3));
			int mm = Integer.parseInt(jdate.substring(3, 5));
			int dd = Integer.parseInt(jdate.substring(5, 7));

			return setJapaneseDate(code, yy, mm, dd);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦日付を設定します。<br />
	 * Setting Date lịch nhật.
	 * </p>
	 *
	 * @param code  和暦元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu lịch nhật 和暦元号コード bằng chuỗi kí tự.
	 * @param year  和暦年を文字列で指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng chuỗi kí tự.
	 * @param month 和暦月を文字列で指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng chuỗi kí tự .
	 * @param day   和暦日を文字列で指定します。<br/>
	 * 				Chỉ định Ngày lịch nhật bằng chuỗi kí tự.
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月日に数字以外が含まれていた場合や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về Kết quả setting bằng boolean.<br />
	 *          Trường hợp trong Năm tháng ngày đã bao gồm không phải chữ số , hoặc là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦日付設定
	public boolean setJapaneseDate(String code, String year, String month, String day) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			int yy = Integer.parseInt(year);
			int mm = Integer.parseInt(month);
			int dd = Integer.parseInt(day);

			return setJapaneseDate(code, yy, mm, dd);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦日付を設定します。<br />
	 * Setting Date lịch nhật.
	 * </p>
	 *
	 * @param code  和暦元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu lịch nhật 和暦元号コード bằng chuỗi kí tự.
	 * @param year  和暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng int.
	 * @param month 和暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng int.
	 * @param day   和暦日をintで指定します。<br/>
	 * 				Chỉ định Ngày lịch nhật bằng int.
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月日が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về Kết quả setting bằng boolean.<br />
	 *          Trường hợp Năm tháng ngày là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦日付設定
	public boolean setJapaneseDate(String code, int year, int month, int day) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 元号リストの検索
		Era era = searchEraListByCode(code);
		if (era == null) {
			return false;
		}
		// 和暦年月日格納領域の設定
		jdate.setYear(year);
		jdate.setMonth(month);
		jdate.setDay(day);
		jdate.setEra(era);

		// 和暦西暦変換
		return convertDate();

	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch nhật. Nếu setting năm tháng bằng method này thì xem như là cái đã được chỉ định ngày 1 của Năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * @param code  元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu bằng chuỗi kí tự.
	 * @param year  和暦年を文字列(yy形式)で指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng chuỗi kí tự (Hình thức yy).
	 * @param month 和暦月を文字列(mm形式)で指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng chuỗi kí tự (Hình thức mm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm tháng không phải chữ số hoặc là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月の設定
	public boolean setJapaneseYearMonth(String code, int year, int month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		return setJapaneseDate(code, year, month, 1);
	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm thang lịch nhật. Nếu setting năm thang bằng method nay thi xem như la cai đa được chỉ định ngay 1 của Năm thang tương ứng,
	 * va được tiến hanh chuyển đổi .</p>
	 * @param jdate 和暦年を文字列(GYYMM形式)で指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng chuỗi ki tự (Hinh thức GYYMM)
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm thang khong phải chữ số hoặc la date khong tồn tại,thi được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月設定
	public boolean setJapaneseYearMonth(String jdate) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			String code = jdate.substring(0,1);
			int yy = Integer.parseInt(jdate.substring(1, 3));
			int mm = Integer.parseInt(jdate.substring(3, 5));

			return setJapaneseDate(code, yy, mm, 1);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の1日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch nhật. Nếu setting năm tháng bằng method này thì xem như là cái đã được chỉ định ngày 1 của Năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * @param code  元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu 元号コード bằng chuỗi kí tự
	 * @param year  和暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng int.
	 * @param month 和暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng int.
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm tháng là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月設定
	public boolean setJapaneseYearMonth(String code, String year, String month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		try {
			int yy = Integer.parseInt(year);
			int mm = Integer.parseInt(month);

			return setJapaneseDate(code, yy, mm, 1);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch nhật.Nếu setting năm tháng bằng method này thì xem như là cái đã được chỉ định Ngày cuối cùng của năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * @param code  元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu 元号コード bằng chuỗi kí tự
	 * @param year  和暦年をintで指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng int .
	 * @param month 和暦月をintで指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng int .
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm tháng là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月の設定（月の最終日とみなして設定）
	public boolean setJapaneseYearMonthAsLastDay(String code, int year, int month) {
		// 参照モードを不可に設定する
		this.canRead = false;

		// チェックモードを退避する
		int cm = checkMode;
		if (checkMode == CHECK_MODE_SEVERE) {
			checkMode = CHECK_MODE_MEDIUM;
		}

		// 最初に指定された月の１日とみなして日付を設定する
		if (setJapaneseDate(code, year, month, 1)) {
			// 翌月の１日に設定して、さらに１日前に戻る
			addMonth(1);
			addDay(-1);
			// 処理を終了する
			checkMode = cm;
			return true;
		} else {
			checkMode = cm;
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch nhật.Nếu setting năm tháng bằng method này thì xem như là cái đã được chỉ định Ngày cuối cùng của năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 * @param code  元号コードを文字列で指定します。<br/>
	 * 				Chỉ định Code niên hiệu 元号コード bằng chuỗi kí tự.
	 * @param year  和暦年を文字列(yy形式)で指定します。<br/>
	 * 				Chỉ định Năm lịch nhật bằng chuỗi kí tự (Hình thức yy).
	 * @param month 和暦月を文字列(mm形式)で指定します。<br/>
	 * 				Chỉ định Tháng lịch nhật bằng chuỗi kí tự (Hình thức mm).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm tháng không phải là chữ số hoặc là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月の設定（月の最終日とみなして設定）
	public boolean setJapaneseYearMonthAsLastDay(String code, String year, String month) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			int yyyy = Integer.parseInt(year);
			int mm   = Integer.parseInt(month);

			return setJapaneseYearMonthAsLastDay(code, yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦年月を設定します。このメソッドで年月を設定すると、当該年月の最後の日が指定されたものと
	 * みなして変換が行われます。</p>
	 * <p>Setting Năm tháng lịch nhật.Nếu setting năm tháng bằng method này thì xem như là cái đã được chỉ định Ngày cuối cùng của năm tháng tương ứng,
	 * và được tiến hành chuyển đổi .</p>
	 *
	 * @param yearMonth 和暦年を文字列(GYYMM形式)で指定します。<br/>
	 * 					Chỉ định Năm lịch nhật bằng chuỗi kí tự (Hình thức GYYMM).
	 * @return 設定結果をbooleanで返します。<br />
	 *          年月が数字以外や存在しない日付であった場合、falseが返されます。<br />
	 *          falseが返された場合、各種取得メソッドは使用できません。<br/>
	 *
	 * 			Trả về kết quả setting bằng boolean .<br />
	 *          Trường hợp Năm tháng không phải là chữ số hoặc là date không tồn tại, thì được trả về false.<br />
	 *          Trường hợp được trả về false, thì không thể sử dụng các loại method get.
	 */
	// 和暦年月の設定（月の最終日とみなして設定）
	public boolean setJapaneseYearMonthAsLastDay(String yearMonth) {
		// 参照モードを不可に設定する
		this.canRead = false;
		// 指定された月の１日とみなして日付を設定する
		try {
			String code = yearMonth.substring(0, 1);
			int yyyy    = Integer.parseInt(yearMonth.substring(1, 3));
			int mm      = Integer.parseInt(yearMonth.substring(3, 5));

			return setJapaneseYearMonthAsLastDay(code, yyyy, mm);

		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * 和暦日付を取得します。<br/>
	 * Get Date lịch nhật
	 * </p>
	 *
	 * @return 和暦日付を文字列(GYYMMDD形式)で返します。<br/>
	 * Trả về Date lịch nhật bằng chuỗi kí tự (Hình thức GYYMMDD).
	 */
	// 和暦日付取得
	public String getJapaneseDate() {
		if (this.canRead) {
			return japaneseDateToString(jdate.getCode(),
					              jdate.getYear(),
								  jdate.getMonth(),
								  jdate.getDay());
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 元号コードを取得します。<br/>
	 * Get Code niên hiệu 元号コード.
	 * </p>
	 *
	 * @return 元号コードを文字列で返します。<br/>
	 * Trả về Code niên hiệu 元号コード bằng chuỗi kí tự .
	 */
	// 和暦元号コード取得
	public String getEraCode() {
		if (this.canRead) {
			return jdate.getCode();
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 元号（名称）を取得します。<br/>
	 * Get Niên hiệu(Ten) 元号（名称）.
	 * </p>
	 *
	 * @return 元号（名称）を文字列で返します。<br/>
	 * Trả về Niên hiệu(Ten) 元号（名称） bằng chuỗi kí tự.
	 */
	// 和暦元号名称取得
	public String getEraName() {
		if (this.canRead) {
			return jdate.getName();
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 元号（略称）を取得します。<br/>
	 * Get Niên hiệu(Tên tắt)元号（略称）.
	 * </p>
	 *
	 * @return 元号（略称）を文字列で返します。<br/>
	 * Trả về Niên hiệu(Tên tắt) 元号（略称） bằng chuỗi kí tự
	 */
	// 和暦元号略称取得
	public String getEraShortName() {
		if (this.canRead) {
			return jdate.getShortName();
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 元号（英字）を取得します。<br/>
	 * Get Niên hệu(Chữ hán) 元号（英字）.
	 * </p>
	 *
	 * @return 元号（英字）を文字列で返します。<br/>
	 * Trả về Niên hiệu(Chữ hán) 元号（英字）bằng chuỗi kí tự
	 */
	// 和暦元号（英字）取得
	public String getEraAlphabeticName() {
		if (this.canRead) {
			return jdate.getAlphabeticName();
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 和暦年を取得します。<br/>
	 * Get Năm lịch nhật .
	 * </p>
	 *
	 * @return 和暦年をintで返します。<br/>
	 * Trả về Năm lịch nhật bằng int .
	 */
	// 和暦年取得
	public int getJapaneseYear() {
		if (this.canRead) {
			return jdate.getYear();
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 和暦月を取得します。<br/>
	 * Get Tháng lịch nhật 和暦月
	 * </p>
	 *
	 * @return 和暦月をintで返します。<br/>
	 * Trả về Tháng lịch nhật  bằng int
	 */
	// 和暦月取得
	public int getJapaneseMonth() {
		if (this.canRead) {
			return jdate.getMonth();
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 和暦日を取得します。<br/>
	 * Get Ngày lịch nhật 和暦日
	 * </p>
	 *
	 * @return 和暦日をintで返します。<br/>
	 * Trả về Ngày lịch nhật bằng int.
	 */
	// 和暦日取得
	public int getJapaneseDay() {
		if (this.canRead) {
			return jdate.getDay();
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 編集した和暦年月日(例：平成18年3月12日)を取得します。<br/>
	 * Get Năm tháng ngày lịch nhật 和暦年月日 đã edit (Ví dụ：Bình Thành 平成18年3月12日).
	 * </p>
	 *
	 * @return 編集した和暦年月日を返します。<br/>
	 * Trả về Năm tháng ngày lịch nhật đã edit .
	 */
	// 編集済和暦日付取得
	public String getFormatJapaneseDate() {
		if (this.canRead) {
			DecimalFormat df = new DecimalFormat("##");
			StringBuffer sb = new StringBuffer();
			sb.append(jdate.getName());
			sb.append(" ");
			int yy = jdate.getYear();
			if (yy == 1) {
				sb.append("元");
			} else {
				sb.append(df.format(Integer.valueOf(yy)));
			}
			sb.append("年");
			sb.append(df.format(Integer.valueOf(jdate.getMonth())));
			sb.append("月");
			sb.append(df.format(Integer.valueOf(jdate.getDay())));
			sb.append("日");

			return sb.toString();

		} else {
			return "";
		}
	}
	
	/**
	 * Convert from YYYYMMDD to 日DD月MM年YYYY ( ex: 2020年01月31日 )
	 * @return 日DD月MM年YYYY
	 */
	public static String getFormatJapaneseDateYYYYMMDD(String date) {
        // Get Year
        String yyyy = date.substring(0, 4);
        // Get Month
        String mm   = date.substring(4, 6);
        // Get Day
        String dd   = date.substring(6, 8);
        StringBuffer sb = new StringBuffer();
        sb.append(yyyy);
        sb.append("年");
        sb.append(mm);
        sb.append("月");
        sb.append(dd);
        sb.append("日");
        return sb.toString();
    }

	/**
	 * <p>
	 * 編集した省略形の和暦年月日(例：平18.3.12)を取得します。<br/>
	 * Get Năm tháng ngày lịch nhật của hình thức giản lược đã edit (Ví dụ：平18.3.12)
	 * </p>
	 *
	 * @return 編集した省略形の和暦年月日を返します。<br/>
	 * 			Trả về Năm tháng ngày lịch nhật của hình thức giản lược đã edit
	 */
	// 編集済和暦日付（省略形）取得
	public String getShortFormatJapaneseDate() {
		if (this.canRead) {
			DecimalFormat df = new DecimalFormat("##");
			StringBuffer sb = new StringBuffer();
			sb.append(jdate.getShortName());
			sb.append(df.format(Integer.valueOf(jdate.getYear())));
			sb.append(".");
			sb.append(df.format(Integer.valueOf(jdate.getMonth())));
			sb.append(".");
			sb.append(df.format(Integer.valueOf(jdate.getDay())));

			return sb.toString();

		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 西暦日付を取得します。<br/>
	 * Get Date lịch nhật.
	 * </p>
	 *
	 * @return 西暦日付を文字列(YYYYMMDD形式)で返します。<br/>
	 * Trả về Date lịch nhật bằng chuỗi kí tự (Hình thức YYYYMMDD).
	 */
	// 西暦日付取得
	public String getDate() {
		if (this.canRead) {
			return dateToString(date.get(Calendar.YEAR),
								 date.get(Calendar.MONTH) + 1,
								 date.get(Calendar.DAY_OF_MONTH));
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 西暦年月を取得します。<br/>
	 * Get Năm tháng lịch tây .
	 * </p>
	 *
	 * @return 西暦年月を文字列(YYYYMM形式)で返します。<br/>
	 * Trả về Năm tháng lịch tây bằng chuỗi kí tự (Hình thức YYYYMM).
	 */
	// 西暦日付取得
	public String getYearMonth() {
		if (this.canRead) {
			return yearMonthToString(date.get(Calendar.YEAR),
									  date.get(Calendar.MONTH) + 1);
		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 西暦年を取得します。<br/>
	 * Get Năm lịch tây
	 * </p>
	 *
	 * @return 西暦年をintで返します。<br/>
	 * Trả về Năm lịch tây bằng int .
	 */
	// 西暦年取得
	public int getYear() {
		if (this.canRead) {
			return date.get(Calendar.YEAR);
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 西暦月年を取得します。<br/>
	 * Get Năm tháng lịch tây .
	 * </p>
	 *
	 * @return 西暦月をintで返します。<br/>
	 * Trả về Tháng lịch tây bằng int.
	 */
	// 西暦月取得
	public int getMonth() {
		if (this.canRead) {
			return date.get(Calendar.MONTH) + 1;
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 西暦日を取得します。<br/>
	 * Get Ngày lịch tây .
	 * </p>
	 *
	 * @return 西暦日をintで返します。<br/>
	 * Trả về Ngày lịch tây bằng int.
	 */
	// 西暦日取得
	public int getDay() {
		if (this.canRead) {
			return date.get(Calendar.DAY_OF_MONTH);
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 編集した西暦年月日(例：2006年3月12日)を取得します。<br/>
	 * Get Năm tháng ngày lịch tây đã edit (Ví dụ：2006年3月12日).
	 * </p>
	 *
	 * @return 編集した西暦年月日を返します。<br/>
	 * Trả về Năm tháng ngày lịch tây đã edit .
	 */
	// 編集済西暦日付取得
	public String getFormatDate() {
		if (this.canRead) {
			DecimalFormat df = new DecimalFormat("0000");
			StringBuffer sb = new StringBuffer();
			sb.append(df.format(Integer.valueOf(getYear())));
			sb.append("年");
			df = new DecimalFormat("00");
			sb.append(df.format(Integer.valueOf(getMonth())));
			sb.append("月");
			sb.append(df.format(Integer.valueOf(getDay())));
			sb.append("日");

			return sb.toString();

		} else {
			return "";
		}
	}

	/**
	 * <p>
	 * 編集した省略形西暦年月日(例：06.3.12)を取得します。<br/>
	 * Get Năm tháng ngày lịch tây hình thức giản lược đã edit (Ví dụ：06.3.12) .
	 * </p>
	 *
	 * @return 編集した省略形西暦年月日を返します。<br/>
	 * Trả về Năm tháng ngày lịch tây hình thức giản lược đã edit .
	 */
	// 編集済西暦日付取得
	public String getShortFormatDate() {
		if (this.canRead) {
			DecimalFormat df = new DecimalFormat("0000");
			StringBuffer sb = new StringBuffer();
			sb.append(df.format(Integer.valueOf(getYear())).substring(2,4));
			sb.append(".");
			df = new DecimalFormat("##");
			sb.append(df.format(Integer.valueOf(getMonth())));
			sb.append(".");
			sb.append(df.format(Integer.valueOf(getDay())));

			return sb.toString();

		} else {
			return "";
		}
	}

	/**
	 *  西暦和暦変換
	 * @return boolean
	 */
	private boolean convertJDate() {

		// 元号リストを検索する
		Era era = searchEraList(date);

		// 元号リストの検索に失敗した場合、falseを返して処理を終了する
		if (era == null) {
			return false;
		}

		// 和暦日付を設定する
		try {
			// 和暦年の算出
			jdate.setYear(date.get(Calendar.YEAR) - era.getStartYear() + 1);
			// 月の設定
			jdate.setMonth(date.get(Calendar.MONTH) + 1);
			// 日の設定
			jdate.setDay(date.get(Calendar.DAY_OF_MONTH));
			// 元号テーブルの設定
			jdate.setEra(era);
			// 戻り値の設定
			return true;

		} catch (NumberFormatException e) {
			// 年月日の変換でエラーが生じた場合は、nullを返す
			return false;
		}
	}

	/**
	 *  和暦西暦変換
	 * @return boolean
	 */
	private boolean convertDate() {

		// 元号リストを検索する
		Era era = searchEraListByCode(jdate.getCode());

		// 元号リストの検索に失敗した場合、falseを返して処理を終了する
		if (era == null) {
			return false;
		}
		// 西暦年の算出
		int yyyy = jdate.getYear() + era.getStartYear() - 1;
		// 月の設定
		int mm = jdate.getMonth();
		// 日の設定
		int dd = jdate.getDay();
		// 西暦年月日の設定
		try {
			date.set(yyyy, mm - 1, dd);
			// 西暦暦日チェック
			date.get(Calendar.YEAR);
			// 和暦暦日チェック
			if (checkMode == CHECK_MODE_SEVERE) {
				// 日レベルまで厳密チェックを行う場合
				if (!era.equals(searchEraList(date))) {
					return false;
				}
			} else if (checkMode == CHECK_MODE_MEDIUM) {
				// 月レベルまで厳密チェックを行う場合
				StringBuffer sb = new StringBuffer();
				DecimalFormat df = new DecimalFormat("0000");
				sb.append(df.format(Integer.valueOf(yyyy)));
				df = new DecimalFormat("00");
				sb.append(df.format(Integer.valueOf(mm)));
				String ym = sb.toString();
				if ((ym.compareTo(era.getStartDate().substring(0, 6)) < 0) ||
				    (ym.compareTo(era.getEndDate().substring(0, 6)) > 0)) {
					return false;
				}
			} else if (checkMode == CHECK_MODE_LOOSE) {
				// 年レベルまで厳密チェックを行う場合
				if ((yyyy < era.getStartYear()) || (yyyy > era.getEndYear())) {
					return false;
				}
			} else {
				return false;
			}
			// 参照モードを可に設定する
			this.canRead = true;
			// 戻り値の設定
			return true;

		} catch (IllegalArgumentException e) {
			// 西暦暦日でエラーの場合はfalseを返す
			return false;
		}

	}

	/**
	 * <p>
	 * 設定されている西暦年月日により和暦年月日を再設定します。<br />
	 * チェックの厳密性にCHECK_MODE_LOOSEが設定されている場合、和暦日付に
	 * 昭和64年12月31日を設定することが可能です。<br />
	 * この状態で和暦年月日を取得すると昭和64年12月31日が返されますが、
	 * 本メソッドを実行すると、和暦年月日が平成元年12月31日に置き換わります。
	 * </p>
	 * <p>Setting lại Năm tháng ngày lịch nhật tùy theo Năm tháng ngày lịch tây đang được setting .<br />
	 * Trường hợp CHECK_MODE_LOOSE đang được setting trong tính nghiêm mật của check ,
	 * thì trong Date lịch nhật 和暦日付 có thể setting 昭和64年12月31日 .<br />
	 * Nếu get Năm tháng ngày lịch nhật tại trạng thái này thì 昭和64年12月31日 được trả về nhưng mà,
	 * nếu thực thi method này, thì Năm tháng ngày lịch nhật和暦年月日 sẽ được thay thế thành Bình Thành năm 1 平成元年12月31日 .
	 * </p>
	 * @return 再計算が正常に行われたときはtrueを、エラーが発生したときはfalseを返します。<br/>
	 * Khi Tính toán lại đã được tiến hành thành công thì trả về true, Khi phát sinh error thì trả về false .
	 */
	// 和暦日付の再計算
	public boolean refresh() {
		if (canRead) {
			canRead = convertJDate();
			return canRead;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 日付に、現在の日付を設定します。<br/>
	 * Trong date , setting date hiện tại .
	 * </p>
	 *
	 */
	// 現在日付の設定
	public void setToday() {
		canRead = true;
		date = Calendar.getInstance();
		refresh();
	}

	/**
	 * <p>
	 * 設定されている日付に、引数で指定された年数を加算します。<br/>
	 * Trong Date đang được setting, cộng thêm Số năm đã được chỉ định tại tham số .
	 * </p>
	 *
	 * @param amount 加算する年数を指定します。減算する場合は負の値を指定します。<br/>
	 * 				Chỉ định Số năm sẽ cộng thêm . Trường hợp giảm đi thì sẽ chỉ định trị âm
	 * @return 加算が正常に行われたときはtrueを、エラーが生じたときはfalseを返します。<br/>
	 * 			Khi đã tiến hành thành công cộng thêm thì trả về true, Khi phát sinh error thì trả về false .
	 */
	// 年の加算
	public boolean addYear(int amount) {
		if (canRead) {
			date.add(Calendar.YEAR, amount);
			return refresh();
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 設定されている日付に、引数で指定された月数を加算します。<br/>
	 * Trong Date đang được setting , thì cộng them Số tháng đã được chỉ định tại tham số .
	 * </p>
	 *
	 * @param amount 加算する月数を指定します。減算する場合は負の値を指定します。<br/>
	 * 				Chỉ định số tháng sẽ cộng thêm. Trường hợp giảm trừ thì chỉ định trị âm.
	 * @return 加算が正常に行われたときはtrueを、エラーが生じたときはfalseを返します。<br/>
	 * 			Khi đã tiến hành thành công cộng thêm thì trả về true, Khi phát sinh error thì trả về false .
	 */
	// 月の加算
	public boolean addMonth(int amount) {
		if (canRead) {
			date.add(Calendar.MONTH, amount);
			return refresh();
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 設定されている日付に、引数で指定された日数を加算します。<br/>
	 * Trong Date đang được setting, cộng thêm Số ngày đã được chỉ định tại tham số .
	 * </p>
	 *
	 * @param amount 加算する日数を指定します。減算する場合は負の値を指定します。<br/>
	 * 				Chỉ định số ngày sẽ cộng thêm. Trường hợp giảm trừ thì chỉ định trị âm.
	 * @return 加算が正常に行われたときはtrueを、エラーが生じたときはfalseを返します。<br/>
	 * 			Khi đã tiến hành thành công cộng thêm thì trả về true, Khi phát sinh error thì trả về false .
	 */
	// 日の加算
	public boolean addDay(int amount) {
		if (canRead) {
			date.add(Calendar.DAY_OF_MONTH, amount);
			return refresh();
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 設定されている日付の月の最終日を返します。<br />
	 * 例えば、2000年2月10日が設定されている場合、29を返します。
	 * </p>
	 * <p>
	 * Trả về Ngày cuối cùng của tháng của date đang được setting .<br />
	 * Ví dụ, Trường hợp , 2000年2月10日 đang được setting, thì trả về 29 .
	 * </p>
	 *
	 * @return 設定されている日付の月の最終日をintで返します。<br/>
	 * 			Trả về Ngày cuối cùng của tháng của date đang được setting tại int .
	*/
	// 月の最終日の取得
	public int getLastDayOfMonth() {
		if (canRead) {
			// カレンダーを生成する
			Calendar cal = Calendar.getInstance();
			// 最初に設定されている月の１日とみなして日付を設定する
			int year  = date.get(Calendar.YEAR);
			int month = date.get(Calendar.MONTH);
			cal.set(year, month, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			// 和暦日付を設定し直して処理を終了する
			if (refresh()) {
				return cal.get(Calendar.DAY_OF_MONTH);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 設定されている日付の年が閏年か判定します。<br />
	 * Phán định Năm của Date đang được setting có là năm nhuận hay không .
	 * </p>
	 *
	 * @return 設定されている日付の年が閏年のときはtrueを、閏年以外のときはfalseを返します。<br />
	 * Khi Năm của date đang được setting là năm nhuận thì trả về true, KHi không phải năm nhuận thì trả về false .
	*/
	// 閏年判定
	public boolean isLeapYear() {
		if (canRead) {
			GregorianCalendar gc = new GregorianCalendar();
			return gc.isLeapYear(getYear());
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * 設定されている日付が未来日か判定します。<br />
	 * Phán định Date đang được setting có là ngày tương lai hay không.
	 * </p>
	 *
	 * @return 設定されている日付が過去の日付のときは負の値を、
	 *          現在の日付の時はゼロを、未来の日付の時は正の値を返します。<br />
	 *          Khi Date đang được setting là date quá khứ thì trả về trị âm
	 *          Khi là date hiện tại, thì là zero, Khi là date tương lai thì trả về trị dương .
	 */
	// 未来日判定
	public int isFuture() {
		if (canRead) {
			// 現在の日付を文字列で取得する
			Calendar cal = Calendar.getInstance();
			int yyyy = cal.get(Calendar.YEAR);
			int mm   = cal.get(Calendar.MONTH);
			int dd   = cal.get(Calendar.DAY_OF_MONTH);
			String str = dateToString(yyyy, (mm + 1), dd);

			// 設定日付と現在の日付を比較する
			return getDate().compareTo(str);
		} else {
			return 0;
		}
	}

	/**
	 * <p>
	 * 設定されている日付が属する会計年度（西暦）を取得します。<br />
	 * Get Niên độ kế toán(Lịch tây) có date đang được setting thuộc về
	 * </p>
	 *
	 * @return 設定されている日付が属する会計年度（西暦）を返します。 <br />
	 * Trả về Niên độ kế toán(Lịch tây) có date đang được setting thuộc về
	*/
	// 会計年度取得（西暦）
	public int getFiscalYear() {
		if (canRead) {
			int yyyy = getYear();
			int mm   = getMonth();
			if (mm <= 3) {
				yyyy--;
			}
			return yyyy;

		} else {
			return 0;

		}
	}

	/**
	 * <p>
	 * 設定されている日付が属する会計年度（西暦）を文字列で取得します。<br />
	 * Get Niên độ kế toán(Lịch tây) có date đang được setting thuộc về, bằng chuỗi kí tự .
	 * </p>
	 *
	 * @return 設定されている日付が属する会計年度（西暦）を文字列で返します。<br />
	 * Trả về Niên độ kế toán(Lịch tây) có date đang được setting thuộc về, bằng chuỗi kí tự.
	 */
	// 会計年度取得（西暦）
	public String getFiscalYearAsString() {
		if (canRead) {
			int yyyy = getYear();
			int mm   = getMonth();
			if (mm <= 3) {
				yyyy--;
			}
			// 編集用フォーマットの設定
			DecimalFormat df = new DecimalFormat("0000");
			// 年度の編集
			return df.format(Integer.valueOf(yyyy));

		} else {
			return "";

		}
	}

	/**
	 * <p>
	 * 設定されている日付が属する会計年度（和暦）を文字列で取得します。<br />
	 * Get Niên độ kế toán(Lịch nhật) có date đang được setting thuộc về, bằng chuỗi kí tự.
	 * </p>
	 *
	 * @return 設定されている日付が属する会計年度（和暦）を文字列(GYY形式)で返します。<br />
	 * Trả về Niên độ kế toán(Lịch nhật) có date đang được setting thuộc về, bằng chuỗi kí tự(Hình thức GYY).
	 */
	// 会計年度取得（和暦）
	public String getFiscalJapaneseYearAsString() {
		if (canRead) {
			int yyyy = getYear();
			int mm   = getMonth();
			if (mm <= 3) {
				yyyy--;
			}
			// 設定されている日付の退避
			int year  = getYear();
			int month = getMonth();
			int day   = getDay();
			// 会計年度（和暦）の算出
			StringBuffer sb = new StringBuffer();
			if (setDate(yyyy, 4, 1)) {
				// 編集用フォーマットの設定
				DecimalFormat df = new DecimalFormat("00");
				// 年度の編集
				sb.append(getEraCode());
				sb.append(df.format(Integer.valueOf(getJapaneseYear())));
			}
			// 退避した日付の復元
			setDate(year, month, day);
			// 戻り値の設定
			return sb.toString();

		} else {
			return "";

		}
	}
	
	/**
     * <p>
     * 設定されている日付が属する会計年度（和暦）を文字列で取得します。<br />
     * Get Niên độ kế toán(Lịch nhật) có date đang được setting thuộc về, bằng chuỗi kí tự.
     * </p>
     *
     * @return 設定されている日付が属する会計年度（和暦）を文字列(GYY形式)で返します。<br />
     * Trả về Niên độ kế toán(Lịch nhật) có date đang được setting thuộc về, bằng chuỗi kí tự(Hình thức GYY).
     */
    // 会計年度取得（和暦）
    public String getOnlyFiscalJapaneseYearAsString() {
        if (canRead) {
            // 会計年度（和暦）の算出
            int yFJ = getJapaneseYear();
            int mm   = getMonth();
            if (mm <= 3) {
                yFJ--;
            }

            // 戻り値の設定
            return String.valueOf(yFJ);
        } else {
            return "";
        }
    }

	/**
	 *  西暦年月日による元号リスト検索
	 * @param date 西暦年月日
	 * @return 元号リスト検索
	 */
	private Era searchEraList(Calendar date) {

		// 戻り値格納領域を初期化する
		Era rc = null;

		// 西暦日付の文字列変換
		String d = dateToString(date.get(Calendar.YEAR),
				   date.get(Calendar.MONTH) + 1,
				   date.get(Calendar.DAY_OF_MONTH));

		// 元号リストを検索する
		for (int i = 0; i < eraList.size(); i++) {
			Era era = (Era)eraList.get(i);
			if ((d.compareTo(era.getStartDate()) >= 0) &&
				(d.compareTo(era.getEndDate()) <= 0)) {
				rc = era;
				break;
			}
		}
		// 処理を終了する
		return rc;
	}

	// 元号コードによる元号リスト検索
	private Era searchEraListByCode(String code) {

		// 戻り値格納領域を初期化する
		Era rc = null;

		// 元号リストを検索する
		for (int i = 0; i < eraList.size(); i++) {
			Era era = (Era)eraList.get(i);
			if (code.equals(era.getCode())) {
				rc = era;
				break;
			}
		}
		// 処理を終了する
		return rc;
	}

	// 西暦日付の文字列変換
	private String dateToString(int yyyy, int mm, int dd) {

		// 編集用ワークエリアの定義
		StringBuffer sb = new StringBuffer();
		// 編集用フォーマットの設定
		DecimalFormat df = new DecimalFormat("0000");
		// 年の編集
		sb.append(df.format(Integer.valueOf(yyyy)));
		// 編集用フォーマットの設定
		df = new DecimalFormat("00");
		// 月の編集
		sb.append(df.format(Integer.valueOf(mm)));
		// 日の編集
		sb.append(df.format(Integer.valueOf(dd)));
		// 戻り値の設定
		return sb.toString();
	}

	// 西暦年月の文字列変換
	private String yearMonthToString(int yyyy, int mm) {

		// 編集用ワークエリアの定義
		StringBuffer sb = new StringBuffer();
		// 編集用フォーマットの設定
		DecimalFormat df = new DecimalFormat("0000");
		// 年の編集
		sb.append(df.format(Integer.valueOf(yyyy)));
		// 編集用フォーマットの設定
		df = new DecimalFormat("00");
		// 月の編集
		sb.append(df.format(Integer.valueOf(mm)));
		// 戻り値の設定
		return sb.toString();
	}

	// 和暦日付の文字列変換
	private String japaneseDateToString(String code, int yy, int mm, int dd) {

		// 編集用フォーマットの設定
		DecimalFormat df = new DecimalFormat("00");
		// 編集用ワークエリアの定義
		StringBuffer sb = new StringBuffer();
		// 元号の編集
		sb.append(code);
		// 年の編集
		sb.append(df.format(Integer.valueOf(yy)));
		// 月の編集
		sb.append(df.format(Integer.valueOf(mm)));
		// 日の編集
		sb.append(df.format(Integer.valueOf(dd)));
		// 戻り値の設定
		return sb.toString();
	}

	@XmlRootElement(name = "root")
	@XmlAccessorType(XmlAccessType.FIELD)
	static class EraRoot {
	    @XmlElement(name = "era")
	    private List<Era> eraList;

        public List<Era> getEraList() {
            return eraList;
        }

        public void setEraList(List<Era> eraList) {
            this.eraList = eraList;
        }
	}
	
	// 元号テーブル格納用クラス
	@XmlRootElement(name = "era")
	@XmlAccessorType (XmlAccessType.FIELD)
	static class Era implements Serializable {

		private static final long serialVersionUID = 1L;

		// 元号開始日付(西暦YYYYMMDD形式)
		@XmlElement(name="startdate")
		private String startDate;

		// 元号終了日付(西暦YYYYMMDD形式)
		@XmlElement(name="enddate")
		private String endDate;

		// 元号コード
		@XmlElement(name="code")
		private String code;

		// 元号（正式名称）
        @XmlElement(name="name")
		private String name;

		// 元号（略称）
        @XmlElement(name="shortname")
		private String shortName;

		// 元号（英字）
        @XmlElement(name="alphabeticName")
		private String alphabeticName;

		// 元号開始日付の取得
		public String getStartDate() {
			return startDate;
		}

		// 元号開始日付の設定
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		// 元号開始年（西暦）の取得
		public int getStartYear() {
			return Integer.parseInt(getStartDate().substring(0, 4));
		}

		// 元号終了日付の取得
		public String getEndDate() {
			return endDate;
		}

		// 元号終了日付の設定
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		// 元号終了年（西暦）の取得
		public int getEndYear() {
			return Integer.parseInt(getEndDate().substring(0, 4));
		}

		// 元号コードの取得
		public String getCode() {
			return code;
		}

		// 元号コードの設定
		public void setCode(String code) {
			this.code = code;
		}

		// 元号（正式名称）の取得
		public String getName() {
			return name;
		}

		// 元号（正式名称）の設定
		public void setName(String name) {
			this.name = name;
		}

		// 元号（略称）の取得
		public String getShortName() {
			return shortName;
		}

		// 元号（略称）の設定
		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		// 元号（英字）の取得
		public String getAlphabeticName() {
			return alphabeticName;
		}

		// 元号（英字）の設定
		public void setAlphabeticName(String alphabeticName) {
			this.alphabeticName = alphabeticName;
		}
	}

	// 和暦日付格納用クラス
	static class JDate {

		// 年
		private int year;

		// 月
		private int month;

		// 日
		private int day;

		// 元号テーブル
		private Era era;

		// クリア処理
		public void clear() {
			this.year  = 0;
			this.month = 0;
			this.day   = 0;
			this.era   = null;
		}

		// 年の取得
		public int getYear() {
			return year;
		}

		// 年の設定
		public void setYear(int year) {
			this.year = year;
		}

		// 月の取得
		public int getMonth() {
			return month;
		}

		// 月の設定
		public void setMonth(int month) {
			this.month = month;
		}

		// 日の取得
		public int getDay() {
			return day;
		}

		// 日の設定
		public void setDay(int day) {
			this.day = day;
		}

		// 元号テーブルの取得
		public Era getEra() {
			return era;
		}

		// 元号テーブルの設定
		public void setEra(Era era) {
			this.era = era;
		}

		// 元号コードの取得
		public String getCode() {
			return era.getCode();
		}

		// 元号（正式名称）の取得
		public String getName() {
			return era.getName();
		}

		// 元号（略称）の取得
		public String getShortName() {
			return era.getShortName();
		}

		// 元号（英字）の取得
		public String getAlphabeticName() {
			return era.getAlphabeticName();
		}
	}

	/**
	 * 現在の西暦日付から指定した西暦日付を減算し、日数を返します。<br>
	 * tinh trừ Date lịch dương đa chỉ định từ Date lịch dương của hiện tại、rồi trả ve số ngay
	 *
	 * @param year  西暦年を文字列(yyyy形式)で指定します。<br>
	 * Chỉ định Năm dương lịch 西暦年 bằng chuỗi ki tự kiểu (yyyy)
	 * @param month 西暦月を文字列(mm形式)で指定します。<br>
	 * Chỉ định Thang dương lịch 西暦月 bằng chuỗi ki tự kiểu (mm)
	 * @param day   西暦日を文字列(dd形式)で指定します。<br>
	 * Chỉ định Ngay dương lịch 西暦日 bằng chuỗi ki tự kiểu (dd)
	 * @return 日数をintで返します。<br>
	 *          現在の西暦日付と指定した西暦日付が同じ場合、0を返します。<br>
	 * trả về số ngay  bằng int<br>
	 *          Trường hợp date dương lịch hiện tại giống với date dương lịch đa chỉ định thi trả về 0
	 */
	public int subtract(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();

		cal.set(getYear(), getMonth() - 1, getDay());
		long one = cal.getTime().getTime();

		cal.set(year, month - 1, day);
		long ano = cal.getTime().getTime();

		return (int)((one - ano) / (1000 * 60 * 60 * 24));
	}

	/**
	 * <p>
	 * 指定した西暦日付の存在することを判定します。<br>
	 *Phan định việc tồn tại Date lịch duong ma đa chỉ định<br>
	 * </p>
	 *
	 * @param val 西暦年月日を文字列(yyyymmdd形式)で指定します。<br>
	 * CHỉ định Ngay thang nam dương lich bằng chuỗi ki tự kiểu (yyyymmdd)
	 * @return 存在する日のときはtrueを、その他のときはfalseを返します。<br>
	 * Khi la date tồn tại thi trả vể true khi khac thi trả về false
	 */
	public static boolean isValid(String val) {
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			String converted = fmt.format(fmt.parse(val));
			return converted.equals(val);
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * Get Financy year
	 *  年度を返す
	 * @param day
	 * @return
	 */
	public static int getFiscalYear(Date day) {

		int year = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM");

		// 月の取得
		String dateStr = sdf.format(day);

		// String→intへ型変換
		int month = Integer.parseInt(dateStr);

		// カレンダーオブジェクトを取得
		Calendar calendar = Calendar.getInstance();

		// 値をセット
		calendar.setTime(day);

		if (month < 4) {
			// 1～3月の場合

			// 西暦から-1する
			calendar.add(Calendar.YEAR, -1);

			// 年のみ取得
			year = calendar.get(Calendar.YEAR);

		} else {
			// 4～12月の場合

			// 年のみ取得
			year = calendar.get(Calendar.YEAR);

		}

		// 呼び出し元(mainメソッド)へ返す
		return year;

	}
	
	/**
	 * Date型を和暦の年度に変換する
	 * @param date 算定する日付
	 * @return 元号 + 年 + 年度をStringで返す
	 */
	public static String convertJpFiscalString(Date date) {
		Locale locale = new Locale("ja", "JP", "JP");
		Calendar cal = Calendar.getInstance(locale);
		cal.setTime(date);
		
		String year;
		if (cal.get(Calendar.MONTH) < 3) {
			year = Integer.toString(cal.get(Calendar.YEAR) - 1);
		} else {
			year = Integer.toString(cal.get(Calendar.YEAR));
		}
		
		if ("1".equals(year)) {
			year = "元";
		}
		
		return (cal.getDisplayName(Calendar.ERA, Calendar.LONG, locale) + year + "年");
	}

	/**
	 * Stringの西暦を年度に変換する
	 * @param year 4桁の西暦
	 * @return 和暦のString
	 */
	public static String convertJpFiscalString(String year) {
		if (year.length() != 4) {
			throw new RuntimeException("4桁の西暦を指定してください。");
		}
		
		try {
			// 西暦から和暦を求めることが目的のため、4/1固定で変換する
			Date baseDate = new SimpleDateFormat("yyyy/MM/dd").parse(year + "/04/10");
			return convertJpFiscalString(baseDate);
		} catch (ParseException e) {
			throw new RuntimeException("Date parse error!");
		}
	}

	/**
	 * LocalDateから日本語で曜日を取得します
	 * @param localDate
	 * @return
	 */
	public static String getDayOfWeekInJapanese(LocalDate localDate) {
		JDayOfWeek jDayOfWeek = JDayOfWeek.parseFrom(localDate.getDayOfWeek());
		return ObjectUtils.isEmpty(jDayOfWeek) ? null : jDayOfWeek.getJDayOfWeek();
	}
	
    /**
     * Convert localdate to jp calendar
     * @param localDate LocalDate
     * @return String
     * @author pdquang
     */
    public static String convertLocalDateToJpString(LocalDate localDate) {
        StringBuilder jpDate = new StringBuilder(CommonConstants.BLANK);
        if (localDate != null) {
            String financialYear =  DateUtil.convertJpFiscalString(String.valueOf(localDate.getYear()));
            jpDate.append(financialYear);
            jpDate.append(localDate.getMonthValue());
            jpDate.append(CommonConstants.MONTH_STRING);
            jpDate.append(localDate.getDayOfMonth());
            jpDate.append(CommonConstants.DAY_STRING);
        }
        return jpDate.toString();
    }

    /**
     * <p>Convert to JP Date</p>
     * <p>Sample: 2020/1/21 -> JP Date: 令和2月1日21</p>
     * @param LocalDate
     * @return String
     * @author tqvu1
     */
    public static String getFormatJapaneseDateForShow(LocalDate localDate) {
        DateUtil datetest = new DateUtil(CommonConstants.ERA_PATH);
        datetest.canRead = true;
        datetest.setDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        return datetest.getFormatJapaneseDate();
    }

    /**
     * <p>Get EraName and FiscalYear from date parameter that convert to jp date</p>
     * <p>Sample: 2020/1/21 -> JP Date: 令和2月1日21 -> EraNameFiscalYear: return 令和2;</p>
     * @param LocalDate
     * @return String
     * @author tqvu1
     */
    public static String getEraNameFiscalYearAsString(LocalDate localDate) {
        DateUtil datetest = new DateUtil(CommonConstants.ERA_PATH);
        datetest.canRead = true;
        datetest.setDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        return datetest.getEraName() + datetest.getJapaneseYear();
    }

    /**
     * <p>Get fiscal year only from current date that convert to jp calendar</p>
     * <p>Sample1: 2020/1/21 -> JP Date: 令和2月1日21 -> Fiscal year only: return 1;</p>
     * <p>Sample2: 2020/4/1 -> JP Date: 令和2月4日1 -> Fiscal year only: return 2;</p>
     * @param
     * @return String
     * @author tqvu1
     */
    public static String getOnlyFiscalJapaneseYearAsStringForSetting() {
        DateUtil datetest = new DateUtil(CommonConstants.ERA_PATH);
        datetest.canRead = true;
        LocalDate currentDate = LocalDate.now();
        datetest.setDate(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        return datetest.getOnlyFiscalJapaneseYearAsString();
    }

}
