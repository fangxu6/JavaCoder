package com.jse.commons.exception;

/**
 * 金额非法异常。比如支付订单的退款金额大于预付金额
 */
@SuppressWarnings("serial")
public class InvalidAmountException extends ZqException {
	/**
	 * @param message
	 */
	public InvalidAmountException(String message) {
		super(message);
		this.errorMsg = message;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}
}
