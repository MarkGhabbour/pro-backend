package com.maiia.pro.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(NotFoundException exc)
	{	
		ErrorResponse err = new ErrorResponse();
		err.setMessage(exc.getMessage());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(DuplicationException exc)
	{	
		ErrorResponse err = new ErrorResponse();
		err.setMessage(exc.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc)
	{
		ErrorResponse err = new ErrorResponse();
		err.setMessage(exc.getMessage());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
	}

}

