package com.hostelmgmt.exception;

public class RoomIdNotFoundException extends RuntimeException {
    public RoomIdNotFoundException(String message) {
        super(message);
    }
}
