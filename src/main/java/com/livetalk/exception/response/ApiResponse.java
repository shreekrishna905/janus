package com.livetalk.exception.response;

public class ApiResponse {
	public Integer httpStatusCode;
	public String errorLevelCode;
	public String errorCode;
	public Integer severityLevel;
	public String errorMessage;
	
	public ApiResponse(){
		
	}

	public ApiResponse(Integer httpStatusCode, String errorLevelCode, String errorCode, Integer severityLevel,
			String errorMessage) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.errorLevelCode = errorLevelCode;
		this.errorCode = errorCode;
		this.severityLevel = severityLevel;
		this.errorMessage = errorMessage;
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getErrorLevelCode() {
		return errorLevelCode;
	}

	public void setErrorLevelCode(String errorLevelCode) {
		this.errorLevelCode = errorLevelCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(Integer severityLevel) {
		this.severityLevel = severityLevel;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
