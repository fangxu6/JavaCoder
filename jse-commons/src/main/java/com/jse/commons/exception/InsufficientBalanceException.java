package com.jse.commons.exception;

/**
 * 账户余额不足，包括支付账户余额不足和股票份额账户余额不足。发生在转账、支付等场景
 */
@SuppressWarnings("serial")
public class InsufficientBalanceException extends ZqException {
	/**
	 * @param message
	 */
	public InsufficientBalanceException(String message) {
		super(message);
		this.errorMsg = message;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

}
