package com.jse.commons.exception;

public class JsonRequestAuthenticationException extends RuntimeException {

	private static final long	serialVersionUID	= -1239102528091152248L;

	public JsonRequestAuthenticationException() {
        super();
    }
    
    public JsonRequestAuthenticationException(final String message) {
        super(message);
    }
}
