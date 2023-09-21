package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DESUtil {
	private static final ZqLogger	logger			= new ZqLogger(DESUtil.class);
	private static final String		DES_ALGORITHM	= "DES";

	/**
	 * DES加密
	 * 
	 * @param plainData
	 *            原始字符串
	 * @param secretKey
	 *            加密密钥
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String encryption(String plainData, String secretKey) throws Exception {

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));
			// 不能把加密后的字节数组直接转换成字符串
			byte[] buf = cipher.doFinal(plainData.getBytes());
			return Base64Utils.encode(buf);
		} catch (Exception e) {
			logger.error(e, "DESUtil,encryption");
			throw new ZqException("加密出现异常", e);
		}

	}

	/**
	 * DES解密
	 * 
	 * @param secretData
	 *            密码字符串
	 * @param secretKey
	 *            解密密钥
	 * @return 原始字符串
	 * @throws Exception
	 */
	public static String decryption(String secretData, String secretKey) throws Exception {

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));
			byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
			return new String(buf);
		} catch (Exception e) {
			logger.error(e, "DESUtil,decryption");
			throw new ZqException("解密出现异常", e);
		}
	}

	/**
	 * 获得秘密密钥
	 * 
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 */
	private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidKeyException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
		DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
		keyFactory.generateSecret(keySpec);
		return keyFactory.generateSecret(keySpec);
	}

	static private class Base64Utils {
		static private char[]	alphabet	= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
													.toCharArray();
		static private byte[]	codes		= new byte[256];
		static {
			for (int i = 0; i < 256; i++)
				codes[i] = -1;
			for (int i = 'A'; i <= 'Z'; i++)
				codes[i] = (byte) (i - 'A');
			for (int i = 'a'; i <= 'z'; i++)
				codes[i] = (byte) (26 + i - 'a');
			for (int i = '0'; i <= '9'; i++)
				codes[i] = (byte) (52 + i - '0');
			codes['+'] = 62;
			codes['/'] = 63;
		}

		/**
		 * 将原始数据编码为base64编码
		 */
		static private String encode(byte[] data) {
			char[] out = new char[((data.length + 2) / 3) * 4];
			for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
				boolean quad = false;
				boolean trip = false;
				int val = (0xFF & (int) data[i]);
				val <<= 8;
				if ((i + 1) < data.length) {
					val |= (0xFF & (int) data[i + 1]);
					trip = true;
				}
				val <<= 8;
				if ((i + 2) < data.length) {
					val |= (0xFF & (int) data[i + 2]);
					quad = true;
				}
				out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
				val >>= 6;
				out[index + 1] = alphabet[val & 0x3F];
				val >>= 6;
				out[index + 0] = alphabet[val & 0x3F];
			}

			return new String(out);
		}

		/**
		 * 将base64编码的数据解码成原始数据
		 */
		static private byte[] decode(char[] data) {
			int len = ((data.length + 3) / 4) * 3;
			if (data.length > 0 && data[data.length - 1] == '=')
				--len;
			if (data.length > 1 && data[data.length - 2] == '=')
				--len;
			byte[] out = new byte[len];
			int shift = 0;
			int accum = 0;
			int index = 0;
			for (int ix = 0; ix < data.length; ix++) {
				int value = codes[data[ix] & 0xFF];
				if (value >= 0) {
					accum <<= 6;
					shift += 6;
					accum |= value;
					if (shift >= 8) {
						shift -= 8;
						out[index++] = (byte) ((accum >> shift) & 0xff);
					}
				}
			}
			if (index != out.length)
				throw new Error("miscalculated data length!");
			return out;
		}
	}
}
