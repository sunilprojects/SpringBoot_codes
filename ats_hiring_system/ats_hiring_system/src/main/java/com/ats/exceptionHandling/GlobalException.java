package com.ats.exceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(EmailExistException.class)
	public ResponseEntity<Object> handleExp(EmailExistException x){
//		Map<String, Object> error=new HashMap();
//		error.put("message", x.getMessage());
		return new ResponseEntity<>(x.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecruiterException.class)
	public ResponseEntity<Object> recruiterExp(RecruiterException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	

}
