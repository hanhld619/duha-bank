package com.duha.duhabank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.duha.duhabank.res.Response;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleAllUnknownExceptions(Exception ex){
        Response<?> response = Response.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Response<?>> handlerNotFoundException(NotFoundException ex) {
		
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<Response<?>> handlerInsufficientBalanceException(InsufficientBalanceException ex) {
		
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<Response<?>> handlerInvalidTransactionException(InvalidTransactionException ex) {
		
		Response<?> response = Response.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
