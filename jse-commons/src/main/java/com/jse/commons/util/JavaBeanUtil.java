/**
 * @Title: JavaBeanUtil.java
 * @Package com.zhuanqian.commons.util
 * @Description: 
 * @author xuzhu
 * @date 2019年4月11日
 * @version V1.0
 */

package com.jse.commons.util;

import com.zhuanqian.commons.annotation.NeedUpdate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JavaBeanUtil
 * @Description:
 * @author xuzhu
 * @date 2019年4月11日 下午4:36:27
 */

public class JavaBeanUtil {
	/**
	 * 实体对象转成Map
	 * 
	 * @param obj
	 *            实体对象
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Object> object2MapIgnoreNullValue(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(obj) != null) {
					map.put(field.getName(), field.get(obj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Map转成实体对象
	 * 
	 * @param map
	 *            map实体对象包含属性
	 * @param clazz
	 *            实体对象类型
	 * @return
	 */
	public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
		if (map == null) {
			return null;
		}
		T obj = null;
		try {
			obj = clazz.newInstance();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				field.set(obj, map.get(field.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 *
	 * @param common
	 * @return
	 */

	public static Map<String, Object> object2MapPreserveFieldWithNeedUpdateAnnotation(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				Annotation[] annotations = field.getAnnotations();
				if (annotations != null) {
					for (Annotation an : annotations) {
						if (an instanceof NeedUpdate) {
							field.setAccessible(true);
							if (field.get(obj) != null) {
								map.put(field.getName(), field.get(obj));
							}
							continue;
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
