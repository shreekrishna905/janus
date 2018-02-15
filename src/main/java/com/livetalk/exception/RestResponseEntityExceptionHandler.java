package com.livetalk.exception;
import java.sql.SQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.livetalk.exception.response.ApiResponse;
import com.livetalk.exception.util.ExceptionCode;
import com.livetalk.exception.util.ExceptionGenLayer;
import com.livetalk.exception.util.ExceptionSeverityLevel;
import com.livetalk.exception.util.UserDuplicationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { JsonMappingException.class, JsonParseException.class })
    protected ResponseEntity<Object> handleJsonMapping(RuntimeException ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.SERIALIZATION.getValue(), ExceptionCode.JSON_MAPPING_EXCEPTION.getValue(), ExceptionSeverityLevel.JSON_PARSE.getValue(), ex.getLocalizedMessage());
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = { IndexOutOfBoundsException.class })
    protected ResponseEntity<Object> handleIndexBound(RuntimeException ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.SERIALIZATION.getValue(), ExceptionCode.INDEX_BOUND.getValue(), ExceptionSeverityLevel.CODE_LEVEL_SEVERITY.getValue(), ex.getLocalizedMessage());
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<Object> handleNullPointer(RuntimeException ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.SERIALIZATION.getValue(), ExceptionCode.NULL_POINTER.getValue(), ExceptionSeverityLevel.CODE_LEVEL_SEVERITY.getValue(), ex.getLocalizedMessage());
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = { UserDuplicationException.class })
    protected ResponseEntity<Object> handleUserDuplication(UserDuplicationException ex, WebRequest request) {
        ApiResponse response = new ApiResponse(HttpStatus.CONFLICT.value(), ExceptionGenLayer.SERIALIZATION.getValue(), ExceptionCode.NULL_POINTER.getValue(), ExceptionSeverityLevel.CODE_LEVEL_SEVERITY.getValue(), ex.getMessage());
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(value = { SQLException.class })
    protected ResponseEntity<Object> handleSqlException(SQLException ex, WebRequest request) {
		ApiResponse response = null;
		if(((SQLException) ex).getSQLState().startsWith("23")){
			 response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.DATABASE.getValue(), ExceptionCode.MYSQL_INTEGRITY_CONSTRAINT_VIOLATION_EXCEPTION.getValue(), ExceptionSeverityLevel.DATABASE_LEVEL.getValue(), ex.getLocalizedMessage());
		} else if(((SQLException) ex).getSQLState().startsWith("42")){
			 response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.DATABASE.getValue(), ExceptionCode.COLUMN_MISSING.getValue(), ExceptionSeverityLevel.DATABASE_LEVEL.getValue(), ex.getLocalizedMessage());
		} else {
			 response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ExceptionGenLayer.DATABASE.getValue(), ExceptionCode.NULL_POINTER.getValue(), ExceptionSeverityLevel.DATABASE_LEVEL.getValue(), ex.getLocalizedMessage());
		}
        return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
	
	
}
