package com.jse.commons.util;

import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {

	private static final ZqLogger	logger	= new ZqLogger(SHA1Util.class);

	/**
	 * 传入文本内容，返回SHA-1串
	 *
	 * @param strText
	 * @return
	 */
	public static String SHA1(final String strText) {
		return SHA(strText, "SHA-1");
	}

	/**
	 * 加密
	 *
	 * @param strText
	 * @param strType
	 * @return
	 */
	private static String SHA(final String strText, final String strType) {
		String strResult = null;
		if (StringUtils.isBlank(strText)) {
			return strResult;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(strType);
			messageDigest.update(strText.getBytes());
			byte byteBuffer[] = messageDigest.digest();
			StringBuilder strHexString = new StringBuilder();
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			strResult = strHexString.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e, "SHA1Util,SHA");
		}
		return strResult;
	}
}
