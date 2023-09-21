package com.jse.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 页面上根据时间段查询，解析开始时间和结束时间
 *
 */
public class TimeRangeUtil {
	private TimeRangeUtil() {
	}

	/**
	 * 
	 * @param beginDateSource
	 * @param endDateSource
	 * @param pattern
	 * @param defaultFallbackDays
	 *            如果beginDateSource或者endDateSource格式错误，或者beginDateSource晚于endDateSource，选取最近defaultFallbackDays天作为时间范围
	 * @return
	 */
	public static Date[] parse(String beginDateSource, String endDateSource, String pattern, int defaultFallbackDays) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = sdf.parse(beginDateSource);
			endDate = sdf.parse(endDateSource);
		} catch (ParseException e) {
			endDate = cal.getTime();
			cal.add(Calendar.DAY_OF_YEAR, -defaultFallbackDays);
			beginDate = cal.getTime();
			return new Date[] { beginDate, endDate };
		}

		// beginDate晚于endDate，默认选取最近defaultFallbackDays天
		if (beginDate.after(endDate)) {
			endDate = cal.getTime();
			cal.add(Calendar.DAY_OF_YEAR, -defaultFallbackDays);
			beginDate = cal.getTime();
			return new Date[] { beginDate, endDate };
		} else {
			// 结束的时间定在当天23:59:59
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.add(Calendar.SECOND, -1);
			endDate = cal.getTime();
			return new Date[] { beginDate, endDate };
		}
	}
}
