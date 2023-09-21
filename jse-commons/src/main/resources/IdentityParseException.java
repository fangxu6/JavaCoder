package com.jse.commons.exception;

/**
 * 身份证图片解析异常
 * @company 杭州转乾网络
 * @author Infernalzero
 * @version IdentityParseException.java, v 0.1 2016年5月3日 下午1:27:21
 */

public class IdentityParseException extends RuntimeException {

	private static final long	serialVersionUID	= 1377151559887920739L;

	public IdentityParseException() {
		super();
	}

	/**
	 * @param message
	 */
	public IdentityParseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IdentityParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IdentityParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IdentityParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
