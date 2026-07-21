package com.example.walletapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;
@Hidden
@RestControllerAdvice(basePackages = "com.example.walletapi.controller")
public class globalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
   public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	



//Catch every other type of unexpected exception
           
     @ExceptionHandler(Exception.class)
     public ResponseEntity<String> generalException(Exception ex){
    	 return new ResponseEntity <>("an unexpected system error",HttpStatus.INTERNAL_SERVER_ERROR);
     }
     
     
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
    	 Map<String,String> errors=new HashMap<>();
    	 ex.getBindingResult().getAllErrors().forEach((error) ->{
    		 String FieldName=((FieldError)error).getField();
    		 String errorMessage=error.getDefaultMessage();
    		 errors.put(FieldName,errorMessage);
    		 
    		 
    	 });
    		 return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
     
     }
}