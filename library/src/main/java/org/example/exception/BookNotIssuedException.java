package org.example.exception;

public class BookNotIssuedException extends RuntimeException {
    public BookNotIssuedException(String message) {
        super(message);
    }
}
