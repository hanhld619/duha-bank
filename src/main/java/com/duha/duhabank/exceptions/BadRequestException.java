package com.duha.duhabank.exceptions;

public class BadRequestException extends RuntimeException {
	
	public BadRequestException(String error) {
		super(error);
	}
}
