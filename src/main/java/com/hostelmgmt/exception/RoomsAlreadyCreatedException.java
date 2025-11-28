package com.hostelmgmt.exception;

public class RoomsAlreadyCreatedException extends RuntimeException {
    public RoomsAlreadyCreatedException(String message) {
        super(message);
    }
}
