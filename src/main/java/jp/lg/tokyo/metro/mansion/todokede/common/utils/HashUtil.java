package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.transaction.SystemException;

import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.commons.lang3.RandomStringUtils;

import jp.lg.tokyo.metro.mansion.todokede.common.ApplicationProperties;
import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;

/**
 * Hashユーティリティクラス。<br>
 * class utility  Hash
 * @author Hitachi Information Systems CO., Ltd.
 * @version 1.7
 */
public final class HashUtil {

	private static final String DEFAULT_DIGEST = "SHA-1";

    /**
     * コンストラクタ. <br>   contractor
     */
    private HashUtil() {
    }

    /**
     * 文字列を暗号化する<br>
     * Ma hoa chuỗi ki tự
     *
     * @param sourceStr 文字列
     * @return 暗号化される文字列  <br> chuỗi ki tự được ma hoa
     * @throws SystemException 
     */
    public static String digest(String sourceStr) throws SystemException {
    	try {
    		MessageDigest digester = MessageDigest.getInstance(DEFAULT_DIGEST);
    		byte[] hashBytes = digester.digest(sourceStr.getBytes());
    		digester.reset();
    		return toHexString(hashBytes);
    	} catch (NoSuchAlgorithmException e) {
    		throw new SystemException("ハッシュ値の取得に失敗しました。");
    	}
    }

    /**
     * byte配列をHEX文字列に変換する<br>
     * Thay đoi array  byte thanh chuỗi ki tự HEX
     *
     * @param arr byte配列
     * @return　HEX文字列
     */
    private static String toHexString(byte[] arr) {

        StringBuffer buff = new StringBuffer(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String b = Integer.toHexString(arr[i] & 0xff);
            if (b.length() == 1) {
                buff.append("0");
            }
            buff.append(b);
        }
        return buff.toString();

    }

    /**
     * UnixCryptを利用して、文字列を暗号化する<br>
     * Sử dụng UnixCrypt ma hoa chuỗi ki tự
     *
     * @param salt ソルト
     * @param sourceStr 文字列
     * @return 暗号化された文字列   <br> chuỗi ki tự đa được ma hoa
     */
    public static String crypt(String salt, String sourceStr) {
    	return UnixCrypt.crypt(sourceStr, salt);

    }

    /**
     * 指定した文字列を暗号化する。<br>
     * Ma hoa chuỗi ki tự đa chỉ định
     * @param plain 文字列
     * @return 暗号化された文字列  <br> chuỗi ki tự đa được ma hoa
     */
    public static String getUnixCript(String plain) {
    	String salt = RandomStringUtils.randomAlphanumeric(2);
    	return crypt(salt, plain);
    }

    /**
     * 指定した文字列が暗号化されたものと同一のものであることを判定する。<br>
     * Phan định xem co la những cai đồng nhất với những cai ma co chuỗi ki tự đa chỉ định đa được ma hoa khong
     * @param plain 文字列
     * @param encrypted 暗号化された文字列
     * @return 同じである場合はtrue、異なる場合はfalseである。<br>
     * Trường hợp giống thi la true、trường hợp khac thi la false
     */
    public static boolean equalsUnixCript(String plain, String encrypted) {
    	String salt = encrypted.substring(0, 2);
    	String result = crypt(salt, plain);
    	return result != null && result.equals(encrypted);

    }

    /**
     * 任意の文字列からハッシュ化された文字列を出力する<br>
     * Output chuỗi ki tự đa được hash từ chuỗi ki tự tuy y
     * @param args 任意の文字列（２文字以上）<br>
     * Chuổi ki tự tuy y（từ ２ki tự trỡ len）
     */
    public static void main(String[] args) {

        if(args.length != 1 || args[0].length() < 2){
            System.out.println("usage : java HashUtil [文字列]");
            System.exit(1);
        }

        String hash = HashUtil.crypt(args[0].substring(0, 2), args[0].substring(2));
        System.out.println(hash);


    }
	/**
     * 文字列を暗号化する<br>
     * Ma hoa chuỗi ki tự
     *
     * @param s 文字列
     * @return 暗号化される文字列   <br>chuỗi ki tự đa được ma hoa
     */
    public static String getHashHex(String s) {
         String typeHash = ApplicationProperties.getProperty(CommonConstants.TYPE_HASH);
         return HashUtil.crypt(typeHash, s);
    }

}
