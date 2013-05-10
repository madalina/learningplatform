package com.madi.learningplatform;

public class CollectionNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public CollectionNotFoundException() {
        super("Collection not found.");
    }

}
