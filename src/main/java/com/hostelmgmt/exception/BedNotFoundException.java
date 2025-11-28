package com.hostelmgmt.exception;

public class BedNotFoundException extends RuntimeException {
    public BedNotFoundException(String bedNotFound) {
        super(bedNotFound);
    }
}
