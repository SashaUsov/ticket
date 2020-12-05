package com.testproject.ticket.exception;

public class BusinessExceptions extends RuntimeException {
    BusinessExceptions(String message) {
        super(message);
    }
}
