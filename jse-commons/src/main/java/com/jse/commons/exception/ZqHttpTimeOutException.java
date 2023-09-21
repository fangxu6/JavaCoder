package com.jse.commons.exception;

/**
 * @company jse-zq
 * @author tiebin
 * @version ZqHttpTimeOutException.java, v 0.1 2015年10月14日 下午2:10:42
 */
public class ZqHttpTimeOutException extends ZqException {

	private static final long	serialVersionUID	= 1L;

	public ZqHttpTimeOutException(String message) {
		this.errorMsg = message;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
