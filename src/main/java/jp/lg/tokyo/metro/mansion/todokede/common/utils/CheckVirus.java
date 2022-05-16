
package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.lg.tokyo.metro.mansion.todokede.common.ApplicationProperties;

/**
 * ウィルスチェックに関するユーティリティ。<br>
 * utility lien quan check virus
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public class CheckVirus {

	/** commonロガー */
    private static final Logger LOG = LogManager.getLogger(CheckVirus.class);

	/**
	 * JavaNativeInterface用DLL
	 */
	private static final String VIRUS_DLL = "virusscan";
	/**
	 * DLLのロード状態  <br>tinh trang load
	 */
	private static boolean load = false;
	/**
	 * ウィルス検出の待ち時間<br>
	 * Thời gian chờ  kiểm xuất virut
	 */
	private static long wait = 0;
	/**
	 * ウィルス検出用の一時書き込みディレクトリ<br>
	 * directoty viết vao tạm thời dung kiểm xuất virus
	 */
	private static String tempDir = null;
	/**
	 * スキャン結果:  <br>    kết quả scan
	 */
	private int scanStatus;
	/**
	 * 発見したウィルスの数<br>
	 * số virus đa phat hiện
	 */
	private int virusCount;  //nativeコードから設定される
	/**
	 * 発見したウィルスの名前<br>
	 * Ten virus đa phat hiện
	 */
	private String[] virusName; //nativeコードから設定される
	/**
	 * nativeコードのエラーメッセージ<br>
	 * message error của code native
	 */
	private String errMassage; //nativeコードから設定される
	/**
	 * nativeコードのエラーコード(10進数)<br>
	 * endcode của code  native (10base)
	 */
	private int errCode; //nativeコードから設定される

	static {
		// DLLをロードする
    // loas DLL
		try{
			System.loadLibrary(VIRUS_DLL);
			load = true;
		} catch(Error er){
			// nothing
		}

		// 設定値を取得する
    // Get trị setting
		tempDir = ApplicationProperties.getProperty("antiVirusTempDir");
		try {
			wait = Long.valueOf(ApplicationProperties.getProperty("antiVirusWaitTime"));
		} catch (Exception ex) {
			// nothing
		}
	}

	/**
	 * <p>
	 * ウィルススキャンを実施します。0バイトデータやNULLが渡された場合<br>
	 * trueが返却されます。
	 * </p>
	 * <p>
	 * Thực thiscan virus trường hợp 0byte data va NULL được truyền <br>
	 * trả về true
	 * </p>
	 * @param  data ウィルススキャンするデータを指定します <br>
	 * Chỉ định data scan virus
	 * @return boolean ウィルスが見つからなかった場合true,見つかればfalse <br>
	 * Truong hợp khong phat hiện ra virus thi true, trường hợp đa phat hiện ra thi la false
	 * @exception Exception スキャン中、エラーが発生すると例外が発生します。 <br>
	 * Phat sinh ngoại lệ la phat sinh error luc đang scan
	 */

	public boolean checkStream(byte[] data) throws Exception{
		if (load) throw new IllegalStateException("ライブラリをロードしていません。");

		StringBuffer buf = new StringBuffer();

		//各初期化 cac init hoa
		init();

		//入力パラメータ     parameter input
		if(data == null || data.length == 0){
			//データがない場合はチェックしない
      //Trường hợp khong co data thi khong check
			return true;
		}

		//スキャン実施       thực thi scen
		scanStatus = virusScan(data);

		if (scanStatus < 0){
			//例外メッセージの編集
      //Edit message ngoại lệ
			buf.append(errMassage);
			buf.append(": ERROR_CODE:");
			buf.append(Integer.toHexString(errCode));
			throw new Exception(buf.toString());
		}
		else{
			//ウィルスタイプの判定       Phan định type virus
      //phan định type virus

			if (scanStatus == 0){
				return true;
			}
			else{
				return false;
			}
		}
	}

	/**
	 * <p>
	 * スキャン結果を返します。<br>
	 * checkStreamメソッドをコールしていないと常に0を返します。
	 * </p>
	 * <p>
	 * Trả về kết quả scan<br>
	 * nếu chưa call method checkStream thi luon trả về 0
	 * </p>
	 * @return scanStatus <br>
	 * 			0:正常終了ウィルスなし  Kết thuc đung khong co virus<br>
	 * 			1:検出                       kiểm xuất<br>
	 * 			2:検出（駆除不可）                      kiểm xuất (khong thể truy tim )<br>
	 * 			-1：異常終了            Kết thuc bất thường<br>
	 */
	public int getScanStatus() {
		if (load) throw new IllegalStateException("ライブラリをロードしていません。");
		return scanStatus;
	}

	/**
	 * <p>
	 * ウィルスが検出された場合、検出したウィルス名を返します。<br>
	 * ウィルスが検出されていない場合、nullを常に返します。
	 * </p>
	 * <p>
	 * Trường hợp kiểm suất ra virus thi trả về ten virus đa kiểm xuất<br>
	 * Trường hợp chưa kiểm xuất được virus thi luon trả về null
	 * </p>
	 * @return virusName[] 検出したウィルス名の配列（但し、最大20個までです。）<br>
	 * Array của ten virut đa kiểm xuất（Tuy nhien、max cho đến 20 cai）
	 */
	public String[] getVirusName() {
		if (load) throw new IllegalStateException("ライブラリをロードしていません。");
		return virusName;
	}

	/**
	 * 検出したウィルスの総数を返します。<br>
	 * trả về tổng số virus đa kiểm xuất
	 * @return virusCount 検出したウィルスの総数<br>
	 * Tổng số virus đa kiểm xuất
	 */
	public int getVirusCount() {
		if (load) throw new IllegalStateException("ライブラリをロードしていません。");
		return virusCount;
	}

	/**
	 * 内部初期化メソッド  <br>
	 *  method init hoa nội bộ
	 */
	private void init(){
		//初期化
		this.scanStatus = 0;
		this.virusCount = 0;
		this.virusName = null;
		this.errMassage = "";
		this.errCode = 0;
	}

	/**
	 * ウィルススキャンネィティブメソッド<br>
	 * method native scan virus
	 * @param  checkData ウィルススキャンするデータを指定します<br>
	 * Chỉ định data scan virus
	 * @return int ウィルススキャンの結果（getScanStatusメソッドの結果と同じです。）<br>
	 * Kết quả scan virus（giống với kết quả của method getScanStatus。）
	 */
	private native int virusScan(byte[] checkData);

	/**
	 * ウィルスを含むかどうかを判定する。<br>
	 * Phan định xem co bao gồm virus khong
	 * @param file ファイル<br>
	 * file
	 * @return ウィルスを含まないと見なせるときtrue、その他の場合false<br>
	 * Khi cho xem khong bao gồm virus thi trả về true、trường hop khac thi trả về false
	 * @throws FileNotFoundException FileNotFoundException
	 * @throws IOException IOException
	 */
	public static boolean validateNoVirus(File file) throws FileNotFoundException, IOException {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			return validateNoVirus(is);
		} finally {
			if (is != null) is.close();
		}
	}

	/**
	 * ウィルスを含むかどうかを判定する。<br>
	 * Phan định xem co bao gồm virus khong
	 * @param is 入力ストリーム<br>
	 * stream input
	 * @return ウィルスを含まないと見なせるときtrue、その他の場合false<br>
	 * Khi cho xem khong bao gồm virus thi trả về true、trường hop khac thi trả về false
	 * @throws FileNotFoundException FileNotFoundException
	 * @throws IOException FileNotFoundException
	 */
	public static boolean validateNoVirus(InputStream is) throws FileNotFoundException, IOException {
		OutputStream os = null;
		File proof = null;
		FileInputStream fis = null;
		try {
			if (tempDir != null) {
				proof = File.createTempFile("checkVirus", ".tmp", new File(tempDir));
			} else {
				proof = File.createTempFile("checkVirus", ".tmp");
			}
			os = new BufferedOutputStream(new FileOutputStream(proof));

			int c;
			InputStream bis = new BufferedInputStream(is); // 念のため       chắc ăn
			while ((c = bis.read()) != -1) {
				os.write(c);
			}
			os.flush();
			//LOG.info(MessageUtil.getMessage(CommonConstants.LGAZ2007_I,proof.getAbsoluteFile().getName()));
		} finally {
			if (os != null) os.close();
		}

		//アンチウィルスにファイルを削除させるため、ファイルを読み込む
    //Vi bắt delete file tại unvirus nen sẽ đọc vao file
		try {
			fis = new FileInputStream(proof);
		} catch (FileNotFoundException e) {
		} finally {
			if (fis != null) fis.close();
		}

		//LOG.info(MessageUtil.getMessage(CommonConstants.LGAZ2008_I,Long.toString(wait)));
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}

		boolean exist = (proof != null) && proof.exists();
		if (exist) {
			proof.delete();
			//LOG.info(MessageUtil.getMessage(CommonConstants.LGAZ2009_I));
		} else {
			//LOG.warn(MessageUtil.getMessage(CommonConstants.LGAZ2010_W));
		}
		return exist;
	}
}
