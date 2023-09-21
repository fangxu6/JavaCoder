package com.jse.commons.log;

import com.alibaba.fastjson.JSON;
import com.jse.commons.util.CommonUtil;
import com.jse.commons.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一的日志输出对象
 */
public class ZqLogger {
	private Logger	logger;
	private String	prefix;

	public ZqLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz.getName());
	}

	/**
	 * @param logName
	 */
	public ZqLogger(String logName) {
		this.logger = LoggerFactory.getLogger(logName);
	}

	private String buildPrefix() {
		prefix = "";
		return prefix;
	}

	/**
	 * 打印出一个复杂对象，简单类型不建议用该方法。且该方法是debug级别
	 * 
	 * @name 打印的名称标识
	 * @param obj
	 */
	public void dump(String name, Object object) {
		long start = System.currentTimeMillis();
		try {
			StringBuffer sb = new StringBuffer("dump [\"" + name + "\"] : ");
			if (object == null) {
				sb.append("[null]");
			} else {
				sb.append(object.getClass().getName() + "@" + object.hashCode()).append("\n");
				sb.append(LogUtil.parse(object, 1, null, null));
			}
			sb.append("\n").append("dump finished");
			long end = System.currentTimeMillis();
			logger.debug(CommonUtil.concat(buildPrefix(), CommonUtil.concat(sb.toString()),
					LogUtil.buildTime(start, end)));

		} catch (Exception e) {
			logger.debug(name, e);
		}
	}

	/**
	 * 打印debug级别日志
	 * 
	 * @param msgArr
	 *            :多个日志信息
	 */
	public void debug(String className, String methodName, Object... msgArr) {
		if (LogUtil.isDebugEnabled()) {
			logger.debug(concatInfo(className, methodName, msgArr));
		}
	}

	/**
	 * 打印warn级别日志
	 * 
	 * @param msgArr
	 *            :多个日志信息
	 */
	public void warn(String className, String methodName, Object... msgArr) {
		logger.warn(concatInfo(className, methodName, msgArr));
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void error(Object... msgArr) {
		logger.error(CommonUtil.concat(buildPrefix(), CommonUtil.concat(msgArr)));
	}

	public void error(Throwable t, Object... msgArr) {
		logger.error(CommonUtil.concat(buildPrefix(), CommonUtil.concat(msgArr)), t);
	}

	/**
	 *
	 * @param className
	 * @param methodName
	 * @param message
	 * @param t
	 */

	public void error(String className, String methodName, Object message, Throwable t) {
		logger.error(concatInfo(className, methodName, message), t);
	}

	/**
	 *
	 * @param className
	 * @param methodName
	 * @param t
	 */

	public void error(String className, String methodName, Throwable t) {
		this.error(className, methodName, t.getMessage(), t);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	/**
	 * 打印info级别日志
	 */
	public void info(String className, String methodName, Object... msgArr) {
		if (LogUtil.isInfoEnabled()) {
			logger.info(concatInfo(className, methodName, msgArr));
		}
	}

	public void info(String msg) {
		if (LogUtil.isInfoEnabled()) {
			logger.info(msg);
		}
	}

	private String concatInfo(String className, String methodName, Object... msgArr) {
		StringBuffer sb = new StringBuffer();
		sb.append(className).append("[").append(methodName).append("]")
				.append(CommonUtil.concat(buildPrefix(), CommonUtil.concat(msgArr)));
		return sb.toString();
	}

	/**
	 * 打印JSON对象
	 */
	public void dumpJsonObject(String name, Object object) {
		if (object != null) {
			long start = System.currentTimeMillis();
			StringBuffer sb = new StringBuffer("dump [\"" + name + "\"] : ");
			sb.append(JSON.toJSONString(object));
			sb.append("\n").append("dumpJsonObject finished");
			long end = System.currentTimeMillis();
			logger.debug(CommonUtil.concat(buildPrefix(), CommonUtil.concat(sb.toString()),
					LogUtil.buildTime(start, end)));
		}
	}

}
