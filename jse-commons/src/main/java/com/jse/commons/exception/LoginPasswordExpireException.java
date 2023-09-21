package com.jse.commons.exception;

public class LoginPasswordExpireException extends RuntimeException {

	private static final long	serialVersionUID	= -1239102528091152248L;

	public LoginPasswordExpireException() {
		super();
	}

	public LoginPasswordExpireException(final String message) {
		super(message);
	}
}
