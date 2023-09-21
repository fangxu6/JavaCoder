package com.jse.commons.exception;

/**
 * 系统业务异常
 */
public class ZqException extends RuntimeException {
	private static final long	serialVersionUID	= -7819187888527567089L;

	/** 错误消息 */
	protected String			errorMsg;
	/** 错误代码 */
	protected String			errorCode;

	public ZqException() {
	}

	public ZqException(Throwable e) {
		super(e);
	}

	public ZqException(String message) {
		super(message);
		this.errorMsg = message;
	}

	/** 带错误代码的异常 */
	public ZqException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ZqException(String errorMsg, Throwable e) {
		super(errorMsg, e);
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "ZqException [errorMsg=" + errorMsg + ", errorCode=" + errorCode + "]";
	}

}
