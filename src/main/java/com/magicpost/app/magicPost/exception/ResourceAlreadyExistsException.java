package com.magicpost.app.magicPost.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String resource) {
        super("This " + resource + " already exist");
    }
}
