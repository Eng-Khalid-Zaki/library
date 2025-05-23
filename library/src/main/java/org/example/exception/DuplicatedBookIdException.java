package org.example.exception;

public class DuplicatedBookIdException extends RuntimeException {
    public DuplicatedBookIdException(String message) {
        super(message);
    }
}
