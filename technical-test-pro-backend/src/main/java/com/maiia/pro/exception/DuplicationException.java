package com.maiia.pro.exception;

public class DuplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicationException() {
		super();

	}

	public DuplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public DuplicationException(String message, Throwable cause) {
		super(message, cause);

	}

	public DuplicationException(String message) {
		super(message);

	}

	public DuplicationException(Throwable cause) {
		super(cause);

	}

}
