package com.hostelmgmt.exception;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException(String string) {
        super(string);
    }
}
