package com.magicpost.app.magicPost.exception;

public class InvalidRequestDataException extends RuntimeException{
    public InvalidRequestDataException(String message) {
        super(message);
    }
}
