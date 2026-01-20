package com.duha.duhabank.exceptions;

public class InvalidTransactionException extends RuntimeException {

	private InvalidTransactionException(String error) {
		super(error);
	}
}
