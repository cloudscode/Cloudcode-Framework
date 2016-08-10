package com.cloudcode.framework.exception;

public class PrimaryKeyUndefinedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2167561462330959802L;

	public PrimaryKeyUndefinedException(String msg) {
		super(msg);
	}

	public PrimaryKeyUndefinedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
