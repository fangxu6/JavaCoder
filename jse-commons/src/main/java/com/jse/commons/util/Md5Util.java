/**
 * 
 */
package com.jse.commons.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 鑫澜 # 下午1:44:39 MD5相关操作 com.zhuanqian.commons.util # Md5Util.java
 * 
 */
public final class Md5Util {

	/******
	 * 根据所给参数生成对应的md5Value
	 * 
	 * @param param
	 * @param arg1
	 * @return
	 */
	public static String getMd5ValueByParams(String param, Object salt) {
		Md5PasswordEncoder md5PE = new Md5PasswordEncoder();
		return md5PE.encodePassword(param, salt);
	}

	public static String getMD5(String md5Str) {
		try {
			// 1.初始化MessageDigest信息摘要对象,并指定为MD5不分大小写都可以
			MessageDigest md = MessageDigest.getInstance("md5");
			// 2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],
			// 将字符串转换为字节数组使用getBytes()方法完成
			// 指定时其字符编码 为utf-8
			md.update(md5Str.getBytes("utf-8"));
			// 3.计算信息摘要digest()方法
			// 返回值为字节数组
			byte[] hashCode = md.digest();
			// 4.将byte[] 转换为找度为32位的16进制字符串
			// 声明StringBuffer对象来存放最后的值
			StringBuffer sb = new StringBuffer();
			// 遍历字节数组
			for (byte b : hashCode) {
				// 对数组内容转化为16进制，
				sb.append(Character.forDigit(b >> 4 & 0xf, 16));
				// 换2次为32位的16进制
				sb.append(Character.forDigit(b & 0xf, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// ignore
		}
		return null;
	}

	// 方法二
	public static String getMD5Second(String str) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			for (byte b : md5.digest()) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
