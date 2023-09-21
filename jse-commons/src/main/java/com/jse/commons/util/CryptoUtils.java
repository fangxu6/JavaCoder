package com.jse.commons.util;

import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @company jse-zq
 * @author tiebin
 * @version CyptoUtils.java, v 0.1 2016年1月14日 下午8:12:52
 */
public class CryptoUtils {

	public static final String	ALGORITHM_DES	= "DES/CBC/PKCS5Padding";

	private static final String	LENGTH_KEY		= "88990055";

	private static final String	PREFIX_KEY		= "zqsystem";

	/**
	 * DES算法，加密
	 *
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @throws InvalidAlgorithmParameterException
	 * @throws Exception
	 */
	public static String encode(String data) {
		if (StringUtils.isBlank(data)) {
			throw new RuntimeException("解密内容为空");
		}
		try {
			DESKeySpec dks = new DESKeySpec(generatorKey(data).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(LENGTH_KEY.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return byte2hex(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * DES算法，解密
	 *
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	public static String decode(String data) {
		if (StringUtils.isBlank(data)) {
			throw new RuntimeException("解密内容为空");
		}
		try {
			DESKeySpec dks = new DESKeySpec(generatorKey(data).getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(LENGTH_KEY.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(hex2byte(data.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	private static String generatorKey(String data) throws NoSuchAlgorithmException {
		String temp = PREFIX_KEY + data;
		temp = byte2hex(temp.getBytes());
		return temp;
	}

	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs.append('0');
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException();
		}
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static void main(String[] args) {

	}
}
