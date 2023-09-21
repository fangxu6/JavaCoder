/**
 * Copyright (c) 2015, jse-zq. All rights reserved.
 */
package com.jse.commons.util;

import com.zhuanqian.commons.log.ZqLogger;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * 日志工具类
 * 
 * @company jse-zq
 * @author dango
 * @since 1.0
 * @date 2015年3月22日 下午3:44:30
 */
public class LogUtil {
	/**
	 * 当前是否支持debug级别日志输出
	 */
	private static final boolean	DEBUGENABLED;

	/**
	 * 当前是否支持info级别日志输出
	 */
	private static final boolean	INFOENABLED;

	static {
		// 统一一次初始化
		Logger logger = LoggerFactory.getLogger(LogUtil.class);
		DEBUGENABLED = logger.isDebugEnabled();
		INFOENABLED = logger.isInfoEnabled();
	}

	private LogUtil() {

	}

	public static boolean isDebugEnabled() {
		return DEBUGENABLED;
	}

	public static boolean isInfoEnabled() {
		return INFOENABLED;
	}

	public static String buildTime(long start, long end) {
		StringBuilder sb = new StringBuilder();
		sb.append(",cost time[ms]:").append(end - start);
		return sb.toString();
	}

	public static String parse(Object object, int lay, String excludeReg, HashSet<Object> linkedParent) {
		HashSet<Object> linked = null;
		if (linkedParent == null) {
			linked = new HashSet<Object>();
		} else {
			linked = new HashSet<Object>(linkedParent);
		}
		Class<?> clazz = object.getClass();
		if (!ClassUtil.isComplex(clazz)) {
			return object.toString();// 对象本身是简单类型，则直接打印
		}

		if (linked.contains(object)) {
			return CommonUtil.getPrefixBlank(lay) + "[referenced]";
		}

		linked.add(object);

		if (object instanceof Collection) {
			return parseCollection((Collection<?>) object, lay, excludeReg, linked);
		} else if (object instanceof Map) {
			return parseMap((Map<?, ?>) object, lay, excludeReg, linked);
		} else if (clazz.isArray()) {
			Object[] arr = null;
			Class<?> cmpType = clazz.getComponentType();
			if (cmpType == long.class) {
				arr = CommonUtil.parse2ObjectArray((long[]) object);
			} else if (cmpType == int.class) {
				arr = CommonUtil.parse2ObjectArray((int[]) object);
			} else if (cmpType == short.class) {
				arr = CommonUtil.parse2ObjectArray((short[]) object);
			} else if (cmpType == double.class) {
				arr = CommonUtil.parse2ObjectArray((double[]) object);
			} else if (cmpType == float.class) {
				arr = CommonUtil.parse2ObjectArray((float[]) object);
			} else {
				arr = (Object[]) object;
			}
			return parseArray(arr, lay, excludeReg, linked);
		} else {
			return parseObject(object, lay, excludeReg, linked);
		}
	}

	private static String parseCollection(Collection<?> coll, int lay, String excludeReg, HashSet<Object> linkedParent) {
		StringBuilder sb = new StringBuilder();
		if (coll.isEmpty()) {
			sb.append(sb.append(CommonUtil.getPrefixBlank(lay)).append("[empty]"));
		} else {
			Object[] arr = coll.toArray(new Object[coll.size()]);
			sb.append(parseArray(arr, lay, excludeReg, linkedParent));
		}
		return sb.toString();
	}

	private static String parseArray(Object[] arr, int lay, String excludeReg, HashSet<Object> linkedParent) {
		StringBuilder sb = new StringBuilder();
		String blankStr = CommonUtil.getPrefixBlank(lay);
		if (ArrayUtils.isEmpty(arr)) {
			return blankStr + "[empty]";
		}
		for (int i = 0; i < arr.length; i++) {
			Object item = arr[i];
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(blankStr);
			sb.append("[" + i + "] : ");
			Class<?> type = (item == null) ? null : item.getClass();
			sb.append(parseItem(item, type, lay, excludeReg, linkedParent));
		}
		return sb.toString();
	}

	private static String parseMap(Map<?, ?> map, int lay, String excludeReg, HashSet<Object> linkedParent) {
		StringBuilder sb = new StringBuilder();
		String blankStr = CommonUtil.getPrefixBlank(lay);
		if (CollectionUtils.isEmpty(map)) {
			return blankStr + "[empty]";
		}
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> en = (Entry<?, ?>) it.next();
			Object key = en.getKey();
			Object value = en.getValue();
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(blankStr);
			sb.append("[key]" + key + " : ");

			Class<?> type = value == null ? null : value.getClass();
			sb.append(parseItem(value, type, lay, excludeReg, linkedParent));
		}

		return sb.toString();
	}

	private static String parseObject(Object object, int lay, String excludeReg, HashSet<Object> linkedParent) {
		// 复杂类型需要循环打印字段
		Field[] fs = ClassUtil.getFields(object.getClass());

		if (ArrayUtils.isEmpty(fs) || object instanceof ZqLogger) {
			return "";
		}
		BeanWrapperImpl beanWarpper = new BeanWrapperImpl(object);
		StringBuffer sb = new StringBuffer();
		for (Field f : fs) {
			String fName = f.getName();
			if (fName.startsWith("$") || (!StringUtils.isEmpty(excludeReg) && Pattern.matches(excludeReg, fName))) {
				continue;
			}
			if ("serialVersionUID".equals(fName)) {
				continue;
			}

			Class<?> fType = f.getType();
			try {
				Object fValue = beanWarpper.getPropertyValue(fName);
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(CommonUtil.getPrefixBlank(lay)).append(fName).append(" : ");
				sb.append(parseItem(fValue, fType, lay, excludeReg, linkedParent));
			} catch (Exception e) {
				new ZqLogger(LogUtil.class).error(e);
			}
		}
		return sb.toString();
	}

	private static String parseItem(Object value, Class<?> type, int lay, String excludeReg,
			HashSet<Object> linkedParent) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbType = new StringBuilder();
		if (type != null) {
			String typeName = type.isArray() ? type.getComponentType().getName() + "[]" : type.getName();
			sbType.append("                   ").append(typeName);
		}
		if (value == null) {
			sb.append("[null]");
			sb.append(sbType);
		} else if (!ClassUtil.isComplex(value.getClass())) {
			if (value instanceof Date) {
				value = DateUtil.getFormatDate((Date) value, DateUtil.DATE_FORMAT_EN_B_YYYYMMDDHHMMSS);
			}

			sb.append(value.toString());
			sb.append(sbType);
		} else {
			sb.append(sbType).append("@" + value.hashCode()).append("\n")
					.append(parse(value, lay + 1, excludeReg, linkedParent));
		}
		return sb.toString();
	}

}
