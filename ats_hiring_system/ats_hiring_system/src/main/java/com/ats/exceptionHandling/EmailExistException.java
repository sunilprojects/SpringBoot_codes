package com.ats.exceptionHandling;

public class EmailExistException extends RuntimeException {

	public EmailExistException(String msg) {
		super(msg);
	}
	
}
