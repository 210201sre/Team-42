package com.revature.exceptions;

public class UserNotFoundException extends RuntimeException {

	/*fields*/
	private static final long serialVersionUID = 6085523359730828557L;

	/*constructors*/
	public UserNotFoundException() {}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/*getters*/
	
	/*setters*/
	
	/*functional methods*/
	
	/*Object class overrides*/
}
