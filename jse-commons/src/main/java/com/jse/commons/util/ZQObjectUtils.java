package com.jse.commons.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tiebin
 *
 */
public final class ZQObjectUtils implements Serializable {

	private static final long	serialVersionUID	= 1L;

	public static boolean simpleObjEquals(Object obj1, Object obj2) {
		if (ObjectUtils.equals(obj1, obj2)) {
			return true;
		}

		if (obj1.getClass() != obj2.getClass()) {
			return false;
		}

		List<String> allFieldNames = getAllFields(obj1, false);
		BeanWrapperImpl obj1Wrapper = new BeanWrapperImpl(obj1);
		BeanWrapperImpl obj2Wrapper = new BeanWrapperImpl(obj2);
		for (String filedName : allFieldNames) {
			if (!isSameFiledValue(obj1Wrapper, obj2Wrapper, filedName)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isSameFiledValue(BeanWrapperImpl obj1Wrapper, BeanWrapperImpl obj2Wrapper, String filedName) {
		Object value1 = obj1Wrapper.getPropertyValue(filedName);
		Object value2 = obj2Wrapper.getPropertyValue(filedName);
		return ObjectUtils.equals(value1, value2);
	}

	public static List<String> getAllFields(Object object, boolean searchSuper) {
		Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<Field>();
		if (searchSuper) {
			while (clazz != null) {
				fieldList.addAll(new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields())));
				clazz = clazz.getSuperclass();
			}
		} else {
			fieldList.addAll(new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields())));
		}

		List<String> result = new ArrayList<String>();
		for (Field filField : fieldList) {
			if (!StringUtils.equals("serialVersionUID", filField.getName())) {
				result.add(filField.getName());
			}
		}
		return result;
	}

}
