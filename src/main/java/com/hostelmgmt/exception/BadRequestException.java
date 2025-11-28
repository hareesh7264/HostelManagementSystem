package com.hostelmgmt.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String string) {
        super(string);
    }
}
