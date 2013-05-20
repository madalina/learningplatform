package com.madi.learningplatform.exceptions;

public class NoteNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoteNotFoundException() {
        super("Note not found!");
    }

}
