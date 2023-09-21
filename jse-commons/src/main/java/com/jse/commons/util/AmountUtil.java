package com.jse.commons.util;

import com.zhuanqian.commons.constant.Constant;
import com.zhuanqian.commons.exception.ZqException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 系统内金额工具类， 主要涉及到与外部渠道的金额转换
 */
public final class AmountUtil {

	/** 系统内部金额正则定义 */
	private static final String	MONEY_REG				= "^(-?\\d+)(\\.\\d{0,4})?";
	/** 系统内部数字期货金额正则定义 */
	private static final String	MONEY_REG_STDEX_FUTURES	= "^(-?\\d+)(\\.\\d+)?";
	// 4位小数
	public static final String	FOUR_DECIMALS			= "#0.0000";
	// 平均价格式
	public static final String	UNIT_PRICE				= "#0.000";
	// 人民币格式
	public static final String	YUAN_FORMAT				= "#0.00";

	public static final String	RATE_OF_CHANGE_FORMAT	= "#0.00";
	// 1亿
	public static final long	HUNDRED_MILLION			= 100000000L;
	// 百万
	public static final long	MILLION					= 1000000L;
	// 1万
	public static final long	TEN_THOUSAND			= 10000L;
	// 1千
	public static final long	THOUSAND				= 1000L;
	public static final long	HUNDRED					= 100L;
	// -1
	public static final long	MINUS_ONE				= -1L;
	// 10^14
	public static final long	ONE_HUNDRED_TRILLION	= 100000000000000L;
	private static final String	regex					= "^[+-]?\\d+(\\.\\d+)?$";

	private AmountUtil() {
	}

	public static long getSettlementNumber(Number number) {
		if (number == null || number.longValue() == 0) {
			return 0L;
		}
		if (number.longValue() <= 100) {
			return 100L;
		}
		return number.longValue();
	}

	/**
	 * 系统内数值金额转换为元为单位的字符串， 精确到分。小于分的部分不做四舍五入<br>
	 * 例子：<br>
	 * 16元1分8厘，160180 -> 16.01 <br>
	 * 6角，6000 -> 0.60<br>
	 * 负8角9分，-8900 -> -0.89
	 * 
	 * @param amount
	 * @return
	 */
	public static String numericValueToYuan(Number amount) {
		if (amount == null) {
			return "0.00";
		}
		long value = amount.longValue();
		boolean isNegative = false;
		if (amount.longValue() < 0) {
			value = Math.abs(amount.longValue());
			isNegative = true;
		}
		StringBuffer channelAmount = new StringBuffer();
		long yuan = value / Constant.PRICE_UNIT;
		long cent = (value % Constant.PRICE_UNIT) / (Constant.PRICE_UNIT / 100);

		channelAmount.append(yuan).append(".");
		if (cent < 10L) {
			channelAmount.append("0");
		}
		channelAmount.append(cent);
		if (isNegative && value / 100 != 0) {
			channelAmount.insert(0, '-');
		}
		return channelAmount.toString();
	}

	/**
	 * 按精度缩小
	 */
	public static String narrowByPrecision(Number amount, int precision) {
		if (amount == null) {
			amount = 0L;
		}
		return new BigDecimal(amount.longValue()).divide(new BigDecimal(Math.pow(10.0, precision)), precision,
				BigDecimal.ROUND_DOWN).toPlainString();
	}

	/**
	 * 系统内数值金额转换为元为单位的字符串， 精确到厘。小于厘的部分不做四舍五入<br>
	 * 例子：<br>
	 * 16元1分8厘3毫，160183 -> 16.018 <br>
	 * 
	 * @param amount
	 * @return
	 */
	public static String numericValueToYuanExactToLi(Number amount) {
		if (amount == null) {
			return "0.000";
		}

		long value = amount.longValue();
		boolean isNegative = false;
		if (amount.longValue() < 0) {
			value = Math.abs(amount.longValue());
			isNegative = true;
		}
		StringBuffer channelAmount = new StringBuffer();
		long yuan = value / Constant.PRICE_UNIT;
		long cent = (value % Constant.PRICE_UNIT) / (Constant.PRICE_UNIT / 1000);

		channelAmount.append(yuan).append(".");
		if (cent < 10L) {
			channelAmount.append("00");
		} else if (cent < 100L && cent >= 10L) {
			channelAmount.append("0");
		}
		channelAmount.append(cent);
		if (isNegative && value / 10 != 0) {
			channelAmount.insert(0, '-');
		}
		return channelAmount.toString();
	}

	/**
	 * 将系统系统内数值金额转换为分为单位的字符串。小于分的部分不做四舍五入<br>
	 * 
	 * 一百元，1000000 -> 10000
	 * 
	 * @param amount
	 * @return
	 */
	public static String numericValueToFen(long amount) {
		return "" + amount / (Constant.PRICE_UNIT / 100);
	}

	/**
	 * 将元为单位的字符串金额转化为系统内部的数值金额<br>
	 * 例：<br>
	 * 1元3角4分5厘6毫转化为数值<br>
	 * <strong>1.3456 -> 13456</strong><br>
	 * <br>
	 * 3角4分转化为数值<br>
	 * <strong>0.34 -> 3400</strong><br>
	 * 
	 * @param amount
	 * @return
	 */
	public static long yuanToNumericValue(String amountStr) {
		if (StringUtils.isBlank(amountStr)) {
			return 0L;
		}
		StringBuffer amount = new StringBuffer();
		if (amountStr.startsWith(".")) {
			amount.append("0");
		}
		amount.append(amountStr);

		if (!amount.toString().matches(MONEY_REG)) {
			throw new ZqException("金额格式不正确:" + amountStr);
		}
		BigDecimal result = new BigDecimal(amount.toString()).multiply(new BigDecimal(10000L));
		if (isOverRangeOfLongValue(result)) {
			throw new ZqException("金额数额范围过大:" + amountStr);
		}
		return result.setScale(0, BigDecimal.ROUND_DOWN).longValueExact();
	}

	/**
	 * 支持数字期货金额转化，与上面yuanToNumericValue方法的区别在于金额的正则表达式
	 * 
	 * @param amountStr
	 * @return
	 */
	public static long yuanToNumericValueWithStdexFutrues(String amountStr) {
		if (StringUtils.isBlank(amountStr)) {
			return 0L;
		}
		StringBuffer amount = new StringBuffer();
		if (amountStr.startsWith(".")) {
			amount.append("0");
		}
		amount.append(amountStr);

		if (!amount.toString().matches(MONEY_REG_STDEX_FUTURES)) {
			throw new ZqException("金额格式不正确:" + amountStr);
		}
		BigDecimal result = new BigDecimal(amount.toString()).multiply(new BigDecimal(10000L));
		if (isOverRangeOfLongValue(result)) {
			throw new ZqException("金额数额范围过大:" + amountStr);
		}
		return result.setScale(0, BigDecimal.ROUND_DOWN).longValueExact();
	}

	/**
	 * 判断数字是否超过Long值的范围
	 * 
	 * @param result
	 * @return
	 */
	private static boolean isOverRangeOfLongValue(BigDecimal result) {
		BigDecimal longMaxValue = new BigDecimal(Long.MAX_VALUE);
		BigDecimal longMinValue = new BigDecimal(Long.MIN_VALUE);
		if (result.compareTo(longMaxValue) > 0 || result.compareTo(longMinValue) < 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Long amount = 37500L - 8500L;
		System.out.println(narrowByPrecision(amount, 4));
	}

	// 按照精度扩大相应倍数
	public static long marketYuanToNumericValueInPointWhitException(String amountStr, int scale, String rate, boolean isRate) {
		if (StringUtils.isBlank(amountStr)) {
			return 0L;
		}
		StringBuffer amount = new StringBuffer();
		if (amountStr.startsWith(".")) {
			amount.append("0");
		}
		amount.append(amountStr);

		if (!amountStr.matches(regex)) {
			return 0L;
		}

		long result = 0;
		if (scale != 0) {
			double multiplyValue = Math.pow(10, scale);
			BigDecimal a = new BigDecimal(amount.toString()).multiply(new BigDecimal(multiplyValue));
			result = a.setScale(0, BigDecimal.ROUND_DOWN).longValueExact();
		} else {
			String[] amounts = StringUtils.split(amount.toString(), ".", 2);
			result = NumberUtils.toLong(amounts[0]) * 10000L;
			boolean isNegative = result < 0L;
			if (amounts.length > 1) {
				int length = amounts[1].length();
				int temp = (int) (NumberUtils.toDouble(amounts[1]) * Math.pow(10, 4 - length));
				if (isNegative) {
					result = result - temp;
				} else {
					result = result + temp;
				}
			}

		}

		if (rate != null && isRate) {
			result = new BigDecimal(result).multiply(new BigDecimal(rate)).longValue();
		}

		return result;
	}

	public static long marketYuanToNumericValueInPointMore(String amountStr, int scale) {
		if (StringUtils.isBlank(amountStr)) {
			return 0L;
		}
		StringBuffer amount = new StringBuffer();
		if (amountStr.startsWith(".")) {
			amount.append("0");
		}
		amount.append(amountStr);
		// if (!amount.toString().matches(MONEY_REG)) {
		// throw new ZqException("传入金额格式不正确， amountStr=" + amount);
		// }
		long result = 0;
		if (scale != 0) {
			double multiplyValue = Math.pow(10, scale);
			BigDecimal a = new BigDecimal(amount.toString()).multiply(new BigDecimal(multiplyValue));
			result = a.setScale(0, BigDecimal.ROUND_DOWN).longValueExact();
		} else {
			String[] amounts = StringUtils.split(amount.toString(), ".", 2);
			result = NumberUtils.toLong(amounts[0]) * 10000L;
			boolean isNegative = result < 0L;
			if (amounts.length > 1) {
				int length = amounts[1].length();
				int temp = (int) (NumberUtils.toDouble(amounts[1]) * Math.pow(10, 4 - length));
				if (isNegative) {
					result = result - temp;
				} else {
					result = result + temp;
				}
			}

		}
		return result;
	}

	public static long yuanToNumericValueInPointMore(String amountStr) {
		if (StringUtils.isBlank(amountStr)) {
			return 0L;
		}
		StringBuffer amount = new StringBuffer();
		if (amountStr.startsWith(".")) {
			amount.append("0");
		}
		amount.append(amountStr);
		String[] amounts = StringUtils.split(amount.toString(), ".", 2);
		long result = NumberUtils.toLong(amounts[0]) * 10000L;
		boolean isNegative = result < 0L;
		if (amounts.length > 1) {
			int length = amounts[1].length();
			int temp = (int) (NumberUtils.toDouble(amounts[1]) * Math.pow(10, 4 - length));
			if (isNegative) {
				result = result - temp;
			} else {
				result = result + temp;
			}
		}
		return result;
	}

	// /**
	// * 股票份额转换
	// *
	// * @param amountStr
	// * @return
	// */
	// public static long convertStockAmount(String amountStr) {
	// if (StringUtils.isBlank(amountStr)) {
	// return 0L;
	// }
	// if (!amountStr.matches("^(-?\\d+)?")) {
	// throw new ZqException("渠道传入股票份额格式不正确， amountStr=" + amountStr);
	// }
	// BigDecimal bigDecimal = new BigDecimal(amountStr);
	// return bigDecimal.longValueExact();
	// }

	/**
	 * 计算涨跌幅，精确到百分比两位小数. <br>
	 * 如涨幅为9.85%，返回字符串"9.85"<br>
	 * 如涨幅为-9.85%，返回字符串"-9.85"<br>
	 * 
	 * @param basePrice
	 *            上一个交易日收盘价
	 * @param currentPrice
	 *            当前价格
	 * @return
	 */
	public static String getRateOfChange(long basePrice, long currentPrice) {
		if (basePrice == 0L) {
			return "0.000";
		}
		BigDecimal bigDecimal = new BigDecimal((currentPrice - basePrice) * 100).divide(new BigDecimal(basePrice), 3,
				BigDecimal.ROUND_HALF_UP);

		return AmountUtil.formatAmountNumber(bigDecimal, UNIT_PRICE);
	}

	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatAmountNumber(Number amount, String numberFormat) {
		if (numberFormat == null) {
			numberFormat = UNIT_PRICE;
		}
		if (amount == null) {
			return "0.000";
		}
		DecimalFormat df = new DecimalFormat(numberFormat);
		return df.format(amount);
	}

	public static String formatThreeDigitsDecimalPoint(Long amount) {
		if (amount == null) {
			return "0.000";
		}
		BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(Constant.PRICE_UNIT), 3,
				BigDecimal.ROUND_HALF_UP);
		return AmountUtil.formatAmountNumber(bigDecimal, UNIT_PRICE);
	}

	public static String formatFourDigitsDecimalPoint(Number amount) {
		if (amount == null) {
			return "0.0000";
		}
		BigDecimal bigDecimal = new BigDecimal(amount.toString()).divide(new BigDecimal(Constant.PRICE_UNIT), 4,
				BigDecimal.ROUND_HALF_UP);
		return AmountUtil.formatAmountNumber(bigDecimal, FOUR_DECIMALS);
	}

	public static String getCostPrice(String totalCostPrice, String multFactor, String totalCount, int precision) {
		if (AmountUtil.expandingTenThousands(totalCount) == 0L) {
			return "--";
		}
		return new BigDecimal(totalCostPrice).divide(new BigDecimal(totalCount).multiply(new BigDecimal(multFactor)),
				precision, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

	/**
	 * 扩大万倍
	 *
	 * @param data
	 * @return
	 */
	public static long expandingTenThousands(String data) {
		if (StringUtils.isBlank(data) || !NumberUtils.isNumber(data)) {
			return 0L;
		}
		BigDecimal result = new BigDecimal(data).multiply(new BigDecimal(TEN_THOUSAND));
		if (isOverRangeOfLongValue(result)) {
			throw new ZqException("数额范围过大:" + data);
		}
		return result.longValue();
	}

	/**
	 * 按精度扩大
	 *
	 * @param amount
	 * @param precision
	 * @return
	 */
	public static String expandingByPrecision(Number amount, int precision) {
		if (amount == null) {
			amount = 0L;
		}
		return new BigDecimal(amount.longValue()).multiply(new BigDecimal(Math.pow(10.0, precision))).toPlainString();
	}

	/**
	 * 缩小万倍，保留4位小数，从第5位小数位开始舍去
	 *
	 * @param data
	 * @return
	 */
	public static String narrowTenThousands(Number data) {
		BigDecimal bigDecimal = shrinkTenThousands(data);
		return bigDecimal.stripTrailingZeros().toPlainString();
	}

	public static BigDecimal shrinkTenThousands(Number data) {
		if (data == null || data.longValue() == 0L) {
			return new BigDecimal(0);
		}
		return new BigDecimal(data.toString()).divide(new BigDecimal(TEN_THOUSAND), 4, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 参数为null，则返回0L；否则返回原来数值
	 *
	 * @param data
	 * @return
	 */
	public static long nullToZero(Number data) {
		if (data == null) {
			return 0L;
		}
		return data.longValue();
	}

	/**
	 * 分单位转化为毫 如 123分 ---> 12300毫
	 */
	public static long fenToHao(String data) {
		if (StringUtils.isBlank(data) || !NumberUtils.isNumber(data)) {
			return 0L;
		}
		BigDecimal result = new BigDecimal(data).multiply(new BigDecimal(HUNDRED));
		if (isOverRangeOfLongValue(result)) {
			throw new ZqException("数额范围过大:" + data);
		}
		return result.longValue();
	}

	/**
	 * 扩大10^14倍
	 *
	 * @param data
	 * @return
	 */
	public static long expandingOneHundredTrillion(String data) {
		if (StringUtils.isBlank(data) || !NumberUtils.isNumber(data)) {
			return 0L;
		}
		BigDecimal result = new BigDecimal(data).multiply(new BigDecimal(ONE_HUNDRED_TRILLION));
		if (isOverRangeOfLongValue(result)) {
			throw new ZqException("数额范围过大:" + data);
		}
		return result.longValue();
	}

	/**
	 * 缩小10^14倍
	 *
	 * @param data
	 * @return
	 */
	public static String narrowOneHundredTrillion(Number data) {
		if (data == null || data.longValue() == 0L) {
			return "0";
		}
		BigDecimal bigDecimal = new BigDecimal(data.toString()).divide(new BigDecimal(ONE_HUNDRED_TRILLION), 14,
				BigDecimal.ROUND_DOWN);
		return bigDecimal.stripTrailingZeros().toPlainString();
	}

	public static Long stringToLong(String data) {
		if (data == null) {
			return null;
		}
		return Long.parseLong(data);
	}

}
