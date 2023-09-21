package com.jse.commons.exception;

public class AuthenticationException extends RuntimeException {
	private static final long	serialVersionUID	= -4722114190546725979L;

	public AuthenticationException() {
        super();
    }
    
    public AuthenticationException(final String message) {
        super(message);
    }
}
