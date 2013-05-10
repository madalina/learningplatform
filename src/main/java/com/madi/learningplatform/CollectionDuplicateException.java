package com.madi.learningplatform;

public class CollectionDuplicateException extends Exception {

    private static final long serialVersionUID = 1L;

    public CollectionDuplicateException() {
        super("A collection with this name already exists");
    }
}
