package com.retail.order_management.exception;

/**
 * Custom exception for duplicate resource scenarios.
 * Thrown when trying to create a user with duplicate email or phone number.
 */
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }

}

