package com.livetalk.exception.util;

public class UserDuplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public UserDuplicationException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	
}
