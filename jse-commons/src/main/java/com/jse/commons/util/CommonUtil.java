/**
 * Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import com.zhuanqian.commons.cache.LocalCache;
import com.zhuanqian.commons.constant.Constant;
import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类。 提供空判断、集合、字符串、类型转换、解析等公共方法。
 * 
 * @company jse-zq
 * @author dango
 * @since 1.0
 * @date 2015年3月22日 下午3:17:52
 */
public class CommonUtil {
	private static ZqLogger							logger					= new ZqLogger(CommonUtil.class);
	private static final Map<String, LocalCache>	LOCAL_CACHE_REPOSITORY	= new ConcurrentHashMap<String, LocalCache>();

	/**
	 * 获取niuqia服务器的内网IP
	 *
	 * @return
	 */
	public static String getLanIpAddress() {
		String command = "ifconfig|grep 192.168.10|awk '{print $2}'|cut -d : -f2";
		String ip = null;
		ip = BashUtil.getInputStream(BashUtil.runCommand(command));
		return ip;
	}

	/**
	 * 判断字符串中是否包含数字
	 *
	 * @param content
	 * @return
	 */
	public static boolean hasDigit(String content) {
		return content.replaceAll("\\d", "").length() != content.length();
	}

	public static String transFirstLetter(String str, boolean isUpper) {
		String firstWord = str.substring(0, 1);
		String leftWords = str.substring(1);
		firstWord = (isUpper) ? firstWord.toUpperCase() : firstWord.toLowerCase();
		return firstWord + leftWords;
	}

	public static int[] parse2IntArray(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[] {};
		}
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = Integer.parseInt(arr[i].toString());
		}
		return result;
	}

	/**
	 * @Description:解析表达式，支持占位符
	 * @param exp
	 *            ,如my name is {0},my age is {1}
	 * @param values
	 *            ,按顺序传入，values[0] = 'jack',values[1] = 20
	 * @return
	 */
	public static String parseExpression(String exp, Object... values) {
		String expRet = exp;
		if (!ArrayUtils.isEmpty(values)) {
			for (int i = 0; i < values.length; i++) {
				String replace = values[i] == null ? "" : values[i].toString();
				expRet = expRet.replaceAll("\\{" + i + "\\}", replace);
			}
		}
		return expRet;
	}

	public static Properties loadProperties(String cpPath) throws IOException {
		InputStream is = null;
		try {
			is = CommonUtil.class.getResourceAsStream(cpPath);
			if (is == null) {
				return null;
			}
			Properties prop = new Properties();
			prop.load(is);
			return prop;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static String getPropertyValue(Properties prop, String key) {
		if (prop == null) {
			return null;
		}
		String value = prop.getProperty(key);
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			value = new String(value.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e.toString());
		}
		return value;
	}

	/**
	 * 替换字符串中的"_",且单词手字母大写
	 * 
	 * @return
	 */
	public static String replace2JavaName(String s) {
		StringBuilder sb = new StringBuilder(s);
		int flag = -1;
		while ((flag = sb.indexOf("_")) != -1) {
			sb.replace(flag, flag + 2, String.valueOf(sb.charAt(flag + 1)).toUpperCase());
		}
		return sb.toString();
	}

	public static Object[] parse2ObjectArray(long[] items) {
		if (ArrayUtils.isEmpty(items)) {
			return new Object[] {};
		}
		Object[] result = new Object[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = Long.valueOf(items[i]);
		}
		return result;
	}

	public static Object[] parse2ObjectArray(int[] items) {
		if (ArrayUtils.isEmpty(items)) {
			return new Object[] {};
		}
		Object[] result = new Object[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = Integer.valueOf(items[i]);
		}
		return result;
	}

	public static Object[] parse2ObjectArray(short[] items) {
		if (ArrayUtils.isEmpty(items)) {
			return new Object[] {};
		}
		Object[] result = new Object[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = Short.valueOf(items[i]);
		}
		return result;
	}

	public static Object[] parse2ObjectArray(float[] items) {
		if (ArrayUtils.isEmpty(items)) {
			return new Object[] {};
		}
		Object[] result = new Object[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = Float.valueOf(items[i]);
		}
		return result;
	}

	public static Object[] parse2ObjectArray(double[] items) {
		if (ArrayUtils.isEmpty(items)) {
			return new Object[] {};
		}
		Object[] result = new Object[items.length];
		for (int i = 0; i < items.length; i++) {
			result[i] = Double.valueOf(items[i]);
		}
		return result;
	}

	public static String getPrefixBlank(int lay) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lay; i++) {
			sb.append("    ");
		}
		return sb.toString();
	}

	/**
	 * 将fieldName 转换成 field_name 这种格式的字符串
	 * 
	 * @param javaFieldName
	 * @return
	 */
	public static String getFiledName(String javaFieldName) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < javaFieldName.length(); i++) {
			char c = javaFieldName.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_');
			}
			sb.append(c);
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 多个参数组成字符串
	 */
	public static String concat(Object... args) {
		int n = 0;
		for (Object s : args) {
			if (s == null) {
				continue;
			}
			n += s.toString().length();
		}
		StringBuilder sb = new StringBuilder(n);
		for (Object s : args) {
			if (s == null) {
				continue;
			}
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 从一串字符串中取出手机号
	 * 
	 * @param num
	 * @return
	 */
	public static String getPhoneNumber(String num) {
		if (num == null || num.length() == 0) {
			return "";
		}
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
		Matcher matcher = pattern.matcher(num);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find()) {
			bf.append(matcher.group()).append(",");
		}
		int len = bf.length();
		if (len > 0) {
			bf.deleteCharAt(len - 1);
		}
		return bf.toString();
	}

	/**
	 * 生成6位随机数
	 * 
	 * @return
	 */
	public static String createRandomNumber() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 8; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 检查是否符合身份证号码规则，待完善
	 *
	 * @param identity
	 * @return
	 */
	public static boolean idCardCheck(String identity) {
		if (StringUtils.isBlank(identity)) {
			return false;
		}
		identity = StringUtils.upperCase(identity);
		if (identity.matches("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9Xx])$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查是否符合手机号规则
	 *
	 * @param identity
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || !phoneNumber.matches("^[1](3|4|5|7|8|9)[0-9]{9}$")) {
			return false;
		}
		return true;
	}

	/**
	 * 隐藏身份证号
	 * 
	 * @param data
	 * @return
	 */
	public static String hideIdNumber(String data) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		data = StringUtils.trimToEmpty(data);
		StringBuffer sb = new StringBuffer();
		if (data.length() >= 2) {
			String hideData = "";
			for (int index = 1; index < data.length(); index++) {
				hideData += "*";
			}
			sb.append(data.substring(0, 1)).append(hideData).append(data.substring(data.length() - 1, data.length()));

		} else {
			sb.append(data);

		}
		return sb.toString();
	}

	public static String hideInfo(String data, int hiddenCharInfo) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		if (hiddenCharInfo <= 0) {
			hiddenCharInfo = 4;
		}
		data = StringUtils.trimToEmpty(data);
		StringBuffer sb = new StringBuffer();
		int dataLength = data.length();
		if (dataLength >= hiddenCharInfo) {
			String hidenStar = StringUtils.leftPad("*", Math.min(hiddenCharInfo, dataLength), "*");
			int remainChar = dataLength - hiddenCharInfo;
			if (remainChar <= 0) {
				sb.append(hidenStar);
			} else {
				remainChar = remainChar / 2;
				if (hiddenCharInfo > 1) {
					sb.append(data.substring(0, remainChar)).append(hidenStar)
							.append(data.substring(hiddenCharInfo + remainChar, data.length()));
				} else {
					hidenStar = StringUtils.leftPad("*", Math.max(hiddenCharInfo, dataLength - 1), "*");
					sb.append(data.charAt(0)).append(hidenStar);
				}
			}
		} else {
			String hidenStar = StringUtils.leftPad("*", Math.min(hiddenCharInfo, dataLength), "*");
			sb.append(hidenStar);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String hideInfo(String data) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		data = StringUtils.trimToEmpty(data);
		StringBuffer sb = new StringBuffer();
		if (data.length() >= 4) {
			sb.append(data.substring(0, 3)).append("****").append(data.substring(data.length() - 4, data.length()));

		} else {
			sb.append(data).append("****").append(data);

		}
		return sb.toString();
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String hideStringInfo(String data) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		data = StringUtils.trimToEmpty(data);
		StringBuffer sb = new StringBuffer();

		if (data.length() > 15) {
			sb.append(data.substring(0, 5)).append("*****").append(data.substring(data.length() - 5, data.length()));

		} else if (data.length() >= 9 && data.length() <= 15) {
			sb.append(data.substring(0, 3)).append("***").append(data.substring(data.length() - 3, data.length()));

		} else if (data.length() > 3 && data.length() < 9) {
			sb.append(data.substring(0, 3)).append("***");
		} else {
			sb.append(data);
		}
		return sb.toString();
	}

	public static String hideIp(String data) {
		if (StringUtils.isBlank(data)) {
			return "";
		}
		data = StringUtils.trimToEmpty(data);
		int length = data.length();
		StringBuffer sb = new StringBuffer();
		String[] dataArr = data.split("\\.");
		if (dataArr.length != 4) {
			return "";
		}
		int hideLength = length - dataArr[0].length() - dataArr[3].length() - 2;
		String hideStr = "";
		for (int i = 0; i < hideLength; i++) {
			hideStr += "*";
		}
		sb.append(dataArr[0] + ".").append(hideStr).append("." + dataArr[3]);
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(hideInfo("15858178525", 12));
	}

	/**
	 * 判断字符串中是都包含中文，默认为true
	 *
	 * @param str
	 * @return
	 */
	public static boolean isChineseCharacterIncluded(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count++;
			}
		}
		if (count == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 *
	 * @param clazz
	 * @param initSize
	 * @return
	 */
	public static LocalCache getLocalCache(Class<?> clazz, Integer initSize) {
		LocalCache localCache = LOCAL_CACHE_REPOSITORY.get(clazz.getName());
		if (localCache == null) {
			// FIXME 考虑多线程重复new entity的情况
			if (initSize == null) {
				localCache = new LocalCache();
			} else {
				localCache = new LocalCache(initSize);
			}
			LOCAL_CACHE_REPOSITORY.put(clazz.getName(), localCache);
			return localCache;
		}
		return localCache;
	}

	/**
	 * 
	 *
	 * @param string
	 * @param initSize
	 * @return
	 */
	public static LocalCache getLocalCache(String key, Integer initSize) {
		LocalCache localCache = LOCAL_CACHE_REPOSITORY.get(key);
		if (localCache == null) {
			// FIXME 考虑多线程重复new entity的情况
			if (initSize == null) {
				localCache = new LocalCache();
			} else {
				localCache = new LocalCache(initSize);
			}
			LOCAL_CACHE_REPOSITORY.put(key, localCache);
			return localCache;
		}
		return localCache;
	}

	/**
	 * 从n个数字中选择m个数字 eg:传入的列表为{1,2,3,4},m=3 result: {[1 ,2 ,3],[ 1, 2, 4],[ 1, 3, 4],[ 2, 3 ,4]}
	 * 
	 * @param list
	 * @param m
	 * @return
	 */
	public static List<Integer[]> combine(List<Integer> list, int m) {
		int n = list.size();
		if (m > n) {
			throw new ZqException("错误！列表中只有" + n + "个元素。" + m + "大于" + n + "!!!");
		}

		List<Integer[]> result = new ArrayList<Integer[]>();
		if (m == n) {
			Integer[] tem = new Integer[n];
			list.toArray(tem);
			result.add(tem);
			return result;
		}
		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		// 初始化
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(getCombineResult(bs, list, m));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			// 将左边的1全部移动到数组的最左边

			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}

			// 检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		result.add(getCombineResult(bs, list, m));

		return result;
	}

	private static Integer[] getCombineResult(int[] bs, List<Integer> a, int m) {
		Integer[] result = new Integer[m];
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[pos] = a.get(i);
				pos++;
			}
		}
		return result;
	}

	/**
	 * 数组suffixArray，长度为k, 从总获取n个账号 ----》Cnk组合
	 */
	public static List<Integer[]> combination(List<Integer> list, int n) {
		if (n > list.size()) {
			throw new ZqException("n不能大于总长度");
		}
		int[] tempArray = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			tempArray[i] = list.get(i);
		}
		List<String> resultTemp = new ArrayList<String>();
		combination(resultTemp, "", tempArray, n);
		List<Integer[]> result = new ArrayList<Integer[]>();
		for (String string : resultTemp) {
			Integer[] intArray = CommonConverter.convertToNumberList(string, ",", Integer.class).toArray(
					new Integer[] {});
			result.add(intArray);
		}
		return result;
	}

	private static void combination(List<String> inedxsResult, String indexTemp, int[] tempArray, int n) {
		if (n == 1) {
			for (int i = 0; i < tempArray.length; i++) {
				inedxsResult.add(indexTemp + tempArray[i]);
			}
		} else {
			for (int i = 0; i < tempArray.length - (n - 1); i++) {
				String ss = indexTemp + tempArray[i] + ",";
				// 建立从i开始的子数组,从子数组里面过滤出 n-1下标
				int[] subTempArray = new int[tempArray.length - i - 1];
				System.arraycopy(tempArray, i + 1, subTempArray, 0, subTempArray.length);
				combination(inedxsResult, ss, subTempArray, n - 1);
			}
		}
	}

	public static String getDataServiceToken() {
		return AESUtil.encrypt(Constant.DATA_SERVICE_TOKEN + System.currentTimeMillis());
	}

	/**
	 * 将16位16进制的字符转化为long，例如hex16=ffffffffffffffff,返回值为-1
	 *
	 * @param hex16
	 * @return
	 */
	public static long Hex16ToLong(String hex16) {
		if (StringUtils.isBlank(hex16)) {
			throw new ZqException("数据非法：" + hex16);
		}
		// 去掉开头的0x
		if (hex16.startsWith("0x")) {
			hex16 = hex16.substring(2, hex16.length());
		}
		return new BigInteger(hex16, 16).longValue();
	}

	public static String hideEmailInfo(String mailForAuth) {
		if (StringUtils.isBlank(mailForAuth)) {
			return "";
		}
		mailForAuth = StringUtils.trimToEmpty(mailForAuth);
		StringBuffer sb = new StringBuffer();
		Integer loginNameLenth = mailForAuth.indexOf("@");
		if (loginNameLenth > 15) {
			sb.append(mailForAuth.substring(0, 5));
			for (int i = 0; i < loginNameLenth - 10; i++) {
				sb.append("*");
			}
			sb.append(mailForAuth.substring(loginNameLenth - 5, loginNameLenth));
		} else if (9 < loginNameLenth && loginNameLenth <= 15) {
			sb.append(mailForAuth.substring(0, 3));
			for (int i = 0; i < loginNameLenth - 6; i++) {
				sb.append("*");
			}
			sb.append(mailForAuth.substring(loginNameLenth - 3, loginNameLenth));
		} else if (loginNameLenth >= 4 && loginNameLenth <= 9) {
			sb.append(mailForAuth.substring(0, 2));
			for (int i = 0; i < loginNameLenth - 4; i++) {
				sb.append("*");
			}
			sb.append(mailForAuth.substring(loginNameLenth - 2, loginNameLenth));
		} else {
			sb.append(mailForAuth.substring(0, 1));
			for (int i = 0; i < loginNameLenth - 1; i++) {
				sb.append("*");
			}
		}
		sb.append(mailForAuth.substring(loginNameLenth, mailForAuth.length()));
		return sb.toString();
	}

	/**
	 * 根据orgLoginId+idInOrg+createTime后三位拼装的订单编号解析orgLoginId和idInOrg,返回一个数组，index 0=orgLoginId,index 1=idInOrg,index=创建时间后3位
	 *
	 * @param orderNo
	 * @return
	 */
	public static Long[] parseOrgLoginIdAndIdInOrg(String orderNo) {
		Long[] orgLoginIdAndidInOrg = new Long[] {};
		if (!NumberUtils.isDigits(orderNo)) {
			return orgLoginIdAndidInOrg;
		}
		Long orgLoginId = null;
		Long idInOrg = null;
		Long createTime = null;
		int length = orderNo.length();
		// 目前我们系统中的机构可以分为三种方式190000，2660000,31240000
		// idForDisplay的组合方式：机构号+idInOrg+createTime后3位,例如31020000机构，有一个idInOrg=1234的订单，组合之后为：31021234235
		if (StringUtils.startsWith(orderNo, "1")) {
			if (length < 6) {
				return orgLoginIdAndidInOrg;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(orderNo, 0, 2) + "0000");
			idInOrg = Long.valueOf(StringUtils.substring(orderNo, 2, length - 3));
			createTime = Long.valueOf(StringUtils.substring(orderNo, length - 3, length));
		} else if (StringUtils.startsWith(orderNo, "2")) {
			if (length < 7) {
				return orgLoginIdAndidInOrg;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(orderNo, 0, 3) + "0000");
			idInOrg = Long.valueOf(StringUtils.substring(orderNo, 3, length - 3));
			createTime = Long.valueOf(StringUtils.substring(orderNo, length - 3, length));
		} else if (StringUtils.startsWith(orderNo, "3")) {
			if (length < 8) {
				return orgLoginIdAndidInOrg;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(orderNo, 0, 4) + "0000");
			idInOrg = Long.valueOf(StringUtils.substring(orderNo, 4, length - 3));
			createTime = Long.valueOf(StringUtils.substring(orderNo, length - 3, length));
		} else {
			return orgLoginIdAndidInOrg;
		}
		return new Long[] { orgLoginId, idInOrg, createTime };
	}

	/**
	 * 获取orgLoginId+idInOrg+createTime后3位组装的id
	 *
	 * @param orgLoginId
	 * @param idInOrg
	 * @param createTime
	 * @return
	 */
	public static String getAssembledId(Long orgLoginId, Long idInOrg, Long createTime) {
		if (orgLoginId == null || idInOrg == null || createTime == null) {
			return "";
		}
		String orgLoginIdStr = orgLoginId.toString();
		String createTimeStr = createTime.toString();
		return StringUtils.substring(orgLoginIdStr, 0, orgLoginIdStr.length() - 4) + idInOrg
				+ StringUtils.substring(createTimeStr, createTimeStr.length() - 3, createTimeStr.length());
	}

	public static Long extractOrgLoginIdFromLoginId(Long loginId) {
		Long orgLoginId = null;
		if (loginId == null) {
			return null;
		}
		String loginIdStr = loginId.toString();
		int length = loginIdStr.length();
		// 目前我们系统中的机构可以分为三种方式190000，2660000,31240000
		// idForDisplay的组合方式：机构号+idInOrg+createTime后3位,例如31020000机构，有一个idInOrg=1234的订单，组合之后为：31021234235
		if (StringUtils.startsWith(loginIdStr, "1")) {
			if (length < 6) {
				return orgLoginId;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(loginIdStr, 0, 2) + "0000");
		} else if (StringUtils.startsWith(loginIdStr, "2")) {
			if (length < 7) {
				return orgLoginId;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(loginIdStr, 0, 3) + "0000");
		} else if (StringUtils.startsWith(loginIdStr, "3")) {
			if (length < 8) {
				return orgLoginId;
			}
			orgLoginId = Long.valueOf(StringUtils.substring(loginIdStr, 0, 4) + "0000");
		} else {
			return orgLoginId;
		}
		return orgLoginId;
	}

	/**
	 * 获取orgLoginId+idInOrg组装后的id
	 *
	 * @param orgLoginId
	 * @param idInOrg
	 * @return
	 */
	public static Long getAssembledIdByOrgLoginIdAndIdInOrg(Long orgLoginId, Long idInOrg) {
		if (orgLoginId == null || idInOrg == null) {
			return null;
		}
		String orgLoginIdStr = orgLoginId.toString();
		return Long.valueOf(StringUtils.substring(orgLoginIdStr, 0, orgLoginIdStr.length() - 4) + idInOrg);
	}

}
