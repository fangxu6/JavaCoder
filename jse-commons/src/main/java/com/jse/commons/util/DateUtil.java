/**
 * Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期公共类 提供与日期相关的公共方法，如日期格式化、字符串转为日期等。
 * 
 * @company jse-zq
 * @author dango
 * @since 1.0
 * @date 2015年3月22日 下午2:31:26
 */
public class DateUtil {
	private static ZqLogger		logger								= new ZqLogger(DateUtil.class);

	public static final String	DATE_FORMAT_YYYYMMDDHHMMSSMS		= "yyyyMMddHHmmssSSS";
	public static final String	DATE_FORMAT_YYYYMMDDHHMMSS			= "yyyyMMddHHmmss";
	public static final String	DATE_FORMAT_YYYYMMDDHHMMSS_ORACLE	= "yyyymmddHH24miss";
	public static final String	DATE_FORMAT_YYYYMMDDHHMM			= "yyyyMMddHHmm";
	public static final String	DATE_FORMAT_YYYYMMDDHH				= "yyyyMMddHH";
	public static final String	DATE_FORMAT_YYYYMMDD				= "yyyyMMdd";
	public static final String	DATE_FORMAT_YYYYMMDD_NEW			= "yyyy-MM-dd";
	public static final String	DATE_FORMAT_YYMMDD					= "yyMMdd";
	public static final String	DATE_FORMAT_YYYYMM					= "yyyyMM";
	public static final String	DATE_FORMAT_YYYY					= "yyyy";
	public static final String	DATE_FORMAT_EN_MMDDYYYY				= "MM/dd/yyyy";
	public static final String	DATE_FORMAT_EN_A_YYYYMMDDHHMMSS		= "yyyy/MM/dd HH:mm:ss";
	public static final String	DATE_FORMAT_EN_A_YYYYMMDDHHMM		= "yyyy/MM/dd HH:mm";
	public static final String	DATE_FORMAT_EN_A_YYYYMMDDHH			= "yyyy/MM/dd HH";
	public static final String	DATE_FORMAT_EN_A_YYYYMMDD			= "yyyy/MM/dd";
	public static final String	DATE_FORMAT_EN_A_YYYYMM				= "yyyy/MM";
	public static final String	DATE_FORMAT_EN_A_YYYY				= "yyyy";
	public static final String	DATE_FORMAT_EN_A_HHMM				= "HH:mm";
	public static final String	DATE_FORMAT_EN_A_MMDDHH				= "MM/dd HH";
	public static final String	DATE_FORMAT_EN_A_MMDDHHMM			= "MM/dd HH:mm";
	public static final String	DATE_FORMAT_EN_A_MMDD				= "MM/dd";
	public static final String	DATE_FORMAT_EN_A_MM					= "MM";
	public static final String	DATE_FORMAT_EN_B_YYYYMMDDHHMMSS		= "yyyy-MM-dd HH:mm:ss";
	public static final String	DATE_FORMAT_EN_B_YYYYMMDDHHMM		= "yyyy-MM-dd HH:mm";
	public static final String	DATE_FORMAT_EN_B_YYYYMMDDHH			= "yyyy-MM-dd HH";
	public static final String	DATE_FORMAT_EN_B_YYYYMMDD			= "yyyy-MM-dd";
	public static final String	DATE_FORMAT_EN_B_YYYYMM				= "yyyy-MM";
	public static final String	DATE_FORMAT_EN_B_YYYY				= "yyyy";
	public static final String	DATE_FORMAT_CN_YYYYMMDDHHMMSS		= "yyyy'年'MM'月'dd'日' HH'时'mm'分'ss'秒'";
	public static final String	DATE_FORMAT_CN_YYYYMMDDHHMM			= "yyyy'年'MM'月'dd'日' HH'时'mm'分'";
	public static final String	DATE_FORMAT_CN_YYYYMMDDHH			= "yyyy'年'MM'月'dd'日' HH'时'";
	public static final String	DATE_FORMAT_CN_YYYYMMDD				= "yyyy'年'MM'月'dd'日'";
	public static final String	DATE_FORMAT_CN_YYYYMM				= "yyyy'年'MM'月'";
	public static final String	DATE_FORMAT_CN_YYYY					= "yyyy'年'";
	public static final int		DATE								= Calendar.DATE;
	public static final int		MONTH								= Calendar.MONTH;
	public static final int		YEAR								= Calendar.YEAR;
	public static final int		HOUR_OF_DAY							= Calendar.HOUR_OF_DAY;
	public static final int		MINUTE								= Calendar.MINUTE;
	public static final int		SECOND								= Calendar.SECOND;
	public static final String	MATCH_YEAR_4						= "[0-9]{4}";
	public static final String	MATCH_YEAR_2						= "[0-9]{2}";
	public static final String	MATCH_MONTH							= "(0[0-9]|1[0-2])";
	public static final String	MATCH_DAY							= "(0[1-9]|1[0-9]|2[0-9]|3[0-1])";
	public static final String	MATCH_HOUR							= "([0-1][0-9]|2[0-3])";
	public static final String	MATCH_MINUTES						= "[0-5][0-9]";
	public static final String	MATCH_SECOND						= "[0-5][0-9]";
	public static final String	DATE_FORMAT_NGINX_TIME_LOCAL		= "dd/MMM/yyyy':'HH:mm:ss Z";
	public static final String	DATE_FORMAT_HHMMSS					= "HH:mm:ss";
	public static final String	DATE_FORMAT_YYMMDDHHMMSSMS			= "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String	DATE_FORMAT_HHMMSSMS				= "HH:mm:ss.SSS";

	private DateUtil() {

	}

	public static Date currentDate() {
		return new Date();
	}

	/**
	 * 将毫秒级日期字符串转化为14位日期格式字符串输出：yyyyMMddHHmmss
	 */
	public static String formatDateFromMillisecond(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
		Date date = new Date(Long.valueOf(dateStr));
		return format.format(date).toString();
	}

	/**
	 * 得到当前日期字符串,根据传入的格式返回
	 * 
	 * @return
	 */
	public static String currentString(String format) {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 获取距离今天offset日的日期
	 *
	 * @param format
	 * @param offset
	 * @return
	 */
	public static String TodayByoffsetString(String format, int offset) {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date(date.getTime() + offset * 24L * 60 * 60 * 1000));
	}

	/**
	 * 得到当前日期字符串,yyyymmdd格式
	 * 
	 * @return
	 */
	public static String currentString8() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
		return formatter.format(date);
	}

	/**
	 * 得到当前日期字符串,yyyy-mm-dd格式
	 * 
	 * @return
	 */
	public static String currentString10() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDD);
		return formatter.format(date);
	}

	/**
	 * 得到当前日期字符串,yyyymmddhhmmssSSS格式
	 * 
	 * @return
	 */
	public static String currentString17() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSSMS);
		return formatter.format(date);
	}

	/**
	 * 得到当前日期字符串,yyyymmddhhmmss格式
	 * 
	 * @return
	 */
	public static String currentString14() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
		return formatter.format(date);
	}

	/**
	 * 得到当前日期字符串,yyyy-mm-dd hh:mm:ss
	 * 
	 * @return
	 */
	public static String currentString19() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDDHHMMSS);
		return formatter.format(date);
	}

	/**
	 * 得到目标日期字符串,DATE_FORMAT_CN_YYYYMMDDHHMMSS
	 * 
	 * @return
	 */
	public static String time2StringCN(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_CN_YYYYMMDDHHMMSS);
		return formatter.format(date);
	}

	public static String time2String(long time, String format) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 返回格式DATE_FORMAT_EN_B_YYYYMMDDHHMMSS
	 *
	 * @param time
	 * @return
	 */
	public static String toTimeStamps(long time) {
		Date date = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDDHHMMSS);
		return formatter.format(date);
	}

	/**
	 * 转换时间日期格式字串为long型
	 * 
	 * @param time
	 *            格式为：yyyy-MM-dd HH:mm:ss的时间日期类型
	 */
	public static Long convertTimeToLong(String time) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDDHHMMSS);
			date = sdf.parse(time);
			return date.getTime();
		} catch (Exception e) {
			logger.error("日期格式解析发生错误！", e);
			return null;
		}
	}

	public static Date getCutoffNow(int cutOffType, int cutOffNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate());
		cal.add(cutOffType, cutOffNum);
		return cal.getTime();
	}

	public static Date getCutoffDate(int cutOffType, int cutOffNum, Date pos) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pos);
		cal.add(cutOffType, cutOffNum);
		return cal.getTime();
	}

	public static String getCutoffNow(int cutOffType, int cutOffNum, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate());
		cal.add(cutOffType, cutOffNum);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(cal.getTime());
	}

	public static String getCutoffDate(int cutOffType, int cutOffNum, Date pos, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(pos);
		cal.add(cutOffType, cutOffNum);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(cal.getTime());
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 得到指定日期后指定的天数后的日期
	 * 
	 * @param strDate
	 *            String 指定日期，格式:yyyMMdd
	 * @param nDelayDays
	 *            int 指定天数,
	 * @return String 指定日期，格式:yyyMMdd
	 */
	public static String getDelayDayStr(String strDate, int nDelayDays) {
		if (strDate.length() != 8) {
			return "";
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
			formatter.setLenient(false);
			Date date = formatter.parse(strDate + "000000");
			Date datetmp = new Date(date.getTime() + nDelayDays * 24L * 60 * 60 * 1000);
			return formatter.format(datetmp).substring(0, 8);
		} catch (Exception e) {
			logger.debug("DateUtil", "getDelayDayStr", "日期操作出错:" + e.getMessage());
			return "";
		}
	}

	/**
	 * 得到指定日期后指定的月数后的月份
	 * 
	 * @param strMon
	 *            String 指定月份 格式:yyyyMM
	 * @param nDelayDays
	 *            int 指定月份数
	 * @return String 指定月份数 格式:yyyyMM
	 */
	public static String getDelayMonStr(String strMon, int nDelayDays) {
		if (strMon.length() != 6) {
			return "";
		}
		if (nDelayDays < 0) {
			logger.debug("DateUtil", "getDelayMonStr", "Common.getDelayMonStr()不能输入负数的天数");
			return "";
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
			formatter.setLenient(false);
			Date date = formatter.parse(strMon + "01000000");
			Date datetmp = new Date(date.getTime() - 1000L * nDelayDays * 24 * 60 * 60);
			return formatter.format(datetmp).substring(0, 6);
		} catch (Exception e) {
			logger.debug("DateUtil", "getDelayMonStr", "月份操作出错:" + e.getMessage());
			return "";
		}
	}

	/**
	 * 根据日期模式，把字符串型得日期转换成JAVA的日期
	 * 
	 * @param dateStr
	 *            日期
	 * @param format
	 *            模式
	 * @return java型的日期
	 */
	public static Date getFormatDate(String dateStr, String format) {
		Date date = null;
		if ((dateStr == null || "".equals(dateStr)) || (format == null || "".equals(format))) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException pe) {
			logger.error(pe, pe);
			return null;
		}
		return date;
	}

	/**
	 * 默认使用比较常用的YYYYMMDDHHMMSS模式来转换日期
	 * 
	 * @return
	 */
	public static String getFormatDate(Date date, String formatStr) {
		try {
			if (date == null) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.format(date);
		} catch (Exception ex) {
			logger.error(ex, ex);
			return null;
		}
	}

	/**
	 * 对输入的日期字符串进行格式化.
	 * 
	 * @param strDate
	 *            需要进行格式化的日期，格式为前面定义的 DATE_FORMAT_YYYYMMDDHHMMSS
	 * @return 经过格式化后的字符串
	 */
	public static Date getFormattedDate(String strDate) {
		String formatStr = DATE_FORMAT_YYYYMMDD;
		if (strDate == null || "".equals(strDate.trim())) {
			return null;
		}
		switch (strDate.trim().length()) {
		case 6:
			if ("0".equals(strDate.substring(0, 1))) {
				formatStr = DATE_FORMAT_YYMMDD;
			} else {
				formatStr = DATE_FORMAT_YYYYMM;
			}
			break;
		case 8:
			formatStr = DATE_FORMAT_YYYYMMDD;
			break;
		case 10:
			if (strDate.indexOf('-') == -1) {
				formatStr = DATE_FORMAT_EN_A_YYYYMMDD;
			} else {
				formatStr = DATE_FORMAT_EN_B_YYYYMMDD;
			}
			break;
		case 11:
			if (strDate.getBytes().length == 14) {
				formatStr = "yyyy年MM月dd日";
			} else {
				return null;
			}
			break;
		case 14:
			formatStr = DATE_FORMAT_YYYYMMDDHHMMSS;
			break;
		case 17:
			formatStr = "yyyyMMdd HH:mm:ss";
			break;
		case 19:
			if (strDate.indexOf('-') == -1) {
				formatStr = DATE_FORMAT_EN_A_YYYYMMDDHHMMSS;
			} else {
				formatStr = DATE_FORMAT_EN_B_YYYYMMDDHHMMSS;
			}
			break;
		default:
			return null;
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			return formatter.parse(strDate);
		} catch (Exception e) {
			logger.debug("DateUtil", "getFormattedDate", "转换日期字符串格式时出错;" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 对输入的日期字符串进行格式化.
	 * 
	 * @param strDate
	 *            需要进行格式化的日期，格式为前面定义的 DATE_FORMAT_YYYYMMDDHHMMSS
	 * @param strFormatTo
	 *            指定采用何种格式进行格式化操作
	 * @return 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate, String strFormatTo) {
		String formatStr = DATE_FORMAT_YYYYMMDD;
		if (strDate == null || "".equals(strDate.trim())) {
			return "";
		}
		switch (strDate.trim().length()) {
		case 6:
			if ("0".equals(strDate.substring(0, 1))) {
				formatStr = DATE_FORMAT_YYMMDD;
			} else {
				formatStr = DATE_FORMAT_YYYYMM;
			}
			break;
		case 8:
			formatStr = DATE_FORMAT_YYYYMMDD;
			break;
		case 10:
			if (strDate.indexOf('-') == -1) {
				formatStr = DATE_FORMAT_EN_A_YYYYMMDD;
			} else {
				formatStr = DATE_FORMAT_EN_B_YYYYMMDD;
			}
			break;
		case 11:
			if (strDate.getBytes().length == 14) {
				formatStr = "yyyy年MM月dd日";
			} else {
				return "";
			}
			break;
		case 14:
			formatStr = DATE_FORMAT_YYYYMMDDHHMMSS;
			break;
		case 19:
			if (strDate.indexOf('-') == -1) {
				formatStr = DATE_FORMAT_EN_A_YYYYMMDDHHMMSS;
			} else {
				formatStr = DATE_FORMAT_EN_B_YYYYMMDDHHMMSS;
			}
			break;
		default:
			return strDate.trim();
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(strDate));
			formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			logger.debug("DateUtil", "getFormattedDate", "转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}

	/**
	 * 得到指定日期 yyyyMMddHHmmss 后 指定秒的数据
	 * 
	 * @param strNowDay
	 * @param nDelaySeconds
	 * @return
	 */
	public static String getDelaySecondStr(String strNowDay, int nDelaySeconds) {
		if (strNowDay.length() != 14) {
			return "";
		}
		if (nDelaySeconds < 0) {
			logger.debug("DateUtil", "getDelaySecondStr", "Common.getDelayMonStr()不能输入负数的天数");
			return "";
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
			formatter.setLenient(false);
			Date date = formatter.parse(strNowDay);
			Date datetmp = new Date(date.getTime() + nDelaySeconds * 1000);
			return formatter.format(datetmp);
		} catch (Exception e) {
			logger.debug("DateUtil", "getDelaySecondStr", "操作出错:" + e.getMessage());
			return "";
		}
	}

	public static int getDateField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	public static Date getFirstDateAtMonth(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.DATE, time.getActualMinimum(Calendar.DAY_OF_MONTH));
		return time.getTime();
	}

	/*
	 * 指定时间延后指定小时后的时间
	 */
	public static Date getDateDelayHours(Date date, int hour) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY) + hour);
		return time.getTime();
	}

	public static Date getLastDateAtMonth(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.DATE, time.getActualMaximum(Calendar.DAY_OF_MONTH));
		return time.getTime();
	}

	public static Date getLastDateAtWeek(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.set(Calendar.DATE, time.getActualMaximum(Calendar.DAY_OF_WEEK));
		return time.getTime();
	}

	/**
	 * 把 YYYY/MM/DD格式的日期转换成 yyyymmdd型的数字日期
	 * 
	 * @param strDate
	 *            String
	 * @return int
	 */
	public static int getSpecDate(String strDate) {
		String year = strDate.substring(0, strDate.indexOf('/'));
		String month = strDate.substring(strDate.indexOf('/') + 1, strDate.lastIndexOf('/'));
		String day = strDate.substring(strDate.lastIndexOf('/') + 1);
		return Integer.parseInt(year) * 10000 + Integer.parseInt(month) * 100 + Integer.parseInt(day);
	}

	/**
	 * 得到指定日期 yyyyMMddHHmmss 前后 指定天的数据
	 * 
	 * @param strNowDay
	 * @param nDelayDays
	 * @return
	 */
	public static String getSpecDayStr(String strNowDay, int nDelayDays) {
		String loStrNowDay = strNowDay;
		if (loStrNowDay.length() != 14) {
			loStrNowDay = getFormattedDate(loStrNowDay, DATE_FORMAT_YYYYMMDDHHMMSS);
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
			formatter.setLenient(false);
			Date date = formatter.parse(loStrNowDay);
			Date datetmp = new Date(date.getTime() + nDelayDays * 24L * 60 * 60 * 1000);
			return formatter.format(datetmp);
		} catch (Exception e) {
			logger.debug("DateUtil", "getSpecDayStr", "操作出错:" + e.getMessage());
			return "";
		}
	}

	/**
	 * 日期时间合法性校验
	 * 
	 * @param srcData
	 * @param formatStr
	 * @return
	 */
	public static boolean checkDate(String srcData, String formatStr) {
		if (srcData == null || "".equals(srcData)) {
			return false;
		}
		try {
			SimpleDateFormat formate = new SimpleDateFormat(formatStr);
			formate.setLenient(false);
			formate.parse(srcData);
			return true;
		} catch (Exception e) {
			logger.error("日期格式解析发生错误！", e);
			return false;
		}
	}

	/**
	 * 日期转为秒数(日期的毫秒数除1000)
	 */
	public static int toSecond(Date date) {
		if (date == null) {
			return 0;
		}
		long second = date.getTime() / 1000;
		return second > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) second;
	}

	/**
	 * 日期转为秒数(日期的毫秒数除1000)
	 */
	public static int toSecond(String dateStr) {
		Date date = DateUtil.getFormattedDate(dateStr);
		if (date == null) {
			return 0;
		}
		long second = date.getTime() / 1000;
		return second > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) second;
	}

	/**
	 * @Description: 获取特定偏移量的日期
	 */
	public static Date offsetDate(Date date, int type, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, amount);
		return calendar.getTime();
	}

	/**
	 * 偏移秒 秒偏移N 比如当前是2012-01-05 06:01:01,移2秒，则是2012-01-05 06:01:03
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetSecond(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, amount);
		return calendar.getTime();

	}

	/**
	 * 偏移小时 小时偏移N 比如当前是2012-01-05 06:01:01,移2个小时，则是2012-01-05 06:03:01
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetHour(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, amount);
		return calendar.getTime();

	}

	/**
	 * 偏移动态天 日期直接偏移N天 比如当前是2012-01-05 06:01:01,移2个动态天，则是2012-01-07 06:01:01
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 偏移动态周 周直接偏移N周 比如当前是2012-01-05 06:01:01(周四),偏移2个动态周(即下下周)，则是2012-01-19 06:01:01(周四)
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetWeek(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEEK_OF_MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 偏移动态月 月份直接偏移N月 比如当前是2012-01-05 06:01:01,偏移2个动态月，则是2012-03-05 06:01:01
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 偏移动态年 年份直接偏移N年 比如当前是2012-01-05 06:01:01,偏移2个动态年，则是2014-01-05 06:01:01
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date offsetYear(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, amount);
		return calendar.getTime();
	}

	/**
	 * @Description: 计算两日期的间隔天数
	 */
	public static Long getBetweenDays(Date startDate, Date endDate) {
		Long days = 0L;

		DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_EN_B_YYYYMMDD);
		Calendar cb = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		String begin = null;
		String end = null;
		boolean flag = false;
		if (startDate.after(endDate)) {
			begin = formatDate(endDate, DateUtil.DATE_FORMAT_EN_B_YYYYMMDD);
			end = formatDate(startDate, DateUtil.DATE_FORMAT_EN_B_YYYYMMDD);
			flag = true;
		} else {
			begin = formatDate(startDate, DateUtil.DATE_FORMAT_EN_B_YYYYMMDD);
			end = formatDate(endDate, DateUtil.DATE_FORMAT_EN_B_YYYYMMDD);
		}

		try {
			cb.setTime(df.parse(begin));
			ce.setTime(df.parse(end));

			while (cb.before(ce)) {
				days++;
				cb.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException pe) {
			logger.error("日期格式解析发生错误！", pe);
		}

		if (flag) {
			return -days;
		}
		return days;
	}

	/**
	 * @Description: 计算两日期的大小
	 */
	public static boolean compare2Day(Date startDate, Date endDate) {
		Calendar cb = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		boolean flag = false;

		cb.setTime(startDate);
		ce.setTime(endDate);

		if (cb.before(ce)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * @Description: 判断两日期是否相等
	 */
	public static boolean isTwoDateEqual(Date startDate, Date endDate) {
		Calendar cb = Calendar.getInstance();
		Calendar ce = Calendar.getInstance();
		boolean flag = false;

		cb.setTime(startDate);
		ce.setTime(endDate);

		if (cb.equals(ce)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 对象表示的月份中的某一天 2012-10-11
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取此Date对象表示的周中的某一天，星期一为1
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return week != 0 ? week : 7;
	}

	/**
	 * 比如今天是周三,本函数获取的时间时候周日的23点59分59秒
	 * 
	 * @return
	 */
	public static Date getLastDateAtWeek() {
		Calendar calendar = Calendar.getInstance();
		int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK); // 获取周开始基准
		int current = calendar.get(Calendar.DAY_OF_WEEK); // 获取当天周内天数
		calendar.add(Calendar.DAY_OF_WEEK, min - current); // 当天-基准，获取周开始日期
		// 如果配置了周六是最后一天，那一周的最后一天就是周六的23点59分59秒
		// 如果没有配置，则默认是周日的23点59分59秒
		calendar.add(Calendar.DAY_OF_WEEK, 6); // 开始+6，获取周结束日期
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取两个时间之间的秒数差 正常情况下大于0 参数非法返回-1L
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getBetweenSeconds(Date startDate, Date endDate) {
		if (startDate == null || endDate == null)
			return -1L;

		long startSec = startDate.getTime();
		long endSec = endDate.getTime();
		long interval = Math.abs(startSec - endSec) / 1000;

		return interval;
	}

	/**
	 * 日期格式转换成毫秒级字符串
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String formateDateStr2Mins(String dateStr) {
		String formatStr = "yyyyMMdd";
		if (dateStr == null || dateStr.trim().equals("")) {
			return "";
		}
		switch (dateStr.trim().length()) {
		case 6:
			formatStr = "yyMMdd";
			break;
		case 8:
			formatStr = "yyyyMMdd";
			break;
		case 10:
			if (dateStr.indexOf('-') == -1) {
				formatStr = "yyyy/MM/dd";
			} else {
				formatStr = "yyyy-MM-dd";
			}
			break;
		case 14:
			formatStr = "yyyyMMddHHmmss";
			break;
		case 19:
			if (dateStr.indexOf('-') == -1) {
				formatStr = "yyyy/MM/dd HH:mm:ss";
			} else {
				formatStr = "yyyy-MM-dd HH:mm:ss";
			}
			break;
		default:
			return dateStr.trim();
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(dateStr));
			Date date = calendar.getTime();
			return Long.valueOf(date.getTime()).toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取昨天，格式为yyyyMMdd
	 * 
	 * @return
	 */
	public static Integer getLastDateInt() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
		return Integer.parseInt(formatter.format(calendar.getTime()));
	}

	/**
	 * 获取昨天，格式为yyyyMMdd
	 * 
	 * @return
	 */
	public static String getLastDateStr8() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取昨天，格式为yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getLastDateStr10() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDD);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获取昨天开始的时间戳
	 * 
	 * @return
	 */
	public static long getStartOfLastday() {
		Calendar nowDate = new GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.add(Calendar.DAY_OF_MONTH, -1);
		return nowDate.getTimeInMillis();
	}

	/**
	 * 获取当日0点0分0秒0毫秒的毫秒数
	 * 
	 * @return
	 */
	public static long getStartOfToday() {
		Calendar nowDate = Calendar.getInstance();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis();
	}

	/*****
	 * 根据给定字符串获取当天开始时间的毫秒数
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long getStartOfToday(String dateStr) {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_NEW);
		Calendar nowDate = null;
		try {
			Date dateTime = format.parse(dateStr);
			nowDate = Calendar.getInstance();
			nowDate.setTime(dateTime);
			nowDate.set(Calendar.HOUR_OF_DAY, 0);
			nowDate.set(Calendar.MINUTE, 0);
			nowDate.set(Calendar.SECOND, 0);
			nowDate.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			logger.error(e, "dateStr:" + dateStr);
			return 0;
		}
		return nowDate.getTimeInMillis();
	}

	public static long getStartOfDay(String dateStr, String dateFormat) {
		DateFormat format = new SimpleDateFormat(dateFormat);
		Calendar nowDate = null;
		try {
			Date dateTime = format.parse(dateStr);
			nowDate = Calendar.getInstance();
			nowDate.setTime(dateTime);
			nowDate.set(Calendar.HOUR_OF_DAY, 0);
			nowDate.set(Calendar.MINUTE, 0);
			nowDate.set(Calendar.SECOND, 0);
			nowDate.set(Calendar.MILLISECOND, 0);
		} catch (Exception e) {
			return 0;
		}
		return nowDate.getTimeInMillis();
	}

	/**
	 * 根据给定日期获取当天开始时间
	 * 
	 * @param date
	 *            给定日期
	 * @return Date
	 */
	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 根据给定字符串获取本日24点的时间戳
	 *
	 * @return
	 */
	public static long getEndOfToday(String dateStr) {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_NEW);
		Calendar nextDay = new GregorianCalendar();
		try {
			Date dateTime = format.parse(dateStr);
			nextDay.setTime(dateTime);
			nextDay.set(Calendar.HOUR_OF_DAY, 0);
			nextDay.set(Calendar.MINUTE, 0);
			nextDay.set(Calendar.SECOND, 0);
			nextDay.set(Calendar.MILLISECOND, 0);
			nextDay.add(Calendar.DAY_OF_MONTH, 1);
		} catch (Exception e) {
			return 0;
		}
		return nextDay.getTimeInMillis();
	}

	/**
	 * 获取昨天结束的时间戳
	 * 
	 * @return
	 */
	public static long getEndOfLastday() {
		Calendar nowDate = new GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		return nowDate.getTimeInMillis();
	}

	/**
	 * 获取N个月后的时间戳（到N个月后的本日24点）
	 * 
	 * @return
	 */
	public static long getTimestampOfNextSeveralMonths(int months) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.add(Calendar.DAY_OF_MONTH, 1);
		nowDate.add(Calendar.MONTH, months);
		return nowDate.getTimeInMillis();
	}

	/**
	 * 获取N个月后的时间戳（到N个月后的本日24点）
	 * 
	 * @return
	 */
	public static long getTimestampOfNextDays(long days) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		int dayNum = (int) days + 1;
		nowDate.add(Calendar.DAY_OF_MONTH, dayNum);
		return nowDate.getTimeInMillis();
	}

	/**
	 * 获取N个月后的时间戳（到N个月后的本日24点）
	 * 
	 * @return
	 */
	public static long getTimestampOfLastWeek(long days) {
		Calendar nowDate = new GregorianCalendar();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		nowDate.set(Calendar.MILLISECOND, 0);
		nowDate.add(Calendar.WEEK_OF_MONTH, -1);
		nowDate.add(Calendar.DAY_OF_MONTH, 0);
		return nowDate.getTimeInMillis();
	}

	/**
	 * 获取本日24点的时间戳
	 * 
	 * @return
	 */
	public static long getEndOfToday() {
		Calendar nextDay = new GregorianCalendar();
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.set(Calendar.MILLISECOND, 0);
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		return nextDay.getTimeInMillis();
	}

	/**
	 * 获取N个月的天数（从第二天到N个月后的本日24点）
	 * 
	 * @return
	 */
	public static long getDaysOfNextSeveralMonths(int months) {
		Calendar nextDay = new GregorianCalendar();
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.set(Calendar.MILLISECOND, 0);
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		long nextDate = nextDay.getTimeInMillis();

		Calendar objectDay = new GregorianCalendar();
		objectDay.set(Calendar.HOUR_OF_DAY, 0);
		objectDay.set(Calendar.MINUTE, 0);
		objectDay.set(Calendar.SECOND, 0);
		objectDay.set(Calendar.MILLISECOND, 0);
		objectDay.add(Calendar.DAY_OF_MONTH, 1);
		objectDay.add(Calendar.MONTH, months);
		long objectDate = objectDay.getTimeInMillis();
		long betweenDays = (objectDate - nextDate) / (1000 * 3600 * 24);

		return betweenDays;
	}

	/**
	 * 前一天
	 * 
	 * @return
	 */
	public static Date previousDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取当前月份，格式为yyyyMM
	 * 
	 * @return
	 */
	public static Integer getCurrentMonthInt() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMM);
		return Integer.parseInt(formatter.format(date));
	}

	/**
	 * 获取当前月份，格式为yyyyMM
	 * 
	 * @return
	 */
	public static String getCurrentMonthStr6() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMM);
		return formatter.format(date);
	}

	/**
	 * 获取当前月份，格式为yyyy-MM
	 * 
	 * @return
	 */
	public static String getCurrentMonthStr7() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMM);
		return formatter.format(date);
	}

	/**
	 * 获取当前年，格式为yyyy
	 * 
	 * @return
	 */
	public static Integer getCurrentYearInt() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY);
		return Integer.parseInt(formatter.format(date));
	}

	/**
	 * 获取当前年，格式为yyyy
	 * 
	 * @return
	 */
	public static String getCurrentYearStr() {
		Date date = currentDate();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY);
		return formatter.format(date);
	}

	/**
	 * 判断是否为最后一天
	 * 
	 * @return
	 */
	public static boolean isExpiring(long expireTime) {
		long now = System.currentTimeMillis();
		if (expireTime == 0 || expireTime <= now)
			return false;
		return (expireTime - now) < (1000 * 3600 * 24);
	}

	/**
	 * 判断是否已经过期
	 * 
	 * @return
	 */
	public static boolean isOverdue(long expireTime) {
		long now = System.currentTimeMillis();
		return expireTime <= now;
	}

	/**
	 * 转换日期字符串,得到yyyy-mm-dd HH mm ss格式
	 * 
	 * @return
	 */
	public static String convertTimeStampToString(long timestamp) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_EN_B_YYYYMMDDHHMMSS);
		return formatter.format(timestamp);
	}

	/**
	 * 转换日期字符串,得到yyyy-mm-dd格式
	 * 
	 * @return
	 */
	public static String convertTimeStampToString10(long timestack) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_NEW);
		return formatter.format(timestack);
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfCurrYear() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfCurrYear() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/****
	 * 在给定时间的基础上再加一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {
		Date value = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		value = new Date(calendar.getTimeInMillis());
		return value;
	}

	/**
	 * 字符串时间转成毫秒
	 *
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static long stringToLong(String date, String format) {
		if (StringUtils.isBlank(date) || StringUtils.isBlank(format)) {
			return 0L;
		}
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(date).getTime();
		} catch (ParseException e) {
			logger.error("DateUtil", "converToLong", "日期【" + date + "】，格式【" + format + "】，转换出错！");
			return 0L;
		}
	}

	/**
	 * 判断两个时间是不是属于同一周
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeek(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		// 同一年和相邻的年份都要考虑
		if (subYear == 0) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 获取两个季度前的最后一天,格式为yyyy-mm-dd <sample>当前日期为2016-05-14,则输出为2015-12-31</sample>
	 *
	 * @return
	 */
	public static String getLastDayOfPreviousHalfYear() {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYYMMDD_NEW);
		return formatter.format(getDateOfPreviousHalfYear());
	}

	/**
	 * 获取两个季度前的月份,格式为yyyymm <sample>当前日期为2016-05-14,则输出为201512</sample>
	 *
	 * @return
	 */
	public static String getLastMonthOfPreviousHalfYear() {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYYYMM);
		return formatter.format(getDateOfPreviousHalfYear());
	}

	public static Date getDateOfPreviousHalfYear() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) - 6;
		int year = calendar.get(Calendar.YEAR);
		if (month < Calendar.JANUARY) {
			month = Calendar.DECEMBER;
			year = year - 1;
		} else {
			month = Calendar.JUNE;
		}
		calendar.set(year, month, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static boolean isToday(Long date) {
		if (date == null) {
			return false;
		}
		Date targetDate = new Date(date);
		Date beginOfToday = getStartOfDay(new Date(System.currentTimeMillis()));
		Date endOfTody = new Date(24 * 60 * 60 * 1000 + beginOfToday.getTime());
		return targetDate.after(beginOfToday) && targetDate.before(endOfTody);
	}

	/**
	 * 获取当月的第一天0点
	 */
	public static Date getBeginOfThisMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取下个月的第一天0点
	 */
	public static Date getBeginOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static boolean isThisMonth(Long date) {
		if (date == null) {
			return false;
		}
		Date targetDate = new Date(date);
		Date beginOfThisMonth = getBeginOfThisMonth();
		Date beginOfNextMonth = getBeginOfNextMonth();
		return targetDate.after(beginOfThisMonth) && targetDate.before(beginOfNextMonth);
	}

	/**
	 * 超过24:00:00，返回null
	 */
	public static String formatterHHmmss(long time) {
		long maxTime = 24L * 60L * 60L * 1000L;
		if (time == maxTime) {
			return "24:00:00";
		}
		if (maxTime < time) {
			return null;
		}
		long startToday = DateUtil.getStartOfToday();
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_HHMMSS);
		return formatter.format(new Date(startToday + time));
	}

	/**
	 * 格式为yyyy-MM-dd HH:mm:ss.SSS 的字符串获取毫秒值
	 */
	public static Long getTimeWithDateStr(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_YYMMDDHHMMSSMS);
		Date date;
		try {
			date = formatter.parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			logger.error("解析时间错误:" + dateStr, e);
		}
		return null;
	}

	public static Date getDateOfMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return cal.getTime();
	}

	public static Long getEndOfMinute(Long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MINUTE, 1);
		return cal.getTimeInMillis();
	}

	public static Long getStartOfMinute(Long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
}
