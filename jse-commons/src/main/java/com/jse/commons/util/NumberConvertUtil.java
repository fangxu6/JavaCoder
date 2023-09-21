package com.jse.commons.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 
 * @company jse-zq
 * @author wei
 * @version AmountUtil.java, v 0.1 2015年4月24日 上午10:15:27
 */
public class NumberConvertUtil {

	private final static long	PERCENT_MULTIPLIER			= 100L;

	private final static long	RATE_MULTIPLIER				= 10000L;

	private final static long	ACCUM_ADJ_FACTOR_MULTIPLIER	= 10000000000L;
	private final static long	TWELVE_RATE					= 1000000000000L;
	private final static long	EIGHT_RATE					= 100000000L;

	/**
	 * 验证前端数值是否符合要求 范围：-9999 9999 9999 .9999 —— 9999 9999 9999.9999
	 * 
	 * @author LuoJiang 2017-12-28
	 */
	public static boolean checkNumberRange(String num) {
		return num.matches("(-)?[0-9]{1,12}(\\.[0-9]{1,4})?");
	}

	/**
	 * 设置涨停，跌停价格， 需要按照厘位进行四舍五入
	 * 
	 * @param rise
	 *            涨幅，如10%，填写rise=10
	 * @param upPrice
	 * 
	 * @return
	 */
	public static long setUpPrice(String priceStr, int rise) {
		long upPrice = string4Long(priceStr) * (100 + rise) / 100;
		int mile = (int) (upPrice % 100);
		if (mile >= 50) {
			upPrice = upPrice + 100;
		}

		return upPrice / 100 * 100L;
	}

	/**
	 * 设置涨停，跌停价格， 需要按照厘位进行四舍五入
	 * 
	 * @param fall
	 *            跌幅，如10%，填写fall=10
	 * @param upPrice
	 * 
	 * @return
	 */
	public static long setDownPrice(String priceStr, int fall) {
		long downPrice = string4Long(priceStr) * (100 - fall) / 100;
		int mile = (int) (downPrice % 100);
		if (mile >= 50) {
			downPrice = downPrice + 100;
		}

		return downPrice / 100 * 100L;
	}

	/**
	 * 设置涨停，跌停价格， 需要按照豪位进行四舍五入
	 * 
	 * @param upPrice
	 * @return
	 */
	public static long setUpPrice3(String priceStr) {
		long upPrice = string4Long(priceStr) * 110 / 100;
		int mile = (int) (upPrice % 10);
		if (mile >= 5) {
			upPrice = upPrice + 10;
		}

		return upPrice / 10 * 10l;
	}

	/**
	 * 设置涨停，跌停价格， 需要按照豪位进行四舍五入
	 * 
	 * @param upPrice
	 * @return
	 */
	public static long setDownPrice3(String priceStr) {
		long downPrice = string4Long(priceStr) * 90 / 100;
		int mile = (int) (downPrice % 10);
		if (mile >= 5) {
			downPrice = downPrice + 10;
		}

		return downPrice / 10 * 10l;
	}

	/**
	 * 设置亏损股涨停价格， 需要按照厘位进行四舍五入
	 * 
	 * @param upPrice
	 * @return
	 */
	public static long setLossUpPrice(String priceStr) {
		long upPrice = string4Long(priceStr) * 105 / 100;
		int mile = (int) (upPrice % 100);
		if (mile >= 50) {
			upPrice = upPrice + 100;
		}

		return upPrice / 100 * 100l;
	}

	/**
	 * 设置亏损股跌停价格， 需要按照厘位进行四舍五入
	 * 
	 * @param upPrice
	 * @return
	 */
	public static long setLossDownPrice(String priceStr) {
		long downPrice = string4Long(priceStr) * 95 / 100;
		int mile = (int) (downPrice % 100);
		if (mile >= 50) {
			downPrice = downPrice + 100;
		}

		return downPrice / 100 * 100l;
	}

	public static String calculateSaleOrBuyAmount(long amount) {
		long num = amount / 100;
		long remainder = amount % 100;
		if (remainder >= 50) {
			num = num + 1;
		}
		return String.valueOf(num);
	}

	public static long string10Long(String str) {
		if (StringUtils.isBlank(str)) {
			return 0l;
		}
		return new BigDecimal(str).multiply(new BigDecimal(ACCUM_ADJ_FACTOR_MULTIPLIER)).longValue();
	}

	public static String long10String(long longValue) {
		return new BigDecimal(longValue).divide(new BigDecimal(ACCUM_ADJ_FACTOR_MULTIPLIER)).toPlainString();
	}

	public static long string4Long(String strValue) {
		if (StringUtils.isBlank(strValue)) {
			return 0l;
		}
		return new BigDecimal(strValue).multiply(new BigDecimal(RATE_MULTIPLIER)).longValue();
	}

	public static long string2Long(String strValue) {
		if (StringUtils.isBlank(strValue)) {
			return 0l;
		}
		return new BigDecimal(strValue).multiply(new BigDecimal(PERCENT_MULTIPLIER)).longValue();
	}

	// 保留两位小数
	public static String long4String(long longValue) {
		BigDecimal bigDecimal = new BigDecimal(longValue).divide(new BigDecimal(RATE_MULTIPLIER));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.toPlainString();
	}

	// 保留2位小数
	public static double long2Double(Long intValue) {
		if (intValue == null) {
			return 0.0;
		}
		BigDecimal bigDecimal = new BigDecimal(intValue).divide(new BigDecimal(RATE_MULTIPLIER));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	public static double long2DoubleWithFormat(long longValue) {
		BigDecimal bigDecimal = new BigDecimal(longValue).divide(new BigDecimal(RATE_MULTIPLIER));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	/**
	 * 
	 * 数据的统一显示格式，数值小于百万，显示全部所有数字，
	 * 
	 * 大于等于百万；小于亿，用万为单位；
	 * 
	 * 大于等于亿，用亿为单位； 需要显示小数，就显示小数点两位。
	 * 
	 * @param longValue
	 * @return
	 */
	public static String calculateFlexible(long longValue) {
		String longStr = String.valueOf(longValue);
		BigDecimal bigDecimal = null;
		if (longStr.length() <= 9) {
			bigDecimal = new BigDecimal(longValue).divide(new BigDecimal(RATE_MULTIPLIER));
			bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			return bigDecimal.toString();
		} else if (longStr.length() > 12) {
			bigDecimal = new BigDecimal(longValue).divide(new BigDecimal(TWELVE_RATE));
			if (bigDecimal.doubleValue() >= 1000) {
				bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
			} else if (bigDecimal.doubleValue() >= 100 && bigDecimal.doubleValue() < 1000) {
				bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
			} else {
				bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			return bigDecimal.toString() + "亿";
		} else {
			bigDecimal = new BigDecimal(longValue).divide(new BigDecimal(EIGHT_RATE));
			if (bigDecimal.doubleValue() >= 1000) {
				bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
			} else if (bigDecimal.doubleValue() >= 100 && bigDecimal.doubleValue() < 1000) {
				bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
			} else {
				bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			return bigDecimal.toString() + "万";
		}

	}

	public static String long2String(Long intValue) {
		BigDecimal bigDecimal = new BigDecimal(intValue).divide(new BigDecimal(PERCENT_MULTIPLIER));
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bigDecimal);
	}

	public static String long2String3(Long intValue) {
		BigDecimal bigDecimal = new BigDecimal(intValue).divide(new BigDecimal(PERCENT_MULTIPLIER));
		bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bigDecimal);
	}

	/**
	 * 将BigDecimal类型的数 保留两位小数
	 * 
	 * @param bigDecimal
	 * @return
	 */
	public static String convertToString(BigDecimal bigDecimal) {
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.toPlainString();
	}

	/****
	 * 将float类型的数字保留3位小数后乘以1000再转化成Integer
	 *
	 * @param f
	 * @return Integer
	 */
	public static Integer getIntegerFromFloat(float f) {
		BigDecimal b = new BigDecimal(f);
		float middleValue = b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
		Float floatValue = middleValue * 1000;
		return floatValue.intValue();
	}

}
