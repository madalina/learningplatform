package com.madi.learningplatform.exceptions;

public class CollectionDuplicateException extends Exception {

    private static final long serialVersionUID = 1L;

    public CollectionDuplicateException() {
        super("A collection with this name already exists");
    }
}
