package com.jse.commons.exception;

public class StopServiceException extends RuntimeException {

	private static final long	serialVersionUID	= -1239102528091152248L;

	public StopServiceException() {
		super();
	}

	public StopServiceException(final String message) {
		super(message);
	}
}
