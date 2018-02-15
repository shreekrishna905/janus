package com.livetalk.exception.util;


public enum ExceptionCode {
	UNKNOWN_EXCEPTION("0x0"), JSON_MAPPING_EXCEPTION("0x1"), NULL_POINTER("0x2"), INDEX_BOUND("0x3"), MYSQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION("0x4"), JSON_PROCESSING_EXCEPTION("0x5"),INPUT_OUTPUT("0x6"),COLUMN_MISSING("0x7");
	public String value = "";
	
	ExceptionCode(String val) {
		this.value = val;
	}

	public String getValue() {
		return this.value;
	}
}
