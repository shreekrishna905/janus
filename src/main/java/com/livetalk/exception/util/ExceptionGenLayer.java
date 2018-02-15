package com.livetalk.exception.util;

/* Identifies the layer at which the exception has occured */

public enum ExceptionGenLayer {
	/* if type of exception could not be resolved */
	UNKNOWN("0x0"),

	/* e.g. foreign key constraints failed */
	DATABASE("0x1"),

	/*
	 * e.g. Post Method not allowed, API doesn't exist
	 */
	SERVICE("0x2"),

	/*
	 * e.g. Jackson Maping errors
	 */
	SERIALIZATION("0x3"),

	/*
	 * e.g. Unautorized api access attempt
	 */
	AUTHORIZATION("0x4");
	
	private String value;

	ExceptionGenLayer(String val) {
		this.value = val;
	}

	public String getValue() {
		return this.value;
	}
}
