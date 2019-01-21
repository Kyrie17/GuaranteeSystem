package tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Md5Utils {

	/**
	 * 	输入：原文
	 * 	输出：经过md5运算的密文
	 */
	public static String getMd5(String plainText) {
		byte[] secretBytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 	获取当前时间的时间戳
	 * @return timestamp
	 */
	public static String getTimestamp() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	
	public static void main(String[] args) {
		String password = "123456";
		String timestamp = getTimestamp();
		String encoded = getMd5(password + timestamp);
		System.out.println(timestamp);
		System.out.println(encoded);
	}
}
