package com.madi.learningplatform.exceptions;

public class CollectionNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public CollectionNotFoundException() {
        super("Collection not found.");
    }

}
