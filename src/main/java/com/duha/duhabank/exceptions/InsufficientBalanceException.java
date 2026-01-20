package com.duha.duhabank.exceptions;

public class InsufficientBalanceException extends RuntimeException{
	
	private InsufficientBalanceException(String error) {
		super(error);
	}
}
