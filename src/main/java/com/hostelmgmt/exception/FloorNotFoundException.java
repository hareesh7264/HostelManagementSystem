package com.hostelmgmt.exception;

public class FloorNotFoundException extends RuntimeException{
    public FloorNotFoundException(String message) {
        super(message);
    }
}
