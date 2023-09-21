/**
 *  Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import com.zhuanqian.commons.enums.InternetServiceProvider;
import com.zhuanqian.commons.enums.LoginType;
import org.apache.commons.lang.StringUtils;

/**
 * 数字相关的验证类
 * 
 * @company jse-zq
 * @author dango
 * @version LoginPhoneVerification.java, v 0.1 2015年3月26日 下午3:02:03
 */
public class UserVerificationUtil {

	/** 国内手机格式的正则表达式 */
	public final static String	REGEX_MOBILE				= "^[1](3|4|5|6|7|8|9)[0-9]{9}$";

	/** 国内手机格式的正则表达式，包含扩展 */
	public final static String	REGEX_MOBILE_CONTAIN_EXTEND	= "^[1](3|4|5|6|7|8|9)[0-9]{9}(-[0-9]{1,6})?$";

	/** 国外手机格式的正则表达式 */
	public final static String	REGEX_FOREIGN_MOBILE		= "^(00)?\\d{1,3}\\-\\d{6,13}$";

	/** Email格式的正则表达式 */
	public final static String	REGEX_EMAIL					= "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,20}$";

	public final static String	REGEX_EMAIL_CONTAIN_EXTEND	= "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,20}(-[0-9]{1,6})?$";

	public final static String	PATTERN_CHINA_MOBILE		= "^1(3[4-9]|5[01789]|8[78])\\d{8}$";

	public final static String	PATTERN_CHINA_UNICOM		= "^1(3[0-2]|5[256]|8[56])\\d{8}$";

	public final static String	PATTERN_CHINA_TELECOM		= "^(18[09]|1[35]3)\\d{8}$";

	/*
	 * 检查手机号是否合法
	 */
	public static boolean checkLoginPhone(String loginPhone) {
		boolean checkResult = true;
		if (loginPhone == null || !loginPhone.matches(REGEX_MOBILE)) {
			checkResult = false;
		}
		return checkResult;
	}

	public static boolean checkLoginMail(String loginMail) {
		boolean checkResult = true;
		if (loginMail == null || !loginMail.matches(REGEX_EMAIL)) {
			checkResult = false;
		}
		return checkResult;
	}

	public static LoginType guessLoginType(String userName) {
		if (checkLoginPhone(userName)) {
			return LoginType.LOGIN_BY_PHONE;
		}
		if (checkLoginMail(userName)) {
			return LoginType.LOGIN_BY_MAIL;
		}
		return LoginType.DEFAULT_LOGIN_TYPE;
	}

	/**
	 * 判断手机号是哪个运营商的
	 *
	 * @param phone
	 * @return
	 */
	public static InternetServiceProvider checkISPForPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return null;
		}
		if (phone.matches(PATTERN_CHINA_MOBILE)) {
			return InternetServiceProvider.CHINA_MOBILE;
		} else if (phone.matches(PATTERN_CHINA_UNICOM)) {
			return InternetServiceProvider.CHINA_UNICOM;
		} else if (phone.matches(PATTERN_CHINA_TELECOM)) {
			return InternetServiceProvider.CHINA_TELECOM;
		} else {
			return null;
		}
	}
}
