package com.livetalk.exception.util;

public enum ExceptionSeverityLevel {
	
	UNKNOWN(00), CODE_LEVEL_SEVERITY(01), DATABASE_LEVEL(02), JSON_MAPPING(03), JSON_PARSE(04), INVALID_REQUEST(05);
	
	public Integer value;

	private ExceptionSeverityLevel(Integer val) {
		this.value = val;
	}

	public Integer getValue() {
		return this.value;
	}
}
