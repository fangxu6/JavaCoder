package com.jse.commons.exception;

public class StopServerOnThisAreaException extends RuntimeException {

	private static final long	serialVersionUID	= -1239102528091152248L;

	public StopServerOnThisAreaException() {
		super();
	}

	public StopServerOnThisAreaException(final String message) {
		super(message);
	}
}
