package com.jse.commons.util;

import net.sf.cglib.core.Converter;

/**
 * Cglib转换器，针对Cglib不能转换的类型手动处理
 * 
 * @company jse-zq
 * @author 铁镔
 * @version CglibConverter.java, v 0.1 2015年5月8日 下午5:53:32
 */
public class CglibConverter implements Converter {

	/**
	 * @see net.sf.cglib.core.Converter#convert(Object, Class, Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convert(Object value, Class target, Object context) {
		if (value == null) {
			return null;
		}

		return value;
	}
}
