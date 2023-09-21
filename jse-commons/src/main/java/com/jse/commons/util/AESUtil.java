package com.jse.commons.util;

import com.jse.commons.component.PropertyTemplate;
import com.jse.commons.exception.ZqException;
import com.jse.commons.log.ZqLogger;
import com.jse.commons.securirty.ZqSecretKeyType;
import com.jse.commons.security.util.ZqEncryptUtil;
import com.jse.commons.spring.SpringContextHolder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	private static final ZqLogger	logger						= new ZqLogger(AESUtil.class);

	private static final String		ENCODED_TYPE				= "utf-8";
	private static final String		KEY_ALGORITHM				= "AES";
	private static final String		DEFAULT_CIPHER_ALGORITHM	= "AES/ECB/PKCS5Padding";		// 默认的加密算法 "算法/模式/补码方式"

	// private static final String DEFAULT_SECURITY_KEY = "ZQJRXXFWZRYXGSJW";

	// public static final String DB_SECURITY_PHONE_KEY = "MYSQLINZHANGQIAN";

	// 加密
	public static String encrypt(String sSrc) {
		PropertyTemplate propertyTemplate = SpringContextHolder.getBean(PropertyTemplate.class);
		String defaultSecretKey = propertyTemplate.getDefaultSecretKey();
		if (StringUtils.isBlank(defaultSecretKey)) {
			logger.error("服务器端没有配置secretKey,导致没法加密密码");
			throw new ZqException("密码加密异常");
		}
		return encryptWithSecurityKey(sSrc, defaultSecretKey);
	}

	// 加密
	public static String encryptWithSecurityKey(String sSrc, String sKey) {
		if (sKey == null) {
			logger.info("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			logger.info("Key长度不是16位");
			return null;
		}
		try {
			byte[] raw = sKey.getBytes(ENCODED_TYPE);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes(ENCODED_TYPE));
			return new Base64().encodeToString(encrypted);
		} catch (Exception e) {
			logger.error(e, "加密出现异常");
			throw new ZqException("加密出现异常", e);
		}
	}

//	public static String decrypt(String sSrc) {
//		PropertyTemplate propertyTemplate = SpringContextHolder.getBean(PropertyTemplate.class);
//		String defaultSecretKey = propertyTemplate.getDefaultSecretKey();
//		if (StringUtils.isBlank(defaultSecretKey)) {
//			logger.error("服务器端没有配置secretKey,导致没法密码解密");
//			throw new ZqException("密码解密异常");
//		}
//		return decryptWithSecurityKey(sSrc, defaultSecretKey);
//	}
	
	public static String decrypt(String sSrc) {
		// PropertyTemplate propertyTemplate = SpringContextHolder.getBean(PropertyTemplate.class);
		// String defaultSecretKey = propertyTemplate.getDefaultSecretKey();
		// if (StringUtils.isBlank(defaultSecretKey)) {
		// logger.error("服务器端没有配置secretKey,导致没法密码解密");
		// throw new ZqException("密码解密异常");
		// }
		return decryptWithSecurityKey(sSrc, "ZQJRXXFWZRYXGSJW");
	}


	// 解密
	public static String decryptWithSecurityKey(String sSrc, String sKey) {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				logger.info("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				logger.info("Key长度不是16位");
				return null;
			}

			byte[] raw = sKey.getBytes(ENCODED_TYPE);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted = new Base64().decode(sSrc);
			byte[] original = cipher.doFinal(encrypted);
			return new String(original, ENCODED_TYPE);
		} catch (Exception ex) {
			logger.error(ex, "解密出现异常");
			throw new ZqException("解密出现异常", ex);
		}
	}

	public static void main(String[] args) throws Exception {
		// 0200hxz 8258 901021
		System.out.println(AESUtil.decrypt("vx2gcgO4hVYpas80q6sxLA=="));
		String mac = "3C:95:09:20:5E:E4";
		// String test = ZqEncryptUtil.getEncrypt("15858178525", "PHONENEWSECURKEY");
		// System.out.println(test);
		// System.out.println("xxx:" + ZqEncryptUtil.getDecrypt(test, "PHONENEWSECURKEY"));
		System.out.println(ZqEncryptUtil.getDecrypt("2eVvWuC3A1+1m/krQpURSg==", ZqSecretKeyType.PHONE.getCurrentKey()));

		System.out.println(ZqEncryptUtil.getDecrypt("7p+Xnhl+AeqSDA6FteNjyA==", ZqSecretKeyType.PHONE.getNewKey()));

	}
}
