package com.coding.SecurityApp.SecurityApplication.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
