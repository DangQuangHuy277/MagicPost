package com.magicpost.app.magicPost.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super("The " + resource + " is not found");
    }
}
