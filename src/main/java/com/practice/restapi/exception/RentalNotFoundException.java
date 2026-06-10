package com.practice.restapi.exception;

public class RentalNotFoundException extends RuntimeException{

    public RentalNotFoundException(String message) {
        super(message);
    }
}
