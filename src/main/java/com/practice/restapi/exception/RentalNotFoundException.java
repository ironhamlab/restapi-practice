package com.practice.restapi.exception;

public class RentalNotFoundException extends RuntimeException{

    RentalNotFoundException(String message) {
        super(message);
    }
}
