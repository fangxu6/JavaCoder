/**
 *  Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import java.util.Random;

/**
 * @company jse-zq
 * @author 铁镔
 * @version VerificationCodeUtil.java, v 0.1 2015年3月26日 上午10:19:33
 */
public final class VerificationCodeUtil {

	/** 手机验证码种子 */
	private static final String	AUTH_CODE	= "0123456789";

	/**
	 * JVM 构造方法
	 */
	private VerificationCodeUtil() {
	}

	/*
	 * 获取随机的字符 "0123456789"
	 */
	public static String getRandomString() {
		int num = new Random().nextInt(AUTH_CODE.length());
		return String.valueOf(AUTH_CODE.charAt(num));
	}

	/**
	 * 按位数获取验证码
	 */
	public static String getCheckCode(int digits) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digits; i++) {
			int index = new Random().nextInt(AUTH_CODE.length());
			sb.append(AUTH_CODE.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * 获取Rrid
	 * 
	 * @return
	 */
	public static String getRrid() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 18; i++) {
			int index = new Random().nextInt(AUTH_CODE.length());
			sb.append(AUTH_CODE.charAt(index));
		}
		return sb.toString();
	}

}
