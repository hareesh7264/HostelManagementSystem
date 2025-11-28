package com.hostelmgmt.exception;

public class FloorNumberNotFoundException extends RuntimeException {
    public FloorNumberNotFoundException(String message) {
        super(message);
    }
}
