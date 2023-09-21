package com.jse.commons.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @company jse-zq
 * @author wei
 * @version CheckRuleUtils.java, v 0.1 2015年5月4日 下午10:00:00
 */
public class CheckRuleUtils {

	/**
	 * 预警检查
	 * 
	 * @param rate
	 * @param balanceSum
	 * @param creditAmount
	 * @return
	 */
	public static boolean checkWarning(long rate, long balanceSum, long creditAmount) {
		if (balanceSum < Math.abs(creditAmount) * rate / 100) {
			return true;
		}
		return false;
	}

	/**
	 * 平仓检查
	 * 
	 * @param rate
	 * @param balanceSum
	 * @param creditAmount
	 * @return
	 */
	public static boolean checkClosePoisition(long rate, long balanceSum, long creditAmount) {
		if (balanceSum < Math.abs(creditAmount) * rate / 100) {
			return true;
		}
		return false;
	}

	/**
	 * 特殊股票代码检查
	 * 
	 * @param stockName
	 * @param content
	 * @return
	 */
	public static boolean checkStockName(String stockName, String content) {
		if (StringUtils.isBlank(stockName) || StringUtils.isBlank(content)) {
			return false;
		}
		String[] checkStockNameArray = content.split(",");
		for (String checkStockName : checkStockNameArray) {
			if (stockName.contains(checkStockName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 特殊股票代码检查
	 * 
	 * @param stockName
	 * @param content
	 * @return
	 */
	public static boolean checkStockCodeInternal(String stockCodeInternal, String content) {
		if (StringUtils.isBlank(content)) {
			return false;
		}
		List<String> list = JSONArray.parseArray(content, String.class);
		if (list.contains(stockCodeInternal)) {
			return true;
		}
		return false;
	}

	/**
	 * 单支股票占总账户不超过设定的值
	 * 
	 * @param stockName
	 * @param content
	 * @return
	 */
	public static boolean checkSingleNoOverSetValue(long totalPrice, long balanceSum, long rate) {
		if (totalPrice > balanceSum * rate / 100)
			return true;
		return false;
	}

	public static boolean checkEntrepreneurialNoOverSetValue(long totalPrice, long balanceSum, long rate) {
		if (totalPrice > balanceSum * rate / 100)
			return true;
		return false;
	}

	public static boolean isNewStock(String stockName) {
		if (StringUtils.isBlank(stockName)) {
			return true;
		}
		return (stockName.startsWith("N") || stockName.startsWith("n"));
	}

	public static boolean isHSIFuturesNightTime(String stockCode) {

		if (stockCode.length() >= 3 && stockCode.substring(0, 3).equals("HSI")) {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			int hmsTime = Integer.parseInt(sdf.format(new Date()));
			if ((hmsTime >= 170000) && (hmsTime <= 234500)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isMHIFuturesNightTime(String stockCode) {
		if (stockCode.length() >= 3 && stockCode.substring(0, 3).equals("MHI")) {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			int hmsTime = Integer.parseInt(sdf.format(new Date()));
			if ((hmsTime >= 170000) && (hmsTime <= 234500)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isCNFuturesNightTime(String stockCode) {
		if (stockCode.length() >= 2 && stockCode.substring(0, 2).equals("CN")) {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			int hmsTime = Integer.parseInt(sdf.format(new Date()));
			if ((hmsTime >= 164000) || (hmsTime <= 20000)) {
				return true;
			}
		}

		return false;
	}

	// public static boolean isEntrepreneurialStock(String stockCode) {
	// if (StringUtils.isBlank(stockCode) || stockCode.length() != 6) {
	// return false;
	// }
	// return "300".equals(stockCode.substring(0, 3));
	// }

}
