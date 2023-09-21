package com.jse.commons.util;

import com.zhuanqian.commons.exception.ZqException;
import com.zhuanqian.commons.log.ZqLogger;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @company jse-zq
 * @author 铁镔
 * @version CommonConverter.java, v 0.1 2015年5月8日 下午5:49:33
 */
public class CommonConverter {
	/** logger */
	private static final ZqLogger					logger			= new ZqLogger(CommonConverter.class);

	/** 通用转换器缓存 */
	private static final Map<String, BeanCopier>	beanCopierMap	= new ConcurrentHashMap<String, BeanCopier>();

	/** Cglib转换器 */
	private static final Converter					converter		= new CglibConverter();

	/**
	 * 单个对象 source --> target
	 * 
	 * @param <T>
	 * @param <E>
	 * @param source
	 * @param target
	 */
	public static <T, E> void convert(T source, E target) {
		// 参数校验
		if (source == null || target == null) {
			return;
		}

		String key = source.getClass() + " " + target.getClass();

		BeanCopier beanCopier;
		if (beanCopierMap.get(key) == null) {
			beanCopier = BeanCopier.create(source.getClass(), target.getClass(), true);
			beanCopierMap.put(key, beanCopier);
		} else {
			beanCopier = beanCopierMap.get(key);
		}

		beanCopier.copy(source, target, converter);

	}

	/**
	 * 数组对象转换
	 * 
	 * @param <T>
	 * @param <E>
	 * @param targetClass
	 * @param sources
	 * @param targets
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T, E extends Object> void convertList(Class<?> targetClass, Collection<T> sources,
			Collection<E> targets) {
		// 参数校验
		if (targetClass == null || CollectionUtils.isEmpty(sources) || targets == null) {
			return;
		}
		try {
			for (T source : sources) {
				E target = (E) targetClass.newInstance();
				convert(source, target);
				targets.add(target);
			}
		} catch (Exception e) {
			logger.error("CommonConverter", "convertList", e);
			throw new ZqException("转换出错");
		}
	}

	/**
	 * 数组转换为list
	 *
	 * @param targetArray
	 * @return
	 */
	public static <T> ArrayList<T> convertArrayToList(T[] targetArray) {
		ArrayList<T> list = new ArrayList<T>();
		for (T element : targetArray) {
			list.add(element);
		}
		return list;
	}

	/**
	 * 将字符串转换成目标类型的list
	 *
	 * @param targetString
	 * @param seperator
	 * @param targetClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E> convertStringToList(String targetString, String seperator, Class<?> targetClass) {
		// 参数校验
		if (targetClass == null || StringUtils.isBlank(seperator) || StringUtils.isBlank(targetString)) {
			return null;
		}
		ArrayList<E> targetList = new ArrayList<E>();
		try {
			for (String element : targetString.split(seperator)) {
				E target = (E) targetClass.newInstance();
				convert(element, target);
				targetList.add(target);
			}
		} catch (Exception e) {
			logger.error("CommonConverter", "convertStringToList", e);
			throw new ZqException("转换出错");
		}
		return targetList;
	}

	/**
	 * 将字符串转换成数值类型的list
	 *
	 * @param targetString
	 * @param seperator
	 * @param targetClass
	 * @return
	 */
	public static <T extends Number> List<T> convertToNumberList(String targetString, String seperator,
			Class<T> targetClass) {
		if (targetString == null) {
			return Collections.emptyList();
		}
		if (StringUtils.isBlank(seperator)) {
			seperator = ",";
		}
		List<T> resultList = new ArrayList<T>();
		for (String text : StringUtils.split(targetString, seperator)) {
			resultList.add(NumberUtils.parseNumber(text, targetClass));
		}
		return resultList;
	}

	public static <T extends Number> List<T> convertToNumberList(String targetString, Class<T> targetClass) {
		return convertToNumberList(targetString, null, targetClass);
	}

	/**
	 * javabean转换成map
	 *
	 * @param object
	 * @return
	 */
	public static Map<Object, Object> beanToMap(Object object) {
		if (object == null) {
			return null;
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String objectKey = property.getName();
				if (!"class".equals(objectKey)) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value != null) {
						map.put(objectKey, value);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}
}